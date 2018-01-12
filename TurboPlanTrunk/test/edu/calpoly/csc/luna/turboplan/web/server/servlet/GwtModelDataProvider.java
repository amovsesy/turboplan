package edu.calpoly.csc.luna.turboplan.web.server.servlet;

import java.util.Date;
import java.util.HashSet;

import org.testng.annotations.DataProvider;

import edu.calpoly.csc.luna.turboplan.core.entity.User;
import edu.calpoly.csc.luna.turboplan.core.entity.AbstractUser.UserStatus;
import edu.calpoly.csc.luna.turboplan.web.client.model.GwtAddress;
import edu.calpoly.csc.luna.turboplan.web.client.model.GwtCompany;
import edu.calpoly.csc.luna.turboplan.web.client.model.GwtSkill;
import edu.calpoly.csc.luna.turboplan.web.client.model.GwtTask;
import edu.calpoly.csc.luna.turboplan.web.client.model.GwtUser;
import edu.calpoly.csc.luna.turboplan.web.client.model.GwtUserProfile;
import edu.calpoly.csc.luna.turboplan.web.client.model.GwtTask.TaskStatus;
import edu.calpoly.csc.luna.turboplan.web.client.model.GwtUser.Permission;

public class GwtModelDataProvider {
	private static final GwtCompany testCompany = new GwtCompany(new Long(1), "GwtModel Company Address", "GwtCompany test name");

	private static final GwtUserProfile testProfile = new GwtUserProfile(null, "Ming", "Liu", new Date());
	
	private static Object [][] userIds = new Object [][] { { new Long(1) } };
   private static Object [][] taskIds = new Object [][] { { new Long(1) } };

	@DataProvider(name = "gwtUsers")
	public static Object[][] gwtUserProvider() {
		Object [][] ret = new Object[][] {
				{ new GwtUser(true, testCompany, "pass", new HashSet<String>(), testProfile, new HashSet<GwtTask>(),
						1L, "GwtUser1") }, };
		long count = 1;
		GwtUserProfile profile = null;
		for (Object[] current : ret)
		{
		   profile = new GwtUserProfile(new Long(count), "Ming", "Liu", new Date());
		   profile.setUserID(count);
		   ((GwtUser)current[0]).setProfile(profile);
         ((GwtUser)current[0]).addPermission(Permission.Preferences);
		   count++;
		}
		return ret;
	}
	
	@DataProvider(name="gwtTasks")
	public static Object[][] gwtTaskProvider() {
	   Object [][] ret = new Object[][] {{blankTask()}};
	   for (Object[] current : ret)
	   {
	      ((GwtTask)current[0]).setCompany(testCompany);
	      ((GwtTask)current[0]).setTaskID(1L);
	   }
	   return ret;
	}

	@DataProvider(name="gwtCompanies")
	public static Object[][] gwtCompanyProvider() {
		return new Object[][] { { testCompany }, };
	}
	
	@DataProvider(name="userIds")
	public static Object[][] userIdProvider()
	{
	   return userIds;
	}
	
	@DataProvider(name="taskIds")
	public static Object[][] taskIdProvider()
	{
	   return taskIds;
	}
	
	@DataProvider(name="companyIds")
	public static Object[][] companyIdProvider()
	{
	   return new Object[][] { { testCompany.getCompanyID() } };
	}

	
	@DataProvider(name="idTaskPair")
	public static Object[][] idTaskPairProvider()
	{
	   Object [][] ret = new Object[userIds[0].length][2];
	   for (int index = 0; index < userIds[0].length; index++)
	   {
	      ret[index][0] = userIds[0][index];
	      ret[index][1] = blankTask();
	   }
	   return ret;
	}
	
   @DataProvider(name="users")
   public static Object [][] userProvider() {
      return new Object [][] { { new User("username", "password", UserStatus.Active) } };
   }
   
   @DataProvider(name="usernameAndPasswordPairs")
   public static Object [][] usernameAndPasswordPairProvider()
   {
      Object [][] users = userProvider();
      Object [][] ret = new Object[users.length][2];
      User user;
      int index = 0;
      for (Object [] userArrayObject : users)
      {
         user = ((User)userArrayObject[0]);
         ret[index][0] = user.getUserName();
         ret[index][1] = user.getPassword();
         index++;
      }
      return ret;
   }
   
   @DataProvider(name="userIDAndPasswordPairs")
   public static Object [][] userIDAndPasswordPairProvider()
   {
      Object [][] users = userProvider();
      Object [][] ret = new Object[users.length][2];
      User user;
      int index = 0;
      for (Object [] userArrayObject : users)
      {
         user = ((User)userArrayObject[0]);
         ret[index][0] = user.getUserID();
         ret[index][1] = user.getPassword();
         index++;
      }
      return ret;
   }
	
	public static GwtTask blankTask()
	{
      Date date = new Date();
      Date due = date;
      Date start = date;
      Date end = date;
	   return new GwtTask(new HashSet<GwtSkill>(), new HashSet<GwtUser>(),
	         TaskStatus.New, "Task", "Complete Task", new GwtAddress(
	               "1 Fake St.", "Ca", "Somewhere", 12345, "SomePlace"),
	               "ContactFirst", "ContactLast", new Long(5555555),
	               new Long(5555556), date, due, start, end, 1.0,
	               GwtTask.TaskPriority.LOW, "Notes");
	}
	
   @DataProvider(name = "userEmails")
   public static Object[][] userEmailProvider() {
      Object [][] users = gwtUserProvider();
      Object [][] ret = new Object[users.length][1];
      for (int index = 0; index < ret[0].length; index++) {
         ret[index][0] = ((User)users[index][0]).getProfile().getEmail();
      }
      return ret;
   }

}
