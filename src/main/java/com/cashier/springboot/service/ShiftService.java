package com.cashier.springboot.service;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;

import com.cashier.springboot.models.Shift;

public interface ShiftService {

	public Page<Shift> getAllShifts(int page, int size);
	
	@Transactional
	public boolean openShift();
	
	@Transactional
	public boolean closeShift(int shiftId);
}
