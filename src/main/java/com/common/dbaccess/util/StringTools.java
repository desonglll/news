package com.common.dbaccess.util;

public class StringTools {

	public static String toFirstUpperCase(String str) {
		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}

	public static String toFirstLowerCase(String str) {
		return str.substring(0, 1).toLowerCase() + str.substring(1);
	}

	public static String removeGet(String methodName) {
		if (!methodName.startsWith("get")) {
			return methodName;
		}
		return toFirstLowerCase(methodName.substring(3));
	}

	public static String removeSet(String methodName) {
		if (!methodName.startsWith("set")) {
			return methodName;
		}
		return toFirstLowerCase(methodName.substring(3));
	}

	public static String addGet(String fieldName) {
		return "get" + toFirstUpperCase(fieldName);
	}

	public static String addSet(String fieldName) {
		return "set" + toFirstUpperCase(fieldName);
	}

}
