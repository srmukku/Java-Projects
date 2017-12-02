package com.payroll.employee;

import java.util.List;
import java.util.Map;

public class EmployeeModel {
	
	List<Map<String, String>> designations; 
	List<Map<String, String>> departments;
	
	public List<Map<String, String>> getDesignations() {
		return designations;
	}
	public void setDesignations(List<Map<String, String>> designations) {
		this.designations = designations;
	}
	public List<Map<String, String>> getDepartments() {
		return departments;
	}
	public void setDepartments(List<Map<String, String>> departments) {
		this.departments = departments;
	}

}
