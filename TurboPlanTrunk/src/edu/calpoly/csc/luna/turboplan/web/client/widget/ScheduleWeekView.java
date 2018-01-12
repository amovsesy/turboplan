package edu.calpoly.csc.luna.turboplan.web.client.widget;

import java.util.Date;

import com.allen_sauer.gwt.log.client.Log;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.data.FieldDef;
import com.gwtext.client.data.RecordDef;
import com.gwtext.client.data.StringFieldDef;
import com.gwtext.client.widgets.Window;
import com.gwtext.client.widgets.grid.ColumnConfig;
import com.gwtext.client.widgets.grid.ColumnModel;
import com.gwtext.client.widgets.grid.GridPanel;
import com.gwtext.client.widgets.grid.event.GridCellListenerAdapter;

import edu.calpoly.csc.luna.turboplan.web.client.TurboPlan;
import edu.calpoly.csc.luna.turboplan.web.client.model.GwtTask;
import edu.calpoly.csc.luna.turboplan.web.client.model.GwtUser;
import edu.calpoly.csc.luna.turboplan.web.client.window.CalTaskWindow;

public class ScheduleWeekView extends ScheduleGrid {
	private static final String SIMPLE_CLASSNAME = "ScheduleGrid.WeekView";

	private static final String TIME_RECORDDEF = "time";
	private static final String SUNDAY_RECORDDEF = "sun";
	private static final String MONDAY_RECORDDEF = "mon";
	private static final String TUESDAY_RECORDDEF = "tues";
	private static final String WEDNESDAY_RECORDDEF = "weds";
	private static final String THURSDAY_RECORDDEF = "thurs";
	private static final String FRIDAY_RECORDDEF = "fri";
	private static final String SATURDAY_RECORDDEF = "sat";

	private GwtUser user;
	private Date today;
	private Date sun;
	private Date sat;

	private String[] montimes;
	private String[] tuestimes;
	private String[] wedstimes;
	private String[] thurtimes;
	private String[] fritimes;
	private String[] sattimes;
	private String[] suntimes;
	private long[][] ids;
	
	
	public ScheduleWeekView(){
		super(new RecordDef(new FieldDef[] {
				new StringFieldDef(TIME_RECORDDEF), new StringFieldDef(SUNDAY_RECORDDEF), 
				new StringFieldDef(MONDAY_RECORDDEF), new StringFieldDef(TUESDAY_RECORDDEF), 
				new StringFieldDef(WEDNESDAY_RECORDDEF), new StringFieldDef(THURSDAY_RECORDDEF), 
				new StringFieldDef(FRIDAY_RECORDDEF), new StringFieldDef(SATURDAY_RECORDDEF)
		}), new ColumnModel(new ColumnConfig[] {
				new ColumnConfig("Time", TIME_RECORDDEF, 55, false),
				new ColumnConfig("Sunday", SUNDAY_RECORDDEF, 98, false, new AvialRender("sun")),
				new ColumnConfig("Monday", MONDAY_RECORDDEF, 98, false, new AvialRender("mon")),
				new ColumnConfig("Tuesday", TUESDAY_RECORDDEF, 98, false, new AvialRender("tues")),
				new ColumnConfig("Wednesday", WEDNESDAY_RECORDDEF, 98, false, new AvialRender("weds")),
				new ColumnConfig("Thrusday", THURSDAY_RECORDDEF, 98, false, new AvialRender("thurs")),
				new ColumnConfig("Friday", FRIDAY_RECORDDEF, 98, false, new AvialRender("fri")),
				new ColumnConfig("Saturday", SATURDAY_RECORDDEF, 98, false, new AvialRender("sat"))
		}), TurboPlan.STANDARD_PAGE_WIDTH - 23);
		Log.info("Constructing " + SIMPLE_CLASSNAME);

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
                Log.trace("col gotten = "+ rowIndex);
                
                final GwtTask task = getTaskById(ids[colIndex-1][rowIndex]);
                
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
		
		
		user  = TurboPlan.loggedInUser; 
		
		montimes = new String[48];
		tuestimes = new String[48];
		wedstimes = new String[48];
		thurtimes = new String[48];
		fritimes = new String[48];
		sattimes = new String[48];
		suntimes = new String[48];
		ids = new long[7][48];
		
		fillDay(montimes);
		fillDay(tuestimes);
		fillDay(wedstimes);
		fillDay(thurtimes);
		fillDay(fritimes);
		fillDay(sattimes);
		fillDay(suntimes);
		
		today = new Date();
		getSunSat();
		
		fillTimes(); //gets availabilities
		getWeekTasks(); //gets the tasks 
		
		getAvail(); //puts in grid
				
		setTitle(sun.toGMTString() + " - " + sat.toGMTString());

		Log.debug("Done constructing " + SIMPLE_CLASSNAME);
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
			
			getStore().add(createRecord(new Object[] {
					
					String.valueOf(time) + ":00 " + am, 
					String.valueOf(suntimes[i*2]),
					String.valueOf(montimes[i*2]),  
					String.valueOf(tuestimes[i*2]),  
					String.valueOf(wedstimes[i*2]),  
					String.valueOf(thurtimes[i*2]),  
					String.valueOf(fritimes[i*2]),  
					String.valueOf(sattimes[i*2])
			}));
			
			getStore().commitChanges();
			
			getStore().add(createRecord(new Object[] {
					
					String.valueOf(time) + ":30 " + am, 
					String.valueOf(suntimes[i*2+1]),
					String.valueOf(montimes[i*2+1]),  
					String.valueOf(tuestimes[i*2+1]),  
					String.valueOf(wedstimes[i*2+1]),  
					String.valueOf(thurtimes[i*2+1]),  
					String.valueOf(fritimes[i*2+1]),  
					String.valueOf(sattimes[i*2+1])  
			}));
			
			getStore().commitChanges();
		}
	}
	
	/**
	 * Gets the time available for the week for the user.
	 */
	private void fillTimes(){
		if(user.getProfile().getAvailability() != null){
			for(int i = 0; i < 7; i++){
				for (int j = 0; j < 48; j++) {
					if(user.getProfile().getAvailability()[i][j]){
						addTimeToDay(i, j, " ");
					}
				}
			}
		}
	}	

	/**
	 * Gets the user's tasks for the week.
	 */
	private void getWeekTasks(){
		GwtTask temptask;
		Date start;
		Date end;
		
		if(res != null){
			Log.trace("NOTNULL RES ");			
			
			for (GwtTask task : res) {
				if(task != null){
					temptask = task;
			Log.trace("res is gwttask = task title = "+ task.getTitle());				
			Log.trace("not null");		
					start = temptask.getScheduledStartTime();
					end = temptask.getScheduledEndTime();
					
					if(isInWeek(start, end) && (start != null && end != null)){
			Log.trace("in this week not null");			
						addTime(start, end, temptask.getTitle(), temptask.getTaskID());
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
		Date temp;
		int stindex;
		int endindex;
		
		if(!isSameDay(start, end)){
			temp = getDateOffset(end, -1);
			if(isInWeek(start, temp)){
				addTime(start, temp, value, id);	
			}
			temp = getDateOffset(start, 1);
			if(isInWeek(end, temp)){
				addTime(end, temp, value, id);	
			}
		}
		
		stindex = start.getHours()*2;
		endindex = end.getHours()*2;
		
		if(start.getMinutes() >= 30){
			stindex += 1;
		}
		if(end.getMinutes() >= 30){
			endindex +=1;
		}

		Log.trace("start = " +start +",  index = "+stindex);
		Log.trace("end = " +end +",  index = "+endindex);
		
		for(int index = stindex; index < endindex; index++){
			addTimeToDay(start.getDay(), index, value);
			ids[start.getDay()][index] = id;
		}
	}
	
	/**
	 * Checks to see if the start or end time are in the current week
	 * @param start the start time for a task
	 * @param end the end time for a task
	 * @return true if the end or start are in the week
	 */
	public boolean isInWeek(Date start, Date end){
		//Start in week
		if(sun != null && sat != null && start != null && end != null){
		
		if(sun.getTime()<= start.getTime() && sat.getTime() >= end.getTime()){	
			return true;
		}
		}
		return false; 
	}
	
	/**
	 * Places the value at in that day and time.
	 * @param day the day of the week that is being changed
	 * @param index the time that is being changed
	 * @param value the value that they time is becoming
	 */
	public void addTimeToDay(int day, int index, String value){
		
	//Log.info("day = " +day+", index = "+index);	
		switch(day){
		case 0: suntimes[index] = value; break; 
		case 1: montimes[index] = value; break;
		case 2: tuestimes[index] = value; break;
		case 3: wedstimes[index] = value; break;
		case 4: thurtimes[index] = value; break;
		case 5: fritimes[index] = value; break;
		case 6: sattimes[index] = value; break;
		}
	}
	
	/**
	 * Gets the end days of the this week.
	 */
	@SuppressWarnings("deprecation")
	public void getSunSat(){
		sun = sat = today = new Date();
		
		if(sun.getDay() != 0){
			sun = getDateOffset(sun, -1 * sun.getDay());
		}
		sun.setHours(0);
		sun.setMinutes(0);
		sun.setSeconds(0);
			
		if(sat.getDay() != 6){
			sat = getDateOffset(sat, 6 - sat.getDay());
		}
		sat.setHours(23);
		sat.setMinutes(59);
		sat.setSeconds(59);
		
	//Log.info("sun " + sun.toString());
	//Log.info("sat " + sat.toString());
		
	}
	
	/**
	 * Returns a new date that is offsetted by the change.
	 * @param old the old date that is getting offsetted by
	 * @param change number of days offset
	 * @return the new date
	 */
	public Date getDateOffset(Date old, int change){
		return new Date((86400000L*change) + old.getTime());		
		
	}
	
	/**
	 * Takes the current month in int and converts it to a string representation.
	 * @return a string of the current month
	 */
	@SuppressWarnings("deprecation")
	private String getMonth() {
		String[] months = new String[12];

		months[0] = "January";
		months[1] = "Febuary";
		months[2] = "March";
		months[3] = "April";
		months[4] = "May";
		months[5] = "June";
		months[6] = "July";
		months[7] = "August";
		months[8] = "September";
		months[9] = "October";
		months[10] = "November";
		months[11] = "December";

		return months[new Date().getMonth()];
	}

}
