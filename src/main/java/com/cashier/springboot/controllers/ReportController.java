package com.cashier.springboot.controllers;

import javax.validation.Valid;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cashier.springboot.models.Product;

@Controller
@RequestMapping(path = "/report")
public class ReportController {
	
	@GetMapping(path = "/")
	public String Report(@RequestParam int shiftId) {
		
		
		
		return "report";
	}

}
