package edu.calpoly.csc.luna.turboplan.core.entity;

import org.testng.Assert;
import org.testng.annotations.Test;

@Test(groups = { "TurboPlan.all", "TurboPlan.core", "TurboPlan.entity" })
public class SkillTest {
	private Skill skill;

	public void constructors() {
		Assert.assertNull(skill);

		skill = new Skill();
		Assert.assertNull(skill.getSkillID());
		Assert.assertNull(skill.getCompany());
		Assert.assertNull(skill.getDescription());
		Assert.assertNull(skill.getTitle());
		
		skill = new Skill("const title", new Company("name", "add"));
		Assert.assertEquals(skill.getTitle(), "const title");
		Assert.assertNull(skill.getDescription());
		Assert.assertNull(skill.getSkillID());
		Assert.assertEquals(skill.getCompany(), new Company("name", "add"));
		
	}

	@Test(dependsOnMethods = { "constructors" })
	public void gettersAndSetters() {
		skill.setSkillID(1L);
		Assert.assertEquals(skill.getSkillID(), Long.valueOf(1L));

		skill.setTitle("title");
		Assert.assertEquals(skill.getTitle(), "title");

		skill.setDescription("desc");
		Assert.assertEquals(skill.getDescription(), "desc");
		
		Company differentCompany = new Company("newName", "newAddress");
		skill.setCompany(differentCompany);
		Assert.assertEquals(skill.getCompany(), differentCompany);
	}
	
	@Test(dependsOnMethods = { "constructors" })
	public void hashCodeReturnValues()
	{
	   skill.setTitle(null);
	   skill.setDescription(null);
	   skill.setSkillID(null);
	   skill.setCompany(null);
	   
	   int blankSkillHashValue = new Integer(skill.hashCode());
	   
	   int prime = 31;
	   String title = "title";
	   Long id = new Long(1000);
	   String description = "description";
	   Company company = new Company("name", "address");
	   
	   skill.setCompany(company);
	   
	   Assert.assertEquals(blankSkillHashValue, skill.hashCode());
	   
	   skill.setTitle(title);
	   Assert.assertEquals(blankSkillHashValue, 
	         skill.hashCode() - title.hashCode());
	   
	   skill.setTitle(null);
	   skill.setSkillID(id);
	   Assert.assertEquals(blankSkillHashValue, skill.hashCode() - (id.hashCode()
	         * prime));
	   
	   skill.setSkillID(null);
	   skill.setDescription(description);
	   Assert.assertEquals(blankSkillHashValue, skill.hashCode() - 
	         (description.hashCode() * prime * prime));
	}
	
	@Test(dependsOnMethods = { "constructors" })
	public void cloneMethod() throws CloneNotSupportedException
	{
	   skill.setTitle(null);
	   skill.setDescription(null);
	   skill.setSkillID(null);
	   skill.setCompany(null);
	   
	   String title = "title";
	   String description = "description";
	   Long id = new Long(1000);
	   Company company = new Company("Name", "Address");
	   
	   Object result = null;
	   
	   result = skill.clone();
	   Assert.assertNotNull(result);
	   Assert.assertTrue(result instanceof Skill);
	   Skill clone = (Skill)result;
      Assert.assertNull(clone.getCompany());
      Assert.assertNull(clone.getDescription());
      Assert.assertNull(clone.getSkillID());
      Assert.assertNull(clone.getTitle());
	   
	   
	   skill.setTitle(title);
	   skill.setDescription(description);
	   skill.setSkillID(id);
	   skill.setCompany(company);
	   
	   Assert.assertNull(clone.getCompany());
      Assert.assertNull(clone.getDescription());
      Assert.assertNull(clone.getSkillID());
      Assert.assertNull(clone.getTitle());
	   
      result = skill.clone();
      Assert.assertNotNull(result);
      Assert.assertTrue(result instanceof Skill);
      clone = (Skill)result;
      Assert.assertEquals(company, clone.getCompany());
      Assert.assertEquals(description, clone.getDescription());
      Assert.assertEquals(id, clone.getSkillID());
      Assert.assertEquals(title, clone.getTitle());
	}
	
	@Test(dependsOnMethods = { "constructors", "cloneMethod" })
	public void equalsComparison() throws CloneNotSupportedException
	{
	   Assert.assertEquals(skill, skill);
	   Assert.assertFalse(skill.equals(null));
	   
	   Assert.assertFalse(skill.equals(new Object()));
	   
	   Skill clone = (Skill)skill.clone();
	   Assert.assertEquals(skill, clone);
	   
	   skill.setCompany(null);
	   clone.setCompany(new Company("Name", "Address"));
	   Assert.assertEquals(skill, clone);
	   
	   skill.setDescription(null);
	   clone.setDescription(null);
	   Assert.assertEquals(skill, clone);
	   
	   skill.setDescription("description");
	   Assert.assertFalse(skill.equals(clone));
	   Assert.assertFalse(clone.equals(skill));
	   
	   clone.setDescription("description");
	   Assert.assertEquals(skill, clone);
	   
	   skill.setSkillID(null);
	   clone.setSkillID(null);
	   Assert.assertEquals(skill, clone);
	   
	   skill.setSkillID(new Long(1000));
	   Assert.assertFalse(skill.equals(clone));
	   Assert.assertFalse(clone.equals(skill));
	   
	   clone.setSkillID(skill.getSkillID());
	   Assert.assertEquals(skill, clone);
	   
	   skill.setTitle(null);
	   clone.setTitle(null);
      Assert.assertEquals(skill, clone);
      
      skill.setTitle("Title");
      Assert.assertFalse(skill.equals(clone));
      Assert.assertFalse(clone.equals(skill));
	   
      clone.setTitle("Title");
      Assert.assertEquals(skill, clone);
	   
	}
}
