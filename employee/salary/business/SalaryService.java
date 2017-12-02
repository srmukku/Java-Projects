package com.payroll.employee.salary.business;

import java.util.List;

import com.payroll.employee.salary.dataobjects.Salary;
import com.payroll.employee.salary.dataobjects.SalaryDAO;
import com.payroll.employee.salary.vo.SalaryVO;

public class SalaryService {
	public List<SalaryVO> getSalaries(){
		return new SalaryDAO().getSalaries();
	}
	
	public String addUpdateSalary(Salary salary){
		return new SalaryDAO().addUpdateSalary(salary);
	}
	public SalaryVO getEmpSalary(int empId){
		return new SalaryDAO().getEmpSalary(empId);
	}
	public String deleteSalary(int empId){
		return new SalaryDAO().deleteEmpSal(empId);
	}

}
