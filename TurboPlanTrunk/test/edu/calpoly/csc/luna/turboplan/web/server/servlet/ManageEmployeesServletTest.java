package edu.calpoly.csc.luna.turboplan.web.server.servlet;

import org.testng.Assert;
import org.testng.annotations.Test;

import edu.calpoly.csc.luna.turboplan.core.dao.DaoDataProvider;
import edu.calpoly.csc.luna.turboplan.web.client.model.GwtUser;

@Test(groups = { "TurboPlan.all", "TurboPlan.web", "TurboPlan.gwtservlet", "TurboPlan.gwtservlet.manageemployees" })
public class ManageEmployeesServletTest {
	private ManageEmployeesServlet servlet = new ManageEmployeesServlet();

	@Test (dataProviderClass=GwtModelDataProvider.class, dataProvider="gwtUsers")
	public void addUser(GwtUser usr) {
	   try
	   {
	      servlet.addUser(usr);
	   }
	   catch (Exception exception)
	   {
	      System.err.println("Unable to add user in ManageEmployeesServlet");
         System.err.println("Exception message: " + exception.getMessage());
	      Assert.fail("Unable to add user in ManageEmployeesServlet");
	   }
	}

   @Test (dataProviderClass=GwtModelDataProvider.class, dataProvider="gwtUsers",
         dependsOnMethods={"addUser"})
	public void deleteUser(GwtUser usr) {
      try
      {
         servlet.deleteUser(usr);
      }
      catch (Exception exception)
      {
         System.err.println("Unable to delete user in ManageEmployeesServlet");
         System.err.println("Exception message: " + exception.getMessage());
         Assert.fail("Unable to delete user in ManageEmployeesServlet");

      }
	}

	@Test (dataProviderClass=GwtModelDataProvider.class, dataProvider="gwtUsers",
	      dependsOnMethods={"addUser"})
	public void getUsers(GwtUser usr) {
      try
      {
         servlet.addUser(usr);
      }
      catch (Exception exception)
      {
         System.err.println("Unable to get user in ManageEmployeesServlet");
         System.err.println("Exception message: " + exception.getMessage());
         Assert.fail("Unable to get user in ManageEmployeesServlet");
      }
		servlet.getUsers(usr);
	}

	@Test (dataProviderClass=DaoDataProvider.class, dataProvider="userEmails",
	      dependsOnMethods={"addUser"})
	public void getUsrByEmail(String email) {
      try
      {
         GwtUser result = servlet.getUsrByEmail(email);
         Assert.assertNotNull(result);
         // TODO add a check for the user's email compared to the entry email.
      }
      catch (Exception exception)
      {
         System.err.println("Unable to get user in ManageEmployeesServlet using"
               + " email address: \"" + email + "\"");
         System.err.println("Exception message: " + exception.getMessage());
         Assert.fail("Unable to get user in ManageEmployeesServlet using"
               + " email address: \"" + email + "\"");
      }
	}


   @Test (dataProviderClass = DaoDataProvider.class, dataProvider = "taskIds",
         dependsOnMethods={"addUser"})
	public void getUsrByID(Long Id) {
      try
      {
         GwtUser result = servlet.getUsrByID(Id);
         Assert.assertNotNull(result);
         Assert.assertEquals(result.getUserID(), Id);
      }
      catch (Exception exception)
      {
         System.err.println("Unable to get user in ManageEmployeesServlet using"
               + " ID number: " + Id);
         System.err.println("Exception message: " + exception.getMessage());
         Assert.fail("Unable to get user in ManageEmployeesServlet using"
               + " ID number: " + Id);
      }
	}

	@Test (dataProviderClass = DaoDataProvider.class, dataProvider = "usernames",
	      dependsOnMethods={"addUser"})
	public void getUsrByUsername(String username) {
      try
      {
         GwtUser result = servlet.getUsrByUsername(username);
         Assert.assertNotNull(result);
         Assert.assertEquals(result.getUsername(), username);
      }
      catch (Exception exception)
      {
         System.err.println("Unable to get user in ManageEmployeesServlet using"
               + " ID number: \"" + username + "\"");
         System.err.println("Exception message: " + exception.getMessage());
         Assert.fail("Unable to get user in ManageEmployeesServlet using"
               + " username: \"" + username + "\"");
      }
	}

	
   @Test (dataProviderClass=GwtModelDataProvider.class, dataProvider="gwtUsers",
         dependsOnMethods={"addUser", "getUsrByUsername"})
	public void updateUser(GwtUser usr) {
      boolean activity = usr.isActive();
      try
      {
         usr.setActive(!activity);
         servlet.updateUser(usr);
         GwtUser result = servlet.getUsrByUsername(usr.getUsername());
         Assert.assertNotNull(result);
         Assert.assertEquals(result.getUserID(), usr.getUsername());
         Assert.assertEquals(!result.isActive(), activity);
      }
      catch (Exception exception)
      {
         System.err.println("Unable to update user with new activity value: "
               + !activity);
         System.err.println("Exception message: " + exception.getMessage());
         Assert.fail("Unable to update user with new activity value: "
               + !activity);
      }
	}

}