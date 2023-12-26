package com.common.dbaccess.core;

import java.sql.CallableStatement;
import java.sql.SQLException;

public interface ProcRowMapper<T> {

	public T mappingRow(CallableStatement cs) throws SQLException;
	
}
