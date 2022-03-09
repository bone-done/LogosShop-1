package com.vansisto.logosshop.controller;

import com.vansisto.logosshop.domain.ImageDTO;
import com.vansisto.logosshop.service.FileStorageService;
import com.vansisto.logosshop.service.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Slf4j
@Validated
@CrossOrigin
@RestController
@RequestMapping("/image")
public class ImageController {

    @Autowired
    private ImageService service;

    @Autowired
    private FileStorageService fileStorageService;

    @GetMapping("{id}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<ImageDTO> getImageById(@PathVariable String id){
        log.info("Get image entity by id: {}", id);
        return ResponseEntity.ok(service.getEntity(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ImageDTO> saveImage(
            @Positive @RequestParam Long productId,
            @RequestParam Boolean isHeadPicture,
            @RequestParam MultipartFile file){
        log.info("Storing file: {}", file.getOriginalFilename());
        String storedFileName = fileStorageService.storeFile(file);

        ImageDTO dto = new ImageDTO();
        dto.setFileName(storedFileName);
        dto.setIsHeadPicture(isHeadPicture);

        log.info("Creating image entity with id {} for product with id {}", dto.getId(), productId);
        return ResponseEntity.ok(service.createForProductById(dto, productId));
    }

    @GetMapping("/file/{fileName}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<Resource> getImageResourceByFilename(@PathVariable String fileName){
        log.debug("Getting image file by name: {}", fileName);
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.IMAGE_PNG)
                .body(fileStorageService.loadFileByFilename(fileName));
    }

    @GetMapping("byProductId/{productId}")
    public ResponseEntity<ImageDTO> getImageEntityByProductId(@Positive @NotNull @PathVariable Long productId) {
        log.debug("Getting image entity by product with id: {}", productId);
        return ResponseEntity.ok(service.getEntityByProductId(productId));
    }
}
