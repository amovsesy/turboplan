package edu.calpoly.csc.luna.turboplan.core.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.allen_sauer.gwt.log.client.Log;

import edu.calpoly.csc.luna.turboplan.core.dao.generator.SimpleUsername;
import edu.calpoly.csc.luna.turboplan.core.dao.generator.UsernameGenerator;
import edu.calpoly.csc.luna.turboplan.core.entity.AbstractUser;
import edu.calpoly.csc.luna.turboplan.core.entity.Company;
import edu.calpoly.csc.luna.turboplan.core.entity.User;
import edu.calpoly.csc.luna.turboplan.core.security.encryption.Encryption;

public class CompanyDao extends BaseDao<Company> {
	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(CompanyDao.class);
	
	private static CompanyDao singleton = new CompanyDao();
	public static CompanyDao getInstance() {
		return singleton;
	}
	
	/**
	 * Add a new company to the table
	 * 
	 * @param nCompany The new company to be added
	 */
	public String addCompany(Company nCompany) {
		String pass = Encryption.getInstance().encrypt(UserDao.DEFAULT_PASSWORD);
		String username = "";
		Session session = newSessionAndTransaction();
		
		Log.info("Adding company with " + nCompany.getName() + " " + nCompany.getAddress());
		
		for(AbstractUser u : nCompany.getUsers())
		{
			username = new UsernameGenerator(new SimpleUsername(), (User)u, session).run();
			u.setPassword(pass);
			u.setUserName(Encryption.getInstance().encrypt(username));
			Log.info("Username of " + username);
		}
	
		session.save(nCompany);

		session.getTransaction().commit();
		return username;
	}
	
	public void updateCompany(Company company) {
		super.update(company);
	}
	
	public void deleteCompany(Company company) {
		super.delete(company);
	}
	
	public List<Company> getAllCompany() {
		return getAll(Company.class);
	}
	
	public List<Company> getAllCompany(boolean fetchSkills, boolean fetchTasks, boolean fetchUsers) {
		Session session = newSessionAndTransaction();
		
		StringBuilder query = new StringBuilder("from Company c");
		if (fetchSkills) {
			query.append(" join fetch c.skills");
		}
		if (fetchTasks) {
			query.append(" join fetch c.tasks");
		}
		if (fetchUsers) {
			query.append(" join fetch c.users");
		}
		
		@SuppressWarnings("unchecked")
		List<Company> ret = session.createQuery(query.toString()).list();
		
		session.getTransaction().commit();
		
		return ret;
	}
	
	public int getTableRowCount() {
		return super.countRowOfTable(Company.class);
	}

	@Override
	public String printTable() {
		return super.printTable(Company.class).toString();
	}

}
