package edu.calpoly.csc.luna.turboplan.core.entity.part;

import java.util.Date;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Test(groups = { "TurboPlan.all", "TurboPlan.core", "TurboPlan.entity", "TurboPlan.entity.part", 
		"TurboPlan.entity.part.TimeInterval" })
public class TimeIntervalTest {
	private final Date start = new Date(109, 1, 12);
	private final Date end = new Date(109, 1, 14);
	private final TimeInterval time = new TimeInterval(start, end);
	
	@Test
	public void checkStart() {
		assert time.getStart().equals(start);
	}
	
	@Test
	public void checkEnd() {
		assert time.getEnd().equals(end);
	}
	
	@Test
	public void checkEquals1() {
		TimeInterval c1 = new TimeInterval(start,end);
		
		assert time.equals(c1);
	}
	
	@Test
	public void checkEquals2() {
		TimeInterval c2 = time;
		
		assert time.equals(c2);
	}
	
	@Test
	public void checkEquals3() {
		TimeInterval c3 = new TimeInterval(new Date(109,1,16), new Date(109,1,9));
		
		assert !(time.equals(c3));
	}
	
	
	@Test
	public void checkEquals4() {
		assert !(time.equals(null));
	}
	
	@Test
	public void checkEquals5() {
		assert !(time.equals(new Long(2)));
	}
}
