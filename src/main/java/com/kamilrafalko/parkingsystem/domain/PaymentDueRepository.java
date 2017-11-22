package com.kamilrafalko.parkingsystem.domain;

import org.springframework.data.repository.CrudRepository;


interface PaymentDueRepository extends CrudRepository<PaymentDue, Long> {
}
