package edu.calpoly.csc.luna.turboplan.web.client.widget;


import com.thoughtworks.selenium.*;

import java.util.regex.Pattern;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
/**
 * Tests Inactivating and Reactivating an Employee
 * @author Stephanie Long
 *
 */
public class ReInActivateEmployeeSeleniumTest {

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
		selenium.click("ext-gen39");
		Thread.sleep(1000);
		selenium.click("xpath=//html/body/table[2]/tbody/tr[2]/td/table/tbody/tr/td[3]/button");
		Thread.sleep(5000);
		selenium.doubleClickAt("xpath=//html/body/table[2]/tbody/tr[3]/td/div/div/div/div[3]/div/div[2]/div/div/div/div[2]/div/div/div[2]/div/div/div[2]/div/table/tbody/tr/td/div", "");
		selenium.click("xpath=//html/body/div[14]/div[2]/div[2]/div/div/div/div/div/table/tbody/tr/td[3]/table/tbody/tr/td[2]/em");
		Thread.sleep(1000);
		selenium.clickAt("xpath=//html/body/div[14]/div[2]/div[2]/div/div/div/div/div/table/tbody/tr/td/table/tbody/tr/td[2]/em/button", "");
		Thread.sleep(1000);
		selenium.doubleClickAt("xpath=//html/body/table[2]/tbody/tr[3]/td/div/div/div/div[3]/div/div[2]/div/div/div/div[2]/div/div/div[2]/div/div[2]/div[2]/div/table/tbody/tr/td/div", "");
		selenium.click("xpath=//html/body/div[18]/div[2]/div[2]/div/div/div/div/div/table/tbody/tr/td[4]/table/tbody/tr/td[2]");
		selenium.stop();
	}
}
