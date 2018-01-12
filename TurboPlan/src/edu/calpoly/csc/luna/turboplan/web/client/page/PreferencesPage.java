package edu.calpoly.csc.luna.turboplan.web.client.page;

import com.allen_sauer.gwt.log.client.Log;
import com.gwtext.client.widgets.Panel;
import edu.calpoly.csc.luna.turboplan.web.client.TurboPlan;
import edu.calpoly.csc.luna.turboplan.web.client.widget.SetAvailPanel;


public class PreferencesPage extends BasePage {
//    private static final String SIMPLE_CLASSNAME = "PreferencesPage";
	private static final String SIMPLE_CLASSNAME = "SetAvailabilityPage";
    private static final String pageTitle = "Set Availability";
    private static final int defaultPageWidth = TurboPlan.STANDARD_PAGE_WIDTH;
    private static final int defaultPageHeight = 370;
    
    private Panel mainPanel;

    public void onShow() {
    }

    public void construct() {
        Log.info("Constructing " + SIMPLE_CLASSNAME);
        
        super.clear();
        
        mainPanel = new Panel(pageTitle);
        
        mainPanel.setWidth(defaultPageWidth);
        mainPanel.setHeight(defaultPageHeight);
        mainPanel.add(new SetAvailPanel());
        
//        mainPanel.add(tabbedPanel);

        add((content = mainPanel));
        Log.debug("Done constructing " + SIMPLE_CLASSNAME);
    }
    
//    private void setupTabbedPanel() {
//        tabbedPanel.setWidth(defaultPageWidth);
//        tabbedPanel.setHeight(defaultPageHeight);
//        tabbedPanel.setTabPosition(Position.BOTTOM);
//        tabbedPanel.setMaskDisabled(true);
//    }
}
