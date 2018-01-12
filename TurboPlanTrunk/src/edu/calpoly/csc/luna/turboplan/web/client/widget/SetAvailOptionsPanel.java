package edu.calpoly.csc.luna.turboplan.web.client.widget;

import java.util.ArrayList;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.MessageBox;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.form.Checkbox;
import com.gwtext.client.widgets.form.event.CheckboxListenerAdapter;
import com.gwtext.client.widgets.layout.FormLayout;

import edu.calpoly.csc.luna.turboplan.web.client.TurboPlan;
import edu.calpoly.csc.luna.turboplan.web.client.model.GwtUserProfile;
import edu.calpoly.csc.luna.turboplan.web.client.service.ServiceManager;
import edu.calpoly.csc.luna.turboplan.web.client.widget.SetAvailGrid.Day;

public class SetAvailOptionsPanel extends Panel { //TreePanel {
    private static final String SIMPLE_CLASSNAME = "AvailOptionsPanel";
    private static final String title = "Block Off Times";
    private static final int defaultPageWidth = (int) (TurboPlan.STANDARD_PAGE_WIDTH * .20);
    private static final int defaultPageHeight = 500;
    private static final FormLayout layout = new FormLayout();
    private static final int numDaysInWeek = 7;
    
    Button saveAvailBtn;
    Checkbox mwfAllDayChkbox;
    Checkbox trAllDayChkbox;
    Checkbox earlyMorningChkbox;
    Checkbox morningChkbox;
    Checkbox afternoonChkbox;
    Checkbox eveningChkbox;
    Checkbox lateNightChkbox;
    
    private SetAvailGrid availGrid;
 
    public SetAvailOptionsPanel(SetAvailGrid availGrid) {
        super();
        Log.info(SIMPLE_CLASSNAME + " is constructing...");
        
        this.availGrid = availGrid;
        setupAttributes();
        setupButtons();
        
        Log.debug("...Finished constructing " + SIMPLE_CLASSNAME + "!");
    }
    
    private void setupAttributes() {
        this.setTitle(title);
        this.setLayout(layout);
        this.setWidth(defaultPageWidth);
    }
    
    private void setupButtons() {
        saveAvailBtn = new Button("Save Availability");
        
        mwfAllDayChkbox = new Checkbox("MWF \t(All Day)");
        trAllDayChkbox = new Checkbox("TR \t(All Day)");
        earlyMorningChkbox = new Checkbox("Early Morning \t(2AM-7AM)");
        morningChkbox = new Checkbox("Morning \t(7AM-12PM)");
        afternoonChkbox = new Checkbox("Afternoon \t(12PM-5PM)");
        eveningChkbox = new Checkbox("Evening \t(5PM-10PM)");
        lateNightChkbox = new Checkbox("Late Night \t(10PM-2AM)");
        
        ArrayList<Checkbox> timeBoxes = new ArrayList<Checkbox>();    
        
        setupListeners();
     
        timeBoxes.add(mwfAllDayChkbox);
        timeBoxes.add(trAllDayChkbox);
        timeBoxes.add(earlyMorningChkbox);
        timeBoxes.add(morningChkbox);
        timeBoxes.add(afternoonChkbox);
        timeBoxes.add(eveningChkbox);
        timeBoxes.add(lateNightChkbox);

        
        for(int i=0; i < timeBoxes.size(); i++) {
            Checkbox tmpChkbox = timeBoxes.get(i);
            
            tmpChkbox.setHideLabel(true);
            this.add(tmpChkbox);
        }
        
        this.add(saveAvailBtn);
    }
    
    private void setupListeners() {
        saveAvailBtn.addListener(new ButtonListenerAdapter() {
            public void onClick(Button button, EventObject e) {
                GwtUserProfile usrProfile = TurboPlan.loggedInUser.getProfile();
                usrProfile.setAvailability(availGrid.getAvailabilities());
                
                ServiceManager.getUserServiceAsync().updateUser(TurboPlan.loggedInUser, new AsyncCallback<Object>() {
                    public void onFailure(Throwable caught) {
                        Log.error("Failed to save availability...", caught);
                    }
    
                    public void onSuccess(Object result) {
                        Log.trace("Successfully Saved Availability!");
                        MessageBox.alert("Saved Availability");
                    }
                });
            }
        });
        
        //12AM-12PM
        mwfAllDayChkbox.addListener(new CheckboxListenerAdapter() {
            public void onCheck(Checkbox field, boolean checked) {
                Log.trace("MWF checked...");
                
                Day days[] = { Day.MON, Day.WED, Day.FRI };
                
                availGrid.checkDays(days, checked, 0, 48);
                
                Log.trace("...Finished MWF checked!");
            }
        });
        
        //12AM-12PM
        trAllDayChkbox.addListener(new CheckboxListenerAdapter() {
            public void onCheck(Checkbox field, boolean checked) {
                Log.trace("TR checked...");
                
                Day days[] = { Day.TUES, Day.THURS };
                
                availGrid.checkDays(days, checked, 0, 48);
                
                Log.trace("...Finished TR checked!");
            }
        });
        
        //2AM-7AM
        earlyMorningChkbox.addListener(new CheckboxListenerAdapter() {
            public void onCheck(Checkbox field, boolean checked) {
                Log.trace("EarlyMorning checked...");
                
                Day days[] = new Day[numDaysInWeek];
                
                int i = 0;
                for(Day day : Day.values()) {
                    days[i++] = day;
                }
                
                availGrid.checkDays(days, checked, 4, 4+10);
                
                Log.trace("...Finished EarlyMorning checked!");
            }
        });
        
        //7AM-12PM
        morningChkbox.addListener(new CheckboxListenerAdapter() {
            public void onCheck(Checkbox field, boolean checked) {
                Log.trace("Morning checked...");
                
                Day days[] = new Day[numDaysInWeek];
                
                int i = 0;
                for(Day day : Day.values()) {
                    days[i++] = day;
                }
                
                availGrid.checkDays(days, checked, 14, 14+10);
                
                Log.trace("...Finished Morning checked!");
            }
        });
        
        //12PM-5PM
        afternoonChkbox.addListener(new CheckboxListenerAdapter() {
            public void onCheck(Checkbox field, boolean checked) {
                Log.trace("Afternoon checked...");
                
                Day days[] = new Day[numDaysInWeek];
                
                int i = 0;
                for(Day day : Day.values()) {
                    days[i++] = day;
                }
                
                availGrid.checkDays(days, checked, 24, 24+10);
                
                Log.trace("...Finished Afternoon checked!");
            }
        });
        
        //5PM-10PM
        eveningChkbox.addListener(new CheckboxListenerAdapter() {
            public void onCheck(Checkbox field, boolean checked) {
                Log.trace("Evening checked...");
                
                Day days[] = new Day[numDaysInWeek];
                
                int i = 0;
                for(Day day : Day.values()) {
                    days[i++] = day;
                }
                
                availGrid.checkDays(days, checked, 34, 34+10);
                
                Log.trace("...Finished Evening checked!");
            }
        });
        
        //10PM-2PM
        lateNightChkbox.addListener(new CheckboxListenerAdapter() {
            public void onCheck(Checkbox field, boolean checked) {
                Log.trace("LateNight checked...");
                
                Day days[] = new Day[numDaysInWeek];
                
                int i = 0;
                for(Day day : Day.values()) {
                    days[i++] = day;
                }
                
                availGrid.checkDays(days, checked, 44, 44+9);
                availGrid.checkDays(days, checked, 0, 0+4);
                
                Log.trace("...Finished LateNight checked!");
            }
        });
    }
}
