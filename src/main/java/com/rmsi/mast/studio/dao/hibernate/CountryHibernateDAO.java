
package com.rmsi.mast.studio.dao.hibernate;

import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.CommuneDAO;
import com.rmsi.mast.studio.dao.CountryDAO;
import com.rmsi.mast.studio.domain.Commune;
import com.rmsi.mast.studio.domain.Country;


@Repository
public class CountryHibernateDAO extends GenericHibernateDAO<Country, Integer>
		implements CountryDAO {
	
}
