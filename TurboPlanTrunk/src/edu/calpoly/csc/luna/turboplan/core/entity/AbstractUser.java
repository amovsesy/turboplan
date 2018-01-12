package edu.calpoly.csc.luna.turboplan.core.entity;

import java.util.EnumSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Index;
import org.hibernate.annotations.Type;
import org.hibernate.validator.NotEmpty;
import org.hibernate.validator.NotNull;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class AbstractUser extends BaseEntity implements Cloneable {

	/**
	 * Permissions represent the feature access a user should be granted while
	 * using TurboPlan
	 */
	public static enum Permission {
		/** View of User's Schedule */
		MySchedule,
		/** View to manage Employees' Schedules */
		ManageSchedule,
		/** View to manage Employees, Manager/Admin Only */
		ManageEmployees,
		/** View to manage tasks, Manager/Admin Only */
		ManageTasks,
		/** View to manage personal informations */
		Preferences,
		/** Root User */
		Root,
	}

	/**
	 * UserStatus represents the a user's status in TurboPlan
	 */
	public static enum UserStatus {
		/** A current employee of a subscribing company */
		Active,
		/** A non-current employee of a subscribing company */
		Inactive
	}

	/**
	 * A user's genuine ID for TurboPlan recognition and access
	 */
	@Id
	@GeneratedValue
	private Long userID;

	/**
	 * A user's name for TurboPlan recognition and access
	 */
	@NotEmpty
	@Index(name = "userNameIndex")
	@Column (unique = true)
	private String userName;

	/**
	 * A user's password for TurboPlan recognition and access
	 */
	@NotEmpty
	private String password;

	/**
	 * A user's status for TurboPlan access
	 */
	@NotNull
	@Index(name = "activeStatusIndex")
	private UserStatus activeStatus;

	/**
	 * A user's set of permissions which determines which features of TurboPlan
	 * a user can access
	 */
	@NotNull
	@Type(type = "serializable")
	@Column(length = Short.MAX_VALUE)
	@Lob
	private EnumSet<Permission> permission;

	/**
	 * A user's associated subscribed company to access in TurboPlan
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "company", updatable = true)
	private Company company;

	public AbstractUser() {
		super();
	}

	public AbstractUser(String username, String password, UserStatus activeStatus) {
		super();
		
		this.userName = username;
		this.password = password;
		this.activeStatus = activeStatus;
		this.permission = EnumSet.noneOf(Permission.class);
	}
	
	/**
	 * @param activeStatus
	 * @param company
	 * @param password
	 * @param permission
	 * @param userName
	 */
	public AbstractUser(UserStatus activeStatus, Company company, String password, EnumSet<Permission> permission,
			String userName) {
		super();
		this.activeStatus = activeStatus;
		this.company = company;
		this.password = password;
		this.permission = permission;
		this.userName = userName;
	}

	/**
	 * @param activeStatus
	 * @param company
	 * @param password
	 * @param permission
	 * @param userID
	 * @param userName
	 */
	public AbstractUser(UserStatus activeStatus, Company company, String password, EnumSet<Permission> permission,
			Long userID, String userName) {
		super();
		this.activeStatus = activeStatus;
		this.company = company;
		this.password = password;
		this.permission = permission;
		this.userID = userID;
		this.userName = userName;
	}

	/**
	 * Set the userId of a user
	 * 
	 * @param userID
	 *            The userId to assign the user
	 */
	public void setUserID(Long userID) {
		this.userID = userID;
	}

	/**
	 * Get the userId of a user
	 * 
	 * @return The userId of a user
	 */
	public Long getUserID() {
		return userID;
	}

	/**
	 * Set the user name of a user
	 * 
	 * @param userName
	 *            The userName to assign the user
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * Get the user name of a user
	 * 
	 * @return The user name of a user
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * Set the password of a user
	 * 
	 * @param password
	 *            The password to assign the user
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Get the password of a user
	 * 
	 * @return The password of a user
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Set the status of a user
	 * 
	 * @param active
	 *            The status to assign the user
	 */
	public void setActive(UserStatus active) {
		this.activeStatus = active;
	}

	/**
	 * Get the status of a user
	 * 
	 * @return The status of a user
	 */
	public UserStatus isActive() {
		return activeStatus;
	}

	/**
	 * Set the permissions associated with a user
	 * 
	 * @param permission
	 *            The permissions to assign the user
	 */
	public void setPermission(EnumSet<Permission> permission) {
		this.permission = permission;
	}

	/**
	 * Get the permissions of a user
	 * 
	 * @return The permissions of a user
	 */
	public EnumSet<Permission> getPermission() {
		return permission;
	}

	/**
	 * Set the company of a user
	 * 
	 * @param company
	 *            The company to assign the user
	 */
	public void setCompany(Company company) {
		this.company = company;
	}

	/**
	 * Get the company of a user
	 * 
	 * @return The company of a user
	 */
	public Company getCompany() {
		return company;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((activeStatus == null) ? 0 : activeStatus.hashCode());
		result = prime * result + ((company == null) ? 0 : company.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((permission == null) ? 0 : permission.hashCode());
		result = prime * result + ((userID == null) ? 0 : userID.hashCode());
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof AbstractUser))
			return false;
		AbstractUser other = (AbstractUser) obj;
		if (activeStatus == null) {
			if (other.activeStatus != null)
				return false;
		} else if (!activeStatus.equals(other.activeStatus))
			return false;
		if (company == null) {
			if (other.company != null)
				return false;
		} else if (!company.equals(other.company))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (permission == null) {
			if (other.permission != null)
				return false;
		} else if (!permission.equals(other.permission))
			return false;
		if (userID == null) {
			if (other.userID != null)
				return false;
		} else if (!userID.equals(other.userID))
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {
		AbstractUser ret = (AbstractUser) super.clone();
		
		ret.setActive(activeStatus);
		ret.setCompany(company);
		ret.setPassword(password);
		ret.setPermission(permission);
		ret.setUserID(userID);
		ret.setUserName(userName);

		return ret;
	}
}
