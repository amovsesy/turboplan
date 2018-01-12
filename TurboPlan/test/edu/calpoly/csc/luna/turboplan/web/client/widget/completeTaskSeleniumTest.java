package edu.calpoly.csc.luna.turboplan.web.client.widget;

import com.thoughtworks.selenium.*;
import org.testng.annotations.Test;

import java.util.regex.Pattern;

import org.testng.annotations.Test;
/**
 * Tests completing a task
 * @author Stephanie Long
 */
public class completeTaskSeleniumTest extends AbstractTurboPlanSeleniumTest {
	protected static final String COMPLETE_TASKS = "xpath=//html/body/table/tbody/tr[3]/td/div/div/div/div[3]/div/div[2]/div[2]/div[4]/div[2]/div/div/div/div/div/div/div[2]/div/div/div[2]/div";
	protected static final String NEW_TASKS = 	   "xpath=//html/body/table/tbody/tr[3]/td/div/div/div/div[3]/div/div[2]/div[2]/div[4]/div[2]/div/div/div/div/div/div/div[2]/div/div[2]/div[2]/div";
										          
	protected static final String TASK_CONTENTS = "/table/tbody/tr/td";
	protected static final String TASK_CLOSE = "/div";
	protected static final int NAME = 1;
	protected static final int STATUS = 5;
	@Test
	public void test() throws Exception {
		selenium.type("ext-gen14", "djanzen");
		selenium.type("ext-gen16", "LunaSet");
		selenium.click("ext-gen39");
		Thread.sleep(1000);
		selenium.click("xpath=//html/body/table/tbody/tr[2]/td/table/tbody/tr/td[3]/button");
		Thread.sleep(1000);
		
		//sort by status
		selenium.click("xpath=//html/body/table/tbody/tr[3]/td/div/div/div/div[3]/div/div[2]/div/div/table/tbody/tr/td[3]/table/tbody/tr/td[2]/table/tbody/tr/td/button");
		selenium.click("xpath=//html/body/div/ul/li[4]/a");
		Thread.sleep(1000);
		                                            
	    String taskRow = getUncompletedTaskRow();
	    String taskName = selenium.getText(taskRow + TASK_CONTENTS + "[" + NAME + "]" + TASK_CLOSE);
		selenium.doubleClickAt(taskRow + TASK_CONTENTS + "[" + NAME + "]" + TASK_CLOSE, "");
		selenium.click("xpath=//html/body/div/div[2]/div[2]/div/div/div/div/div/table/tbody/tr/td/table/tbody/tr/td[2]/em/button");
		
		//make sure the form checks for time spent input 
		assert("Timespent required to complete a task".equals(selenium.getText("xpath=//html/body/div/div[2]/div/div/div/div/div/div[2]/span")));
		selenium.keyPressNative("" + java.awt.event.KeyEvent.VK_SPACE);//("xpath=//html/body/div/div[2]/div[2]/div/div/div/div/div/table/tbody/tr/td/table/tbody/tr/td[2]/em/button");
		//selenium.select("xpath=//html/body/div/div[2]/div[2]/div/div/div/div/div/table/tbody/tr/td/table/tbody/tr/td[2]/em/button","");
		
		//Currently accepts negative values, this should fail
		/**
		selenium.type("xpath=/", "-1");
		selenium.click("xpath=//html/body/div/div[2]/div[2]/div/div/div/div/div/table/tbody/tr/td/table/tbody/tr/td[2]/em/button");
		assert(selenium.isElementPresent("xpath=//html/body/div/div[2]/div[2]/div/div/div/div/div/table/tbody/tr/td/table/tbody/tr/td[2]/em/button"));
		assert("Timespent required to complete a task".equals(selenium.getText("xpath=//html/body/div/div[2]/div/div/div/div/div/div[2]/span")));
		**/
		
		//Enter a valid value
		//selenium.keyPress("xpath=//html/body/div[34]/div[2]/div/div/div/div/div/div[2]/div/div/div/div/div[3]/div[2]/div/div/div/div/div/div[2]/div/div/div[2]/div/input", "\\8");
		//selenium.keyPress("xpath=//html/body/div[34]/div[2]/div/div/div/div/div/div[2]/div/div/div/div/div[3]/div[2]/div/div/div/div/div/div[2]/div/div/div[2]/div/input", "\\8");
		selenium.type("xpath=//html/body/div/div[2]/div/div/div/div/div/div[2]/div/div/div/div/div[3]/div[2]/div/div/div/div/div/div[2]/div/div/div[2]/div/input", "1");
		selenium.click("xpath=//html/body/div/div[2]/div[2]/div/div/div/div/div/table/tbody/tr/td/table/tbody/tr/td[2]/em/button");
		Thread.sleep(1000);
		
		//confirm that the tasks status was changed
		taskRow = findTaskRow(taskName);
		assert("Complete".equals(selenium.getText(taskRow + TASK_CONTENTS + "[" + STATUS + "]" + TASK_CLOSE)));
		/**
		Thread.sleep(1000);
		selenium.doubleClickAt("xpath=//html/body/table/tbody/tr[3]/td/div/div/div/div[2]/div/div[2]/div[2]/div/div[2]/div/div/div/div/div/div/div[2]/div/div[2]/div[2]/div/table/tbody/tr/td/div", "");
		Thread.sleep(1000);
		assert(("Fix Disposal").equals(selenium.getValue("xpath=//html/body/div/div[2]/div/div/div/div/div/div[2]/div/div/div/div/div/div[2]/div/div/div/input")));
		assert(("03/09/09").equals(selenium.getValue("xpath=//html/body/div/div[2]/div/div/div/div/div/div[2]/div/div/div/div/div/div[2]/div/div[2]/div/div/div/div/div/div/table/tbody/tr/td/div/div/div/div/div/div/div/div/div/input")));
		assert(("03/09/09").equals(selenium.getValue("xpath=//html/body/div/div[2]/div/div/div/div/div/div[2]/div/div/div/div/div/div[2]/div/div[2]/div/div/div/div[2]/div/div/table/tbody/tr/td/div/div/div/div/div/div/div/div/div/input")));
		assert(("9:30 AM").equals(selenium.getValue("xpath=./html/body/div/div[2]/div/div/div/div/div/div[2]/div/div/div/div/div/div[2]/div/div[2]/div/div/div/div/div/div/table/tbody/tr[2]/td/div/div/div/div/div/div/div/div/div/input")));
		assert(("11:30 AM").equals(selenium.getValue("xpath=//html/body/div/div[2]/div/div/div/div/div/div[2]/div/div/div/div/div/div[2]/div/div[2]/div/div/div/div[2]/div/div/table/tbody/tr[2]/td/div/div/div/div/div/div/div/div/div/input")));
		assert(("Normal").equals(selenium.getValue("xpath=//html/body/div/div[2]/div/div/div/div/div/div[2]/div/div/div/div/div/div[2]/div/div[3]/div/div/div/div/div/div/div/div/div/input[2]")));
		assert(("Disposal in kitchen").equals(selenium.getValue("xpath=//html/body/div/div[2]/div/div/div/div/div/div[2]/div/div/div/div/div/div[2]/div/div[4]/div/textarea")));
		assert(("Johnny").equals(selenium.getValue("xpath=/html/body/div/div[2]/div/div/div/div/div/div[2]/div/div/div/div/div[2]/div[2]/div/div/div/div/div/div/div/input")));
		assert(("Johnson").equals(selenium.getValue("xpath=//html/body/div/div[2]/div/div/div/div/div/div[2]/div/div/div/div/div[2]/div[2]/div/div/div/div/div/div[2]/div/input")));
		assert(("2223334444").equals(selenium.getValue("xpath=//html/body/div/div[2]/div/div/div/div/div/div[2]/div/div/div/div/div[2]/div[2]/div/div/div/div/div/div[3]/div/input")));
		assert(("2223334444").equals(selenium.getValue("xpath=//html/body/div/div[2]/div/div/div/div/div/div[2]/div/div/div/div/div[2]/div[2]/div/div/div/div/div/div[4]/div/input")));
		assert(("123 Main St.").equals(selenium.getValue("xpath=//html/body/div/div[2]/div/div/div/div/div/div[2]/div/div/div/div/div[2]/div[2]/div/div/div[2]/div/div/div/div/textarea")));
		assert(("Testtown").equals(selenium.getValue("xpath=//html/body/div/div[2]/div/div/div/div/div/div[2]/div/div/div/div/div[2]/div[2]/div/div/div[2]/div/div/div[2]/div/input")));
		assert(("California").equals(selenium.getValue("xpath=//html/body/div/div[2]/div/div/div/div/div/div[2]/div/div/div/div/div[2]/div[2]/div/div/div[2]/div/div/div[3]/div/div/input[2]")));
		assert(("22222").equals(selenium.getValue("xpath=//html/body/div/div[2]/div/div/div/div/div/div[2]/div/div/div/div/div[2]/div[2]/div/div/div[2]/div/div/div[4]/div/input")));
		assert(("4.50").equals(selenium.getValue("xpath=//html/body/div/div[2]/div/div/div/div/div/div[2]/div/div/div/div/div[3]/div[2]/div/div/div/div/div/div[2]/div/div/div/div/input")));
		assert(("Bring tools").equals( selenium.getValue("xpath=//html/body/div/div[2]/div/div/div/div/div/div[2]/div/div/div/div/div[3]/div[2]/div/div[2]/div/textarea")));
		assert(("Complete").equals(selenium.getValue("xpath=//html/body/div/div[2]/div/div/div/div/div/div[2]/div/div/div/div/div[3]/div[2]/div/div/div/div/div/div/div/div/div/div/div/input[2]")));
		**/
	}
	
	protected String getUncompletedTaskRow() {
		String row = COMPLETE_TASKS;
		int ndx = 0;
		for (ndx = 1; selenium.getText(row + TASK_CONTENTS + "[" + STATUS + "]" + TASK_CLOSE).equals("Complete"); ndx++)
			row = COMPLETE_TASKS + "[" + ndx + "]";
		
		return row;
	}
	
	protected String findTaskRow(String taskName) {
		String row = COMPLETE_TASKS;
		int ndx = 0;
		for (ndx = 1; !selenium.getText(row + TASK_CONTENTS + TASK_CLOSE).equals(taskName); ndx++)
			row = COMPLETE_TASKS + "[" + ndx + "]";
		
		return row;
	}
}
