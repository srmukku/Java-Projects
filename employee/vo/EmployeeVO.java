package com.payroll.employee.vo;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.payroll.Utils;

public class EmployeeVO {
	
	private String firstName;
	private String lastName;
	private String middleName;
	private String designation;
	private String email;
	private String phone;
	private String joiningDate;
	private String lastWorkingDate;
	private String address;
	private String dob;
	private String contactNo;
	private String pan;
	private String adharNo;
	private String lastPromotionDate;
	private int departmentId;
	private int designationId;
	private int employeeId;
	private String department;
	private String fullName;
	
	private String addressLine1;
	private String addressLine2;
	private String addressLine3;
	private String status;
	private String gender;
	private Date rowUpdatedDate;
	private String headName;
	private int headId;
	
	public EmployeeVO(){
		
	}
	public String getFullName() {
		return fullName;
	}
	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	
	public EmployeeVO (int employeeId, String firstName, String lastName, String middleName){
		this.employeeId = employeeId;
		this.fullName = getName(firstName, middleName, lastName);
	}
	
	public EmployeeVO(int employeeId, String firstName, String lastName, String middleName,
			String email, String phone, String pan, String aadhar, Date dob, String department,
			String headName, String designation, String addressLine1, String addressLine2, 
			String addressLine3, String gender, Date joiningDate){
		this.employeeId = employeeId;
		
		this.email = email;
		this.pan = pan;
		this.adharNo = aadhar;
		this.phone = phone;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.designation = designation;
		this.department = department;
		this.dob = (dob != null) ? dateFormat.format(dob) : "";
		this.addressLine1 = addressLine1;
		this.addressLine2 = addressLine2;
		this.addressLine3 = addressLine3;
		this.gender = gender; 
		this.fullName = getName(firstName, middleName, lastName);
		this.headName = headName;
		this.joiningDate = (joiningDate != null) ? dateFormat.format(joiningDate) : "";
	}
	
	public EmployeeVO(int employeeId, String firstName, String lastName, String middleName,
			String email, String phone, String pan, String aadhar, Date dob, int departmentId,
			int headId, int designationId, String addressLine1, String addressLine2, String addressLine3, 
			String gender, Date joiningDate){
		this.employeeId = employeeId;
		
		this.email = email;
		this.pan = pan;
		this.adharNo = aadhar;
		this.phone = phone;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.designationId = designationId;
		this.departmentId = departmentId;
		this.dob = (dob != null) ? dateFormat.format(dob) : "";
		this.addressLine1 = addressLine1;
		this.addressLine2 = addressLine2;
		this.addressLine3 = addressLine3;
		this.gender = gender; 
		//this.fullName = firstName, middleName, lastName);
		this.firstName = firstName;
		this.lastName = lastName;
		this.middleName = middleName;
		this.headId = headId;
		this.joiningDate = (joiningDate != null) ? dateFormat.format(joiningDate) : "";
	}
	
	
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
	public String getJoiningDate() {
		return joiningDate;
	}
	public String getLastWorkingDate() {
		return lastWorkingDate;
	}
	public String getAddress() {
		return address;
	}
	public String getDob() {
		return dob;
	}
	public String getContactNo() {
		return contactNo;
	}
	public String getPan() {
		return pan;
	}
	public String getAdharNo() {
		return adharNo;
	}
	public String getLastPromotionDate() {
		return lastPromotionDate;
	}
	public int getDepartmentId() {
		return departmentId;
	}
	public int getDesignationId() {
		return designationId;
	}
	public int getEmployeeId() {
		return employeeId;
	}
	public String getDepartment() {
		return department;
	}
	public SimpleDateFormat getDateFormat() {
		return dateFormat;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public String getAddressLine3() {
		return addressLine3;
	}

	public String getStatus() {
		return status;
	}

	public String getGender() {
		return gender;
	}

	public Date getRowUpdatedDate() {
		return rowUpdatedDate;
	}
	
	private String getName(String fName, String mName, String lName){
		StringBuffer fullNameSB = new StringBuffer(fName);
		mName = Utils.safeTrim(mName);
		if(!mName.equals(""))
			fullNameSB.append(" ");
		fullNameSB.append(Utils.safeTrim(mName)).append(" ").append(Utils.safeTrim(lName));
		return fullNameSB.toString();
	}
	public String getHeadName() {
		return headName;
	}
	public int getHeadId() {
		return headId;
	}
}
