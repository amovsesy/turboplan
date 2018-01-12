package edu.calpoly.csc.luna.turboplan.core.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import edu.calpoly.csc.luna.turboplan.core.entity.Task;
import edu.calpoly.csc.luna.turboplan.core.entity.User;
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
	
	/**
	 * Get all distinct users from company id that have tasks on each week day for
	 * the week with the given date.
	 * @returns a list of 7 lists representing who has tasks starting from sun and ending on sat.
	 */
	public List<List<User>> getAllUsersHaveTaskForEachDayOfWeek(Long id, Date dayInWeek) {
		List<List<User>> weekList = new ArrayList<List<User>>();
		List<User> sun, mon, tues, wed, thurs, fri, sat;
		Date[] weekDate = getWeekDates(dayInWeek);
		
		sun 	= getAllUsersHaveTaskOnDate(id, weekDate[0]);
		mon 	= getAllUsersHaveTaskOnDate(id, weekDate[1]);
		tues	= getAllUsersHaveTaskOnDate(id, weekDate[2]);
		wed		= getAllUsersHaveTaskOnDate(id, weekDate[3]);
		thurs	= getAllUsersHaveTaskOnDate(id, weekDate[4]);
		fri		= getAllUsersHaveTaskOnDate(id, weekDate[5]);
		sat		= getAllUsersHaveTaskOnDate(id, weekDate[6]);
		
		weekList.add(sun);
		weekList.add(mon);
		weekList.add(tues);
		weekList.add(wed);
		weekList.add(thurs);
		weekList.add(fri);
		weekList.add(sat);
			
		return weekList;
	}
	
	/**
	 * Gets each days Date for week from sunday to saturday.
	 */
	@SuppressWarnings("deprecation")
	private Date[] getWeekDates(Date dayInThisWeek){
		Date[] week = new Date[7];
		
		Date today = dayInThisWeek;
		int todayNum = today.getDay();
		
		week[0] = getDateOffset(today, 0 - todayNum);
		week[1] = getDateOffset(today, 1 - todayNum);
		week[2] = getDateOffset(today, 2 - todayNum);
		week[3] = getDateOffset(today, 3 - todayNum);
		week[4] = getDateOffset(today, 4 - todayNum);
		week[5] = getDateOffset(today, 5 - todayNum);
		week[6] = getDateOffset(today, 6 - todayNum);
		
		return week;
	}
	
	/**
	 * Returns a new date that is offsetted by the change.
	 * @param old the old date that is getting offsetted by
	 * @param change number of days offset
	 * @return the new date
	 */
	private Date getDateOffset(Date old, int change){
		return new Date(old.getTime() + (86400000L*change));		
	}
	
	
	
	/**
	 * Get all distinct users from company id that have tasks on the given date.
	 */
	public List<User> getAllUsersHaveTaskOnDate(Long id, Date date) {
		ArrayList<User> usrs = new ArrayList<User>();
		ArrayList<Long> ids = new ArrayList<Long>();
		
		Date start = new Date();
		Date end = new Date();
		
		start.setHours(0);
		start.setMinutes(0);
		start.setSeconds(0);
		start.setDate(date.getDate());
		start.setMonth(date.getMonth());
		start.setYear(date.getYear());
		
		end.setHours(23);
		end.setMinutes(59);
		end.setSeconds(59);
		end.setDate(date.getDate());
		end.setMonth(date.getMonth());
		end.setYear(date.getYear());
		
		Session session = newSessionAndTransaction();

		@SuppressWarnings("unchecked")
		List<Task> ret = session.createQuery("from Task where company = ?").setLong(0, id).list();

		session.getTransaction().commit();
		
		for(Task t : ret) {
			if(!t.getStatus().equals(TaskStatus.New)) {
//				System.err.println("scheduled start "+t.getScheduledStartTime());
//				System.err.println("scheduled end "+t.getScheduledEndTime());
				if(t.getScheduledStartTime() != null && t.getScheduledEndTime() != null) {
					if(t.getScheduledStartTime().after(start) &&
						t.getScheduledStartTime().before(end) &&
						t.getScheduledEndTime().after(start) &&
						t.getScheduledEndTime().before(end)) {
						for(User u : t.getUsers()) {
							if(!ids.contains(u.getUserID())) {
								ids.add(u.getUserID());
								usrs.add(u);
							}
						}
					}
				}
			}
		}

		return usrs;
	}
	
	public List<Task> getAllNewTaskBelongToCompanyById(Long id) {

		@SuppressWarnings("unchecked")
		ArrayList<Task> ret = new ArrayList<Task>();
		List<Task> tasks = getAllTaskBelongToCompanyById(id);
		
		for(Task t : tasks) {
			if(t.getStatus().equals(TaskStatus.New)) {
				ret.add(t);
			}
		}

		return ret;
	}
	
	public List<Task> getAllTaskNotComplete(Long id) {

		@SuppressWarnings("unchecked")
		ArrayList<Task> ret = new ArrayList<Task>();
		List<Task> tasks = getAllTaskBelongToCompanyById(id);
		
		for(Task t : tasks) {
			if(!t.getStatus().equals(TaskStatus.Complete)) {
				ret.add(t);
			}
		}

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
