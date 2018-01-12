package edu.calpoly.csc.luna.turboplan.web.client.widget;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.RadioButton;
import com.gwtext.client.data.SimpleStore;
import com.gwtext.client.data.Store;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.form.ComboBox;
import com.gwtext.client.widgets.form.DateField;
import com.gwtext.client.widgets.form.Label;
import com.gwtext.client.widgets.form.NumberField;
import com.gwtext.client.widgets.form.TextArea;
import com.gwtext.client.widgets.form.TextField;
import com.gwtext.client.widgets.form.VType;
import com.gwtext.client.widgets.layout.AnchorLayoutData;
import com.gwtext.client.widgets.layout.ColumnLayout;
import com.gwtext.client.widgets.layout.ColumnLayoutData;
import com.gwtext.client.widgets.layout.FormLayout;
import edu.calpoly.csc.luna.turboplan.web.client.GwtStringUtil;
import edu.calpoly.csc.luna.turboplan.web.client.model.GwtAddress;
import edu.calpoly.csc.luna.turboplan.web.client.model.GwtUser;
import edu.calpoly.csc.luna.turboplan.web.client.model.GwtUserProfile;

/**
 * EmployeeProfilePanel represents the contact information stored for a user
 * 
 * @author Ming Liu
 * @author Stephanie Long
 */
public class EmployeeProfilePanel extends Panel {
	private static final String SIMPLE_CLASSNAME = "EmployeeProfilePanel";

	private static final int LABEL_WIDTH = 125;
	private boolean manager = false;


	private TextField firstName;
	private TextField lastName;
	private TextField middle;
	private DateField birthDate;
	private ComboBox gender;
	private RadioButton managePerm;
	private RadioButton empPerm;

	private TextField streetAddress;
	private TextField city;
	private ComboBox state;
	private NumberField zip;
	private TextField country;

	private NumberField home;
	private NumberField mobile;
	private NumberField other1;
	private NumberField other2;
	private NumberField other3;

	private TextField email;
	private TextField email2;
	private TextField email3;
	private TextField email4;
	private TextField email5;

	/**
	 * Constructor creates a new employee profile panel
	 */
	public EmployeeProfilePanel() {
		super();
		Log.info("Constructing " + SIMPLE_CLASSNAME);

		FormLayout layout = new FormLayout();
		layout.setLabelWidth(LABEL_WIDTH);
		setLayout(layout);
		setFrame(true);

		// create top panel with ColumnLayout
		Panel topPanel = new Panel();
		topPanel.setLayout(new ColumnLayout());
		topPanel.setHeight(200);

		// create panel for general information and add fields to it
		final Panel genInfoPanel = buildGenInfoPanel();

		// add general information panel to top panel
		topPanel.add(genInfoPanel, new ColumnLayoutData(.5));

		// create panel for address information and add fields to it
		final Panel addressPanel = buildAddressPanel();

		// add panel as second column with 50% of the width
		topPanel.add(addressPanel, new ColumnLayoutData(.5));

		Panel btmPanel = new Panel();
		btmPanel.setLayout(new ColumnLayout());

		// create panel for email information and add fields to it
		final Panel emailPanel = buildEmailPanel();

		// add panel as first column in bottom panel with 50% of the width
		btmPanel.add(emailPanel, new ColumnLayoutData(.5));

		// add panel for phone information and create fields
		final Panel phoneNumberPanel = buildPhoneNumberPanel();

		// add panel as second column in bottom panel with 50% of the width
		btmPanel.add(phoneNumberPanel, new ColumnLayoutData(.5));

		// add columned top and bottom panel to form
		add(topPanel);
		add(btmPanel);
		
		Log.debug("Done constructing " + SIMPLE_CLASSNAME);
	}

	/**
	 * Create an employee profile panel and set it's fields to the specified
	 * user profile
	 * 
	 * @param usr
	 *            The user profile to reflect when creating the employee profile
	 *            panel
	 */
	public EmployeeProfilePanel(GwtUserProfile usr) {
		this();

		setUserProfile(usr);
	}

	/**
	 * Set the specified user profile panel with the values specified in the
	 * user profile
	 * 
	 * @param usr
	 *            The user profile to reflect in the employee profile window
	 */
	public void setUserProfile(GwtUserProfile usr) {
		Log.info("Set user profile begin ");
		final DateTimeFormat dateFormatter = DateTimeFormat.getFormat("M/d/y");
		firstName.setValue(usr.getFirstName());
		lastName.setValue(usr.getLastName());
		Log.info("Set names done ");
		birthDate.setRawValue(dateFormatter.format(usr.getDateOfBirth()));
		if (usr.getMiddleInitial() != null) {
			middle.setValue(usr.getMiddleInitial() + "");
		}
		Log.info("set mi and dob done ");
		gender.setValue(usr.getGender() == null ? "Female" : usr.getGender().name());

		if (manager) {
			this.managePerm.setChecked(true);
		} else {
			this.empPerm.setChecked(true);
		}
		Log.info("Set perms done ");
		GwtAddress local = usr.getAddress();
		streetAddress.setValue(local.getAddress());
		city.setValue(local.getCity());
		state.setValue(local.getState());
		zip.setValue(local.getZip());
		country.setValue(GwtStringUtil.nullStringCheck(local.getCountry()));
		Log.info("Set address done ");
		email.setValue(usr.getEmail());
		email2.setValue(usr.getEmail2());
		email3.setValue(usr.getEmail3());
		email4.setValue(usr.getEmail4());
		email5.setValue(usr.getEmail5());
		Log.info("Set emails done ");
		if (usr.getHomePhoneNumber()!=null) {
			home.setValue(GwtStringUtil.nullStringCheck(usr.getHomePhoneNumber().toString()));
		}
		if (usr.getMobilePhoneNumber()!=null) {
			mobile.setValue(GwtStringUtil.nullStringCheck(usr.getMobilePhoneNumber().toString()));
		}
		if (usr.getOtherPhoneNumber()!=null) {
			other1.setValue(GwtStringUtil.nullStringCheck(usr.getOtherPhoneNumber().toString()));
		}
		if (usr.getOtherPhoneNumber2()!=null) {
			other2.setValue(GwtStringUtil.nullStringCheck(usr.getOtherPhoneNumber2().toString()));
		}
		if (usr.getOtherPhoneNumber()!=null) {
			other3.setValue(GwtStringUtil.nullStringCheck(usr.getOtherPhoneNumber3().toString()));
		}
		Log.info("Set numbers done ");
	}

	/*
	 * Create the panel to hold all the phone numbers of the user
	 */
	private Panel buildPhoneNumberPanel() {
		final Panel phoneNumberPanel = new Panel("Phone Numbers (Numbers Only)");
		phoneNumberPanel.setLayout(new FormLayout());

		home = new NumberField("Home", "home", 210);
		home.setAllowBlank(true);
		phoneNumberPanel.add(home);
		home.setAllowDecimals(false);
		mobile = new NumberField("Mobile*", "mobile", 210);
		phoneNumberPanel.add(mobile);
		mobile.setAllowDecimals(false);
		other1 = new NumberField("Other", "other", 210);
		other1.setAllowBlank(true);
		phoneNumberPanel.add(other1);
		other1.setAllowDecimals(false);
		other2 = new NumberField("Other", "other2", 210);
		other2.setAllowBlank(true);
		phoneNumberPanel.add(other2);
		other2.setAllowDecimals(false);
		other3 = new NumberField("Other", "other3", 210);
		other3.setAllowBlank(true);
		other3.setAllowDecimals(false);
		phoneNumberPanel.add(other3);

		return phoneNumberPanel;
	}

	/*
	 * Create the panel to hold all the emails of a user
	 */
	private Panel buildEmailPanel() {
		final Panel emailPanel = new Panel("Emails");
		emailPanel.setLayout(new FormLayout());

		email = new TextField("Primary Email*", "email", 210);
		email.setVtype(VType.EMAIL);
		emailPanel.add(email, new AnchorLayoutData("95%"));
		email2 = new TextField("Email", "email2", 210);
		email2.setVtype(VType.EMAIL);
		emailPanel.add(email2, new AnchorLayoutData("95%"));
		email3 = new TextField("Email", "email3", 210);
		email3.setVtype(VType.EMAIL);
		emailPanel.add(email3, new AnchorLayoutData("95%"));
		email4 = new TextField("Email", "email4", 210);
		email4.setVtype(VType.EMAIL);
		emailPanel.add(email4, new AnchorLayoutData("95%"));
		email5 = new TextField("Email", "email5", 210);
		email5.setVtype(VType.EMAIL);
		emailPanel.add(email5, new AnchorLayoutData("95%"));

		return emailPanel;
	}

	/*
	 * Create the panel to hold all the address of the user
	 */
	private Panel buildAddressPanel() {
		final Panel addressPanel = new Panel("Address");
		addressPanel.setLayout(new FormLayout());

		streetAddress = new TextArea("Street Number and Name*", "street");
		addressPanel.add(streetAddress, new AnchorLayoutData("95%"));
		city = new TextField("City*", "city");
		addressPanel.add(city, new AnchorLayoutData("95%"));

		// add a ComboBox field
		Store storeState = new SimpleStore("states", getStates());

		state = new ComboBox();
		state.setFieldLabel("State*");
		state.setHiddenName("states");
		state.setStore(storeState);
		state.setDisplayField("states");
		state.setTypeAhead(true);
		state.setMode(ComboBox.LOCAL);
		state.setTriggerAction(ComboBox.ALL);
		state.setEmptyText("Select a state...");
		state.setSelectOnFocus(true);
		state.setWidth(170);
		addressPanel.add(state);

		zip = new NumberField("Zip Code*", "zipcode");
		zip.setAllowDecimals(false);
		addressPanel.add(zip, new AnchorLayoutData("55%"));

		country = new TextField("Country", "country");

		addressPanel.add(country, new AnchorLayoutData("95%"));

		return addressPanel;
	}

	/*
	 * Create the panel to hold all the general information of the user
	 */
	private Panel buildGenInfoPanel() {
		final Panel genInfoPanel = new Panel("General Information");
		genInfoPanel.setLayout(new FormLayout());

		firstName = new TextField("First Name*", "first");
		genInfoPanel.add(firstName, new AnchorLayoutData("95%"));

		middle = new TextField("Middle Initial", "middle");

		genInfoPanel.add(middle, new AnchorLayoutData("35%"));

		lastName = new TextField("Last Name*", "last");
		genInfoPanel.add(lastName, new AnchorLayoutData("95%"));

		birthDate = new DateField("Date of birth*", "dob", 170);
		birthDate.setAllowBlank(false);
		genInfoPanel.add(birthDate);

		final Store store = new SimpleStore("gender", getGender());
		store.load();

		gender = new ComboBox();
		gender.setForceSelection(true);
		gender.setMinChars(1);
		gender.setFieldLabel("Gender*");
		gender.setStore(store);
		gender.setDisplayField("gender");
		gender.setMode(ComboBox.LOCAL);
		gender.setTriggerAction(ComboBox.ALL);
		gender.setEmptyText("Select Gender...");
		gender.setLoadingText("Gender");
		gender.setTypeAhead(true);
		gender.setSelectOnFocus(true);
		gender.setWidth(150);

		gender.setHideTrigger(false);
		genInfoPanel.add(gender);
		
		Label reqLabel = new Label("* Required Fields");
		
		Panel permPanel = new Panel();
		genInfoPanel.add(reqLabel);
		
		Label permLabel = new Label("Permission*");
		managePerm = new RadioButton("btnGroup", "Manager Permission");
		empPerm = new RadioButton("btnGroup", "Employee Permission");
		permPanel.add(permLabel);
		permPanel.add(managePerm);
		permPanel.add(empPerm);
		genInfoPanel.add(permPanel);
		
		return genInfoPanel;
	}

	/**
	 * Clear the fields in the panel of any input
	 */
	public void clearPanel() {
		firstName.setValue("");
		lastName.setValue("");
		middle.setValue("");
		birthDate.setValue("");
		gender.clearValue();
		empPerm.setChecked(false);
		managePerm.setChecked(false);

		streetAddress.setValue("");
		city.setValue("");
		state.clearValue();
		zip.setValue("");
		country.setValue("");

		home.setValue("");
		mobile.setValue("");
		other1.setValue("");
		other2.setValue("");
		other3.setValue("");

		email.setValue("");
		email2.setValue("");
		email3.setValue("");
		email4.setValue("");
		email5.setValue("");
	}
	

	/**
	 * Get the genders to populate the gender combo box
	 * 
	 * @return The gender combo box representation of the genders
	 */
	private String[] getGender() {
		return new String[] { "Female", "Male" };
	}

	/**
	 * Get the states to populate the state combo box
	 * 
	 * @return The state combo box representation of states
	 */
	private String[] getStates() {
		return new String[] { "N/A", "Alabama", "Alaska", "Arizona", "Arkansas", "California", "Colorado",
				"Connecticut", "Delaware", "District of Columbia", "Florida", "Georgia", "Hawaii", "Idaho", "Illinois",
				"Indiana", "Iowa", "Kansas", "Kentucky", "Louisiana", "Maine", "Massachusetts", "Maryland", "Michigan",
				"Minnesota", "Mississippi", "Missouri", "Montana", "Nebraska", "Nevada", "New Hampshire", "New Jersey",
				"New Mexico", "New York", "North Carolina", "North Dakota", "Ohio", "Oklahoma", "Oregon",
				"Pennsylvania", "Rhode Island ", "South Carolina ", "South Dakota ", "Tennessee", "Texas", "Utah",
				"Vermont", "Virginia", "Washington", "West Virginia", "Wisconsin", "Wyoming", "Other" };
	}

	/**
	 * Get a user profile consisting of the input values of the employee profile
	 * panel
	 * 
	 * @return The created user profile
	 */
	public GwtUserProfile getUserProfile() {
		GwtUserProfile profile = new GwtUserProfile();
		
		profile.setFirstName(firstName.getText());
		profile.setLastName(lastName.getText());

		profile.setMiddleInitial(middle.getText().isEmpty() ? null : middle.getText().charAt(0));
		profile.setDateOfBirth(birthDate.getValue());
		profile.setGender(GwtUserProfile.EmployeeGender.valueOf(gender.getText()));

		profile.setAddress(new GwtAddress(streetAddress.getText(), state.getText(), city.getText(), zip.getValue()
				.intValue(), country.getText().isEmpty() ? "United States" : country.getText()));

		profile.setHomePhoneNumber(extractLongFromNumberField(home));
		profile.setMobilePhoneNumber(extractLongFromNumberField(mobile));
		profile.setOtherPhoneNumber(extractLongFromNumberField(other1));
		profile.setOtherPhoneNumber2(extractLongFromNumberField(other2));
		profile.setOtherPhoneNumber3(extractLongFromNumberField(other3));

		profile.setEmail(email.getText());
		profile.setEmail2(email2.getText());
		profile.setEmail3(email3.getText());
		profile.setEmail4(email4.getText());
		profile.setEmail5(email5.getText());

		
		
		
	
		boolean[][] tempavil = new boolean[7][48];

		// Populate Availability with nothing is available
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 48; j++) {
				tempavil[i][j] = false;
			}
		}

		profile.setAvailability(tempavil);
		
		return profile;
	}
	
	
	/**
	 * Gets the permission of the employee
	 * @return The permissions of the employee
	 */
	public Set<String> getPermission() {
		EnumSet<GwtUser.Permission> permSet;
		if (managePerm.isChecked()) {
			permSet = EnumSet.noneOf(GwtUser.Permission.class);
			
			permSet.add(GwtUser.Permission.ManageEmployees);
			permSet.add(GwtUser.Permission.ManageSchedule);
			permSet.add(GwtUser.Permission.ManageTasks);
			permSet.add(GwtUser.Permission.MasterSchedule);
			permSet.add(GwtUser.Permission.MySchedule);
			permSet.add(GwtUser.Permission.Preferences);
		} else {
			permSet = EnumSet.noneOf(GwtUser.Permission.class);
			
			permSet.add(GwtUser.Permission.Preferences);
			permSet.add(GwtUser.Permission.MySchedule);
		}
		
		Set<String> ret = new HashSet<String>();
		for (GwtUser.Permission perm : permSet) {
			ret.add(perm.name());
		}
		
		return ret;
	}

	/**
	 * Sets if the manager is set for the profile
	 */
	public void setManager() {
		manager = true;
	}
	
	/**
	 * Extracts a long number from a number field in the panel
	 * @return The long version of the number from the field
	 */
	private Long extractLongFromNumberField(NumberField field) {
		return field.getText().isEmpty() ? null : field.getValue().longValue();
	}

}
