package com.vansisto.logosshop.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@ToString(exclude = {"order"})
@EqualsAndHashCode(exclude = {"order"})

@Entity
public class History extends BaseEntity {
    @Column(nullable = false)
    private LocalDateTime dateTime;

    @OneToMany(mappedBy = "history", fetch = FetchType.LAZY)
    private Set<UserOrder> order;
}
