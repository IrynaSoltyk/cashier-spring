package com.cashier.springboot.service;

import org.springframework.data.domain.Page;

import com.cashier.springboot.models.Cheque;

public interface ChequeService {

	public Page<Cheque> getAllCheques(int page, int size);
	
	public Cheque newCheque();
	
	public Cheque editCheque(int id);
	
	public boolean deleteCheque(int id);
	
	public boolean closeCheque(int id);
	
	public boolean cancelCheque(int id);
}
