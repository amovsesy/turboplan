package edu.calpoly.csc.luna.turboplan.nonfunctional;

import com.thoughtworks.selenium.*;

import java.io.InputStream;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * NonfunctionalTest2 - tests time to load TurboPlan main page (web)
 * 
 * @author Bradley Barbee - Luna Set
 * @version 1.0
 */
@Test(groups = { "TurboPlan.all", "TurboPlan.nonfunctional" })
public class NonfunctionalTest2 {

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
	public void testNonfunctional2() {
		
		try {
			selenium.start();
			selenium.open("/TurboPlan/TurboPlan.html");
			selenium.type("ext-gen14", "djanzen");
			selenium.type("ext-gen16", "LunaSet");
			selenium.click("xpath=//html/body/div[4]/div[2]/div/div/div/div/div/div[2]/div[2]/div/div/div/div/div/table/tbody/tr/td/table/tbody/tr/td[2]");
			Thread.sleep(1000);
			assert(selenium.isVisible("xpath=//html/body/table[2]/tbody/tr[3]/td/div/div/div/div/div/div[2]/div/div/div/div/div/div/div/div/div/div/div/div[2]/div/div/div/div/div/div/div[2]/div"));
			selenium.stop();
		} catch (Exception e) {
			
		}
		
	}
}
