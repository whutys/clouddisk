<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
        <div class="col-xs-2 col-sm-2 col-lg-1" id="myScrollspy">
            <ul class="nav nav-pills nav-stacked">
                <li id="user"><a href="javascript:void(0)">用户</a></li>
                <li id="file"><a href="javascript:void(0)">文件</a></li>
            </ul>
        </div>
        <div class="col-xs-10 col-lg-11">
            <div class="btn-group-sm hidden-xs" id="toolbar" role="group">
                <a class="btn btn-success" onclick=""><span class="glyphicon glyphicon-plus-sign"></span>新增</a>
                <a class="btn btn-primary" onclick=""><span class="glyphicon glyphicon-edit"></span>修改</a>
                <a class="btn btn-danger" onclick=""><span class="glyphicon glyphicon-trash"></span>删除</a>
            </div>
            <div class="row clearfix pre-scrollable ">
                <div class="table-responsive " id="tb">
                    <table class="table table-hover pre-scrollable">
                        <thead>
                        <tr>
                            <th>用户名</th>
                            <th>昵称</th>
                            <th>电子邮箱</th>
                            <th>是否会员</th>
                            <th>操作</th>
                        <tr>
                        </thead>
                        <tbody>
                        <c:forEach var="user" items="${requestScope.users}"
                                   varStatus="stat">
                            <tr class="${stat.count%2==0?'success':'warning'}">
                                <td><span>${user.username}</span></td>
                                <td><span>${user.nickname}</span></td>
                                <td><span>${user.email}</span></td>
                                <td><span>${user.isVip}</span></td>
                                <td>
                                    <button type="button" class="btn btn-default btn-xs"
                                            onclick="edit(${user.id})">
                                        <span class="glyphicon glyphicon-edit"></span>修改
                                    </button>
                                    <button type="button" class="btn btn-danger btn-xs"
                                            onclick="godelete(${user.id})">
                                        <span class="glyphicon glyphicon-trash"></span>删除
                                    </button>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    function godelete(userId) {
        var r = confirm("确认删除？");
        if (r == true) {
            window.location.href = '${pageContext.request.contextPath}/user/remove/' + userId;
        } else {
            return false;
        }
    }

    function edit(userId) {
        window.location.href = '${pageContext.request.contextPath}/user/edit/' + userId;
    }
</script>
    
</body>
</html>








