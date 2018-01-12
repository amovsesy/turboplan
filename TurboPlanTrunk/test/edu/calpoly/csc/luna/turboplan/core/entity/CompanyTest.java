package edu.calpoly.csc.luna.turboplan.core.entity;

import java.util.Set;
import java.util.TreeSet;

import org.testng.Assert;
import org.testng.annotations.Test;

@Test ( groups = { "TurboPlan.all", "TurboPlan.core", "TurboPlan.entity" } )
public class CompanyTest {
	private Company company;
	
	public void constructors() {
		company = new Company();
		Assert.assertNull(company.getAddress());
		Assert.assertNull(company.getCompanyID());
		Assert.assertNull(company.getName());
		Assert.assertNull(company.getSkills());
		Assert.assertNull(company.getTasks());
		Assert.assertNull(company.getUsers());
		
		company = new Company("constname", "constadd");
		Assert.assertEquals(company.getName(), "constname");
		Assert.assertEquals(company.getAddress(), "constadd");
	}
	
	public void gettersAndSetters() {
		company.setCompanyID(1L);
		Assert.assertEquals(company.getCompanyID(), Long.valueOf(1L));
		
		company.setName("name");
		Assert.assertEquals(company.getName(), "name");
		
		company.setAddress("address");
		Assert.assertEquals(company.getAddress(), "address");
		
		Set<AbstractUser> users = new TreeSet<AbstractUser>();
		company.setUsers(users);
		Assert.assertEquals(users, company.getUsers());
	}
}
