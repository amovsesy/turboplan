package edu.calpoly.csc.luna.turboplan.core.entity.part;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;

public class Availability implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6502165720776349153L;
	private boolean[][] avail;
	
	public Availability() {
		avail = new boolean[7][48];
	}
	
	public Availability(boolean [][] av){
		avail = av;
	}
	
	public boolean[][] getAvail() {
		return avail;
	}
	
	public void setAvail(boolean[][] avail) {
		this.avail = avail;
	}
	
	public boolean getAvail(int day, int time){
		return avail[day][time];
	}

	//1 = available
	//0 = nonavailable
	
	public void setAvail(int day, int time, boolean value){
		avail[day][time] = value;
	}
	
	//generate time intervals from grid
	@SuppressWarnings("deprecation")
	public Collection<TimeInterval> generateTimeInterval() {
		Collection<TimeInterval> times = new LinkedList<TimeInterval>();
		Date today = new Date();
		int day = 0;
		
		Date start = new Date();
		Date end = new Date();
		
		start.setDate(getSunDay(today.getDay(), today.getDate()));
		
		while(day != 7){
			if(avail[day][0]){
				start.setHours(0);
			}
	
			for(int j = 1; j < 48; j++){
				if(avail[day][j-1] != avail[day][j]){
					if(avail[day][j]){
						start.setHours(j/2);
							if(j%2 != 0){
								start.setMinutes(30);
							}
					}
					else{
						end.setHours(j/2);
						if(j%2 != 0){
							start.setMinutes(30);
						}
						
						//Adds a TimeInterval to the collect
						times.add(new TimeInterval(start, end));
					}
				}
			}
			if(avail[day][47]){
				end.setHours(24);
			}
			
			day++;
		}
	    return times;
	}
	
	public int getSunDay(int day, int date){
		while(day != 0){
			date --;
		}
		
		return date;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder("[avail = [");
		
		for (int i = 0; i < 7; i++) {
			switch(i) {
			case 0:
				builder.append("Sunday    ");
				break;
			case 1:
				builder.append("Monday    ");
				break;
			case 2:
				builder.append("Tuesday   ");
				break;
			case 3:
				builder.append("Wednesday ");
				break;
			case 4:
				builder.append("Thursday  ");
				break;
			case 5:
				builder.append("Friday    ");
				break;
			case 6:
				builder.append("Saturday  ");
				break;
			default:
				break;
			}
			
			for (int j = 0; j < 48; j++) {
				builder.append("|");
				if (avail[i][j]) {
					builder.append("1");
				} else {
					builder.append("0");
				}
			}
			builder.append("\n");
		}
		
		return builder.append("]]").toString();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(avail);
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Availability))
			return false;
		Availability other = (Availability) obj;
		if (!Arrays.equals(avail, other.avail))
			return false;
		return true;
	}
	
	
}
