package edu.calpoly.csc.luna.turboplan.core.security;

import org.testng.annotations.Test;

@Test(groups = { "TurboPlan.all", "TurboPlan.core", "TurboPlan.security", "TurboPlan.security.authentication"},
		dependsOnGroups = {"TurboPlan.dao.user"})
public class AuthenticationTest {
	private static final String uname = "mliu2";
	private static final String correctPass = "LunaSet";
	private static final String wrongPass = "abc";
	
	@Test
	public void checkAccessGranted() {
		assert (Authentication.authenticate(uname, correctPass) != null);
	}
	
	@Test
	public void checkAccessNotGranted() {
		assert (Authentication.authenticate(uname, wrongPass) == null);
	}
}
