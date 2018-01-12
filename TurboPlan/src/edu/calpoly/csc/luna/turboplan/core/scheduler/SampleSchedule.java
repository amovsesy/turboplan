package edu.calpoly.csc.luna.turboplan.core.scheduler;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.apache.log4j.Logger;

import edu.calpoly.csc.luna.turboplan.core.dao.TaskDao;
import edu.calpoly.csc.luna.turboplan.core.dao.UserDao;
import edu.calpoly.csc.luna.turboplan.core.entity.Task;
import edu.calpoly.csc.luna.turboplan.core.entity.User;
import edu.calpoly.csc.luna.turboplan.core.entity.Task.TaskStatus;

/**
 * This class is an abstract schedule generation class
 *
 * @author Bradley Barbee
 * @version 0.2
 */
public abstract class SampleSchedule implements SchedulingStrategy {
	
	/** Represents the scheduling options for this schedule generation */
	private SchedulingOptions opt;
	/** Represents the number of hour in 1 half hour */
	protected static final double halfhour_hour = 0.5D;
	/** Represents the number of minutes in 1 hour */
	protected static final int hour_min = 60;
	/** Represents the number of minutes in 1 half hour */
	protected static final int halfhour_min = 30;
	/** Represents the number of milliseconds in 1 hour */
	protected static final long hour_ms = 3600000L;
	/** Represents the number of milliseconds in 1 half hour */
	protected static final long halfhour_ms = 1800000L;
	/** Represents the number of milliseconds in 1 minute */
	protected static final long min_ms = 60000L;
	/** Represents the number of milliseconds in 1 day */
	protected static final long day_ms = 86400000L;
	
	private static final Logger log = Logger.getLogger(SampleSchedule.class);
	private Random rand = new Random();
	
	/**
	 * Attempts to assign a task to a list of users
	 * 
	 * @param task   - task to be assigned
	 * @param users  - list of current users
	 */
	public abstract Boolean assignTask(Task task, User user);

	public Boolean assign(Long taskId, Long userId) {
		Task task = TaskDao.getInstance().getTaskById(taskId);
		User user = UserDao.getInstance().getUserById(userId);
		return assignTask(task, user);
	}
	
	/**
	 * Call to generate a simple schedule
	 * 
	 * @param companyId
	 *            The company for which to generate the schedule for
	 */
	@Override
	public void generateSchedule(Long companyId, Long userId) {
		/** scheduling options with only start time first options set */
		SchedulingOptions opt = new SchedulingOptions(0,0,false,false,false,false,false,false,false,false);
		log.trace("Generating Schedule w/o Options");
		new StartTimeFirstSchedule().generateSchedule(companyId, userId, opt);
	}
	
	public void generateSchedule(Long companyId, Long userId, SchedulingOptions opt) {
		log.trace("Generating Schedule w/ Options");
		generateTemplateSchedule(companyId, userId, opt);
	}
	
	/**
	 * Calls UserDAO
	 */
	public List<User> getAllUsers(Long userId) {
		log.trace("Getting all Users from company of user: " + userId);
		List<User> users = UserDao.getInstance().getAllUsersInSameCompanyAsThisUserById(userId);
		
		if(opt.getRandom()) {
			log.trace("Random Employee Assignment Initiated");
			Collections.shuffle(users);
		}
		
		return users;
	}
	
	/**
	 * Calls TaskDAO
	 */
	public List<Task> getAllTasks(Long companyId) {
		
		log.trace("Getting all Tasks from company: " + companyId);
		StringBuilder sb = new StringBuilder();
		
		sb.append("from Task where company = ?");
		
		if(opt.isOrderByTask()) {
			log.trace("Task Query ordered by...");
			sb.append("order by ");
		} else {
			sb.append("  ");
		}

		if(opt.getPriority()) {
			log.trace(" ..Highest Priority");
			sb.append("priority desc, ");
		}
		
		if(opt.getShortest() && opt.getLongest()) {
			// Do nothing!
		} else if(opt.getShortest()) {
			log.trace(" ..Shortest Estimated Time");
			sb.append("estimatedTime asc, ");			
		} else if(opt.getLongest()) {
			log.trace(" ..Longest Estimated Time");
			sb.append("estimatedTime desc, ");			
		}

		if(opt.getStart() && opt.getDue()) {
			// Do nothing!
		} else if(opt.getStart()) {
			log.trace(" ..Start Date");
			sb.append("suggestedStartTime asc, ");			
		} else if(opt.getDue()) {
			log.trace(" ..Due Date");
			sb.append("suggestedEndTime asc, ");			
		}

		log.trace("Querying: " + sb.toString().substring(0, sb.toString().length()-2));
		return TaskDao.getInstance().getAllTaskBelongToCompanyByIdQuery(companyId,
				sb.toString().substring(0, sb.toString().length()-2));
	}	
	
	/**
	 * Generates a template schedule that just looks for
	 * options for generation
	 * 
	 * @param companyId
	 *            The company for which to generate the schedule for
	 */
	protected void generateTemplateSchedule(Long companyId, Long userId, SchedulingOptions opt) {
		log.trace("Generating schedule for company [id=" + companyId + "]");
		this.opt = opt;
		List<User> allUsers = getAllUsers(userId);
		List<Task> allTasks = getAllTasks(companyId);
		
		for (Task task : allTasks) {

			log.debug("Generation - Assigning task: " + task.getTitle());
			if(task == null) {
				continue;
			}
			
			for (User user : allUsers) {

				if(user == null) {
					continue;
				}
				
				if (task.getStatus() == TaskStatus.Assigned) {
					log.debug("Generation - Task Already Assigned " + task.getTitle());
					break;
				}
				
				log.debug("Generation - Checking employee: " + user.getUserID());
				if(assignTask(task, user)) {
					break;
				}
			}

			if(opt.getRandom()) {
				log.debug("Employees shuffled");
				Collections.shuffle(allUsers, new Random(rand.nextLong()));
			}
		}
	}
	
	/**
	 * Assigns a user to a particular task
	 * 
	 * @param task    - task to be assigned
	 * @param user    - assignee of the task
	 * @param start   - start time of the task
	 */
	protected User assignUserToTask(User user, Task task, Date start) {

		/** Task already contains updated information from assignTaskToUser */
		user.getTasks().add(task);

		return user;
	}
	
	/**
	 * Assigns a task to a particular user
	 * 
	 * @param task    - task to be assigned
	 * @param user    - assignee of the task
	 * @param start   - start time of the task
	 */
	protected Task assignTaskToUser(User user, Task task, Date start) {

		double task_len = roundEstimatedTimeToMinutes(task.getEstimatedTime());
		Date end = new Date();
		
		task.setStatus(TaskStatus.Assigned);
		task.getUsers().add(user);

		task.setSuggestedStartTime(roundUpTime(start));
		task.setScheduledStartTime(roundUpTime(start));
		
		/** Add task length to start time to find end time */
		end.setTime(roundUpTime(start).getTime() + 
				(halfhour_ms * (long)(task_len / halfhour_min))); 
		
		task.setSuggestedEndTime(end);
		task.setScheduledEndTime(end);
		
		return task;
	}
	   
	/** 
	 * Converts a given Date to a TurboPlan availability value
	 *  
	 * @param date	current date
	 * @return		availability value
	 */
	protected int toAvailValue(Date date) {
		
		date = roundUpTime(date);
		return (date.getHours() * 2) + ((date.getMinutes() == 30) ? 1 : 0);
	}
	
	/**
	 * Verifies there are no assigned tasks at the current date
	 * 
	 * @param tasks     current tasks
	 * @param cur_date  current date to be checked
	 * @return		    true if no tasks at current time, false otherwise
	 */
	protected boolean checkNoTasksAtCurrentTime(Set<Task> tasks, Date cur_date) {
		
		if(tasks==null) {
			return true;
		}
		
		for(Task task : tasks) {
			
			if(task == null) {
				continue;
			}
			
			if((cur_date.after(task.getScheduledStartTime()) ||
					cur_date.equals(task.getScheduledStartTime())) &&
					(cur_date.before(task.getScheduledEndTime()) ||
							cur_date.equals(task.getScheduledEndTime()))) {
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Verifies there are no assigned tasks AND user is available
	 * 
	 * @param user      current user
	 * @param date  	current date to be checked
	 * @return		    true if no tasks and available at current time, false otherwise
	 */
	protected boolean isUserAvailable(User user, Date date) {

		/** get availability value to use for getAvail function */
		int avail_val = toAvailValue(date);
		
		/** if the time block is available (availability and no task) */
		try {
			if (user.getProfile().getAvailability().getAvail(date.getDay(), avail_val)
					&& checkNoTasksAtCurrentTime(user.getTasks(), date)) {
				return true;
			}
		} catch(Exception e) {
		}
		
		return false;
	}
	
	/**
	 * Rounds up the estimated task time to the nearest half hour
	 * and converts from hours to minutes
	 * 
	 * @param time	initial estimated task time (in hours)
	 * @return		rounded value (in minutes)
	 */
	protected double roundEstimatedTimeToMinutes(Double time) {
		
		double task_len;
		
		if((time % halfhour_hour) != 0) {
			task_len = time - (time % halfhour_hour) + halfhour_hour;
		} else {
			task_len = time;
		}
		
		return task_len * hour_min;
	}
	
	/**
	 * Rounds up the time of the Date to the nearest half hour
	 * 
	 * @param date	date to be rounded
	 * @return		date with rounded time
	 */
	protected Date roundUpTime(Date date) {
		
		long time = date.getTime();
		long rounded_time;
		
		if((time % halfhour_ms) != 0) {
			rounded_time = time - (time % halfhour_ms) + halfhour_ms;
		} else {
			rounded_time = time;
		}
		
		date.setTime(rounded_time);
		return date;
	}

	/**
	 * Rounds down the time of the Date to the nearest half hour
	 * 
	 * @param date	date to be rounded
	 * @return		date with rounded time
	 */
	protected Date roundDownTime(Date date) {
		
		long time = date.getTime();
		long rounded_time;
		
		if((time % halfhour_ms) != 0) {
			rounded_time = time - (time % halfhour_ms);
		} else {
			rounded_time = time;
		}
		
		date.setTime(rounded_time);
		return date;
	}
	
	/**
	 * Checks the minimum start date for a task
	 * 
	 * @param date	date to be checked
	 * @return		date with minimum start time
	 */
	protected Date checkMin(Date date) {
		if(opt.getMinDate() > 0) {
			if(date.getTime() < opt.getMinDate()) {
				date.setTime(opt.getMinDate());
			}
		}
		
		return date;
	}
	

	/**
	 * Checks the maximum end date for a task
	 * 
	 * @param date	date to be checked
	 * @return		date with maximum end time
	 */
	protected Date checkMax(Date date) {
		if(opt.getMaxDate() > 0) {
			if(date.getTime() > (opt.getMaxDate()+day_ms)) {
				date.setTime(opt.getMaxDate());
			}
		}
		
		return date;
	}
	
	/**
	 * Ensures that the length of the task is less than the (end - start) times
	 * 
	 * @param task		current Task
	 * @param task_len	estimated task length in minutes
	 * @return
	 */
	protected boolean checkTaskLength(Task task, double task_len) {
		
		if((roundDownTime(task.getSuggestedEndTime()).getTime()
				- roundUpTime(task.getSuggestedStartTime()).getTime())
				>= task_len * min_ms) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * Adjusts a current time by a task length to its start time
	 *  
	 * @param date		current date/time
	 * @param task_len	task length (in minutes)
	 * @return			date with new date/time
	 */
	protected Date resetCurrentTimeByTaskLengh(Date date, double task_len) {
		
		date.setTime(date.getTime() -
				(halfhour_ms * 
						(long)(task_len / halfhour_min)));
		
		return date;
	}
	
	protected SchedulingOptions getOptions() {
		return opt;
	}
	
	protected void setOptions(SchedulingOptions opt) {
		this.opt = opt;
	}
}
