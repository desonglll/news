package com.mike.news.dao;

import com.common.dbaccess.core.BaseDAO;
import com.common.dbaccess.core.IRowMapper;
import com.mike.news.bean.News;

import java.text.SimpleDateFormat;
import java.util.List;

public class NewsDao extends BaseDAO<News> {


    public News getNews(int id) {
        String sql = "select * from v_news where id=?";
        // Search single data
        return singleBySql(News.class, sql, id);
    }

    public List<News> getNewsList() {
        String sql = "select * from v_news order by id desc";
        List<News> newsList = selectList(News.class, sql);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (News news : newsList) {
            news.datatime = format.format(news.ctime);//java.util.Date
        }
        return newsList;
    }

    //singleBySql() select single data
    //selectList() select list, and select all data
    public void updateNews(News news) {
        if (news.id == null || news.id == 0) {
            // id not exists, insert
            String sql = "insert into tbl_news(title,content,ctime,nid,image,photo,price) values(?,?,now(),?,?,?,?)";
            // updateBySql() can add, update, delete
            updateBySql(sql, news.title, news.content, news.nid, news.image, news.photo, news.price);

        } else {
            // id exists, update
            String sql = "update tbl_news set title=?,content=?,nid=?,image=?,photo=?,price=? where id=?";
            updateBySql(sql, news.title, news.content, news.nid, news.image, news.photo, news.price, news.id);
        }
    }
}
