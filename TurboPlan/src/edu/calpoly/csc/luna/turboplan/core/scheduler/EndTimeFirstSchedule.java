package edu.calpoly.csc.luna.turboplan.core.scheduler;

import java.util.Date;
import org.apache.log4j.Logger;

import edu.calpoly.csc.luna.turboplan.core.dao.TaskDao;
import edu.calpoly.csc.luna.turboplan.core.entity.Task;
import edu.calpoly.csc.luna.turboplan.core.entity.User;

/**
 * This class is for a schedule generation by suggested end times first
 * 
 * @author Bradley Barbee
 * @version 0.2
 */
public class EndTimeFirstSchedule extends SampleSchedule implements SchedulingStrategy {
	
	private static final Logger log = Logger.getLogger(EndTimeFirstSchedule.class);
	
	/**
	 * Attempts to assign a task to a user
	 * 
	 * @param task   - task to be assigned
	 * @param user   - user to be scheduled
	 */
	public Boolean assignTask(Task task, User user) {
		
		int time_block = 0;
		Date cur_date = new Date();
		Date start_date = new Date();
		
		/** rounds the estimated time to the nearest half hour */
		double task_len = roundEstimatedTimeToMinutes(task.getEstimatedTime());

		/** round down the suggested end time to the nearest half hour */
		cur_date = checkMax(roundDownTime(task.getSuggestedEndTime()));
		/** round up the suggested start time to the nearest half hour */
		start_date = checkMin(roundUpTime(task.getSuggestedStartTime()));
		
		/** availability is checked in a forward manner, need to adjust half hour */
		cur_date.setTime(cur_date.getTime() - halfhour_ms);
			
		/** iterate, checking for availability in half hour time blocks */
		while(cur_date.after(start_date) || cur_date.equals(start_date)) {
				
			/** if the user is available increase available time block by half hour
			 *   otherwise, reset available time block to 0 */
			if (isUserAvailable(user, cur_date)) {
				time_block += halfhour_min;
			} else {
				time_block = 0;
			}
				
			/** adjust current time by half hour to test previous time block */
			cur_date.setTime(cur_date.getTime() - halfhour_ms);
			
			/** if available time block == estimated task length, assign task */
			if (time_block == task_len) {
				
				/** associate the task and user and update database */
				task = assignTaskToUser(user, task, cur_date);
				TaskDao.getInstance().updateTask(task);
				return true;
			}
		}
		
		return false;
	}
}
