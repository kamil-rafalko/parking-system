package com.kamilrafalko.parkingsystem.rest;

import com.kamilrafalko.parkingsystem.domain.ParkingStateService;
import com.kamilrafalko.parkingsystem.domain.dto.CarState;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
class ParkingStateController {

    private final ParkingStateService parkingStateService;

    ParkingStateController(ParkingStateService parkingStateService) {
        this.parkingStateService = parkingStateService;
    }

    @GetMapping("state/car")
    public CarState getCarState(@RequestParam("licenseNumber") String licenseNumber) {
        return parkingStateService.getCarState(licenseNumber);
    }

}
