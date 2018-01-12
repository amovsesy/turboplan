package edu.calpoly.csc.luna.turboplan.web.client.widget;

import com.gwtext.client.data.Record;
import com.gwtext.client.data.Store;
import com.gwtext.client.widgets.grid.CellMetadata;
import com.gwtext.client.widgets.grid.Renderer; 


public class AvialRender implements Renderer{

	private String field;
	
	public AvialRender(String field){
		this.field = field;
	}
	
	public String render(Object value, CellMetadata cellMetadata, Record record, int rowIndex, 
			int colNum, Store store) {

		if(record.getAsString(field).matches("false") 
				|| record.getAsString(field).matches("-") ) {
		//		|| record.getAsString(field).equals(null)){	
    		cellMetadata.setHtmlAttribute("style=\"background:whitesmoke;\"");
		}
		else if(record.getAsString(field).matches("2") 
				|| record.getAsString(field).equals(" ")){	
    		//cellMetadata.setHtmlAttribute("style=\"background:mediumspringgreen;\"");
     		cellMetadata.setHtmlAttribute("style=\"background:silver;\"");
		}	
		else{
			cellMetadata.setHtmlAttribute("style=\"background:paleturquoise;\"");
		}
		
		return value.toString();
	}

}
