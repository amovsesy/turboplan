package edu.calpoly.csc.luna.turboplan.core.entity;

import java.util.Date;

import org.testng.annotations.Test;

import edu.calpoly.csc.luna.turboplan.core.entity.AbstractUser.UserStatus;
import edu.calpoly.csc.luna.turboplan.core.entity.Task.TaskPriority;
import edu.calpoly.csc.luna.turboplan.core.entity.Task.TaskStatus;
import edu.calpoly.csc.luna.turboplan.core.entity.embeddable.Address;
import edu.calpoly.csc.luna.turboplan.core.entity.embeddable.UserProfile;
import edu.calpoly.csc.luna.turboplan.core.entity.embeddable.UserProfile.EmployeeGender;
/**
 * Tests User.java
 * @author Stephanie Long
 *
 */
@Test(groups = { "TurboPlan.all", "TurboPlan.core" })
public class UserTest {
	@SuppressWarnings("deprecation")
	@Test
	public void read() {
		
		User testUser = new User("TestDummy", "testPassword", UserStatus.Active);
		UserProfile testProf = new UserProfile("Test", "Dummy", new Date("12/23/2008"));
		Address testAdd = new Address("123 Test St.", "Testville", "USA", "TS", 99999);
		Company comp = new Company("testCpmp", "123 Address");
		
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
		testUser.setProfile(testProf);
		testUser.setCompany(comp);
		
		UserProfile usersProf = testUser.getProfile();
		
		assert testUser.getCompany().getName().equals("testCpmp");
		assert testUser.isActive().equals(UserStatus.Active);
		assert usersProf.getFirstName().equals("Test");
		assert usersProf.getLastName().equals("Dummy");
		assert usersProf.getDateOfBirth().equals(new Date("12/23/2008"));
		assert usersProf.getAddress().getAddress().equals("123 Test St.");
		assert usersProf.getAddress().getCity().equals("Testville");
		assert usersProf.getAddress().getState().equals("TS");
		assert usersProf.getAddress().getZip() == 99999;
		assert usersProf.getEmail().equals("test1@test.com");
		assert usersProf.getEmail2().equals("test2@test.com");
		assert usersProf.getEmail3().equals("test3@test.com");
		assert usersProf.getEmail4().equals("test4@test.com");
		assert usersProf.getEmail5().equals("test5@test.com");
		assert usersProf.getGender().equals(EmployeeGender.Male);
		assert usersProf.getHomePhoneNumber().equals("1112223333");
		assert usersProf.getMobilePhoneNumber().equals("1112224444");
		assert usersProf.getOtherPhoneNumber().equals("1112225555");
		assert usersProf.getOtherPhoneNumber2().equals("1112226666");
		assert usersProf.getOtherPhoneNumber3().equals("1112227777");
		
		
		//Need to test  tasks, converttoGWT  here!
		
		Task task = new Task(TaskStatus.New, "Title", "description",
				testAdd, "contactFirstName", "contactLastName", "12222222222", "3333333333", new Date("4/2/09"),
			    new  Date("12/3/09"), new Date("3/4/07"), 01.25, TaskPriority.NORMAL, comp);
	
	     
	
	}
}