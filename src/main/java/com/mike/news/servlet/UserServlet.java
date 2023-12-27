package com.mike.news.servlet;

import com.common.dbaccess.servlet.BaseHttpServlet;
import com.mike.news.bean.User;
import com.mike.news.dao.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;


@WebServlet({"/user_signin"})
public class UserServlet extends BaseHttpServlet {
    UserDao dao = new UserDao();

    public String signin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = getParameter(req, User.class);
        User result = dao.getUser(user);
        if (result == null) {//Login failed
            print("输入的数据是" + user);
            req.setAttribute("msg", "用户名或密码错误！");
            return forward("/signin.jsp");
        } else {// Login success
            System.out.println("Login successfully! owo!" + result.id);
            // Direct to main page
            return redirect("/main.html?username=" + URLEncoder.encode(result.username, "UTF-8"));
//            return redirect("/main.html");
        }
    }
}
//    public void test(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String[] array = {"张三", "李四"};
//        System.out.println("Enter /user_test");
//        System.out.println(Arrays.toString(array));
//    }

