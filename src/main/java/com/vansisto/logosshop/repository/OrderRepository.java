package com.vansisto.logosshop.repository;

import com.vansisto.logosshop.entity.UserOrder;
import com.vansisto.logosshop.entity.enums.OrderState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<UserOrder, Long> {
    Page<UserOrder> findByUserId(Long userId, Pageable pageRequest);
    Optional<UserOrder> findByUserIdAndStateIs(Long userId, OrderState orderState);
}
