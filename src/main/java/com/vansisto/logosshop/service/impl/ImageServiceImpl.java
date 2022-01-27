package com.vansisto.logosshop.service.impl;

import com.vansisto.logosshop.domain.ImageDTO;
import com.vansisto.logosshop.entity.Image;
import com.vansisto.logosshop.repository.ImageRepository;
import com.vansisto.logosshop.service.ImageService;
import com.vansisto.logosshop.util.ModelMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageRepository repository;

    @Autowired
    private ModelMapperUtil mapper;

    @Override
    public ImageDTO create(ImageDTO dto) {
        Image savedEntity = repository.save(map(dto));
        return map(savedEntity);
    }

    @Override
    public ImageDTO update(ImageDTO dto) {
        Image updatedEnity = repository.save(map(dto));
        return map(updatedEnity);
    }

    @Override
    public ImageDTO delete(ImageDTO dto) {
        repository.delete(mapper.map(dto, Image.class));
        return dto;
    }

    @Override
    public String deleteById(String id) {
        repository.deleteById(id);
        return id;
    }

    @Override
    public ImageDTO getEntity(String id) {
        return mapper.map(repository.getById(id), ImageDTO.class);
    }

    @Override
    public List<ImageDTO> getAll() {
        return mapper.mapAll(repository.findAll(), ImageDTO.class);
    }


    private ImageDTO map(Image entity) {
        return mapper.map(entity, ImageDTO.class);
    }

    private Image map(ImageDTO dto) {
        return mapper.map(dto, Image.class);
    }
}
