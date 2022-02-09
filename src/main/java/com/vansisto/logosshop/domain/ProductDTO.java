package com.vansisto.logosshop.domain;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Data
public class ProductDTO {
    private Long id;
    @NotNull
    @NotBlank
    @Length(max = 100)
    private String title;

    private String description;

    @NotNull
    @PositiveOrZero
    private Double price;
}
