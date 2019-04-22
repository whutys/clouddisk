<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>工艺410共享云盘</title>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, user-scalable=no">

    <script type="text/javascript" src="static/js/jquery-3.3.1.min.js"></script>
    <link rel="stylesheet" href="static/css/bootstrap.css"/>
    <script type="text/javascript" src="static/js/bootstrap.min.js"></script>

</head>
<body>
<%@include file="/head.jsp" %>

<div class="container">
    <div style="padding: 100px 0px 10px;">
        <div class="col-lg-8 col-lg-offset-3">
            <form action="${pageContext.request.contextPath}/searchfile" method="post" class="form-horizontal"
                  role="form">
                <div class="row">
                    <div class="col-lg-6 col-lg-offset-1">
                        <div class="input-group">
                            <h2>输入关键词搜索 !</h2>
                        </div>
                    </div>
                    <br> <br> <br> <br>
                    <div class="col-lg-8 ">
                        <div class="input-group">
                            <input type="text" class="form-control" name="searchcontent">
                            <span class="input-group-btn">
									<button class="btn btn-primary" type="button"
                                            onclick="document.forms[0].submit()">搜索</button>
								</span>
                        </div>
                        <!-- /input-group -->
                    </div>
                    <!-- /.col-lg-6 -->
                </div>
                <!-- /.row -->
            </form>
        </div>
    </div>
</div>

<nav class="navbar navbar-default navbar-fixed-bottom">
    <div class="container text-center">Copyright ©工艺410</div>
</nav>
</body>
</html>