package com.cashier.springboot.service;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.cashier.springboot.models.Cheque;
import com.cashier.springboot.models.Shift;
import com.cashier.springboot.models.User;
import com.cashier.springboot.repository.ChequeRepository;
import com.cashier.springboot.repository.ShiftRepository;
import com.cashier.springboot.repository.UnitRepository;

@Service
public class ChequeServiceImpl implements ChequeService{
	
	@Autowired
	ChequeRepository chequeRepository;
	@Autowired
	UnitRepository unitRepository;
	@Autowired
	ShiftRepository shiftRepository;
	
	@Override
	public Page<Cheque> getAllCheques(int page, int size) {
		page--;
		Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
		return chequeRepository.findAll(pageable);
	}
	
	@Override
	public Cheque newCheque() {
		Shift openShift = shiftRepository.getOpenShift().get();
		if (openShift == null)
			return null;
		Cheque cheque = new Cheque();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)authentication.getPrincipal();
		cheque.setCreatedBy(user);
		cheque.setShiftIdOpened(openShift);
		chequeRepository.save(cheque);
		return cheque;
	}
	
	@Override
	public Cheque editCheque(int id) {
		return chequeRepository.getById(id);
	}
	
	@Override
	public boolean deleteCheque(int id) {
		Cheque cheque = chequeRepository.getById(id);
		if (cheque.getDateCreated() != null) {
			return false;
		}
		chequeRepository.delete(cheque);
		return true;
	}
	
	@Override
	public boolean closeCheque(int id) {
		Cheque cheque = chequeRepository.getById(id);
		if (cheque == null | cheque.getProducts() == null)
			return false;
		cheque.setDateCreated(Instant.now());
		chequeRepository.save(cheque);
		return true;
	}
	
	@Override
	public boolean cancelCheque(int id) {
		Cheque cheque = chequeRepository.getById(id);
		if (cheque == null)
			return false;
		cheque.setCancelledDate(Instant.now());
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)authentication.getPrincipal();
		cheque.setCancelledBy(user);
		chequeRepository.save(cheque);
		return true;
	}
}
