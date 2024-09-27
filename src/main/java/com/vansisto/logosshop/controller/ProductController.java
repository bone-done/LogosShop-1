package com.vansisto.logosshop.controller;

import com.vansisto.logosshop.domain.ProductDTO;
import com.vansisto.logosshop.domain.UserOrderDTO;
import com.vansisto.logosshop.service.OrderService;
import com.vansisto.logosshop.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService service;
    @Autowired
    private OrderService orderService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO product){
        log.info("Creating product: {} ...", product.toString());
        return ResponseEntity.ok(service.create(product));
    }

    @GetMapping
    public ResponseEntity<Page<ProductDTO>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize
    ){
        log.info("Getting all products...");
        return ResponseEntity.ok(service.getAll(PageRequest.of(page, pageSize)));
    }

    @PutMapping("{productId}")
    public ResponseEntity<ProductDTO> attachProductToCurrentOrder(
            @PathVariable Long productId,
            Principal principal){
        boolean isOrderExistsForCurrentUser = orderService.existsForUserByEmail(principal.getName());

        UserOrderDTO orderDTO;
        if (!isOrderExistsForCurrentUser) {
            log.info("Opened order doesn't exist. Creating new order for current user: {}", principal.getName());
            orderDTO = orderService.createForUser(new UserOrderDTO(), principal.getName());
        }
        else orderDTO = orderService.getByUserEmail(principal.getName());

        log.info("Attaching product: {} to order: {}", productId, orderDTO.getId());
        return ResponseEntity.ok(service.attachProductByIdToOrderById(productId, orderDTO.getId()));
    }
}
