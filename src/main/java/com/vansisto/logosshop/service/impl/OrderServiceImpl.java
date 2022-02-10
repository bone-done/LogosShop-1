package com.vansisto.logosshop.service.impl;

import com.vansisto.logosshop.domain.UserOrderDTO;
import com.vansisto.logosshop.entity.User;
import com.vansisto.logosshop.entity.UserOrder;
import com.vansisto.logosshop.entity.enums.OrderState;
import com.vansisto.logosshop.exception.AlreadyExistsException;
import com.vansisto.logosshop.exception.NotFoundException;
import com.vansisto.logosshop.repository.OrderRepository;
import com.vansisto.logosshop.repository.UserRepository;
import com.vansisto.logosshop.service.OrderService;
import com.vansisto.logosshop.util.ModelMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository repository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapperUtil mapper;
    private final String ENTITY_NAME = "Order";

    @Override
    @Transactional
    public UserOrderDTO create(UserOrderDTO dto) {
        if (!Objects.isNull(dto.getId()) && repository.existsById(dto.getId()))
            throw new AlreadyExistsException(ENTITY_NAME, "id", dto.getId());
        return map(repository.save(map(dto)));
    }

    @Override
    @Transactional
    public UserOrderDTO createForUser(UserOrderDTO dto, String userEmail) {
        User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new NotFoundException(
                "User", "email", userEmail
        ));
        UserOrder order = map(dto);
        order.setUser(user);
        return map(repository.save(order));
    }

    @Override
    @Transactional
    public UserOrderDTO update(UserOrderDTO dto) {
        if (Objects.isNull(dto.getId()) && !repository.existsById(dto.getId()))
            throw new NotFoundException(ENTITY_NAME, "id", dto.getId());
        return map(repository.save(map(dto)));
    }

    @Override
    @Transactional
    public UserOrderDTO delete(UserOrderDTO dto) {
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
    public UserOrderDTO getEntity(Long id) {
        return map(repository.findById(id).orElseThrow(() -> new NotFoundException(ENTITY_NAME, "id", id)));
    }

    @Override
    public UserOrderDTO getByUserEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("User", "email", email));
        UserOrder order = repository.findByUserIdAndStateIs(user.getId(), OrderState.OPENED).orElseThrow(() -> new NotFoundException(ENTITY_NAME, "user id", user.getId()));
        return map(order);
    }

    @Override
    public Page<UserOrderDTO> getAll(PageRequest pageRequest) {
        return repository.findAll(pageRequest).map(entity -> map(entity));
    }

    @Override
    public Page<UserOrderDTO> getAllByUserId(Long userId, PageRequest pageRequest) {
        return repository.findByUserId(userId, pageRequest).map(entity -> {
            UserOrderDTO mappedDTO = map(entity);
            mappedDTO.setOrderState(entity.getState());
            return mappedDTO;
        });
    }

    @Override
    public boolean existsForUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User", "email", email));
        UserOrder order = repository
                .findByUserIdAndStateIs(user.getId(), OrderState.OPENED)
                .orElse(null);
        return !Objects.isNull(order);
    }

    private UserOrderDTO map(UserOrder entity) {
        return mapper.map(entity, UserOrderDTO.class);
    }

    private UserOrder map(UserOrderDTO dto) {
        return mapper.map(dto, UserOrder.class);
    }

}
