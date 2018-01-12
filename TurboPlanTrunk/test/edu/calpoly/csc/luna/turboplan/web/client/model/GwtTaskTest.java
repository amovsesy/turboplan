package edu.calpoly.csc.luna.turboplan.web.client.model;


import java.util.Date;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import edu.calpoly.csc.luna.turboplan.web.client.model.GwtTask.TaskPriority;
import edu.calpoly.csc.luna.turboplan.web.client.model.GwtTask.TaskStatus;


/**
 * Tests a GWT User
 * @author Paul De Leon
 *
 */
@Test(groups = { "TurboPlan.all", "TurboPlan.web" })
public class GwtTaskTest {
    GwtTask testTask;
    Date date = new Date();
    
    @Test
    public void testEmptyConstructor() {
        testTask = new GwtTask();
        Assert.assertEquals(testTask.getPriority(), TaskPriority.NORMAL);
    }
    
    @BeforeTest
    public void setupTask() {
        testTask = new GwtTask(null, null, TaskStatus.New, "newTask", "newDesc",
                new GwtAddress("newStreet", "newCity", "newState", 12345),
                "newFirstName", "newLastName", 12345L, 67890L, date,
                date, date, date, new Double(2.0), TaskPriority.HIGH, ""); 
    }
    
    @Test
    public void testGetters() {
       Assert.assertEquals(testTask.getGwtTask(), testTask);
        
        testTask.setTaskID(1234L);
        Assert.assertEquals(testTask.getTaskID(), new Long(1234));
        
        
        testTask.setRequiredSkill(null);
//        assertNull (testTask.getRequiredSkill());
        Assert.assertNull(testTask.getRequiredSkill());
        
        testTask.setUsers(null);
        Assert.assertNull(testTask.getUsers());
        
        testTask.setStatus(TaskStatus.Complete);
        Assert.assertEquals(testTask.getStatus(), TaskStatus.Complete);
        
        testTask.setPriority(TaskPriority.LOW);
        Assert.assertEquals(testTask.getPriority(), TaskPriority.LOW);
        
        testTask.setTitle("updateTitle");
        Assert.assertEquals(testTask.getTitle(), "updateTitle");
        
        testTask.setDescription("updateDesc");
        Assert.assertEquals(testTask.getDescription(), "updateDesc");
        
        testTask.setCreatedDate(date);
        Assert.assertEquals(testTask.getCreatedDate(), date);
        
        testTask.setDueDate(date);
        Assert.assertEquals(testTask.getDueDate(), date);
        
        testTask.setNotes("updateNotes");
        Assert.assertEquals(testTask.getNotes(), "updateNotes");
        
        GwtAddress testAddress = new GwtAddress("updateStreet", "updateState", "updateCity", 1234);
        testTask.setLocation(testAddress);
        Assert.assertEquals(testTask.getLocation(), testAddress);
        
        testTask.setSuggestedStartTime(date);
        Assert.assertEquals(testTask.getSuggestedStartTime(), date);
        
        testTask.setSuggestedEndTime(date);
        Assert.assertEquals(testTask.getSuggestedEndTime(), date);
        
        testTask.setScheduledStartTime(date);
        Assert.assertEquals(testTask.getScheduledStartTime(), date);
        
        testTask.setScheduledEndTime(date);
        Assert.assertEquals(testTask.getScheduledEndTime(), date);
        
        testTask.setEstimatedTime(new Double(2.0));
        Assert.assertEquals(testTask.getEstimatedTime(), new Double(2.0));
        
        testTask.setCustomerFirstName("updateFirstName");
        Assert.assertEquals(testTask.getCustomerFirstName(), "updateFirstName");
        
        testTask.setCustomerLastName("updateLastName");
        Assert.assertEquals(testTask.getCustomerLastName(), "updateLastName");
        
        testTask.setHomeNum(1234L);
        Assert.assertEquals(testTask.getHomeNum(), new Long(1234));
        
        testTask.setMobileNum(5678L);
        Assert.assertEquals(testTask.getMobileNum(), new Long(5678));
        
        testTask.setTimespent(new Double(3.0));
        Assert.assertEquals(testTask.getTimespent(), 3.0);
        
        GwtCompany testCompany = new GwtCompany("companyAddress", "companyName");
        testTask.setCompany(testCompany);
        Assert.assertEquals(testTask.getCompany(), testCompany);
        
        Assert.assertEquals(TaskStatus.Assigned.toString(), "Assigned");
        Assert.assertEquals(TaskPriority.LOW.toString(), "Low");
    }
}