package com.vansisto.logosshop.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Data
@ToString(exclude = "user")
@EqualsAndHashCode(exclude = "user")

@Entity
//TODO: Why it didn't work? Table cannot be named as "Order", because it's reserved word in mysql
public class UserOrder extends BaseEntity {
    @NotNull
    private OrderState state = OrderState.OPENED;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
