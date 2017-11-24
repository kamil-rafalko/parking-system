package com.kamilrafalko.parkingsystem.domain;

import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;


interface PaymentDueRepository extends CrudRepository<PaymentDue, Long> {

    List<PaymentDue> findPaymentDuesByDateCreated(LocalDate dateCreated);

}
