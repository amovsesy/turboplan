package edu.calpoly.csc.luna.turboplan.web.client.widget;

import java.util.Date;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.data.FieldDef;
import com.gwtext.client.data.RecordDef;
import com.gwtext.client.data.StringFieldDef;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.Window;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.grid.ColumnConfig;
import com.gwtext.client.widgets.grid.ColumnModel;
import com.gwtext.client.widgets.grid.GridPanel;
import com.gwtext.client.widgets.grid.event.GridCellListenerAdapter;

import edu.calpoly.csc.luna.turboplan.web.client.TurboPlan;
import edu.calpoly.csc.luna.turboplan.web.client.model.GwtTask;
import edu.calpoly.csc.luna.turboplan.web.client.model.GwtUser;
import edu.calpoly.csc.luna.turboplan.web.client.window.CalTaskWindow;

public class ScheduleDayView extends ScheduleGrid {
		private static final String SIMPLE_CLASSNAME = "ScheduleGrid.DayView";

		private static final String TIME_RECORDDEF = "time";
		private static final String DAY_RECORDDEF = "day";
		
		private static long dateMill = 1000*60*60*24; 
		
		private final DateTimeFormat fullDateFormatter = DateTimeFormat.getFormat("E M/dd/yy");
		
		private GwtUser user;
		private Date today;
		private String[] times;
		private long[] ids;
		
		public ScheduleDayView() {
			super(new RecordDef(new FieldDef[] {
					new StringFieldDef(TIME_RECORDDEF), new StringFieldDef(DAY_RECORDDEF)
			}), new ColumnModel(new ColumnConfig[] {
					new ColumnConfig("Time", TIME_RECORDDEF, 55, false), 
					new ColumnConfig("Availability", DAY_RECORDDEF, 100, false, new AvialRender("day"))
			}), TurboPlan.STANDARD_PAGE_WIDTH - 30);
			Log.info("Constructing " + SIMPLE_CLASSNAME);
			
			setupButtons();

			addGridCellListener(new GridCellListenerAdapter() {   
	            public void onCellClick(GridPanel grid, int rowIndex, int colindex,   
	                    EventObject e) {   
	            }   

				public void onCellContextMenu(GridPanel grid, int rowIndex,   
				                        int colIndex, EventObject e) {   
				}   
				
				public void onCellDblClick(GridPanel grid, int rowIndex,   
				                        int colIndex, EventObject e) {   
            
	                
	                Log.trace("before task");
	                 //   final GwtTask task = myGrid.getTaskByTitle(grid.getSelectionModel().getSelected().toString());
	                		//getAsString());
	                Log.trace("col gotten = "+ rowIndex);
	                
	                final GwtTask task = getTaskById(ids[rowIndex]);
	                
	                Log.trace("tasktitle  = " +task.getTitle()+", taskid = "+task.getTaskID());
	                
	                //Need to get task by id... etiher by having list of task or a list if ids
	               // final GwtTask task = grid.getSelectionModel().getSelected().getAsString("day");
					final CalTaskWindow taskWin = new CalTaskWindow("Selected Task Profile", task);
	                taskWin.changeSaveBtnLabel();
	                taskWin.setCloseAction(Window.HIDE); 
	                taskWin.setPlain(true);
	                taskWin.setModal(true);
	                taskWin.setVisible(true); 
	                
	                Log.trace("after task");
	                
	                Log.trace("before setTaskProfile");
	                taskWin.getTaskProfilePanel().setTaskProfile(task);
	                Log.trace("after setTaskProfile");
	                
	                taskWin.show();
	                Log.trace("showed windw");
	            }   
			});
			
			
			user = TurboPlan.loggedInUser;

			today = new Date();

			times = new String[48];
			ids = new long[48];
			
			displayDay();
			Log.debug("Done constructing " + SIMPLE_CLASSNAME);
		}
		
		/**
		 * Inputs the data into the grid with the current day.
		 */
		private void displayDay(){
			getStore().removeAll();
			
			fillDay(times);
			fillTimes();

			getTodayTasks();
			getAvail();

			setTitle(fullDateFormatter.format(today));
			
			Log.trace("Refresh the grid");
		}
		
	    private void setupButtons() {
	        Log.trace("Setting up Buttons...");
	        //Buttons on This Task Panel
	        Button prev = new Button("Previous");
	        Button tod = new Button("Today");
	        Button next = new Button("Next");
	        
	        prev.addListener(new ButtonListenerAdapter() {
	            public void onClick(Button button, EventObject e) {
	                //Change the sat and sun to a prev
	            	today = new Date(today.getTime() - dateMill); 
	                
	                displayDay();
	            }
	        });
	        
	        tod.addListener(new ButtonListenerAdapter() {
	            public void onClick(Button button, EventObject e) {
	                //Original sat and sun
	            	today = new Date();
	        		
	        		displayDay();
	            	
	            }
	        });
	        
	        next.addListener(new ButtonListenerAdapter() {
	            public void onClick(Button button, EventObject e) {
	                //Change the date to a next
	            	today = new Date(today.getTime() + dateMill); 
	                
	                displayDay();
	            }
	        });
	        
	        //Add Button to top ToolBar
	        this.setTopToolbar(new Button[] { prev, tod, next });
	        
	        Log.trace("Buttons Setup!");
	    }
		
		
		/**
		 * Fills the grid with the avail, nonavail and tasks.
		 */
		private void getAvail() {
			String am;
			int time;
			
			for (int i = 0; i < 24; i++) {
				time = i%12;
				if(i < 12){
					am = "am";
				}
				else{
					am = "pm";
				}
				if(i %12 == 0){
					time = 12;
				}
				
				getStore().add(
						createRecord(new Object[] { String.valueOf(time) + ":00 " + am,
								String.valueOf(times[i * 2]) }));
				getStore().commitChanges();

				getStore().add(
						createRecord(new Object[] { String.valueOf(time) + ":30 " + am,
								String.valueOf(times[i * 2 + 1]) }));
				getStore().commitChanges();
			}
		}

		/**
		 * Gets the time available for today for the user.
		 */
		@SuppressWarnings("deprecation")
		private void fillTimes() {
			if(user.getProfile().getAvailability() != null){
				for (int j = 0; j < 48; j++) {
					if(user.getProfile().getAvailability()[today.getDay()][j]){
					times[j] = "Available";
					}
				}
			}
		}

		/**
		 * Gets the user's tasks for today.
		 */
		private void getTodayTasks(){
			GwtTask temptask;
			Date start;
			Date end;
			
			if(res != null){
				for (GwtTask task : res) {
			
					temptask = task;
					if(task != null){// && isSameDay(today, task.getScheduledStartTime())){
						start = temptask.getScheduledStartTime();
						end = temptask.getScheduledEndTime();
					
						if(start != null && end!= null && isSameDay(today, task.getScheduledStartTime())){
							addTime(start, end, temptask.getTitle(), temptask.getTaskID());
						//	res.add(temptask);
						}
					}
				}
			}
			
		}	

		/**
		 * Adds the task to the grid so that is shows up on the calendar
		 * @param start date the task start
		 * @param end date the task ends
		 * @param value task title
		 */
		@SuppressWarnings("deprecation")
		public void addTime(Date start, Date end, String value, long id){
			int stindex;
			int endindex;
			
			//Checks to make sure the task starts on today
		/*	if(today.getDate() != start.getDate()){
				start = today;
				start.setHours(0);
			}
			//Checks to make sure the task ends on today
			else if(today.getDate() != end.getDate()){
				end = today;
				end.setHours(24);
			}*/
						
			stindex = start.getHours()*2;
			endindex = end.getHours()*2;
			
			if(start.getMinutes() >= 30){
				stindex += 1;
			}
			if(end.getMinutes() >= 30){
				endindex +=1;
			}
		
			for(int index = stindex; index <= endindex; index++){
				times[index] = value;
				ids[index] = id;
			}
		}
	}
