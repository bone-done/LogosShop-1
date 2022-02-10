package com.vansisto.logosshop.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {
    public NotFoundException(String itemType, String identificator, String value) {
        super(String.format("%s with %s - %s not found", itemType, identificator, value));
        log.error("{} not found", itemType, this);
    }
    public NotFoundException(String itemType, String identificator, Number value) {
        super(String.format("%s with %s - %s not found", itemType, identificator, String.valueOf(value)));
        log.error("{} not found", itemType, this);
    }
}
