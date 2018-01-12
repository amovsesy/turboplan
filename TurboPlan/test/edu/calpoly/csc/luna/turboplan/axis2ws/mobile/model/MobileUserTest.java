package edu.calpoly.csc.luna.turboplan.axis2ws.mobile.model;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * MobileUserTest - tests methods within MobileUser
 * 
 * @author Bradley Barbee - Luna Set
 * @version 1.0
 */
@Test(groups = { "TurboPlan.all", "TurboPlan.mobile", "TurboPlan.mobile.model" })
public class MobileUserTest {

	MobileUser user;
	
	@BeforeMethod
    public void setup() {
        user = new MobileUser(true);
	}
	
	@Test (groups = {"constructor", "getters"})
	public void testDefaultConstructor() {
		user = new MobileUser();
		Assert.assertTrue(user.isActiveStatus());
	}

	@Test (groups = {"constructor", "getters"})
	public void testConstructor() {
		user = new MobileUser(true, new String[]{"perm"}, (long)25);
		Assert.assertTrue(user.isActiveStatus());
		Assert.assertTrue(user.getPermission()[0].equals("perm"));
		Assert.assertEquals((long)25, user.getUserID());
	}
	
	@Test (groups = "getters")
    public void testgetActiveStatus() {
	   Assert.assertTrue(user.isActiveStatus());
	}
	
	@Test (groups = {"setters", "getters"})
    public void testActiveStatus() {
		user.setActiveStatus(false);
		Assert.assertFalse(user.isActiveStatus());
	}
	
	@Test (groups = "getters")
    public void testPermission() {
		String[] permissions = new String[]{"Perm1", "Perm2"}; 
		user.setPermission(permissions);
		Assert.assertTrue(user.getPermission().equals(permissions));
	}
	
	@Test (groups = "getters")
    public void testID() {
		user.setUserID((long)1);
		Assert.assertEquals((long)1, user.getUserID());
	}
	
	@Test (groups = "hib2mob")
	public void toEntityModel() { 
		//assert(user.toEntityModel().getUserID() == user.getUserID());
	}
}

