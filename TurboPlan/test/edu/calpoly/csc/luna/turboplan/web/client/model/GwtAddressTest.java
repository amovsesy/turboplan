package edu.calpoly.csc.luna.turboplan.web.client.model;


import org.testng.Assert;
import org.testng.annotations.Test;


@Test(groups = { "TurboPlan.all", "TurboPlan.web", "TurboPlan.gwtmodel" })
public class GwtAddressTest {
	@Test
	public void read() {
		
		
		GwtAddress testAddress = new GwtAddress("123 Test St.", "TS", "Testville",  88888, "USA");
		Assert.assertEquals(testAddress.getAddress(), "123 Test St.");
		Assert.assertEquals(testAddress.getCity(), "Testville");
		Assert.assertEquals(testAddress.getCountry(), "USA");
		Assert.assertEquals(testAddress.getState(), "TS");
		Assert.assertEquals(testAddress.getZip(), 88888);
	}
}