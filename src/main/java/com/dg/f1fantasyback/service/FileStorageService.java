package com.dg.f1fantasyback.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileStorageService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    public String storeFile(MultipartFile file) throws IOException {
        // Générer un nom unique pour l'image
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(uploadDir).resolve(fileName);

        // Créer le dossier s'il n'existe pas
        Files.createDirectories(filePath.getParent());

        // Sauvegarder le fichier
        Files.copy(file.getInputStream(), filePath);

        return fileName; // Retourne le nom du fichier sauvegardé
    }

    public void removeFile(String fileName) throws IOException {
        Path filePath = Paths.get(uploadDir).resolve(fileName);
        Files.delete(filePath);
    }

    public byte[] loadFileAsBytes(String fileName) throws IOException {
        Path filePath = Paths.get(uploadDir).resolve(fileName);
        return Files.readAllBytes(filePath);
    }
}
