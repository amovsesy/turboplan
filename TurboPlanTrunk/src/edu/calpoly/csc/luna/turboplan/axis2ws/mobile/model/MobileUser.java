package edu.calpoly.csc.luna.turboplan.axis2ws.mobile.model;

import edu.calpoly.csc.luna.turboplan.core.entity.User;
import edu.calpoly.csc.luna.turboplan.core.modelconverter.EntityModelConverter;
import edu.calpoly.csc.luna.turboplan.core.util.MobileUtil;

public class MobileUser implements MobileModel, EntityModelConverter<User> {
	private static final String[] EMPTY_PERMISSION = new String[] {};
	
	private long userID;

	private boolean activeStatus;
	private String[] permission = EMPTY_PERMISSION;

	public MobileUser() {
		super();
		
		activeStatus = true;
		userID = 0L;
	}
	
	public MobileUser(boolean activeStatus, String[] permission, long userID) {
		super();
		this.activeStatus = activeStatus;
		this.permission = permission;
		this.userID = userID;
	}

	public void setActiveStatus(boolean activeStatus) {
		this.activeStatus = activeStatus;
	}

	public boolean isActiveStatus() {
		return activeStatus;
	}
	
	public MobileUser(boolean active) {
		super();
		
		this.activeStatus = active;
	}

	public String[] getPermission() {
		return permission;
	}

	public void setPermission(String[] permission) {
		this.permission = permission;
	}

	public long getUserID() {
		return userID;
	}

	public void setUserID(long userID) {
		this.userID = userID;
	}

	@Override
	public User toEntityModel() {
		return MobileUtil.mobile2hib(this);
	}

	
}
