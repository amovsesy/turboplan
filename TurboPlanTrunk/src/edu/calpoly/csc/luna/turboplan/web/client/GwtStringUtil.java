package edu.calpoly.csc.luna.turboplan.web.client;

import com.gwtext.client.widgets.form.NumberField;

import edu.calpoly.csc.luna.turboplan.web.client.model.GwtUserProfile;

public class GwtStringUtil {
	public static final String EMPTY_STRING = "";
	
	public static String nullStringCheck(String input) {
		return input == null ? EMPTY_STRING : input;
	}
 
	public static void main(String[] args) {
		GwtUserProfile profile = new GwtUserProfile();
		
		System.out.println(profile.getMiddleInitial());
//		char b;
//		
//		b = (char) null;
//		
//		System.out.println(b);
	}
	
	public static String getSimpleClassname(Class<?> clazz) {
		String[] result = clazz.getName().split(".");
		System.out.println();
		return result[result.length - 1];
	}
}
