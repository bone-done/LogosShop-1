package com.vansisto.logosshop.service.impl;

import com.vansisto.logosshop.exception.NotFoundException;
import com.vansisto.logosshop.service.FileStorageService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.UUID;

@Slf4j
@Service
public class FileStorageServiceImpl implements FileStorageService {

    private final Path fileStorageLocation;

    public FileStorageServiceImpl(@Value("${custom.paths.file-directory}") String customPathsFileDirectory){
        String uploadsDir = System.getProperty("user.home") +
                    System.getProperty("file.separator") +
                    customPathsFileDirectory;

        this.fileStorageLocation = Paths.get(uploadsDir).toAbsolutePath().normalize();

        try {
            Files.createDirectories(fileStorageLocation);
        } catch (IOException e){
            log.error("Directory creation failed ({})", uploadsDir, e);
        }
    }

    @SneakyThrows
    @Override
    public boolean isExists(String fileName) {
        Path filePath = fileStorageLocation.resolve(fileName);
        Resource resource = new UrlResource(filePath.toUri());
        return resource.exists();
    }

    @Override
    public String storeFile(MultipartFile file) {
        String fileName;
        do {
            fileName = UUID.randomUUID().toString() + "." +
                    Arrays.stream(file.getOriginalFilename().split("\\.")).reduce((f, s) -> s).orElseThrow( () ->
                                new RuntimeException("Unable to get original file format")
                    );
        } while (isExists(fileName));

        Path targetLocation = fileStorageLocation.resolve(fileName);
        try {
            Files.copy(file.getInputStream(), targetLocation);
        } catch (IOException e) {
            log.error("File copying error", e);
            fileName = null;
        }
        return fileName;
    }

    @Override
    public Resource loadFileByFilename(String fileName) {
        if (isExists(fileName)){
            try{
                return new UrlResource(fileStorageLocation.resolve(fileName).toUri());
            } catch (MalformedURLException e) {
                log.error("File downloading error", e);
            }
        } else throw new NotFoundException("File", "fileName", fileName);
        return null;
    }
}
