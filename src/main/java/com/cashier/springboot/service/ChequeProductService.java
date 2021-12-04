package com.cashier.springboot.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cashier.springboot.models.Cheque;
import com.cashier.springboot.models.ChequeProduct;
import com.cashier.springboot.models.Product;
import com.cashier.springboot.repository.ChequeProductRepository;
import com.cashier.springboot.repository.ChequeRepository;
import com.cashier.springboot.repository.ProductRepository;

@Service
public class ChequeProductService {
	@Autowired
	ChequeRepository chequeRepository;
	@Autowired
	ProductRepository productRepository;
	@Autowired
	ChequeProductRepository cpRepository;
	
	@Transactional
	public boolean addProduct(int chequeId, int productId, int amount) {
		Cheque cheque = chequeRepository.getById(chequeId);
		Product product = productRepository.getById(productId);
		List <ChequeProduct> chequeProducts = cheque.getProducts();
		int availableAmount = product.getAmount();
		if (amount > availableAmount || chequeProducts.stream().anyMatch(p -> p.getProduct().getId() == productId))
			return false;
		
		ChequeProduct cp = new ChequeProduct();
		cp.setAmount(amount);
		cp.setPrice(product.getPrice());
		cp.setCheque(cheque);
		cp.setProduct(product);
		cpRepository.save(cp);
		product.setAmount(availableAmount-amount);
		productRepository.save(product);
		return true;
	}
	
	@Transactional
	public boolean editProduct(int cpId, int amount) {
		ChequeProduct cp = cpRepository.getById(cpId);
		// check availability
		int availableAmount = cp.getProduct().getAmount();
		int oldAmount = cp.getAmount();
		if((availableAmount + oldAmount) < amount)
			return false;
		
		cp.setAmount(amount);
		Product product = cp.getProduct();
		product.setAmount(availableAmount + oldAmount - amount);
		productRepository.save(product);
		return true;
	}
	
	@Transactional
	public void deleteProduct(int cpId) {
		ChequeProduct cp = cpRepository.getById(cpId);
		cpRepository.delete(cp);
		Product product = cp.getProduct();
		int oldAmount = product.getAmount();
		product.setAmount(oldAmount + cp.getAmount());
		productRepository.save(product);
	}
	
	@Transactional
	public void cancelProduct(int cpId) {
		ChequeProduct cp = cpRepository.getById(cpId);
		Product product = cp.getProduct();
		int oldAmount = product.getAmount();
		product.setAmount(oldAmount + cp.getAmount());
		cp.setAmount(0);
		cpRepository.save(cp);
		productRepository.save(product);
	}
}
