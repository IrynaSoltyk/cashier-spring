package com.cashier.springboot.service;

import java.time.Instant;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.cashier.springboot.models.Shift;
import com.cashier.springboot.models.User;
import com.cashier.springboot.repository.ShiftRepository;

@Service
public class ShiftService {
	
	@Autowired
	ShiftRepository shiftRepository;
	
	public Page<Shift> getAllShifts(int page, int size) {
		page--;
		Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
		return shiftRepository.findAll(pageable);
	}
	
	@Transactional
	public boolean openShift(){
		Optional<Shift> openShift = shiftRepository.getOpenShift();
		
		if(openShift.isPresent()) 
			return false;
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)authentication.getPrincipal();
		Shift shift = new Shift();
		shift.setBeginDate(Instant.now());
		shift.setUser(user);
		
		shiftRepository.save(shift);
		
		return true;
	}
	
	@Transactional
	public boolean closeShift(int shiftId) {
		Shift shift = shiftRepository.getById(shiftId);
		
		if(shift == null) {
			return false;
		} 
		
		shift.setEndDate(Instant.now());
		shiftRepository.save(shift);
		
		return true;
	}
}
