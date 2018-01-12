package edu.calpoly.csc.luna.turboplan.core.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.LazyInitializationException;

import com.allen_sauer.gwt.log.client.Log;

import edu.calpoly.csc.luna.turboplan.core.entity.AbstractUser;
import edu.calpoly.csc.luna.turboplan.core.entity.Company;
import edu.calpoly.csc.luna.turboplan.core.entity.Skill;
import edu.calpoly.csc.luna.turboplan.core.entity.Task;
import edu.calpoly.csc.luna.turboplan.core.entity.User;
import edu.calpoly.csc.luna.turboplan.core.entity.AbstractUser.Permission;
import edu.calpoly.csc.luna.turboplan.core.entity.Task.TaskStatus;
import edu.calpoly.csc.luna.turboplan.core.entity.embeddable.Address;
import edu.calpoly.csc.luna.turboplan.core.entity.embeddable.UserProfile;
import edu.calpoly.csc.luna.turboplan.core.entity.part.Availability;
import edu.calpoly.csc.luna.turboplan.core.modelconverter.GwtModelConverter;
import edu.calpoly.csc.luna.turboplan.core.security.encryption.Encryption;
import edu.calpoly.csc.luna.turboplan.web.client.model.GwtAddress;
import edu.calpoly.csc.luna.turboplan.web.client.model.GwtCompany;
import edu.calpoly.csc.luna.turboplan.web.client.model.GwtModel;
import edu.calpoly.csc.luna.turboplan.web.client.model.GwtSkill;
import edu.calpoly.csc.luna.turboplan.web.client.model.GwtTask;
import edu.calpoly.csc.luna.turboplan.web.client.model.GwtUser;
import edu.calpoly.csc.luna.turboplan.web.client.model.GwtUserProfile;
import edu.calpoly.csc.luna.turboplan.web.client.model.GwtUserProfile.EmployeeGender;

/**
 * Used to convert objects to be GWT compatible
 * @author Ming Liu
 * @author Stephanie Long
 */
public class GwtUtil {
	private static final Logger log = Logger.getLogger(GwtUtil.class);

	/**
	 * Converts Hibernate objects to be GWT compatible
	 * @param <T1> GWT entity
	 * @param <T2> TurboPlanDatabaseEntity
	 * @param entity TurboPlan converted
	 * @return GWT converted entity
	 */
	@SuppressWarnings("unchecked")
	public static <G extends GwtModel, H extends GwtModelConverter<G>> G hib2gwt(H entity) {
		if (entity instanceof Company) {
			return (G) hib2gwtCompany((Company) entity);
		} else if (entity instanceof Skill) {
			return (G) hib2gwtSkill((Skill) entity);
		} else if (entity instanceof Task) {
			return (G) hib2gwtTask((Task) entity);
		} else if (entity instanceof User) {
			return (G) hib2gwtUser((User) entity);
		} else if (entity instanceof UserProfile) {
			return (G) hib2gwtUserProfile((UserProfile) entity);
		} else {
			return null;
		}
	}

	/**
	 * Converts GWT objects to be Hibernate compatible
	 * @param <T1> TurboPlanDatabaseEntity
	 * @param <T2> GWT entity
	 * @param gwtModel GWT converted
	 * @return Hibernate converted entity
	 */
	@SuppressWarnings("unchecked")
	public static <G extends GwtModel, H extends GwtModelConverter<G>> H gwt2hib(G gwtModel) {
		if (gwtModel instanceof GwtCompany) {
			return (H) gwt2hibCompany((GwtCompany) gwtModel);
		} else if (gwtModel instanceof GwtSkill) {
			return (H) gwt2hibSkill((GwtSkill) gwtModel);
		} else if (gwtModel instanceof GwtTask) {
			return (H) gwt2hibTask((GwtTask) gwtModel);
		} else if (gwtModel instanceof GwtUser) {
			return (H) gwt2hibUser((GwtUser) gwtModel);
		} else if (gwtModel instanceof GwtUserProfile) {
			return (H) gwt2hibUserProfile((GwtUserProfile) gwtModel);
		} else {
			return null;
		}
	}

	/**
	 * Converts Hibernate address to be GWT compatible
	 * @param usr The address to convert 
	 * @return The address in GWT compatible form
	 */
	private static GwtAddress hib2gwtAddress(Address address) {
		return address == null ? null : new GwtAddress(address.getAddress(), address.getState(), address.getCity(), address.getZip(), address.getCountry());
	}

	/**
	 * Converts Hibernate company to be GWT compatible
	 * @param usr The company to convert 
	 * @return The company in GWT compatible form
	 */
	private static GwtCompany hib2gwtCompany(Company company) {
		return company == null ? null : new GwtCompany(company.getCompanyID(), company.getName(), company.getAddress());
	}

	/**
	 * Converts Hibernate skill to be GWT compatible
	 * @param usr The skill to convert 
	 * @return The skill in GWT compatible form
	 */
	private static GwtSkill hib2gwtSkill(Skill skill) {
		if (skill == null) {
			return null;
		}
		
		GwtSkill ret = new GwtSkill(skill.getTitle(), hib2gwtCompany(skill.getCompany()));
		
		ret.setDescription(skill.getDescription());
		ret.setSkillID(skill.getSkillID());
		
		return ret;
	}

	/**
	 * Converts Hibernate task to be GWT compatible
	 * @param task The task to convert 
	 * @return The task in GWT compatible form
	 */
	private static GwtTask hib2gwtTask(Task task) {
		if (task == null)
			return null;
		
		GwtTask gTask = new GwtTask();
		
		gTask.setCustomerLastName(task.getCustomerLastName());
		gTask.setCustomerFirstName(task.getCustomerFirstName());
		
		if(task.getHomeNum() != null && !task.getHomeNum().equals("")) {
			gTask.setHomeNum(StringUtil.parseLong(task.getHomeNum()));
		}
		
		if(task.getMobileNum() != null && !task.getMobileNum().equals("")){
			gTask.setMobileNum(StringUtil.parseLong(task.getMobileNum()));
		}
		
		gTask.setCompany(hib2gwtCompany(task.getCompany()));
		gTask.setCreatedDate(task.getCreatedDate());
		gTask.setDescription(task.getDescription());
		gTask.setLocation(hib2gwtAddress(task.getLocation()));
		gTask.setNotes(task.getNotes());
		gTask.setRequiredSkill(hib2gwtSkillSet(task.getRequiredSkill()));
		gTask.setScheduledEndTime(task.getScheduledEndTime());
		gTask.setScheduledStartTime(task.getScheduledStartTime());
		gTask.setStatus(hib2gwtTaskStatus(task.getStatus()));
		gTask.setSuggestedEndTime(task.getSuggestedEndTime());
		gTask.setSuggestedStartTime(task.getSuggestedStartTime());
		gTask.setTaskID(task.getTaskID());
		gTask.setTitle(task.getTitle());
		gTask.setNotes(task.getNotes());
		gTask.setUsers(hib2gwtUserSet(task.getUsers()));
		gTask.setPriority(hib2gwtTaskPriority(task.getPriority()));
		gTask.setEstimatedTime(task.getEstimatedTime());
		gTask.setTimespent(task.getTimespent());

		return gTask;
	}

	private static GwtTask.TaskPriority hib2gwtTaskPriority(Task.TaskPriority task) {
		return task == null ? null : GwtTask.TaskPriority.valueOf(task.name());
	}
	
	private static GwtTask.TaskStatus hib2gwtTaskStatus(Task.TaskStatus status) {
		return status == null ? null : GwtTask.TaskStatus.valueOf(status.name());
	}

	/**
	 * Converts Hibernate availability to be GWT compatible
	 * @param interval The availability to convert 
	 * @return The availability in GWT compatible form
	 */
	private static boolean[][] hib2gwtAvailability(Availability interval) {
		return interval == null || interval.getAvail() == null ? null : interval.getAvail();
	}

	/**
	 * Converts Hibernate user to be GWT compatible
	 * @param usr The user to convert 
	 * @return The user in GWT compatible form
	 */
	private static GwtUser hib2gwtUser(User usr) {
		if (usr == null)
			return null;

		GwtUser gwtUser = new GwtUser();
		// TODO: KNOWN BUG
		// Causing password to be reset whenever the user profile update
		// Because the username/password stored on db is in binary
		// it cannot be converted to text and send to the

		gwtUser.setCompany(hib2gwtCompany(usr.getCompany()));
		gwtUser.setUserID(usr.getUserID());
		gwtUser.setActive(AbstractUser.UserStatus.Active.equals(usr.isActive()));
		gwtUser.setProfile(hib2gwtUserProfile(usr.getProfile()));
		gwtUser.setPermission(hib2gwtPermission(usr.getPermission()));

		return gwtUser;
	}

	private static Set<String> hib2gwtPermission(EnumSet<Permission> permission) {
		if (permission == null) {
			return null;
		}
		
		Set<String> ret = new HashSet<String>();
		
		for (Permission perm : permission) {
			ret.add(perm.name());
		}

		return ret;
	}

	/**
	 * Converts Hibernate user profile to be GWT compatible
	 * @param usr The user profile to convert 
	 * @return The user profile in GWT compatible form
	 */
	public static GwtUserProfile hib2gwtUserProfile(UserProfile profile) {
		if (profile == null)
			return null;

		GwtUserProfile gProfile = new GwtUserProfile();

		gProfile.setAddress(hib2gwtAddress(profile.getAddress()));
		gProfile.setAvailability(profile.getAvailability() == null ? null : profile.getAvailability().getAvail());
		gProfile.setDateOfBirth(profile.getDateOfBirth());
		gProfile.setEmail(profile.getEmail());
		gProfile.setEmail2(profile.getEmail2());
		gProfile.setEmail3(profile.getEmail3());
		gProfile.setEmail4(profile.getEmail4());
		gProfile.setEmail5(profile.getEmail5());
		gProfile.setFirstName(profile.getFirstName());
		gProfile.setGender(profile.getGender() == null ? null : EmployeeGender.valueOf(profile.getGender().name()));
		gProfile.setHomePhoneNumber(StringUtil.parseLong(profile.getHomePhoneNumber()));
		gProfile.setMobilePhoneNumber(StringUtil.parseLong(profile.getMobilePhoneNumber()));
		gProfile.setOtherPhoneNumber(StringUtil.parseLong(profile.getOtherPhoneNumber()));
		gProfile.setOtherPhoneNumber2(StringUtil.parseLong(profile.getOtherPhoneNumber2()));
		gProfile.setOtherPhoneNumber3(StringUtil.parseLong(profile.getOtherPhoneNumber3()));	
		gProfile.setSkills(hib2gwtSkillSet(profile.getSkills()));
//		gProfile.setTasks(hib2gwtTaskSet(profile.getTasks()));
		gProfile.setLastName(profile.getLastName());
		gProfile.setMiddleInitial(profile.getMiddleInitial());

		return gProfile;
	}

	/**
	 * Converts Hibernate task set to be GWT compatible
	 * @param hibSet The task set to convert 
	 * @return The task set in GWT compatible form
	 */
	public static Set<GwtTask> hib2gwtTaskSet(List<Task> hibSet) {
		if (hibSet == null)
			return null;

		Set<GwtTask> gSet = new HashSet<GwtTask>();

		try {
			for (Task task : hibSet) {
				gSet.add(hib2gwtTask(task));
			}
		} catch (LazyInitializationException ex) {
			return null;
		}

		return gSet;
	}

	/**
	 * Converts GWT address to be Hibernate compatible
	 * @param address The address to convert 
	 * @return The address in Hibernate compatible form
	 */
	private static Address gwt2hibAddress(GwtAddress address) {
		if (address == null)
			return null;

		Address add = new Address();

		add.setAddress(address.getAddress());
		add.setCity(address.getCity());
		add.setState(address.getState());
		add.setZip(address.getZip());
		add.setCountry(address.getCountry());

		return add;
	}

	/**
	 * Converts GWT company to be Hibernate compatible
	 * @param company The company to convert 
	 * @return The company in Hibernate compatible form
	 */
	private static Company gwt2hibCompany(GwtCompany gwtCompany) {
		if (gwtCompany == null)
			return null;

		Company company = new Company();

		company.setAddress(gwtCompany.getAddress());
		company.setCompanyID(gwtCompany.getCompanyID());
		company.setName(gwtCompany.getName());
		
		Set<AbstractUser> au = new HashSet<AbstractUser>();
		
		
		if(gwtCompany.getUsers() != null){
		
			for(GwtUser u : gwtCompany.getUsers()){
				au.add(gwt2hibUser(u));
			}
		}
				
		company.setUsers(au);

		return company;
	}

	/**
	 * Converts GWT skill to be Hibernate compatible
	 * @param skill The skill to convert 
	 * @return The skill in Hibernate compatible form
	 */
	private static Skill gwt2hibSkill(GwtSkill gwtSkill) {
		if (gwtSkill == null)
			return null;

		Skill skill = new Skill(gwtSkill.getTitle(), gwt2hibCompany(gwtSkill.getCompany()));

		skill.setSkillID(gwtSkill.getSkillID());
		skill.setDescription(gwtSkill.getDescription());

		return skill;
	}

	/**
	 * Converts GWT task to be Hibernate compatible
	 * @param task The task to convert 
	 * @return The task in Hibernate compatible form
	 */
	private static Task gwt2hibTask(GwtTask gwtTask) {
		if (gwtTask == null)
			return null;

		Task task = new Task();

		task.setTaskID(gwtTask.getTaskID());
		task.setRequiredSkill(gwt2hibSkillSet(gwtTask.getRequiredSkill()));
		task.setStatus(gwt2hibTaskStatus(gwtTask.getStatus()));
		task.setUsers(gwt2hibUserSet(gwtTask.getUsers()));
		task.setPriority(gwt2hibTaskPriority(gwtTask.getPriority()));
		task.setCompany(gwt2hibCompany(gwtTask.getCompany()));
		task.setCreatedDate(gwtTask.getCreatedDate());
		Log.trace("util Created date" + gwtTask.getCreatedDate());
		task.setCustomerFirstName(gwtTask.getCustomerFirstName());
		task.setCustomerLastName(gwtTask.getCustomerLastName());
		task.setDescription(gwtTask.getDescription());
		task.setHomeNum(StringUtil.long2str(gwtTask.getHomeNum()));
		task.setMobileNum(StringUtil.long2str(gwtTask.getMobileNum()));
		task.setTitle(gwtTask.getTitle());
		task.setLocation(gwt2hibAddress(gwtTask.getLocation()));
		task.setEstimatedTime(gwtTask.getEstimatedTime());
		task.setTimespent(gwtTask.getTimespent());
		task.setSuggestedStartTime(gwtTask.getSuggestedStartTime());
		task.setSuggestedEndTime(gwtTask.getSuggestedEndTime());
		task.setNotes(gwtTask.getNotes());
		task.setScheduledStartTime(gwtTask.getScheduledStartTime());
		task.setScheduledEndTime(gwtTask.getScheduledEndTime());
		
		return task;
	}

	private static Task.TaskPriority gwt2hibTaskPriority(GwtTask.TaskPriority gwtTask) {
		return gwtTask == null ? null : Task.TaskPriority.valueOf(gwtTask.name());
	}

	private static Task.TaskStatus gwt2hibTaskStatus(GwtTask.TaskStatus gwtTask) {
		return gwtTask == null ? null : TaskStatus.valueOf(gwtTask.name());
	}

	/**
	 * Converts GWT user set to be Hibernate compatible
	 * @param gwtUsers The user set to convert 
	 * @return The user set in Hibernate compatible form
	 */
	private static Set<User> gwt2hibUserSet(Set<GwtUser> gwtUsrs) {
		if (gwtUsrs == null)
			return null;

		Set<User> hibSet = new HashSet<User>();

		for (GwtUser usr : gwtUsrs) {
			hibSet.add(gwt2hibUser(usr));
		}

		return hibSet;
	}

	/**
	 * Converts GWT task detail to be Hibernate compatible
	 * @param gwtTask The task detail to convert 
	 * @return The task detail in Hibernate compatible form
	 */
	/*private static TaskDetail gwt2hibTaskDetail(GwtTask gwtTask) {
		if (gwtTask == null)
			return null;

		TaskDetail detail = new TaskDetail(gwtTask.getTitle(), gwtTask.getLocation());

		detail.setContactNames(gwtTask.getContactNames());
		detail.setCreatedDate(gwtTask.getCreatedDate());
		detail.setDescription(gwtTask.getDescription());
		detail.setDueDate(gwtTask.getDueDate());
		detail.setNotes(gwtTask.getNotes());
		detail.setPhoneNumbers(gwt2hibPhoneNumberCollection(gwtTask.getPhoneNumbers()));
		detail.setScheduledEndTime(gwtTask.getScheduledEndTime());
		detail.setScheduledStartTime(gwtTask.getScheduledStartTime());
		detail.setSuggestedEndTime(gwtTask.getSuggestedEndTime());
		detail.setSuggestedStartTime(gwtTask.getSuggestedStartTime());

		return detail;
	}*/

	/**
	 * Converts GWT phone number collection to be Hibernate compatible
	 * @param gwtCol The phone number collection to convert 
	 * @return The phone number collection in Hibernate compatible form
	 */
	private static Collection<Long> gwt2hibPhoneNumberCollection(Collection<String> gwtCol) {
		if (gwtCol == null)
			return null;

		Collection<Long> hibCol = new ArrayList<Long>();

		for (String num : gwtCol) {
			hibCol.add(Long.valueOf(num));
		}

		return hibCol;
	}

	/**
	 * Converts GWT skill set to be Hibernate compatible
	 * @param gSkill The skill set to convert 
	 * @return The skill set in Hibernate compatible form
	 */
	private static Set<Skill> gwt2hibSkillSet(Set<GwtSkill> gSkill) {
		if (gSkill == null)
			return null;

		Set<Skill> hibSkill = new HashSet<Skill>();

		for (GwtSkill skill : gSkill) {
			hibSkill.add(gwt2hibSkill(skill));
		}

		return hibSkill;
	}

	private static User gwt2hibUser(GwtUser gwtUsr) {
		if (gwtUsr == null) 
			return null;

		User usr = new User();

		usr.setUserName(Encryption.getInstance().encrypt(gwtUsr.getUsername()));
		usr.setPassword(Encryption.getInstance().encrypt(gwtUsr.getPassword()));
		usr.setActive(gwtUsr.isActive() ? AbstractUser.UserStatus.Active : AbstractUser.UserStatus.Inactive);
		usr.setCompany(gwt2hibCompany(gwtUsr.getCompany()));
		usr.setUserID(gwtUsr.getUserID());
		usr.setPermission(gwt2hibPermission(gwtUsr.getPermission()));
		usr.setProfile(gwt2hibUserProfile(gwtUsr.getProfile()));

		return usr;
	}

	private static EnumSet<Permission> gwt2hibPermission(Set<String> permission) {
		if (permission == null) {
			return null;
		}
		
		EnumSet<Permission> ret = EnumSet.noneOf(AbstractUser.Permission.class);
		
		for (String str : permission) {
			ret.add(AbstractUser.Permission.valueOf(str));
		}

		return ret;
	}

	/**
	 * Converts GWT user profile to be Hibernate compatible
	 * @param profile The user profile to convert 
	 * @return The user profile in Hibernate compatible form
	 */
	public static UserProfile gwt2hibUserProfile(GwtUserProfile profile) {
		if (profile == null)
			return null;

		UserProfile eProfile = new UserProfile();

		eProfile.setAddress(gwt2hibAddress(profile.getAddress()));
		eProfile.setAvailability(gwt2hibAvailability(profile.getAvailability()));
		eProfile.setDateOfBirth(profile.getDateOfBirth());
		eProfile.setEmail(profile.getEmail());
		eProfile.setEmail2(profile.getEmail2());
		eProfile.setEmail3(profile.getEmail4());
		eProfile.setEmail4(profile.getEmail4());
		eProfile.setEmail5(profile.getEmail5());
		eProfile.setFirstName(profile.getFirstName());
		eProfile.setGender(gwt2hibGender(profile.getGender()));
		eProfile.setHomePhoneNumber(StringUtil.long2str(profile.getHomePhoneNumber()));
		eProfile.setLastName(profile.getLastName());
		eProfile.setMiddleInitial(profile.getMiddleInitial());
		eProfile.setMobilePhoneNumber(StringUtil.long2str(profile.getMobilePhoneNumber())); // TODO:
		// testing
		// needed,
		// type
		// long
		// to
		// Number
		// conversion
		// and
		// convert
		// back.
		eProfile.setOtherPhoneNumber(StringUtil.long2str(profile.getOtherPhoneNumber()));
		eProfile.setOtherPhoneNumber2(StringUtil.long2str(profile.getOtherPhoneNumber2()));
		eProfile.setOtherPhoneNumber3(StringUtil.long2str(profile.getOtherPhoneNumber3()));
		eProfile.setSkills(gwt2hibSkillSet(profile.getSkills()));
//		eProfile.setTasks(gwt2hibTaskSet(profile.getTasks()));
		

		return eProfile;
	}

	private static UserProfile.EmployeeGender gwt2hibGender(GwtUserProfile.EmployeeGender gender) {
		if (gender == null) {
			return null;
		}
		
		return UserProfile.EmployeeGender.valueOf(gender.name());
	}

	/**
	 * Converts GWT task set to be Hibernate compatible
	 * @param gTaskThe task set to convert 
	 * @return The task set in Hibernate compatible form
	 */
	private static Set<Task> gwt2hibTaskSet(Set<GwtTask> gTask) {
		if (gTask == null)
			return null;

		Set<Task> eTask = new HashSet<Task>();

		for (GwtTask gtsk : gTask) {
			eTask.add(gwt2hibTask(gtsk));
		}

		return eTask;
	}

	/**
	 * Converts GWT availability to be Hibernate compatible
	 * @param repeats The availability to convert 
	 * @return The availability in Hibernate compatible form
	 */
	private static Availability gwt2hibAvailability(boolean[][] repeats) {
		if (repeats == null)
			return null;

		return new Availability(repeats);
	}

	/**
	 * Converts Hibernate user set to be GWT compatible
	 * @param hibUser The user set to convert 
	 * @return The user set in GWT compatible form
	 */
	private static Set<GwtUser> hib2gwtUserSet(Set<User> hibUsr) {
		if (hibUsr == null)
			return null;

		Set<GwtUser> gUser = new HashSet<GwtUser>();

		for (User usr : hibUsr) {
			gUser.add(hib2gwtUser(usr));
		}

		return gUser;
	}

	/**
	 * Converts Hibernate phone number collection to be GWT compatible
	 * @param hibCol The phone number collection to convert 
	 * @return The phone number collection in GWT compatible form
	 */
	private static Collection<String> hib2gwtContactPhoneNumberCollection(Collection<Long> hibCol) {
		if (hibCol == null)
			return null;

		Collection<String> gwtNumber = new ArrayList<String>();

		for (Long number : hibCol) {
			gwtNumber.add(number.toString());
		}

		return gwtNumber;
	}

	/**
	 * Converts Hibernate skill set to be GWT compatible
	 * @param hibSkill The skill set to convert 
	 * @return The skill set in GWT compatible form
	 */
	private static Set<GwtSkill> hib2gwtSkillSet(Set<Skill> hibSkill) {
		if (hibSkill == null)
			return null;

		Set<GwtSkill> gSkill = new HashSet<GwtSkill>();

		try {
			for (Skill skill : hibSkill) {
				gSkill.add(hib2gwtSkill(skill));
			}
		} catch (LazyInitializationException ex) {
			return null;
		}

		return gSkill;
	}

//	private static Collection<GwtRepeatStrategy> hib2gwtAvailability(Availability avail) {
//		if (avail == null)
//			return null;
//
//		return avail.getRepeats();
//	}

}
