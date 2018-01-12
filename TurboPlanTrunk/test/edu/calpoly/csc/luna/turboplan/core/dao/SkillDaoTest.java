package edu.calpoly.csc.luna.turboplan.core.dao;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import edu.calpoly.csc.luna.turboplan.core.entity.Company;
import edu.calpoly.csc.luna.turboplan.core.entity.Skill;

@Test(groups = { "TurboPlan.all", "TuboPlan.core", "TurboPlan.dao", "TurboPlan.dao.skill" }, dependsOnGroups = { "TurboPlan.dao.company" })
public class SkillDaoTest {
	@Test(dataProvider = "companies", dataProviderClass = DaoDataProvider.class)
	public void getSkillByCompanyId(Company company) {
		List<Skill> list = SkillDao.getInstance().getSkillsBelongToCompanyById(company.getCompanyID());

		for (Skill skill : list) {
			Assert.assertEquals(skill.getCompany().getCompanyID(), company.getCompanyID());
		}
	}

	@Test(dataProvider = "skills", dataProviderClass = DaoDataProvider.class)
	public void addSkill(Skill skill) {
		int before = SkillDao.getInstance().getTableRowCount();

		skill.setCompany(DaoDataProvider.testCompany);
		SkillDao.getInstance().addSkill(skill);

		int after = SkillDao.getInstance().getTableRowCount();

		Assert.assertEquals(after, before + 1);
	}

	public void getAllSkill() {
		int actual = SkillDao.getInstance().getAllSkill().size();
		int expected = SkillDao.getInstance().getTableRowCount();

		Assert.assertEquals(actual, expected);
	}

	@Test(dependsOnMethods = { "addSkill" })
	public void getSkillTableRowCount() {
		Assert.assertTrue(SkillDao.getInstance().getTableRowCount() > 0);
	}
}
