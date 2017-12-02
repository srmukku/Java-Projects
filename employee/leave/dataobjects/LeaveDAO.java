package com.payroll.employee.leave.dataobjects;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.payroll.HibernateConnection;
import com.payroll.employee.leave.vo.LeaveVO;
import com.payroll.employee.salary.dataobjects.Salary;
import com.payroll.employee.salary.vo.SalaryVO;
import com.payroll.headInfo.dataobjects.HeadInfo;

public class LeaveDAO {
	public List<com.payroll.employee.leave.vo.LeaveVO> getLeaves(){
			List<com.payroll.employee.leave.vo.LeaveVO> leaves = null;
				Session session = null;
				
				try{
					String queryString = " select new com.payroll.employee.leave.vo.LeaveVO(l.empId, (select e.firstName from Employee e where e.employeeId = l.empId),"
							+ " (select e.lastName from Employee e where e.employeeId = l.empId), "
							+ "l.leaveId, l.leaveType, l.noOfLeaves, l.leaveBalance) from Leave l where l.status = ?";		
					
					session = HibernateConnection.getSessionFactory().openSession();
					Query query = session.createQuery(queryString);
					query.setParameter(0, "A");
					leaves = query.list();
				}catch(Exception e){
					e.printStackTrace();
				}finally{
					HibernateConnection.closeSession(session);
				}
			
			return leaves;
		}
	public LeaveVO getEmpLeave(int leaveId){
		LeaveVO leaveVO = null;
		Session session = null;
		try{
			String queryString = " select new com.payroll.employee.leave.vo.LeaveVO(l.empId, "+
					"(select dept.departmentId from Department dept where dept.departmentId = (select eDept.departmentId "
					+ "from EmpDepartment eDept where eDept.empId = l.empId)), (select desg.designationId "
					+ "from Designation desg where desg.designationId = (select eDesg.designationId from EmpDesignation eDesg "
					+ "where eDesg.empId = l.empId and eDesg.lastWokingDate is null)), "
					+ "(select dh.headId from DesignationHead dh where dh.designationId = "
					+ "(select eDesg.designationId from EmpDesignation eDesg where eDesg.empId = l.empId and eDesg.lastWokingDate is null)), "
					+ "l.leaveId, l.leaveType, l.noOfLeaves, l.leaveBalance) from Leave l where l.leaveId = ?";		
			
			session = HibernateConnection.getSessionFactory().openSession();
			Query query = session.createQuery(queryString);
			query.setParameter(0, leaveId);
			leaveVO = (LeaveVO)(!(query.list().isEmpty()) ? query.list().get(0) : null);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			HibernateConnection.closeSession(session);
		}
		return leaveVO;
	}
	
		private Leave getLeaveById(int leaveId, Session session){
			Leave leave = null;
			try{
				if(session == null || !session.isOpen())
					session = HibernateConnection.getSessionFactory().openSession();
				Query query = session.createQuery(" from Leave l where l.leaveId = ?");
				query.setParameter(0, leaveId);
				leave = (Leave)(!(query.list().isEmpty()) ? query.list().get(0) : null);
			}catch(Exception e){
				e.printStackTrace();
			}
			return leave;
		}
		public String addUpdateLeave(Leave leave){
			String result = null;
			Session session = null;
			Transaction transaction = null;
			try{
				session = HibernateConnection.getSessionFactory().openSession();
				transaction = session.beginTransaction();
				if(leave != null && leave.getLeaveId() !=0){
					Leave leaveDB = getLeaveById(leave.getLeaveId(), session);
					leaveDB.setLeaveBalance(leave.getNoOfLeaves() + leaveDB.getLeaveBalance());
					leaveDB.setRowUpdDate(new Timestamp(System.currentTimeMillis()));
					session.update(leaveDB);
				}else {
					if(checkLeaveExist(leave.getEmpId(), leave.getLeaveType(), session)){
						result = "Leave type is exist for selected Employee, please select other or update existing!";
						return result;
					}
					int maxId = getMaxLeaveId(session);
					leave.setStatus("A");
					leave.setLeaveBalance(leave.getNoOfLeaves());
					leave.setRowUpdDate(new Timestamp(System.currentTimeMillis()));
					leave.setLeaveId(maxId);
					session.save(leave);
				}
				transaction.commit();
				result = "Yes";
			}catch(Exception e){
				e.printStackTrace();
				transaction.rollback();
				result = "Unable to Add/Update leave for selected Employee!";
			}finally {
				HibernateConnection.closeSession(session);
			}
			return result;
		}
		private boolean checkLeaveExist(int empId, String leaveType, Session session){
			boolean exist = false;
			try{
				if(session == null)
					session = HibernateConnection.getSessionFactory().openSession();
				Query query = session.createQuery(" from Leave l where l.empId = ? and l.leaveType = ? and l.status = ?");
				query.setParameter(0, empId);
				query.setParameter(1, leaveType);
				query.setParameter(2, "A");
				exist = !(query.list().isEmpty()) ? true: false;	
			}catch(Exception e){
				e.printStackTrace();
			}
			return exist;
		}
		private int getMaxLeaveId(Session session){
			int maxId = 0;
			//Session session = null;
			try{
				if(session == null)
					session = HibernateConnection.getSessionFactory().openSession();
				Leave leave = (Leave)session.createQuery("select l from Leave l order by l.leaveId desc").setMaxResults(1).uniqueResult();
				int leaveId = (leave != null) ? leave.getLeaveId() : 0;
				maxId = leaveId + 1;
				System.out.println("maxId:"+maxId);
			}catch(Exception e){
				e.printStackTrace();
				maxId = 0;
			}/*finally {
				HibernateConnection.closeSession(session);
			}*/
			return maxId;
		}
		
		public String deleteEmpLeave(int leaveId){
			String result = null;
			Session session = null;
			try{
				session = HibernateConnection.getSessionFactory().openSession();
				Query query = session.createQuery("update Leave l set l.status = ?, l.rowUpdDate = ? where l.leaveId = ?");
				query.setParameter(0, "S");
				query.setParameter(1, new Date());
				query.setParameter(2, leaveId);
				int updated = query.executeUpdate();
				if(updated > 0)
					result = "Successfully deleted Leave!";
			}catch(Exception e){
				e.printStackTrace();
				result = "Failed deleted Leave!";
			}finally{
				HibernateConnection.closeSession(session);
			}
			return result;
		}
		
		public List<LeaveVO> getLeavesByEmp(int empId){
			List<LeaveVO> leaveList = null;
			Session session = null;
			try{
				session = HibernateConnection.getSessionFactory().openSession();
				String queryString = " select new com.payroll.employee.leave.vo.LeaveVO(l.empId, (select e.firstName from Employee e where e.employeeId = l.empId),"
						+ " (select e.lastName from Employee e where e.employeeId = l.empId), "
						+ "l.leaveId, l.leaveType, l.noOfLeaves, l.leaveBalance) from Leave l where l.status = ? and l.empId = ?";		
				
				Query query = session.createQuery(queryString);
				query.setParameter(0, "A");
				query.setParameter(1, empId);
				leaveList = query.list();
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				HibernateConnection.closeSession(session);
			}
			return leaveList;
		}
}
