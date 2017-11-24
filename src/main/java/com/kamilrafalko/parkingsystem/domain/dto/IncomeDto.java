package com.kamilrafalko.parkingsystem.domain.dto;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class IncomeDto {

    private BigDecimal income;

    public IncomeDto(BigDecimal income) {
        this.income = income;
    }
}
