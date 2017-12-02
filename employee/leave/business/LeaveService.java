package com.payroll.employee.leave.business;

import java.util.Iterator;
import java.util.List;

import com.payroll.employee.leave.dataobjects.Leave;
import com.payroll.employee.leave.dataobjects.LeaveDAO;
import com.payroll.employee.leave.vo.LeaveVO;

public class LeaveService {
	
	public List<LeaveVO> getLeaves(){
		return new LeaveDAO().getLeaves();
	}
	
	public String addUpdateLeave(LeaveVO leaveVO){
		return new LeaveDAO().addUpdateLeave(copyProperties(leaveVO));
	}
	
	public LeaveVO getLeaveByIde(int leaveId){
		return new LeaveDAO().getEmpLeave(leaveId);
	}
	
	private Leave copyProperties(LeaveVO leaveVO){
		Leave leave = new Leave();
		leave.setEmpId(leaveVO.getEmpId());
		leave.setLeaveId(leaveVO.getLeaveId());
		leave.setLeaveType(leaveVO.getLeaveType());
		leave.setNoOfLeaves(leaveVO.getNoOfLeaves());
		leave.setLeaveBalance(leaveVO.getNoOfLeaves());
		return leave;
	}
	
	public String deleteLeave(int leaveId){
		return new LeaveDAO().deleteEmpLeave(leaveId);
	}
	
	public LeaveVO getEmpAvailableLeaves(int empId){
		List<LeaveVO> empLeaves = new LeaveDAO().getLeavesByEmp(empId);
		int sLeave = 0, cLeave = 0, pLeave = 0;
		String empName = null;
		StringBuffer leaveIds = new StringBuffer("");
		for (Iterator iterator = empLeaves.iterator(); iterator.hasNext();) {
			LeaveVO leave = (LeaveVO) iterator.next();
			if(leave.getLeaveType() != null){
				if(leave.getLeaveType().equalsIgnoreCase("Sick Leave")){
					sLeave = leave.getNoOfLeaves();
					empName = leave.getFullName();
					leaveIds.append("SL:");
					leaveIds.append(sLeave);
				}
				if(leave.getLeaveType().equalsIgnoreCase("Casual Leave")){
					cLeave = leave.getNoOfLeaves();
					leaveIds.append("CL:");
					leaveIds.append(cLeave);
				}
				if(leave.getLeaveType().equalsIgnoreCase("Paid Vacation")){
					pLeave = leave.getNoOfLeaves();
					leaveIds.append("PL:");
					leaveIds.append(pLeave);
				}
			}
		}
		LeaveVO leaveVO = new LeaveVO(empId, empName, cLeave, pLeave, sLeave, leaveIds.toString());
		return leaveVO;
	}

}
