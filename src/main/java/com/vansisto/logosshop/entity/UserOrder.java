package com.vansisto.logosshop.entity;

import com.vansisto.logosshop.entity.enums.OrderState;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Data
@ToString(exclude = {"user", "history", "products"})
@EqualsAndHashCode(exclude = {"user", "history", "products"})

@Entity
//TODO: Why it didn't work? Table cannot be named as "Order", because it's reserved word in mysql
public class UserOrder extends BaseEntity {
    @NotNull
    private OrderState state = OrderState.OPENED;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false)
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private History history;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {
            CascadeType.MERGE,
            CascadeType.DETACH,
            CascadeType.PERSIST,
            CascadeType.REFRESH
    })
    @JoinTable
    private Set<Product> products = new HashSet<>();
}
