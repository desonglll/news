package com.mike.news.dao;

import com.common.dbaccess.core.BaseDAO;
import com.mike.news.bean.User;

public class UserDao extends BaseDAO<User> {
    public User getUser(User user) {
        //传入一个信息再返回一个信息
        String sql = "SELECT * FROM tbl_user " +
                "WHERE username = ? " +
                "AND password = ?";

        return singleBySql(User.class, sql, user.username, user.password);

    }
}
