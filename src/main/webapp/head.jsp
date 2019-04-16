<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<nav class="navbar navbar-default" role="navigation">
    <div class="navbar-header">
        <button type="button" class="navbar-toggle" data-toggle="collapse"
                data-target="#example-navbar-collapse">
            <span class="sr-only">切换导航</span> <span class="icon-bar"></span> <span
                class="icon-bar"></span> <span class="icon-bar"></span>
        </button>
        <shiro:hasRole name="admin"><a class="navbar-brand" href="/user">管理员</a></shiro:hasRole>
        <shiro:lacksRole name="admin"><a class="navbar-brand" href="/">410云盘</a></shiro:lacksRole>
    </div>
    <div class="collapse navbar-collapse" id="example-navbar-collapse">
        <ul class="nav navbar-nav navbar-right">
            <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                    工具
                    <b class="caret"></b>
                </a>
                <ul class="dropdown-menu">
                    <li><a href="${pageContext.request.contextPath}/vipPlayer">Vip视频解析</a></li>
                    <li class="divider"></li>
                    <li><a href="${pageContext.request.contextPath}/chat">聊天室</a></li>
                    <li class="divider"></li>
                    <li><a href="#">其他功能</a></li>
                    <li class="divider"></li>
                    <li><a href="#">待加入</a></li>
                </ul>
            </li>
            <shiro:user>
                <li><a href="${pageContext.request.contextPath}/autoLogin"><span
                        class="glyphicon glyphicon-user"></span><shiro:principal property="nickname"/></a>
                </li>
                <li><a href="${pageContext.request.contextPath}/signOut"><span
                        class="glyphicon glyphicon-log-out"></span>退 出</a></li>
                <shiro:lacksRole name="vip">
                    <li><a href="#"><span class="glyphicon glyphicon-log-out"></span>注册VIP</a></li>
                </shiro:lacksRole>
            </shiro:user>
            <shiro:guest>
                <li><a href="${pageContext.request.contextPath}/logIn"><span
                        class="glyphicon glyphicon-log-in"></span>登录</a></li>
                <li><a href="${pageContext.request.contextPath}/register"><span
                        class="glyphicon glyphicon-log-in"></span>注册</a></li>
            </shiro:guest>
            <li><a href="${pageContext.request.contextPath}/help.jsp"><span
                    class="glyphicon glyphicon-info-sign"></span>帮助</a></li>
        </ul>
    </div>
</nav>
