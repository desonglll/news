package com.common.dbaccess.core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.common.dbaccess.config.DBConnection;
import com.common.dbaccess.util.PageModel;

public class BaseDAO<T> {
	DBConnection dbConn = new DBConnection();

	/**
	 * 按照主键id删除行
	 * @param table
	 * @param id
	 * @return
	 */
	public int deleteById(String table, int id) {
		return updateBySql("delete from " + table + " where id = " + id);
	}
	
	/**
	 * 新增操作并返回id
	 * @param sql
	 * @param param
	 * @return
	 */
	public int insert(String sql, final Object... param) {
		return insert(sql, new IParamBinding() {
			@Override
			public void bindParam(PreparedStatement pstmt) throws SQLException {
				for (int i = 0; i < param.length; i++) 
					pstmt.setObject(i + 1, param[i]);
			}
		});
	}
	
	public int insert(String sql, IParamBinding bind) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dbConn.getConnection();
			pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			bind.bindParam(pstmt);
			pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		} finally {
			dbConn.closeStatement(pstmt);
			dbConn.closeConnection(conn);
		}
	}
	
	/**
	 * 修改、删除、新增（3合1）
	 * 返回：受影响数据的行数
	 * @param sql
	 * @param param
	 * @return
	 */
	public int updateBySql(String sql, final Object... param) {
		return updateBySql(sql, new IParamBinding() {
			@Override
			public void bindParam(PreparedStatement pstmt) throws SQLException {
				for (int i = 0; i < param.length; i++) 
					pstmt.setObject(i + 1, param[i]);
			}
		});
	}
	
	public int updateBySql(String sql, IParamBinding bind) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dbConn.getConnection();
			pstmt = conn.prepareStatement(sql);
			bind.bindParam(pstmt);
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		} finally {
			dbConn.closeStatement(pstmt);
			dbConn.closeConnection(conn);
		}
	}
	
	public int updateBySql(String sql) {
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = dbConn.getConnection();
			stmt = conn.createStatement();
			return stmt.executeUpdate(sql);
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		} finally {
			dbConn.closeStatement(stmt);
			dbConn.closeConnection(conn);
		}
	}
	
	/**
	 * @Select家族  查询核心方法（共6个）
	 * 
	 */
	public T singleBySql(String sql, IRowMapper<T> mapper) {
		List<T> list = selectList(sql, mapper);
		return list != null && !list.isEmpty() ? list.get(0) : null;
	}
	
	public T singleBySql(String sql, IParamBinding bind, IRowMapper<T> mapper) {
		List<T> list = selectList(sql, bind, mapper);
		return list != null && !list.isEmpty() ? list.get(0) : null;
	}
	
	public T singleBySql(Class<T> beanClass, String sql, Object... param) {
		List<T> list = selectList(beanClass, sql, param);
		return list != null && !list.isEmpty() ? list.get(0) : null;
	}
	
	public List<T> selectList(Class<T> beanClass, String sql, final Object... param) {
		return selectList(sql, new IParamBinding() {
			@Override
			public void bindParam(PreparedStatement pstmt) throws SQLException {
				for (int i = 0; i < param.length; i++) 
					pstmt.setObject(i + 1, param[i]);
			}
		}, new MysqlReflectMapping<>(beanClass));
	}
	
	public List<T> selectList(String sql, IParamBinding bind, IRowMapper<T> mapper) {
		if (bind == null) return selectList(sql, mapper);
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<T> retList = new ArrayList<T>();
		try {
			conn = dbConn.getConnection();
			pstmt = conn.prepareStatement(sql);
			bind.bindParam(pstmt);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				T obj = mapper.mappingRow(rs);
				retList.add(obj);
			}
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		} finally {
			dbConn.closeResultSet(rs);
			dbConn.closeStatement(pstmt);
			dbConn.closeConnection(conn);
		}
		return retList;
	}

	public List<T> selectList(String sql, IRowMapper<T> mapper) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<T> retList = new ArrayList<T>();
		try {
			conn = dbConn.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				T obj = mapper.mappingRow(rs);
				retList.add(obj);
			}
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		} finally {
			dbConn.closeResultSet(rs);
			dbConn.closeStatement(stmt);
			dbConn.closeConnection(conn);
		}
		return retList;
	}
	
	public PageModel<T> splitPage(Class<T> beanClass, String listSQL, String countSQL, int curpage, int pagesize, final Object... param) {
		return splitPage(listSQL, countSQL, curpage, pagesize, new IParamBinding() {
			@Override
			public void bindParam(PreparedStatement pstmt) throws SQLException {
				for (int i = 0; i < param.length; i++) 
					pstmt.setObject(i + 1, param[i]);
			}
		}, new MysqlReflectMapping<>(beanClass));
	}
	
	public PageModel<T> splitPage(String listSQL, String countSQL, int curpage, int pagesize, IParamBinding bind, IRowMapper<T> mapper) {
		PageModel<T> page = new PageModel<T>();
		String sql = listSQL + " limit " + (curpage - 1) * pagesize + "," + pagesize;
		page.setRetList(selectList(sql, bind, mapper));
		page.setCurpage(curpage);
		page.setPagesize(pagesize);
		int count = selectCount(countSQL, bind);
		page.setTotal(count);
		page.setPages(getPages(count, pagesize));
		return page;
	}
	
	private int getPages(int total, int pagesize) {
		int nums = total % pagesize;
		if (total == 0) {
			return 1;
		} else if (nums == 0) {
			return total / pagesize;
		} else {
			return total / pagesize + 1;
		}
	}
	
	public int selectCount(String sql, final Object... param) {
		return selectCount(sql, new IParamBinding() {
			@Override 
			public void bindParam(PreparedStatement pstmt) throws SQLException {
				for (int i = 0; i < param.length; i++) 
					pstmt.setObject(i + 1, param[i]);
			}
		});
	}
	
	public int selectCount(String sql, IParamBinding bind) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int count = 0;
		try {
			conn = dbConn.getConnection();
			pstmt = conn.prepareStatement(sql);
			if (bind != null) {
				bind.bindParam(pstmt);
			}
			rs = pstmt.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		} finally {
			dbConn.closeResultSet(rs);
			dbConn.closeStatement(pstmt);
			dbConn.closeConnection(conn);
		}
		return count;
	}

	/**
	 * 批处理 => 事务
	 * @param sqls
	 * @return
	 */
	public boolean executeBatch(String[] sqls) {
		Connection conn = null;
		Statement stmt = null;
		boolean success = true;
		try {
			conn = dbConn.getConnection();
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			for (String sql : sqls) {
				stmt.addBatch(sql);
			}
			stmt.executeBatch();
			return true;
		} catch (SQLException e) {
			success = false;
			e.printStackTrace();
			dbConn.rollbackTrans(conn);
			return false;
		} finally {
			if (success)
			dbConn.commitTrans(conn);
			dbConn.closeStatement(stmt);
			dbConn.closeConnection(conn);
		}
	}
	
	/**
	 * 批处理，此处为逻辑批操作
	 * SQL批量操作为 物理批操作
	 * @param sql
	 * @param bind
	 * @return
	 */
	public boolean executeBatch(String sql, IParamBinding bind) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean success = true;
        try {
            conn = dbConn.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(sql);
            bind.bindParam(pstmt); // for中，加pstmt.addBatch();
            pstmt.executeBatch();
            return true;
        } catch (Exception e) {
        	success = false;
        	e.printStackTrace();
        	dbConn.rollbackTrans(conn);
			return false;
        } finally {
        	if (success)
        	dbConn.commitTrans(conn);
			dbConn.closeStatement(pstmt);
			dbConn.closeConnection(conn);
        }
    }
	
}
