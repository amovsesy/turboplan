package edu.calpoly.csc.luna.turboplan.web.client.service;

import java.util.Set;

import com.google.gwt.user.client.rpc.AsyncCallback;

import edu.calpoly.csc.luna.turboplan.web.client.model.GwtTask;
import edu.calpoly.csc.luna.turboplan.web.client.model.GwtUser;

public interface UserService extends BaseService {
	public GwtUser authenticate(String username, String password);

	public Set<GwtTask> getTasksForUser(Long userID);
	
	public void updateUser(GwtUser usr);
	
	public void updateUserPassword(Long id, String password);
	
	public Boolean verifyUserPassword(Long id, String password);
}
