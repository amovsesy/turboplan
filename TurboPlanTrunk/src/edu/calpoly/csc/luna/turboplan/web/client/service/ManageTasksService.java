package edu.calpoly.csc.luna.turboplan.web.client.service;

import java.util.List;
import java.util.Set;

import com.google.gwt.user.client.rpc.AsyncCallback;

import edu.calpoly.csc.luna.turboplan.core.scheduler.SchedulingOptions;
import edu.calpoly.csc.luna.turboplan.web.client.model.GwtTask;

public interface ManageTasksService extends BaseService {
	public Set<GwtTask> getTasksForUserById(Long id);
	
	public void addTaskForUserById(Long id, GwtTask task);
	
	public void updateTask(GwtTask task);
	
	public void deleteTask(GwtTask task);
	
	public void generateSchedule(Long comapanyId, Long userId,
			boolean random, boolean priority, boolean shortEstTime,
         	boolean longEstTime, boolean startDate, boolean dueDate, boolean reverse);
	
	public void addTask(GwtTask task);
	
	public Set<GwtTask> getTaskByCompanyId(Long id);
	
	public GwtTask getTaskById(Long id);
}
