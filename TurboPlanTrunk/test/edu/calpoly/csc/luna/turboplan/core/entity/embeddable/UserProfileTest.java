package edu.calpoly.csc.luna.turboplan.core.entity.embeddable;

import java.awt.List;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.testng.annotations.Test;

import edu.calpoly.csc.luna.turboplan.core.entity.User;
import edu.calpoly.csc.luna.turboplan.core.entity.AbstractUser.UserStatus;
import edu.calpoly.csc.luna.turboplan.core.entity.Task.TaskStatus;
import edu.calpoly.csc.luna.turboplan.core.entity.embeddable.Address;
import edu.calpoly.csc.luna.turboplan.core.entity.embeddable.UserProfile.EmployeeGender;

/**
 * Tests the UserProfile.java
 * @author Stephanie Long
 *
 */
@Test(groups = { "TurboPlan.all", "TurboPlan.core" })
public class UserProfileTest {
	@SuppressWarnings("deprecation")
	@Test
	public void read() {
		
		User testUser = new User("TestDummy", "testPassword", UserStatus.Active);
		UserProfile testProf = new UserProfile("Test", "Dummy", new Date("12/23/2008"));
		Address testAdd = new Address("123 Test St.", "Testville", "USA", "TS", 99999);
		
		testProf.setEmail("test1@test.com");
		testProf.setEmail2("test2@test.com");
		testProf.setEmail3("test3@test.com");
		testProf.setEmail4("test4@test.com");
		testProf.setEmail5("test5@test.com");
		testProf.setGender(EmployeeGender.Male);
		testProf.setHomePhoneNumber("1112223333");
		testProf.setMobilePhoneNumber("1112224444");
		testProf.setOtherPhoneNumber("1112225555");
		testProf.setOtherPhoneNumber2("1112226666");
		testProf.setOtherPhoneNumber3("1112227777");
		testProf.setAddress(testAdd);
		testProf.setUsr(testUser);
		
		//Need to test skills here!
		
		assert testProf.getFirstName().equals("Test");
		assert testProf.getLastName().equals("Dummy");
		assert testProf.getDateOfBirth().equals(new Date("12/23/2008"));
		assert testProf.getAddress().getAddress().equals("123 Test St.");
		assert testProf.getAddress().getCity().equals("Testville");
		assert testProf.getAddress().getState().equals("TS");
		assert testProf.getAddress().getZip() == 99999;
		assert testProf.getEmail().equals("test1@test.com");
		assert testProf.getEmail2().equals("test2@test.com");
		assert testProf.getEmail3().equals("test3@test.com");
		assert testProf.getEmail4().equals("test4@test.com");
		assert testProf.getEmail5().equals("test5@test.com");
		assert testProf.getGender().equals(EmployeeGender.Male);
		assert testProf.getHomePhoneNumber().equals("1112223333");
		assert testProf.getMobilePhoneNumber().equals("1112224444");
		assert testProf.getOtherPhoneNumber().equals("1112225555");
		assert testProf.getOtherPhoneNumber2().equals("1112226666");
		assert testProf.getOtherPhoneNumber3().equals("1112227777");
		
		
		
		
	
	
	
	}
}