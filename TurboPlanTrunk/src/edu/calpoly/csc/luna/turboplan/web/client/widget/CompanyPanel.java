package edu.calpoly.csc.luna.turboplan.web.client.widget;

import java.util.Date;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.core.Position;
import com.gwtext.client.data.SimpleStore;
import com.gwtext.client.data.Store;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.MessageBox;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.form.ComboBox;
import com.gwtext.client.widgets.form.DateField;
import com.gwtext.client.widgets.form.NumberField;
import com.gwtext.client.widgets.form.TextArea;
import com.gwtext.client.widgets.form.TextField;
import com.gwtext.client.widgets.layout.AnchorLayoutData;
import com.gwtext.client.widgets.layout.ColumnLayout;
import com.gwtext.client.widgets.layout.ColumnLayoutData;
import com.gwtext.client.widgets.layout.FormLayout;

import edu.calpoly.csc.luna.turboplan.core.entity.AbstractUser;
import edu.calpoly.csc.luna.turboplan.web.client.model.GwtAddress;
import edu.calpoly.csc.luna.turboplan.web.client.model.GwtCompany;
import edu.calpoly.csc.luna.turboplan.web.client.model.GwtUser;
import edu.calpoly.csc.luna.turboplan.web.client.model.GwtUserProfile;
import edu.calpoly.csc.luna.turboplan.web.client.model.GwtUserProfile.EmployeeGender;
import edu.calpoly.csc.luna.turboplan.web.client.service.ServiceManager;
import edu.calpoly.csc.luna.turboplan.web.client.window.ForgotUsernamePassWindow;
import edu.calpoly.csc.luna.turboplan.web.client.window.LoginWindow;
import edu.calpoly.csc.luna.turboplan.web.client.window.ForgotUsernamePassWindow.BackToLoginAllertCallback;

/**
 * Form for COMPANY and MANAGER information.
 * 
 * @author Paul De Leon
 */
public class CompanyPanel extends Panel {
	private static String SIMPLE_CLASSNAME = "CompanyPanel";
	private static final CompanyPanel singleton = new CompanyPanel();

	public static CompanyPanel getInstance() {
		return singleton;
	}

	/**
	 * @author Aleks
	 */
	private final class saveBtnListener extends ButtonListenerAdapter {
		public void onClick(Button button, EventObject e) {
			if (areFieldsFilled()) {
				try {
					Log.info("creating a company");
					final GwtUser usr = new GwtUser();
					String companyAddress = getCompanyStreet() + ", "
							+ getCompanyCity() + ", " + getCompanyState()
							+ ", " + getCompanyZip() + ", "
							+ getCompanyCountry();

					Log.info("company address is " + getCompanyStreet() + ", "
							+ getCompanyCity() + ", " + getCompanyState()
							+ ", " + getCompanyZip());

					GwtCompany company = new GwtCompany(companyAddress,
							getCompanyName());
					GwtUserProfile profile = new GwtUserProfile();
					GwtAddress usrAddress = new GwtAddress(getManagerStreet(),
							getManagerState(), getManagerCity(), Integer
									.parseInt(getManagerZip()),
							getManagerCountry());

					Log.info("user address is " + getManagerStreet() + ", "
							+ getManagerCity() + ", " + getManagerState()
							+ ", " + getManagerZip());

					profile.setAddress(usrAddress);
					profile.setDateOfBirth(new Date(getManagerBirthDate()));
					profile.setEmail(getManagerEmail());
					profile.setFirstName(getManagerFirstName());
					profile.setLastName(getManagerLastName());

					if (!getManagerMobile().equals("")) {
						profile.setMobilePhoneNumber(Long
								.parseLong(getManagerMobile()));
					}

					if (!getManagerHome().equals("")) {
						profile.setHomePhoneNumber(Long
								.parseLong(getManagerHome()));
					}

					if (getManagerGender().equals("Female")) {
						profile.setGender(EmployeeGender.Female);
					} else {
						profile.setGender(EmployeeGender.Male);
					}

					usr.setProfile(profile);

					Set<String> ret = new HashSet<String>();
					for (GwtUser.Permission perm : EnumSet
							.allOf(GwtUser.Permission.class)) {
						if (!perm.name().equals("Root")) {
							ret.add(perm.name());
						}
					}

					usr.setPermission(ret);

					Set<GwtUser> users = new HashSet<GwtUser>();
					users.add(usr);
					company.setUsers(users);

					ServiceManager.getComapnyServiceAsync().addCompany(company,
							new AsyncCallback<Object>() {

								public void onFailure(Throwable caught) {
									// TODO Auto-generated method stub
									MessageBox.alert(caught.getMessage());
								}

								public void onSuccess(Object result) {
									// TODO Auto-generated method stub
									companyName.setValue("");

									companyStreet.setValue("");
									companyCity.setValue("");
									companyState.clearValue();
									companyZip.setValue("");
									companyCountry.setValue("");

									// Manager Fields
									managerFirstName.setValue("");
									managerMiddleInitial.setValue("");
									managerLastName.setValue("");
									managerBirthDate.setValue("");
									managerGender.clearValue();
									managerHome.setValue("");
									managerMobile.setValue("");
									managerEmail.setValue("");

									managerStreet.setValue("");
									managerCity.setValue("");
									managerState.clearValue();
									managerZip.setValue("");
									managerCountry.setValue("");
									MessageBox
											.alert("The company was saved to the database");
								}
							});
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			} else {
				MessageBox
						.alert("Please fill out all required fields and then press save.");
			}
		}

		public Boolean areFieldsFilled() {
			if (getCompanyName().equals("") || getCompanyStreet().equals("")
					|| getCompanyState().equals("")
					|| getCompanyCity().equals("")
					|| getCompanyZip().equals("")
					|| getManagerBirthDate().equals("")
					|| getManagerCity().equals("")
					|| getManagerEmail().equals("")
					|| getManagerFirstName().equals("")
					|| getManagerGender().equals("")
					|| getManagerLastName().equals("")
					|| getManagerMobile().equals("")
					|| getManagerState().equals("")
					|| getManagerStreet().equals("")
					|| getManagerZip().equals("")) {
				return false;
			}

			return true;
		}

	}

	// Company Fields
	private TextField companyName;

	private TextArea companyStreet;
	private TextField companyCity;
	private ComboBox companyState;
	private NumberField companyZip;
	private TextField companyCountry;

	// Manager Fields
	private TextField managerFirstName;
	private TextField managerMiddleInitial;
	private TextField managerLastName;
	private DateField managerBirthDate;
	private ComboBox managerGender;
	private TextField managerHome;
	private TextField managerMobile;
	private TextField managerEmail;

	private TextArea managerStreet;
	private TextField managerCity;
	private ComboBox managerState;
	private NumberField managerZip;
	private TextField managerCountry;

	private Button saveBtn;

	/**
	 * @author Paul De Leon
	 */
	public CompanyPanel() {
		super();
		Log.info(SIMPLE_CLASSNAME + " is constructing...");

		setLayout(new FormLayout());
		initializeFields();
		setupPanels();

		Log.debug("...Finished constructing " + SIMPLE_CLASSNAME + "!");
	}

	/**
	 * @author Paul De Leon
	 */
	private void initializeFields() {
		// Company Fields
		companyName = new TextField("Company Name*", "companyName");

		// companyStreet = new TextField("Street", "companyStreet");
		companyStreet = new TextArea("Street Number and Name*", "companyStreet");
		companyCity = new TextField("City*", "companyCity");
		companyState = new ComboBox("State*", "companyState");
		companyZip = new NumberField("Zip*", "managerZip");
		companyCountry = new TextField("Country", "managerCountry");

		// Manager Fields
		managerFirstName = new TextField("First Name*", "managerFirstName");
		managerMiddleInitial = new TextField("Middle Initial",
				"managerMiddleInitial");
		managerLastName = new TextField("Last Name*", "managerLastName");

		managerBirthDate = new DateField("Date of birth*", "dob", 170);
		managerBirthDate.setAllowBlank(false);

		final Store store = new SimpleStore("gender", getGender());
		store.load();
		managerGender = new ComboBox();
		managerGender.setForceSelection(true);
		managerGender.setMinChars(1);
		managerGender.setFieldLabel("Gender*");
		managerGender.setStore(store);
		managerGender.setDisplayField("gender");
		managerGender.setMode(ComboBox.LOCAL);
		managerGender.setTriggerAction(ComboBox.ALL);
		managerGender.setEmptyText("Select Gender...");
		managerGender.setLoadingText("Gender");
		managerGender.setTypeAhead(true);
		managerGender.setSelectOnFocus(true);
		managerGender.setWidth(150);
		managerGender.setHideTrigger(false);

		managerHome = new TextField("Home", "managerHome");
		managerMobile = new TextField("Mobile*", "managerMobile");
		managerEmail = new TextField("Email*", "managerEmail");

		// managerStreet = new TextField("Street*", "managerStreet");
		managerStreet = new TextArea("Street Number and Name*", "managerStreet");
		managerCity = new TextField("City*", "managerCity");
		managerState = new ComboBox("State*", "managerState");
		managerZip = new NumberField("Zip*", "managerZip");
		managerCountry = new TextField("Country", "managerCountry");

		saveBtn = new Button("Save", new saveBtnListener());
	}

	/**
	 * @author Paul De Leon
	 */
	private void setupPanels() {
		setAutoScroll(true);
		add(buildCompanyInfoPanel());
		add(buildManagerInfoPanel());

		setButtons(new Button[] { saveBtn });
		setButtonAlign(Position.RIGHT);
	}

	/**
	 * @author Paul De Leon
	 */
	private Panel buildCompanyInfoPanel() {
		return buildSplitPanel(buildCompanyGenInfoPanel(),
				buildCompanyAddressPanel(), "Company Information");
	}

	/**
	 * @author Paul De Leon
	 */
	private Panel buildManagerInfoPanel() {
		return buildSplitPanel(buildManagerGenInfoPanel(),
				buildManagerAddressPanel(), "Manager Information");
	}

	/*
	 * Build Customer Info Panel. Contains General Info and Address Info.
	 * 
	 * @author Paul De Leon
	 */
	private Panel buildSplitPanel(Panel leftPanel, Panel rightPanel,
			String title) {
		Panel fullPanel = new Panel(); // The main panel
		fullPanel.setTitle(title);
		fullPanel.setCollapsible(false);
		fullPanel.setLayout(new ColumnLayout());

		fullPanel.add(leftPanel, new ColumnLayoutData(.5));
		fullPanel.add(rightPanel, new ColumnLayoutData(.5));

		return fullPanel;
	}

	/**
	 * @author Paul De Leon
	 */
	private Panel buildCompanyGenInfoPanel() {
		Panel companyGenPanel = new Panel();

		companyGenPanel.setLayout(new FormLayout());
		companyGenPanel.setBorder(false);

		companyGenPanel.add(companyName, new AnchorLayoutData("95%"));

		return companyGenPanel;
	}

	/**
	 * @author Paul De Leon
	 */
	private Panel buildCompanyAddressPanel() {
		return buildAddressPanel(companyStreet, companyCity, companyState,
				companyZip, companyCountry);
	}

	/*
	 * Helper Method. Builds Customer General Info Panel.
	 * 
	 * @author Paul De Leon
	 */
	private Panel buildManagerGenInfoPanel() {
		Panel managerGenPanel = new Panel();

		// Set Panel Attributes
		managerGenPanel.setLayout(new FormLayout());
		managerGenPanel.setBorder(false);

		// Add Fields to Panel
		managerGenPanel.add(managerFirstName, new AnchorLayoutData("95%"));
		managerGenPanel.add(managerMiddleInitial, new AnchorLayoutData("35%"));
		managerGenPanel.add(managerLastName, new AnchorLayoutData("95%"));
		managerGenPanel.add(managerBirthDate);
		managerGenPanel.add(managerGender);
		managerGenPanel.add(managerHome);
		managerGenPanel.add(managerMobile);
		managerGenPanel.add(managerEmail);

		return managerGenPanel;
	}

	/**
	 * @author Paul De Leon
	 */
	private Panel buildManagerAddressPanel() {
		return buildAddressPanel(managerStreet, managerCity, managerState,
				managerZip, managerCountry);
	}

	/*
	 * Helper Method. Builds Address Panel.
	 * 
	 * @author Paul De Leon
	 */
	private Panel buildAddressPanel(TextField street, TextField city,
			ComboBox state, NumberField zip, TextField country) {
		final Panel addressPanel = new Panel();
		addressPanel.setLayout(new FormLayout());

		addressPanel.setBorder(false);
		addressPanel.add(street, new AnchorLayoutData("95%"));
		addressPanel.add(city, new AnchorLayoutData("95%"));

		// add a ComboBox field
		Store storeState = new SimpleStore("states", getStates());
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

		zip.setAllowDecimals(false);
		addressPanel.add(zip, new AnchorLayoutData("95%"));

		addressPanel.add(country, new AnchorLayoutData("95%"));

		return addressPanel;
	}

	/**
	 * Gets all states in U.S.
	 * 
	 * @return The states in string form
	 * @author Stephanie Long
	 */
	private String[] getStates() {
		return new String[] { "N/A", "Alabama", "Alaska", "Arizona",
				"Arkansas", "California", "Colorado", "Connecticut",
				"Delaware", "District of Columbia", "Florida", "Georgia",
				"Hawaii", "Idaho", "Illinois", "Indiana", "Iowa", "Kansas",
				"Kentucky", "Louisiana", "Maine", "Massachusetts", "Maryland",
				"Michigan", "Minnesota", "Mississippi", "Missouri", "Montana",
				"Nebraska", "Nevada", "New Hampshire", "New Jersey",
				"New Mexico", "New York", "North Carolina", "North Dakota",
				"Ohio", "Oklahoma", "Oregon", "Pennsylvania", "Rhode Island ",
				"South Carolina ", "South Dakota ", "Tennessee", "Texas",
				"Utah", "Vermont", "Virginia", "Washington", "West Virginia",
				"Wisconsin", "Wyoming", "Other" };
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
	 * @return the companyName
	 */
	public String getCompanyName() {
		return companyName.getText();
	}

	/**
	 * @return the companyStreet
	 */
	public String getCompanyStreet() {
		return companyStreet.getText();
	}

	/**
	 * @return the companyCity
	 */
	public String getCompanyCity() {
		return companyCity.getText();
	}

	/**
	 * @return the companyState
	 */
	public String getCompanyState() {
		return companyState.getText();
	}

	/**
	 * @return the companyZip
	 */
	public String getCompanyZip() {
		return companyZip.getValue().toString();
	}

	/**
	 * @return the companyCountry
	 */
	public String getCompanyCountry() {
		return companyCountry.getText();
	}

	/**
	 * @return the managerFirstName
	 */
	public String getManagerFirstName() {
		return managerFirstName.getText();
	}

	/**
	 * @return the managerMiddleInitial
	 */
	public String getManagerMiddleInitial() {
		return managerMiddleInitial.getText();
	}

	/**
	 * @return the managerLastName
	 */
	public String getManagerLastName() {
		return managerLastName.getText();
	}

	/**
	 * @return the managerBirthDate
	 */
	public String getManagerBirthDate() {
		return managerBirthDate.getText();
	}

	/**
	 * @return the managerGender
	 */
	public String getManagerGender() {
		return managerGender.getText();
	}

	/**
	 * @return the managerHome
	 */
	public String getManagerHome() {
		return managerHome.getText();
	}

	/**
	 * @return the managerMobile
	 */
	public String getManagerMobile() {
		return managerMobile.getText();
	}

	/**
	 * @return the managerEmail
	 */
	public String getManagerEmail() {
		return managerEmail.getText();
	}

	/**
	 * @return the managerStreet
	 */
	public String getManagerStreet() {
		return managerStreet.getText();
	}

	/**
	 * @return the managerCity
	 */
	public String getManagerCity() {
		return managerCity.getText();
	}

	/**
	 * @return the managerState
	 */
	public String getManagerState() {
		return managerState.getText();
	}

	/**
	 * @return the managerZip
	 */
	public String getManagerZip() {
		return managerZip.getValue().toString();
	}

	/**
	 * @return the managerCountry
	 */
	public String getManagerCountry() {
		return managerCountry.getText();
	}
}
