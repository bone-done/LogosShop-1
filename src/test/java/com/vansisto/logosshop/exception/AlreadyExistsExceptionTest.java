package com.vansisto.logosshop.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AlreadyExistsExceptionTest {

    @Test
    void constructorWithString(){
        Exception exception = assertThrows(AlreadyExistsException.class, () -> {
            throw new AlreadyExistsException("User", "email", "test@email.com");
        });
        String expectedMessage = "User with email - test@email.com already exists";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void constructorWithNumber(){
        Exception exception = assertThrows(AlreadyExistsException.class, () -> {
            throw new AlreadyExistsException("User", "id", 1);
        });
        String expectedMessage = "User with id - 1 already exists";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }
}