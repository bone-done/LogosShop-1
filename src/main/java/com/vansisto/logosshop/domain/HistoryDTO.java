package com.vansisto.logosshop.domain;

import lombok.Data;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class HistoryDTO {
    private Long id;
    @NotNull
    @FutureOrPresent
    private LocalDateTime dateTime;
}
