package edu.calpoly.csc.luna.turboplan.web.client.service;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.google.gwt.user.client.rpc.AsyncCallback;

import edu.calpoly.csc.luna.turboplan.web.client.model.GwtTask;
import edu.calpoly.csc.luna.turboplan.web.client.model.GwtUser;

public interface ManageTasksServiceAsync extends BaseServiceAsync {
	public void getTasksForUserById(Long id, AsyncCallback<Set<GwtTask>> callback);
	
	public void addTaskForUserById(Long id, GwtTask task, AsyncCallback<Object> callback);
	
	public void updateTask(GwtTask task, AsyncCallback<Object> callback);
	
	public void deleteTask(GwtTask task, AsyncCallback<Object> callback);
		
	public void generateSchedule(Long companyId, Long userId,
			long minDate, long maxDate, boolean directions,
			boolean random, boolean priority, boolean shortEstTime,
         	boolean longEstTime, boolean startDate, boolean dueDate, boolean reverse,
         	AsyncCallback<Object> callback);
	
	public void assignTask(Long companyId, Long userId, Long taskId,
         	AsyncCallback<Boolean> callback);
	
	public void addTask(GwtTask task, AsyncCallback<Object> callback);
	
	public void getTaskByCompanyId(Long id, AsyncCallback<Set<GwtTask>> asyncCallback);
	
	public void getNonCompleteTasks(Long id, AsyncCallback<Set<GwtTask>> asyncCallback);
	
	public void getTaskById(Long id, AsyncCallback<GwtTask> asyncCallback);
	
//	public void getTasksForUserOfDate(Long id, Date date, AsyncCallback<List<GwtTask>> callback);
	
	/**
	 * Gets all users that have tasks for each day of the week. 
	 * @param companyID the company that all the users belong to
	 * @param dayInWeek the day in the week
	 * @return List of Lists of all employees that have tasks for each day of the week
	 * from sun to sat.
	 */
	public void getUsrsHaveTaskForEachDayOfWeek(Long companyID, Date dayInWeek, 
			AsyncCallback<List<List<GwtUser>>> asyncCallback);
	
	/**
	 * Gets all tasks for user on specified day.
	 * @author Paul De Leon
	 * @param usrID
	 * @param dayOfTasks
	 * @return
	 */
	public void getTasksForUserOnDate(Long usrID, Date dayOfTasks, 
			AsyncCallback<Set<GwtTask>> asyncCallback);
}
