package com.payroll.advance.vo;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.payroll.Utils;

public class Advance {
	
	private int advanceId;
	private int departmentId;
	private int designationId;
	private Double advanceAmount;
	private String paymentDate;
	private int empId;
	private String departmentName;
	private String designationName;
	private String fullName;
	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	
	public int getEmpId() {
		return empId;
	}
	public void setEmpId(int empId) {
		this.empId = empId;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public String getDesignationName() {
		return designationName;
	}
	public String getFullName() {
		return fullName;
	}
	public Advance(){
		
	}
	public Advance(int empId, String fName, String lName, int deptId, String deptName, 
			int desgId, String desgName, Date paymentDate, Double advanceAmount){
		this.empId = empId;
		this.departmentId = deptId;
		this.designationId = desgId;
		this.departmentName = deptName;
		this.designationName = desgName;
		this.advanceAmount = advanceAmount;
		if(paymentDate != null)
			this.paymentDate = dateFormat.format(paymentDate);
		StringBuffer fullNameSB = new StringBuffer(fName);
		fullNameSB.append(Utils.safeTrim(lName));
		this.fullName = fullNameSB.toString();
			
	}
	
	public String getPaymentDate() {
		return paymentDate;
	}
	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}
	public int getAdvanceId() {
		return advanceId;
	}
	public void setAdvanceId(int advanceId) {
		this.advanceId = advanceId;
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
	public Double getAdvanceAmount() {
		return advanceAmount;
	}
	public void setAdvanceAmount(Double advanceAmount) {
		this.advanceAmount = advanceAmount;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "EmpId |"+empId+"|DeptId|"+departmentId+"|DesgId|"+designationId+"|paymentDate|"+paymentDate
				+"advanceAmount|"+advanceAmount;
	}
}
