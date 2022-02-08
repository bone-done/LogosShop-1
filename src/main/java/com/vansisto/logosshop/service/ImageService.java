package com.vansisto.logosshop.service;

import com.vansisto.logosshop.domain.ImageDTO;

import java.util.List;
import java.util.Optional;

public interface ImageService {
    ImageDTO create(ImageDTO dto);
    ImageDTO update(ImageDTO dto);
    ImageDTO delete(ImageDTO dto);
    String deleteById(String id);
    ImageDTO getEntity(String id);
    List<ImageDTO> getAll();
}
