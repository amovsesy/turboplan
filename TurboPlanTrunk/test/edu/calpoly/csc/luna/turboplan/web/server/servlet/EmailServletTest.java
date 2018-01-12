package edu.calpoly.csc.luna.turboplan.web.server.servlet;

import javax.mail.MessagingException;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Test(groups = { "TurboPlan.all", "TurboPlan.web", "TurboPlan.gwtservlet", "TurboPlan.gwtservlet.email" })
public class EmailServletTest {
	private String resetPass;
	private String resetUP;
	private String newUsr;
	
	@BeforeClass
	public void setUp() throws MessagingException{
		resetPass = new EmailServlet().sendEmail("LunaSet", "bbarbee", "lunasetturboplan@gmail.com", true, false);
		newUsr = new EmailServlet().sendEmail("LunaSet", "bbarbee", "lunasetturboplan@gmail.com", false, true);
		resetUP = new EmailServlet().sendEmail("LunaSet", "bbarbee", "lunasetturboplan@gmail.com", false, false);
		// Password for lunasetturboplan@gmail.com is: "capstone"
	}
	
	@Test
	public void resetCheck() {
		Assert.assertEquals(resetPass, "Your password has been reset to LunaSet\nGo to http://lunaset.csc.calpoly.edu/TurboPlan/TurboPlan.html");
	}
	
	@Test
	public void newUsrCheck() {
		Assert.assertEquals(newUsr, "Your username has been set to bbarbee and your password has been set to LunaSet\nGo to http://lunaset.csc.calpoly.edu/TurboPlan/TurboPlan.html");
	}
	
	@Test
	public void resetUPCheck() {	
		Assert.assertEquals(resetUP, "Your username has been reset to bbarbee and your password has been reset to LunaSet\nGo to http://lunaset.csc.calpoly.edu/TurboPlan/TurboPlan.html");
	}
}
