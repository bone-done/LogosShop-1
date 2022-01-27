package com.vansisto.logosshop.service.impl;

import com.vansisto.logosshop.service.FileStorageService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.support.MethodReplacer;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Slf4j
@Service
public class FileStorageServiceImpl implements FileStorageService {

    @Value("${custom.paths.file-directory}")
    private String customPathsFileDirectory;

    private final String HOME_PATH = System.getProperty("user.home");
    private final String FILE_SEPARATOR = System.getProperty("file.separator");

    private String uploadsDir;

    private final Path fileStorageLocation;

    public FileStorageServiceImpl(){
        uploadsDir = HOME_PATH + FILE_SEPARATOR + "uploads";
        log.info(customPathsFileDirectory);

        this.fileStorageLocation = Paths.get(uploadsDir).toAbsolutePath().normalize();

        try {
            Files.createDirectories(fileStorageLocation);
        } catch (IOException e){
            log.error("Directory creation failed (" + uploadsDir + ")", e);
        }
    }

    @SneakyThrows
    @Override
    public boolean isExists(String fileName) {
        log.info(customPathsFileDirectory);
        Path filePath = fileStorageLocation.resolve(fileName);
        Resource resource = new UrlResource(filePath.toUri());
        return resource.exists();    }

    @Override
    public String storeFile(MultipartFile file) {
        log.info(customPathsFileDirectory);

        String fileName;
        do {
            fileName = UUID.randomUUID().toString() + "." + file.getOriginalFilename().split("\\.")[1];
        } while (isExists(fileName));

        Path targetLocation = fileStorageLocation.resolve(fileName);
        try {
            Files.copy(file.getInputStream(), targetLocation);
        } catch (IOException e) {
            log.error("File copying error", e);
        }
        return fileName;
    }

    @Override
    public Resource loadFileByFilename(String fileName) {
        Path filePath = fileStorageLocation.resolve(fileName);
        try {
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) return resource;
//            TODO:
            else throw new Exception("File not found");
        } catch (Exception e) {
            log.error("File downloading error", e);
        }
        return null;
    }
}
