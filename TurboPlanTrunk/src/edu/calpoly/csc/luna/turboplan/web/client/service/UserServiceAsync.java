package edu.calpoly.csc.luna.turboplan.web.client.service;

import java.util.Set;

import com.google.gwt.user.client.rpc.AsyncCallback;

import edu.calpoly.csc.luna.turboplan.web.client.model.GwtTask;
import edu.calpoly.csc.luna.turboplan.web.client.model.GwtUser;

public interface UserServiceAsync extends BaseServiceAsync {
	public void authenticate(String username, String password, AsyncCallback<GwtUser> valid);

	public void getTasksForUser(Long userID, AsyncCallback<Set<GwtTask>> asyncCallback);
	
	public void updateUser(GwtUser usr, AsyncCallback<Object> asyncCallback);
	
	public void updateUserPassword(Long id, String password, AsyncCallback<Object> callback);
	
	public void verifyUserPassword(Long id, String password, AsyncCallback<Boolean> callback);
}
