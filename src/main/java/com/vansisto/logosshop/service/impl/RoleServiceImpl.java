package com.vansisto.logosshop.service.impl;

import com.vansisto.logosshop.domain.RoleDTO;
import com.vansisto.logosshop.domain.UserDTO;
import com.vansisto.logosshop.entity.Role;
import com.vansisto.logosshop.entity.User;
import com.vansisto.logosshop.exception.AlreadyExistsException;
import com.vansisto.logosshop.exception.NotFoundException;
import com.vansisto.logosshop.repository.RoleRepository;
import com.vansisto.logosshop.service.RoleService;
import com.vansisto.logosshop.util.ModelMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository repository;

    @Autowired
    private ModelMapperUtil mapper;

    @Override
    public RoleDTO create(RoleDTO dto) {
        if (!Objects.isNull(dto.getId()) && repository.existsById(dto.getId()))
            throw new AlreadyExistsException("Role", "id", dto.getId());
        return map(repository.save(map(dto)));
    }

    @Override
    public RoleDTO update(RoleDTO dto) {
        if (Objects.isNull(dto.getId()) && !repository.existsById(dto.getId()))
            throw new NotFoundException("Role", "id", dto.getId());
        return map(repository.save(map(dto)));
    }

    @Override
    public RoleDTO delete(RoleDTO dto) {
        repository.delete(map(dto));
        return dto;
    }

    @Override
    public Long deleteById(Long id) {
        if (!repository.existsById(id)) throw new NotFoundException("Role", "id", id);
        repository.deleteById(id);
        return id;
    }

    @Override
    public RoleDTO getEntity(Long id) {
        Role entity = repository.findById(id).orElseThrow(() -> new NotFoundException("Role", "id", id));
        return map(entity);
    }

    @Override
    public List<RoleDTO> getAll() {
        return mapper.mapAll(repository.findAll(), RoleDTO.class);
    }

    private RoleDTO map(Role entity) {
        return mapper.map(entity, RoleDTO.class);
    }

    private Role map(RoleDTO dto) {
        return mapper.map(dto, Role.class);
    }

}
