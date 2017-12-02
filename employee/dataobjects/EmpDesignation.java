package com.payroll.employee.dataobjects;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

public class EmpDesignation implements Serializable{
	
	int empId;
	int designationId;
	Date startDate;
	Date lastWokingDate;
	private String status;
	private Timestamp rowUpdDate;
	
	public int getEmpId() {
		return empId;
	}
	public void setEmpId(int empId) {
		this.empId = empId;
	}
	public int getDesignationId() {
		return designationId;
	}
	public void setDesignationId(int designationId) {
		this.designationId = designationId;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getLastWokingDate() {
		return lastWokingDate;
	}
	public void setLastWokingDate(Date lastWokingDate) {
		this.lastWokingDate = lastWokingDate;
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
	
}
