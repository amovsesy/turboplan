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
 * This class is for a schedule generation by suggested end time
 * 
 * @author Bradley Barbee
 * @version 0.2
 */
public class EndTimeFirstSchedule extends SampleSchedule implements SchedulingStrategy {
	
	private static final Logger log = Logger.getLogger(EndTimeFirstSchedule.class);
	
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
		Date end_cur = new Date();
		
		/** rounds the estimated time to the nearest half hour */
		double task_len = roundEstimatedTime(task);

		end_cur = task.getSuggestedEndTime();
			
		/** availability is checked in a forward manner, need to adjust half hour */
		end_cur.setTime(end_cur.getTime() - halfhour_ms);
			
		while(end_cur.after(task.getSuggestedStartTime())) {
				
			/** get availability value to use for getAvail function */
			avail_val = (end_cur.getHours() * 2) +
						((end_cur.getMinutes() == 30) ? 1 : 0);
				
			/** while the time block is available (availability and no task) */
			if (user.getProfile().getAvailability().getAvail(end_cur.getDay(), avail_val)
					&& noTasksAtCurrentTime(user.getTasks(), end_cur)) {
				time_block += halfhour_min;
			} else {
				time_block = 0;
			}
				
			end_cur.setTime(end_cur.getTime() - halfhour_ms);
			
			if (time_block == task_len) {
				assignTaskToUser(task, user, end_cur);
				break;
			}
		}
	}
}
