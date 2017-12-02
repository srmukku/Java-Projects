package com.payroll.report.business;

import java.util.List;

import com.payroll.report.vo.EmployeeReportDAO;
import com.payroll.report.vo.EmployeeReportVO;

public class EmployeeReportService {
	
	public List<EmployeeReportVO> getEmployees(){
		return new EmployeeReportDAO().getEmployees();
	}
	public List<EmployeeReportVO> getEmployees(int deptId, int desgId){
		if (deptId != 0 && desgId != 0)
			return new EmployeeReportDAO().getEmployees(deptId, desgId);
		else if (deptId != 0)
			return new EmployeeReportDAO().getEmployeesByDepartment(deptId);
		else if (desgId != 0 )
			return new EmployeeReportDAO().getEmployeesByDesignation(desgId);
		else return new EmployeeReportDAO().getEmployees();
			
	}
}
