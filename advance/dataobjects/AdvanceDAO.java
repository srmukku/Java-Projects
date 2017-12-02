package com.payroll.advance.dataobjects;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.payroll.HibernateConnection;
import com.payroll.overtime.dataobjects.Overtime;

public class AdvanceDAO {
	
	public List<com.payroll.advance.vo.Advance> getAdvances(){
		List<com.payroll.advance.vo.Advance> advanceList = null;
		Session session = null;
		
		try{
			//String queryString = " from Advance";
			String queryString = " select new com.payroll.advance.vo.Advance(a.empId, (select e.firstName from Employee e where e.employeeId = a.empId),"
					+ " (select e.firstName from Employee e where e.employeeId = a.empId), a.departmentId, (select d.departmantName from Department d where d.departmentId = a.departmentId),"
					+ "a.designationId, (select desg.designationName from Designation desg where desg.designationId = a.designationId),"
					+ "a.paymentDate, a.advanceAmount) from Advance a ";		
			
					
			session = HibernateConnection.getSessionFactory().openSession();
			Query query = session.createQuery(queryString);
			advanceList = query.list();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			HibernateConnection.closeSession(session);
		}
		return advanceList;
	}
	
	public boolean deleteAdvance(int advanceId){
		boolean success = false;
		Session session = null;
		Transaction transaction = null;
		try{
			session = HibernateConnection.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			if(advanceId != 0){
				Query query = session.createQuery("delete from Advance a where a.advanceId = ?");
				query.setParameter(0, advanceId);
				int updated = query.executeUpdate();
				System.out.println("Deleted:"+updated);
				if(updated == 1){
					transaction.commit();
					success = true;
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
			transaction.rollback();
			success = false;
		}finally {
			HibernateConnection.closeSession(session);
		}
		return success;
	}
	
	public Advance getAdvanceById(int empId, Date paymentDate){
		Advance advDB = null;
		Session session = null;
		try{
			session = HibernateConnection.getSessionFactory().openSession();
			advDB = checkAdvance(empId, paymentDate, session);
			
		}catch(Exception e){
			e.printStackTrace();
			
		}finally {
			HibernateConnection.closeSession(session);
		}
		return advDB;
	}
	public boolean addUpdateAdvance(Advance advance){
		boolean success = false;
		Session session = null;
		Transaction transaction = null;
		try{
			session = HibernateConnection.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			Advance advanceDB = checkAdvance(advance.getEmpId(), advance.getPaymentDate(), session);
			if(advanceDB !=null)
				session.update(advanceDB);
			else {
				//advance.setAdvanceId(getMaxEmpId(session));
				session.save(advance);
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
	
	private Advance checkAdvance(int empId, Date paymentDate, Session session){
		Advance advance = null;
		try{
			if(session == null)
				session = HibernateConnection.getSessionFactory().openSession();
			Query query = session.createQuery("select a from Advance a where a.empId = ? and a.paymentDate = ?");
			//.setMaxResults(1).uniqueResult();
			query.setParameter(0, empId);
			query.setParameter(1, paymentDate);
			if(query.list() !=null && !query.list().isEmpty() )
				advance = (Advance)query.list().get(0);
		}catch(Exception e){
			e.printStackTrace();
		
		}
		return advance;
	}
	
	
	private int getMaxEmpId(Session session){
		int maxDesgId = 0;
		//Session session = null;
		try{
			if(session == null)
				session = HibernateConnection.getSessionFactory().openSession();
			Advance advance = (Advance)session.createQuery("select d from Advance a order by a.advanceId desc").setMaxResults(1).uniqueResult();
			int advanceId = (advance != null) ? advance.getAdvanceId() : 0;
			maxDesgId = advanceId + 1;
			System.out.println("maxDesgId:"+maxDesgId);
		}catch(Exception e){
			e.printStackTrace();
			maxDesgId = 0;
		}/*finally {
			HibernateConnection.closeSession(session);
		}*/
		return maxDesgId;
	}


}
