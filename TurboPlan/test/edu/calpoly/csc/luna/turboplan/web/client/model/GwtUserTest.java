package edu.calpoly.csc.luna.turboplan.web.client.model;


import java.util.Date;


import org.testng.Assert;
import org.testng.annotations.Test;

import edu.calpoly.csc.luna.turboplan.core.entity.AbstractUser.UserStatus;
import edu.calpoly.csc.luna.turboplan.web.client.model.GwtUserProfile.EmployeeGender;


/**
 * Tests a GWT User
 * @author Stephanie Long
 *
 */
@Test(groups = { "TurboPlan.all", "TurboPlan.web" })
public class GwtUserTest {
	@SuppressWarnings("deprecation")
	@Test
	public void read() {
		
		GwtUser testUser = new GwtUser("TestDummy", "testPassword");
		GwtUserProfile testProf = new GwtUserProfile(1L, "Test", "Dummy", new Date("12/23/2008"));
		GwtAddress testAdd = new GwtAddress("123 Test St.", "TS" , "Testville", 99999);
		testProf.setEmail("test1@test.com");
		testProf.setFirstName("Johnny");
		testProf.setGender(EmployeeGender.Male);
		testProf.setAddress(testAdd);
		testProf.setUserID(1L);
		testUser.setProfile(testProf);
		
		GwtCompany comp = new GwtCompany(1L, "testCpmp", "123 Address");
		testUser.setCompany(comp);
		
		
		Assert.assertEquals(testUser.getPassword(), "testPassword");
		Assert.assertEquals(testUser.getCompany().getName(), "testCpmp");
		Assert.assertEquals(testUser.getProfile().getFirstName(), "Johnny");
		Assert.assertEquals(testUser.getProfile().getGender(), EmployeeGender.Male);
		Assert.assertEquals(testUser.getProfile().getEmail(), "test1@test.com");
		Assert.assertEquals(testUser.getProfile().getAddress().getState(), "TS");
	}
}