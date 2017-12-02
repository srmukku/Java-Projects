package com.payroll.headInfo.dataobjects;

import java.io.Serializable;
import java.util.Date;

public class HeadDepartment implements Serializable{
	private int departmentId;
	private int headId;
	private Date startDate;
	public int getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}
	public int getHeadId() {
		return headId;
	}
	public void setHeadId(int headId) {
		this.headId = headId;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
}
