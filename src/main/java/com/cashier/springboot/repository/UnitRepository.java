package com.cashier.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cashier.springboot.models.Unit;

public interface UnitRepository extends JpaRepository<Unit, Integer> {

}
