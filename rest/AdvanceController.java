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

import com.payroll.advance.business.AdvanceService;
import com.payroll.advance.dataobjects.Advance;
import com.payroll.department.business.DepartmentService;
import com.payroll.department.dataobjects.Department;
import com.payroll.designation.business.DesignationService;
import com.payroll.designation.dataobjects.Designation;

@Controller
public class AdvanceController {
	
	@RequestMapping(value="/listAdvance", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List<com.payroll.advance.vo.Advance> getAdvances(){
	   List<com.payroll.advance.vo.Advance> conveyances = new AdvanceService().getAdvanceList();
	   return conveyances;
    }
	
	@RequestMapping(value = "/viewAdvance", method = RequestMethod.GET)
	public String viewAdvances(ModelMap model) {
		return "listAdvance";
	}
	
	@RequestMapping(value = "/inputAdvance", method = RequestMethod.POST)
	public ModelAndView inputAdvance(com.payroll.advance.vo.Advance advance) {
		ObjectMapper mapper = new ObjectMapper();
		System.out.println("Advance:"+advance);
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
		com.payroll.advance.vo.Advance newAdvance = new AdvanceService().getAdvanceById(advance.getEmpId(), advance.getPaymentDate());
		ModelAndView model = new ModelAndView("advance", "command", newAdvance);
		model.addObject("advance", newAdvance);
		model.addObject("departments", depJSON);
		model.addObject("designations", desigJSON);
		return model;
	}
	   
	@RequestMapping(value="/addAdvance",method=RequestMethod.POST)
	public @ResponseBody
	String addAdvance(@RequestBody com.payroll.advance.vo.Advance advance){
	   System.out.println("advance:"+advance);
	   boolean addedDesg = new AdvanceService().addUpdateAdvance(advance);
	   if(addedDesg)
		   return "Yes";
	   else
		   return "No";
	}
	
	@RequestMapping(value="/deleteAdvance",method=RequestMethod.POST)
	public String deleteAdvance(com.payroll.advance.vo.Advance advance){
	   System.out.println("advance:"+advance);
	   if(new AdvanceService().deleteAdvance(advance.getAdvanceId()))
		   System.out.println("Successfully deleted Advance!!");
	   else
		   System.out.println("Failed to deleted Advance!!");
	   return "listAdvance";
	}



}
