package edu.calpoly.csc.luna.turboplan.web.client.window;

import com.gwtext.client.core.Position;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.Window;

import edu.calpoly.csc.luna.turboplan.web.client.TurboPlan;
import edu.calpoly.csc.luna.turboplan.web.client.model.GwtUserProfile;
import edu.calpoly.csc.luna.turboplan.web.client.widget.EmployeeProfilePanel;

/**
 * Employee Profile Window is the container for an employee profile panel
 * 
 * @author Ming Liu
 * @author Stephanie Long
 */
public class EmployeeProfileWindow extends Window {
	private final EmployeeProfilePanel ePanel;

	
	/**
	 * Constructor of a EmployeeProfileWindow. Create panel add title and button bar.
	 * 
	 * @param title The title specified for the employee profile window
	 * @param buttonBar The button bar specified for the employee profile window
	 */
	public EmployeeProfileWindow(String title, Button[] buttonBar) {
		ePanel = new EmployeeProfilePanel();
		ePanel.setWidth("100%");
		ePanel.setHeight("100%");
		
		add(ePanel);
		
		if (buttonBar != null) {
//		setBottomToolbar(buttonBar);
			setButtonAlign(Position.RIGHT);
			
			for (Button button : buttonBar) {
				addButton(button);
			}
		}
		
		setWidth(TurboPlan.STANDARD_PAGE_WIDTH);
		setTitle(title);
	}
	
	/**
	 * Constructor of a EmployeeProfileWindow. Create panel, set fields to reflect user profile information and button bar.
	 * 
	 * @param usr The user profile specified to create the employee profile window with
	 * @param buttonBar The button bar specified for the employee profile window
	 */
	private EmployeeProfileWindow(GwtUserProfile usr, Button[] buttonBar) {
		this(usr.getLastName() + ", " + usr.getFirstName(), buttonBar);
		
		ePanel.setUserProfile(usr);
	}
	
	/**
	 * Returns the employee profile panel associated with the employee profile window.
	 * 
	 * @return The employee profile panel associated with the employee profile window.
	 */
	public EmployeeProfilePanel getEmployeeProfilePanel() {
		return ePanel;
	}
}
