package edu.calpoly.csc.luna.turboplan.web.client.model;

import org.testng.Assert;
import org.testng.annotations.Test;

import edu.calpoly.csc.luna.turboplan.core.entity.AbstractUser;
import edu.calpoly.csc.luna.turboplan.core.entity.Task;
import edu.calpoly.csc.luna.turboplan.core.entity.embeddable.UserProfile;

@Test(groups = { "TurboPlan.all", "TurboPlan.web", "TurboPlan.gwtmodel" })
public class GwtModelTest {
	@Test
	public void syncTaskStatusEnum() {
		GwtTask.TaskStatus[] gwtTaskStatus = GwtTask.TaskStatus.values();
		Task.TaskStatus[] dbTaskStatus = Task.TaskStatus.values();
		
		Assert.assertEquals(gwtTaskStatus.length, dbTaskStatus.length);
		
		/* Will raise exception when the contents in 2 classes don't match */
		for (GwtTask.TaskStatus gwtStatus : gwtTaskStatus) {
			Task.TaskStatus.valueOf(gwtStatus.name());
		}
		
	}
	
	@Test
	public void syncEmployeeGenderEnum() {
		GwtUserProfile.EmployeeGender[] gwtGenderVal = GwtUserProfile.EmployeeGender.values();
		UserProfile.EmployeeGender[] gender = UserProfile.EmployeeGender.values();
		
		Assert.assertEquals(gwtGenderVal.length, gender.length);
		
		/* Will raise exception when the contents in 2 classes don't match */
		for (GwtUserProfile.EmployeeGender gen : gwtGenderVal) {
			UserProfile.EmployeeGender.valueOf(gen.name());
		}
	}
	
	@Test
	public void syncPermissionEnum() {
		GwtUser.Permission[] gwtPermVal = GwtUser.Permission.values();
		AbstractUser.Permission[] ePermVal = AbstractUser.Permission.values();
		
		Assert.assertEquals(gwtPermVal.length, ePermVal.length);
		
		for (GwtUser.Permission perm : gwtPermVal) {
			AbstractUser.Permission.valueOf(perm.name());
		}
	}
}
