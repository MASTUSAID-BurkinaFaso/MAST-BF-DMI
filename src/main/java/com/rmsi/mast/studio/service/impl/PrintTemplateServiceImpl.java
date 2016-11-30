
package com.rmsi.mast.studio.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rmsi.mast.studio.dao.PrintTemplateDAO;
import com.rmsi.mast.studio.domain.Printtemplate;
import com.rmsi.mast.studio.service.PrintTemplateService;

/**
 * @author Aparesh.Chakraborty
 *
 */
@Service
public class PrintTemplateServiceImpl implements PrintTemplateService{

	@Autowired
	private PrintTemplateDAO printTemplateDAO;
	

	@Override
	public List<Printtemplate> findByProjectName(String name) {
		
		return printTemplateDAO.findByProjectName(name);
		
	}
	
	@Override
	public List<Printtemplate> findAllPrintTemplates() {
		
		return printTemplateDAO.findAll();
		
	}

	
}
