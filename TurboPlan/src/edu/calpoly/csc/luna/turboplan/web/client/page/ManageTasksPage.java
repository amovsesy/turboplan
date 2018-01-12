package edu.calpoly.csc.luna.turboplan.web.client.page;

import com.allen_sauer.gwt.log.client.Log;

import edu.calpoly.csc.luna.turboplan.web.client.widget.TaskPanel;

public class ManageTasksPage extends BasePage {
	private static final String SIMPLE_CLASSNAME = "ManageTasksPage";
	
	public void construct() {
		Log.info("Constructing " + SIMPLE_CLASSNAME);
//		add((content = new TaskGrid()));
	    add((content = new TaskPanel()));
		Log.debug("Done constructing " + SIMPLE_CLASSNAME);
	}

}
