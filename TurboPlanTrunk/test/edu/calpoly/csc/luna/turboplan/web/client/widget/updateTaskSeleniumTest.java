package edu.calpoly.csc.luna.turboplan.web.client.widget;

import com.thoughtworks.selenium.*;


import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.regex.Pattern;

/**
 * Tests updating a task
 * @author Stephanie Long
 *
 */
public class updateTaskSeleniumTest {
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
		selenium = new DefaultSelenium("localhost", 4444, BrowserSelection.Firefox.value(), "http://www.google.com");
	}
	
	@Test
	public void test() throws Exception {
		selenium.open("/TurboPlan/TurboPlan.html");
		selenium.type("ext-gen14", "djanzen");
		selenium.type("ext-gen16", "LunaSet");
		selenium.click("ext-gen39");
		Thread.sleep(1000);
		selenium.click("xpath=//html/body/table[2]/tbody/tr[2]/td/table/tbody/tr/td[2]/button");
		Thread.sleep(1000);
		selenium.doubleClickAt("xpath=//html/body/table[2]/tbody/tr[3]/td/div/div/div/div[2]/div/div[2]/div[2]/div/div[2]/div/div/div/div/div/div/div[2]/div/div/div[2]/div[4]/table/tbody/tr/td/div", "");
		selenium.type("xpath=//html/body/div[26]/div[2]/div/div/div/div/div/div[2]/div/div/div/div/div/div[2]/div/div/div/input", "Fix Disposal");
		selenium.type("xpath=//html/body/div[26]/div[2]/div/div/div/div/div/div[2]/div/div/div/div/div/div[2]/div/div[2]/div/div/div/div/div/div/table/tbody/tr/td/div/div/div/div/div/div/div/div/div/input", "03/09/09");
		selenium.type("xpath=//html/body/div[26]/div[2]/div/div/div/div/div/div[2]/div/div/div/div/div/div[2]/div/div[2]/div/div/div/div[2]/div/div/table/tbody/tr/td/div/div/div/div/div/div/div/div/div/input", "03/09/09");
		selenium.type("xpath=./html/body/div[26]/div[2]/div/div/div/div/div/div[2]/div/div/div/div/div/div[2]/div/div[2]/div/div/div/div/div/div/table/tbody/tr[2]/td/div/div/div/div/div/div/div/div/div/input", "9:30 AM");
		selenium.type("xpath=//html/body/div[26]/div[2]/div/div/div/div/div/div[2]/div/div/div/div/div/div[2]/div/div[2]/div/div/div/div[2]/div/div/table/tbody/tr[2]/td/div/div/div/div/div/div/div/div/div/input", "11:30 AM");
		selenium.type("xpath=//html/body/div[26]/div[2]/div/div/div/div/div/div[2]/div/div/div/div/div/div[2]/div/div[3]/div/div/div/div/div/div/div/div/div/input[2]", "Normal");
		selenium.type("xpath=//html/body/div[26]/div[2]/div/div/div/div/div/div[2]/div/div/div/div/div/div[2]/div/div[4]/div/textarea", "Disposal in kitchen");
		selenium.type("xpath=/html/body/div[26]/div[2]/div/div/div/div/div/div[2]/div/div/div/div/div[2]/div[2]/div/div/div/div/div/div/div/input", "Johnny");
		selenium.type("xpath=//html/body/div[26]/div[2]/div/div/div/div/div/div[2]/div/div/div/div/div[2]/div[2]/div/div/div/div/div/div[2]/div/input", "Johnson");
		selenium.type("xpath=//html/body/div[26]/div[2]/div/div/div/div/div/div[2]/div/div/div/div/div[2]/div[2]/div/div/div/div/div/div[3]/div/input", "2223334444");
		selenium.type("xpath=//html/body/div[26]/div[2]/div/div/div/div/div/div[2]/div/div/div/div/div[2]/div[2]/div/div/div/div/div/div[4]/div/input", "2223334444");
		selenium.type("xpath=//html/body/div[26]/div[2]/div/div/div/div/div/div[2]/div/div/div/div/div[2]/div[2]/div/div/div[2]/div/div/div/div/textarea", "123 Main St.");
		selenium.type("xpath=//html/body/div[26]/div[2]/div/div/div/div/div/div[2]/div/div/div/div/div[2]/div[2]/div/div/div[2]/div/div/div[2]/div/input", "Testtown");
		selenium.type("xpath=//html/body/div[26]/div[2]/div/div/div/div/div/div[2]/div/div/div/div/div[2]/div[2]/div/div/div[2]/div/div/div[3]/div/div/input[2]", "California");
		selenium.type("xpath=//html/body/div[26]/div[2]/div/div/div/div/div/div[2]/div/div/div/div/div[2]/div[2]/div/div/div[2]/div/div/div[4]/div/input", "22222");
		selenium.type("xpath=//html/body/div[26]/div[2]/div/div/div/div/div/div[2]/div/div/div/div/div[3]/div[2]/div/div/div/div/div/div[2]/div/div/div/div/input", "4.50");
		selenium.type("xpath=//html/body/div[26]/div[2]/div/div/div/div/div/div[2]/div/div/div/div/div[3]/div[2]/div/div[2]/div/textarea", "Bring tools");
		selenium.click("xpath=//html/body/div[26]/div[2]/div[2]/div/div/div/div/div/table/tbody/tr/td[5]/table/tbody/tr/td[2]/em/button");
		Thread.sleep(1000);
		selenium.doubleClickAt("xpath=//html/body/table[2]/tbody/tr[3]/td/div/div/div/div[2]/div/div[2]/div[2]/div/div[2]/div/div/div/div/div/div/div[2]/div/div[2]/div[2]/div/table/tbody/tr/td/div", "");
		Thread.sleep(1000);
		assert(("Fix Disposal").equals(selenium.getValue("xpath=//html/body/div[26]/div[2]/div/div/div/div/div/div[2]/div/div/div/div/div/div[2]/div/div/div/input")));
		assert(("03/09/09").equals(selenium.getValue("xpath=//html/body/div[26]/div[2]/div/div/div/div/div/div[2]/div/div/div/div/div/div[2]/div/div[2]/div/div/div/div/div/div/table/tbody/tr/td/div/div/div/div/div/div/div/div/div/input")));
		assert(("03/09/09").equals(selenium.getValue("xpath=//html/body/div[26]/div[2]/div/div/div/div/div/div[2]/div/div/div/div/div/div[2]/div/div[2]/div/div/div/div[2]/div/div/table/tbody/tr/td/div/div/div/div/div/div/div/div/div/input")));
		assert(("9:30 AM").equals(selenium.getValue("xpath=./html/body/div[26]/div[2]/div/div/div/div/div/div[2]/div/div/div/div/div/div[2]/div/div[2]/div/div/div/div/div/div/table/tbody/tr[2]/td/div/div/div/div/div/div/div/div/div/input")));
		assert(("11:30 AM").equals(selenium.getValue("xpath=//html/body/div[26]/div[2]/div/div/div/div/div/div[2]/div/div/div/div/div/div[2]/div/div[2]/div/div/div/div[2]/div/div/table/tbody/tr[2]/td/div/div/div/div/div/div/div/div/div/input")));
		assert(("Normal").equals(selenium.getValue("xpath=//html/body/div[26]/div[2]/div/div/div/div/div/div[2]/div/div/div/div/div/div[2]/div/div[3]/div/div/div/div/div/div/div/div/div/input[2]")));
		assert(("Disposal in kitchen").equals(selenium.getValue("xpath=//html/body/div[26]/div[2]/div/div/div/div/div/div[2]/div/div/div/div/div/div[2]/div/div[4]/div/textarea")));
		assert(("Johnny").equals(selenium.getValue("xpath=/html/body/div[26]/div[2]/div/div/div/div/div/div[2]/div/div/div/div/div[2]/div[2]/div/div/div/div/div/div/div/input")));
		assert(("Johnson").equals(selenium.getValue("xpath=//html/body/div[26]/div[2]/div/div/div/div/div/div[2]/div/div/div/div/div[2]/div[2]/div/div/div/div/div/div[2]/div/input")));
		assert(("2223334444").equals(selenium.getValue("xpath=//html/body/div[26]/div[2]/div/div/div/div/div/div[2]/div/div/div/div/div[2]/div[2]/div/div/div/div/div/div[3]/div/input")));
		assert(("2223334444").equals(selenium.getValue("xpath=//html/body/div[26]/div[2]/div/div/div/div/div/div[2]/div/div/div/div/div[2]/div[2]/div/div/div/div/div/div[4]/div/input")));
		assert(("123 Main St.").equals(selenium.getValue("xpath=//html/body/div[26]/div[2]/div/div/div/div/div/div[2]/div/div/div/div/div[2]/div[2]/div/div/div[2]/div/div/div/div/textarea")));
		assert(("Testtown").equals(selenium.getValue("xpath=//html/body/div[26]/div[2]/div/div/div/div/div/div[2]/div/div/div/div/div[2]/div[2]/div/div/div[2]/div/div/div[2]/div/input")));
		assert(("California").equals(selenium.getValue("xpath=//html/body/div[26]/div[2]/div/div/div/div/div/div[2]/div/div/div/div/div[2]/div[2]/div/div/div[2]/div/div/div[3]/div/div/input[2]")));
		assert(("22222").equals(selenium.getValue("xpath=//html/body/div[26]/div[2]/div/div/div/div/div/div[2]/div/div/div/div/div[2]/div[2]/div/div/div[2]/div/div/div[4]/div/input")));
		assert(("4.50").equals(selenium.getValue("xpath=//html/body/div[26]/div[2]/div/div/div/div/div/div[2]/div/div/div/div/div[3]/div[2]/div/div/div/div/div/div[2]/div/div/div/div/input")));
		assert(("Bring tools").equals(selenium.getValue("xpath=//html/body/div[26]/div[2]/div/div/div/div/div/div[2]/div/div/div/div/div[3]/div[2]/div/div[2]/div/textarea")));
	}
}
