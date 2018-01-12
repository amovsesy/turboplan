package edu.calpoly.csc.luna.turboplan.web.server.servlet;

import java.util.Set;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import edu.calpoly.csc.luna.turboplan.web.client.model.GwtTask;
import edu.calpoly.csc.luna.turboplan.web.client.model.GwtUser;

@Test(groups = { "TurboPlan.all", "TurboPlan.web", "TurboPlan.gwtservlet",
      "TurboPlan.gwtservlet.managetask" })
public class ManageTasksServletTest {
	private ManageTasksServlet serv;

	@BeforeClass
	public void setUp() {
		serv = new ManageTasksServlet();
	}

   @Test (dataProviderClass = GwtModelDataProvider.class,
         dataProvider="userIds", dependsOnMethods="addTaskForUserById")
	public void getTasksForUserById(Long id) {
      Set<GwtTask> tasks = serv.getTasksForUserById(id);
      Assert.assertNotNull(tasks);
      boolean found;
      // For every Task that exists, verify the user is assigned to it.
      for (GwtTask task : tasks)
      {
         found = false;
         for (GwtUser user : task.getUsers())
         {
            if (user.getUserID().equals(id))
            {
               found = true;
            }
         }
         Assert.assertTrue(found);
      }
	}

   @Test (dataProviderClass = GwtModelDataProvider.class,
         dataProvider="idTaskPair")
	public void addTaskForUserById(Long id, GwtTask task) {
      try
      {
         serv.addTaskForUserById(id, task);
         serv.deleteTask(task);
      }
      catch (Exception exception)
      {
         Assert.fail(exception.getMessage());
      }
	}

   @Test (dataProviderClass = GwtModelDataProvider.class,
         dataProvider="gwtTasks", dependsOnMethods = { "addTaskForUserById" })
	public void updateTask(GwtTask task) {
      try
      {
         serv.updateTask(task);
      }
      catch (Exception exception)
      {
         Assert.fail(exception.getMessage());
      }
	}
	
	@Test (dependsOnMethods = { "updateTask", "addTaskForUserById" })
	public void deleteTask(GwtTask task) {
      try
      {
         serv.addTask(task);
      }
      catch (Exception exception)
      {
         Assert.fail(exception.getMessage());
      }
	}

	@Test (dataProviderClass = GwtModelDataProvider.class,
	      dataProvider = "companyIds", dependsOnMethods={"addTaskForUserById"})
	public void generateSchedule(Long companyId) {
		// TODO impement test
	   //serv.generateSchedule(companyId, userID);
	   Assert.fail("Test not implemented");
	}

	
   @Test (dataProviderClass=GwtModelDataProvider.class, dataProvider="gwtTasks",
         dependsOnMethods={"addTaskForUserById"})
	public void addTask(GwtTask task){
	   try
	   {
	      serv.addTask(task);
	   }
	   catch (Exception exception)
	   {
         System.err.println("Unable to complete test on addTask in " +
            "ManageTasksServlet.");
         System.err.println("Exception message: " + exception.getMessage());
         Assert.fail("Unable to complete test on addTask in " +
            "ManageTasksServlet.");
	   }
	}

	@Test (dataProviderClass = GwtModelDataProvider.class,
	      dataProvider = "taskIds", dependsOnMethods={"addTaskForUserById"})
	public void getTaskByCompanyId(Long id) {
	   try
	   {
	      Set<GwtTask> set = serv.getTaskByCompanyId(id);
	      for (GwtTask task : set)
	      {
	         Assert.assertEquals(task.getCompany().getCompanyID(), id);
	      }
	   }
	   catch (Exception exception)
	   {
         System.err.println("Unable to complete test on getTasksByCompanyId in "
               + "ManageTasksServlet.");
         System.err.println("Exception message: " + exception.getMessage());
         Assert.fail("Unable to complete test on getTasksByCompanyId in " +
               "ManageTasksServlet.");
	   }
	}

	@Test (dataProviderClass=GwtModelDataProvider.class, dataProvider="taskIds",
	      dependsOnMethods={"addTaskForUserById"})
	public void getTaskById(Long id) {
	   try
	   {
	      GwtTask task = serv.getTaskById(id);
	      Assert.assertEquals(task.getTaskID(), id);
	   }
	   catch (Exception exception)
	   {
	      System.err.println("Unable to complete test on getTasksById in " +
	      		"ManageTasksServlet.");
         System.err.println("Exception message: " + exception.getMessage());
         Assert.fail("Unable to complete test on getTasksById in " +
         		"ManageTasksServlet.");

	   }
	}
}
