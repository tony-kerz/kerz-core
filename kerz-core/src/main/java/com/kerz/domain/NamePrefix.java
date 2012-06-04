/*
 * Created May 2011
 */
package com.kerz.domain;

/**
 * @author Kent Rancourt
 * @since 0.2
 */
public enum NamePrefix {

	MR("Mr."), MRS("Mrs."), MS("Ms."), MISS("Miss"), DR("Dr.");
	
	private String displayString;
	
	private NamePrefix(String displayString) {
		this.displayString = displayString;
	}
	
	public String getDisplayString() {
		return displayString;
	}
	
}