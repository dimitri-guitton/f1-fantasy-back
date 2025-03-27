package com.dg.f1fantasyback.controller;

import com.dg.f1fantasyback.service.FileStorageService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class FileController {
    private final FileStorageService fileStorageService;

    public FileController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @GetMapping("/images/{imageName}")
    public ResponseEntity<byte[]> getImage(@PathVariable String imageName) {
        try {
            byte[] image = fileStorageService.loadFileAsBytes(imageName);
            String contentType = "image/jpeg";
            if (imageName.endsWith(".png")) {
                contentType = "image/png";
            } else if (imageName.endsWith(".gif")) {
                contentType = "image/gif";
            }else if (imageName.endsWith(".avif")) {
                contentType = "image/avif";
            }

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, contentType)
                    .body(image);
        } catch (IOException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
