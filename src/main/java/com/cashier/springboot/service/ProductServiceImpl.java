package com.cashier.springboot.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cashier.springboot.models.Product;
import com.cashier.springboot.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService{

	@Autowired
	private ProductRepository productRepository;
	
	Logger logger = LogManager.getLogger(ProductServiceImpl.class);
	
	@Override
	public boolean addProduct(Product product) {
		productRepository.saveAndFlush(product);
		try {
			productRepository.saveAndFlush(product);
		} catch (DataIntegrityViolationException e) {
			logger.info(e);
			return false;
		}
		return true;
	}
	
	@Override
	public Product getProduct(int id) {
		return productRepository.getById(id);
	}
	
	@Override
	public Page<Product> getAllProducts(int page, int size) {
		page--;
		Pageable pageable = PageRequest.of(page, size);
		return productRepository.findAll(pageable);
	}
	
	@Override
	public void deleteProduct(int id) {
		Product product = new Product();
		product.setId(id);
		productRepository.delete(product);
	}
	
	@Override
	public Page<Product> searchProducts(int page, int size, String searchValue) {
		page--;
		Pageable pageable = PageRequest.of(page, size);
		return productRepository.findProductsByNameOrIdLike(searchValue, pageable);
	}
	
}
