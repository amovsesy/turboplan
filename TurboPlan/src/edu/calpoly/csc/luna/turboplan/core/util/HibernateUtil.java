package edu.calpoly.csc.luna.turboplan.core.util;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import edu.calpoly.csc.luna.turboplan.core.entity.Company;
import edu.calpoly.csc.luna.turboplan.core.entity.Skill;
import edu.calpoly.csc.luna.turboplan.core.entity.Task;
import edu.calpoly.csc.luna.turboplan.core.entity.User;

public class HibernateUtil {
	private static final Logger log = Logger.getLogger(HibernateUtil.class);
	private static final SessionFactory sessionFactory;

	static {
		try {
			// Create the SessionFactory from hibernate.cfg.xml
			sessionFactory = new AnnotationConfiguration().addPackage("edu.calpoly.csc.luna.turboplan.core.entity")
					.addAnnotatedClass(Company.class).addAnnotatedClass(Skill.class).addAnnotatedClass(Task.class)
					.addAnnotatedClass(User.class).configure()
					.buildSessionFactory();

			// Uses hibernate service in JBoss. Currently disabled, maybe
			// configured in the future
			// InitialContext ctx = new InitialContext();
			// sessionFactory = (SessionFactory)
			// ctx.lookup("java:/hibernate/ExampleSessionFactory");
		} catch (IllegalAccessError ex) {
			log.fatal("", ex);
			throw new ExceptionInInitializerError(ex);
		} catch (Throwable ex) {
			// Make sure you log the exception, as it might be swallowed
			log.fatal("Initial SessionFactory creation failed." + ex, ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/**
	 * Converts a hibernate "persistence set" to java.util set
	 * 
	 * @param <T>
	 *            set that contains any type
	 * @param hibernateSet
	 * @return
	 */
	public static <T> Set<T> setConversion(Set<T> hibernateSet) {
		return new HashSet<T>(hibernateSet);

	}
}
