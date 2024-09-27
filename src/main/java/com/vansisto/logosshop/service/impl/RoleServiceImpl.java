package com.vansisto.logosshop.service.impl;

import com.vansisto.logosshop.domain.RoleDTO;
import com.vansisto.logosshop.entity.Role;
import com.vansisto.logosshop.exception.AlreadyExistsException;
import com.vansisto.logosshop.exception.NotFoundException;
import com.vansisto.logosshop.repository.RoleRepository;
import com.vansisto.logosshop.service.RoleService;
import com.vansisto.logosshop.util.ModelMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository repository;
    @Autowired
    private ModelMapperUtil mapper;
    
    private static final String ENTITY_NAME = "Role";

    @Override
    @Transactional
    public RoleDTO create(RoleDTO dto) {
        if (!Objects.isNull(dto.getId()) && repository.existsById(dto.getId()))
            throw new AlreadyExistsException(ENTITY_NAME, "id", dto.getId());
        return map(repository.save(map(dto)));
    }

    @Override
    @Transactional
    public RoleDTO update(RoleDTO dto) {
        if (Objects.isNull(dto.getId()) && !repository.existsById(dto.getId()))
            throw new NotFoundException(ENTITY_NAME, "id", dto.getId());
        return map(repository.save(map(dto)));
    }

    @Override
    @Transactional
    public RoleDTO delete(RoleDTO dto) {
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
    public RoleDTO getEntity(Long id) {
        return map(repository.findById(id).orElseThrow(() -> new NotFoundException(ENTITY_NAME, "id", id)));
    }

    @Override
    public Page<RoleDTO> getAll(PageRequest pageRequest) {
        return repository.findAll(pageRequest).map(this::map);
    }

    private RoleDTO map(Role entity) {
        return mapper.map(entity, RoleDTO.class);
    }

    private Role map(RoleDTO dto) {
        return mapper.map(dto, Role.class);
    }

}
