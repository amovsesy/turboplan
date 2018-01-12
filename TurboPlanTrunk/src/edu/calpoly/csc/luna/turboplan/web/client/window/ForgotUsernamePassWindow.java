package edu.calpoly.csc.luna.turboplan.web.client.window;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.core.Position;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.MessageBox;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.form.FormPanel;
import com.gwtext.client.widgets.form.TextField;

import edu.calpoly.csc.luna.turboplan.web.client.model.GwtUser;
import edu.calpoly.csc.luna.turboplan.web.client.service.ServiceManager;

/**
 * This class is for the forgot username/password page.
 * 		
 */
public class ForgotUsernamePassWindow extends BaseWindow {
	
	/**
	 * This class is to allow the page to go back to login on successfully
	 *   sending an email.
	 * 		
	 */
	public static class BackToLoginAllertCallback implements MessageBox.AlertCallback {

		/**
		 * This method is called when the ok button is clicked
		 * 
		 */
		public void execute() {
			ForgotUsernamePassWindow.getInstance().hide();
			LoginWindow.getInstance().show();
		}

	}
	
	private static final int FORM_LABEL_WIDTH = 60;
	private static final int WIDTH_FOR_TEXTBOX = 250;
	private static final int WINDOW_WIDTH = 420;
	private static final ForgotUsernamePassWindow singleton = new ForgotUsernamePassWindow();

	/**
	 * This method returns the instance of ForgotUsernamePassWindow
	 * 
	 * @return the instance of ForgotUsernamePassWindow
	 */
	public static ForgotUsernamePassWindow getInstance() {
		return singleton;
	}

	/**
	 * Creates the window and sets all the button events
	 * 
	 */
	private ForgotUsernamePassWindow() {
		FormPanel fPanel = new FormPanel(Position.LEFT);

		HorizontalPanel firstRow = new HorizontalPanel();
		HorizontalPanel secondRow = new HorizontalPanel();

		Label usernameLabel = new Label("Username: ");
		Label emailLabel = new Label("Email: ");

		usernameLabel.setWidth("65px");
		emailLabel.setWidth("65px");

		fPanel.setFrame(true);
		fPanel.setButtonAlign(Position.RIGHT);
		fPanel.setLabelWidth(FORM_LABEL_WIDTH);
		fPanel.setAutoWidth(true);
		fPanel.setAutoHeight(true);

		final TextField username = new TextField("Username");
		final TextField email = new TextField("Email");

		Button getPass = new Button("Forgot Password");
		Button getBoth = new Button("Forgot Username/Password");

		Hyperlink backToLogin = new Hyperlink("Back to Login", "");

		backToLogin.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				ForgotUsernamePassWindow.getInstance().hide();
				LoginWindow.getInstance().show();
			}
		});

		getPass.addListener(new ButtonListenerAdapter() {
			public void onClick(Button button, EventObject e) {
				// call method to reset password and send it to the employee
				final BackToLoginAllertCallback callBackToLogin = new BackToLoginAllertCallback();
				
				String curUsername = username.getText();
				
				ServiceManager.getManageEmployeesServiceAsync().getUsrByUsername(curUsername, 
						new AsyncCallback<GwtUser>() {

							public void onFailure(Throwable caught) {
								MessageBox.alert("Cound not find the username: " + username.getText()
										+ " because of " + caught.getMessage());
							}

							public void onSuccess(final GwtUser result) {
								ServiceManager.getUserServiceAsync().updateUserPassword(result.getUserID(), "LunaSet", new AsyncCallback<Object>()
								{
									public void onFailure(Throwable caught) {
										MessageBox.alert("Trouble reseting the password" + caught.getMessage());
									}

									public void onSuccess(Object res) {
										ServiceManager.getEmailServiceAsync().sendEmail("LunaSet", "",
												result.getProfile().getEmail(), 
												true, false, new AsyncCallback<String>(){
													public void onFailure(Throwable caught) {
														MessageBox.alert("Problem emailing " + caught.getMessage());
													}

													public void onSuccess(String result) {
														username.setValue("");
														MessageBox.alert("Reset", "Your password has been reset and a temporary one has been sent to your email.", callBackToLogin);
													}});
									}});
							}});
			}
		});

		getBoth.addListener(new ButtonListenerAdapter() {
			public void onClick(Button button, EventObject e) {
				final BackToLoginAllertCallback callBackToLogin = new BackToLoginAllertCallback();
				
				final String curEmail = email.getText();
				
				ServiceManager.getManageEmployeesServiceAsync().getUsrByEmail(curEmail, 
						new AsyncCallback<GwtUser>() {

							public void onFailure(Throwable caught) {
								MessageBox.alert("Couldn't find the email :  " + curEmail +
										" beacuse of " + caught.getMessage());
							}

							public void onSuccess(GwtUser result) {
								ServiceManager.getEmailServiceAsync().sendEmail("LunaSet", 
										buildUserName(result.getProfile().getFirstName(), result.getProfile().getLastName()),
										email.getText(), false, false, new AsyncCallback<String>() {

											public void onFailure(
													Throwable caught) {
												MessageBox.alert("Problem emailing " + caught.getMessage());
											}

											public void onSuccess(String result) {
												email.setValue("");
												MessageBox.alert("Reset", "Your username and password have been reset and temporary ones have been sent to your email.",
														callBackToLogin);
											}});
							}
							
							public String buildUserName(String first, String last) {
								String username = String.valueOf((first.trim()).charAt(0));

								username = username.concat(last.trim());

								username = username.toLowerCase();

								return username;
							}});
			}
		});

		email.setWidth(WIDTH_FOR_TEXTBOX - FORM_LABEL_WIDTH - 35);
		username.setWidth(WIDTH_FOR_TEXTBOX - FORM_LABEL_WIDTH - 35);

		firstRow.add(usernameLabel);
		firstRow.add(username);
		firstRow.add(getPass);
		firstRow.setSpacing(5);

		secondRow.add(emailLabel);
		secondRow.add(email);
		secondRow.add(getBoth);
		secondRow.setSpacing(5);

		fPanel.add(firstRow);
//		fPanel.add(secondRow);

		fPanel.add(backToLogin);

		setTitle("Forgot Username/Password");
		setWidth(WINDOW_WIDTH);
		setResizable(false);
		setClosable(false);

		add(fPanel);
	}

}
