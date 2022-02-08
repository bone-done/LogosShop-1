package com.vansisto.logosshop.service;

import com.vansisto.logosshop.domain.UserOrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface OrderService extends BaseService<UserOrderDTO> {
    Page<UserOrderDTO> getAllByUserId(Long userId, PageRequest pageRequest);
}
