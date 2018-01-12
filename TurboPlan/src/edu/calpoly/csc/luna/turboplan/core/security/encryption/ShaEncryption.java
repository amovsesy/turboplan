package edu.calpoly.csc.luna.turboplan.core.security.encryption;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;

import sun.misc.BASE64Encoder;

/**
 * This class is to encrypt using the sha algorithm.
 * 		
 */
public final class ShaEncryption extends Encryption {
	private static final String ENCODING = "UTF-8";
	private static final String ALGORITHM_NAME = "SHA";
	private static final Logger log = Logger.getLogger(ShaEncryption.class);
	private MessageDigest mdigest;

	/**
	 * Creates a new ShaEncryption object
	 * 
	 * @return a new ShaEncryption object
	 */
	protected ShaEncryption() {
		try {
			mdigest = MessageDigest.getInstance(ALGORITHM_NAME);
		} catch (NoSuchAlgorithmException e) {
			log.error(ALGORITHM_NAME + " algorithm does not exist", e);
		}
	}

	/**
	 * This method encrypts the plaintext using the sha algorithm
	 * 
	 * @param plaintext the string to be encrypted
	 * @return the encrypted string
	 */
	@Override
	public String encrypt(String plaintext) {
		synchronized (this) {
			try {
				mdigest.update(plaintext.getBytes(ENCODING));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				log.error(ENCODING + "encoding is not supported", e);
			}

			return new BASE64Encoder().encode(mdigest.digest());
		}
	}
}
