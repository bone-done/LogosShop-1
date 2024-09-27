package com.vansisto.logosshop.service;

import com.vansisto.logosshop.domain.HistoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface HistoryService extends BaseService<HistoryDTO> {
    Page<HistoryDTO> getAllByUserId(Long userId, PageRequest pageRequest);
}
