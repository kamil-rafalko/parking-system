package com.kamilrafalko.parkingsystem.domain;

import com.kamilrafalko.parkingsystem.domain.dto.IncomeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class ParkingFinancesService {

    private final PaymentDueRepository repository;

    @Autowired
    public ParkingFinancesService(PaymentDueRepository repository) {
        this.repository = repository;
    }

    public IncomeDto countIncomeFor(LocalDate date) {
        BigDecimal income = repository.findPaymentDuesByDateCreated(date)
                .stream()
                .map(PaymentDue::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new IncomeDto(income);
    }

}
