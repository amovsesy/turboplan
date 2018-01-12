package edu.calpoly.csc.luna.turboplan.web.client.window;

import java.util.Date;
import java.util.Set;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.core.Position;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.MessageBox;
import com.gwtext.client.widgets.Window;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;

import edu.calpoly.csc.luna.turboplan.web.client.TurboPlan;
import edu.calpoly.csc.luna.turboplan.web.client.model.GwtTask;
import edu.calpoly.csc.luna.turboplan.web.client.model.GwtUser;
import edu.calpoly.csc.luna.turboplan.web.client.model.GwtTask.TaskStatus;
import edu.calpoly.csc.luna.turboplan.web.client.service.ServiceManager;
import edu.calpoly.csc.luna.turboplan.web.client.widget.TaskGrid;
import edu.calpoly.csc.luna.turboplan.web.client.widget.TaskProfilePanel;
import edu.calpoly.csc.luna.turboplan.web.client.window.TaskWindow.CompleteButtonListener;
import edu.calpoly.csc.luna.turboplan.web.client.window.TaskWindow.DeleteButtonListener;
import edu.calpoly.csc.luna.turboplan.web.client.window.TaskWindow.SaveButtonListener;
import edu.calpoly.csc.luna.turboplan.web.client.window.TaskWindow.UpdateButtonListener;

public class CalTaskWindow extends Window{
    private final int thisWidth = TurboPlan.STANDARD_PAGE_WIDTH;
    private final int thisHeight = 400;
    private final TaskProfilePanel tPanel;
    private Button completeBtn = null;
    private Button saveBtn = null;
    private Button cancelBtn = null;
    private Button updateBtn = null;
    private GwtTask task = null;

/**
	 * Constructs the task window with the given title and task grid
     * @param title The title to assign the window
	 */
	public CalTaskWindow(String title) {
        super();
        this.setTitle(title);
        this.tPanel = new TaskProfilePanel();
        this.setupAttributes();
        this.setupButtons();
    }    
    
    
    /**
     * Constructs the task window with the given title and task grid
     * @param title The title to assign the window
     * @param activeGrid The task grid to give the window
     */
    public CalTaskWindow(String title, GwtTask curTask) {
        super();
        this.setTitle(title);
        this.tPanel = new TaskProfilePanel();
        this.setupAttributes();
        this.task = curTask;
        this.setupButtons();
    }
    
    /**
     * Constructs a task window with given buttons and title
     * @param title The title to assign the window
     * @param buttonBar The button bar to assign the window
     */
    public CalTaskWindow(String title, Button[] buttonBar) {
        super();
        this.setTitle(title);
        this.tPanel = new TaskProfilePanel();
        this.setupAttributes();
        this.setButtons(buttonBar);
    }
    
    
    /**
     * Sets up the attributes for the panel
     */
    private void setupAttributes() {
        this.tPanel.setWidth("100%");
        this.tPanel.setHeight("100%");
        
        this.add(tPanel);
       
        this.setCloseAction(Window.HIDE);
        this.setWidth(thisWidth);
    }
    
    /**
	 * Inner button listener for the Save Button
	 * @author Stephanie Long
	 * @author Paul De Leon
	 */
    public final class SaveButtonListener extends ButtonListenerAdapter {
        private final CalTaskWindow win;
        private final AsyncCallback<Object> callback;

        /**
         * Constructs a new listener with the provided window and callback
         * @param win
         * @param asyncCallback
         */
        public SaveButtonListener(CalTaskWindow win, AsyncCallback<Object> asyncCallback) {
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
            Log.trace("Saving New Task");
            MessageBox.alert("Saved New Task");
            

            GwtTask task = getTaskProfilePanel().getGwtTask();
            task.setCreatedDate(new Date());
            ////task.setEstimatedTime(new Double(60));
            Log.trace("before service call");
            task.setCompany(TurboPlan.loggedInUser.getCompany());
            ServiceManager.getManageTasksServiceAsync().addTask(task, callback);
            Log.trace("after service call");
            getTaskProfilePanel().clearTaskProfile();
            win.close();
        }
    }    
    
    
    /**
	 * Inner button listener for the Save Button
	 * @author Stephanie Long
	 * @author Paul De Leon
	 */
	public final class UpdateButtonListener extends ButtonListenerAdapter {
		private final CalTaskWindow win;
		private final AsyncCallback<Object> callback;

		/**
		 * Constructs a new listener with the provided window and callback
		 * @param win
		 * @param asyncCallback
		 */
		public UpdateButtonListener(CalTaskWindow win, AsyncCallback<Object> asyncCallback) {
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
		    Log.trace("Saving/Updating Task from  update");
		    Log.trace(" get task " + task.getTitle() + " from grid");
		//	GwtTask task = grid.getTaskById(grid.getSelectionModel().getSelected().getAsString("taskId"));
			Log.trace("task = " + task.getDescription());
			Date tempCrDate;
			Set<GwtUser> tempOwners = task.getUsers();
			if (task.getCreatedDate()!=null) {
				tempCrDate = task.getCreatedDate();
			} else {
			 tempCrDate = new Date();
			}
			Date tempDuDate = task.getDueDate();
			//Double tempEst = task.getEstimatedTime();
			Long tempId = task.getTaskID();
			task = win.getTaskProfilePanel().getGwtTask();
			task.setCreatedDate(tempCrDate);
			task.setDueDate(tempDuDate);
			task.setUsers(tempOwners);
			task.setCompany(TurboPlan.loggedInUser.getCompany());
			//task.setEstimatedTime(tempEst);
			
			task.setTaskID(tempId);
			//task.setRequiredSkill(new SortedSet<GwtSkill>());
            
            Log.trace("task null = " + task.equals(null));
			ServiceManager.getManageTasksServiceAsync().updateTask(task, callback);
			win.close();
			getTaskProfilePanel().clearTaskProfile();

		}
	}
	
	/**
	 * Inner button listener for the Complete Button
	 * @author Stephanie Long
	 * @author Paul De Leon
	 */
	public final class CompleteButtonListener extends ButtonListenerAdapter {
		private final CalTaskWindow win;
		private final AsyncCallback<Object> callback;

		/**
		 * Constructs a new listener with the provided window and callback
		 * @param win
		 * @param asyncCallback
		 */
		public CompleteButtonListener(CalTaskWindow win, AsyncCallback<Object> asyncCallback) {
			this.win = win;
			this.callback = asyncCallback;
		}

		/**
		 * Action to occur if the specified button is clicked. For complete, will make call to complete a task to the database
		 * 
		 * @param button Button to click upon
		 * @param e Event to call upon
		 */
		public void onClick(Button button, EventObject e) {
		    Log.trace("Completing Task");
		    
			//GwtTask task = grid.getTaskById(grid.getSelectionModel().getSelected().getAsString("taskId"));

			Log.trace("task = " + task.getDescription());
			Date tempCrDate;
			Set<GwtUser> tempOwners = task.getUsers();
			if (task.getCreatedDate()!=null) {
				tempCrDate = task.getCreatedDate();
			} else {
			 tempCrDate = new Date();
			}
			Date tempDuDate = task.getDueDate();
			//Double tempEst = task.getEstimatedTime();
			Long tempId = task.getTaskID();
			task = win.getTaskProfilePanel().getGwtTask();
			task.setCreatedDate(tempCrDate);
			task.setDueDate(tempDuDate);
			task.setTaskID(tempId);
			task.setCompany(TurboPlan.loggedInUser.getCompany());
			//task.setEstimatedTime(tempEst);
			task.setUsers(tempOwners);
			//task.setRequiredSkill(new SortedSet<GwtSkill>());
			task.setStatus(TaskStatus.Complete);
			Log.trace("task = null: " + (task==null));
			ServiceManager.getManageTasksServiceAsync().updateTask(task, callback);

			Log.trace("competle set");

			win.close();
			getTaskProfilePanel().clearTaskProfile();
			Log.trace("done complete");
		}
	}
		
    /**
     * Changes the label of the Save button to read Update Task and
     * unhide the Mark as Complete button.
     */
    public void changeSaveBtnLabel() {
    	saveBtn.setVisible(false);
    	updateBtn.setVisible(true);
    	completeBtn.setVisible(true);
    }
    
    /**
     * Setup this window's Top Tool Bar
     */
    private void setupButtons() {
        completeBtn = new Button("Mark As Complete");
        completeBtn.setVisible(false);
        saveBtn = new Button("Save");
        cancelBtn = new Button("Cancel");
        updateBtn = new Button("Update Task");
        updateBtn.setVisible(false);
        
        this.setButtonAlign(Position.RIGHT);
        this.setButtons(new Button[] { completeBtn, saveBtn, cancelBtn, updateBtn } );
        
        
        final CalTaskWindow win = this;
        saveBtn.addListener(new SaveButtonListener(win, new AsyncCallback<Object>() {
            /**
             * Handles if add new user call fails on server
             */
            public void onFailure(Throwable caught) {
                MessageBox.alert(caught.getMessage());
                win.close();
            }
            
            /**
             * Handles if add user is successful on server. Clear grid and refresh employee data.
             */
            public void onSuccess(Object result) {
                //MessageBox.alert("Saved New Employee", "Changes saved successfully. Employee created.");
                win.close();
            }
		}));
        
        updateBtn.addListener(new UpdateButtonListener(win, new AsyncCallback<Object>() {
            /**
             * Handles if add new user call fails on server
             */
            public void onFailure(Throwable caught) {
                MessageBox.alert(caught.getMessage());
                win.close();
            }
            
            /**
             * Handles if add user is successful on server. Clear grid and refresh employee data.
             */
            public void onSuccess(Object result) {
                //MessageBox.alert("Saved New Employee", "Changes saved successfully. Employee created.");
                Log.info("Removed all");
                Log.info("commit all");
                Log.info("load");
                Log.info("commit afain");
                win.close();
            }
		}));
        completeBtn.addListener(new CompleteButtonListener(win, new AsyncCallback<Object>() {
            /**
             * Handles if add new user call fails on server
             */
            public void onFailure(Throwable caught) {
                MessageBox.alert(caught.getMessage());
                win.close();
            }
            
            /**
             * Handles if add user is successful on server. Clear grid and refresh employee data.
             */
            public void onSuccess(Object result) {
                //MessageBox.alert("Saved New Employee", "Changes saved successfully. Employee created.");
                win.close();
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
    public TaskProfilePanel getTaskProfilePanel() {
        Log.trace("Returning TaskProfilePanel");
        return tPanel;
    } 
}



