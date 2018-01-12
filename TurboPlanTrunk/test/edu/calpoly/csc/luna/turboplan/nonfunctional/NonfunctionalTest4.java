package edu.calpoly.csc.luna.turboplan.nonfunctional;

import java.util.List;

import org.testng.annotations.Test;

import edu.calpoly.csc.luna.turboplan.core.dao.DaoDataProvider;
import edu.calpoly.csc.luna.turboplan.core.dao.TaskDao;
import edu.calpoly.csc.luna.turboplan.core.dao.UserDao;
import edu.calpoly.csc.luna.turboplan.core.entity.Task;
import edu.calpoly.csc.luna.turboplan.core.entity.User;

/**
 * NonfunctionalTest4 - tests time to read queries for users and tasks
 * 
 * @author Bradley Barbee - Luna Set
 * @version 1.0
 */
@Test(groups = { "TurboPlan.all", "TurboPlan.nonfunctional" })
public class NonfunctionalTest4 {
	
	@Test
	public void testNonfunctional4() {
		long start = System.currentTimeMillis();
		
		User user = UserDao.getInstance().getUserByClearTextUsername("djanzen");
		assert(user!=null);
		
		List<Task> task = TaskDao.getInstance().getAllTasksBelongToUserById(user.getUserID());
		assert(task!=null);
		
		assert((System.currentTimeMillis() - start) < 5000);
	}
}
