package com.payroll.employee.lic.business;

import java.text.SimpleDateFormat;
import java.util.List;

import com.payroll.employee.lic.dataobjects.EmpLic;
import com.payroll.employee.lic.dataobjects.EmpLicDAO;
import com.payroll.employee.lic.vo.EmpLicVO;

public class EmpLicService {
	public List<EmpLicVO> getEmpLicList(){
		return new EmpLicDAO().getEmpLicList();
	}
	
	public EmpLicVO getEmpLicById(int empId){
		return new EmpLicDAO().getEmpLicById(empId);
	}
	
	public String deleteLic(int empId){
		return new EmpLicDAO().deleteEmpLic(empId);
	}
	
	public String addUpdateEmpLic(EmpLicVO licVO){
		return new EmpLicDAO().addUpdateEmpLic(copyProperties(licVO));
	}
	
	private EmpLic copyProperties(EmpLicVO licVO){
		EmpLic empLic = new EmpLic();
		try{
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			empLic.setEmpId(licVO.getEmpId());
			empLic.setInstlmtAmt(licVO.getInstlmtAmt());
			empLic.setPolicyNo(licVO.getPolicyNo());
			empLic.setAddUpdate(licVO.getAddUpdate());
			if(licVO.getPaymentDate() !=null)
				empLic.setPaymentDate(dateFormat.parse(licVO.getPaymentDate()));
		}catch(Exception e){
			e.printStackTrace();
		}
		return empLic;
	}
}
