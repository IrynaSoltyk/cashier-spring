package com.cashier.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cashier.springboot.models.User;


public interface UserRepository extends JpaRepository<User, Integer> {
	 User findByLogin(String login);
}
