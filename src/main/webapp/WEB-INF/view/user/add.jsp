<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>
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
</head>
<body>
<%@include file="/head.jsp"%>
<div class="container-fluid">
    <div class="row">
        <div class="col-xs-10 col-lg-11">
            <form:form id="addForm" modelAttribute="user" method="post"
                       class="form-horizontal"
                       action="${pageContext.request.contextPath }/user/add">
                <div class="form-group">
                    <form:hidden path="id"/>
                    <label class="col-lg-3 control-label">昵称</label>
                    <div class="col-lg-5">
                        <form:input path="nickname" type="text" class="form-control"
                                    name="nickname" placeholder="请输入昵称"/>
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
                                value="submit">提交
                        </button>
                        <button type="button" class="btn btn-info" id="cancelBtn"
                                href="${pageContext.request.contextPath}/user">取消
                        </button>
                    </div>
                </div>
            </form:form>
        </div>
    </div>
</div>
    
</body>
</html>








