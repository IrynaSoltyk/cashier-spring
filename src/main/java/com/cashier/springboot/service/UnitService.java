package com.cashier.springboot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cashier.springboot.models.Unit;
import com.cashier.springboot.repository.UnitRepository;

@Service
public class UnitService {
	
	@Autowired
	UnitRepository unitRepository;

	public List<Unit> findAll() {
		return unitRepository.findAll();
	}
}
