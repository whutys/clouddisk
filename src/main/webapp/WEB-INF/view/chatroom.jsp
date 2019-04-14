<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
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
            <li><a href="${pageContext.request.contextPath}/signInPage"><span
                    class="glyphicon glyphicon-log-in"></span>登录</a></li>
            <li><a href="${pageContext.request.contextPath}/registPage"><span
                    class="glyphicon glyphicon-log-in"></span>注册</a></li>
            <li><a
                    href="${pageContext.request.contextPath}/autoSignIn?user_name=${user_name}"><span
                    class="glyphicon glyphicon-user"></span>我的主页</a></li>
            <li><a href="${pageContext.request.contextPath}/help.jsp"><span
                    class="glyphicon glyphicon-info-sign"></span>帮助</a></li>
            <li><a href="${pageContext.request.contextPath}/admin"><span
                    class="glyphicon glyphicon-info-sign"></span>管理员</a></li>
        </ul>
    </div>
</nav>

<div class="container">
    <div style="padding: 100px 0px 10px;">
        <div class="col-lg-8 col-lg-offset-3">
            <form action="searchfile" method="post" class="form-horizontal"
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
    <div class="text-center">Copyright ©工艺410</div>
</nav>
<script>
    var socket;
    if (typeof(WebSocket) === "undefined") {
        console.log("您的浏览器不支持WebSocket");
    } else {
        console.log("您的浏览器支持WebSocket");
        //实现化WebSocket对象，指定要连接的服务器地址与端口  建立连接
        //等同于socket = new WebSocket("ws://localhost:8083/checkcentersys/websocket/20");
        socket = new WebSocket("${basePath}websocket/${cid}".replace("http", "ws"));
        //打开事件
        socket.onopen = function () {
            console.log("Socket 已打开");
            //socket.send("这是来自客户端的消息" + location.href + new Date());
        };
        //获得消息事件
        socket.onmessage = function (msg) {
            console.log(msg.data);
            //发现消息进入    开始处理前端触发逻辑
        };
        //关闭事件
        socket.onclose = function () {
            console.log("Socket已关闭");
        };
        //发生了错误事件
        socket.onerror = function () {
            alert("Socket发生了错误");
            //此时可以尝试刷新页面
        }
        //离开页面时，关闭socket
        //jquery1.8中已经被废弃，3.0中已经移除
        // $(window).unload(function(){
        //     socket.close();
        //});
    }
</script>
</body>
</html>