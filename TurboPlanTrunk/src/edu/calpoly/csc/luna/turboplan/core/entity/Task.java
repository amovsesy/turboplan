package edu.calpoly.csc.luna.turboplan.core.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.hibernate.validator.NotEmpty;
import org.hibernate.validator.NotNull;

import edu.calpoly.csc.luna.turboplan.axis2ws.mobile.model.MobileTask;
import edu.calpoly.csc.luna.turboplan.core.dao.EntityConstants;
import edu.calpoly.csc.luna.turboplan.core.entity.embeddable.Address;
import edu.calpoly.csc.luna.turboplan.core.modelconverter.GwtModelConverter;
import edu.calpoly.csc.luna.turboplan.core.modelconverter.MobileModelConverter;
import edu.calpoly.csc.luna.turboplan.core.util.GwtUtil;
import edu.calpoly.csc.luna.turboplan.core.util.MobileUtil;
import edu.calpoly.csc.luna.turboplan.core.util.StringUtil;
import edu.calpoly.csc.luna.turboplan.web.client.model.GwtTask;

/**
 *  A TurboPlan task.
 * @author Stephanie Long
 * @author Ming Liu
 * @author Paul De Leon
 */
@Entity
@org.hibernate.annotations.Entity(dynamicUpdate = true)
public class Task extends BaseEntity implements GwtModelConverter<GwtTask>,
		MobileModelConverter<MobileTask> {
	/**
	 * TaskStatus represents the status a TurboPlan task.
	 */
	public static enum TaskStatus {
		/** A new Task */ New, 
		/** A task which is on a schedule of an employee */ Assigned, 
		/** A task which has been completed by the assigned employee */ Complete
	};


	/**
	 * Priorities that a task can be Low, Normal, High.
	 * @author Paul De Leon
	 */
	public enum TaskPriority {
		/** A low priority task */ LOW("Low"), 
		/** A normal priority task */ NORMAL("Normal"), 
		/** A high priority task */ HIGH("High");

		private final String name; // the name of the field

		TaskPriority(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return this.name;
		}
	}

	/**
	 * The Task's unique identification number
	 */
	@Id
	@GeneratedValue
	private Long taskID;

	
	/**
	 * The Task's required skill set
	 */
	@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	private Set<Skill> requiredSkill;

	/**
	 * The employee's who are assigned to complete the task
	 */
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = EntityConstants.TaskUserJunction, joinColumns = @JoinColumn(name = "taskID"), inverseJoinColumns = @JoinColumn(name = "userID"))
	private Set<User> users;

	/**
	 *  The status of the task
	 */
	@NotNull
	@Enumerated(EnumType.STRING)
	private TaskStatus status;

	/**
	 * The priority of the task.
	 */
	@NotNull
	private TaskPriority priority;

	/**
	 * Gets the priority of the task
	 * @return The priority of the task
	 */
	public TaskPriority getPriority() {
		return priority;
	}

	/**
	 * The title of the task
	 */
	@NotEmpty
	private String title;

	/**
	 * The description of the task
	 */
	private String description;

	/**
	 * The location of the task
	 */
	private Address location;

	/**
	 * The first name of the task
	 */
	private String customerFirstName;

	/**
	 * The last name of the task
	 */
	private String customerLastName;

	/**
	 * The home phone number of the customer associated with the task
	 */
	private String homeNum;

	/**
	 * The mobile phone number of the customer associated with the task
	 */
	private String mobileNum;

	/**
	 * The notes with the task
	 */
	private String notes;

	/**
	 * The created date of the task
	 */
	@NotNull
	private Date createdDate;

	/**
	 * The suggested start time of the task
	 */
	@NotNull
	private Date suggestedStartTime;

	/**
	 * The suggested end time of the task
	 */
	@NotNull
	private Date suggestedEndTime;

	/**
	 * The scheduled start time of the task
	 */
	private Date scheduledStartTime;
	
	/**
	 * The scheduled end time the task
	 */
	private Date scheduledEndTime;

	/**
	 * The estimated time to complete the task
	 */
	@NotNull
	private Double estimatedTime;
	
	/**
	 * The time spent to complete the task
	 */
	private Double timespent;

	/**
	 * The company the task belongs to
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "company")
	@NotNull
	private Company company;

	/**
	 * Default constructor
	 * 
	 * @deprecated use other contructors instead. This is only for hibernate
	 *             contruct Task reflectively
	 */
	@Deprecated
	public Task() {
	}

	/**
	 * A new task in TurboPlan
	 * @param status The task's status
	 * @param title The task's title
	 * @param description The task's description
	 * @param location The task's location
	 * @param contactFirstName The task's contact first name
	 * @param contactLastName The task's contact last name
	 * @param homeNum The task's contact home phone number
	 * @param mobileNum The task's contact mobile phone number
	 * @param createdDate The task's created date
	 * @param suggestedStartTime The task's suggested start time
	 * @param suggestedEndTime The task's suggested end time
	 * @param estimatedTime The estimate time to complete the task
	 * @param priority The priority of the task
	 * @param company The company the task belongs to
	 */
	public Task(TaskStatus status, String title, String description,
			Address location, String contactFirstName, String contactLastName,
			String homeNum, String mobileNum, Date createdDate,
			Date suggestedStartTime, Date suggestedEndTime,
			Double estimatedTime, TaskPriority priority, Company company) {
		super();

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
		this.suggestedStartTime = suggestedStartTime;
		this.suggestedEndTime = suggestedEndTime;
		this.estimatedTime = estimatedTime;
		this.setCompany(company);
		this.requiredSkill = new HashSet<Skill>();
	}

	/**
	 * Gets the task's unique identifier
	 * @return the task's unique identifier
	 */
	public Long getTaskID() {
		return taskID;
	}

	/**
	 * Set's the task's unique identifier
	 * @param taskID The unique identifier to set the task's ID to
	 */
	public void setTaskID(Long taskID) {
		this.taskID = taskID;
	}

	/**
	 * Set's the required skill set of the task
	 * @param requiredSkill The skill set required to complete the task
	 */
	public void setRequiredSkill(Set<Skill> requiredSkill) {
		this.requiredSkill = requiredSkill;
	}

	/**
	 * Gets the skill set required to complete the task
	 * @return The skill set required to complete the task
	 */
	public Set<Skill> getRequiredSkill() {
		return requiredSkill;
	}

	/**
	 * Sets the assigned employees to the task
	 * @param users The users to assign the task to
	 */
	public void setUsers(Set<User> users) {
		this.users = users;
	}

	/**
	 * Gets the assigned employees of the task
	 * @return The assigned employees of the task
	 */
	public Set<User> getUsers() {
		return users;
	}

	/**
	 * Sets the status of the task
	 * @param status The status to assign the task
	 */
	public void setStatus(TaskStatus status) {
		this.status = status;
	}

	/**
	 * Sets the priority of the task
	 * @param priority The priority to set for the task
	 */
	public void setPriority(TaskPriority priority) {
		this.priority = priority;
	}

	/**
	 * Gets the task's status
	 * @return The task's status
	 */
	public TaskStatus getStatus() {
		return status;
	}

	/**
	 * Gets the title of the task
	 * @return The task title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title of the task
	 * @param title The title to give the task
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Gets the description of the task
	 * @return The tasks description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description of the task
	 * @param description The description to give the task
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the location of the task 
	 * @return The task's location
	 */
	public Address getLocation() {
		return location;
	}

	/**
	 * Sets the location of the task
	 * @param location The location to give the task
	 */
	public void setLocation(Address location) {
		this.location = location;
	}

	/**
	 * Gets the notes of the task
	 * @return The task's notes
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * Sets the notes of the task
	 * @param notes The notes to set for the task
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}

	/**
	 * Gets the created date of the task
	 * @return The date the task was created
	 */
	public Date getCreatedDate() {
		return createdDate;
	}

	/**
	 * Sets the created date of the task
	 * @param createdDate The date the task was created
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * Gets the suggested start time for the task
	 * @return The suggested time to start the task
	 */
	public Date getSuggestedStartTime() {
		return suggestedStartTime;
	}

	/**
	 * Sets the suggested start time for the task
	 * @param suggestedStartTime The suggested time to start
	 */
	public void setSuggestedStartTime(Date suggestedStartTime) {
		this.suggestedStartTime = suggestedStartTime;
	}

	/**
	 * Gets the suggested end time for the task
	 * @return The suggested time to end the task
	 */
	public Date getSuggestedEndTime() {
		return suggestedEndTime;
	}

	/**
	 * Sets the suggested end time for the task
	 * @param suggestedEndTime The suggested time to end
	 */
	public void setSuggestedEndTime(Date suggestedEndTime) {
		this.suggestedEndTime = suggestedEndTime;
	}

	/**
	 * Gets the scheduled start time for the task
	 * @return The scheduled time to start the task
	 */
	public Date getScheduledStartTime() {
		return scheduledStartTime;
	}

	/**
	 * Gets the scheduled start time for the task
	 * @param scheduledStartTime The scheduled tome to start the task
	 */
	public void setScheduledStartTime(Date scheduledStartTime) {
		this.scheduledStartTime = scheduledStartTime;
	}

	/**
	 * Gets the sscheduled end time for the task
	 * @return The scheduled time to end the task
	 */
	public Date getScheduledEndTime() {
		return scheduledEndTime;
	}

	/**
	 * Sets the suggested start time for the task
	 * @param scheduledEndTime The scheduled end time of the task
	 */
	public void setScheduledEndTime(Date scheduledEndTime) {
		this.scheduledEndTime = scheduledEndTime;
	}

	/**
	 * Gets the estimated time to complete the task
	 * @return The estimated time to complete the task
	 */
	public Double getEstimatedTime() {
		return estimatedTime;
	}

	/**
	 * Sets the estimated time to complete the task
	 * @param estimatedTime The estimated time to complete the task
	 */
	public void setEstimatedTime(Double estimatedTime) {
		this.estimatedTime = estimatedTime;
	}

	/* (non-Javadoc)
	 * @see edu.calpoly.csc.luna.turboplan.core.modelconverter.MobileModelConverter#convert2MobileModel()
	 */
	@Override
	public MobileTask convert2MobileModel() {
		return MobileUtil.hib2mobile(this);
	}

	/*
	 * (non-Javadoc)
	 * @see edu.calpoly.csc.luna.turboplan.core.modelconverter.GwtModelConverter#convert2GwtModel()
	 */
	@Override
	public GwtTask convert2GwtModel() {
		return GwtUtil.hib2gwt(this);
	}

	/**
	 * Sets the task's customer's first name
	 * @param customerFirstName The first name to give the task's customer
	 */
	public void setCustomerFirstName(String customerFirstName) {
		this.customerFirstName = customerFirstName;
	}

	/**
	 * Gets the task's customer's first name
	 * @return The task's customer's first name
	 */
	public String getCustomerFirstName() {
		return customerFirstName;
	}

	/**
	 * Sets the task's customer's last name
	 * @param customerLastName The task's customer's last name
	 */
	public void setCustomerLastName(String customerLastName) {
		this.customerLastName = customerLastName;
	}

	/**
	 * Gets the task's customer's last name
	 * @return
	 */
	public String getCustomerLastName() {
		return customerLastName;
	}

	/**
	 * Sets the task's customer's home phone number
	 * @param homeNum The task's customer's home phone number
	 */
	public void setHomeNum(String homeNum) {
		this.homeNum = homeNum;
	}

	/**
	 * Gets the task's customer's home phone number
	 * @return The task's customer's home phone number
	 */
	public String getHomeNum() {
		return homeNum;
	}

	/**
	 * Sets the task's customer's mobile phone number
	 * @param mobileNum The task's customer's mobile phone number
	 */
	public void setMobileNum(String mobileNum) {
		this.mobileNum = mobileNum;
	}

	/**
	 * Gets the task's customer's mobile phone number
	 * @return The task's customer's mobile phone number
	 */
	public String getMobileNum() {
		return mobileNum;
	}

	/**
	 * Sets the company the task belongs to
	 * @param company
	 */
	public void setCompany(Company company) {
		this.company = company;
	}

	/**
	 * Gets the company the task belongs to
	 * @return The company the task belongs to
	 */
	public Company getCompany() {
		return company;
	}
	
	/**
	 * Sets the time spent on a task
	 * @param timespent
	 */
	public void setTimespent(Double timespent) {
		this.timespent = timespent;
	}

	/**
	 * Gets the time spent on a task
	 * @return the time spent on the task
	 */
	public Double getTimespent() {
		return timespent;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((company == null) ? 0 : company.hashCode());
		result = prime * result
				+ ((createdDate == null) ? 0 : createdDate.hashCode());
		result = prime
				* result
				+ ((customerFirstName == null) ? 0 : customerFirstName
						.hashCode());
		result = prime
				* result
				+ ((customerLastName == null) ? 0 : customerLastName.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result
				+ ((estimatedTime == null) ? 0 : estimatedTime.hashCode());
		result = prime * result + ((homeNum == null) ? 0 : homeNum.hashCode());
		result = prime * result
				+ ((location == null) ? 0 : location.hashCode());
		result = prime * result
				+ ((mobileNum == null) ? 0 : mobileNum.hashCode());
		result = prime * result + ((notes == null) ? 0 : notes.hashCode());
		result = prime * result
				+ ((priority == null) ? 0 : priority.hashCode());
		result = prime * result
				+ ((requiredSkill == null) ? 0 : requiredSkill.hashCode());
		result = prime
				* result
				+ ((scheduledEndTime == null) ? 0 : scheduledEndTime.hashCode());
		result = prime
				* result
				+ ((scheduledStartTime == null) ? 0 : scheduledStartTime
						.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime
				* result
				+ ((suggestedEndTime == null) ? 0 : suggestedEndTime.hashCode());
		result = prime
				* result
				+ ((suggestedStartTime == null) ? 0 : suggestedStartTime
						.hashCode());
		result = prime * result + ((taskID == null) ? 0 : taskID.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((users == null) ? 0 : users.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof Task))
			return false;
		Task other = (Task) obj;
		if (company == null) {
			if (other.company != null)
				return false;
		} else if (!company.equals(other.company))
			return false;
		if (createdDate == null) {
			if (other.createdDate != null)
				return false;
		} else if (!createdDate.equals(other.createdDate))
			return false;
		if (customerFirstName == null) {
			if (other.customerFirstName != null)
				return false;
		} else if (!customerFirstName.equals(other.customerFirstName))
			return false;
		if (customerLastName == null) {
			if (other.customerLastName != null)
				return false;
		} else if (!customerLastName.equals(other.customerLastName))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (estimatedTime == null) {
			if (other.estimatedTime != null)
				return false;
		} else if (!estimatedTime.equals(other.estimatedTime))
			return false;
		if (homeNum == null) {
			if (other.homeNum != null)
				return false;
		} else if (!homeNum.equals(other.homeNum))
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (mobileNum == null) {
			if (other.mobileNum != null)
				return false;
		} else if (!mobileNum.equals(other.mobileNum))
			return false;
		if (notes == null) {
			if (other.notes != null)
				return false;
		} else if (!notes.equals(other.notes))
			return false;
		if (priority == null) {
			if (other.priority != null)
				return false;
		} else if (!priority.equals(other.priority))
			return false;
		if (requiredSkill == null) {
			if (other.requiredSkill != null)
				return false;
		} else if (!requiredSkill.equals(other.requiredSkill))
			return false;
		if (scheduledEndTime == null) {
			if (other.scheduledEndTime != null)
				return false;
		} else if (!scheduledEndTime.equals(other.scheduledEndTime))
			return false;
		if (scheduledStartTime == null) {
			if (other.scheduledStartTime != null)
				return false;
		} else if (!scheduledStartTime.equals(other.scheduledStartTime))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (suggestedEndTime == null) {
			if (other.suggestedEndTime != null)
				return false;
		} else if (!suggestedEndTime.equals(other.suggestedEndTime))
			return false;
		if (suggestedStartTime == null) {
			if (other.suggestedStartTime != null)
				return false;
		} else if (!suggestedStartTime.equals(other.suggestedStartTime))
			return false;
		if (taskID == null) {
			if (other.taskID != null)
				return false;
		} else if (!taskID.equals(other.taskID))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (users == null) {
			if (other.users != null)
				return false;
		} else if (!users.equals(other.users))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see edu.calpoly.csc.luna.turboplan.core.entity.BaseEntity#toString()
	 */
	@Override
	public String toString() {
		return StringUtil.beanToString(this, "users").toString();
	}


}
