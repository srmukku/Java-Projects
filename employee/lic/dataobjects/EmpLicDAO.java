package com.payroll.employee.lic.dataobjects;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.payroll.HibernateConnection;
import com.payroll.employee.lic.vo.EmpLicVO;


public class EmpLicDAO {
	
	public List<EmpLicVO> getEmpLicList(){
		List<EmpLicVO> instlmtAmt = null;
			Session session = null;
			
			try{
				String queryString = " select new com.payroll.employee.lic.vo.EmpLicVO(l.empId, (select e.firstName from Employee e where e.employeeId = l.empId),"
						+ " (select e.lastName from Employee e where e.employeeId = l.empId), "
						+ "l.policyNo, l.paymentDate, l.instlmtAmt) from EmpLic l where l.status = ?";		
				
				session = HibernateConnection.getSessionFactory().openSession();
				Query query = session.createQuery(queryString);
				query.setParameter(0, "A");
				instlmtAmt = query.list();
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				HibernateConnection.closeSession(session);
			}
		
		return instlmtAmt;
	}
	
	public EmpLicVO getEmpLicById(int empId){
		EmpLicVO empLicVO = null;
			Session session = null;
			try{
				String queryString = " select new com.payroll.employee.lic.vo.EmpLicVO(l.empId, "
						+ "(select dept.departmentId from Department dept where dept.departmentId = (select eDept.departmentId "
						+ "from EmpDepartment eDept where eDept.empId = l.empId)), (select desg.designationId "
						+ "from Designation desg where desg.designationId = (select eDesg.designationId from EmpDesignation eDesg "
						+ "where eDesg.empId = l.empId and eDesg.lastWokingDate is null)), l.policyNo, l.paymentDate, l.instlmtAmt) from EmpLic l where l.status = ?";		
				
				session = HibernateConnection.getSessionFactory().openSession();
				Query query = session.createQuery(queryString);
				query.setParameter(0, "A");
				empLicVO = (EmpLicVO)(!(query.list().isEmpty()) ? query.list().get(0) : null);
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				HibernateConnection.closeSession(session);
			}
		
		return empLicVO;
	}
	
	public String deleteEmpLic(int empId){
		String result = null;
		Session session = null;
		try{
			session = HibernateConnection.getSessionFactory().openSession();
			Query query = session.createQuery("update EmpLic l set l.status = ?, l.rowUpdDate = ? where l.empId = ?");
			query.setParameter(0, "S");
			query.setParameter(1, new Timestamp(System.currentTimeMillis()));
			query.setParameter(2, empId);
			int updated = query.executeUpdate();
			if(updated > 0)
				result = "Successfully deleted LIC Details!";
		}catch(Exception e){
			e.printStackTrace();
			result = "Failed to delete LIC details!";
		}finally{
			HibernateConnection.closeSession(session);
		}
		return result;
	}
	
	public String addUpdateEmpLic(EmpLic empLic){
		String result = null;
		Session session = null;
		Transaction transaction = null;
		try{
			session = HibernateConnection.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			EmpLic licDB = checkEmpLic(empLic.getEmpId(), session);
			if(licDB != null){
				if(empLic.getAddUpdate() ==0){
					result ="LIC details are exist for selected Employee!";
					return result;
				}
				licDB.setPolicyNo(empLic.getPolicyNo());
				licDB.setInstlmtAmt(empLic.getInstlmtAmt());
				licDB.setPaymentDate(empLic.getPaymentDate());
				licDB.setRowUpdDate(new Timestamp(System.currentTimeMillis()));
				session.update(licDB);
			}
			else {
				empLic.setStatus("A");
				empLic.setRowUpdDate(new Timestamp(System.currentTimeMillis()));
				session.save(empLic);
			}
			transaction.commit();
			result = "Yes";
		}catch(Exception e){
			e.printStackTrace();
			transaction.rollback();
			result = "Failed to add/update Employee LIC details ";
		}finally {
			HibernateConnection.closeSession(session);
		}
		return result;
	}

	private EmpLic checkEmpLic(int empId, Session session){
		EmpLic empLic = null;
		try{
			if(session == null)
				session = HibernateConnection.getSessionFactory().openSession();
			Query query = session.createQuery("select l from EmpLic l where l.empId = ? and l.status = ?");
			//.setMaxResults(1).uniqueResult();
			query.setParameter(0, empId);
			query.setParameter(1, "A");
			if(query.list() !=null && !query.list().isEmpty() )
				empLic = (EmpLic)query.list().get(0);
		}catch(Exception e){
			e.printStackTrace();
		
		}
		return empLic;
	}

}
