package edu.calpoly.csc.luna.turboplan.web.server.servlet;

import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import edu.calpoly.csc.luna.turboplan.core.dao.UserDao;
import edu.calpoly.csc.luna.turboplan.core.entity.Task;
import edu.calpoly.csc.luna.turboplan.core.entity.User;
import edu.calpoly.csc.luna.turboplan.core.entity.embeddable.UserProfile;
import edu.calpoly.csc.luna.turboplan.core.security.Authentication;
import edu.calpoly.csc.luna.turboplan.core.util.GwtUtil;
import edu.calpoly.csc.luna.turboplan.web.client.model.GwtTask;
import edu.calpoly.csc.luna.turboplan.web.client.model.GwtUser;
import edu.calpoly.csc.luna.turboplan.web.client.service.UserService;

public class UserServlet extends BaseTurboPlanServlet implements UserService {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8662756156949540451L;
	private static final Logger log = Logger.getLogger(UserServlet.class);

	@Override
	public GwtUser authenticate(String username, String password) {
		log.trace("Recieved request for authentication");
		User usr = Authentication.authenticate(username, password);

		if (usr == null) {
			return null;
		} else {
			return usr.convert2GwtModel();
		}
	}

//	@Override
//	public GwtUserProfile getUserProfileForUser(GwtUser usr) {
//		User user = GwtUtil.gwt2hib(usr);
//		UserProfile eProfile = UserProfileDao.getInstance().getProfileByUser(user);
//
//		return GwtUtil.hib2gwtUserProfile(eProfile);
//	}

	@Override
	public Set<GwtTask> getTasksForUser(Long userID) {
		List<Task> task = UserDao.getInstance().getTasksByUserId(userID);

		return GwtUtil.hib2gwtTaskSet(task);
	}

//	@Override
//	public void updateUserProfile(GwtUserProfile profile) {
//		log.trace("Entering updateUserProfile");
//		System.out.println(profile.getAvailability());
//		UserProfile eProfile = GwtUtil.gwt2hibUserProfile(profile);
//		System.out.println(eProfile.getAvailability());
//		UserProfileDao.getInstance().updateUserProfile(eProfile);
//	}

	private String printboolean(boolean[][] arr) {
		StringBuilder builder = new StringBuilder();

		for (int i = 0; i < 7; i++) {
			switch (i) {
			case 0:
				builder.append("Sunday    ");
				break;
			case 1:
				builder.append("Monday    ");
				break;
			case 2:
				builder.append("Tuesday   ");
				break;
			case 3:
				builder.append("Wednesday ");
				break;
			case 4:
				builder.append("Thursday  ");
				break;
			case 5:
				builder.append("Friday    ");
				break;
			case 6:
				builder.append("Saturday  ");
				break;
			default:
				break;
			}

			for (int j = 0; j < 48; j++) {
				builder.append("|");
				if (arr[i][j]) {
					builder.append("1");
				} else {
					builder.append("0");
				}
			}
			builder.append("\n");
		}

		return builder.toString();
	}

	@Override
	public void updateUser(GwtUser usr) {
		User eUser = GwtUtil.gwt2hib(usr);
		
		UserDao.getInstance().updateUser(eUser, true, true);
	}

	@Override
	public void updateUserPassword(Long id, String password) {
		log.trace("Updating password for user id = " + id + ": " + password);
		
		User usr = UserDao.getInstance().getUserById(id);
		log.trace(usr);
		UserProfile profile = usr.getProfile();
		log.trace(profile);
		new EmailServlet().sendEmailChangePass(password, profile.getEmail());
		//.sendEmail(password, "changing", profile.getEmail(), true, false);
		log.trace("email sent");
		UserDao.getInstance().updateClearTextPasswordForUserById(id, password);
		log.trace("updated password");
	}
	
	@Override
	public Boolean verifyUserPassword(Long id, String password) {
		log.trace("Verify password for user id = " + id + ": " + password);
				
		return UserDao.getInstance().correctPassForUserId(id, password);
	}
}
