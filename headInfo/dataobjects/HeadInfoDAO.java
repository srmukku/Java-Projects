package com.payroll.headInfo.dataobjects;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import com.payroll.HibernateConnection;
import com.payroll.employee.salary.dataobjects.Salary;
import com.payroll.headInfo.vo.HeadInfoVO;

public class HeadInfoDAO {
	public List<HeadInfoVO> getHeadInfoList(){
		List<HeadInfoVO> headInfoList = null;
		Session session = null;
		
		try{
			String queryString = "select new com.payroll.headInfo.vo.HeadInfoVO(h.headId, "
					+ "(select dept.departmantName from Department dept where dept.departmentId = h.departmentId),"
					+ " h.headName, h.description) from HeadInfo h where h.status = ? ";
					
			session = HibernateConnection.getSessionFactory().openSession();
			Query query = session.createQuery(queryString);
			query.setString(0, "A");
			headInfoList = query.list();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			HibernateConnection.closeSession(session);
		}
		return headInfoList;
	}
	
	public List<HeadInfoVO> getHeadInfoList(int deptId){
		List<HeadInfoVO> headInfoList = null;
		Session session = null;
		
		try{
			String queryString = "select new com.payroll.headInfo.vo.HeadInfoVO(h.headId, "
					+ "(select dept.departmantName from Department dept where dept.departmentId = h.departmentId),"
					+ " h.headName)"
					+ " from HeadInfo h where h.departmentId = ? and h.status = ? ";
					
			session = HibernateConnection.getSessionFactory().openSession();
			Query query = session.createQuery(queryString);
			query.setInteger(0, deptId);
			query.setString(1, "A");
			headInfoList = query.list();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			HibernateConnection.closeSession(session);
		}
		return headInfoList;
	}
	
	public HeadInfo getHeadInfoById(int headId){
		HeadInfo headInfo = null;
		Session session = null;
		
		try{
			session = HibernateConnection.getSessionFactory().openSession();
			Query query = session.createQuery("from HeadInfo h where h.headId = ? and h.status = ?");
			query.setInteger(0, headId);
			query.setString(1, "A");
			headInfo = (HeadInfo)(!query.list().isEmpty()? query.list().get(0) : null);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			HibernateConnection.closeSession(session);
		}
		return headInfo;
	}
	
	public String deleteHeadInfo(int headInfoId){
		String result = null;
		Session session = null;
		Transaction transaction = null;
		try{
			session = HibernateConnection.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			if(headInfoId !=0 ){
				Query query = session.createQuery("update HeadInfo h set h.status = ?, h.rowUpdDate = ? where h.headId = ?");
				query.setParameter(0, "S");
				query.setParameter(1, new Timestamp(System.currentTimeMillis()));
				query.setParameter(2, headInfoId);
				int updated = query.executeUpdate();
				System.out.println("Deleted:"+updated);
				if(updated == 1){
					transaction.commit();
					result = "Deleted Head details!";
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
			transaction.rollback();
			result = "Failed to delete Head details!";
		}finally {
			HibernateConnection.closeSession(session);
		}
		return result;
	}
	
	public String addUpdateHeadInfo(HeadInfo headInfo){
		String result = null;
		Session session = null;
		Transaction transaction = null;
		try{
			session = HibernateConnection.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			if(checkHead(headInfo.getDepartmentId(), headInfo.getHeadName(), session) != null){
				result = "Head details are exist for selected Department!";
				return result;
			}
			if(headInfo.getHeadId() != 0){
				headInfo.setStatus("A");
				headInfo.setRowUpdDate(new Timestamp(System.currentTimeMillis()));
				session.update(headInfo);
			}
			else {
				headInfo.setHeadId(getMaxHeadInfoId(session));
				headInfo.setStatus("A");
				headInfo.setRowUpdDate(new Timestamp(System.currentTimeMillis()));
				session.save(headInfo);
				HeadDepartment headDept =  new HeadDepartment();
				headDept.setDepartmentId(headInfo.getDepartmentId());
				headDept.setHeadId(headInfo.getHeadId());
				headDept.setStartDate(new Date());
				session.save(headDept);
			}
			transaction.commit();
			result = "Yes";
		}catch(Exception e){
			e.printStackTrace();
			transaction.rollback();
			result = "Failed to add/update Head details!";
		}finally {
			HibernateConnection.closeSession(session);
		}
		return result;
	}
	
	
	private int getMaxHeadInfoId(Session session){
		int maxDesgId = 0;
		//Session session = null;
		try{
			if(session == null)
				session = HibernateConnection.getSessionFactory().openSession();
			HeadInfo headInfo = (HeadInfo)session.createQuery("select h from HeadInfo h order by h.headId desc").setMaxResults(1).uniqueResult();
			int headInfoId = (headInfo != null) ? headInfo.getHeadId() : 0;
			maxDesgId = headInfoId + 1;
			System.out.println("maxDesgId:"+maxDesgId);
		}catch(Exception e){
			e.printStackTrace();
			maxDesgId = 0;
		}/*finally {
			HibernateConnection.closeSession(session);
		}*/
		return maxDesgId;
	}
	
	private HeadInfo checkHead(int deptId, String headName, Session session){
		HeadInfo headInfo = null;
		try{
			if(session == null)
				session = HibernateConnection.getSessionFactory().openSession();
			Query query = session.createQuery("select h from HeadInfo h where h.departmentId = ? and h.headName = ? and h.status = ?");
			//.setMaxResults(1).uniqueResult();
			query.setParameter(0, deptId);
			query.setParameter(1, headName);
			query.setParameter(2, "A");
			if(query.list() !=null && !query.list().isEmpty() )
				headInfo = (HeadInfo)query.list().get(0);
		}catch(Exception e){
			e.printStackTrace();
		}
		session.clear();
		return headInfo;
	}
	
}
