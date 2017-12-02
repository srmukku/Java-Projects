package com.payroll.employee.qtr.dataobjects;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.payroll.HibernateConnection;
import com.payroll.employee.qtr.vo.EmpQuartersVO;

public class EmpQuartersDAO {
	public List<EmpQuartersVO> getEmpOtrList(){
		List<EmpQuartersVO> qtrList = null;
			Session session = null;
			
			try{
				String queryString = " select new com.payroll.employee.qtr.vo.EmpQuartersVO(q.empId, (select e.firstName from Employee e where e.employeeId = q.empId),"
						+ " (select e.lastName from Employee e where e.employeeId = q.empId), "
						+ "q.afkQtr) from EmpQuarters q where q.status = ?";		
				
				session = HibernateConnection.getSessionFactory().openSession();
				Query query = session.createQuery(queryString);
				query.setParameter(0, "A");
				qtrList = query.list();
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				HibernateConnection.closeSession(session);
			}
		
		return qtrList;
	}
	
	public EmpQuartersVO getEmpQtrById(int empId){
		EmpQuartersVO empQtr = null;
		Session session = null;
			
			try{
				String queryString = " select new com.payroll.employee.qtr.vo.EmpQuartersVO(q.empId, q.afkQtr, "
						+ "(select dept.departmentId from Department dept where dept.departmentId = (select eDept.departmentId "
						+ "from EmpDepartment eDept where eDept.empId = q.empId)), (select desg.designationId "
						+ "from Designation desg where desg.designationId = (select eDesg.designationId from EmpDesignation eDesg "
						+ "where eDesg.empId = q.empId and eDesg.lastWokingDate is null)))"
						+ "from EmpQuarters q where q.empId = ? and q.status= ?";		
				
				session = HibernateConnection.getSessionFactory().openSession();
				Query query = session.createQuery(queryString);
				query.setParameter(0, empId);
				query.setParameter(1, "A");
				empQtr = (EmpQuartersVO)(!(query.list().isEmpty())?query.list().get(0):null);
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				HibernateConnection.closeSession(session);
			}
		return empQtr;
	}
	
	public String addUpdateEmpQtr(EmpQuarters empQtr){
		String result = null;
		Session session = null;
		Transaction transaction = null;
		try{
			session = HibernateConnection.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			EmpQuarters empQtrDB = checkEmpQtr(empQtr.getEmpId(), session);
			if(empQtrDB != null){
				if(empQtr.getAddUpdate() ==0){
					result = "Quarters for selected employee is already added!";
					return result;
				}
				empQtrDB.setAfkQtr(empQtr.getAfkQtr());
				empQtr.setRowUpdDate(new Timestamp(System.currentTimeMillis()));
				session.update(empQtrDB);
			}
			else {
				empQtr.setStatus("A");
				empQtr.setRowUpdDate(new Timestamp(System.currentTimeMillis()));
				session.save(empQtr);
			}
			transaction.commit();
			result = "Yes";
		}catch(Exception e){
			e.printStackTrace();
			transaction.rollback();
			result = "Failed to add/update Quartes for selected employee";
		}finally {
			HibernateConnection.closeSession(session);
		}
		return result;
	}

	private EmpQuarters checkEmpQtr(int empId, Session session){
		EmpQuarters empQtr = null;
		try{
			if(session == null)
				session = HibernateConnection.getSessionFactory().openSession();
			Query query = session.createQuery(" from EmpQuarters q where q.empId = ? and q.status = ?");
			//.setMaxResults(1).uniqueResult();
			query.setParameter(0, empId);
			query.setParameter(1, "A");
			if(query.list() !=null && !query.list().isEmpty() )
				empQtr = (EmpQuarters)query.list().get(0);
		}catch(Exception e){
			e.printStackTrace();
		
		}
		return empQtr;
	}
	
	public String deleteEmpQtr(int empId){
		String result = null;
		Session session = null;
		try{
			session = HibernateConnection.getSessionFactory().openSession();
			Query query = session.createQuery("update EmpQuarters q set q.status = ?, q.rowUpdDate = ? where q.empId = ?");
			query.setParameter(0, "S");
			query.setParameter(1, new Date());
			query.setParameter(2, empId);
			int updated = query.executeUpdate();
			if(updated > 0)
				result = "Successfully deleted Quarters details!";
		}catch(Exception e){
			e.printStackTrace();
			result = "Failed to delete Quarters details!";
		}finally{
			HibernateConnection.closeSession(session);
		}
		return result;
	}
	

}
