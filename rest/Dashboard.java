package com.payroll.rest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class Dashboard {
	
	 @RequestMapping(value = "/dashboard", method = RequestMethod.GET)public 
	   String printHello(ModelMap model) {
		     return "dashboard";
	   }

}
