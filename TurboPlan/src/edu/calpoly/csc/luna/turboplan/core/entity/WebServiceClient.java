package edu.calpoly.csc.luna.turboplan.core.entity;

import java.util.EnumSet;

import javax.persistence.Entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@OnDelete(action = OnDeleteAction.CASCADE)
public class WebServiceClient extends AbstractUser {
	public WebServiceClient() {
		super();
	}

	public WebServiceClient(String username, String password, UserStatus activeStatus) {
		super(username, password, activeStatus);
	}

	public WebServiceClient(UserStatus activeStatus, Company company, String password, EnumSet<Permission> permission,
			String userName) {
		super(activeStatus, company, password, permission, userName);
	}

	public WebServiceClient(UserStatus activeStatus, Company company, String password, EnumSet<Permission> permission,
			Long userID, String userName) {
		super(activeStatus, company, password, permission, userID, userName);
	}

}
