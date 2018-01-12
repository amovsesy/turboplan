package edu.calpoly.csc.luna.turboplan.core;

import java.util.Date;
import java.util.EnumSet;
import java.util.Set;

import edu.calpoly.csc.luna.turboplan.core.entity.Company;
import edu.calpoly.csc.luna.turboplan.core.entity.Skill;
import edu.calpoly.csc.luna.turboplan.core.entity.Task;
import edu.calpoly.csc.luna.turboplan.core.entity.User;
import edu.calpoly.csc.luna.turboplan.core.entity.AbstractUser.Permission;
import edu.calpoly.csc.luna.turboplan.core.entity.AbstractUser.UserStatus;
import edu.calpoly.csc.luna.turboplan.core.entity.Task.TaskPriority;
import edu.calpoly.csc.luna.turboplan.core.entity.Task.TaskStatus;
import edu.calpoly.csc.luna.turboplan.core.entity.embeddable.Address;
import edu.calpoly.csc.luna.turboplan.core.entity.embeddable.UserProfile;
import edu.calpoly.csc.luna.turboplan.core.security.encryption.Encryption;

public class EntityBuilder {
	private static final String password = "LunaSet";
	private static final String compAddress = "CalPoly, SE";
	private static int changeAddress = 0;
	
	public static User buildUser(String username, String firstname, String lastname, String email, UserProfile.EmployeeGender gender, Company company, boolean manager) {
		User usr = new User(Encryption.getInstance().encrypt(username), Encryption.getInstance().encrypt(password), UserStatus.Active);
		
		usr.setProfile(buildProfile(firstname, lastname, email, gender));
		usr.setPermission(buildPermission(manager));
		
		return usr;
	}
	
	private static EnumSet<Permission> buildPermission(boolean manager) {
		if (manager) {
			return EnumSet.allOf(Permission.class);
		} else {
			EnumSet<Permission> ret = EnumSet.noneOf(Permission.class);
			
			ret.add(Permission.MySchedule);
			ret.add(Permission.Preferences); 
			
			return ret;
		}
	}

	private static UserProfile buildProfile(String firstname, String lastname, String email, UserProfile.EmployeeGender gender) {
		UserProfile profile = new UserProfile(firstname, lastname, new Date());
		
		profile.setEmail(email);
		profile.setGender(gender);
		
		return profile;
	}
	
	public static Company buildCompany(String name) {
		return new Company(name, compAddress);
	}
	
	public static Task buildTask(Set<Skill> skills, String title, Date suggestedStart, Date suggestedEnd, Double estimate, Company company) {
		
		String street;
		String city;
		String state = "California";
		int zip;
		
		if((changeAddress%5) == 0) {
			street = "5424 Geary Blvd";
			city = "San Francisco";
			zip = 94121;
		} else if ((changeAddress%4) == 0) {
			street = "6111 San Ignacio Avenue ";
			city = "San Jose";
			zip = 95119;
		} else if ((changeAddress%3) == 0) {
			street = "100 Madonna Road";
			city = "San Luis Obispo";
			zip = 93405;
		} else if ((changeAddress%2) == 0) {
			street = "1313 S Harbor Blvd";
			city = "Anaheim";
			zip = 92802;
		} else {
			street = "120 Soledad St";
			city = "Salinas";
			zip = 93901;
		}
		changeAddress++;
		
		return new Task(
				TaskStatus.New, 
				title, 
				"desc", 
				new Address(street, city, state, zip), 
				"Joe", "Smith", "12345", "12345", 
				new Date(), 
				suggestedStart, 
				suggestedEnd, 
				estimate, 
				TaskPriority.HIGH, 
				company
		); 
	}
	
    public static Task buildTask2(Set<Skill> skills, String title, Date suggestedStart, Date suggestedEnd, Double estimate, Company company) {
        return new Task(
        		TaskStatus.New, 
        		title, 
        		"desc", 
        		new Address("sample street", "sample city", "sample state", 12345),
        		"Jane", 
        		"Smith", 
        		"12345", 
        		"12345", 
        		new Date(), 
        		suggestedStart, 
        		suggestedEnd, 
        		estimate, 
        		TaskPriority.LOW, 
        		company
        );
    }
	
}
