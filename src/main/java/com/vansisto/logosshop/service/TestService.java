package com.vansisto.logosshop.service;

import com.vansisto.logosshop.dto.TestDTO;

import java.util.List;

public interface TestService extends BaseService<TestDTO> {
    TestDTO findByName(String name);
    List<TestDTO> findAllCustom();
    List<TestDTO> findAllCustomNative();
}
