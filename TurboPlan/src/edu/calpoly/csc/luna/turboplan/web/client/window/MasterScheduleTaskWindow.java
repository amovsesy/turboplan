package edu.calpoly.csc.luna.turboplan.web.client.window;

import java.util.Date;

import com.gwtext.client.core.EventObject;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.Window;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;

import edu.calpoly.csc.luna.turboplan.web.client.TurboPlan;
import edu.calpoly.csc.luna.turboplan.web.client.model.GwtUser;
import edu.calpoly.csc.luna.turboplan.web.client.widget.MasterScheduleTaskGrid;


/**
 * Creates a task window
 * @author Paul DeLeon
 */
public class MasterScheduleTaskWindow extends Window {
	private MasterScheduleTaskGrid masterSchedGrid;
//	private GwtUser ownerOfTasks;
//	private Date dateOfTasks;
	
	public MasterScheduleTaskWindow(GwtUser user, Date date) {
		super();
		masterSchedGrid = new MasterScheduleTaskGrid(user, date);
//		ownerOfTasks = user;
//		dateOfTasks = date;
		
		initializeWindow();
		addButtons();
	}
	
	
	public MasterScheduleTaskWindow(String title, GwtUser user, Date date) {
		super();
		this.setTitle(title);
		masterSchedGrid = new MasterScheduleTaskGrid(user, date);
//		ownerOfTasks = user;
//		dateOfTasks = date;
		
		initializeWindow();
		addButtons();
	}
	
	private void initializeWindow() {
		this.add(masterSchedGrid);
		
	    this.setCloseAction(Window.HIDE);
	    this.setWidth(TurboPlan.STANDARD_PAGE_WIDTH);
	    
	}
	
	private void addButtons() {
		Button closeBtn = new Button("Close");
		
		closeBtn.addListener(new ButtonListenerAdapter() {
            public void onClick(Button button, EventObject e) {
                close();
            } 
        });
		
		this.addButton(closeBtn);
	}
	
	public MasterScheduleTaskGrid getMasterSchedGrid() {
		return masterSchedGrid;
	}
}
