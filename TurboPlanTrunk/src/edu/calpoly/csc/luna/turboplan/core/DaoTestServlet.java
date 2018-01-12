package edu.calpoly.csc.luna.turboplan.core;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.log4j.WriterAppender;

import edu.calpoly.csc.luna.turboplan.core.dao.CompanyDao;
import edu.calpoly.csc.luna.turboplan.core.dao.TaskDao;
import edu.calpoly.csc.luna.turboplan.core.entity.AbstractUser;
import edu.calpoly.csc.luna.turboplan.core.entity.Company;
import edu.calpoly.csc.luna.turboplan.core.entity.Skill;
import edu.calpoly.csc.luna.turboplan.core.entity.Task;
import edu.calpoly.csc.luna.turboplan.core.entity.User;
import edu.calpoly.csc.luna.turboplan.core.entity.AbstractUser.Permission;
import edu.calpoly.csc.luna.turboplan.core.entity.embeddable.Address;
import edu.calpoly.csc.luna.turboplan.core.entity.embeddable.UserProfile;
import edu.calpoly.csc.luna.turboplan.core.entity.embeddable.UserProfile.EmployeeGender;
import edu.calpoly.csc.luna.turboplan.core.entity.part.Availability;
import edu.calpoly.csc.luna.turboplan.core.scheduler.Scheduler;
import edu.calpoly.csc.luna.turboplan.core.scheduler.SimpleSchedule;
import edu.calpoly.csc.luna.turboplan.core.security.encryption.Encryption;

public class DaoTestServlet extends HttpServlet {
	private static final int TASK_START_DAY = 9;
	private static final int TASK_END_DAY = 13;
	private static final int NUMBER_OF_TASK_TO_GENERATE = 30;
	
	private static final String TASK_PARAMETER_VALUE = "task";
	private static final String COMPANY_PARAMETER_VALUE = "company";
	private static final String SCHEDULE_PARAMETER = "schedule";
	private static final String POPULATE_TABLE_PARAMETER = "populateTable";
	private static final String PRINT_SCHEDULE_PARAMETER = "printSchedule";

	private static final Map<String, String[]> ALL_TABLES_AND_SCHEDULE;
	private static final Map<String, String[]> ALL_TABLES_NO_SCHEDULE;
	private static final Map<String, String[]> COMPANY_TABLE_ONLY;

	static {
		ALL_TABLES_AND_SCHEDULE = new HashMap<String, String[]>();
		ALL_TABLES_AND_SCHEDULE.put(POPULATE_TABLE_PARAMETER, new String[] { COMPANY_PARAMETER_VALUE,
				TASK_PARAMETER_VALUE });
		ALL_TABLES_AND_SCHEDULE.put(SCHEDULE_PARAMETER, new String[] { "1" });

		COMPANY_TABLE_ONLY = new HashMap<String, String[]>();
		COMPANY_TABLE_ONLY.put(POPULATE_TABLE_PARAMETER, new String[] { COMPANY_PARAMETER_VALUE });

		ALL_TABLES_NO_SCHEDULE = new HashMap<String, String[]>();
		ALL_TABLES_NO_SCHEDULE.put(POPULATE_TABLE_PARAMETER, new String[] { COMPANY_PARAMETER_VALUE,
				TASK_PARAMETER_VALUE });
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		PrintWriter writer = resp.getWriter();
		writer.println("<html><head><title>Event Manager</title></head><body>");

		doStuff(req, writer, null);

		writer.println("</body></html>");
		writer.flush();
		writer.close();
	}

	/**
	 * DaoTestServlet now accept parameters. Here's an example of usage.
	 * 
	 * http://lunaset.csc.calpoly.edu/TurboPlan/daotest?populateTable=company&
	 * populateTable=task&schedule=1
	 * 
	 * will populate the tables company and task and schedule tasks for company
	 * with id 1
	 * 
	 * @param req
	 * @param writer
	 */
	@SuppressWarnings("unchecked")
	private void doStuff(HttpServletRequest req, PrintWriter writer, Map<String, String[]> map) {
		if (map == null) {
			map = req.getParameterMap();
		}
		populateTable(writer, map.get(POPULATE_TABLE_PARAMETER));
		//scheduleTask(writer, map.get(SCHEDULE_PARAMETER));
		//printSchedule(writer, map.get(PRINT_SCHEDULE_PARAMETER));
	}

	private void printSchedule(PrintWriter writer, String[] strings) {
		if (strings == null) {
			return;
		}

		writer.println(buildTaskAssignmentTree());
	}

	private void populateTable(PrintWriter writer, String[] tables) {
		if (tables == null) {
			return;
		}

		Set<String> tableSet = new HashSet<String>(Arrays.asList(tables));

		if (tableSet.contains(COMPANY_PARAMETER_VALUE)) {
			Object[][] obj = populateCompanyTable();
			println(writer, "Company table populated");
			println(writer, "User table populated");
			println(writer, "Skill table populated");

			if (tableSet.contains(TASK_PARAMETER_VALUE)) {
				for (Object[] o : obj) {
					populateTaskTable((Set<Skill>) o[0], (Company) o[1]);
				}
				println(writer, "Task table populated");
			}
		}
	}

	private void scheduleTask(PrintWriter writer, String[] companyIds) {
		if (companyIds == null) {
			return;
		}
		for (String str : companyIds) {
//			Scheduler.newInstance(Long.valueOf(str), new SimpleSchedule()).run();
			println(writer, "Generated schedule for company [id=" + str + "]");
		}
	}

	public Object[][] populateCompanyTable() {
		Object[] comp1 = addCompany("LunaSet", "Cal Poly, SE");
		Object[] comp2 = addCompany2("EmptySet", "Cal Poly, SE");

		// addCompany("Penguin Mofia", "Cal Poly, SE");
		// addCompany("In_It!", "Cal Poly, SE");

		return new Object[][] { comp1, comp2 };
	}

	private Object[] addCompany(String name, String address) {
		Company comp = new Company();

		Set<Skill> skills = buildSkillList(comp);
		Set<AbstractUser> users = buildUserSet();

		comp.setName(name);
		comp.setAddress(address);
		comp.setUsers(users);
		comp.setSkills(skills);

		CompanyDao.getInstance().addCompany(comp);

		return new Object[] { skills, comp };
	}

	private Set<AbstractUser> buildUserSet() {
		Set<AbstractUser> users = new HashSet<AbstractUser>();

		users.add(buildUser("mliu", "LunaSet", buildUserProfile("Ming", "Liu", "liu@calpoly.edu", new Address(
				"123 Main St", "SLO", "California", 12345), "male"), false));
		users.add(buildUser("tdavies", "LunaSet", buildUserProfile("Tracy", "Davies", "tldavies87@gmail.com",
				new Address("123 Main St", "SLO", "California", 12345), "female"), false));
		users.add(buildUser("slong", "LunaSet", buildUserProfile("Stephanie", "Long", "long@calpoly.edu", new Address(
				"123 Main St", "SLO", "California", 12345), "female"), false));
		users.add(buildUser("djanzen", "LunaSet", buildUserProfile("David", "Janzen", "fake@domain.com", new Address(
				"123 Main St", "SLO", "California", 12345), "male"), true));

		return users;
	}

	private Object[] addCompany2(String name, String address) {
		Company comp = new Company();

		Set<AbstractUser> users = buildUserSet2();
		Set<Skill> skills = buildSkillList(comp);

		comp.setName(name);
		comp.setAddress(address);
		comp.setUsers(users);
		comp.setSkills(skills);

		CompanyDao.getInstance().addCompany(comp);

		return new Object[] { skills, comp };
	}

	private Set<AbstractUser> buildUserSet2() {
		Set<AbstractUser> users = new HashSet<AbstractUser>();

		users.add(buildUser("bbarbee", "LunaSet", buildUserProfile("Bradley", "Barbee", "bradleybarbee@gmail.com",
				new Address("123 Main St", "SLO", "California", 12345), "male"), false));
		users.add(buildUser("pdeleon", "LunaSet", buildUserProfile("Paul", "De Leon", "pdeleon85@hotmail.com",
				new Address("123 Main St", "SLO ", "California", 12345), "male"), false));
		users.add(buildUser("amovsesyan", "LunaSet", buildUserProfile("Aleksandr", "Movsesyan", "alex122287@gmail.com",
				new Address("123 Main St", "SLO", "California", 12345), "male"), false));
		users.add(buildUser("krazina", "LunaSet", buildUserProfile("Kate", "Razina", "fake@domain.com", new Address(
				"123 Main St", "SLO", "California", 12345), "female"), true));

		return users;
	}

	private User buildUser(String username, String password, UserProfile profile, boolean manager) {
		User usr = new User();

		EnumSet<Permission> perm = EnumSet.noneOf(Permission.class);
		if (manager) {
			perm.add(Permission.Preferences);
			perm.add(Permission.ManageEmployees);
			perm.add(Permission.ManageSchedule);
			perm.add(Permission.ManageTasks);
			perm.add(Permission.MySchedule);
		} else {
			perm.add(Permission.Preferences);
			perm.add(Permission.MySchedule);
		}

		usr.setUserName(Encryption.getInstance().encrypt(username));
		usr.setPassword(Encryption.getInstance().encrypt(password));
		usr.setPermission(perm);
		usr.setActive(AbstractUser.UserStatus.Active);
		usr.setProfile(profile);

		return usr;
	}

	private UserProfile buildUserProfile(String firstName, String lastName, String email, Address add, String gender) {
		UserProfile usrProfile = new UserProfile();
		String number = "111111231231231";

		usrProfile.setFirstName(firstName);
		usrProfile.setLastName(lastName);
		usrProfile.setDateOfBirth(new Date());
		usrProfile.setEmail(email);
		usrProfile.setMiddleInitial('M');
		usrProfile.setAddress(add);
		usrProfile.setHomePhoneNumber(number);
		usrProfile.setMobilePhoneNumber(number);
		usrProfile.setOtherPhoneNumber(number);
		usrProfile.setOtherPhoneNumber2(number);
		usrProfile.setOtherPhoneNumber3(number);

		if (gender.equals("male")) {
			usrProfile.setGender(EmployeeGender.Male);
		} else {
			usrProfile.setGender(EmployeeGender.Female);
		}

		boolean[][] tempavil = new boolean[7][48];

		// Populate Availability with nothing is available
		for (int i = 1; i < 6; i++) {
			for (int j = 15; j < 34; j++) {
				tempavil[i][j] = true;
			}
		}

		usrProfile.setAvailability(new Availability(tempavil));

		// usrProfile.setHomePhoneNumber(0L);
		// usrProfile.setMobilePhoneNumber(0L);
		// usrProfile.setOtherPhoneNumber(0L);
		// usrProfile.setOtherPhoneNumber2(0L);
		// usrProfile.setOtherPhoneNumber3(0L);
		//		
		// Collection<TimeInterval> avail = new ArrayList<TimeInterval>();
		// avail.add(new TimeInterval(new Date(), new Date()));
		// avail.add(new TimeInterval(new Date(), new Date()));
		//		
		// usrProfile.setAvailability(avail);

		return usrProfile;
	}

	private static final DaoTestServlet singleton = new DaoTestServlet();

	@SuppressWarnings("deprecation")
	private void populateTaskTable(Set<Skill> skills, Company company) {
		for (int i = 0; i < NUMBER_OF_TASK_TO_GENERATE; i++) {
			int randomDay = ((int) (Math.random() * 100)) % (TASK_END_DAY - TASK_START_DAY + 1) + TASK_START_DAY;
			int randomStartTime = ((int) (Math.random() * 100)) % 23;
			int randomEndTime = 0;
			double randomEstimate = ((double) (((int) (Math.random() * 100)) % 50)) / 10D;

			while (randomEndTime < randomStartTime) {
				randomEndTime = ((int) (Math.random() * 100)) % 23;
			}

			TaskDao.getInstance().addTask(EntityBuilder.buildTask(
					skills,
					"Task " + System.currentTimeMillis() / Math.abs(((long) Math.random()) + 1),
					new Date(109, 2, randomDay, randomStartTime, 0),
					new Date(109, 2, randomDay, randomEndTime, 0),
					randomEstimate,
					company
			));
		}
	}

	private Set<Skill> buildSkillList(Company company) {
		Set<Skill> skills = new HashSet<Skill>();

		skills.add(new Skill("Uber micro skills", company));
		skills.add(new Skill("Pro skills", company));
		skills.add(new Skill("Mad skills", company));
		skills.add(new Skill("Nunchuck skills", company));

		return skills;
	}

	private void println(PrintWriter writer, String line) {
		if (writer != null) {
			writer.println(line + "<br/>");
		} else {
			System.out.println(line);
		}
	}

	private String buildTaskAssignmentTree() {
		StringBuilder builder = new StringBuilder();

		List<Company> list = CompanyDao.getInstance().getAllCompany(true, true, true);
		for (Company company : list) {
			builder.append(company.getName()).append("</br>");
			for (Task task : company.getTasks()) {
				builder.append(nbsp(4) + task.getTitle()).append("</br>");
				for (User usr : task.getUsers()) {
					builder.append(nbsp(8) + usr.getProfile().getFirstName()).append("</br>");
				}
			}
		}

		return builder.toString();
	}

	private String nbsp(int count) {
		StringBuilder builder = new StringBuilder();

		for (int i = 0; i < count; i++) {
			builder.append("&nbsp;");
		}

		return builder.toString();
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		 singleton.doStuff(null, null, ALL_TABLES_AND_SCHEDULE);
//		System.out.println();
//		for (int i = 0; i < 30; i++) {
//			System.out.println(((double) (((int) (Math.random() * 100)) % 50)) / 10D);
//		}
		// Logger.getLogger(Company.class).addAppender(new Append)
		// TaskDao.getInstance().getTaskForUserBetweenDates(id, start, end)

		// for (Task task : TaskDao.getInstance().getf)

		// List<User> list = UserDao.getInstance().getAllUsers();
		// for (User usr : list) {
		// System.out.println(StringUtil.getHexString(usr.getUserName()));
		// }
	}
}
