package edu.calpoly.csc.luna.turboplan.web.client.model;

import java.util.HashSet;
import java.util.Set;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwtext.client.widgets.MessageBox;

import edu.calpoly.csc.luna.turboplan.web.client.service.ServiceManager;

/**
 * A user in GWT compatible form
 * 
 * @author Ming Liu
 * @author Stephanie Long
 * 
 */
public class GwtUser implements GwtModel, HasLazyFetchables {
	/**
     * The permission of a user; grants the access to functionality for the user
	 */
	public static enum Permission {
		/** View of User's Schedule */ MySchedule, 
		/** View for Manager to Manage the Schedule*/ ManageSchedule,
		/** View to manage Employees, Manager Only */ ManageEmployees, 
		 /** View to manage Tasks, Manager/Admin Only */ ManageTasks,
		 /** View to manage user's preferences */ Preferences,
		 /** Root User */ Root,
	}
	
	/**
	 * The user's unique identifier
	 */
	private Long userID;
	/**
	 * The user's unique username
	 */
	private String username;
	/**
	 * The user's password
	 */
	private String password;

	/**
	 * The user's active status
	 */
	private boolean active;

	/**
	 * The company the user belongs to
	 */
	private GwtCompany company; /* Company is eager fetched */
	/**
	 * The profile associated with the user
	 */
	private GwtUserProfile profile; /* Profile is lazy fetched */
	/**
	 * The tasks assigned to the user
	 */
	private Set<GwtTask> tasks; /* Tasks is lazy fetched, use fetchTasks */
	
	/**
	 * The permissions the user is granted
	 */
	private Set<String> permission;

	public Set<String> getPermission() {
		return permission;
	}

	/**
	 * Sets the permissions for the user
	 * @param permission The permission set to assign the user
	 */
	public void setPermission(Set<String> permission) {
		this.permission = permission;
	}

	/**
	 * Empty Constructor. Makes a blank user name and password
	 */
	public GwtUser() {
		username = "";
		password = "";
	}

	/**
	 * Creates a new GWT user with the specified user name and password
	 * 
	 * @param usrn
	 *            The user name to set for the new user
	 * @param pass
	 *            The password to set for the new user
	 */
	public GwtUser(String usrn, String pass) {
		username = usrn;
		password = pass;
	}
	
	
	/**
	 * @param active
	 * @param company
	 * @param password
	 * @param permission
	 * @param profile
	 * @param tasks
	 * @param userID
	 * @param username
	 */
	public GwtUser(boolean active, GwtCompany company, String password, Set<String> permission, GwtUserProfile profile,
			Set<GwtTask> tasks, Long userID, String username) {
		super();
		this.active = active;
		this.company = company;
		this.password = password;
		this.permission = permission;
		this.profile = profile;
		this.tasks = tasks;
		this.userID = userID;
		this.username = username;
	}

	/**
	 * Get the user name of the GWT user
	 * 
	 * @return The GWT user's name
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Get the password of the GWT user
	 * 
	 * @return The GWT user's password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Get the profile of the GWT user
	 * 
	 * @return The GWT user's profile
	 */
	public GwtUserProfile getProfile() {
		return profile;
	}

	/**
	 * Get the company of the GWT user
	 * 
	 * @return The GWT user's company
	 */
	public GwtCompany getCompany() {
		return company;
	}

	/**
	 * Sets the company of the GWT user
	 * 
	 * @param company
	 *            The company of the GWT user
	 */
	public void setCompany(GwtCompany company) {
		this.company = company;
	}

	/**
	 * Sets the profile of the GWT user
	 * 
	 * @param profile
	 *            The user profile to set to the GWT user
	 */
	public void setProfile(GwtUserProfile profile) {
		this.profile = profile;
	}

	/**
	 * Sets the user ID of the GWT user
	 * 
	 * @param userID
	 *            The user ID to set the GWT user to
	 */
	public void setUserID(Long userID) {
		this.userID = userID;
	}

	/**
	 * Get the user ID of the GWT user
	 * 
	 * @return The GWT user's ID
	 */
	public Long getUserID() {
		return userID;
	}

	/**
	 * Sets the status of the user
	 * 
	 * @param active
	 *            True if the user should be active, false for inactive
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

	/**
	 * Checks the status of a user
	 * 
	 * @return True if the user is active, otherwise false
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * Get the status of the GWT user
	 * 
	 * @return The GWT user's status
	 */
	public String getStatus() {
		if (active) {
			return "Active";
		} else {
			return "Inactive";
		}
	}

	/**
	 * Set the assigned tasks of the GWT User
	 * 
	 * @param tasks
	 *            The tasks to assign the user
	 */
	public void setTasks(Set<GwtTask> tasks) {
		this.tasks = tasks;
	}

	/**
	 * Get the assigned task set of the GWT user
	 * 
	 * @return The GWT user's assigned task set
	 */
	public Set<GwtTask> getTasks() {
		return tasks;
	}

	/**
	 * Get the tasks of the current GWT user
	 * 
	 * @return The GWT user's assigned tasks
	 */
	public boolean fetchTasks() {
		if (tasks != null) {
			return true;
		}

		ServiceManager.getUserServiceAsync().getTasksForUser(userID, new AsyncCallback<Set<GwtTask>>() {
			public void onFailure(Throwable caught) {
				MessageBox.alert("Call failed when fetching task");
			}

			public void onSuccess(Set<GwtTask> result) {
				tasks = result;
			}
		});

		return false;
	}
	
	/**
	 * Adds a permission to the user
	 * @param perm The permission to add to the user
	 */
	public void addPermission(GwtUser.Permission perm) {
		if (permission == null) {
			permission = new HashSet<String>();
		}
		
		permission.add(perm.name());
	}
	
	/**
	 * Removes the permission from the user
	 * @param perm The permission to remove from the user
	 */
	public void removePermission(GwtUser.Permission perm) {
		permission.remove(perm.name());
	}
}
