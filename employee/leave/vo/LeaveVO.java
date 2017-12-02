package com.payroll.employee.leave.vo;

import com.payroll.Utils;

public class LeaveVO {
	
	private int empId;
	private int leaveId;
	private String leaveType;
	private int noOfLeaves;
	private int leaveBalance;
	private String fullName;
	private int departmentId;
	private int designationId;
	private int headId;
	
	private int casualLeaves;
	private int paidLeaves;
	private int sickLeaves;
	
	public LeaveVO(){}
	
	public LeaveVO(int empId, String fName, String lName, int leaveId, String leaveType, int noOfLeaves, int leaveBalance){
		this.empId = empId;
		this.leaveId = leaveId;
		this.leaveBalance = leaveBalance;
		this.noOfLeaves = noOfLeaves;
		this.leaveType = leaveType;
		StringBuffer fullNameSB = new StringBuffer(fName);
		fullNameSB.append(" ");
		fullNameSB.append(Utils.safeTrim(lName));
		this.fullName = fullNameSB.toString();
	}
	
	public LeaveVO (int empId, int deptId, int desgId, int headId, int leaveId, String leaveType,
			int noOfLeaves, int leaveBalance){
		this.empId = empId;
		this.departmentId = deptId;
		this.designationId = desgId;
		this.leaveBalance = leaveBalance;
		this.noOfLeaves = noOfLeaves;
		this.leaveType = leaveType;
		this.leaveId = leaveId;
		this.headId = headId;
	}
	
	public LeaveVO(int empId, String empName, int cLeave, int pLeave, int sLeave, String leaveIds){
		this.casualLeaves = cLeave;
		this.sickLeaves = sLeave;
		this.paidLeaves = pLeave;
		this.empId =empId;
		this.fullName = empName;
		this.leaveBalance = casualLeaves + sickLeaves + paidLeaves;
	}

	public int getEmpId() {
		return empId;
	}

	public int getLeaveId() {
		return leaveId;
	}

	public String getLeaveType() {
		return leaveType;
	}

	public int getNoOfLeaves() {
		return noOfLeaves;
	}

	public int getLeaveBalance() {
		return leaveBalance;
	}

	public String getFullName() {
		return fullName;
	}

	public int getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}

	public int getDesignationId() {
		return designationId;
	}

	public void setDesignationId(int designationId) {
		this.designationId = designationId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public void setLeaveId(int leaveId) {
		this.leaveId = leaveId;
	}

	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}

	public void setNoOfLeaves(int noOfLeaves) {
		this.noOfLeaves = noOfLeaves;
	}

	public void setLeaveBalance(int leaveBalance) {
		this.leaveBalance = leaveBalance;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "employeeId:"+empId+" |  leaveType:"+this.leaveType+" | noOfLeaves:"+this.noOfLeaves +" | LeaveId:"+leaveId;
	}

	public int getHeadId() {
		return headId;
	}

	public void setHeadId(int headId) {
		this.headId = headId;
	}

	public int getCasualLeaves() {
		return casualLeaves;
	}

	public int getPaidLeaves() {
		return paidLeaves;
	}

	public int getSickLeaves() {
		return sickLeaves;
	}
}
