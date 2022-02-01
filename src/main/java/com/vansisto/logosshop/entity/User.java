package com.vansisto.logosshop.entity;

import lombok.Data;

import javax.persistence.Entity;

@Data

@Entity
public class User extends BaseEntity {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
}
