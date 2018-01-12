/**
 * 
 */
package edu.calpoly.csc.luna.turboplan.web.client.page;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Widget;

import edu.calpoly.csc.luna.turboplan.web.client.TurboPlan;

public enum TurboPlanPage {
	MasterSchedule(new MasterSchedulePage(), "Master<br/>Schedule"),
	MySchedule(new MySchedulePage(), "My<br/>Schedule"),
	ManageSchedule(new ManageSchedulePage(), "Manage<br/>Schedule"),
	EmployeesViewed(new EmployeesViewedPage(), "Employees<br/>Viewed"),
	ManageTasks(new ManageTasksPage(), "Manage<br/>Tasks"),
	ManageEmployees(new ManageEmployeesPage(), "Manage<br/>Employees"),
	Preferences(new PreferencesPage(), "Set<br/>Availability"),
	AdminPreferences(new AdminPreferencesPage(), "Admin<br/>Preferences");

	private static final String LINK_BUTTON_HEIGHT = "60px";
	private BasePage page;
	private String pageName;
	private Button linkButton;

	TurboPlanPage(BasePage bpage, String str) {
		page = bpage;
		pageName = str;
		
		linkButton = new Button(pageName);
//		linkButton.setWidth("");
		linkButton.setHeight(LINK_BUTTON_HEIGHT);
		
		final TurboPlanPage thisPage = this;
		linkButton.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				TurboPlan.swapToPage(thisPage);
			}
		});
		
		bpage.hide();
	}

	public BasePage getPage() {
		return page;
	}
	
	public Button getLinkButton() {
		return linkButton;
	}

	public String getPageProperName() {
		return pageName;
	}

}
