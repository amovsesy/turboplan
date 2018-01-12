package edu.calpoly.csc.luna.turboplan.core.entity.embeddable;

import java.io.Serializable;
import javax.persistence.Embeddable;

import edu.calpoly.csc.luna.turboplan.core.util.StringUtil;

/**
 * This class represents an address for a user of TurboPlan. Fields
 * such as the street address, state, city, and zip are included in 
 * a user's address.
 * 
 * @author Ming Liu
 * @author Stephanie Long
 *
 */
@SuppressWarnings("serial")
@Embeddable
public class Address implements Serializable {
	private String address;
	private String state;
	private String city;
	private String country;
	private int zip;
	
	/**
	 * Empty constructor
	 * 
	 * @deprecated All fields in this class is required.  Do not use this default constructor.
	 */
	@Deprecated
	public Address() {
		super();
	}
	
	/**
	 * Constructor settings all the fields of an address
	 * @param address The street address correlated with the address
	 * @param city The city correlated with the address
	 * @param state The state correlated with the address
	 * @param zip The zip code correlated with the address
	 */
	public Address(String address, String city, String state, int zip) {
		super();
		this.address = address;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.country = "United States";
	}
	
	public Address(String address, String city, String country, String state,
			int zip) {
		super();
		this.address = address;
		this.city = city;
		this.country = country;
		this.state = state;
		this.zip = zip;
	}

	/**
	 * Gets the street address correlated with the address
	 * 
	 * @return The street address correlated with the address
	 */
	public String getAddress() {
		return address;
	}
	
	/**
	 * Sets the street address correlated with the address
	 * 
	 * @param address The address to set the street address field to
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	
	/**
	 * Gets the state correlated with the address
	 * 
	 * @return The state correlated with the address
	 */
	public String getState() {
		return state;
	}
	
	/**
	 * Sets the state correlated with the address
	 * 
	 * @param state The state to set the state field to 
	 */
	public void setState(String state) {
		this.state = state;
	}
	
	/**
	 * Gets the city correlated with the address
	 * @return The city correlated with the address 
	 */
	public String getCity() {
		return city;
	}
	
	/**
	 * Sets the city correlated with the address
	 * 
	 * @param city The city to set the city field to 
	 */
	public void setCity(String city) {
		this.city = city;
	}
	
	/**
	 * Gets the zip code correlated with the address
	 * @return The zip code correlated with the address
	 */
	public int getZip() {
		return zip;
	}
	
	/**
	 * Sets the zip code correlated with the address
	 * 
	 * @param zip The zip code to set the zip code field to 
	 */
	public void setZip(int zip) {
		this.zip = zip;
	}
	
	/**
	 * Gets the country correlated with the address
	 * @return The country correlated with the address
	 */
	public String getCountry() {
		return country;
	}
	
	/**
	 * Sets the country correlated with the address
	 * 
	 * @param country The country to set the country field to 
	 */
	public void setCountry(String country) {
		this.country = country;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + zip;
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Address))
			return false;
		Address other = (Address) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (country == null) {
			if (other.country != null)
				return false;
		} else if (!country.equals(other.country))
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		if (zip != other.zip)
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return StringUtil.beanToString(this).toString();
	}
}
