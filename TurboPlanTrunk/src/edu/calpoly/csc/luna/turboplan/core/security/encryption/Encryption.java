package edu.calpoly.csc.luna.turboplan.core.security.encryption;

import java.security.MessageDigest;

/**
 * This is am abstract class for the encryption of the username
 *   and password.
 * 		
 */
public abstract class Encryption {
	private static Encryption instance = new ShaEncryption();

	/**
	 * This is to return an instance of the Encryption class
	 * 
	 * @return the instance of the encryption
	 */
	public static Encryption getInstance() {
		return instance;
	}

	/**
	 * This method is to be overwriten by every encryption
	 * 
	 * @param input the string to be encrypted
	 * @return the encrypted string
	 */
	public abstract String encrypt(String input);
}
