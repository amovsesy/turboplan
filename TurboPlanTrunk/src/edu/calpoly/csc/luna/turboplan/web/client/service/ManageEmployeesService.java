package edu.calpoly.csc.luna.turboplan.web.client.service;

import java.util.List;

import edu.calpoly.csc.luna.turboplan.web.client.model.GwtUser;

/**
 * ManageEmployeesService handles calls to the database to manage employees
 * @author Stephanie Long
 */
public interface ManageEmployeesService extends BaseService {
	
	/**
	 * Add a user to the database
	 * @param usr The user to add
	 * @throws MessagingException 
	 */
	public void addUser(GwtUser usr);
	
	 
	/**
	 * Get the users from the database who are co-workers of the specified user
	 * @param usr The user to get co-workers of
	 * @return The co-workers of the specified user
	 */
	public List<GwtUser> getUsers(GwtUser usr);
	
	/**
	 * Update a user in the database
	 * @param usr The user to update
	 */
	public void updateUser(GwtUser usr);
	
	/**
	 * Get the user associated with the specified Id
	 * @param Id The id to get the associated user of
	 * @return The associated user of the specified Id
	 */
	public GwtUser getUsrByID(Long Id);

	/**
	 * Delete a user in the database
	 * @param usr The user to delete
	 * @return True if the user was successfully deleted, else False
	 */
	public boolean deleteUser(GwtUser usr);
	
	/**
	 * Get the user associated with the specified user name
	 * @param username The user name to get the associated user of
	 * @return The associated user of the specified user name
	 */
	public GwtUser getUsrByUsername(String username);
	
	/**
	 * Get the user associated with the specified email
	 * @param email The email to get the associated user of
	 * @return The associated user of the specified email
	 */
	public GwtUser getUsrByEmail(String email);
}
