package edu.calpoly.csc.luna.turboplan.axis2ws.mobile.model;

import edu.calpoly.csc.luna.turboplan.core.entity.Company;
import edu.calpoly.csc.luna.turboplan.core.modelconverter.EntityModelConverter;
import edu.calpoly.csc.luna.turboplan.core.util.MobileUtil;

public class MobileCompany implements MobileModel, EntityModelConverter<Company> {
	private Long companyID = 0L;

	private String name = "";
	private String address = "";

	public MobileCompany() {
		super(); 
	}
	
	public MobileCompany(String name, String address) {
		super();

		this.name = name;
		this.address = address;
	}

	public Long getCompanyID() {
		return companyID;
	}

	public void setCompanyID(Long companyID) {
		this.companyID = companyID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public Company toEntityModel() {
		return MobileUtil.mobile2hib(this);
	}
}
