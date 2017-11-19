package com.kamilrafalko.parkingsystem.rest;

import com.kamilrafalko.parkingsystem.domain.ParkingMeter;
import com.kamilrafalko.parkingsystem.domain.dto.Car;
import com.kamilrafalko.parkingsystem.domain.dto.StartResponse;
import com.kamilrafalko.parkingsystem.domain.dto.StopResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ParkingMeterController {

    private final ParkingMeter parkingMeter;

    @Autowired
    public ParkingMeterController(ParkingMeter parkingMeter) {
        this.parkingMeter = parkingMeter;
    }

    @PostMapping("meter/start")
    public ResponseEntity<StartResponse> start(@RequestBody Car car) {
        StartResponse response = parkingMeter.start(car);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @PostMapping("meter/stop")
    public ResponseEntity<StopResponse> stop(@RequestBody Car car) {
        StopResponse response =  parkingMeter.stop(car);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
