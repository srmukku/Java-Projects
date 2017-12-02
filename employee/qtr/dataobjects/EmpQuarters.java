package com.payroll.employee.qtr.dataobjects;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

public class EmpQuarters implements Serializable{
	
	private int empId;
	private Boolean afkQtr;
	private short addUpdate; // 0 - Add / 1 - update
	private String status;
	private Timestamp rowUpdDate;
	
	public int getEmpId() {
		return empId;
	}
	public void setEmpId(int empId) {
		this.empId = empId;
	}
	public Boolean getAfkQtr() {
		return afkQtr;
	}
	public void setAfkQtr(Boolean afk_qtr) {
		this.afkQtr = afk_qtr;
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
	

}
