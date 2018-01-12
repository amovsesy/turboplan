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
 * This class is for a simple schedule generation
 * 
 */
public class SimpleSchedule {
	
	private static final Logger log = Logger.getLogger(SimpleSchedule.class);

	/**
	 * Generates a simple schedule that just looks for the first user that is
	 * avaliable for the task
	 * 
	 * @param companyId
	 *            The company for which to generate the schedule for
	 */
	public void generateSimpleSchedule(Long companyId, Long userId) {
		log.trace("Generating schedule for company [id=" + companyId + "]");
		List<User> allUsers = UserDao.getInstance()
				.getAllUsersInSameCompanyAsThisUserById(userId);
		List<Task> allTasks = TaskDao.getInstance()
				.getAllTaskBelongToCompanyById(companyId);

		for (Task task : allTasks) {
			for (User usr : allUsers) {
				int i = (task.getSuggestedStartTime().getHours() * 2);
				int end = (task.getSuggestedEndTime().getHours() * 2);
				int length;
				double time;
				boolean assignedFlag = false;
				
				if((task.getEstimatedTime()%0.5D) != 0) {
					time = task.getEstimatedTime() + 0.5D;
				}
				else {
					time = task.getEstimatedTime();
				}
				
				length = (int) (time * 2);
				
				end -= length;

				if (task.getSuggestedStartTime().getMinutes() == 30) {
					i++;
				}

				if (task.getSuggestedEndTime().getMinutes() == 30) {
					end++;
				}

				if ((task.getEstimatedTime() - (double) time) > 0.0D ) {
					end--;
					length++;
				}

				while (i < end) {
					int j;
					for (j = 0; j < length; j++) {
						if (!(usr.getProfile().getAvailability().getAvail(task
								.getSuggestedStartTime().getDay(), (i + j)))) {
							break;
						}
					}

					if (j == length) {
						for (j = 0; j < length; j++) {
							usr.getProfile().getAvailability().setAvail(
									task.getSuggestedStartTime().getDay(),
									(i + j), false);
						}

						UserDao.getInstance().updateUser(usr);

						task.setStatus(TaskStatus.Assigned);
						task.getUsers().add(usr);
						int hour;
						int min;

						if ((i % 2) == 0) {
							hour = i / 2;
							min = 0;
						} else {
							hour = (i - 1) / 2;
							min = 30;
						}

						Date startTime = new Date(task.getSuggestedStartTime()
								.getYear(), task.getSuggestedStartTime()
								.getMonth(), task.getSuggestedStartTime()
								.getDate(), hour, min);

						if ((length % 2) == 0) {
							hour += (length / 2);
						} else {
							hour += ((length - 1) / 2);
							min = (min + 30) % 60;

							if (min == 0) {
								hour++;
							}
						}

						Date endTime = new Date(task.getSuggestedStartTime()
								.getYear(), task.getSuggestedStartTime()
								.getMonth(), task.getSuggestedStartTime()
								.getDate(), hour, min);

						task.setScheduledStartTime(startTime);
						task.setScheduledEndTime(endTime);
						task.setSuggestedStartTime(startTime);
						task.setSuggestedEndTime(endTime);

						assignedFlag = true;

						TaskDao.getInstance().updateTask(task);

						break;
					}

					i++;
				}

				if (assignedFlag) {
					break;
				}
			}
		}
	}
}
