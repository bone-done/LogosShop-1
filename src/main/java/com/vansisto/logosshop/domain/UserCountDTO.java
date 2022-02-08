package com.vansisto.logosshop.domain;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
public class UserCountDTO {
    private Long id;
    @NotNull
    @Positive
    private BigDecimal amount;
}
