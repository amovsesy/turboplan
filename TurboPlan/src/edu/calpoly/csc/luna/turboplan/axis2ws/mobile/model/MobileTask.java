package edu.calpoly.csc.luna.turboplan.axis2ws.mobile.model;

import java.lang.reflect.Field;

import org.apache.log4j.Logger;

import edu.calpoly.csc.luna.turboplan.core.entity.Task;
import edu.calpoly.csc.luna.turboplan.core.modelconverter.EntityModelConverter;
import edu.calpoly.csc.luna.turboplan.core.util.MobileUtil;
import edu.calpoly.csc.luna.turboplan.core.util.StringUtil;


public class MobileTask implements MobileModel, EntityModelConverter<Task> {
	private static final Logger log = Logger.getLogger(MobileTask.class);
	
	private static final MobileUser[] EMPTY_USER_LIST = new MobileUser[] {};
	public static final MobileTask[] EMPTY_MOBILETASK_ARRAY = new MobileTask[] {};
	
	public static enum TaskStatus {
		New, Assigned, Updated, Complete, Canceled
	};

	private long taskID;

	private String contactFirstName = "";
	
	private String contactLastName = "";

	private String contactHomePhone = "";
	
	private String contactMobilePhone = "";

	private String title = "";

	private String description = "";

	private long createdDate = 0L;

	private long dueDate = 0L;
	
	private long timespent = 0L;

	private String notes = "";

	private String status = "";

	/**
	 * location[0] = street address
	 * location[1] = city
	 * location[2] = country
	 * location[3] = state
	 * location[4] = zip
	 */
	private String[] location = new String[] {};

	private long scheduledStartTime = 0L;

	private long scheduledEndTime = 0L;
	
	private MobileUser[] users = EMPTY_USER_LIST;
	
	private String priority = "";

	public MobileTask() {
		super();
	}

	public long getTaskID() {
		return taskID;
	}

	public void setTaskID(long taskID) {
		this.taskID = taskID;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		if (title == null) {
			this.title = "";
		} else {
			this.title = title;
		}
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		if (description == null) {
			this.description = "";
		} else {
			this.description = description;
		}
	}

	public long getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(long createdDate) {
		this.createdDate = createdDate;
	}

	public long getDueDate() {
		return dueDate;
	}

	public void setDueDate(long dueDate) {
		this.dueDate = dueDate;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		if (notes == null) {
			this.notes = "";
		} else {
			this.notes = notes;
		}
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		if (status == null) {
			this.status = "";
		} else {
			this.status = status;
		}
	}

	public long getScheduledStartTime() {
		return scheduledStartTime;
	}

	public void setScheduledStartTime(long scheduledStartTime) {
		this.scheduledStartTime = scheduledStartTime;
	}

	public long getScheduledEndTime() {
		return scheduledEndTime;
	}

	public void setScheduledEndTime(long scheduledEndTime) {
		this.scheduledEndTime = scheduledEndTime;
	}

	public String getContactFirstName() {
		return contactFirstName;
	}

	public void setContactFirstName(String contactFirstName) {
		if (contactFirstName == null) {
			this.contactFirstName = "";
		} else {
			this.contactFirstName = contactFirstName;
		}
	}

	public String getContactLastName() {
		return contactLastName;
	}

	public void setContactLastName(String contactLastName) {
		if (contactLastName == null) {
			this.contactLastName = "";
		} else {
			this.contactLastName = contactLastName;
		}
	}

	public String getContactHomePhone() {
		return contactHomePhone;
	}

	public void setContactHomePhone(String contactHomePhone) {
		if (contactHomePhone == null) {
			this.contactHomePhone = "";
		} else {
			this.contactHomePhone = contactHomePhone;
		}
	}

	public String getContactMobilePhone() {
		return contactMobilePhone;
	}

	public void setContactMobilePhone(String contactMobilePhone) {
		if (contactMobilePhone == null) {
			this.contactMobilePhone = "";
		} else {
			this.contactMobilePhone = contactMobilePhone;
		}
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		if (priority == null) {
			this.priority = "";
		} else {
			this.priority = priority;
		}
	}

	@Override
	public Task toEntityModel() {
		return MobileUtil.mobile2hib(this);
	}

	@Override
	public String toString() {
		return StringUtil.beanToString(this, (Field[]) null).toString();
	}

	public void setUsers(MobileUser[] users) {
		if (users == null) {
			this.users = new MobileUser[] {};
		} else {
			this.users = users;
		}
	}

	public MobileUser[] getUsers() {
		return users;
	}

	public void setLocation(String[] location) {
		if (location == null) {
			this.location = new String[] {};
			return;
		}
		if (location.length < 5) {
			log.warn("MobileTask.location length < 5");
		}
		if (location != null) {
			this.location = location;
		}
	}

	public String[] getLocation() {
		if (location.length < 5) {
			log.warn("MobileTask.location length < 5");
		}
		return location;
	}

	public void setTimespent(long timespent) {
		this.timespent = timespent;
	}

	public long getTimespent() {
		return timespent;
	}

}
