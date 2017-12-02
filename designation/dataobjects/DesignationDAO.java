package com.payroll.designation.dataobjects;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.payroll.HibernateConnection;
import com.payroll.department.dataobjects.Department;
import com.payroll.designation.vo.DesignationVO;
import com.payroll.employee.dataobjects.Employee;

public class DesignationDAO {
	
	public List<Designation> getDesignations(){
		List<Designation> designationList = null;
		Session session = null;
		
		try{
			String queryString = " from Designation d where d.status = ?";
					
			session = HibernateConnection.getSessionFactory().openSession();
			Query query = session.createQuery(queryString);
			query.setParameter(0, "A");
			designationList = query.list();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			HibernateConnection.closeSession(session);
		}
		return designationList;
	}
	
	public List<DesignationVO> getDesignationsByHead(int headId){
		List<DesignationVO> designationList = null;
		Session session = null;
		
		try{
			String queryString = "select new com.payroll.designation.vo.DesignationVO(d.designationId, d.designationName)"
					+ " from Designation d where d.status = ? and d.headId = ?";
					
			session = HibernateConnection.getSessionFactory().openSession();
			Query query = session.createQuery(queryString);
			query.setParameter(0, "A");
			query.setParameter(1, headId);
			designationList = query.list();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			HibernateConnection.closeSession(session);
		}
		return designationList;
	}
	
	
	public List<DesignationVO> getDesignationList(){
		List<DesignationVO> designationList = null;
		Session session = null;
		
		try{
			String queryString = "select new com.payroll.designation.vo.DesignationVO(d.designationId, "
					+ "(select h.headId from HeadInfo h where h.headId = (select dh.headId from "
					+ "DesignationHead dh where dh.designationId = d.designationId)), (select h.headName "
					+ "from HeadInfo h where h.headId = (select dh.headId from DesignationHead dh where "
					+ "dh.designationId = d.designationId)), (select dept.departmantName from "
					+ "Department dept where dept.departmentId = (select hd.departmentId from HeadDepartment hd "
					+ "where hd.headId = (select dh.headId from DesignationHead dh where dh.designationId = d.designationId))),"
					+ " d.designationName, d.description) from Designation d "
					+ "where d.status = ?";
					
			session = HibernateConnection.getSessionFactory().openSession();
			Query query = session.createQuery(queryString);
			query.setParameter(0, "A");
			designationList = query.list();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			HibernateConnection.closeSession(session);
		}
		return designationList;
	}
	
	public boolean deleteDesg(int desgId){
		boolean success = false;
		Session session = null;
		Transaction transaction = null;
		try{
			session = HibernateConnection.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			if(desgId != 0){
				Query query = session.createQuery("update Designation d set status = ?, rowUpdDate = ? where d.designationId = ?");
				query.setParameter(0, "S");
				query.setParameter(1, new Date());
				query.setParameter(2, desgId);
				int updated = query.executeUpdate();
				System.out.println("Suspended:"+updated);
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
	
	public String addUpdateDesg(Designation desg){
		String result = null;
		Session session = null;
		Transaction transaction = null;
		try{
			session = HibernateConnection.getSessionFactory().openSession();
			/*if(nameExist(desg.getDesignationName(), session)){
				result = "Desingation already exist!";
				return result;
			}*/
			if(desgExist(desg.getDesignationName(), desg.getHeadId(), session)){
				result = "Desingation is exist for selected Head!";
				return result;
			}
			transaction = session.beginTransaction();
			if(desg.getDesignationId() !=0){
				desg.setStatus("A");
				desg.setRowUpdDate(new Timestamp(System.currentTimeMillis()));
				session.update(desg);
			}
			else {
				desg.setDesignationId(getMaxEmpId(session));
				desg.setStatus("A");
				desg.setRowUpdDate(new Timestamp(System.currentTimeMillis()));
				session.save(desg);
				DesignationHead desgHead = new DesignationHead();
				desgHead.setDesignationId(desg.getDesignationId());
				desgHead.setHeadId(desg.getHeadId());
				desgHead.setStartDate(new Date());
				session.save(desgHead);
			}
			transaction.commit();
			result = "Yes";
		
		}catch(Exception e){
			e.printStackTrace();
			transaction.rollback();
			result = "Unable to Add/Update Deisgnation!";
		}finally {
			HibernateConnection.closeSession(session);
		}
		return result;
	}
	
	private int getMaxEmpId(Session session){
		int maxDesgId = 0;
		//Session session = null;
		try{
			if(session == null)
				session = HibernateConnection.getSessionFactory().openSession();
			Designation desg = (Designation)session.createQuery("select d from Designation d order by d.designationId desc").setMaxResults(1).uniqueResult();
			int desgId = (desg != null) ? desg.getDesignationId() : 0;
			maxDesgId = desgId + 1;
			System.out.println("maxDesgId:"+maxDesgId);
		}catch(Exception e){
			e.printStackTrace();
			maxDesgId = 0;
		}/*finally {
			HibernateConnection.closeSession(session);
		}*/
		return maxDesgId;
	}
	
	public Designation getDesignationById(int desgId){
		Designation designation = null;
		Session session = null;
		
		try{
			String queryString = " from Designation d where d.designationId = ? ";
					
			session = HibernateConnection.getSessionFactory().openSession();
			Query query = session.createQuery(queryString);
			query.setParameter(0, desgId);
			designation = (Designation)(!(query.list().isEmpty())?query.list().get(0) : null);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			HibernateConnection.closeSession(session);
		}
		return designation;
	}
	
	public DesignationVO getDesignationVOById(int desgId){
		DesignationVO designation = null;
		Session session = null;
		
		try{
			//String queryString = " from Designation d where d.designationId = ? ";
			String queryString = "select new com.payroll.designation.vo.DesignationVO(d.designationId, "
					+ "(select h.headId from HeadInfo h where h.headId = (select dh.headId from "
					+ "DesignationHead dh where dh.designationId = d.designationId)), (select dept.departmentId from "
					+ "Department dept where dept.departmentId = (select hd.departmentId from HeadDepartment hd "
					+ "where hd.headId = (select dh.headId from DesignationHead dh where dh.designationId = d.designationId))),"
					+ " d.designationName, d.description) from Designation d "
					+ "where d.status = ? and d.designationId = ?";
			
					
			session = HibernateConnection.getSessionFactory().openSession();
			Query query = session.createQuery(queryString);
			query.setParameter(0, "A");
			query.setParameter(1, desgId);
			designation = (DesignationVO)(!(query.list().isEmpty())?query.list().get(0) : null);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			HibernateConnection.closeSession(session);
		}
		return designation;
	}
	
	
	public boolean nameExist(String name, Session session){
		boolean exist = false;
		Designation designation = null;
		try{
			String queryString = " from Designation d where d.designationName = ? and d.status = ? ";
					
			session = HibernateConnection.getSessionFactory().openSession();
			Query query = session.createQuery(queryString);
			query.setParameter(0, name);
			query.setParameter(1, "A");
			designation = (Designation)(!(query.list().isEmpty())?query.list().get(0) : null);
			if(designation != null){
				exist = designation.getDesignationName().equalsIgnoreCase(name) ? true : false;
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			HibernateConnection.closeSession(session);
		}
		return exist;
	}
	
	public boolean desgExist(String name, int headId, Session session){
		boolean exist = false;
		Designation designation = null;
		try{
			String queryString = " from Designation d where d.designationName = ? and d.headId = ? and d.status = ? ";
			if(session == null)		
				session = HibernateConnection.getSessionFactory().openSession();
			Query query = session.createQuery(queryString);
			query.setParameter(0, name);
			query.setParameter(1, headId);
			query.setParameter(2, "A");
			designation = (Designation)(!(query.list().isEmpty())?query.list().get(0) : null);
			exist = designation != null ? true : false;
			
		}catch(Exception e){
			e.printStackTrace();
		}/*finally{
			HibernateConnection.closeSession(session);
		}*/
		return exist;
	}


}
