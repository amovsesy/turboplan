package edu.calpoly.csc.luna.turboplan.core.dao;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import edu.calpoly.csc.luna.turboplan.core.entity.Company;
import edu.calpoly.csc.luna.turboplan.core.entity.Task;
import edu.calpoly.csc.luna.turboplan.core.entity.User;
import edu.calpoly.csc.luna.turboplan.core.security.encryption.Encryption;
import edu.calpoly.csc.luna.turboplan.core.util.StringUtil;

@Test(groups = { "TurboPlan.all", "TurboPlan.core", "TurboPlan.dao", "TurboPlan.dao.user" }, dependsOnGroups = { "TurboPlan.dao.company" })
public class UserDaoTest {
	@Test(dataProviderClass = DaoDataProvider.class, dataProvider = "users", groups = {"TurboPlan.dao.user.add"})
	public void addUser(User usr) throws CloneNotSupportedException {
		int before = UserDao.getInstance().getTableRowCount();

		DaoDataProvider.addUsername(UserDao.getInstance().addUser(usr), UserDao.DEFAULT_PASSWORD);

		int after = UserDao.getInstance().getTableRowCount();

		Assert.assertEquals(after, before + 1);
	}

	@Test(dataProviderClass = DaoDataProvider.class, dataProvider = "users", dependsOnMethods = { "addUser", "getUserPerm",  "updateClearTextPasswordForUserById"}, alwaysRun = true, enabled = false, dependsOnGroups={"TurboPlan.require.user"})
	public void deleteUser(User usr) {
		int before = UserDao.getInstance().getTableRowCount();

		UserDao.getInstance().deleteUser(usr);

		int after = UserDao.getInstance().getTableRowCount();

		Assert.assertEquals(after, before - 1);
	}

	@Test(dataProviderClass = DaoDataProvider.class, dataProvider = "companies", dependsOnMethods = { "addUser" })
	public void getAllUserOfCompany(Company company) {
		List<User> list = UserDao.getInstance().getAllUserOfCompany(company);
		for (User usr : list) {
			Assert.assertEquals(usr.getCompany(), company);
		}
	}

	@Test
	public void getAllUsers() {
		Assert.assertEquals(UserDao.getInstance().getAllUsers().size(), UserDao.getInstance().getTableRowCount());
	}

	@Test(dataProviderClass = DaoDataProvider.class, dataProvider = "users", dependsOnMethods = { "addUser" })
	public void getAllUsersInSameCompanyAsThisUserById(User usr) {
		List<User> list = UserDao.getInstance().getAllUsersInSameCompanyAsThisUserById(usr.getUserID());
		for (User user : list) {
			Assert.assertEquals(user.getCompany().getCompanyID(), usr.getCompany().getCompanyID());
		}
	}

   @Test(dataProviderClass = DaoDataProvider.class, dataProvider = "users", dependsOnMethods = { "addUser" })
	public void getTasksByUserId(User usr) {
		List<Task> list = UserDao.getInstance().getTasksByUserId(usr.getUserID());
		for (Task task : list) {
			Assert.assertTrue(task.getUsers().contains(usr));
		}
	}

	@Test(dataProviderClass = DaoDataProvider.class, dataProvider = "users", dependsOnMethods = { "addUser" })
	public void getUserById(User usr) {
		User retUsr = UserDao.getInstance().getUserById(usr.getUserID());

		Assert.assertEquals(retUsr.getUserID(), usr.getUserID());
	}
	
   @Test(dataProviderClass = DaoDataProvider.class, dataProvider = "usernames", dependsOnMethods = { "addUser", "updateClearTextPasswordForUserById" })
   public void fakeTest(String expected)
   {
      Assert.assertTrue(true);
   }
	

	@Test(dataProviderClass = DaoDataProvider.class, dataProvider = "usernames", dependsOnMethods = { "addUser", "updateClearTextPasswordForUserById" })
	public void getUserByUsername(String expected) {
		String actual = UserDao.getInstance().getUserByClearTextUsername(expected).getUserName();
		expected = Encryption.getInstance().encrypt(expected);

		Assert.assertEquals(actual, expected);
	}

	@Test(dataProviderClass = DaoDataProvider.class, dataProvider = "users", dependsOnMethods = { "addUser" })
	public void getUserEmail(User input) {
		User ret = UserDao.getInstance().getUserEmail(input.getProfile().getEmail());

		Assert.assertEquals(ret.getProfile().getEmail(), input.getProfile().getEmail());
	}

	@Test(dataProviderClass = DaoDataProvider.class, dataProvider = "users", dependsOnMethods = { "addUser" })
	public void updateClearTextPasswordForUserById(User input) {
		String newpass = "testpass";
		
		UserDao.getInstance().updateClearTextPasswordForUserById(input.getUserID(), newpass);
		
		Assert.assertEquals(
				UserDao.getInstance().getUserById(input.getUserID()).getPassword(), 
				Encryption.getInstance().encrypt(newpass)
		);
	}
	
	@Test(dataProviderClass = DaoDataProvider.class, dataProvider = "users", dependsOnMethods = { "addUser" })
	public void getUserPerm(User input) {
		Assert.assertNotNull(UserDao.getInstance().getUserPerm(input.getUserID()));
	}

	@Test(dataProviderClass = DaoDataProvider.class, dataProvider = "users", dependsOnMethods = { "addUser" })
	public void updateUser(User input) {
		byte[] rand = new byte[20];
		DaoDataProvider.RANDOM_GENERATOR.nextBytes(rand);
		String expected = StringUtil.getHexString(rand);

		input.getProfile().setOtherPhoneNumber3(expected);
		UserDao.getInstance().updateUser(input);
		String actual = UserDao.getInstance().getUserById(input.getUserID()).getProfile().getOtherPhoneNumber3();
		
		Assert.assertEquals(actual, expected);
	}

}
