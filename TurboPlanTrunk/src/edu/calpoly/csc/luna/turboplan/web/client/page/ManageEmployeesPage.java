package edu.calpoly.csc.luna.turboplan.web.client.page;

import com.allen_sauer.gwt.log.client.Log;

import edu.calpoly.csc.luna.turboplan.web.client.widget.EmployeeGrid;

/**
 * The Manage Employees Page. Contains the Employee Grid
 * @author Stephanie Long
 */
public class ManageEmployeesPage extends BasePage {
	private static final String SIMPLE_CLASSNAME = "ManageEmloyeesPage";

	/** (non-Javadoc)
	 * @see edu.calpoly.csc.luna.turboplan.web.client.page.BasePage#construct()
	 */
	public void construct() {
		Log.info("Constructing " + SIMPLE_CLASSNAME);
		
		add((content = new EmployeeGrid()));

		Log.debug("Done constructing " + SIMPLE_CLASSNAME);
	}

}
