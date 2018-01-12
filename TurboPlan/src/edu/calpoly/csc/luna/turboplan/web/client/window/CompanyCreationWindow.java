package edu.calpoly.csc.luna.turboplan.web.client.window;

import edu.calpoly.csc.luna.turboplan.web.client.TurboPlan;
import edu.calpoly.csc.luna.turboplan.web.client.widget.CompanyPanel;

public class CompanyCreationWindow extends BaseWindow {
	private static CompanyCreationWindow singleton = new CompanyCreationWindow();
    private final static int winWidth = TurboPlan.STANDARD_PAGE_WIDTH;
    private final static int winHeight = 400;
    
    
	public static CompanyCreationWindow getInstance() {
//		singleton.setTitle("Company");
		singleton.setWidth(winWidth);
		singleton.setHeight(winHeight);
		singleton.setPosition(40, 40);
		return singleton;
	}
	
	private CompanyCreationWindow() {
		add(new CompanyPanel());
	}
}
