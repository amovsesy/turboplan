package edu.calpoly.csc.luna.turboplan.web.client.widget;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwtext.client.data.DateFieldDef;
import com.gwtext.client.data.FieldDef;
import com.gwtext.client.data.FloatFieldDef;
import com.gwtext.client.data.Record;
import com.gwtext.client.data.RecordDef;
import com.gwtext.client.data.Store;
import com.gwtext.client.data.StringFieldDef;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.MessageBox;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.grid.CellMetadata;
import com.gwtext.client.widgets.grid.ColumnConfig;
import com.gwtext.client.widgets.grid.ColumnModel;
import com.gwtext.client.widgets.grid.GridPanel;
import com.gwtext.client.widgets.grid.Renderer;
import com.gwtext.client.widgets.grid.event.GridCellListenerAdapter;
import com.gwtext.client.core.EventObject;

import edu.calpoly.csc.luna.turboplan.web.client.TurboPlan;
import edu.calpoly.csc.luna.turboplan.web.client.model.GwtTask;
import edu.calpoly.csc.luna.turboplan.web.client.model.GwtUser;
import edu.calpoly.csc.luna.turboplan.web.client.service.ManageTasksServiceAsync;
import edu.calpoly.csc.luna.turboplan.web.client.service.ServiceManager;
import edu.calpoly.csc.luna.turboplan.web.client.window.MasterScheduleTaskWindow;

public class MasterScheduleView extends Panel {
	public enum WeekDay {
		SUN, MON, TUES, WED, THURS, FRI, SAT;
	}
	
	private class EmpInfo {
		final int numDaysInWeek = 7;
		final int SUN = 0, MON = 1, TUES = 2, WED = 3, THURS = 4, FRI = 5, SAT = 6;
		
		private String empName;
		private boolean[] hasTasks;
		
		public EmpInfo(String empName) {
			this.empName = empName;
			hasTasks = new boolean[numDaysInWeek];
			
			//initialize boolean array to false
			clearTasks();
		}
		
		public void clearTasks() {
			//initialize boolean array to false
			for(int day = 0; day < numDaysInWeek; day++) {
				hasTasks[day] = false;
			}
		}
		
		public String getEmpName() {
			return empName;
		}
		
//		private void setHasTasksOnDays(/*Set<WeekDay> days) {*/ WeekDay[] days) {
//			
////			Iterator<WeekDay> dayIter = days.iterator();
////			
////			while(dayIter.hasNext()) {
////				setHasTasksOnDay(days[])
////			}
//			
//			Set<WeekDay> myDays = new HashSet<WeekDay>();
//			myDays.
//			
//			for(int idx = 0; idx < days.length; idx++) {
//				setHasTasksOnDay(days[idx]);
//			}
//		}
		
		public boolean hasTasksOnDay(WeekDay day) {
			return hasTasks[getDayIdx(day)];
		}
		
		public void setHasTasksOnDay(WeekDay day) {
			switch(day) {
				case SUN :
					hasTasks[SUN] = true;
					break;
				case MON :
					hasTasks[MON] = true;
			        break;
				case TUES :
					hasTasks[TUES] = true;
					break;
				case WED :
					hasTasks[WED] = true;
					break;
				case THURS :
					hasTasks[THURS] = true;
					break;
				case FRI :
					hasTasks[FRI] = true;
					break;
				case SAT :
					hasTasks[SAT] = true;
					break;
				default :
					//DO NOTHING
			}
		}
		
		private int getDayIdx(WeekDay day) {
			switch(day) {
				case SUN :
					return SUN;
				case MON :
					return MON;
				case TUES :
					return TUES;
				case WED :
					return WED;
				case THURS :
					return THURS;
				case FRI :
					return FRI;
				case SAT :
					return SAT;
				default :
					//DO NOTHING
					Log.warn("Error: Not a valid WeekDay");
					return -1;
			}
		}
	}
	private class AvailRenderer implements Renderer {
		private String field;
		
		public AvailRenderer(String field){
			this.field = field;
		}
		
		public String render(Object value, CellMetadata cellMetadata, 
				Record record, int rowIndex, int colNum, Store store) {
			
			if(record.getAsString(field).matches("-")) {
	    		cellMetadata.setHtmlAttribute("style=\"background:whitesmoke;\"");
			}
			else{
				cellMetadata.setHtmlAttribute("style=\"background:paleturquoise;\"");
			}
			
			return value.toString();
		}

	}
	private static final String PAGE_TITLE		 = "Master Schedule";
	private static final String SIMPLE_CLASSNAME = "MasterScheduleView";
	
	private static final String YES_TASKS = "TASKS";
	private static final String NO_TASKS = "-";
	
	private static final String EMP_RECORDDEF	 = "emp";
	private static final String SUN_RECORDDEF 	 = "sun";
	private static final String MON_RECORDDEF 	 = "mon";
	private static final String TUES_RECORDDEF 	 = "tues";
	private static final String WED_RECORDDEF 	 = "wed";
	private static final String THURS_RECORDDEF  = "thurs";
	private static final String FRI_RECORDDEF 	 = "fri";
	private static final String SAT_RECORDDEF 	 = "sat";
	
	private static final int EMPCOL_WIDTH = 160;
	private static final int DAYCOL_WIDTH = (TurboPlan.STANDARD_PAGE_WIDTH - EMPCOL_WIDTH) / 7;
	
	final DateTimeFormat shortDateFormatter = DateTimeFormat.getFormat("M/dd");
	final DateTimeFormat fullDateFormatter = DateTimeFormat.getFormat("M/dd/yy");
	
	
	List<EmpInfo> empInfoList;     //Cached Information About All Employees to render grid
	List<GwtUser> companyEmpList;  //All Employees in Logged in User's Company.
	Date dayInViewingWeek;
	
	private GridPanel grid;
	private RecordDef recordDef;
	private ColumnConfig[] columns;
	private Date[] week; 
	
    public MasterScheduleView () {
    	super();
    	empInfoList = new ArrayList<EmpInfo>();
    	companyEmpList = new ArrayList<GwtUser>();
    	dayInViewingWeek = new Date();  //Set current week viewed to the current week.
    	setup();
    }
    
    private void setup() {
  
        recordDef = new RecordDef(  
                new FieldDef[]{  
                        new StringFieldDef(EMP_RECORDDEF),
                        new StringFieldDef(SUN_RECORDDEF),
                        new StringFieldDef(MON_RECORDDEF),
                        new StringFieldDef(TUES_RECORDDEF),
                        new StringFieldDef(WED_RECORDDEF),
                        new StringFieldDef(THURS_RECORDDEF),
                        new StringFieldDef(FRI_RECORDDEF),
                        new StringFieldDef(SAT_RECORDDEF),
                }  
        );  
        
  
//        GridPanel grid = new GridPanel();  
        grid = new GridPanel();
        
        
//        Object[][] data = getCompanyData();  
//        MemoryProxy proxy = new MemoryProxy(data);  
  
//        ArrayReader reader = new ArrayReader(recordDef);  
//        Store store = new Store(proxy, reader);  
        Store store = new Store(recordDef);
        grid.setStore(store);  
        
        week = getWeek(dayInViewingWeek); //Get days of this current week
                
        columns = new ColumnConfig[]{  
                //column ID is company which is later used in setAutoExpandColumn  
                new ColumnConfig("Employee", EMP_RECORDDEF, EMPCOL_WIDTH, true, null, EMP_RECORDDEF),  
                new ColumnConfig("Sun ", SUN_RECORDDEF, DAYCOL_WIDTH, false, new AvailRenderer(SUN_RECORDDEF)),  
                new ColumnConfig("Mon ", MON_RECORDDEF, DAYCOL_WIDTH, false, new AvailRenderer(MON_RECORDDEF)),
                new ColumnConfig("Tues ", TUES_RECORDDEF, DAYCOL_WIDTH, false, new AvailRenderer(TUES_RECORDDEF)),  
                new ColumnConfig("Wed ", WED_RECORDDEF, DAYCOL_WIDTH, false, new AvailRenderer(WED_RECORDDEF)),  
                new ColumnConfig("Thurs ", THURS_RECORDDEF, DAYCOL_WIDTH, false, new AvailRenderer(THURS_RECORDDEF)),
                new ColumnConfig("Fri ", FRI_RECORDDEF, DAYCOL_WIDTH, false, new AvailRenderer(FRI_RECORDDEF)),
                new ColumnConfig("Sat ", SAT_RECORDDEF, DAYCOL_WIDTH, false, new AvailRenderer(SAT_RECORDDEF)),
        };  

        ColumnModel columnModel = new ColumnModel(columns);  
        grid.setColumnModel(columnModel);  

        grid.setFrame(true);  
        grid.setStripeRows(true);  
        grid.setAutoExpandColumn(EMP_RECORDDEF);  

        grid.setHeight(350);  
//        grid.setWidth(600);
        grid.setWidth(TurboPlan.STANDARD_PAGE_WIDTH);
         
//        Toolbar bottomToolbar = new Toolbar();  
//        bottomToolbar.addFill();  
//        bottomToolbar.addButton(new ToolbarButton("Clear Sort", new ButtonListenerAdapter() {  
//            public void onClick(Button button, EventObject e) {  
//                grid.clearSortState(true);  
//            }  
//        }));  
//        grid.setBottomToolbar(bottomToolbar);  

       
        
        grid.addGridCellListener(new GridCellListenerAdapter() {
        	public void onCellDblClick(GridPanel grid, int rowIndex, int colIndex, EventObject e) {
        		final MasterScheduleTaskWindow win;
        		Record selectedRecord = grid.getSelectionModel().getSelected();
        		String selectedDayLabel = "";
        		Date[] weekDays = getWeek(dayInViewingWeek);
        		
        		
//        	    MessageBox.alert("DblClicked Cell: " +
//        	    	selectedRecord.getAsString(columns[0].getDataIndex()) + " " +
//        	    	selectedRecord.getAsString(columns[colIndex].getDataIndex()) + " " +
//        	    	columns[colIndex].getDataIndex() );
        	    
        		String empName = selectedRecord.getAsString(columns[0].getDataIndex());
        		Log.info("ColumnHeader: " + grid.getColumnModel().getColumnHeader(colIndex));
        		Log.info("ColumnId: " + grid.getColumnModel().getColumnId(colIndex));
        		Log.info("ColumnStuff1: " + selectedRecord.getAsString(columns[colIndex].getDataIndex()));
        		Log.info("ColumnStuff2: " + grid.getColumnModel().getDataIndex(colIndex));
        		
        		String selectedTaskLabel = selectedRecord.getAsString(columns[colIndex].getDataIndex());
        		
        		if(colIndex > 0 && selectedTaskLabel.equals(YES_TASKS)) {
        			Date dateSelected = weekDays[colIndex-1];
        			selectedDayLabel = fullDateFormatter.format(dateSelected);
        			
        			//TODO get specified user's id
        			GwtUser selectedUser = getGwtUser(empName);
        			
        			if(selectedUser == null) {
        				MessageBox.alert("Employee Doesn't Exist");
        			}
        			else {
        				win = new MasterScheduleTaskWindow(empName + " Tasks  (" + selectedDayLabel + ")",
            				selectedUser, dateSelected);
        				win.show();
        			}
        		}
        		else if(selectedTaskLabel.equals(NO_TASKS)) {
        			MessageBox.alert("No Tasks On This Day");
        		}
        	}
        });

        
        getTheGrid();
                 
        setupButtons();
        
        this.add(grid);
    }  
   
    private void getTheGrid(){
    	
    	grid.getStore().removeAll();
    	
    	week = getWeek(dayInViewingWeek);
    	
    	empInfoList = new ArrayList<EmpInfo>();
    	companyEmpList = new ArrayList<GwtUser>();
    	
    	grid.setTitle(PAGE_TITLE + "  (" + fullDateFormatter.format(week[0]) 
    			+ " - " + fullDateFormatter.format(week[6]) + ")");  

//        addEmployee();
        
        loadCompanyUsers();
          
        }
 
    
    private void setupButtons() {
        Log.trace("Setting up Buttons...");
        //Buttons on This Task Panel
        Button prev = new Button("Previous");
        Button tod = new Button("This Week");
        Button next = new Button("Next");
        
        prev.addListener(new ButtonListenerAdapter() {
            public void onClick(Button button, EventObject e) {
                //Change the sat and sun to a prev
            	dayInViewingWeek = getDateOffset(dayInViewingWeek,-7);
                
                getTheGrid();
            }
        });

        tod.addListener(new ButtonListenerAdapter() {
            public void onClick(Button button, EventObject e) {
                //Original sat and sun
            	dayInViewingWeek = new Date();
        		
        		getTheGrid();    	
            }
        });
        
        next.addListener(new ButtonListenerAdapter() {
            public void onClick(Button button, EventObject e) {
                //Change the sat and sun to a next
            	dayInViewingWeek = getDateOffset(dayInViewingWeek,7);
             
                getTheGrid();
            }
        });
        
        //Add Button to top ToolBar
        this.setTopToolbar(new Button[] { prev, tod, next });
        
        Log.trace("Buttons Setup!");
    }
     
    

    // Get the given GwtUser
    private GwtUser getGwtUser(String nameToMatch) {
    	//Search employee list for GwtUser with name
    	for(int idx = 0; idx < companyEmpList.size(); idx++) {
    		GwtUser gUser = companyEmpList.get(idx);
    		String empName = gUser.getProfile().getFirstName() + " " + gUser.getProfile().getLastName();
    		
    		if(empName.equals(nameToMatch)) {
    			return gUser;
    		}
    	}
    	
    	Log.trace("User not found");
    	return null;
    }
    
 
//    private ArrayList<Record> getEmpRecords(ArrayList<String> empNameList) {
//    	ArrayList<Record> empRecords = new ArrayList<Record>();
//    	
//    	for(int idx = 0; idx < empNameList.size(); idx++) {
//    		Record empRecordToAdd = recordDef.createRecord(
//    			new Object[] {
//    				empNameList.get(idx),
//    				NO_TASKS,
//    				NO_TASKS,
//    				NO_TASKS,
//    				NO_TASKS,
//    				NO_TASKS,
//    				NO_TASKS,
//    				NO_TASKS
//    			});
//    		
//    		empRecords.add(empRecordToAdd);
//    	}
//    	
//    	return empRecords;
//    }
    
    
    /**
     * Batch add employee records to grid store.
     * @param empRecords
     */
    private void addEmpRecords(List<Record> empRecords) {
    	Log.trace("Batch Adding Employee Records...");
    	
    	for(int idx = 0; idx < empRecords.size(); idx++) {
    		addEmpRecord(empRecords.get(idx));
    	}
    	
    	Log.debug("...Finished Batch Adding Employee Records!");
    }
    
    
    /**
     * Add single employee record to grid store.
     * @param empRecord
     */
    private void addEmpRecord(Record empRecord) {
    	Log.trace("Adding An Employee Record...");
    	
    	grid.getStore().add(empRecord);
    	grid.getStore().commitChanges();
    	
    	Log.debug("...Finished Adding An Employee Record!");
    }
    
    
//    /**
//     * TEST
//     */
//    private void addEmployee() {
//        Log.trace("Adding Employee...");
//        
//        grid.getStore().add(recordDef.createRecord(
//            new Object[] {
//            	"Paul De Leon",
//            	NO_TASKS,
//            	NO_TASKS,
//            	YES_TASKS,
//            	NO_TASKS,
//            	YES_TASKS,
//            	NO_TASKS,
//            	NO_TASKS
//            })
//         );
//        grid.getStore().commitChanges();
//        
//        
//        grid.getStore().add(recordDef.createRecord(
//            new Object[] {
//            	"Stephanie Long",
//            	YES_TASKS,
//            	YES_TASKS,
//            	YES_TASKS,
//            	NO_TASKS,
//            	NO_TASKS,
//            	NO_TASKS,
//            	NO_TASKS
//            })
//         );   
//        grid.getStore().commitChanges();
//            
//        
//        
//        grid.getStore().add(recordDef.createRecord(
//            new Object[] {
//            	"Brad Barbee",
//            	YES_TASKS,
//            	NO_TASKS,
//            	NO_TASKS,
//            	NO_TASKS,
//            	NO_TASKS,
//            	NO_TASKS,
//            	YES_TASKS
//            })
//         );    
//        grid.getStore().commitChanges();
//        
//        Log.info("...Done Adding Employee!");
//    }  
    
 
    
    

    
    
  /**
  * Sets Owner Field from Owners in the Database.
  * @param taskWin
  * @author Paul De Leon
  */
 public void loadCompanyUsers() {
//     final ArrayList<String> owners = new ArrayList<String>();

     ServiceManager.getManageEmployeesServiceAsync().getUsers(TurboPlan.loggedInUser, new AsyncCallback<List<GwtUser>>() {
         /**
          * Handles if retrieve users call fails on server
          */
         public void onFailure(Throwable caught) {
             Log.warn("Error Getting Users From Database", caught);
         }
     
         /**
          * Handles if add user is successful on server. Add the user to a group of users to be added on the grid
          */
         public void onSuccess(List<GwtUser> userList) {
             Log.trace("Succesfully Got Users From Database");
             if (userList != null && !userList.isEmpty()) {
                 Log.info("UserList gotten from database is not empty or null");
                 
			     //Get all employees for logged in user from the database
			     for(GwtUser user : userList) {
			    	 String empName = user.getProfile().getFirstName() + " " + 
			    	 	user.getProfile().getLastName();
			    	 
			    	 Log.info("Got Employee: " + empName);
			    	 
			    	 companyEmpList.add(user);
			    	 empInfoList.add(new EmpInfo(empName));
			     }
                 
			     
			     //Get info on employees who have tasks for the week.
                 loadWhoHasTaskData();
             }
         }
     }); 
 }
 
 
 /**
  * Load the task data for the company of the logged in user.
  * @author Paul De Leon
  */
 public void loadWhoHasTaskData() { 
     Log.trace("Getting WhoHasTaskData..."); 
     
     /**
      * Gets employees from database
      */
     ServiceManager.getManageTasksServiceAsync().getUsrsHaveTaskForEachDayOfWeek(TurboPlan.loggedInUser.getCompany().getCompanyID(), dayInViewingWeek, 
    		 new AsyncCallback<List<List<GwtUser>>>() {
         /**
          * Handles if retrieve users call fails on server
          */
         public void onFailure(Throwable caught) {
             Log.warn("getUsrsHaveTaskForEachDayOfWeek Failed", caught);
         }

         /**
          * Handles if add user is successful on server. Add the user to a group of users to be added on the grid
          */
         public void onSuccess(List<List<GwtUser>> result) {
             Log.trace("Usrs Who Have Tasks Gotten Successfully!");
             
             if(result == null) {
            	 Log.trace("Result from who has tasks is null");
             }
             else if(result.isEmpty()) {
            	 Log.trace("Result from who has tasks is empty!");
            	 
             }
             else{
                 //Fills the grid
                 getTaskDays(result);
             }
         }           
     });	 	
 }
 
 
 private void getTaskDays(List<List<GwtUser>> stubList){
//   if(result != null && !result.isEmpty()) {
     //TODO
//     //==================================================
//     List<List<GwtUser>> stubList = new ArrayList<List<GwtUser>>();
//     List<GwtUser> sun, mon, tues, wed, thurs, fri, sat;
//     
//     sun = new ArrayList<GwtUser>();
//     sun.add(TurboPlan.loggedInUser);
//     mon = tues = wed = thurs = fri = sat = sun;
//     
//     stubList.add(sun);
//     stubList.add(mon);
//     stubList.add(tues);
//     stubList.add(wed);
//     stubList.add(thurs);
//     stubList.add(fri);
//     stubList.add(sat);
//     //==================================== GET FROM DATABASE
     
     //For Each Day, For each user in that day, add them to list and set that they have tasks on that day
     for(int dayIdx = 0; dayIdx < stubList.size(); dayIdx++) {
    	 List<GwtUser> usersForDay = stubList.get(dayIdx);
    	 int userTotal = usersForDay.size();
    	 
    	 //For each day, for all employees that have tasks on that day, set in their empInfo that they have tasks.
    	 for(int userIdx = 0; userIdx < userTotal; userIdx++) {
    		 GwtUser userToAdd = usersForDay.get(userIdx);
    		 String empName = userToAdd.getProfile().getFirstName() + " " + 
 				userToAdd.getProfile().getLastName();
    		 
    		 Log.info(empName + " Has Tasks on " + dayIdx);
    		 
    		 EmpInfo info = getEmpInfo(empName);
    		 WeekDay dayHasTask = WeekDay.SUN;
    		 
    		 switch(dayIdx) {
    		 	case 0 :
    		 		dayHasTask = WeekDay.SUN;
    		 		break;
    		 	case 1 :
    		 		dayHasTask = WeekDay.MON;
    		 		break;
    		 	case 2 :
    		 		dayHasTask = WeekDay.TUES;
    		 		break;
    		 	case 3 :
    		 		dayHasTask = WeekDay.WED;
    		 		break;
    		 	case 4 :
    		 		dayHasTask = WeekDay.THURS;
    		 		break;
    		 	case 5 :
    		 		dayHasTask = WeekDay.FRI;
    		 		break;
    		 	case 6 :
    		 		dayHasTask = WeekDay.SAT;
    		 		break;
    		    default :
    		    	//DO NOTHING
    		 }
    		 
    		 Log.info("Setting " + empName + " to have Task on " + dayHasTask.name());
    		 info.setHasTasksOnDay(dayHasTask);
    	 }
     }
     
     //Convert Employee Info to Records and Add Records to Store
     List<Record> empRecords = convertEmpInfoToRecords(empInfoList);
     addEmpRecords(empRecords);
// }
}

 

 private EmpInfo getEmpInfo(String nameToMatch) {
	 for(int idx = 0; idx < empInfoList.size(); idx++) {
		 EmpInfo info = empInfoList.get(idx);
		 
		 if(info.getEmpName().equals(nameToMatch)) {
			 return info;
		 }
	 }
	 
	 return new EmpInfo("");
 }
 
 
 private List<Record> convertEmpInfoToRecords(List<EmpInfo> infoList) {
	ArrayList<Record> empRecords = new ArrayList<Record>();
	
	for(int idx = 0; idx < infoList.size(); idx++) {
		Record newEmpRecord;
		EmpInfo info = infoList.get(idx);
		
		newEmpRecord = recordDef.createRecord(
			new Object[] {
				info.getEmpName(),
				getHasTaskLabel(info.hasTasksOnDay(WeekDay.SUN)),
				getHasTaskLabel(info.hasTasksOnDay(WeekDay.MON)),
				getHasTaskLabel(info.hasTasksOnDay(WeekDay.TUES)),
				getHasTaskLabel(info.hasTasksOnDay(WeekDay.WED)),
				getHasTaskLabel(info.hasTasksOnDay(WeekDay.THURS)),
				getHasTaskLabel(info.hasTasksOnDay(WeekDay.FRI)),
				getHasTaskLabel(info.hasTasksOnDay(WeekDay.SAT))
			}
		);
		
		empRecords.add(newEmpRecord);
	}
	
	return empRecords;	 
 }
 
 
 private String getHasTaskLabel(boolean hasTasks) {
	 if(hasTasks == true) {
		 return YES_TASKS;
	 }
	 else {
		 return NO_TASKS;
	 }
 }
 
 
 
 
 
 
 
// public void loadTaskData(List<GwtUser> empList, List<Date> weekDays) {
//	 Log.trace("Loading Task Data...");
//     ManageTasksServiceAsync taskService = ServiceManager.getManageTasksServiceAsync();
//     
//     for(int empIdx = 0; empIdx < empList.size(); empIdx++) {
//         for(int dayIdx = 0; dayIdx < weekDays.size(); dayIdx++) {
//	    	taskService.getTasksForUserOfDate(empList.get(empIdx).getUserID(), weekDays.get(dayIdx), new AsyncCallback<List<GwtTask>>() {
//	            /**
//	             * Handles if retrieve users call fails on server
//	             */
//	            public void onFailure(Throwable caught) {
//	                Log.warn("Error Getting Users From Database", caught);
//	            }
//	        
//	            /**
//	             * Handles if add user is successful on server. Add the user to a group of users to be added on the grid
//	             */
//	            public void onSuccess(List<GwtTask> taskList) {
//	                Log.trace("Succesfully Got Tasks From Database");
//	                
//	                for(GwtTask task : taskList) {
//	                	GwtTask[] owner = new GwtTask[1];
//	                	
//	                	task.getUsers().toArray(owner);
//	                	if(owner.length > 0) {
//	                		
//	                	}
//	                }
//
////	                Log.debug("...Finished Getting Tasks From Database!");
//	            }
//	    	});
//         }
//     }
//     
//     Log.debug("...Finished Loading Task Data!");
// }
    
 
// private String[] getEmployeeNames(List<GwtUser> userList) {
//	 String[] employeeNames[userList.size()];
//	 
//     //Get all employees for logged in user from the database
//     for(GwtUser user : userList) {
//         Log.info("Adding UserName to Owner ComboBox String[]");
//         owners.add(user.getProfile().getFirstName() + " " +
//                 user.getProfile().getLastName());
//     }
// }
    
    /**
     * Return the list of names of employees in given GwtUser List.
     */
 	private ArrayList<String> getEmpListNames(List<GwtUser> userList) {
 		Log.trace("Getting Employee List Names...");
 		
 		ArrayList<String> empListNames = new ArrayList<String>();
 		
 		for(GwtUser user : userList) {
 			Log.info("Getting Name: " + user.getProfile().getFirstName() + " " + user.getProfile().getLastName());
 			empListNames.add(user.getProfile().getFirstName() + " " + user.getProfile().getLastName());
 		}
 		
 		Log.debug("...Finished Getting Employee List Names!");
 		return empListNames;
 	}
    
    /**
     * Add a task as a record to the grid
     * @param task The task to add to the grid's records
     * @author Paul De Leon
     */
//    private void addEmployee(GwtTask task) {
//    	StringBuilder logBuilder = new StringBuilder("Adding ");
//    	logBuilder.append(task.getTitle()).append(" ");
//    	logBuilder.append(task.getSuggestedEndTime()).append(" ");
//    	logBuilder.append(task.getPriority());
//        Log.trace(logBuilder.toString());
//        
//        String ownerName;
//        
////        this.taskList.add(task);
//        
//        Iterator<GwtUser> ownerIter = task.getUsers().iterator();
//        if(ownerIter.hasNext()) {
//            GwtUser nextUsr = ownerIter.next();
//            ownerName = nextUsr.getProfile().getFirstName() + " " +
//                nextUsr.getProfile().getLastName();
//        }
//        else {
//            ownerName = "None";
//        }        
//        
//        Log.trace("the id is set to " + task.getTaskID().toString());
//        
//        this.getStore().add(recordDef.createRecord(
//                new Object[] {
//                	task.getTaskID().toString(),
//                    task.getTitle(),
//                    dateFormatter.format(task.getSuggestedStartTime()),
//                    dateFormatter.format(task.getSuggestedEndTime()),
//                    task.getPriority().toString(),
//                    task.getStatus().toString(),
//                    task.getCustomerFirstName() + " " + task.getCustomerLastName(),
//                    ownerName
//                }));
//        
//        
//        this.getStore().commitChanges();
//    }  


	/**
	 * Gets the days of the this week.
	 */
	@SuppressWarnings("deprecation")
	public Date[] getWeek(Date dayInThisWeek){
		Date[] week = new Date[7];
		
		Date today = dayInThisWeek;
		int todayNum = today.getDay();
		
		week[0] = getDateOffset(today, 0 - todayNum);  //SUN
		week[1] = getDateOffset(today, 1 - todayNum);  //MON
		week[2] = getDateOffset(today, 2 - todayNum);  //TUES
		week[3] = getDateOffset(today, 3 - todayNum);  //WED
		week[4] = getDateOffset(today, 4 - todayNum);  //THURS
		week[5] = getDateOffset(today, 5 - todayNum);  //FRI
		week[6] = getDateOffset(today, 6 - todayNum);  //SAT
		
		return week;
	}
	
	/**
	 * Returns a new date that is offsetted by the change.
	 * @param old the old date that is getting offsetted by
	 * @param change number of days offset
	 * @return the new date
	 */
	public Date getDateOffset(Date old, int change){
		return new Date(old.getTime() + (86400000L*change));		
		
	}
    
    
    
//	private Object[][] getCompanyData() {  
//	    return new Object[][]{  
//	            new Object[]{"3m Co", new Double(71.72), new Double(0.02),  
//	                    new Double(0.03), "9/1 12:00am", "MMM", "Manufacturing"},  
//	            new Object[]{"Alcoa Inc", new Double(29.01), new Double(0.42),  
//	                    new Double(1.47), "9/1 12:00am", "AA", "Manufacturing"},  
//	            new Object[]{"Altria Group Inc", new Double(83.81), new Double(0.28),  
//	                    new Double(0.34), "9/1 12:00am", "MO", "Manufacturing"},  
//	            new Object[]{"American Express Company", new Double(52.55), new Double(0.01),  
//	                    new Double(0.02), "9/1 12:00am", "AXP", "Finance"},  
//	            new Object[]{"American International Group, Inc.", new Double(64.13), new Double(0.31),  
//	                    new Double(0.49), "9/1 12:00am", "AIG", "Services"},  
//	            new Object[]{"AT&T Inc.", new Double(31.61), new Double(-0.48),  
//	                    new Double(-1.54), "9/1 12:00am", "T", "Services"},  
//	            new Object[]{"Boeing Co.", new Double(75.43), new Double(0.53),  
//	                    new Double(0.71), "9/1 12:00am", "BA", "Manufacturing"},  
//	            new Object[]{"Caterpillar Inc.", new Double(67.27), new Double(0.92),  
//	                    new Double(1.39), "9/1 12:00am", "CAT", "Services"},  
//	            new Object[]{"Citigroup, Inc.", new Double(49.37), new Double(0.02),  
//	                    new Double(0.04), "9/1 12:00am", "C", "Finance"},  
//	            new Object[]{"E.I. du Pont de Nemours and Company", new Double(40.48), new Double(0.51),  
//	                    new Double(1.28), "9/1 12:00am", "DD", "Manufacturing"}  
//	    };  
//	} 
}
