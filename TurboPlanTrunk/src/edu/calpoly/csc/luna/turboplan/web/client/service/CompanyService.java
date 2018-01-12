package edu.calpoly.csc.luna.turboplan.web.client.service;

import edu.calpoly.csc.luna.turboplan.web.client.model.GwtCompany;

/**
 * This class is service used to add a company to the database.
 * 		
 */
public interface CompanyService extends BaseService {
	public void addCompany(GwtCompany company);
}
