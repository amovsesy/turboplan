package edu.calpoly.csc.luna.turboplan.web.client.widget;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.core.SortDir;
import com.gwtext.client.core.TextAlign;
import com.gwtext.client.data.FieldDef;
import com.gwtext.client.data.GroupingStore;
import com.gwtext.client.data.Record;
import com.gwtext.client.data.RecordDef;
import com.gwtext.client.data.SortState;
import com.gwtext.client.data.StringFieldDef;
import com.gwtext.client.widgets.BoxComponent;
import com.gwtext.client.widgets.Component;
import com.gwtext.client.widgets.MessageBox;
import com.gwtext.client.widgets.Window;
import com.gwtext.client.widgets.form.ComboBox;
import com.gwtext.client.widgets.form.Field;
import com.gwtext.client.widgets.form.event.ComboBoxCallback;
import com.gwtext.client.widgets.form.event.ComboBoxListener;
import com.gwtext.client.widgets.grid.ColumnConfig;
import com.gwtext.client.widgets.grid.ColumnModel;
import com.gwtext.client.widgets.grid.GridPanel;
import com.gwtext.client.widgets.grid.GroupingView;
import com.gwtext.client.widgets.grid.event.GridRowListenerAdapter;

import edu.calpoly.csc.luna.turboplan.web.client.TurboPlan;
import edu.calpoly.csc.luna.turboplan.web.client.model.GwtTask;
import edu.calpoly.csc.luna.turboplan.web.client.model.GwtUser;
import edu.calpoly.csc.luna.turboplan.web.client.model.GwtTask.TaskStatus;
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
        
    private static final String SIMPLE_CLASSNAME = "TaskGrid";
    
    /**
     * Task Grid
     */
    private final TaskGrid myGrid = this;
    private final RecordDef recordDef;
    private ColumnConfig[] columnConfig;
    private GroupingStore taskGroupStore;
    final DateTimeFormat dateFormatter = DateTimeFormat.getFormat("MM/dd/yy");
    
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
     * Creates an empty TaskGrid.
     */
    public TaskGrid() {
    	recordDef = null;
    }
    
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
                final TaskWindow taskWin = new TaskWindow("Selected Task Profile", myGrid, true);
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
                taskWin.getTaskProfilePanel().setTaskId(Long.parseLong(grid.getSelectionModel().getSelected().getAsString("taskId")));
                Log.trace("after setTaskProfile the id is " + taskWin.getTaskProfilePanel().getTaskId());
                
                taskWin.getTaskProfilePanel().getOwnerBox().addListener(new ComboBoxListener(){

        			public boolean doBeforeQuery(ComboBox comboBox, ComboBoxCallback cb) {
        				// TODO Auto-generated method stub
        				return false;
        			}

        			public boolean doBeforeSelect(ComboBox comboBox, Record record,
        					int index) {
        				// TODO Auto-generated method stub
        				return false;
        			}

        			public void onCollapse(ComboBox comboBox) {
        				// TODO Auto-generated method stub
        				
        			}

        			public void onExpand(ComboBox comboBox) {
        				// TODO Auto-generated method stub
        				
        			}

        			public void onSelect(ComboBox comboBox, Record record, int index) {
        				// TODO Auto-generated method stub
        				
        			}

        			public void onBlur(Field field) {
        				// TODO Auto-generated method stub
        				
        			}

        			public void onChange(Field field, final Object newVal, final Object oldVal) {
        				// TODO Auto-generated method stub
        				taskWin.setDisabled(true);
        				
        				Log.trace("1");
        				final GwtTask t = taskWin.getTaskProfilePanel().getGwtTask();
        				Log.trace("2");
        				
        				ServiceManager.getManageEmployeesServiceAsync().getUsers(TurboPlan.loggedInUser, 
        						new AsyncCallback<List<GwtUser>>(){

									public void onFailure(Throwable error) {
										System.err.println(error.getMessage());
										taskWin.getTaskProfilePanel().getOwnerBox().setValue(oldVal.toString());
										taskWin.setDisabled(false);
									}

									public void onSuccess(List<GwtUser> users) {
										boolean tryingToAssign = false;
										Log.trace("3");
										for(GwtUser u : users) {
											final String name = u.getProfile().getFirstName() + " " +
												u.getProfile().getLastName();
											if(name.equals(newVal.toString())){
												Log.trace("user id = " + u.getUserID().toString());
												Log.trace("task id = " + taskWin.getTaskProfilePanel().getTaskId().toString());
												tryingToAssign = true;
												ServiceManager.getManageTasksServiceAsync().assignTask(
														TurboPlan.loggedInUser.getCompany().getCompanyID(), 
														u.getUserID(), 
														taskWin.getTaskProfilePanel().getTaskId(),
														new AsyncCallback<Boolean>(){

															public void onFailure(
																	Throwable error) {
																System.err.println(error.getMessage());
																taskWin.getTaskProfilePanel().getOwnerBox().setValue(oldVal.toString());
																taskWin.setDisabled(false);
															}

															public void onSuccess(
																	Boolean arg0) {
																Log.trace("5");
										        				if(arg0) {
										        					MessageBox.alert("The task has been assigned to " + name);
										        					
//										        					taskWin.close();
										        					
										        					taskWin.getTaskProfilePanel().setTaskProfile(t);
										        					taskWin.getTaskProfilePanel().setOwners(taskWin);
//										        					taskWin.show();
										        					taskWin.setDisabled(false);
										        				} else {
										        					MessageBox.alert("The task could not be assigned to " + name);
										        					taskWin.getTaskProfilePanel().getOwnerBox().setValue(oldVal.toString());
										        				}
										        				
										        				taskWin.setDisabled(false);
															}});
												break;
											}
										}
										
										if(!tryingToAssign) {
											taskWin.getTaskProfilePanel().getOwnerBox().setValue(oldVal.toString());
											MessageBox.alert(newVal + " could not be found.");
											taskWin.setDisabled(false);
										}
									}});
        			}

        			public void onFocus(Field field) {
        				// TODO Auto-generated method stub
        				
        			}

        			public void onInvalid(Field field, String msg) {
        				// TODO Auto-generated method stub
        				
        			}

        			public void onSpecialKey(Field field, EventObject e) {
        				// TODO Auto-generated method stub
        				
        			}

        			public void onValid(Field field) {
        				// TODO Auto-generated method stub
        				
        			}

        			public void onMove(BoxComponent component, int x, int y) {
        				// TODO Auto-generated method stub
        				
        			}

        			public void onResize(BoxComponent component, int adjWidth,
        					int adjHeight, int rawWidth, int rawHeight) {
        				// TODO Auto-generated method stub
        				
        			}

        			public boolean doBeforeDestroy(Component component) {
        				// TODO Auto-generated method stub
        				return false;
        			}

        			public boolean doBeforeHide(Component component) {
        				// TODO Auto-generated method stub
        				return false;
        			}

        			public boolean doBeforeRender(Component component) {
        				// TODO Auto-generated method stub
        				return false;
        			}

        			public boolean doBeforeShow(Component component) {
        				// TODO Auto-generated method stub
        				return false;
        			}

        			public boolean doBeforeStateRestore(Component component,
        					JavaScriptObject state) {
        				// TODO Auto-generated method stub
        				return false;
        			}

        			public boolean doBeforeStateSave(Component component,
        					JavaScriptObject state) {
        				// TODO Auto-generated method stub
        				return false;
        			}

        			
        			public void onDestroy(Component component) {
        				// TODO Auto-generated method stub
        				
        			}

        			
        			public void onDisable(Component component) {
        				// TODO Auto-generated method stub
        				
        			}

        			
        			public void onEnable(Component component) {
        				// TODO Auto-generated method stub
        				
        			}

        			
        			public void onHide(Component component) {
        				// TODO Auto-generated method stub
        				
        			}

        			
        			public void onRender(Component component) {
        				// TODO Auto-generated method stub
        				
        			}

        			
        			public void onShow(Component component) {
        				// TODO Auto-generated method stub
        				
        			}

        			
        			public void onStateRestore(Component component,
        					JavaScriptObject state) {
        				// TODO Auto-generated method stub
        				
        			}

        			
        			public void onStateSave(Component component, JavaScriptObject state) {
        				// TODO Auto-generated method stub
        				
        			}});
      
                taskWin.getTaskProfilePanel().setOwners(taskWin);
//                taskWin.show();
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
        Log.trace(" Id = " + Id);
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
        ServiceManager.getManageTasksServiceAsync().getNonCompleteTasks(TurboPlan.loggedInUser.getCompany().getCompanyID(),  new AsyncCallback<Set<GwtTask>>() {
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
