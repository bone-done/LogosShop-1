package com.vansisto.logosshop.domain;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Data
public class UserDTO {
    private Long id;
    @NotNull(message = "Email cannot be null")
    @Email
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Set<RoleDTO> roles = new HashSet<>();
}
