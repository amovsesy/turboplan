package edu.calpoly.csc.luna.turboplan.web.client.widget;

import java.util.LinkedList;
import java.util.List;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.core.SortDir;
import com.gwtext.client.data.FieldDef;
import com.gwtext.client.data.GroupingStore;
import com.gwtext.client.data.Record;
import com.gwtext.client.data.RecordDef;
import com.gwtext.client.data.SortState;
import com.gwtext.client.data.StringFieldDef;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.MessageBox;
import com.gwtext.client.widgets.Window;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.grid.ColumnConfig;
import com.gwtext.client.widgets.grid.ColumnModel;
import com.gwtext.client.widgets.grid.GridPanel;
import com.gwtext.client.widgets.grid.GroupingView;
import com.gwtext.client.widgets.grid.event.GridRowListenerAdapter;

import edu.calpoly.csc.luna.turboplan.web.client.TurboPlan;
import edu.calpoly.csc.luna.turboplan.web.client.model.GwtUser;
import edu.calpoly.csc.luna.turboplan.web.client.model.GwtUserProfile;
import edu.calpoly.csc.luna.turboplan.web.client.service.ServiceManager;
import edu.calpoly.csc.luna.turboplan.web.client.window.EmployeeProfileWindow;

/**
 * The Employee Grid represents the user interface to manage employees
 *  
 * @author Ming Liu
 * @author Stephanie Long
 * @author Aleks Movsesyan
 */
public class EmployeeGrid extends GridPanel {
	private static final String LASTNAME_RECORDDEF = "lastname";

	private static final String FIRSTNAME_RECORDDEF = "firstname";

	private static final String EMPLOYEE_ID_RECORDDEF = "employeeID";
	
	private static final String STATUS_RECORDDEF = "status";

	/**
	 * Employee Grid
	 */
	private final EmployeeGrid myGrid = this;
	
	/**
	 * Users to be displayed on employee grid
	 */
	private List<GwtUser> userList;

	/**
	 * Inner button listener for the Add Employee button
	 * @author Stephanie Long
	 */
	private final class AddUserButtonListener extends ButtonListenerAdapter {
		private final EmployeeProfileWindow win;
		private final AsyncCallback<Object> callback;

		/**
		 * Constructs a new listener with the provided window and callback
		 * @param win
		 * @param asyncCallback
		 */
		private AddUserButtonListener(EmployeeProfileWindow win, AsyncCallback<Object> asyncCallback) {
			this.win = win;
			this.callback = asyncCallback;
		}

		/**
		 * Action to occur if the specified button is clicked. For add, will make call to add an employee to the database.
		 * Creates the username of the first initial and last name of a user and the password is defaulted to "LunaSet"
		 * @param button Button to click upon
		 * @param e Event to call upon
		 */
		public void onClick(Button button, EventObject e) {
			Log.info(button.getText() + " button clicked");
			GwtUserProfile profile = win.getEmployeeProfilePanel().getUserProfile();
			
			GwtUser usr = new GwtUser();
			
			usr.setActive(true);
			usr.setProfile(profile);
			usr.setCompany(TurboPlan.loggedInUser.getCompany());
			usr.setPermission(win.getEmployeeProfilePanel().getPermission());

			ServiceManager.getManageEmployeesServiceAsync().addUser(usr, callback);

		}

		/**
		 * Build a user name to assign to a newly created user
		 * @param first First name of user to create
		 * @param last Last name of user to create
		 * @return The user name to be assigned to the newly created user
		 */
		public String buildUserName(String first, String last) {
			String username = String.valueOf((first.trim()).charAt(0));

			username = username.concat(last.trim());

			username = username.toLowerCase();

			return username;
		}
	}

	/**
	 * Inner button listener for the Update Employee button
	 * @author Stephanie Long
	 */
	private final class UpdateUserButtonListener extends ButtonListenerAdapter {
		private final EmployeeProfileWindow win;
		private final AsyncCallback<Object> callback;

		/**
		 * Constructs a new listener with the provided window and callback
		 * @param win
		 * @param asyncCallback
		 */
		private UpdateUserButtonListener(EmployeeProfileWindow win, AsyncCallback<Object> asyncCallback) {
			this.win = win;
			this.callback = asyncCallback;
		}

		/**
		 * Action to occur if the specified button is clicked. For update, will make call to update an employee to the database
		 * 
		 * @param button Button to click upon
		 * @param e Event to call upon
		 */
		public void onClick(Button button, EventObject e) {
			final GwtUser usr = myGrid.getUserById(myGrid.getSelectionModel().getSelected().getAsString(EMPLOYEE_ID_RECORDDEF));

			String realFirst = usr.getProfile().getFirstName().trim();
			String realLast = usr.getProfile().getLastName().trim();
			String tempFirst = win.getEmployeeProfilePanel().getUserProfile().getFirstName().trim();
			String tempLast = win.getEmployeeProfilePanel().getUserProfile().getLastName().trim();

			if (!realFirst.equals(tempFirst) || !realLast.equals(tempLast)) {
				Log.info("not a match");
//				ServiceManager.getEmailServiceAsync().sendEmailChangeUsername(usr, new AsyncCallback<String>() {
//					@Override
//					public void onFailure(Throwable caught) {
//						MessageBox.alert("Error Updating Username");
//					}
//
//					@Override
//					public void onSuccess(String result) {				
							usr.setActive(true);
							usr.setProfile(win.getEmployeeProfilePanel().getUserProfile());
							usr.setPermission(win.getEmployeeProfilePanel().getPermission());
							ServiceManager.getManageEmployeesServiceAsync().updateUser(usr, callback);
//						    MessageBox.alert(result);				
//					}					
//				});

				
			}

		}
	}
	
	/**
	 * Inner button listener for the Reactivate Employee button
	 * @author Stephanie Long
	 */
	private final class ReactivateUserButtonListener extends ButtonListenerAdapter {
		private final EmployeeProfileWindow win;
		private final AsyncCallback<Object> callback;

		/**
		 * Constructs a new listener with the provided window and callback
		 * @param win
		 * @param asyncCallback
		 */
		private ReactivateUserButtonListener(EmployeeProfileWindow win, AsyncCallback<Object> asyncCallback) {
			this.win = win;
			this.callback = asyncCallback;
		}

		/**
		 * Action to occur if the specified button is clicked. For reactivate, will make call to update an employee's
		 * status to active in the database. Reactivates an inactive user.
		 * 
		 * @param button Button to click upon
		 * @param e Event to call upon
		 */
		public void onClick(Button button, EventObject e) {
			GwtUser usr = myGrid.getUserById(myGrid.getSelectionModel().getSelected().getAsString(EMPLOYEE_ID_RECORDDEF));

			usr.setActive(true);
			usr.setProfile(win.getEmployeeProfilePanel().getUserProfile());
			System.out.println(usr.getUserID());
			usr.getProfile().setUserID(usr.getUserID());
			ServiceManager.getManageEmployeesServiceAsync().updateUser(usr, callback);
		}
	}
	
	/**
	 * Inner button listener for the Inactivate Employee button
	 * @author Stephanie Long
	 */
	private class InactivateUserButtonListener extends ButtonListenerAdapter {
		private final EmployeeProfileWindow win;
		private final AsyncCallback<Object> callback;

		/**
		 * Constructs a new listener with the provided window and callback
		 * @param win
		 * @param asyncCallback
		 */
		private InactivateUserButtonListener(EmployeeProfileWindow win, AsyncCallback<Object> asyncCallback) {
			this.win = win;
			this.callback = asyncCallback;
		}

		/**
		 * Action to occur if the specified button is clicked. For inactivate, will make call to update an employee's
		 * status to inactive in the database. Inactivates an active user.
		 * 
		 * @param button Button to click upon
		 * @param e Event to call upon
		 */
		public void onClick(Button button, EventObject e) {
			GwtUser usr = myGrid.getUserById(myGrid.getSelectionModel().getSelected().getAsString(EMPLOYEE_ID_RECORDDEF));

			usr.setActive(false);
			usr.setProfile(win.getEmployeeProfilePanel().getUserProfile());
			System.out.println(usr.getUserID());
			usr.getProfile().setUserID(usr.getUserID());

			ServiceManager.getManageEmployeesServiceAsync().updateUser(usr, callback);
		}
	}
	
	/**
	 * Inner button listener for the Delete Employee button
	 * @author Stephanie Long
	 */
	private final class DeleteUserButtonListener extends ButtonListenerAdapter {
		private final EmployeeProfileWindow win;
		private final AsyncCallback<Boolean> callback;

		/**
		 * Constructs a new listener with the provided window and callback
		 * @param win
		 * @param asyncCallback
		 */
		private DeleteUserButtonListener(EmployeeProfileWindow win, AsyncCallback<Boolean> asyncCallback) {
			this.win = win;
			this.callback = asyncCallback;
		}

		/**
		 * Action to occur if the specified button is clicked. For reactivate, will make call to update an employee's
		 * status to active in the database. Reactivates an inactive user.
		 * 
		 * @param button Button to click upon
		 * @param e Event to call upon
		 */
		public void onClick(Button button, EventObject e) {
			GwtUser usr = myGrid.getUserById(myGrid.getSelectionModel().getSelected().getAsString(EMPLOYEE_ID_RECORDDEF));

			ServiceManager.getManageEmployeesServiceAsync().deleteUser(usr, callback);
		}
	}

	private final RecordDef recordDef;
	private static final String SIMPLE_CLASSNAME = "EmployeeGrid";

	/**
	 * Construct the grid. Construct the store, record definitions, and column configuration for the grid.
	 * Populate the grid with employee data. Group the data by status.
	 */
	public EmployeeGrid() {
		super();
		Log.info("Constructing " + SIMPLE_CLASSNAME);

		userList = new LinkedList<GwtUser>();

		recordDef = new RecordDef(new FieldDef[] { new StringFieldDef(LASTNAME_RECORDDEF), new StringFieldDef(FIRSTNAME_RECORDDEF),
				new StringFieldDef(EMPLOYEE_ID_RECORDDEF), new StringFieldDef(STATUS_RECORDDEF)});
		
		Log.trace("Setting " + SIMPLE_CLASSNAME + " GroupingStore");
		GroupingStore store = new GroupingStore(recordDef);
		SortState initialSortState = new SortState("status", SortDir.ASC );
		store.setInitialSortState(initialSortState);
		store.setGroupField("status");
		setStore(store);

		setColumnModel(new ColumnModel(new ColumnConfig[] {
				// column ID is company which is later used in
				new ColumnConfig("Last Name", LASTNAME_RECORDDEF, 300, true, null, LASTNAME_RECORDDEF),
				new ColumnConfig("First Name", FIRSTNAME_RECORDDEF, 300, true, null, FIRSTNAME_RECORDDEF),
				//new ColumnConfig("ID", EMPLOYEE_ID_RECORDDEF, 40, true, null, EMPLOYEE_ID_RECORDDEF),
				new ColumnConfig("Status", STATUS_RECORDDEF, 100, true, null, STATUS_RECORDDEF) 
		}));

		getEmployeeData();
		
		Log.trace(SIMPLE_CLASSNAME + " Setting GroupingView");
		GroupingView gridView = new GroupingView();
		gridView.setForceFit(true);  
		gridView.setGroupByText(STATUS_RECORDDEF);
		gridView.setGroupTextTpl("{text} ({[values.rs.length]} {[values.rs.length > 1 ?   \"Items\" : \"Item\"]})");  
//		gridView.updateHeaderSortState();
		setView(gridView); 

		Log.trace(SIMPLE_CLASSNAME + "setting other settings");
		setFrame(true);
		setStripeRows(true);  
		setHeight(350);
		setWidth(TurboPlan.STANDARD_PAGE_WIDTH);
		setTitle("Employees");

		addGridRowListener(new GridRowListenerAdapter() {
			/**
			 * Listener for double click on the grid. Begins read and update employee action.
			 */
			public void onRowDblClick(GridPanel grid, int rowIndex, EventObject e) {
				Log.debug(SIMPLE_CLASSNAME + " row double click listener triggered");
				
				Button update = new Button("Update Employee");
				Button cancelBtnOnUp = new Button("Cancel");
				Button inactivateOnUp = new Button("Inactivate Employee");
				Button reactivate = new Button("Reactivate Employee");
				Button deleteButton = new Button("Delete Employee");
				
				
				final EmployeeProfileWindow win = new EmployeeProfileWindow("Selected Employee Profile", new Button[] { update, cancelBtnOnUp,
						inactivateOnUp, reactivate, deleteButton  });
				win.setCloseAction(Window.HIDE); 
				win.setPlain(true);
				win.setModal(true);
				win.setVisible(true);

				final GwtUser usr = myGrid.getUserById(grid.getSelectionModel().getSelected().getAsString(EMPLOYEE_ID_RECORDDEF));
				if (usr.getPermission().size() > 2) {
					win.getEmployeeProfilePanel().setManager();
				}
				win.getEmployeeProfilePanel().setUserProfile(usr.getProfile());
		

				/**
				 * Add listener to update button
				 */
				update.addListener(new UpdateUserButtonListener(win, new AsyncCallback<Object>() {
					
					/**
					 * Handles if update fails on server
					 */
					public void onFailure(Throwable caught) {
						MessageBox.alert(caught.getMessage());

					}

					/**
					 * Handles if update is successful on server. Clear grid and refresh employee data.
					 */
					public void onSuccess(Object result) {
				Log.trace("success update");
						MessageBox.alert("Updated Employee", "Changes saved successfully. Employee Updated.");
						myGrid.getStore().removeAll();
						myGrid.getStore().commitChanges();
						getEmployeeData();
						myGrid.getStore().commitChanges();
						win.close();
						
						myGrid.getStore().reload();
						
						myGrid.getStore().sort("status", SortDir.ASC);
					}
				}));

				/**
				 * Add listener to cancel button
				 */
				cancelBtnOnUp.addListener(new ButtonListenerAdapter() {
					
					/**
					 * Handles if cancel button clicked. Close the window
					 */
					public void onClick(Button button, EventObject e) {
						win.close();
					}
				});
				
				/**
				 * Add listener to inactivate button
				 */
				inactivateOnUp.addListener(new InactivateUserButtonListener(win, new AsyncCallback<Object>() {
					
					/**
					 * Handles if inactivate user call fails on server
					 */
					public void onFailure(Throwable caught) {
						MessageBox.alert(caught.getMessage());

					}

					/**
					 * Handles if inactivate user is successful on server. Clear grid and refresh employee data.
					 */
					public void onSuccess(Object result) {
						MessageBox.alert("Updated Employee", "Changes saved successfully. Employee Updated.");
						myGrid.getStore().removeAll();
						myGrid.getStore().commitChanges();
						getEmployeeData();
						myGrid.getStore().commitChanges();
						win.close();
						
						myGrid.getStore().reload();
					}
				}));
				
				/**
				 * Add listener to reactivate button
				 */
				reactivate.addListener(new ReactivateUserButtonListener(win, new AsyncCallback<Object>() {
							
							/**
							 * Handles if reactivate user call fails on server
							 */
							public void onFailure(Throwable caught) {
								MessageBox.alert(caught.getMessage());

								myGrid.getStore().removeAll();
								myGrid.getStore().commitChanges();
								getEmployeeData();
								myGrid.getStore().commitChanges();
								win.close();
								myGrid.getStore().reload();
							}

							/**
							 * Handles if reactivate user is successful on server. Clear grid and refresh employee data.
							 */
							public void onSuccess(Object result) {
								//MessageBox.alert("Saved New Employee", "Changes saved successfully. Employee created.");
								myGrid.getStore().removeAll();
								myGrid.getStore().commitChanges();
								getEmployeeData();
								myGrid.getStore().commitChanges();
								win.close();
								
								myGrid.getStore().reload();
							}
						}));
				

				/**
				 * Add listener to delete button
				 */
				deleteButton.addListener(new DeleteUserButtonListener(win, new AsyncCallback<Boolean>() {
					
					/**
					 * Handles if update fails on server
					 */
					public void onFailure(Throwable caught) {
						MessageBox.alert("Unsuccessful Delete");

					}

					/**
					 * Handles if update is successful on server. Clear grid and refresh employee data.
					 */
					public void onSuccess(Boolean result) {
				Log.trace("successful Delete");
						MessageBox.alert("Employee Delete", "Changes saved successfully. Employee Deleted.");
						myGrid.getStore().removeAll();
						myGrid.getStore().commitChanges();
						getEmployeeData();
						myGrid.getStore().commitChanges();
						win.close();
						
						myGrid.getStore().reload();
						
						myGrid.getStore().sort("status", SortDir.ASC);
					}
				}));
	
				win.show();
			}
			
			
		});

		/**
		 * Set up the toolbar at the top of the employee grid. Add the "Add employee" button
		 */
		setTopToolbar(new Button[] { new Button("Add Employee", new ButtonListenerAdapter() {
			
			/**
			 * Handles if the add button clicked. Open a new Employee profile window
			 */
			public void onClick(Button button, EventObject e) {
				Button addOnAdd = new Button("Add New Employee");
				Button cancelBtnOnAdd = new Button("Cancel");
				Button addAnotherOnAdd = new Button("Save New and Add Another");

				/**
				 * Create a new employee profile window
				 */
				final EmployeeProfileWindow win = new EmployeeProfileWindow("Add Employee",
						new Button[] { addOnAdd, cancelBtnOnAdd, addAnotherOnAdd});

				/**
				 * Add listener to add employee button
				 */
				addOnAdd.addListener(new AddUserButtonListener(win, new AsyncCallback<Object>() {
					/**
					 * Handles if add new user call fails on server
					 */
					public void onFailure(Throwable caught) {
						MessageBox.alert(caught.getMessage());

						myGrid.getStore().removeAll();
						myGrid.getStore().commitChanges();
						getEmployeeData();
						myGrid.getStore().commitChanges();
						win.close();
						
						myGrid.getStore().reload();
					}
					
					/**
					 * Handles if add user is successful on server. Clear grid and refresh employee data.
					 */
					public void onSuccess(Object result) {
						//MessageBox.alert("Saved New Employee", "Changes saved successfully. Employee created.");
						myGrid.getStore().removeAll();
						myGrid.getStore().commitChanges();
						getEmployeeData();
						myGrid.getStore().commitChanges();
						win.close();
						
						myGrid.getStore().reload();
					}
				}));

				/**
				 * Add listener to add employee button
				 */
				cancelBtnOnAdd.addListener(new ButtonListenerAdapter() {
					public void onClick(Button button, EventObject e) {
						win.close();
					}
				});

				/**
				 * Add listener to add another employee button
				 */
				addAnotherOnAdd.addListener(new AddUserButtonListener(win, new AsyncCallback<Object>() {
					
					/**
					 * Handles if add new user call fails on server
					 */
					public void onFailure(Throwable caught) {
						MessageBox.alert(caught.getMessage());
						myGrid.getStore().removeAll();
						myGrid.getStore().commitChanges();
						getEmployeeData();
						myGrid.getStore().commitChanges();
						win.getEmployeeProfilePanel().clearPanel();
						myGrid.getStore().reload();
					}

					/**
					 * Handles if add user is successful on server. Clear grid and refresh employee data and clear panel
					 * to create another new user
					 */
					public void onSuccess(Object result) {

						//MessageBox.alert("Saved New Employee", "Changes saved successfully. Employee created.");
						myGrid.getStore().removeAll();
						myGrid.getStore().commitChanges();
						getEmployeeData();
						myGrid.getStore().commitChanges();
						win.getEmployeeProfilePanel().clearPanel();
						myGrid.getStore().reload();
					}
				}));

				win.show();
		Log.trace("Showed Window");
			}
		}) });

		Log.debug("Done constructing " + SIMPLE_CLASSNAME);
	}

	/**
	 * Get the employee data of the company whose user is logged into TurboPlan
	 */
	private void getEmployeeData() {
		/**
		 * Gets employees from database
		 */
		ServiceManager.getManageEmployeesServiceAsync().getUsers(TurboPlan.loggedInUser, new AsyncCallback<List<GwtUser>>() {
			/**
			 * Handles if retrieve users call fails on server
			 */
			public void onFailure(Throwable caught) {
				Log.error("Call to server to fetch employee data failed", caught);
			}

			/**
			 * Handles if add user is successful on server. Add the user to a group of users to be added on the grid
			 */
			public void onSuccess(List<GwtUser> result) {
				Log.debug("Call to server to fetch employee data successed");

				addUsers(result);
			}
		});

	};

	/**
	 * Get the user associated with the specified id
	 * 
	 * @param id The id which to get the associated user of
	 * @return The user associated with the specified id
	 */
	private GwtUser getUserById(String id) {
		for (GwtUser usr : userList) {
			if (usr.getUserID().toString().equals(id)) {
				return usr;
			}
		}

		return null;
	}

	/**
	 * Get the record associated with the specified id
	 * 
	 * @param id The specified id to get the record of 
	 * @return The record associated with the specified id
	 */
	private Record getRecordByUserId(String id) {
		return myGrid.getStore().query(EmployeeGrid.EMPLOYEE_ID_RECORDDEF, id)[0];
	}

	/**
	 * Add a user as a record to the grid
	 * @param usr The user to add to the grid's records
	 */
	private void addUser(GwtUser usr) {
		GwtUserProfile profile = usr.getProfile();

		myGrid.userList.add(usr);

		myGrid.getStore().add(recordDef.createRecord(new Object[] { profile.getLastName(), profile.getFirstName(), usr.getUserID(), usr.getStatus() }));

		myGrid.getStore().commitChanges();
	}

	/**
	 * Add the specified list of users to the grid
	 * @param users The list of users to add to the grid
	 */
	private void addUsers(List<GwtUser> users) {
		for (GwtUser usr : users) {
			Log.trace("Adding " + usr.getProfile().getFirstName() + " to EmployeeGrid");
			addUser(usr);
		}
	}

	/**
	 * Remove the associated user from the grid by the specified id
	 * @param id The id to remove the associated user of
	 */
	private void removeUserByUserId(String id) {
		myGrid.getStore().remove(getRecordByUserId(id));
	}
}
