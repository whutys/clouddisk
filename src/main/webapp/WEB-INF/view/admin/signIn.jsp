<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>管理员登录</title>
<meta name="description"
	content="particles.js is a lightweight JavaScript library for creating particles.">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<link rel="stylesheet" href="/static/css/bootstrap.css" />
<script type="text/javascript" src="/static/js/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="/static/js/bootstrap.min.js"></script>
</head>
<body>

	<nav class="navbar navbar-default" role="navigation">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target="#example-navbar-collapse">
				<span class="sr-only">切换导航</span> <span class="icon-bar"></span> <span
					class="icon-bar"></span> <span class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="#">410云盘</a>
		</div>
		<div class="collapse navbar-collapse" id="example-navbar-collapse">
			<ul class="nav navbar-nav navbar-right">
				<li><a href="${pageContext.request.contextPath}/index.jsp"><span
						class="glyphicon glyphicon-home"></span>首页</a></li>
				<li><a href="${pageContext.request.contextPath}/help.jsp"><span
						class="glyphicon glyphicon-info-sign"></span>帮助</a></li>
			</ul>
		</div>
	</nav>
	<div class="container">
		<div class="row clearfix">
			<div class="col-lg-8 col-lg-offset-2">
				<div class="page-header">
					<h2>登录</h2>
				</div>
				<form:form class="form-horizontal"
					action="${pageContext.request.contextPath }/admin/signInForm"
					modelAttribute="admin" method="post">
					<div class="form-group">
						<label class="col-lg-3 control-label"></label>
						<div class="col-lg-5">
							<form:errors path="*"/>
						</div>
					</div>
					<div class="form-group">
						<label for="inputUsername" class="col-lg-3 control-label">用户名</label>
						<div class="col-lg-5">
							<form:input path="adminName" type="text" class="form-control"
								id="inputUsername" name="adminName" placeholder="请输入您的用户名" />
						</div>
					</div>
					<div class="form-group">
						<label for="inputPassword" class="col-lg-3 control-label">密码</label>
						<div class="col-lg-5">
							<form:input path="adminPassWord" type="password" class="form-control"
								id="inputPassword" name="adminPassWord" placeholder="请输入您的密码" />
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-offset-3 col-lg-5">
							<div class="checkbox">
								<label><input type="checkbox" />记住密码</label>
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