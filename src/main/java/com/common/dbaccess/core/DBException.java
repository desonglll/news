package com.common.dbaccess.core;

public class DBException extends RuntimeException {

	private static final long serialVersionUID = -7064299972354714582L;

	public DBException(String message) {
		super("DBException: " + message);
	}

}
