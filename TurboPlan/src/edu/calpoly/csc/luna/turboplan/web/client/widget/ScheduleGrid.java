package edu.calpoly.csc.luna.turboplan.web.client.widget;

import java.util.Date;
import java.util.Set;

import com.allen_sauer.gwt.log.client.Log;
import com.gwtext.client.data.Record;
import com.gwtext.client.data.RecordDef;
import com.gwtext.client.data.Store;
import com.gwtext.client.widgets.MessageBox;
import com.gwtext.client.widgets.grid.ColumnModel;
import com.gwtext.client.widgets.grid.GridPanel;
import edu.calpoly.csc.luna.turboplan.web.client.model.GwtTask;

public abstract class ScheduleGrid extends GridPanel {
	private final RecordDef recordDef;
    private final ScheduleGrid myGrid = this;
	public static Set<GwtTask> res = null;
    
    public ScheduleGrid(){
    	recordDef = new RecordDef(null);
    }
    
	protected ScheduleGrid(RecordDef rDef, final ColumnModel cModel, final int width) {
		recordDef = rDef;

//		getAllAssignedTasksForCurUsr(); //Loads the users tasks.
		
		setStore(new Store(recordDef));
		setColumnModel(cModel);
		
		setFrame(true);
		setStripeRows(true);
		setHeight(330);
		setWidth(width);
		
	}
		
		
	protected Record createRecord(Object[] obj) {
		if (obj.length != recordDef.getFields().length) {
			MessageBox.alert("Error: ScheduleGrid.createRecord: obj.length != field length");
			return null;
		}
		
		return recordDef.createRecord(obj);
	}
	
	public void fillDay(String [] stin){
		for(int i = 0; i < 48; i++){
			stin[i]= "-";
		}
	}
		
	@SuppressWarnings("deprecation")
	public boolean isSameDay(Date a, Date b){
		if(a == null || b == null){
			return false;
		}
		
		if(a.getYear() == b.getYear() 
				&& a.getMonth() == b.getMonth()
				&& a.getDate() == b.getDate()){
			return true;
		}
		return false;
	}
	
	  /**
     * Get Task in Grid By ID.
     * @param title
     * @return
     * @author Paul De Leon
     */
    public GwtTask getTaskById(long id) {
        Log.trace(" Id = " + id);
       	for (GwtTask task : res) {
            Log.trace("tasktitle  = " +task.getTitle()+", taskid = "+task.getTaskID());
            
               if (task.getTaskID() == id) {
                   	Log.trace("got task with id: " + task.getTaskID());
                   	return task;
                }
       	}

		return null;
   }

}
