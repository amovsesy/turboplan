package edu.calpoly.csc.luna.turboplan.web.client.widget;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.core.SortDir;
import com.gwtext.client.core.TextAlign;
import com.gwtext.client.data.FieldDef;
import com.gwtext.client.data.GroupingStore;
import com.gwtext.client.data.RecordDef;
import com.gwtext.client.data.SortState;
import com.gwtext.client.data.StringFieldDef;
import com.gwtext.client.widgets.Window;
import com.gwtext.client.widgets.grid.ColumnConfig;
import com.gwtext.client.widgets.grid.ColumnModel;
import com.gwtext.client.widgets.grid.GridPanel;
import com.gwtext.client.widgets.grid.GroupingView;
import com.gwtext.client.widgets.grid.event.GridRowListenerAdapter;
import com.gwtext.client.widgets.menu.CheckItem;

import edu.calpoly.csc.luna.turboplan.web.client.TurboPlan;
import edu.calpoly.csc.luna.turboplan.web.client.model.GwtTask;
import edu.calpoly.csc.luna.turboplan.web.client.model.GwtUser;
import edu.calpoly.csc.luna.turboplan.web.client.service.ServiceManager;
import edu.calpoly.csc.luna.turboplan.web.client.window.TaskWindow;

  
/**
 * @author Paul DeLeon
 * @author Stephanie Long
 *
 */
public class TaskGrid extends GridPanel {  
    
    /**
     * Different Task Groupings.
     * @author Paul De Leon
     */
    public enum TaskGroup {
    	ID ("Id", "taskId"),
        DUEDATE ("Due Date", "dueDate"),
        PRIORITY ("Priority", "priority"),
        STARTDATE ("Start Date", "startDate"),
        STATUS ("Status", "status"),
        OWNER ("Owner", "owner"),
        CUSTOMER ("Customer", "customer"),
        LOCATION ("Location", "location");
        
        private final String name, id;
        TaskGroup(String name, String id) {
            this.name = name;
            this.id = id;
        }
        
        public String getGroupId() {
            return this.id;
        }
        
        public String toString() {
            return this.name;
        }       
    }
    
    /**
     * Different Schedule Generating Options
     * @author Bradley Barbee
     */
    public enum GenOpt {
    	RANDOM ("Assign to Random Employees", "random"),
    	PRIORITY ("Assign Highest Priorities First", "none"),
    	SHORTEST("Assign Shortest Tasks First", "esttime"),
    	LONGEST("Assign Longest Tasks First", "esttime"),
    	START ("Assign by Earliest Start Dates", "date"),
    	DUE ("Assign by Earliest Due Dates", "date"),
    	REVERSE ("Assign Tasks Near Due Dates", "date");

        private final String name, id;
        GenOpt(String name, String id) {
            this.name = name;
            this.id = id;
        }

        public String getName() {
            return this.name;
        }
        
        public String getGroupId() {
            return this.id;
        }
        
        public String toString() {
            return this.name;
        }       
    }

    
    private static final String SIMPLE_CLASSNAME = "TaskGrid";
    
    /**
     * Task Grid
     */
    private final TaskGrid myGrid = this;
    private final RecordDef recordDef;
    private ColumnConfig[] columnConfig;
    private GroupingStore taskGroupStore;
    final DateTimeFormat dateFormatter = DateTimeFormat.getFormat("M/d/yy");
    
    /**
     * Tasks to be displayed on employee grid
     */
    private List<GwtTask> taskList = new ArrayList<GwtTask>();
    private List<GwtUser> userList = new ArrayList<GwtUser>();
    
    private final int titleColWidth = 300;
    private final int thisWidth = TurboPlan.STANDARD_PAGE_WIDTH;
    private final int thisHeight = 350; //Same as EmployeeGrid Height
    private TaskGroup groupBy;
    
    private final int dateColWidth = 65;
    private final int priorityColWidth = 65;
    private final int statusColWidth = 80;
    private final int nameColWidth = 100;
    
    /**
     * Construct a Task Grid.
     * @param groupBy
     * @author Paul De Leon
     */
    public TaskGrid(TaskGroup groupBy) {
        super();
        Log.info("Constructing " + SIMPLE_CLASSNAME);
        
        this.groupBy = groupBy;
        recordDef = new RecordDef(  
                new FieldDef[]{
                		new StringFieldDef(TaskGroup.ID.getGroupId()),
                        new StringFieldDef("taskTitle"),
                        new StringFieldDef(TaskGroup.STARTDATE.getGroupId()),
                        new StringFieldDef(TaskGroup.DUEDATE.getGroupId()),
                        new StringFieldDef(TaskGroup.PRIORITY.getGroupId()),
                        new StringFieldDef(TaskGroup.STATUS.getGroupId()),
                        new StringFieldDef(TaskGroup.CUSTOMER.getGroupId()),
                        new StringFieldDef(TaskGroup.OWNER.getGroupId())
                }  
        );          
        
        this.initialize();
        Log.debug("Done constructing " + SIMPLE_CLASSNAME);
    }
    
    /**
     * Initialize the Grid.
     * @author Paul De Leon
     */
    public void initialize() {
    	Log.debug("Initializing " + SIMPLE_CLASSNAME);
    	ColumnModel columnModel;
    	
    	taskGroupStore = createTaskGroupingStore();
        
        
        columnConfig = new ColumnConfig[] {
                new ColumnConfig("Task Title: ", "taskTitle", titleColWidth, true, null, "taskTitle"),
                new ColumnConfig(TaskGroup.STARTDATE.toString(), TaskGroup.STARTDATE.getGroupId(), dateColWidth),
                new ColumnConfig(TaskGroup.DUEDATE.toString(), TaskGroup.DUEDATE.getGroupId(), dateColWidth),
                new ColumnConfig(TaskGroup.PRIORITY.toString(), TaskGroup.PRIORITY.getGroupId(), priorityColWidth),
                new ColumnConfig(TaskGroup.STATUS.toString(), TaskGroup.STATUS.getGroupId(), statusColWidth),
                new ColumnConfig(TaskGroup.CUSTOMER.toString(), TaskGroup.CUSTOMER.getGroupId(), nameColWidth),
                new ColumnConfig(TaskGroup.OWNER.toString(), TaskGroup.OWNER.getGroupId())
        };  
        
        int numColumns = 7;
        for(int i=0; i < numColumns; i++) {
            columnConfig[i].setAlign(TextAlign.LEFT);
        }

        columnModel = new ColumnModel(columnConfig);  
        
        this.setStore(taskGroupStore);
        this.setColumnModel(columnModel);  
        this.setFrame(true);  
        this.setStripeRows(true);  
        this.setAutoExpandColumn("taskTitle");
        this.setHeight(thisHeight);  
        this.setWidth(thisWidth);

        GroupingView thisView = new GroupingView();  
        thisView.setForceFit(true);  
        thisView.setGroupTextTpl("{text} ({[values.rs.length]} {[values.rs.length > 1 ?   \"Items\" : \"Item\"]})"); 

        this.setView(thisView);  
        this.setFrame(true); 
        this.setAnimCollapse(false);  
        this.setIconCls("this-icon");  
        
        this.setStripeRows(true);

        setupListeners();
    }  
    
    /**
     * Add listeners to this Grid.
     * @author Paul De Leon
     */
    private void setupListeners() {
        this.addGridRowListener(new GridRowListenerAdapter() {
            public void onRowDblClick(GridPanel grid, int rowIndex, EventObject e) {
                final TaskWindow taskWin = new TaskWindow("Selected Task Profile", myGrid);
                taskWin.changeSaveBtnLabel();
                taskWin.setCloseAction(Window.HIDE); 
                taskWin.setPlain(true);
                taskWin.setModal(true);
                taskWin.setVisible(true);               
                
                Log.trace("before task " + grid.getSelectionModel().getSelected().getAsString("taskId"));
                final GwtTask task = myGrid.getTaskById(grid.getSelectionModel().getSelected().getAsString("taskId"));
                
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
    public GwtTask getTaskById(String Id) {
        Log.trace(" Id = " + id);
        for (GwtTask task : taskList) {
            Log.trace(task.getTaskID().toString() + " ?= " + Id);
            if (task.getTaskID().toString().equals(Id)) {
               Log.trace("got task with title: " + task.getTitle()); 
               return task;
            }
        }

        return null;
    }
    
    
    /**
     * Create Grouping Store with the specified sorting for this grid.
     * @param groupBy
     * @return
     * @author Paul De Leon
     */
    private GroupingStore createTaskGroupingStore() {
        GroupingStore store = new GroupingStore(recordDef);  
        SortState sortState = new SortState(groupBy.getGroupId(), SortDir.ASC);

        store.setInitialSortState(sortState);
        store.setSortInfo(sortState);
        store.setGroupField(groupBy.getGroupId()); //field to sort by
        
        return store;
    }

    
    /**
     * Load the task data for the company of the logged in user.
     * @author Paul De Leon
     */
    public void loadTaskData() {
        Log.trace("Getting TaskData..."); 
        
        /**
         * Gets employees from database
         */
        ServiceManager.getManageTasksServiceAsync().getTaskByCompanyId(TurboPlan.loggedInUser.getCompany().getCompanyID(),  new AsyncCallback<Set<GwtTask>>() {
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
        
        this.taskList.add(task);
        
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
                    dateFormatter.format(task.getSuggestedStartTime()),
                    dateFormatter.format(task.getSuggestedEndTime()),
                    task.getPriority().toString(),
                    task.getStatus().toString(),
                    task.getCustomerFirstName() + " " + task.getCustomerLastName(),
                    ownerName
                }));
        
        
        this.getStore().commitChanges();
    }    
    
    /**
     * Add the specified list of tasks to the grid sorted.
     * @param users The list of users to add to the grid
     * @author Paul De Leon
     */
    private void addTasks(Set<GwtTask> gwtTaskList) {
        // Clear the Local TaskList
        taskList.removeAll(taskList);
        
        // Add all items to the Local TaskList
        for (GwtTask gwtTask : gwtTaskList) {
            addTask(gwtTask);
        }
        
        // Sort the items in the store
        this.getStore().sort(groupBy.getGroupId());
    }    
   
    
    /**
     * Return the Task Group of the Grid.
     * @return
     * @author Paul De Leon
     */
    public TaskGroup getTaskGroup() {
        return groupBy;
    }
    
    /**
     * Return the Group Name of the Grid.
     * @author Paul De Leon
     */
    public String toString() {
        return groupBy.toString();
    }
    
    /**
     * Return the column config (column names & id's) for the grid.
     * @return
     * @author Paul De Leon
     */
    public ColumnConfig[] getColumnConfig() {
        return columnConfig;
    }
    
    /**
     * Return the recordDef (row info setup) for the grid.
     * @return
     * @author Paul De Leon
     */
    public RecordDef getRecordDef() {
        return recordDef;
    }
    
    /**
     * Return the grouping store.
     * @return
     * @author Paul De Leon
     */
    public GroupingStore getTaskGroupingStore() {
        return taskGroupStore;
    }
}  
