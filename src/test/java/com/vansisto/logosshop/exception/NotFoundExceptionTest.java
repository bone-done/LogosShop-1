package com.vansisto.logosshop.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class NotFoundExceptionTest {

    @Test
    void constructorWithString(){
        Exception exception = assertThrows(NotFoundException.class, () -> {
            throw new NotFoundException("User", "email", "test@email.com");
        });
        String expectedMessage = "User with email - test@email.com not found";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void constructorWithNumber(){
        Exception exception = assertThrows(NotFoundException.class, () -> {
            throw new NotFoundException("User", "id", 1);
        });
        String expectedMessage = "User with id - 1 not found";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

}