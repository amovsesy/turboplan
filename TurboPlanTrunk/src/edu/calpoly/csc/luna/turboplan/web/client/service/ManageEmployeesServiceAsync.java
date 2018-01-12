package edu.calpoly.csc.luna.turboplan.web.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import edu.calpoly.csc.luna.turboplan.web.client.model.GwtUser;


/**
 * ManageEmployeesServicesAsync handles the calls made to the database to manage users in the system
 * 
 * @author Stephanie Long
 */
public interface ManageEmployeesServiceAsync extends BaseServiceAsync {
	
	/**
	 * Add a user to the database
	 * @param usr The user to add
	 * @param callback
	 */
	public void addUser(GwtUser usr, AsyncCallback<Object> callback);
	
	/**
	 * Get users by the user specified's company
	 * @param usr The user to reference the company to get other co-workers by
	 * @param asyncCallback
	 */
	public void getUsers(GwtUser usr, AsyncCallback<List<GwtUser>> asyncCallback);
	
	/**
	 * Get a user by their specified id
	 * @param Id The id to get the associated user of 
	 * @param asyncCallback
	 */
	public void getUsrByID(Long Id, AsyncCallback<GwtUser> asyncCallback);
	
	/**
	 * Update the user profile in the database
	 * @param usrProfile The user profile to update
	 * @param callback
	 */
	public void updateUser(GwtUser usr, AsyncCallback<Object> callback);
	
	/**
	 * Delete a user from the database
	 * @param usr The user to delete in the database
	 * @param callback
	 */
	public void deleteUser(GwtUser usr, AsyncCallback<Boolean> callback);
	
	/**
	 * Get a user by the specified user name
	 * @param username The user name to get the associated user of
	 * @param asyncCallback
	 */
	public void getUsrByUsername(String username, AsyncCallback<GwtUser> asyncCallback);
	
	/**
	 * Get a user by the specified email
	 * @param email The email to get the associated user of
	 * @param asyncCallback
	 */
	public void getUsrByEmail(String email, AsyncCallback<GwtUser> asyncCallback);
}
