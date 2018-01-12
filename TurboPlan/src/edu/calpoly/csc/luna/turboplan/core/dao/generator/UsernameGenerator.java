package edu.calpoly.csc.luna.turboplan.core.dao.generator;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import edu.calpoly.csc.luna.turboplan.core.entity.User;

public class UsernameGenerator {
	private static final Logger log = Logger.getLogger(UsernameGenerator.class);

	private UsernameGenerationStrategy strategy;
	private Session session;
	private User usr;

	public UsernameGenerator(UsernameGenerationStrategy strategy, User usr, Session session) {
		this.strategy = strategy;
		this.usr = usr;
		this.session = session;
	}
	
	public String run() {
		String ret = strategy.generateUsername(usr, session);
		
		log.trace("Generated username: " + ret);

		return ret;
	}
}
