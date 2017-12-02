package com.payroll.employee.pf.dataobjects;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

public class EmpPf implements Serializable{
	
	private int empId;
	private double pfsCpfCntrbn;
	private double pfLoneRecAmt;
	private double cfLoneRecAmt;
	private double apfAcpfCntrbn;
	private Date pfDate;
	private String status;
	private short addUpdate; // 0 - Add / 1 - update
	private Timestamp rowUpdDate;
	
	
	public int getEmpId() {
		return empId;
	}
	public void setEmpId(int empId) {
		this.empId = empId;
	}
	public double getPfsCpfCntrbn() {
		return pfsCpfCntrbn;
	}
	public void setPfsCpfCntrbn(double pfsCpfCntrbn) {
		this.pfsCpfCntrbn = pfsCpfCntrbn;
	}
	public double getPfLoneRecAmt() {
		return pfLoneRecAmt;
	}
	public void setPfLoneRecAmt(double pfLoneRecAmt) {
		this.pfLoneRecAmt = pfLoneRecAmt;
	}
	public double getCfLoneRecAmt() {
		return cfLoneRecAmt;
	}
	public void setCfLoneRecAmt(double cfLoneRecAmt) {
		this.cfLoneRecAmt = cfLoneRecAmt;
	}
	public double getApfAcpfCntrbn() {
		return apfAcpfCntrbn;
	}
	public void setApfAcpfCntrbn(double apfAcpfCntrbn) {
		this.apfAcpfCntrbn = apfAcpfCntrbn;
	}
	public Date getPfDate() {
		return pfDate;
	}
	public void setPfDate(Date pfDate) {
		this.pfDate = pfDate;
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
	public short getAddUpdate() {
		return addUpdate;
	}
	public void setAddUpdate(short addUpdate) {
		this.addUpdate = addUpdate;
	}
	

}
