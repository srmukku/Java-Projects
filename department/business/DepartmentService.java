package com.payroll.department.business;

import java.util.List;

import com.payroll.department.dataobjects.Department;
import com.payroll.department.dataobjects.DepartmentDAO;

public class DepartmentService {
	public List<Department> getDepartments(){
		return new DepartmentDAO().getDepartments();
	}
	
	public String addUpdateDepartment(Department dept){
		return new DepartmentDAO().addUpdateDepartment(dept);
	}
	
	public boolean deleteDepartment(int deptId){
		return new DepartmentDAO().deleteDepartment(deptId);
	}
	
	public Department getDeptById(int deptId){
		return new DepartmentDAO().getDepartmentById(deptId);
	}
}
