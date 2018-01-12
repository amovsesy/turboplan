package edu.calpoly.csc.luna.turboplan.core.dao;

import edu.calpoly.csc.luna.turboplan.core.entity.AbstractUser;
import edu.calpoly.csc.luna.turboplan.core.entity.Company;
import edu.calpoly.csc.luna.turboplan.core.entity.Skill;
import edu.calpoly.csc.luna.turboplan.core.entity.Task;
import edu.calpoly.csc.luna.turboplan.core.entity.User;
import edu.calpoly.csc.luna.turboplan.core.entity.WebServiceClient;

public final class EntityConstants {
	public static final String TaskUserJunction = "User_Task";
	public static final String User = User.class.getName();
	public static final String AbsUser = AbstractUser.class.getName();
	public static final String Task = Task.class.getName();
	public static final String Company = Company.class.getName();
	public static final String Skill = Skill.class.getName();
	public static final String WebServiceClient = WebServiceClient.class.getName();
}
