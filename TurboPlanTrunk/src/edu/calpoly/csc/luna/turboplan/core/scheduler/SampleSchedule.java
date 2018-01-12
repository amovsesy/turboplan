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
import edu.calpoly.csc.luna.turboplan.core.scheduler.SimpleSchedule;

/**
 * This class is an abstract schedule generation class
 *
 * @author Bradley Barbee
 * @version 0.2
 */
public abstract class SampleSchedule implements SchedulingStrategy {
	
	/** Represents the number of minutes in 1 hour */
	protected static final int hour_min = 60;
	/** Represents the number of minutes in 1 half hour */
	protected static final int halfhour_min = 30;
	/** Represents the number of milliseconds in 1 hour */
	protected static final long hour_ms = 3600000L;
	/** Represents the number of milliseconds in 1 half hour */
	protected static final long halfhour_ms = 1800000L;
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
	public abstract void assignTask(Task task, User user);

	
	/**
	 * Call to generate a simple schedule
	 * 
	 * @param companyId
	 *            The company for which to generate the schedule for
	 */
	@Override
	public void generateSchedule(Long companyId, Long userId) {
		/** scheduling options with only start time first options set */
		SchedulingOptions opt = new SchedulingOptions(false,false,false,false,false,false,false);
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
	public List<User> getAllUsers(Long userId, SchedulingOptions opt) {
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
	public List<Task> getAllTasks(Long companyId, SchedulingOptions opt) {
		
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
		} else if(opt.getLongest()) {
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
		List<User> allUsers = getAllUsers(userId, opt);
		List<Task> allTasks = getAllTasks(companyId, opt);
		
		for (Task task : allTasks) {
			//log.debug("Generation - Assigning task: " + task.getTitle());
			for (User user : allUsers) {
				
				if (task.getStatus() == TaskStatus.Assigned) {
					//log.debug("Generation - Assigned " + task.getTitle());
					break;
				}
				
				//log.debug("Generation - Checking employee: " + user.getUserID());
				assignTask(task, user);
			}

			if(opt.getRandom()) {
				//log.debug("Employees shuffled");
				Collections.shuffle(allUsers, new Random(rand.nextLong()));
			}
		}
	}
	
	/**
	 * Assigns a task to a particular user
	 * 
	 * @param task    - task to be assigned
	 * @param user    - assignee of the task
	 * @param start   - start time of the task
	 */
	protected void assignTaskToUser(Task task, User user, Date start) {

		double task_len = roundEstimatedTime(task);
		
		task.setStatus(TaskStatus.Assigned);
		task.getUsers().add(user);

		task.setScheduledStartTime(start);
		
		start.setTime(start.getTime() + 
				(halfhour_ms * (long)(task_len / halfhour_min))); 
		
		task.setScheduledEndTime(start);

		TaskDao.getInstance().updateTask(task);
	}
	
	/**
	 * Rounds the estimated task time to the nearest half hour
	 * 
	 * @param task  current task
	 * @return		rounded value
	 */
	protected double roundEstimatedTime(Task task) {
		
		double task_len;
		
		if((task.getEstimatedTime()%0.5D) != 0) {
			task_len = (task.getEstimatedTime() - 
					(task.getEstimatedTime() % 0.5D) + 0.5D)
						* hour_min;
		} else {
			task_len = task.getEstimatedTime() * hour_min;
		}
		
		return task_len;
	}

	/**
	 * Verifies there are no assigned tasks at the current date
	 * 
	 * @param tasks     current tasks
	 * @param cur_date  current date to be checked
	 * @return		    true if no tasks at current time, false otherwise
	 */
	protected boolean noTasksAtCurrentTime(Set<Task> tasks, Date cur_date) {
		
		for(Task task : tasks) {
			if(cur_date.after(task.getScheduledStartTime()) &&
					cur_date.before(task.getScheduledEndTime())) {
				return false;
			}
		}
		
		return true;
	}
}
