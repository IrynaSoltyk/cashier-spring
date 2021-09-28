package com.cashier.springboot.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cashier.springboot.models.Cheque;
import com.cashier.springboot.models.ChequeProduct;
import com.cashier.springboot.models.Product;
import com.cashier.springboot.repository.ChequeProductRepository;
import com.cashier.springboot.repository.ChequeRepository;
import com.cashier.springboot.repository.ProductRepository;
@Controller
@RequestMapping(path = "/cp")
public class ChequeProductController {

	@Autowired
	ProductRepository productRepository;
	@Autowired
	ChequeRepository chequeRepository;
	@Autowired
	ChequeProductRepository cpRepository;
	
	@Transactional()
	@PostMapping(path="/addproduct")
	public String addProduct(RedirectAttributes redirectAttributes, 
			@RequestParam int productId, @RequestParam int chequeId, @RequestParam int amount) {
		Cheque cheque = chequeRepository.getById(chequeId);
		Product product = productRepository.getById(productId);
		//check if this product is in this cheque already
		List <ChequeProduct> chequeProducts = cheque.getProducts();
		for (ChequeProduct cproduct : chequeProducts) {
			if (cproduct.getProduct().getId() == productId) {
				redirectAttributes.addFlashAttribute("errorMsg", "You already have this product in your cheque. Please edit amount instead");
				redirectAttributes.addAttribute("chequeId", cheque.getId());
				return "redirect:/cheques/edit";
			}
		}
		int availableAmount = product.getAmount();

		if (availableAmount < amount) {
			redirectAttributes.addFlashAttribute("errorMsg", "Can't take " + amount + "of this product. There're only " + availableAmount + "left on stock.");
			redirectAttributes.addAttribute("chequeId", cheque.getId());
			return "redirect:/cheques/edit";
		}
		ChequeProduct cp = new ChequeProduct();
		cp.setAmount(amount);
		cp.setPrice(product.getPrice());
		cp.setCheque(cheque);
		cp.setProduct(product);
		cpRepository.save(cp);
		product.setAmount(availableAmount-amount);
		productRepository.save(product);
		redirectAttributes.addAttribute("chequeId", cheque.getId());
		return "redirect:/cheques/edit";
	}
	
	@GetMapping(path="/addproduct")
	public String beforeAddingProduct(RedirectAttributes redirectAttributes, @RequestParam int chequeId) {
		redirectAttributes.addAttribute("chequeId", chequeId);
		return "redirect:/products/all";
	}
	
	@Transactional()
	@PostMapping(path="/editproduct")
	public String addProduct(RedirectAttributes redirectAttributes, 
			@RequestParam int cpId, @RequestParam int amount) {
		ChequeProduct cp = cpRepository.getById(cpId);
		// check availability
		int availableAmount = cp.getProduct().getAmount();
		int oldAmount = cp.getAmount();
		if((availableAmount + oldAmount) < amount) {
			redirectAttributes.addFlashAttribute("errorMsg", "Can't take " + amount + "of this product. There're only " + availableAmount + "left on stock.");
			redirectAttributes.addAttribute("chequeId", cp.getCheque().getId());
			return "redirect:/cheques/edit";
		}
		
		cp.setAmount(amount);
		Product product = cp.getProduct();
		product.setAmount(availableAmount + oldAmount - amount);
		productRepository.save(product);
		redirectAttributes.addAttribute("chequeId",cp.getCheque().getId());
		return "redirect:/cheques/edit";
	}
	
	@Transactional()
	@PostMapping(path="/delete")
	public String deleteProduct(RedirectAttributes redirectAttributes, 
			@RequestParam int cpId) {
		ChequeProduct cp = cpRepository.getById(cpId);
		cpRepository.delete(cp);
		Product product = cp.getProduct();
		int oldAmount = product.getAmount();
		product.setAmount(oldAmount + cp.getAmount());
		productRepository.save(product);
		redirectAttributes.addFlashAttribute("successMsg", "Product deleted successfully");
		redirectAttributes.addAttribute("chequeId",cp.getCheque().getId());
		return "redirect:/cheques/edit";	
	}
	
	@Transactional()
	@PostMapping(path="/cancel")
	public String cancelProduct(RedirectAttributes redirectAttributes, 
			@RequestParam int cpId) {
		ChequeProduct cp = cpRepository.getById(cpId);
		Product product = cp.getProduct();
		int oldAmount = product.getAmount();
		product.setAmount(oldAmount + cp.getAmount());
		cp.setAmount(0);
		cpRepository.save(cp);
		productRepository.save(product);
		redirectAttributes.addFlashAttribute("successMsg", "Product cancelled successfully");
		redirectAttributes.addAttribute("chequeId",cp.getCheque().getId());
		return "redirect:/cheques/edit";	
	}
}
