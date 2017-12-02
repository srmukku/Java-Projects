package com.payroll.employee.pf.business;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.payroll.employee.pf.dataobjects.EmpPf;
import com.payroll.employee.pf.dataobjects.EmpPfDAO;
import com.payroll.employee.pf.vo.EmpPfVO;

public class EmpPfService {
	
	public List<EmpPfVO> getPfList(){
		return new EmpPfDAO().getEmpPfList();
	}
	
	public String addUpdateEmpPf(EmpPfVO empPfVO){
		EmpPf empPf = copyProperties(empPfVO);
		return new EmpPfDAO().addUpdateEmpPf(empPf);
	}
	public String deleteEmpPf(int empId){
		return new EmpPfDAO().deleteEmpPf(empId);
	}
	
	private EmpPf copyProperties(EmpPfVO empPfVO){
		EmpPf empPf = new EmpPf();
		try{
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			empPf.setEmpId(empPfVO.getEmpId());
			empPf.setApfAcpfCntrbn(empPfVO.getApfAcpfCntrbn());
			empPf.setCfLoneRecAmt(empPfVO.getCfLoneRecAmt());
			empPf.setPfLoneRecAmt(empPfVO.getPfLoneRecAmt());
			empPf.setPfsCpfCntrbn(empPfVO.getPfsCpfCntrbn());
			empPf.setPfDate((empPfVO.getPfDate()!= null ? dateFormat.parse(empPfVO.getPfDate()): new Date()));
			empPf.setAddUpdate(empPfVO.getAddUpdate());
		}catch(Exception e){
			e.printStackTrace();
		}
		return empPf;
	}
	
	public EmpPfVO getEmpPfById(int empId){
		return new EmpPfDAO().getEmpPfById(empId);
	}
}
