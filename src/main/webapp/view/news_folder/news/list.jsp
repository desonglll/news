<%--
  Created by IntelliJ IDEA.
  User: mikeshinoda
  Date: 2023/12/27
  Time: 08:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>首页</title>
    <!-- 新 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
    <script src="js/jquery.min.js"></script>
    <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
    <script src="js/bootstrap.min.js"></script>

</head>
<body>

<ul class="breadcrumb" style="margin:0;">
    <li><a href="#">NewsManagement</a></li>
    <li>Search News</li>
</ul>
<form class="form-inline">
    <div class="row alert alert-info" style="margin:0;padding:3px;">

        <div class="form-group">
            <label class="" for="activename">NewsID</label>
            <input type="email" class="form-control" id="activename" placeholder="请输入学员姓名">
        </div>
        <input type="button" class="btn btn-danger" value="Search"/>
        <a class="btn btn-success" href="/news/news_add">AddNews</a>

    </div>

    <table class="table table-condensed table-striped">
        <tr>
            <th>Main key</th>
            <th>Title</th>
            <th>Content</th>
            <th>Release Date</th>
            <th>NewsNav</th>
            <th>Image</th>
            <th>Photo</th>
            <th>Price</th>
        </tr>
        <c:forEach items="${retList}" var="bean">
            <tr>
                <td>${bean.id}</td>
                <td>${bean.title}</td>
                <td>${bean.content}</td>
                <td>${bean.datatime}</td>
                <td>${bean.nav}</td>
                <td>
                    <a href="${bean.image}" target="_blank">
                            ${bean.image}
                    </a>
                </td>
                <td>
                    <a href="${bean.photo}" target="_blank">
                            ${bean.photo}
                    </a>
                </td>
                <td>${bean.price}</td>
                <th>
                    <a class="btn btn-success btn-xs" href="/news/news_add?id=${bean.id}">Change</a>

                    <a class="btn btn-danger btn-xs" href="/news/news_del?id=${bean.id}">Delete</a>
                </th>
            </tr>
        </c:forEach>
    </table>

</form>
</body>
</html>
