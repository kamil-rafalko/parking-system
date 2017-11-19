package com.kamilrafalko.parkingsystem.domain;

import com.kamilrafalko.parkingsystem.domain.dto.Car;
import com.kamilrafalko.parkingsystem.domain.dto.StartResponse;
import com.kamilrafalko.parkingsystem.domain.dto.StopResponse;
import com.kamilrafalko.parkingsystem.domain.exceptions.IllegalParkingMeterOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ParkingMeter {

    private final ParkingEntryRepository parkingEntryRepository;

    @Autowired
    public ParkingMeter(ParkingEntryRepository parkingEntryRepository) {
        this.parkingEntryRepository = parkingEntryRepository;
    }

    public StartResponse start(Car car) {

        String licenseNumber = car.getLicenseNumber();

        parkingEntryRepository.findByLicenseNumberAndEndDateIsNull(licenseNumber)
                .ifPresent(entry -> {
            throw new IllegalParkingMeterOperation("Can not start parking meter - car already is on parking");
        });

        ParkingEntry entry = new ParkingEntry(licenseNumber, LocalDateTime.now());
        parkingEntryRepository.save(entry);

        return new StartResponse(licenseNumber, entry.getStartDate());
    }

    public StopResponse stop(Car car) {

        String licenseNumber = car.getLicenseNumber();

        ParkingEntry entry = parkingEntryRepository.findByLicenseNumberAndEndDateIsNull(licenseNumber)
                .orElseThrow(() ->
                        new IllegalParkingMeterOperation("Can not stop parking meter - parking entry not found"));

        entry.setEndDate(LocalDateTime.now());
        parkingEntryRepository.save(entry);


        return new StopResponse(licenseNumber, entry.getStartDate(), entry.getEndDate());
    }
}
