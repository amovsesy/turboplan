package edu.calpoly.csc.luna.turboplan.web;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

public class TurboPlanSeleniumTest {
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
	
	private Selenium sel;
	
	@BeforeClass
	public void setup() {
		sel = new DefaultSelenium("localhost", 4444, BrowserSelection.Firefox.value(), "http://www.google.com");
	}
	
	@Test
	public void test() {
		
	}
}
