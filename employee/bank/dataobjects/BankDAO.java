package com.payroll.employee.bank.dataobjects;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.payroll.HibernateConnection;
import com.payroll.employee.bank.vo.BankVO;
import com.payroll.employee.salary.dataobjects.Salary;

public class BankDAO {
	
	public List<com.payroll.employee.bank.vo.BankVO> getBankList(){
		List<com.payroll.employee.bank.vo.BankVO> bankList = null;
			Session session = null;
			
			try{
				String queryString = " select new com.payroll.employee.bank.vo.BankVO(b.empId, (select e.firstName from Employee e where e.employeeId = b.empId),"
						+ " (select e.lastName from Employee e where e.employeeId = b.empId), "
						+ "b.bankName, b.ifscCode, b.accountNo) from Bank b where b.status = ?";		
				
				session = HibernateConnection.getSessionFactory().openSession();
				Query query = session.createQuery(queryString);
				query.setParameter(0, "A");
				bankList = query.list();
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				HibernateConnection.closeSession(session);
			}
		
		return bankList;
	}
	
	public BankVO getBankByEmpId(int empId){
		BankVO bankVO = null;
		Session session = null;
		
		try{
			String queryString = " select new com.payroll.employee.bank.vo.BankVO(b.empId, "
					+ "(select dept.departmentId from Department dept where dept.departmentId = (select eDept.departmentId "
					+ "from EmpDepartment eDept where eDept.empId = b.empId)), (select desg.designationId "
					+ "from Designation desg where desg.designationId = (select eDesg.designationId from EmpDesignation eDesg "
					+ "where eDesg.empId = b.empId and eDesg.lastWokingDate is null)), "
					+ "(select dh.headId from DesignationHead dh where dh.designationId = "
					+ "(select eDesg.designationId from EmpDesignation eDesg where eDesg.empId = b.empId and eDesg.lastWokingDate is null)), "
					+ "b.bankName, b.ifscCode, b.accountNo) from Bank b where b.status = ? and b.empId = ?";		
			
			session = HibernateConnection.getSessionFactory().openSession();
			Query query = session.createQuery(queryString);
			query.setParameter(0, "A");
			query.setParameter(1, empId);
			bankVO = (BankVO)(!query.list().isEmpty() ? query.list().get(0) : null);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			HibernateConnection.closeSession(session);
		}
		return bankVO;
	}
	
	public String addUpdateBank(Bank bank){
		String result = null;
		Session session = null;
		Transaction transaction = null;
		try{
			session = HibernateConnection.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			Bank bankDB = checkEmpBank(bank.getEmpId(), session);
			if(bankDB != null){
				if(bank.getAddUpdate() ==0){
					result = "Bank details are exist for employee is exist!";
					return result;
				}
				System.out.println("Update");
				bankDB.setBankName(bank.getBankName());
				bankDB.setAccountNo(bank.getAccountNo());
				bankDB.setIfscCode(bank.getIfscCode());
				bankDB.setStatus("A");
				bankDB.setRowUpdDate(new Timestamp(System.currentTimeMillis()));
				session.refresh(bankDB);
			}else {
				System.out.println("Add");
				bank.setStatus("A");
				bank.setRowUpdDate(new Timestamp(System.currentTimeMillis()));
				session.save(bank);
			}
			transaction.commit();
			result = "Yes";
		}catch(Exception e){
			e.printStackTrace();
			transaction.rollback();
			result = "Failed to Add/Update Employee Bank!";
		}finally {
			HibernateConnection.closeSession(session);
		}
		return result;
	}

	private Bank checkEmpBank(int empId, Session session){
		Bank bank = null;
		try{
			if(session == null)
				session = HibernateConnection.getSessionFactory().openSession();
			Query query = session.createQuery("select b from Bank b where b.empId = ? and b.status = ?");
			//.setMaxResults(1).uniqueResult();
			query.setParameter(0, empId);
			query.setParameter(1, "A");
			if(query.list() !=null && !query.list().isEmpty() )
				bank = (Bank)query.list().get(0);
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			session.clear();
		}
		
		return bank;
	}
	
	public String deleteEmpBank(int empId){
		String result = null;
		Session session = null;
		try{
			session = HibernateConnection.getSessionFactory().openSession();
			Query query = session.createQuery("update Bank b set b.status = ?, b.rowUpdDate = ? where b.empId = ?");
			query.setParameter(0, "S");
			query.setParameter(1, new Date());
			query.setParameter(2, empId);
			int updated = query.executeUpdate();
			if(updated > 0)
				result = "Successfully deleted Bank Details!";
		}catch(Exception e){
			e.printStackTrace();
			result = "Failed to delete Bank details!";
		}finally{
			HibernateConnection.closeSession(session);
		}
		return result;
	}
	
}
