package com.payroll.employee.business;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.payroll.Utils;
import com.payroll.employee.dataobjects.Employee;
import com.payroll.employee.dataobjects.EmployeeDAO;
import com.payroll.employee.vo.EmployeeVO;

public class EmployeeService {
	
	public List<EmployeeVO> getEmployees(){
		return new EmployeeDAO().getEmployees();
	}
	public List<EmployeeVO> getEmployees(int deptId, int desgId){
		return new EmployeeDAO().getEmployees(deptId, desgId);
	}
	
	public String addUpdateEmployee(com.payroll.employee.Employee emp){
		return new EmployeeDAO().addUpdateEmployee(copyEmp(emp));
	}
	public com.payroll.employee.Employee getEmployeeById(int empId){
		return copyDBEmp(new EmployeeDAO().getEmployeeById(empId));
	}
	public boolean deleteEmp(int empId){
		return new EmployeeDAO().deleteEmp(empId);
	}
	private Employee copyEmp(com.payroll.employee.Employee emp){
		Employee dbEmp = null;
		try{
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			dbEmp =  new Employee();
			dbEmp.setAddressLine1(emp.getAddressLine1());
			dbEmp.setAddressLine2(emp.getAddressLine2());
			dbEmp.setAddressLine3(emp.getAddressLine3());
			dbEmp.setAdharNo(emp.getAdharNo());
			dbEmp.setContactNo(emp.getContactNo());
			dbEmp.setDepartmentId(emp.getDepartmentId());
			dbEmp.setJoiningDate(!Utils.isEmpty(emp.getJoiningDate())? dateFormat.parse(emp.getJoiningDate()): 
				new Date());
			dbEmp.setDob(!Utils.isEmpty(emp.getDob())? dateFormat.parse(emp.getDob()):new Date());
			dbEmp.setEmail(emp.getEmail());
			dbEmp.setFirstName(emp.getFirstName());
			dbEmp.setLastName(emp.getLastName());
			dbEmp.setMiddleName(emp.getMiddleName());
			dbEmp.setPhone(emp.getPhone());
			dbEmp.setPan(emp.getPan());
			dbEmp.setEmployeeId(emp.getEmployeeId());
			dbEmp.setDesignationId(emp.getDesignationId());
			dbEmp.setGender(emp.getGender());
			//dbEmp.setRowUpdatedDate(new Date());
		}catch(Exception e){
			e.printStackTrace();
		}
		return dbEmp;
	}
	
	private com.payroll.employee.Employee copyDBEmp(EmployeeVO emp){
		com.payroll.employee.Employee dbEmp = null;
		try{
		//SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			dbEmp =  new com.payroll.employee.Employee();
			dbEmp.setAddressLine1(emp.getAddressLine1());
			dbEmp.setAddressLine2(emp.getAddressLine2());
			dbEmp.setAddressLine3(emp.getAddressLine3());
			dbEmp.setAdharNo(emp.getAdharNo());
			dbEmp.setContactNo(emp.getContactNo());
			dbEmp.setDepartmentId(emp.getDepartmentId());
			dbEmp.setJoiningDate(emp.getJoiningDate());
			dbEmp.setDob(emp.getDob());
			dbEmp.setEmail(emp.getEmail());
			dbEmp.setFirstName(emp.getFirstName());
			dbEmp.setLastName(emp.getLastName());
			dbEmp.setMiddleName(emp.getMiddleName());
			dbEmp.setPhone(emp.getPhone());
			dbEmp.setPan(emp.getPan());
			dbEmp.setEmployeeId(emp.getEmployeeId());
			dbEmp.setDesignationId(emp.getDesignationId());
			dbEmp.setGender(emp.getGender());
			System.out.println("headId:"+emp.getHeadId());
			dbEmp.setHeadId(emp.getHeadId());
		}catch(Exception e){
			e.printStackTrace();
		}
		return dbEmp;
	}
}
