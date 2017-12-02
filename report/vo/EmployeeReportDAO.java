package com.payroll.report.vo;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.payroll.HibernateConnection;
import com.payroll.report.vo.EmployeeReportVO;
import com.payroll.employee.salary.dataobjects.SalaryDAO;
import com.payroll.employee.salary.vo.SalaryVO;

public class EmployeeReportDAO {

	Session session = null;
	public List<EmployeeReportVO> getEmployees(){
		List<EmployeeReportVO> employeeList = null;
		Session session = null;
		
		try{
			//String queryString = " from Employee";
			/*String queryString = " select new com.payroll.report.vo.EmployeeReportVO(e.employeeId, e.firstName, e.lastName, e.middleName,"
					+ " e.email, e.phone, e.pan, e.adharNo, e.dob,(select dept.departmantName from Department dept "
					+ "where dept.departmentId = (select eDept.departmentId from EmpDepartment eDept where eDept.empId = e.employeeId)), "
					+ "(select desg.designationName from Designation desg where desg.designationId = "
					+ "(select eDesg.designationId from EmpDesignation eDesg where eDesg.empId = e.employeeId and eDesg.lastWokingDate is null)), "
					+ "e.addressLine1, e.addressLine2, e.addressLine3, e.gender, e.joiningDate) from Employee e where e.status= ?";	*/
			
			String queryString = " select new com.payroll.report.vo.EmployeeReportVO(e.employeeId, e.firstName, e.lastName, e.middleName,"
					+ " e.email, e.phone, e.pan, e.adharNo, e.dob,(select dept.departmantName from Department dept "
					+ "where dept.departmentId = (select eDept.departmentId from EmpDepartment eDept where eDept.empId = e.employeeId)), "
					+ "(select desg.designationName from Designation desg where desg.designationId = "
					+ "(select eDesg.designationId from EmpDesignation eDesg where eDesg.empId = e.employeeId and eDesg.lastWokingDate is null)), "
					+ "e.addressLine1, e.addressLine2, e.addressLine3, e.gender, e.joiningDate,"
					+ "(select h.headName from HeadInfo h where h.headId = (select dh.headId from DesignationHead dh where dh.headId = h.headId and "
					+ "dh.designationId = (select eDesg.designationId from EmpDesignation eDesg "
					+ "where eDesg.designationId = dh.designationId and eDesg.empId = e.employeeId and eDesg.lastWokingDate is null)))"
					+ ") from Employee e where e.status= ?";
			
			session = HibernateConnection.getSessionFactory().openSession();
			Query query = session.createQuery(queryString);
			query.setParameter(0, "A");
			//query.setParameter(1, "A");
			employeeList = query.list();
			
			for (EmployeeReportVO employeeVo: employeeList) {
				SalaryDAO dao = new SalaryDAO();
				employeeVo.setSalaryVo(dao.getEmpSalary(employeeVo.getEmployeeId()));
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			HibernateConnection.closeSession(session);
		}
		return employeeList;
	}
	
	public List<EmployeeReportVO> getEmployeesByDepartment(int deptId){
		List<EmployeeReportVO> employeeList = null;
		Session session = null;
		
		try{
			String queryString  = " select new com.payroll.report.vo.EmployeeReportVO(e.employeeId, e.firstName, e.lastName, e.middleName,"
					+ " e.email, e.phone, e.pan, e.adharNo, e.dob,(select dept.departmantName from Department dept "
					+ "where dept.departmentId = ?), "
					+ "(select desg.designationName from Designation desg where desg.designationId = "
					+ "(select eDesg.designationId from EmpDesignation eDesg where eDesg.empId = e.employeeId and eDesg.lastWokingDate is null)), "
					+ "e.addressLine1, e.addressLine2, e.addressLine3, e.gender, e.joiningDate," 
					+ "(select h.headName from HeadInfo h where h.headId = (select dh.headId from DesignationHead dh where dh.headId = h.headId and "
					+ "dh.designationId = (select eDesg.designationId from EmpDesignation eDesg "
					+ "where eDesg.designationId = dh.designationId and eDesg.empId = e.employeeId and eDesg.lastWokingDate is null)))"
					+ ") from Employee e where e.status= ? and "
					+ "e.employeeId = (select eDept.empId from EmpDepartment eDept where e.employeeId = eDept.empId and eDept.departmentId = ? )";
			
				session = HibernateConnection.getSessionFactory().openSession();
				Query query = session.createQuery(queryString);
				query.setParameter(0, deptId);
				query.setParameter(1, "A");
				query.setParameter(2, deptId);
				employeeList = query.list();
				
				for (EmployeeReportVO employeeVo: employeeList) {
					SalaryDAO dao = new SalaryDAO();
					employeeVo.setSalaryVo(dao.getEmpSalary(employeeVo.getEmployeeId()));
				}
				
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				HibernateConnection.closeSession(session);
			}
			return employeeList;
	}
	
	public List<EmployeeReportVO> getEmployeesByDesignation( int desgId){
		
		List<EmployeeReportVO> employeeList = null;
		Session session = null;
		
		try{
			String queryString  = " select new com.payroll.report.vo.EmployeeReportVO(e.employeeId, e.firstName, e.lastName, e.middleName,"
				+ " e.email, e.phone, e.pan, e.adharNo, e.dob,(select dept.departmantName from Department dept "
				+ "where dept.departmentId = (select eDept.departmentId from EmpDepartment eDept where eDept.empId = e.employeeId)), "
				+ "(select desg.designationName from Designation desg where desg.designationId = ?), "
				+ "e.addressLine1, e.addressLine2, e.addressLine3, e.gender, e.joiningDate," 
				+ "(select h.headName from HeadInfo h where h.headId = (select dh.headId from DesignationHead dh where dh.headId = h.headId and "
				+ "dh.designationId = (select eDesg.designationId from EmpDesignation eDesg "
				+ "where eDesg.designationId = dh.designationId and eDesg.empId = e.employeeId and eDesg.lastWokingDate is null)))"
				+ ") from Employee e where e.status= ? and "
				+ "e.employeeId = (select eDesg.empId from EmpDesignation eDesg where "
				+ "e.employeeId = eDesg.empId and eDesg.designationId = ? and eDesg.lastWokingDate is null)";
			
			session = HibernateConnection.getSessionFactory().openSession();
			Query query = session.createQuery(queryString);
			query.setParameter(0, desgId);
			query.setParameter(1, "A");
			query.setParameter(2, desgId);
			
			employeeList = query.list();
			for (EmployeeReportVO employeeVo: employeeList) {
				SalaryDAO dao = new SalaryDAO();
				employeeVo.setSalaryVo(dao.getEmpSalary(employeeVo.getEmployeeId()));
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			HibernateConnection.closeSession(session);
		}
		return employeeList;
		
	}
	
	public List<EmployeeReportVO> getEmployees(int deptId, int desgId){
		List<EmployeeReportVO> employeeList = null;
		Session session = null;
		
		try{
			String queryString = " select new com.payroll.report.vo.EmployeeReportVO(e.employeeId, e.firstName, e.lastName, e.middleName,"
					+ " e.email, e.phone, e.pan, e.adharNo, e.dob,(select dept.departmantName from Department dept "
					+ "where dept.departmentId = (select eDept.departmentId from EmpDepartment eDept where eDept.empId = e.employeeId)), "
					+ "(select desg.designationName from Designation desg where desg.designationId = "
					+ "(select eDesg.designationId from EmpDesignation eDesg where eDesg.empId = e.employeeId and eDesg.lastWokingDate is null)), "
					+ "e.addressLine1, e.addressLine2, e.addressLine3, e.gender) from Employee e where e.status= ? and "
					+ "e.employeeId = (select eDept.empId from EmpDepartment eDept where eDept.empId=e.employeeId and eDept.departmentId = ? ) and "
					+ "e.employeeId = (select eDesg.empId from EmpDesignation eDesg where eDesg.empId=e.employeeId and "
					+ " eDesg.designationId = ? and eDesg.lastWokingDate is null)";
			
			session = HibernateConnection.getSessionFactory().openSession();
			Query query = session.createQuery(queryString);
			query.setParameter(0, "A");
			query.setParameter(1, deptId);
			query.setParameter(2, desgId);
			
			employeeList = query.list();
			
			for (EmployeeReportVO employeeVo: employeeList) {
				SalaryDAO dao = new SalaryDAO();
				employeeVo.setSalaryVo(dao.getEmpSalary(employeeVo.getEmployeeId()));
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			HibernateConnection.closeSession(session);
		}
		return employeeList;
	}
}
