package com.vansisto.logosshop.service.impl;

import com.vansisto.logosshop.domain.HistoryDTO;
import com.vansisto.logosshop.entity.History;
import com.vansisto.logosshop.entity.UserOrder;
import com.vansisto.logosshop.exception.AlreadyExistsException;
import com.vansisto.logosshop.exception.NotFoundException;
import com.vansisto.logosshop.repository.HistoryRepository;
import com.vansisto.logosshop.repository.OrderRepository;
import com.vansisto.logosshop.service.HistoryService;
import com.vansisto.logosshop.util.ModelMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class HistoryServiceImpl implements HistoryService {
    @Autowired
    private HistoryRepository repository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ModelMapperUtil mapper;
    private static final String ENTITY_NAME = "History record";

    @Override
    @Transactional
    public HistoryDTO create(HistoryDTO dto) {
        if (!Objects.isNull(dto.getId()) && repository.existsById(dto.getId()))
            throw new AlreadyExistsException(ENTITY_NAME, "id", dto.getId());
        return map(repository.save(map(dto)));
    }

    @Override
    @Transactional
    public HistoryDTO update(HistoryDTO dto) {
        if (Objects.isNull(dto.getId()) && !repository.existsById(dto.getId()))
            throw new NotFoundException(ENTITY_NAME, "id", dto.getId());
        return map(repository.save(map(dto)));
    }

    @Override
    @Transactional
    public HistoryDTO delete(HistoryDTO dto) {
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
    public HistoryDTO getEntity(Long id) {
        return map(repository.findById(id).orElseThrow(() -> new NotFoundException(ENTITY_NAME, "id", id)));
    }

    @Override
    public Page<HistoryDTO> getAll(PageRequest pageRequest) {
        return repository.findAll(pageRequest).map(this::map);
    }

    @Override
    public Page<HistoryDTO> getAllByUserId(Long userId, PageRequest pageRequest) {
        List<UserOrder> userOrders= orderRepository.findByUserId(userId, pageRequest).getContent();
        List<HistoryDTO> historyList = userOrders.stream()
                .map(uo -> map(uo.getHistory()))
                .collect(Collectors.toList());
        return new PageImpl<>(historyList);
    }

    private HistoryDTO map(History entity) {
        return mapper.map(entity, HistoryDTO.class);
    }

    private History map(HistoryDTO dto) {
        return mapper.map(dto, History.class);
    }
}
