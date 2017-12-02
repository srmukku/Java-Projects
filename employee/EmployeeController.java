package com.payroll.employee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.payroll.department.dataobjects.Department;
import com.payroll.department.business.DepartmentService;
import com.payroll.designation.dataobjects.Designation;
import com.payroll.designation.business.DesignationService;

import org.springframework.ui.ModelMap;

@Controller
public class EmployeeController {
	   @RequestMapping(value = "/employee", method = RequestMethod.GET)public String printHello(ModelMap model) {
		      //model.addAttribute("message", "Hello Spring MVC Framework!");
		      return "listEmp";
	   }
	   
	   @RequestMapping(value="/view", method = RequestMethod.GET, produces = "application/json")
	    public @ResponseBody List<Employee> getEmployees(){
		   List<Employee> employees = new ArrayList<Employee>();
		   employees.add(new Employee("Rajendra", "Gangarde", "", "Vice President", "raj@gmail.com", "9878687678"));
		   employees.add(new Employee("Ramanjaneyulu", "Kummari", "", "Tech Lead", "ram040284@gmail.com", "8939345488"));
		   employees.add(new Employee("Srinivasa", "Mukku", "", "Tech Lead", "srini.mukku@gmail.com", "98787687686"));
	        return employees;
	    }
	   @RequestMapping(value = "/viewEmp", method = RequestMethod.GET)
	   public ModelAndView  viewEmp() {
		   ObjectMapper mapper = new ObjectMapper();
		   
		   List<Department> deptList = new DepartmentService().getDepartments();
		   /*deptList.add(new Department(1, "CSE"));
		   deptList.add(new Department(2, "EEE"));
		   deptList.add(new Department(1, "Mechanical"));*/
		   
		   List<Designation> desigList = new DesignationService().getDesignationList();/*new ArrayList<Designation>();
		   /desigList.add(new Designation(1, "Chairman"));
		   desigList.add(new Designation(2, "Vise Chairman"));
		   desigList.add(new Designation(3, "Staff"));*/
		   
		   String depJSON = "";
		   String desigJSON = "";
			try {
				depJSON = mapper.writeValueAsString(deptList);
				desigJSON = mapper.writeValueAsString(desigList);
			} catch (Exception e) {
				e.printStackTrace();
			}
			ModelAndView model = new ModelAndView("employee");
			model.addObject("departments", depJSON);
			model.addObject("designations", desigJSON);
		 
			return model;
	   }
	   
	   @RequestMapping(value="/addEmp",method=RequestMethod.POST)
	    public @ResponseBody
	    String addEmp(@RequestBody Employee employee){
		   System.out.println("Employee"+employee);
		   if(employee!=null)
			   return "Yes";
		   else
			   return "No";
	    }
	   
}	
