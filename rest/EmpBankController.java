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
import com.payroll.employee.bank.business.BankService;
import com.payroll.employee.bank.dataobjects.Bank;
import com.payroll.employee.bank.vo.BankVO;
import com.payroll.employee.leave.business.LeaveService;
import com.payroll.employee.leave.vo.LeaveVO;

@Controller
public class EmpBankController {
	
	@RequestMapping(value="/listBank", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List<BankVO> getEmpBanks(){
		System.out.println("listBank-- getEmpBanks");
	   List<BankVO> empBanks = new BankService().getBankList();
	   return empBanks;
    }
	
	@RequestMapping(value = "/viewBank", method = RequestMethod.GET)
	public String viewBank(ModelMap model) {
		return "listBank";
	}
	
	@RequestMapping(value = "/inputBank", method = RequestMethod.POST)
	public ModelAndView inputBank(Bank bank) {
		ObjectMapper mapper = new ObjectMapper();
		System.out.println("inputBank -- bank:"+bank);
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
		BankVO bankVO = null; 
		bankVO = (bank.getEmpId() !=0) ? new BankService().getBankByEmpId(bank.getEmpId()): new BankVO();
		ModelAndView model = new ModelAndView("bank", "command", bankVO);
		model.addObject("bank", bankVO);
		model.addObject("departments", depJSON);
		model.addObject("designations", desigJSON);
		return model;
	}
	   
	@RequestMapping(value="/addBank",method=RequestMethod.POST)
	public @ResponseBody
	String addBank(@RequestBody Bank bank){
	   System.out.println("addBank -- Bank:"+bank);
	   String result = new BankService().addUpdateBank(bank);
	   System.out.println("Result:"+result);
	   return result;
	}
	
	@RequestMapping(value="/deleteBank",method=RequestMethod.POST)
	public String deleteBank(Bank bank){
	   System.out.println("deleteBank -- Bank:"+bank.getEmpId());
	   String result = new BankService().deleteEmpBank(bank.getEmpId());
	   System.out.println("result:"+result);
	   return "listBank";
	}
	
}
