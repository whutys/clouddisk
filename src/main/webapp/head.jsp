<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<nav class="navbar navbar-default" role="navigation">
    <div class="navbar-header">
        <button type="button" class="navbar-toggle" data-toggle="collapse"
                data-target="#example-navbar-collapse">
            <span class="sr-only">切换导航</span> <span class="icon-bar"></span> <span
                class="icon-bar"></span> <span class="icon-bar"></span>
        </button>
        <a class="navbar-brand" href="/">410云盘</a>
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
                    <li><a href="#">其他功能</a></li>
                    <li class="divider"></li>
                    <li><a href="#">待加入</a></li>
                </ul>
            </li>
            <c:choose>
                <c:when test="${user!=null}">
                    <li><a href="${pageContext.request.contextPath}/autoSignIn?user_name=${user.userName}"><span
                            class="glyphicon glyphicon-user"></span>${user.nickName}</a>
                    </li>
                    <li><a href="${pageContext.request.contextPath}/signOut"><span
                            class="glyphicon glyphicon-log-out"></span>退 出</a></li>
                    <c:if test="${user.isVip==0 }">
                        <li><a href="#"><span class="glyphicon glyphicon-log-out"></span>注册VIP</a></li>
                    </c:if></c:when>
                <c:otherwise>
                    <li><a href="${pageContext.request.contextPath}/signInPage"><span
                            class="glyphicon glyphicon-log-in"></span>登录</a></li>
                    <li><a href="${pageContext.request.contextPath}/registPage"><span
                            class="glyphicon glyphicon-log-in"></span>注册</a></li>
                </c:otherwise>
            </c:choose>
            <li><a href="${pageContext.request.contextPath}/help.jsp"><span
                    class="glyphicon glyphicon-info-sign"></span>帮助</a></li>
            <li><a href="${pageContext.request.contextPath}/admin"><span
                    class="glyphicon glyphicon-info-sign"></span>管理员</a></li>
        </ul>
    </div>
</nav>
