package com.cashier.springboot.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cashier.springboot.models.User;


public interface UserRepository extends JpaRepository<User, Integer> {
	 Optional<User> findByLogin(String login);
}
