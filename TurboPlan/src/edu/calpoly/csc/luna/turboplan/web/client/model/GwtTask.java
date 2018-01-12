package edu.calpoly.csc.luna.turboplan.web.client.model;

import java.util.Date;
import java.util.Set;

/**
 * A task in TurboPlan
 * @author Stephanie Long
 * @author Ming Liu
 * @author Paul DeLeon
 *
 */
public class GwtTask implements GwtModel {
	/**
	 * Task Status represents the status of a task 
	 */
	public static enum TaskStatus {
		/** The initial status of a task*/ New("New"), 
		/** The status of a task with an assigned employee*/ Assigned("Assigned"), 
		/** The status once a task is completed*/ Complete("Complete");
		
	    private final String name;
	    TaskStatus(String name) {
	        this.name = name;
	    }
	    
	    public String toString() {
	        return name;
	    }
	};

	/**
	 * Task Priority represents the priority of a task relative to the other tasks
	 */
    public enum TaskPriority {
        /** A task of low priority*/ LOW ("Low"), 
        /** A task of normal priority*/ NORMAL ("Normal"), 
        /** A task of high priority*/ HIGH ("High");
        
        private final String name;  //the name of the field
        TaskPriority(String name) {
            this.name = name;
        }
        
        public String toString() {
            return name;
        }        
    }
	private Long taskID;
	private Set<GwtSkill> requiredSkill;
	private Set<GwtUser> owners;
	private TaskStatus status;
	private TaskPriority priority;

	private String title;
	private String description;
	private GwtCompany company;
	
	private String customerFirstName;
	private String customerLastName;
	
	private Long homeNum;
	private Long mobileNum;

	private Date createdDate;
	private Date dueDate;
	private String notes;
	private GwtAddress location;
	private Date suggestedStartTime;
	private Date suggestedEndTime;
	private Date scheduledStartTime;
	private Date scheduledEndTime;
	private Double estimatedTime;
	private Double timeSpent;
	
	/**
	 *  Default contructor
	 */
	public GwtTask() {
	    this.priority = TaskPriority.NORMAL;
	}
	
    /**
     * Constriucts a Gwt Task
     * @param requiredSkill The skills required for the task
     * @param owners The assigned employees of the task
     * @param status The task's status
     * @param title The task's title
     * @param description The task's description
     * @param location The task's location
     * @param contactFirstName The task's customer's first name
     * @param contactLastName The task's customer's last name
     * @param homeNum The task's customer's home phone number
     * @param mobileNum The task's customer's mobile phone number
     * @param createdDate The task's created date
     * @param dueDate The task's due date
     * @param suggestedStartTime The task's suggested start time
     * @param suggestedEndTime The task's suggested end time
     * @param estimatedTime The task's estimated time to complete the task
     * @param priority The task's priority 
     * @param notes The task's notes
     */
    public GwtTask(Set<GwtSkill> requiredSkill, Set<GwtUser> owners, TaskStatus status, String title, String description,
            GwtAddress location, String contactFirstName, String contactLastName, Long homeNum, Long mobileNum, Date createdDate,
            Date dueDate, Date suggestedStartTime, Date suggestedEndTime, Double estimatedTime, TaskPriority priority, String notes) {
        this.requiredSkill = requiredSkill;
        this.owners = owners;
        this.status = status;
        this.priority = priority;
        this.title = title;
        this.description = description;
        this.location = location;
        this.customerFirstName = contactFirstName;
        this.customerLastName = contactLastName;
        this.homeNum = homeNum;
        this.mobileNum = mobileNum;
        this.createdDate = createdDate;
        this.dueDate = dueDate;
        this.suggestedStartTime = suggestedStartTime;
        this.suggestedEndTime = suggestedEndTime;
        //this.scheduledEndTime = suggestedEndTime;
        //this.scheduledStartTime = suggestedStartTime;
        this.estimatedTime = estimatedTime;
        this.notes = notes;
    }

    
	/**
	 * Gets the unique identifier for the task
	 * @return The unique identifier for the task
	 */
	public Long getTaskID() {
		return taskID;
	}

	/**
	 * Sets the unique identifier for the task
	 * @param taskID The unique identifier for the task
	 */
	public void setTaskID(Long taskID) {
		this.taskID = taskID;
	}

	/**
	 * Gets the required skills for the task
	 * @return The required skills for the task
	 */
	public Set<GwtSkill> getRequiredSkill() {
		return requiredSkill;
	}

	/**
	 * Sets the required skills for the task
	 * @param requiredSkill The required skills for the task
	 */
	public void setRequiredSkill(Set<GwtSkill> requiredSkill) {
		this.requiredSkill = requiredSkill;
	}

	/**
	 * Gets the assigned employees for the task
	 * @return The assigned employees of the task 
	 */
	public Set<GwtUser> getUsers() {
		return owners;
	}

	/**
	 * Sets the assigned employees for the task
	 * @param users The assigned employees for the task
	 */
	public void setUsers(Set<GwtUser> users) {
		this.owners = users;
	}

	/**
	 * Gets the status for the task
	 * @return The status for the task
	 */
	public TaskStatus getStatus() {
		return status;
	}

	/**
	 * Sets the status for the task
	 * @param status The status for the task
	 */
	public void setStatus(TaskStatus status) {
		this.status = status;
	}
	
	/**
	 * Gets the priority for the task
	 * @return The priority for the task
	 */
	public TaskPriority getPriority() {
	    return priority;
	}
	
	/**
	 * Sets the priority for the task
	 * @param priority The priority for the task
	 */
	public void setPriority(TaskPriority priority) {
	    this.priority = priority;
	}

	/**
	 * Gets the title for the task
	 * @return
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title for the task
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Gets the description for the task
	 * @return The description for the task
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description for the task
	 * @param description The description for the task
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the created date for the task
	 * @return The created date for the task
	 */
	public Date getCreatedDate() {
		return createdDate;
	}

	/**
	 * Sets the created date for the task
	 * @param createdDate The created date for the task
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * Gets the due date for the task
	 * @return The due date for the task
	 */
	public Date getDueDate() {
		return dueDate;
	}

	/**
	 * Sets the due date for the task
	 * @param dueDate The due date for the task
	 */
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	/**
	 * Gets the notes for the task
	 * @return The notes for the task
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * Sets the notes for the task 
	 * @param notes The notes for the task 
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}

	/**
	 * Gets the location for the task 
	 * @return The location for the task 
	 */
	public GwtAddress getLocation() {
		return location;
	}

	/**
	 * Sets the location for the task 
	 * @param location The location for the task 
	 */
	public void setLocation(GwtAddress location) {
		this.location = location;
	}

	/**
	 * Sets the suggested start time for the task 
	 * @return  Sets the suggested start time for the task 
	 */
	public Date getSuggestedStartTime() {
		return suggestedStartTime;
	}

	/**
	 * Sets the suggested start time for the task 
	 * @param suggestedStartTime  The suggested start time for the task 
	 */
	public void setSuggestedStartTime(Date suggestedStartTime) {
		this.suggestedStartTime = suggestedStartTime;
	}

	/**
	 * Gets the suggested end time for the task  
	 * @return  The suggested end time for the task 
	 */
	public Date getSuggestedEndTime() {
		return suggestedEndTime;
	}

	/**
	 * Sets the suggested end time for the task 
	 * @param suggestedEndTime The suggested end time for the task
	 */
	public void setSuggestedEndTime(Date suggestedEndTime) {
		this.suggestedEndTime = suggestedEndTime;
	}

	/**
	 * Gets the scheduled start time for the task 
	 * @return The scheduled start time for the task
	 */
	public Date getScheduledStartTime() {
		return scheduledStartTime;
	}

	/**
	 * Sets the scheduled start time for the task 
	 * @param scheduledStartTime The scheduled start time for the task
	 */
	public void setScheduledStartTime(Date scheduledStartTime) {
		this.scheduledStartTime = scheduledStartTime;
	}

	/**
	 * Gets the scheduled end time for the task
	 * @return The scheduled end time for the task
	 */
	public Date getScheduledEndTime() {
		return scheduledEndTime;
	}

	/**
	 * Sets the scheduled end time for the task 
	 * @param scheduledEndTime The scheduled end time for the task 
	 */
	public void setScheduledEndTime(Date scheduledEndTime) {
		this.scheduledEndTime = scheduledEndTime;
	}
	
	/**
	 * Sets the estimated time to complete the task 
	 * @param estimatedTime  The estimated time to complete the task 
	 */
	public void setEstimatedTime(Double estimatedTime) {
	    this.estimatedTime = estimatedTime;
	}
	
	/**
	 * Gets the estimated time to complete the task
	 * @return The estimated time to complete the task 
	 */
	public Double getEstimatedTime() {
	    return estimatedTime;
	}

	/**
	 * Sets the first name belonging to the task customer
	 * @param firstName The first name belonging to the task customer
	 */
	public void setCustomerFirstName(String firstName) {
		this.customerFirstName = firstName;
	}

	/**
	 * Gets the first name belonging to the task customer
	 * @return The task's customer's first name
	 */
	public String getCustomerFirstName() {
		return customerFirstName;
	}

	/**
	 * Sets the last name belonging to the task customer
	 * @param lastName the lastName to set for the task's customer
	 */
	public void setCustomerLastName(String lastName) {
		this.customerLastName = lastName;
	}

	/**
	 * Gets the last name belonging to the task customer
	 * @return the lastName of the task's customer
	 */
	public String getCustomerLastName() {
		return customerLastName;
	}

	/**
	 * Sets the home number belonging to the task customer
	 * @param homeNum the homeNum to set
	 */
	public void setHomeNum(Long homeNum) {
		this.homeNum = homeNum;
	}

	/**
	 * Gets the home number belonging to the task customer
	 * @return the homeNum The home number of the task's customer
	 */
	public Long getHomeNum() {
		return homeNum;
	}

	/**
	 * Sets the mobile number belonging to the task customer
	 * @param mobileNum the mobileNum to set
	 */
	public void setMobileNum(Long mobileNum) {
		this.mobileNum = mobileNum;
	}

	/**
	 * Gets the mobile number belonging to the task customer
	 * @return the mobileNum The mobile number belonging to the task customer
	 */
	public Long getMobileNum() {
		return mobileNum;
	}
	
	/**
	 * Gets this Gwt task
	 * @return The Gwt task
	 */
	public GwtTask getGwtTask() {
	    return this;
	}

    /**
     * Get the company assigned to the task
     * @return The company the task is assigned to
     */
    public GwtCompany getCompany() {
        return company;
    }

    /**
     * Set the company to assign to the task
     * @param company The company to assign to the task
     */
    public void setCompany(GwtCompany company) {
        this.company = company;
    }
    
    /**
	 * Gets the time spent on a task
	 * @return the timeSpent
	 */
	public Double getTimespent() {
		return timeSpent;
	}

	/**
	 * Sets the time spent on a task
	 * @param timeSpent the timeSpent to set
	 */
	public void setTimespent(Double timeSpent2) {
		this.timeSpent = timeSpent2;
		
	}
	
	public boolean isTimespentOk()
	{
		return (this.timeSpent > 0.00);
	}
	
	
 }
