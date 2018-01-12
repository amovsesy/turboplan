package edu.calpoly.csc.luna.turboplan.core.scheduler;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import edu.calpoly.csc.luna.turboplan.core.dao.CompanyDao;
import edu.calpoly.csc.luna.turboplan.core.dao.TaskDao;
import edu.calpoly.csc.luna.turboplan.core.entity.Company;
import edu.calpoly.csc.luna.turboplan.core.entity.Task;
import edu.calpoly.csc.luna.turboplan.core.entity.Task.TaskStatus;

@Test(groups = { "TurboPlan.all", "TurboPlan.core", "TurboPlan.scheduler", "TurboPlan.require.user"}, dependsOnGroups = {
      "TurboPlan.dao.company.add", "TurboPlan.dao.task.add", "TurboPlan.dao.user.add" })
public class SchedulerTest {
   private Company testComp;
   private Scheduler s;

   @BeforeClass
   public void setUp() {
      for (Company c : CompanyDao.getInstance().getAllCompany()) {
         if (c.getName().equals("TestCompany")) {
            testComp = c;
         }
      }

      // TODO CHANGE THE NEXT LINE TO REFLECT A USERID PROPERLY
      s = Scheduler.newInstance(testComp.getCompanyID(), /*CHANGE HERE!*/new Long(0)/*CHANGE HERE!*/);
      
   }

   @Test
   public void checkGeneration() {
      s.run();
      
      boolean atLeastOneScheduled = false;

      for (Task t : TaskDao.getInstance().getAllTaskBelongToCompanyById(
            testComp.getCompanyID())) {
         if (t.getStatus().equals(TaskStatus.Assigned))
         {
            atLeastOneScheduled = true;
         }
      }
      
      Assert.assertTrue(atLeastOneScheduled);
   }
}
