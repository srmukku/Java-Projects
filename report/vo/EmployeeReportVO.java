package com.payroll.report.vo;


import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.payroll.Utils;
import com.payroll.employee.salary.vo.SalaryVO;

public class EmployeeReportVO implements Serializable{
	
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
	private String headName;
	private Date rowUpdatedDate;
	private SalaryVO salaryVo;
	
	public EmployeeReportVO(){
		
	}
	public String getFullName() {
		return fullName;
	}
	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	
	public EmployeeReportVO (int employeeId, String firstName, String lastName, String middleName){
		this.employeeId = employeeId;
		this.fullName = getName(firstName, middleName, lastName);
	}
	
	public EmployeeReportVO(int employeeId, String firstName, String lastName, String middleName,
			String email, String phone, String pan, String aadhar, Date dob, String department,
			String designation, String addressLine1, String addressLine2, String addressLine3, 
			String gender, Date joiningDate, String head){
		this.employeeId = employeeId;
		
		this.email = getValue(email);
		this.pan = getValue(pan);
		this.adharNo = getValue(aadhar);
		this.phone = getValue(phone);
		this.firstName = getValue(firstName);
		this.middleName = getValue(middleName);
		this.lastName = getValue(lastName);
		this.designation = getValue(designation);
		this.department = getValue(department);
		this.dob = (dob != null) ? dateFormat.format(dob) : "";
		this.addressLine1 = getValue(addressLine1);
		this.addressLine2 = getValue(addressLine2);
		this.addressLine3 = getValue(addressLine3);
		this.address = addressLine1 + " " + addressLine2 + " " + addressLine3;
		this.gender = getValue(gender); 
		this.fullName = getName(firstName, middleName, lastName);
		this.joiningDate = (joiningDate != null) ? dateFormat.format(joiningDate) : "";
		this.headName = getValue(head);
	}
		
	public EmployeeReportVO(int employeeId, String firstName, String lastName, String middleName,
			String email, String phone, String pan, String aadhar, Date dob, String department,
			String designation, String addressLine1, String addressLine2, String addressLine3, 
			String gender, Date joiningDate, SalaryVO salVo){
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
		this.addressLine1 = getValue(addressLine1);
		this.addressLine2 = getValue(addressLine2);
		this.addressLine3 = getValue(addressLine3);
		this.address = addressLine1 + addressLine2 + addressLine3;
		this.gender = gender; 
		this.fullName = getName(firstName, middleName, lastName);
		this.joiningDate = (joiningDate != null) ? dateFormat.format(joiningDate) : "";
		this.salaryVo = salVo;
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
	 
	public String getValue(String val) {
	   return (val == null) ? "" : val.trim();
	}
	public SalaryVO getSalaryVo() {
		return salaryVo;
	}
	public void setSalaryVo(SalaryVO salaryVo) {
		this.salaryVo = salaryVo;
	}
	public String getHeadName() {
		return headName;
	}
	public void setHeadName(String headName) {
		this.headName = headName;
	}
}

