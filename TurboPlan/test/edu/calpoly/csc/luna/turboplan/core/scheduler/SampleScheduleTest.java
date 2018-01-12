package edu.calpoly.csc.luna.turboplan.core.scheduler;

import java.util.Date;
import java.util.HashSet;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import edu.calpoly.csc.luna.turboplan.core.entity.Company;
import edu.calpoly.csc.luna.turboplan.core.entity.Task;
import edu.calpoly.csc.luna.turboplan.core.entity.User;
import edu.calpoly.csc.luna.turboplan.core.entity.AbstractUser.UserStatus;
import edu.calpoly.csc.luna.turboplan.core.entity.Task.TaskPriority;
import edu.calpoly.csc.luna.turboplan.core.entity.Task.TaskStatus;
import edu.calpoly.csc.luna.turboplan.core.entity.embeddable.Address;
import edu.calpoly.csc.luna.turboplan.core.entity.embeddable.UserProfile;

@Test(groups = { "TurboPlan.all", "TurboPlan.core", "TurboPlan.scheduler", "TurboPlan.require.user" })
		//dependsOnGroups = {"TurboPlan.dao.company.add", "TurboPlan.dao.task.add", "TurboPlan.dao.user.add" })
public class SampleScheduleTest {

	StartTimeFirstSchedule schedule;
	Company company;
	User user;
	Task task;
	Date date;

	@BeforeMethod
	public void setUp() {

		boolean[][] availability
						= new boolean[][] { 
									{ false,false,false,false,false,false,false,false,false,false,false,false,
									false,false,false,false,false,false,false,false,false,false,false,false,
									false,false,false,false,false,false,false,false,false,false,false,false,
									false,false,false,false,false,false,false,false,false,false,false,false,},
									{ false,false,false,false,false,false,false,false,false,false,false,false,
									false,false,false,false,false,false,false,false,false,false,false,false,
									false,false,false,false,false,false,false,false,false,false,false,false,
									false,false,false,false,false,false,false,false,false,false,false,false,},
									{ false,false,false,false,false,false,false,false,false,false,false,false,
									false,false,false,false,false,false,false,false,false,false,false,false,
									false,false,false,false,false,false,false,false,false,false,false,false,
									false,false,false,false,false,false,false,false,false,false,false,false,},
									{ false,false,false,false,false,false,false,false,false,false,false,false,
									false,false,false,false,false,false,false,false,false,false,false,false,
									false,false,false,false,false,false,false,false,false,false,false,false,
									false,false,false,false,false,false,false,false,false,false,false,false,},
									{ false,false,false,false,false,false,false,false,false,false,false,false,
									false,false,false,false,false,false,false,false,false,false,false,false,
									false,false,false,false,false,false,false,false,false,false,false,false,
									false,false,false,false,false,false,false,false,false,false,false,false,},
									{ true,true,true,true,true,true,true,true,true,true,true,true,
									true,true,true,true,true,true,true,true,true,true,true,true,
									true,true,true,true,true,true,true,true,true,true,true,true,
									true,true,true,true,true,true,true,true,true,true,true,true,},
									{ false,false,false,false,false,false,false,false,false,false,false,false,
									false,false,false,false,false,false,false,false,false,false,false,false,
									false,false,false,false,false,false,false,false,false,false,false,false,
									false,false,false,false,false,false,false,false,false,false,false,false,}};
		UserProfile profile = new UserProfile("Bradley", "Barbee",
				new Date(), availability, "bbarbee@calpoly.edu");
		Date sugStartDate = new Date(2010, 0, 0, 12, 15);
		Date sugEndDate = new Date(2010, 0, 0, 16, 0);
		
		this.schedule = new StartTimeFirstSchedule();
		this.company = new Company("LunaSet", "1 Grand Avenue");
		this.user = new User(profile, UserStatus.Active, company);
		this.user.setTasks(new HashSet<Task>(){});
		this.task = new Task(TaskStatus.New, "Task 1", "First Task", new Address("","","",1),
								"Kate", "Razina", "123456789", "123456789",
								new Date(), sugStartDate, sugEndDate,
								new Double(2.25), TaskPriority.HIGH, company);
		this.task.setUsers(new HashSet<User>(){});
		this.date = new Date(2010, 0, 0, 12, 15);
	}

	@Test
	public void testAssignUserToTask() {
		assert(task.getStatus()==TaskStatus.New);
		task = schedule.assignTaskToUser(user, task, date);
		assert(task.getStatus()==TaskStatus.Assigned);
		assert(task.getUsers().size()==1);
		assert(task.getUsers().contains(user));
		assert(task.getScheduledStartTime().getHours() == 12 &&
				task.getScheduledStartTime().getMinutes() == 30);		
		assert(task.getScheduledEndTime().getHours() == 15 &&
				task.getScheduledEndTime().getMinutes() == 0);		
	}

	@Test
	public void testToAvailValue() {
		assert(schedule.toAvailValue(date) == 25);
		assert(schedule.toAvailValue(new Date(2010, 0, 0, 0, 0)) == 0);
		assert(schedule.toAvailValue(new Date(2010, 0, 0, 0, 1)) == 1);
		assert(schedule.toAvailValue(new Date(2010, 0, 0, 0, 30)) == 1);
		assert(schedule.toAvailValue(new Date(2010, 0, 0, 0, 31)) == 2);
	}

	@Test
	public void testCheckNoTasksAtCurrentTime() {
		assert(schedule.checkNoTasksAtCurrentTime(user.getTasks(),date));
		task = schedule.assignTaskToUser(user, task, date);
		user = schedule.assignUserToTask(user, task, date);
		assert(!schedule.checkNoTasksAtCurrentTime(user.getTasks(),task.getScheduledStartTime()));
		assert(!schedule.checkNoTasksAtCurrentTime(user.getTasks(),task.getScheduledEndTime()));
	}

	@Test
	public void testIsUserAvailable() {
		assert(user.getProfile().getAvailability().getAvail(date.getDay(), schedule.toAvailValue(date)));
		assert(schedule.isUserAvailable(user, date));
		task = schedule.assignTaskToUser(user, task, date);
		user = schedule.assignUserToTask(user, task, date);
		assert(!schedule.isUserAvailable(user, task.getScheduledStartTime()));
		assert(!schedule.isUserAvailable(user, task.getScheduledEndTime()));
		assert(schedule.isUserAvailable(user, new Date(2010, 0, 0, 0, 0)));
		assert(schedule.isUserAvailable(user, new Date(2010, 0, 0, 0, 39)));
		assert(!schedule.isUserAvailable(user, new Date(2010, 0, 1, 0, 0)));
	}

	@Test
	public void testCheckMin() {
		Date minDate = new Date(2010, 0, 0, 0, 0);
		Date maxDate = new Date(2010, 0, 1, 0, 0);
		schedule.setOptions(new SchedulingOptions(minDate.getTime(), maxDate.getTime(), 
				false, false, false, false, false, false, false, false));
		assert(schedule.checkMin(date).getTime() == new Date(2010, 0, 0, 12, 15).getTime());
		schedule.setOptions(new SchedulingOptions(maxDate.getTime(), maxDate.getTime(),
				false, false, false, false, false, false, false, false));
		assert(schedule.checkMin(date).getTime() == new Date(2010, 0, 1, 0, 0).getTime());
	}
	
	@Test
	public void testCheckMax() {
		Date minDate = new Date(2009, 0, 0, 0, 0);
		Date maxDate = new Date(2010, 0, 0, 0, 0);
		schedule.setOptions(new SchedulingOptions(minDate.getTime(), maxDate.getTime(), 
				false, false, false, false, false, false, false, false));
		assert(schedule.checkMax(date).getTime() == new Date(2010, 0, 0, 12, 15).getTime());
		schedule.setOptions(new SchedulingOptions(minDate.getTime(), minDate.getTime(),
				false, false, false, false, false, false, false, false));
		assert(schedule.checkMax(date).getTime() == new Date(2009, 0, 0, 0, 0).getTime());
	}
	
	@Test
	public void testRoundEstimatedTimeToMinutes() {
		assert(schedule.roundEstimatedTimeToMinutes(new Double(2.25)) == (2.5 * 60));
		assert(schedule.roundEstimatedTimeToMinutes(new Double(2.13)) == (2.5 * 60));
		assert(schedule.roundEstimatedTimeToMinutes(new Double(2.52)) == (3.0 * 60));
	}

	@Test
	public void testRoundUpTime() {
		assert(schedule.roundUpTime(date).getMinutes()==30);
		date.setMinutes(19);
		assert((schedule.roundUpTime(date).getMinutes())%30==0);
		assert(schedule.roundUpTime(date).getMinutes()==30);
		date.setMinutes(32);
		assert((schedule.roundUpTime(date).getMinutes())%30==0);
		assert(schedule.roundUpTime(date).getMinutes()==0);
		date.setMinutes(30);
		assert((schedule.roundUpTime(date).getMinutes())%30==0);
		assert(schedule.roundUpTime(date).getMinutes()==30);		
	}

	@Test
	public void testRoundDownTime() {
		assert(schedule.roundDownTime(date).getMinutes()==0);
		date.setMinutes(19);
		assert((schedule.roundDownTime(date).getMinutes())%30==0);
		assert(schedule.roundDownTime(date).getMinutes()==0);
		date.setMinutes(32);
		assert((schedule.roundDownTime(date).getMinutes())%30==0);
		assert(schedule.roundDownTime(date).getMinutes()==30);
		date.setMinutes(32);
		assert((schedule.roundDownTime(date).getMinutes())%30==0);
		assert(schedule.roundDownTime(date).getMinutes()==30);
	}

	@Test
	public void testCheckTaskLength() {
		assert(schedule.checkTaskLength(task, 2.0 * 60));
		assert(!schedule.checkTaskLength(task, 4.0 * 60));
		task.setEstimatedTime(3.1);
		assert(schedule.checkTaskLength(task, 3.5 * 60));
	}

	@Test
	public void testResetCurrentTimeByTaskLengh() {
		long old_time = date.getTime();
		assert(schedule.resetCurrentTimeByTaskLengh(date, 0.0 * 60).getTime() == old_time);
		assert(schedule.resetCurrentTimeByTaskLengh(date, 2.0 * 60).getTime() ==
			old_time - (2.0 * 60 * 60 * 1000));
	}
}
