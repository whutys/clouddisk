<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>管理CloudDisk</title>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <link rel="stylesheet" href="/static/css/bootstrap.css"/>
    <link rel="stylesheet" href="/static/css/fileinput.min.css"/>

    <script type="text/javascript" src="/static/js/jquery-3.3.1.min.js"></script>
    <script type="text/javascript" src="/static/js/fileinput.min.js"></script>
    <script type="text/javascript" src="/static/js/locales/zh.js"></script>
    <script type="text/javascript" src="/static/js/bootstrap.min.js"></script>
    <script type="text/javascript">

    </script>
</head>
<body>
<nav class="navbar navbar-default" role="navigation">
    <div class="navbar-header">
        <button type="button" class="navbar-toggle" data-toggle="collapse"
                data-target="#example-navbar-collapse">
            <span class="sr-only">切换导航</span> <span class="icon-bar"></span> <span
                class="icon-bar"></span> <span class="icon-bar"></span>
        </button>
        <a class="navbar-brand" href="#">管理员</a>
    </div>
    <div class="collapse navbar-collapse" id="example-navbar-collapse">
        <ul class="nav navbar-nav navbar-right">
            <c:if test="${user!=null}">
                <li><a href="javscript:void(0)"><span class="glyphicon glyphicon-user"></span>${admin.nickName}</a></li>
            </c:if>
            <li><a href="${pageContext.request.contextPath}/signOut"><span
                    class="glyphicon glyphicon-log-out"></span>退 出</a></li>
            <li><a href="${pageContext.request.contextPath}/index.jsp"><span
                    class="glyphicon glyphicon-home"></span>首页</a></li>
            <li><a href="${pageContext.request.contextPath}/help.jsp"><span
                    class="glyphicon glyphicon-info-sign"></span>帮助</a></li>
        </ul>
    </div>
</nav>

<div class="container-fluid">
    <div class="row">
        <div class="col-xs-10 col-lg-11">
            <form:form id="editForm" modelAttribute="user" method="post"
                       class="form-horizontal"
                       action="${pageContext.request.contextPath }/user/edit">
                <div class="form-group">
                    <form:hidden path="id" />
                    <label class="col-lg-3 control-label">昵称</label>
                    <div class="col-lg-5">
                        <form:input path="nickname" type="text" class="form-control"
                                    name="nickname" placeholder="请输入昵称"/>
                        <span id="checknickname">${nickNameError }</span>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-lg-3 control-label">用户名</label>
                    <div class="col-lg-5">
                        <form:input path="username" type="text" class="form-control"
                                    name="userName" placeholder="请输入用户名"/>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-lg-3 control-label">密码</label>
                    <div class="col-lg-5">
                        <form:input path="password" type="password" class="form-control"
                                    name="passWord" placeholder="请输入密码"/>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-lg-3 control-label">电子邮箱</label>
                    <div class="col-lg-5">
                        <form:input path="email" type="text" class="form-control" name="email"
                                    placeholder="请输入电子邮箱"/>
                    </div>
                </div>

                <div class="form-group">
                    <div class="col-lg-9 col-lg-offset-3">
                        <button type="submit" class="btn btn-primary" name="submit"
                                value="submit">提交</button>
                        <button type="button" class="btn btn-info" id="resetBtn">重置</button>
                    </div>
                </div>
            </form:form>
        </div>
        <div class="col-xs-2 col-sm-2 col-lg-1" id="myScrollspy">
            <ul class="nav nav-pills nav-stacked">
                <li id="user"><a href="javascript:void(0)">用户</a></li>
                <li id="file"><a href="javascript:void(0)">文件</a></li>
            </ul>
        </div>
        </div>
    </div>
</div>
    
</body>
</html>








