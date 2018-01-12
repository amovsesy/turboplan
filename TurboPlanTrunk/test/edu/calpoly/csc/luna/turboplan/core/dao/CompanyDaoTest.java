package edu.calpoly.csc.luna.turboplan.core.dao;

import java.util.List;

import org.hibernate.LazyInitializationException;
import org.testng.Assert;
import org.testng.annotations.Test;

import edu.calpoly.csc.luna.turboplan.core.entity.Company;

@Test(groups = { "TurboPlan.all", "TurboPlan.core", "TurboPlan.dao", "TurboPlan.dao.company" })
public class CompanyDaoTest {
	@Test(groups = { "TurboPlan.dao.company.add" })
	public void addCompany() {
		int before = CompanyDao.getInstance().getTableRowCount();

		CompanyDao.getInstance().save(DaoDataProvider.testCompany);

		int after = CompanyDao.getInstance().getTableRowCount();

		Assert.assertEquals(after, before + 1);
	}

	@Test(dependsOnMethods={"addCompany"})
	public void getAllCompany() {
		List<Company> list = CompanyDao.getInstance().getAll(Company.class);
		Assert.assertTrue(list.size() > 0);

		for (Company comp : list) {
			assertExceptionCount(comp, 3);
		}
	}

	public void getAllCompanyEager() {
		List<Company> assert0 = CompanyDao.getInstance().getAllCompany(true, true, true);
		for (Company comp : assert0) {
			assertExceptionCount(comp, 0);
		}

		List<Company> assert1 = CompanyDao.getInstance().getAllCompany(true, true, false);
		for (Company comp : assert1) {
			assertExceptionCount(comp, 1);
		}

		List<Company> assert2 = CompanyDao.getInstance().getAllCompany(true, false, false);
		for (Company comp : assert2) {
			assertExceptionCount(comp, 2);
		}

		List<Company> assert3 = CompanyDao.getInstance().getAllCompany(false, false, false);
		for (Company comp : assert3) {
			assertExceptionCount(comp, 3);
		}

	}

	private void assertExceptionCount(Company comp, int count) {
		int exCount = 0;

		try {
			comp.getSkills().size();
		} catch (LazyInitializationException ex) {
			exCount++;
		}

		try {
			comp.getTasks().size();
		} catch (LazyInitializationException ex) {
			exCount++;
		}

		try {
			comp.getUsers().size();
		} catch (LazyInitializationException ex) {
			exCount++;
		}

		Assert.assertEquals(exCount, count);
	}

	@Test(dependsOnMethods = { "addCompany" })
	public void getTableRowCount() {
		Assert.assertTrue(CompanyDao.getInstance().getTableRowCount() > 0);
	}

	@Test(dependsOnMethods = { "addCompany" })
	public void printTable() {
		String[] lines = CompanyDao.getInstance().printTable().split("\n");
		Assert.assertTrue(lines.length > 0);
		for (String line : lines) {
			Assert.assertTrue(line.split("\t").length > 2);
		}
	}

}
