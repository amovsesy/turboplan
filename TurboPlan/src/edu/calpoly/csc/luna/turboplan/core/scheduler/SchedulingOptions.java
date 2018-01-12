package edu.calpoly.csc.luna.turboplan.core.scheduler;

/**
 * This class defines the schedule generation options
 * 		
 * @author Bradley Barbeee
 * @version 0.2
 */
public class SchedulingOptions {
		
	private long minDate = 0;
	private long maxDate = 0;
	private boolean directions = false;
	private boolean random = false;
	private boolean priority = false;
	private boolean shortest = false;
	private boolean longest = false;
	private boolean start = false;
	private boolean due = false;
	private boolean reverse = false;
	
	public SchedulingOptions(long minDate, long maxDate, boolean directionsy, boolean random,
			boolean priority, boolean shortest, boolean longest, boolean start,
			boolean due, boolean reverse) {
		this.minDate = minDate;
		this.maxDate = maxDate;
		this.directions = directions;
		this.random = random;
		this.priority = priority;
		this.shortest = shortest;
		this.longest = longest;
		this.start = start;
		this.due = due;
		this.reverse = reverse;
	}

	public long getMinDate() {
		return minDate;
	}
	
	public void setMinDate(long minDate) {
		this.minDate = minDate;
	}

	public long getMaxDate() {
		return maxDate;
	}
	
	public void setMaxDate(long maxDate) {
		this.maxDate = maxDate;
	}

	public boolean getDirections() {
		return directions;
	}
	
	public void setDirections(boolean directions) {
		this.directions = directions;
	}
	
	public boolean getRandom() {
		return random;
	}
	
	public void setRandom(boolean random) {
		this.random = random;
	}

	public boolean getPriority() {
		return priority;
	}
	
	public void setPriority(boolean priority) {
		this.priority = priority;
	}

	public boolean getShortest() {
		return shortest;
	}
	
	public void setShortest(boolean shortest) {
		this.shortest = shortest;
	}

	public boolean getLongest() {
		return longest;
	}
	
	public void setLongest(boolean longest) {
		this.longest = longest;
	}

	public boolean getStart() {
		return start;
	}
	
	public void setStart(boolean start) {
		this.start = start;
	}

	public boolean getDue() {
		return due; 
	}
	
	public void setDue(boolean due) {
		this.due = due;
	}

	public boolean getReverse() {
		return reverse; 
	}
	
	public void setReverse(boolean reverse) {
		this.reverse = reverse;
	}
	
	public boolean isOrderByTask() {
		return (priority || longest || shortest || start || due);
	}
}
