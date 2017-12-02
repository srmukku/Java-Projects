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
import com.payroll.employee.leave.business.LeaveService;
import com.payroll.employee.leave.dataobjects.Leave;
import com.payroll.employee.leave.vo.LeaveVO;
import com.payroll.employee.salary.business.SalaryService;
import com.payroll.employee.vo.EmployeeVO;
import com.payroll.overtime.business.OvertimeService;
import com.payroll.overtime.vo.Overtime;

@Controller
public class EmpLeaveController {
	
	@RequestMapping(value="/listLeaves", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List<LeaveVO> getEmpLeaves(){
		System.out.println("listLeaves-- getEmpLeaves");
	   List<LeaveVO> overtimes = new LeaveService().getLeaves();
	   return overtimes;
    }
	
	@RequestMapping(value = "/viewLeave", method = RequestMethod.GET)
	public String viewLeave(ModelMap model) {
		return "listLeaves";
	}
	
	@RequestMapping(value = "/inputLeave", method = RequestMethod.POST)
	public ModelAndView inputLeave(LeaveVO leave) {
		ObjectMapper mapper = new ObjectMapper();
		System.out.println("inputLeave -- leave:"+leave);
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
		if(leave.getLeaveId()!=0)
			leave = new LeaveService().getLeaveByIde(leave.getLeaveId());
		ModelAndView model = new ModelAndView("leave", "command", leave);
		model.addObject("leave", leave);
		model.addObject("departments", depJSON);
		model.addObject("designations", desigJSON);
		return model;
	}
	   
	@RequestMapping(value="/addLeave",method=RequestMethod.POST)
	public @ResponseBody
	String addLeave(@RequestBody LeaveVO leave){
	   System.out.println("addLeave -- Leave:"+leave);
	   String result = new LeaveService().addUpdateLeave(leave);
	   System.out.println("Result:"+result);
	   return result;
	}
	
	/*@RequestMapping(value="/loadEmployees",method=RequestMethod.POST, produces = "application/json")
	public @ResponseBody List<EmployeeVO> loadEmployees(@RequestBody Overtime overtime){
	   System.out.println("overtime:"+overtime);
	   List<EmployeeVO> employees = new EmployeeService().getEmployees(overtime.getDepartmentId(), overtime.getDesignationId());
	   return employees;
	}
	*/
	@RequestMapping(value="/deleteLeave",method=RequestMethod.POST)
	public String deleteLeave(LeaveVO leave){
	   System.out.println("deleteLeave -- Leave:"+leave.getLeaveId());
	   String result = new LeaveService().deleteLeave(leave.getLeaveId());
	   System.out.println("result:"+result);
	   return "listLeaves";
	}

	@RequestMapping(value="/empLeaves",method=RequestMethod.POST)
	public String getLeavesByEmp(LeaveVO leave){
	   System.out.println("empLeaves -- Leave:"+leave.getEmpId());
	   LeaveVO leaveVoNew = new LeaveService().getEmpAvailableLeaves(leave.getLeaveId());
	   System.out.println("result:"+leaveVoNew);
	   return "listLeaves";
	}

}
