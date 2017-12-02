package com.payroll.designation.dataobjects;

import java.io.Serializable;
import java.util.Date;

public class DesignationHead implements Serializable{
	private int designationId;
	private int headId;
	private Date startDate;
	
	
	public int getDesignationId() {
		return designationId;
	}
	public void setDesignationId(int designationId) {
		this.designationId = designationId;
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
