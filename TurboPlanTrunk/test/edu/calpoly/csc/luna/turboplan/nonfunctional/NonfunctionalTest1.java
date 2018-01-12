package edu.calpoly.csc.luna.turboplan.nonfunctional;

import java.io.InputStream;
import java.util.StringTokenizer;

import org.testng.annotations.Test;

/**
 * NonfunctionalTest1 - tests server response time
 * 
 * @author Bradley Barbee - Luna Set
 * @version 1.0
 */
@Test(groups = { "TurboPlan.all", "TurboPlan.nonfunctional" })
public class NonfunctionalTest1 {
	
	@Test
	public void testNonfunctional1() {
		int pingCount = 0, successCount = 0;
		
		while(pingCount++ < 100) {
			if(doPing()) {
				successCount++;
			}
		}
		
		assert(successCount >= 90);
	}

	public static boolean doPing() {
		
		try {
			InputStream ins = Runtime.getRuntime().exec(
					"ping -c 1 -w 100000 lunaset.csc.calpoly.edu").getInputStream();
			Thread.sleep(200);
			byte[] prsbuf = new byte[ins.available()];
			ins.read(prsbuf);
			String parsstr = new StringTokenizer(new String(prsbuf), "%")
					.nextToken().trim();
			if (!parsstr.endsWith("100"))
				return true;
		} catch (Exception e) {
			return false;
		}
		
		return false;
	}
}
