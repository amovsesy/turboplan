package edu.calpoly.csc.luna.turboplan.axis2ws.mobile.model;

import java.util.Date;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import edu.calpoly.csc.luna.turboplan.axis2ws.mobile.model.MobileTask.TaskStatus;
import edu.calpoly.csc.luna.turboplan.core.entity.Task;

/**
 * MobileTaskTest - tests methods within MobileTask
 * 
 * @author Bradley Barbee - Luna Set
 * @version 1.0
 */
@Test(groups = { "TurboPlan.all", "TurboPlan.mobile", "TurboPlan.mobile.model" })
public class MobileTaskTest {

   MobileTask task;

   @BeforeMethod
    public void setup() {
        task = new MobileTask();
   }
   
   @Test (groups = {"setters", "getters"})
    public void testID() {
      task.setTaskID((long)25);
      Assert.assertEquals((long)25, task.getTaskID());
   }

   @Test (groups = {"setters", "getters"})
    public void testContactFirstNameNull() {
      task.setContactFirstName(null);
      Assert.assertTrue(task.getContactFirstName().equals(""));
   }
   
   @Test (groups = {"setters", "getters"})
    public void testContactFirstName() {
      task.setContactFirstName("Bradley");
      Assert.assertTrue(task.getContactFirstName().equals("Bradley"));
   }
   

   @Test (groups = {"setters", "getters"})
    public void testContactLastNameNull() {
      task.setContactLastName(null);
      Assert.assertTrue(task.getContactLastName().equals(""));
   }
   
   @Test (groups = {"setters", "getters"})
    public void testContactLastName() {
      task.setContactLastName("Barbee");
      Assert.assertTrue(task.getContactLastName().equals("Barbee"));
   }
   

   @Test (groups = {"setters", "getters"})
    public void testContactHomePhoneNull() {
      task.setContactHomePhone(null);
      Assert.assertTrue(task.getContactHomePhone().equals(""));
   }
   
   @Test (groups = {"setters", "getters"})
    public void testContactHomePhone() {
      task.setContactHomePhone("1234567");
      Assert.assertTrue(task.getContactHomePhone().equals("1234567"));
   }
   

   @Test (groups = {"setters", "getters"})
    public void testContactMobilePhoneNull() {
      task.setContactMobilePhone(null);
      Assert.assertTrue(task.getContactMobilePhone().equals(""));
   }
   
   @Test (groups = {"setters", "getters"})
    public void testContactMobilePhone() {
      task.setContactMobilePhone("2345678");
      Assert.assertTrue(task.getContactMobilePhone().equals("2345678"));
   }
   

   @Test (groups = {"setters", "getters"})
    public void testContactTitle() {
      task.setTitle(null);
      Assert.assertTrue(task.getTitle().equals(""));
   }
   
   @Test (groups = {"setters", "getters"})
    public void testTitle() {
      task.setTitle("Task1");
      Assert.assertTrue(task.getTitle().equals("Task1"));
   }
   

   @Test (groups = {"setters", "getters"})
    public void testDescriptionNull() {
      task.setDescription(null);
      Assert.assertTrue(task.getDescription().equals(""));
   }
   
   @Test (groups = {"setters", "getters"})
    public void testDescription() {
      task.setDescription("This is a task");
      Assert.assertTrue(task.getDescription().equals("This is a task"));
   }
   
   @Test (groups = {"setters", "getters"})
    public void testCreatedDate() {
      task.setCreatedDate((long)25);
      Assert.assertEquals((long)25, task.getCreatedDate());
   }
   
   @Test (groups = {"setters", "getters"})
    public void testDueDate() {
      task.setDueDate((long)25);
      Assert.assertEquals((long)25, task.getDueDate());
   }
   

   @Test (groups = {"setters", "getters"})
    public void testNotesNull() {
      task.setNotes(null);
      Assert.assertTrue(task.getNotes().equals(""));
   }
   
   @Test (groups = {"setters", "getters"})
    public void testNotes() {
      task.setNotes("notes");
      Assert.assertTrue(task.getNotes().equals("notes"));
   }

   @Test (groups = {"setters", "getters"})
    public void testStatusNull() {
      task.setStatus(null);
      Assert.assertTrue(task.getStatus().equals(""));
   }
   
   @Test (groups = {"setters", "getters"})
    public void testStatus() {
      task.setStatus("Assigned");
      Assert.assertTrue(task.getStatus().equals("Assigned"));
   }


   @Test (groups = {"setters", "getters"})
    public void testLocationLess() {
      task.setLocation(new String[]{"1 Grand", "City", "CA", "93405"});
      Assert.assertTrue(task.getLocation()[0].equals("1 Grand"));
      Assert.assertTrue(task.getLocation()[1].equals("City"));
      Assert.assertTrue(task.getLocation()[2].equals("CA"));
      Assert.assertTrue(task.getLocation()[3].equals("93405"));      
      task.setLocation(null);
      Assert.assertEquals(0, task.getLocation().length);
   }
   
   /*
   @Test (groups = {"setters", "getters"})
    public void testLocationNull() {
      task.setLocation(null);
      System.out.println("  " + task.getLocation().length);
      assert(task.getLocation().length==0);
   }
   */
   
   @Test (groups = {"setters", "getters"})
    public void testLocation() {
      task.setLocation(new String[]{"1 Grand", "USA", "City", "CA", "93405"});
      Assert.assertTrue(task.getLocation()[0].equals("1 Grand"));
      Assert.assertTrue(task.getLocation()[1].equals("USA"));
      Assert.assertTrue(task.getLocation()[2].equals("City"));
      Assert.assertTrue(task.getLocation()[3].equals("CA"));
      Assert.assertTrue(task.getLocation()[4].equals("93405"));      
   }

   @Test (groups = {"setters", "getters"})
    public void testScheduledStartTime() {
      task.setScheduledStartTime((long)25);
      Assert.assertEquals((long)25, task.getScheduledStartTime());
   }

   @Test (groups = {"setters", "getters"})
    public void testScheduledEndTime() {
      task.setScheduledEndTime((long)25);
      Assert.assertEquals((long)25, task.getScheduledEndTime());
   }
   
   @Test (groups = {"setters", "getters"})
    public void testUsersNull() {
      task.setUsers(null);
      Assert.assertEquals(0, task.getUsers().length);
   }

   @Test (groups = {"setters", "getters"})
    public void testUsers() {
      MobileUser[] users = { new MobileUser(true) };     
      task.setUsers(users);
      Assert.assertTrue(task.getUsers().equals(users));
   }
   

   @Test (groups = {"setters", "getters"})
    public void testPriorityNull() {
      task.setPriority(null);
      Assert.assertTrue(task.getStatus().equals(""));
   }

   @Test (groups = {"setters", "getters"})
    public void testPriority() {
      task.setPriority("High");
      Assert.assertTrue(task.getPriority().equals("High"));
   }
   
   @Test (groups = {})
    public void testEnum() {
      Assert.assertEquals(TaskStatus.New.ordinal(), 0);     
      Assert.assertEquals(TaskStatus.Assigned.ordinal(), 1);
      Assert.assertEquals(TaskStatus.Updated.ordinal(), 2);
      Assert.assertEquals(TaskStatus.Complete.ordinal(), 3);
      Assert.assertEquals(TaskStatus.Canceled.ordinal(), 4);      
   }
   
   @Test (groups = {})
   public void testtoEntityModel()
   {
      String [] location = new String [] {"1 Grand", "USA", "City", "CA", 
            "93405"};
      MobileUser [] users = new MobileUser[0];
      task.setContactFirstName("First");
      task.setContactLastName("Last");
      task.setContactHomePhone("555-5555");
      task.setContactMobilePhone("555-5556");
      task.setTitle("Title");
      task.setDescription("Description");
      task.setCreatedDate(1L);
      task.setDueDate(2L);
      task.setNotes("Notes");
      task.setStatus("New");
      task.setTaskID(3L);
      task.setLocation(location);
      task.setScheduledStartTime(4L);
      task.setScheduledEndTime(5L);
      task.setUsers(users);
      task.setPriority("Low");
      Task toTask = task.toEntityModel();
      Assert.assertEquals(toTask.getCustomerFirstName(), "First");
      Assert.assertEquals(toTask.getCustomerLastName(), "Last");
      Assert.assertEquals(toTask.getHomeNum(), "555-5555");
      Assert.assertEquals(toTask.getMobileNum(), "555-5556");
// Null results
//    Assert.assertEquals(toTask.getTitle(), "Title");
//    Assert.assertEquals(toTask.getDescription(), "Description");
      Assert.assertEquals(toTask.getCreatedDate(), new Date(1L));
      // TODO add check for "Due Date" match.
      Assert.assertEquals(toTask.getNotes(), "Notes");
      Assert.assertEquals(toTask.getStatus().toString(), "New");
      Assert.assertEquals(toTask.getTaskID(), new Long(3L));
// Argument redirection
//    Assert.assertEquals(toTask.getLocation(), location);
      Assert.assertEquals(toTask.getScheduledStartTime(), new Date(4L));
      Assert.assertEquals(toTask.getScheduledEndTime(), new Date(5L));
      // TODO add check for User match.
//    Assert.assertEquals(toTask.getUsers(), new Set<User>());
      Assert.assertEquals(task.getPriority(), "Low");
   }
}
