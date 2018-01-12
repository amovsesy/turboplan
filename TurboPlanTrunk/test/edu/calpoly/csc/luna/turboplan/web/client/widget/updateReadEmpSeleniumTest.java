package edu.calpoly.csc.luna.turboplan.web.client.widget;

import com.thoughtworks.selenium.*;

import java.util.regex.Pattern;
import org.testng.annotations.Test;

import org.testng.annotations.BeforeClass;

/**
 * Tests updating and reading an employee'e profile information
 * @author Stephanie Long
 */
public class updateReadEmpSeleniumTest {
	private enum BrowserSelection {
		Firefox ("*firefox");
		
		private String value;
		
		private BrowserSelection(String value) {
			this.value = value;
		}
		
		public String value() {
			return value;
		}
	}
	
	private Selenium selenium;
	
	@BeforeClass
	public void setup() {
		selenium = new DefaultSelenium("localhost", 4444, BrowserSelection.Firefox.value(), "http://lunaset.csc.calpoly.edu");
	}
	@Test
	public void test() throws Exception {
		selenium.start();
		selenium.open("/TurboPlan/TurboPlan.html");
		selenium.type("ext-gen14", "djanzen");
		selenium.type("ext-gen16", "LunaSet");
		selenium.click("xpath=//html/body/div[4]/div[2]/div/div/div/div/div/div[2]/div[2]/div/div/div/div/div/table/tbody/tr/td/table/tbody/tr/td[2]");
		Thread.sleep(1000);
		selenium.click("xpath=//html/body/table[2]/tbody/tr[2]/td/table/tbody/tr/td[3]/button");
		Thread.sleep(1000);
		selenium.doubleClickAt("xpath=//html/body/table[2]/tbody/tr[3]/td/div/div/div/div[3]/div/div[2]/div/div/div/div[2]/div/div/div[2]/div/div/div[2]/div/table/tbody/tr/td/div", "");
		assert(("David").equals(selenium.getValue("xpath=//html/body/div[14]/div[2]/div/div/div/div/div/div[2]/div/div/div/div/div/div/div/div/div/div[2]/div/div/div/input")));
		assert(("Janzen").equals(selenium.getValue("xpath=//html/body/div[14]/div[2]/div/div/div/div/div/div[2]/div/div/div/div/div/div/div/div/div/div[2]/div/div[3]/div/input")));
		assert(("Male").equals(selenium.getValue("xpath=//html/body/div[14]/div[2]/div/div/div/div/div/div[2]/div/div/div/div/div/div/div/div/div/div[2]/div/div[5]/div/div/input")));
		assert(("123 Main St").equals(selenium.getValue("xpath=//html/body/div[14]/div[2]/div/div/div/div/div/div[2]/div/div/div/div/div/div/div/div/div[2]/div[2]/div/div/div/textarea")));
		assert(("SLO").equals(selenium.getValue("xpath=//html/body/div[14]/div[2]/div/div/div/div/div/div[2]/div/div/div/div/div/div/div/div/div[2]/div[2]/div/div[2]/div/input")));
		assert(("California").equals(selenium.getValue("xpath=//html/body/div[14]/div[2]/div/div/div/div/div/div[2]/div/div/div/div/div/div/div/div/div[2]/div[2]/div/div[3]/div/div/input[2]")));
		assert(("12345").equals(selenium.getValue("xpath=//html/body/div[14]/div[2]/div/div/div/div/div/div[2]/div/div/div/div/div/div/div/div/div[2]/div[2]/div/div[4]/div/input")));
		assert(("fake@domain.com").equals(selenium.getValue("xpath=//html/body/div[14]/div[2]/div/div/div/div/div/div[2]/div/div/div/div/div[2]/div/div/div/div/div[2]/div/div/div/input")));
		assert(("111111231231231").equals(selenium.getValue("xpath=//html/body/div[14]/div[2]/div/div/div/div/div/div[2]/div/div/div/div/div[2]/div/div/div/div[2]/div[2]/div/div[2]/div/input")));
		selenium.type("xpath=//html/body/div[14]/div[2]/div/div/div/div/div/div[2]/div/div/div/div/div[2]/div/div/div/div[2]/div[2]/div/div/div/input", "8888888888888");
		selenium.type("xpath=//html/body/div[14]/div[2]/div/div/div/div/div/div[2]/div/div/div/div/div/div/div/div/div/div[2]/div/div[2]/div/input", "A");
		selenium.click("ext-gen291");
		selenium.click("xpath=//html/body/div[14]/div[2]/div[2]/div/div/div/div/div/table/tbody/tr/td/table/tbody/tr/td[2]/em/button");
		Thread.sleep(1000);
		selenium.doubleClickAt("xpath=//html/body/table[2]/tbody/tr[3]/td/div/div/div/div[3]/div/div[2]/div/div/div/div[2]/div/div/div[2]/div/div/div[2]/div/table/tbody/tr/td/div", "");
		assert(("David").equals(selenium.getValue("xpath=//html/body/div[18]/div[2]/div/div/div/div/div/div[2]/div/div/div/div/div/div/div/div/div/div[2]/div/div/div/input")));
		assert(("A").equals(selenium.getValue("xpath=//html/body/div[18]/div[2]/div/div/div/div/div/div[2]/div/div/div/div/div/div/div/div/div/div[2]/div/div[2]/div/input")));
		assert(("Janzen").equals(selenium.getValue("xpath=//html/body/div[18]/div[2]/div/div/div/div/div/div[2]/div/div/div/div/div/div/div/div/div/div[2]/div/div[3]/div/input")));
		assert(("Male").equals(selenium.getValue("xpath=//html/body/div[18]/div[2]/div/div/div/div/div/div[2]/div/div/div/div/div/div/div/div/div/div[2]/div/div[5]/div/div/input")));
		assert(("123 Main St").equals(selenium.getValue("xpath=//html/body/div[18]/div[2]/div/div/div/div/div/div[2]/div/div/div/div/div/div/div/div/div[2]/div[2]/div/div/div/textarea")));
		assert(("SLO").equals(selenium.getValue("xpath=//html/body/div[18]/div[2]/div/div/div/div/div/div[2]/div/div/div/div/div/div/div/div/div[2]/div[2]/div/div[2]/div/input")));
		assert(("California").equals(selenium.getValue("xpath=//html/body/div[18]/div[2]/div/div/div/div/div/div[2]/div/div/div/div/div/div/div/div/div[2]/div[2]/div/div[3]/div/div/input[2]")));
		assert(("12345").equals(selenium.getValue("xpath=//html/body/div[18]/div[2]/div/div/div/div/div/div[2]/div/div/div/div/div/div/div/div/div[2]/div[2]/div/div[4]/div/input")));
		assert(("fake@domain.com").equals(selenium.getValue("xpath=//html/body/div[18]/div[2]/div/div/div/div/div/div[2]/div/div/div/div/div[2]/div/div/div/div/div[2]/div/div/div/input")));
		assert(("111111231231231").equals(selenium.getValue("xpath=//html/body/div[18]/div[2]/div/div/div/div/div/div[2]/div/div/div/div/div[2]/div/div/div/div[2]/div[2]/div/div[2]/div/input")));
		assert(("8888888888888").equals(selenium.getValue("xpath=//html/body/div[18]/div[2]/div/div/div/div/div/div[2]/div/div/div/div/div[2]/div/div/div/div[2]/div[2]/div/div/div/input")));
		selenium.stop();
	}
	
	


}
