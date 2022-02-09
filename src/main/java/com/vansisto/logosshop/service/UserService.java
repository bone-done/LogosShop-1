package com.vansisto.logosshop.service;

import com.vansisto.logosshop.domain.UserDTO;
import com.vansisto.logosshop.entity.User;

public interface UserService extends BaseService<UserDTO> {
    UserDTO attachRoleToUser(String role, User user);
    void attachRoleToUserById(String role, Long userId);
    UserDTO getByEmail(String email);
    boolean hasRole(Long userId, String roleName);
}
