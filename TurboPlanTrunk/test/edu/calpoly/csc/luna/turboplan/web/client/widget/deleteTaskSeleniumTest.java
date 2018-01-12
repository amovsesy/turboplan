package edu.calpoly.csc.luna.turboplan.web.client.widget;

import com.thoughtworks.selenium.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.regex.Pattern;
/**
 * Tests deleting a task
 * @author Stephanie Long
 *
 */
public class deleteTaskSeleniumTest {
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
		selenium.doubleClickAt("xpath=//html/body/table[2]/tbody/tr[3]/td/div/div/div/div[2]/div/div[2]/div[2]/div/div[2]/div/div/div/div/div/div/div[2]/div/div/div[2]/div/table/tbody/tr/td/div", "");
		selenium.click("xpath=//html/body/div[26]/div[2]/div[2]/div/div/div/div/div/table/tbody/tr/td[4]/table/tbody/tr/td[2]/em/button");
		Thread.sleep(1000);
		selenium.clickAt("xpath=//html/body/div[25]/div[2]/div[2]/div/div/div/div/div/table/tbody/tr/td/table/tbody/tr/td[2]", "");
	}
}
