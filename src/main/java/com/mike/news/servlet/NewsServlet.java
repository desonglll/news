package com.mike.news.servlet;

import com.common.dbaccess.annotation.NotNull;
import com.common.dbaccess.servlet.BaseHttpServlet;
import com.common.dbaccess.util.NotNullUtil;
import com.mike.news.bean.Nav;
import com.mike.news.dao.NavDao;
import com.mike.news.dao.NewsDao;
import com.mike.news.bean.News;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@MultipartConfig // Enable receiving files
@WebServlet({"/news_list", "/news_del", "/news_add", "/news_image"}) // prefix is related to servlet name
public class NewsServlet extends BaseHttpServlet {
    NewsDao newsDao = new NewsDao();
    NavDao navDao = new NavDao();

    // For image upload
    public void image(HttpServletRequest req, HttpServletResponse resp) {
        // for jetty project
//        String fileName = copyUploadFile(req, "~/IdeaProject/demo/src/main/webapp/upload");
//        System.out.println(fileName);
        // for tomcat project
        // Can not be a relative path, not support "~/"
        String filePath = "/Users/mikeshinoda/create/upload";
        String fileName = copyUpload(req, filePath);
        System.out.println(fileName);
        fileName = fileName.replace("/Users/mikeshinoda", "");
        System.out.println(fileName);
        outRespJson(fileName, resp);

    }

    public String add(HttpServletRequest req, HttpServletResponse resp) {
        if (isPost(req)) {
            // post request
            // press submit
            News news = getParameter(req, News.class);
            if (NotNullUtil.isBlank(news)) {
                return jsAlert("Please fill the information! orz", resp);
            }
            print(news);
            newsDao.updateNews(news);
            return redirect("/news_list");
        } else {
            // get request
            Integer id = getIntParam(req, "id");
            if (id != null) {
                // Pressed Change button
                System.out.println("Change" + id);
                req.setAttribute("bean", newsDao.getNews(id));
            }
            List<Nav> navList = navDao.getNavList();
            System_out_println(navList);
            // Return attribute for user to select type.
            req.setAttribute("navList", navList);
            return forward("/view/news_folder/news/add.jsp");
        }
    }

    public String del(HttpServletRequest req, HttpServletResponse resp) {
        int id = getIntParam(req, "id");
        // or
        // int id = Integer.parseInt(req.getParameter("id"));
        newsDao.deleteById("tbl_news", id);
        return redirect("/news_list");
    }

    public String list(HttpServletRequest req, HttpServletResponse resp) {
        List<News> newsList = newsDao.getNewsList();
        // System_out_println(newsList);
        // outRespJson(newsList, resp);//output data to client.
        req.setAttribute("retList", newsList);

        print(newsList);
        return forward("/view/news_folder/news/list.jsp");
    }
}
