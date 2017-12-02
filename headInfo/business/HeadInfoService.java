package com.payroll.headInfo.business;

import java.util.List;

import com.payroll.headInfo.dataobjects.HeadInfo;
import com.payroll.headInfo.dataobjects.HeadInfoDAO;
import com.payroll.headInfo.vo.HeadInfoVO;

public class HeadInfoService {
	public List<HeadInfoVO> getHeadInfoList(){
		return new HeadInfoDAO().getHeadInfoList();
	}
	
	public List<HeadInfoVO> getHeadInfoList(int deptId){
		return new HeadInfoDAO().getHeadInfoList(deptId);
	}
	
	public HeadInfo getHeadInfoById(int headId){
		return new HeadInfoDAO().getHeadInfoById(headId);
	}
	
	public String addUpdateHeadInfo(HeadInfo headInfo){
		return new HeadInfoDAO().addUpdateHeadInfo(headInfo);
	}
	public String deleteHeadInfo(int headInfoId){
		return new HeadInfoDAO().deleteHeadInfo(headInfoId);
	}
}
