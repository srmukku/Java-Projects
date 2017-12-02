package com.payroll.employee.qtr.vo;

import com.payroll.Utils;

public class EmpQuartersVO {
	
	private int empId;
	private String afkQtr;
	private String fullName;
	private int departmentId;
	private int designationId;
	private short addUpdate; // 0 - Add / 1 - update
	
	
	public EmpQuartersVO(){
		
	}
	
	public EmpQuartersVO(int empId, Boolean afkQtr, int deptId, int desgId){
		this.empId = empId;
		this.afkQtr = afkQtr ? "Yes" : "No";
		this.departmentId = deptId;
		this.designationId = desgId;
	}
	
	public EmpQuartersVO(int empId, String fName, String lName, Boolean afkQtr){
		this.empId = empId;
		this.afkQtr = afkQtr ? "Yes" : "No";
		StringBuffer fullNameSB = new StringBuffer(fName);
		fullNameSB.append(" ");
		fullNameSB.append(Utils.safeTrim(lName));
		this.fullName = fullNameSB.toString();
		
	}

	public int getEmpId() {
		return empId;
	}

	public String getAfkQtr() {
		return afkQtr;
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
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "employeeId:"+empId+" |  afkQtr:"+this.afkQtr;
	}

	public short getAddUpdate() {
		return addUpdate;
	}

	public void setAddUpdate(short addUpdate) {
		this.addUpdate = addUpdate;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public void setAfkQtr(String afkQtr) {
		this.afkQtr = afkQtr;
	}

}
