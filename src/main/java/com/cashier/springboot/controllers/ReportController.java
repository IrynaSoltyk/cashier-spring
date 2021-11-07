package com.cashier.springboot.controllers;


import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.cashier.springboot.models.Cheque;
import com.cashier.springboot.models.Report;
import com.cashier.springboot.models.User;
import com.cashier.springboot.repository.ChequeRepository;


@Controller
@RequestMapping(path = "/report")
public class ReportController {
	
	@Autowired
	ChequeRepository chequeRepository;
	
	@GetMapping(path = "/compose")
	public ModelAndView Report(@RequestParam int shiftId, String type) {
		List<Cheque> cheques = chequeRepository.report(shiftId);
		int closed = 0;
		BigDecimal closedCost = BigDecimal.ZERO;
		int cancelled = 0;
		BigDecimal cancelledCost = BigDecimal.ZERO;
		for (Cheque cheque : cheques) {
			if(cheque.getCancelledDate() == null) {
				cancelled ++;
				cancelledCost = cancelledCost.add(cheque.getCost());
			} else {
				closed ++;
				closedCost = closedCost.add(cheque.getCost());
			}
		}
		
		Report report = new Report();
		report.setShiftId(shiftId);
		report.setCancelled(cancelled);
		report.setCancelledCost(cancelledCost);
		report.setClosed(closed);
		report.setClosedCost(closedCost);
		
		ModelAndView mav = new ModelAndView("report");
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)authentication.getPrincipal();
		mav.addObject("report", report);
		mav.addObject("type", type);
		mav.addObject("user", user.getUsername());
		return mav;
	}

}
