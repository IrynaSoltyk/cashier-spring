package com.cashier.springboot.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cashier.springboot.models.Shift;

public interface ShiftRepository extends JpaRepository<Shift, Integer> {
	@Query("SELECT s FROM shifts s WHERE end_date = null")
	Optional<Shift> getOpenShift();
	
	
	
	
}
