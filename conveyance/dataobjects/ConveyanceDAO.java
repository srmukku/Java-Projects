package com.payroll.conveyance.dataobjects;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.payroll.HibernateConnection;
import com.payroll.designation.dataobjects.Designation;

public class ConveyanceDAO {
	
	public List<Conveyance> getConveyAllowances(){
		List<Conveyance> conveyanceList = null;
		Session session = null;
		
		try{
			String queryString = " from Conveyance";
					
			session = HibernateConnection.getSessionFactory().openSession();
			Query query = session.createQuery(queryString);
			conveyanceList = query.list();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			HibernateConnection.closeSession(session);
		}
		return conveyanceList;
	}
	
	public Conveyance getConveyance(int conveyanceId, int desgId){
		Conveyance conveyance = null;
		Session session = null;
		try{
			session = HibernateConnection.getSessionFactory().openSession();
			Query query = session.createQuery(" from Conveyance c where c.conveyanceId = ? "
				+ "and c.designationId = ?");
			query.setParameter(0, conveyanceId);
			query.setParameter(1, desgId);
			conveyance = (Conveyance)(!(query.list().isEmpty())?query.list().get(0):null);
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			HibernateConnection.closeSession(session);
		}
		return conveyance;
	}
	
	public boolean deleteConveyance(int conveyanceId){
		boolean success = false;
		Session session = null;
		Transaction transaction = null;
		try{
			session = HibernateConnection.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			if(conveyanceId != 0){
				Query query = session.createQuery("delete from Conveyance c where c.conveyanceId = ?");
				query.setParameter(0, conveyanceId);
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
	
	public boolean addUpdateConveyance(Conveyance conveyance){
		boolean success = false;
		Session session = null;
		Transaction transaction = null;
		try{
			session = HibernateConnection.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			if(conveyance.getConveyanceId() !=0)
				session.update(conveyance);
			else {
				conveyance.setConveyanceId(getMaxEmpId(session));
				session.save(conveyance);
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
	
	private int getMaxEmpId(Session session){
		int maxDesgId = 0;
		//Session session = null;
		try{
			if(session == null)
				session = HibernateConnection.getSessionFactory().openSession();
			Conveyance conveyance = (Conveyance)session.createQuery("select d from Conveyance c order by c.conveyanceId desc").setMaxResults(1).uniqueResult();
			int conveyanceId = (conveyance != null) ? conveyance.getConveyanceId() : 0;
			maxDesgId = conveyanceId + 1;
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
