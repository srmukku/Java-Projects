package com.payroll.designation.vo;

public class DesignationVO {
	
	private int designationId;
	private int headId;
	private String description;
	private String designationName;
	private String headName;
	private String departmentName;
	private int departmentId;
	public DesignationVO(){
		
	}
	
	public DesignationVO(Integer designationId, Integer headId, String headName, 
			String departmentName, String designationName, String description){
		this.designationId = designationId;
		this.headId = headId;
		this.headName = headName;
		this.departmentName = departmentName;
		this.designationName = designationName;
		this.description = description;
	}
	
	public DesignationVO(Integer designationId, Integer headId,  
			int departmentId, String designationName, String description){
		this.designationId = designationId;
		this.headId = headId;
		this.departmentId = departmentId;
		this.designationName = designationName;
		this.description = description;
	}

	public DesignationVO(Integer designationId, String designationName){
		this.designationId = designationId;
		this.designationName = designationName;
	}
	
	public int getDesignationId() {
		return designationId;
	}

	public int getHeadId() {
		return headId;
	}

	public String getDescription() {
		return description;
	}

	public String getDesignationName() {
		return designationName;
	}

	public String getHeadName() {
		return headName;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public int getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}

	public void setDesignationId(int designationId) {
		this.designationId = designationId;
	}

	public void setHeadId(int headId) {
		this.headId = headId;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setDesignationName(String designationName) {
		this.designationName = designationName;
	}

	public void setHeadName(String headName) {
		this.headName = headName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

}
