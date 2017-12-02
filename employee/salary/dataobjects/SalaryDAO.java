package com.payroll.employee.salary.dataobjects;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import com.payroll.HibernateConnection;
import com.payroll.employee.salary.vo.SalaryVO;

public class SalaryDAO {
	
	public List<com.payroll.employee.salary.vo.SalaryVO> getSalaries(){
		List<com.payroll.employee.salary.vo.SalaryVO> salaries = null;
			Session session = null;
			
			try{
				String queryString = " select new com.payroll.employee.salary.vo.SalaryVO(s.empId, (select e.firstName from Employee e where e.employeeId = s.empId),"
						+ " (select e.lastName from Employee e where e.employeeId = s.empId), "
						+ "s.year, s.basic, s.gradePay, s.scalePay, s.scaleInc) from Salary s where s.status = ?";		
				
				session = HibernateConnection.getSessionFactory().openSession();
				Query query = session.createQuery(queryString);
				query.setParameter(0, "A");
				salaries = query.list();
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				HibernateConnection.closeSession(session);
			}
		
		return salaries;
	}
	
	public String addUpdateSalary(Salary salary){
		String result = null;
		Session session = null;
		Transaction transaction = null;
		try{
			session = HibernateConnection.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			Salary salayDB = checkEmpSalary(salary.getEmpId(), session);
			if(salayDB != null){
				if(salary.getAddUpdate() ==0){
					result = "Salary for selected employee is exist!";
					return result;
				}
				salayDB.setBasic(salary.getBasic());
				salayDB.setGradePay(salary.getGradePay());
				salayDB.setScaleInc(salary.getScaleInc());
				salayDB.setScalePay(salary.getScalePay());
				salayDB.setYear(salary.getYear());
				salayDB.setRowUpdDate(new Timestamp(System.currentTimeMillis()));
				session.update(salayDB);
			}
			else {
				salary.setRowUpdDate(new Timestamp(System.currentTimeMillis()));
				salary.setStatus("A");
				session.save(salary);
			}
			transaction.commit();
			result = "Yes";
		}catch(Exception e){
			e.printStackTrace();
			transaction.rollback();
			result = "Unable to Add/Update Salary for selected employee";
		}finally {
			HibernateConnection.closeSession(session);
		}
		return result;
	}
	
	public SalaryVO getEmpSalary(int empId){
		SalaryVO salVO = null;
		Session session = null;
		try{
			String queryString = " select new com.payroll.employee.salary.vo.SalaryVO(s.empId, "+
					"(select dept.departmentId from Department dept where dept.departmentId = (select eDept.departmentId "
					+ "from EmpDepartment eDept where eDept.empId = s.empId)), (select desg.designationId "
					+ "from Designation desg where desg.designationId = (select eDesg.designationId from EmpDesignation eDesg "
					+ "where eDesg.empId = s.empId and eDesg.lastWokingDate is null)), "
					+ "(select dh.headId from DesignationHead dh where dh.designationId = "
					+ "(select eDesg.designationId from EmpDesignation eDesg where eDesg.empId = s.empId and eDesg.lastWokingDate is null)), "
					+ "s.year, s.basic, s.gradePay, s.scalePay, s.scaleInc) from Salary s where s.empId = ?";		
			
			session = HibernateConnection.getSessionFactory().openSession();
			Query query = session.createQuery(queryString);
			query.setParameter(0, empId);
			salVO = (SalaryVO)(!(query.list().isEmpty()) ? query.list().get(0) : null);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			HibernateConnection.closeSession(session);
		}
		return salVO;
	}
	
	public String deleteEmpSal(int empId){
		String result = null;
		Session session = null;
		try{
			session = HibernateConnection.getSessionFactory().openSession();
			Query query = session.createQuery("update Salary s set s.status = ?, s.rowUpdDate = ? where s.empId = ?");
			query.setParameter(0, "S");
			query.setParameter(1, new Date());
			query.setParameter(2, empId);
			int updated = query.executeUpdate();
			if(updated > 0)
				result = "Successfully deleted Salary!";
		}catch(Exception e){
			e.printStackTrace();
			result = "Failed deleted Salary!";
		}finally{
			HibernateConnection.closeSession(session);
		}
		return result;
	}
	private Salary checkEmpSalary(int empId, Session session){
		Salary salary = null;
		try{
			if(session == null)
				session = HibernateConnection.getSessionFactory().openSession();
			Query query = session.createQuery("select s from Salary s where s.empId = ? and s.status = ?");
			//.setMaxResults(1).uniqueResult();
			query.setParameter(0, empId);
			query.setParameter(1, "A");
			if(query.list() !=null && !query.list().isEmpty() )
				salary = (Salary)query.list().get(0);
		}catch(Exception e){
			e.printStackTrace();
		
		}
		return salary;
	}
	
}
