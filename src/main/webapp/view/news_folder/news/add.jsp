<%--
  Created by IntelliJ IDEA.
  User: mikeshinoda
  Date: 2023/12/27
  Time: 10:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--for loop--%>

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
    <%--专用于上传图片--%>
    <script src="js/jquery.ajaxupload.js"></script>
    <script>
        window.onload = function () {
            // $ is equals to jQuery
            $.ajaxUploadSettings.name = "file";
            $('#image').ajaxUploadPrompt({
                // Properties
                url: '/news/news_image', // Java address to receive the picture
                success: function (data) {
                    $('#image').prev().val(data);
                    $('#image').next().attr("src", data);
                }
            })
            $('#photo').ajaxUploadPrompt({
                // Properties
                url: '/news/news_image', // Java address to receive the picture
                success: function (data) {
                    $('#photo').prev().val(data);
                    $('#photo').next().attr("src", data);
                }
            })
        }
    </script>
</head>
<body>

<ul class="breadcrumb" style="margin:0;">
    <li><a href="#">NewsManagement</a></li>
    <li>
        <c:if test="${empty bean}">
            AddNews
        </c:if>
        <c:if test="${not empty bean}">
            ChangeNews
        </c:if>
    </li>
</ul>
<%--leave empty for submit to current address--%>
<form action="/news/news_add" method="post" class="form-horizontal">
    <c:if test="${not empty bean}">
        <input type="hidden" name="id" value="${bean.id}"/>

    </c:if>

    <h5 class="page-header alert-info" style="padding:10px;margin:0 0 10px 0;">基本信息</h5>
    <div class="row col-sm-9 form-group">
        <label class="col-sm-3 control-label">NewsTitle</label>
        <div class="col-sm-6">
            <input type="text" name="title" class="form-control input-sm" value="${bean.title}"/>
        </div>
    </div>
    <div class="row col-sm-9 form-group">
        <label class="col-sm-3 control-label">Content</label>
        <div class="col-sm-6">
            <input type="text" name="content" class="form-control input-sm" value="${bean.content}"/>
        </div>
    </div>
    <div class="row col-sm-9 form-group">
        <label class="col-sm-3 control-label">Photo1</label>
        <div class="col-sm-6">
            <input type="hidden" name="image" class="form-control input-sm" value="${bean.image}"/>
            <input id="image" type="button" class="btn btn-success" value="Upload Image"/>
            <img style="width: 300px" src="${bean.image}"/>
        </div>
    </div>
    <div class="row col-sm-9 form-group">
        <label class="col-sm-3 control-label">Photo2</label>
        <div class="col-sm-6">
            <input type="hidden" name="photo" class="form-control input-sm" value="${bean.photo}"/>
            <input id="photo" type="button" class="btn btn-success" value="Upload Image2"/>
            <img style="width: 300px" src="${bean.photo}"/>
        </div>
    </div>
    <div class="row col-sm-9 form-group">
        <label class="col-sm-3 control-label">Price</label>
        <div class="col-sm-6">
            <input type="number" name="price" class="form-control input-sm" value="${bean.price}"/>
        </div>
    </div>
    <div class="row col-sm-9 form-group">
        <label class="col-sm-3 control-label">BelongsNav</label>
        <div class="col-sm-6">
            <select name="nid" class="form-control input-sm">
                <c:forEach items="${navList}" var="item">
                    <option value=${item.id}>${item.nav}</option>
                    <%--display item.nav, but submit item.id--%>
                </c:forEach>
            </select>
        </div>
    </div>

    <div class="col-sm-3 col-sm-offset-4">
        <input type="submit" class="btn btn-success" value="保存"/>
        <a class="btn btn-warning" href="javascript:history.back()">返回上一级</a>
    </div>
</form>

</body>
</html>
