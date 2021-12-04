package com.cashier.springboot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.cashier.springboot.service.ReportService;


@Controller
@RequestMapping(path = "/report")
public class ReportController {
	
	@Autowired
	ReportService reportService;
	
	@GetMapping(path = "/compose")
	public ModelAndView Report(@RequestParam int shiftId, String type) {
		ModelAndView mav = new ModelAndView("report");
		mav.addObject("report", reportService.makeReport(shiftId));
		mav.addObject("type", type);
		return mav;
	}

}
