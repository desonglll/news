package com.mike.news;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.mike.news.bean.User;
import com.mike.news.dao.UserDao;

public class App {

    public static void main(String[] args) {

        // Your servlet logic here
        UserDao dao = new UserDao();
        User user = new User();
        user.username = "张三";
        user.password = "1";
        User result = dao.getUser(user);
        System.out.println(result.id);
    }
}
