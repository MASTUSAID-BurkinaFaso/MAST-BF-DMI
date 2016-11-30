/**
 * 
 */
package com.rmsi.mast.studio.mobile.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rmsi.mast.studio.dao.VillageDAO;
import com.rmsi.mast.studio.domain.AttributeOptions;
import com.rmsi.mast.studio.domain.Citizenship;
import com.rmsi.mast.studio.domain.Gender;
import com.rmsi.mast.studio.domain.GroupType;
import com.rmsi.mast.studio.domain.LandType;
import com.rmsi.mast.studio.domain.LandUseType;
import com.rmsi.mast.studio.domain.MaritalStatus;
import com.rmsi.mast.studio.domain.MutationType;
import com.rmsi.mast.studio.domain.NatureOfApplication;
import com.rmsi.mast.studio.domain.NatureOfPower;
import com.rmsi.mast.studio.domain.OccupancyType;
import com.rmsi.mast.studio.domain.ParcelType;
import com.rmsi.mast.studio.domain.ShareType;
import com.rmsi.mast.studio.domain.SlopeValues;
import com.rmsi.mast.studio.domain.SoilQualityValues;
import com.rmsi.mast.studio.domain.SpatialUnit;
import com.rmsi.mast.studio.domain.TenureClass;
import com.rmsi.mast.studio.domain.TitleExisting;
import com.rmsi.mast.studio.domain.Village;
import com.rmsi.mast.studio.mobile.dao.AttributeOptionsDao;
import com.rmsi.mast.studio.mobile.dao.CitizenshipDao;
import com.rmsi.mast.studio.mobile.dao.GenderDao;
import com.rmsi.mast.studio.mobile.dao.GroupTypeDao;
import com.rmsi.mast.studio.mobile.dao.LandTypeDao;
import com.rmsi.mast.studio.mobile.dao.LandUseTypeDao;
import com.rmsi.mast.studio.mobile.dao.MaritalStatusDao;
import com.rmsi.mast.studio.mobile.dao.OccupancyTypeDao;
import com.rmsi.mast.studio.mobile.dao.ShareTypeDao;
import com.rmsi.mast.studio.mobile.dao.SlopeValuesDao;
import com.rmsi.mast.studio.mobile.dao.SoilQualityValuesDao;
import com.rmsi.mast.studio.mobile.dao.TenureClassDao;
import com.rmsi.mast.studio.mobile.dao.hibernate.SpatialUnitHibernateDao;
import com.rmsi.mast.studio.mobile.service.SpatialUnitService;
import com.rmsi.mast.studio.util.GeometryConversion;
import com.rmsi.mast.viewer.dao.MutationTypeDao;
import com.rmsi.mast.viewer.dao.NatureOfApplicationDao;
import com.rmsi.mast.viewer.dao.NatureOfPowerDao;
import com.rmsi.mast.viewer.dao.ParcelTypeDao;
import com.rmsi.mast.viewer.dao.TitleExistingDao;

/**
 * @author Shruti.Thakur
 *
 */
@Service
public class SpatialUnitServiceImp implements SpatialUnitService {

	@Autowired
	SpatialUnitHibernateDao spatialUnitDao;

	@Autowired
	GenderDao genderDao;

	@Autowired
	MaritalStatusDao maritalStatusDao;

	@Autowired
	OccupancyTypeDao occupancyTypeDao;

	@Autowired
	TenureClassDao tenureClassDao;

	@Autowired
	ShareTypeDao tenureRelationTypeDao;

	@Autowired
	LandUseTypeDao landUseTypeDao;

	@Autowired
	SlopeValuesDao slopeValuesDao;

	@Autowired
	SoilQualityValuesDao soilQualityValuesDao;

	@Autowired
	GroupTypeDao groupTypeDao;

	@Autowired
	LandTypeDao landTypeDao;
	
	@Autowired
	CitizenshipDao citizenshipDao;
	
	@Autowired
	NatureOfPowerDao natureOfPowerDao;
	
	@Autowired
	VillageDAO villageDAO;
	
	@Autowired
	NatureOfApplicationDao natureOfApplicationDao;
	
	@Autowired
	MutationTypeDao mutationTypeDao;
	
	@Autowired
	ParcelTypeDao parcelTypeDao;

	@Autowired
	TitleExistingDao titleExistingdao;
	
	@Autowired
	AttributeOptionsDao attributeOptionsDao; 
	
	@Override
	public List<SpatialUnit> getSpatialUnitDataByProjectId(String projectId) {

		// Convert WKT to Geometry Type and returns the List<SpatialUnit>
		return new GeometryConversion().converGeometryToString(spatialUnitDao
				.getSpatialUnitByProject(projectId));

	}

	@Override
	public Gender getGenderById(long genderId) {

		return genderDao.getGenderById(genderId);

	}

	@Override
	public MaritalStatus getMartitalStatus(int maritalStatusId) {

		return maritalStatusDao.getMaritalStatusById(maritalStatusId);

	}

	@Override
	public OccupancyType getOccupancyTypeById(int occId) {

		return occupancyTypeDao.getOccupancyTypeById(occId);

	}

	@Override
	public TenureClass getTenureClassById(int tenureId) {

		return tenureClassDao.getTenureClassById(tenureId);

	}

	@Override
	public ShareType getShareTypeById(int tenureRelationId) {

		return tenureRelationTypeDao
				.getTenureRelationshipTypeById(tenureRelationId);

	}

	@Override
	public LandUseType getLandUseTypeById(int landUseTypeId) {

		return landUseTypeDao.getLandUseTypeById(landUseTypeId);

	}

	@Override
	public SpatialUnit getSpatialUnitByUsin(long usin) {

		return spatialUnitDao.getSpatialUnitByUsin(usin);

	}

	@Override
	public List<Long> getSpatialUnitByStatusId(String projectId, int statusId) {

		List<Long> usinList = new ArrayList<Long>();

		List<SpatialUnit> spatialUnit = spatialUnitDao
				.findSpatialUnitByStatusId(projectId, statusId);

		if (spatialUnit != null) {

			Iterator<SpatialUnit> spatialUnitIter = spatialUnit.iterator();

			while (spatialUnitIter.hasNext()) {

				usinList.add(spatialUnitIter.next().getUsin());
			}
		}
		return usinList;
	}

	@Override
	public GroupType getGroupTypeById(int groupTypeId) {

		return groupTypeDao.getGroupTypeById(groupTypeId);

	}

	@Override
	public SoilQualityValues getSoilQualityValuesById(int soilQualityValueId) {

		return soilQualityValuesDao
				.getSoilQualityValuesById(soilQualityValueId);

	}

	@Override
	public SlopeValues getSlopeValuesById(int slopeValuesId) {

		return slopeValuesDao.getSlopeValuesById(slopeValuesId);

	}

	@Override
	public LandType getLandTypeById(int landTypeId) {

		return landTypeDao.getLandTypeById(landTypeId);

	}

	@Override
	public Citizenship getCitizenship(int val) {
		return citizenshipDao.getCitizensbyId(val);
	}

	@Override
	public NatureOfPower getNop(int nopId) {
		return natureOfPowerDao.getNopById(nopId);
	}

	@Override
	public Village getVillageById(int villageId) {
	return villageDAO.findById(villageId, false);
	}

	@Override
	public NatureOfApplication getNoaById(int noaId) {
	return natureOfApplicationDao.findNoaByID(noaId);
	}

	@Override
	public MutationType getMtById(int mtId) {
		return mutationTypeDao.findMtById(mtId);
	}

	@Override
	public ParcelType getParcelTypeById(int parcelId) {
		return parcelTypeDao.findParcelTypeById(parcelId);
	}

	@Override
	public TitleExisting getExistingTitleById(int parseInt) {
		return titleExistingdao.findParcelTypeById(parseInt);
	}

	@Override
	public Integer getExistingId(int landUseTypeId) {
		return landUseTypeDao.getExistingById(landUseTypeId);
	}

	@Override
	public String getListExistingUse(String value) {
		List<Integer> tmpInt=new ArrayList<Integer>();
		String [] result=value.split(",");
		String returnvalue="";
		for (String string : result) {
			tmpInt.add(Integer.parseInt(string));
		}
		
		List<AttributeOptions> lstTmp = attributeOptionsDao.getAttributeOptions(16l);
		int i=0;
		for (AttributeOptions attributeOptions : lstTmp) {
			
			if(tmpInt.contains(attributeOptions.getId())){
				if(i==0){
					
					returnvalue+=attributeOptions.getParentId();
					i++;
				}
				else{
					returnvalue+=","+attributeOptions.getParentId();
				}
				
			}
				
			
		}
		return returnvalue;
	}

	
}
