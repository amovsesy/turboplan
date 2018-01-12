package edu.calpoly.csc.luna.turboplan.web.client.page;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.core.Position;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.MessageBox;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.TabPanel;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.form.TextField;
import com.gwtext.client.widgets.layout.AnchorLayoutData;
import com.gwtext.client.widgets.layout.FormLayout;
import com.gwtext.client.widgets.layout.VerticalLayout;

import edu.calpoly.csc.luna.turboplan.web.client.TurboPlan;
import edu.calpoly.csc.luna.turboplan.web.client.service.ServiceManager;
import edu.calpoly.csc.luna.turboplan.web.client.widget.SetAvailPanel;


public class PreferencesPage extends BasePage {
    private static final String SIMPLE_CLASSNAME = "PreferencesPage";
    private static final String pageTitle = "Preferences";
    private static final int defaultPageWidth = TurboPlan.STANDARD_PAGE_WIDTH;
    private static final int defaultPageHeight = 370;
    
    private Panel mainPanel;
    private TabPanel tabbedPanel;
    
    private TextField nPassword;
    private TextField oPassword;
    private TextField cnPassword;

    public void onShow() {
    }

    public void construct() {
        Log.info("Constructing " + SIMPLE_CLASSNAME);
        
        super.clear();
        
        mainPanel = new Panel(pageTitle);
        tabbedPanel = new TabPanel();
        
        setupTabbedPanel();
        
        tabbedPanel.add(createAvailPanel());
        tabbedPanel.add(createChangePwPanel());   
        
        mainPanel.add(tabbedPanel);

        add((content = mainPanel));
        Log.debug("Done constructing " + SIMPLE_CLASSNAME);
    }
    
    
    private void setupTabbedPanel() {
        tabbedPanel.setWidth(defaultPageWidth);
        tabbedPanel.setHeight(defaultPageHeight);
        tabbedPanel.setTabPosition(Position.BOTTOM);
        tabbedPanel.setMaskDisabled(true);
    }
    
    
    private Panel createAvailPanel() {
        SetAvailPanel availTab = new SetAvailPanel();
        
        return availTab;
    }
    
    /**
     * @author Stephanie Long
     * @author Paul De Leon
     * @return
     */
    private Panel createChangePwPanel() {
        Panel outPanel = new Panel("Change Password");
        outPanel.setLayout(new VerticalLayout(10));
        Panel changePassword = new Panel("Change Password");
        changePassword.setLayout(new FormLayout());
        changePassword.setPaddings(25);
        oPassword = new TextField("Old Password", "OldPassword"); 
        oPassword.setPassword(true);
        nPassword = new TextField("New Password", "NewPassword");
        nPassword.setPassword(true);
        cnPassword = new TextField("Confirm New", "ConfirmNewPassword");
        cnPassword.setPassword(true);
        Button saveBtn = new Button("Save Password"); 
        saveBtn.addListener(new ButtonListenerAdapter() {
            public void onClick(Button button, EventObject e) {
            	
            	ServiceManager.getUserServiceAsync().verifyUserPassword(TurboPlan.loggedInUser.getUserID(), oPassword.getText().trim(), new AsyncCallback<Boolean>() {
            		
            		public void onFailure(Throwable caught) {
            			Log.error("Call to server to save password failed", caught);
            			MessageBox.alert("Unsuccessful password change " + caught.getMessage());
            			oPassword.setValue("");
            			nPassword.setValue("");
            			cnPassword.setValue("");
            		}

            		public void onSuccess(Boolean result) {
            			if (result) {
            				if (nPassword.getValueAsString().equals(cnPassword.getValueAsString())) { //and oPassword equals logged in user's old password
            					ServiceManager.getUserServiceAsync().updateUserPassword(TurboPlan.loggedInUser.getUserID(), cnPassword.getText(), new AsyncCallback<Object>() {
                    
            						public void onFailure(Throwable caught) {
            							Log.error("Call to server to save password failed", caught);
            							MessageBox.alert("Unsuccessful password change " + caught.getMessage());
            							oPassword.setValue("");
            							nPassword.setValue("");
            							cnPassword.setValue("");
            						}

            						public void onSuccess(Object result) {
            							Log.debug("Successful saved user's new password");
            							MessageBox.alert("Successful password change. Email Confirmation Sent");
            							oPassword.setValue("");
            							nPassword.setValue("");
            							cnPassword.setValue("");
            						}
            					});
            				} else {
            					MessageBox.alert("New Passwords Not Matching");
            					oPassword.setValue("");
            					nPassword.setValue("");
            					cnPassword.setValue("");
            					
            				}
            			}else {
            				MessageBox.alert("Old Password Not Correct");
        					oPassword.setValue("");
        					nPassword.setValue("");
        					cnPassword.setValue("");
            			}
            		}
            	});
            }
        });
        
        changePassword.setWidth(TurboPlan.STANDARD_PAGE_WIDTH);
        changePassword.add(oPassword, new AnchorLayoutData("35%"));
        changePassword.add(nPassword, new AnchorLayoutData("35%"));
        changePassword.add(cnPassword, new AnchorLayoutData("35%"));
        changePassword.add(saveBtn);
        outPanel.add(changePassword);
        
        outPanel.setButtonAlign(Position.CENTER);
        
        return outPanel;
    }
}
