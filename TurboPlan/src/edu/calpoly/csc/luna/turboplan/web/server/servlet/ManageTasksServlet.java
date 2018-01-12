package edu.calpoly.csc.luna.turboplan.web.server.servlet;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.allen_sauer.gwt.log.client.Log;

import edu.calpoly.csc.luna.turboplan.core.dao.TaskDao;
import edu.calpoly.csc.luna.turboplan.core.dao.UserDao;
import edu.calpoly.csc.luna.turboplan.core.entity.Task;
import edu.calpoly.csc.luna.turboplan.core.entity.User;
import edu.calpoly.csc.luna.turboplan.core.scheduler.DistanceSchedule;
import edu.calpoly.csc.luna.turboplan.core.scheduler.Scheduler;
import edu.calpoly.csc.luna.turboplan.core.scheduler.SchedulingOptions;
import edu.calpoly.csc.luna.turboplan.core.util.GwtUtil;
import edu.calpoly.csc.luna.turboplan.web.client.model.GwtTask;
import edu.calpoly.csc.luna.turboplan.web.client.model.GwtUser;
import edu.calpoly.csc.luna.turboplan.web.client.service.ManageTasksService;

public class ManageTasksServlet extends BaseTurboPlanServlet implements ManageTasksService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5119932489568292756L;

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
			long minDate, long maxDate, boolean directions,
			boolean random, boolean priority, boolean shortEstTime,
         	boolean longEstTime, boolean startDate, boolean dueDate, boolean reverse) {
		
		if(directions) {
			try {
				DistanceSchedule.class.newInstance().generateSchedule(companyId, userId);
			} catch (Exception e) {
				e.printStackTrace();
			} 
		} else {
			SchedulingOptions opt = new SchedulingOptions(minDate, maxDate, directions,
				random, priority, shortEstTime,
				longEstTime, startDate, dueDate, reverse);
			Scheduler.newInstance(companyId, userId, opt).run(opt);
		}
	}
	
	@Override
	public Boolean assignTask(Long companyId, Long userId, Long taskId) {
		return Scheduler.newInstance(companyId, userId, taskId).assign();
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
	public Set<GwtTask> getNonCompleteTasks(Long id) {
		List<Task> tasks = TaskDao.getInstance().getAllTaskNotComplete(id);
		
		return GwtUtil.hib2gwtTaskSet(tasks);
	}

	
	@Override
	public GwtTask getTaskById(Long id) {
		return GwtUtil.hib2gwt(TaskDao.getInstance().getTaskById(id));
	}
	
	/**
	 * @author Paul De Leon
	 */
	@Override
	public Set<GwtTask> getTasksForUserOnDate(Long id, Date date) {
		List<Task> tasks = TaskDao.getInstance().getTasksForUserOfDate(id, date);
		
		return GwtUtil.hib2gwtTaskSet(tasks);
	}

	/**
	 * @author Paul De Leon
	 */
	@Override
	public List<List<GwtUser>> getUsrsHaveTaskForEachDayOfWeek(Long companyID,
			Date dayInWeek) {
		//Get List of all Users
		List<List<User>> users = 
			TaskDao.getInstance().getAllUsersHaveTaskForEachDayOfWeek(companyID, dayInWeek);
		
		ArrayList<List<GwtUser>> gUsers = new ArrayList<List<GwtUser>>();
		
		ArrayList<GwtUser> sun, mon, tues, wed, thurs, fri, sat;
		sun 	= new ArrayList<GwtUser>();
		mon 	= new ArrayList<GwtUser>();
		tues 	= new ArrayList<GwtUser>();
		wed 	= new ArrayList<GwtUser>();
		thurs 	= new ArrayList<GwtUser>();
		fri 	= new ArrayList<GwtUser>();
		sat 	= new ArrayList<GwtUser>();
		
		gUsers.add(sun);
		gUsers.add(mon);
		gUsers.add(tues);
		gUsers.add(wed);
		gUsers.add(thurs);
		gUsers.add(fri);
		gUsers.add(sat);

		//Convert to from List<List<User>> to <List<List<GwtUser>>
		for(int weekDay = 0; weekDay < users.size(); weekDay++) {
			List<User> usrsForDay = users.get(weekDay);
			
			for(int usrIdx = 0; usrIdx < usrsForDay.size(); usrIdx++) {
				Log.trace("\tAdding User...");
				GwtUser gUsrToAdd = GwtUtil.hib2gwt(usrsForDay.get(usrIdx));
				gUsers.get(weekDay).add(gUsrToAdd);
			}
		}
		
		return gUsers;
	}
}
