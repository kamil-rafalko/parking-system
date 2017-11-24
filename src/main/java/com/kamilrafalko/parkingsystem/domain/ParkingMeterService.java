package com.kamilrafalko.parkingsystem.domain;

import com.kamilrafalko.parkingsystem.domain.dto.StartRequest;
import com.kamilrafalko.parkingsystem.domain.dto.StartResponse;
import com.kamilrafalko.parkingsystem.domain.dto.StopRequest;
import com.kamilrafalko.parkingsystem.domain.dto.StopResponse;
import com.kamilrafalko.parkingsystem.domain.exceptions.IllegalParkingMeterOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@Transactional
public class ParkingMeterService {

    private final ParkingEntryRepository parkingEntryRepository;
    private final PaymentDueRepository paymentDueRepository;
    private final PricingConfigResolver pricingConfigResolver;

    @Autowired
    public ParkingMeterService(ParkingEntryRepository parkingEntryRepository,
                               PaymentDueRepository paymentDueRepository,
                               PricingConfigResolver pricingConfigResolver) {
        this.parkingEntryRepository = parkingEntryRepository;
        this.paymentDueRepository = paymentDueRepository;
        this.pricingConfigResolver = pricingConfigResolver;
    }

    public StartResponse start(StartRequest startRequest) {

        String licenseNumber = startRequest.getLicenseNumber();

        parkingEntryRepository.findByLicenseNumberAndEndDateIsNull(licenseNumber)
                .ifPresent(entry -> {
            throw new IllegalParkingMeterOperation("Can not start parking meter - startRequest already is on parking");
        });

        ParkingEntry entry = new ParkingEntry(licenseNumber, LocalDateTime.now(), startRequest.getVipCode());
        parkingEntryRepository.save(entry);

        return new StartResponse(licenseNumber, entry.getStartDate());
    }

    public StopResponse stop(StopRequest stopRequest) {

        String licenseNumber = stopRequest.getLicenseNumber();

        ParkingEntry entry = parkingEntryRepository.findByLicenseNumberAndEndDateIsNull(licenseNumber)
                .orElseThrow(() ->
                        new IllegalParkingMeterOperation("Can not stop parking meter - parking entry not found"));

        entry.setEndDate(LocalDateTime.now());

        PaymentDue paymentDue = calculatePayment(entry);
        paymentDueRepository.save(paymentDue);

        entry.setPaymentDue(paymentDue);

        return new StopResponse(licenseNumber, entry.getStartDate(), entry.getEndDate(), paymentDue.getAmount());
    }

    private PaymentDue calculatePayment(ParkingEntry entry) {
        final PricingConfig pricingConfig = pricingConfigResolver.resolveConfig(entry.getVipCode());

        final PaymentCalculator paymentCalculator = new PaymentCalculator(pricingConfig);
        final BigDecimal paymentValue = paymentCalculator.calculate(entry.getStartDate(), entry.getEndDate());

        return new PaymentDue(paymentValue, LocalDate.now());
    }
}
