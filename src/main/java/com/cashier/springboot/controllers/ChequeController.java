package com.cashier.springboot.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cashier.springboot.models.Cheque;
import com.cashier.springboot.models.Unit;
import com.cashier.springboot.service.ChequeService;
import com.cashier.springboot.service.UnitService;

@Controller
@RequestMapping(path = "/cheques")
public class ChequeController {

	@Autowired
	ChequeService chequeService; 
	@Autowired
	UnitService unitService;

	
	@GetMapping(path = "/all")
	public ModelAndView getAllCheques(@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "5") int size) {
			ModelAndView mav = new ModelAndView("cheques");
			mav.addObject("cheques", chequeService.getAllCheques(page, size));
		return mav;
	}
	
	@PostMapping(path = "/add")
	public String addCheque(RedirectAttributes redirectAttributes) {
		Cheque cheque = chequeService.addCheque();
		if (cheque == null) {
			redirectAttributes.addFlashAttribute("errorMsg", "Shifts error. Possibly no open shifts.");
			return "redirect:/cheques/all";
		}
	
		redirectAttributes.addAttribute("chequeId", cheque.getId());
		return "redirect:/cheques/edit";
	}
	
	@GetMapping(path = "/edit")
	public ModelAndView editCheque(@RequestParam int chequeId) {
		ModelAndView mav = new ModelAndView("cheque");
		Cheque cheque = chequeService.editCheque(chequeId);
		List<Unit> units = unitService.findAll();
		mav.addObject("cheque", cheque);
		mav.addObject("unitsvalues", units);
		return mav;
	}
	
	@PostMapping(path="/delete")
	public String deleteCheque(RedirectAttributes redirectAttributes, @RequestParam int chequeId) {
		boolean success = chequeService.deleteCheque(chequeId);
		if (!success) {
			redirectAttributes.addFlashAttribute("errorMsg", "Cheque is already closed. You can't delete closed cheque. Only cancel one");
			redirectAttributes.addAttribute("chequeId", chequeId);
			return "redirect:/cheques/edit";
		}
		
		redirectAttributes.addFlashAttribute("successMsg", "Cheque has been deleted successfully");
		return "redirect:/cheques/all";
	}
	
	@PostMapping(path="/close")
	public String closeCheque(RedirectAttributes redirectAttributes, @RequestParam int chequeId) {
		boolean success = chequeService.closeCheque(chequeId);
		if (!success) {
			redirectAttributes.addFlashAttribute("errorMsg", "Error deleting cheque. Possibly empty cheque.");
			redirectAttributes.addAttribute("chequeId", chequeId);
			return "redirect:/cheques/edit";
		}
		redirectAttributes.addFlashAttribute("successMsg", "Cheque closed");
		return "redirect:/cheques/all";	
	}
	
	
	@PostMapping(path="/cancel")
	public String cancelCheque(RedirectAttributes redirectAttributes, @RequestParam int chequeId) {
		boolean success = chequeService.cancelCheque(chequeId);
		if (!success) {
			redirectAttributes.addFlashAttribute("errorMsg", "Error canceling cheque");
		} else {
			redirectAttributes.addFlashAttribute("successMsg", "Cheque cancelled successfully");
		}
		redirectAttributes.addAttribute("chequeId", chequeId);
		return "redirect:/cheques/edit";	
	}
	
	
}
