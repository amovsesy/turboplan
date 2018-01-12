package edu.calpoly.csc.luna.turboplan.axis2ws.mobile;

import org.testng.Assert;
import org.testng.annotations.Test;

import edu.calpoly.csc.luna.turboplan.axis2ws.mobile.model.MobileUser;
import edu.calpoly.csc.luna.turboplan.core.dao.DaoDataProvider;

@Test (groups={ "TurboPlan.all", "TurboPlan.mobile", "TurboPlan.mobile.webservice" })
public class MobileServiceTest {
	private MobileService service = new MobileService();
	
	@Test (dataProviderClass=DaoDataProvider.class, dataProvider="usernameAndPasswordPairs", dependsOnGroups={"TurboPlan.dao"})
	public void authenticate(String username, String password) {
		MobileUser usr = service.authenticate(username, password);
		
		Assert.assertNotNull(usr);
	}
	
	public void getTasksForUserOfDate() {
		Assert.fail("Test not implemented");
	}
	
	public void getTasksForUserOfDates() {
		Assert.fail("Test not implemented");
	}
	
	public void getAllTasksForUser() {
		Assert.fail("Test not implemented");
	}
	
	public void setTaskStatus() {
		Assert.fail("Test not implemented");
	}

	public void setTaskNotes() {
		Assert.fail("Test not implemented");
	}

}
