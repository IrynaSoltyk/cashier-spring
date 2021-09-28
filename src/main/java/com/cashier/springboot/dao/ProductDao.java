package com.cashier.springboot.dao;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cashier.springboot.models.Product;

@Repository
@Transactional
public class ProductDao {
	@Autowired
    private JdbcTemplate jdbcTemplate;
  
    public List<Product> list() {
    	String sql = "SELECT * FROM PRODUCTS";
        List<Product> products = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Product.class));
        return products;
    }
  
    public void save(Product product) {
    }
  
    public Product get(int id) {
        return null;
    }
  
    public void update(Product product) {
    }
  
    public void delete(int id) {
    }
    

}

