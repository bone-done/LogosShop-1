package com.vansisto.logosshop.service.impl;

import com.vansisto.logosshop.domain.UserCountDTO;
import com.vansisto.logosshop.entity.UserCount;
import com.vansisto.logosshop.exception.AlreadyExistsException;
import com.vansisto.logosshop.exception.NotFoundException;
import com.vansisto.logosshop.repository.UserCountRepository;
import com.vansisto.logosshop.service.UserCountService;
import com.vansisto.logosshop.util.ModelMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class UserCountServiceImpl implements UserCountService {

    @Autowired
    private UserCountRepository repository;
    @Autowired
    private ModelMapperUtil mapper;
    private static final String ENTITY_NAME = "User count";

    @Override
    @Transactional
    public UserCountDTO create(UserCountDTO dto) {
        if (!Objects.isNull(dto.getId()) && repository.existsById(dto.getId()))
            throw new AlreadyExistsException(ENTITY_NAME, "id", dto.getId());
        return map(repository.save(map(dto)));
    }

    @Override
    @Transactional
    public UserCountDTO update(UserCountDTO dto) {
        if (Objects.isNull(dto.getId()) && !repository.existsById(dto.getId()))
            throw new NotFoundException(ENTITY_NAME, "id", dto.getId());
        return map(repository.save(map(dto)));
    }

    @Override
    @Transactional
    public UserCountDTO delete(UserCountDTO dto) {
        repository.delete(map(dto));
        return dto;
    }

    @Override
    @Transactional
    public Long deleteById(Long id) {
        if (!repository.existsById(id)) throw new NotFoundException(ENTITY_NAME, "id", id);
        repository.deleteById(id);
        return id;
    }

    @Override
    public UserCountDTO getEntity(Long id) {
        return map(repository.findById(id).orElseThrow(() -> new NotFoundException(ENTITY_NAME, "id", id)));
    }

    @Override
    public Page<UserCountDTO> getAll(PageRequest pageRequest) {
        return repository.findAll(pageRequest).map(this::map);
    }

    @Override
    public UserCountDTO getByUserId(Long userId) {
        return map(repository.findByUserId(userId).orElseThrow(() -> new NotFoundException(ENTITY_NAME, "user id", userId)));
    }

    private UserCountDTO map(UserCount entity) {
        return mapper.map(entity, UserCountDTO.class);
    }

    private UserCount map(UserCountDTO dto) {
        return mapper.map(dto, UserCount.class);
    }

}
