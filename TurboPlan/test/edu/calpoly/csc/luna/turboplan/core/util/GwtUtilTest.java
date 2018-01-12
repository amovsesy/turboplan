package edu.calpoly.csc.luna.turboplan.core.util;

import java.util.Arrays;
import java.util.Set;

import org.testng.Assert;
import org.testng.annotations.Test;

import edu.calpoly.csc.luna.turboplan.core.entity.Company;
import edu.calpoly.csc.luna.turboplan.core.entity.Skill;
import edu.calpoly.csc.luna.turboplan.core.entity.Task;
import edu.calpoly.csc.luna.turboplan.core.entity.User;
import edu.calpoly.csc.luna.turboplan.core.entity.embeddable.UserProfile;
import edu.calpoly.csc.luna.turboplan.web.client.model.GwtCompany;
import edu.calpoly.csc.luna.turboplan.web.client.model.GwtSkill;
import edu.calpoly.csc.luna.turboplan.web.client.model.GwtTask;
import edu.calpoly.csc.luna.turboplan.web.client.model.GwtUser;
import edu.calpoly.csc.luna.turboplan.web.client.model.GwtUserProfile;

@Test (groups = {"TurboPlan.all", "TurboPlan.core", "TurboPlan.util", "TurboPlan.util.gwt"})
public class GwtUtilTest {
	@Test
	public void gwt2hib() {
		Object company = GwtUtil.gwt2hib(new GwtCompany());
		Assert.assertTrue(company instanceof Company);

		Object skill = GwtUtil.gwt2hib(new GwtSkill());
		Assert.assertTrue(skill instanceof Skill);

		Object task = GwtUtil.gwt2hib(new GwtTask());
		Assert.assertTrue(task instanceof Task);

		Object user = GwtUtil.gwt2hib(new GwtUser());
		Assert.assertTrue(user instanceof User);

	}

	@Test
	public void hib2gwt() {
		Object user = GwtUtil.hib2gwt(new User());
		Assert.assertTrue(user instanceof GwtUser);
		
		Object task = GwtUtil.hib2gwt(new Task());
		Assert.assertTrue(task instanceof GwtTask);
		
		Object skill = GwtUtil.hib2gwt(new Skill());
		Assert.assertTrue(skill instanceof GwtSkill);
		
		Object company = GwtUtil.hib2gwt(new Company());
		Assert.assertTrue(company instanceof GwtCompany);
	}

	public void gwt2hibUserProfile() {
		Object profile = GwtUtil.gwt2hibUserProfile(new GwtUserProfile());
		Assert.assertTrue(profile instanceof UserProfile);
	}

	public void hib2gwtUserProfile() {
		Object profile = GwtUtil.hib2gwtUserProfile(new UserProfile());
		Assert.assertTrue(profile instanceof GwtUserProfile);
	}

	public void hib2gwtTaskSet() {
		Set<GwtTask> set = GwtUtil.hib2gwtTaskSet(Arrays.asList(new Task[] {
				new Task(), new Task()
		}));
		
		Assert.assertEquals(set.size(), 2);
		for (Object obj : set) {
			Assert.assertTrue(obj instanceof GwtTask);
		}
	}
}
