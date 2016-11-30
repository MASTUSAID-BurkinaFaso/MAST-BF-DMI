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
package com.rmsi.mast.studio.dao.hibernate;

import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.CommuneDAO;
import com.rmsi.mast.studio.domain.Commune;


@Repository
public class CommuneHibernateDAO extends GenericHibernateDAO<Commune, Integer>
		implements CommuneDAO {

	
	private static final Logger logger = Logger.getLogger(CommuneHibernateDAO.class);
	
	@Override
	public List<Commune> findbyProvinceId(Integer provincenId) {
		
		
		try {

			Query query = getEntityManager().createQuery("Select p from Commune p where p.province.provinceId = :provinceId");
			@SuppressWarnings("unchecked")
			List<Commune> communelst = query.setParameter("provinceId", provincenId).getResultList();		

			if(communelst.size() > 0){
				return communelst;
			}		
			else
			{
				return null;
			}
		} catch (Exception e) {

			logger.error(e);
			return null;
		}
	
	}
	
}