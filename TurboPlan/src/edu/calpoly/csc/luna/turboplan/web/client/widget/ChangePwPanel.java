package edu.calpoly.csc.luna.turboplan.web.client.widget;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.MessageBox;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.form.TextField;
import com.gwtext.client.widgets.layout.AnchorLayoutData;
import com.gwtext.client.widgets.layout.FormLayout;
import edu.calpoly.csc.luna.turboplan.web.client.TurboPlan;
import edu.calpoly.csc.luna.turboplan.web.client.service.ServiceManager;

public class ChangePwPanel extends Panel {
	private TextField oldPW, newPW, confirmNewPW;
	
	public ChangePwPanel() {
		super();
		createChangePwPanel();
	}
	
    /**
     * @author Stephanie Long
     * @author Paul De Leon
     * @return
     */
    private void createChangePwPanel() {
//        Panel outPanel = new Panel("Change Password");
//        this.setLayout(new VerticalLayout(10));
//        Panel changePassword = new Panel("Change Password");
        this.setLayout(new FormLayout());
        this.setPaddings(25);
        oldPW = new TextField("Old Password", "OldPassword"); 
        oldPW.setPassword(true);
        newPW = new TextField("New Password", "NewPassword");
        newPW.setPassword(true);
        confirmNewPW = new TextField("Confirm New", "ConfirmNewPassword");
        confirmNewPW.setPassword(true);
        Button saveBtn = new Button("Save Password"); 
        saveBtn.addListener(new ButtonListenerAdapter() {
            public void onClick(Button button, EventObject e) {
            	
            	ServiceManager.getUserServiceAsync().verifyUserPassword(TurboPlan.loggedInUser.getUserID(), oldPW.getText().trim(), new AsyncCallback<Boolean>() {
            		
            		public void onFailure(Throwable caught) {
            			Log.error("Call to server to save password failed", caught);
            			MessageBox.alert("Unsuccessful password change " + caught.getMessage());
            			oldPW.setValue("");
            			newPW.setValue("");
            			confirmNewPW.setValue("");
            		}

            		public void onSuccess(Boolean result) {
            			if (result) {
            				if (newPW.getValueAsString().equals(confirmNewPW.getValueAsString())) { //and oPassword equals logged in user's old password
            					ServiceManager.getUserServiceAsync().updateUserPassword(TurboPlan.loggedInUser.getUserID(), confirmNewPW.getText(), new AsyncCallback<Object>() {
                    
            						public void onFailure(Throwable caught) {
            							Log.error("Call to server to save password failed", caught);
            							MessageBox.alert("Unsuccessful password change " + caught.getMessage());
            							oldPW.setValue("");
            							newPW.setValue("");
            							confirmNewPW.setValue("");
            						}

            						public void onSuccess(Object result) {
            							Log.debug("Successful saved user's new password");
            							MessageBox.alert("Successful password change. Email Confirmation Sent");
            							oldPW.setValue("");
            							newPW.setValue("");
            							confirmNewPW.setValue("");
            						}
            					});
            				} else {
            					MessageBox.alert("New Passwords Not Matching");
            					oldPW.setValue("");
            					newPW.setValue("");
            					confirmNewPW.setValue("");
            					
            				}
            			}else {
            				MessageBox.alert("Old Password Not Correct");
        					oldPW.setValue("");
        					newPW.setValue("");
        					confirmNewPW.setValue("");
            			}
            		}
            	});
            }
        });
        
        this.setWidth(TurboPlan.STANDARD_PAGE_WIDTH);
        this.add(oldPW, new AnchorLayoutData("35%"));
        this.add(newPW, new AnchorLayoutData("35%"));
        this.add(confirmNewPW, new AnchorLayoutData("35%"));
        this.add(saveBtn);
//        this.add(changePassword);
        
//        this.setButtonAlign(Position.CENTER);
        
//        return outPanel;
    }
}
