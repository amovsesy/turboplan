package edu.calpoly.csc.luna.turboplan.core.scheduler;

import java.util.Date;

import org.apache.log4j.Logger;

import edu.calpoly.csc.luna.turboplan.core.dao.TaskDao;
import edu.calpoly.csc.luna.turboplan.core.entity.Task;
import edu.calpoly.csc.luna.turboplan.core.entity.User;

/**
 * This class is for a schedule generation by suggested start times first
 * 
 * @author Bradley Barbee
 * @version 0.2
 */
public class StartTimeFirstSchedule extends SampleSchedule implements SchedulingStrategy {
	
	private static final Logger log = Logger.getLogger(StartTimeFirstSchedule.class);

	/**
	 * Attempts to assign a task to a user
	 * 
	 * @param task   - task to be assigned
	 * @param user   - user to be scheduled 
	 */
	public Boolean assignTask(Task task, User user) {
		
		int time_block = 0;
		Date cur_date = new Date();
		Date end_date = new Date();
			
		/** rounds the estimated time to the nearest half hour */
		double task_len = roundEstimatedTimeToMinutes(task.getEstimatedTime());

		/** round up the suggested start time to the nearest half hour */
		cur_date = checkMin(roundUpTime(task.getSuggestedStartTime()));
		
		/** round down the suggested end time to the nearest half hour */
		end_date = checkMax(roundDownTime(task.getSuggestedEndTime()));
						
		/** iterate, checking for availability in half hour time blocks */
		while(cur_date.before(end_date)) {
				
			/** if the user is available increase available time block by half hour
			 *   otherwise, reset available time block to 0 */
			if (isUserAvailable(user, cur_date)) {
				time_block += halfhour_min;
			} else {
				time_block = 0;
			}
				
			/** adjust current time by half hour to test next time block */
			cur_date.setTime(cur_date.getTime() + halfhour_ms);
			
			/** if available time block == estimated task length, assign task */
			if (time_block == task_len) {
				
				/** adjust current time = (current time - task length) */
				cur_date = resetCurrentTimeByTaskLengh(cur_date, task_len);
				
				/** associate the task and user and update database */
				task = assignTaskToUser(user, task, cur_date);
				TaskDao.getInstance().updateTask(task);
				return true;
			}
		}
		
		return false;
	}
}
