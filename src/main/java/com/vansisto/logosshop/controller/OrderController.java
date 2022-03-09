package com.vansisto.logosshop.controller;

import com.vansisto.logosshop.domain.UserOrderDTO;
import com.vansisto.logosshop.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService service;

    @GetMapping("allByUserId/{userId}")
    public ResponseEntity<Page<UserOrderDTO>> getAllByUserId(
            @PathVariable Long userId,
            Principal principal,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize) {

        log.info("Principal: {}", principal.toString());
        return ResponseEntity.ok(service.getAllByUserId(userId, PageRequest.of(page, pageSize)));
    }

    @PutMapping("payAndClose")
    public ResponseEntity<UserOrderDTO> payAndClose(Principal principal){
        log.info("Paying and closing order for user with email: {}", principal.getName());
        return ResponseEntity.ok(service.payAndCloseForUser(principal.getName()));
    }
}
