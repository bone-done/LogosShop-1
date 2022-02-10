package com.vansisto.logosshop.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Data
//TODO: should callSuper. If product are identical, then duplicates don't save in order
@ToString(exclude = {"order", "images"}, callSuper = true)
@EqualsAndHashCode(exclude = {"order", "images"}, callSuper = true)

@Entity
public class Product extends BaseEntity {
    @Column(nullable = false)
    private String title;

    private String description;

    @Column(columnDefinition = "Decimal(7,2)")
    private Double price;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "products")
    private Set<UserOrder> order = new HashSet<>();

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private Set<Image> images = new HashSet<>();
}
