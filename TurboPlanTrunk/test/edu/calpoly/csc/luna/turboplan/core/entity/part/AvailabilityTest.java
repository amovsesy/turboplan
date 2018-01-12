package edu.calpoly.csc.luna.turboplan.core.entity.part;

import org.testng.Assert;
import org.testng.annotations.Test;

public class AvailabilityTest {
	private Availability avail;

	@Test
	public void constructors() {
		avail = new Availability(new boolean[][] { { true, false, true, false }, { true, false, true, false },
				{ true, false, true, false }, { true, false, true, false }, { true, false, true, false }, });
		Assert.assertEquals(avail.getAvail(), new boolean[][] { { true, false, true, false },
				{ true, false, true, false }, { true, false, true, false }, { true, false, true, false },
				{ true, false, true, false }, });

		avail = new Availability();
		Assert.assertNotNull(avail.getAvail());

	}

	@Test(dependsOnMethods = { "constructors" })
	public void gettersAndSetters() {
		avail.setAvail(new boolean[][] { { true, false, true, false }, { true, false, true, false },
				{ true, false, true, true }, { true, false, true, false }, { true, false, true, false }, });
		Assert.assertEquals(avail.getAvail(), new boolean[][] { { true, false, true, false },
				{ true, false, true, false }, { true, false, true, true }, { true, false, true, false },
				{ true, false, true, false }, });

	}
}
