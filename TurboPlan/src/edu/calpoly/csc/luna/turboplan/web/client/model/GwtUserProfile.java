package edu.calpoly.csc.luna.turboplan.web.client.model;

import java.util.Date;
import java.util.Set;

/**
 * The Profile of the GWT User. Consists of the contact information.
 * 
 * @author Ming Liu
 * @author Stephanie Long
 *
 */
public class GwtUserProfile implements GwtModel, HasLazyFetchables {
	
	/**
	 * EmployeeGender represents the gender of the user
	 */
	public static enum EmployeeGender {
		/** Male User*/ Male, /**Female User*/ Female
	}
	
	private Long userID;
	private String firstName;
	private Character middleInitial;
	private String lastName;
	private Date dateOfBirth;
	private Long homePhoneNumber;
	private Long mobilePhoneNumber;
	private Long otherPhoneNumber;
	private Long otherPhoneNumber2;
	private Long otherPhoneNumber3;
	private String email;
	private String email2;
	private String email3;
	private String email4;
	private String email5;
	private EmployeeGender gender;
	private GwtAddress address;
	private boolean[][] availability;
	private Set<GwtSkill> skills;

	/**
	 * Empty Constructor
	 */
	public GwtUserProfile() {}
	
	
	/**
	 * Constructor creates a GWT user profile with the specified information
	 * 
	 * @param userID The user ID of the user the profile is associated with
	 * @param firstName The first name of the user the profile is associated with
	 * @param lastName The last name  of the user the profile is associated with
	 * @param dateOfBirth The date of birth of the user the profile is associated with
	 */
	public GwtUserProfile(Long userID, String firstName, String lastName, Date dateOfBirth) {
		super();
		this.userID = userID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
	}

	/**
	 * Gets the user ID associated with the user profile
	 * 
	 * @return The user ID associated with the profile
	 */
	public Long getUserID() {
		return userID;
	}

	/**
	 * Sets the user ID associated with the user profile
	 * 
	 * @param userID The user ID to associate with the profile
	 */
	public void setUserID(Long userID) {
		this.userID = userID;
	}

	/**
	 * Gets the first name associated with the user profile
	 * 
	 * @return The first name associated with the user profile
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets the first name associated with the user profile
	 * 
	 * @param firstName The first name to associate with the user profile
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Gets the middle initial associated with the user profile
	 * 
	 * @return The middle initial associated with the user profile
	 */
	public Character getMiddleInitial() {
		return middleInitial;
	}

	/**
	 * Sets the middle initial to associate with the user profile
	 * 
	 * @param middleInitial The middle initial to associate with the user profile
	 */
	public void setMiddleInitial(Character middleInitial) {
		this.middleInitial = middleInitial;
	}

	/**
	 * Gets the last name associated with the user profile
	 * 
	 * @return The last name associated with the user profile
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets the last name associated with the user profile
	 * 
	 * @param lastName The last name associated with the user profile
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Gets the date of birth associated with the user profile
	 * 
	 * @return The date of birth associated with the user profile
	 */
	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	/**
	 * Sets the date of birth to associate with the user profile
	 * 
	 * @param dateOfBirth The date of birth to associate with the user profile
	 */
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	/**
	 * Gets the home phone number associated with the user profile
	 * 
	 * @return The home phone number associated with the user profile
	 */
	public Long getHomePhoneNumber() {
		return homePhoneNumber;
	}

	/**
	 * Sets the home phone number associated with the user profile
	 * 
	 * @param homePhoneNumber The home phone number to associate with the user profile
	 */
	public void setHomePhoneNumber(Long homePhoneNumber) {
		this.homePhoneNumber = homePhoneNumber;
	}

	/**
	 * Gets the mobile phone number to associate with the user profile
	 * 
	 * @return The mobile phone number to associate with the user profile
	 */
	public Long getMobilePhoneNumber() {
		return mobilePhoneNumber;
	}

	
	/**
	 * Sets the mobile phone number associated with the user profile
	 * 
	 * @param mobilePhoneNumber The mobile phone number to associate with the user profile
	 */
	public void setMobilePhoneNumber(Long mobilePhoneNumber) {
		this.mobilePhoneNumber = mobilePhoneNumber;
	}

	/**
	 * The third phone number associated with the user profile
	 * 
	 * @return The third phone number associated with the user profile
	 */
	public Long getOtherPhoneNumber() {
		return otherPhoneNumber;
	}

	/**
	 * Sets the third phone number associated with the user profile
	 * 
	 * @param otherPhoneNumber The third phone number to associate with the user profile
	 */
	public void setOtherPhoneNumber(Long otherPhoneNumber) {
		this.otherPhoneNumber = otherPhoneNumber;
	}

	/**
	 * Gets the fourth phone number associated with the user profile
	 * 
	 * @return The fourth phone number to associate with the user profile
	 */
	public Long getOtherPhoneNumber2() {
		return otherPhoneNumber2;
	}

	/**
	 * Sets the fourth phone number associated with the user profile
	 * 
	 * @param otherPhoneNumber2 The fourth phone number to associate with the user profile
	 */
	public void setOtherPhoneNumber2(Long otherPhoneNumber2) {
		this.otherPhoneNumber2 = otherPhoneNumber2;
	}

	
	/**
	 * Gets the fifth phone number associated with the user profile
	 * 
	 * @return The fifth phone number associated with the user profile
	 */
	public Long getOtherPhoneNumber3() {
		return otherPhoneNumber3;
	}

	
	/**
	 * Sets the fifth phone number associated with the user profile
	 * 
	 * @param otherPhoneNumber3 The fifth phone number to associate with the user profile
	 */
	public void setOtherPhoneNumber3(Long otherPhoneNumber3) {
		this.otherPhoneNumber3 = otherPhoneNumber3;
	}

	/**
	 * Gets the primary email associated with the user profile
	 * 
	 * @return The primary email associated with the user profile
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the primary email associated with the user profile
	 * 
	 * @param email The primary email associated with the user profile
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * The secondary email associated with the user profile
	 * 
	 * @return The secondary email associated with the user profile
	 */
	public String getEmail2() {
		return email2;
	}

	/**
	 * Sets the secondary email associated with the user profile
	 * 
	 * @param email2 The secondary email associated with the user profile
	 */
	public void setEmail2(String email2) {
		this.email2 = email2;
	}

	/**
	 * Gets the third email associated with the user profile
	 * 
	 * @return The third email associated with the user profile
	 */
	public String getEmail3() {
		return email3;
	}

	/**
	 * Sets the third email associated with the user profile
	 * 
	 * @param email3 The third email associated with the user profile
	 */
	public void setEmail3(String email3) {
		this.email3 = email3;
	}

	/**
	 * Gets the fourth email associated with the user profile
	 * 
	 * @return The fourth email associated with the user profile
	 */
	public String getEmail4() {
		return email4;
	}

	/**
	 * Sets the fourth email associated with the user profile
	 * 
	 * @param email4 The fourth email associated with the user profile
	 */
	public void setEmail4(String email4) {
		this.email4 = email4;
	}

	/**
	 * Gets the fifth email associated with the user profile
	 * 
	 * @return The fifth email associated with the user profile
	 */
	public String getEmail5() {
		return email5;
	}

	/**
	 * Sets the fifth email associated with the user profile
	 * 
	 * @param email5 The fifth email associated with the user profile
	 */
	public void setEmail5(String email5) {
		this.email5 = email5;
	}

	/**
	 * Gets the gender associated with the user profile
	 * 
	 * @return The gender associated with the user profile
	 */
	public EmployeeGender getGender() {
		return gender;
	}

	/**
	 * Sets the gender associated with the user profile
	 * 
	 * @param gender The gender to associate with the user profile
	 */
	public void setGender(EmployeeGender gender) {
		this.gender = gender;
	}

	/**
	 * Gets the GWT Address associated with the user profile
	 * 
	 * @return The GWT address associated with the user profile
	 */
	public GwtAddress getAddress() {
		return address;
	}

	/**
	 * Sets the address associated with the user profile
	 * 
	 * @param address The address to associate with the user profile
	 */
	public void setAddress(GwtAddress address) {
		this.address = address;
	}

	/**
	 * Gets the availability associated with the GWT user profile
	 * 
	 * @return The availability associated with the GWT user profile
	 */
	public boolean [][] getAvailability() {
		return availability;
	}

	/**
	 * Sets the availability associated with the user profile
	 * 
	 * @param availability The availability to associate with the user profile
	 */
	public void setAvailability(boolean[][] availability) {
		this.availability = availability;
	}

	/**
	 * Gets the skill set associated with the user profile
	 * 
	 * @return The skill set associated with the user profile
	 */
	public Set<GwtSkill> getSkills() {
		return skills;
	}

	/**
	 * Sets the skills associated with the user profile
	 * 
	 * @param skills The skill set to associate with the user profile
	 */
	public void setSkills(Set<GwtSkill> skills) {
		this.skills = skills;
	}

	/**
	 * The user profile in string format
	 * 
	 * @return The user profile in string format
	 */
	public String toString() {
		StringBuilder builder = new StringBuilder();

		for (int i = 0; i < 7; i++) {
			switch (i) {
			case 0:
				builder.append("Sunday    ");
				break;
			case 1:
				builder.append("Monday    ");
				break;
			case 2:
				builder.append("Tuesday   ");
				break;
			case 3:
				builder.append("Wednesday ");
				break;
			case 4:
				builder.append("Thursday  ");
				break;
			case 5:
				builder.append("Friday    ");
				break;
			case 6:
				builder.append("Saturday  ");
				break;
			default:
				break;
			}

			for (int j = 0; j < 48; j++) {
				builder.append("|");
				if (availability[i][j]) {
					builder.append("1");
				} else {
					builder.append("0");
				}
			}
			builder.append("\n");
		}

		return builder.toString();
	}
}
