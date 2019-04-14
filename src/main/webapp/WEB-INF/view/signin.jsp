<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>登录页</title>
    <meta name="description"
          content="particles.js is a lightweight JavaScript library for creating particles.">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <link rel="stylesheet" href="static/css/bootstrap.css"/>
    <script type="text/javascript" src="static/js/jquery-3.3.1.min.js"></script>
    <script type="text/javascript" src="static/js/bootstrap.min.js"></script>
</head>
<body>
<%@include file="/head.jsp" %>

<div class="container">
    <div class="row clearfix">
        <div class="col-lg-8 col-lg-offset-2">
            <div class="page-header">
                <h2>登录</h2>
            </div>
            <form:form class="form-horizontal"
                       action="${pageContext.request.contextPath }/logIn"
                       modelAttribute="user" method="post">
                <div class="form-group">
                    <label class="col-lg-3 control-label"></label>
                    <div class="col-lg-5">
                        <form:errors path="*"/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="inputUsername" class="col-lg-3 control-label">用户名</label>
                    <div class="col-lg-5">
                        <form:input path="username" type="text" class="form-control"
                                    id="inputUsername" name="username" placeholder="请输入您的用户名"/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="inputPassword" class="col-lg-3 control-label">密码</label>
                    <div class="col-lg-5">
                        <form:input path="password" type="password" class="form-control"
                                    id="inputPassword" name="password" placeholder="请输入您的密码"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-3 col-lg-5">
                        <div class="checkbox">
                            <label><input type="checkbox" id="rememberMe" name="rememberMe"/>记住我</label>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-3 col-lg-9">
                        <button type="submit" class="btn btn-primary">登录</button>
                    </div>
                </div>
            </form:form>
        </div>
    </div>
</div>
</body>
</html>