package com.cashier.springboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cashier.springboot.models.Cheque;

public interface ChequeRepository extends JpaRepository<Cheque, Integer> {
	@Query("SELECT c FROM cheques c WHERE shift_id_cancelled = :shiftId OR shift_id_opened = :shiftId")
	List<Cheque> report(@Param("shiftId")int shiftId);
}
