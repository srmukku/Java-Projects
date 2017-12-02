package com.payroll.rest;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.payroll.department.business.DepartmentService;
import com.payroll.department.dataobjects.Department;
import com.payroll.designation.business.DesignationService;
import com.payroll.designation.dataobjects.Designation;
import com.payroll.employee.Employee;
import com.payroll.employee.salary.vo.SalaryVO;
import com.payroll.report.business.EmployeeReportService;
import com.payroll.report.vo.EmployeeReportVO;

@Controller
public class EmployeeReportController {
	@RequestMapping(value="/employeeSearch",method=RequestMethod.GET)
	public ModelAndView getEmployeesSearch(HttpServletRequest request, ModelMap modelMap){
	   ObjectMapper mapper = new ObjectMapper();
	   List<Department> deptList = new DepartmentService().getDepartments();
	   List<Designation> desigList = new DesignationService().getDesignationList();
		   
	   String depJSON = "";
	   String desigJSON = "";
		try {
			depJSON = mapper.writeValueAsString(deptList);
			desigJSON = mapper.writeValueAsString(desigList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	   request.getSession().setAttribute("employees", new ArrayList());
	   request.getSession().setAttribute("departments", depJSON);
	   request.getSession().setAttribute("designations", depJSON);
	   
	   Employee employee = new Employee();
	   ModelAndView model = new ModelAndView("employeeReport", "command", employee);
	   model.addObject(employee);
	   model.addObject("departments", depJSON);
	   model.addObject("designations", desigJSON);
	   return model;
    }
	   
   @RequestMapping(value="/employeeReport", method=RequestMethod.POST)
   public ModelAndView getEmployeesReport(HttpServletRequest request, Employee employee){
	  /* List<Department> deptList = new DepartmentService().getDepartments();
	   List<Designation> desigList = new DesignationService().getDesignationList();
	   ObjectMapper mapper = new ObjectMapper();
	   String depJSON = "";
	   String desigJSON = "";
	   try {
			depJSON = mapper.writeValueAsString(deptList);
			desigJSON = mapper.writeValueAsString(desigList);
	   } catch (Exception e) {
		   	e.printStackTrace();
	   }*/
			
	   List<EmployeeReportVO> employeesList = new EmployeeReportService().getEmployees(employee.getDepartmentId(), employee.getDesignationId()); 
	   
	   List<EmployeeReportVO> employees = new ArrayList<EmployeeReportVO>();
	   String name = employee.getFirstName()!= null? employee.getFirstName().trim(): "";
	   if(!name.equals("") && employeesList != null && employeesList.size() != 0) {
		   for (EmployeeReportVO empVO : employeesList) {
			   if (empVO.getFullName().toUpperCase().contains(name.toUpperCase())) {
				   employees.add(empVO);
			   }
		   }
	   } else {
		   employees = employeesList;
	   }

	   request.getSession().setAttribute("employees", employees);
	   ModelAndView model = new ModelAndView("employeeReport", "command", employee);
	   model.addObject(employee);
	   model.addObject("employees", employees);
	   //model.addObject("departments", depJSON);
	   //model.addObject("designations", desigJSON);
	   return model;
   }
   
   @RequestMapping(value = "/employeeRptDownload", method = RequestMethod.GET, produces = "text/csv")
   public @ResponseBody void downloadEmployeeReport(HttpServletRequest request, HttpServletResponse response) throws IOException {
	   DateFormat format = new SimpleDateFormat("yyyyMMdd_HHmmss");
	   String csvFileName = "EmployeeReport_"+format.format(new Date())+".csv";
	   
	   List<EmployeeReportVO> employeesList = (List<EmployeeReportVO>)request.getSession().getAttribute("employees");
	   StringBuilder fileContent =new StringBuilder("Name, Gender, Date of Birth, Department, Head, Designation, Joining Date, Phone, Email, Address, PAN, Aadhar, Year, Basic Pay, Grade Pay, Scale Pay, Scale Increment,").append("\n");
	   for (EmployeeReportVO employeeVO : employeesList) {
		   fileContent.append(employeeVO.getFullName()).append(", ");
		   fileContent.append(employeeVO.getGender()).append(", ");
		   fileContent.append(employeeVO.getDob()).append(", ");
		   fileContent.append(employeeVO.getDepartment()).append(", ");
		   fileContent.append(employeeVO.getHeadName()).append(", ");
		   fileContent.append(employeeVO.getDesignation()).append(", ");
		   fileContent.append(employeeVO.getJoiningDate()).append(", ");
		   fileContent.append(employeeVO.getPhone()).append(", ");
		   fileContent.append(employeeVO.getEmail()).append(", ");
		   fileContent.append(employeeVO.getAddress()).append(", ");
		   fileContent.append(employeeVO.getPan()).append(", ");
		   fileContent.append(employeeVO.getAdharNo()).append(", ");
		   SalaryVO salVo = employeeVO.getSalaryVo();
		   if (salVo != null) {
			   fileContent.append(salVo.getYear()).append(", ");
			   fileContent.append(salVo.getBasic()).append(", ");
			   fileContent.append(salVo.getGradePay()).append(", ");
			   fileContent.append(salVo.getScalePay()).append(", ");
			   fileContent.append(salVo.getScaleInc()).append(", ");
		   }
		   fileContent.append("\n");
		   
	   }
	   
       response.setContentType("text/csv");
       response.setHeader("Content-Disposition", "attachment; filename=" + csvFileName);

       ServletOutputStream os = response.getOutputStream();
	   os.write(fileContent.toString().getBytes());
	   os.flush();
	   os.close();
   }
   
   public String getValue(String val) {
	   return (val == null) ? "" : val.trim();
   }

}	
