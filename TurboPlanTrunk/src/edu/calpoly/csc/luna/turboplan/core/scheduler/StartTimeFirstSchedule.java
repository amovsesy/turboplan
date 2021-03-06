package edu.calpoly.csc.luna.turboplan.core.scheduler;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import edu.calpoly.csc.luna.turboplan.core.dao.TaskDao;
import edu.calpoly.csc.luna.turboplan.core.dao.UserDao;
import edu.calpoly.csc.luna.turboplan.core.entity.Task;
import edu.calpoly.csc.luna.turboplan.core.entity.User;
import edu.calpoly.csc.luna.turboplan.core.entity.Task.TaskStatus;

/**
 * This class is for a schedule generation by suggested start time
 * 
 * @author Bradley Barbee
 * @version 0.2
 */
public class StartTimeFirstSchedule extends SampleSchedule implements SchedulingStrategy {
	
	private static final Logger log = Logger.getLogger(StartTimeFirstSchedule.class);
	
	/**
	 * Calls UserDAO
	 */
	public List<User> getAllUsers(Long userId) {
		
		return null;
	}
	
	/**
	 * Calls TaskDAO
	 */
	public List<Task> getAllTasks(Long companyId) {
		
		return null;
	}

	/**
	 * Attempts to assign a task to a list of users
	 * 
	 * @param task   - task to be assigned
	 * @param users  - list of current users
	 */
	public void assignTask(Task task, User user) {
		
		int i = 0;
		int avail_val = 0;
		int time_block = 0;
		Date start_cur = new Date();
			
		/** rounds the estimated time to the nearest half hour */
		double task_len = roundEstimatedTime(task);

		start_cur = task.getSuggestedStartTime();
						
		while(start_cur.before(task.getSuggestedEndTime())) {
				
			/** get availability value to use for getAvail function */
			avail_val = (start_cur.getHours() * 2) +
						((start_cur.getMinutes() == 30) ? 1 : 0);
			
			/** while the time block is available (availability and no task) */
			if (user.getProfile().getAvailability().getAvail(start_cur.getDay(), avail_val)
					&& noTasksAtCurrentTime(user.getTasks(), start_cur)) {
				time_block += halfhour_min;
			} else {
				time_block = 0;
			}
				
			start_cur.setTime(start_cur.getTime() + halfhour_ms);
				
			if (time_block == task_len) {
				/** adjust start time for task length */
				start_cur.setTime(start_cur.getTime() -
						(halfhour_ms * 
								(long)(task_len / halfhour_min)));
				assignTaskToUser(task, user, start_cur);
				break;
			}
		}
	}
}
