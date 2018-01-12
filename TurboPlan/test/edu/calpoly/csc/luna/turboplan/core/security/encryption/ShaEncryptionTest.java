package edu.calpoly.csc.luna.turboplan.core.security.encryption;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Test(groups = { "TurboPlan.all", "TurboPlan.core", "TurboPlan.security", "TurboPlan.security.authentication",
		"TurboPlan.security.authentication.encryption"})
public class ShaEncryptionTest {
	private static final String plainText = "asdlfdjadfsaf";
	
	@Test
	public void checkShaCorrect(){
		assert Encryption.getInstance().encrypt("asdlfdjadfsaf").equals("zW/ZMgi9BeJDB4Aw90p/5SKbAbc=");
	}
	
	@Test
	public void checkShaWrong(){
		assert !(Encryption.getInstance().encrypt("asdlfdjadfsaf").equals("zW/asdf"));
	}
}
