package com.cashier.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cashier.springboot.models.Cheque;

public interface ChequeRepository extends JpaRepository<Cheque, Integer> {

}
