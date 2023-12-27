package com.mike.news.dao;

import com.common.dbaccess.core.BaseDAO;
import com.mike.news.bean.News;

import java.util.List;

public class NewsDao extends BaseDAO<News> {
    public List<News> getNewsList() {
        String sql = "select * from tbl_news order by id desc";
        return selectList(News.class, sql);
    }
    //singleBySql() 查询单条数据
    //selectList() 査列表，査洵全部数据
}
