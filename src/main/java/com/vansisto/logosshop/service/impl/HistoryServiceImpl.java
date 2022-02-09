package com.vansisto.logosshop.service.impl;

import com.vansisto.logosshop.domain.HistoryDTO;
import com.vansisto.logosshop.entity.History;
import com.vansisto.logosshop.exception.AlreadyExistsException;
import com.vansisto.logosshop.exception.NotFoundException;
import com.vansisto.logosshop.repository.HistoryRepository;
import com.vansisto.logosshop.service.HistoryService;
import com.vansisto.logosshop.util.ModelMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class HistoryServiceImpl implements HistoryService {
    @Autowired
    private HistoryRepository repository;
    @Autowired
    private ModelMapperUtil mapper;
    private final String ENTITY_NAME = "History record";

    @Override
    public HistoryDTO create(HistoryDTO dto) {
        if (!Objects.isNull(dto.getId()) && repository.existsById(dto.getId()))
            throw new AlreadyExistsException(ENTITY_NAME, "id", dto.getId());
        return map(repository.save(map(dto)));
    }

    @Override
    public HistoryDTO update(HistoryDTO dto) {
        if (Objects.isNull(dto.getId()) && !repository.existsById(dto.getId()))
            throw new NotFoundException(ENTITY_NAME, "id", dto.getId());
        return map(repository.save(map(dto)));
    }

    @Override
    public HistoryDTO delete(HistoryDTO dto) {
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
    public HistoryDTO getEntity(Long id) {
        return map(repository.findById(id).orElseThrow(() -> new NotFoundException(ENTITY_NAME, "id", id)));
    }

    @Override
    public Page<HistoryDTO> getAll(PageRequest pageRequest) {
        return repository.findAll(pageRequest).map(entity -> map(entity));
    }

    private HistoryDTO map(History entity) {
        return mapper.map(entity, HistoryDTO.class);
    }

    private History map(HistoryDTO dto) {
        return mapper.map(dto, History.class);
    }
}
