package com.common.dbaccess.core;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface IParamBinding {

	public void bindParam(PreparedStatement pstmt) throws SQLException;

}
