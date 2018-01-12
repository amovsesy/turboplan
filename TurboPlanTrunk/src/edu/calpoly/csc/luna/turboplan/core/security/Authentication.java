package edu.calpoly.csc.luna.turboplan.core.security;

import org.apache.log4j.Logger;

import edu.calpoly.csc.luna.turboplan.core.dao.UserDao;
import edu.calpoly.csc.luna.turboplan.core.entity.User;
import edu.calpoly.csc.luna.turboplan.core.security.encryption.Encryption;

/**
 * This class is made to test the username and password
 *   stored in the database to the ones given at login.
 * 		
 */
public class Authentication {
	public static final Logger log = Logger.getLogger(Authentication.class);
	
	/**
	 * This is the compare method of the given and known
	 *   username and password
	 * 
	 * @param username the username given at login
	 * @param password the password given at login
	 * @return null if the user was not authenticated
	 *   and the usr if he/she was
	 */
	public static User authenticate(String username, String password) {
		log.debug("Authenticating: " + username + "/" + password);
		User usr = UserDao.getInstance().getUserByClearTextUsername(username);
		
		log.debug("Found user...comparing password");
		
		if (usr != null && usr.getPassword().equals(Encryption.getInstance().encrypt(password))) {
			log.debug("User authenticated");
			return usr;
		} else {
			log.debug("password don't match");
			return null;
		}
	}
}
