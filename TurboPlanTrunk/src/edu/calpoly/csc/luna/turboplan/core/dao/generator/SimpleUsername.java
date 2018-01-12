package edu.calpoly.csc.luna.turboplan.core.dao.generator;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import edu.calpoly.csc.luna.turboplan.core.entity.User;
import edu.calpoly.csc.luna.turboplan.core.security.encryption.Encryption;

public class SimpleUsername implements UsernameGenerationStrategy {
	@Override
	public String generateUsername(User usr, Session session) {
		String username = usr.getProfile().getFirstName().toLowerCase().charAt(0) + usr.getProfile().getLastName().toLowerCase();

		User existusr = null;
		String curUsername = null;
		int count = 0;
		do {
			curUsername = genUsername(username, count++);
			
			Query query = session.createQuery("from User where userName = ?");
			query.setString(0, Encryption.getInstance().encrypt(curUsername));
			existusr = (User) query.uniqueResult();
		} while (existusr != null);
		
		return curUsername;
	}

	private String genUsername(String base, int count) {
		if (count == 0) {
			return base;
		}
		
		return base + count;
	}

}
