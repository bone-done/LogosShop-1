package com.vansisto.logosshop.service.impl;

import com.vansisto.logosshop.domain.ProductDTO;
import com.vansisto.logosshop.entity.Product;
import com.vansisto.logosshop.exception.AlreadyExistsException;
import com.vansisto.logosshop.exception.NotFoundException;
import com.vansisto.logosshop.repository.ProductRepository;
import com.vansisto.logosshop.service.ProductService;
import com.vansisto.logosshop.util.ModelMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository repository;
    @Autowired
    private ModelMapperUtil mapper;
    private final String ENTITY_NAME = "Product";

    @Override
    public ProductDTO create(ProductDTO dto) {
        if (!Objects.isNull(dto.getId()) && repository.existsById(dto.getId()))
            throw new AlreadyExistsException(ENTITY_NAME, "id", dto.getId());
        return map(repository.save(map(dto)));
    }

    @Override
    public ProductDTO update(ProductDTO dto) {
        if (Objects.isNull(dto.getId()) && !repository.existsById(dto.getId()))
            throw new NotFoundException(ENTITY_NAME, "id", dto.getId());
        return map(repository.save(map(dto)));
    }

    @Override
    public ProductDTO delete(ProductDTO dto) {
        repository.delete(map(dto));
        return dto;
    }

    @Override
    public Long deleteById(Long id) {
        if (!repository.existsById(id)) throw new NotFoundException(ENTITY_NAME, "id", id);
        repository.deleteById(id);
        return id;
    }

    @Override
    public ProductDTO getEntity(Long id) {
        return map(repository.findById(id).orElseThrow(() -> new NotFoundException(ENTITY_NAME, "id", id)));
    }

    @Override
    public Page<ProductDTO> getAll(PageRequest pageRequest) {
        return repository.findAll(pageRequest).map(entity -> map(entity));
    }

    private ProductDTO map(Product entity) {
        return mapper.map(entity, ProductDTO.class);
    }

    private Product map(ProductDTO dto) {
        return mapper.map(dto, Product.class);
    }
}
