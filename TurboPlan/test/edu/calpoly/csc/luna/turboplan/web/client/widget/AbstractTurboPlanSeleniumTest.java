package edu.calpoly.csc.luna.turboplan.web.client.widget;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

/**
 * Base class for writing selenium tests. Provides the necessary enums and setup
 * initialization methods used by selenium tests. When writing a new Selenium 
 * Test Class, extend this class an implement the test() method. Put all testing
 * code in the test() method body. Calls can be made directly to the selenium 
 * instance variable which has proteced scope.
 * 
 * @author James Reed
 *
 */
public abstract class AbstractTurboPlanSeleniumTest {

	public AbstractTurboPlanSeleniumTest() {
		super();
	}

	protected enum BrowserSelection {
			Firefox ("*firefox");
			
			private String value;
			
			private BrowserSelection(String value) {
				this.value = value;
			}
			
			public String value() {
				return value;
			}
		}

	protected Selenium selenium;

	@BeforeClass
	public void setup() {
		selenium = new DefaultSelenium("localhost", 4444, BrowserSelection.Firefox.value(), "http://qalunaset.csc.calpoly.edu");
		selenium.start();
		selenium.open("/TurboPlan/TurboPlan.html");
	}

	@Test
	public abstract void test() throws Exception;
}