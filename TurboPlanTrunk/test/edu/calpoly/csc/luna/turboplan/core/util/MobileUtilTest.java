package edu.calpoly.csc.luna.turboplan.core.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.testng.Assert;
import org.testng.annotations.Test;

import edu.calpoly.csc.luna.turboplan.axis2ws.mobile.model.MobileCompany;
import edu.calpoly.csc.luna.turboplan.axis2ws.mobile.model.MobileModel;
import edu.calpoly.csc.luna.turboplan.axis2ws.mobile.model.MobileTask;
import edu.calpoly.csc.luna.turboplan.axis2ws.mobile.model.MobileUser;
import edu.calpoly.csc.luna.turboplan.core.entity.BaseEntity;
import edu.calpoly.csc.luna.turboplan.core.entity.Company;
import edu.calpoly.csc.luna.turboplan.core.entity.Skill;
import edu.calpoly.csc.luna.turboplan.core.entity.Task;
import edu.calpoly.csc.luna.turboplan.core.entity.User;
import edu.calpoly.csc.luna.turboplan.core.modelconverter.MobileModelConverter;

@Test(groups = { "TurboPlan.all", "TurboPlan.core", "TurboPlan.util", "TurboPlan.util.mobile" })
public class MobileUtilTest {
	public void hib2mobile() {
		Object user = MobileUtil.hib2mobile(new User());
		Assert.assertTrue(user instanceof MobileUser);

		Object task = MobileUtil.hib2mobile(new Task());
		Assert.assertTrue(task instanceof MobileTask);

		Object company = MobileUtil.hib2mobile(new Company());
		Assert.assertTrue(company instanceof MobileCompany);
	}

	public void mobile2hib() {
		Object user = MobileUtil.mobile2hib(new MobileUser());
		Assert.assertTrue(user instanceof User);

		Object task = MobileUtil.mobile2hib(new MobileTask());
		Assert.assertTrue(task instanceof Task);

		Object company = MobileUtil.mobile2hib(new MobileCompany());
		Assert.assertTrue(company instanceof Company);
	}

	public void hib2mobileCollection() {
		Collection<MobileModelConverter<MobileCompany>> hibCompany = new ArrayList<MobileModelConverter<MobileCompany>>();
		hibCompany.add(new Company());
		Object company = MobileUtil.hib2mobileCollection(hibCompany);
		Assert.assertTrue(company instanceof MobileCompany[]);

		Collection<MobileModelConverter<MobileTask>> hibTask = new ArrayList<MobileModelConverter<MobileTask>>();
		hibTask.add(new Task());
		Object task = MobileUtil.hib2mobileCollection(hibTask);
		Assert.assertTrue(task instanceof MobileTask[]);

		Collection<MobileModelConverter<MobileUser>> hibUser = new ArrayList<MobileModelConverter<MobileUser>>();
		hibUser.add(new User());
		Object user = MobileUtil.hib2mobileCollection(hibUser);
		Assert.assertTrue(user instanceof MobileUser[]);
	}

	public void mobile2hibCollection() {
		Set company = MobileUtil.mobile2hibCollection(new MobileCompany[] { new MobileCompany() }, HashSet.class);
		for (Object obj : company) {
			Assert.assertTrue(obj instanceof Company);
		}

		Set task = MobileUtil.mobile2hibCollection(new MobileTask[] { new MobileTask() }, HashSet.class);
		for (Object obj : task) {
			Assert.assertTrue(obj instanceof Task);
		}

		Set user = MobileUtil.mobile2hibCollection(new MobileUser[] { new MobileUser() }, HashSet.class);
		for (Object obj : user) {
			Assert.assertTrue(obj instanceof User);
		}
	}
}
