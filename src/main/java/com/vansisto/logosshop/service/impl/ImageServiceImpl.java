package com.vansisto.logosshop.service.impl;

import com.vansisto.logosshop.domain.ImageDTO;
import com.vansisto.logosshop.entity.Image;
import com.vansisto.logosshop.exception.AlreadyExistsException;
import com.vansisto.logosshop.exception.NotFoundException;
import com.vansisto.logosshop.repository.ImageRepository;
import com.vansisto.logosshop.service.ImageService;
import com.vansisto.logosshop.util.ModelMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageRepository repository;

    @Autowired
    private ModelMapperUtil mapper;

    @Override
    public ImageDTO create(ImageDTO dto) {
        if (!Objects.isNull(dto.getId()) && repository.existsById(dto.getId()))
            throw new AlreadyExistsException("Image entity", "id", dto.getId());
        return map(repository.save(map(dto)));
    }

    @Override
    public ImageDTO update(ImageDTO dto) {
        if (Objects.isNull(dto.getId()) && !repository.existsById(dto.getId()))
            throw new NotFoundException("Image entity", "id", dto.getId());
        return map(repository.save(map(dto)));
    }

    @Override
    public ImageDTO delete(ImageDTO dto) {
        repository.delete(map(dto));
        return dto;
    }

    @Override
    public String deleteById(String id) {
        if (!repository.existsById(id)) throw new NotFoundException("Image entity", "id", id);
        repository.deleteById(id);
        return id;
    }

    @Override
    public ImageDTO getEntity(String id) {
        Image image = repository.findById(id).orElseThrow(() -> new NotFoundException("Image entity", "id", id));
        return map(image);
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
