package com.payroll.employee.dataobjects;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.payroll.HibernateConnection;
import com.payroll.employee.vo.EmployeeVO;

public class EmployeeDAO {

	Session session = null;
	public List<EmployeeVO> getEmployees(){
		List<EmployeeVO> employeeList = null;
		Session session = null;
		
		try{
			//String queryString = " from Employee";
			String queryString = " select new com.payroll.employee.vo.EmployeeVO(e.employeeId, e.firstName, e.lastName, e.middleName,"
					+ " e.email, e.phone, e.pan, e.adharNo, e.dob,"
					+ "(select dept.departmantName from Department dept "
					+ "where dept.departmentId = (select eDept.departmentId from EmpDepartment eDept where eDept.empId = e.employeeId)),"
					+ "(select h.headName from HeadInfo h where h.headId = (select dh.headId from DesignationHead dh where dh.headId = h.headId and "
					+ "dh.designationId = "
					+ "(select eDesg.designationId from EmpDesignation eDesg where eDesg.designationId = dh.designationId and eDesg.empId = e.employeeId and eDesg.lastWokingDate is null))), "
					+ "(select desg.designationName from Designation desg where desg.designationId = "
					+ "(select eDesg.designationId from EmpDesignation eDesg where eDesg.empId = e.employeeId and eDesg.lastWokingDate is null)), "
					+ "e.addressLine1, e.addressLine2, e.addressLine3, e.gender, e.joiningDate) from Employee e where e.status= ?";		
			session = HibernateConnection.getSessionFactory().openSession();
			Query query = session.createQuery(queryString);
			query.setParameter(0, "A");
			employeeList = query.list();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			HibernateConnection.closeSession(session);
		}
		return employeeList;
	}
	
	public List<EmployeeVO> getEmployees(int deptId, int desgId){
		List<EmployeeVO> employeeList = null;
		Session session = null;
		
		try{
			//String queryString = " from Employee";
			String queryString = " select new com.payroll.employee.vo.EmployeeVO(e.employeeId, e.firstName, "
					+ "e.lastName, e.middleName) from Employee e where e.status = ? and e.employeeId = "
					+ "(select eDept.empId from EmpDepartment eDept where eDept.empId=e.employeeId and eDept.departmentId = ? ) and "
					+ "e.employeeId = (select eDesg.empId from EmpDesignation eDesg where eDesg.empId=e.employeeId and "
					+ " eDesg.designationId = ? and eDesg.lastWokingDate is null)";		
			session = HibernateConnection.getSessionFactory().openSession();
			Query query = session.createQuery(queryString);
			query.setParameter(0, "A");
			query.setParameter(1, deptId);
			query.setParameter(2, desgId);
			employeeList = query.list();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			HibernateConnection.closeSession(session);
		}
		return employeeList;
	}
	
	public EmployeeVO getEmployeeById(int empId){
		//Session session = null;
		EmployeeVO employee = null;
		try{
			//String queryString = " from Employee e where e.employeeId = ?";
			String queryString = " select new com.payroll.employee.vo.EmployeeVO(e.employeeId, e.firstName, e.lastName, e.middleName,"
					+ " e.email, e.phone, e.pan, e.adharNo, e.dob,(select dept.departmentId from Department dept "
					+ "where dept.departmentId = (select eDept.departmentId from EmpDepartment eDept where eDept.empId = e.employeeId)), "
					+ "(select dh.headId from DesignationHead dh where dh.designationId = "
					+ "(select eDesg.designationId from EmpDesignation eDesg where eDesg.empId = e.employeeId and eDesg.lastWokingDate is null)), "
					+ "(select desg.designationId from Designation desg where desg.designationId = "
					+ "(select eDesg.designationId from EmpDesignation eDesg where eDesg.empId = e.employeeId and eDesg.lastWokingDate is null)), "
					+ "e.addressLine1, e.addressLine2, e.addressLine3, e.gender, e.joiningDate) from Employee e where e.employeeId = ? and e.status = ?";		
			if(session == null || !session.isOpen()) 
				session = HibernateConnection.getSessionFactory().openSession();
			Query query = session.createQuery(queryString);
			query.setParameter(0, empId);
			query.setParameter(1, "A");
			employee = (EmployeeVO)(!(query.list().isEmpty()) ? query.list().get(0) : null);
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			HibernateConnection.closeSession(session);
		}
		return employee;
	}
	
	public EmpDesignation getEmpDesignationByIds(int empId, int desgId, Session session){
		EmpDesignation empDesig = null;
		try{
			String queryString = " from EmpDesignation ed where ed.empId = ? and ed.designationId = ? and ed.status = ?";
			session = HibernateConnection.getSessionFactory().openSession();
			Query query = session.createQuery(queryString);
			query.setParameter(0, empId);
			query.setParameter(1, desgId);
			query.setParameter(2, "A");
			empDesig = (EmpDesignation)((!query.list().isEmpty())? query.list().get(0) : null);
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			HibernateConnection.closeSession(session);
		}
		return empDesig;
	}
	
	private EmployeeVO getEmployeeById(int empId, Session session){
		this.session = session;
		return getEmployeeById(empId);
	}
	
	public boolean deleteEmp(int empId){
		boolean success = false;
		Session session = null;
		Transaction transaction = null;
		try{
			session = HibernateConnection.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			if(empId != 0){
				Query query = session.createQuery("Update Employee e set e.status = ?, e.rowUpdatedDate = ? where e.employeeId = ?");
				query.setParameter(0, "S");
				query.setParameter(1, new Date());
				query.setParameter(2, empId);
				int updated = query.executeUpdate();
				System.out.println("Deleted:"+updated);
				if(updated == 1){
					EmployeeVO empDB = getEmployeeById(empId, session);
					query = session.createQuery("Update EmpDesignation eDesg set eDesg.status = ?, eDesg.rowUpdatedDate = ? where e.empId = ? and eDesg.designationId = ?");
					query.setParameter(0, "S");
					query.setParameter(1, new Timestamp(System.currentTimeMillis()));
					query.setParameter(2, empId);
					query.setParameter(3, empDB.getDesignationId());
					updated = query.executeUpdate();
					query = session.createQuery("Update EmpDepartment eDept set eDept.status = ?, eDept.rowUpdatedDate = ? where eDept.empId = ? and eDept.departmentId = ?");
					query.setParameter(0, "S");
					query.setParameter(1, new Timestamp(System.currentTimeMillis()));
					query.setParameter(2, empId);
					query.setParameter(3, empDB.getDepartmentId());
					updated = query.executeUpdate();
					transaction.commit();
					success = true;
				}
			}
			transaction.commit();
			success = true;
		}catch(Exception e){
			e.printStackTrace();
			transaction.rollback();
			success = false;
		}finally {
			HibernateConnection.closeSession(session);
		}
		return success;
	}
	
	public String addUpdateEmployee(Employee emp){
		String result = null;
		Session session = null;
		Transaction transaction = null;
		try{
			session = HibernateConnection.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			if(emp.getEmployeeId() != 0) {
				EmployeeVO empDB = getEmployeeById(emp.getEmployeeId());
				if(empDB.getDepartmentId() != emp.getDepartmentId()){
					EmpDepartment empDept = new EmpDepartment();
					empDept.setDepartmentId(emp.getDepartmentId());
					empDept.setEmpId(emp.getEmployeeId());
					empDept.setStartDate(new Date());
					empDept.setStatus("A");
					empDept.setRowUpdDate(new Timestamp(System.currentTimeMillis()));
					session.save(empDept);
				}
				if(empDB.getDesignationId() != emp.getDesignationId()){
					// Updating Last working day for Employee designation
					EmpDesignation empDesgDB = getEmpDesignationByIds(emp.getEmployeeId(), empDB.getDesignationId(), session);
					empDesgDB.setLastWokingDate(new Date());
					empDesgDB.setRowUpdDate(new Timestamp(System.currentTimeMillis()));
					session.update(empDesgDB);
					// Adding new designation for Employee
					EmpDesignation empDesg = new EmpDesignation();
					empDesg.setDesignationId(emp.getDesignationId());
					empDesg.setEmpId(emp.getEmployeeId());
					empDesg.setStartDate(new Date());
					empDesg.setStatus("A");
					empDesg.setRowUpdDate(new Timestamp(System.currentTimeMillis()));
					session.save(empDesg);
				}
				emp.setStatus("A");
				emp.setRowUpdatedDate(new Timestamp(System.currentTimeMillis()));
				session.update(emp);
				
			}else {
				emp.setEmployeeId(getMaxEmpId(session));
				emp.setStatus("A");
				emp.setRowUpdatedDate(new Timestamp(System.currentTimeMillis()));
				session.save(emp);
				//Inserting Employee Department:
				EmpDepartment empDept = new EmpDepartment();
				empDept.setDepartmentId(emp.getDepartmentId());
				empDept.setEmpId(emp.getEmployeeId());
				empDept.setStartDate(new Date());
				empDept.setStatus("A");
				empDept.setRowUpdDate(new Timestamp(System.currentTimeMillis()));
				
				session.save(empDept);
				//Inserting Employee Designation:
				EmpDesignation empDesg = new EmpDesignation();
				empDesg.setDesignationId(emp.getDesignationId());
				empDesg.setEmpId(emp.getEmployeeId());
				empDesg.setStartDate(new Date());
				empDesg.setStatus("A");
				empDesg.setRowUpdDate(new Timestamp(System.currentTimeMillis()));
				
				session.save(empDesg);
			}
			transaction.commit();
			result = "Yes";
		}catch(Exception e){
			e.printStackTrace();
			transaction.rollback();
			result = "Unable Add/Update Employee!";
		}finally {
			HibernateConnection.closeSession(session);
		}
		return result;
	}
	
	private int getMaxEmpId(Session session){
		int maxEmpId = 0;
		//Session session = null;
		try{
			if(session == null)
				session = HibernateConnection.getSessionFactory().openSession();
			Employee emp = (Employee)session.createQuery("select e from Employee e order by e.employeeId desc").setMaxResults(1).uniqueResult();
			int empId = (emp != null) ? emp.getEmployeeId() : 0;
			maxEmpId = empId + 1;
			System.out.println("maxEmpId:"+maxEmpId);
		}catch(Exception e){
			e.printStackTrace();
			maxEmpId = 0;
		}/*finally {
			HibernateConnection.closeSession(session);
		}*/
		return maxEmpId;
	}
	
}
