package com.cashier.springboot.service;

import javax.transaction.Transactional;

public interface ChequeProductService {
	@Transactional
	public boolean addProduct(int chequeId, int productId, int amount);
	
	@Transactional
	public boolean editProduct(int cpId, int amount);
	
	@Transactional
	public void deleteProduct(int cpId);
	
	@Transactional
	public void cancelProduct(int cpId);
	
}
