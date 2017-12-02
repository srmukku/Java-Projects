package com.payroll.employee.salary.vo;

import java.io.Serializable;

import com.payroll.Utils;

public class SalaryVO implements Serializable{
	private int empId;
	private int year;
	private double basic;
	private double gradePay;
	private double scalePay;
	private double scaleInc;
	private String fullName;
	private int departmentId;
	private int designationId;
	private int headId;
	public SalaryVO() {
		
	}
	public SalaryVO (int empId, String fName, String lName, int year, double basic, 
			double gradePay, double scalePay, double scaleInc){
		this.empId = empId;
		this.year = year;
		this.basic = basic;
		this.gradePay = gradePay;
		this.scaleInc = scaleInc;
		this.scalePay = scalePay;
		StringBuffer fullNameSB = new StringBuffer(fName);
		fullNameSB.append(" ");
		fullNameSB.append(Utils.safeTrim(lName));
		this.fullName = fullNameSB.toString();
		
	}
	
	public SalaryVO (int empId, int deptId, int desgId, int headId, int year, double basic, 
			double gradePay, double scalePay, double scaleInc){
		this.empId = empId;
		this.departmentId = deptId;
		this.designationId = desgId;
		this.year = year;
		this.basic = basic;
		this.gradePay = gradePay;
		this.scaleInc = scaleInc;
		this.scalePay = scalePay;
		this.headId = headId;
	}
	
	public int getEmpId() {
		return empId;
	}
	public int getYear() {
		return year;
	}
	public double getBasic() {
		return basic;
	}
	public double getGradePay() {
		return gradePay;
	}
	public double getScalePay() {
		return scalePay;
	}
	public double getScaleInc() {
		return scaleInc;
	}

	public String getFullName() {
		return fullName;
	}

	public int getDepartmentId() {
		return departmentId;
	}

	public int getDesignationId() {
		return designationId;
	}
	public void setEmpId(int empId) {
		this.empId = empId;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public void setBasic(double basic) {
		this.basic = basic;
	}
	public void setGradePay(double gradePay) {
		this.gradePay = gradePay;
	}
	public void setScalePay(double scalePay) {
		this.scalePay = scalePay;
	}
	public void setScaleInc(double scaleInc) {
		this.scaleInc = scaleInc;
	}
	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}
	public void setDesignationId(int designationId) {
		this.designationId = designationId;
	}
	public int getHeadId() {
		return headId;
	}
	public void setHeadId(int headId) {
		this.headId = headId;
	}
	
	
}
