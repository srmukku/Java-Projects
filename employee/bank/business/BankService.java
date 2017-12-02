package com.payroll.employee.bank.business;

import java.util.List;

import com.payroll.employee.bank.dataobjects.Bank;
import com.payroll.employee.bank.dataobjects.BankDAO;
import com.payroll.employee.bank.vo.BankVO;

public class BankService {
	public List<BankVO> getBankList(){
		return new BankDAO().getBankList();
	}
	
	public String addUpdateBank(Bank bank){
		return new BankDAO().addUpdateBank(bank);
	}
	
	public BankVO getBankByEmpId(int empId){
		return new BankDAO().getBankByEmpId(empId);
	}
	
	public String deleteEmpBank(int empId){
		return new BankDAO().deleteEmpBank(empId);
	}
}
