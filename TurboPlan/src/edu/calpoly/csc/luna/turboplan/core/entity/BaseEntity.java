package edu.calpoly.csc.luna.turboplan.core.entity;


public abstract class BaseEntity {
	/**
	 * BaseEntity equals doesn't check for anything
	 */
	@Override
	public boolean equals(Object obj) {
		return true;
	}
	
	@Override
	public int hashCode() {
		return super.hashCode();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "";
	}
	
	
}
