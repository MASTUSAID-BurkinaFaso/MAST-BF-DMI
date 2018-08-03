package com.rmsi.mast.studio.web.mvc;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.apache.log4j.Logger;

import com.rmsi.mast.studio.domain.Bookmark;
import com.rmsi.mast.studio.domain.Commune;
import com.rmsi.mast.studio.domain.Module;
import com.rmsi.mast.studio.domain.Province;
import com.rmsi.mast.studio.domain.User;
import com.rmsi.mast.studio.domain.Village;
import com.rmsi.mast.studio.service.CommuneService;
import com.rmsi.mast.studio.service.ProvinceService;
import com.rmsi.mast.studio.service.VillageService;

@Controller
public class VillageController {

	private static final Logger logger = Logger.getLogger(VillageController.class);
	
	@Autowired
	VillageService   villageService; 
	
	@Autowired
	CommuneService  communeService;
	
	@Autowired
	ProvinceService provinceService;
	
	
	@RequestMapping(value = "/studio/village/search/count/", method = RequestMethod.POST)
	@ResponseBody
	public Integer searchListSize(HttpServletRequest request, HttpServletResponse response)
	{
		String villageName="";
		try
		{
			villageName = ServletRequestUtils.getRequiredStringParameter(request, "village_txtSearch");
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		try {
			 
			return  villageService.searchSize(villageName);
			
		} catch (Exception e) {

			logger.error(e);
			return null;
		}
		
	}
	
	@RequestMapping(value = "/studio/village/search/{startpos}", method = RequestMethod.POST)
	@ResponseBody
	public List<Village> searchUser(HttpServletRequest request, HttpServletResponse response,@PathVariable Integer startpos)
	{
		String villageName="";
		List<Village> villagelst= new ArrayList<Village>();
		try
		{
			villageName = ServletRequestUtils.getRequiredStringParameter(request, "village_txtSearch");
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		try {
			villagelst=villageService.searchVillage(villageName, startpos);
			
		} catch (Exception e) {

			logger.error(e);
			return null;
		}
		return villagelst;
		
		
	}
	
	@RequestMapping(value = "/studio/Commune/all/", method = RequestMethod.GET)
	@ResponseBody
    public List<Commune>  Communelist(){
		return 	communeService.getAllCommune();
	}
	
	@RequestMapping(value = "/studio/village/{villageId}", method = RequestMethod.GET)
	@ResponseBody
	public Village getVillageById(@PathVariable String villageId){		
		return villageService.findVillageById(Integer.parseInt(villageId));
	}
	
	@RequestMapping(value = "/studio/village/delete/{id}", method = RequestMethod.GET)
	@ResponseBody
    public boolean deleteVillage(@PathVariable String id){
		return villageService.deleteVillageByID(Integer.parseInt(id));
	}
	
	
	@RequestMapping(value = "/studio/village/create", method = RequestMethod.POST)
	@ResponseBody
    public String createVillage(HttpServletRequest request, HttpServletResponse response){
		
		
		String villageNameEn="";
		String villageNameFr = "";
		String Cvf_agent="";
		String VillageCode="";
		String communeId="";
		String villageId="";
		
		try
		{
			villageNameEn = ServletRequestUtils.getRequiredStringParameter(request, "nameEn");
			villageNameFr = ServletRequestUtils.getRequiredStringParameter(request, "nameFR");
			Cvf_agent = ServletRequestUtils.getRequiredStringParameter(request, "cfV_agent");
			VillageCode = ServletRequestUtils.getRequiredStringParameter(request, "VillageCode");
			communeId = ServletRequestUtils.getRequiredStringParameter(request, "commmune_id");
			villageId = ServletRequestUtils.getRequiredStringParameter(request, "hid_villageid");
			
			Village  village= new Village();
			
			if(villageId!="")
			{
				village = getVillageById(villageId);

			}
			
			village.setActive(true);
			village.setVillageName(villageNameEn);
			village.setVillageNameFr(villageNameFr);
			village.setVillage_code(VillageCode);
			village.setCfv_agent(Cvf_agent);
			if(communeId!="")
			{
				Commune objCommune= communeService.findCommuneById(Integer.parseInt(communeId));
				village.setCommune(objCommune);

			}
			
			village=villageService.addVillage(village);
			return "true";
			
				
		}catch(Exception e)
		{
			e.printStackTrace();
			return "false";
		}
		
		
		
		
	}
	
	@RequestMapping(value = "/studio/AllProvince", method = RequestMethod.GET)
	@ResponseBody
	public List<Province> getAllProvince() {
		return provinceService.findAllProvince();
	}
	
	
}
