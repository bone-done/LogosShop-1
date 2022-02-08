package com.vansisto.logosshop.domain;

import com.vansisto.logosshop.entity.OrderState;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserOrderDTO {
    private Long id;
    @NotNull
    private OrderState orderState;
}

