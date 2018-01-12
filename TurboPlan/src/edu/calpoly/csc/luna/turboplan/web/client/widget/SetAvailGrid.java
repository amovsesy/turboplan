package edu.calpoly.csc.luna.turboplan.web.client.widget;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.Element;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.core.TextAlign;
import com.gwtext.client.data.FieldDef;
import com.gwtext.client.data.RecordDef;
import com.gwtext.client.data.Store;
import com.gwtext.client.data.StringFieldDef;
import com.gwtext.client.widgets.grid.ColumnConfig;
import com.gwtext.client.widgets.grid.ColumnModel;
import com.gwtext.client.widgets.grid.GridPanel;
import com.gwtext.client.widgets.grid.event.GridCellListenerAdapter;

import edu.calpoly.csc.luna.turboplan.web.client.TurboPlan;


/**
 * 
 * @author Paul De Leon
 */
public class SetAvailGrid extends GridPanel {     
    
    public static enum Day {
        SUN("SUN", 1), 
        MON("MON", 2), 
        TUES("TUES", 3),
        WED("WED", 4),
        THURS("THURS", 5),
        FRI("FRI", 6),
        SAT("SAT", 7);
        
        private final String name;
        private final int col;
        
        Day(String name, int col) {
            this.name = name;
            this.col = col;
        }
        
        public int getColNum() {
            return col;
        }
        
        public String toString() {
            return name;
        }
    };
    private static final String SIMPLE_CLASSNAME = "SetAvailGridPanel";
    
//    private static final int defaultPageWidth = TurboPlan.STANDARD_PAGE_WIDTH * .20);
//    private static final int defaultPageHeight = 500;
    
    private static final String avail = "Avail";
    private static final String unavail = "-";
    private static final String timeColName = "Time";
    private static final String days[] = { "SUN", "MON", "TUES", "WED", "THURS", "FRI", "SAT" };
    private static final int timeColWidth = 70;
    private static final int dayColWidth = 60;
    
    private static final int timeCol = 1;    // Column for times
    private static final int dayCols = 7;    // 7 days in a week
    private static final int timeRows = 48;  // 12:00am - 12:00pm with intervals of 30min = 48 entries
    
    private static final int cols = timeCol + dayCols;
    private static final int rows = timeRows;
    
    private String[][] recordData = new String[rows][cols];     // 8x48
    private boolean[][] boolData = new boolean[rows][dayCols];  // 7x48
    RecordDef recordDef; 
    private ColumnConfig columns[];
    private Store store;
    private ColumnModel columnModel;
    
    /**
     * @author Paul De Leon
     */
    public SetAvailGrid() {
        super();
        Log.info(SIMPLE_CLASSNAME + " is constructing...");        
        
        setupGrid();
        setupAttributes();
        setupListeners();
        
        
        boolean tmpAvail[][] = TurboPlan.loggedInUser.getProfile().getAvailability();
        if(tmpAvail == null) {
        	//If database availability is null, initialize boolData to all not available.
        	for(int i = 0; i < rows; i++) {
        		for(int j = 0; j < dayCols; j++) {
        		    boolData[i][j] = false;
        		}
        	}	
        }
        else {
        	//User availability from database
        	boolData = convertTo48x7Array(tmpAvail);
        }
                 
        setAvailabilities(boolData);
        addDataToStore();
        
        Log.debug("...Finished constructing " + SIMPLE_CLASSNAME + "!");
    }
    
    
    /**
     * @author Paul De Leon
     */
    private void setupGrid() {
        Log.trace("Setting up SetAvailGrid...");
        columns = new ColumnConfig[cols];
        FieldDef[] fields = new FieldDef[cols];  

        //SetUp Time ColumnConfig
        fields[0] = new StringFieldDef(timeColName);
        columns[0] = new ColumnConfig(timeColName, timeColName, timeColWidth);
        columns[0].setAlign(TextAlign.LEFT);
        
        //SetUp Days ColumnConfig
        for(int i=1; i <= days.length; i++) {
            String dayName = days[i-1];
            fields[i] = new StringFieldDef(dayName);
            columns[i] = new ColumnConfig(dayName, dayName, dayColWidth); //, false, new SetAvailRenderer(dayName));
            columns[i].setAlign(TextAlign.CENTER);
        }
        
        //Initialize
        recordDef = new RecordDef(fields);
        columnModel = new ColumnModel(columns);  
        store = new Store(recordDef); 
        
        
        setupAttributes();
        
        //Initialize data
        //Initialize Times
        String timeOfDay = "am";
        int timeIdx = 0;
        int hours = 0;
        
        for(int hourIdx = 0; hourIdx < rows/2; hourIdx++) {
            hours = hourIdx%12;
            
            if(hourIdx < 12) 
            {
                timeOfDay = "am";
            }
            else
            {
                timeOfDay = "pm";
            }
            
            if(hourIdx%12 == 0) {
                hours = 12;
            }
            
            recordData[timeIdx++][0] = String.valueOf(hours) + ":00" + timeOfDay;
            recordData[timeIdx++][0] = String.valueOf(hours) + ":30" + timeOfDay;
        }

        
        // Initialize time availabilities;  For all time intervals
        for(int rowIdx = 0; rowIdx < rows; rowIdx++) {
            // Go through all days
            for(int dayIdx = 1; dayIdx < cols; dayIdx++) {  
//                data[i][j] = new String("(" + i + "," + j + ")");
                recordData[rowIdx][dayIdx] = unavail;
            }  
        }
           

        addDataToStore();
        
        Log.trace("...Finished Setting up SetAvailGrid!");
    }
    
    
    /**
     * Load avail data to store
     */
    private void addDataToStore() {
        store.removeAll();
        
        //Add Data to store
        for(int i=0; i < rows; i++) {
            store.add(recordDef.createRecord(
                    new Object[] {
                          recordData[i][0],
                          recordData[i][1],
                          recordData[i][2],
                          recordData[i][3],
                          recordData[i][4],
                          recordData[i][5],
                          recordData[i][6],
                          recordData[i][7]
                    }
            ));
        }
        
        store.commitChanges();
    }
    
    
    private boolean[][] convertTo48x7Array(boolean[][] reverseArray) {
        Log.trace("Begin convertTo48x7Array...");
        boolean[][] availArr = new boolean[rows][dayCols];
        
        for(int row1 = 0; row1 < rows; row1++) {
            for(int col1 = 0; col1 < dayCols; col1++) {
                availArr[row1][col1] = reverseArray[col1][row1];
            }
        }
        
        Log.trace("...Finished convertTo48x7Array!");
        return availArr;
    }
    
    
    private boolean[][] convertTo7x48Array(boolean[][] reverseArray) {
        Log.trace("Begin convertTo7x48Array...");
        boolean[][] availArr = new boolean[dayCols][rows];
        
        for(int row1 = 0; row1 < rows; row1++) {
            for(int col1 = 0; col1 < dayCols; col1++) {
                availArr[col1][row1] = reverseArray[row1][col1];
            }
        }
        
        Log.trace("...Finished convertTo7x48Array!");
        return availArr;
    }
    
    
    private void setupAttributes() {
        this.setStore(store);
        this.setColumnModel(columnModel);
        this.setFrame(true);
        this.setStripeRows(true);
        this.setAutoScroll(true);
//        this.setHeight(defaultPageHeight);
    }
    
    
    /**
     * @author Paul De Leon
     */
    private void setupListeners() {
        Log.trace("Setting up SetAvailGrid Listeners...");
        this.addGridCellListener(new GridCellListenerAdapter() {
            public void onCellClick(GridPanel grid, int rowIndex, int colIndex, EventObject e) {
                Log.info("Clicked" + "(" + rowIndex + ", " + colIndex + ")");
                Element cell = grid.getView().getCell(rowIndex, colIndex);
                
                //Toggle availabilities
                if(cell.getInnerText().equals(unavail)) {
                    Log.info("HTML: " + cell.getInnerHTML());
                    recordData[rowIndex][colIndex] = avail;
                    boolData[rowIndex][colIndex-1] = true;
                    cell.setInnerText(avail);
                }
                else if(cell.getInnerText().equals(avail)) {
                    Log.info("HTML: " + cell.getInnerHTML());
                    recordData[rowIndex][colIndex] = unavail;
                    boolData[rowIndex][colIndex-1] = false;
                    cell.setInnerText(unavail);
                }
            }
        });
        Log.trace("Finished Setting up SetAvailGrid Listeners!");
    }
    
    /**
     * Sets the availabilities of the grid to given boolean array.
     * @param availArr
     * @return
     * @author Paul De Leon
     */
    public void setAvailabilities(boolean[][] availArr) {
        Log.trace("Setting avail array...");
        
        // check right size
        if(availArr.length != rows) {
            Log.error("Wrong length of array. Got " + 
                availArr.length + " expected " + Integer.toString(rows));
        }
        else {
            String available = "";
            
            for(int rowIdx = 0; rowIdx < rows; rowIdx++) {
                for(int dayIdx = 0; dayIdx < dayCols; dayIdx++) {
                    
                    //If true, set local data true and render on grid
                    if(availArr[rowIdx][dayIdx]) {
                        available = avail;
                    }
                    else {
                        available = unavail;
                    }
                    
                    recordData[rowIdx][dayIdx+1] = available;
                }
            }
        }
        
        Log.trace("Finished setting avail array!");
    }    
    
    
    public void rerenderGrid() {
        Log.trace("Begin rerender Grid...");
        
        Element cell;
        String available = "";
        
        for(int rowIdx = 0; rowIdx < rows; rowIdx++) {
            for(int dayIdx = 0; dayIdx < dayCols; dayIdx++) {
                cell = this.getView().getCell(rowIdx, dayIdx+1);
                
                //If true, set local data true and render on grid
                if(boolData[rowIdx][dayIdx]) {
                    available = avail;
                }
                else {
                    available = unavail;
                }

                cell.setInnerText(available);
            }
        }
        
        Log.trace("...Finished rerender Grid!"); 
    }
    
        
    
    
    /**
     * Gets a boolean array representation of availabilities.
     * @return
     * @author Paul De Leon
     */
    public boolean[][] getAvailabilities() {
        Log.trace("Getting avail array...");
        
        boolean[][] availArr = new boolean[rows][dayCols];
        boolean available = false;
        
        for(int rowIdx=0; rowIdx < rows; rowIdx++) {
            for(int dayIdx=0; dayIdx < dayCols; dayIdx++) {
                if(recordData[rowIdx][dayIdx+1].equals(avail)) {
                    available = true;
                }
                else {
                    available = false;
                }
                
                availArr[rowIdx][dayIdx] = available;
            }
        }
        
        Log.trace("...Finished getting avail array!");
        return convertTo7x48Array(availArr);
    }
    
 
    public void checkDays(Day[] days, boolean valueToSet, int rowBegin, int rowEnd) {
        Log.trace("Begin checkDays...");
        
        for(int count = 0; count < days.length; count++) {
            checkADay(days[count], valueToSet, rowBegin, rowEnd);
        }
        
        setAvailabilities(boolData);
        addDataToStore();
        rerenderGrid();
        Log.trace("...Finished checkDays!");
    }
    
    
    
    private void checkADay(Day day, boolean valueToSet, int rowBegin, int rowEnd) {
        Log.trace("Begin checkDay...");
        
        int col = day.getColNum();
        Log.info("    Day col = " + Integer.toString(col));
        
        for(int i=rowBegin; i < rowEnd && i < rows; i++) {
            boolData[i][col-1] = valueToSet;
        }
        
        Log.trace("...Finished checkDay!");
    }
}
