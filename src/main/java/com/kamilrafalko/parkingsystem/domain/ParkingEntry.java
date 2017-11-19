package com.kamilrafalko.parkingsystem.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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

    @Setter
    private LocalDateTime endDate;

    ParkingEntry(String licenseNumber, LocalDateTime startDate) {
        this.licenseNumber = licenseNumber;
        this.startDate = startDate;
    }

}
