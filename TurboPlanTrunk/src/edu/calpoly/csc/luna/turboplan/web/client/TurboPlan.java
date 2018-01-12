package edu.calpoly.csc.luna.turboplan.web.client;

import java.util.EnumSet;
import java.util.Set;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import edu.calpoly.csc.luna.turboplan.web.client.model.GwtUser;
import edu.calpoly.csc.luna.turboplan.web.client.page.TurboPlanPage;
import edu.calpoly.csc.luna.turboplan.web.client.window.CompanyCreationWindow;
import edu.calpoly.csc.luna.turboplan.web.client.window.LoginWindow;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class TurboPlan implements EntryPoint {
	public static final int STANDARD_PAGE_WIDTH = 800;
	public static final TurboPlanConstants I18N_CONSTANT = GWT.create(TurboPlanConstants.class);

	private static TurboPlanMainUI mainUI;
	public static GwtUser loggedInUser;

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		LoginWindow.getInstance().show();
//		CompanyCreationWindow.getInstance().show();
	}

	public static void buildTurboPlanUI() {
		mainUI = new TurboPlanMainUI();

		mainUI.show();
	}

	public static void destroyTurboPlanUI() {
		Log.info("Destroying TurboPlan UI");
		for (TurboPlanPage page : TurboPlanPage.values()) {
			Log.trace("Destroying " + page.name());
			page.getPage().destroy();
		}

		mainUI.hide();
		Log.debug("Done destroying TurboPlan UI");
	}

	public static void swapToPage(TurboPlanPage page) {
		mainUI.swapToPage(page);
	}

	private static class TurboPlanMainUI {
		private VerticalPanel mainPanel;
		private TurboPlanPage currentPage;

		private TurboPlanMainUI() {
			mainPanel = new VerticalPanel();
			
//			Hyperlink logout = new Hyperlink("Logout", true, "");
//			
//			String url = Window.Location.getHref();
//			
//			String html = "<a href=\"" + url + "\">Logout</a>";
//		
//			logout.setHTML(html);

			Button logout = new Button("Logout", new ClickListener() {
				public void onClick(Widget sender) {
//					TurboPlan.destroyTurboPlanUI();
//					TurboPlan.loggedInUser = null;
//					LoginWindow.getInstance().show();
					Window.Location.reload();
				}
			});

			HorizontalPanel hPanel = new HorizontalPanel();
			hPanel.setWidth("100%");
			hPanel.add(new Image("images/product_logo.png"));
			hPanel.add(logout);

			HorizontalPanel navBar = new HorizontalPanel();
			com.gwtext.client.widgets.Panel rootPagePanel = new com.gwtext.client.widgets.Panel();

			for (TurboPlanPage page : getPageSetFromPermission(TurboPlan.loggedInUser.getPermission())) {
				navBar.add(page.getLinkButton());
				rootPagePanel.add(page.getPage());
			}

			mainPanel.add(hPanel);
			mainPanel.add(navBar);
			mainPanel.add(rootPagePanel);

			RootPanel.get().add(mainPanel);
		}

		private EnumSet<TurboPlanPage> getPageSetFromPermission(Set<String> set) {
			Log.debug("Checking logged in user's permissions");
			EnumSet<TurboPlanPage> ret = EnumSet.noneOf(TurboPlanPage.class);

			if (set.contains(GwtUser.Permission.MySchedule.name())) {
				Log.trace("This user has " + GwtUser.Permission.MySchedule.name() + " permission");
				ret.add(TurboPlanPage.MySchedule);
			}
			if (set.contains(GwtUser.Permission.ManageTasks.name())) {
				Log.trace("This user has " + GwtUser.Permission.ManageTasks.name() + " permission");
				ret.add(TurboPlanPage.ManageTasks);
			}
			if (set.contains(GwtUser.Permission.ManageEmployees.name())) {
				Log.trace("This user has " + GwtUser.Permission.ManageEmployees.name() + " permission");
				ret.add(TurboPlanPage.ManageEmployees);
			}
			/*if (set.contains(GwtUser.Permission.ManageSchedule.name())) {
				Log.trace("This user has " + GwtUser.Permission.ManageSchedule.name() + " permission");
				ret.add(TurboPlanPage.ManageSchedule);
			}*/
			if (set.contains(GwtUser.Permission.Preferences.name())) {
				Log.trace("This user has " + GwtUser.Permission.Preferences.name() + " permission");
				ret.add(TurboPlanPage.Preferences);
			}

			return ret;
		}

		private void hide() {
			mainPanel.setVisible(false);
		}

		private void show() {
			mainPanel.setVisible(true);
		}

		public void swapToPage(TurboPlanPage page) {
			Log.info("Swapping to " + page.name());
			
			if (!TurboPlan.loggedInUser.getPermission().contains(page.name())) {
				Log.warn("Logged in user does not has permission for " + page.name());
				return;
			}

			if (!page.getPage().isConstructed()) {
				page.getPage().construct();
			}

			if (currentPage != null) {
				currentPage.getPage().hide();
			}

			currentPage = page;
			currentPage.getPage().show();
		}
	}
}
