package com.rmsi.mast.viewer.web.mvc;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.rmsi.mast.custom.dto.BoundaryMapDto;
import com.rmsi.mast.custom.dto.CcroDto;
import com.rmsi.mast.custom.dto.Form1Dto;
import com.rmsi.mast.custom.dto.Form2Dto;
import com.rmsi.mast.custom.dto.Form3Dto;
import com.rmsi.mast.custom.dto.Form5Dto;
import com.rmsi.mast.custom.dto.Form7Dto;
import com.rmsi.mast.custom.dto.Form8Dto;
import com.rmsi.mast.custom.dto.LandRecordDto;
import com.rmsi.mast.custom.dto.PaymentDto;
import com.rmsi.mast.studio.dao.PaymentInfoDAO;
import com.rmsi.mast.studio.dao.SUnitHistoryDAO;
import com.rmsi.mast.studio.domain.ActionTools;
import com.rmsi.mast.studio.domain.AttributeCategory;
import com.rmsi.mast.studio.domain.AttributeValues;
import com.rmsi.mast.studio.domain.Citizenship;
import com.rmsi.mast.studio.domain.EducationLevel;
import com.rmsi.mast.studio.domain.Gender;
import com.rmsi.mast.studio.domain.GroupType;
import com.rmsi.mast.studio.domain.LandType;
import com.rmsi.mast.studio.domain.LandUseType;
import com.rmsi.mast.studio.domain.MaritalStatus;
import com.rmsi.mast.studio.domain.MutationType;
import com.rmsi.mast.studio.domain.NaturalPerson;
import com.rmsi.mast.studio.domain.NatureOfApplication;
import com.rmsi.mast.studio.domain.NatureOfPower;
import com.rmsi.mast.studio.domain.NonNaturalPerson;
import com.rmsi.mast.studio.domain.OccupancyType;
import com.rmsi.mast.studio.domain.PaymentInfo;
import com.rmsi.mast.studio.domain.Person;
import com.rmsi.mast.studio.domain.PersonType;
import com.rmsi.mast.studio.domain.Project;
import com.rmsi.mast.studio.domain.ProjectArea;
import com.rmsi.mast.studio.domain.ProjectHamlet;
import com.rmsi.mast.studio.domain.Role;
import com.rmsi.mast.studio.domain.ShareType;
import com.rmsi.mast.studio.domain.SlopeValues;
import com.rmsi.mast.studio.domain.SocialTenureRelationship;
import com.rmsi.mast.studio.domain.SoilQualityValues;
import com.rmsi.mast.studio.domain.SourceDocument;
import com.rmsi.mast.studio.domain.Status;
import com.rmsi.mast.studio.domain.TenureClass;
import com.rmsi.mast.studio.domain.TitleExisting;
import com.rmsi.mast.studio.domain.User;
import com.rmsi.mast.studio.domain.Village;
import com.rmsi.mast.studio.domain.Workflow;
import com.rmsi.mast.studio.domain.fetch.AttributeValuesFetch;
import com.rmsi.mast.studio.domain.fetch.ProjectTemp;
import com.rmsi.mast.studio.domain.fetch.SpatialUnitExtension;
import com.rmsi.mast.studio.domain.fetch.SpatialUnitStatusHistory;
import com.rmsi.mast.studio.domain.fetch.SpatialUnitTable;
import com.rmsi.mast.studio.domain.fetch.SpatialUnitTemp;
import com.rmsi.mast.studio.domain.fetch.SpatialunitDeceasedPerson;
import com.rmsi.mast.studio.domain.fetch.SpatialunitPersonwithinterest;
import com.rmsi.mast.studio.domain.fetch.Usertable;
import com.rmsi.mast.studio.mobile.dao.SpatialUnitDao;
import com.rmsi.mast.studio.mobile.service.SpatialUnitService;
import com.rmsi.mast.studio.mobile.service.UserDataService;
import com.rmsi.mast.studio.service.ProjectService;
import com.rmsi.mast.studio.service.UserService;
import com.rmsi.mast.viewer.service.LandRecordsService;

@Controller
public class LandRecordsController {

	private static final Logger logger = Logger
			.getLogger(LandRecordsController.class);

	@Autowired
	private ProjectService projectService;

	@Autowired
	private UserService userService;

	@Autowired
	private LandRecordsService landRecordsService;
	@Autowired
	private SpatialUnitService spatialUnitService;

	@Autowired
	private UserDataService userDataService;

	@Autowired
	private PaymentInfoDAO paymentInfoDAO;
	
	@Autowired
	private SUnitHistoryDAO sUnitHistoryDAO;

	@RequestMapping(value = "/viewer/landrecords/", method = RequestMethod.GET)
	@ResponseBody
	public ProjectTemp list(Principal principal) {
		String username = principal.getName();
		User user = userService.findByUniqueName(username);
		return projectService.findProjectTempByName(user.getDefaultproject());

	}

	@RequestMapping(value = "/viewer/landrecords/tenuretype/", method = RequestMethod.GET)
	@ResponseBody
	public List<ShareType> tenureList() {

		return landRecordsService.findAllTenureList();

	}

	@RequestMapping(value = "/viewer/landrecords/socialtenure/{id}", method = RequestMethod.GET)
	@ResponseBody
	public List<SocialTenureRelationship> socialTenureList(@PathVariable Long id) {

		return landRecordsService.findAllSocialTenureByUsin(id);
	}

	@RequestMapping(value = "/viewer/landrecords/spatialunitlist/", method = RequestMethod.GET)
	@ResponseBody
	public List<SpatialUnitTable> allspatialUnitList() {

		return landRecordsService.findAllSpatialUnitlist();

	}

	@RequestMapping(value = "/viewer/landrecords/editattribute/{id}", method = RequestMethod.GET)
	@ResponseBody
	public List<Object> editAttribute(@PathVariable Long id) {

		List<Object> list = new ArrayList<Object>();
		list.add(landRecordsService.findSpatialUnitbyId(id).get(0));
		list.add(landRecordsService.findSpatialByUsin(id));
		return list;
	}

	/*
	 * @RequestMapping(value = "/viewer/landrecords/updateuka", method =
	 * RequestMethod.POST)
	 * 
	 * @ResponseBody public boolean updateUkaNum(HttpServletRequest request,
	 * HttpServletResponse response) { long Usin = 0; String UkaNumber="";
	 * 
	 * 
	 * SpatialUnitTable spatialUnit= new SpatialUnitTable();
	 * 
	 * try { Usin = ServletRequestUtils.getRequiredLongParameter(request,
	 * "primeryky"); UkaNumber =
	 * ServletRequestUtils.getRequiredStringParameter(request, "uka_no");
	 * 
	 * 
	 * 
	 * spatialUnit.setUsin(Usin); spatialUnit.setPropertyno(UkaNumber);
	 * 
	 * 
	 * return landRecordsService.update(spatialUnit);
	 * 
	 * 
	 * } catch (ServletRequestBindingException e) {
	 * 
	 * logger.error(e); return false;
	 * 
	 * } }
	 */

	@RequestMapping(value = "/viewer/landrecords/updateattributes", method = RequestMethod.POST)
	@ResponseBody
	public Integer updateAttributes(HttpServletRequest request,
			HttpServletResponse response) {

		long Usin = 0;
		int househidno = 0;
		String landOwner = "";
		String existingUse = "";

		String project_name = "";
		String survey_date = "";
		Double area = 0.0;

		int userid = 0;
		int length_general = 0;
		String project_general = "";

		String neighbour_north = "";
		String neighbour_south = "";
		String neighbour_east = "";
		String neighbour_west = "";

		String usinStr = "";
		//String app_issueDate = "";
		int app_nature = 0;
		String village_no = "";
		String registration_no = "";
		int mutation_type = 0;
		String issuance_date = "";
		String notice_startDate = "";
		String recognition_rights_date="";
		String  contradictory_date="";
		String title_number="";
		String title_date="";
		int title_id=0;
		String family_name="";
		String other_use="";

		try {
			Usin = ServletRequestUtils.getRequiredLongParameter(request,
					"primary");
			usinStr = ServletRequestUtils.getRequiredStringParameter(request,
					"usinStr_key");

			househidno = ServletRequestUtils.getRequiredIntParameter(request,
					"household");
			landOwner = ServletRequestUtils.getRequiredStringParameter(request,
					"landowner");

			existingUse = ServletRequestUtils.getRequiredStringParameter(request,
					"existing_hidden");


			project_name = ServletRequestUtils.getRequiredStringParameter(
					request, "project_key");


			area = ServletRequestUtils.getRequiredDoubleParameter(request,
					"plot_area");

			userid = ServletRequestUtils.getRequiredIntParameter(request,
					"usertable_id");
			length_general = ServletRequestUtils.getRequiredIntParameter(
					request, "general_length");
			project_general = ServletRequestUtils.getRequiredStringParameter(
					request, "projectname_key1");

			neighbour_north = ServletRequestUtils.getRequiredStringParameter(
					request, "neighbor_north");
			neighbour_south = ServletRequestUtils.getRequiredStringParameter(
					request, "neighbor_south");
			neighbour_east = ServletRequestUtils.getRequiredStringParameter(
					request, "neighbor_east");
			neighbour_west = ServletRequestUtils.getRequiredStringParameter(
					request, "neighbor_west");


			app_nature = ServletRequestUtils.getRequiredIntParameter(request,
					"app_nature");
			mutation_type = ServletRequestUtils.getRequiredIntParameter(
					request, "mutation_type");
			/*app_issueDate = ServletRequestUtils.getRequiredStringParameter(
					request, "app_issueDate");*/
			village_no = ServletRequestUtils.getRequiredStringParameter(
					request, "village_no");

			registration_no = ServletRequestUtils.getRequiredStringParameter(
					request, "registration_no");

			other_use = ServletRequestUtils.getRequiredStringParameter(request, "other_useType");
		/*	try {
				notice_startDate = ServletRequestUtils.getRequiredStringParameter(
						request, "notice_startDate");
			} catch (Exception e1) {
				logger.error(e1);
			}*/

			try{
				recognition_rights_date=ServletRequestUtils.getRequiredStringParameter(
						request, "recognition_date");
			} catch (Exception e2) {
				logger.error(e2);
			}

			try{
				contradictory_date = ServletRequestUtils.getRequiredStringParameter(
						request, "contradictory_date");
			} catch (Exception e3) {
				logger.error(e3);
			}


			try {
				recognition_rights_date=ServletRequestUtils.getRequiredStringParameter(
						request, "recognition_date");
			} catch (Exception e1) {
				logger.error(e1);
			}
			/*	try {
				contradictory_date = ServletRequestUtils.getRequiredStringParameter(
						request, "contradictory_date");
			} catch (Exception e1) {
				logger.error(e1);
			}*/
			title_number = ServletRequestUtils.getRequiredStringParameter(request, "title_number");
			try {
				title_date =ServletRequestUtils.getRequiredStringParameter(request, "title_date");
			} catch (Exception e1) {
				logger.error(e1);
			}
			title_id =ServletRequestUtils.getRequiredIntParameter(request, "title_name");
			family_name=ServletRequestUtils.getRequiredStringParameter(request, "family_name");


			SpatialUnitTable spatialUnit = (SpatialUnitTable) landRecordsService
					.findSpatialUnitbyId(Usin).get(0);

			SpatialUnitExtension spa_extn = landRecordsService.findAllSpatialUnitByUsin(Usin);


			spatialUnit.setHousehidno(househidno);
			//spatialUnit.setLandOwner(landOwner);


			//spatialUnit.setArea(area);



			spatialUnit.setProject(project_name);
			spatialUnit.setActive(true);

			spatialUnit.setStatusUpdateTime(new Date());
			spatialUnit.setNeighbor_east(neighbour_east);
			spatialUnit.setNeighbor_north(neighbour_north);
			spatialUnit.setNeighbor_south(neighbour_south);
			spatialUnit.setNeighbor_west(neighbour_west);

			spatialUnit.setUsinStr(usinStr);

			if (survey_date != "")

			{
				DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

				try {
					Date date = format.parse(survey_date);
					spatialUnit.setSurveyDate(date);

				} catch (ParseException e) {

					logger.error(e);
				}
			}

			/*		if (statusId != 0) {
				Status statusObj = landRecordsService.findAllSTatus(statusId);
				spatialUnit.setStatus(statusObj);
			}*/


			/*	if (existingUse != 0) {
				LandUseType existingObj = landRecordsService
						.findLandUseById(existingUse);
				spatialUnit.setExistingUse(existingObj);
			 */

			spatialUnit.setExisting_use(existingUse);

			if (userid != 0) {
				Usertable userObj = landRecordsService.findUserByID(userid);
				spatialUnit.setUser(userObj);
				spatialUnit.setUserid(userid);
			}


			if (app_nature != 0) {
				NatureOfApplication noaObj = landRecordsService
						.findNatureofApplication(app_nature);
				spatialUnit.setNoa_id(noaObj);

			}
			if (mutation_type != 0) {
				MutationType mtObj = landRecordsService
						.findMutatitonType(mutation_type);
				spatialUnit.setMt_id(mtObj);

			}

			/*if (app_issueDate != "")

			{
				DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

				try {
					Date date = format.parse(app_issueDate);
					spatialUnit.setApplicationdate(date);

				} catch (ParseException e) {

					logger.error(e);
				}
			}*/
			spatialUnit.setVillageno(village_no);
			/*spatialUnit.setParcelno(parcel_no);*/
			spatialUnit.setRegistrationno(registration_no);

			if (issuance_date != "")

			{
				DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

				try {
					Date date = format.parse(issuance_date);
					spatialUnit.setIssuancedate(date);

				} catch (ParseException e) {

					logger.error(e);
				}
			}

	/*		if (notice_startDate != "")

			{
				DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

				try {
					Date date = format.parse(notice_startDate);
					spatialUnit.setPublic_notice_startdate(date);
					Calendar c = Calendar.getInstance();
					c.setTime(date);
					c.add(Calendar.DATE, 45);
					spatialUnit.setPublic_notice_enddate(c.getTime());

				} catch (ParseException e) {

					logger.error(e);
				}
			}*/


			if (recognition_rights_date != "")

			{
				DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

				try {
					Date date1 = format.parse(recognition_rights_date);
					spa_extn.setRecognition_rights_date(date1);
					spa_extn.setUsin(Usin);

				} catch (ParseException e) {

					logger.error(e);
				}
			}
			if (contradictory_date != "")

			{
				DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

				try {
					Date date2 = format.parse(contradictory_date);
					spa_extn.setContradictory_date(date2);
					spa_extn.setUsin(Usin);

				} catch (Exception e) {

					logger.error(e);
				}
			}

			/*spatialUnit.setApplication_no(application_no);*/
			spatialUnit.setFamily_name(family_name);

			//existing title info

			spatialUnit.setTitle_number(title_number);
			if(existingUse.contains("6")){
				spatialUnit.setOtherUseType(other_use);
			}
			else
				spatialUnit.setOtherUseType("");
			if (title_date != "")

			{
				DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

				try {
					Date date = format.parse(title_date);
					spatialUnit.setTitle_date(date);

				} catch (ParseException e) {

					logger.error(e);
				}
			}

			if (title_id != 0) {
				TitleExisting titleObj = landRecordsService
						.findTitleById(title_id);
				spatialUnit.setTitle_id(titleObj);

			}

			new AttributeValues();

			if (length_general > 0) {
				for (int i = 0; i < length_general; i++) {

					StringBuilder sb = new StringBuilder();
					sb.append("alias");
					sb.append(i);
					String alias = sb.toString();
					Long value_key = 0l;

					alias = ServletRequestUtils.getRequiredStringParameter(
							request, "alias_general" + i);
					value_key = ServletRequestUtils.getRequiredLongParameter(
							request, "alias_general_key" + i);
					if (value_key != 0) {
						landRecordsService.updateAttributeValues(value_key,
								alias);
					}
				}

			}
			// For updating in Attribute Values Table
			try {
				userDataService.updateGeneralAttribValues(spatialUnit,
						project_general);
			} catch (Exception e) {
				logger.error(e);

			}
			try {
				if(spa_extn.getContradictory_date()!=null || spa_extn.getRecognition_rights_date()!=null)
					landRecordsService.updateExtn(spa_extn);
			} catch (Exception e) {
				logger.error(e);


			}
			if( landRecordsService.update(spatialUnit))
				return spatialUnit.getWorkflow_id().getWorkflowId();

		} catch (ServletRequestBindingException e) {
			logger.error(e);

			return 0;

		}
		return 0;

	}

	@RequestMapping(value = "/viewer/landrecords/updateapprove/{id}", method = RequestMethod.GET)
	@ResponseBody
	public boolean updateApprove(@PathVariable Long id, Principal principal) {

		String username = principal.getName();
		User user = userService.findByUniqueName(username);
		long userid = user.getId();
		return landRecordsService.updateApprove(id, userid);

	}

	@RequestMapping(value = "/viewer/landrecords/rejectstatus/{id}", method = RequestMethod.GET)
	@ResponseBody
	public boolean rejectStatus(@PathVariable Long id, Principal principal) {

		String username = principal.getName();
		User user = userService.findByUniqueName(username);
		long userid = user.getId();
		return landRecordsService.rejectStatus(id, userid);

	}


	@RequestMapping(value = "/viewer/landrecords/status/", method = RequestMethod.GET)
	@ResponseBody
	public List<Status> listdata() {
		List<Status> statuslst = new ArrayList<Status>();

		try {

			statuslst = landRecordsService.findallStatus();

		} catch (Exception e) {

			logger.error(e);
			return statuslst;
		}

		return statuslst;

	}

	@RequestMapping(value = "/viewer/landrecords/naturalperson/{id}", method = RequestMethod.GET)
	@ResponseBody
	public List<NaturalPerson> naturalPerson(@PathVariable Long id) {

		return landRecordsService.naturalPersonById(id);

	}

	@RequestMapping(value = "/viewer/landrecords/nonnaturalperson/{id}", method = RequestMethod.GET)
	@ResponseBody
	public List<NonNaturalPerson> nonnaturalPerson(@PathVariable Long id) {
		return landRecordsService.nonnaturalPersonById(id);

	}

	@RequestMapping(value = "/viewer/landrecords/gendertype/", method = RequestMethod.GET)
	@ResponseBody
	public List<Gender> genderList() {
		return landRecordsService.findAllGenders();

	}

	@RequestMapping(value = "/viewer/landrecords/maritalstatus/", method = RequestMethod.GET)
	@ResponseBody
	public List<MaritalStatus> maritalStatusList() {

		return landRecordsService.findAllMaritalStatus();

	}

	@RequestMapping(value = "/viewer/landrecords/updatenatural", method = RequestMethod.POST)
	@ResponseBody
	public boolean updateNaturalPerson(HttpServletRequest request,
			HttpServletResponse response) {

		Long id = 0L;
		Long genderid = 0L;
		int maritalid = 0;
		long persontype = 1;
		String first_name = "";
		String middle_name = "";
		String last_name = "";
		String mobile_number = "";
		int age = 0;
		int length_natural = 0;
		String project_name = "";
		long citizenship = 0;
		int newnatural_length = 0;
		long usin = 0;
		String profession = "";
		String dob = "";
		String address = "";
		String refrenceIDNo = "";
		String fatherName = "";
		String motherName = "";
		int nop_id = 0;
		String ID_establishment_date = "";
		String birthplace = "";
		String mandate_establishmentDate="";
		String mandate_location="";

		NaturalPerson naturalPerson = new NaturalPerson();
		Citizenship citizenshipobj = new Citizenship();
		NatureOfPower nop_obj = new NatureOfPower();
		try {
			try {
				id = ServletRequestUtils.getRequiredLongParameter(request,
						"natural_key");
			} catch (Exception e2) {
				logger.error(e2);
			}

			try {
				usin = ServletRequestUtils.getRequiredLongParameter(request,
						"natural_usin");
			} catch (Exception e) {
				logger.error(e);
			}

			first_name = ServletRequestUtils.getRequiredStringParameter(
					request, "fname");
			middle_name = ServletRequestUtils.getRequiredStringParameter(
					request, "mname");
			last_name = ServletRequestUtils.getRequiredStringParameter(request,
					"lname");
			// mobile_number =
			// ServletRequestUtils.getRequiredStringParameter(request,
			// "mobile_natural");
			profession = ServletRequestUtils.getRequiredStringParameter(
					request, "profession");
			/*
			 * try { age= ServletRequestUtils.getRequiredIntParameter(request,
			 * "age"); } catch (Exception e1) { logger.error(e1); }
			 */
			maritalid = ServletRequestUtils.getRequiredIntParameter(request,
					"marital_status");
			genderid = ServletRequestUtils.getRequiredLongParameter(request,
					"gender");

			length_natural = ServletRequestUtils.getRequiredIntParameter(
					request, "natual_length");
			project_name = ServletRequestUtils.getRequiredStringParameter(
					request, "projectname_key");
			// citizenship=
			// ServletRequestUtils.getRequiredLongParameter(request,
			// "citizenship");
			dob = ServletRequestUtils
					.getRequiredStringParameter(request, "dob");
			address = ServletRequestUtils.getRequiredStringParameter(request,
					"person_address");
			refrenceIDNo = ServletRequestUtils.getRequiredStringParameter(
					request, "refrence_IdCard");
			fatherName = ServletRequestUtils.getRequiredStringParameter(
					request, "father_name");
			motherName = ServletRequestUtils.getRequiredStringParameter(
					request, "mother_name");
			ID_establishment_date = ServletRequestUtils
					.getRequiredStringParameter(request,
							"id_establishment_date");
			birthplace = ServletRequestUtils.getRequiredStringParameter(
					request, "birth_place");
			nop_id = ServletRequestUtils.getRequiredIntParameter(request,
					"person_nop");
			mandate_establishmentDate=ServletRequestUtils.getRequiredStringParameter(
					request, "mandate_issuance_date");
			mandate_location=ServletRequestUtils.getRequiredStringParameter(
					request, "mandate_location");
			try {
				newnatural_length = ServletRequestUtils
						.getRequiredIntParameter(request, "newnatural_length");
			} catch (Exception e) {
				logger.error(e);
			}

			// person_subType=ServletRequestUtils.getRequiredLongParameter(request,
			// "person_subType");

			if (id != 0) {
				naturalPerson = (NaturalPerson) landRecordsService
						.naturalPersonById(id).get(0);

			}
			naturalPerson.setAlias(first_name);
			naturalPerson.setFirstName(first_name);
			naturalPerson.setMiddleName(middle_name);
			naturalPerson.setLastName(last_name);
			naturalPerson.setMobile(mobile_number);
			naturalPerson.setOccupation(profession);
			naturalPerson.setAddress(address);
			naturalPerson.setIdcard(refrenceIDNo);
			naturalPerson.setFathername(fatherName);
			naturalPerson.setMothername(motherName);
			naturalPerson.setBirthplace(birthplace);
			naturalPerson.setMandate_location(mandate_location);

			if (ID_establishment_date != "")

			{
				DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

				try {
					Date date = format.parse(ID_establishment_date);
					naturalPerson.setIdcard_establishment_date(date);

				} catch (ParseException e) {

					logger.error(e);
				}
			}
			if (dob != "")

			{
				DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

				try {
					Date date = format.parse(dob);
					naturalPerson.setDob(date);

				} catch (ParseException e) {

					logger.error(e);
				}
			}
			if (mandate_establishmentDate != "")

			{
				DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

				try {
					Date date = format.parse(mandate_establishmentDate);
					naturalPerson.setMandate_issuanceDate(date);

				} catch (ParseException e) {

					logger.error(e);
				}
			}
			naturalPerson.setOwner(false);

			if (nop_id != 0) {
				nop_obj = landRecordsService.findPowerByID(nop_id);
				naturalPerson.setNop_id(nop_obj);
			}
			/*
			 * if(owner_natural==1){ naturalPerson.setOwner(true); }
			 * 
			 * naturalPerson.setResident_of_village(false); if(resd_village==1)
			 * { naturalPerson.setResident_of_village(true);
			 * 
			 * }
			 */
			// naturalPerson.setAdministator(admin);
			if (citizenship != 0) {
				citizenshipobj = landRecordsService
						.findcitizenship(citizenship);
				naturalPerson.setCitizenship_id(citizenshipobj);
			}

			/*
			 * if(education!=0) { EducationLevel educationObj =
			 * landRecordsService.findEducationById(education);
			 * naturalPerson.setEducation(educationObj);
			 * 
			 * }
			 */
			if (age != 0) {
				naturalPerson.setAge(age);
			}

			// naturalPerson.setOccAgeBelow(occupational_age);
			naturalPerson.setActive(true);

			if (genderid != 0) {
				Gender genderIdObj = landRecordsService
						.findGenderById(genderid);
				naturalPerson.setGender(genderIdObj);

			}

			PersonType personTypeObj = landRecordsService
					.findPersonTypeById(persontype);
			naturalPerson.setPerson_type_gid(personTypeObj);

			if (maritalid != 0) {

				MaritalStatus maritalIdObj = landRecordsService
						.findMaritalById(maritalid);
				naturalPerson.setMarital_status(maritalIdObj);
			}

			/*
			 * if(person_subType!=0) {
			 * 
			 * PersonType personsubTypeObj =
			 * landRecordsService.findPersonTypeById(person_subType);
			 * naturalPerson.setPersonSubType(personsubTypeObj);
			 * 
			 * }
			 */

			// AttributeValues attributeValues=new AttributeValues();

			if (length_natural > 0) {
				for (int i = 0; i < length_natural; i++) {

					StringBuilder sb = new StringBuilder();
					sb.append("alias");
					sb.append(i);
					String alias = sb.toString();
					Long value_key = 0l;

					alias = ServletRequestUtils.getRequiredStringParameter(
							request, "alias_natural" + i);
					value_key = ServletRequestUtils.getRequiredLongParameter(
							request, "alias_natural_key" + i);
					if (value_key != 0) {
						landRecordsService.updateAttributeValues(value_key,
								alias);
					}
				}

			}

			// For updating in Attribute Values Table

			NaturalPerson naturalobj = landRecordsService
					.editnatural(naturalPerson);

			if (id == 0) {

				SocialTenureRelationship socialtenuretmp = (SocialTenureRelationship) landRecordsService
						.findAllSocialTenureByUsin(usin).get(0);
				socialtenuretmp.setGid(0);
				socialtenuretmp.setPerson_gid(naturalobj);
				/*
				 * SocialTenureRelationship socialTenureRelationship= new
				 * SocialTenureRelationship();
				 * socialTenureRelationship.setIsActive(true);
				 * socialTenureRelationship
				 * .setOccupancy_type_id(socialtenuretmp.
				 * getOccupancy_type_id());
				 * socialTenureRelationship.setPerson_gid(naturalobj);
				 * socialTenureRelationship
				 * .setShare_type(socialtenuretmp.getShare_type());
				 * socialTenureRelationship
				 * .setSocial_tenure_enddate(socialtenuretmp
				 * .getSocial_tenure_enddate());
				 * socialTenureRelationship.setSocial_tenure_startdate
				 * (socialtenuretmp.getSocial_tenure_startdate());
				 * socialTenureRelationship
				 * .setTenure_duration(socialtenuretmp.getTenure_duration());
				 * socialTenureRelationship
				 * .setTenureclass_id(socialtenuretmp.getTenureclass_id());
				 * socialTenureRelationship.setUsin(usin);
				 */
				landRecordsService.edittenure(socialtenuretmp);

			}

			if (newnatural_length != 0) {
				AttributeValues tmpvalue = new AttributeValues();
				for (int i = 0; i < newnatural_length; i++) {
					int j = i + 1;
					StringBuilder sb = new StringBuilder();
					sb.append("alias");
					sb.append(i);
					String alias_value = sb.toString();
					Long uid = 0l;

					alias_value = ServletRequestUtils
							.getRequiredStringParameter(request,
									"alias_nat_custom" + j);
					uid = ServletRequestUtils.getRequiredLongParameter(request,
							"alias_uid" + j);

					tmpvalue.setValue(alias_value);
					tmpvalue.setParentuid(naturalobj.getPerson_gid());
					tmpvalue.setUid(uid);
					landRecordsService.saveAttributealues(tmpvalue);

				}

			}
			try {
				userDataService.updateNaturalPersonAttribValues(naturalobj,
						project_name);
			} catch (Exception e) {
				logger.error(e);
			}

			return true;

		} catch (ServletRequestBindingException e) {

			logger.error(e);

		}
		return false;

	}

	@RequestMapping(value = "/viewer/landrecords/updatenonnatural", method = RequestMethod.POST)
	@ResponseBody
	public boolean updatenonNaturalPerson(HttpServletRequest request,
			HttpServletResponse response) {

		Long id = 0L;
		String institute_name = "";
		String address = "";
		String phone_no = "";
		long persontype = 2;
		long poc_id = 0;
		String mobileGroupId = "";
		int length_nonnatural = 0;
		String project_nonnatural = "";
		int group_type = 0;

		NonNaturalPerson nonnaturalPerson = new NonNaturalPerson();

		try {
			id = ServletRequestUtils.getRequiredLongParameter(request,
					"non_natural_key");
			institute_name = ServletRequestUtils.getRequiredStringParameter(
					request, "institution");
			address = ServletRequestUtils.getRequiredStringParameter(request,
					"address");
			phone_no = ServletRequestUtils.getRequiredStringParameter(request,
					"mobile_no");
			poc_id = ServletRequestUtils.getRequiredLongParameter(request,
					"poc_id");
			mobileGroupId = ServletRequestUtils.getRequiredStringParameter(
					request, "mobileGroupId");
			length_nonnatural = ServletRequestUtils.getRequiredIntParameter(
					request, "nonnatual_length");
			project_nonnatural = ServletRequestUtils
					.getRequiredStringParameter(request, "projectname_key2");

			group_type = ServletRequestUtils.getRequiredIntParameter(request,
					"group_type");

			nonnaturalPerson.setPerson_gid(id);
			nonnaturalPerson.setAddress(address);
			nonnaturalPerson.setInstitutionName(institute_name);
			nonnaturalPerson.setPhoneNumber(phone_no);
			nonnaturalPerson.setActive(true);

			PersonType personTypeObj = landRecordsService
					.findPersonTypeById(persontype);
			nonnaturalPerson.setPerson_type_gid(personTypeObj);
			nonnaturalPerson.setMobileGroupId(mobileGroupId);
			nonnaturalPerson.setPoc_gid(poc_id);
			if (group_type != 0) {
				GroupType grouptypeObj = landRecordsService
						.findGroupType(group_type);
				nonnaturalPerson.setGroupType(grouptypeObj);
			}

			// AttributeValues attributeValues=new AttributeValues();

			if (length_nonnatural > 0) {
				for (int i = 0; i < length_nonnatural; i++) {

					StringBuilder sb = new StringBuilder();
					sb.append("alias");
					sb.append(i);
					String alias = sb.toString();
					Long value_key = 0l;

					alias = ServletRequestUtils.getRequiredStringParameter(
							request, "alias_nonnatural" + i);
					value_key = ServletRequestUtils.getRequiredLongParameter(
							request, "alias_nonnatural_key" + i);
					if (value_key != 0) {
						landRecordsService.updateAttributeValues(value_key,
								alias);
					}
				}

			}

			// For Updating Non Natural in Attribute Vlaues
			try {
				userDataService.updateNonNaturalPersonAttribValues(
						nonnaturalPerson, project_nonnatural);
			} catch (Exception e) {
				logger.error(e);
			}

			return landRecordsService.editNonNatural(nonnaturalPerson);

		} catch (ServletRequestBindingException e) {

			logger.error(e);
			return false;

		}

	}

	@RequestMapping(value = "/viewer/landrecords/updatetenure", method = RequestMethod.POST)
	@ResponseBody
	public boolean updateTenure(HttpServletRequest request,
			HttpServletResponse response) {

		Long type = 0L;
		int length = 0;
		long usin = 0;
		int resident_tenure = 0;

		String project_tenure = "";

		// SocialTenureRelationship socialTenureRelationship= new
		// SocialTenureRelationship();

		try {
			ServletRequestUtils.getRequiredIntParameter(request,
					"tenure_key");
			type = ServletRequestUtils.getRequiredLongParameter(request,
					"tenure_type");
			// tenureclassid =
			// ServletRequestUtils.getRequiredIntParameter(request,
			// "tenureclass_id");
			usin = ServletRequestUtils
					.getRequiredLongParameter(request, "usin");
			ServletRequestUtils.getRequiredLongParameter(request,
					"person_gid");
			length = ServletRequestUtils.getRequiredIntParameter(request,
					"tenure_length");
			project_tenure = ServletRequestUtils.getRequiredStringParameter(
					request, "projectname_key3");
			resident_tenure = ServletRequestUtils.getRequiredIntParameter(
					request, "tenure_resident");
			List<SocialTenureRelationship> socialTenuretmplst = landRecordsService
					.findAllSocialTenureByUsin(usin);

			if (length > 0) {

				for (int i = 0; i < length; i++) {
					long uid = 0l;
					for (int j = 0; j < socialTenuretmplst.size(); j++) {

						StringBuilder sb = new StringBuilder();
						sb.append("alias");
						sb.append(i);
						String alias = sb.toString();
						Long value_key = 0l;

						value_key = ServletRequestUtils
								.getRequiredLongParameter(request,
										"alias_tenure_key" + i);
						Long attributeKey = 0L;
						if (value_key != 0) {

							AttributeValues attributefetch = landRecordsService
									.getAttributeValue(value_key);
							uid = attributefetch.getUid();
							attributeKey = landRecordsService.getAttributeKey(
									socialTenuretmplst.get(j).getGid(), uid);
						}

						alias = ServletRequestUtils.getRequiredStringParameter(
								request, "alias_tenure" + i);
						if (attributeKey != 0) {
							landRecordsService.updateAttributeValues(
									attributeKey, alias);
						}
					}
				}

			}

			for (int i = 0; i < socialTenuretmplst.size(); i++) {

				SocialTenureRelationship socialTenureRelationship = (SocialTenureRelationship) landRecordsService
						.findSocialTenureByGid(
								socialTenuretmplst.get(i).getGid()).get(0);
				// socialTenureRelationship.setGid(socialTenuretmplst.get(i).getGid());
				if (type != 0) {

					ShareType tenuretypeobj = landRecordsService
							.findTenureType(type);
					socialTenureRelationship.setShare_type(tenuretypeobj);

				}

				socialTenureRelationship.setResident(false);
				TenureClass tenureclassobj = landRecordsService
						.findtenureClasseById(1);
				if (resident_tenure == 2) {
					socialTenureRelationship.setResident(true);
					tenureclassobj = landRecordsService.findtenureClasseById(2);
				}

				socialTenureRelationship.setTenureclass_id(tenureclassobj);

				// For Updating tenure in AttributeValues
				try {
					userDataService.updateTenureAttribValues(
							socialTenureRelationship, project_tenure);
				} catch (Exception e) {
					logger.error(e);
				}
				try {
					landRecordsService.edittenure(socialTenureRelationship);
				} catch (Exception e) {
					logger.error(e);
					return false;
				}
			}
			return true;

		} catch (ServletRequestBindingException e) {

			logger.error(e);
			return false;

		}

	}

	@RequestMapping(value = "/viewer/landrecords/updatemultimedia", method = RequestMethod.POST)
	@ResponseBody
	public boolean updateMultimedia(HttpServletRequest request,
			HttpServletResponse response) {

		int gid = 0;
		String id = "";
		String recordation = "";
		String scannedDocs = "";
		String qualityType = "";
		String comments = "";
		long usin = 0;
		long personGid = 0;
		int socialGid = 0;
		String inventory_type = "";
		String source_path = "";
		String project_multimedia = "";
		String entityName = "";

		SourceDocument sourceDocument = new SourceDocument();

		try {
			gid = ServletRequestUtils.getRequiredIntParameter(request,
					"primary_key");
			id = ServletRequestUtils.getRequiredStringParameter(request,
					"multimedia_id");
			try {
				recordation = ServletRequestUtils.getRequiredStringParameter(
						request, "recordation");
			} catch (Exception e) {
				logger.error(e);
			}
			entityName = ServletRequestUtils.getRequiredStringParameter(
					request, "entity_name");
			scannedDocs = ServletRequestUtils.getRequiredStringParameter(
					request, "scanned_srs");
			qualityType = ServletRequestUtils.getRequiredStringParameter(
					request, "quality_type");
			comments = ServletRequestUtils.getRequiredStringParameter(request,
					"comments_multimedia");
			usin = ServletRequestUtils.getRequiredLongParameter(request,
					"usink");
			source_path = ServletRequestUtils.getRequiredStringParameter(
					request, "source_path");
			project_multimedia = ServletRequestUtils
					.getRequiredStringParameter(request,
							"projectname_multimedia_key");

			try {
				personGid = ServletRequestUtils.getRequiredLongParameter(
						request, "person_gidk");
			} catch (Exception e) {
				logger.error(e);
			}
			try {
				socialGid = ServletRequestUtils.getRequiredIntParameter(
						request, "social_gid");
			} catch (Exception e) {

				logger.error(e);
			}
			inventory_type = ServletRequestUtils.getRequiredStringParameter(
					request, "inventory_type");

			sourceDocument.setGid(gid);
			if (id != "") {
				sourceDocument.setId(id);
			}
			Date recordDate = null;
			try {
				SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");

				recordDate = sdfDate.parse(recordation);
			} catch (ParseException e) {

				logger.error(e);
			}
			sourceDocument.setEntity_name(entityName);
			sourceDocument.setRecordation(recordDate);
			sourceDocument.setScanedSourceDoc(scannedDocs);
			sourceDocument.setQualityType(qualityType);
			sourceDocument.setComments(comments);
			sourceDocument.setUsin(usin);
			if (personGid != 0) {
				sourceDocument.setPerson_gid(personGid);
			}
			if (socialGid != 0) {
				sourceDocument.setSocial_tenure_gid(socialGid);
			}
			sourceDocument.setSocialTenureInvantoryType(inventory_type);
			sourceDocument.setLocScannedSourceDoc(source_path);
			sourceDocument.setActive(true);

			// For Updating tenure in AttributeValues
			try {
				userDataService.updateMultimediaAttribValues(sourceDocument,
						project_multimedia);
			} catch (Exception e) {
				logger.error(e);
			}

			landRecordsService.updateMultimedia(sourceDocument);
			return true;

		} catch (ServletRequestBindingException e) {

			logger.error(e);
			return false;

		}

	}

	@RequestMapping(value = "/viewer/landrecords/socialtenure/edit/{id}", method = RequestMethod.GET)
	@ResponseBody
	public List<SocialTenureRelationship> SocialTenurebyGidList(
			@PathVariable Integer id) {

		return landRecordsService.findSocialTenureByGid(id);

	}

	@RequestMapping(value = "/viewer/landrecords/multimedia/edit/{id}", method = RequestMethod.GET)
	@ResponseBody
	public List<SourceDocument> MultimediaList(@PathVariable Long id) {

		return landRecordsService.findMultimediaByUsin(id);

	}

	@RequestMapping(value = "/viewer/landrecords/multimedia/{id}", method = RequestMethod.GET)
	@ResponseBody
	public List<SourceDocument> MultimediaGidList(@PathVariable Long id) {

		return landRecordsService.findMultimediaByGid(id);

	}

	@RequestMapping(value = "/viewer/landrecords/delete/{id}", method = RequestMethod.GET)
	@ResponseBody
	public boolean deleteMultimedia(@PathVariable Long id) {

		return landRecordsService.deleteMultimedia(id);

	}

	@RequestMapping(value = "/viewer/landrecords/deleteNatural/{id}", method = RequestMethod.GET)
	@ResponseBody
	public boolean deleteNatural(@PathVariable Long id) {

		return landRecordsService.deleteNatural(id);

	}

	@RequestMapping(value = "/viewer/landrecords/deletenonnatural/{id}", method = RequestMethod.GET)
	@ResponseBody
	public boolean deleteNonNatural(@PathVariable Long id) {

		return landRecordsService.deleteNonNatural(id);

	}

	@RequestMapping(value = "/viewer/landrecords/deleteTenure/{id}", method = RequestMethod.GET)
	@ResponseBody
	public boolean deleteTenure(@PathVariable Long id) {

		return landRecordsService.deleteTenure(id);

	}

	@RequestMapping(value = "/viewer/landrecords/educationlevel/", method = RequestMethod.GET)
	@ResponseBody
	public List<EducationLevel> educationList() {

		return landRecordsService.findAllEducation();

	}

	@RequestMapping(value = "/viewer/landrecords/landusertype/", method = RequestMethod.GET)
	@ResponseBody
	public List<LandUseType> landUserList() {
		return landRecordsService.findAllLanduserType();

	}

	@RequestMapping(value = "/viewer/landrecords/tenureclass/", method = RequestMethod.GET)
	@ResponseBody
	public List<TenureClass> tenureclassList() {

		return landRecordsService.findAllTenureClass();
	}

	@RequestMapping(value = "/viewer/landrecords/ukanumber/{id}", method = RequestMethod.GET)
	@ResponseBody
	public String ukaNumberByUsin(@PathVariable Long id) {

		return landRecordsService.findukaNumberByUsin(id);
	}

	@RequestMapping(value = "/viewer/landrecords/hamletname/{project}", method = RequestMethod.GET)
	@ResponseBody
	public List<ProjectHamlet> findAllHamlet(@PathVariable String project) {

		return projectService.findHamletByProject(project);
	}

	@RequestMapping(value = "/viewer/landrecords/occupancytype/", method = RequestMethod.GET)
	@ResponseBody
	public List<OccupancyType> OccTypeList() {

		return landRecordsService.findAllOccupancyTypes();
	}

	@RequestMapping(value = "/viewer/landrecords/attributecategory/", method = RequestMethod.GET)
	@ResponseBody
	public List<AttributeCategory> attribList() {

		return landRecordsService.findAllAttributeCategories();
	}

	@RequestMapping(value = "/viewer/landrecords/getCCRO/{id}", method = RequestMethod.GET)
	@ResponseBody
	public List<SpatialUnitTable> CCROList(@PathVariable Long id,
			Principal principal) {
		/*
		 * String username = principal.getName(); User user =
		 * userService.findByUniqueName(username); long userid=user.getId();
		 * 
		 * List<SpatialUnitTable> spatialunit =
		 * landRecordsService.findSpatialUnitbyId(id);
		 * if(spatialunit.get(0).getStatus().getWorkflowStatusId()==4) {
		 * if(landRecordsService.updateCCRO(id,userid)) { return
		 * landRecordsService.findCCRO_by_USIN(id); } else {
		 * 
		 * return null; } } else{
		 * 
		 * return landRecordsService.findCCRO_by_USIN(id);
		 * 
		 * }
		 */

		return landRecordsService.findSpatialUnitbyId(id);

	}

	/*
	 * @RequestMapping(value = "/viewer/gethamlet/{projectname}", method =
	 * RequestMethod.GET)
	 * 
	 * @ResponseBody public String HamletbyUsin(@PathVariable String
	 * projectname) {
	 * 
	 * Project project= projectService.findProjectByName(projectname); return
	 * project.getProjectAreas().get(0).getVillage();
	 * 
	 * }
	 */

	@RequestMapping(value = "/viewer/landrecords/attributedata/{categoryid}/{parentid}", method = RequestMethod.GET)
	@ResponseBody
	public List<AttributeValuesFetch> attributeDataList(
			@PathVariable long categoryid, @PathVariable long parentid) {

		return landRecordsService.findAttributelistByCategoryId(parentid,
				categoryid);

	}

	@RequestMapping(value = "/viewer/landrecords/naturalpersondata/{personid}", method = RequestMethod.GET)
	@ResponseBody
	public Person naturalPersonList(@PathVariable long personid) {

		return landRecordsService.findPersonGidById(personid);

	}

	@RequestMapping(value = "/viewer/landrecords/finalstatus/{id}", method = RequestMethod.GET)
	@ResponseBody
	public boolean updateFinal(@PathVariable Long id, Principal principal) {

		String username = principal.getName();
		User user = userService.findByUniqueName(username);
		long userid = user.getId();
		return landRecordsService.updateFinal(id, userid);

	}

	@RequestMapping(value = "/viewer/landrecords/uploadweb/", method = RequestMethod.POST)
	@ResponseBody
	public String uploadSpatialData(MultipartHttpServletRequest request,
			HttpServletResponse response, Principal principal) {
		String username = principal.getName();
		User user = userService.findByUniqueName(username);
		String projectName = user.getDefaultproject();

		try {

			Iterator<String> file = request.getFileNames();
			Long usin = 0l;
			usin = ServletRequestUtils.getRequiredLongParameter(request,
					"Usin_Upload");
			String document_name = "";
			document_name = ServletRequestUtils.getRequiredStringParameter(
					request, "document_name");
			String document_comments = "";
			try {
				document_comments = ServletRequestUtils
						.getRequiredStringParameter(request,
								"document_comments");
			} catch (Exception e2) {
				logger.error(e2);

			}

			byte[] document = null;

			while (file.hasNext()) {
				String fileName = file.next();
				MultipartFile mpFile = request.getFile(fileName);

				String originalFileName = mpFile.getOriginalFilename();
				SourceDocument sourceDocument = new SourceDocument();

				String fileExtension = originalFileName.substring(
						originalFileName.indexOf(".") + 1,
						originalFileName.length()).toLowerCase();

				if (originalFileName != "") {
					document = mpFile.getBytes();
				}

				String uploadFileName = null;

				String tmpDirPath = request.getSession().getServletContext()
						.getRealPath(File.separator);

				String outDirPath = tmpDirPath.replace("mast", "")
						+ "resources" + File.separator + "documents"
						+ File.separator + projectName + File.separator
						+ "webupload";

				File outDir = new File(outDirPath);
				boolean exists = outDir.exists();
				if (!exists) {

					(new File(outDirPath)).mkdirs();

				}

				sourceDocument.setScanedSourceDoc(originalFileName);
				uploadFileName = ("resources/documents/" + projectName + "/webupload");
				sourceDocument.setLocScannedSourceDoc(uploadFileName);
				sourceDocument.setEntity_name(document_name);
				sourceDocument.setComments(document_comments);
				sourceDocument.setActive(true);
				sourceDocument.setRecordation(new Date());
				sourceDocument.setUsin(usin);

				sourceDocument = landRecordsService
						.saveUploadedDocuments(sourceDocument);

				Integer id = sourceDocument.getGid();

				try {
					FileOutputStream uploadfile = new FileOutputStream(
							outDirPath + File.separator + id + "."
									+ fileExtension);
					uploadfile.write(document);
					uploadfile.flush();
					uploadfile.close();

					// use for Compression if needed
					// return compressPicture(outDirPath,id,fileExtension);
					return "Success";

				}

				catch (Exception e) {

					logger.error(e);
					return "Error";
				}

			}

		} catch (Exception e) {
			logger.error(e);
			return "Error";
		}
		return "false";
	}

	@RequestMapping(value = "/viewer/landrecords/sourcedocument/{usinId}", method = RequestMethod.GET)
	@ResponseBody
	public List<SourceDocument> sourcedocumentList(@PathVariable long usinId) {

		return landRecordsService.findMultimediaByUsin(usinId);

	}

	@RequestMapping(value = "/viewer/landrecords/adjudicatestatus/{id}", method = RequestMethod.GET)
	@ResponseBody
	public boolean updateAdjudicated(@PathVariable Long id, Principal principal) {

		String username = principal.getName();
		User user = userService.findByUniqueName(username);
		long userid = user.getId();
		return landRecordsService.updateAdjudicated(id, userid);

	}

	@RequestMapping(value = "/viewer/landrecords/download/{id}", method = RequestMethod.GET)
	@ResponseBody
	public void download(@PathVariable Long id, HttpServletRequest request,
			HttpServletResponse response) {

		SourceDocument doc = landRecordsService.getDocumentbyGid(id);
		String fileName = doc.getScanedSourceDoc();
		String fileType = fileName.substring(fileName.indexOf(".") + 1,
				fileName.length()).toLowerCase();
		// Object path_temp =
		// request.getSession().getServletContext().getRealPath(File.separator);
		String filepath = request.getSession().getServletContext()
				.getRealPath(File.separator).replace("mast", "")
				+ doc.getLocScannedSourceDoc()
				+ File.separator
				+ id
				+ "."
				+ fileType;
		Path path = Paths.get(filepath);
		try {
			byte[] data = Files.readAllBytes(path);

			response.setContentLength(data.length);
			OutputStream out = response.getOutputStream();
			out.write(data);
			out.flush();
			out.close();




		} catch (Exception e) {
			logger.error(e);
		}

	}

	@RequestMapping(value = "/viewer/landrecords/Adjuticator/{id}", method = RequestMethod.GET)
	@ResponseBody
	public List<SpatialUnitTable> AdjuticatorList(Principal principal,
			@PathVariable Long id) {
		/*
		 * List<SpatialUnitTable> spatialunit =
		 * landRecordsService.findSpatialUnitbyId(id);
		 * if(spatialunit.get(0).getStatus().getWorkflowStatusId()==1) {
		 * if(updateAdjudicated(id, principal)) { return
		 * landRecordsService.findAdjuticator_by_USIN(id); }
		 * 
		 * else{
		 * 
		 * return null; } } else { return
		 * landRecordsService.findAdjuticator_by_USIN(id);
		 * 
		 * }
		 */

		return landRecordsService.findSpatialUnitbyId(id);

	}

	@RequestMapping(value = "/viewer/landrecords/projectarea/", method = RequestMethod.GET)
	@ResponseBody
	public List<ProjectArea> updateFinal(Principal principal) {

		String username = principal.getName();
		User user = userService.findByUniqueName(username);
		String projectName = user.getDefaultproject();
		return landRecordsService.findProjectArea(projectName);

	}

	@RequestMapping(value = "/viewer/landrecords/soilquality/", method = RequestMethod.GET)
	@ResponseBody
	public List<SoilQualityValues> soilQualityList() {

		return landRecordsService.findAllsoilQuality();
	}

	@RequestMapping(value = "/viewer/landrecords/slope/", method = RequestMethod.GET)
	@ResponseBody
	public List<SlopeValues> slopeList() {

		return landRecordsService.findAllSlopeValues();

	}

	@RequestMapping(value = "/viewer/landrecords/typeofland/", method = RequestMethod.GET)
	@ResponseBody
	public List<LandType> landTypeList() {

		return landRecordsService.findAllLandType();
	}

	@RequestMapping(value = "/viewer/landrecords/grouptype/", method = RequestMethod.GET)
	@ResponseBody
	public List<GroupType> groupTypeList() {

		return landRecordsService.findAllGroupType();
	}

	@RequestMapping(value = "/viewer/landrecords/personbyusin/{id}", method = RequestMethod.GET)
	@ResponseBody
	public List<SocialTenureRelationship> personByUsin(@PathVariable Long id) {
		return landRecordsService.findAllSocialTenureByUsin(id);
	}

	@RequestMapping(value = "/viewer/landrecords/ccrodownload/{id}", method = RequestMethod.GET)
	@ResponseBody
	public List<String> ccrodownload(@PathVariable Long id) {

		List<SourceDocument> doc = landRecordsService.findMultimediaByUsin(id);
		ArrayList<String> ccrodoc = new ArrayList<String>();
		ArrayList<Long> naturalGid = landRecordsService
				.findOwnerPersonByUsin(id);

		try {
			for (int i = 0; i < doc.size(); i++) {
				if ((naturalGid.contains(doc.get(i).getPerson_gid()))) {
					String fileName = doc.get(i).getScanedSourceDoc();
					if (fileName.toLowerCase().contains("jpg"))

					{
						String fileType = fileName.substring(
								fileName.indexOf(".") + 1, fileName.length())
								.toLowerCase();
						String filepath = doc.get(i).getLocScannedSourceDoc()
								+ File.separator + doc.get(i).getGid() + "."
								+ fileType;
						NaturalPerson naturalpersontmp = (NaturalPerson) landRecordsService
								.naturalPersonById(doc.get(i).getPerson_gid())
								.get(0);
						naturalGid.remove(doc.get(i).getPerson_gid());
						String name = naturalpersontmp.getFirstName() + " "
								+ naturalpersontmp.getMiddleName() + " "
								+ naturalpersontmp.getLastName();
						// doc.get(i).getPerson_gid();
						ccrodoc.add(name);
						ccrodoc.add(filepath);

					}
				}
			}

			if (naturalGid.size() != 0) {
				for (int i = 0; i < naturalGid.size(); i++) {
					NaturalPerson naturalpersontmp = (NaturalPerson) landRecordsService
							.naturalPersonById(naturalGid.get(i)).get(0);
					String name = naturalpersontmp.getFirstName() + " "
							+ naturalpersontmp.getMiddleName() + " "
							+ naturalpersontmp.getLastName();
					ccrodoc.add(name);
					ccrodoc.add("");
				}

			}
		} catch (Exception e) {

			logger.error(e);
		}

		return ccrodoc;

	}

	@RequestMapping(value = "/viewer/landrecords/getpersontype/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Integer getpersontypeByUsin(@PathVariable Long id) {

		List<SocialTenureRelationship> socialtenuretmp = landRecordsService
				.findAllSocialTenureByUsin(id);

		if (socialtenuretmp.size() > 0) {
			if (socialtenuretmp.size() > 1) {
				return 1;
			}

			else if (socialtenuretmp.get(0).getPerson_gid()
					.getPerson_type_gid().getPerson_type_gid() == 1)
				return 0;

			else if (socialtenuretmp.get(0).getPerson_gid()
					.getPerson_type_gid().getPerson_type_gid() == 2)
				return 2;

		}
		return null;
	}

	@RequestMapping(value = "/viewer/landrecords/getinstitutename/{id}", method = RequestMethod.GET)
	@ResponseBody
	public String Institute(@PathVariable Long id) {

		List<SocialTenureRelationship> socialtenuretmp = landRecordsService
				.findAllSocialTenureByUsin(id);
		long gid = socialtenuretmp.get(0).getPerson_gid().getPerson_gid();

		List<NonNaturalPerson> nonNaturalpersonList;
		try {
			nonNaturalpersonList = landRecordsService.nonnaturalPersonById(gid);
			return nonNaturalpersonList.get(0).getInstitutionName();
		} catch (Exception e) {
			logger.error(e);
			return null;
		}

	}

	@RequestMapping(value = "/viewer/landrecords/ccroinstituteperson/{id}", method = RequestMethod.GET)
	@ResponseBody
	public String InstitutePerson(@PathVariable Long id) {

		List<SocialTenureRelationship> socialtenuretmp = landRecordsService
				.findAllSocialTenureByUsin(id);
		long gid = socialtenuretmp.get(0).getPerson_gid().getPerson_gid();

		List<NonNaturalPerson> nonNaturalpersonList = landRecordsService
				.nonnaturalPersonById(gid);
		long naturalid = nonNaturalpersonList.get(0).getPoc_gid();

		NaturalPerson naturaltmp = (NaturalPerson) landRecordsService
				.naturalPersonById(naturalid).get(0);
		String name = naturaltmp.getFirstName() + " "
				+ naturaltmp.getMiddleName() + " " + naturaltmp.getLastName();
		return name;

	}

	@RequestMapping(value = "/viewer/landrecords/personadmin/{id}", method = RequestMethod.GET)
	@ResponseBody
	public List<String> PersonAdmin(@PathVariable Long id) {

		List<String> adminName = new ArrayList<String>();

		try {

			// Commented on 24-08-2016
			/*
			 * List<Long> adminList= landRecordsService.getAdminId(id);
			 * 
			 * for (Long adminID: adminList) {
			 * 
			 * PersonAdministrator personadmin=
			 * landRecordsService.getAdministratorById(adminID); SourceDocument
			 * admindoc=landRecordsService.getdocumentByAdminId(adminID); String
			 * name=
			 * personadmin.getFirstname()+" "+personadmin.getMiddlename()+" "
			 * +personadmin.getLastname(); String
			 * fileName=admindoc.getScanedSourceDoc(); String fileType =
			 * fileName
			 * .substring(fileName.indexOf(".")+1,fileName.length()).toLowerCase
			 * (); String
			 * filepath=admindoc.getLocScannedSourceDoc()+File.separator
			 * +admindoc.getGid()+"."+fileType; adminName.add(name);
			 * adminName.add(filepath); }
			 */} catch (Exception e) {
				 logger.error(e);
			 }

		return adminName;
	}

	@RequestMapping(value = "/viewer/landrecords/spatialunit/{project}", method = RequestMethod.POST)
	@ResponseBody
	public Integer totalRecords(Principal principal,
			@PathVariable String project, HttpServletRequest request,
			HttpServletResponse response) {

		String loggeduser = principal.getName();
		User user = userService.findByUniqueName(loggeduser);
		int[] mapped_workids = null;
		try {
			mapped_workids = ServletRequestUtils.getRequiredIntParameters(
					request, "workflow");
		} catch (ServletRequestBindingException e) {
			logger.error(e);
		}
		String defaultProject = user.getDefaultproject();
		if (project != null) {
			defaultProject = project;
		}
		if(user.getRoles().iterator().next().getId()==9){
			int [] workids1={4};
			mapped_workids=workids1;
		}

		// return landRecordsService.findAllSpatialUnit(defaultProject);
		return landRecordsService.AllSpatialUnitTemp(defaultProject,
				mapped_workids);

	}

	@RequestMapping(value = "/viewer/landrecords/changeccrostatus/{id}", method = RequestMethod.GET)
	@ResponseBody
	public boolean changeStatus(@PathVariable Long id, Principal principal) {
		String username = principal.getName();
		User user = userService.findByUniqueName(username);
		long userid = user.getId();
		List<SpatialUnitTable> spatialunit = landRecordsService
				.findSpatialUnitbyId(id);
		if (spatialunit.get(0).getStatus().getWorkflowStatusId() == 4) {
			return landRecordsService.updateCCRO(id, userid);
		} else {

			return false;
		}

	}

	@RequestMapping(value = "/viewer/landrecords/{project}", method = RequestMethod.GET)
	@ResponseBody
	public ProjectTemp list(@PathVariable String project) {
		return projectService.findProjectTempByName(project);
	}

	@RequestMapping(value = "/viewer/landrecords/spatialfalse/{id}", method = RequestMethod.GET)
	@ResponseBody
	public boolean spatialUnitList(@PathVariable Long id) {
		return landRecordsService.deleteSpatialUnit(id);
	}

	@RequestMapping(value = "/viewer/landrecords/shownatural/{id}", method = RequestMethod.GET)
	@ResponseBody
	public List<SocialTenureRelationship> showDeletedNatural(
			@PathVariable Long id) {
		ArrayList<SocialTenureRelationship> objtemp = new ArrayList<SocialTenureRelationship>();
		List<SocialTenureRelationship> obj = landRecordsService
				.showDeletedPerson(id);
		for (int i = 0; i < obj.size(); i++) {
			if (obj.get(i).getPerson_gid().getPerson_type_gid()
					.getPerson_type_gid() == 1)
				objtemp.add(obj.get(i));
		}

		return objtemp;
	}

	@RequestMapping(value = "/viewer/landrecords/addnatural/{gid}", method = RequestMethod.GET)
	@ResponseBody
	public boolean addDeletedNatural(@PathVariable Long gid) {
		return landRecordsService.addDeletedPerson(gid);
	}

	@RequestMapping(value = "/viewer/landrecords/shownonnatural/{id}", method = RequestMethod.GET)
	@ResponseBody
	public List<SocialTenureRelationship> showDeletedNonNatural(
			@PathVariable Long id) {
		ArrayList<SocialTenureRelationship> objtemp = new ArrayList<SocialTenureRelationship>();
		List<SocialTenureRelationship> obj = landRecordsService
				.showDeletedPerson(id);
		for (int i = 0; i < obj.size(); i++) {
			if (obj.get(i).getPerson_gid().getPerson_type_gid()
					.getPerson_type_gid() == 2)
				objtemp.add(obj.get(i));
		}

		return objtemp;
	}

	@RequestMapping(value = "/viewer/landrecords/addnonnatural/{gid}", method = RequestMethod.GET)
	@ResponseBody
	public boolean addDeletedNonNatural(@PathVariable Long gid) {
		return landRecordsService.addDeletedPerson(gid);
	}


	@RequestMapping(value = "/viewer/landRecords/check/naturalimage/{person_gid}/{admin_id}", method = RequestMethod.GET)
	@ResponseBody
	public List<String> naturalImageUrl(@PathVariable Long person_gid,
			@PathVariable Long admin_id) {
		SourceDocument sourcetemp = new SourceDocument();
		ArrayList<String> resulttmp = new ArrayList<String>();
		if (person_gid != 0)
			sourcetemp = landRecordsService.getdocumentByPerson(person_gid);

		else if (admin_id != 0)
			sourcetemp = landRecordsService.getdocumentByAdminId(admin_id);

		if (sourcetemp != null && sourcetemp.isActive()) {
			resulttmp.add(sourcetemp.getEntity_name());
			resulttmp.add(sourcetemp.getLocScannedSourceDoc() + "/"
					+ sourcetemp.getGid() + ".jpg");

			return resulttmp;
		} else {
			return resulttmp;
		}

	}

	@RequestMapping(value = "/viewer/landrecords/upload/personimage/", method = RequestMethod.POST)
	@ResponseBody
	public String uploadNaturalImage(MultipartHttpServletRequest request,
			HttpServletResponse response, Principal principal) {
		try {

			Long usin = 0l;
			String document_name = "";
			String document_comments = "";
			String projectName = "";
			long person_gid = 0;
			long admin_id = 0;

			Iterator<String> file = request.getFileNames();

			usin = ServletRequestUtils.getRequiredLongParameter(request,
					"Usin_Upload");
			document_name = ServletRequestUtils.getRequiredStringParameter(
					request, "document_name");
			try {
				projectName = ServletRequestUtils.getRequiredStringParameter(
						request, "proj_name");
			} catch (Exception e) {
				logger.error(e);

			}

			try {
				person_gid = ServletRequestUtils.getRequiredLongParameter(
						request, "person_gid");
			} catch (Exception e) {
				logger.error(e);

			}
			try {
				admin_id = ServletRequestUtils.getRequiredLongParameter(
						request, "admin_id");
			} catch (Exception e) {
				logger.error(e);

			}
			/*
			 * try { document_comments=
			 * ServletRequestUtils.getRequiredStringParameter(request,
			 * "document_comments"); } catch (Exception e2) { logger.error(e2);
			 * 
			 * }
			 */

			byte[] document = null;
			while (file.hasNext()) {
				String fileName = file.next();
				MultipartFile mpFile = request.getFile(fileName);

				String originalFileName = mpFile.getOriginalFilename();
				SourceDocument sourceDocument1 = new SourceDocument();
				if (person_gid != 0)
					sourceDocument1 = landRecordsService
					.getdocumentByPerson(person_gid);
				if (admin_id != 0)
					sourceDocument1 = landRecordsService
					.getdocumentByAdminId(admin_id);
				if (sourceDocument1 == null)
					sourceDocument1 = new SourceDocument();
				String fileExtension = originalFileName.substring(
						originalFileName.indexOf(".") + 1,
						originalFileName.length()).toLowerCase();

				if (originalFileName != "") {
					document = mpFile.getBytes();
				}
				String uploadFileName = null;

				String tmpDirPath = request.getSession().getServletContext()
						.getRealPath(File.separator);

				String outDirPath = tmpDirPath.replace("mast", "")
						+ "resources" + File.separator + "documents"
						+ File.separator + projectName + File.separator
						+ "multimedia";

				File outDir = new File(outDirPath);
				boolean exists = outDir.exists();
				if (!exists) {
					(new File(outDirPath)).mkdirs();

				}

				sourceDocument1.setScanedSourceDoc(originalFileName);

				uploadFileName = ("resources/documents/" + projectName + "/multimedia");

				sourceDocument1.setLocScannedSourceDoc(uploadFileName);
				if (person_gid != 0)
					sourceDocument1.setPerson_gid(person_gid);
				if (admin_id != 0)
					sourceDocument1.setAdminid(admin_id);
				sourceDocument1.setEntity_name(document_name);
				sourceDocument1.setComments(document_comments);

				sourceDocument1.setActive(true);
				try {
					sourceDocument1.setRecordation(new Date());
					sourceDocument1.setUsin(usin);
				} catch (Exception e1) {
					logger.error(e1);
				}
				sourceDocument1 = landRecordsService
						.saveUploadedDocuments(sourceDocument1);

				Integer id = sourceDocument1.getGid();

				try {
					FileOutputStream uploadfile = new FileOutputStream(
							outDirPath + File.separator + id + "."
									+ fileExtension);
					uploadfile.write(document);
					uploadfile.flush();
					uploadfile.close();
					// use it for compression if needed
					// return compressPicture(outDirPath,id,fileExtension);
					return "Success";

				}

				catch (Exception e) {

					logger.error(e);
					return "Error";
				}

			}

		} catch (Exception e) {
			logger.error(e);
			return "Error";
		}
		return "false";
	}

	// Commented on 24-08-2016

	/*
	 * @RequestMapping(value = "/viewer/landrecords/administrator/{id}", method
	 * = RequestMethod.GET)
	 * 
	 * @ResponseBody public PersonAdministrator findadminByID(@PathVariable Long
	 * id) { return landRecordsService.getAdministratorById(id); }
	 * 
	 * 
	 * 
	 * @RequestMapping(value = "/viewer/landrecords/updateadmin/{id}", method =
	 * RequestMethod.POST)
	 * 
	 * @ResponseBody public PersonAdministrator updateAdmin(HttpServletRequest
	 * request, HttpServletResponse response, @PathVariable Long id) { Long
	 * adminid=0L; String first_name=""; String middle_name=""; String
	 * last_name=""; long genderid=0; int maritalid=0; int age=0; String
	 * citizenship=""; int resident; String mobile_number=""; String address="";
	 * Long usin=0L;
	 * 
	 * PersonAdministrator personobj= new PersonAdministrator(); try { try {
	 * adminid = ServletRequestUtils.getRequiredLongParameter(request,
	 * "adminId"); } catch (Exception e2) { logger.error(e2); } first_name =
	 * ServletRequestUtils.getRequiredStringParameter(request, "admin_fname");
	 * middle_name = ServletRequestUtils.getRequiredStringParameter(request,
	 * "admin_mname"); last_name =
	 * ServletRequestUtils.getRequiredStringParameter(request, "admin_lname");
	 * mobile_number = ServletRequestUtils.getRequiredStringParameter(request,
	 * "admin_mobile");
	 * 
	 * try { age= ServletRequestUtils.getRequiredIntParameter(request,
	 * "admin_age"); } catch (Exception e1) { logger.error(e1); } maritalid=
	 * ServletRequestUtils.getRequiredIntParameter(request,
	 * "admin_marital_status"); address =
	 * ServletRequestUtils.getRequiredStringParameter(request, "admin_address");
	 * 
	 * genderid = ServletRequestUtils.getRequiredLongParameter(request,
	 * "admin_gender");
	 * 
	 * ServletRequestUtils.getRequiredStringParameter(request,
	 * "projectname_key");
	 * 
	 * resident= ServletRequestUtils.getRequiredIntParameter(request,
	 * "admin_resident"); citizenship=
	 * ServletRequestUtils.getRequiredStringParameter(request,
	 * "admin_citizenship"); usin=
	 * ServletRequestUtils.getRequiredLongParameter(request, "admin_usin");
	 * 
	 * if(adminid!=0){ personobj.setAdminid(adminid); }
	 * personobj.setFirstname(first_name); personobj.setMiddlename(middle_name);
	 * personobj.setLastname(last_name);
	 * personobj.setPhonenumber(mobile_number);
	 * personobj.setCitizenship(citizenship); personobj.setAddress(address);
	 * personobj.setActive(true); if(age!=0){ personobj.setAge(age); }
	 * if(genderid!=0) { Gender genderIdObj =
	 * landRecordsService.findGenderById(genderid);
	 * personobj.setGender(genderIdObj);
	 * 
	 * }
	 * 
	 * personobj.setResident(false); if(resident==1) {
	 * personobj.setResident(true);
	 * 
	 * }
	 * 
	 * if(maritalid!=0){
	 * 
	 * MaritalStatus maritalIdObj =
	 * landRecordsService.findMaritalById(maritalid);
	 * personobj.setMaritalstatus(maritalIdObj); }
	 * //landRecordsService.editAdmin(personobj);
	 * 
	 * //For updating Spacial Unit Administrator
	 * 
	 * if (adminid!=0) { return landRecordsService.editAdmin(personobj);
	 * 
	 * }
	 * 
	 * else {
	 * 
	 * SpatialunitPersonadministrator spaobj = new
	 * SpatialunitPersonadministrator();
	 * spaobj.setPersonAdministrator(personobj); spaobj.setUsin(usin);
	 * landRecordsService.updateSpatialAdmin(spaobj); return personobj; }
	 * 
	 * 
	 * 
	 * //For updating in Attribute Values Table try {
	 * userDataService.updateNaturalPersonAttribValues(personobj,project_name);
	 * } catch (Exception e) { logger.error(e); } } catch
	 * (ServletRequestBindingException e) { logger.error(e); return null; }
	 * 
	 * }
	 */
	@RequestMapping(value = "/viewer/landrecords/deleteadmin/{id}", method = RequestMethod.GET)
	@ResponseBody
	public boolean deleteAdmin(@PathVariable Long id) {

		return landRecordsService.deleteAdminById(id);

	}

	// Commented on 24-08-2016
	/*
	 * @RequestMapping(value = "/viewer/landrecords/existingadmin/{id}", method
	 * = RequestMethod.GET)
	 * 
	 * @ResponseBody public List<PersonAdministrator>
	 * deletedAdminList(@PathVariable Long id) { List<Long> adminList=
	 * landRecordsService.getAdminId(id); List<PersonAdministrator>
	 * personadminList= new ArrayList<PersonAdministrator>();
	 * if(adminList!=null){ for (Long adminID: adminList) {
	 * 
	 * PersonAdministrator
	 * personadmin=landRecordsService.getAdministratorById(adminID);
	 * if(!personadmin.getActive()) personadminList.add(personadmin); } } return
	 * personadminList; }
	 */

	@RequestMapping(value = "/viewer/landrecords/addadmin/{adminId}", method = RequestMethod.GET)
	@ResponseBody
	public boolean existingadminList(@PathVariable Long adminId) {
		return landRecordsService.addAdminById(adminId);

	}

	@RequestMapping(value = "/viewer/landrecords/naturalcustom/{project}", method = RequestMethod.GET)
	@ResponseBody
	public List<String> naturalCustom(@PathVariable String project) {

		return landRecordsService.findnaturalCustom(project);

	}

	@RequestMapping(value = "/viewer/landrecords/personofinterest/{usin}", method = RequestMethod.GET)
	@ResponseBody
	public List<SpatialunitPersonwithinterest> personWithInterest(@PathVariable Long usin) {
		//ArrayList<String> tempList = new ArrayList<String>();
		List<SpatialunitPersonwithinterest> personList = landRecordsService
				.findpersonInterestByUsin(usin);
		/*	for (int i = 0; i < personList.size(); i++) {
			tempList.add(personList.get(i).getPersonName());

		}*/
		return personList;
	}

	@RequestMapping(value = "/viewer/landrecords/deceasedperson/{usin}", method = RequestMethod.GET)
	@ResponseBody
	public List<SpatialunitDeceasedPerson> deceasedPerson(
			@PathVariable Long usin) {

		return landRecordsService.findDeceasedPersonByUsin(usin);

	}

	@RequestMapping(value = "/viewer/landrecords/autogenerateuka/{project}", method = RequestMethod.GET)
	@ResponseBody
	public boolean autogenerateUKA(@PathVariable String project) {
		/*
		 * List<SpatialUnitTemp> spatialunit
		 * =landRecordsService.findSpatialUnitforUKAGeneration(project); String
		 * village
		 * =projectService.findProjectByName(project).getProjectAreas().get
		 * (0).getVillage_code();
		 * 
		 * try { for (int i = 0; i < spatialunit.size(); i++) { SpatialUnitTemp
		 * spatialunitmp=spatialunit.get(i);
		 * spatialunitmp.setPropertyno(village+
		 * "/"+spatialunitmp.getHamlet_Id().getHamletCode
		 * ()+"/"+(spatialunitmp.getHamlet_Id().getCount()+1));
		 * spatialunitmp.getHamlet_Id
		 * ().setCount(spatialunitmp.getHamlet_Id().getCount()+1);
		 * projectService.saveHamlet(spatialunitmp.getHamlet_Id());
		 * landRecordsService.addSpatialUnitTemp(spatialunitmp);
		 * 
		 * } } catch (Exception e) { logger.error(e); } return true;
		 */
		List<ProjectHamlet> projecthamlets = projectService
				.findHamletByProject(project);
		String village = projectService.findProjectByName(project)
				.getProjectAreas().get(0).getVillage_code();
		try {
			for (int i = 0; i < projecthamlets.size(); i++) {
				String hamletCode = projecthamlets.get(i).getHamletCode();
				int count = 0;
				List<Long> usinList = landRecordsService
						.findUsinforUKAGeneration(project, hamletCode);

				for (int j = 0; j < usinList.size(); j++) {
					String uka = village + "/" + hamletCode + "/"
							+ (projecthamlets.get(i).getCount() + j + 1);

					count = projecthamlets.get(i).getCount() + j + 1;
					landRecordsService.updateUKAnumber(usinList.get(j), uka);

				}
				if (count != 0) {
					projecthamlets.get(i).setCount(count);
					projectService.saveHamlet(projecthamlets.get(i));

				}

			}
			return true;

		} catch (Exception e) {
			logger.error(e);
			return false;
		}

	}

	@RequestMapping(value = "/viewer/landrecords/updatehamlet/{project}", method = RequestMethod.POST)
	@ResponseBody
	public boolean updateUkaNum(HttpServletRequest request,
			HttpServletResponse response, @PathVariable String project) {
		long Usin = 0;
		long HamletId = 0;

		// SpatialUnitTable spatialunit=new SpatialUnitTable();
		try {
			Usin = ServletRequestUtils.getRequiredLongParameter(request,
					"primeryky");
			HamletId = ServletRequestUtils.getRequiredLongParameter(request,
					"hamlet_id");

			projectService.findProjectByName(project)
			.getProjectAreas().get(0).getVillage_code();
			SpatialUnitTable spatialunit = (SpatialUnitTable) landRecordsService
					.findSpatialUnitbyId(Usin).get(0);
			ProjectHamlet projectHamlet = projectService
					.findHamletById(HamletId);
			/*spatialunit.setHamlet_Id(projectHamlet);*/
			/*
			spatialunit.setPropertyno(village + "/"
					+ projectHamlet.getHamletCode() + "/"
					+ (projectHamlet.getCount() + 1));
			projectHamlet.setCount(projectHamlet.getCount() + 1);*/
			projectService.addHamlets(projectHamlet);
			return landRecordsService.update(spatialunit);

		} catch (ServletRequestBindingException e) {

			logger.error(e);
			return false;

		}
	}

	@RequestMapping(value = "/viewer/landrecords/personwithinterest/{usin}", method = RequestMethod.GET)
	@ResponseBody
	public List<SpatialunitPersonwithinterest> nxtTokin(@PathVariable Long usin) {

		List<SpatialunitPersonwithinterest> personList = landRecordsService
				.findpersonInterestByUsin(usin);

		return personList;

	}

	@RequestMapping(value = "/viewer/landrecords/updatepwi", method = RequestMethod.POST)
	@ResponseBody
	public boolean updatepwi(HttpServletRequest request,
			HttpServletResponse response) {
		long Usin = 0;

		long id = 0;
		String firstName="";
		String middleName="";
		String lastName ="";
		String address="";
		String refrenceID="";
		long gender=0;
		SpatialunitPersonwithinterest spi = new SpatialunitPersonwithinterest();

		try {
			Usin = ServletRequestUtils.getRequiredLongParameter(request,
					"usin_kin");
			firstName = ServletRequestUtils.getRequiredStringParameter(request,
					"fname_kin");
			middleName = ServletRequestUtils.getRequiredStringParameter(request,
					"mname_kin");
			lastName = ServletRequestUtils.getRequiredStringParameter(request,
					"lname_kin");
			address = ServletRequestUtils.getRequiredStringParameter(request,
					"address_kin");
			refrenceID = ServletRequestUtils.getRequiredStringParameter(request,
					"ref_id_kin");
			gender =ServletRequestUtils.getRequiredIntParameter(request, "gender_kin");
			id = ServletRequestUtils
					.getRequiredLongParameter(request, "id_kin");

			if (id != 0)
				spi.setId(id);
			spi.setPersonName(firstName);
			spi.setUsin(Usin);
			spi.setMiddle_name(middleName);
			spi.setLast_name(lastName);
			spi.setAddress(address);
			spi.setIdcard_refrence(refrenceID);
			if (gender != 0) {
				Gender genderIdObj = landRecordsService
						.findGenderById(gender);
				spi.setGender(genderIdObj);

			}
			return landRecordsService.addnxtTokin(spi);

		} catch (ServletRequestBindingException e) {

			logger.error(e);
			return false;

		}
	}

	@RequestMapping(value = "/viewer/landrecords/deletekin/{id}", method = RequestMethod.GET)
	@ResponseBody
	public boolean deletePersonWithInterest(@PathVariable Long id) {

		return landRecordsService.deletePersonWithInterest(id);

	}

	@RequestMapping(value = "/viewer/landrecords/personsubtype", method = RequestMethod.GET)
	@ResponseBody
	public List<PersonType> PersonTypelst() {

		return landRecordsService.AllPersonType();

	}

	@RequestMapping(value = "/viewer/landrecords/hamletbyusin/{usin}", method = RequestMethod.GET)
	@ResponseBody
	public List<SpatialUnitTable> hamletName(@PathVariable long usin) {

		return landRecordsService.findSpatialUnitbyId(usin);

	}

	@RequestMapping(value = "/viewer/landrecords/updatedeceased", method = RequestMethod.POST)
	@ResponseBody
	public boolean updateDeceasedPerson(HttpServletRequest request,
			HttpServletResponse response) {
		long Usin = 0;
		String firstname = "";
		String middlename = "";
		String lastname = "";
		long id = 0;

		SpatialunitDeceasedPerson spdeceased = new SpatialunitDeceasedPerson();

		try {
			Usin = ServletRequestUtils.getRequiredLongParameter(request,
					"usin_deceased");
			firstname = ServletRequestUtils.getRequiredStringParameter(request,
					"d_firstname");
			middlename = ServletRequestUtils.getRequiredStringParameter(
					request, "d_middlename");
			lastname = ServletRequestUtils.getRequiredStringParameter(request,
					"d_lastname");
			id = ServletRequestUtils.getRequiredLongParameter(request,
					"deceased_key");

			if (id != 0)
				spdeceased.setId(id);
			spdeceased.setFirstname(firstname);
			spdeceased.setMiddlename(middlename);
			spdeceased.setLastname(lastname);
			spdeceased.setUsin(Usin);

			return landRecordsService.saveDeceasedPerson(spdeceased);

		} catch (ServletRequestBindingException e) {

			logger.error(e);
			return false;

		}
	}

	@RequestMapping(value = "/viewer/landrecords/deletedeceased/{id}", method = RequestMethod.GET)
	@ResponseBody
	public boolean deleteDeceasedPerson(@PathVariable Long id) {

		return landRecordsService.deleteDeceasedPerson(id);

	}

	@RequestMapping(value = "/viewer/landrecords/validator/{id}", method = RequestMethod.GET)
	@ResponseBody
	public String personValidator(@PathVariable Long id) {

		List<SocialTenureRelationship> socialTenureRelationshiptmp = landRecordsService
				.findAllSocialTenureByUsin(id);
		List<SpatialunitPersonwithinterest> spatialunitPersonwithinteresttmp = landRecordsService
				.findpersonInterestByUsin(id);
		List<SpatialunitDeceasedPerson> spatialunitDeceasedPersontmp = landRecordsService
				.findDeceasedPersonByUsin(id);
		List<String> personSubType = new ArrayList<String>();
		List<String> personAge = new ArrayList<String>();

		for (int i = 0; i < socialTenureRelationshiptmp.size(); i++) {

			if (socialTenureRelationshiptmp.get(i).getPerson_gid()
					.getPerson_type_gid().getPerson_type_gid() == 1)

			{

				NaturalPerson naturalPerson = (NaturalPerson) landRecordsService
						.naturalPersonById(
								socialTenureRelationshiptmp.get(i)
								.getPerson_gid().getPerson_gid())
								.get(0);

				try {
					if (naturalPerson.getPersonSubType().getPerson_type_gid() == 3) {
						personSubType.add("Owner");
						if (naturalPerson.getAge() >= 18) {
							personAge.add("Adult");
						} else {
							personAge.add("Minor");
						}
					}

					else if (naturalPerson.getPersonSubType()
							.getPerson_type_gid() == 4)
						personSubType.add("Admin");
					else if (naturalPerson.getPersonSubType()
							.getPerson_type_gid() == 5)
						personSubType.add("Guard");
				} catch (Exception e) {
					logger.error(e);
					return "Please specify person subtype";
				}

				if (personSubType.size() == socialTenureRelationshiptmp.size()) {
					// Case-1 for Owner and Admin
					if (socialTenureRelationshiptmp.get(i).getShare_type()
							.getGid() == 4) {
						if ((!personSubType.contains("Guard") && personSubType
								.contains("Owner"))
								&& personSubType.contains("Admin")) {
							if (spatialunitDeceasedPersontmp.size() > 1)
								return "Only one deceased persons are allowed";
							/*
							 * if(personSubType.size()>2) return
							 * "Only two administrators are allowed";
							 */
							if (personAge.contains("Adult")) {

								if (spatialunitDeceasedPersontmp.size() > 0) {
									/*
									 * if(spatialunitPersonwithinteresttmp.size()
									 * >0) { return "Success";
									 * 
									 * } else{ return "Add person of interest";
									 * 
									 * }
									 */
									return "Success";
								} else {

									return "Add deceased person";
								}
							} else {

								return "Owner is minor";
							}

						} else if ((!personSubType.contains("Guard") && !personSubType
								.contains("Owner"))
								&& personSubType.contains("Admin")) {

							if (spatialunitDeceasedPersontmp.size() > 0
									&& spatialunitDeceasedPersontmp.size() <= 1) {

								return "Success";
							} else if (spatialunitDeceasedPersontmp.size() > 1) {
								return "Only one deceased persons are allowed";
							} else if (spatialunitDeceasedPersontmp.size() == 0) {
								return "Add deceased person";

							}
						} else {
							return "Tenure type is tenancy in probate,provide inputs accordingly";
						}

					}

					// Case-2 for Only Owner
					if (socialTenureRelationshiptmp.get(i).getShare_type()
							.getGid() == 1
							|| socialTenureRelationshiptmp.get(i)
							.getShare_type().getGid() == 2
							|| socialTenureRelationshiptmp.get(i)
							.getShare_type().getGid() == 3) {
						if ((!personSubType.contains("Admin") && !personSubType
								.contains("Guard"))
								&& personSubType.contains("Owner")) {
							if (spatialunitDeceasedPersontmp.size() > 0) {
								return "Remove deceased person";
							}

							else if (personAge.contains("Minor")) {
								return "All owner must be adult";
							}

							else if (socialTenureRelationshiptmp.get(i)
									.getShare_type().getGid() == 2) {
								if (personSubType.size() == 1) {
									return "Success";
								}

								else {
									return "For the selected tenure number of Owner must be one";
								}

							} else if ((socialTenureRelationshiptmp.get(i)
									.getShare_type().getGid() == 3 || socialTenureRelationshiptmp
									.get(i).getShare_type().getGid() == 1)) {
								if (personSubType.size() >= 2) {
									return "Success";
								}

								else {
									return "For the selected tenure number of Owner must be more than one";
								}

							}
							/*
							 * else {
							 * 
							 * return "Condition not fulfilled"; }
							 */
						} else {

							return "Tenure Mismatched";
						}
					}

					/*
					 * // Case-3 Only Admin if(personSubType.contains("Admin")
					 * && !personSubType.contains("Owner") &&
					 * !personSubType.contains("Guard")) {
					 * 
					 * if(spatialunitDeceasedPersontmp.size()>0 &&
					 * spatialunitDeceasedPersontmp.size()<=2) {
					 * 
					 * return "Success"; } else
					 * if(spatialunitDeceasedPersontmp.size()>2) { return
					 * "Only two deceased persons are allowed"; } else
					 * if(spatialunitDeceasedPersontmp.size()==0) { return
					 * "Add deceased person";
					 * 
					 * }
					 * 
					 * } else{ return
					 * "Tenure type is tenancy in probate,provide inputs accordingly"
					 * ; }
					 */

					// Case-4 Owner And Guardian

					if (socialTenureRelationshiptmp.get(i).getShare_type()
							.getGid() == 5) {
						if (personSubType.contains("Guard")
								&& personSubType.contains("Owner")
								&& !personSubType.contains("Admin")) {

							if (spatialunitDeceasedPersontmp.size() > 0) {
								return "Remove deceased person";
							}

							else if (personAge.contains("Adult")) {
								return "All Owner must be minor";
							}

							else if (spatialunitPersonwithinteresttmp.size() > 0) {
								return "Remove person of interest";

							} else {

								return "Success";
							}

						}

						else {

							return "Tenure type is Guardian(minor),provide inputs accordingly";
						}

					}

					/*
					 * // Case-5 Admin And Guardian
					 * if(personSubType.contains("Guard") &&
					 * personSubType.contains("Admin") &&
					 * !personSubType.contains("Owner")){
					 * 
					 * if(spatialunitDeceasedPersontmp.size()>0){
					 * 
					 * return "Remove guardian"; } else{ return
					 * "Remove administrator and add Minor"; }
					 * 
					 * }
					 * 
					 * // Case-6 Admin And Guardian and Owner
					 * 
					 * if(personSubType.contains("Guard") &&
					 * personSubType.contains("Admin") &&
					 * personSubType.contains("Owner")) { return "Invalid Data";
					 * 
					 * }
					 */
				}

			} else if (socialTenureRelationshiptmp.get(i).getPerson_gid()
					.getPerson_type_gid().getPerson_type_gid() == 2) {
				if (socialTenureRelationshiptmp.get(i).getShare_type().getGid() == 6)
					return "Success";
				else
					return "Tenure Mismatched";
			}

		}

		return "Invalid Data";

	}

	@RequestMapping(value = "/viewer/landrecords/deceasedpersonbyid/{usin}", method = RequestMethod.GET)
	@ResponseBody
	public ArrayList<String> deceasedPersonAdjudication(@PathVariable Long usin) {
		ArrayList<String> tempList = new ArrayList<String>();
		List<SpatialunitDeceasedPerson> deceasedobj = landRecordsService
				.findDeceasedPersonByUsin(usin);
		for (int i = 0; i < deceasedobj.size(); i++) {

			String name = deceasedobj.get(i).getFirstname() + " "
					+ deceasedobj.get(i).getMiddlename() + " "
					+ deceasedobj.get(i).getLastname();
			tempList.add(name);
		}
		return tempList;
	}

	@RequestMapping(value = "/viewer/landrecords/vertexlabel", method = RequestMethod.POST)
	@ResponseBody
	public boolean vertexLabel(HttpServletRequest request,
			HttpServletResponse response) {

		try {
			String vertexData = request.getParameter("vertexList");
			// ArrayList<Double> bbox=new ArrayList<Double>();
			if (landRecordsService.deleteAllVertexLabel())

			{

				String[] arr = vertexData.split(",");
				int serialId = 1;
				for (int i = 0; i < arr.length;) {

					landRecordsService.addAllVertexLabel(serialId, arr[i + 1],
							arr[i]);
					serialId++;
					i = i + 2;
				}

			}
			return true;
		} catch (Exception e) {
			logger.error(e);
			return false;
		}

	}

	@RequestMapping(value = "/viewer/landrecords/ccronew/{usin}", method = RequestMethod.GET)
	@ResponseBody
	public CcroDto ccroDetails(@PathVariable Long usin) {
		CcroDto tmpdto = new CcroDto();
		HashMap<String, String> personUrl = new HashMap<String, String>(); // for
		// url
		// against
		// person
		List<String> admin = new ArrayList<String>(); // for admin name
		List<String> name = new ArrayList<String>(); // for natural person name
		String institute = ""; // for institute name
		List<String> sharepercentage = new ArrayList<String>(); // for share
		// percentage in
		// case multiple
		// tenancy
		List<String> guardianName = new ArrayList<String>(); // for Guardian
		// List
		List<String> guardianUrl = new ArrayList<String>(); // for guardian Url
		HashMap<String, String> adminUrl = new HashMap<String, String>(); // for
		// admin
		// url
		Calendar cal = Calendar.getInstance();

		SpatialUnitTable spa = (SpatialUnitTable) landRecordsService
				.findSpatialUnitbyId(usin).get(0);
		ProjectArea projectArea = (ProjectArea) landRecordsService
				.findProjectArea(spa.getProject()).get(0);
		List<SocialTenureRelationship> socialtenure = landRecordsService
				.findAllSocialTenureByUsin(usin);
		HashMap<String, Long> personalogGid = new HashMap<String, Long>();

		List<String> deceasedPerson = new ArrayList<String>();

		List<SpatialunitDeceasedPerson> deceasedlst = deceasedPerson(usin);

		if (deceasedlst.size() != 0) {

			for (int j = 0; j < deceasedlst.size(); j++) {

				deceasedPerson.add(deceasedlst.get(j).getFirstname() + " "
						+ deceasedlst.get(j).getMiddlename() + " "
						+ deceasedlst.get(j).getLastname());
			}

		}

		for (int i = 0; i < socialtenure.size(); i++) {
			Person person = socialtenure.get(i).getPerson_gid();
			if (person.getPerson_type_gid().getPerson_type_gid() == 1) {
				NaturalPerson naturalPerson = (NaturalPerson) landRecordsService
						.naturalPersonById(person.getPerson_gid()).get(0);
				if (naturalPerson.getPersonSubType().getPerson_type_gid() == 3) {
					SourceDocument sourceDocument = landRecordsService
							.getdocumentByPerson(naturalPerson.getPerson_gid());
					String personname = naturalPerson.getFirstName() + " "
							+ naturalPerson.getLastName();
					if (naturalPerson.getMiddleName() != null)
						personname = naturalPerson.getFirstName() + " "
								+ naturalPerson.getMiddleName() + " "
								+ naturalPerson.getLastName();
					personUrl.put(personname, "Url");
					name.add(personname);

					personalogGid
					.put(personname, naturalPerson.getPerson_gid());

					sharepercentage.add(socialtenure.get(i)
							.getSharePercentage() + "%");
					if (sourceDocument != null && sourceDocument.isActive()) {

						String fileName = sourceDocument.getScanedSourceDoc();
						String fileType = fileName.substring(
								fileName.indexOf(".") + 1, fileName.length())
								.toLowerCase();
						personUrl.put(personname,
								sourceDocument.getLocScannedSourceDoc() + "/"
										+ sourceDocument.getGid() + "."
										+ fileType);
					}

				} else if (naturalPerson.getPersonSubType()
						.getPerson_type_gid() == 4) {
					SourceDocument sourceDocument = landRecordsService
							.getdocumentByPerson(naturalPerson.getPerson_gid());

					String personname = naturalPerson.getFirstName() + " "
							+ naturalPerson.getLastName();
					if (naturalPerson.getMiddleName() != null)
						personname = naturalPerson.getFirstName() + " "
								+ naturalPerson.getMiddleName() + " "
								+ naturalPerson.getLastName();
					adminUrl.put(personname, "Url");
					admin.add(personname);

					if (sourceDocument != null && sourceDocument.isActive()) {

						String fileName = sourceDocument.getScanedSourceDoc();
						String fileType = fileName.substring(
								fileName.indexOf(".") + 1, fileName.length())
								.toLowerCase();
						adminUrl.put(personname,
								sourceDocument.getLocScannedSourceDoc() + "/"
										+ sourceDocument.getGid() + "."
										+ fileType);
					}
				}

				else if (naturalPerson.getPersonSubType().getPerson_type_gid() == 5) {

					String personname = naturalPerson.getFirstName() + " "
							+ naturalPerson.getLastName();
					if (naturalPerson.getMiddleName() != null)
						personname = naturalPerson.getFirstName() + " "
								+ naturalPerson.getMiddleName() + " "
								+ naturalPerson.getLastName();
					guardianName.add(personname);
					SourceDocument sourceDocument = landRecordsService
							.getdocumentByPerson(naturalPerson.getPerson_gid());
					guardianUrl.add("Url");
					if (sourceDocument != null && sourceDocument.isActive()) {

						String fileName = sourceDocument.getScanedSourceDoc();
						String fileType = fileName.substring(
								fileName.indexOf(".") + 1, fileName.length())
								.toLowerCase();
						guardianUrl.add(sourceDocument.getLocScannedSourceDoc()
								+ "/" + sourceDocument.getGid() + "."
								+ fileType);
					}

				}

			}

			else if (person.getPerson_type_gid().getPerson_type_gid() == 2) {
				NonNaturalPerson nonNaturalPerson = (NonNaturalPerson) landRecordsService
						.nonnaturalPersonById(person.getPerson_gid()).get(0);
				NaturalPerson naturalPerson = (NaturalPerson) landRecordsService
						.naturalPersonById(nonNaturalPerson.getPoc_gid())
						.get(0);
				SourceDocument sourceDocument = landRecordsService
						.getdocumentByPerson(naturalPerson.getPerson_gid());
				String personname = naturalPerson.getFirstName() + " "
						+ naturalPerson.getLastName();
				if (naturalPerson.getMiddleName() != null)
					personname = naturalPerson.getFirstName() + " "
							+ naturalPerson.getMiddleName() + " "
							+ naturalPerson.getLastName();
				personUrl.put(personname, "Url");
				name.add(personname);
				institute = nonNaturalPerson.getInstitutionName();
				if (sourceDocument != null && sourceDocument.isActive()) {

					String fileName = sourceDocument.getScanedSourceDoc();
					String fileType = fileName.substring(
							fileName.indexOf(".") + 1, fileName.length())
							.toLowerCase();
					personUrl.put(personname,
							sourceDocument.getLocScannedSourceDoc() + "/"
									+ sourceDocument.getGid() + "." + fileType);
				}

			}

		}

		try {
			tmpdto.setPersonName_url(personUrl);
			tmpdto.setInstitute(institute);
			tmpdto.setName(name);
			tmpdto.setPerson_type(socialtenure.get(0).getPerson_gid()
					.getPerson_type_gid().getPerson_type_gid());
			tmpdto.setOwnership(socialtenure.get(0).getShare_type().getGid());
			tmpdto.setDlo(projectArea.getDistrictOfficer());
			tmpdto.setVillagechairman(projectArea.getVillageChairman());
			tmpdto.setVillageexecutive(projectArea.getApprovingExecutive());
			tmpdto.setNeighbour_east(spa.getNeighbor_east());
			tmpdto.setNeighbour_north(spa.getNeighbor_north());
			tmpdto.setNeighbour_south(spa.getNeighbor_south());
			tmpdto.setNeighbour_west(spa.getNeighbor_west());
			/*	tmpdto.setHamlet(spa.getHamlet_Id().getHamletName());
			tmpdto.setProposeduse(spa.getProposedUse().getLandUseType_sw());*/
			tmpdto.setAddress(projectArea.getAddress());
			tmpdto.setAdminName(admin);

			tmpdto.setSharepercentage(sharepercentage);
			tmpdto.setResident(socialtenure.get(0).isResident());
			/*	tmpdto.setUka(spa.getPropertyno());*/
			tmpdto.setUsin(spa.getUsin());
			tmpdto.setPersonwithGid(personalogGid);
			tmpdto.setGuardian(guardianName);
			tmpdto.setGuardianUrl(guardianUrl);
			tmpdto.setAdmin_url(adminUrl);

			tmpdto.setDeceasedPerson(deceasedPerson);

			// Added Survey Year
			cal.setTime(new Date());
			int surveyYear = cal.get(Calendar.YEAR);
			tmpdto.setSurveyYear(surveyYear);

		} catch (Exception e) {
			logger.error(e);
		}

		return tmpdto;
	}

	@RequestMapping(value = "/viewer/landrecords/updateshare", method = RequestMethod.POST)
	@ResponseBody
	public boolean updateShare(HttpServletRequest request,
			HttpServletResponse response) {
		int length_share = 0;

		try {
			length_share = ServletRequestUtils.getRequiredIntParameter(request,
					"length_person");

			if (length_share > 0) {
				for (int i = 0; i < length_share; i++) {

					String alias = "";
					long personGid = 0;
					try {
						alias = ServletRequestUtils.getRequiredStringParameter(
								request, "alias_person" + i);
						personGid = ServletRequestUtils
								.getRequiredLongParameter(request, "person_gid"
										+ i);
						landRecordsService.updateSharePercentage(alias,
								personGid);
					} catch (ServletRequestBindingException e) {
						logger.error(e);
						return false;
					}

				}

				return true;

			} else
				return false;
		} catch (ServletRequestBindingException e) {
			logger.error(e);
			return false;
		}

	}

	// compression method to compress image file

	@SuppressWarnings("unused")
	private String compressPicture(String outDirPath, Integer id,
			String fileExtension) {

		File compressedImageFile = new File(outDirPath + File.separator + id
				+ "." + fileExtension);
		float k = 1.0f;
		try {
			while (compressedImageFile.length() > 50 * 1024) {
				File input = new File(outDirPath + File.separator + id + "."
						+ fileExtension);
				BufferedImage image = ImageIO.read(input);

				compressedImageFile = new File(outDirPath + File.separator + id
						+ "." + fileExtension);
				OutputStream os = new FileOutputStream(compressedImageFile);

				Iterator<ImageWriter> writers = ImageIO
						.getImageWritersByFormatName(fileExtension);
				ImageWriter writer = (ImageWriter) writers.next();

				ImageOutputStream ios = ImageIO.createImageOutputStream(os);
				writer.setOutput(ios);

				ImageWriteParam param = writer.getDefaultWriteParam();

				param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
				param.setCompressionQuality((float) (0.5 * k));
				writer.write(null, new IIOImage(image, null, null), param);

				os.flush();
				os.close();
				ios.flush();
				ios.close();
				writer.dispose();

				k = (float) (k * 0.5);
			}
			return "Success";

		} catch (FileNotFoundException e) {
			logger.error(e);
			return "Error";
		} catch (IOException e) {
			logger.error(e);
			return "Error";
		}

	}

	@RequestMapping(value = "/viewer/landrecords/citizenship/", method = RequestMethod.GET)
	@ResponseBody
	public List<Citizenship> citizenList() {

		return landRecordsService.findAllCitizenShip();

	}

	@RequestMapping(value = "/viewer/landrecords/deletenaturalphoto/{id}", method = RequestMethod.GET)
	@ResponseBody
	public String deleteNaturalImage(@PathVariable Long id) {

		if (landRecordsService.checkActivePerson(id) == false) {
			return "false";
		} else {
			landRecordsService.deleteNaturalImage(id);
			return "true";
		}

	}

	@RequestMapping(value = "/viewer/landrecords/workflowLst/", method = RequestMethod.GET)
	@ResponseBody
	public List<Workflow> getAllworkflows() {
		List<Workflow> workflowLst = new ArrayList<Workflow>();



		workflowLst=landRecordsService.getAllworkFlow();

		return workflowLst;

	}

	@RequestMapping(value = "/viewer/landrecords/appnature/", method = RequestMethod.GET)
	@ResponseBody
	public List<NatureOfApplication> noaList() {

		return landRecordsService.findAllAppNature();

	}

	@RequestMapping(value = "/viewer/landrecords/mutationtype/", method = RequestMethod.GET)
	@ResponseBody
	public List<MutationType> mutationTypeList() {

		return landRecordsService.findAllMutationType();

	}

	@RequestMapping(value = "/viewer/landrecords/nop/", method = RequestMethod.GET)
	@ResponseBody
	public List<NatureOfPower> nopList() {

		return landRecordsService.findAllApplicantPowers();

	}

	@RequestMapping(value = "/viewer/landrecords/exportdata/", method = RequestMethod.GET)
	@ResponseBody
	public void exportData(HttpServletRequest request,HttpServletResponse response) throws FileNotFoundException, IOException {

		Workbook wb = new HSSFWorkbook();
		DecimalFormat df = new DecimalFormat("#.##");
		// Create a blank sheet
		Sheet sheet = wb.createSheet("new sheet");

		List<Object> vertexLst = landRecordsService.findAllVerteces();
		String[] columnList = { "Point", "X Coordonnées", "Y Coordonnées" };

		int rowCount = 0;

		Row row = sheet.createRow(rowCount++);

		int columnCount = 0;
		for (String header : columnList) {
			Cell cell = row.createCell(columnCount++);

			cell.setCellValue((String) header);

		}

		try {
			for (int i = 0; i < vertexLst.size(); i++) {
				row = sheet.createRow(rowCount++);
				columnCount = 0;

				Object[] obj = (Object[]) vertexLst.get(i);
				Integer id = (Integer) obj[0];
				Cell cell = row.createCell(columnCount++);
				cell.setCellValue(id);

				Double x = (Double) obj[1];
				cell = row.createCell(columnCount++);
				cell.setCellValue(df.format(x));

				Double y = (Double) obj[2];
				cell = row.createCell(columnCount++);
				cell.setCellValue(df.format(y));

			}

		} catch (Exception e) {
			logger.error(e);
		}

		response.setHeader("Content-Disposition", "attachment; filename=Coordonn_du_plan.xls");
		response.setContentType("text/xls");
		OutputStream out = response.getOutputStream();
		wb.write(out);
		out.flush();
		out.close();

	}

	@RequestMapping(value = "/viewer/landrecords/spatialunitbyworkflow/{project}/{startfrom}", method = RequestMethod.POST)
	@ResponseBody
	public List<LandRecordDto> spatialUnitbyWorkflowList(
			@PathVariable String project, @PathVariable Integer startfrom,
			HttpServletRequest request, HttpServletResponse response,
			Principal principal) {
		String username = principal.getName();
		int role = 0;
		User user = userService.findByUniqueName(username);
		Set<Role> roles = user.getRoles();
		Iterator<Role> itr = roles.iterator();
		while (itr.hasNext()) {

			role = itr.next().getId();
		}
		// return
		// projectService.findProjectTempByName(user.getDefaultproject());
		List<LandRecordDto> landDto = new ArrayList<LandRecordDto>();
		int[] mapped_workids = null;
		try {
			mapped_workids = ServletRequestUtils.getRequiredIntParameters(
					request, "workflow");
		} catch (ServletRequestBindingException e) {
			logger.error(e);
		}
		List<SpatialUnitTemp> tmp = new ArrayList<SpatialUnitTemp>();

		if (role == 9) {
			int[] workid = {4};
			tmp = landRecordsService.findAllSpatialUnitTemp(project, startfrom,workid);

		} else {

			tmp = landRecordsService.findAllSpatialUnitTemp(project, startfrom,mapped_workids);
		}

		//System.out.println("mapped workflows---" + mapped_workids);
		try {
			if(tmp!=null){
				for (SpatialUnitTemp spatialUnitTemp : tmp) {

					LandRecordDto dto = new LandRecordDto();
					SocialTenureRelationship socialtenure = landRecordsService.findAllSocialTenureByUsin(spatialUnitTemp.getUsin()).get(0);
					NaturalPerson naturalperson = landRecordsService.naturalPersonById(
							socialtenure.getPerson_gid().getPerson_gid()).get(0);
					dto.setApplication_no(spatialUnitTemp.getApplication_no()); // change to application no.
					dto.setApplication_stage(spatialUnitTemp.getStatus());
					dto.setApplication_type(socialtenure.getShare_type());
					dto.setFirstname(naturalperson.getFirstName());
					dto.setLastname(naturalperson.getLastName());
					dto.setParceltype(spatialUnitTemp.getParceltype_id());
					dto.setPv_no(spatialUnitTemp.getPv_no());
					dto.setWorkflow_stage(spatialUnitTemp.getWorkflow_id());
					dto.setUsin(spatialUnitTemp.getUsin());
					dto.setApfr_no(spatialUnitTemp.getApfr_no());
					dto.setSection(0);
					if(spatialUnitTemp.getSection()!=null)
					dto.setSection(spatialUnitTemp.getSection());
					landDto.add(dto);

				}

			}
		} catch (Exception e) {
			logger.error(e);
		}


		return landDto;

	}


	@RequestMapping(value = "/viewer/landrecords/actionapprove/{id}/{workflowId}", method = RequestMethod.POST)
	@ResponseBody
	public Integer actionApprove(@PathVariable Long id, Principal principal,@PathVariable Integer workflowId,
			HttpServletRequest request, HttpServletResponse response) {

		String comments="";
		try {
			comments = ServletRequestUtils.getRequiredStringParameter(request, "commentsStatus");
		} catch (ServletRequestBindingException e) {
			logger.error(e);
		}

		String username = principal.getName();
		User user = userService.findByUniqueName(username);
		long userid = user.getId();
		return landRecordsService.actionApprove(id, userid,workflowId,comments);

	}


	@RequestMapping(value = "/viewer/landrecords/actionreject/{id}/{workflowId}", method = RequestMethod.POST)
	@ResponseBody
	public Integer actionReject(@PathVariable Long id, Principal principal,@PathVariable Integer workflowId,
			HttpServletRequest request, HttpServletResponse response) {

		String comments="";
		try {
			comments = ServletRequestUtils.getRequiredStringParameter(request, "commentsStatus");
		} catch (ServletRequestBindingException e) {
			logger.error(e);
		}
		String username = principal.getName();
		User user = userService.findByUniqueName(username);
		long userid = user.getId();
		return landRecordsService.rejectApprove(id, userid,workflowId,comments);


	}

	@RequestMapping(value = "/viewer/landrecords/action/{id}/{workflowId}", method = RequestMethod.GET)
	@ResponseBody
	public List<ActionTools> actionToolList(@PathVariable Integer id,@PathVariable String workflowId)
	{
		List<ActionTools> actionList= new ArrayList<ActionTools>();

		try {

			actionList=landRecordsService.findallAction(id,workflowId);

		} catch (Exception e) {

			logger.error(e);
			return actionList;
		}



		return actionList;

	}


	@RequestMapping(value = "/viewer/landrecords/spatialunit/search/{project}/{startfrom}", method = RequestMethod.POST)
	@ResponseBody
	public List<LandRecordDto>  getSearch(Principal principal,@PathVariable String project,@PathVariable Integer startfrom,
			HttpServletRequest request, HttpServletResponse response) {


		String appno="";
		String pvno="";
		String apfr="";
		String name="";
		int apptype=0;
		int landregistry=0;
		int [] workids=null;
		int search_appStatus=0;

		try {
			appno = ServletRequestUtils.getRequiredStringParameter(request, "search_appno");
		} catch (ServletRequestBindingException e) {
			logger.error(e);
		}
		try {
			pvno = ServletRequestUtils.getRequiredStringParameter(request, "search_pv");
		} catch (ServletRequestBindingException e) {
			logger.error(e);
		}
		try {
			apfr = ServletRequestUtils.getRequiredStringParameter(request, "search_apfr");
		} catch (ServletRequestBindingException e) {
			logger.error(e);
		}
		try {
			name = ServletRequestUtils.getRequiredStringParameter(request, "search_name");
		} catch (ServletRequestBindingException e) {
			logger.error(e);
		}
		try {
			apptype = ServletRequestUtils.getRequiredIntParameter(request, "search_apptype");
		} catch (ServletRequestBindingException e) {
			logger.error(e);
		}
		try {
			workids = ServletRequestUtils.getRequiredIntParameters(request, "workflow");
		} catch (ServletRequestBindingException e) {
			logger.error(e);
		}
		try {
			landregistry = ServletRequestUtils.getRequiredIntParameter(request, "islandRegistry");
		} catch (ServletRequestBindingException e) {
			logger.error(e);
		}
		try {
			search_appStatus = ServletRequestUtils.getRequiredIntParameter(request, "search_appStatus");
		} catch (ServletRequestBindingException e) {
			logger.error(e);
		}
		

		String username = principal.getName();
		User user = userService.findByUniqueName(username);
		String projectname=user.getDefaultproject();

		if(user.getRoles().iterator().next().getId()==9){
			int [] workids1={4};
			workids=workids1;
		}

		if(project!=null && project!=null){
			projectname=project;
		}
		if(landregistry==1){
			int [] workids1={9,14}; //for landregistry
			workids=workids1;

		}

		//long userid = user.getId();

		List<?> spatialUnit = landRecordsService.getSearchResult(appno,pvno,apfr,name,apptype,workids,projectname,startfrom,search_appStatus);

		List<LandRecordDto> tmpList=new ArrayList<LandRecordDto>();
		try {
			if(spatialUnit!=null){

				for (int i = 0; i < spatialUnit.size(); i++) {

					LandRecordDto dto =new LandRecordDto();
					Object[] obj = (Object[]) spatialUnit.get(i);
					dto.setApplication_no((String)obj[0]);
					//dto.setApplication_stage(application_stage);
					Integer share=(Integer)obj[1];
					dto.setApplication_type(landRecordsService.findTenureType(share.longValue()));
					dto.setFirstname((String)obj[2]);
					dto.setLastname((String)obj[3]);
					dto.setPv_no((String)obj[4]);
					BigInteger usintmp = (BigInteger)obj[5];
					dto.setUsin(usintmp.longValue());
					Workflow workflow=landRecordsService.getWorkflowbyId((Integer)obj[6]);
					dto.setWorkflow_stage(workflow);
					BigInteger statustmp1 = (BigInteger)obj[7];
					Status statustmp=landRecordsService.getStatusById(statustmp1.intValue());
					dto.setApplication_stage(statustmp);
					Integer section = (Integer)obj[8];
					dto.setSection(0);
					if(section!=null)
					dto.setSection(section);
					dto.setApfr_no((String)obj[9]);
					tmpList.add(dto);

				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return tmpList;


	}

	@RequestMapping(value = "/viewer/landrecords/spatialunit/search/{project}", method = RequestMethod.POST)
	@ResponseBody
	public Integer getSearchCount(Principal principal,@PathVariable String project,
			HttpServletRequest request, HttpServletResponse response) {


		String appno="";
		String pvno="";
		String apfr="";
		String name="";
		int apptype=0;
		int landregistry=0;
		int [] workids=null;
		int search_appStatus=0;

		try {
			appno = ServletRequestUtils.getRequiredStringParameter(request, "search_appno");
		} catch (ServletRequestBindingException e) {
			logger.error(e);
		}
		try {
			pvno = ServletRequestUtils.getRequiredStringParameter(request, "search_pv");
		} catch (ServletRequestBindingException e) {
			logger.error(e);
		}
		try {
			apfr = ServletRequestUtils.getRequiredStringParameter(request, "search_apfr");
		} catch (ServletRequestBindingException e) {
			logger.error(e);
		}
		try {
			name = ServletRequestUtils.getRequiredStringParameter(request, "search_name");
		} catch (ServletRequestBindingException e) {
			logger.error(e);
		}
		try {
			apptype = ServletRequestUtils.getRequiredIntParameter(request, "search_apptype");
		} catch (ServletRequestBindingException e) {
			logger.error(e);
		}
		try {
			workids = ServletRequestUtils.getRequiredIntParameters(request, "workflow");
		} catch (ServletRequestBindingException e) {
			logger.error(e);
		}
		try {
			landregistry = ServletRequestUtils.getRequiredIntParameter(request, "islandRegistry");
		} catch (ServletRequestBindingException e) {
			logger.error(e);
		}
		try {
			search_appStatus = ServletRequestUtils.getRequiredIntParameter(request, "search_appStatus");
		} catch (ServletRequestBindingException e) {
			logger.error(e);
		}

		String username = principal.getName();
		User user = userService.findByUniqueName(username);
		String projectname=user.getDefaultproject();



		if(user.getRoles().iterator().next().getId()==9){
			int [] workids1={4};
			workids=workids1;
		}

		if(project!=null && project!=null){
			projectname=project;
		}

		if(landregistry==1){
			int [] workids1={9,14}; //for landregistry
			workids=workids1;

		}

		//long userid = user.getId();

		int searchRecords = landRecordsService.getSearchResult(appno,pvno,apfr,name,apptype,workids,projectname,search_appStatus);


		return searchRecords;


	}




	@RequestMapping(value = "/viewer/landrecords/spatialunit/form1/{usin}", method = RequestMethod.GET)
	@ResponseBody
	public Form1Dto  getform1(@PathVariable Long usin) {

		Form1Dto tmpLst=new Form1Dto();

		try {
			SocialTenureRelationship tenureTmp = landRecordsService.findAllSocialTenureByUsin(usin).get(0);
			SpatialUnitTable  spatialTmp= landRecordsService.findSpatialUnitbyId(usin).get(0);
			NaturalPerson natureTmp = landRecordsService.naturalPersonById(tenureTmp.getPerson_gid().getPerson_gid()).get(0);
			//Project projectTmp=projectService.findProjectByName(spatialTmp.getProject());

			tmpLst.setAddress(natureTmp.getAddress());
			tmpLst.setApplicationno(spatialTmp.getApplication_no());
			tmpLst.setArea(String.valueOf(spatialTmp.getArea()));
			tmpLst.setBirthdate(natureTmp.getDob());
			tmpLst.setBirthplace(natureTmp.getBirthplace());
			if(spatialTmp.getVillage_id()!=null){
				tmpLst.setCommune(spatialTmp.getVillage_id().getCommune().getCommuneNameFr());	
				tmpLst.setProvince(spatialTmp.getVillage_id().getCommune().getProvince().getProvinceNameFr());
				tmpLst.setRegion(spatialTmp.getVillage_id().getCommune().getProvince().getRegion().getRegionNameFr());
				tmpLst.setVillage(spatialTmp.getVillage_id().getVillageNameFr());
				tmpLst.setCfvname(spatialTmp.getUser().getName());
			}

			tmpLst.setDateofapplication(spatialTmp.getApplicationdate());
			tmpLst.setFathername(natureTmp.getFathername());
			tmpLst.setFirstname(natureTmp.getFirstName());
			tmpLst.setIssuancedate(spatialTmp.getIssuancedate());
			tmpLst.setLastname(natureTmp.getLastName());
			if(natureTmp.getMarital_status()!=null)
				tmpLst.setMaritialStatus(natureTmp.getMarital_status().getMaritalStatus_sw());
			tmpLst.setMothername(natureTmp.getMothername());
			if(spatialTmp.getNoa_id()!=null)
				tmpLst.setNatureofapplication(spatialTmp.getNoa_id().getNatureOfApplicationFr());
			if(natureTmp.getNop_id()!=null)
				tmpLst.setNatureofpower(natureTmp.getNop_id().getNatureOfPowerFr());
			tmpLst.setNeighbour_east(spatialTmp.getNeighbor_east());
			tmpLst.setNeighbour_north(spatialTmp.getNeighbor_north());
			tmpLst.setNeighbour_south(spatialTmp.getNeighbor_south());
			tmpLst.setNeighbour_west(spatialTmp.getNeighbor_west());
			tmpLst.setProfession(natureTmp.getOccupation());

			tmpLst.setReferenceofId(natureTmp.getIdcard());
			if(tenureTmp.getShare_type()!=null)
				tmpLst.setTypeoftenancy(tenureTmp.getShare_type().getShareType_sw());
			tmpLst.setTenancyId(tenureTmp.getShare_type().getGid());
			tmpLst.setVillageno(spatialTmp.getVillageno());

			tmpLst.setLocation(tmpLst.getCommune()+" "+tmpLst.getVillage());// check
			tmpLst.setIdcardestablishment_date(natureTmp.getIdcard_establishment_date());
			tmpLst.setIdcard_origin(natureTmp.getIdcard_origin());
			tmpLst.setMandate_establishmentDate(natureTmp.getMandate_issuanceDate());
			tmpLst.setMandate_location(natureTmp.getMandate_location());
			


		} catch (Exception e) {
			logger.error(e);
		}


		return tmpLst;

	}


	@RequestMapping(value = "/viewer/landrecords/spatialunit/form2/{usin}", method = RequestMethod.GET)
	@ResponseBody
	public Form2Dto  getForm2(@PathVariable Long usin) {

		Form2Dto form2=new Form2Dto();
		try {
			SocialTenureRelationship tenureTmp = landRecordsService.findAllSocialTenureByUsin(usin).get(0);
			SpatialUnitTable  spatialTmp= landRecordsService.findSpatialUnitbyId(usin).get(0);
			NaturalPerson natureTmp = landRecordsService.naturalPersonById(tenureTmp.getPerson_gid().getPerson_gid()).get(0);
			//Project projectTmp=projectService.findProjectByName(spatialTmp.getProject());

			List<SpatialunitPersonwithinterest> poiLst = landRecordsService.findpersonInterestByUsin(usin);
			form2.setAddress(natureTmp.getAddress());
			if(spatialTmp.getVillage_id()!=null){
				form2.setCommune(spatialTmp.getVillage_id().getCommune().getCommuneNameFr());
				form2.setVillage(spatialTmp.getVillage_id().getVillageNameFr());
				form2.setRegion(spatialTmp.getVillage_id().getCommune().getProvince().getRegion().getRegionNameFr());
				form2.setProvince(spatialTmp.getVillage_id().getCommune().getProvince().getProvinceNameFr());
				form2.setCfvname(spatialTmp.getUser().getName());

			}

			form2.setDateofMandate(natureTmp.getMandate_issuanceDate()); // change to mandate date
			form2.setFamily_name(spatialTmp.getFamily_name()); // change to family name
			form2.setIdEstablishDate(natureTmp.getIdcard_establishment_date());
			form2.setLocation(form2.getCommune()+" "+form2.getVillage());
			form2.setFirstname(natureTmp.getFirstName());
			form2.setLastname(natureTmp.getLastName());
			form2.setBirthdate(natureTmp.getDob());
			form2.setBirthplace(natureTmp.getBirthplace());
			form2.setRefrenceID(natureTmp.getIdcard());
			form2.setMandate_origin(natureTmp.getMandate_location());
			form2.setPoiLst(poiLst);
			
			
		} catch (Exception e) {
			logger.error(e);
		}

		return form2;


	}


	@RequestMapping(value = "/viewer/landrecords/spatialunit/form3/{usin}", method = RequestMethod.GET)
	@ResponseBody
	public Form3Dto  getForm3(@PathVariable Long usin) {

		Form3Dto form3=new Form3Dto();
		try {
			SocialTenureRelationship tenureTmp = landRecordsService.findAllSocialTenureByUsin(usin).get(0);
			SpatialUnitTable  spatialTmp= landRecordsService.findSpatialUnitbyId(usin).get(0);
			NaturalPerson natureTmp = landRecordsService.naturalPersonById(tenureTmp.getPerson_gid().getPerson_gid()).get(0);
			SpatialUnitExtension spatialunit_extensionTmp = landRecordsService.findAllSpatialUnitByUsin(usin);
			Project projectTmp=projectService.findProjectByName(spatialTmp.getProject());


			List<SpatialunitPersonwithinterest> poiLst = landRecordsService.findpersonInterestByUsin(usin);
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");


			if(spatialTmp.getVillage_id()!=null){
				form3.setRegion(spatialTmp.getVillage_id().getCommune().getProvince().getRegion().getRegionNameFr());
				form3.setProvince(spatialTmp.getVillage_id().getCommune().getProvince().getProvinceNameFr());
				form3.setCommune(spatialTmp.getVillage_id().getCommune().getCommuneNameFr());
				form3.setVillage(spatialTmp.getVillage_id().getVillageNameFr());
				form3.setCfv_president(spatialTmp.getVillage_id().getCfv_agent());
			}


			form3.setArea(spatialTmp.getArea());
			form3.setFirstname(natureTmp.getFirstName());
			form3.setLastname(natureTmp.getLastName());
			form3.setAddress(natureTmp.getAddress());
			form3.setDob(natureTmp.getDob());
			form3.setBirthplace(natureTmp.getBirthplace());
			if(tenureTmp.getShare_type()!=null)
				form3.setShare(tenureTmp.getShare_type().getShareType_sw());
			form3.setTennancytypeID(tenureTmp.getShare_type().getGid());
			/*if(spatialTmp.getExistingUse()!=null)
				form3.setExisting_use(spatialTmp.getExistingUse().getLandUseType_sw());*/

			List<LandUseType> existingList = landRecordsService.getExistingUseName(spatialTmp.getExisting_use());
			/*			if(spatialTmp.getExistingUse()!=null) */
			form3.setExisting_use(existingList);
			form3.setNeighbor_north(spatialTmp.getNeighbor_north());
			form3.setNeighbor_south(spatialTmp.getNeighbor_south());
			form3.setNeighbor_east(spatialTmp.getNeighbor_east());
			form3.setNeighbor_west(spatialTmp.getNeighbor_west());
			form3.setLot_no("000");
			form3.setParcel_no(spatialTmp.getUsin());
			form3.setSection_no(spatialTmp.getSection());
			
			form3.setFamilyname(natureTmp.getLastName()+" "+natureTmp.getFirstName()); //we have to add family name 

			/*	Calendar c1 = Calendar.getInstance();
			c1.setTime(spatialTmp.getSurveyDate()); // Now use today date.
			//form3.setContradictory_time(c1.getTime());
			 */			form3.setContradictory_date(spatialunit_extensionTmp.getContradictory_date());


			 /*	Calendar c = Calendar.getInstance();
			c.setTime(new Date()); // Now use today date.
			c.add(Calendar.DATE, 45); // Adding 45 days
			  */
			 form3.setPublic_notice_startdate(spatialTmp.getPublic_notice_startdate());
			 form3.setPublic_notice_enddate(spatialTmp.getPublic_notice_enddate());
			 form3.setOther_use(spatialTmp.getOtherUseType());
			 if(spatialTmp.getPublic_notice_startdate()==null)
			 {
				 form3.setFlag(true);
			 }
			 else{
				 form3.setFlag(false);
			 }

			 /*	//set public notice start and end date
			spatialTmp.setPublic_notice_startdate(new Date());
			spatialTmp.setPublic_notice_enddate(c.getTime());
			landRecordsService.update(spatialTmp);*/




		} catch (Exception e) {
			logger.error(e);
		}

		return form3;


	}




	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/viewer/landrecords/spatialunit/form7/{usin}", method = RequestMethod.GET)
	@ResponseBody
	public Form7Dto  getForm7(@PathVariable Long usin) {

		Form7Dto form7=new Form7Dto();
		try {
			SocialTenureRelationship tenureTmp = landRecordsService.findAllSocialTenureByUsin(usin).get(0);
			SpatialUnitTable  spatialTmp= landRecordsService.findSpatialUnitbyId(usin).get(0);
			NaturalPerson natureTmp = landRecordsService.naturalPersonById(tenureTmp.getPerson_gid().getPerson_gid()).get(0);
			SpatialUnitExtension spatialunit_extensionTmp = landRecordsService.findAllSpatialUnitByUsin(usin);
			List<SpatialunitPersonwithinterest> poiLst = landRecordsService.findpersonInterestByUsin(usin);
			

			if(spatialTmp.getVillage_id()!=null){
				form7.setRegion(spatialTmp.getVillage_id().getCommune().getProvince().getRegion().getRegionNameFr());
				form7.setProvince(spatialTmp.getVillage_id().getCommune().getProvince().getProvinceNameFr());
				form7.setCommune(spatialTmp.getVillage_id().getCommune().getCommuneNameFr());
				form7.setVillage(spatialTmp.getVillage_id().getVillageNameFr());
				form7.setCfv_president(spatialTmp.getVillage_id().getCfv_agent());
			}

			form7.setAddress(natureTmp.getAddress());
			form7.setApplication_year(spatialTmp.getApplicationdate().getYear()+1900);
			form7.setApplication_date(spatialTmp.getApplicationdate());
			form7.setApplication_dd(spatialTmp.getApplicationdate().getDate());
			
			//set application month
			switch(spatialTmp.getApplicationdate().getMonth()){
			case 1 :
				form7.setApplication_month("Janvier");
				break;
			case 2 :
				form7.setApplication_month("Février");
				break;
			case 3 :
				form7.setApplication_month("Mars");
				break;
			case 4 :
				form7.setApplication_month("Avril");
				break;
			case 5 :
				form7.setApplication_month("Mai");
				break;
			case 6 :
				form7.setApplication_month("Juin");
				break;
			case 7 :
				form7.setApplication_month("Juillet");
				break;
			case 8 :
				form7.setApplication_month("Août");
				break;
			case 9 :
				form7.setApplication_month("Septembre");
				break;
			case 10 :
				form7.setApplication_month("Octobre");
				break;
			case 11 :
				form7.setApplication_month("Novembre");
				break;
			case 12 :
				form7.setApplication_month("Décembre");
				break;
				default:
					
				
			}
			
			form7.setApplication_no(spatialTmp.getApplication_no());
			form7.setArea(spatialTmp.getArea().toString());
			
			//form7.setCommune(projectTmp.getProjectAreas().get(0).getCommune());
			if(spatialunit_extensionTmp !=null)
				form7.setDate_recognition_rights(spatialunit_extensionTmp.getParcel_generation_date()); //change to recognition date
			form7.setFamily_name(spatialTmp.getFamily_name()); //change to family name
			form7.setLocation(form7.getCommune()+" "+form7.getVillage());
			
			form7.setNeighbour_east(spatialTmp.getNeighbor_east());
			form7.setNeighbour_north(spatialTmp.getNeighbor_north());
			form7.setNeighbour_south(spatialTmp.getNeighbor_south());
			form7.setNeighbour_west(spatialTmp.getNeighbor_west());
			form7.setPoiLst(poiLst);
			form7.setProfession(natureTmp.getOccupation());
			form7.setVillage_no(spatialTmp.getVillageno());
			form7.setPublic_issuansedate(spatialTmp.getPublic_notice_startdate());
	
			if(tenureTmp.getShare_type().getGid()==7 && natureTmp.getGender().getGenderId()==1){
				form7.setName("M "+natureTmp.getLastName()+" "+natureTmp.getFirstName());
			}
			else if(tenureTmp.getShare_type().getGid()==7 && natureTmp.getGender().getGenderId()==2){
				form7.setName("Mme "+natureTmp.getLastName()+" "+natureTmp.getFirstName());
			}
			else if(tenureTmp.getShare_type().getGid()==8){
				form7.setName("famille "+natureTmp.getLastName()+" "+natureTmp.getFirstName());
			}
			/*form7.setArea_ares(spatialTmp.getArea()*100);
			form7.setArea_centiares(spatialTmp.getArea()*10000);*/
		} catch (Exception e) {
			logger.error(e);
		}

		return form7;


	}


	@RequestMapping(value = "/viewer/landrecords/spatialunit/form5/{usin}", method = RequestMethod.GET)
	@ResponseBody
	public Form5Dto  getForm5(@PathVariable Long usin) {

		Form5Dto form5=new Form5Dto();
		try {
			SocialTenureRelationship tenureTmp = landRecordsService.findAllSocialTenureByUsin(usin).get(0);
			SpatialUnitTable  spatialTmp= landRecordsService.findSpatialUnitbyId(usin).get(0);
			NaturalPerson natureTmp = landRecordsService.naturalPersonById(tenureTmp.getPerson_gid().getPerson_gid()).get(0);
			SpatialUnitExtension spatialunit_extensionTmp = landRecordsService.findAllSpatialUnitByUsin(usin);
			ProjectArea projectTmp=projectService.findProjectByName(spatialTmp.getProject()).getProjectAreas().get(0);


			if(spatialTmp.getVillage_id()!=null){
				form5.setRegion(spatialTmp.getVillage_id().getCommune().getProvince().getRegion().getRegionNameFr());
				form5.setProvince(spatialTmp.getVillage_id().getCommune().getProvince().getProvinceNameFr());
				form5.setCommune(spatialTmp.getVillage_id().getCommune().getCommuneNameFr());
				form5.setVillage(spatialTmp.getVillage_id().getVillageNameFr());
			}

			form5.setAddress(natureTmp.getAddress());
			form5.setApplication_date(spatialTmp.getApplicationdate());
			form5.setApplication_no(spatialTmp.getApplication_no());
			form5.setArea(spatialTmp.getArea().toString());
			form5.setApfr_date(spatialTmp.getApplicationdate()); //change to apfr date
			form5.setBirthplace(natureTmp.getBirthplace());
			form5.setDate_recognition_right(spatialTmp.getApplicationdate()); //change to recognition date
			form5.setDob(natureTmp.getDob());

			List<LandUseType> existingList = landRecordsService.getExistingUseName(spatialTmp.getExisting_use());
			form5.setExisting_use(existingList);
			/*	if(spatialTmp.getExistingUse()!=null)
				form5.setExisting_use(spatialTmp.getExistingUse().getLandUseType_sw());*/


			form5.setFirst_name(natureTmp.getFirstName());
			form5.setLast_name(natureTmp.getLastName());
			form5.setLocation(form5.getCommune()+" "+form5.getVillage());

			form5.setLot("000");
			form5.setParcel_no(spatialTmp.getUsin());
			form5.setSection(spatialTmp.getSection());
			
			form5.setMayor_name(projectTmp.getMayorname());
			form5.setNeighbour_east(spatialTmp.getNeighbor_east());
			form5.setNeighbour_north(spatialTmp.getNeighbor_north());
			form5.setNeighbour_south(spatialTmp.getNeighbor_south());
			form5.setNeighbour_west(spatialTmp.getNeighbor_west());

			form5.setProfession(natureTmp.getOccupation());
			form5.setPv_no(spatialTmp.getPv_no());
			form5.setRefrence_id_card(natureTmp.getIdcard());

			if(natureTmp.getGender()!=null)
				form5.setSex(natureTmp.getGender().getGender_sw());

			form5.setVillage_no(spatialTmp.getVillageno());
			form5.setOther_use(spatialTmp.getOtherUseType());
			form5.setApfrno(spatialTmp.getApfr_no());

		} catch (Exception e) {
			logger.error(e);
		}

		return form5;


	}


	@RequestMapping(value = "/viewer/landrecords/spatialunit/form8/{usin}", method = RequestMethod.GET)
	@ResponseBody
	public Form8Dto  getForm8(@PathVariable Long usin) {

		Form8Dto form8=new Form8Dto();
		try {
			SocialTenureRelationship tenureTmp = landRecordsService.findAllSocialTenureByUsin(usin).get(0);
			SpatialUnitTable  spatialTmp= landRecordsService.findSpatialUnitbyId(usin).get(0);
			NaturalPerson natureTmp = landRecordsService.naturalPersonById(tenureTmp.getPerson_gid().getPerson_gid()).get(0);
			//SpatialUnitExtension spatialunit_extensionTmp = landRecordsService.findAllSpatialUnitByUsin(usin);
			String mayorname=projectService.findProjectByName(spatialTmp.getProject()).getProjectAreas().get(0).getMayorname();
			List<SpatialunitPersonwithinterest> poiLst = landRecordsService.findpersonInterestByUsin(usin);


			if(spatialTmp.getVillage_id()!=null){

				form8.setRegion(spatialTmp.getVillage_id().getCommune().getProvince().getRegion().getRegionNameFr());
				form8.setProvince(spatialTmp.getVillage_id().getCommune().getProvince().getProvinceNameFr());
				form8.setCommune(spatialTmp.getVillage_id().getCommune().getCommuneNameFr());
				form8.setVillage(spatialTmp.getVillage_id().getVillageNameFr());
			}

			form8.setAddress(natureTmp.getAddress());
			form8.setApplication_date(spatialTmp.getApplicationdate());
			form8.setApplication_no(spatialTmp.getApplication_no());
			form8.setArea(spatialTmp.getArea().toString());
			form8.setApfr_date(spatialTmp.getApfr_date()); //change to apfr date
			form8.setBirthplace(natureTmp.getBirthplace());
			form8.setDate_recognition_right(spatialTmp.getApplicationdate()); //change to recognition date
			form8.setDob(natureTmp.getDob());

			List<LandUseType> existingList = landRecordsService.getExistingUseName(spatialTmp.getExisting_use());
			form8.setExisting_use(existingList);
			/*			if(spatialTmp.getExistingUse()!=null)
				form8.setExisting_use(spatialTmp.getExistingUse().getLandUseType_sw());*/

			form8.setFirst_name(natureTmp.getFirstName());
			form8.setLast_name(natureTmp.getLastName());
			form8.setLocation(form8.getCommune()+" "+form8.getVillage());
			form8.setMayor_name(mayorname);
			form8.setNeighbour_east(spatialTmp.getNeighbor_east());
			form8.setNeighbour_north(spatialTmp.getNeighbor_north());
			form8.setNeighbour_south(spatialTmp.getNeighbor_south());
			form8.setNeighbour_west(spatialTmp.getNeighbor_west());
			form8.setProfession(natureTmp.getOccupation());
			form8.setPv_no(spatialTmp.getPv_no());
			form8.setRefrence_id_card(natureTmp.getIdcard());
			if(natureTmp.getGender()!=null)
				form8.setSex(natureTmp.getGender().getGender_sw());
			form8.setVillage_no(spatialTmp.getVillageno());
			form8.setFamilyname(natureTmp.getFirstName()+" "+natureTmp.getLastName());
			form8.setFamily_address(natureTmp.getAddress());
			form8.setId_card_date_place(natureTmp.getIdcard()); // change to id establish date place;

			if(natureTmp.getNop_id()!=null)
				form8.setRefrenceof_mandate(natureTmp.getNop_id().getNatureOfPowerFr()); // change to refrence of mandate
			form8.setPoiLst(poiLst);
			/*form8.setArea_ares(spatialTmp.getArea()*100);
			form8.setArea_centiares(spatialTmp.getArea()*10000);*/
			form8.setOther_use(spatialTmp.getOtherUseType());
			
			form8.setApfrno(spatialTmp.getApfr_no());
			form8.setMandateDate(natureTmp.getMandate_issuanceDate());



		} catch (Exception e) {
			logger.error(e);
		}

		return form8;


	}



	@RequestMapping(value = "/viewer/landrecords/poi/{id}", method = RequestMethod.GET)
	@ResponseBody
	public SpatialunitPersonwithinterest personWithInterestByID(@PathVariable Long id)
	{

		try {
			return landRecordsService.findPOIById(id);
		} catch (Exception e) {
			logger.error(e);
			return null;
		}

	}
	@RequestMapping(value = "/viewer/landrecords/neighbour/{id}", method = RequestMethod.GET)
	@ResponseBody
	public BoundaryMapDto neighbourstByUsin(@PathVariable Long id,Principal principal)
	{

		try {
			
			SocialTenureRelationship tenureTmp = landRecordsService.findAllSocialTenureByUsin(id).get(0);
			SpatialUnitTable  spatialTmp= landRecordsService.findSpatialUnitbyId(id).get(0);
			NaturalPerson natureTmp = landRecordsService.naturalPersonById(tenureTmp.getPerson_gid().getPerson_gid()).get(0);

			BoundaryMapDto tmpDto=new BoundaryMapDto();
			tmpDto.setApplication_no(spatialTmp.getApplication_no());
			tmpDto.setApplicationdate(spatialTmp.getApplicationdate());
			tmpDto.setName(natureTmp.getFirstName()+" "+natureTmp.getLastName());
			tmpDto.setNeighbourLst(landRecordsService.findNeighbourstByUsin(id));
			tmpDto.setVillagename(spatialTmp.getVillage_id().getVillageName());
			if(spatialTmp.getWorkflow_id().getWorkflowId()==4)
			{
				// 4 is the workflow id i.e Approve
				// 3 is the workflow status i.e Process Application
				try {
					long userId = landRecordsService.findSFRname(id,4,3);
					tmpDto.setSfr_name(userService.findUserByUserId((int) userId).getName());
				} catch (Exception e) {
				logger.error(e);
				}
				
			}
			else{
			tmpDto.setSfr_name(userService.findUserByName(principal.getName()).getName());
			}
			//tmpDto.setSfr_name(principal.getName());
			tenureTmp=null;
			spatialTmp=null;
			natureTmp=null;

			return tmpDto ;
		} catch (Exception e) {
			logger.error(e);
			return null;
		}

	}
	/*	@RequestMapping(value = "/viewer/landrecords/getbounds/{id}/{project}", method = RequestMethod.GET)
	@ResponseBody
	public List<String> getBbox(@PathVariable long id ,@PathVariable String project)
	{
		List<String> tmplst=new ArrayList<String>();
		try {
			SpatialUnit spa= landRecordsService.findBbox(id,project).get(0);

			tmplst.add(spa.getExtent());
		} catch (Exception e) {
		logger.error(e);

		}
		return tmplst;

	}*/

	@RequestMapping(value = "/viewer/landrecords/comments/{id}", method = RequestMethod.GET)
	@ResponseBody
	public List<SpatialUnitStatusHistory> findStatusHistorytByUsin(@PathVariable Long id)
	{

		try {
			return landRecordsService.findStatusHistorytByUsin(id);
		} catch (Exception e) {
			logger.error(e);
			return null;
		}

	}
	/*


	 */

	@RequestMapping(value = "/viewer/landrecords/parcelcountbytenure/{project}/{tag}/{villageId}", method = RequestMethod.GET)
	@ResponseBody
	public void report1(HttpServletRequest request,HttpServletResponse response,@PathVariable String project,@PathVariable String tag ,@PathVariable Integer villageId) 
			throws FileNotFoundException, IOException {

		Workbook wb = new HSSFWorkbook();
		
		String filename="";
		if(tag.equalsIgnoreCase("NEW")){
			filename="Report_By_Tenure_Map_register.xls";
		}
		else if(tag.equalsIgnoreCase("REGISTERED")){
			filename="Report_By_Tenure_Application_register.xls";
		}
		else if(tag.equalsIgnoreCase("APFR")){
			filename="Report_By_Tenure_APFR_register.xls";
		}
		
		// Create a blank sheet
		Sheet sheet = wb.createSheet("new sheet");

		List<Object> vertexLst = landRecordsService.findregparcelcountbyTenure(project,tag,villageId);
		String[] columnList = { "Tenure Type", "Count" };

		int rowCount = 0;

		Row row = sheet.createRow(rowCount++);

		int columnCount = 0;
		for (String header : columnList) {
			Cell cell = row.createCell(columnCount++);

			cell.setCellValue((String) header);

		}

		try {
			if(vertexLst!=null)
			for (int i = 0; i < vertexLst.size(); i++) {
				row = sheet.createRow(rowCount++);
				columnCount = 0;

				Object[] obj = (Object[]) vertexLst.get(i);
				String id = (String) obj[0];
				Cell cell = row.createCell(columnCount++);
				cell.setCellValue(id);

				BigInteger x = (BigInteger) obj[1];
				cell = row.createCell(columnCount++);
				cell.setCellValue(x.toString());

			}
			

		} catch (Exception e) {
			logger.error(e);
		}
		

		response.setHeader("Content-Disposition", "attachment; filename="+filename);
		response.setContentType("application/xls");
		OutputStream out = response.getOutputStream();

		wb.write(out);
		out.flush();
		out.close();
	}





	@RequestMapping(value = "/viewer/landrecords/parcelcountbygender/{project}/{tag}/{villageId}", method = RequestMethod.GET)
	@ResponseBody
	public void report2(HttpServletRequest request,HttpServletResponse response,@PathVariable String project,@PathVariable String tag,@PathVariable Integer villageId) 
			throws FileNotFoundException, IOException {

		Workbook wb = new HSSFWorkbook();

		// Create a blank sheet
		Sheet sheet = wb.createSheet("new sheet");
		
		String filename="";
		
		
		
		if(tag.equalsIgnoreCase("NEW")){
			filename="Report_By_Gender_Map_register.xls";
		}
		else if(tag.equalsIgnoreCase("REGISTERED")){
			filename="Report_By_Gender_Application_register.xls";
		}
		else if(tag.equalsIgnoreCase("APFR")){
			filename="Report_By_Gender_APFR_register.xls";
		}
		
		

		List<Object> genderCountLst = landRecordsService.findparcelcountbygender(project,tag,villageId);
		String[] columnList = { "Gender", "Count" };

		int rowCount = 0;

		Row row = sheet.createRow(rowCount++);

		int columnCount = 0;
		for (String header : columnList) {
			Cell cell = row.createCell(columnCount++);

			cell.setCellValue((String) header);

		}

		try {
			if(genderCountLst!=null)
			for (int i = 0; i < genderCountLst.size(); i++) {
				row = sheet.createRow(rowCount++);
				columnCount = 0;

				Object[] obj = (Object[]) genderCountLst.get(i);
				String id = (String) obj[0];
				Cell cell = row.createCell(columnCount++);
				cell.setCellValue(id);

				BigInteger x = (BigInteger) obj[1];
				cell = row.createCell(columnCount++);
				cell.setCellValue(x.toString());

				/*	Double y = (Double) obj[2];
				cell = row.createCell(columnCount++);
				cell.setCellValue(y);*/
				
			}

		} catch (Exception e) {
			logger.error(e);
		}


		response.setHeader("Content-Disposition", "attachment; filename="+filename);
		response.setContentType("application/xls");
		OutputStream out = response.getOutputStream();

		wb.write(out);
		out.flush();
		out.close();

	}



	@RequestMapping(value = "/viewer/landrecords/generateparcel", method = RequestMethod.POST)
	@ResponseBody
	public String generatePacelNumber(HttpServletRequest request,
			HttpServletResponse response) {

		long usin=0;
		String sectionno="";
		String lotno="";
		long parcel_count=0;
		String parcel_number="";

		try {
			usin= ServletRequestUtils.getRequiredLongParameter(request, "usin_spa");
			sectionno = ServletRequestUtils.getRequiredStringParameter(request, "section_no");
			lotno=ServletRequestUtils.getRequiredStringParameter(request, "lot_no");
			parcel_count=ServletRequestUtils.getRequiredLongParameter(request, "number_seq");

			SpatialUnitExtension spatialobj =landRecordsService.findSpatialByUsin(usin);

			if(spatialobj.getUsin()!=0l){
				spatialobj.setLotno(lotno);
				spatialobj.setSection_no(sectionno);
				if(parcel_count<10)
					parcel_number =sectionno+"-"+lotno+"-000"+parcel_count;
				else if(parcel_count>=10 && parcel_count<100)
					parcel_number =sectionno+"-"+lotno+"-00"+parcel_count;
				else if(parcel_count>=100 && parcel_count<999)
					parcel_number =sectionno+"-"+lotno+"-0"+parcel_count;
				spatialobj.setParcelno(parcel_number);

				//spatialobj.setParcelno(sectionno+"-"+lotno+"-"+parcel_count);
				landRecordsService.updateSpatialExtension(spatialobj);
			}
			else{
				SpatialUnitExtension speObj = new SpatialUnitExtension();
				speObj.setUsin(usin);
				speObj.setLotno(lotno);
				speObj.setSection_no(sectionno);

				if(parcel_count<10)
					parcel_number =sectionno+"-"+lotno+"-000"+parcel_count;
				else if(parcel_count>=10 && parcel_count<100)
					parcel_number =sectionno+"-"+lotno+"-00"+parcel_count;
				else if(parcel_count>=100 && parcel_count<999)
					parcel_number =sectionno+"-"+lotno+"-0"+parcel_count;
				speObj.setParcelno(parcel_number);


				landRecordsService.updateSpatialExtension(speObj);
			}

			boolean val = landRecordsService.updateParcelCount(parcel_count);

			if(val)
				return parcel_number;


		} catch (ServletRequestBindingException e) {
			logger.error(e);

		}
		return parcel_number;

	}

	@RequestMapping(value = "/viewer/landrecords/findcount/", method = RequestMethod.GET)
	@ResponseBody
	public long findcountbyId()
	{

		try {
			return landRecordsService.findcountbyId()+1l;
		} catch (Exception e) {
			logger.error(e);
			return 0;
		}

	}
	@RequestMapping(value = "/viewer/landrecords/findexisting/{usin}", method = RequestMethod.GET)
	@ResponseBody
	public SpatialUnitExtension findrecordByusin(@PathVariable long usin)
	{

		try {
			return landRecordsService.findSpatialByUsin(usin);
		} catch (Exception e) {
			logger.error(e);
			return null;
		}

	}
	@RequestMapping(value = "/viewer/landrecords/title/", method = RequestMethod.GET)
	@ResponseBody
	public List<TitleExisting> findAllTitles()
	{

		return landRecordsService.findAllTitles();

	}


	@RequestMapping(value = "/viewer/landrecords/updatepayment/{usin}", method = RequestMethod.POST)
	@ResponseBody
	public String updatePayment(HttpServletRequest request,
			HttpServletResponse response,@PathVariable long usin,Principal principal) {


		String receiptId="";
		long paymentAmount=0;
		String paymentDate="";
		String application_no="";
		String amount_comment="";


		try {
			paymentAmount= ServletRequestUtils.getRequiredLongParameter(request, "paymentAmount");
			paymentDate= ServletRequestUtils.getRequiredStringParameter(request, "paymentDate");
			receiptId= ServletRequestUtils.getRequiredStringParameter(request, "receiptId");
			amount_comment= ServletRequestUtils.getRequiredStringParameter(request, "amount_comment");


		} catch (ServletRequestBindingException e) {
			logger.error(e);
			return application_no;
		}
		PaymentInfo paymentTmp=new PaymentInfo();
		
			paymentTmp.setUsin(usin);
			paymentTmp.setAmount(paymentAmount);
			paymentTmp.setReceipt_no(receiptId);
			paymentTmp.setComment(amount_comment);


		Date payment_Date = null;
		try {
			SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");

			payment_Date = sdfDate.parse(paymentDate);
		} catch (ParseException e) {

			logger.error(e);
			return application_no;
		}

		paymentTmp.setPayment_date(payment_Date);
		paymentTmp.setUpdate_date(new Date());

		if(landRecordsService.savepaymentInfo(paymentTmp)){
			try {
				SpatialUnitTable spaTmp = landRecordsService.findSpatialUnitbyId(usin).get(0);
				/*String username = principal.getName();
				User user = userService.findByUniqueName(username);
				long userid = user.getId();
				landRecordsService.actionApprove(usin, userid,spaTmp.getWorkflow_id().getWorkflowId(),amount_comment);*/
				application_no=spaTmp.getApplication_no();
			} catch (Exception e) {
				logger.error(e);

			}

		}

		return application_no;

	}




	@RequestMapping(value = "/viewer/landrecords/registrytable/{project}/{tag}/{villageId}", method = RequestMethod.GET)
	@ResponseBody
	public void report3(HttpServletRequest request,HttpServletResponse response,@PathVariable String project,@PathVariable String tag,@PathVariable Integer villageId) 
			throws FileNotFoundException, IOException {

		Workbook wb = new HSSFWorkbook();

		// Create a blank sheet
		Sheet sheet = wb.createSheet("new sheet");

		List<Object> genderCountLst = landRecordsService.findRegistrytable(project,tag,villageId);
		String[] columnList = { "First Name","Last Name","Applcation No", "Application Date"};

		int rowCount = 0;

		Row row = sheet.createRow(rowCount++);

		int columnCount = 0;
		for (String header : columnList) {
			Cell cell = row.createCell(columnCount++);

			cell.setCellValue((String) header);

		}

		try {
			for (int i = 0; i < genderCountLst.size(); i++) {
				row = sheet.createRow(rowCount++);
				columnCount = 0;

				Object[] obj = (Object[]) genderCountLst.get(i);
				String id = (String) obj[0];
				Cell cell = row.createCell(columnCount++);
				cell.setCellValue(id);

				obj = (Object[]) genderCountLst.get(i);
				id = (String) obj[1];
				cell = row.createCell(columnCount++);
				cell.setCellValue(id);

				obj = (Object[]) genderCountLst.get(i);
				id = (String) obj[2];
				cell = row.createCell(columnCount++);
				cell.setCellValue(id);


				Date x = (Date) obj[3];
				cell = row.createCell(columnCount++);
				cell.setCellValue(x.toString());

				/*	Double y = (Double) obj[2];
				cell = row.createCell(columnCount++);
				cell.setCellValue(y);*/

			}

		} catch (Exception e) {
			logger.error(e);
		}


		response.setHeader("Content-Disposition", "attachment; filename=datafile.xls");
		response.setContentType("application/xls");
		OutputStream out = response.getOutputStream();

		wb.write(out);
		out.flush();
		out.close();

	}

	@RequestMapping(value = "/viewer/landrecords/village/", method = RequestMethod.GET)
	@ResponseBody
	public List<Village> findAllVillages()
	{

		return landRecordsService.findAllVillages();

	}

	@RequestMapping(value = "/viewer/landrecords/updatedate/{usin}", method = RequestMethod.POST)
	@ResponseBody
	public boolean updatePayment(HttpServletRequest request,
			HttpServletResponse response,@PathVariable long usin) {



		String signatoryDate="";

		try {
			signatoryDate= ServletRequestUtils.getRequiredStringParameter(request, "signatoryDate");
		} catch (ServletRequestBindingException e1) {
			logger.error(e1);
		}


		Date date = null;
		try {
			SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");

			date = sdfDate.parse(signatoryDate);

		} catch (ParseException e) {

			logger.error(e);
			return false;
		}


		SpatialUnitExtension spaExtnObj = landRecordsService.findSpatialByUsin(usin);
		spaExtnObj.setMayor_sign_date(date);
		spaExtnObj.setUsin(usin);
		return landRecordsService.updateMayorDate(spaExtnObj);	

	}
	@RequestMapping(value = "/viewer/landrecords/signatory/{usin}", method = RequestMethod.GET)
	@ResponseBody
	public Date fidnDateByUsin(@PathVariable Long usin)
	{

		return landRecordsService.findSpatialByUsin(usin).getMayor_sign_date();

	}
	@RequestMapping(value = "/viewer/landrecords/checkdate/{usin}", method = RequestMethod.GET)
	@ResponseBody
	public String findLetterGenerationDate(@PathVariable Long usin)
	{

		if(landRecordsService.findLetterGenerationDatebyUsin(usin)!=null){
			return landRecordsService.findLetterGenerationDatebyUsin(usin).toString();
		}
		else
			return "";
	}
	@SuppressWarnings("null")
	@RequestMapping(value = "/viewer/landrecords/savedate/{usin}", method = RequestMethod.GET)
	@ResponseBody
	public boolean updateLetterGenerationDate(@PathVariable Long usin)
	{
		PaymentInfo paymentObj = paymentInfoDAO.findById(usin, false);

			paymentObj.setUsin(usin);

		paymentObj.setLetter_generation_date(new Date());
		boolean result = landRecordsService.savepaymentInfo(paymentObj);
		if(result){
			return true;
		}
		else{
			return false;
		}

	}
	
	
	
	@RequestMapping(value = "/viewer/landrecords/spatialunit/paymentdetail/{usin}", method = RequestMethod.GET)
	@ResponseBody
	public PaymentDto  getpaymentdetail(@PathVariable Long usin) {
		
		PaymentDto tmpDto=new PaymentDto();
		
		try {
			PaymentInfo paymentTmp = paymentInfoDAO.findById(usin, false);
			if(paymentTmp!=null){
				
				if(paymentTmp.getLetter_generation_date()!=null){
					tmpDto.setPrintDate(paymentTmp.getLetter_generation_date());
					
				}
				
				else{
					PaymentInfo paymentInfo=new PaymentInfo();
					paymentInfo.setUsin(usin);
					paymentInfo.setLetter_generation_date(new Date());
					
					boolean val=landRecordsService.savePayemrnt(paymentInfo);
					if(val)
						tmpDto.setPrintDate(new Date());
				}
				
				
			}
			
			
			else {
				
				PaymentInfo paymentInfo=new PaymentInfo();
				paymentInfo.setUsin(usin);
				paymentInfo.setLetter_generation_date(new Date());
				
				boolean val=landRecordsService.savePayemrnt(paymentInfo);
				if(val)
					tmpDto.setPrintDate(new Date());
			}
			
		} catch (Exception e) {
			logger.error(e);
		}
		
		
		SocialTenureRelationship tenureTmp = landRecordsService.findAllSocialTenureByUsin(usin).get(0);
		SpatialUnitTable  spatialTmp= landRecordsService.findSpatialUnitbyId(usin).get(0);
		NaturalPerson natureTmp = landRecordsService.naturalPersonById(tenureTmp.getPerson_gid().getPerson_gid()).get(0);
		
		tmpDto.setApplication_no(spatialTmp.getApplication_no());
		tmpDto.setApplicationDate(spatialTmp.getApplicationdate());
		tmpDto.setArea(spatialTmp.getArea().toString());
		
		tmpDto.setFirstname(natureTmp.getFirstName());
		tmpDto.setLastname(natureTmp.getLastName());
		
		tmpDto.setPv_no(spatialTmp.getPv_no());
		if(spatialTmp.getVillage_id()!=null){
			tmpDto.setCommune(spatialTmp.getVillage_id().getCommune().getCommuneNameFr());
			tmpDto.setProvince(spatialTmp.getVillage_id().getCommune().getProvince().getProvinceNameFr());
			tmpDto.setRegion(spatialTmp.getVillage_id().getCommune().getProvince().getRegion().getRegionNameFr());
			tmpDto.setVillage(spatialTmp.getVillage_id().getVillageNameFr());	
		}
		
		tenureTmp=null;
		spatialTmp=null;
		natureTmp=null;
		return tmpDto;
		
		
	}

	@RequestMapping(value = "/viewer/landrecords/checknoticedate/{usin}", method = RequestMethod.GET)
	@ResponseBody
	public String findNoticeStartDate(@PathVariable Long usin)
	{

		
			if(landRecordsService.findNoticeStartDatebyUsin(usin)!=null)
				return new SimpleDateFormat("dd-MM-yyyy").format(landRecordsService.findNoticeStartDatebyUsin(usin));
			else	
				return new SimpleDateFormat("dd-MM-yyyy").format(new Date());
	}
	
	@RequestMapping(value = "/viewer/landrecords/updatenoticedate/{usin}", method = RequestMethod.GET)
	@ResponseBody
	public boolean updateNoticeStartDate(@PathVariable Long usin)
	{
		if(landRecordsService.findNoticeStartDatebyUsin(usin)==null){
		SpatialUnitTable spatialObj = landRecordsService.findSpatialUnitbyId(usin).get(0);
		spatialObj.setPublic_notice_startdate(new Date());
		
		try {
			landRecordsService.update(spatialObj);
			return true;
		} catch (Exception e) {
			logger.error(e);
			return false;
		}
		}
		else{
			return false;
		}
	}
	
}