package edu.calpoly.csc.luna.turboplan.web.client.model;

import com.google.gwt.user.client.rpc.IsSerializable;


public class GwtAddress implements IsSerializable {
	private String address;
	private String state;
	private String city;
	private int zip;
	private String country;
	
	/**
	 * Default Constructor
	 */
	public GwtAddress() {}

	/**
	 * @param address
	 * @param state
	 * @param city
	 * @param zip
	 */
	public GwtAddress(String address, String state, String city, int zip) {
		super();
		
		this.address = address;
		this.state = state;
		this.city = city;
		this.zip = zip;
		this.country = "United States";
	}
	
	/**
	 * Constructs a GWT address with a street address, state, city, zip, and country
	 * @param address The street address component of the address
	 * @param state The state component of the address
	 * @param city The city component of the address
	 * @param zip The zip component of the address
	 * @param country The country component of the address
	 */
	public GwtAddress(String address, String state, String city, int zip, String country) {
		super();
		
		this.address = address;
		this.state = state;
		this.city = city;
		this.zip = zip;
		this.country = country;
	}



	/**
	 * Gets the street address component of the address
	 * @return The street address component of the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Sets the street address component of the address
	 * @param address The street address component of the address
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
     * Gets the state component of the address
	 * @return The state component of the address
	 */
	public String getState() {
		return state;
	}

	/**
	 * Sets the state component of the address
	 * @param state The state component of the address
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * Gets the city component of the address
	 * @return The city component of the address
	 */
	public String getCity() {
		return city;
	}

	/**
	 * Sets the city component of the address
	 * @param city  The city component of the address
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * Gets the zipcode component of the address
	 * @return The zipcode component of the address
	 */
	public int getZip() {
		return zip;
	}

	/**
	 * Gets the zipcode component of the address
	 * @param zip The zipcode component of the address
	 */
	public void setZip(int zip) {
		this.zip = zip;
	}
	
	/**
	 * Gets the country component of the address
	 * @return The country component of the address
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * Sets the country component of the address
	 * @param country The country component of the address
	 */
	public void setCountry(String country) {
		this.country = country;
	}

}
