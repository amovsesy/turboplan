package edu.calpoly.csc.luna.turboplan.web.client.model;

import java.util.Set;



public class GwtCompany implements GwtModel {
	private Long companyID;
	private String name;
	private String address;
	private Set<GwtUser> users;

	public GwtCompany() {}
	
	/**
	 * @param address
	 * @param name
	 */
	public GwtCompany(String address, String name) {
		super();
		this.address = address;
		this.name = name;
	}

	public GwtCompany(Long companyID, String name, String address) {
		super();
		this.companyID = companyID;
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

	/**
	 * @param users the users to set
	 */
	public void setUsers(Set<GwtUser> users) {
		this.users = users;
	}

	/**
	 * @return the users
	 */
	public Set<GwtUser> getUsers() {
		return users;
	}
	
}
