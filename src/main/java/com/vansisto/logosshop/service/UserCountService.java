package com.vansisto.logosshop.service;

import com.vansisto.logosshop.domain.UserCountDTO;

public interface UserCountService extends BaseService<UserCountDTO> {
    UserCountDTO getByUserId(Long userId);
}
