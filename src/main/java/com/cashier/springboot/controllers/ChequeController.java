package com.cashier.springboot.controllers;

import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cashier.springboot.models.Cheque;
import com.cashier.springboot.models.Shift;
import com.cashier.springboot.models.Unit;
import com.cashier.springboot.models.User;
import com.cashier.springboot.repository.ChequeRepository;
import com.cashier.springboot.repository.ShiftRepository;
import com.cashier.springboot.repository.UnitRepository;

@Controller
@RequestMapping(path = "/cheques")
public class ChequeController {
	@Autowired
	ChequeRepository chequeRepository;
	@Autowired
	UnitRepository unitRepository;
	@Autowired
	ShiftRepository shiftRepository;

	
	@GetMapping(path = "/all")
	public ModelAndView getAllCheques(@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "5") int size) {
		page--;
		ModelAndView mav = new ModelAndView("cheques");
		Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
		mav.addObject("cheques", chequeRepository.findAll(pageable));
		return mav;
	}
	
	@PostMapping(path = "/add")
	public String addCheque(RedirectAttributes redirectAttributes) {
	// Get open shifts	
		List<Shift> openShiftsList = shiftRepository.getOpenShift();
		if(openShiftsList.isEmpty() | openShiftsList.size() > 1) {
			redirectAttributes.addFlashAttribute("errorMsg", "Shifts error. Possibly no open shifts.");
			return "redirect:/cheques/all";
		}
		Cheque cheque = new Cheque();
		User mock = new User();/// take from session
		mock.setId(1);
		cheque.setCreatedBy(mock);
		cheque.setShiftIdOpened(openShiftsList.get(0));
		List<Unit> units = unitRepository.findAll();
		chequeRepository.save(cheque);
		
		redirectAttributes.addAttribute("chequeId", cheque.getId());
		return "redirect:/cheques/edit";
	}
	
	@GetMapping(path = "/edit")
	public ModelAndView editCheque(@RequestParam int chequeId) {
		ModelAndView mav = new ModelAndView("cheque");
		Cheque cheque = chequeRepository.getById(chequeId);
		List<Unit> units = unitRepository.findAll();
		mav.addObject("cheque", cheque);
		mav.addObject("unitsvalues", units);
		return mav;
	}
	
	@PostMapping(path="/delete")
	public String deleteCheque(RedirectAttributes redirectAttributes, @RequestParam int chequeId) {
		//check if cheque is closed
		Cheque cheque = chequeRepository.getById(chequeId);
		if (cheque.getDateCreated() != null) {
			redirectAttributes.addFlashAttribute("errorMsg", "Cheque is already closed. You can't delete closed cheque. Only cancel one");
			redirectAttributes.addAttribute("chequeId", cheque.getId());
			return "redirect:/cheques/edit";
		}
		chequeRepository.delete(cheque);
		redirectAttributes.addFlashAttribute("successMsg", "Cheque has been deleted successfully");
		return "redirect:/cheques/all";
	}
	
	@PostMapping(path="/close")
	public String closeCheque(RedirectAttributes redirectAttributes, @RequestParam int chequeId) {
		Cheque cheque = chequeRepository.getById(chequeId);
		if (cheque.getDateCreated() != null) {
			redirectAttributes.addFlashAttribute("errorMsg", "Cheque is already closed.");
			redirectAttributes.addAttribute("chequeId", cheque.getId());
			return "redirect:/cheques/edit";
		}
		if (cheque.getProducts().isEmpty()) {
			redirectAttributes.addFlashAttribute("errorMsg", "You can't close cheque without products. You can delete it");
			redirectAttributes.addAttribute("chequeId", cheque.getId());
			return "redirect:/cheques/edit";
		}
		cheque.setDateCreated(Instant.now());
		chequeRepository.save(cheque);
		return "redirect:/cheques/all";	
	}
	
	
	@PostMapping(path="/cancel")
	public String cancelCheque(RedirectAttributes redirectAttributes, @RequestParam int chequeId) {
		redirectAttributes.addFlashAttribute("successMsg", "Cheque cancelled successfully");
		Cheque cheque = chequeRepository.getById(chequeId);
		cheque.setCancelledDate(Instant.now());
		User user = new User();//mock get from db
		user.setId(3);
		cheque.setCancelledBy(user);
		chequeRepository.save(cheque);
		redirectAttributes.addAttribute("chequeId", cheque.getId());
		return "redirect:/cheques/edit";	
	}
	
	
}
