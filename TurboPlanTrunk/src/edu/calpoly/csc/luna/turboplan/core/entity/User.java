package edu.calpoly.csc.luna.turboplan.core.entity;

import java.util.EnumSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import edu.calpoly.csc.luna.turboplan.axis2ws.mobile.model.MobileUser;
import edu.calpoly.csc.luna.turboplan.core.entity.embeddable.UserProfile;
import edu.calpoly.csc.luna.turboplan.core.modelconverter.GwtModelConverter;
import edu.calpoly.csc.luna.turboplan.core.modelconverter.MobileModelConverter;
import edu.calpoly.csc.luna.turboplan.core.util.GwtUtil;
import edu.calpoly.csc.luna.turboplan.core.util.MobileUtil;
import edu.calpoly.csc.luna.turboplan.web.client.model.GwtUser;

/**
 * An object representing a User of TurboPlan
 * 
 * @author Ming Liu
 * @author Stephanie Long
 */
@Entity
@OnDelete(action=OnDeleteAction.CASCADE)
public class User extends AbstractUser implements GwtModelConverter<GwtUser>, MobileModelConverter<MobileUser>, Cloneable {

	/**
	 * A user's associated profile containing contact information 
	 */
	@Embedded
	private UserProfile profile;

	/**
	 * A user's assigned tasks
	 */
	@ManyToMany (fetch=FetchType.EAGER, cascade=CascadeType.PERSIST, mappedBy="users")
	@Cascade(value={org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	private Set<Task> tasks;

	/**
	 * Default constructor
	 * 
	 * @deprecated use other constructors instead. This is only used by
	 *             hibernate to construct reflectively
	 */
	@Deprecated
	public User() {
	}

	/**
	 * Constructor to create a user with a pre-created user name, password, and status
	 * 
	 * @param username The user name to assign the new user
	 * @param password The password to assign the new user
	 * @param activeStatus The status to assign the new user, default is Active
	 */
	public User(String username, String password, UserStatus activeStatus) {
		super(username, password, activeStatus);
	}
	/**
	 * Constructor to create a user with a pre-created user name, password, and status
	 * 
	 * @param profile The profile to assign the new user
	 * @param status The status to assign the new user
	 */
	public User(UserProfile profile, UserStatus status) {
		super();
		
		setProfile(profile);
		setActive(status);
	}

	/**
	 * Constructor to create a user with a pre-created user name, password, and status
	 * 
	 * @param profile The profile to assign the new user
	 * @param status The status to assign the new user
	 * @param company The company to assign the new user
	 */
	public User(UserProfile profile, UserStatus status, Company company) {
		super();
		
		setProfile(profile);
		setActive(status);
		setCompany(company);
		setPermission(EnumSet.noneOf(AbstractUser.Permission.class));
	}

	/**
	 * Set the profile of a user
	 * 
	 * @param profile The profile to assign the user
	 */
	public void setProfile(UserProfile profile) {
		this.profile = profile;
	}

	/**
	 * Get the profile of a user
	 * 
	 * @return The profile of a user
	 */
	public UserProfile getProfile() {
		return profile;
	}

	/**
	 * Get the assigned tasks of a user
	 * 
	 * @return The assigned tasks of a user
	 */
	public Set<Task> getTasks() {
		return tasks;
	}

	/**
	 * Set the assigned tasks of a user
	 * 
	 * @param tasks The tasks to assign the user
	 */
	public void setTasks(Set<Task> tasks) {
		this.tasks = tasks;
	}

	/**
	 * Convert the user to a mobile compatible version
	 */
	@Override
	public MobileUser convert2MobileModel() {
		return MobileUtil.hib2mobile(this);
	}

	/**
	 * Convert the user to a GWT compatible version
	 * 
	 * @return The GWT version of the user
	 */
	@Override
	public GwtUser convert2GwtModel() {
		return GwtUtil.hib2gwt(this);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((profile == null) ? 0 : profile.hashCode());
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
		if (!(obj instanceof User))
			return false;
		User other = (User) obj;
		if (profile == null) {
			if (other.profile != null)
				return false;
		} else if (!profile.equals(other.profile))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("[");
		
		builder.append("userID = ").append(getUserID()).append(", ");
		builder.append("username = ").append(getUserName()).append(", ");
		builder.append("password = ").append(getPassword()).append(", ");
		builder.append("company = ").append(getCompany()).append(", ");
		builder.append("permission = ").append(getPermission()).append(", ");
		builder.append("profile = ").append(profile.toString());
		
		return builder.append("]").toString();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Object clone() throws CloneNotSupportedException {
		User ret = (User) super.clone();
		
		ret.setProfile(profile);
		ret.setTasks(tasks);
		
		return ret;
	}
	
}
