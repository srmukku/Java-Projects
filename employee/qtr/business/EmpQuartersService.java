package com.payroll.employee.qtr.business;

import java.util.List;

import com.payroll.employee.qtr.dataobjects.EmpQuarters;
import com.payroll.employee.qtr.dataobjects.EmpQuartersDAO;
import com.payroll.employee.qtr.vo.EmpQuartersVO;

public class EmpQuartersService {
	public List<EmpQuartersVO> getQtrList(){
		return new EmpQuartersDAO().getEmpOtrList();
	}
	
	public String addUpdateEmpQtr(EmpQuarters empQtr){
		//EmpQuarters empQtr = copyProperties(empQtrVO);
		return new EmpQuartersDAO().addUpdateEmpQtr(empQtr);
	}
	public String deleteEmpQtr(int empId){
		return new EmpQuartersDAO().deleteEmpQtr(empId);
	}
	
	/*private EmpQuarters copyProperties(EmpQuartersVO empQtrVO){
		EmpQuarters empQtr = new EmpQuarters();
		empQtr.setEmpId(empQtrVO.getEmpId());
		empQtr.setAfkQtr(empQtrVO.getAfkQtr());
		return empQtr;
	}*/
	
	public EmpQuartersVO getEmpQtrById(int empId){
		return new EmpQuartersDAO().getEmpQtrById(empId);
	}

}
