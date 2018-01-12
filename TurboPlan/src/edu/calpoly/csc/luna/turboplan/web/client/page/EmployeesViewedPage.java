package edu.calpoly.csc.luna.turboplan.web.client.page;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.ui.Image;

public class EmployeesViewedPage extends BasePage {
	private static final String SIMPLE_CLASSNAME = "EmployeesViewedPage";

	public void construct() {
		Log.info("Constructing " + SIMPLE_CLASSNAME);
		add((content = new Image("images/viewed.png")));
		Log.debug("Done constructing " + SIMPLE_CLASSNAME);
	}

}
