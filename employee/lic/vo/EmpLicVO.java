package com.payroll.employee.lic.vo;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.payroll.Utils;

public class EmpLicVO {
	private int empId;
	private double instlmtAmt;
	private String policyNo;
	private String paymentDate;
	private String fullName;
	private short addUpdate; // 0 - Add / 1 - update
	private int designationId;
	private int departmentId;
	
	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	
	public EmpLicVO(){
		
	}
	
	public EmpLicVO(int empId, String fName, String lName, String policyNo, Date paymentDate, double instlmtAmt) {
		this.empId = empId;
		StringBuffer fullNameSB = new StringBuffer(fName);
		fullNameSB.append(" ");
		fullNameSB.append(Utils.safeTrim(lName));
		this.fullName = fullNameSB.toString();
		this.policyNo = policyNo;
		this.instlmtAmt = instlmtAmt;
		if(paymentDate != null)
			this.paymentDate = dateFormat.format(paymentDate);
	}
	public EmpLicVO(int empId, int departmentId, int designationId, String policyNo, Date paymentDate, double instlmtAmt) {
		this.empId = empId;
		this.policyNo = policyNo;
		this.departmentId = departmentId;
		this.designationId = designationId;
		this.instlmtAmt = instlmtAmt;
		if(paymentDate != null)
			this.paymentDate = dateFormat.format(paymentDate);
	}
	public int getEmpId() {
		return empId;
	}
	public double getInstlmtAmt() {
		return instlmtAmt;
	}
	public String getPolicyNo() {
		return policyNo;
	}
	public String getPaymentDate() {
		return paymentDate;
	}
	public String getFullName() {
		return fullName;
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

	public void setInstlmtAmt(double instlmtAmt) {
		this.instlmtAmt = instlmtAmt;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}

	public int getDesignationId() {
		return designationId;
	}

	public int getDepartmentId() {
		return departmentId;
	}
	
}
