package com.common.dbaccess.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.common.dbaccess.config.DBConnection;

public class JdbcExec {

	// 静态内部类 SQLdata
	public static class SQLdata {
		public DBConnection dbConn;
		public Integer row;
		public ResultSet s;
		public Connection conn;
		public PreparedStatement pstmt;

		public void close() {
			dbConn.closeResultSet(s);
			dbConn.closeStatement(pstmt);
			dbConn.closeConnection(conn);
		}
		
	}

	public static SQLdata SQL(String sql, Object... param) {
		return sql.trim().startsWith("select") ? select(sql, param) : update(sql, param);
	}

	public static SQLdata update(String sql, Object... param) {
		SQLdata data = new SQLdata();
		DBConnection dbConn = new DBConnection();
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dbConn.getConnection();
			pstmt = conn.prepareStatement(sql);
			for (int i = 0; i < param.length; i++)
				pstmt.setObject(i + 1, param[i]);
			data.row = pstmt.executeUpdate();
			return data;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbConn.closeStatement(pstmt);
			dbConn.closeConnection(conn);
		}
		return null;
	}

	public static SQLdata select(String sql, Object... param) {
		SQLdata data = new SQLdata();
		data.dbConn = new DBConnection();
		try {
			data.conn = data.dbConn.getConnection();
			data.pstmt = data.conn.prepareStatement(sql);
			for (int i = 0; i < param.length; i++)
				data.pstmt.setObject(i + 1, param[i]);
			data.s = data.pstmt.executeQuery();
			return data;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
