package edu.calpoly.csc.luna.turboplan.axis2ws.mobile.model;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * MobileCompanyTest - tests methods within MobileCompany
 * 
 * @author Bradley Barbee - Luna Set
 * @version 1.0
 */
@Test(groups = { "TurboPlan.all", "TurboPlan.mobile", "TurboPlan.mobile.model" })
public class MobileCompanyTest {

   MobileCompany company;

   @BeforeMethod
    public void setup() {
        company = new MobileCompany(
                "Empty Set",  
                "1 Grand Avenue");
   }
   
   @Test (groups = {"constructor", "setters", "getters"})
    public void testConstructor() {
      company = new MobileCompany();
      company.setAddress("test");
      Assert.assertTrue(company.getAddress().equals("test"));
   }
   
   @Test (groups = "getters")
    public void testGetAddress() {
      Assert.assertTrue(company.getAddress().equals("1 Grand Avenue"));
   }
   
   @Test (groups = {"setters", "getters"})
    public void testSetAddress() {
      company.setAddress("2 Grand Avenue");
      Assert.assertTrue(company.getAddress().equals("2 Grand Avenue"));
   }
   
   @Test (groups = "getters")
    public void testGetName() {
      Assert.assertTrue(company.getName().equals("Empty Set"));
   }

   @Test (groups = {"setters", "getters"})
    public void testSetName() {
      company.setName("Bradley Inc");
      Assert.assertTrue(company.getName().equals("Bradley Inc"));
   }

   @Test (groups = {"setters", "getters"})
    public void testID() {
      company.setCompanyID(1L);
      Assert.assertTrue(company.getCompanyID().equals(1L));
   }  
}
