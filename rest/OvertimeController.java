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
import com.payroll.employee.business.EmployeeService;
import com.payroll.employee.vo.EmployeeVO;
import com.payroll.overtime.business.OvertimeService;
import com.payroll.overtime.vo.Overtime;

@Controller
public class OvertimeController {
	
	@RequestMapping(value="/listOvertimes", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List<Overtime> getOvertimes(){
		System.out.println("listOvertimes-- getOvertimes");
	   List<Overtime> overtimes = new OvertimeService().getOvertimeList();
	   return overtimes;
    }
	
	@RequestMapping(value = "/viewOvertime", method = RequestMethod.GET)
	public String viewOvertime(ModelMap model) {
		return "listOvertimes";
	}
	
	@RequestMapping(value = "/inputOvertime", method = RequestMethod.POST)
	public ModelAndView inputOvertime(Overtime overtime) {
		ObjectMapper mapper = new ObjectMapper();
		System.out.println("inputOvertime -- overtime:"+overtime);
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
		if(overtime.getOvertimeDate() !=null)
			overtime.setOvertimeDate(null);
		ModelAndView model = new ModelAndView("overtime", "command", overtime);
		model.addObject("overtime", overtime);
		model.addObject("departments", depJSON);
		model.addObject("designations", desigJSON);
		return model;
	}
	   
	@RequestMapping(value="/addOvertime",method=RequestMethod.POST)
	public @ResponseBody
	String addOvertime(@RequestBody Overtime overtime){
	   System.out.println("addOvertime -- overtime:"+overtime);
	   boolean addedOvertime = new OvertimeService().addUpdateOvertime(overtime);
	   if(addedOvertime)
		   return "Yes";
	   else
		   return "No";
	}
	
	@RequestMapping(value="/loadEmployees",method=RequestMethod.POST, produces = "application/json")
	public @ResponseBody List<EmployeeVO> loadEmployees(@RequestBody Overtime overtime){
	   System.out.println("overtime:"+overtime);
	   List<EmployeeVO> employees = new EmployeeService().getEmployees(overtime.getDepartmentId(), overtime.getDesignationId());
	   return employees;
	}
	
	@RequestMapping(value="/deleteOvertime",method=RequestMethod.POST)
	public String deleteOvertime(Overtime overtime){
	   System.out.println("deleteOvertime -- overtime:"+overtime);
	   if(new OvertimeService().deleteOvertime(overtime.getEmpId(), overtime.getOvertimeDate()))
		   System.out.println("Successfully deleted Overtime!!");
	   else
		   System.out.println("Failed to deleted Overtime!!");
	   return "listOvertime";
	}
}
