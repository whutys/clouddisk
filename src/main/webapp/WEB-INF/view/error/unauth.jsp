<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>无访问权限</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">

<link rel="stylesheet" href="static/css/bootstrap.css" />
<script type="text/javascript" src="static/js/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="static/js/bootstrap.min.js"></script>
</head>
<body>
	<script type="text/javascript">
		var time = 5; //时间,秒
		function Redirect() {
			window.location = "../../../";
		}
		var i = 0;
		function dis() {
			document.all.s.innerHTML = "****** 错误信息：未授权 ******   "
					+ (time - i) + "秒后自动跳往主页";
			i++;
		}
		timer = setInterval('dis()', 1000); //显示时间
		timer = setTimeout('Redirect()', time * 1000); //跳转
	</script>
	<div class="container">
		<span id="s"></span>
	</div>
</body>
</html>