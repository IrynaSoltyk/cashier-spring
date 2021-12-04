package com.cashier.springboot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cashier.springboot.repository.ChequeRepository;
import com.cashier.springboot.repository.ProductRepository;
import com.cashier.springboot.service.ChequeProductService;

@Controller
@RequestMapping(path = "/cp")
public class ChequeProductController {

	@Autowired
	ProductRepository productRepository;
	@Autowired
	ChequeRepository chequeRepository;
	@Autowired
	ChequeProductService cpService;
	
	@PostMapping(path="/addproduct")
	public String addProduct(RedirectAttributes redirectAttributes, 
			@RequestParam int productId, @RequestParam int chequeId, @RequestParam int amount) {

		boolean success = cpService.addProduct(chequeId, productId, amount);
		
			if (!success) {
				redirectAttributes.addFlashAttribute("errorMsg", "Cant add this product. Possibly there is such product in your cheque or this amount of product is unavailable");
				redirectAttributes.addAttribute("chequeId", chequeId);
				return "redirect:/cheques/edit";
			}
			
		redirectAttributes.addAttribute("chequeId", chequeId);
		return "redirect:/cheques/edit";
	}
	
	@GetMapping(path="/addproduct")
	public String beforeAddingProduct(RedirectAttributes redirectAttributes, @RequestParam int chequeId) {
		redirectAttributes.addAttribute("chequeId", chequeId);
		return "redirect:/products/all";
	}
	
	@PostMapping(path="/editproduct")
	public String editProduct(RedirectAttributes redirectAttributes, 
			@RequestParam int chequeId, @RequestParam int cpId, @RequestParam int amount) {
		boolean success = cpService.editProduct(cpId, amount);
		
		if(!success) {
			redirectAttributes.addFlashAttribute("errorMsg", "Can't take " + amount + "of this product");
			redirectAttributes.addAttribute("chequeId", chequeId);
			return "redirect:/cheques/edit";
		}
				
		redirectAttributes.addAttribute("chequeId",chequeId);
		return "redirect:/cheques/edit";
	}
	
	@Transactional()
	@PostMapping(path="/delete")
	public String deleteProduct(RedirectAttributes redirectAttributes, 
			@RequestParam int chequeId, @RequestParam int cpId) {
		cpService.deleteProduct(cpId);
		redirectAttributes.addFlashAttribute("successMsg", "Product deleted successfully");
		redirectAttributes.addAttribute("chequeId", chequeId);
		return "redirect:/cheques/edit";	
	}
	
	@Transactional()
	@PostMapping(path="/cancel")
	public String cancelProduct(RedirectAttributes redirectAttributes, 
			@RequestParam int chequeId, @RequestParam int cpId) {
		cpService.cancelProduct(cpId);
		redirectAttributes.addFlashAttribute("successMsg", "Product cancelled successfully");
		redirectAttributes.addAttribute("chequeId", chequeId);
		return "redirect:/cheques/edit";	
	}
}
