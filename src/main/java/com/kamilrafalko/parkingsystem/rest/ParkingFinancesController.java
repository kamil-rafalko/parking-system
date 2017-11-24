package com.kamilrafalko.parkingsystem.rest;

import com.kamilrafalko.parkingsystem.domain.ParkingFinancesService;
import com.kamilrafalko.parkingsystem.domain.dto.IncomeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
public class ParkingFinancesController {

    private final ParkingFinancesService parkingFinancesService;

    @Autowired
    ParkingFinancesController(ParkingFinancesService parkingFinancesService) {
        this.parkingFinancesService = parkingFinancesService;
    }

    @GetMapping("income")
    public IncomeDto getIncomeFor(@RequestParam("date")
                                  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                  LocalDate date) {
        return parkingFinancesService.countIncomeFor(date);
    }

}
