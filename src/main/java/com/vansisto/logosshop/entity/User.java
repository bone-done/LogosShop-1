package com.vansisto.logosshop.entity;

import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

@Data

@Entity
public class User extends BaseEntity {
//    @NotNull(message = "Email can not be null")
//    @Email
    private String email;
//    @Length(max = 2, message = "Password length should be greater than 6")
    private String password;
    private String firstName;
    private String lastName;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {
            CascadeType.MERGE,
            CascadeType.DETACH,
            CascadeType.PERSIST,
            CascadeType.REFRESH
    })
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "roled")
    )
    private Set<Role> roles = new HashSet<>();
}
