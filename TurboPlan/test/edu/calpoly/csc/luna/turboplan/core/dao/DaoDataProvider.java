package edu.calpoly.csc.luna.turboplan.core.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.testng.annotations.DataProvider;

import edu.calpoly.csc.luna.turboplan.core.entity.Company;
import edu.calpoly.csc.luna.turboplan.core.entity.Skill;
import edu.calpoly.csc.luna.turboplan.core.entity.Task;
import edu.calpoly.csc.luna.turboplan.core.entity.User;
import edu.calpoly.csc.luna.turboplan.core.entity.AbstractUser.UserStatus;
import edu.calpoly.csc.luna.turboplan.core.entity.Task.TaskPriority;
import edu.calpoly.csc.luna.turboplan.core.entity.Task.TaskStatus;
import edu.calpoly.csc.luna.turboplan.core.entity.embeddable.Address;
import edu.calpoly.csc.luna.turboplan.core.entity.embeddable.UserProfile;

public class DaoDataProvider {
	public static final Random RANDOM_GENERATOR = new Random();

	private static final String DEFAULT_COMPANY_ADDRESS = "Cal Poly, SE";

	public static final Company testCompany = new Company("TestCompany", "LunaSet");
	
	private static final Map<String, String> unameAndPass = new HashMap<String, String>();

	private static final boolean[][] avail = {
			{ false, false, false, false, false, false, false, false, false, false, false, false, false, false, false,
					false, false, false, false, false, false, false, false, false, false, false, false, false, false,
					false, false, false, false, false, false, false, false, false, false, false, false, false, false,
					false, false, false, false, false },
			{ false, false, false, false, false, false, false, false, false, false, false, false, false, false, false,
					true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true,
					true, false, false, false, false, false, false, false, false, false, false, false, false, false,
					false, false, false },
			{ false, false, false, false, false, false, false, false, false, false, false, false, false, false, false,
					true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true,
					true, false, false, false, false, false, false, false, false, false, false, false, false, false,
					false, false, false },
			{ false, false, false, false, false, false, false, false, false, false, false, false, false, false, false,
					true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true,
					true, false, false, false, false, false, false, false, false, false, false, false, false, false,
					false, false, false },
			{ false, false, false, false, false, false, false, false, false, false, false, false, false, false, false,
					true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true,
					true, false, false, false, false, false, false, false, false, false, false, false, false, false,
					false, false, false },
			{ false, false, false, false, false, false, false, false, false, false, false, false, false, false, false,
					true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true,
					true, false, false, false, false, false, false, false, false, false, false, false, false, false,
					false, false, false },
			{ false, false, false, false, false, false, false, false, false, false, false, false, false, false, false,
					false, false, false, false, false, false, false, false, false, false, false, false, false, false,
					false, false, false, false, false, false, false, false, false, false, false, false, false, false,
					false, false, false, false, false } };

	private static final Object[][] users = new Object[][] {
			{ new User(new UserProfile("Ming", "Liu", new Date(), avail, "sample@email.com"), UserStatus.Active,
					testCompany) },
			{ new User(new UserProfile("Ming", "Liu", new Date(), avail, "sample@email.com"), UserStatus.Active,
					testCompany) },
			{ new User(new UserProfile("Ming", "Liu", new Date(), avail, "sample@email.com"), UserStatus.Active,
					testCompany) },
			{ new User(new UserProfile("Ming", "Liu", new Date(), avail, "sample@email.com"), UserStatus.Active,
					testCompany) }, };

	@SuppressWarnings("deprecation")
	private static final Object[][] tasks = new Object[][] {
			{ new Task(TaskStatus.New, "Task 1", "Go to work", new Address("1 fake st", "San Fran", "Cali", 88774),
					"Tracy", "Davies", "1231231", "12321312", new Date(), new Date(109, 4, 1, 8, 30), new Date(109, 4,
							1, 17, 20), new Double(2.2), TaskPriority.LOW, testCompany) },
			{ new Task(TaskStatus.New, "Task 2", "Go to work", new Address("1 fake st", "San Fran", "Cali", 88774),
					"Tracy", "Davies", "1231231", "12321312", new Date(), new Date(109, 4, 1, 8, 30), new Date(109, 4,
							1, 17, 20), new Double(6.2), TaskPriority.LOW, testCompany) },
			{ new Task(TaskStatus.New, "Task 3", "Go to work", new Address("1 fake st", "San Fran", "Cali", 88774),
					"Tracy", "Davies", "1231231", "12321312", new Date(), new Date(109, 4, 1, 8, 30), new Date(109, 4,
							1, 17, 20), new Double(4.25), TaskPriority.LOW, testCompany) },
			{ new Task(TaskStatus.New, "Task 4", "Go to work", new Address("1 fake st", "San Fran", "Cali", 88774),
					"Tracy", "Davies", "1231231", "12321312", new Date(), new Date(109, 4, 1, 8, 30), new Date(109, 4,
							1, 17, 20), new Double(3.65), TaskPriority.LOW, testCompany) },
			{ new Task(TaskStatus.New, "Task 5", "Go to work", new Address("1 fake st", "San Fran", "Cali", 88774),
					"Tracy", "Davies", "1231231", "12321312", new Date(), new Date(109, 4, 1, 8, 30), new Date(109, 4,
							1, 17, 20), new Double(7.0), TaskPriority.LOW, testCompany) }, };

	private static final Object[][] companies = new Object[][] { { testCompany } };

	private static final Object[][] booleans = new Object[][] { { Boolean.TRUE }, { Boolean.FALSE }, };

	private static final Object[][] skills = new Object[][] { { new Skill("Mad skills", testCompany) },
			{ new Skill("Uber micro skills", testCompany) }, { new Skill("Nunchuck skills", testCompany) }, };

	@DataProvider(name = "companyID")
	public static Object[][] companyIdProvider() {
		return new Object[][] { { 1L }, { 2L } };
	}

	@DataProvider(name = "users")
	public static Object[][] userProvider() {
		return users;
	}

	@DataProvider(name = "companies")
	public static Object[][] companyProvider() {
		return companies;
	}

	@DataProvider(name = "booleans")
	public static Object[][] booleanProvider() {
		return booleans;
	}

	@DataProvider(name = "skills")
	public static Object[][] skillProvider() {
		return skills;
	}

	@DataProvider(name = "tasks")
	public static Object[][] taskProvider() {
		return tasks;
	}
	
	@DataProvider(name = "taskIds")
	public static Object[][] taskIdProvider() {
	   Object[][] ret = new Object[1][tasks.length];
	   for (int i = 0; i < tasks.length; i++)
	   {
	      ret[0][i] = ((Task)tasks[i][0]).getTaskID();
	   }
	   return ret;
	}
	
   @DataProvider(name = "userIds")
   public static Object[][] userIdProvider() {
      Object [][] ret = new Object[1][users.length];
      for (int i = 0; i < users.length; i++)
      {
         ret[0][i] = ((User)users[i][0]).getUserID();
      }
      return ret;
   }
	
	
	@DataProvider(name = "usernames")
	public static Object[][] usernameProvider() {
		List<String> list = new ArrayList<String>(unameAndPass.keySet());
      Object[][] ret = new Object[1][list.size()];
		int index = 0;
		for (String current : list)
		{
		   ret[0][index++] = current;
		}
		return ret;
	}
	
	@DataProvider(name = "usernameAndPasswordPairs")
	public static Object[][] usernamePasswordPairProvider() {
		Object[][] ret = new Object[2][unameAndPass.size()];
		List<Map.Entry<String, String>> list = new ArrayList<Map.Entry<String, String>>(unameAndPass.entrySet());
		for(int index = 0; index < ret.length; index++) {
			ret[0][index] = list.get(index).getKey();
			ret[1][index] = list.get(index).getValue();
		}
		return ret;
	}
	
   @DataProvider(name = "userEmails")
	public static Object[][] userEmailProvider() {
      Object [][] ret = new Object[1][users.length];
      for (int index = 0; index < ret[0].length; index++) {
         ret[0][index] = ((User)users[index][0]).getProfile().getEmail();
      }
      return ret;
   }
   
	public static void setUserNamePasswordMap(Map<String, String> usernameToPassword) {
	   unameAndPass.clear();
	   unameAndPass.putAll(usernameToPassword);
	}
	public static void addUsername(String uname, String password) {
		unameAndPass.put(uname, password);
	}
}
