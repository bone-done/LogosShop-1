package com.vansisto.logosshop.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface BaseService<T> {
    T create(T t);
    T update(T t);
    T delete(T t);
    Long deleteById(Long id);
    T getEntity(Long id);
    Page<T> getAll(PageRequest pageRequest);
}
