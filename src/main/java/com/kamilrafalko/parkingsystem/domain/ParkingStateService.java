package com.kamilrafalko.parkingsystem.domain;

import com.kamilrafalko.parkingsystem.domain.dto.CarState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParkingStateService {

    private final ParkingEntryRepository parkingEntryRepository;

    @Autowired
    public ParkingStateService(ParkingEntryRepository parkingEntryRepository) {
        this.parkingEntryRepository = parkingEntryRepository;
    }

    public CarState getCarState(String licenseNumber) {
        boolean onParking = parkingEntryRepository.findByLicenseNumberAndEndDateIsNull(licenseNumber)
                .isPresent();
        return new CarState(onParking);
    }

}
