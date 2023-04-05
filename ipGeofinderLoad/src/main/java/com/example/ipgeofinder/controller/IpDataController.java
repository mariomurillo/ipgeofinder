package com.example.ipgeofinder.controller;

import com.example.ipgeofinder.service.FileProcessor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/data")
public class IpDataController {

    private FileProcessor fileProcessor;

    public IpDataController(FileProcessor fileService) {
        this.fileProcessor = fileService;
    }

    @PostMapping("/upload-csv")
    public ResponseEntity<String> uploadCsv(@RequestParam("csvFile") MultipartFile csvFile) {
        try {
            fileProcessor.process(csvFile.getInputStream());
            return ResponseEntity.ok("Archivo CSV procesado y guardado correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocurrió un error al procesar el archivo CSV: " + e.getMessage());
        }
    }
}
