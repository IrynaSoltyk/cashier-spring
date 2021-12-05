package com.cashier.springboot.service;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import com.cashier.springboot.repository.UserRepository;


@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private WebApplicationContext applicationContext;
    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    public void completeSetup() {
        userRepository = applicationContext.getBean(UserRepository.class);
    }

    @Override
    public UserDetails loadUserByUsername(final String login) {
        return userRepository.findByLogin(login).orElseThrow(() -> new UsernameNotFoundException(login));
    }
}