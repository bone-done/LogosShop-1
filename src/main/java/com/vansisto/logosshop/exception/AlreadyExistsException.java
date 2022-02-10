package com.vansisto.logosshop.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ResponseStatus(HttpStatus.CONFLICT)
public class AlreadyExistsException extends RuntimeException {
    public AlreadyExistsException(String itemType, String identificator, String value) {
        super(String.format("%s with %s - %s already exists", itemType, identificator, value));
        log.error("{} already exists", itemType, this);
    }
    public AlreadyExistsException(String itemType, String identificator, Number value) {
        super(String.format("%s with %s - %s already exists", itemType, identificator, String.valueOf(value)));
        log.error("{} already exists", itemType, this);
    }
}
