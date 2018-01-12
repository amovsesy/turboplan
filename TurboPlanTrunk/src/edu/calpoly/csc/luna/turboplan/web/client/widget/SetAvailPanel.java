package edu.calpoly.csc.luna.turboplan.web.client.widget;

import com.allen_sauer.gwt.log.client.Log;
import com.gwtext.client.core.Position;
import com.gwtext.client.core.RegionPosition;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.layout.BorderLayout;
import com.gwtext.client.widgets.layout.BorderLayoutData;
import com.gwtext.client.widgets.layout.HorizontalLayout;

import edu.calpoly.csc.luna.turboplan.web.client.TurboPlan;

public class SetAvailPanel extends Panel{
    private static final String SIMPLE_CLASSNAME = "SetAvailPanel";
//    private static final int padding = 5;
//    private static final HorizontalLayout layout = new HorizontalLayout(padding);
    private static final String pageTitle = "My Availability";
    private static final BorderLayout layout = new BorderLayout();
    private SetAvailGrid availGrid;
    private SetAvailOptionsPanel optionsPanel;
    
    private static final int defaultPageWidth = TurboPlan.STANDARD_PAGE_WIDTH;
    private static final int defaultPageHeight = 340;
    
    public SetAvailPanel() {
        super();
        Log.info(SIMPLE_CLASSNAME + " is constructing...");  
        
        availGrid = new SetAvailGrid();
        optionsPanel = new SetAvailOptionsPanel(availGrid);
        
        setupAttributes();
        
        Log.debug("...Finished constructing " + SIMPLE_CLASSNAME + "!"); 
    }
    
    private void setupAttributes() {
        this.setLayout(layout);
        this.setTitle(pageTitle);
        this.add(optionsPanel, new BorderLayoutData(RegionPosition.WEST));
        this.add(availGrid, new BorderLayoutData(RegionPosition.CENTER));
        this.setFrame(true);
        this.setAutoScroll(true);
        this.setWidth(defaultPageWidth);
        this.setHeight(defaultPageHeight);
    }
    
    public SetAvailGrid getGridPanel() {
        return availGrid;
    }
}
