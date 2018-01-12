package edu.calpoly.csc.luna.turboplan.core.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import edu.calpoly.csc.luna.turboplan.axis2ws.mobile.model.MobileCompany;
import edu.calpoly.csc.luna.turboplan.axis2ws.mobile.model.MobileModel;
import edu.calpoly.csc.luna.turboplan.axis2ws.mobile.model.MobileTask;
import edu.calpoly.csc.luna.turboplan.axis2ws.mobile.model.MobileUser;
import edu.calpoly.csc.luna.turboplan.core.entity.AbstractUser;
import edu.calpoly.csc.luna.turboplan.core.entity.BaseEntity;
import edu.calpoly.csc.luna.turboplan.core.entity.Company;
import edu.calpoly.csc.luna.turboplan.core.entity.Task;
import edu.calpoly.csc.luna.turboplan.core.entity.User;
import edu.calpoly.csc.luna.turboplan.core.entity.Task.TaskStatus;
import edu.calpoly.csc.luna.turboplan.core.entity.embeddable.Address;
import edu.calpoly.csc.luna.turboplan.core.modelconverter.MobileModelConverter;

/**
 * Used to convert objects to be GWT and Hibernate compatible
 * 
 * @author Ming Liu
 * @author Stephanie Long
 * 
 */
@SuppressWarnings("unused")
public class MobileUtil {
	private static final Logger log = Logger.getLogger(MobileUtil.class);

	/**
	 * Converts Hibernate objects to be mobile compatible
	 * 
	 * @param <T1>
	 *            mobile entity
	 * @param <T2>
	 *            TurboPlanDatabaseEntity
	 * @param entity
	 *            TurboPlan converted
	 * @return mobile converted entity
	 */
	@SuppressWarnings("unchecked")
	public static <M extends MobileModel, H extends MobileModelConverter<M>> M hib2mobile(H entity) {
		if (entity instanceof Company) {
			return (M) hib2mobileCompany((Company) entity);
		} else if (entity instanceof Task) {
			return (M) hib2mobileTask((Task) entity);
		} else if (entity instanceof User) {
			return (M) hib2mobileUser((User) entity);
		} else {
			return null;
		}
	}

	/**
	 * Converts Mobile objects to be Hibernate compatible
	 * 
	 * @param <T1>
	 *            TurboPlanDatabaseEntity
	 * @param <T2>
	 *            Mobile entity
	 * @param mobileModel
	 *            Model mobile converted
	 * @return Hibernate converted entity
	 */
	@SuppressWarnings("unchecked")
	public static <M extends MobileModel, H extends MobileModelConverter<M>> H mobile2hib(M mobileModel) {
		if (mobileModel instanceof MobileCompany) {
			return (H) mobile2hibCompany((MobileCompany) mobileModel);
		} else if (mobileModel instanceof MobileTask) {
			return (H) mobile2hibTask((MobileTask) mobileModel);
		} else if (mobileModel instanceof MobileUser) {
			return (H) mobile2hibUser((MobileUser) mobileModel);
		} else {
			return null;
		}
	}

	/**
	 * Converts a Mobile user profile to be Hibernate compatible
	 * 
	 * @param mobileModel
	 *            The user profile to convert
	 * @return The user profile in Hibernate compatible form
	 */
	/*
	 * private static UserProfile mobile2hibUserProfile(MobileUserProfile
	 * mobileModel) { if (mobileModel == null) return null;
	 * 
	 * UserProfile profile = new UserProfile();
	 * 
	 * profile.setAddress(mobile2hibAddress(mobileModel.getAddress())); //
	 * profile.setAvailability(mobile2hibTimeIntervalCollection(mobileModel. //
	 * getAvailability())); profile.setDateOfBirth(new
	 * Date(mobileModel.getDateOfBirth()));
	 * profile.setEmail(mobileModel.getEmail());
	 * profile.setEmail2(mobileModel.getEmail2());
	 * profile.setEmail3(mobileModel.getEmail3());
	 * profile.setEmail4(mobileModel.getEmail4());
	 * profile.setEmail5(mobileModel.getEmail5());
	 * profile.setFirstName(mobileModel.getFirstName());
	 * profile.setGender(EmployeeGender.valueOf(mobileModel.getGender()));
	 * profile
	 * .setHomePhoneNumber(String.valueOf(mobileModel.getHomePhoneNumber()));
	 * profile.setLastName(mobileModel.getLastName());
	 * profile.setMiddleInitial(mobileModel.getMiddleInitial());
	 * profile.setMobilePhoneNumber
	 * (String.valueOf(mobileModel.getMobilePhoneNumber()));
	 * profile.setOtherPhoneNumber
	 * (String.valueOf(mobileModel.getOtherPhoneNumber()));
	 * profile.setOtherPhoneNumber2
	 * (String.valueOf(mobileModel.getOtherPhoneNumber2()));
	 * profile.setOtherPhoneNumber3
	 * (String.valueOf(mobileModel.getOtherPhoneNumber3()));
	 * profile.setSkills(mobile2hibSkillSet(mobileModel.getSkills()));
	 * 
	 * return profile; }
	 */

	/**
	 * Converts a Mobile task set to be Hibernate compatible
	 * 
	 * @param tasks
	 *            The task set to convert
	 * @return The task set in Hibernate compatible form
	 */
	/*
	 * private static Set<Task> mobile2hibTaskSet(MobileTask[] tasks) { if
	 * (tasks == null) return null;
	 * 
	 * Set<Task> eTask = new HashSet<Task>();
	 * 
	 * for (MobileTask mTask : tasks) { eTask.add(mobile2hibTask(mTask)); }
	 * 
	 * return eTask; }
	 */

	/**
	 * Converts a Mobile skill set to be Hibernate compatible
	 * 
	 * @param skills
	 *            The skill set to convert
	 * @return The skill set in Hibernate compatible form
	 */
	/*
	 * private static Set<Skill> mobile2hibSkillSet(MobileSkill[] skills) { if
	 * (skills == null) return null;
	 * 
	 * Set<Skill> eSkill = new HashSet<Skill>();
	 * 
	 * for (MobileSkill mSkill : skills) { eSkill.add(mobile2hibSkill(mSkill));
	 * }
	 * 
	 * return eSkill; }
	 */

	/**
	 * Converts a Mobile availability to be Hibernate compatible
	 * 
	 * @param availability
	 *            The availability to convert
	 * @return The availability in Hibernate compatible form
	 */
	/*
	 * private static Collection<TimeInterval>
	 * mobile2hibTimeIntervalCollection(Collection<MobileTimeInterval>
	 * availability) { if (availability == null) return null;
	 * 
	 * Collection<TimeInterval> eInt = new ArrayList<TimeInterval>();
	 * 
	 * for (MobileTimeInterval interval : availability) {
	 * eInt.add(mobile2hibTimeInterval(interval)); }
	 * 
	 * return eInt; }
	 */

	/**
	 * Converts a Mobile time interval to be Hibernate compatible
	 * 
	 * @param interval
	 *            The time interval to convert
	 * @return The time interval in Hibernate compatible form
	 */
	/*
	 * private static TimeInterval mobile2hibTimeInterval(MobileTimeInterval
	 * interval) { if (interval == null) return null;
	 * 
	 * return new TimeInterval(new Date(interval.getStart()), new
	 * Date(interval.getEnd())); }
	 */

	/**
	 * Converts a Mobile address to be Hibernate compatible
	 * 
	 * @param address
	 *            The address to convert
	 * @return The address in Hibernate compatible form
	 */
	private static Address mobile2hibAddress(String[] address) {
		if (address == null || address.length < 5) {
			return null;
		}

		return new Address(address[0], address[1], address[2], address[3], Integer.valueOf(address[4]));
	}

	/**
	 * Converts a Mobile user to be Hibernate compatible
	 * 
	 * @param mobileModel
	 *            The user to convert
	 * @return The user in Hibernate compatible form
	 */
	private static User mobile2hibUser(MobileUser mobileModel) {
		if (mobileModel == null)
			return null;

		User usr = new User();

		usr.setUserID(mobileModel.getUserID());
		// usr.setProfile(mobile2hibUserProfile(mobileModel.getProfile()));
		usr.setPermission(mobile2hibPermission(mobileModel.getPermission()));

		return usr;
	}

	/**
	 * Converts a Mobile permission to be Hibernate compatible
	 * 
	 * @param permission
	 *            The permission to convert
	 * @return The permission in Hibernate compatible form
	 */
	private static EnumSet<AbstractUser.Permission> mobile2hibPermission(String[] permission) {
		EnumSet<AbstractUser.Permission> set = EnumSet.noneOf(AbstractUser.Permission.class);

		for (String str : permission) {
			set.add(AbstractUser.Permission.valueOf(str));
		}

		return set;
	}

	/**
	 * Converts a Mobile task to be Hibernate compatible
	 * 
	 * @param mobileModel
	 *            The task to convert
	 * @return The task in Hibernate compatible form
	 */
	private static Task mobile2hibTask(MobileTask mobileModel) {
		if (mobileModel == null)
			return null;

		Task eTask = new Task();

		eTask.setCustomerFirstName(mobileModel.getContactFirstName());
		eTask.setCustomerLastName(mobileModel.getContactLastName());
		eTask.setCreatedDate(new Date(mobileModel.getCreatedDate()));
		eTask.setLocation(mobile2hibAddress(mobileModel.getLocation()));
		eTask.setNotes(mobileModel.getNotes());
		eTask.setHomeNum(mobileModel.getContactHomePhone());
		eTask.setMobileNum(mobileModel.getContactMobilePhone());
		eTask.setScheduledEndTime(new Date(mobileModel.getScheduledEndTime()));
		eTask.setScheduledStartTime(new Date(mobileModel.getScheduledStartTime()));
		// eTask.setSuggestedEndTime(new
		// Date(mobileModel.getSuggestedEndTime()));
		// eTask.setSuggestedStartTime(new
		// Date(mobileModel.getSuggestedStartTime()));

		//eTask.setRequiredSkill(mobile2hibSkillSet(mobileModel.getRequiredSkill
		// ()));
		eTask.setStatus(mobile2hibTaskStatus(mobileModel.getStatus()));
		eTask.setUsers(mobile2hibUserSet(mobileModel.getUsers()));
		eTask.setTaskID(mobileModel.getTaskID());
		eTask.setTimespent(new Double(mobileModel.getTimespent()) < 0 ? null : new Double(mobileModel.getTimespent()));

		return eTask;
	}

	private static Task.TaskStatus mobile2hibTaskStatus(String mobileModel) {
		return mobileModel == null || mobileModel.isEmpty() ? null : Task.TaskStatus.valueOf(mobileModel);
	}

	/**
	 * Converts a Mobile phone numbers to be Hibernate compatible
	 * 
	 * @param phoneNumbers
	 *            The phone numbers to convert
	 * @return The phone numbers in Hibernate compatible form
	 */
	private static Collection<Long> mobile2hibPhoneNumberCollection(String[] phoneNumbers) {
		if (phoneNumbers == null)
			return null;

		Collection<Long> hibPhones = new ArrayList<Long>();

		for (String num : phoneNumbers) {
			hibPhones.add(Long.valueOf(num));
		}

		return hibPhones;
	}

	/**
	 * Converts a Mobile skill to be Hibernate compatible
	 * 
	 * @param mobileModel
	 *            The skill to convert
	 * @return The skill in Hibernate compatible form
	 */
	/*
	 * private static Skill mobile2hibSkill(MobileSkill mobileModel) { if
	 * (mobileModel == null) return null;
	 * 
	 * Skill skill = new Skill(mobileModel.getTitle());
	 * 
	 * skill.setId(mobileModel.getSkillID());
	 * skill.setDescription(mobileModel.getDescription());
	 * 
	 * return skill; }
	 */

	/**
	 * Converts a Mobile company to be Hibernate compatible
	 * 
	 * @param company
	 *            The company to convert
	 * @return The company in Hibernate compatible form
	 */

	private static Company mobile2hibCompany(MobileCompany mobileModel) {
		if (mobileModel == null)
			return null;

		Company company = new Company(mobileModel.getName(), mobileModel.getAddress());

		company.setCompanyID(mobileModel.getCompanyID());
		company.setAddress(mobileModel.getAddress());
		company.setName(mobileModel.getName());

		return company;
	}

	/**
	 * Converts a Mobile set of users to be Hibernate compatible
	 * 
	 * @param users
	 *            The user set to convert
	 * @return The user set in Hibernate compatible form
	 */
	private static Set<User> mobile2hibUserSet(MobileUser[] users) {
		if (users == null)
			return null;

		Set<User> eUsers = new HashSet<User>();

		for (MobileUser usr : users) {
			eUsers.add(mobile2hibUser(usr));
		}

		return eUsers;
	}

	/**
	 * Converts a Hibernate user profile to be mobile compatible
	 * 
	 * @param entity
	 *            The user profile to convert
	 * @return The user profile in mobile compatible form
	 */
	/*
	 * private static MobileUserProfile hib2mobileUserProfile(UserProfile
	 * entity) { if (entity == null) return null;
	 * 
	 * MobileUserProfile mProfile = new MobileUserProfile();
	 * 
	 * mProfile.setFirstName(entity.getFirstName());
	 * mProfile.setMiddleInitial(entity.getMiddleInitial());
	 * mProfile.setDateOfBirth(entity.getDateOfBirth().getTime());
	 * 
	 * // TODO: one of these lines gives null pointer exception //
	 * mProfile.setHomePhoneNumber(entity.getHomePhoneNumber() == null ? // null
	 * : entity.getHomePhoneNumber().longValue()); //
	 * mProfile.setMobilePhoneNumber(entity.getMobilePhoneNumber() == null ? //
	 * null : entity.getMobilePhoneNumber().longValue()); //
	 * mProfile.setOtherPhoneNumber(entity.getOtherPhoneNumber() == null ? //
	 * null : entity.getOtherPhoneNumber().longValue()); //
	 * mProfile.setOtherPhoneNumber2(entity.getOtherPhoneNumber2() == null ? //
	 * null : entity.getOtherPhoneNumber2().longValue()); //
	 * mProfile.setOtherPhoneNumber3(entity.getOtherPhoneNumber3() == null ? //
	 * null : entity.getOtherPhoneNumber3().longValue());
	 * mProfile.setEmail(entity.getEmail());
	 * mProfile.setEmail2(entity.getEmail2());
	 * mProfile.setEmail3(entity.getEmail3());
	 * mProfile.setEmail4(entity.getEmail4());
	 * mProfile.setEmail5(entity.getEmail5());
	 * mProfile.setGender(entity.getGender() == null ? null :
	 * entity.getGender().name());
	 * mProfile.setAddress(hib2mobileAddress(entity.getAddress())); //
	 * mProfile.setAvailability(hib2mobileTimeIntervalCollection(entity. //
	 * getAvailability()));
	 * mProfile.setSkills(hib2mobileSkillSet(entity.getSkills()));
	 * 
	 * return mProfile; }
	 */

	/**
	 * Converts a Hibernate skill set to be mobile compatible
	 * 
	 * @param skills
	 *            The skill set to convert
	 * @return The skills set in mobile compatible form
	 */
	/*
	 * private static MobileSkill[] hib2mobileSkillSet(Set<Skill> skills) { if
	 * (skills == null) return null;
	 * 
	 * return skills.toArray(new MobileSkill[skills.size()]); }
	 */

	/**
	 * Converts a Hibernate availability to be mobile compatible
	 * 
	 * @param eAvailability
	 *            The availability to convert
	 * @return The availability in mobile compatible form
	 */
	/*
	 * private static Collection<MobileTimeInterval>
	 * hib2mobileTimeIntervalCollection( Collection<TimeInterval> eAvailability)
	 * { if (eAvailability == null) return null;
	 * 
	 * Collection<MobileTimeInterval> mAvailability = new
	 * ArrayList<MobileTimeInterval>();
	 * 
	 * for (TimeInterval interval : eAvailability) {
	 * mAvailability.add(hib2mobileTimeInterval(interval)); }
	 * 
	 * return mAvailability; }
	 */

	/**
	 * Converts a Hibernate user profile to be mobile compatible
	 * 
	 * @param entity
	 *            The user profile to convert
	 * @return The user profile in mobile compatible form
	 */
	/*
	 * private static MobileTimeInterval hib2mobileTimeInterval(TimeInterval
	 * interval) { if (interval == null) return null; return new
	 * MobileTimeInterval(interval.getStart().getTime(),
	 * interval.getEnd().getTime()); }
	 */

	/**
	 * Converts a Hibernate address to be mobile compatible
	 * 
	 * @param address
	 *            The address to convert
	 * @return The address in mobile compatible form
	 */
	private static String[] hib2mobileAddress(Address address) {
		if (address == null)
			return null;

		return new String[] { address.getAddress(), address.getCity(), address.getCountry(), address.getState(),
				String.valueOf(address.getZip()) };
	}

	/**
	 * Converts a Hibernate user to be mobile compatible
	 * 
	 * @param entity
	 *            The user to convert
	 * @return The user in mobile compatible form
	 */
	private static MobileUser hib2mobileUser(User entity) {
		if (entity == null)
			return null;

		MobileUser mUser = new MobileUser();

		mUser.setUserID(entity.getUserID() == null ? -1L : entity.getUserID());
		mUser.setActiveStatus(AbstractUser.UserStatus.Active.equals(entity.isActive()));
		mUser.setPermission(hib2mobilePermission(entity.getPermission()));

		return mUser;
	}

	/**
	 * Converts a Hibernate permission set to be mobile compatible
	 * 
	 * @param entity
	 *            The permisison set to convert
	 * @return The upermission set in mobile compatible form
	 */
	private static String[] hib2mobilePermission(EnumSet<AbstractUser.Permission> permission) {
		if (permission == null)
			return null;

		ArrayList<String> mPerm = new ArrayList<String>();

		for (AbstractUser.Permission perm : permission) {
			mPerm.add(perm.name());
		}

		return mPerm.toArray(new String[mPerm.size()]);
	}

	/**
	 * Converts a Hibernate task to be mobile compatible
	 * 
	 * @param entity
	 *            The task to convert
	 * @return The task in mobile compatible form
	 */
	private static MobileTask hib2mobileTask(Task entity) {
		if (entity == null)
			return null;

		MobileTask mTask = new MobileTask();

		mTask.setContactFirstName(entity.getCustomerFirstName());
		mTask.setContactLastName(entity.getCustomerLastName());
		mTask.setCreatedDate(hib2mobileDate(entity.getCreatedDate()));
		mTask.setDescription(entity.getDescription());
		mTask.setLocation(hib2mobileAddress(entity.getLocation()));
		mTask.setNotes(entity.getNotes());
		mTask.setContactHomePhone(entity.getHomeNum());
		mTask.setContactMobilePhone(entity.getMobileNum());
		mTask.setScheduledEndTime(hib2mobileDate(entity.getScheduledEndTime()));
		mTask.setScheduledStartTime(hib2mobileDate(entity.getScheduledStartTime()));
		mTask.setStatus(hib2mobileTaskStatus(entity.getStatus()));
		mTask.setTaskID(entity.getTaskID() == null ? -1L : entity.getTaskID());
		mTask.setTitle(entity.getTitle());
		mTask.setUsers(hib2mobileCollection(entity.getUsers()));
		mTask.setTimespent(new Long(entity.getTimespent().toString()) == null ? -1L : new Long(entity.getTimespent().toString()));

		return mTask;
	}

	private static String hib2mobileTaskStatus(Task.TaskStatus entity) {
		return entity == null ? null : entity.name();
	}

	private static long hib2mobileDate(Date entity) {
		return entity == null ? -1L : entity.getTime();
	}

	/**
	 * Converts a Hibernate phone number collection to be mobile compatible
	 * 
	 * @param entity
	 *            The phone number collection to convert
	 * @return The phone number collection in mobile compatible form
	 */
	private static String[] hib2mobilePhoneNumberCollection(Collection<Long> phoneNumbers) {
		if (phoneNumbers == null)
			return null;

		String[] ret = new String[phoneNumbers.size()];

		int index = 0;
		for (Long num : phoneNumbers) {
			ret[index++] = num.toString();
		}

		return ret;
	}

	/**
	 * Converts a Hibernate skill to be mobile compatible
	 * 
	 * @param entity
	 *            The skill to convert
	 * @return The skill in mobile compatible form
	 */
	/*
	 * private static MobileSkill hib2mobileSkill(Skill entity) { if (entity ==
	 * null) return null; return new MobileSkill(entity.getId(),
	 * entity.getTitle(), entity.getDescription()); }
	 */

	/**
	 * Converts a Hibernate company to be mobile compatible
	 * 
	 * @param entity
	 *            The company to convert
	 * @return The company in mobile compatible form
	 */
	private static MobileCompany hib2mobileCompany(Company entity) {
		if (entity == null)
			return null;

		MobileCompany ret = new MobileCompany(entity.getName(), entity.getAddress());
		ret.setCompanyID(entity.getCompanyID());

		return ret;
	}

	@SuppressWarnings("unchecked")
	public static <M extends MobileModel, H extends MobileModelConverter<M>> M[] hib2mobileCollection(Collection<H> hib) {
		if (hib == null) {
			return null;
		}

		Collection<M> mCol = new ArrayList<M>();

		for (H mobile : hib) {
			mCol.add(mobile.convert2MobileModel());
		}

		Class<? extends BaseEntity> entityClazz = (Class<BaseEntity>) ReflectionUtil.getCollectionContentType(hib);
		return (M[]) mCol.toArray((M[]) java.lang.reflect.Array.newInstance(hib2mobileClass(entityClazz), hib.size()));
	}

	public static <M extends MobileModel, H extends MobileModelConverter<M>, C extends Collection<MobileModelConverter<M>>> C mobile2hibCollection(
			M[] mobileModel, Class<C> clazz) {
		C ret = null;

		try {
			ret = clazz.newInstance();
		} catch (InstantiationException e) {
			StringBuilder logBuilder = new StringBuilder("Unable to instanciate class: ");
			logBuilder.append(clazz.getName());
			
			log.warn(logBuilder, e);
		} catch (IllegalAccessException e) {
			StringBuilder logBuilder = new StringBuilder("Unable to instanciate class: ");
			logBuilder.append(clazz.getName());
			
			log.warn(logBuilder, e);
		}

		for (M item : mobileModel) {
			ret.add(mobile2hib(item));
		}

		return ret;
	}

	private static Class<? extends MobileModel> hib2mobileClass(Class<? extends BaseEntity> clazz) {
		if (clazz == null) {
			return null;
		} else if (Task.class.equals(clazz)) {
			return MobileTask.class;
		} else if (Company.class.equals(clazz)) {
			return MobileCompany.class;
		} else if (User.class.equals(clazz)) {
			return MobileUser.class;
		} else {
			throw new NoModelCounterpartExistException("No MobileModel counter part exist for: " + clazz.getName());
		}
	}

	private static class NoModelCounterpartExistException extends RuntimeException {
		private static final long serialVersionUID = -2618284916550673721L;

		private NoModelCounterpartExistException(String msg) {
			super(msg);
		}
	}

}
