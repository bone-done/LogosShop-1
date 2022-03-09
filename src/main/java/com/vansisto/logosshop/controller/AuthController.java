package com.vansisto.logosshop.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/auth")
public class AuthController {

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity auth(Principal principal){
        log.info("User {} was authenticated", principal.getName());
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
