package edu.calpoly.csc.luna.turboplan.web.client.widget;

import java.util.Date;
import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.Widget;
import com.gwtext.client.widgets.DatePicker;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.event.DatePickerListenerAdapter;
import com.gwtext.client.widgets.form.Checkbox;
import com.gwtext.client.widgets.form.DateField;
import com.gwtext.client.widgets.form.Label;
import com.gwtext.client.widgets.form.event.CheckboxListenerAdapter;
import com.gwtext.client.widgets.layout.ColumnLayout;
import com.gwtext.client.widgets.layout.ColumnLayoutData;
import com.gwtext.client.widgets.layout.HorizontalLayout;
import com.gwtext.client.widgets.layout.VerticalLayout;
import com.gwtext.client.widgets.menu.Separator;

/**
 * The Schedule Generation profile panel which displays the generation options
 * @author Bradley Barbee
 *
 */
public class ScheduleProfilePanel extends Panel {
	
	private static final String SIMPLE_CLASSNAME = "ScheduleProfilePanel";

    private final int dateFieldWidth = 100;
    private final DateTimeFormat dateFormatter = DateTimeFormat.getFormat("MM/dd/yy");
	
	private static boolean schedGen_directions = false;
	private static boolean schedGen_minDate = false;
	private static boolean schedGen_maxDate = false;
	private static boolean schedGen_random = false;
	private static boolean schedGen_priority = false;
	private static boolean schedGen_estTime = false;
	private static boolean schedGen_shortest = false;
	private static boolean schedGen_longest = false;
	private static boolean schedGen_date = false;
	private static boolean schedGen_startDate = false;
	private static boolean schedGen_dueDate = false;
	private static boolean schedGen_nearDue = false;

    private static final int LABEL_WIDTH = 125;
    private final int vertPadding = 2;


    private static final String infoLbl =
    	"Generating a schedule will assign employees to tasks automatically. All checked options, if any, will be considered in the schedule generation process.";
    private Checkbox directionsCheckbox;
    private static final String directionsLbl_check =
    	"CHECKED: Assign tasks, considering distance to the next task. All employees will be checked for the least distance to the task from previous tasks. Time to travel to each task will also be considered.";
    private static final String directionsLbl_uncheck =
    	"UNCHECKED: Assign tasks without any distance considerations";
    private static final String directionsLbl_disclaimer =
    	"Disclaimer: We are slightly violating Google Maps Terms of Service, hence, this cannot be used in the final product but is here for proof of concept purposes.";
    private static DateField minDateField;
    private Checkbox minDateCheckbox;
    private static final String minDateLbl_check =
    	"CHECKED: Assign no tasks before the provided date";
    private static final String minDateLbl_uncheck =
    	"UNCHECKED: Assign all tasks with any start date";
    private static DateField maxDateField;
    private Checkbox maxDateCheckbox;
    private static final String maxDateLbl_check =
    	"CHECKED: Assign no tasks after the provided date";
    private static final String maxDateLbl_uncheck =
    	"UNCHECKED: Assign all tasks with any end date";
    private Checkbox randomCheckbox;
    private static final String randomLbl_check =
    	"CHECKED: Assign a random employee, giving an equal oppurtunity to be scheduled";
    private static final String randomLbl_uncheck =
    	"UNCHECKED: Assign the first available employee from an employee list for each task";
    private Checkbox priorityCheckbox;
    private static final String priorityLbl_check =
    	"CHECKED: Assign tasks from highest to lowest priority";
    private static final String priorityLbl_uncheck =    
    	"UNCHECKED: Assign tasks from an unordered task list";
    private Checkbox estTimeCheckbox;
    private static final String estTimeLbl =
    	"CHECKED: Enabled    UNCHECKED: Disabled";
    private RadioButton shortestButton;
    private static final String shortestLbl =
    	"Assign tasks with shortest estimated times first";
    private static RadioButton longestButton;
    private static final String longestLbl =
    	"Assign tasks with longest estimated times first";
    private Checkbox dateCheckbox;
    private static final String dateLbl =
    	"CHECKED: Enabled    UNCHECKED: Disabled";    
    private RadioButton startButton;
    private static final String startLbl =
    	"Assign tasks with earliest start dates first";
    private RadioButton dueButton;
    private static final String dueLbl =
    	"Assign tasks with earliest due dates first";
    private Checkbox reverseCheckbox;
    private static final String reverseLbl_check =
    	"CHECKED: Search for employees available closest to a task's due date";
    private static final String reverseLbl_uncheck =
    	"UNCHECKED: Search for employees available closest to a task's start date";
   

    /**
     * Constructor of a task profile panel
     */
    public ScheduleProfilePanel() {
    	
        super();
        Log.info(SIMPLE_CLASSNAME + " is constructing");
        
        setFrame(true);
        this.setHeight(350);
        this.setAutoScroll(true);

        final Panel rangePanel = buildRangePanel();
        final Panel directionsPanel = buildDirectionsPanel();
        final Panel randomPanel = buildRandomPanel();
        final Panel priorityPanel = buildPriorityPanel();
        final Panel estTimePanel = buildEstTimePanel();
        final Panel datePanel = buildDatePanel();
        final Panel reversePanel = buildReversePanel();
        
        add(new Label(infoLbl));
        add(new Separator());
        add(rangePanel);
        add(directionsPanel);
        add(randomPanel);
        add(priorityPanel);
        add(estTimePanel);
        add(datePanel);
        add(reversePanel);
        
        Log.debug(SIMPLE_CLASSNAME + " is constructed");
    } 

    /**
     * Build Date Range Assignment Panel.
     * @author Bradley Barbee
     */
    private Panel buildRangePanel() {
    	
        final Panel rangePanel = new Panel("Assign Between Date Range (Inclusive)");
        rangePanel.setLayout(new ColumnLayout());
        Panel minDatePanel = new Panel();
        minDatePanel.setLayout(new VerticalLayout());
        Panel maxDatePanel = new Panel();        
        maxDatePanel.setLayout(new VerticalLayout());
        Panel minDateFieldPanel = new Panel();
        minDateFieldPanel.setLayout(new HorizontalLayout(2));
        Panel maxDateFieldPanel = new Panel();
        maxDateFieldPanel.setLayout(new HorizontalLayout(2));
        
        minDateCheckbox = new Checkbox();
        maxDateCheckbox = new Checkbox();
        
        minDateCheckbox.addListener(new CheckboxListenerAdapter() {

			public void onCheck(Checkbox field, boolean checked) {
        	   	if(checked) {
        	   		schedGen_minDate = true;
        	        minDateField.setDisabled(false);
            		Log.trace("Minimum Start Date Generation Selected");
            	} else {
            		schedGen_minDate = false;
            		minDateField.setDisabled(true);
            		Log.trace("Minimum Start Date Generation Unselected");
            	}
			}
        });
        
        maxDateCheckbox.addListener(new CheckboxListenerAdapter() {

			public void onCheck(Checkbox field, boolean checked) {
        	   	if(checked) {
        	   		schedGen_maxDate = true;
        	        maxDateField.setDisabled(false);
            		Log.trace("Maximum End Date Generation Selected");
            	} else {
            		schedGen_maxDate = false;
                    maxDateField.setDisabled(true);
            		Log.trace("Maximum End Date Generation Unselected");
            	}
			}
        });
                
        minDateField = new DateField("Start Date*", "startDate", dateFieldWidth);     
        maxDateField = new DateField("End Date*", "endDate", dateFieldWidth);
                
        minDateField.setValue(dateFormatter.format(new Date())); 
        maxDateField.setValue(dateFormatter.format(new Date()));
        minDateField.setDisabled(true);
        maxDateField.setDisabled(true);

        minDateField.addListener(new DatePickerListenerAdapter() {
            public void onSelect(DatePicker dataPicker, java.util.Date date) {
                if(date.after(maxDateField.getValue())) {
                    maxDateField.setValue(dateFormatter.format(date));
                }
            }
        });
        
        maxDateField.addListener(new DatePickerListenerAdapter() {
            public void onSelect(DatePicker dataPicker, java.util.Date date) {
                if(date.before(minDateField.getValue())) {
                    minDateField.setValue(dateFormatter.format(date));
                }
            }
        });
        
        minDateFieldPanel.add(minDateCheckbox);
        minDateFieldPanel.add(minDateField);
        minDatePanel.add(minDateFieldPanel);
        minDatePanel.add(new Label(minDateLbl_check));
        minDatePanel.add(new Label(minDateLbl_uncheck));
        maxDateFieldPanel.add(maxDateCheckbox);
        maxDateFieldPanel.add(maxDateField);
        maxDatePanel.add(maxDateFieldPanel);
        maxDatePanel.add(new Label(maxDateLbl_check));
        maxDatePanel.add(new Label(maxDateLbl_uncheck));
        rangePanel.add(minDatePanel, new ColumnLayoutData(.5));
        rangePanel.add(maxDatePanel, new ColumnLayoutData(.5));
                
        return rangePanel;
    }    
    
    /**
     * Returns the minimum start date field.
     * @return The minimum start date field for the check box
     * @author Bradley Barbee
     */
    public static long getMinDate() {
    	if(schedGen_minDate) {
    		return minDateField.getValue().getTime();
    	} else {
    		return 0;
    	}
    }
    
    /**
     * Returns the maximum end date field.
     * @return The maximum end date field for the check box
     * @author Bradley Barbee
     */
    public static long getMaxDate() {
    	if(schedGen_maxDate) {
    		return maxDateField.getValue().getTime();
    	} else {
    		return 0;
    	}
    }

    /**
     * Build Directions Assignment Panel.
     * @author Bradley Barbee
     */
    private Panel buildDirectionsPanel() {
    	
        final Panel directionsPanel = new Panel("Shortest Distance Between Tasks");
        directionsPanel.setLayout(new VerticalLayout());
        
        directionsCheckbox = new Checkbox();
        
        directionsCheckbox.addListener(new CheckboxListenerAdapter() {

			public void onCheck(Checkbox field, boolean checked) {
        	   	if(checked) {
        	   		schedGen_directions= true;
            		Log.trace("Shortest Distance Between Tasks Generation Selected");
            	} else {
            		schedGen_directions = false;
            		Log.trace("Shortest Distance Between Tasks Generation Unselected");
            	}
			}
        });
        
        directionsPanel.add(directionsCheckbox);
        directionsPanel.add(new Label(directionsLbl_check));
        directionsPanel.add(new Label(directionsLbl_uncheck));
        Label disclaimer = new Label(directionsLbl_disclaimer);
        disclaimer.setDisabled(true);
        directionsPanel.add(disclaimer);
        
        
        return directionsPanel;
    }    
    
    /**
     * Returns the directions field.
     * @return The directions field for the check box
     * @author Bradley Barbee
     */
    public static boolean getDirections() {
    	return schedGen_directions;
    }
    
    /**
     * Build Random Employee Assignment Panel.
     * @author Bradley Barbee
     */
    private Panel buildRandomPanel() {
    	
        final Panel randomPanel = new Panel("Assign Random Employees");
        randomPanel.setLayout(new VerticalLayout());
        
        randomCheckbox = new Checkbox();
        
        randomCheckbox.addListener(new CheckboxListenerAdapter() {

			public void onCheck(Checkbox field, boolean checked) {
        	   	if(checked) {
        	   		schedGen_random = true;
            		Log.trace("Random Schedule Generation Selected");
            	} else {
            		schedGen_random = false;
            		Log.trace("Random Schedule Generation Unselected");
            	}
			}
        });
        
        randomPanel.add(randomCheckbox);
        randomPanel.add(new Label(randomLbl_check));
        randomPanel.add(new Label(randomLbl_uncheck));
        
        return randomPanel;
    }    
    
    /**
     * Returns the random field.
     * @return The random field for the check box
     * @author Bradley Barbee
     */
    public static boolean getRandom() {
    	return schedGen_random;
    }

    /**
     * Build Highest Priority First Panel.
     * @author Bradley Barbee
     */
    private Panel buildPriorityPanel() {
    	
        final Panel priorityPanel = new Panel("Assign By Highest Priority");
        priorityPanel.setLayout(new VerticalLayout());
        
        priorityCheckbox = new Checkbox();
        
        priorityCheckbox.addListener(new CheckboxListenerAdapter() {

			public void onCheck(Checkbox field, boolean checked) {
        	   	if(checked) {
        	   		schedGen_priority = true;
            		Log.trace("Assign by Highest Priority Schedule Generation Selected");
            	} else {
            		schedGen_priority = false;
            		Log.trace("Assign by Highest Priority Schedule Generation Unselected");
            	}
			}
        });
        
        priorityPanel.add(priorityCheckbox);
        priorityPanel.add(new Label(priorityLbl_check));
        priorityPanel.add(new Label(priorityLbl_uncheck));
        
        return priorityPanel;
    }    
    
    /**
     * Returns the priority field.
     * @return The priority field for the check box
     * @author Bradley Barbee
     */
    public static boolean getPriority() {
    	return schedGen_priority;
    }

    /**
     * Build Estimated Time First Panel.
     * @author Bradley Barbee
     */
    private Panel buildEstTimePanel() {
    	
        final Panel estTimePanel = new Panel("Assign By Estimated Time");
        estTimePanel.setLayout(new VerticalLayout());
       
        estTimeCheckbox = new Checkbox(estTimeLbl);
        shortestButton = new RadioButton("estTime",shortestLbl);
        longestButton = new RadioButton("estTime",longestLbl);
            
        estTimeCheckbox.addListener(new CheckboxListenerAdapter() {
        	public void onCheck(Checkbox field, boolean checked) {
        	   	if(checked) {
        	   		schedGen_estTime = true;
        	   		shortestButton.setEnabled(true);
        	   		longestButton.setEnabled(true);
            		Log.trace("Assign by Estimated Time Schedule Generation Enabled");
            	} else {
            		schedGen_estTime = false;
               		shortestButton.setEnabled(false);
        	   		longestButton.setEnabled(false);
        	   		Log.trace("Assign by Estimated Time Schedule Generation Disabled");
            	}
        	}
        });
        
        shortestButton.setEnabled(false);
        shortestButton.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				schedGen_shortest = true;
				schedGen_longest = false;
        		Log.trace("Assign by Shortest Estimated Time Schedule Generation Selected");				
			}
        });
        
        longestButton.setEnabled(false);
        longestButton.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				schedGen_shortest = false;
				schedGen_longest = true;
        		Log.trace("Assign by Longest Estimated Time Schedule Generation Selected");				
			}
        });   

        estTimePanel.add(estTimeCheckbox);
        estTimePanel.add(shortestButton);
        estTimePanel.add(longestButton);
        
        return estTimePanel;
    }    
    
    /**
     * Returns the shortest time field.
     * @return The shortest time field for the check box
     * @author Bradley Barbee
     */
    public static boolean getShortest() {
    	return schedGen_estTime && schedGen_shortest;
    }  

    /**
     * Returns the longest time field.
     * @return The longest time field for the check box
     * @author Bradley Barbee
     */
    public static boolean getLongest() {
    	return schedGen_estTime && schedGen_longest;
    }    

    /**
     * Build Date First Panel.
     * @author Bradley Barbee
     */
    private Panel buildDatePanel() {
    	
        final Panel datePanel = new Panel("Assign By Start/Due Date");
        datePanel.setLayout(new VerticalLayout());
       
        dateCheckbox = new Checkbox(dateLbl);
        startButton = new RadioButton("date",startLbl);
        dueButton = new RadioButton("date",dueLbl);
            
        dateCheckbox.addListener(new CheckboxListenerAdapter() {
        	public void onCheck(Checkbox field, boolean checked) {
        	   	if(checked) {
        	   		schedGen_date = true;
        	   		startButton.setEnabled(true);
        	   		dueButton.setEnabled(true);
            		Log.trace("Assign by Date Schedule Generation Enabled");
            	} else {
        	   		schedGen_date = false;
        	   		startButton.setEnabled(false);
        	   		dueButton.setEnabled(false);
            		Log.trace("Assign by Date Schedule Generation Enabled");
            	}
        	}
        });
        
        startButton.setEnabled(false);
        startButton.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				schedGen_startDate = true;
				schedGen_dueDate = false;
        		Log.trace("Assign by Start Date Schedule Generation Selected");				
			}
        });
        
        dueButton.setEnabled(false);
        dueButton.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				schedGen_startDate = false;
				schedGen_dueDate = true;
        		Log.trace("Assign by Due Date Schedule Generation Selected");				
			}
        });   

        datePanel.add(dateCheckbox);
        datePanel.add(startButton);
        datePanel.add(dueButton);
        
        return datePanel;
    }    
        
    /**
     * Returns the start date field.
     * @return The start date field for the check box
     * @author Bradley Barbee
     */
    public static boolean getStart() {
    	return schedGen_date && schedGen_startDate;
    }  

    /**
     * Returns the due date field.
     * @return The due date field for the check box
     * @author Bradley Barbee
     */
    public static boolean getDue() {
    	return schedGen_date && schedGen_dueDate;
    }    

    /**
     * Build Nearest Due Date Panel.
     * @author Bradley Barbee
     */
    private Panel buildReversePanel() {
    	
        final Panel reversePanel = new Panel("Assign Closest to Due Date");
        reversePanel.setLayout(new VerticalLayout());
        
        reverseCheckbox = new Checkbox();
        
        reverseCheckbox.addListener(new CheckboxListenerAdapter() {

			public void onCheck(Checkbox field, boolean checked) {
        	   	if(checked) {
        	   		schedGen_nearDue = true;
            		Log.trace("Assign Closest to Due Date Schedule Generation Selected");
            	} else {
            		schedGen_nearDue = false;
            		Log.trace("Assign Closest to Due Date Schedule Generation Unselected");
            	}
			}
        });
        
        reversePanel.add(reverseCheckbox);
        reversePanel.add(new Label(reverseLbl_check));
        reversePanel.add(new Label(reverseLbl_uncheck));
        
        return reversePanel;
    }    
    
    /**
     * Returns the nearest due date field.
     * @return The nearest due date field for the check box
     * @author Bradley Barbee
     */
    public static boolean getReverse() {
    	return schedGen_nearDue;
    }  
   
    /**
     * Clears the profile panel
     */
    public void clearScheduleProfile() {
    	schedGen_random = false;
    	schedGen_priority = false;
    	schedGen_estTime = false;
    	schedGen_shortest = false;
    	schedGen_longest = false;
    	schedGen_date = false;
    	schedGen_startDate = false;
    	schedGen_dueDate = false;
    	schedGen_nearDue = false;
        Log.info("done clearing");
    } 
}