package edu.calpoly.csc.luna.turboplan.web.client.window;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.core.Position;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.MessageBox;
import com.gwtext.client.widgets.Window;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;

import edu.calpoly.csc.luna.turboplan.web.client.TurboPlan;
import edu.calpoly.csc.luna.turboplan.web.client.service.ServiceManager;
import edu.calpoly.csc.luna.turboplan.web.client.widget.ScheduleProfilePanel;
import edu.calpoly.csc.luna.turboplan.web.client.widget.TaskGrid;


/**
 * Creates a schedule generating options window
 * @author Bradley Barbee
 */
public class ScheduleOptionsWindow extends Window {
    private final int thisWidth = TurboPlan.STANDARD_PAGE_WIDTH;
    private final int thisHeight = 400;
    private final ScheduleProfilePanel sPanel;
    private Button genOptBtn = null;
    private Button cancelBtn = null;
    private TaskGrid grid = null;

    /**
     * Get the grid associated with this panel.
     * @return The associated grid.
     */
    public TaskGrid getGrid() {
		return grid;
	}


	/**
	 * Set the grid associated with this window.
	 * @param grid The grid to associate with the grid.
	 */
	public void setGrid(TaskGrid grid) {
		this.grid = grid;
	}


	/**
	 * Constructs the task window with the given title and task grid
     * @param title The title to assign the window
	 */
	public ScheduleOptionsWindow(String title) {
        super();
        this.setTitle(title);
        this.sPanel = new ScheduleProfilePanel();
        this.sPanel.setAutoScroll(true);
        this.setupAttributes();
        this.setupButtons();
    }    
    
    
    /**
     * Constructs the task window with the given title and task grid
     * @param title The title to assign the window
     * @param activeGrid The task grid to give the window
     */
    public ScheduleOptionsWindow(String title, TaskGrid activeGrid) {
        super();
        this.setTitle(title);
        this.sPanel = new ScheduleProfilePanel();
        this.sPanel.setAutoScroll(true);
        this.setupAttributes();
        this.setGrid(activeGrid);
        this.setupButtons();
    }
    
    /**
     * Constructs a task window with given buttons and title
     * @param title The title to assign the window
     * @param buttonBar The button bar to assign the window
     */
    public ScheduleOptionsWindow(String title, Button[] buttonBar) {
        super();
        this.setTitle(title);
        this.sPanel = new ScheduleProfilePanel();
        this.sPanel.setAutoScroll(true);
        this.setupAttributes();
        this.setButtons(buttonBar);
    }
    
    
    /**
     * Sets up the attributes for the panel
     */
    private void setupAttributes() {
        this.sPanel.setWidth("97%");
        this.sPanel.setHeight("90%");
        
        this.add(sPanel);
       
        this.setCloseAction(Window.HIDE);
        this.setWidth(thisWidth);
        this.setHeight(thisHeight);
        this.setAutoScroll(true);
    }
        
    /**
	 * Inner button listener for the Generate w/ Options Button
	 * @author Bradley Barbee
	 */
	public final class GenOptButtonListener extends ButtonListenerAdapter {
		private final ScheduleOptionsWindow win;
		private final AsyncCallback<Object> callback;

		/**
		 * Constructs a new listener with the provided window and callback
		 * @param win
		 * @param asyncCallback
		 */
		public GenOptButtonListener(ScheduleOptionsWindow win, AsyncCallback<Object> asyncCallback) {
			this.win = win;
			this.callback = asyncCallback;
		}

		/**
		 * Action to occur if the specified button is clicked. For save, will make call to save a task to the database
		 * 
		 * @param button Button to click upon
		 * @param e Event to call upon
		 */
		public void onClick(Button button, EventObject e) {
            
            Log.trace("Generating Schedule with Options: " +
            		ScheduleProfilePanel.getMinDate() + ", " +
            		ScheduleProfilePanel.getMaxDate() + ", " +
            		ScheduleProfilePanel.getDirections() + ", " +
            		ScheduleProfilePanel.getRandom() + ", " + 
                	ScheduleProfilePanel.getPriority() + ", " +
                	ScheduleProfilePanel.getShortest() + ", " +
                	ScheduleProfilePanel.getLongest() + ", " +
                	ScheduleProfilePanel.getStart() + ", " +
                	ScheduleProfilePanel.getDue() + ", " +
                	ScheduleProfilePanel.getReverse());

            ServiceManager.getManageTasksServiceAsync().generateSchedule(TurboPlan.loggedInUser.getCompany().getCompanyID(),
            	TurboPlan.loggedInUser.getUserID(),
            	ScheduleProfilePanel.getMinDate(), 
            	ScheduleProfilePanel.getMaxDate(), 
            	ScheduleProfilePanel.getDirections(), 
            	ScheduleProfilePanel.getRandom(), 
            	ScheduleProfilePanel.getPriority(),
            	ScheduleProfilePanel.getShortest(),
            	ScheduleProfilePanel.getLongest(),
            	ScheduleProfilePanel.getStart(),
            	ScheduleProfilePanel.getDue(),
            	ScheduleProfilePanel.getReverse(),
                new AsyncCallback<Object>(){

                    public void onFailure(Throwable caught) {
                        Log.error(caught.getMessage());
                        grid.getStore().removeAll();
                        grid.getStore().commitChanges();
                        grid.loadTaskData();
                        grid.getStore().commitChanges();
                        MessageBox.alert(caught.getMessage());
                    }

                    public void onSuccess(Object result) {
                        Log.trace("Done Generating Schedule");
                        
                        grid.getStore().removeAll();
                        grid.getStore().commitChanges();
                        grid.loadTaskData();
                        grid.getStore().commitChanges();
                        
                        Log.trace("Schedule Generated w/ Options");
                        MessageBox.alert("Schedule Generated with Selected Options");
                        
            			getScheduleProfilePanel().clearScheduleProfile();            
                        win.close();
                    }
                });

		}
	}
	
    /**
     * Setup this window's Top Tool Bar
     */
    private void setupButtons() {
    	
        genOptBtn = new Button("Generate Schedule");
        cancelBtn = new Button("Cancel");
        
        this.setButtonAlign(Position.RIGHT);
        this.setButtons(new Button[] { genOptBtn, cancelBtn } );
        
        
        final ScheduleOptionsWindow win = this;

        genOptBtn.addListener(new GenOptButtonListener(win, new AsyncCallback<Object>() {
            /**
             * Handles if add new user call fails on server
             */
            public void onFailure(Throwable caught) {
            }
            
            /**
             * Handles if add user is successful on server. Clear grid and refresh employee data.
             */
            public void onSuccess(Object result) {

            }
		}));
        
        cancelBtn.addListener(new ButtonListenerAdapter() {
            public void onClick(Button button, EventObject e) {
                close();
            } 
        });
    }
    

    /**
     * Gets the task profile panel
     * @return The task profile panel
     */
    public ScheduleProfilePanel getScheduleProfilePanel() {
        Log.trace("Returning ScheduleProfilePanel");
        return sPanel;
    } 
}



