package edu.calpoly.csc.luna.turboplan.web.client.widget;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import com.gwtext.client.widgets.form.Field;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Widget;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.data.Record;
import com.gwtext.client.data.SimpleStore;
import com.gwtext.client.data.Store;
import com.gwtext.client.widgets.BoxComponent;
import com.gwtext.client.widgets.Component;
import com.gwtext.client.widgets.DatePicker;
import com.gwtext.client.widgets.MessageBox;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.event.DatePickerListenerAdapter;
import com.gwtext.client.widgets.form.ComboBox;
import com.gwtext.client.widgets.form.DateField;
import com.gwtext.client.widgets.form.Field;
import com.gwtext.client.widgets.form.Label;
import com.gwtext.client.widgets.form.NumberField;
import com.gwtext.client.widgets.form.TextArea;
import com.gwtext.client.widgets.form.TextField;
import com.gwtext.client.widgets.form.TimeField;
import com.gwtext.client.widgets.form.event.ComboBoxCallback;
import com.gwtext.client.widgets.form.event.ComboBoxListener;
import com.gwtext.client.widgets.form.event.TextFieldListener;
import com.gwtext.client.widgets.layout.AnchorLayoutData;
import com.gwtext.client.widgets.layout.ColumnLayout;
import com.gwtext.client.widgets.layout.ColumnLayoutData;
import com.gwtext.client.widgets.layout.FormLayout;
import com.gwtext.client.widgets.layout.HorizontalLayout;
import com.gwtext.client.widgets.layout.VerticalLayout;

import edu.calpoly.csc.luna.turboplan.web.client.TurboPlan;
import edu.calpoly.csc.luna.turboplan.web.client.model.GwtAddress;
import edu.calpoly.csc.luna.turboplan.web.client.model.GwtTask;
import edu.calpoly.csc.luna.turboplan.web.client.model.GwtUser;
import edu.calpoly.csc.luna.turboplan.web.client.model.GwtTask.TaskPriority;
import edu.calpoly.csc.luna.turboplan.web.client.model.GwtTask.TaskStatus;
import edu.calpoly.csc.luna.turboplan.web.client.service.ServiceManager;
import edu.calpoly.csc.luna.turboplan.web.client.window.TaskWindow;

/**
 * The Task profile panel which displays the information of a task
 * @author Stephanie Long
 * @author Paul De Leon
 * @author Ming Liu
 *
 */
public class TaskProfilePanel extends Panel {
	private static final String SIMPLE_CLASSNAME = "TaskProfilePanel";
	private Panel assignedTimePanel;
	
    private static final int LABEL_WIDTH = 125;
    private final DateTimeFormat dateFormatter = DateTimeFormat.getFormat("M/d/yy");
    private final DateTimeFormat timeFormatter = DateTimeFormat.getFormat("hh:mm a");
    
    private final int dateFieldWidth = 100;
    private final int timeFieldWidth = 100; 
    private final int vertPadding = 2;
    private final int phoneWidth = 210;
    private Long taskId;
    Date curDate, startDate, endDate;
    
    //Fields
    private TextField taskName;
    private DateField startDateField;
    private DateField endDateField;
    private TimeField startTimeField;
    private TimeField endTimeField;
    private DateField asgndStartDateField;
    private DateField asgndEndDateField;
    private TimeField asgndStartTimeField;
    private TimeField asgndEndTimeField;
    private ComboBox priority;
    private ComboBox owner;
//    private TextField owner;
    private TextArea description;

    private TextField customerFirstName;
    private TextField customerLastName;
    private TextField street;
    private TextField city;
    private ComboBox state;
    private NumberField zip;
    private TextField country; 
    private NumberField homePhone;
    private NumberField mobilePhone;
    
    private ComboBox status;
    private TextArea notes;
    private NumberField estimatedTime;
    private NumberField timeSpent;
    
    //Default Values
    private TaskPriority defaultTaskPriority = TaskPriority.NORMAL;
    private TaskStatus defaultTaskStatus = TaskStatus.New;
    private final String defaultEstTime = "1.00";
    
    ArrayList<String> owners;


    /**
     * Constructor of a task profile panel
     */
    public TaskProfilePanel(boolean needAssigned) {
        super();
        Log.info(SIMPLE_CLASSNAME + " is constructing");
        
        owners = new ArrayList<String>();
        
        curDate   = new Date();
        startDate = curDate;
        endDate   = curDate;
        
        FormLayout layout = new FormLayout();
        layout.setLabelWidth(LABEL_WIDTH);
        setLayout(layout);
        setFrame(true);

        final Panel genInfoPanel = buildGenInfoPanel(needAssigned);
        final Panel custInfoPanel = buildCustomerInfoPanel();
        final Panel updateInfoPanel = buildUpdateInfoPanel();
        
        add(genInfoPanel);
        add(custInfoPanel);
        add(updateInfoPanel);

        Log.debug(SIMPLE_CLASSNAME + " is constructed");
    } 
    


	/*
     * Build Task General Info Panel.
     * @author Paul De Leon
     */
    private Panel buildGenInfoPanel(boolean needAssigned) {
        final Panel genInfoPanel = new Panel("General Information");
        Label reqLabel = new Label("* Required Fields");
        genInfoPanel.setLayout(new FormLayout());
        
        taskName = new TextField("Task Name*", "taskName");

        genInfoPanel.add(taskName, new AnchorLayoutData("95%"));
        genInfoPanel.add(buildTimePanel()); 
        if (needAssigned) {
        	genInfoPanel.add(buildAssignedTimePanel());
        }
        genInfoPanel.add(buildPriorityAndOwnerPanel());
        genInfoPanel.add(description, new AnchorLayoutData("95%"));
        genInfoPanel.add(reqLabel);
        
        return genInfoPanel;
    }    
    
    /*
     * Helper Method. Builds Suggested Time Panel with Start Time/Date and End Time/Date.
     * @author Paul De Leon
     */
    public Panel buildTimePanel() {
        Panel timePanel = new Panel(); //The main panel
        Panel startPanel = new Panel(); //Start Time and Date Panel
        Panel endPanel = new Panel(); //End Time and Date Panel
        
        Panel startDatePanel = new Panel();
        Panel startTimePanel = new Panel();
        Panel endDatePanel = new Panel();
        Panel endTimePanel = new Panel();
        
        FormLayout layout = new FormLayout();
        
        //Set Attributes
        timePanel.setLayout(new ColumnLayout());
        startPanel.setLayout(new VerticalLayout(vertPadding));
        endPanel.setLayout(new VerticalLayout(vertPadding));
        
        startDatePanel.setLayout(layout);
        startTimePanel.setLayout(layout);
        endDatePanel.setLayout(layout);
        endTimePanel.setLayout(layout);
        
        //Create Fields
        startDateField = new DateField("Suggested Start Date*", "startDate", dateFieldWidth);     
        endDateField = new DateField("Suggested End Date*", "endDate", dateFieldWidth);
        startTimeField = new TimeField("Suggested Start Time*", "startTime", timeFieldWidth);
        endTimeField = new TimeField("Suggested End Time*", "endTime", timeFieldWidth);
        //Set Default Values
        startDateField.setValue(dateFormatter.format(new Date())); 
        endDateField.setValue(dateFormatter.format(new Date())); 
        startTimeField.setValue(timeFormatter.format(new Date()));
        endTimeField.setValue(timeFormatter.format(new Date()));
        
        
        description = new TextArea("Description", "description");
                
        
        
        startDateField.addListener(new DatePickerListenerAdapter() {
            public void onSelect(DatePicker dataPicker, java.util.Date date) {
                Log.trace("StartDate Selected");
                //If start date picked is after end date, change end date to start date
                if(date.after(endDateField.getValue())) {
                    Log.info("StartDate After EndDate -> Changing EndDate to StartDate");
                    endDateField.setValue(dateFormatter.format(date));
                }
            }
        });
        
        endDateField.addListener(new DatePickerListenerAdapter() {
            public void onSelect(DatePicker dataPicker, java.util.Date date) {
                Log.trace("EndDate Selected");
                //If start date picked is after end date, change end date to start date
                if(date.before(startDateField.getValue())) {
                    Log.info("EndDate Before StartDate -> Changing StartDate to EndDate");
                    startDateField.setValue(dateFormatter.format(date));
                }
            }
        });
        
        
        //Add Fields to Panel
        startDatePanel.add(startDateField);
        startTimePanel.add(startTimeField);
        endDatePanel.add(endDateField);
        endTimePanel.add(endTimeField);
      
        startPanel.add(startDatePanel);
        startPanel.add(startTimePanel);
        endPanel.add(endDatePanel);
        endPanel.add(endTimePanel);
        
        
        timePanel.add(startPanel, new ColumnLayoutData(.4));
        timePanel.add(endPanel, new ColumnLayoutData(.6));    
        
        return timePanel;
    }
    
    /*
     * Helper Method. Builds Time Panel with Start Time/Date and End Time/Date.
     * @author Paul De Leon
     */
    public Panel buildAssignedTimePanel() {
        assignedTimePanel = new Panel(); //The main panel
        assignedTimePanel.setLayout(new FormLayout());
        Panel asngdStartPanel = new Panel(); //Start Time and Date Panel
        Panel asngdEndPanel = new Panel(); //End Time and Date Panel
        
        Panel asngdStartDatePanel = new Panel();
        Panel asngdStartTimePanel = new Panel();
        Panel asngdEndDatePanel = new Panel();
        Panel asngdEndTimePanel = new Panel();
        
        FormLayout layout = new FormLayout();
        
        //Set Attributes
        assignedTimePanel.setLayout(new HorizontalLayout(95));
        asngdStartPanel.setLayout(new VerticalLayout(vertPadding));
        asngdEndPanel.setLayout(new VerticalLayout(vertPadding));
        
        asngdStartDatePanel.setLayout(layout);
        asngdStartTimePanel.setLayout(layout);
        asngdEndDatePanel.setLayout(layout);
        asngdEndTimePanel.setLayout(layout);
        
        //Create Fields
        asgndStartDateField = new DateField("Assigned Start Date*", "startDate", dateFieldWidth);     
        asgndEndDateField = new DateField("Assigned End Date*", "endDate", dateFieldWidth);
        asgndStartTimeField = new TimeField("Assigned Start Time*", "startTime", timeFieldWidth);
        asgndEndTimeField = new TimeField("Assigned End Time*", "endTime", timeFieldWidth);
        
        
        
                
        //Set Default Values
//        startDateField.setValue(dateFormatter.format(new Date())); 
//        endDateField.setValue(dateFormatter.format(new Date())); 
//        startTimeField.setValue(timeFormatter.format(new Date()));
//        endTimeField.setValue(timeFormatter.format(new Date()));
        
        
        asgndStartDateField.addListener(new DatePickerListenerAdapter() {
            public void onSelect(DatePicker dataPicker, java.util.Date date) {
                Log.trace("StartDate Selected");
                //If start date picked is after end date, change end date to start date
                if(date.after(endDateField.getValue())) {
                    Log.info("StartDate After EndDate -> Changing EndDate to StartDate");
                    endDateField.setValue(dateFormatter.format(date));
                }
            }
        });
        
        asgndEndDateField.addListener(new DatePickerListenerAdapter() {
            public void onSelect(DatePicker dataPicker, java.util.Date date) {
                Log.trace("EndDate Selected");
                //If start date picked is after end date, change end date to start date
                if(date.before(startDateField.getValue())) {
                    Log.info("EndDate Before StartDate -> Changing StartDate to EndDate");
                    startDateField.setValue(dateFormatter.format(date));
                }
            }
        });
        
        
        //Add Fields to Panel
        asngdStartDatePanel.add(asgndStartDateField);
        asngdStartTimePanel.add(asgndStartTimeField);
        asngdEndDatePanel.add(asgndEndDateField);
        asngdEndTimePanel.add(asgndEndTimeField);
      
        asngdStartPanel.add(asngdStartDatePanel);
        asngdStartPanel.add(asngdStartTimePanel);
        asngdEndPanel.add(asngdEndDatePanel);
        asngdEndPanel.add(asngdEndTimePanel);
        
        
        assignedTimePanel.add(asngdStartPanel, new ColumnLayoutData(.4));
        assignedTimePanel.add(asngdEndPanel, new ColumnLayoutData(.6));    

        return assignedTimePanel;
    }
    
    private void setAssignedTimePanel(GwtTask task) {
    	asgndStartDateField.setValue(dateFormatter.format(task.getScheduledStartTime())); 
    	asgndEndDateField.setValue(dateFormatter.format(task.getScheduledEndTime())); 
    	asgndStartTimeField.setValue(timeFormatter.format(task.getScheduledStartTime()));
    	asgndEndTimeField.setValue(timeFormatter.format(task.getScheduledEndTime()));
		
	}

    
    /*
     * Helper Method.  Builds Priority and Owner Panel.
     * @author Paul De Leon
     */
    private Panel buildPriorityAndOwnerPanel() {
        Panel priorityAndOwnerPanel = new Panel();
        priorityAndOwnerPanel.setLayout(new ColumnLayout());
     
        // add a ComboBox field
        Store storePriority = new SimpleStore("priority", getPriority());

        Panel priorityPanel = new Panel();
        priorityPanel.setLayout(new FormLayout());
        priority = new ComboBox();
        priority.setValue("Normal"); //Default
        priority.setFieldLabel("Priority");
        priority.setHiddenName("priority");
        priority.setStore(storePriority);
        priority.setDisplayField("priority");
        priority.setTypeAhead(true);
        priority.setMode(ComboBox.LOCAL);
        priority.setTriggerAction(ComboBox.ALL);
        priority.setEmptyText("Select a Priority...");
        priority.setSelectOnFocus(true);
        priority.setWidth(170);
        priorityPanel.add(priority);      
        
        
        // add a ComboBox field
        Store storeOwner = new SimpleStore("owner", getOwner());
        Panel ownerPanel = new Panel();
        ownerPanel.setLayout(new FormLayout());
        
        owner = new ComboBox();
        owner.setValue("None");  // Default
        owner.setFieldLabel("Owner");
        owner.setHiddenName("owner");
        owner.setStore(storeOwner);
        owner.setDisplayField("owner");
        owner.setTypeAhead(true);
        owner.setMode(ComboBox.LOCAL);
        owner.setTriggerAction(ComboBox.ALL);
        owner.setEmptyText("Select an Owner...");
        owner.setSelectOnFocus(true);
        owner.setWidth(170);
        
//        owner.setDisabled(true); //Make box uneditable
        ownerPanel.add(owner);            
        
        priorityAndOwnerPanel.add(priorityPanel, new ColumnLayoutData(.5));
        priorityAndOwnerPanel.add(ownerPanel, new ColumnLayoutData(.5));
        
        return priorityAndOwnerPanel;
    }
    

    /**
     * Returns the owner field.
     * @return The owner field for the combo box
     * @author Paul De Leon
     */
    ComboBox getOwnerBox() {
        return owner;
    }
    
    
    /*
     * Build Customer Info Panel.  Contains General Info and Address Info.
     * @author Paul De Leon
     */
    private Panel buildCustomerInfoPanel() {
        final Panel custInfoPanel = new Panel("Customer Information");  //The main panel
        custInfoPanel.setCollapsible(true);
        custInfoPanel.setLayout(new ColumnLayout());
        
        custInfoPanel.add(buildCustGenInfoPanel(), new ColumnLayoutData(.5));
        custInfoPanel.add(buildCustAddressPanel(), new ColumnLayoutData(.5));
        
        return custInfoPanel;
    }
    
    /*
     * Helper Method.  Builds Customer General Info Panel.
     * @author Paul De Leon
     */
    private Panel buildCustGenInfoPanel() {
        Panel customerGenInfo = new Panel();
        
        //Set Panel Attributes
        customerGenInfo.setLayout(new FormLayout());
        
        //Create Fields
        customerFirstName = new TextField("First Name", "firstName");
        customerLastName = new TextField("Last Name", "lastName");
        homePhone = new NumberField("Home", "home", phoneWidth);
        mobilePhone = new NumberField("Mobile", "mobile", phoneWidth);   
        homePhone.setAllowDecimals(false);
        mobilePhone.setAllowDecimals(false);
        
        //Add Fields to Panel
        customerGenInfo.add(customerFirstName, new AnchorLayoutData("95%"));
        customerGenInfo.add(customerLastName, new AnchorLayoutData("95%"));
        customerGenInfo.add(homePhone);
        customerGenInfo.add(mobilePhone);
        
        return customerGenInfo;
    }

    /*
     * Helper Method.  Builds Address Panel.
     * @author Paul De Leon
     */
    private Panel buildCustAddressPanel() {
        final Panel addressPanel = new Panel();
        addressPanel.setLayout(new FormLayout());

        street = new TextArea("Street Number and Name", "street");
        addressPanel.add(street, new AnchorLayoutData("95%"));
        city = new TextField("City", "city");
        addressPanel.add(city, new AnchorLayoutData("75%"));

        // add a ComboBox field
        Store storeState = new SimpleStore("states", getStates());

        state = new ComboBox();
        state.setFieldLabel("State");
        state.setHiddenName("states");
        state.setStore(storeState);
        state.setDisplayField("states");
        state.setTypeAhead(true);
        state.setMode(ComboBox.LOCAL);
        state.setTriggerAction(ComboBox.ALL);
        state.setEmptyText("Select a state...");
        state.setSelectOnFocus(true);
        state.setWidth(170);
        addressPanel.add(state);

        zip = new NumberField("Zip Code", "zipcode");
        zip.setAllowDecimals(false);
        addressPanel.add(zip, new AnchorLayoutData("45%"));
        
        Hyperlink mapLink = new Hyperlink("Get Directions", "");
		mapLink.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				Log.debug("Get Directions Hyperlink clicked");
				if (street.getText().equals("") || city.getText().equals("") || state.getText().equals("")|| zip.getText().equals("")) {
					Log.debug("address incomplete. cannot get directions");
					MessageBox.alert("Address Incomplete. Cannot get Directions.");
				} else {
					Log.info(TurboPlan.loggedInUser.getCompany().getAddress());
					String fromAdd = TurboPlan.loggedInUser.getCompany().getAddress();
					Log.info(street.getText()+ ", "+ city.getText() + ", "+ state.getText() +", " + zip.getText());
					String toAdd = street.getText()+ ", "+ city.getText() + ", "+ state.getText() +", " + zip.getText();
					com.google.gwt.user.client.Window.open("http://maps.google.com/maps?saddr=" + fromAdd + "&daddr=" +  toAdd , "DrivingDirections", "menubar=yes,location=yes,resizable=yes,scrollbars=yes,status=yes");
				}
			}
		});
		addressPanel.add(mapLink);
		country = new TextField("Country", "country");
        addressPanel.add(country, new AnchorLayoutData("95%"));

        return addressPanel;
     }
    
    /*
     * Build Update Panel with Status, Notes, Estimated Time Fields.
     * @author Paul De Leon
     */
    private Panel buildUpdateInfoPanel() {
        final Panel updateInfoPanel = new Panel("Notes"); //The main Panel
        Panel statusAndEstTimePanel = new Panel();  //SubPanel
        Panel statusPanel = new Panel(); //SubSubPanel
        Panel estTimePanel = new Panel(); //SubSubPanel
        
        //Set Attributes
        updateInfoPanel.setCollapsible(true);
        updateInfoPanel.setLayout(new FormLayout());
        statusAndEstTimePanel.setLayout(new ColumnLayout());
        statusPanel.setLayout(new FormLayout());
        estTimePanel.setLayout(new FormLayout());
        
        // add a ComboBox field
        Store storeState = new SimpleStore("status", getStatus());

        status = new ComboBox();
        status.setValue(defaultTaskStatus.toString());
        status.setFieldLabel("Status");
        status.setHiddenName("status");
        status.setStore(storeState);
        status.setDisplayField("status");
        status.setTypeAhead(true);
        status.setMode(ComboBox.LOCAL);
        status.setTriggerAction(ComboBox.ALL);
        status.setEmptyText("Select a Status...");
        status.setSelectOnFocus(true);
        status.setWidth(170);
                

        estimatedTime = new NumberField("Est. Time(hrs)* (Ex: 1.00)", "estimatedTime");
        estimatedTime.setValue(defaultEstTime);
        timeSpent = new NumberField("Time Spent(hrs) (Ex: 1.00)", "timeSpent");
        
        //Add Fields to SubSubPanels
        statusPanel.add(status);
        estTimePanel.add(estimatedTime);
        estTimePanel.add(timeSpent);
        
        //Add SubSubPanels to SubPanel
        statusAndEstTimePanel.add(statusPanel, new ColumnLayoutData(.5));
        statusAndEstTimePanel.add(estTimePanel, new ColumnLayoutData(.5));        
        
        notes = new TextArea("Notes", "notes");
        
        //Add Fields/SubPanels to Main Panel
        updateInfoPanel.add(statusAndEstTimePanel);
        updateInfoPanel.add(notes, new AnchorLayoutData("95%"));
        
        return updateInfoPanel;
    }

    
    /*
     * Gets all Priorities
     * @author Paul De Leon
     */
    private String[] getPriority() {
        ArrayList<String> priorities = new ArrayList<String>();
        for(TaskPriority priority : TaskPriority.values()) {
            priorities.add(priority.toString());
        }
        
        String priorityArr[] = new String[priorities.size()];
        priorities.toArray(priorityArr); 
        
        return priorityArr;        
    }   
    
    
    /**
     * Return names of all possible owners for this task.
     * @return names of all possible owners for this task.
     * @author Paul De Leon
     */
    private String[] getOwner() {
        //STUB
        return new String[] { "None" };
    }
    

    /**
     * Gets all Task Statuses.
     * @return The status in string form
     * @author Paul De Leon
     */
    private String[] getStatus() {   
        ArrayList<String> statuses = new ArrayList<String>();
        for(TaskStatus status : TaskStatus.values()) {
            statuses.add(status.toString());
        }
        
        String statusArr[] = new String[statuses.size()];
        statuses.toArray(statusArr); 
        
        return statusArr;
    }
    
    
    /**
     * Gets all states in U.S.
     * @return The states in string form
     * @author Stephanie Long
     */
    private String[] getStates() {
        return new String[] {
                "N/A", "Alabama", "Alaska", "Arizona", "Arkansas", "California", "Colorado", "Connecticut", "Delaware", "District of Columbia", "Florida",
                "Georgia", "Hawaii", "Idaho", "Illinois", "Indiana", "Iowa", "Kansas", "Kentucky", "Louisiana", "Maine", "Massachusetts", "Maryland",
                "Michigan", "Minnesota", "Mississippi", "Missouri", "Montana", "Nebraska", "Nevada", "New Hampshire", "New Jersey", "New Mexico",
                "New York", "North Carolina", "North Dakota", "Ohio", "Oklahoma", "Oregon", "Pennsylvania", "Rhode Island ", "South Carolina ",
                "South Dakota ", "Tennessee", "Texas", "Utah", "Vermont", "Virginia", "Washington", "West Virginia", "Wisconsin", "Wyoming", "Other"
        };
    }
    
    
    /**
     * Sets Owner Field from Owners in the Database.
     * @param taskWin
     * @author Paul De Leon
     */
    public void setOwners(final TaskWindow taskWin) {
        final ArrayList<String> owners = new ArrayList<String>();

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
                        Log.info("Adding UserName to Owner ComboBox String[]");
                        owners.add(user.getProfile().getFirstName() + " " +
                                user.getProfile().getLastName());
                    }
                    
                    //Create ownerStore
                    String ownerArr[] = new String[owners.size()];
                    owners.toArray(ownerArr);
                    
                    //Set ownerStore
                    Store storeOwner = new SimpleStore("owner", ownerArr);
                    taskWin.getTaskProfilePanel().getOwnerBox().setStore(storeOwner);
                    
                    //Show the window
                    taskWin.show();
                }
            }
        }); 
    }
    
    public void hideAssinged()
    {
    	this.assignedTimePanel.setVisible(false);
    	Log.info("hide assigned");
    }
    /**
     * Set a Task Profile to the given task.
     * @param task
     * @author Paul De Leon
     */
    public void setTaskProfile(GwtTask task) {
        Log.info("   Setting taskTitle: " + task.getTitle());
        if (task.getStatus() == TaskStatus.Assigned) {
        	setAssignedTimePanel(task);
        }
        else {
        	assignedTimePanel.setVisible(false);
        }
        taskName.setValue(task.getTitle());
        
        Log.info("set Start" + task.getSuggestedStartTime());
        Log.info("set End" + task.getSuggestedEndTime());
        ((Field)startDateField).setRawValue(dateFormatter.format(task.getSuggestedStartTime()));
        ((Field)endDateField).setRawValue(dateFormatter.format(task.getSuggestedEndTime()));
        ((Field)startTimeField).setRawValue(timeFormatter.format(task.getSuggestedStartTime()));
        ((Field)endTimeField).setRawValue(timeFormatter.format(task.getSuggestedEndTime()));
        
        
        priority.setValue(task.getPriority().toString());  
        
        if(task.getUsers().isEmpty() || task.getUsers() == null) {
            owner.setValue("None");
        }
        else {
            //Get the first Owner in the Set
            Iterator<GwtUser> ownerIter = task.getUsers().iterator();
            
            for(int i=0; i < 1; i++) {
                if(ownerIter.hasNext()) {
                    GwtUser nextUsr = ownerIter.next();
                    owner.setValue(nextUsr.getProfile().getFirstName() + " " +
                            nextUsr.getProfile().getLastName());
                }
            }
        }
        

        description.setValue(task.getDescription());
        customerFirstName.setValue(task.getCustomerFirstName());
        customerLastName.setValue(task.getCustomerLastName());
        street.setValue(task.getLocation().getAddress());  
        city.setValue(task.getLocation().getCity());
        state.setValue(task.getLocation().getState());
        zip.setValue(task.getLocation().getZip());
        country.setValue(task.getLocation().getCountry());
        
        Number homeNum = task.getHomeNum();
        if(homeNum != null) {
        	homePhone.setRawValue(homeNum.toString());
        }
        Number mobileNum = task.getMobileNum();
        if(mobileNum != null) {
        	mobilePhone.setRawValue(mobileNum.toString());
        }

        status.setValue(task.getStatus().toString());
        
        Double estTime = task.getEstimatedTime();
        if(estTime != null) {
            estimatedTime.setValue(estTime);
        }
        else {
            estimatedTime.setValue(defaultEstTime);
        }
        
        Double timeTook = task.getTimespent();
        if(timeTook != null) {
            timeSpent.setValue(timeTook);
        }
        
        notes.setValue(task.getNotes());        
        Log.trace("end");
    }   
    

   



	/**
     * Get a task representation from the panel.
     * @return The task created out of the profile fields
     * @author Paul De Leon
     */
    @SuppressWarnings("deprecation")
	public GwtTask getGwtTask() {
        Log.trace("Getting TaskProfile");
        GwtTask profile = new GwtTask();
        
//        profile.setTaskID();
        profile.setTitle(taskName.getText());       
        profile.setDueDate(endDateField.getValue());
        profile.setEstimatedTime(estimatedTime.getValue().doubleValue());
        Date startTime = new Date(startDateField.getRawValue() + " " + startTimeField.getRawValue());
        Date endTime = new Date(endDateField.getRawValue() + " " + endTimeField.getRawValue());
        
        Log.info(" get start make task" + startTime);
        Log.info(" get end make task" + endTime);
        
        
        profile.setSuggestedEndTime(endTime);  
        profile.setSuggestedStartTime(startTime);  
        profile.setPriority(defaultTaskPriority);
        for(TaskPriority p : TaskPriority.values()) {
            if(priority.getText().equals(p.toString())) {
                profile.setPriority(p);
            }
        }
        
//        profile.setUsers(new Set<GwtUser> = { owner.getT)   NEED TO GET USER
        
        
        profile.setDescription(description.getText());
        
        profile.setCustomerFirstName(customerFirstName.getText());
        profile.setCustomerLastName(customerLastName.getText());
        GwtAddress customerAddress = new GwtAddress();
        String tempAd = street.getText();

        if (!tempAd.equals("")) {
        	customerAddress.setAddress(tempAd);
        }
        String tempcit = city.getText();
        if (!tempcit.equals("")) {
        	customerAddress.setCity(tempcit);
        }
        String tempstate= state.getText();
        if (!tempstate.equals("")) {
        	customerAddress.setState(tempstate);
        }
        
        
        Integer tempzip = null;
        if(!zip.getValueAsString().equals("")){
        	tempzip = zip.getValue().intValue();
        }
        if (tempzip!=null) {
        	customerAddress.setZip(tempzip);
        }
        if(!country.getText().equals("") && country != null) {
            customerAddress.setCountry(country.getText());
        }
        else {
            customerAddress.setCountry("United States");
        }

       profile.setLocation(customerAddress);
        
        if (!homePhone.getText().equals("")) {
            profile.setHomeNum(homePhone.getValue().longValue());
        }
        if (!mobilePhone.getText().equals("")) {
            profile.setMobileNum( mobilePhone.getValue().longValue());
        }
 
        profile.setStatus(defaultTaskStatus);
        for (TaskStatus s : TaskStatus.values()) {
            if(status.getText().equals(s.toString())) {
                profile.setStatus(s);
            }
        }
        if (!timeSpent.getValueAsString().equals("")) {
        	profile.setTimespent(timeSpent.getValue().doubleValue());
        }
        profile.setEstimatedTime(new Double(estimatedTime.getValue().doubleValue()));
        profile.setNotes(notes.getText());
        
        Log.trace("Returning Task Profile");
        return profile;
    }
    
    /**
     * Clears the profile panel
     */
    public void clearTaskProfile() {
        taskName.setValue("");
        startDateField.setValue("");
        endDateField.setValue("");
        startTimeField.setValue("");
        endTimeField.setValue("");
        priority.setValue("");
        owner.setValue("");
        description.setValue("");

        customerFirstName.setValue("");
        customerLastName.setValue("");
        street.setValue("");
        city.setValue("");
        state.setValue("");
        zip.setValue("");
        country.setValue(""); 
        homePhone.setValue("");
        mobilePhone.setValue("");
        
        status.setValue("");
        timeSpent.setValue("");
        estimatedTime.setValue("");
        notes.setValue("");
        Log.info("done clearing");
    }


	/**
	 * @param taskId the taskId to set
	 */
	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}


	/**
	 * @return the taskId
	 */
	public Long getTaskId() {
		return taskId;
	} 
}