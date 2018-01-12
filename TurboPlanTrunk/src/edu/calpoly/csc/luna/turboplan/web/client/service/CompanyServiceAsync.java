package edu.calpoly.csc.luna.turboplan.web.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;

import edu.calpoly.csc.luna.turboplan.web.client.model.GwtCompany;

/**
 * This class is service used to add a company to the database using async.
 * 		
 */
public interface CompanyServiceAsync extends BaseServiceAsync{
	public void addCompany(GwtCompany company, AsyncCallback<Object> asyncCallback);
}
