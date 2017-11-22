package com.kamilrafalko.parkingsystem.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
class ParkingEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String licenseNumber;
    private LocalDateTime startDate;
    private String vipCode;

    @Setter
    private LocalDateTime endDate;

    @Setter
    @OneToOne
    private PaymentDue paymentDue;

    ParkingEntry(String licenseNumber, LocalDateTime startDate, String vipCode) {
        this.licenseNumber = licenseNumber;
        this.startDate = startDate;
        this.vipCode = vipCode;
    }
}
