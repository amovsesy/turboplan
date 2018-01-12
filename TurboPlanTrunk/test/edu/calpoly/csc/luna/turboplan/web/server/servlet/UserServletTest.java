package edu.calpoly.csc.luna.turboplan.web.server.servlet;

import java.util.Set;

import org.testng.Assert;
import org.testng.annotations.Test;

import edu.calpoly.csc.luna.turboplan.core.dao.DaoDataProvider;
import edu.calpoly.csc.luna.turboplan.core.entity.User;
import edu.calpoly.csc.luna.turboplan.core.util.GwtUtil;
import edu.calpoly.csc.luna.turboplan.web.client.model.GwtTask;
import edu.calpoly.csc.luna.turboplan.web.client.model.GwtUser;

@Test(groups = { "TurboPlan.all", "TurboPlan.web", "TurboPlan.gwtservlet", "TurboPlan.gwtservlet.user" }/*, dependsOnGroups={"TurboPlan.dao"}*/)
public class UserServletTest {
	private UserServlet servlet = new UserServlet();
	
	
	@Test (dataProviderClass = GwtModelDataProvider.class, dataProvider = "usernameAndPasswordPairs")
	public void authenticate(String username, String password) {
		Assert.assertNotNull(servlet.authenticate(username, password));
		Assert.assertNull(servlet.authenticate(username, password + "Q"));
	}
	
	@Test (dataProviderClass = GwtModelDataProvider.class, dataProvider = "users")
	public void getTasksForUser(User usr) {
		GwtUser gwtusr = GwtUtil.hib2gwt(usr);
		Set<GwtTask> list = servlet.getTasksForUser(usr.getUserID());
		for (GwtTask task : list) {
			Assert.assertTrue(task.getUsers().contains(gwtusr));
		}
	}
	
   @Test (dataProviderClass = GwtModelDataProvider.class, dataProvider = "gwtUsers")
	public void updateUser(GwtUser usr) {
	   try
	   {
	      usr.setActive(!usr.isActive());
	      servlet.updateUser(usr);
	   }
	   catch (Exception exception)
	   {
	      Assert.fail("failed to update user" + exception.getMessage());
	   }
	}
	
   @Test (dataProviderClass = GwtModelDataProvider.class, dataProvider = "userIDAndPasswordPairs")
	public void updateUserPassword(Long id, String password) {
	   try
	   {
	      servlet.updateUserPassword(id, password + "a");
	      servlet.updateUserPassword(id, password);
	   }
	   catch (Exception exception)
	   {
	      Assert.fail("Unable to update password: " + exception.getMessage());
	   }
	}

}
