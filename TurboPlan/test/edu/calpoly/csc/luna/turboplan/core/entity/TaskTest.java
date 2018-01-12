package edu.calpoly.csc.luna.turboplan.core.entity;

import java.util.Date;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import edu.calpoly.csc.luna.turboplan.core.entity.Task.TaskPriority;
import edu.calpoly.csc.luna.turboplan.core.entity.Task.TaskStatus;
import edu.calpoly.csc.luna.turboplan.core.entity.embeddable.Address;

/**
 * Task Test
 * @author Paul De Leon
 */
@Test ( groups = { "TurboPlan.all", "TurboPlan.core", "TurboPlan.entity" } )
@SuppressWarnings("deprecation")
public class TaskTest {    
    TaskStatus status;
    String title;
    String description;
    Address location;
    String customerFirstName;
    String customerLastName;
    String homeNum;
    String mobileNum;
    
    final int yearOffset = 1900;
    int createdYear, startYear, endYear;
    int createdMonth, startMonth, endMonth;
    int createdDayOfMonth, startDayOfMonth, endDayOfMonth;
    int createdHours, startHours, endHours;
    int createdMin, startMin, endMin;
    
    Date createdDate;
    Date startDate; 
    Date endDate;

    Double estTime;
    TaskPriority priority;
    
    Company company;
    
    Task task;
    

    @BeforeClass
    public void setUp() {
        status = TaskStatus.New;
        title = "Paul's Task";
        description = "Deliver the package";
        location = new Address("4096 Northland Terrace", "Fremont", "California", 95206);
        customerFirstName = "Stephanie";
        customerLastName = "Long";
        homeNum = "805-987-6543";
        mobileNum = "805-123-4567";
        
        createdYear = 2009; startYear = 2009; endYear = 2009;
        createdMonth = 0; startMonth = 2; endMonth = 2;
        createdDayOfMonth = 18; startDayOfMonth = 28; endDayOfMonth = 28;
        createdHours = 7; startHours = 7; endHours = 11;
        createdMin = 30; startMin = 0; endMin = 30;
        
        createdDate = new Date(createdYear - yearOffset, createdMonth, createdDayOfMonth, createdHours, createdMin);
        startDate = new Date(startYear - yearOffset, startMonth, startDayOfMonth, startHours, startMin); 
        endDate = new Date(endYear - yearOffset, endMonth, endDayOfMonth, endHours, endMin);

        estTime = new Double(60.0);
        priority = TaskPriority.NORMAL;
        
        company = new Company("FedEx", "Somewhere Out There 93405");
        
        task = new Task(status, title, description, location, customerFirstName, customerLastName, 
                homeNum, mobileNum, createdDate, startDate, endDate, estTime, priority, company);
            
        }

    
    @Test ( groups = { "TurboPlan.all", "TurboPlan.entity" } )
    public void testGetters() {
        Assert.assertTrue(task.getStatus().equals(status));
        Assert.assertTrue(task.getTitle().equals(title));
        Assert.assertTrue(task.getDescription().equals(description));
        Assert.assertTrue(task.getLocation().equals(location));
        Assert.assertTrue(task.getCustomerFirstName().equals(customerFirstName));
        Assert.assertTrue(task.getCustomerLastName().equals(customerLastName));
        Assert.assertTrue(task.getHomeNum().equals(homeNum));
        Assert.assertTrue(task.getMobileNum().equals(mobileNum));
        Assert.assertTrue(task.getCreatedDate().equals(createdDate));
        Assert.assertTrue(task.getSuggestedStartTime().equals(startDate));
        Assert.assertTrue(task.getSuggestedEndTime().equals(endDate));
        Assert.assertTrue(task.getEstimatedTime().equals(estTime));
        Assert.assertTrue(task.getPriority().equals(priority));
        Assert.assertTrue(task.getCompany().equals(company));
        task.setTimespent(new Double(3.0));
        Assert.assertTrue(task.getTimespent() == 3.0);
    }
}
