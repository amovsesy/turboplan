package edu.calpoly.csc.luna.turboplan.core.entity.part;

import java.io.Serializable;
import java.util.Date;

import edu.calpoly.csc.luna.turboplan.core.util.StringUtil;

/**
 * This class is to set a time interval so that
 *   an availability can be set between a start
 *   and an end date.
 * 		
 */
public class TimeInterval implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3308554889065962558L;
	private Date start;
	private Date end;

	/**
	 * Creates a new instance of the TimeInterval with
	 *   no start or end date
	 * 
	 * @return a TimeInterval object
	 */
	public TimeInterval() {
	}

	/**
	 * Creates a new instance of the TimeInterval with
	 *   the given start or end date
	 * 
	 * @param start
	 * @param end
	 * @return a TimeInterval object
	 */
	public TimeInterval(Date start, Date end) {
		super();

		if (start.after(end)) {
			this.start = end;
			this.end = start;
		} else {
			this.start = start;
			this.end = end;
		}
	}

	/**
	 * Returns the start date of the current object
	 * 
	 * @return start date of the current object
	 */
	public Date getStart() {
		return start;
	}

	/**
	 * Returns the end date of the current object
	 * 
	 * @return end date of the current object
	 */
	public Date getEnd() {
		return end;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((end == null) ? 0 : end.hashCode());
		result = prime * result + ((start == null) ? 0 : start.hashCode());
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
		if (!(obj instanceof TimeInterval))
			return false;
		TimeInterval other = (TimeInterval) obj;
		if (end == null) {
			if (other.end != null)
				return false;
		} else if (!end.equals(other.end))
			return false;
		if (start == null) {
			if (other.start != null)
				return false;
		} else if (!start.equals(other.start))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString(java.lang.Object)
	 */
	@Override
	public String toString() {
		return StringUtil.beanToString(this).toString();
	}
}
