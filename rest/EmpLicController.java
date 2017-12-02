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
import com.payroll.employee.lic.business.EmpLicService;
import com.payroll.employee.lic.vo.EmpLicVO;
import com.payroll.employee.qtr.business.EmpQuartersService;
import com.payroll.employee.qtr.vo.EmpQuartersVO;

@Controller
public class EmpLicController {

	@RequestMapping(value="/listEmpLic", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List<EmpLicVO> getEmpLicList(){
		System.out.println("listEmpLic-- getEmpLicList");
	   List<EmpLicVO> empLics = new EmpLicService().getEmpLicList();
	   return empLics;
    }
	
	@RequestMapping(value = "/viewEmpLic", method = RequestMethod.GET)
	public String viewEmpLic(ModelMap model) {
		return "listEmpLic";
	}
	
	@RequestMapping(value = "/inputEmpLic", method = RequestMethod.POST)
	public ModelAndView inputEmpLic(EmpLicVO empLicVO) {
		ObjectMapper mapper = new ObjectMapper();
		System.out.println("inputEmpLic -- EmpLic:"+empLicVO);
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
		if(empLicVO.getEmpId()!=0)
			empLicVO = new EmpLicService().getEmpLicById(empLicVO.getEmpId());
		ModelAndView model = new ModelAndView("empLic", "command", empLicVO);
		model.addObject("empLic", empLicVO);
		model.addObject("departments", depJSON);
		model.addObject("designations", desigJSON);
		return model;
	}
	   
	@RequestMapping(value="/addEmpLic",method=RequestMethod.POST)
	public @ResponseBody
	String addEmpLic(@RequestBody EmpLicVO empLicVO){
	   System.out.println("addEmpLic -- EmpLic:"+empLicVO);
	   String result = new EmpLicService().addUpdateEmpLic(empLicVO);
	   System.out.println("Result:"+result);
	   return result;
	}
	
	@RequestMapping(value="/deleteLic",method=RequestMethod.POST)
	public String deleteQtr(EmpLicVO empLicVO){
	   System.out.println("deleteLic -- empLicVO:"+empLicVO.getEmpId());
	   String result = new EmpLicService().deleteLic(empLicVO.getEmpId());
	   System.out.println("Result:"+result);
	   return "listEmpLic";
	}

}
