/* ----------------------------------------------------------------------
 * Copyright (c) 2011 by RMSI.
 * All Rights Reserved
 *
 * Permission to use this program and its related files is at the
 * discretion of RMSI Pvt Ltd.
 *
 * The licensee of RMSI Software agrees that:
 *    - Redistribution in whole or in part is not permitted.
 *    - Modification of source is not permitted.
 *    - Use of the source in whole or in part outside of RMSI is not
 *      permitted.
 *
 * THIS SOFTWARE IS PROVIDED ''AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL RMSI OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 * ----------------------------------------------------------------------
 */

package com.rmsi.mast.studio.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


//import com.googlecode.ehcache.annotations.Cacheable;
//import com.googlecode.ehcache.annotations.TriggersRemove;


import com.rmsi.mast.studio.dao.LayerDAO;
import com.rmsi.mast.studio.dao.LayerFieldDAO;
import com.rmsi.mast.studio.dao.LayerGroupDAO;
import com.rmsi.mast.studio.domain.Layer;
import com.rmsi.mast.studio.domain.LayerField;
import com.rmsi.mast.studio.domain.Layergroup;
import com.rmsi.mast.studio.service.LayerService;
import com.rmsi.mast.studio.util.ConfigurationUtil;
import com.rmsi.mast.viewer.web.mvc.LandRecordsController;

@Service
public class LayerServiceImpl implements LayerService{
	
	
	private static final Logger logger = Logger.getLogger(LayerServiceImpl.class);
	
	@Autowired
	private LayerDAO layerDao;
	@Autowired
	private LayerFieldDAO layerFieldDao;
	@Autowired
	private LayerGroupDAO layerGroupDao;
	
	//@Cacheable(cacheName="layerFBNCache")
	public List<Layer> findAllLayers(){
		List<Layer> lstLayers = layerDao.findAll();
		return lstLayers;
	}
	
	//@Cacheable(cacheName="layerFBNCache")
	public Layer findLayerByName(String alias){		
		Layer layer = layerDao.findByAliasName(alias);		
		return layer;
	}
	
	//@Cacheable(cacheName="layerFBNCache")
	public List<String> detailsByOrder(String id){
		return layerDao.getLayerByLayerOrder();
	}
	
	//@TriggersRemove(cacheName="layerFBNCache", removeAll=true)	    
	public Layer updateLayer(Layer layer, Set<LayerField> layerFieldSet){
		layer.getLayerFields().clear();
		layerFieldDao.delete(layer.getAlias());
		
		//Iterate and set the layer bean
		for(Iterator<LayerField> itrLayerField = layerFieldSet.iterator();
						itrLayerField.hasNext();){
			
			LayerField layerField = itrLayerField.next();
			layerField.setLayerBean(layer);
			
		}
		layer.setLayerFields(layerFieldSet);
		
		return layerDao.makePersistent(layer);
	}
	
	//@TriggersRemove(cacheName="layerFBNCache", removeAll=true)	    
	public Layer createLayer(Layer layer){
		return layerDao.makePersistent(layer);
	}
	
	//@TriggersRemove(cacheName={"projectFBNCache", "layerFBNCache", "layerGroupFBNCache", "maptipFBNCache"}, removeAll=true)	    
	public boolean deleteLayerById(String id){
		
		List<Layergroup> lyrGroup = layerGroupDao.findLayerGroupByLayerName(id);
		return layerDao.deleteLayerByAliasName(id, lyrGroup);
	}
	
	//@Cacheable(cacheName="layerFBNCache")
	public String getGeometryType(String id){
		return layerDao.getGeometryType(id);
	}
	
	public String saveSLD(String layerName, String sld){
		 try {
			sld = URLDecoder.decode(sld, "UTF-8");
			return layerDao.saveSLD(layerName, sld);
		} catch (UnsupportedEncodingException e) {
			logger.error(e);
		}
		return "fail";
	}
	
	//@Cacheable(cacheName="layerFBNCache")
	public Set<LayerField> getLayerFieldsByLayerName(String alias){
		Layer layer = layerDao.findByAliasName(alias);
		return layer.getLayerFields();
	}
	
	//@Cacheable(cacheName="layerFBNCache")
	public List<Object[]> getLayersVisibility(String[] layers){
		List<Object[]> lstVisibility = layerDao.getVisibilityStatus(layers);
		return lstVisibility;
	}
}
