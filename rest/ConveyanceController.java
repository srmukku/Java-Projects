package com.payroll.rest;

import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.payroll.conveyance.business.ConveyanceService;
import com.payroll.conveyance.dataobjects.Conveyance;
import com.payroll.designation.business.DesignationService;
import com.payroll.designation.dataobjects.Designation;

@Controller
public class ConveyanceController {
	
	@RequestMapping(value="/listConveyance", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List<Conveyance> getConveyance(){
	   List<Conveyance> conveyances = new ConveyanceService().getConveyanceList();
	   return conveyances;
    }
	
	@RequestMapping(value = "/viewConveyance", method = RequestMethod.GET)
	public String viewConveyance(ModelMap model) {
		return "listConveyance";
	}
	
	@RequestMapping(value = "/inputConveyance", method = RequestMethod.POST)
	public ModelAndView inputConveyance(Conveyance conveyance) {
		ObjectMapper mapper = new ObjectMapper();
		System.out.println("Conveyance:"+conveyance);
		List<Designation> desigList = new DesignationService().getDesignationList();
		String desigJSON = "";
		try {
			desigJSON = mapper.writeValueAsString(desigList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(conveyance.getConveyanceId() != 0 && conveyance.getDesignationId() != 0)
			conveyance = new ConveyanceService().
				getConveyanceById(conveyance.getConveyanceId(), conveyance.getDesignationId()); 
		ModelAndView model = new ModelAndView("conveyance", "command", conveyance);
		model.addObject("conveyance", conveyance);
		model.addObject("designations", desigJSON);
		return model;
	}
	   
	@RequestMapping(value="/addConveyance",method=RequestMethod.POST)
	public @ResponseBody
	String addConveyance(@RequestBody Conveyance conveyance){
	   System.out.println("Conveyance:"+conveyance);
	   boolean addedDesg = new ConveyanceService().addUpdateConveyance(conveyance);
	   if(addedDesg)
		   return "Yes";
	   else
		   return "No";
	}
	
	@RequestMapping(value="/deleteConveyance",method=RequestMethod.POST)
	public String deleteConveyance(Conveyance conveyance){
	   System.out.println("designation:"+conveyance);
	   if(new ConveyanceService().deleteConveyance(conveyance.getConveyanceId()))
		   System.out.println("Successfully deleted Conveyance!!");
	   else
		   System.out.println("Failed to deleted Conveyance!!");
	   return "listConveyance";
	}


}
