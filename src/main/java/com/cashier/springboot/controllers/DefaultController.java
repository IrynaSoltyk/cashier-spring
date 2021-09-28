package com.cashier.springboot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/")
public class DefaultController {
	
	@GetMapping(path = "/login")
	public String doLogin(){
		return "/login";
	}
	
	@GetMapping(path = "/403.jsp")
	public String notAuth(){
		System.out.println("here");
		return "/403.html";
	}
	
	
	
}
