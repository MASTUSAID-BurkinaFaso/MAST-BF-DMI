package com.rmsi.mast.viewer.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rmsi.mast.studio.dao.AttributeCategoryDAO;
import com.rmsi.mast.studio.dao.AttributeMasterDAO;
import com.rmsi.mast.studio.dao.AttributeValueFetchDAO;
import com.rmsi.mast.studio.dao.GenderDAO;
import com.rmsi.mast.studio.dao.GroupTypeDAO;
import com.rmsi.mast.studio.dao.LandTypeDAO;
import com.rmsi.mast.studio.dao.MaritalStatusDAO;
import com.rmsi.mast.studio.dao.OccupancyDAO;
import com.rmsi.mast.studio.dao.PaymentInfoDAO;
import com.rmsi.mast.studio.dao.PersonDAO;
import com.rmsi.mast.studio.dao.PersonTypeDAO;
import com.rmsi.mast.studio.dao.ProjectAdjudicatorDAO;
import com.rmsi.mast.studio.dao.ProjectAreaDAO;
import com.rmsi.mast.studio.dao.ProjectHamletDAO;
import com.rmsi.mast.studio.dao.SUnitHistoryDAO;
import com.rmsi.mast.studio.dao.ShareTypeDAO;
import com.rmsi.mast.studio.dao.SlopeValuesDAO;
import com.rmsi.mast.studio.dao.SocialTenureRelationshipDAO;
import com.rmsi.mast.studio.dao.SoilQualityValuesDAO;
import com.rmsi.mast.studio.dao.SourceDocumentDAO;
import com.rmsi.mast.studio.dao.TenureClassDAO;
import com.rmsi.mast.studio.dao.UnitDAO;
import com.rmsi.mast.studio.dao.UsertableDAO;
import com.rmsi.mast.studio.dao.VillageDAO;
import com.rmsi.mast.studio.dao.WorkflowDAO;
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
import com.rmsi.mast.studio.domain.ParcelCount;
import com.rmsi.mast.studio.domain.PaymentInfo;
import com.rmsi.mast.studio.domain.Person;
import com.rmsi.mast.studio.domain.PersonType;
import com.rmsi.mast.studio.domain.ProjectAdjudicator;
import com.rmsi.mast.studio.domain.ProjectArea;
import com.rmsi.mast.studio.domain.ProjectHamlet;
import com.rmsi.mast.studio.domain.ShareType;
import com.rmsi.mast.studio.domain.SlopeValues;
import com.rmsi.mast.studio.domain.SocialTenureRelationship;
import com.rmsi.mast.studio.domain.SoilQualityValues;
import com.rmsi.mast.studio.domain.SourceDocument;
import com.rmsi.mast.studio.domain.SpatialUnit;
import com.rmsi.mast.studio.domain.Status;
import com.rmsi.mast.studio.domain.TenureClass;
import com.rmsi.mast.studio.domain.TitleExisting;
import com.rmsi.mast.studio.domain.Unit;
import com.rmsi.mast.studio.domain.Village;
import com.rmsi.mast.studio.domain.Workflow;
import com.rmsi.mast.studio.domain.fetch.AttributeValuesFetch;
import com.rmsi.mast.studio.domain.fetch.SpatialUnitExtension;
import com.rmsi.mast.studio.domain.fetch.SpatialUnitStatusHistory;
import com.rmsi.mast.studio.domain.fetch.SpatialUnitTable;
import com.rmsi.mast.studio.domain.fetch.SpatialUnitTemp;
import com.rmsi.mast.studio.domain.fetch.SpatialunitDeceasedPerson;
import com.rmsi.mast.studio.domain.fetch.SpatialunitPersonwithinterest;
import com.rmsi.mast.studio.domain.fetch.Usertable;
import com.rmsi.mast.studio.mobile.dao.AttributeValuesDao;
import com.rmsi.mast.studio.mobile.dao.CitizenshipDao;
import com.rmsi.mast.studio.mobile.dao.EducationLevelDao;
import com.rmsi.mast.studio.mobile.dao.LandUseTypeDao;
import com.rmsi.mast.studio.mobile.dao.NaturalPersonDao;
import com.rmsi.mast.studio.mobile.dao.NonNaturalPersonDao;
import com.rmsi.mast.studio.mobile.dao.SpatialUnitDao;
import com.rmsi.mast.studio.mobile.dao.StatusDao;
import com.rmsi.mast.studio.mobile.dao.SurveyProjectAttributeDao;
import com.rmsi.mast.studio.util.ConstantUtil;
import com.rmsi.mast.viewer.dao.ActionToolsDao;
import com.rmsi.mast.viewer.dao.LandRecordsDao;
import com.rmsi.mast.viewer.dao.MutationTypeDao;
import com.rmsi.mast.viewer.dao.NatureOfApplicationDao;
import com.rmsi.mast.viewer.dao.NatureOfPowerDao;
import com.rmsi.mast.viewer.dao.ParcelCountDao;
import com.rmsi.mast.viewer.dao.SpatialStatusDao;
import com.rmsi.mast.viewer.dao.SpatialUnitDeceasedPersonDao;
import com.rmsi.mast.viewer.dao.SpatialUnitExtensionDao;
import com.rmsi.mast.viewer.dao.SpatialUnitPersonWithInterestDao;
import com.rmsi.mast.viewer.dao.SpatialUnitTempDao;
import com.rmsi.mast.viewer.dao.TitleExistingDao;
import com.rmsi.mast.viewer.service.LandRecordsService;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;
/**
 * 
 * @author Abhishek.Dasgupta
 *
 */
@Service
public class LandRecordsServiceImpl implements LandRecordsService {

	private static final Logger logger = Logger.getLogger(LandRecordsServiceImpl.class);

	@Autowired
	private LandRecordsDao	landRecordsDao; 

	@Autowired
	private LandUseTypeDao	landUseTypeDao; 


	@Autowired
	private SpatialStatusDao spatialStatusDao;


	@Autowired
	private SocialTenureRelationshipDAO socialTenureRelationshipDAO;

	@Autowired
	private NaturalPersonDao naturalPersonDao;

	@Autowired
	private SUnitHistoryDAO  sUnitHistoryDAO;


	@Autowired
	private NonNaturalPersonDao nonNaturalPersonDao;
	@Autowired
	private GenderDAO genderDAO;

	@Autowired
	private MaritalStatusDAO maritalStatusDAO;

	@Autowired
	private PersonTypeDAO personTypeDAO;

	@Autowired
	private EducationLevelDao educationLevelDao;

	@Autowired
	private PersonDAO personDAO;

	@Autowired
	private OccupancyDAO occupancyDAO;

	@Autowired
	private TenureClassDAO tenureClassDAO;

	@Autowired
	private SourceDocumentDAO sourceDocumentDAO;


	@Autowired
	private ShareTypeDAO socialTenureRelationshipTypeDAO;


	@Autowired
	private StatusDao statusDao;

	@Autowired
	private SpatialUnitDao spatialUnitDao;

	@Autowired
	private UsertableDAO usertableDAO;

	@Autowired
	private SUnitHistoryDAO sunitHistoryDAO;

	@Autowired
	private UnitDAO unitDAO;

	@Autowired
	private AttributeCategoryDAO attributeCategoryDAO;

	@Autowired
	private AttributeMasterDAO attributeMasterDAO;

	@Autowired
	private AttributeValueFetchDAO attributeValueFetchDAO;


	@Autowired
	private ProjectAreaDAO	projectAreaDAO; 

	@Autowired
	private LandTypeDAO landTypeDAO;
	@Autowired
	private SoilQualityValuesDAO soilQualityValuesDAO;
	@Autowired
	private SlopeValuesDAO slopeValuesDAO;


	@Autowired
	private GroupTypeDAO groupTypeDAO;


	@Autowired
	private SpatialUnitTempDao spatialUnitTempDao;


	@Autowired
	private AttributeValuesDao attributeValuesDao;


	@Autowired
	private SurveyProjectAttributeDao	 surveyProjectAttributeDao;

	@Autowired
	private SpatialUnitPersonWithInterestDao spatialUnitPersonWithInterestDao;

	@Autowired
	private ProjectHamletDAO projectHamletDAO;

	@Autowired
	private SpatialUnitDeceasedPersonDao spatialUnitDeceasedPersonDao;

	@Autowired
	private ProjectAdjudicatorDAO projectAdjudicatorDAO;

	@Autowired
	private CitizenshipDao citizenshipDao;

	@Autowired
	private WorkflowDAO workflowDAO;

	@Autowired
	private NatureOfApplicationDao natureOfApplicationDao;

	@Autowired
	private MutationTypeDao mutationTypeDao;

	@Autowired
	private NatureOfPowerDao natureOfPowerDao;

	@Autowired
	private ActionToolsDao actionToolsDao;

	@Autowired
	private SpatialUnitExtensionDao spatialUnitExtensionDao;

	@Autowired
	private ParcelCountDao parcelCountDao;

	@Autowired
	private TitleExistingDao titleExistingDao;

	@Autowired
	private PaymentInfoDAO paymentInfoDAO;

	@Autowired
	private VillageDAO villageDAO;


	@Override
	public List<SpatialUnitTable> findAllSpatialUnit(String defaultProject) {
		return landRecordsDao.findallspatialUnit(defaultProject);
	}



	@Override
	public boolean updateApprove(Long id ,long userid) {

		try {

			SpatialUnitStatusHistory  spatialUnitStatusHistory=new  SpatialUnitStatusHistory();

			spatialUnitStatusHistory.setStatus_change_time(new Date());
			spatialUnitStatusHistory.setUserid(userid);
			spatialUnitStatusHistory.setUsin(id);
			spatialUnitStatusHistory.setWorkflow_status_id(4);

			sUnitHistoryDAO.makePersistent(spatialUnitStatusHistory);
		} catch (Exception e) {

			logger.error(e);
		}

		return landRecordsDao.updateApprove(id);
	}




	@Override
	public List<Status> findallStatus() {
		return spatialStatusDao.findAll(); 
	}

	@Override
	public List<SpatialUnitTable> search(String usinStr, String ukaNumber,
			String projname, String dateto, String datefrom, Long status,Integer startpos) {
		return landRecordsDao.search(usinStr,ukaNumber,projname,dateto,datefrom,status,startpos);
	}

	@Override
	public List<SpatialUnitTable> findSpatialUnitbyId(Long id) {
		return landRecordsDao.findSpatialUnitById(id);
	}

	@Override
	public List<SpatialUnitTable> findAllSpatialUnitlist() {
		return landRecordsDao.findAll();
	}

	@Override
	public boolean update(SpatialUnitTable spatialUnit) {
		try {
			landRecordsDao.makePersistent(spatialUnit);
			return true;
		} catch (Exception e) {

			logger.error(e);
			return false;
		}

	}

	@Override
	public List<SocialTenureRelationship> findAllSocialTenure() {

		return socialTenureRelationshipDAO.findAll();
	}

	@Override
	public List<NaturalPerson> naturalPersonById(Long id) {

		try {
			return naturalPersonDao.findById(id);
		} catch (Exception e) {
			logger.error(e);
			return null;
		}

	}

	@Override
	public List<Gender> findAllGenders() {
		return genderDAO.findAll();
	}

	@Override
	public Gender findGenderById(Long id) {
		return genderDAO.findById(id, false);
	}

	@Override
	public NaturalPerson editnatural(NaturalPerson naturalPerson) {
		try {
			return naturalPersonDao.makePersistent(naturalPerson);


		} catch (Exception e) {

			logger.error(e);

		}
		return null;
	}

	@Override
	public MaritalStatus findMaritalById(int maritalid) {
		return maritalStatusDAO.findById(maritalid, false);
	}

	@Override
	public PersonType findPersonTypeById(long persontype) {
		return personTypeDAO.findById(persontype, false);
	}

	@Override
	public List<MaritalStatus> findAllMaritalStatus() {
		return maritalStatusDAO.findAll();
	}

	@Override
	public List<SocialTenureRelationship> findAllSocialTenureByUsin(Long id) {
		return socialTenureRelationshipDAO.findbyUsin(id);
	}

	@Override
	public List<NonNaturalPerson> nonnaturalPersonById(Long id) {
		return nonNaturalPersonDao.findById(id);
	}

	@Override
	public boolean editNonNatural(NonNaturalPerson nonnaturalPerson) {
		try {
			nonNaturalPersonDao.makePersistent(nonnaturalPerson);
			return true;
		} catch (Exception e) {

			logger.error(e);
			return false;
		}

	}

	@Override
	public ShareType findTenureType(Long type) {

		try {
			return socialTenureRelationshipTypeDAO.findById(type.intValue(), false);
		} catch (Exception e) {

			logger.error(e);
			return null;
		}
	}

	@Override
	public boolean edittenure(SocialTenureRelationship socialTenureRelationship) {
		try {
			socialTenureRelationship = socialTenureRelationshipDAO.makePersistent(socialTenureRelationship);
			return true;
		} catch (Exception e) {

			logger.error(e);
			return false;
		}
	}

	@Override
	public Person findPersonGidById(long persontype) {
		return personDAO.findById(persontype, false);
	}

	@Override
	public OccupancyType findOccupancyTyoeById(int occid) {
		return occupancyDAO.findById(occid, false);
	}

	@Override
	public TenureClass findtenureClasseById(int tenureclassid) {
		return tenureClassDAO.findById(tenureclassid, false);
	}

	@Override
	public List<SocialTenureRelationship> findSocialTenureByGid(Integer id) {
		return socialTenureRelationshipDAO.findByGid(id);
	}

	@Override
	public List<ShareType> findAllTenureList() {
		return socialTenureRelationshipTypeDAO.findAll();
	}

	@Override
	public List<SourceDocument> findMultimediaByUsin(Long id) {
		return sourceDocumentDAO.findSourceDocumentById(id);

	}

	@Override
	public List<SourceDocument> findAllMultimedia() {

		return sourceDocumentDAO.findAll();
	}

	@Override
	public boolean updateMultimedia(SourceDocument sourceDocument) {
		try {
			sourceDocumentDAO.makePersistent(sourceDocument);
			return true;
		} catch (Exception e) {

			logger.error(e);
			return false;
		}


	}

	@Override
	public List<SourceDocument> findMultimediaByGid(Long id) {
		return sourceDocumentDAO.findByGId(id);
	}

	@Override
	public boolean deleteMultimedia(Long id) {
		return sourceDocumentDAO.deleteMultimedia(id);

	}

	@Override
	public boolean deleteNatural(Long id) {
		//check if source document is not present against person_gid
		if(getdocumentByPerson(id)!=null)
			deleteMultimedia(Long.valueOf(getdocumentByPerson(id).getGid()));

		return socialTenureRelationshipDAO.deleteNatural(id);
	}

	@Override
	public boolean deleteNonNatural(Long id) {

		return socialTenureRelationshipDAO.deleteNonNatural(id);

	}

	@Override
	public boolean deleteTenure(Long id) {

		return socialTenureRelationshipDAO.deleteTenure(id);

	}

	@Override
	public Status findAllSTatus(Long statusId) {
		return statusDao.findById(statusId.intValue(), false);
	}

	@Override
	public List<EducationLevel> findAllEducation() {
		return educationLevelDao.findAll();
	}

	@Override
	public List<LandUseType> findAllLanduserType() {
		return landUseTypeDao.findAll();
	}

	@Override
	public List<TenureClass> findAllTenureClass() {
		return tenureClassDAO.findAll();
	}

	@Override
	public EducationLevel findEducationById(Long education) {
		return educationLevelDao.findById(education.intValue(), false);
	}

	@Override
	public LandUseType findLandUseById(int existingUse) {
		return landUseTypeDao.findById(existingUse, false);
	}

	@Override
	public LandUseType findProposedUseById(int proposedUse) {
		return landUseTypeDao.findById(proposedUse, false);
	}



	@Override
	public Usertable findUserByID(int userid) {
		return usertableDAO.findById(userid, false);
	}

	@Override
	public String findukaNumberByUsin(Long id) {

		return landRecordsDao.findukaNumberByUsin(id);
	}

	@Override
	public List<OccupancyType> findAllOccupancyTypes() {
		return occupancyDAO.findAll();
	}

	@Override
	public boolean rejectStatus(Long id , long userid) {
		try {

			SpatialUnitStatusHistory  spatialUnitStatusHistory=new  SpatialUnitStatusHistory();

			spatialUnitStatusHistory.setStatus_change_time(new Date());
			spatialUnitStatusHistory.setUserid(userid);
			spatialUnitStatusHistory.setUsin(id);
			spatialUnitStatusHistory.setWorkflow_status_id(5);

			sUnitHistoryDAO.makePersistent(spatialUnitStatusHistory);
		} catch (Exception e) {

			logger.error(e);
		}


		return landRecordsDao.rejectStatus(id);
	}

	@Override
	public Unit findAllMeasurementUnit(String measurement_unit) {
		return unitDAO.findByName(measurement_unit);
	}

	@Override
	public List<AttributeCategory> findAllAttributeCategories() {

		return attributeCategoryDAO.findAll();
	}

	@Override
	public List<AttributeValuesFetch> findAttributelistByCategoryId(Long parentid, Long id) {
		try {
			return attributeMasterDAO.fetchCustomAttribs(parentid,id.intValue());
		} catch (Exception e) {
			logger.error(e);
			return null;
		}

	}

	@Override
	public boolean updateAttributeValues(Long value_key, String alias) {
		return attributeValueFetchDAO.updateValue(value_key,alias);
	}

	@Override
	public boolean updateFinal(Long id, long userid) {

		try {

			SpatialUnitStatusHistory  spatialUnitStatusHistory=new  SpatialUnitStatusHistory();

			spatialUnitStatusHistory.setStatus_change_time(new Date());
			spatialUnitStatusHistory.setUserid(userid);
			spatialUnitStatusHistory.setUsin(id);
			spatialUnitStatusHistory.setWorkflow_status_id(7);

			sUnitHistoryDAO.makePersistent(spatialUnitStatusHistory);
		} catch (Exception e) {

			logger.error(e);
		}

		return landRecordsDao.updateFinal(id);
	}

	@Override
	public SourceDocument saveUploadedDocuments(SourceDocument sourceDocument) {

		try {
			return sourceDocumentDAO.makePersistent(sourceDocument);
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}



	@Override
	public boolean updateAdjudicated(Long id, long userid) {

		try {

			SpatialUnitStatusHistory  spatialUnitStatusHistory=new  SpatialUnitStatusHistory();

			spatialUnitStatusHistory.setStatus_change_time(new Date());
			spatialUnitStatusHistory.setUserid(userid);
			spatialUnitStatusHistory.setUsin(id);
			spatialUnitStatusHistory.setWorkflow_status_id(2);

			sUnitHistoryDAO.makePersistent(spatialUnitStatusHistory);
		} catch (Exception e) {

			logger.error(e);
		}

		return landRecordsDao.updateAdjudicated(id);
	}

	@Override
	public SourceDocument getDocumentbyGid(Long id) {

		try {
			return sourceDocumentDAO.findById(id.intValue(), false);
		} catch (Exception e) {

			e.printStackTrace();
			return null;
		}

	}




	@Override
	public List<ProjectArea> findProjectArea(String projectName) {
		return projectAreaDAO.findByProjectName(projectName);
	}


	@Override
	public List<SoilQualityValues> findAllsoilQuality() {

		return soilQualityValuesDAO.findAll();
	}

	@Override
	public List<SlopeValues> findAllSlopeValues() {
		return slopeValuesDAO.findAll();
	}

	@Override
	public List<LandType> findAllLandType() {
		return landTypeDAO.findAll();
	}

	@Override
	public SoilQualityValues findSoilQuality(int quality_soil) {
		return soilQualityValuesDAO.findById(quality_soil, false);
	}

	@Override
	public SlopeValues findSlopeValues(int slope_values) {
		return slopeValuesDAO.findById(slope_values, false);
	}

	@Override
	public LandType findLandType(int land_type) {
		return landTypeDAO.findById(land_type, false);
	}

	@Override
	public List<GroupType> findAllGroupType() {
		return groupTypeDAO.findAll();
	}

	@Override
	public GroupType findGroupType(int group_type) {
		return groupTypeDAO.findById(group_type, false);
	}

	@Override
	public boolean updateCCRO(Long id, long userid) {
		try {

			SpatialUnitStatusHistory  spatialUnitStatusHistory=new  SpatialUnitStatusHistory();

			spatialUnitStatusHistory.setStatus_change_time(new Date());
			spatialUnitStatusHistory.setUserid(userid);
			spatialUnitStatusHistory.setUsin(id);
			spatialUnitStatusHistory.setWorkflow_status_id(6);

			sUnitHistoryDAO.makePersistent(spatialUnitStatusHistory);
		} catch (Exception e) {

			logger.error(e);
		}

		SpatialUnitTable spatialUnit =new SpatialUnitTable();
		try {
			spatialUnit = landRecordsDao.findSpatialUnitById(id).get(0);
		} catch (Exception e) {

			e.printStackTrace();
		}
		Status status=findAllSTatus(6L);
		spatialUnit.setStatus(status);
		landRecordsDao.makePersistent(spatialUnit);
		return true;
	}


	/*@Override
	public List<SpatialUnitTemp> findAllSpatialUnitTemp(String defaultProject,int startfrom,int[]workID) {
		return spatialUnitTempDao.findOrderedSpatialUnit(defaultProject,startfrom,workID);
	}
	 */



	@Override
	public ArrayList<Long> findOwnerPersonByUsin(Long id) {
		List<SocialTenureRelationship> socailTenure = socialTenureRelationshipDAO.findbyUsin(id);
		ArrayList<Long>naturalPerson=new ArrayList<Long>();

		for (int i = 0; i < socailTenure.size(); i++) {

			naturalPerson.add(socailTenure.get(i).getPerson_gid().getPerson_gid());

		}


		return naturalPersonDao.findOwnerByGid(naturalPerson);

	}

	/*@Override
		public List<Long> getAdminId(Long id) {

			return spatialUnitPersonAdministratorDao.findAdminIdbyUsin(id);
		}*/



	@Override
	public Integer AllSpatialUnitTemp(String defaultProject,int[]workId) {
		return spatialUnitTempDao.AllSpatialUnitTemp(defaultProject,workId);
	}

	@Override
	public SourceDocument getdocumentByAdminId(Long adminID) {
		return sourceDocumentDAO.findDocumentByAdminId(adminID);
	}

	@Override
	public boolean deleteSpatialUnit(Long id) {

		return landRecordsDao.deleteSpatial(id);
	}


	@Override
	public List<SocialTenureRelationship> showDeletedPerson(Long id) {
		try {
			return socialTenureRelationshipDAO.findDeletedPerson(id);
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}

	@Override
	public boolean addDeletedPerson(Long gid) {
		return socialTenureRelationshipDAO.addDeletedPerson(gid);
	}

	@Override
	public Integer searchListSize(String usinStr, String ukaNumber,
			String projname, String dateto, String datefrom, long status) {
		// 
		return landRecordsDao.searchSize(usinStr,ukaNumber,projname,dateto,datefrom,status);
	}


	@Override
	public SourceDocument getdocumentByPerson(Long person_gid) {
		return sourceDocumentDAO.getDocumentByPerson(person_gid);
	}




	/*@Override
		public SpatialunitPersonadministrator updateSpatialAdmin(SpatialunitPersonadministrator spaobj) {
			try {
				return spatialUnitPersonAdministratorDao.makePersistent(spaobj);
			} catch (Exception e) {

				e.printStackTrace();
				return null;
			}

		}*/

	@Override
	public List<String> findnaturalCustom(String project) {
		return surveyProjectAttributeDao.findnaturalCustom(project);
	}

	@Override
	public boolean saveAttributealues(AttributeValues tmpvalue) {
		try {
			attributeValuesDao.makePersistent(tmpvalue);
			return true;
		} catch (Exception e) {
			logger.error(e);
			return false;

		}

	}

	@Override
	public List<SpatialunitPersonwithinterest> findpersonInterestByUsin(
			Long usin) {
		return spatialUnitPersonWithInterestDao.findByUsin(usin);

	}



	@Override
	public List<SpatialUnitTemp> findSpatialUnitforUKAGeneration(
			String project) {
		return spatialUnitTempDao.findSpatialUnitforUKAGeneration(project);
	}



	@Override
	public void addSpatialUnitTemp(SpatialUnitTemp spatialunitmp) {
		try {
			spatialUnitTempDao.makePersistent(spatialunitmp);
		} catch (Exception e) {
			logger.error(e);
		}

	}



	@Override
	public List<Long> findUsinforUKAGeneration(String project,
			String hamletCode) {
		return spatialUnitTempDao.findUsinforUKAGeneration(project,hamletCode);
	}



	@Override
	public boolean updateUKAnumber(Long long1, String uka) {
		return spatialUnitTempDao.updateUKAnumber(long1,uka);
	}



	@Override
	public boolean addnxtTokin(SpatialunitPersonwithinterest spi) {
		try {
			spatialUnitPersonWithInterestDao.makePersistent(spi);
			return true;
		} catch (Exception e) {
			logger.error(e);
			return false;
		}
	}



	@Override
	public boolean deletePersonWithInterest(Long id) {
		try {
			spatialUnitPersonWithInterestDao.makeTransientByID(id);
			return true;
		} catch (Exception e) {
			logger.error(e);
			return false;
		}		
	}



	@Override
	public List<PersonType> AllPersonType() {
		return personTypeDAO.findAll();
	}



	@Override
	public List<SpatialUnitTable> getSpatialUnitByBbox(String bbox , String project_name) {

		return landRecordsDao.getSpatialUnitByBbox(bbox,project_name);
	}



	@Override
	public AttributeValues getAttributeValue(Long value_key) {
		try {
			return attributeValuesDao.findById(value_key, false);
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}



	@Override
	public Long getAttributeKey(long person_gid, long uid) {
		return attributeValuesDao.getAttributeKeyById(person_gid,uid);
	}



	@Override
	public boolean findExistingHamlet(long hamlet_id) {
		return landRecordsDao.findExistingHamlet(hamlet_id);
	}



	@Override
	public List<SpatialunitDeceasedPerson> findDeceasedPersonByUsin(
			Long usin) {

		return spatialUnitDeceasedPersonDao.findPersonByUsin(usin);
	}



	@Override
	public boolean saveDeceasedPerson(SpatialunitDeceasedPerson spdeceased) {
		try {
			spatialUnitDeceasedPersonDao.makePersistent(spdeceased);
			return true;
		} catch (Exception e) {

			logger.error(e);
			return false;
		}
	}



	@Override
	public boolean deleteDeceasedPerson(Long id) {

		try {
			spatialUnitDeceasedPersonDao.makeTransientByID(id);
			return true;
		} catch (Exception e) {

			logger.error(e);
			return false;
		}
	}



	@Override
	public boolean deleteAllVertexLabel() {
		return landRecordsDao.deleteAllVertexLabel();
	}



	@Override
	public boolean addAllVertexLabel(int k, String lat, String lon) {
		return landRecordsDao.addAllVertexLabel(k,lat,lon);
	}



	@Override
	public ProjectAdjudicator findAdjudicatorByID(int witness1) {
		return projectAdjudicatorDAO.findById(witness1, false);
	}



	@Override
	public boolean updateSharePercentage(String alias, long personGid) {
		return socialTenureRelationshipDAO.updateSharePercentage(alias,personGid);
	}




	@Override
	public Citizenship findcitizenship(long citizenship) {
		return citizenshipDao.findBycitizenId(citizenship);
	}



	@Override
	public List<Citizenship> findAllCitizenShip() {
		try {
			return citizenshipDao.findAll();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}



	@Override
	public boolean deleteNaturalImage(Long id) {
		return sourceDocumentDAO.deleteNaturalPersonImage(id);
	}



	@Override
	public boolean checkActivePerson(Long id) {
		return sourceDocumentDAO.checkPersonImage(id);
	}



	@Override
	public boolean deleteAdminById(Long id) {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public boolean addAdminById(Long adminId) {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public List<Long> getAdminId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public List<Workflow> getAllworkFlow() {
		try {
			//return workflowDAO.findAll();
			return workflowDAO.findSFRWorkflow();
		} catch (Exception e) {
			logger.error(e);
			return new ArrayList<Workflow>();
		}
	}



	@Override
	public List<NatureOfApplication> findAllAppNature() {
		try {
			return natureOfApplicationDao.findAll();
		} catch (Exception e) {
			logger.error(e);
			return new ArrayList<NatureOfApplication>();
		}
	}



	@Override
	public List<MutationType> findAllMutationType() {
		try {
			return mutationTypeDao.findAll();
		} catch (Exception e) {
			logger.error(e);
			return new ArrayList<MutationType>();
		}
	}



	@Override
	public NatureOfApplication findNatureofApplication(int app_nature) {
		// TODO Auto-generated method stub
		return natureOfApplicationDao.findById(app_nature, false);
	}



	@Override
	public MutationType findMutatitonType(int mutation_type) {
		return mutationTypeDao.findById(mutation_type, false);
	}



	@Override
	public List<NatureOfPower> findAllApplicantPowers() {
		// TODO Auto-generated method stub
		return natureOfPowerDao.findAll();
	}



	@Override
	public NatureOfPower findPowerByID(int nop_id) {
		try {
			return natureOfPowerDao.findById(nop_id,false);
		} catch (Exception e) {
			logger.error(e);
			return null; 
		}
	}



	@Override
	public List<Object> findAllVerteces() {
		return landRecordsDao.findAllVerteces();
	}



	@Override
	public List<SpatialUnitTemp> findAllSpatialUnitTemp(String project,
			Integer startfrom, int[] mapped_workids) {
		return spatialUnitTempDao.findOrderedSpatialUnit(project, startfrom ,mapped_workids);
	}




	@Override
	public Integer actionApprove(Long id, long userid, Integer workflowId,String comments ,String project) {

		boolean historyUpdate=true;
		
		int wf= 0;
		try {

			wf=workflowId;
			SpatialUnitStatusHistory sunitHistory=new SpatialUnitStatusHistory();
			sunitHistory.setUsin(id);
			sunitHistory.setWorkflow_status_id(4); //for approve ID-4
			sunitHistory.setUserid(userid);
			sunitHistory.setComments(comments);
			sunitHistory.setStatus_change_time(new Date());
			sunitHistory.setWorkflow_id(workflowId++);

			sunitHistoryDAO.makePersistent(sunitHistory);
		} catch (Exception e) {
			logger.error(e);
			historyUpdate=false;
		}

		if(historyUpdate){

			try {
				//return landRecordsDao.actionUpdateWorkflow(id,workflowId,1);

				SpatialUnitTable spatmp = landRecordsDao.findById(id, false);
				if(workflowId!=10 && workflowId!=15)
					spatmp.setWorkflow_id(workflowDAO.findById(workflowId, false));
				if(workflowId==9 || workflowId==14){
					spatmp.setStatus(statusDao.findById(7, false)); //for final status
				}
				else{
					spatmp.setStatus(statusDao.findById(8, false)); //for pending status
				}
				String countval="";
				String village_name = spatmp.getVillage_id().getVillage_code();
				village_name=village_name+"-";

				
				// check for print notice // kamal
				
				if(wf==3)
				{
					spatmp.setPublic_notice_startdate(new Date());
				}
					
				/*village_name.substring(village_name.length()-2);
				int village_no=0;

				try{
					village_no=Integer.parseInt(village_name.substring(village_name.length()-1));

					village_no=Integer.parseInt(village_name.substring(village_name.length()-2));		
				}
			catch(Exception e ){
				logger.error(e);

			}

				if(village_no!=0){
					village_name=village_name.substring(0, 4)+village_no+"-";	
				}

				else{
					village_name=village_name.substring(0, 4)+"-";	
				}*/
				//case for application no
				if((workflowId==2 || workflowId==11) && (spatmp.getApplication_no()==null || spatmp.getApplication_no()=="")){
					//ParcelCount parcelCount = parcelCountDao.findById(1, false); //get application count
					ParcelCount parcelCount = parcelCountDao.findParcelCountByTypeAndProjectName(ConstantUtil.APPLICATION, project);
					long count=parcelCount.getCount()+1;
					parcelCount.setCount(count);

					if(count<10)
						countval="0000"+String.valueOf(count);
					else if(count>=10 && count <=99)
						countval="000"+String.valueOf(count);
					else if (count>=100 && count<=999)
						countval="00"+String.valueOf(count);
					else if (count>=1000 && count<=9999)
						countval="0"+String.valueOf(count);
					else if(count>=10000 && count<=99999)
						countval=String.valueOf(count);
					spatmp.setApplication_no(village_name+countval);
					spatmp.setApplicationdate(new Date());
					parcelCountDao.makePersistent(parcelCount);
				}


				// PV no case
				else if(workflowId==6){
					//ParcelCount parcelCount = parcelCountDao.findById(2, false); //get pv count
					ParcelCount parcelCount = parcelCountDao.findParcelCountByTypeAndProjectName(ConstantUtil.PV, project);

					long count=parcelCount.getCount()+1;
					parcelCount.setCount(count);
					if(count<10)
						countval="0000"+String.valueOf(count);
					else if(count>=10 && count <=99)
						countval="000"+String.valueOf(count);
					else if (count>=100 && count<=999)
						countval="00"+String.valueOf(count);
					else if (count>=1000 && count<=9999)
						countval="0"+String.valueOf(count);
					else if(count>=10000 && count<=99999)
						countval=String.valueOf(count);
					spatmp.setPv_no(village_name+countval);
					parcelCountDao.makePersistent(parcelCount);
				}


				// APFR no case
				else if(workflowId==7 || workflowId==13 ){
					//ParcelCount parcelCount = parcelCountDao.findById(4, false); //get apfr count
					ParcelCount parcelCount = parcelCountDao.findParcelCountByTypeAndProjectName(ConstantUtil.APFR, project);
					long count=parcelCount.getCount()+1;
					parcelCount.setCount(count);
					if(count<10)
						countval="0000"+String.valueOf(count);
					else if(count>=10 && count <=99)
						countval="000"+String.valueOf(count);
					else if (count>=100 && count<=999)
						countval="00"+String.valueOf(count);
					else if (count>=1000 && count<=9999)
						countval="0"+String.valueOf(count);
					else if(count>=10000 && count<=99999)
						countval=String.valueOf(count);
					spatmp.setApfr_no(village_name+countval);
					//spatmp.setApfr_date(new Date());
					parcelCountDao.makePersistent(parcelCount);
				}

				landRecordsDao.makePersistent(spatmp);
				return workflowId; 


			} catch (Exception e) {
				logger.error(e);
				return 0;
			}
		}

		else{

			return 0;
		}

	}



	@Override
	public Integer rejectApprove(Long id, long userid, Integer workflowId,String comments ) {

		boolean historyUpdate=true;
		try {	
			SpatialUnitStatusHistory sunitHistory=new SpatialUnitStatusHistory();
			sunitHistory.setUsin(id);
			sunitHistory.setWorkflow_status_id(5); //for reject ID-5
			sunitHistory.setWorkflow_id(workflowId--);
			sunitHistory.setUserid(userid);
			sunitHistory.setComments(comments);
			sunitHistory.setStatus_change_time(new Date());
			sunitHistoryDAO.makePersistent(sunitHistory);
		} catch (Exception e) {
			logger.error(e);
			historyUpdate=false;
		}

		if(historyUpdate){

			try {
				SpatialUnitTable spa=landRecordsDao.findById(id, false);
				if(workflowId!=0 && workflowId!=9)
				{
					spa.setWorkflow_id(workflowDAO.findById(workflowId, false));
				}
				else{
					workflowId=workflowId+1;
				}
				spa.setStatus(statusDao.findById(5, false)); //for reject
				landRecordsDao.makePersistent(spa);

			} catch (Exception e) {
				logger.error(e);
			} 

			return workflowId;

		}

		else{

			return 0;
		}

	}




	@Override
	public List<ActionTools> findallAction(Integer id,String workId) {
		return actionToolsDao.findByRoleId(id,workId);
	}




	@Override
	public List<?> getSearchResult(String usin,String appno, String pvno, String apfr,
			String name, int apptype, int[] workids,String projectname,Integer startpos,int status) {
		return landRecordsDao.getSearchResult(usin,appno,pvno,apfr,name,apptype,workids,projectname,startpos,status);
	}



	@Override
	public Workflow getWorkflowbyId(Integer integer) {
		return workflowDAO.findById(integer, false);
	}



	@Override
	public int getSearchResult(String usin,String appno, String pvno, String apfr,
			String name, int apptype, int[] workids, String projectname,int status) {
		return landRecordsDao.getSearchCount(usin,appno,pvno,apfr,name,apptype,workids,projectname,status);
	}



	@Override
	public Status getStatusById(Integer integer) {
		return statusDao.findById(integer, false);
	}




	@Override
	public SpatialunitPersonwithinterest findPOIById(Long id) {
		return spatialUnitPersonWithInterestDao.findById(id, false);
	}




	@Override
	public List<String> findNeighbourstByUsin(Long id) {
		List<SpatialUnitTable> spatialObj = landRecordsDao.findSpatialUnitById(id);

		try {
			List<String> list = new ArrayList<String>();
			list.add(spatialObj.get(0).getNeighbor_north());
			list.add(spatialObj.get(0).getNeighbor_south());
			list.add(spatialObj.get(0).getNeighbor_east());
			list.add(spatialObj.get(0).getNeighbor_west());

			return list;
		} catch (Exception e) {
			logger.error(e);
			return new ArrayList<String>();
		}



	}



	@Override
	public List<SpatialUnit> findBbox(long id ,String project) {
		return spatialUnitDao.findBbox(id,project);
	}



	@Override
	public List<SpatialUnitStatusHistory> findStatusHistorytByUsin(Long id) {
		return sunitHistoryDAO.findHistoryByUsin(id);
	}



	@Override
	public SpatialUnitExtension findAllSpatialUnitByUsin(Long usin) {

		return spatialUnitExtensionDao.findSpatialunitbyUsin(usin);
	}




	@Override
	public boolean updateParcelCount(long parcel_count) {
		ParcelCount parcelCount = parcelCountDao.findById(3, false);
		parcelCount.setCount(parcel_count);
		try {
			parcelCountDao.makePersistent(parcelCount);
			return true;
		} catch (Exception e) {
			logger.error(e);
			return false;
		}

	}



	@Override
	public boolean updateSpatialExtension(SpatialUnitExtension speObj) {

		try {
			spatialUnitExtensionDao.makePersistent(speObj);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}



	@Override
	public long findcountbyId() {

		try {
			return parcelCountDao.findById(3, false).getCount();
		} catch (Exception e) {
			logger.error(e);
			return 0;
		}

	}



	@Override
	public SpatialUnitExtension findSpatialByUsin(long usin) {
		return spatialUnitExtensionDao.findSpatialunitbyUsin(usin);
	}




	@Override
	public List<Object> findparcelcountbytenure(String project) {
		return landRecordsDao.findparcelcountbytenure(project);
	}



	@Override
	public List<Object> findparcelcountbygender(String project,String tag,Integer villageId) {
		return landRecordsDao.findparcelcountbygender(project,tag,villageId);
	}



	@Override
	public SpatialUnitExtension updateExtn(SpatialUnitExtension spa_extn) {
		return spatialUnitExtensionDao.makePersistent(spa_extn);
	}



	@Override
	public List<LandUseType> getExistingUseName(String existing_use) {
		return landUseTypeDao.findEntriesById(existing_use);
	}



	@Override
	public List<TitleExisting> findAllTitles() {
		return titleExistingDao.findAll();
	}



	@Override
	public TitleExisting findTitleById(int title_id) {
		return titleExistingDao.findById(title_id, false);
	}



	@Override
	public List<Object> findregparcelcountbyTenure(String project,String tag,Integer villageId) {
		return landRecordsDao.findregparcelcountbyTenure(project,tag,villageId);
	}



	@Override
	public boolean savepaymentInfo(PaymentInfo paymentTmp) {

		try {
			paymentInfoDAO.makePersistent(paymentTmp);
		} catch (Exception e) {
			logger.error(e);
			return false;
		}

		return true;
	}



	@Override
	public List<Object> findRegistrytable(String project, String tag,Integer villageId) {
		return landRecordsDao.findRegistrytable(project,tag,villageId);
	}



	@Override
	public List<Village> findAllVillages() {
		return villageDAO.findactiveVillage();
	}



	@Override
	public boolean updateMayorDate(SpatialUnitExtension spaExtnObj) {
		try {
			spatialUnitExtensionDao.makePersistent(spaExtnObj);
			return true;
		} catch (Exception e) {
			logger.error(e);
			return false;
		}
	}



	@Override
	public Date findLetterGenerationDatebyUsin(Long usin) {
		return paymentInfoDAO.findLetterDate(usin);
	}



	@Override
	public boolean updateLetterGenerationDate(Long usin, Date letterdate) {
		return paymentInfoDAO.updateDate(usin,letterdate);
	}



	@Override
	public boolean savePayemrnt(PaymentInfo paymentInfo) {

		try {
			paymentInfoDAO.makePersistent(paymentInfo);
			return true;
		} catch (Exception e) {
			logger.error(e);
			return false;
		}
	}



	@Override
	public long findSFRname(Long id, int workflowId, int workflowStatus) {
		return sunitHistoryDAO.findSFRnameByUsin(id,workflowId,workflowStatus);
	}



	@Override
	public Date findNoticeStartDatebyUsin(Long usin) {
		try {
			return landRecordsDao.findSpatialUnitById(usin).get(0).getPublic_notice_startdate();
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}



	@Override
	public Date findApfrDatebyUsin(Long usin) {
		try {
			return landRecordsDao.findSpatialUnitById(usin).get(0).getApfr_date();
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}



	@Override
	public SpatialUnitTable setApfrDate(SpatialUnitTable spaObj) {
		try {
			return landRecordsDao.makePersistent(spaObj);
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}
}