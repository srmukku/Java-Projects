package com.payroll.employee.dataobjects;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

public class Employee implements Serializable{
	
	private String firstName;
	private String lastName;
	private String middleName;
	private String designation;
	private String email;
	private String phone;
	private Date joiningDate;
	private Date lastWorkingDate;
	private String addressLine1;
	private String addressLine2;
	private String addressLine3;
	private String status;
	private String gender;
	private Timestamp rowUpdatedDate;
	private Date dob;
	private String contactNo;
	private String pan;
	private String adharNo;
	private String lastPromotionDate;
	private int departmentId;
	private int designationId;
	private int employeeId;
	
	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
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

	public Date getJoiningDate() {
		return joiningDate;
	}

	public void setJoiningDate(Date joiningDate) {
		this.joiningDate = joiningDate;
	}

	public Date getLastWorkingDate() {
		return lastWorkingDate;
	}

	public void setLastWorkingDate(Date lastWorkingDate) {
		this.lastWorkingDate = lastWorkingDate;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String address) {
		this.addressLine1 = address;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public String getPan() {
		return pan;
	}

	public void setPan(String pan) {
		this.pan = pan;
	}

	public String getAdharNo() {
		return adharNo;
	}

	public void setAdharNo(String adharNo) {
		this.adharNo = adharNo;
	}

	public String getLastPromotionDate() {
		return lastPromotionDate;
	}

	public void setLastPromotionDate(String lastPromotionDate) {
		this.lastPromotionDate = lastPromotionDate;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	
	/*public Employee(String firstName, String lastName, String middleName, String designation, 
			String email, String phone){
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.email = email;
		this.designation = designation;
		this.phone = phone;
		
	}*/
	
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public String getMiddleName() {
		return middleName;
	}
	public String getDesignation() {
		return designation;
	}
	public String getEmail() {
		return email;
	}
	public String getPhone() {
		return phone;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "FName:"+this.firstName+", lName:"+this.lastName+", desigId:"+this.designationId+", "
				+ "departId:"+this.departmentId+", email:"+this.email+", phone:"+this.phone;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getAddressLine3() {
		return addressLine3;
	}

	public void setAddressLine3(String addressLine3) {
		this.addressLine3 = addressLine3;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Timestamp getRowUpdatedDate() {
		return rowUpdatedDate;
	}

	public void setRowUpdatedDate(Timestamp rowUpdatedDate) {
		this.rowUpdatedDate = rowUpdatedDate;
	}
}
