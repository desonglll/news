package com.mike.news.servlet;

import com.common.dbaccess.servlet.BaseHttpServlet;
import com.mike.news.dao.NewsDao;
import com.mike.news.bean.News;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@WebServlet({"/news_list"})//prefix is related to servlet name
public class NewsServlet extends BaseHttpServlet {
    NewsDao newsDao = new NewsDao();

    public String list(HttpServletRequest req, HttpServletResponse resp) {
        List<News> newsList = newsDao.getNewsList();
//        print(newsList);
//        System_out_println(newsList);
//        outRespJson(newsList, resp);//output data to client.
        req.setAttribute("retList", newsList);

        return forward("/view/news_folder/news/list.jsp");
    }
}
