package com.vansisto.logosshop.controller;

import com.vansisto.logosshop.dto.TestDTO;
import com.vansisto.logosshop.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/test")
public class TestController {
    @Autowired
    private TestService service;

    @GetMapping("/{id}")
    public ResponseEntity<TestDTO> getById(@PathVariable Long id){
        return ResponseEntity.ok(service.getEntity(id));
    }

    @GetMapping
    public ResponseEntity<List<TestDTO>> getAll(){
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/byName")
    public ResponseEntity<TestDTO> getAll(@RequestParam(name = "name") String name){
        return ResponseEntity.ok(service.findByName(name));
    }

    @GetMapping("/findallcustom")
    public ResponseEntity<List<TestDTO>> getAllCustom(){
        return ResponseEntity.ok(service.findAllCustom());
    }

    @GetMapping("/findallcustomnative")
    public ResponseEntity<List<TestDTO>> getAllCustomNative(){
        return ResponseEntity.ok(service.findAllCustomNative());
    }
}
