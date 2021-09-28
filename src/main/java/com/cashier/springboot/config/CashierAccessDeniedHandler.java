package com.cashier.springboot.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;

public class CashierAccessDeniedHandler  implements AccessDeniedHandler {

	  //  public static final Logger LOG
	    //  = Logger.getLogger();

	    @Override
	    public void handle(
	      HttpServletRequest request,
	      HttpServletResponse response, 
	      AccessDeniedException exc) throws IOException, ServletException {
	        
	        Authentication auth 
	          = SecurityContextHolder.getContext().getAuthentication();
	        if (auth != null) {
	        //    LOG.warn("User: " + auth.getName() 
	         //     + " attempted to access the protected URL: "
	        //      + request.getRequestURI());
	        }

	        response.sendRedirect(request.getContextPath() + "/403");
	    }
	}