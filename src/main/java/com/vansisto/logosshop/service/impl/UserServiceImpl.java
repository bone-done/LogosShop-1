package com.vansisto.logosshop.service.impl;

import com.vansisto.logosshop.domain.UserDTO;
import com.vansisto.logosshop.entity.Role;
import com.vansisto.logosshop.entity.User;
import com.vansisto.logosshop.entity.UserCount;
import com.vansisto.logosshop.exception.AlreadyExistsException;
import com.vansisto.logosshop.exception.NotFoundException;
import com.vansisto.logosshop.repository.RoleRepository;
import com.vansisto.logosshop.repository.UserCountRepository;
import com.vansisto.logosshop.repository.UserRepository;
import com.vansisto.logosshop.service.UserService;
import com.vansisto.logosshop.util.ModelMapperUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service("applicationUserService")
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository repository;
    private final ModelMapperUtil mapper;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserCountRepository userCountRepository;

    private final String ENTITY_NAME = "User entity";

    @Autowired
    public UserServiceImpl(
            UserRepository repository,
            ModelMapperUtil mapper,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder,
            UserCountRepository userCountRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userCountRepository = userCountRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repository.findByEmail(email).orElseThrow(() -> new NotFoundException(ENTITY_NAME, "email", email));
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRoles().stream().map(r -> r.getName()).toArray(String[]::new))
            .build();
    }

    @Override
    @Transactional
    public UserDTO create(UserDTO dto) {
        if (!Objects.isNull(dto.getId()) && repository.existsById(dto.getId()))
            throw new AlreadyExistsException(ENTITY_NAME, "id", dto.getId());
        User userToSave = map(dto);
        Role userRole = roleRepository.findByName("USER").orElseThrow(() -> new NotFoundException("Role", "name", "USER"));
        userToSave.getRoles().add(userRole);
        userToSave.setPassword(passwordEncoder.encode(userToSave.getPassword()));
        userToSave.setUserCount(new UserCount());
        User createdUser = repository.save(userToSave);

        UserDTO createdUserDTO = map(createdUser);
        createdUserDTO.setPassword(null);
        return createdUserDTO;
    }

    @Override
    @Transactional
    public UserDTO update(UserDTO dto) {
        if (Objects.isNull(dto.getId()) && !repository.existsById(dto.getId()))
            throw new NotFoundException(ENTITY_NAME, "id", dto.getId());
        UserDTO updatedUser = map(repository.save(map(dto)));
        updatedUser.setPassword(null);
        return updatedUser;
    }

    @Override
    @Transactional
    public UserDTO delete(UserDTO dto) {
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
    public UserDTO getEntity(Long id) {
        User entity = repository.findById(id).orElseThrow(() -> new NotFoundException(ENTITY_NAME, "id", id));
        UserDTO returnedUser = map(entity);
        returnedUser.setPassword(null);
        return returnedUser;
    }

    @Override
    public Page<UserDTO> getAll(PageRequest pageRequest) {
        return repository.findAll(pageRequest).map(user -> {
                    UserDTO mappedUser = map(user);
//                    mappedUser.setPassword(null);
                    return mappedUser;
                });
    }

    @Override
    @Transactional
    public UserDTO attachRoleToUser(String role, User user) {
        Role roleTmp = roleRepository.findByName(role).orElseThrow(() -> new NotFoundException("Role", "name", role));
        user.getRoles().add(roleTmp);
        UserDTO savedUser = map(repository.save(user));
        savedUser.setPassword(null);
        return savedUser;
    }

    @Override
    @Transactional
    public void attachRoleToUserById(String role, Long userId) {
        User user = repository.findById(userId).orElseThrow(() -> new NotFoundException(ENTITY_NAME, "id", userId));
        attachRoleToUser(role, user);
    }

    @Override
    public UserDTO getByEmail(String email) {
        UserDTO userDTO = map(repository.findByEmail(email).orElseThrow(() -> new NotFoundException(ENTITY_NAME, "email", email)));
        userDTO.setPassword(null);
        return userDTO;
    }

    @Override
    public boolean hasRole(Long userId, String roleName) {
        User user = repository.findById(userId).orElseThrow(() -> new NotFoundException(ENTITY_NAME, "id", userId));
        Set<Role> roles = user.getRoles();
        roles = roles.stream().filter(role -> role.getName().equalsIgnoreCase(roleName)).collect(Collectors.toSet());
        return !roles.isEmpty();
    }


    private UserDTO map(User entity) {
        return mapper.map(entity, UserDTO.class);
    }

    private User map(UserDTO dto) {
        return mapper.map(dto, User.class);
    }

}
