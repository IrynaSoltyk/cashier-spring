package com.cashier.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cashier.springboot.models.ChequeProduct;

public interface ChequeProductRepository  extends JpaRepository<ChequeProduct, Integer>{

}
