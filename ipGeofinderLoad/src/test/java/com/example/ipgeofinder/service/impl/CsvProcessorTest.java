package com.example.ipgeofinder.service.impl;

import com.example.ipgeofinder.model.EventType;
import com.example.ipgeofinder.repository.IpDataRepository;
import com.example.ipgeofinder.service.AuditService;
import com.example.ipgeofinder.service.FileProcessor;
import com.example.ipgeofinder.service.MappingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class CsvProcessorTest {

    private FileProcessor processor;
    private MappingService mappingService;
    private IpDataRepository ipDataRepository;
    private AuditService auditService;

    @BeforeEach
    public void SetUp() {
        mappingService = mock(MappingService.class);
        ipDataRepository = mock(IpDataRepository.class);
        auditService = mock(AuditService.class);
        processor = new CsvProcessor(1, Arrays.asList("column1, column2"), 1, mappingService, ipDataRepository, auditService);
    }

    @Test
    public void processTestSuccess() {
        String testData = "value1,value2";
        InputStream inputStream = new ByteArrayInputStream(testData.getBytes());

        when(mappingService.mapBlockToEntity(any(), anyInt())).thenReturn(new ArrayList());
        when(ipDataRepository.saveAll(any())).thenReturn(new ArrayList());

        processor.process(inputStream);

        verify(mappingService, times(1)).mapBlockToEntity(any(), anyInt());
        verify(ipDataRepository, times(1)).saveAll(any());
        verify(auditService, times(1)).audit("Load a block of 1 lines", EventType.WRITE);
    }

    @Test
    public void processTestFail() {
        String testData = "";
        InputStream inputStream = new ByteArrayInputStream(testData.getBytes());

        when(mappingService.mapBlockToEntity(any(), anyInt())).thenReturn(new ArrayList());
        when(ipDataRepository.saveAll(any())).thenReturn(new ArrayList());

        processor.process(inputStream);

        verify(mappingService, times(0)).mapBlockToEntity(any(), anyInt());
        verify(ipDataRepository, times(0)).saveAll(any());
        verify(auditService, times(0)).audit("Load a block of 1 lines", EventType.WRITE);
    }
}
