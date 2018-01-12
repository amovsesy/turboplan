package edu.calpoly.csc.luna.turboplan.web.client.page;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

import edu.calpoly.csc.luna.turboplan.web.client.TurboPlan;

public abstract class BasePage extends SimplePanel {
	/**
	 * The content of this page
	 */
	protected Widget content;

	protected BasePage() {
		setWidth(TurboPlan.STANDARD_PAGE_WIDTH + "px");
	}

	/**
	 * Must sent 'content' This is used for page destruction when user logs out
	 */
	public abstract void construct();
	
	public void destroy() {
		if (isConstructed()) {
			content.setVisible(false);
			remove(content);
			content = null;
			Log.trace(this.getClass().getName().substring(47) + " is destroyed");
		}
	}
	
	public boolean isConstructed() {
		return content != null;
	}

	public void hide() {
		if (content != null)
			content.setVisible(false);
	}

	public void show() {
		if (content != null)
			content.setVisible(true);
	}
}
