package com.vansisto.logosshop.domain;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class RoleDTO {
    private Long id;
    private String name;
    private Set<UserDTO> users = new HashSet<>();
}
