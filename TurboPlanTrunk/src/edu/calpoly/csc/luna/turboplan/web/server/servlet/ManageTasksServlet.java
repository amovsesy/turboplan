package edu.calpoly.csc.luna.turboplan.web.server.servlet;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.calpoly.csc.luna.turboplan.core.dao.TaskDao;
import edu.calpoly.csc.luna.turboplan.core.dao.UserDao;
import edu.calpoly.csc.luna.turboplan.core.entity.Task;
import edu.calpoly.csc.luna.turboplan.core.entity.User;
import edu.calpoly.csc.luna.turboplan.core.scheduler.Scheduler;
import edu.calpoly.csc.luna.turboplan.core.scheduler.SchedulingOptions;
import edu.calpoly.csc.luna.turboplan.core.util.GwtUtil;
import edu.calpoly.csc.luna.turboplan.web.client.model.GwtTask;
import edu.calpoly.csc.luna.turboplan.web.client.service.ManageTasksService;

public class ManageTasksServlet extends BaseTurboPlanServlet implements ManageTasksService {

	@Override
	public Set<GwtTask> getTasksForUserById(Long id) {
		List<Task> tasks = UserDao.getInstance().getTasksByUserId(id);
		
		Set<GwtTask> ret = new HashSet<GwtTask>();
		
		for (Task task : tasks) {
			GwtTask gTask = GwtUtil.hib2gwt(task); 
			ret.add(gTask);
		}
		
		return ret;
	}

	@Override
	public void addTaskForUserById(Long id, GwtTask task) {
		Task eTask = GwtUtil.gwt2hib(task);
		
		User usr = UserDao.getInstance().getUserById(id);
		usr.getTasks().add(eTask);
		
		UserDao.getInstance().updateUser(usr);
	}

	@Override
	public void updateTask(GwtTask task) {
		Task eTask = GwtUtil.gwt2hib(task);
		TaskDao.getInstance().updateTask(eTask);
	}
	
	@Override
	public void deleteTask(GwtTask task) {
		Task eTask = GwtUtil.gwt2hib(task);
		TaskDao.getInstance().deleteTask(eTask);
	}
	
	@Override
	public void generateSchedule(Long companyId, Long userId,
			boolean random, boolean priority, boolean shortEstTime,
         	boolean longEstTime, boolean startDate, boolean dueDate, boolean reverse) {
		SchedulingOptions opt = new SchedulingOptions(random, priority, shortEstTime,
				longEstTime, startDate, dueDate, reverse);
		Scheduler.newInstance(companyId, userId, opt).run(opt);
	}
	
	@Override
	public void addTask(GwtTask task) {
		Task eTask = GwtUtil.gwt2hib(task);
		TaskDao.getInstance().addTask(eTask);
	}

	@Override
	public Set<GwtTask> getTaskByCompanyId(Long id) {
		List<Task> tasks = TaskDao.getInstance().getAllTaskBelongToCompanyById(id);
		
		return GwtUtil.hib2gwtTaskSet(tasks);
	}

	@Override
	public GwtTask getTaskById(Long id) {
		return GwtUtil.hib2gwt(TaskDao.getInstance().getTaskById(id));
	}
}
