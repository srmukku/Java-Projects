package com.payroll.conveyance.dataobjects;

import java.io.Serializable;

public class Conveyance implements Serializable{
	
	private int conveyanceId;
	private int designationId;
	private Double conveyanceAmount;
	
	public int getConveyanceId() {
		return conveyanceId;
	}
	public void setConveyanceId(int conveyanceId) {
		this.conveyanceId = conveyanceId;
	}
	public int getDesignationId() {
		return designationId;
	}
	public void setDesignationId(int designationId) {
		this.designationId = designationId;
	}
	public Double getConveyanceAmount() {
		return conveyanceAmount;
	}
	public void setConveyanceAmount(Double conveyanceAmount) {
		this.conveyanceAmount = conveyanceAmount;
	}

	
}
