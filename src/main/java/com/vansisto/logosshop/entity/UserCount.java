package com.vansisto.logosshop.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.math.BigDecimal;

@Data
@ToString(exclude = "user")
@EqualsAndHashCode(exclude = "user")

@Entity
public class UserCount extends BaseEntity {
    @Column(nullable = false, columnDefinition = "Decimal(7,2)")
    private BigDecimal amount = new BigDecimal(0);

    @OneToOne(mappedBy = "userCount")
    private User user;
}
