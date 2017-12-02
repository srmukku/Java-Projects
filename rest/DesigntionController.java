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
import com.payroll.designation.vo.DesignationVO;
import com.payroll.headInfo.business.HeadInfoService;
import com.payroll.headInfo.dataobjects.HeadInfo;
import com.payroll.headInfo.vo.HeadInfoVO;
@Controller
public class DesigntionController {
	
	@RequestMapping(value="/listDesg", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List<DesignationVO> getDesignations(){
	   List<DesignationVO> designations = new DesignationService().getDesignationVOList();
	   return designations;
    }
	
	@RequestMapping(value = "/viewDesg", method = RequestMethod.GET)
	public String viewDesg(ModelMap model) {
		return "listDesg";
	}
	
	@RequestMapping(value="/loadDesignations",method=RequestMethod.POST, produces = "application/json")
	public @ResponseBody List<DesignationVO> loadDesignations(@RequestBody HeadInfo headInfo){
	   System.out.println("Department Id:"+headInfo.getDepartmentId());
	   List<DesignationVO> designations = new DesignationService().getDesignationsByHead(headInfo.getHeadId());
	   return designations;
	}
	
	
	@RequestMapping(value = "/inputDesg", method = RequestMethod.POST)
	public ModelAndView inputDesg(DesignationVO designation) {
		System.out.println("designation Id:"+designation.getDesignationId());
		ObjectMapper mapper = new ObjectMapper();
		List<Department> deptList = new DepartmentService().getDepartments();
		String depJSON = "";
		try {
			depJSON = mapper.writeValueAsString(deptList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(designation.getDesignationId() != 0){
			designation = new DesignationService().getDesignationVOById(designation.getDesignationId());
			System.out.println("designation dept:"+designation.getDepartmentId());
		}
		ModelAndView model = new ModelAndView("designation", "command", designation);
		model.addObject("designation", designation);
		model.addObject("departments", depJSON);
		return model;
	}
	   
	@RequestMapping(value="/addDesg",method=RequestMethod.POST)
	public @ResponseBody
	String addDesg(@RequestBody Designation designation){
	   System.out.println("Designation:"+designation);
	   String result = new DesignationService().addUpdateDesignation(designation);
	   System.out.println("Result:"+result);
	  return result;
	}
	
	@RequestMapping(value="/deleteDesg",method=RequestMethod.POST)
	public String deleteDesg(Designation designation){
	   System.out.println("designation:"+designation);
	   if(new DesignationService().deleteDesignation(designation.getDesignationId()))
		   System.out.println("Successfully deleted Designation!!");
	   else
		   System.out.println("Failed to deleted Designation!!");
	   return "listDesg";
	}
}
