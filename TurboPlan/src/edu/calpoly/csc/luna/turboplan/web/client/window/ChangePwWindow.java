package edu.calpoly.csc.luna.turboplan.web.client.window;

import com.gwtext.client.widgets.Window;

import edu.calpoly.csc.luna.turboplan.web.client.TurboPlan;
import edu.calpoly.csc.luna.turboplan.web.client.widget.ChangePwPanel;

public class ChangePwWindow extends Window {
	public ChangePwWindow() {
		super();
		this.setTitle("Change Password");
		this.add(new ChangePwPanel());
		
	    this.setCloseAction(Window.HIDE);
	    this.setWidth(TurboPlan.STANDARD_PAGE_WIDTH / 2);
	}
}
