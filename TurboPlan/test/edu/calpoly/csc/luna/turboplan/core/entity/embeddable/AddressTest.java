package edu.calpoly.csc.luna.turboplan.core.entity.embeddable;


import org.testng.Assert;
import org.testng.annotations.Test;


import edu.calpoly.csc.luna.turboplan.core.entity.embeddable.Address;

@Test(groups = { "TurboPlan.all", "TurboPlan.core", "TurboPlan.entity" })
public class AddressTest {
   @Test
   public void read() {
      
      
      Address testAddress = new Address("123 Test St.", "Testville", "USA", "TS", 88888);
      Assert.assertEquals(testAddress.getAddress(), "123 Test St.");
      Assert.assertEquals(testAddress.getCity(), "Testville");
      Assert.assertEquals(testAddress.getCountry(), "USA");
      Assert.assertEquals(testAddress.getState(), "TS");
      Assert.assertEquals(testAddress.getZip(), 88888);
   }
}