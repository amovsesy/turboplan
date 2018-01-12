package edu.calpoly.csc.luna.turboplan.web.client.widget;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.data.FieldDef;
import com.gwtext.client.data.RecordDef;
import com.gwtext.client.data.Store;
import com.gwtext.client.data.StringFieldDef;
import com.gwtext.client.widgets.Window;
import com.gwtext.client.widgets.grid.ColumnConfig;
import com.gwtext.client.widgets.grid.ColumnModel;
import com.gwtext.client.widgets.grid.GridPanel;
import com.gwtext.client.widgets.grid.event.GridRowListenerAdapter;

import edu.calpoly.csc.luna.turboplan.web.client.TurboPlan;
import edu.calpoly.csc.luna.turboplan.web.client.model.GwtTask;
import edu.calpoly.csc.luna.turboplan.web.client.model.GwtUser;
import edu.calpoly.csc.luna.turboplan.web.client.service.ServiceManager;
import edu.calpoly.csc.luna.turboplan.web.client.window.TaskWindow;

public class MasterScheduleTaskGrid extends TaskGrid { //extends GridPanel {
//	private static final String PAGE_TITLE = "Task List";
	private static final String SIMPLE_CLASSNAME = "MasterScheduleTaskGrid";
	private final DateTimeFormat timeFormatter = DateTimeFormat.getFormat("h:mm a");
	
	private static final String TASKID_RECORDDEF	 = "masterTaskID";
	private static final String TASKTITLE_RECORDDEF	 = "taskTitle";
	private static final String STARTTIME_RECORDDEF  = "startTime";
	private static final String ENDTIME_RECORDDEF 	 = "endTime";
	private static final String PRIORITY_RECORDDEF 	 = "priority";
	private static final String STATUS_RECORDDEF 	 = "status";
	private static final String CUSTOMER_RECORDDEF   = "customer";
	private static final String OWNER_RECORDDEF 	 = "owner";
	
	private static final double GRID_WIDTH = TurboPlan.STANDARD_PAGE_WIDTH;
	private static final int TASKTITLE_COLWIDTH 	 = (int) (GRID_WIDTH * .3);
	private static final int TIME_COLWIDTH 			 = (int) (GRID_WIDTH * .1);
	private static final int PRIORITY_COLWIDTH		 = (int) (GRID_WIDTH * .1);
	private static final int STATUS_COLWIDTH 		 = (int) (GRID_WIDTH * .1);
	private static final int PERSONNAME_COLWIDTH 	 = (int) (GRID_WIDTH * .15);
	
	private List<GwtTask> masterTaskList;
	private RecordDef recordDef;
	private ColumnConfig[] columns;
	
	private GwtUser ownerOfTasks;
	private Date dateOfTasks;
	
	private final MasterScheduleTaskGrid myGrid = this;
	
	public MasterScheduleTaskGrid(GwtUser user, Date date) {
		super();
		ownerOfTasks = user;
		dateOfTasks = date;
		
		initializeMasterTaskGrid();
		setupGrid();
		setupListeners();
	}
	
	public MasterScheduleTaskGrid(String title, GwtUser user, Date date) {
		super();
		this.setTitle(title);
		ownerOfTasks = user;
		dateOfTasks = date;
		
		initializeMasterTaskGrid();
		setupGrid();
		setupListeners();
	}
	
	private void initializeMasterTaskGrid() {
		masterTaskList = new ArrayList<GwtTask>();
	}
	
	private void setupGrid() {
        recordDef = new RecordDef(  
                new FieldDef[]{  
                		new StringFieldDef(TASKID_RECORDDEF),
                        new StringFieldDef(TASKTITLE_RECORDDEF),
                        new StringFieldDef(STARTTIME_RECORDDEF),
                        new StringFieldDef(ENDTIME_RECORDDEF),
                        new StringFieldDef(PRIORITY_RECORDDEF),
                        new StringFieldDef(STATUS_RECORDDEF),
                        new StringFieldDef(CUSTOMER_RECORDDEF),
                        new StringFieldDef(OWNER_RECORDDEF)
                }  
        );  
        
  
        
        Store store = new Store(recordDef);
        
//        store.load();  
        this.setStore(store);  
  
        
        columns = new ColumnConfig[]{  
                //column ID is company which is later used in setAutoExpandColumn  
                new ColumnConfig("Task", TASKTITLE_RECORDDEF, TASKTITLE_COLWIDTH, true, null, TASKTITLE_RECORDDEF),  
                new ColumnConfig("Start Time", STARTTIME_RECORDDEF, TIME_COLWIDTH),  
                new ColumnConfig("End Time", ENDTIME_RECORDDEF, TIME_COLWIDTH),
                new ColumnConfig("Priority", PRIORITY_RECORDDEF, PRIORITY_COLWIDTH),  
                new ColumnConfig("Status", STATUS_RECORDDEF, STATUS_COLWIDTH),  
                new ColumnConfig("Customer", CUSTOMER_RECORDDEF, PERSONNAME_COLWIDTH),
                new ColumnConfig("Owner", OWNER_RECORDDEF, PERSONNAME_COLWIDTH),
        };  
  
        ColumnModel columnModel = new ColumnModel(columns);  
        this.setColumnModel(columnModel);  
  
        this.setFrame(true);  
        this.setStripeRows(true);  
        this.setAutoExpandColumn(TASKTITLE_RECORDDEF);  
  
        this.setHeight(350);  
//        this.setWidth(600);
        this.setWidth(TurboPlan.STANDARD_PAGE_WIDTH);
        
        loadTaskData();
	}
	
	
    /**
     * Add listeners to this Grid.
     * @author Paul De Leon
     */
    private void setupListeners() {
        this.addGridRowListener(new GridRowListenerAdapter() {
            public void onRowDblClick(GridPanel grid, int rowIndex, EventObject e) {
            	Log.trace("Double-Clicked task");
            	
                final TaskWindow taskWin = new TaskWindow("Selected Task Profile", myGrid, true);
                taskWin.changeSaveBtnLabel();
                taskWin.setCloseAction(Window.HIDE); 
                taskWin.setPlain(true);
                taskWin.setModal(true);
                taskWin.setVisible(true);               
                
                 
                Log.trace("before task " + grid.getSelectionModel().getSelected().getAsString(TASKID_RECORDDEF));
                final GwtTask task = myGrid.getMasterTaskById(grid.getSelectionModel().getSelected().getAsString(TASKID_RECORDDEF));
                
                Log.trace("after task");
    
                
                Log.trace("before setTaskProfile");
                taskWin.getTaskProfilePanel().setTaskProfile(task);
                Log.trace("after setTaskProfile");
                
                taskWin.show();
                Log.trace("showed windw");
            }
        });
    }
    
    /**
     * Get Task in Grid By ID.
     * @param title
     * @return
     * @author Paul De Leon
     */
    public GwtTask getMasterTaskById(String Id) {
        Log.trace(" Id = " + Id);
        for (GwtTask task : masterTaskList) {
            Log.trace(task.getTaskID().toString() + " ?= " + Id);
            if (task.getTaskID().toString().equals(Id)) {
               Log.trace("got task with title: " + task.getTitle()); 
               return task;
            }
        }

        return null;
    }
	
	
    /**
     * Load the task data for the user on the date specified.
     * @author Paul De Leon
     */
    public void loadTaskData() {
        Log.trace("Getting TaskData..."); 
        
        /**
         * Gets tasks from database on specified date
         */
        ServiceManager.getManageTasksServiceAsync().getTasksForUserOnDate(ownerOfTasks.getUserID(), dateOfTasks, 
        		new AsyncCallback<Set<GwtTask>>() {
            /**
             * Handles if retrieve users call fails on server
             */
            public void onFailure(Throwable caught) {
                Log.warn("getTasksForUserById Failed", caught);
            }

            /**
             * Handles if add user is successful on server. Add the user to a group of users to be added on the grid
             */
            public void onSuccess(Set<GwtTask> result) {
                Log.trace("Tasks Gotten Successfully!");
                
                if(result != null && !result.isEmpty()) {
                    addTasks(result);
                }
            }
        });
    }; 	
    
    
    /**
     * Add the specified list of tasks to the grid sorted.
     * @param users The list of users to add to the grid
     * @author Paul De Leon
     */
    private void addTasks(Set<GwtTask> gwtTaskList) {
        // Clear the Local TaskList
        masterTaskList.removeAll(masterTaskList);
        
        // Add all items to the Local TaskList
        for (GwtTask gwtTask : gwtTaskList) {
            addTask(gwtTask);
        }
        
        // Sort the items in the store
//        this.getStore().sort(groupBy.getGroupId());
    }  
    
    
    /**
     * Add a task as a record to the grid
     * @param task The task to add to the grid's records
     * @author Paul De Leon
     */
    private void addTask(GwtTask task) {
    	StringBuilder logBuilder = new StringBuilder("Adding ");
    	logBuilder.append(task.getTitle()).append(" ");
    	logBuilder.append(task.getSuggestedEndTime()).append(" ");
    	logBuilder.append(task.getPriority());
        Log.trace(logBuilder.toString());
        
        String ownerName;
        
        this.masterTaskList.add(task);
        
        Iterator<GwtUser> ownerIter = task.getUsers().iterator();
        if(ownerIter.hasNext()) {
            GwtUser nextUsr = ownerIter.next();
            ownerName = nextUsr.getProfile().getFirstName() + " " +
                nextUsr.getProfile().getLastName();
        }
        else {
            ownerName = "None";
        }        
        
        Log.trace("the id is set to " + task.getTaskID().toString());
        
        this.getStore().add(recordDef.createRecord(
                new Object[] {
                	task.getTaskID().toString(),
                    task.getTitle(),
                    timeFormatter.format(task.getSuggestedStartTime()),
                    timeFormatter.format(task.getSuggestedEndTime()),
                    task.getPriority().toString(),
                    task.getStatus().toString(),
                    task.getCustomerFirstName() + " " + task.getCustomerLastName(),
                    ownerName
                }));
        
        
        this.getStore().commitChanges();
    }    
    
    
    
    
//    /**
//     * Add listeners to this Grid.
//     * @author Paul De Leon
//     */
//    private void setupListeners() {
//    	//TODO add double-click to view info on task 
////        this.addGridRowListener(new GridRowListenerAdapter() {
////            public void onRowDblClick(GridPanel grid, int rowIndex, EventObject e) {
////                final TaskWindow taskWin = new TaskWindow("Selected Task Profile");
////                taskWin.changeSaveBtnLabel();
////                taskWin.setCloseAction(Window.HIDE); 
////                taskWin.setPlain(true);
////                taskWin.setModal(true);
////                taskWin.setVisible(true);               
////                
////                Log.trace("before task " + grid.getSelectionModel().getSelected().getAsString("taskId"));
////                final GwtTask task = getTaskById(grid.getSelectionModel().getSelected().getAsString("taskId"));
////                
////                Log.trace("after task");
////    
////                
////                Log.trace("before setTaskProfile");
////                taskWin.getTaskProfilePanel().setTaskProfile(task);
////                Log.trace("after setTaskProfile");
////                
////                taskWin.show();
////                Log.trace("showed windw");
////            }
////        });
//    }
//    
//    /**
//     * Get Task in Grid By ID.
//     * @param title
//     * @return
//     * @author Paul De Leon
//     */
//    public GwtTask getTaskById(String Id) {
//        Log.trace(" Id = " + id);
//        for (GwtTask task : taskList) {
//            Log.trace(task.getTaskID().toString() + " ?= " + Id);
//            if (task.getTaskID().toString().equals(Id)) {
//               Log.trace("got task with title: " + task.getTitle()); 
//               return task;
//            }
//        }
//
//        return null;
//    }
}
