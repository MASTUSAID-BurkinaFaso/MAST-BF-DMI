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

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.PaymentInfoDAO;
import com.rmsi.mast.studio.domain.PaymentInfo;



@Repository
public class PaymentInfoHibernateDAO extends GenericHibernateDAO<PaymentInfo, Long>
		implements PaymentInfoDAO {
	private static final Logger logger = Logger.getLogger(PaymentInfoHibernateDAO.class);

	@Override
	public Date findLetterDate(Long usin) {

		try {
			Query query = getEntityManager().createQuery("Select pi from PaymentInfo pi where pi.usin = :usin");
			@SuppressWarnings("unchecked")
			List<PaymentInfo> paymentList = query.setParameter("usin", usin).getResultList();


			if(paymentList.size() > 0){
				return paymentList.get(0).getLetter_generation_date();
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

	@Override
	public boolean updateDate(Long usin, Date letterdate) {

		try {
		
			Query query = getEntityManager().createQuery("UPDATE PaymentInfo pi SET pi.letter_generation_date = :letter_generation_date  where pi.usin = :usin");
			int updaterec = query.setParameter("letter_generation_date", letterdate).setParameter("usin",usin).executeUpdate();	

			if(updaterec>0)
			{
				return true;
			}


		} catch (Exception e) {

			logger.error(e);
			return false;
		}

		return false;

	}
	
}
