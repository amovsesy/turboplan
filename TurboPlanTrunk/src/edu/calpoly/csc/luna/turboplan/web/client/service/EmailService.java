package edu.calpoly.csc.luna.turboplan.web.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;

import edu.calpoly.csc.luna.turboplan.web.client.model.GwtUser;

/**
 * This class is service used to send emails when new users are created,
 *   passwords are reset, and usernames are reset.
 * 		
 */
public interface EmailService  extends BaseService {
	
	/**
	 * This is the method that sends the email to the correct email
	 * 
	 * @param password the password the user was set/reset to
	 * @param username the username the user was set/reset to
	 * @param justPass true if only the password was reset
	 * @param newUser true if a new user has been added
	 * @return the text of the email
	 */
	public String sendEmail(String password, String username, String email, Boolean justPass, Boolean newUser);
	
	/**
	 * This is the method that sends the email to the correct email
	 * 
	 * @param password the password the user was set to
	 * @param username the username the user was set to
	 * @param email the email of the user
	 * @return the text of the email
	 */
	public String sendEmailNewUser(String password, String username, String email);
	
	/**
	 * This is the method that sends the email to the correct email
	 * 
	 * @param password the password the user was reset to
	 * @param email the email of the user
	 * @return the text of the email
	 */
	public String sendEmailResetPass(String password, String email);
	
	/**
	 * This is the method that sends the email to the correct email
	 * 
	 * @param password the password the user was reset to
	 * @param username the username the user was reset to
	 * @param email the email of the user
	 * @return the text of the email
	 */
	public String sendEmailResetUnamePass(String username, String password, String email);
	
	/**
	 * This is the method that sends the email to the correct email
	 * 
	 * @param password the password the user was changed to
	 * @param email the email of the user
	 * @return the text of the email
	 */
	public String sendEmailChangePass(String password, String email);
	
	/**
	 * This is the method that sends the email to the correct email
	 * 
	 * @param firstname the firstname of the user
	 * @param lastname the lastname of the user
	 * @param email the email of the user
	 * @return the text of the email
	 */
	public String sendEmailChangeUsername(GwtUser gwtUsr);
}
