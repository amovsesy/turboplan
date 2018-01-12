package edu.calpoly.csc.luna.turboplan.core.dao.generator;

import org.hibernate.Session;

import edu.calpoly.csc.luna.turboplan.core.entity.User;

public interface UsernameGenerationStrategy {
	public String generateUsername(User usr, Session session);
}
