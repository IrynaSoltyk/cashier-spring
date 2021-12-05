package com.cashier.springboot.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.cashier.springboot.models.Cheque;
import com.cashier.springboot.models.Report;
import com.cashier.springboot.models.User;
import com.cashier.springboot.repository.ChequeRepository;

@Service
public class ReportServiceImpl implements ReportService {
	
	@Autowired
	ChequeRepository chequeRepository;
	
	@Override
	public Report makeReport(int shiftId) {
		List<Cheque> cheques = chequeRepository.report(shiftId);
		
		int closed = 0;
		BigDecimal closedCost = BigDecimal.ZERO;
		int cancelled = 0;
		BigDecimal cancelledCost = BigDecimal.ZERO;
		for (Cheque cheque : cheques) {
			if(cheque.getCancelledDate() != null) {
				cancelled ++;
				cancelledCost = cancelledCost.add(cheque.getCost());
			} else {
				closed ++;
				closedCost = closedCost.add(cheque.getCost());
			}
		}
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)authentication.getPrincipal();
		
		Report report = new Report();
		report.setShiftId(shiftId);
		report.setCancelled(cancelled);
		report.setCancelledCost(cancelledCost);
		report.setClosed(closed);
		report.setClosedCost(closedCost);
		report.setComposedBy(user.getUsername());
		
		return report;
	}
}
