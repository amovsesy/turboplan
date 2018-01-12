package edu.calpoly.csc.luna.turboplan.core.dao;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import edu.calpoly.csc.luna.turboplan.core.entity.Company;
import edu.calpoly.csc.luna.turboplan.core.entity.Task;
import edu.calpoly.csc.luna.turboplan.core.entity.User;

@Test(groups = { "TurboPlan.all", "TuboPlan.core", "TurboPlan.dao", "TurboPlan.dao.task" }, dependsOnGroups = { "TurboPlan.dao.company" })
public class TaskDaoTest {
   @Test(dataProviderClass = DaoDataProvider.class, dataProvider = "tasks", groups = { "TurboPlan.dao.task.add" })
   public void addTask(Task task2add) {
      int before = TaskDao.getInstance().getTableRowCount();

      TaskDao.getInstance().addTask(task2add);

      int after = TaskDao.getInstance().getTableRowCount();

      Assert.assertEquals(after, before + 1);
   }

   @Test(dataProviderClass = DaoDataProvider.class, dataProvider = "users")
   public void getAllTasksBelongToUserById(User usr) {
      List<Task> tasks = TaskDao.getInstance().getAllTasksBelongToUserById(usr.getUserID());
      for (Task task : tasks) {
         Assert.assertTrue(task.getUsers().contains(usr));
      }
   }

   @Test(dataProviderClass = DaoDataProvider.class, dataProvider = "tasks", dependsOnMethods = {"getAllTasks"}, dependsOnGroups={"TurboPlan.dao.task.add"})
   public void updateTask(Task input) {
      String before = input.getDescription();
      input.setDescription("some changes"); // make some change

      TaskDao.getInstance().update(input);

      String after = TaskDao.getInstance().getTaskById(input.getTaskID()).getDescription();

      Assert.assertFalse(before.equals(after));
   }

   // TODO warning: some tests depend on tasks, these tasks shouldn't be
   // deleted
   @Test(dataProviderClass = DaoDataProvider.class, dataProvider = "tasks", enabled = false, dependsOnGroups = { "TurboPlan.require.task" })
   public void dTask(Task task2delete) {
      int before = TaskDao.getInstance().getTableRowCount();

      TaskDao.getInstance().delete(task2delete);

      int after = TaskDao.getInstance().getTableRowCount();

      Assert.assertEquals(after, before - 1);
   }

   @Test(dataProviderClass = DaoDataProvider.class, dataProvider = "companies")
   public void getAllTaskBelongToCompanyById(Company company) {
      List<Task> list = TaskDao.getInstance().getAllTaskBelongToCompanyById(company.getCompanyID());
      for (Task task : list) {
         Assert.assertEquals(task.getCompany(), company);
      }
   }

   @Test
   public void getAllTasks() {
      List<Task> all = TaskDao.getInstance().getAllTasks();
      Assert.assertEquals(all.size(), TaskDao.getInstance().getTableRowCount());

      for (Object[] obj : DaoDataProvider.taskProvider()) {
         Task task = (Task) obj[0];
         for (Task t : all) {
            if (t.getTaskID().equals(task.getTaskID())) {
               task.setUsers(new HashSet<User>(t.getUsers()));
            }
         }
      }
   }

   @Test
   public void getAllUnsceduledTasks() {
      List<Task> list = TaskDao.getInstance().getAllUnsceduledTasks();
      for (Task task : list) {
         Assert.assertEquals(task.getStatus(), Task.TaskStatus.New);
      }
   }

   @Test(dataProviderClass = DaoDataProvider.class, dataProvider = "tasks")
   public void getTaskById(Task expected) {
      Task actual = TaskDao.getInstance().getTaskById(expected.getTaskID());

      Assert.assertEquals(actual, expected);
   }

   @Test
   public void getTaskForUserBetweenDates() {
      Date start = new Date(108, 2, 18);
      Date end = new Date(110, 2, 20);

      List<Task> list = TaskDao.getInstance().getTaskForUserBetweenDates(3L, start, end);
      for (Task task : list) {
         Assert.assertTrue(task.getScheduledStartTime().after(start));
         Assert.assertTrue(task.getScheduledStartTime().before(end));
      }
   }

   @SuppressWarnings("deprecation")
   @Test
   public void getTasksForUserOfDate() {
      Date date = new Date(109, 2, 19);
      Date start = new Date(date.getTime());
      Date end = new Date(109, 2, 20);
      end.setTime(end.getTime() - 1);

      List<Task> list = TaskDao.getInstance().getTasksForUserOfDate(3L, date);
      for (Task task : list) {
         Assert.assertTrue(task.getScheduledStartTime().after(start));
         Assert.assertTrue(task.getScheduledEndTime().before(end));
      }

   }

   @Test(dataProvider = "tasks", dataProviderClass = DaoDataProvider.class)
   public void updateTaskStatusById(Task task) {
      Task.TaskStatus newStatus = Task.TaskStatus.values()[Math.abs(DaoDataProvider.RANDOM_GENERATOR.nextInt())
            % Task.TaskStatus.values().length];
      TaskDao.getInstance().updateTaskSingleFieldById(task.getTaskID(), "status", newStatus);

      Task thisTask = TaskDao.getInstance().getTaskById(task.getTaskID());

      Assert.assertEquals(thisTask.getStatus(), newStatus);
   }

   @Test(dependsOnMethods = { "addTask" })
   public void taskTableRowCount() {
      Assert.assertTrue(TaskDao.getInstance().getTableRowCount() > 0);
   }

}
