package com.vansisto.logosshop.domain;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Data
public class UserCountDTO {
    private Long id;
    @NotNull
    @PositiveOrZero
    private BigDecimal amount;
}
