package com.cashier.springboot.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cashier.springboot.models.Product;
import com.cashier.springboot.models.Unit;
import com.cashier.springboot.repository.ProductRepository;
import com.cashier.springboot.repository.UnitRepository;

@Controller
@RequestMapping(path = "/products")
public class ProductController {
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private UnitRepository unitRepository;

	@PostMapping(path = "/add")
	public String addNewProduct(RedirectAttributes redirectAttributes, @Valid Product product) {
		productRepository.save(product);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		System.out.println(auth);
		redirectAttributes.addFlashAttribute("successMsg", "Product has been successfully saved");
		return "redirect:/products/all";
	}

	@GetMapping(path = "/edit")
	public ModelAndView getProduct(@RequestParam int id) {
		ModelAndView mav = new ModelAndView("product");
		Product product = productRepository.getById(id);
		List<Unit> units = unitRepository.findAll();
		mav.addObject("product", product);
		mav.addObject("unitsvalues", units);
		return mav;
	}

	@GetMapping(path = "/add")
	public ModelAndView addProduct() {
		ModelAndView mav = new ModelAndView("product");
		Product product = new Product();
		List<Unit> units = unitRepository.findAll();
		mav.addObject("product", product);
		mav.addObject("unitsvalues", units);
		return mav;
	}

	@GetMapping(path = "/all")
	public ModelAndView getAllProducts(@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "5") int size) {
		page--;
		ModelAndView mav = new ModelAndView("products");
		Pageable pageable = PageRequest.of(page, size);
		mav.addObject("products", productRepository.findAll(pageable));
		return mav;
	}

	@PostMapping(path = "/delete")
	public String deleteProduct(RedirectAttributes redirectAttributes, @RequestParam int id) {
		Product product = new Product();
		product.setId(id);
		productRepository.delete(product);
		redirectAttributes.addFlashAttribute("successMsg", "Product has been successfully deleted");
		return "redirect:/products/all";
	}

	@GetMapping(path = "/search")
	public ModelAndView searchProduct(@RequestParam String searchValue, @RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "5") int size) {
		page--;
		ModelAndView mav = new ModelAndView("products");
		Pageable pageable = PageRequest.of(page, size);
		mav.addObject("products", productRepository.findProductsByNameOrIdLike(searchValue, pageable));
		return mav;
	}
}