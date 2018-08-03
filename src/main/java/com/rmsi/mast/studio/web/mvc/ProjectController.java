
package com.rmsi.mast.studio.web.mvc;
import java.util.ArrayList;


import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rmsi.mast.studio.dao.CommuneDAO;
import com.rmsi.mast.studio.dao.CountryDAO;
import com.rmsi.mast.studio.dao.ProvinceDAO;
import com.rmsi.mast.studio.dao.RegionDAO;
import com.rmsi.mast.studio.domain.Baselayer;
import com.rmsi.mast.studio.domain.Bookmark;
import com.rmsi.mast.studio.domain.Commune;
import com.rmsi.mast.studio.domain.Country;
import com.rmsi.mast.studio.domain.LayerLayergroup;
import com.rmsi.mast.studio.domain.Layergroup;
import com.rmsi.mast.studio.domain.ParcelCount;
import com.rmsi.mast.studio.domain.Project;
import com.rmsi.mast.studio.domain.ProjectArea;
import com.rmsi.mast.studio.domain.ProjectBaselayer;
import com.rmsi.mast.studio.domain.ProjectLayergroup;
import com.rmsi.mast.studio.domain.ProjectRegion;
import com.rmsi.mast.studio.domain.Province;
import com.rmsi.mast.studio.domain.Region;
import com.rmsi.mast.studio.domain.Savedquery;
import com.rmsi.mast.studio.domain.User;
import com.rmsi.mast.studio.domain.UserProject;
import com.rmsi.mast.studio.domain.UserRole;
import com.rmsi.mast.studio.service.BookmarkService;
import com.rmsi.mast.studio.service.OutputformatService;
import com.rmsi.mast.studio.service.ParcelCountService;
import com.rmsi.mast.studio.service.ProjectService;
import com.rmsi.mast.studio.service.ProjectionService;
import com.rmsi.mast.studio.service.RoleService;
import com.rmsi.mast.studio.service.UnitService;
import com.rmsi.mast.studio.service.UserService;
import com.rmsi.mast.studio.util.ConstantUtil;
import com.rmsi.mast.studio.util.SaveProject;
import com.rmsi.mast.viewer.dao.ParcelCountDao;
import com.rmsi.mast.viewer.service.LandRecordsService;

/**
 * @author Aparesh.Chakraborty
 * 
 */
@Controller
public class ProjectController {

	private static final Logger logger = Logger.getLogger(ProjectController.class);
	
	@Autowired
	ProjectService projectService;
	
	@Autowired
	LandRecordsService landRecordsService;

	@Autowired
	RoleService roleService;
	
	
	@Autowired
	UserService userService;
	
	@Autowired
	private ProjectionService projectionService;

	@Autowired
	private UnitService unitService;
	@Autowired
	private OutputformatService outputformatService;
	
	@Autowired
	private BookmarkService bookmarkService;
	
	
	@Autowired
	private CountryDAO countryDAO;
	
	@Autowired
	private RegionDAO regionDAO;
	
	@Autowired 
	private ProvinceDAO provinceDAO;
	
	@Autowired
	private CommuneDAO communeDAO;
	
	@Autowired
	private ParcelCountService parcelCountService;
	
	@RequestMapping(value = "/studio/userproject/", method = RequestMethod.GET)
	@ResponseBody
	public List<Project> getAllUserProjects() {
		return projectService.getAllUserProjects();
	}
	
	
	@RequestMapping(value = "/studio/ownerproject/", method = RequestMethod.POST)
	@ResponseBody
	public List<Project> getProjectsByOwner(HttpServletRequest request,
			HttpServletResponse response) {
		String emailid = request.getParameter("email");
		return projectService.getProjectsByOwner(emailid);
		
	}
	
	
	
	@RequestMapping(value = "/studio/project/", method = RequestMethod.GET)
	@ResponseBody
	public List<Project> list() {
		return projectService.findAllProjects();
	}
	
	
	@RequestMapping(value = "/studio/project/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Project getProjectById(@PathVariable String id) {
		
		Project objProject=	projectService.findProjectByName(id);

		if(!objProject.getName().equals(""))
					{
						ParcelCount objParcelCount=parcelCountService.findParcelCountByTypeAndProjectName(ConstantUtil.APFR, objProject.getName());
						if(objParcelCount==null)
						{
							objProject.setApfrcount(0l);
						}else
					    {
							objProject.setApfrcount(objParcelCount.getCount());	
						}
						
						
					}
		return objProject;
	}

	
	@RequestMapping(value = "/studio/project/delete/", method = RequestMethod.GET)
	@ResponseBody
	public void deleteProject() {
		projectService.deleteProject();
	}

	
	@RequestMapping(value = "/studio/project/delete/{id}", method = RequestMethod.GET)
	@ResponseBody
	public void deleteProjectById(@PathVariable String id) {
		projectService.deleteProjectById(id);
	}

	
	@RequestMapping(value = "/studio/project/create", method = RequestMethod.POST)
	@ResponseBody
	public String createProject(HttpServletRequest request,HttpServletResponse response) 
	{
	
		String projectName;
		Project project = null ;
		try {
			projectName = ServletRequestUtils.getRequiredStringParameter(request, "name");
			
			/*try {
				project_adjudicatorhid = ServletRequestUtils.getRequiredStringParameters(request, "project_adjudicatorhid");
			} catch (Exception e) {
				logger.error(e);
			}
			//hamlet_length=ServletRequestUtils.getRequiredIntParameter(request, "hamlets_length");
			try {
				hamlet_name=ServletRequestUtils.getRequiredStringParameters(request, "hamletName");
				hamlet_alias=ServletRequestUtils.getRequiredStringParameters(request, "hamletAlias");
				hamlet_code=ServletRequestUtils.getRequiredStringParameters(request, "hamletCode");
				
			} catch (Exception e1) {
			logger.error(e1);
			}*/
			
			
			
			String idseq = ServletRequestUtils.getRequiredStringParameter(request, "hid_idseq");
			if(idseq=="")
				
			{
				if(projectService.checkduplicatename(projectName))
				
				{
					
					return "DuplicateName";
				}
				
				if(projectName=="")
					
				{
					
					return "EnterName";
				}
				
			}
			try {
				project = getProjectById(projectName);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			if(project==null){
				
				project=new Project();				
			}
			System.out.println("-----------------"+ request.getParameter("emailid"));
			project.setName(projectName);
			/*project.setActive(Boolean.parseBoolean(ServletRequestUtils
					.getRequiredStringParameter(request, "active")));*/
			project.setActive(true);
			
			project.setActivelayer(ServletRequestUtils
					.getRequiredStringParameter(request, "activelayer"));
			
			project.setCopyright(ServletRequestUtils
					.getRequiredStringParameter(request, "copyright"));
			project.setCosmetic(Boolean.parseBoolean(ServletRequestUtils
					.getRequiredStringParameter(request, "cosmetic")));
			project.setDescription(ServletRequestUtils
					.getRequiredStringParameter(request, "description"));
			/*project.setDisclaimer(ServletRequestUtils
					.getRequiredStringParameter(request, "disclaimer"));*/

			// project.setWidth(Integer.parseInt(ServletRequestUtils.getRequiredStringParameter(request,"width")));
			// project.setHeight(Integer.parseInt(ServletRequestUtils.getRequiredStringParameter(request,"height")));
			project.setMinextent(ServletRequestUtils
					.getRequiredStringParameter(request, "minextent"));
			project.setMaxextent(ServletRequestUtils
					.getRequiredStringParameter(request, "maxextent"));
			// project.setMaxresolutions(Integer.parseInt(ServletRequestUtils.getRequiredStringParameter(request,"maxresolutions")));
			// project.setMinresolutions(Integer.parseInt(ServletRequestUtils.getRequiredStringParameter(request,"minresolutions")));
			project.setNumzoomlevels(Integer.parseInt(ServletRequestUtils
					.getRequiredStringParameter(request, "numzoomlevels")));
			

			project.setOverlaymap(ServletRequestUtils
					.getRequiredStringParameter(request, "overlaymap"));
			
			
			project.setRestrictedextent(ServletRequestUtils
					.getRequiredStringParameter(request, "restrictedextent"));
			// project.setThumbnail(ServletRequestUtils.getRequiredStringParameter(request,"thumbnail"));
			project.setWatermask(ServletRequestUtils
					.getRequiredStringParameter(request, "watermask"));

			project.setUnit(unitService.findUnitByName(ServletRequestUtils
					.getRequiredStringParameter(request, "unit.name")));
			
			
			/*project.setActivelayer(ActivelayerService.findActivelayerByName(ServletRequestUtils
					.getRequiredStringParameter(request, "activelayer.name")));*/
		/*	
			project.setUnit(unitService.findUnitByName(ServletRequestUtils
					.getRequiredStringParameter(request, "overlaymap.name")));*/
			
			
			project.setProjection(projectionService.findProjectionByName(ServletRequestUtils
						.getRequiredStringParameter(request,"projection.code")));
			
			project.setDisplayProjection(projectionService.findProjectionByName(ServletRequestUtils
							.getRequiredStringParameter(request,"displayProjection.code")));
			
			project.setOutputformat(outputformatService.findOutputformatByName(ServletRequestUtils
							.getRequiredStringParameter(request,"outputFormat.name")));
			
			//by Aparesh/
			project.setAdmincreated(true);
			project.setOwner(request.getParameter("emailid"));
			project.setCopyright("custom");
			project.setWatermask("custom");

			String layerGroup[] = request
					.getParameterValues("selectedLayergroups");

		
			String users[]=request.getParameterValues("project_user");
			
			String baselayers[]=null;
			
			try{
				baselayers = request.getParameterValues("selectedBaselayer");
				}catch(Exception e){
					logger.error(e);
				}
			
			
			Set<UserProject> userProjectList = new HashSet<UserProject> ();			
			Set<ProjectBaselayer> projectBaselayerList = new HashSet<ProjectBaselayer> ();
			List<ProjectArea> projectAreaList = new ArrayList<ProjectArea> ();	
			
			ProjectArea projectArea = new ProjectArea();	
			//SET PRoject Area
			
			String id="";
			
			String villagecode="";
			String villagepostalcode="";
			String commune="";
			
			String cfvPresident="";
			String mayorName="";
			
		//districtOfficer,villageChairman,approvingExecutive
			
			try {
				/*try {
					countryname = ServletRequestUtils.getRequiredStringParameter(request, "countryId");
				} catch (Exception e) {
					
					logger.error(e);
				}
				try {
					region = ServletRequestUtils.getRequiredStringParameter(request, "regionId");
				} catch (Exception e) {
					
					logger.error(e);
				}
				try {
					//districtname = ServletRequestUtils.getRequiredStringParameter(request, "districtId");
				} catch (Exception e) {
					
					logger.error(e);
				}
				try {
					province = ServletRequestUtils.getRequiredStringParameter(request, "provinceId");
				} catch (Exception e) {
				
					logger.error(e);
				}*/
				try {
					commune = ServletRequestUtils.getRequiredStringParameter(request, "communeId");
				} catch (Exception e) {
					
					logger.error(e);
				}
				try {
					ServletRequestUtils.getRequiredStringParameter(request, "name");
				} catch (Exception e) {
					
					logger.error(e);
				}
				try {
					id= ServletRequestUtils.getRequiredStringParameter(request, "hid_id");
				} catch (Exception e) {
						
					logger.error(e);
				}
				
				//add for save VillageChairman By RM
				
				try {
					cfvPresident= ServletRequestUtils.getRequiredStringParameter(request, "cfvpresident");
				} catch (Exception e) {
						
					logger.error(e);
				}
				
				try {
					mayorName= ServletRequestUtils.getRequiredStringParameter(request, "mayorname");
				} catch (Exception e) {
						
					logger.error(e);
				}
				
				/*try {
					districtOfficer= ServletRequestUtils.getRequiredStringParameter(request, "districtofficer");
				} catch (Exception e) {
						
					logger.error(e);
				}*/
				try {
					villagecode= ServletRequestUtils.getRequiredStringParameter(request, "villagecode");
				} catch (Exception e) {
						
					logger.error(e);
				}
				try {
					villagepostalcode= ServletRequestUtils.getRequiredStringParameter(request, "villagepostalcode");
				} catch (Exception e) {
						
					logger.error(e);
				}
				
				
				
				//
			        
				if(id!="")
				{
					projectArea.setAreaId(Long.parseLong(id));
					
				}
					
				
				/*projectArea.setDistrictName(districtname);
				projectArea.setVillage(village);*/

				/*projectArea.setHamlet(hamlet);*/

				projectArea.setInitiationDate(new Date());
				projectArea.setProjectName(projectName);
				
				/*projectArea.setVillageChairman(villageChairman);
				projectArea.setApprovingExecutive(approvingExecutive);
				projectArea.setDistrictOfficer(districtOfficer);*/
				projectArea.setVillage_code(villagecode);
				projectArea.setAddress(villagepostalcode);
				
				/*Country countryObj=countryDAO.findById(Integer.valueOf(countryname),false);
				Region regionObj =regionDAO.findById(Integer.valueOf(region), false);
				Province provinceObj=provinceDAO.findById(Integer.valueOf(province), false);*/
				Commune communeObj=communeDAO.findById(Integer.valueOf(commune), false);
				
				/*projectArea.setCountry_id(countryObj);
				projectArea.setRegion_id(regionObj);
				projectArea.setProvince_id(provinceObj);*/
				
				projectArea.setCommune_id(communeObj);
				
				projectArea.setCountry_name(communeObj.getProvince().getRegion().getCountry_id().getCountry_name());
				projectArea.setRegion(communeObj.getProvince().getRegion().getRegionName());
				projectArea.setProvince(communeObj.getProvince().getProvinceName());
				
				projectArea.setCommune(communeObj.getCommuneName());
				projectArea.setPresidentname(cfvPresident);
				projectArea.setMayorname(mayorName);
				projectAreaList.add(projectArea);
				
				
				
				
			} catch (Exception e) {
				
				logger.error(e);
			}
	            
			//SET user
			for(int i = 0; i < users.length; i++)
			{
	            UserProject userProject=new UserProject();
	            User obuser=userService.findUserByUserId(Integer.parseInt(users[i]));
	            userProject.setUser(obuser);
	            userProject.setProjectBean(project);
	            userProjectList.add(userProject);  
	        }
	
			
			//SET Baselayer
			if(baselayers!=null){
				for(int j = 0; j < baselayers.length; j++){
					ProjectBaselayer projectBaselayer=new ProjectBaselayer();
		            Baselayer baselayer=new Baselayer();
		            baselayer.setName(baselayers[j]);
		            
		            projectBaselayer.setBaselayerBean(baselayer);
		            projectBaselayer.setProjectBean(project);
		            projectBaselayer.setBaselayerorder(j+1);
		            
					System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+ baselayer.getName());
					projectBaselayerList.add(projectBaselayer);	           
		        }
			}
			
			Set<ProjectLayergroup> plgList = new HashSet<ProjectLayergroup>();
			//Set<ProjectLayergroup> plgList = project.getProjectLayergroups();
			
			for (int i = 0; i < layerGroup.length; i++) {
				ProjectLayergroup plg = new ProjectLayergroup();
				Layergroup lg = new Layergroup();
				//Project proj = new Project();

				lg.setName(layerGroup[i]);
				//proj.setName(projName);

				plg.setLayergroupBean(lg);
				plg.setProjectBean(project);
				plg.setGrouporder(i + 1);
				plgList.add(plg);
			}
			
			// add for project configuration
			
		/*	if(baselayers!=null){
				for(int j = 0; j < baselayers.length; j++){
					ProjectBaselayer projectBaselayer=new ProjectBaselayer();
		            Baselayer baselayer=new Baselayer();
		            baselayer.setName(baselayers[j]);
		            
		            projectBaselayer.setBaselayerBean(baselayer);
		            projectBaselayer.setProjectBean(project);
		            projectBaselayer.setBaselayerorder(j+1);
		            
					System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+ baselayer.getName());
					projectBaselayerList.add(projectBaselayer);	           
		        }
			}
		*/

			project.setProjectLayergroups(plgList);
			project.setUserProjects(userProjectList);
			project.setProjectBaselayers(projectBaselayerList);
			project.setProjectAreas(projectAreaList);
					
			
			
			
			projectService.addProject(project);
			
			// kamal 
			
			String apfr =ServletRequestUtils.getRequiredStringParameter(request, "apfrcount");
			int  apfrcount =Integer.parseInt(apfr);
			
			if(!project.getName().equals(""))
			{
				ParcelCount objParcelCount=parcelCountService.findParcelCountByTypeAndProjectName(ConstantUtil.APFR, project.getName());
				if(objParcelCount==null)
				{
					objParcelCount= new ParcelCount();
					objParcelCount.setCount(apfrcount);
					objParcelCount.setType(ConstantUtil.APFR);
					objParcelCount.setPname(project.getName());
					parcelCountService.addParcelCount(objParcelCount);
					
					
				}else
				{
					objParcelCount.setCount(apfrcount);
					parcelCountService.addParcelCount(objParcelCount);

				}
					
				
			}
				
			
			
			projectService.deleteAdjByProject(projectName);
			
			
			/*ProjectAdjudicator adjObj=new ProjectAdjudicator();
			
				for(String str:project_adjudicatorhid){  
				adjObj.setAdjudicatorName(str);
				adjObj.setProjectName(projectName);
				projectService.addAdjudicatorDetails(adjObj);
			}
			//projectService.deleteHamletByProject(projectName);
			
				List<String> hamlettmplst = projectService.getHamletCodesbyProject(projectName);
				
				List<ProjectHamlet>hamletObjtmp=new ArrayList<ProjectHamlet>();
	//Must check the code after 1-Oct-15 for HamletList use.
					for (int j = 0; j < hamlet_name.length; j++) {
						ProjectHamlet hamletObj=new ProjectHamlet();
							hamletObj.setHamletName(hamlet_name[j]);
							hamletObj.setHamletNameSecondLanguage(hamlet_alias[j]);
							hamletObj.setHamletCode(hamlet_code[j]);
							hamletObj.setProjectName(projectName);
							hamletObj.setCount(0);
							if(!hamlettmplst.contains(hamlet_code[j]))
								projectService.addHamlets(hamletObj);
					}*/
					
			/*//	hamletObjtmp.removeAll(hamlettmplst);
				for (int i = 0; i < hamletObjtmp.size(); i++) {
					
					projectService.addHamlets(hamletObjtmp.get(i));
				}*/
				
				
					
				
			
			return "ProjectAdded";
		} catch (Exception e) 
		{			
			logger.error(e);
			return "false";
		}
		

	}

	
	@RequestMapping(value = "/studio/project/{id}/bookmark/", method = RequestMethod.GET)
	@ResponseBody
	public List<Bookmark> getBookmarksByProject(@PathVariable String id) {

		return projectService.getBookmarksByProject(id);
	}

	
	@RequestMapping(value = "/studio/project/{id}/savedquery/", method = RequestMethod.GET)
	@ResponseBody
	public List<Savedquery> getSavedqueryByProject(@PathVariable String id) {

		return projectService.getSavedqueryByProject(id);
	}
	
	
	@RequestMapping(value = "/studio/project/names", method = RequestMethod.GET)
	@ResponseBody
	public List<String> getAllProjectNames(){
		List<String> results = projectService.getAllProjectNames();
		
		return results;
	}
	
	
	@RequestMapping(value = "/studio/project/{id}/user", method = RequestMethod.GET)
	@ResponseBody
	public List<String> getUsersByProject(@PathVariable String id){
		
		//List<String> results = projectService.getUsersByProject(id);		
		List<String> results = projectService.getUserEmailByProject(id);
		return results;
	}
	
	@RequestMapping(value = "/studio/project/save", method = RequestMethod.POST)
	@ResponseBody
	//public boolean saveProject(HttpServletRequest request){
	public boolean saveProject(@RequestBody SaveProject saveProject){
		

		
		String[][] layerVis = saveProject.getLayerVisibility();
		
		String[] users = saveProject.getUsers();
		
		System.out.println("------------USERS----------");
		for(int i=0;i<users.length;i++){
			System.out.println(users[i]);
		}
		System.out.println("----------------------");
		
		Project project = getProjectById(saveProject.getActualProjectName());
		try{
			Project newProject = (Project)project.clone();
			newProject.setMinextent(saveProject.getExtent());
			newProject.setName(saveProject.getNewProjectName());
			newProject.setDescription(saveProject.getNewProjectDescription());
			newProject.setAdmincreated(false);
			newProject.setOwner(saveProject.getOwner());
			
			//Get the layergroup records from projectlayergroups and create new instance of layergroup
			Set<ProjectLayergroup> projectLayergroups = newProject.getProjectLayergroups();
			Iterator<ProjectLayergroup> itr = projectLayergroups.iterator();
			Set<Layergroup> layerGroups = new HashSet<Layergroup>();
			Set<ProjectLayergroup> projLayerGroups = new HashSet<ProjectLayergroup>();
			
			for(;itr.hasNext();){
				 ProjectLayergroup projectLayerGroup = itr.next();
				 Layergroup lyrGroupBean = projectLayerGroup.getLayergroupBean();
				 Layergroup lyrGroup = new Layergroup();
				 lyrGroup.setName(newProject.getName()+"_" + lyrGroupBean.getName());
				 lyrGroup.setAlias(newProject.getName()+"_" +lyrGroupBean.getAlias());
				 lyrGroup.setTenantid(lyrGroupBean.getTenantid());
				 
				 //Add layer_layergroup collection
				  Set<LayerLayergroup> lyrLayerGroups = lyrGroupBean.getLayerLayergroups();
				  Iterator<LayerLayergroup> itrLyrGrp = lyrLayerGroups.iterator();
				  HashSet<LayerLayergroup> setLyrLayerGroups = new HashSet<LayerLayergroup>();
				  for(;itrLyrGrp.hasNext();){
					  LayerLayergroup lyrLayerGroup = itrLyrGrp.next();
					  LayerLayergroup newLayerLayerGroup = new LayerLayergroup();
					  newLayerLayerGroup.setLayer(lyrLayerGroup.getLayer());
					  newLayerLayerGroup.setLayerorder(lyrLayerGroup.getLayerorder());
					  newLayerLayerGroup.setTenantid(lyrLayerGroup.getTenantid());
					  newLayerLayerGroup.setLayervisibility(getLayerVisibilityState(lyrLayerGroup.getLayer(), layerVis));
					  newLayerLayerGroup.setLayergroupBean(lyrGroup);
					  setLyrLayerGroups.add(newLayerLayerGroup);
				  }
				  lyrGroup.setLayerLayergroups(setLyrLayerGroups);
				  
				  ProjectLayergroup newProjLayerGroup = new ProjectLayergroup();
				  newProjLayerGroup.setGrouporder(projectLayerGroup.getGrouporder());
				  newProjLayerGroup.setLayergroupBean(lyrGroup);
				  newProjLayerGroup.setTenantid(projectLayerGroup.getTenantid());
				  newProjLayerGroup.setProjectBean(newProject);
				  projLayerGroups.add(newProjLayerGroup);
				  
				  lyrGroup.setProjectLayergroups(projLayerGroups);
				  layerGroups.add(lyrGroup);
			}
			newProject.setProjectLayergroups(projLayerGroups);
			
			/**************	set project's base layer **********************/
			
			Set<ProjectBaselayer> projectBaselayerList = newProject.getProjectBaselayers();
			Iterator<ProjectBaselayer> baselyritr = projectBaselayerList.iterator();
			HashSet<ProjectBaselayer> newProjectBaselayerList = new HashSet<ProjectBaselayer>();
			Project baseLyrProj=new Project();
			baseLyrProj.setName(newProject.getName());
			
			for(;baselyritr.hasNext();){
				
				ProjectBaselayer newProjectBaselayer = baselyritr.next();
				
				newProjectBaselayer.setProjectBean(newProject);
				newProjectBaselayer.setId(null);
				
				newProjectBaselayerList.add(newProjectBaselayer);
			}			 						
			
			newProject.setProjectBaselayers(newProjectBaselayerList);

			/**************	set Associated users **********************/
			
			Set<UserProject> userProjecs = new HashSet<UserProject> ();
			for(int i = 0; i < users.length; i++){
	            UserProject userProject=new UserProject();
	            User usr=new User();
	            //usr.setName(users[i]);
	            usr.setEmail(users[i]);
	            userProject.setUser(usr);
	            userProject.setProjectBean(newProject);
	            
	            userProjecs.add(userProject);	           
	        }
			newProject.setUserProjects(userProjecs);
			
			
			
			//Verify by Iterating layer groups
			Iterator<Layergroup> itrLg = layerGroups.iterator();
			Layergroup lg = null;
			for(;itrLg.hasNext();){
				lg = itrLg.next();
				
				Set<LayerLayergroup> setLLg = lg.getLayerLayergroups();
				Iterator<LayerLayergroup> itrLLg = setLLg.iterator();
				System.out.println("----------Printing Associated LayerLayergroup-------------");
				for(; itrLLg.hasNext();){
					itrLLg.next();
					
				}
			}
			
			
			Set<ProjectLayergroup> setPLg = lg.getProjectLayergroups();
			Iterator<ProjectLayergroup> itrPlg = setPLg.iterator();
			for(;itrPlg.hasNext();){
				itrPlg.next();
				
			}
			
			
			Set<ProjectBaselayer> setBlyr = newProject.getProjectBaselayers();
			Iterator<ProjectBaselayer> itrBlyr = setBlyr.iterator();
			for(;itrBlyr.hasNext();){
				itrBlyr.next();
				
			}
			
			
			Set<UserProject> setusrproj = newProject.getUserProjects();
			Iterator<UserProject> itrusrprojr = setusrproj.iterator();
			for(;itrusrprojr.hasNext();){
				itrusrprojr.next();
							
			}
			
			
			//get project's bookmark
			List<Bookmark> bookmarks=bookmarkService.getBookmarksByProject(saveProject.getActualProjectName());
			
			
			
			projectService.addSaveProject(newProject,layerGroups,bookmarks,saveProject.getActualProjectName());
			
		}catch(CloneNotSupportedException e){
			logger.error(e);
		}
		return true;
	}
	
	private boolean getLayerVisibilityState(String layer, String[][] lyrVisibility){
		boolean bVisState = false;
		for(int i=0; i<lyrVisibility.length; i++){
			if(layer.equals(lyrVisibility[i][0])){
				bVisState = Boolean.parseBoolean(lyrVisibility[i][1]);
				break;
			}
		}
		return bVisState;
	}
	
	
	/* ************@RMSI/NK add for country,region, district,village,hamlet * start ***1-5 ***********/
	
	@RequestMapping(value = "/studio/projectcontry/", method = RequestMethod.GET)
	@ResponseBody
	public List<Country> getList() {
		return projectService.findAllCountry();
	}
	
	
	
/*	@RequestMapping(value = "/studio/projectregion/{countryname}", method = RequestMethod.GET)
	@ResponseBody
	public List<ProjectRegion> getList(@PathVariable String countryname) {
		return projectService.findRegionByCountry(countryname);
	}
	
	@RequestMapping(value = "/studio/projectdistrict/{countryname}", method = RequestMethod.GET)
	@ResponseBody
	public List<ProjectRegion> getListRegion(@PathVariable String countryname) {
		return projectService.findDistrictByRegion(countryname);
	}
	
	
	
	@RequestMapping(value = "/studio/projectvillage/{countryname}", method = RequestMethod.GET)
	@ResponseBody
	public List<ProjectRegion> getListVillage(@PathVariable String countryname) {
		return projectService.findVillageByDistrict(countryname);
	}
	
	
	
	@RequestMapping(value = "/studio/projecthamlet/{countryname}", method = RequestMethod.GET)
	@ResponseBody
	public List<ProjectRegion> getListHamlet(@PathVariable String countryname) {
		return projectService.findHamletByVillage(countryname);
	}*/
	
	@RequestMapping(value = "studio/project/userbyrole/", method = RequestMethod.GET)
	@ResponseBody
	public List<User> userrolelist()
	{
		List<User> userrolelst = new ArrayList<User>();
		ArrayList<Integer> userid= new ArrayList<Integer>();
	    
		List<String> lstRole= new ArrayList<String>();
	    lstRole.add("ROLE_TRUSTED_INTERMEDIARY");
	    lstRole.add("ROLE_PM");
	    lstRole.add("ROLE_LAO");
	    lstRole.add("ROLE_ADJUDICATOR");
		
		 List<UserRole> userroleid = projectService.findAlluserrole(lstRole);
		
		 for (int i = 0; i < userroleid.size(); i++) {

			 Integer id=userroleid.get(i).getUser().getId();
			 
			 userid.add(id);
		 }
			 try {
				
				 userrolelst=userService.findUserById(userid);
				
				 return userrolelst;
			} catch (Exception e) {
				logger.error(e);
			}
		 return userrolelst;
			
		
		
		
				
	}
	
	@RequestMapping(value = "/studio/projectregion/{countryId}", method = RequestMethod.GET)
	@ResponseBody
	public List<Region> getRegionList(@PathVariable Integer countryId) {
		return projectService.findRegionByCountry(countryId);
	}
	
	@RequestMapping(value = "/studio/projectprovince/{regionId}", method = RequestMethod.GET)
	@ResponseBody
	public List<Province> getProvinceList(@PathVariable Integer regionId) {
		return projectService.findProvinceByRegion(regionId);
	}
	
	@RequestMapping(value = "/studio/projectcommune/{provincenId}", method = RequestMethod.GET)
	@ResponseBody
	public List<Commune> getCommuneList(@PathVariable Integer provincenId) {
		return projectService.findCommuneByProvince(provincenId);
	}
	
	
	//Added for Burkina
	
	
	
	/*@RequestMapping(value = "/studio/adjudicators/{projname}", method = RequestMethod.GET)
	@ResponseBody
	public List<ProjectAdjudicator> getListAdjudicators(@PathVariable String projname) {
		return projectService.findAdjudicatorByProject(projname);
	}
	
	@RequestMapping(value = "/studio/hamlet/{projname}", method = RequestMethod.GET)
	@ResponseBody
	public List<ProjectHamlet> getListHamlets(@PathVariable String projname) {
		return projectService.findHamletByProject(projname);
	}
	
	 ************@RMSI/NK add for country,region, district,village,hamlet * start ***1-5 **********
	
	
	@RequestMapping(value = "/studio/project/delethamlet/{hamletcode}/{projectName}", method = RequestMethod.GET)
	@ResponseBody
	public boolean deleteHamlet (@PathVariable String hamletcode,@PathVariable String projectName) {
		
		long hamlet_id=projectService.getHamletIdbyCode(hamletcode,projectName);
		boolean check=landRecordsService.findExistingHamlet(hamlet_id);
		if(!check)	
		return projectService.deletHamletbyId(hamlet_id);
		else
			return false;
		
	}*/
	
}
