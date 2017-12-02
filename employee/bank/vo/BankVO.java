package com.payroll.employee.bank.vo;

import java.util.Date;

import com.payroll.Utils;

public class BankVO {
	
	private int empId;
	private String bankName;
	private String ifscCode;
	private String accountNo;
	private String fullName;
	private int departmentId;
	private int designationId;
	private int headId;
	
	public BankVO() {
		
	}
	
	public BankVO(int empId, String fName, String lName, String ifscCode, String accountNo, String bankName) {
		this.empId = empId;
		StringBuffer fullNameSB = new StringBuffer(fName);
		fullNameSB.append(" ");
		fullNameSB.append(Utils.safeTrim(lName));
		this.fullName = fullNameSB.toString();
		this.ifscCode = ifscCode;
		this.bankName = bankName;
		this.accountNo = accountNo;
	}
	
	public BankVO(int empId, int deptId, int desgId, int headId, String ifscCode, String accountNo, String bankName) {
		this.empId = empId;
		this.ifscCode = ifscCode;
		this.bankName = bankName;
		this.accountNo = accountNo;
		this.departmentId = deptId;
		this.designationId = desgId;
		this.headId = headId;
	}
	public int getEmpId() {
		return empId;
	}
	public String getBankName() {
		return bankName;
	}
	public String getIfscCode() {
		return ifscCode;
	}
	public String getAccountNo() {
		return accountNo;
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

	public int getHeadId() {
		return headId;
	}
	

}
