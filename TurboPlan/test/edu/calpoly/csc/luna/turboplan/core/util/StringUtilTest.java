package edu.calpoly.csc.luna.turboplan.core.util;

import org.testng.Assert;
import org.testng.annotations.Test;

@Test(groups = { "TurboPlan.all", "TurboPlan.core", "TurboPlan.util", "TurboPlan.util.string" })
public class StringUtilTest {
	public void arrayToString() {
		String[] arr = new String[] { "string1", "string2", "string3" };

		String actual = StringUtil.arrayToString(arr);
		String expected = "[string1, string2, string3]";
		Assert.assertEquals(actual, expected);
	}

	public void beanToString() {
		// test 2 overrided
		Assert.fail("Test not implemented");
		
		
	}

	public void fieldName2GetterName() {
		String actual = StringUtil.fieldName2GetterName("userName");
		String expected = "getUserName";
		Assert.assertEquals(actual, expected);

		actual = StringUtil.fieldName2GetterName("Password");
		expected = "getPassword";
		Assert.assertEquals(actual, expected);
	}

	public void fieldName2SetterName() {
		String actual = StringUtil.fieldName2SetterName("userName");
		String expected = "setUserName";
		Assert.assertEquals(actual, expected);

		actual = StringUtil.fieldName2SetterName("Password");
		expected = "setPassword";
		Assert.assertEquals(actual, expected);
	}

	public void getHexString() {
		byte[] bin = new byte[] { (byte) 0xff, (byte) 0xaf, 0x28, 0x48 };
		String actual = StringUtil.getHexString(bin);
		String expected = "ffaf2848";
		Assert.assertEquals(actual, expected);

		bin = new byte[] { (byte) 0xfb, (byte) 0xaf, 0x18, 0x44 };
		actual = StringUtil.getHexString(bin);
		expected = "fbaf1844";
		Assert.assertEquals(actual, expected);
		
		bin = new byte[] { (byte) 0xeb, (byte) 0xae, 0x1e, (byte) 0xf4 };
		actual = StringUtil.getHexString(bin);
		expected = "ebae1ef4";
		Assert.assertEquals(actual, expected);
	}

	public void long2str() {
		Long val = 12985767L;

		String actual = StringUtil.long2str(val);
		String expected = "12985767";
		Assert.assertEquals(actual, expected);

		Assert.assertNull(StringUtil.long2str(null));
	}

	public void parseLong() {
		Long actual = StringUtil.parseLong("12578923123");
		Long expected = 12578923123L;
		Assert.assertEquals(actual, expected);

		Assert.assertNull(StringUtil.parseLong(null));
	}

	public static void main(String[] args) {
//		System.out.println(StringUtil.beanToString(new User, Field));
	}
}
