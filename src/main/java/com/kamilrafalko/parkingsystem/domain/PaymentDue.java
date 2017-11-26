package com.kamilrafalko.parkingsystem.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
class PaymentDue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private BigDecimal amount;
    private LocalDate dateCreated;

    PaymentDue(BigDecimal amount, LocalDate dateCreated) {
        this.amount = amount;
        this.dateCreated = dateCreated;
    }
}
