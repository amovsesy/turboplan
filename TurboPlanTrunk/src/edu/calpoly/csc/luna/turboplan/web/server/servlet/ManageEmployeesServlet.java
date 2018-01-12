package edu.calpoly.csc.luna.turboplan.web.server.servlet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import edu.calpoly.csc.luna.turboplan.core.dao.UserDao;
import edu.calpoly.csc.luna.turboplan.core.entity.AbstractUser;
import edu.calpoly.csc.luna.turboplan.core.entity.User;
import edu.calpoly.csc.luna.turboplan.core.util.GwtUtil;
import edu.calpoly.csc.luna.turboplan.web.client.model.GwtUser;
import edu.calpoly.csc.luna.turboplan.web.client.service.ManageEmployeesService;

/**
 * ManageEmployeesServlet communicates as the Service to the database
 * 
 * @author Ming Liu
 * @author Stephanie Long
 */
@SuppressWarnings("serial")
public class ManageEmployeesServlet extends BaseTurboPlanServlet implements ManageEmployeesService {
	private static final Logger log = Logger.getLogger(ManageEmployeesServlet.class);
	private static final List<GwtUser> EMPTY_LIST = new ArrayList<GwtUser>();

	/** 
	 * @see edu.calpoly.csc.luna.turboplan.web.client.service.ManageEmployeesService#getUsers(edu.calpoly.csc.luna.turboplan.web.client.model.GwtUser)
	 */
	@Override
	public List<GwtUser> getUsers(GwtUser usr) {
		// Need to varify this user is authorized to get user
		if (!authorizeUser(usr.getUserID(), AbstractUser.Permission.ManageEmployees)) {
			return EMPTY_LIST;
		}

		List<User> list = UserDao.getInstance().getAllUsersInSameCompanyAsThisUserById(usr.getUserID());
		List<GwtUser> gwtList = new ArrayList<GwtUser>();

		for (User eUsr : list) {
			gwtList.add(eUsr.convert2GwtModel());
		}

		return gwtList;
	}
	
	/**
	 * @throws MessagingException 
	 * @see edu.calpoly.csc.luna.turboplan.web.client.service.ManageEmployeesService#addUser(edu.calpoly.csc.luna.turboplan.web.client.model.GwtUser)
	 */
	@Override
	public void addUser(GwtUser gwtUsr) {
		User usr = GwtUtil.gwt2hib(gwtUsr);
		String username = UserDao.getInstance().addUser(usr, true);
		
		try {
			new EmailServlet().sendEmail(UserDao.DEFAULT_PASSWORD, username, gwtUsr.getProfile().getEmail(), false, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		gwtUsr.setUserID(usr.getUserID());
	}

	/** 
	 * @see edu.calpoly.csc.luna.turboplan.web.client.service.ManageEmployeesService#updateUser(edu.calpoly.csc.luna.turboplan.web.client.model.GwtUser)
	 */
	@Override
	public void updateUser(GwtUser gwtUsr) {
		User usr = GwtUtil.gwt2hib(gwtUsr);
		
		UserDao.getInstance().updateUser(usr, true, true);
	}

	/** 
	 * @see edu.calpoly.csc.luna.turboplan.web.client.service.ManageEmployeesService#getUsrByID(java.lang.Long)
	 */
	@Override
	public GwtUser getUsrByID(Long Id) {
		
		return UserDao.getInstance().getUserById(Id).convert2GwtModel();
	}

	/**
	 * @see edu.calpoly.csc.luna.turboplan.web.client.service.ManageEmployeesService#deleteUser(edu.calpoly.csc.luna.turboplan.web.client.model.GwtUser)
	 */
	@Override
	public boolean deleteUser(GwtUser usr) {
		User eUser = GwtUtil.gwt2hib(usr);
		UserDao.getInstance().deleteUser(eUser);
		
		return true;
	}

	/**
	 * @see edu.calpoly.csc.luna.turboplan.web.client.service.ManageEmployeesService#getUsrByUsername(java.lang.String)
	 */
	@Override
	public GwtUser getUsrByUsername(String username) {
		
		return UserDao.getInstance().getUserByClearTextUsername(username).convert2GwtModel();
	}

	/**
	 * @see edu.calpoly.csc.luna.turboplan.web.client.service.ManageEmployeesService#getUsrByEmail(java.lang.String)
	 */
	@Override
	public GwtUser getUsrByEmail(String email) {
		
		return UserDao.getInstance().getUserEmail(email).convert2GwtModel();
	}
}
