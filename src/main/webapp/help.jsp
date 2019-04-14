<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>帮助页</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">

<link rel="stylesheet" href="static/css/bootstrap.css" />
<script type="text/javascript" src="static/js/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="static/js/bootstrap.min.js"></script>

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
				<li><a href="${pageContext.request.contextPath}/signInPage"><span
						class="glyphicon glyphicon-log-in"></span>登录</a></li>
				<li><a href="${pageContext.request.contextPath}/registPage"><span
						class="glyphicon glyphicon-log-in"></span>注册</a></li>
				<li><a
					href="${pageContext.request.contextPath}/autoSignIn?user_name=${user_name}"><span
						class="glyphicon glyphicon-user"></span>我的主页</a></li>
			</ul>
		</div>
	</nav>

	<h2> 如何上传或者下载文件？</h2>
	<br />
	<p>任何人均可以下载他人共享的文件，如果想上传文件，您必须先注册并登陆</p>
	<br />
	<br />
	<h2> 上传的文件会被别人看到并下载吗？</h2>
	<br />
	<p>您上传的文件默认是私有的，上传之后您可以选择共享哪些文件</p>
	<br />
	<h2> 忘记密码怎么办？</h2>
	<br />
	<p>您可以发email至1017577243@qq.com询问，后续会开通密码找回功能</p>
	<br />
	<h2> 上传文件有什么限制吗？</h2>
	<br />
	<p>普通用户单次上传文件不能超过100Mb，VIP用户不能超过500Mb</p>
	<br />

</body>
</html>