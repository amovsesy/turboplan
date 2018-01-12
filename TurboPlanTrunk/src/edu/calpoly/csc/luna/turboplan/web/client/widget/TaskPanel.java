package edu.calpoly.csc.luna.turboplan.web.client.widget;


import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.CycleButton;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.SplitButton;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.event.CycleButtonListenerAdapter;
import com.gwtext.client.widgets.event.SplitButtonListenerAdapter;
import com.gwtext.client.widgets.layout.CardLayout;
import com.gwtext.client.widgets.menu.BaseItem;
import com.gwtext.client.widgets.menu.CheckItem;
import com.gwtext.client.widgets.menu.Menu;
import com.gwtext.client.widgets.menu.event.CheckItemListener;
import com.gwtext.client.widgets.menu.event.CheckItemListenerAdapter;
import com.gwtext.client.widgets.menu.event.MenuListenerAdapter;

import edu.calpoly.csc.luna.turboplan.web.client.TurboPlan;
import edu.calpoly.csc.luna.turboplan.web.client.service.ServiceManager;
import edu.calpoly.csc.luna.turboplan.web.client.widget.TaskGrid.GenOpt;
import edu.calpoly.csc.luna.turboplan.web.client.widget.TaskGrid.TaskGroup;
import edu.calpoly.csc.luna.turboplan.web.client.window.TaskWindow;
import edu.calpoly.csc.luna.turboplan.web.client.window.EmployeeProfileWindow;

/**
 * A task panel holds the task grids and provides the buttons controlling functionality
 * @author Paul De Leon
 * @author Stephanie Long
 */
public class TaskPanel extends Panel {
	
	private static boolean schedGen_random = true;
	private static boolean schedGen_priority = false;
	private static boolean schedGen_shortEstTime = false;
	private static boolean schedGen_longEstTime = false;
	private static boolean schedGen_startDate = false;
	private static boolean schedGen_dueDate = false;
	private static boolean schedGen_nearDue = false;
	
    private final int thisWidth = TurboPlan.STANDARD_PAGE_WIDTH;
    private final int thisHeight = 350; //Same as EmployeeGrid Height  
      
    private TaskGrid dueDateGrid;
    private TaskGrid priorityGrid;
    private TaskGrid startDateGrid;
    private TaskGrid statusGrid;
    private TaskGrid customerGrid;
    private TaskGrid ownerGrid;
    
    private final int dueDateGridNum = 0;
    private final int priorityGridNum = 1;
    private final int startDateGridNum = 2;
    private final int statusGridNum = 3;
    private final int customerGridNum = 4;
    private final int ownerGridNum = 5;
    
    private TaskGrid activeGrid;  //THE ACTIVE GRID!
   
    private Button completeBtn = null;
    private Button saveBtn = null;
    private Button cancelBtn = null;
    private Button deleteBtn = null;
    private Button[] buttons = new Button[4];
    
    /**
     * Construct Task Panel
     * @author Paul De Leon
     */
    public TaskPanel() {
        super();
        this.activeGrid = dueDateGrid;
        this.setupPanel();
        this.setupButtons();
    }
  
    
    /**
     * @author Paul De Leon
     */
    private void setupPanel() {
        Log.trace("Setting Up TaskPanel...");
        this.setLayout(new CardLayout());
          
        dueDateGrid = new TaskGrid(TaskGroup.DUEDATE);
        priorityGrid = new TaskGrid(TaskGroup.PRIORITY);
        startDateGrid = new TaskGrid(TaskGroup.STARTDATE);
        statusGrid = new TaskGrid(TaskGroup.STATUS);
        customerGrid = new TaskGrid(TaskGroup.CUSTOMER);
        ownerGrid = new TaskGrid(TaskGroup.OWNER);
  
        this.add(dueDateGrid);
        this.add(priorityGrid);
        this.add(startDateGrid);
        this.add(statusGrid);
        this.add(customerGrid);
        this.add(ownerGrid);
        
        //Set dueDateGrid as active grid
        setActiveGrid(dueDateGrid, dueDateGridNum); 
        
        this.setTitle("Tasks");
        this.setWidth(thisWidth);
        this.setHeight(thisHeight);
        Log.trace("Setup TaskPanel!");
    }

    
    
    /**
     * Create and Add Buttons to Top Toolbar.
     * @author Paul De Leon
     */
    private void setupButtons() {
        Log.trace("Setting up TaskPanel Buttons...");
        //Buttons on This Task Panel
        Button addTaskBtn = new Button("Add Task");
        Button genSchedBtn = setupGenSchedBtn();
        Button genSchedOptBtn = setupGenOptBtn();
        Button arrangeBtn = setupArrangeBtn();  
        
        addTaskBtn.addListener(new ButtonListenerAdapter() {
            public void onClick(Button button, EventObject e) {
                
                final TaskWindow taskWin = new TaskWindow("Create Task", activeGrid);
            	taskWin.show();
            }
        });        
        
        
        //Add Button to top ToolBar
        this.setTopToolbar(new Button[] { addTaskBtn, genSchedBtn, genSchedOptBtn, arrangeBtn });
        Log.trace("TaskPanel Buttons Setup!");
    }
    
    /**
     * Create Add Task Button.
     * @return
     * @author Paul De Leon
     */
    private Button setupAddTaskBtn() {
        Button addTaskBtn = new Button("Add Task");
        
        addTaskBtn.addListener(new ButtonListenerAdapter() {
            public void onClick(Button button, EventObject e) {
                
                final TaskWindow taskWin = new TaskWindow("Create Task", activeGrid);
                taskWin.show();
            }
        });    
        
        return addTaskBtn;
    }
    
    /**
     * Sets up the generate schedule button
     * @return The generate schedule button set up
     * @author Aleks
     */
    private Button setupGenSchedBtn() {
        Button genSchedBtn = new Button("Generate Schedule");
    
        genSchedBtn.addListener(new ButtonListenerAdapter() {
            public void onClick(Button button, EventObject e) {
                Log.trace("Generating Schedule");
                ServiceManager.getManageTasksServiceAsync().generateSchedule(TurboPlan.loggedInUser.getCompany().getCompanyID(),
                	TurboPlan.loggedInUser.getUserID(),
                	false,false,false,false,false,false,false,
                    new AsyncCallback<Object>(){

                        public void onFailure(Throwable caught) {
                            Log.error(caught.getMessage());
                            activeGrid.getStore().removeAll();
                            activeGrid.getStore().commitChanges();
                            activeGrid.loadTaskData();
                            activeGrid.getStore().commitChanges();    
                        }
    
                        public void onSuccess(Object result) {
                            //Do nothing
                            
                            Log.trace("Done Generating Schedule");
                            
                            activeGrid.getStore().removeAll();
                            activeGrid.getStore().commitChanges();
                            activeGrid.loadTaskData();
                            activeGrid.getStore().commitChanges();
                        }
                    });
                }           
        });  
        
        return genSchedBtn;
    }
    
    /**
     * Create Arrange Button for Cycling through different Grid Groupings.
     * @return
     * @author Paul De Leon
     */
    private Button setupArrangeBtn() {
        CycleButton arrangeBtn = new CycleButton(); 
        
        //create a CycleButton  
        arrangeBtn.setShowText(true);  
        arrangeBtn.setPrependText("    Arrange By: ");  

        //add CheckItem's to the CycleButton  
        CheckItem dueDateItem = new CheckItem(TaskGroup.DUEDATE.toString(), true);
        CheckItem priorityItem = new CheckItem(TaskGroup.PRIORITY.toString(), false);
        CheckItem startDateItem = new CheckItem(TaskGroup.STARTDATE.toString(), false);
        CheckItem statusItem = new CheckItem(TaskGroup.STATUS.toString(), false);
        CheckItem customerItem = new CheckItem(TaskGroup.CUSTOMER.toString(), false);
        CheckItem ownerItem = new CheckItem(TaskGroup.OWNER.toString(), false);
        
        
        dueDateItem.setId(TaskGroup.DUEDATE.getGroupId());
        priorityItem.setId(TaskGroup.PRIORITY.getGroupId());
        startDateItem.setId(TaskGroup.STARTDATE.getGroupId());
        statusItem.setId(TaskGroup.STATUS.getGroupId());
        customerItem.setId(TaskGroup.CUSTOMER.getGroupId());
        ownerItem.setId(TaskGroup.OWNER.getGroupId());
        
        arrangeBtn.addItem(dueDateItem);
        arrangeBtn.addItem(priorityItem);
        arrangeBtn.addItem(startDateItem);
        arrangeBtn.addItem(statusItem);
        arrangeBtn.addItem(customerItem);
        arrangeBtn.addItem(ownerItem);
        
        arrangeBtn.addListener(new CycleButtonListenerAdapter() {  
            public void onChange(CycleButton self, CheckItem item) { 
                String itemId = item.getId();
                if(itemId.equals(TaskGroup.DUEDATE.getGroupId())) {
                    setActiveGrid(dueDateGrid, dueDateGridNum);
                }
                else if(itemId.equals(TaskGroup.PRIORITY.getGroupId())) {
                    setActiveGrid(priorityGrid, priorityGridNum);
                }
                else if(itemId.equals(TaskGroup.STARTDATE.getGroupId())) {
                    setActiveGrid(startDateGrid, startDateGridNum);
                }
                else if(itemId.equals(TaskGroup.STATUS.getGroupId())) {
                    setActiveGrid(statusGrid, statusGridNum);
                }
                else if(itemId.equals(TaskGroup.CUSTOMER.getGroupId())) {
                    setActiveGrid(customerGrid, customerGridNum);
                }
                else if(itemId.equals(TaskGroup.OWNER.getGroupId())) {
                    setActiveGrid(ownerGrid, ownerGridNum);
                }
                
                Log.info("Active Grid is now: " + activeGrid.toString());
            }  
        });        
        
        return arrangeBtn;
    }

    /**
     * Create Generate Schedule Options Button for deciding the options to generate a schedule.
     * @return
     * @author Bradley Barbee
     */
    private Button setupGenOptBtn() {
	
        //create a SplitButton
    	SplitButton arrangeBtn = new SplitButton();
    	arrangeBtn.setText("Generate Schedule w/ Options:");
        
    	arrangeBtn.addListener(new ButtonListenerAdapter() {
            public void onClick(Button button, EventObject e) {
                Log.trace("Generating Schedule");
                ServiceManager.getManageTasksServiceAsync().generateSchedule(TurboPlan.loggedInUser.getCompany().getCompanyID(),
                	TurboPlan.loggedInUser.getUserID(),
                	schedGen_random, schedGen_priority, schedGen_shortEstTime,
                 	schedGen_longEstTime, schedGen_startDate, schedGen_dueDate, schedGen_nearDue,
                    new AsyncCallback<Object>(){

                        public void onFailure(Throwable caught) {
                            Log.error(caught.getMessage());
                            activeGrid.getStore().removeAll();
                            activeGrid.getStore().commitChanges();
                            activeGrid.loadTaskData();
                            activeGrid.getStore().commitChanges();    
                        }
    
                        public void onSuccess(Object result) {
                            //Do nothing
                            
                            Log.trace("Done Generating Schedule");
                            
                            activeGrid.getStore().removeAll();
                            activeGrid.getStore().commitChanges();
                            activeGrid.loadTaskData();
                            activeGrid.getStore().commitChanges();
                        }
                    });
                }           
        });  
        	
    	Menu menu = new Menu();
    	
        //add CheckItem's to the CycleButton  
        final CheckItem randomItem = new CheckItem(GenOpt.RANDOM.toString(), true);
        final CheckItem priorityItem = new CheckItem(GenOpt.PRIORITY.toString(), false);
        final CheckItem shortEstItem = new CheckItem(GenOpt.SHORTEST.toString(), false);
        final CheckItem longEstItem = new CheckItem(GenOpt.LONGEST.toString(), false);
        final CheckItem startItem = new CheckItem(GenOpt.START.toString(), false);
        final CheckItem dueItem = new CheckItem(GenOpt.DUE.toString(), false);
        final CheckItem reverseItem = new CheckItem(GenOpt.REVERSE.toString(), false);
        
        randomItem.addListener(new CheckItemListenerAdapter() {
        	@Override
        	public void onCheckChange(CheckItem item, boolean checked) {
        	   	if(checked) {
        	   		schedGen_random = true;
            		Log.trace("Random Schedule Generation Selected");
            	} else {
            		schedGen_random = false;
            		Log.trace("Random Schedule Generation Unselected");
            	}
        	}
        });
        priorityItem.addListener(new CheckItemListenerAdapter() {
        	@Override
        	public void onCheckChange(CheckItem item, boolean checked) {
        	   	if(checked) {
        	   		schedGen_priority = true;
            		Log.trace("Assign by Highest Priority Schedule Generation Selected");
            	} else {
            		schedGen_priority = false;
            		Log.trace("Assign by Highest Priority Schedule Generation Unselected");
            	}
        	}
        });
        shortEstItem.addListener(new CheckItemListenerAdapter() {
        	@Override
        	public void onCheckChange(CheckItem item, boolean checked) {
        	   	if(checked) {
        	   		schedGen_shortEstTime = true;
        	   		Log.trace("Assign by Shortest Estimated Time Schedule Generation Selected");
            		Log.trace("Assign by Estimated Time = " + (schedGen_shortEstTime ^ schedGen_longEstTime));
            	} else {
        	   		schedGen_shortEstTime = false;
        	   		Log.trace("Assign by Shortest Estimated Time Schedule Generation Unselected");
        	   		Log.trace("Assign by Estimated Time = " + (schedGen_shortEstTime ^ schedGen_longEstTime));
            	}
        	}
        });
        longEstItem.addListener(new CheckItemListenerAdapter() {
        	@Override
        	public void onCheckChange(CheckItem item, boolean checked) {
        	   	if(checked) {
        	   		schedGen_longEstTime = true;
        	   		Log.trace("Assign by Longest Estimated Time Schedule Generation Selected");
        	   		Log.trace("Assign by Estimated Time = " + (schedGen_shortEstTime ^ schedGen_longEstTime));
            	} else {
        	   		schedGen_longEstTime = false;
        	   		Log.trace("Assign by Longest Estimated Time Schedule Generation Unselected");
        	   		Log.trace("Assign by Estimated Time = " + (schedGen_shortEstTime ^ schedGen_longEstTime));
            	}
        	}
        });
        startItem.addListener(new CheckItemListenerAdapter() {
        	@Override
        	public void onCheckChange(CheckItem item, boolean checked) {
        	   	if(checked) {
        	   		schedGen_startDate = true;
        	   		Log.trace("Assign by Suggested Start Date Schedule Generation Selected");
        	   		Log.trace("Assign by Suggested Date = " + (schedGen_startDate ^ schedGen_dueDate));
            	} else {
            		schedGen_startDate = false;
        	   		Log.trace("Assign by Suggested Start Date Schedule Generation Unselected");
        	   		Log.trace("Assign by Suggested Date = " + (schedGen_startDate ^ schedGen_dueDate));
            	}
        	}
        });
        dueItem.addListener(new CheckItemListenerAdapter() {
        	@Override
        	public void onCheckChange(CheckItem item, boolean checked) {
        	   	if(checked) {
        	   		schedGen_dueDate = true;
        	   		Log.trace("Assign by Suggested Due Date Schedule Generation Selected");
        	   		Log.trace("Assign by Suggested Date = " + (schedGen_startDate ^ schedGen_dueDate));
            	} else {
            		schedGen_dueDate = false;
        	   		Log.trace("Assign by Suggested Due Date Schedule Generation Unselected");
        	   		Log.trace("Assign by Suggested Date = " + (schedGen_startDate ^ schedGen_dueDate));
            	}
        	}
        }); 
        reverseItem.addListener(new CheckItemListenerAdapter() {
        	@Override
        	public void onCheckChange(CheckItem item, boolean checked) {
        	   	if(checked) {
        	   		schedGen_nearDue = true;
            		Log.trace("Assign Closest to Due Dates Schedule Generation Selected");
            	} else {
            		schedGen_nearDue = false;
            		Log.trace("Assign Closest to Due Dates Schedule Generation Unselected");
            	}
        	}
        });
        
        menu.addItem(randomItem);
        menu.addItem(priorityItem);
        menu.addItem(shortEstItem);
        menu.addItem(longEstItem);
        menu.addItem(startItem);
        menu.addItem(dueItem);
        menu.addItem(reverseItem);
        
    	arrangeBtn.setMenu(menu);
    	
        return arrangeBtn;
    }
    
    /**
     * Sets the activegrid of the panel
     * @param newActiveGrid
     * @param newActiveGridNum
     * @author Paul De Leon
     */
    private void setActiveGrid(TaskGrid newActiveGrid, int newActiveGridNum) {
        Log.trace("Setting New ActiveTaskGrid...");
        this.setActiveItem(newActiveGridNum);
        activeGrid = newActiveGrid;
        reloadGridTaskData(activeGrid);
        Log.trace("Finished Setting New ActiveTaskGrid!");
    }
    
    
    /**
     * Reload the TaskData into the grid.
     * @param grid
     * @author Paul De Leon
     */
    private void reloadGridTaskData(TaskGrid grid) {
        Log.trace("Reloading TaskGrid Data...");
        grid.getStore().removeAll();
        grid.getStore().commitChanges();
        grid.loadTaskData();
        grid.getStore().commitChanges();
        Log.trace("Finished Reloading TaskGrid Data!");
    }
}
