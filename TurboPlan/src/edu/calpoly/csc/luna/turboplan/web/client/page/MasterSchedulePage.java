package edu.calpoly.csc.luna.turboplan.web.client.page;

import com.allen_sauer.gwt.log.client.Log;
import edu.calpoly.csc.luna.turboplan.web.client.widget.MasterScheduleView;

public class MasterSchedulePage extends BasePage {
	private static final String SIMPLE_CLASSNAME = "MasterSchedulePage";

	/** (non-Javadoc)
	 * @see edu.calpoly.csc.luna.turboplan.web.client.page.BasePage#construct()
	 */
	public void construct() {
		Log.info("Constructing " + SIMPLE_CLASSNAME);
		
		add((content = new MasterScheduleView()));

		Log.debug("Done constructing " + SIMPLE_CLASSNAME);
	}
}
