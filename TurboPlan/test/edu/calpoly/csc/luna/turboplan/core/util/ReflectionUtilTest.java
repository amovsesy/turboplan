package edu.calpoly.csc.luna.turboplan.core.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;

import org.testng.Assert;
import org.testng.annotations.Test;

import edu.calpoly.csc.luna.turboplan.core.entity.User;

@Test(groups = { "TurboPlan.all", "TurboPlan.core", "TurboPlan.util" })
public class ReflectionUtilTest {

	public void setAndGetFieldValueOfObject() throws SecurityException, NoSuchMethodException,
			IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		final String expected = "abc";

		User usr = new User();
		ReflectionUtil.setFieldValueOfObject(usr, "userName", expected);

		Assert.assertEquals(ReflectionUtil.getFieldValueOfObject(usr, "userName"), expected);
	}

	public void getCollectionContentType() {
		Collection<User> col = new ArrayList<User>();
		col.add(new User());

		Assert.assertEquals(ReflectionUtil.getCollectionContentType(col), User.class);

	}
}
