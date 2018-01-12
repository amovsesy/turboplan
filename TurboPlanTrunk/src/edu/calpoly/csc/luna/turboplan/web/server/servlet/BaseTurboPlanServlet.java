package edu.calpoly.csc.luna.turboplan.web.server.servlet;

import java.util.Date;
import java.util.EnumSet;

import org.apache.log4j.Logger;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import edu.calpoly.csc.luna.turboplan.core.dao.UserDao;
import edu.calpoly.csc.luna.turboplan.core.entity.AbstractUser;
import edu.calpoly.csc.luna.turboplan.core.entity.User;
import edu.calpoly.csc.luna.turboplan.core.entity.AbstractUser.Permission;
import edu.calpoly.csc.luna.turboplan.core.entity.AbstractUser.UserStatus;
import edu.calpoly.csc.luna.turboplan.core.entity.embeddable.UserProfile;
import edu.calpoly.csc.luna.turboplan.core.security.encryption.Encryption;

@SuppressWarnings("serial")
public abstract class BaseTurboPlanServlet extends RemoteServiceServlet {
	private static final Logger log = Logger.getLogger(BaseTurboPlanServlet.class);
	
	static {
		if (UserDao.getInstance().getUserByClearTextUsername("root") == null) {
			User usr = new User(Encryption.getInstance().encrypt("root"),Encryption.getInstance().encrypt("intuit"), UserStatus.Active);
			usr.setPermission(EnumSet.noneOf(Permission.class));
			usr.getPermission().add(Permission.Root);

			usr.setProfile(new UserProfile("root", "root", new Date()));

			UserDao.getInstance().addUser(usr);
		}
	}

	protected boolean authorizeUser(Long id, AbstractUser.Permission perm) {
		StringBuilder authLog = new StringBuilder("Authorizing user[id=");
		authLog.append(id).append("]");
		authLog.append(" for [permission=").append(perm.name()).append("] permission"); 
		log.debug(authLog);
		
		boolean auth = UserDao.getInstance().getUserById(id).getPermission().contains(perm);
		
		if (auth) {
			StringBuilder logBuilder = new StringBuilder("User [id="); 
			logBuilder.append(id).append("] authorized for [").append(perm.name()).append("]");
			
			log.trace(logBuilder);
		} else {
			StringBuilder logBuilder = new StringBuilder("User[id=");
			logBuilder.append(id).append("] denied of [permission=").append(perm.name()).append("]");
			log.trace(logBuilder);
		}
		
		return auth;
	}
}
