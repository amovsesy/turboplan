package edu.calpoly.csc.luna.turboplan.web.client.model;


import java.util.Date;

import org.testng.Assert;
import org.testng.annotations.Test;



import edu.calpoly.csc.luna.turboplan.core.entity.AbstractUser.UserStatus;
import edu.calpoly.csc.luna.turboplan.core.entity.Task.TaskStatus;
import edu.calpoly.csc.luna.turboplan.web.client.model.GwtAddress;
import edu.calpoly.csc.luna.turboplan.web.client.model.GwtUserProfile.EmployeeGender;


@Test(groups = { "TurboPlan.all", "TurboPlan.web", "TurboPlan.gwtmodel" })
public class GwtUserProfileTest {
	@SuppressWarnings("deprecation")
	@Test
	public void read() {
		
		GwtUser testUser = new GwtUser( "TestDummy", "testPassword");
		GwtUserProfile testProf = new GwtUserProfile(1L, "Test", "Dummy", new Date("12/23/2008"));
		GwtAddress testAdd = new GwtAddress("123 Test St.", "TS", "Testville",  99999, "USA");
		
		testProf.setEmail("test1@test.com");
		testProf.setEmail2("test2@test.com");
		testProf.setEmail3("test3@test.com");
		testProf.setEmail4("test4@test.com");
		testProf.setEmail5("test5@test.com");
		testProf.setGender(EmployeeGender.Male);
		testProf.setHomePhoneNumber(1112223333L);
		testProf.setMobilePhoneNumber(1112224444L);
		testProf.setOtherPhoneNumber(1112225555L);
		testProf.setOtherPhoneNumber2(1112226666L);
		testProf.setOtherPhoneNumber3(1112227777L);
		testProf.setAddress(testAdd);
		
		//Need to test skills here!
		
		Assert.assertEquals(testProf.getFirstName(), "Test");
		Assert.assertEquals(testProf.getLastName(), "Dummy");
		Assert.assertEquals(testProf.getDateOfBirth(), new Date("12/23/2008"));
		Assert.assertEquals(testProf.getAddress().getAddress(), "123 Test St.");
		Assert.assertEquals(testProf.getAddress().getCity(), "Testville");
		Assert.assertEquals(testProf.getAddress().getState(), "TS");
		Assert.assertEquals(testProf.getAddress().getZip(), 99999);
		Assert.assertEquals(testProf.getEmail(), "test1@test.com");
		Assert.assertEquals(testProf.getEmail2(), "test2@test.com");
		Assert.assertEquals(testProf.getEmail3(), "test3@test.com");
		Assert.assertEquals(testProf.getEmail4(), "test4@test.com");
		Assert.assertEquals(testProf.getEmail5(), "test5@test.com");
		Assert.assertEquals(testProf.getGender(), EmployeeGender.Male);
		Assert.assertEquals(testProf.getHomePhoneNumber(), new Long(1112223333));
		Assert.assertEquals(testProf.getMobilePhoneNumber(), new Long(1112224444));
		Assert.assertEquals(testProf.getOtherPhoneNumber(), new Long(1112225555));
		Assert.assertEquals(testProf.getOtherPhoneNumber2(), new Long(1112226666));
		Assert.assertEquals(testProf.getOtherPhoneNumber3(), new Long(1112227777));
	}
}