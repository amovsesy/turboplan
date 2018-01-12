package edu.calpoly.csc.luna.turboplan.web.client.widget;

import com.thoughtworks.selenium.*;
import org.testng.annotations.Test;

import java.util.regex.Pattern;

import org.testng.annotations.BeforeClass;

/**
 * Tests adding an employee followed by adding another
 * @author Stephanie Long
 *
 */
public class AddAddAnotherEmpSeleniumTest extends AbstractTurboPlanSeleniumTest {
	
	@Test
	public void test() throws Exception {		
		selenium.type("ext-gen14", "djanzen");
		selenium.type("ext-gen16", "LunaSet");
		selenium.click("xpath=//html/body/div[4]/div[2]/div/div/div/div/div/div[2]/div[2]/div/div/div/div/div/table/tbody/tr/td/table/tbody/tr/td[2]");
		Thread.sleep(1000);	
		selenium.click("xpath=//html/body/table/tbody/tr[2]/td/table/tbody/tr/td[3]/button");
		selenium.click("xpath=//html/body/table/tbody/tr[3]/td/div/div/div/div[3]/div/div[2]/div/div/div/div/div/table/tbody/tr/td/table/tbody/tr/td[2]/em/button");
		selenium.type("xpath=//html/body/div[13]/div[2]/div/div/div/div/div/div[2]/div/div/div/div/div/div/div/div/div/div[2]/div/div/div/input", "Daisy");
		selenium.type("xpath=//html/body/div[13]/div[2]/div/div/div/div/div/div[2]/div/div/div/div/div/div/div/div/div/div[2]/div/div[3]/div/input", "Duck");
		selenium.click("xpath=//html/body/div[13]/div[2]/div/div/div/div/div/div[2]/div/div/div/div/div/div/div/div/div/div[2]/div/div[4]/div/div/img");
		selenium.click("xpath=// html/body/div[15]/ul/li/div/table/tbody/tr[2]/td/table/tbody/tr[3]/td/a");
		selenium.type("xpath=//html/body/div[13]/div[2]/div/div/div/div/div/div[2]/div/div/div/div/div/div/div/div/div/div[2]/div/div[5]/div/div/input", "Female");
		selenium.click("gwt-uid-2");
		selenium.type("xpath=//html/body/div[13]/div[2]/div/div/div/div/div/div[2]/div/div/div/div/div/div/div/div/div[2]/div[2]/div/div/div/textarea", "123 Duck Way");
		selenium.type("xpath=//html/body/div[13]/div[2]/div/div/div/div/div/div[2]/div/div/div/div/div/div/div/div/div[2]/div[2]/div/div[2]/div/input", "Duckville");
		selenium.type("xpath=//html/body/div[13]/div[2]/div/div/div/div/div/div[2]/div/div/div/div/div/div/div/div/div[2]/div[2]/div/div[3]/div/div/input[2]", "Alaska");
		selenium.type("xpath=//html/body/div[13]/div[2]/div/div/div/div/div/div[2]/div/div/div/div/div/div/div/div/div[2]/div[2]/div/div[4]/div/input", "77889");
		selenium.type("xpath=//html/body/div[13]/div[2]/div/div/div/div/div/div[2]/div/div/div/div/div[2]/div/div/div/div/div[2]/div/div/div/input", "long@calpoly.edu");
		selenium.type("xpath=//html/body/div[13]/div[2]/div/div/div/div/div/div[2]/div/div/div/div/div[2]/div/div/div/div[2]/div[2]/div/div[2]/div/input", "56733338888");
		selenium.click("xpath=//html/body/div[13]/div[2]/div[2]/div/div/div/div/div/table/tbody/tr/td[3]/table/tbody/tr/td[2]/em/button");
		selenium.type("xpath=//html/body/div[13]/div[2]/div/div/div/div/div/div[2]/div/div/div/div/div/div/div/div/div/div[2]/div/div/div/input", "Elmer");
		selenium.type("xpath=//html/body/div[13]/div[2]/div/div/div/div/div/div[2]/div/div/div/div/div/div/div/div/div/div[2]/div/div[3]/div/input", "Fudd");
		selenium.click("xpath=//html/body/div[13]/div[2]/div/div/div/div/div/div[2]/div/div/div/div/div/div/div/div/div/div[2]/div/div[4]/div/div/img");
		selenium.click("xpath=// html/body/div[15]/ul/li/div/table/tbody/tr[2]/td/table/tbody/tr[3]/td/a");
		selenium.type("xpath=//html/body/div[13]/div[2]/div/div/div/div/div/div[2]/div/div/div/div/div/div/div/div/div/div[2]/div/div[5]/div/div/input", "Male");
		selenium.click("gwt-uid-1");
		selenium.type("xpath=//html/body/div[13]/div[2]/div/div/div/div/div/div[2]/div/div/div/div/div[2]/div/div/div/div/div[2]/div/div/div/input", "long@calpoly.edu");
		selenium.type("xpath=//html/body/div[13]/div[2]/div/div/div/div/div/div[2]/div/div/div/div/div/div/div/div/div[2]/div[2]/div/div/div/textarea", "432432 Gun Way");
		selenium.type("xpath=//html/body/div[13]/div[2]/div/div/div/div/div/div[2]/div/div/div/div/div/div/div/div/div[2]/div[2]/div/div[2]/div/input", "Mountain View");
		selenium.type("xpath=//html/body/div[13]/div[2]/div/div/div/div/div/div[2]/div/div/div/div/div/div/div/div/div[2]/div[2]/div/div[3]/div/div/input[2]", "Missouri");
		selenium.type("xpath=//html/body/div[13]/div[2]/div/div/div/div/div/div[2]/div/div/div/div/div/div/div/div/div[2]/div[2]/div/div[4]/div/input", "66789");
		selenium.type("xpath=//html/body/div[13]/div[2]/div/div/div/div/div/div[2]/div/div/div/div/div[2]/div/div/div/div[2]/div[2]/div/div[2]/div/input", "2234445656");
		selenium.click("xpath=//html/body/div[13]/div[2]/div[2]/div/div/div/div/div/table/tbody/tr/td/table/tbody/tr/td[2]/em/button");
		
		selenium.stop();
	}
}
