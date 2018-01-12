package edu.calpoly.csc.luna.turboplan.core.entity.embeddable;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Parent;
import org.hibernate.annotations.Type;
import org.hibernate.validator.Email;
import org.hibernate.validator.NotEmpty;
import org.hibernate.validator.NotNull;

import edu.calpoly.csc.luna.turboplan.core.entity.Skill;
import edu.calpoly.csc.luna.turboplan.core.entity.User;
import edu.calpoly.csc.luna.turboplan.core.entity.part.Availability;
import edu.calpoly.csc.luna.turboplan.core.util.StringUtil;

/**
 * An object representing a User's Profile of TurboPlan. Includes the contact
 * information associated with the user.
 * 
 * @author Ming Liu
 * @author Stephanie Long
 */
@Embeddable
public class UserProfile {
	/**
	 * EmployeeGender represents the genders a user of TurboPlan must be
	 * assigned
	 */
	public static enum EmployeeGender {
		/** Male User */
		Male,
		/** Female User */
		Female
	};

	/**
	 * A user's first name
	 */
	@NotEmpty
	private String firstName;

	/**
	 * A user's middle initial
	 */
	@Column(nullable = true)
	private Character middleInitial;

	/**
	 * A user's last name
	 */
	@NotEmpty
	private String lastName;

	/**
	 * A user's date of birth
	 */
	@NotNull
	private Date dateOfBirth;

	/**
	 * A user's home phone number
	 */
	private String homePhoneNumber;

	/**
	 * A user's mobile phone number
	 */
	private String mobilePhoneNumber;

	/**
	 * A user's extra phone number
	 */
	private String otherPhoneNumber;

	/**
	 * A user's extra phone number
	 */
	private String otherPhoneNumber2;

	/**
	 * A user's extra phone number
	 */
	private String otherPhoneNumber3;

	/**
	 * A user's primary email address
	 */
	@Email
	private String email;

	/**
	 * A user's secondary email address
	 */
	@Email
	private String email2;

	/**
	 * A user's extra email address
	 */
	@Email
	private String email3;

	/**
	 * A user's extra email address
	 */
	@Email
	private String email4;

	/**
	 * A user's extra email address
	 */
	@Email
	private String email5;

	/**
	 * A user's gender
	 */
	@Enumerated(EnumType.STRING)
	private EmployeeGender gender;

	/**
	 * A user's address
	 */
	@Embedded
	private Address address;

	/**
	 * A user's availability
	 */
	@Type(type = "serializable")
	@Column(length = Short.MAX_VALUE - 1)
	@Lob
	private Availability availability;

	/**
	 * A user's skills
	 */
	@OneToMany(fetch = FetchType.EAGER)
	private Set<Skill> skills;

	/** 
	 * The user the profile represents
	 */
	@Parent
	private User usr;

	/**
	 * Default constructor
	 * 
	 * @deprecated use other constructors instead. This is only used by
	 *             hibernate to construct reflectively
	 */
	@Deprecated
	public UserProfile() {
	}


	/**
	 * Constructor to create a user profile with a pre-created user first and last name, and date of birth
	 * @param firstName The first name for the user profile
	 * @param lastName The last name for the user profile
	 * @param dateOfBirth The date of birth for the user profile
	 */
	public UserProfile(String firstName, String lastName, Date dateOfBirth) {
		super();

		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.address = new Address();
	}

	/**
	 * Constructor to create a user profile with a pre-created user first and last name, date of birth, and 
	 * availability
	 * @param firstName The first name for the user profile
	 * @param lastName The last name for the user profile
	 * @param dateOfBirth The date of birth for the user profile
	 * @param avail The availability of the user associated with the user profile
	 */
	public UserProfile(String firstName, String lastName, Date dateOfBirth, boolean[][] avail) {
		super();

		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.address = new Address();
		this.availability = new Availability(avail);
	}
	
	/**
	 * Constructor to create a user profile with a pre-created user first and last name, date of birth, 
	 * availability, and email
	 * @param firstName The first name for the user profile
	 * @param lastName The last name for the user profile
	 * @param dateOfBirth The date of birth for the user profile
	 * @param avail The availability of the user associated with the user profile
	 * @param email The primary email for the user profile
	 * 
	 */
	public UserProfile(String firstName, String lastName, Date dateOfBirth, boolean[][] avail, String email) {
		super();

		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.address = new Address();
		this.availability = new Availability(avail);
		this.email = email;
	}

	/**
	 * Get the first name of a user
	 * 
	 * @return The first name of a user
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Set the first name of a user
	 * 
	 * @param firstName
	 *            The firstName to assign the user
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Get the middle initial of a user
	 * 
	 * @return The middle initial of a user
	 */
	public Character getMiddleInitial() {
		return middleInitial;
	}

	/**
	 * Set the middle initial of a user
	 * 
	 * @param middleInitial
	 *            The middleInitial to assign the user
	 */
	public void setMiddleInitial(Character middleInitial) {
		this.middleInitial = middleInitial;
	}

	/**
	 * Get the last name of a user
	 * 
	 * @return The last name of a user
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Set the last name of a user
	 * 
	 * @param lastName
	 *            The lastName to assign the user
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Get the date of birth of a user
	 * 
	 * @return The date of birth of a user
	 */
	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	/**
	 * Set the date of birth of a user
	 * 
	 * @param dateOfBirth
	 *            The date of birth to assign the user
	 */
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	/**
	 * Get the home phone number of a user
	 * 
	 * @return The home phone number of a user
	 */
	public String getHomePhoneNumber() {
		return homePhoneNumber;
	}

	/**
	 * Set the home phone number of a user
	 * 
	 * @param homePhoneNumber
	 *            The home phone number to assign the user
	 */
	public void setHomePhoneNumber(String homePhoneNumber) {
		this.homePhoneNumber = homePhoneNumber;
	}

	/**
	 * Get the mobile phone number of a user
	 * 
	 * @return The mobile phone number of a user
	 */
	public String getMobilePhoneNumber() {
		return mobilePhoneNumber;
	}

	/**
	 * Set the mobile phone number of a user
	 * 
	 * @param mobilePhoneNumber
	 *            The mobile phone number to assign the user
	 */
	public void setMobilePhoneNumber(String mobilePhoneNumber) {
		this.mobilePhoneNumber = mobilePhoneNumber;
	}

	/**
	 * Get the third phone number of a user
	 * 
	 * @return The third phone number of a user
	 */
	public String getOtherPhoneNumber() {
		return otherPhoneNumber;
	}

	/**
	 * Set the third phone number of a user
	 * 
	 * @param otherPhoneNumber
	 *            The third phone number to assign the user
	 */
	public void setOtherPhoneNumber(String otherPhoneNumber) {
		this.otherPhoneNumber = otherPhoneNumber;
	}

	/**
	 * Get the fourth phone number of a user
	 * 
	 * @return The fourth phone number of a user
	 */
	public String getOtherPhoneNumber2() {
		return otherPhoneNumber2;
	}

	/**
	 * Set the fourth phone number of a user
	 * 
	 * @param otherPhoneNumber2
	 *            The fourth phone number to assign the user
	 */
	public void setOtherPhoneNumber2(String otherPhoneNumber2) {
		this.otherPhoneNumber2 = otherPhoneNumber2;
	}

	/**
	 * Get the fifth phone number of a user
	 * 
	 * @return The fifth phone number of a user
	 */
	public String getOtherPhoneNumber3() {
		return otherPhoneNumber3;
	}

	/**
	 * Set the fifth phone number of a user
	 * 
	 * @param otherPhoneNumber3
	 *            The fifth phone number to assign the user
	 */
	public void setOtherPhoneNumber3(String otherPhoneNumber3) {
		this.otherPhoneNumber3 = otherPhoneNumber3;
	}

	/**
	 * Get the first email of a user
	 * 
	 * @return The first email of a user
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Set the email of a user
	 * 
	 * @param email
	 *            The primary email to assign the user
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Get the second email of a user
	 * 
	 * @return The second email of a user
	 */
	public String getEmail2() {
		return email2;
	}

	/**
	 * Set the secondary email of a user
	 * 
	 * @param email2
	 *            The secondary email to assign the user
	 */
	public void setEmail2(String email2) {
		this.email2 = email2;
	}

	/**
	 * Get the third email of a user
	 * 
	 * @return The third email of a user
	 */
	public String getEmail3() {
		return email3;
	}

	/**
	 * Set the third email of a user
	 * 
	 * @param email3
	 *            The third email to assign the user
	 */
	public void setEmail3(String email3) {
		this.email3 = email3;
	}

	/**
	 * Get the fourth email of a user
	 * 
	 * @return The fourth email of a user
	 */
	public String getEmail4() {
		return email4;
	}

	/**
	 * Set the fourth email of a user
	 * 
	 * @param email4
	 *            The fourth email to assign the user
	 */
	public void setEmail4(String email4) {
		this.email4 = email4;
	}

	/**
	 * Get the fifth email of a user
	 * 
	 * @return The fifth email of a user
	 */
	public String getEmail5() {
		return email5;
	}

	/**
	 * Set the fifth email of a user
	 * 
	 * @param email5
	 *            The fifth email to assign the user
	 */
	public void setEmail5(String email5) {
		this.email5 = email5;
	}

	/**
	 * Get the gender of a user
	 * 
	 * @return The gender of a user
	 */
	public EmployeeGender getGender() {
		return gender;
	}

	/**
	 * Set the gender of a user
	 * 
	 * @param gender
	 *            The gender to assign the user
	 */
	public void setGender(EmployeeGender gender) {
		this.gender = gender;
	}

	/**
	 * Get the address of a user
	 * 
	 * @return The address of a user
	 */
	public Address getAddress() {
		return address;
	}

	/**
	 * Set the address of a user
	 * 
	 * @param address
	 *            The address to assign the user
	 */
	public void setAddress(Address address) {
		this.address = address;
	}

	/**
	 * Get the availability of a user
	 * 
	 * @return The availability of a user
	 */
	public Availability getAvailability() {
		return availability;
	}

	/**
	 * Set the availability of a user
	 * 
	 * @param availability
	 *            The availability to assign the user
	 */
	public void setAvailability(Availability availability) {
		this.availability = availability;
	}

	/**
	 * Get the skills of a user
	 * 
	 * @return The skills of a user
	 */
	public Set<Skill> getSkills() {
		return skills;
	}

	/**
	 * Set the skills of a user
	 * 
	 * @param skills
	 *            The skills to assign the user
	 */
	public void setSkills(Set<Skill> skills) {
		this.skills = skills;
	}

	public User getUsr() {
		return usr;
	}

	public void setUsr(User usr) {
		this.usr = usr;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((availability == null) ? 0 : availability.hashCode());
		result = prime * result + ((dateOfBirth == null) ? 0 : dateOfBirth.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((email2 == null) ? 0 : email2.hashCode());
		result = prime * result + ((email3 == null) ? 0 : email3.hashCode());
		result = prime * result + ((email4 == null) ? 0 : email4.hashCode());
		result = prime * result + ((email5 == null) ? 0 : email5.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((gender == null) ? 0 : gender.hashCode());
		result = prime * result + ((homePhoneNumber == null) ? 0 : homePhoneNumber.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((middleInitial == null) ? 0 : middleInitial.hashCode());
		result = prime * result + ((mobilePhoneNumber == null) ? 0 : mobilePhoneNumber.hashCode());
		result = prime * result + ((otherPhoneNumber == null) ? 0 : otherPhoneNumber.hashCode());
		result = prime * result + ((otherPhoneNumber2 == null) ? 0 : otherPhoneNumber2.hashCode());
		result = prime * result + ((otherPhoneNumber3 == null) ? 0 : otherPhoneNumber3.hashCode());
		result = prime * result + ((skills == null) ? 0 : skills.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof UserProfile))
			return false;
		UserProfile other = (UserProfile) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (availability == null) {
			if (other.availability != null)
				return false;
		} else if (!availability.equals(other.availability))
			return false;
		if (dateOfBirth == null) {
			if (other.dateOfBirth != null)
				return false;
		} else if (!dateOfBirth.equals(other.dateOfBirth))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (email2 == null) {
			if (other.email2 != null)
				return false;
		} else if (!email2.equals(other.email2))
			return false;
		if (email3 == null) {
			if (other.email3 != null)
				return false;
		} else if (!email3.equals(other.email3))
			return false;
		if (email4 == null) {
			if (other.email4 != null)
				return false;
		} else if (!email4.equals(other.email4))
			return false;
		if (email5 == null) {
			if (other.email5 != null)
				return false;
		} else if (!email5.equals(other.email5))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (gender == null) {
			if (other.gender != null)
				return false;
		} else if (!gender.equals(other.gender))
			return false;
		if (homePhoneNumber == null) {
			if (other.homePhoneNumber != null)
				return false;
		} else if (!homePhoneNumber.equals(other.homePhoneNumber))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (middleInitial == null) {
			if (other.middleInitial != null)
				return false;
		} else if (!middleInitial.equals(other.middleInitial))
			return false;
		if (mobilePhoneNumber == null) {
			if (other.mobilePhoneNumber != null)
				return false;
		} else if (!mobilePhoneNumber.equals(other.mobilePhoneNumber))
			return false;
		if (otherPhoneNumber == null) {
			if (other.otherPhoneNumber != null)
				return false;
		} else if (!otherPhoneNumber.equals(other.otherPhoneNumber))
			return false;
		if (otherPhoneNumber2 == null) {
			if (other.otherPhoneNumber2 != null)
				return false;
		} else if (!otherPhoneNumber2.equals(other.otherPhoneNumber2))
			return false;
		if (otherPhoneNumber3 == null) {
			if (other.otherPhoneNumber3 != null)
				return false;
		} else if (!otherPhoneNumber3.equals(other.otherPhoneNumber3))
			return false;
		if (skills == null) {
			if (other.skills != null)
				return false;
		} else if (!skills.equals(other.skills))
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return StringUtil.beanToString(this, "availability", "skills", "usr").toString();
	}
}