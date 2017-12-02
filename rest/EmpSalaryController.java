package com.payroll.rest;

import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.payroll.department.business.DepartmentService;
import com.payroll.department.dataobjects.Department;
import com.payroll.designation.business.DesignationService;
import com.payroll.designation.dataobjects.Designation;
import com.payroll.employee.leave.business.LeaveService;
import com.payroll.employee.leave.dataobjects.Leave;
import com.payroll.employee.leave.vo.LeaveVO;
import com.payroll.employee.salary.business.SalaryService;
import com.payroll.employee.salary.dataobjects.Salary;
import com.payroll.employee.salary.vo.SalaryVO;

@Controller
public class EmpSalaryController {
	@RequestMapping(value="/listSalary", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List<SalaryVO> getEmpSalaries(){
		System.out.println("listSalary-- getEmpSalaries");
	   List<SalaryVO> salaries = new SalaryService().getSalaries();
	   return salaries;
    }
	
	@RequestMapping(value = "/viewSalary", method = RequestMethod.GET)
	public String viewSalary(ModelMap model) {
		return "listSalary";
	}
	
	@RequestMapping(value = "/inputSalary", method = RequestMethod.POST)
	public ModelAndView inputSalary(SalaryVO salary) {
		ObjectMapper mapper = new ObjectMapper();
		System.out.println("inputSalary -- salary - empId:"+salary.getEmpId());
		List<Designation> desigList = new DesignationService().getDesignationList();
		List<Department> deptList = new DepartmentService().getDepartments();
		String desigJSON = "";
		String depJSON = "";
		try {
			depJSON = mapper.writeValueAsString(deptList);
			desigJSON = mapper.writeValueAsString(desigList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(salary.getEmpId() != 0)
			salary = new SalaryService().getEmpSalary(salary.getEmpId());
		ModelAndView model = new ModelAndView("salary", "command", salary);
		model.addObject("salary", salary);
		model.addObject("departments", depJSON);
		model.addObject("designations", desigJSON);
		return model;
	}
	   
	@RequestMapping(value="/addSalary",method=RequestMethod.POST)
	public @ResponseBody
	String addSalary(@RequestBody Salary salary){
	   System.out.println("addSalary -- salary:"+salary);
	   String result = new SalaryService().addUpdateSalary(salary);
	   System.out.println("Add/Update Salary -- result:"+result);
	   return result;
	}
	
	@RequestMapping(value="/deleteSalary",method=RequestMethod.POST)
	public String deleteSalary(Salary salary){
	   System.out.println("deleteSalary -- salary:"+salary.getEmpId());
	   String result = new SalaryService().deleteSalary(salary.getEmpId());
	   System.out.println("Result:"+result);
	   return "listSalary";
	}
}
