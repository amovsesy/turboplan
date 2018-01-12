package edu.calpoly.csc.luna.turboplan.web.client.service;

import java.util.List;
import java.util.Set;

import com.google.gwt.user.client.rpc.AsyncCallback;

import edu.calpoly.csc.luna.turboplan.core.scheduler.SchedulingOptions;
import edu.calpoly.csc.luna.turboplan.web.client.model.GwtTask;

public interface ManageTasksServiceAsync extends BaseServiceAsync {
	public void getTasksForUserById(Long id, AsyncCallback<Set<GwtTask>> callback);
	
	public void addTaskForUserById(Long id, GwtTask task, AsyncCallback<Object> callback);
	
	public void updateTask(GwtTask task, AsyncCallback<Object> callback);
	
	public void deleteTask(GwtTask task, AsyncCallback<Object> callback);
		
	public void generateSchedule(Long companyId, Long userId,
			boolean random, boolean priority, boolean shortEstTime,
         	boolean longEstTime, boolean startDate, boolean dueDate, boolean reverse,
         	AsyncCallback<Object> callback);
	
	public void addTask(GwtTask task, AsyncCallback<Object> callback);
	
	public void getTaskByCompanyId(Long id, AsyncCallback<Set<GwtTask>> asyncCallback);
	
	public void getTaskById(Long id, AsyncCallback<GwtTask> asyncCallback);
}
