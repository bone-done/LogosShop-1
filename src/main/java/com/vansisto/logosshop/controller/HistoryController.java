package com.vansisto.logosshop.controller;

import com.vansisto.logosshop.domain.HistoryDTO;
import com.vansisto.logosshop.service.HistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/history")
public class HistoryController {

    @Autowired
    private HistoryService service;

    @GetMapping("allByUserId/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<HistoryDTO>> getAllByUserId(
        @PathVariable Long userId,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int pageSize
    ){
        return ResponseEntity.ok(service.getAllByUserId(userId, PageRequest.of(page, pageSize)));
    }

}
