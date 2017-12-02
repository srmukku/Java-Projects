package com.payroll.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.payroll.Utils;
import com.payroll.department.business.DepartmentService;
import com.payroll.department.dataobjects.Department;

@Controller
public class DepartmentController {
	@RequestMapping(value="/listDept", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List<Department> getDepartments(){
	   List<Department> departments = new DepartmentService().getDepartments();
	   return departments;
    }
	
	@RequestMapping(value = "/viewDept", method = RequestMethod.GET)
	public String viewDept(ModelMap model) {
		return "listDept";
	}
	
	@RequestMapping(value = "/inputDept", method = RequestMethod.POST)
	public  ModelAndView inputDept(Department department) {
		System.out.println("department:"+department);
		if(department.getDepartmentId() != 0)
			department = new DepartmentService().getDeptById(department.getDepartmentId());
		ModelAndView model = new ModelAndView("department", "command", department);
		model.addObject("department", department);
		//RedirectView
		
		return model;
	}
	   
	@RequestMapping(value="/addDept",method=RequestMethod.POST)
	public @ResponseBody
		String addDept(@RequestBody Department department){
		System.out.println("department:"+department);
		String result = new DepartmentService().addUpdateDepartment(department);
		return result;
	}
	
	@RequestMapping(value="/deleteDept",method=RequestMethod.POST)
	public String deleteDept(Department department){
	   System.out.println("department:"+department);
	   if(new DepartmentService().deleteDepartment(department.getDepartmentId()))
		   System.out.println("Successfully deleted Department!!");
	   else
		   System.out.println("Failed to deleted Department!!");
	   return "listDept";
	}
}
