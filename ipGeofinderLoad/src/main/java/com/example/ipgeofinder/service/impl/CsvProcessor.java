package com.example.ipgeofinder.service.impl;

import com.example.ipgeofinder.model.EventType;
import com.example.ipgeofinder.model.IpData;
import com.example.ipgeofinder.repository.IpDataRepository;
import com.example.ipgeofinder.service.AuditService;
import com.example.ipgeofinder.service.FileProcessor;
import com.example.ipgeofinder.service.MappingService;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
public class CsvProcessor implements FileProcessor {

    private static final Logger log = LoggerFactory.getLogger(CsvProcessor.class);

    private final int blockSize; // Tamaño del bloque a procesar

    private final List<String> headers;
    private final int threadsLimit;

    private final MappingService mappingService;

    private final IpDataRepository ipDataRepository;

    private final AuditService auditService;

    public CsvProcessor(
            @Value("${csv.block.size}") int blockSize,
            @Value("#{'${csv.headers}'.split(',')}") List<String> headers,
            @Value("${csv.concurrency.limit}") int threadsLimit,
            MappingService mappingService, IpDataRepository ipDataRepository, AuditService auditService) {
        this.blockSize = blockSize;
        this.headers = headers;
        this.threadsLimit = threadsLimit;
        this.mappingService = mappingService;
        this.ipDataRepository = ipDataRepository;
        this.auditService = auditService;
    }

    @Override
    public void process(InputStream inputStream) {
        log.info("Starting processing file");
        try (CSVReader reader = new CSVReaderBuilder(new InputStreamReader(inputStream)).build()) {

            String[][] block = new String[blockSize][headers.size()];

            int count; // Contador de líneas

            List<Future<?>> futures = new ArrayList<>();
            ExecutorService executorService = Executors.newFixedThreadPool(threadsLimit);

            while ((count = readNext(block, 0, blockSize, reader)) != 0) { // Leer bloque
                String[][] blockToProcess = block.clone();
                int finalCount = count;
                Future<?> future = executorService.submit(() -> processBlock(blockToProcess, finalCount)); // Ejecutar bloque de procesamiento en paralelo
                futures.add(future);
                block = new String[blockSize][headers.size()]; // Reiniciar bloque
            }

            executorService.shutdown();

            while (!futures.isEmpty()) {
                Iterator<Future<?>> it = futures.iterator();
                while (it.hasNext()) {
                    Future<?> future = it.next();
                    if (future.isDone()) {
                        it.remove();
                    }
                }
            }

            log.info("Finished processing file");
        } catch (IOException | CsvValidationException e) {
            throw new RuntimeException(e);
        }
    }

    private void processBlock(String[][] block, int count) {
        List<IpData> ipDataList = mappingService.mapBlockToEntity(block, count);
        ipDataRepository.saveAll(ipDataList);
        auditService.audit(String.format("Load a block of %s lines", count), EventType.WRITE);
    }

    private int readNext(String[][] block, int offset, int maxRows, CSVReader csvReader) throws IOException, CsvValidationException {
        int rowCount = 0;
        String[] line;
        while (rowCount < maxRows && (line = csvReader.readNext()) != null) {
            block[rowCount + offset] = line;
            rowCount++;
        }
        return rowCount;
    }
}
