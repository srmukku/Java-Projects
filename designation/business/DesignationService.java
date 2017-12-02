package com.payroll.designation.business;

import java.util.List;

import com.payroll.designation.dataobjects.Designation;
import com.payroll.designation.dataobjects.DesignationDAO;
import com.payroll.designation.vo.DesignationVO;

public class DesignationService {
	
	public List<DesignationVO> getDesignationVOList(){
		return new DesignationDAO().getDesignationList();
	}
	public List<Designation> getDesignationList(){
		return new DesignationDAO().getDesignations();
	}
	
	public List<DesignationVO> getDesignationsByHead(int headId) {
		return new DesignationDAO().getDesignationsByHead(headId);
	}
	public String addUpdateDesignation(Designation designation){
		return new DesignationDAO().addUpdateDesg(designation);
	}
	public boolean deleteDesignation(int designationId){
		return new DesignationDAO().deleteDesg(designationId);
	}
	
	public Designation getDesgById(int desgId){
		return new DesignationDAO().getDesignationById(desgId);
	}
	public DesignationVO getDesignationVOById(int desgId){
		return new DesignationDAO().getDesignationVOById(desgId);
	}
}
