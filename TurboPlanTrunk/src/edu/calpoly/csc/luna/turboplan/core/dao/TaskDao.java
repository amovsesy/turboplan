package edu.calpoly.csc.luna.turboplan.core.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import edu.calpoly.csc.luna.turboplan.core.entity.Task;
import edu.calpoly.csc.luna.turboplan.core.entity.Task.TaskStatus;
import edu.calpoly.csc.luna.turboplan.core.util.ReflectionUtil;

public class TaskDao extends BaseDao<Task> {
	private static final Logger log = Logger.getLogger(TaskDao.class);

	private static TaskDao singleton = new TaskDao();

	public static TaskDao getInstance() {
		return singleton;
	}

	public void addTask(Task nTask) {
		super.save(nTask);
	}

	public void updateTask(Task task) {
		super.update(task);
	}

	public void deleteTask(Task task) {
		super.delete(task);
	}

	public Task getTaskById(Long id) {
		return super.getEntityById(Task.class, id);
	}

	public List<Task> getAllTasks() {
		return getAll(Task.class);
	}

	public List<Task> getAllTasksBelongToUserById(Long id) {
		Session session = newSessionAndTransaction();
		
		String query = "from Task t join fetch t.users u where u.userID = ?";
		List<Task> list = session.createQuery(query).setLong(0, id).list();
		
		session.getTransaction().commit();
		
		return list;
//		return UserDao.getInstance().getTasksByUserId(id);
	}

	public List<Task> getAllTaskBelongToCompanyById(Long id) {
		Session session = newSessionAndTransaction();

		@SuppressWarnings("unchecked")
		List<Task> ret = session.createQuery("from Task where company = ?").setLong(0, id).list();

		session.getTransaction().commit();

		return ret;
	}
	

	public List<Task> getAllTaskBelongToCompanyByIdQuery(Long id, String query) {
		Session session = newSessionAndTransaction();

		@SuppressWarnings("unchecked")
		List<Task> ret = session.createQuery(query).setLong(0, id).list();

		session.getTransaction().commit();

		return ret;
	}
	

	public List<Task> getAllTaskBelongToCompanyByEstTimeDesc(Long id) {
		Session session = newSessionAndTransaction();

		@SuppressWarnings("unchecked")
		List<Task> ret = session.createQuery("from Task where company = ? order by estimatedTime desc").setLong(0, id).list();

		session.getTransaction().commit();

		return ret;
	}

	public List<Task> getAllUnsceduledTasks() {
		ArrayList<Task> ret = new ArrayList<Task>();

		for (Task task : getAllTasks()) {
			if (task.getStatus().equals(Task.TaskStatus.New)) {
				ret.add(task);
			}
		}

		return ret;
	}

	@SuppressWarnings("deprecation")
	public List<Task> getTasksForUserOfDate(long id, Date date) {
		Date start = new Date(date.getTime());
		start.setHours(0);
		start.setMinutes(0);
		start.setSeconds(0);

		Date end = new Date(date.getTime());
		end.setHours(23);
		end.setMinutes(59);
		end.setSeconds(59);

		return getTaskForUserBetweenDates(id, start, end);
	}

	public List<Task> getTaskForUserBetweenDates(long id, Date start, Date end) {
		// TODO mobile get task
		List<Task> list = TaskDao.getInstance().getAllTasksBelongToUserById(id);

		List<Task> ret = new ArrayList<Task>();
		for (Task task : list) {
			if (task.getScheduledStartTime() != null && !Task.TaskStatus.New.equals(task.getStatus())
					&& task.getScheduledStartTime().after(start) && task.getScheduledEndTime().before(end)) {
				ret.add(task);
			}
		}
		
		StringBuilder builder = new StringBuilder("TaskDao.getTaskForUserBetweenDates returning ");
		builder.append(ret.size()).append(" for userID = ").append(id).append(" between dates: ");
		builder.append(start).append(" and ").append(end);
		log.trace(builder);

		return ret;
		// Session session = newSessionAndTransaction();
		//		
		// String query =
		// "from Task t join t.users u where t.scheduledStartTime > ? and t.scheduledStartTime < ? and u.userID = ?"
		// ;
		// // String query =
		// "select tasks from User u join u.tasks t where t.scheduledStartTime > ? and t.scheduledStartTime < ? and u.userID = ?"
		// ;
		// @SuppressWarnings("unchecked")
		// List<Task> ret = session.createQuery(query).setDate(0,
		// start).setDate(1, end).setLong(2, id).list();
		//		
		// // String query = "select * from Task where scheduledStartTime < ?";
		// // List<Task> ret = session.createSQLQuery(query).setDate(0,
		// start).list();
		//
		// session.getTransaction().commit();
		//
		// return ret;
	}

	public boolean updateTaskSingleFieldById(Long id, String field, Object value) {
		try {
			Session session = newSessionAndTransaction();

			Task task = (Task) session.createQuery("from Task where taskID = ?").setLong(0, id).uniqueResult();
			ReflectionUtil.setFieldValueOfObject(task, field, value);

			session.update(task);

			session.getTransaction().commit();
		} catch (Exception ex) {
			log.warn("Failed to fetch and update task id = " + id, ex);
			return false;
		}

		return true;
	}
	
	public boolean updateTaskMultipleFieldById(Long id, Map<String, Object> fieldValuePairs) {
		try {
			Session session = newSessionAndTransaction();

			Task task = (Task) session.createQuery("from Task where taskID = ?").setLong(0, id).uniqueResult();
			for (Map.Entry<String, Object> fieldValuePair : fieldValuePairs.entrySet()) {
				ReflectionUtil.setFieldValueOfObject(task, fieldValuePair.getKey(), fieldValuePair.getValue());
			}

			session.update(task);

			session.getTransaction().commit();
		} catch (Exception ex) {
			log.warn("Failed to fetch and update task id = " + id, ex);
			return false;
		}

		return true;
	}

	@Override
	public int getTableRowCount() {
		return super.countRowOfTable(Task.class);
	}

	@Override
	public String printTable() {
		return super.printTable(Task.class).toString();
	}

	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		TaskDao.getInstance().getAllTasksBelongToUserById(1L);
	}

}
