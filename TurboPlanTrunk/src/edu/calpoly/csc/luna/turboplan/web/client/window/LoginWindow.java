package edu.calpoly.csc.luna.turboplan.web.client.window;

import java.util.Date;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Widget;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.core.Position;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.MessageBox;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.form.FormPanel;
import com.gwtext.client.widgets.form.TextField;

import edu.calpoly.csc.luna.turboplan.web.client.TurboPlan;
import edu.calpoly.csc.luna.turboplan.web.client.model.GwtCompany;
import edu.calpoly.csc.luna.turboplan.web.client.model.GwtUser;
import edu.calpoly.csc.luna.turboplan.web.client.model.GwtUserProfile;
import edu.calpoly.csc.luna.turboplan.web.client.page.TurboPlanPage;
import edu.calpoly.csc.luna.turboplan.web.client.service.ServiceManager;
import edu.calpoly.csc.luna.turboplan.web.client.widget.CompanyPanel;

/**
* This class is for the login page.
* 		
*/
public class LoginWindow extends BaseWindow {
	private static final int FORM_LABEL_WIDTH = 60;
	private static final int WINDOW_WIDTH = 250;
	private static final LoginWindow singleton = new LoginWindow();

	/**
	 * This method returns the instance of LoginWindow
	 * 
	 * @return the instance of LoginWindow
	 */
	public static LoginWindow getInstance() {
		return singleton;
	}

	/**
	 * Creates the window and sets all the button events
	 * 
	 */
	private LoginWindow() {
		FormPanel fPanel = new FormPanel(Position.LEFT);
		fPanel.setFrame(true);
		fPanel.setButtonAlign(Position.RIGHT);
		fPanel.setLabelWidth(FORM_LABEL_WIDTH);
		fPanel.setAutoWidth(true);
		fPanel.setAutoHeight(true);

		final TextField username = new TextField("Username");
		final TextField password = new TextField("Password");

		Hyperlink forgotPass = new Hyperlink("Forgot Username/Password", "");
		forgotPass.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				Log.debug("Forgot Username/Password Hyperlink clicked");
				LoginWindow.getInstance().hide();
				ForgotUsernamePassWindow.getInstance().show();
			}
		});

		password.setPassword(true);
		password.setWidth(WINDOW_WIDTH - FORM_LABEL_WIDTH - 35);
		username.setWidth(WINDOW_WIDTH - FORM_LABEL_WIDTH - 35);

		fPanel.add(username);
		fPanel.add(password);
		fPanel.add(forgotPass);

		fPanel.addButton(new Button("Login", new ButtonListenerAdapter() {
			public void onClick(Button button, EventObject e) {
				Log.debug("Login button clicked");
				String unamePlain = username.getValueAsString();
				String passPlain = password.getValueAsString();
				Log.trace("Attempting to authenticate " + unamePlain + "/" + passPlain);

				ServiceManager.getUserServiceAsync().authenticate(username.getText(), password.getText(), new AsyncCallback<GwtUser>() {
					public void onFailure(Throwable caught) {
						Log.error("Call to authenticate", caught);
						MessageBox.alert(caught.getMessage());
						TurboPlan.loggedInUser = null;
						username.setValue("");
						password.setValue("");
					}

					public void onSuccess(GwtUser result) {
						Log.trace("Call to authenticate user succeeded");
						if(result != null && result.getPermission().contains(GwtUser.Permission.Root.name())) {
							Log.debug("Root User");
							singleton.hide();
							TurboPlan.loggedInUser = result;
							username.setValue("");
							password.setValue("");
							CompanyCreationWindow.getInstance().show();
						}
						else {
							if (result != null) {
								Log.debug("User authenticated");
								TurboPlan.loggedInUser = result;
							
								singleton.hide();
								username.setValue("");
								password.setValue("");
								TurboPlan.buildTurboPlanUI();
								TurboPlan.swapToPage(TurboPlanPage.MySchedule);
							} else {
								Log.debug("User failed to authenticate");
								MessageBox.alert("The username and/or password was incorrect!");
								username.setValue("");
								password.setValue("");
								TurboPlan.loggedInUser = null;
							}
						}
					}

				});
			}
		}));

		setTitle(TurboPlan.I18N_CONSTANT.login());
		setWidth(WINDOW_WIDTH);
		setResizable(false);
		setClosable(false);

		add(fPanel);
	}
	
	/**
	 * Overrides the show method to ass a log
	 * 
	 */
	public void show() {
		Log.debug("LoginWindow show");
		
		super.show();
	}
}
