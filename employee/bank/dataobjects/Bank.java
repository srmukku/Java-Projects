package com.payroll.employee.bank.dataobjects;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

public class Bank implements Serializable{
	
	private int empId;
	private String bankName;
	private String ifscCode;
	private String accountNo;
	private short addUpdate; // 0 - Add / 1 - update
	private String status;
	private Timestamp rowUpdDate;
	
	
	public int getEmpId() {
		return empId;
	}
	public void setEmpId(int empId) {
		this.empId = empId;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getIfscCode() {
		return ifscCode;
	}
	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public short getAddUpdate() {
		return addUpdate;
	}
	public void setAddUpdate(short addUpdate) {
		this.addUpdate = addUpdate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Timestamp getRowUpdDate() {
		return rowUpdDate;
	}
	public void setRowUpdDate(Timestamp rowUpdDate) {
		this.rowUpdDate = rowUpdDate;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "employeeId:"+empId+" |  bankName:"+this.bankName+" | accountNo:"+this.accountNo 
				+" | ifscCode:"+ifscCode+" | addUpdate:"+addUpdate;
	}

	

}
