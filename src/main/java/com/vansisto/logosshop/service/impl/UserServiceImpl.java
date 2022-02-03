package com.vansisto.logosshop.service.impl;

import com.vansisto.logosshop.domain.UserDTO;
import com.vansisto.logosshop.entity.User;
import com.vansisto.logosshop.exception.AlreadyExistsException;
import com.vansisto.logosshop.exception.NotFoundException;
import com.vansisto.logosshop.repository.UserRepository;
import com.vansisto.logosshop.service.UserService;
import com.vansisto.logosshop.util.ModelMapperUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service("applicationUserService")
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private ModelMapperUtil mapper;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        @Data
        @AllArgsConstructor
        class Role {
            private String name;
        }

        Set<Role> roles = new HashSet<>();
        roles.add(new Role("ADMIN"));
//        roles.add(new Role("USER"));


        User user = repository.findByEmail(email).orElseThrow(() -> new NotFoundException("User entity", "email", email));
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
//                .roles("ADMIN")
                .roles(roles.stream().map(r -> r.getName()).toArray(String[]::new))
            .build();
    }

    @Override
    @Transactional
    public UserDTO create(UserDTO dto) {
        if (!Objects.isNull(dto.getId()) && repository.existsById(dto.getId()))
            throw new AlreadyExistsException("User entity", "id", dto.getId());
        return map(repository.save(map(dto)));
    }

    @Override
    public UserDTO update(UserDTO dto) {
        if (Objects.isNull(dto.getId()) && !repository.existsById(dto.getId()))
            throw new NotFoundException("User entity", "id", dto.getId());
        return map(repository.save(map(dto)));
    }

    @Override
    public UserDTO delete(UserDTO dto) {
        repository.delete(map(dto));
        return dto;
    }

    @Override
    public Long deleteById(Long id) {
        if (!repository.existsById(id)) throw new NotFoundException("User entity", "id", id);
        repository.deleteById(id);
        return id;
    }

    @Override
    public UserDTO getEntity(Long id) {
        User entity = repository.findById(id).orElseThrow(() -> new NotFoundException("User entity", "id", id));
        return map(entity);
    }

    @Override
    public List<UserDTO> getAll() {
        return mapper.mapAll(repository.findAll(), UserDTO.class);
    }


    private UserDTO map(User entity) {
        return mapper.map(entity, UserDTO.class);
    }

    private User map(UserDTO dto) {
        return mapper.map(dto, User.class);
    }

}
