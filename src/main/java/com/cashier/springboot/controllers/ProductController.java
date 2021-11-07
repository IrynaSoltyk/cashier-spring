package com.cashier.springboot.controllers;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
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
	
	Logger logger = LogManager.getLogger(ProductController.class);

	@PostMapping(path = "/add")
	public ModelAndView addNewProduct(RedirectAttributes redirectAttributes, @Valid Product product, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			ModelAndView mav = createProductModelAndView();
			return mav;
		}
		
		try {
			productRepository.saveAndFlush(product);
		} catch (DataIntegrityViolationException e) {
			logger.info(e);
			ModelAndView mav = createProductModelAndView();
			mav.addObject("errorMsg", "There s product with name "+product.getName()+" already in the database");
			return mav;
		}
		redirectAttributes.addFlashAttribute("successMsg", "Product has been successfully saved");
		return new ModelAndView("redirect:/products/all");
	}

	@GetMapping(path = "/edit")
	public ModelAndView getProduct(@RequestParam int id) {
		ModelAndView mav = createProductModelAndView();
		Product product = productRepository.getById(id);
		mav.addObject("product", product);
		return mav;
	}

	@GetMapping(path = "/add")
	public ModelAndView addProduct() {
		ModelAndView mav = createProductModelAndView();
		Product product = new Product();
		mav.addObject("product", product);
		
		return mav;
	}

	private ModelAndView createProductModelAndView() {
		ModelAndView mav = new ModelAndView("product");
		List<Unit> units = unitRepository.findAll();
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