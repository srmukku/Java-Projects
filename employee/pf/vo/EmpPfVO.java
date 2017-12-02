package com.payroll.employee.pf.vo;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.payroll.Utils;

public class EmpPfVO {
	
	private int empId;
	private double pfsCpfCntrbn;
	private double pfLoneRecAmt;
	private double cfLoneRecAmt;
	private double apfAcpfCntrbn;
	private String pfDate;
	private String fullName;
	private int designationId;
	private int departmentId;
	private short addUpdate; // 0 - Add / 1 - update
	
	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	public EmpPfVO(){
		
	}
	public EmpPfVO(int empId, String fName, String lName, Date pfDate, double pfsCpfCntrbn, double pfLoneRecAmt, 
			double cfLoneRecAmt, double apfAcpfCntrbn){
		this.empId =empId;
		this.pfLoneRecAmt = pfLoneRecAmt;
		this.pfsCpfCntrbn = pfsCpfCntrbn;
		this.cfLoneRecAmt = cfLoneRecAmt;
		this.apfAcpfCntrbn = apfAcpfCntrbn;
		StringBuffer fullNameSB = new StringBuffer(fName);
		fullNameSB.append(" ");
		fullNameSB.append(Utils.safeTrim(lName));
		this.fullName = fullNameSB.toString();
		
		if(pfDate != null)
			this.pfDate = dateFormat.format(pfDate);
	}
	
	public EmpPfVO(int empId, int deptId, int desgId, Date pfDate, double pfsCpfCntrbn, double pfLoneRecAmt, 
			double cfLoneRecAmt, double apfAcpfCntrbn){
		this.empId =empId;
		this.pfLoneRecAmt = pfLoneRecAmt;
		this.pfsCpfCntrbn = pfsCpfCntrbn;
		this.cfLoneRecAmt = cfLoneRecAmt;
		this.apfAcpfCntrbn = apfAcpfCntrbn;
		this.designationId = desgId;
		this.departmentId = deptId;
		if(pfDate != null)
			this.pfDate = dateFormat.format(pfDate);
	}
	
	
	public int getEmpId() {
		return empId;
	}
	public double getPfsCpfCntrbn() {
		return pfsCpfCntrbn;
	}
	public double getPfLoneRecAmt() {
		return pfLoneRecAmt;
	}
	public double getCfLoneRecAmt() {
		return cfLoneRecAmt;
	}
	public double getApfAcpfCntrbn() {
		return apfAcpfCntrbn;
	}
	public String getPfDate() {
		return pfDate;
	}
	public String getFullName() {
		return fullName;
	}
	public int getDesignationId() {
		return designationId;
	}
	public void setDesignationId(int designationId) {
		this.designationId = designationId;
	}
	public int getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}
	public SimpleDateFormat getDateFormat() {
		return dateFormat;
	}
	public void setDateFormat(SimpleDateFormat dateFormat) {
		this.dateFormat = dateFormat;
	}
	public void setEmpId(int empId) {
		this.empId = empId;
	}
	public void setPfsCpfCntrbn(double pfsCpfCntrbn) {
		this.pfsCpfCntrbn = pfsCpfCntrbn;
	}
	public void setPfLoneRecAmt(double pfLoneRecAmt) {
		this.pfLoneRecAmt = pfLoneRecAmt;
	}
	public void setCfLoneRecAmt(double cfLoneRecAmt) {
		this.cfLoneRecAmt = cfLoneRecAmt;
	}
	public void setApfAcpfCntrbn(double apfAcpfCntrbn) {
		this.apfAcpfCntrbn = apfAcpfCntrbn;
	}
	public void setPfDate(String pfDate) {
		this.pfDate = pfDate;
	}
	public short getAddUpdate() {
		return addUpdate;
	}
	public void setAddUpdate(short addUpdate) {
		this.addUpdate = addUpdate;
	}
	

}
