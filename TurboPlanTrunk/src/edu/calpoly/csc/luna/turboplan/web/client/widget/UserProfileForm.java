package edu.calpoly.csc.luna.turboplan.web.client.widget;

import com.google.gwt.user.client.ui.Composite;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.MessageBox;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.form.FormPanel;
import com.gwtext.client.widgets.form.NumberField;
import com.gwtext.client.widgets.form.TextField;

import edu.calpoly.csc.luna.turboplan.web.client.model.GwtUserProfile;

public class UserProfileForm extends Composite {

	private static final int LABEL_WIDTH = 135;

	private FormPanel form;
	
	private TextField firstName;
	private TextField lastName;
	private TextField middle;
	
	private NumberField homePhone;
	private NumberField mobilePhone;
	
	private TextField address;
	private TextField addresscont;
	private TextField id;
	
	private TextField email;
	private TextField password;
	
	public UserProfileForm(boolean heading) {
		form = new FormPanel();
		
		firstName = new TextField();
		lastName = new TextField();
		middle = new TextField();
		homePhone = new NumberField();
		mobilePhone = new NumberField();
		address = new TextField();
		addresscont = new TextField();
		id = new TextField();
		email = new TextField();
		password = new TextField();
		homePhone.setAllowDecimals(false);
		mobilePhone.setAllowDecimals(false);

		id.setReadOnly(true);
		
		firstName.setFieldLabel("First Name");
		lastName.setFieldLabel("Last Name");
		middle.setFieldLabel("Middle Initial");
		homePhone.setFieldLabel("Home Phone Number");
		mobilePhone.setFieldLabel("Mobile Phone Number");
		address.setFieldLabel("Address");
		addresscont.setFieldLabel("Address Continued");
		id.setFieldLabel("Empl ID");
		email.setFieldLabel("E-mail");
		password.setFieldLabel("Password");

		form.add(firstName);
		form.add(middle);
		form.add(lastName);
		form.add(email);
		form.add(password);
		form.add(homePhone);
		form.add(mobilePhone);
		form.add(address);
		form.add(addresscont);
		form.add(id);
		
		form.addButton(new Button("Save", new ButtonListenerAdapter() {
			public void onClick(Button button, EventObject e) {
				MessageBox.alert("", "User profile saved", null);
			}
		}));
		form.addButton(new Button("Cancel", new ButtonListenerAdapter() {
			public void onClick(Button button, EventObject e) {
				MessageBox.alert("", "User profile saved", null);
			}
		}));
		
		if (heading) {
//			form.setHeading("User Profile");
		} else {
//			form.setHeaderVisible(false);
		}
		form.setLabelWidth(LABEL_WIDTH);
		form.setBodyBorder(false);
		
		initWidget(form);
	}
	
	public void fillForm(GwtUserProfile bean) {
		firstName.setValue(bean.getFirstName());
		middle.setValue(bean.getMiddleInitial() + "");
		lastName.setValue(bean.getLastName());
		homePhone.setValue(bean.getHomePhoneNumber());
		mobilePhone.setValue(bean.getMobilePhoneNumber());
//		address.setValue(bean.getAddress());
//		addresscont.setValue(bean.getAddCont());
		id.setValue(bean.getUserID() + "");
		email.setValue(bean.getEmail());
	}
//	public FormPanel getform() {
//		return form;
//	}
}
