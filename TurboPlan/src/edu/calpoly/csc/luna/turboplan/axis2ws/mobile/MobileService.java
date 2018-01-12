package edu.calpoly.csc.luna.turboplan.axis2ws.mobile;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import edu.calpoly.csc.luna.turboplan.axis2ws.mobile.model.MobileTask;
import edu.calpoly.csc.luna.turboplan.axis2ws.mobile.model.MobileUser;
import edu.calpoly.csc.luna.turboplan.core.dao.TaskDao;
import edu.calpoly.csc.luna.turboplan.core.entity.Task;
import edu.calpoly.csc.luna.turboplan.core.entity.User;
import edu.calpoly.csc.luna.turboplan.core.security.Authentication;
import edu.calpoly.csc.luna.turboplan.core.util.MobileUtil;

public class MobileService {
	private static final Logger log = Logger.getLogger(MobileService.class);
	
	public MobileUser authenticate(String username, String password) {
		log.debug("MobileService recieved request to authenticate");
		
		User eUsr = Authentication.authenticate(username, password);
		
		MobileUser usr = MobileUtil.hib2mobile(eUsr);
		
		return usr;
	}
	
	public String hello() {
		return "Hello world!";
	}
	
	public MobileTask[] getTasksForUserOfDate(long id, long date) {
		StringBuilder logBuilder = new StringBuilder();
		logBuilder.append("Mobile recieved request to getTasksForUserOfDate: id = ");
		logBuilder.append(id).append(" date = [").append(new Date(date)).append(']');
		log.debug(logBuilder);
		
		List<Task> list = TaskDao.getInstance().getTasksForUserOfDate(id, new Date(date));
		
		logBuilder = new StringBuilder("getTasksForUserOfDate returning ");
		logBuilder.append(list.size()).append(" results");
		log.debug(logBuilder);
		if (list.size() > 0) {
			return MobileUtil.hib2mobileCollection(list);
		} else {
			return MobileTask.EMPTY_MOBILETASK_ARRAY;
		}
		
	}
	
	public MobileTask[] getTasksForUserOfDates(long id, long start, long end) {
		StringBuilder logBuilder = new StringBuilder("Mobile recieved request to getTasksForUserOfDates: ");
		logBuilder.append(id).append("/");
		logBuilder.append(start).append("/");
		log.debug(logBuilder);
		
		List<Task> list = TaskDao.getInstance().getTaskForUserBetweenDates(id, new Date(start), new Date(end));
		
      logBuilder = new StringBuilder("getTasksForUserOfDates returning ");
      logBuilder.append(list.size()).append(" results");
      log.debug(logBuilder);
      if (list.size() > 0) {
         return MobileUtil.hib2mobileCollection(list);
      } else {
         return MobileTask.EMPTY_MOBILETASK_ARRAY;
      }
	}
	
	public MobileTask[] getAllTasksForUser(long id) {
		List<Task> list = TaskDao.getInstance().getAllTasksBelongToUserById(id);
		
		return MobileUtil.hib2mobileCollection(list);
	}
	
	public boolean setTaskStatus(Long id, String status) {
		StringBuilder logBuilder = new StringBuilder("Mobile service recieved request to setTaskStatus for: id = ");
		logBuilder.append(id).append("; to: ").append(status);
		log.debug(logBuilder);
		
		return TaskDao.getInstance().updateTaskSingleFieldById(id, "status", Task.TaskStatus.valueOf(status));
	}

	public boolean setTaskNotes(Long id, String notes) {
		StringBuilder logBuilder = new StringBuilder("Mobile service recieved request to setTaskNotes for: id = ");
		logBuilder.append(id).append("; note: ").append(notes);
		log.debug(logBuilder);
		
		return TaskDao.getInstance().updateTaskSingleFieldById(id, "notes", notes);
	}
	
	public boolean setTaskTimespent(Long id, Long timespent) {
		StringBuilder logBuilder = new StringBuilder("Mobile service recieved request to setTaskTimespent for: id = ");
		logBuilder.append(id).append("; timespent: ").append(timespent);
		log.debug(logBuilder);
		
		return TaskDao.getInstance().updateTaskSingleFieldById(id, "timespent", timespent);
	}
	
	public boolean setTaskAttributes(Long id, String status, String notes, Long timespent) {
		StringBuilder logBuilder = new StringBuilder("Mobile service recieved request to setTaskAttributes for: id = ");
		logBuilder.append(id).append("; status: ").append(status);
		logBuilder.append("; note: ").append(notes);
		logBuilder.append("; timespent: ").append(timespent);
		log.debug(logBuilder);
		
		Map<String, Object> fieldValuePairs = new HashMap<String, Object>();
		fieldValuePairs.put("status", status);
		fieldValuePairs.put("notes", notes);
		fieldValuePairs.put("timespent", timespent);
		
		return TaskDao.getInstance().updateTaskMultipleFieldById(id, fieldValuePairs);
	}
}
