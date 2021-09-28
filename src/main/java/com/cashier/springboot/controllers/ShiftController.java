package com.cashier.springboot.controllers;

import java.math.BigDecimal;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.cashier.springboot.models.Cheque;
import com.cashier.springboot.models.Report;
import com.cashier.springboot.models.Shift;
import com.cashier.springboot.models.User;
import com.cashier.springboot.repository.ShiftRepository;

import java.util.List;

@Controller
@RequestMapping(path = "/shifts")
public class ShiftController {
	@Autowired
	ShiftRepository shiftRepository;
	
	@GetMapping(path = "/all")
	public ModelAndView getAllShifts(@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "5") int size) {
		page--;
		ModelAndView mav = new ModelAndView("shifts");
		Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
		mav.addObject("shifts", shiftRepository.findAll(pageable));
		return mav;
	}
	
	@PostMapping(path="/close")
	public ModelAndView closeCurrentShift() {
		List<Shift> openShiftsList = shiftRepository.getOpenShift();
		ModelMap map = new ModelMap();	
		if(openShiftsList.isEmpty()) {
			map.addAttribute("errorMsg", "No open shifts");
			return new ModelAndView("redirect:/shifts/all", map);
		}
		Shift shift = openShiftsList.get(0);
		shift.setEndDate(Instant.now());
		if (openShiftsList.size() > 1) {
			shiftRepository.save(shift);
			map.addAttribute("errorMsg" , "There are still open shifts left. Please report this bug");
			return new ModelAndView("redirect:/shifts/all", map);
	} 
		shiftRepository.save(shift);
		map.addAttribute("successMsg","Shift has been closed");
		return new ModelAndView("redirect:/shifts/all", map);
		//throw new UnsupportedOperationException();
	}
	
	@PostMapping(path="/open")
	public ModelAndView openNewShift() {
		ModelMap map = new ModelMap();			
		List<Shift> openShiftsList = shiftRepository.getOpenShift();
		
		if (!openShiftsList.isEmpty()) {
			map.addAttribute("errorMsg" , "There are still open shifts left. Close current shift first.");
			return new ModelAndView("redirect:/shifts/all", map);
		} 			
		User mock = new User();// take from session
		mock.setId(3);
		Shift shift = new Shift();
		shift.setBeginDate(Instant.now());
		shift.setUser(mock);
		
		shiftRepository.save(shift);
		map.addAttribute("successMsg","New shift is open");
		return new ModelAndView("redirect:/shifts/all", map);
		//throw new UnsupportedOperationException();
	}
	
	@GetMapping(path="/report")
	public ModelAndView formReport(@RequestParam int shiftId) {
//		ModelAndView mav = new ModelAndView("report");	
//		Shift shift = shiftRepository.getById(shiftId);
//		int closed = 0;
//		int cancelled = 0;
//		BigDecimal cancelledCost = BigDecimal.ZERO;
//		BigDecimal closedCost = BigDecimal.ZERO;
//		List<Cheque> cheques = shift.getCheques();
//		System.out.println(cheques.size() + " ~~ ");
//		for (Cheque c : cheques) {
//			if (c.getDate() != null) {
//				if (c.getCancelledDate() == null) {
//					closed++;
//					closedCost = closedCost.add(c.getCost()); 
//				} else {
//					cancelled++;
//					cancelledCost = cancelledCost.add(c.getCost());
//				}
//			}
//		}
//		Report report = new Report();
//		report.setShiftId(shift.getId());
//		report.setClosed(closed);
//		report.setCancelled(cancelled);
//		report.setCancelledCost(cancelledCost);
//		report.setClosedCost(closedCost);
//		
//		mav.addObject("report",report);
//		return mav;
		throw new UnsupportedOperationException();
	}
	
}
