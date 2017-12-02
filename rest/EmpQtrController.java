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
import com.payroll.employee.pf.business.EmpPfService;
import com.payroll.employee.pf.vo.EmpPfVO;
import com.payroll.employee.qtr.business.EmpQuartersService;
import com.payroll.employee.qtr.dataobjects.EmpQuarters;
import com.payroll.employee.qtr.vo.EmpQuartersVO;

@Controller
public class EmpQtrController {
	@RequestMapping(value="/listEmpQtr", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List<EmpQuartersVO> getEmpQtrList(){
		System.out.println("listEmpQtr-- getEmpQtrList");
	   List<EmpQuartersVO> empQtrs = new EmpQuartersService().getQtrList();
	   return empQtrs;
    }
	
	@RequestMapping(value = "/viewEmpQtr", method = RequestMethod.GET)
	public String viewEmpQtr(ModelMap model) {
		return "listEmpQtr";
	}
	
	@RequestMapping(value = "/inputEmpQtr", method = RequestMethod.POST)
	public ModelAndView inputEmpQtr(EmpQuartersVO empQtrVO) {
		ObjectMapper mapper = new ObjectMapper();
		System.out.println("inputEmpQtr -- empQtrVO:"+empQtrVO);
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
		if(empQtrVO.getEmpId() != 0)
			empQtrVO = new EmpQuartersService().getEmpQtrById(empQtrVO.getEmpId());
		ModelAndView model = new ModelAndView("empQtr", "command", empQtrVO);
		model.addObject("empQtr", empQtrVO);
		model.addObject("departments", depJSON);
		model.addObject("designations", desigJSON);
		return model;
	}
	   
	@RequestMapping(value="/addEmpQtr",method=RequestMethod.POST)
	public @ResponseBody
	String addEmpQtr(@RequestBody EmpQuarters empQtr){
	   System.out.println("addEmpQtr -- EmpQtr:"+empQtr);
	   String result = new EmpQuartersService().addUpdateEmpQtr(empQtr);
	   System.out.println("result:"+result);
	   return result;
	}
	
	@RequestMapping(value="/deleteQtr",method=RequestMethod.POST)
	public String deleteQtr(EmpQuartersVO empQuartersVO){
	   System.out.println("deleteQtr -- empQuartersVO:"+empQuartersVO.getEmpId());
	   String result = new EmpQuartersService().deleteEmpQtr(empQuartersVO.getEmpId());
	   System.out.println("Result:"+result);
	   return "listEmpQtr";
	}

}	
