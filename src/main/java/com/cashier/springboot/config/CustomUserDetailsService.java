package com.cashier.springboot.config;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import com.cashier.springboot.models.User;
import com.cashier.springboot.repository.UserRepository;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private WebApplicationContext applicationContext;

    private UserRepository userRepository;

    @PostConstruct
    public void completeSetup() {
        userRepository = applicationContext.getBean(UserRepository.class);
    }

    @Override
    public UserDetails loadUserByUsername(final String login) {
        final User user = userRepository.findByLogin(login);
        if (user == null) {
            throw new UsernameNotFoundException(login);
        }
        return user;
    }
}