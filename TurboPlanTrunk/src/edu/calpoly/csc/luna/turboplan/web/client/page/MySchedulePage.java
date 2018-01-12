package edu.calpoly.csc.luna.turboplan.web.client.page;

import java.util.Set;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Image;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.core.Position;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.TabPanel;
import com.gwtext.client.widgets.Toolbar;
import com.gwtext.client.widgets.ToolbarButton;
import com.gwtext.client.widgets.ToolbarFill;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.grid.GridPanel;
import com.gwtext.client.widgets.layout.FitLayout;

import edu.calpoly.csc.luna.turboplan.web.client.TurboPlan;
import edu.calpoly.csc.luna.turboplan.web.client.model.GwtTask;
import edu.calpoly.csc.luna.turboplan.web.client.service.ServiceManager;
import edu.calpoly.csc.luna.turboplan.web.client.widget.ScheduleDayView;
import edu.calpoly.csc.luna.turboplan.web.client.widget.ScheduleGrid;
import edu.calpoly.csc.luna.turboplan.web.client.widget.ScheduleWeekView;

public class MySchedulePage extends BasePage {
	private static final String SIMPLE_CLASSNAME = "MySchedulePage";

	public void construct() {
		Log.info("Constructing " + SIMPLE_CLASSNAME);

		final TabPanel tPanel = new TabPanel();
		// tPanel.setLayout(new FitLayout());

		tPanel.setHeight(360);
		tPanel.setWidth(TurboPlan.STANDARD_PAGE_WIDTH - 20);
		tPanel.setTabPosition(Position.BOTTOM);

		ServiceManager.getManageTasksServiceAsync().getTasksForUserById(TurboPlan.loggedInUser.getUserID(),
				new AsyncCallback<Set<GwtTask>>() {

					public void onFailure(Throwable caught) {
						System.err.println("Failed getting all tasks");
					}

					public void onSuccess(Set<GwtTask> result) {
						ScheduleGrid.res = result;
						tPanel.add(buildGridTab("Day View", new ScheduleDayView()));
						tPanel.add(buildGridTab("Week View", new ScheduleWeekView()));
						// tPanel.add(buildTab("Month View",
						// "images/man_schedule_m.png"));
						tPanel.setBodyBorder(false);
						tPanel.setTabPosition(Position.BOTTOM);

						Panel cPanel = new Panel();
						cPanel.setFrame(true);
						cPanel.setLayout(new FitLayout());
						cPanel.setTitle("Calendar");
						cPanel.setButtonAlign(Position.RIGHT);
						cPanel.add(tPanel);

						add((content = cPanel));

						Log.trace("Done constructing MySchedulePage");
					}
				});
		Log.debug("Done constructing " + SIMPLE_CLASSNAME);
	}

	private Panel buildTab(String viewName, String imageUrl) {
		Panel tItem = new Panel(viewName);

		Image image = new Image(imageUrl);
		image.setWidth("780px");
		tItem.add(image);

		return tItem;
	}

	private Panel buildGridTab(String viewName, GridPanel gPanel) {
		Panel tItem = new Panel(viewName);
		tItem.add(gPanel);

		return tItem;
	}
}