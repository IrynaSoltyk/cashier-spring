package com.cashier.springboot.service;

import org.springframework.data.domain.Page;

import com.cashier.springboot.models.Product;

public interface ProductService {
	
	public boolean addProduct(Product product);
	
	public Page<Product> getAllProducts(int page, int size);
	
	public Product getProduct(int id);
	
	public void deleteProduct(int id);
	
	public Page<Product> searchProducts(int page, int size, String searchValue);
}
