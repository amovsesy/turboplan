package edu.calpoly.csc.luna.turboplan.web.client.service;

import java.util.Date;
import java.util.List;
import java.util.Set;

import edu.calpoly.csc.luna.turboplan.web.client.model.GwtTask;
import edu.calpoly.csc.luna.turboplan.web.client.model.GwtUser;

public interface ManageTasksService extends BaseService {
	public Set<GwtTask> getTasksForUserById(Long id);
	
	public void addTaskForUserById(Long id, GwtTask task);
	
	public void updateTask(GwtTask task);
	
	public void deleteTask(GwtTask task);
	
	public void generateSchedule(Long comapanyId, Long userId,
			long minDate, long maxDate, boolean directions,
			boolean random, boolean priority, boolean shortEstTime,
         	boolean longEstTime, boolean startDate, boolean dueDate, boolean reverse);
	
	public Boolean assignTask(Long companyId, Long userId, Long taskId);
	
	public void addTask(GwtTask task);
	
	public Set<GwtTask> getTaskByCompanyId(Long id);
	
	public Set<GwtTask> getNonCompleteTasks(Long id);
	
	public GwtTask getTaskById(Long id);

//	public List<GwtTask> getTasksForUserOfDate(Long id, Date date);
	
	/**
	 * Gets all users that have tasks for each day of the week. 
	 * @author Paul De Leon
	 * @param companyID the company that all the users belong to
	 * @param dayInWeek the day in the week
	 * @return List of Lists of all employees that have tasks for each day of the week
	 * from sun to sat.
	 */
	public List<List<GwtUser>> getUsrsHaveTaskForEachDayOfWeek(Long companyID, Date dayInWeek);
	
	/**
	 * Gets all tasks for user on specified day.
	 * @author Paul De Leon
	 * @param usrID
	 * @param dayOfTasks
	 * @return
	 */
	public Set<GwtTask> getTasksForUserOnDate(Long usrID, Date dayOfTasks);
}
