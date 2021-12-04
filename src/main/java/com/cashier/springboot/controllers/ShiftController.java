package com.cashier.springboot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.cashier.springboot.service.ShiftService;


@Controller
@RequestMapping(path = "/shifts")
public class ShiftController {
	@Autowired
	ShiftService shiftService;
	
	@GetMapping(path = "/all")
	public ModelAndView getAllShifts(@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "5") int size) {
		ModelAndView mav = new ModelAndView("shifts");
		
		mav.addObject("shifts", shiftService.getAllShifts(page, size));
		
		return mav;
	}
	
	@PostMapping(path="/close")
	public ModelAndView closeCurrentShift(@RequestParam int shiftId) {
		ModelMap map = new ModelMap();	

		boolean success = shiftService.closeShift(shiftId);	

		if (!success) {
			map.addAttribute("errorMsg", "No open shifts");
		} else {
			map.addAttribute("successMsg","Shift has been closed");
		}
		return new ModelAndView("redirect:/shifts/all", map);
	}
	
	@PostMapping(path="/open")
	public ModelAndView openNewShift() {
		ModelMap map = new ModelMap();			
		boolean success = shiftService.openShift();	
		if (!success) {
			map.addAttribute("errorMsg" , "Please check if ther is open shift");
		} else {
			map.addAttribute("successMsg","New shift is open");
		}
		return new ModelAndView("redirect:/shifts/all", map);
	}
	
	
}
