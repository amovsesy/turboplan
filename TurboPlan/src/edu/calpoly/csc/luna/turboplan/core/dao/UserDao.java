package edu.calpoly.csc.luna.turboplan.core.dao;

import java.util.Date;
import java.util.EnumSet;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import edu.calpoly.csc.luna.turboplan.axis2ws.mobile.MobileService;
import edu.calpoly.csc.luna.turboplan.axis2ws.mobile.model.MobileTask;
import edu.calpoly.csc.luna.turboplan.core.dao.generator.SimpleUsername;
import edu.calpoly.csc.luna.turboplan.core.dao.generator.UsernameGenerator;
import edu.calpoly.csc.luna.turboplan.core.entity.AbstractUser;
import edu.calpoly.csc.luna.turboplan.core.entity.Company;
import edu.calpoly.csc.luna.turboplan.core.entity.Task;
import edu.calpoly.csc.luna.turboplan.core.entity.User;
import edu.calpoly.csc.luna.turboplan.core.security.encryption.Encryption;

/**
 * UserDao object to pass to the database
 * 
 * @author Ming Liu
 * @author Stephanie Long
 * @author Paul DeLeon
 */
public class UserDao extends BaseDao<User> {
	public static final String DEFAULT_PASSWORD = "LunaSet";

	/**
	 * Logger
	 */
	private static final Logger log = Logger.getLogger(UserDao.class);

	/**
	 * UserDao instance
	 */
	private static UserDao singleton = new UserDao();

	/**
	 * Get the instance of the UserDao
	 * 
	 * @return The instance of the UserDao
	 */
	public static UserDao getInstance() {
		return singleton;
	}

	/**
	 * Add a user to the database. Auto generate username if it is null or
	 * empty.
	 * 
	 * @param usr
	 *            The user to add
	 * @return the username of this user, possibly a generated one.
	 */
	public String addUser(User usr) {
		// fail safe check
		if (usr.getUserName() == null || usr.getUserName().length() == 0) {
			return addUser(usr, true);
		}
		if (usr.getPassword() == null || usr.getPassword().length() == 0) {
			usr.setPassword(Encryption.getInstance().encrypt(DEFAULT_PASSWORD));
		}

		super.save(usr);
		return usr.getUserName();
	}

	public String addUser(User usr, boolean genUsername) {
		if (!genUsername) {
			addUser(usr);
			return usr.getUserName();
		}

		Session session = newSessionAndTransaction();
		String username = new UsernameGenerator(new SimpleUsername(), usr, session).run();

		usr.setUserName(Encryption.getInstance().encrypt(username));
		usr.setPassword(Encryption.getInstance().encrypt(DEFAULT_PASSWORD));

		session.save(usr);

		session.getTransaction().commit();

		return username;
	}

	/**
	 * Update a user to the database
	 * 
	 * @param usr
	 *            The user to update
	 */
	public void updateUser(User usr) {
		super.update(usr);
	}

	/**
	 * update user
	 * 
	 * @param usr
	 * @param username
	 *            NOT update username
	 * @param password
	 *            NOT update password
	 */
	public void updateUser(User usr, boolean username, boolean password) {
		Session session = newSessionAndTransaction();

		User original = (User) session.createQuery("from User where userID = ?").setLong(0, usr.getUserID())
				.uniqueResult();

		if (username)
			usr.setUserName(original.getUserName());
		if (password)
			usr.setPassword(original.getPassword());

		session.clear();
		session.update(usr);

		session.getTransaction().commit();
	}

	/**
	 * Delete the specified user
	 * 
	 * @param usr
	 *            The user to delete
	 * @return True if the user was successfully deleted, else false
	 */
	public void deleteUser(User usr) {
		super.delete(usr);
	}

	/**
	 * Get all the users from the database associated with a company
	 * 
	 * @return The users from the database associated with a company
	 */
	public List<User> getAllUsers() {
		return getAll(User.class);
	}

	/**
	 * Gets the users associated with the same company as a specified user
	 * 
	 * @param id
	 *            The userID to get the co-workers of
	 * @return The users belonging to the same company as the id specified
	 */
	public List<User> getAllUsersInSameCompanyAsThisUserById(Long id) {
		Session session = newSessionAndTransaction();

		String query = "from User usr where usr.company = (select company from User where userID = ?) order by activeStatus";
		@SuppressWarnings("unchecked")
		List<User> ret = session.createQuery(query).setLong(0, id).list();

		session.getTransaction().commit();

		return ret;
	}

	/**
	 * Gets all the users associated with a specific company
	 * 
	 * @param company
	 *            The company to get the associated users of
	 * @return The users belonging to the specified company
	 */
	public List<User> getAllUserOfCompany(Company company) {
		Session session = newSessionAndTransaction();

		@SuppressWarnings("unchecked")
		List<User> ret = session.createQuery("from User where company = ?").setLong(0, company.getCompanyID())
				.setFetchSize(10).list();

		session.getTransaction().commit();

		return ret;
	}

	/**
	 * Gets the user by the specified user name
	 * 
	 * @param username
	 *            The user name to get a user by
	 * @return The desired user
	 */
	public User getUserByClearTextUsername(String username) {
		log.trace("Getting user by username = " + username);
		Session session = newSessionAndTransaction();
		username = Encryption.getInstance().encrypt(username);

		String query = "from User where userName = ?";

		User usr = (User) session.createQuery(query).setString(0, username).uniqueResult();

		session.getTransaction().commit();

		return usr;
	}
	
	public boolean correctPassForUserId(Long id, String pass) {
		Session session = newSessionAndTransaction();
		pass = Encryption.getInstance().encrypt(pass);
		
		String query = "from User u where u.userID = ?";
		
		User usr = (User) session.createQuery(query).setLong(0, id).uniqueResult();
		
		session.getTransaction().commit();
		
		return usr.getPassword().equals(pass);
	}

	/**
	 * Gets the user by the specified email
	 * 
	 * @param email
	 *            The email to get a user by
	 * @return The user associated with the specified email
	 */
	public User getUserEmail(String email) {
		for (User usr : getAllUsers()) {
			if (usr.getProfile().getEmail().equals(email)) {
				return usr;
			}
		}

		return null;
	}

	/**
	 * Gets the user by the specified ID
	 * 
	 * @param id
	 *            The userID to get a user by
	 * @return The user associated with the specified id
	 */
	public User getUserById(Long id) {
		return super.getEntityById(User.class, id);
	}

	// public User getUserByIdWithTask(Long id, boolean fetchTask) {
	//		
	// }

	/**
	 * Gets the user's permissions by the specified id
	 * 
	 * @param id
	 *            The userID to get a user by
	 * @return The permissions associated with the user who is specified by the
	 *         given id
	 */
	public EnumSet<AbstractUser.Permission> getUserPerm(Long id) {
		Session session = newSessionAndTransaction();

		@SuppressWarnings("unchecked")
		EnumSet<AbstractUser.Permission> obj = (EnumSet<AbstractUser.Permission>) session.createQuery(
				"select permission from User where userID = ?").setLong(0, id).uniqueResult();

		return obj;
	}

	/**
	 * Gets the user's tasks by the specified userID
	 * 
	 * @param userID
	 *            The userID to get a user's associated tasks by
	 * @return The assigned tasks of the user associated witht the given userId
	 */
	public List<Task> getTasksByUserId(Long userID) {
		return TaskDao.getInstance().getAllTasksBelongToUserById(userID);
	}

	public void updateClearTextPasswordForUserById(Long id, String clearText) {
		// TODO dao method implementation
		Session session = newSessionAndTransaction();

		User usr = (User) session.createQuery("from User where userID = ?").setLong(0, id).uniqueResult();

		usr.setPassword(Encryption.getInstance().encrypt(clearText));

		session.update(usr);

		session.getTransaction().commit();
	}

	@Override
	public int getTableRowCount() {
		return super.countRowOfTable(User.class);
	}

	@Override
	public String printTable() {
		return super.printTable(User.class).toString();
	}

	public static void main(String[] args) {
		// List<Task> list = UserDao.getInstance().getTasksByUserId(1L);
		// System.out.println(list.size());

		MobileTask[] results = new MobileService().getTasksForUserOfDate(1L, new Date(109, 2, 19).getTime());
		System.out.println(results.length);

		// Date date = new Date(109, 2, 19);
		// System.out.println(date);
		// System.out.println(date.getTime());
	}
}
