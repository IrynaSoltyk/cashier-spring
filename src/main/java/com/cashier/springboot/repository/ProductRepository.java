package com.cashier.springboot.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cashier.springboot.models.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
	@Query("SELECT p FROM products p WHERE p.name LIKE %:searchValue% OR CONVERT(p.id, CHAR(20)) LIKE %:searchValue%") 
	Page<Product> findProductsByNameOrIdLike(@Param("searchValue") String searchValue, Pageable pageable);
    	
	@Query("SELECT amount FROM products WHERE id = :productId")
	int getActualAmount(@Param("productId")int productId);
	
	@Query("UPDATE products SET amount =:amount")
	void updateProductAmount(@Param("amount") int amount);
}
