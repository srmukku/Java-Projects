package com.payroll.employee.pf.dataobjects;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.payroll.HibernateConnection;
import com.payroll.employee.bank.dataobjects.Bank;
import com.payroll.employee.pf.vo.EmpPfVO;

public class EmpPfDAO {
	
	public List<EmpPfVO> getEmpPfList(){
		List<EmpPfVO> pfList = null;
			Session session = null;
			
			try{
				String queryString = " select new com.payroll.employee.pf.vo.EmpPfVO(p.empId, (select e.firstName from Employee e where e.employeeId = p.empId),"
						+ " (select e.lastName from Employee e where e.employeeId = p.empId), "
						+ "p.pfDate, p.pfsCpfCntrbn, p.pfLoneRecAmt, p.cfLoneRecAmt, p.apfAcpfCntrbn) from EmpPf p where p.status = ?";		
				
				session = HibernateConnection.getSessionFactory().openSession();
				Query query = session.createQuery(queryString);
				query.setParameter(0, "A");
				pfList = query.list();
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				HibernateConnection.closeSession(session);
			}
		
		return pfList;
	}
	
	public EmpPfVO getEmpPfById(int empId){
		EmpPfVO empPf = null;
		Session session = null;
			
			try{
				String queryString = " select new com.payroll.employee.pf.vo.EmpPfVO(p.empId, "
						+ "(select dept.departmentId from Department dept where dept.departmentId = (select eDept.departmentId "
						+ "from EmpDepartment eDept where eDept.empId = p.empId)), (select desg.designationId "
						+ "from Designation desg where desg.designationId = (select eDesg.designationId from EmpDesignation eDesg "
						+ "where eDesg.empId = p.empId and eDesg.lastWokingDate is null)), p.pfDate, p.pfsCpfCntrbn, p.pfLoneRecAmt, "
						+ "p.cfLoneRecAmt, p.apfAcpfCntrbn) from EmpPf p where p.empId = ? and p.status = ? ";		
				
				session = HibernateConnection.getSessionFactory().openSession();
				Query query = session.createQuery(queryString);
				query.setParameter(0, empId);
				query.setParameter(1, "A");
				empPf = (EmpPfVO)(!(query.list().isEmpty())?query.list().get(0):null);
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				HibernateConnection.closeSession(session);
			}
		
		return empPf;
	}
	
	public String deleteEmpPf(int empId){
		String result = null;
		Session session = null;
		try{
			session = HibernateConnection.getSessionFactory().openSession();
			Query query = session.createQuery("update EmpPf p set p.status = ?, p.rowUpdDate = ? where p.empId = ?");
			query.setParameter(0, "S");
			query.setParameter(1, new Date());
			query.setParameter(2, empId);
			int updated = query.executeUpdate();
			if(updated > 0)
				result = "Successfully deleted Provident fund details!";
		}catch(Exception e){
			e.printStackTrace();
			result = "Failed to delete Provident fund details!";
		}finally{
			HibernateConnection.closeSession(session);
		}
		return result;
	}
	
	public String addUpdateEmpPf(EmpPf empPf){
		String result = null;
		Session session = null;
		Transaction transaction = null;
		try{
			session = HibernateConnection.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			EmpPf empPfDB = checkEmpPf(empPf.getEmpId(), session);
			if(empPfDB != null){
				if(empPf.getAddUpdate() ==0){
					result = "Provident details for Employee are exit!";
					return result;
				}
				empPfDB.setPfDate(empPf.getPfDate());
				empPfDB.setApfAcpfCntrbn(empPf.getApfAcpfCntrbn());
				empPfDB.setCfLoneRecAmt(empPf.getCfLoneRecAmt());
				empPfDB.setPfLoneRecAmt(empPf.getPfLoneRecAmt());
				empPfDB.setPfsCpfCntrbn(empPf.getPfsCpfCntrbn());
				empPfDB.setRowUpdDate(new Timestamp(System.currentTimeMillis()));
				session.update(empPfDB);
			}
			else {
				empPf.setRowUpdDate(new Timestamp(System.currentTimeMillis()));
				empPf.setStatus("A");
				session.save(empPf);
			}
			transaction.commit();
			result = "Yes";
		}catch(Exception e){
			e.printStackTrace();
			transaction.rollback();
			result = "Failed to add/update Provident Fund details";
		}finally {
			HibernateConnection.closeSession(session);
		}
		return result;
	}

	private EmpPf checkEmpPf(int empId, Session session){
		EmpPf empPf = null;
		try{
			if(session == null)
				session = HibernateConnection.getSessionFactory().openSession();
			Query query = session.createQuery("select p from EmpPf p where p.empId = ?");
			//.setMaxResults(1).uniqueResult();
			query.setParameter(0, empId);
			if(query.list() !=null && !query.list().isEmpty() )
				empPf = (EmpPf)query.list().get(0);
		}catch(Exception e){
			e.printStackTrace();
		
		}
		return empPf;
	}


}
