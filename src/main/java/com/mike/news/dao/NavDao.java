package com.mike.news.dao;

import com.common.dbaccess.core.BaseDAO;
import com.mike.news.bean.Nav;

import java.util.List;

public class NavDao extends BaseDAO<Nav> {
    public List<Nav> getNavList() {
        return selectList(Nav.class, "select * from tbl_nav");
    }
}
