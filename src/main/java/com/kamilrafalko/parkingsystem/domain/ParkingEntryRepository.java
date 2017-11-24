package com.kamilrafalko.parkingsystem.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

interface ParkingEntryRepository extends CrudRepository<ParkingEntry, Long> {

    Optional<ParkingEntry> findByLicenseNumberAndEndDateIsNull(String licenseNumber);

}
