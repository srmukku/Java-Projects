package com.payroll.conveyance.business;

import java.util.List;

import com.payroll.conveyance.dataobjects.Conveyance;
import com.payroll.conveyance.dataobjects.ConveyanceDAO;

public class ConveyanceService {
	
	public List<Conveyance> getConveyanceList(){
		return new ConveyanceDAO().getConveyAllowances();
	}
	
	public boolean addUpdateConveyance(Conveyance conveyance){
		return new ConveyanceDAO().addUpdateConveyance(conveyance);
	}
	
	public Conveyance getConveyanceById(int conveyanceId, int desgId){
		return new ConveyanceDAO().getConveyance(conveyanceId, desgId);
	}

	public boolean deleteConveyance(int conveyanceId){
		return new ConveyanceDAO().deleteConveyance(conveyanceId);
	}

}
