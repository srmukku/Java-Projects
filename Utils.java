package com.payroll;

public class Utils {
	
	public static String safeTrim(String value){
		return (value !=null) ? value.trim():"";
	}
	public static boolean isEmpty(String value){
		return (safeTrim(value).equals("")) ? true : false;
	}

}
