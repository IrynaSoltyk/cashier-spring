package com.cashier.springboot.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cashier.springboot.models.Product;
import com.cashier.springboot.models.Shift;

@Repository
@Transactional
public class ShiftDao {
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	/*public List<Shift> list() {
    	String sql = "SELECT * FROM SHIFTS";
        List<Shift> products = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Shift.class));
        return products;
    }
  
    public void save(Shift shift) {
    }
  
    public Product get(int id) {
        return null;
    }
  
    public void update(Shift shift) {
    }
  
    public void delete(int id) {
    }*/
}
