<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>聊天室</title>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, user-scalable=no">

    <script type="text/javascript" src="static/js/jquery-3.3.1.min.js"></script>
    <link rel="stylesheet" href="static/css/bootstrap.css"/>
    <script type="text/javascript" src="static/js/bootstrap.min.js"></script>

</head>
<body>
<%@include file="/head.jsp" %>
<div class="container-fluid">
    <div class="row">
        <div class="col-lg-2 col-sm-5 col-xs-5">【用户】："${fromUser}"</div>
        <div class="col-lg-1 col-sm-2 col-xs-2" id="openSocket"><a href="#" onclick="openSocket()">上线</a></div>
        <div class="col-lg-1 col-sm-2 col-xs-2" id="closeSocket" style="display: none"><a href="#" onclick="closeSocket()">下线</a>
        </div>
    </div>
    <br>
    <div class="row">
        <div class="col-lg-4 col-sm-3 col-xs-2">
            <span>在线用户</span>
            <ul id="userList" class="nav nav-pills nav-stacked">
            </ul>
        </div>
        <div class="col-lg-8 col-sm-9 col-xs-10">
            <span>【发送给】：<span id="toUser"></span></span>
            <div class="panel-body" data-spy="scroll" data-target="#navbar-example" data-offset="280"
                 style="height: 280px">
                <pre id="message" style="height:100%;"></pre>
            </div>

            <div id="message_container" class="input-group" style="display: none">
                <input id="contentText" type="text" class="form-control">
                <span class="input-group-addon btn btn-default" onclick="sendMessage()">发送消息</span>
            </div>
        </div>
    </div>
</div>
<script>
    var webSocket;
    var name = '${fromUser}';
    $(document).ready(function () {
//        join();
    });

    /**
     * Connecting to socket
     */
    function join() {
        // Checking person name
//        if ($('#fromUser').val().trim().length <= 0) {
//            alert('Enter your name');
//        } else {
//            name = $('#fromUser').val().trim();
        $('#openSocket').fadeOut(1000, function () {
            // opening socket connection
            openSocket();
        });
//        };
        return false;
    }

    function openSocket() {
        if (typeof(WebSocket) === "undefined") {
            console.log("您的浏览器不支持WebSocket");
        } else {
            if (webSocket !== undefined && webSocket.readyState !== WebSocket.CLOSED) {
                return;
            }
            var wsUrl = '';
            if (window.location.protocol === 'http:') {
                wsUrl = 'ws://<%=basePath%>chat/' + name;
            } else {
                wsUrl = 'wss://<%=basePath%>chat/' + name;
            }
            console.log(wsUrl);
            webSocket = new WebSocket(wsUrl);
            //打开事件
            webSocket.onopen = function (event) {
                console.log("Socket 已打开");
                $('#openSocket').hide();
                $('#closeSocket').show();
                $('#message_container').show();
//                showUser(event.data);
//                if (event.data === undefined)
//                    return;
//                webSocket.send("这是来自客户端的消息" + location.href + new Date());
            };
            webSocket.onmessage = function (msg) {
                console.log(msg.data);
                parseMessage(msg.data);
            };
            webSocket.onclose = function () {
                console.log("Socket已关闭");
                $('#closeSocket').hide(500);
                $('#openSocket').show(500);
                $('ul[id=userList]').find("li").remove();
            };
            webSocket.onerror = function () {
                alert("Socket发生了错误");
            }
        }
    }

    function parseMessage(message) {
        var jObj = $.parseJSON(message);
        var flag = jObj.flag;
        if (flag === 'message')
            $("#message").append('<p>' + jObj.timestamp + '| ' + jObj.fromUser + ':<br>' + jObj.contentText + '</p>');
        else {
            var userList = jObj.userList;
            if (flag === 'addUser') {
                $.each(userList, function (i) {
                    console.log(userList[i]);
                    $("#userList").append('<li id="' + userList[i] + '">' + userList[i] + '<button class="btn" onclick="sendMessageTo(\'' + userList[i] + '\')" > 聊天 </button></li>');
                });
            } else if (flag === "removeUser") {
                $.each(userList, function (i) {
                    console.log(userList[i]);
                    $("ul li[id=" + userList[i] + "]").remove();
                });
            }
        }
    }

    function sendMessage() {
        if (typeof(WebSocket) === "undefined") {
            console.log("您的浏览器不支持WebSocket");
        } else {
            var toUser = $("#toUser").text();
            if (toUser===''){
                alert("请先选择聊天对象");
                return;
            }
            var timestamp = new Date().toLocaleString();
            var contentText = $("#contentText").val();
//            console.log('[{"toUser":"' + $("#toUser").val() + '","contentText":"' + $("#contentText").val() + '"}]');
            $("#message").append('<p>' + timestamp + '| 我:<br>' + contentText + '</p>');
            webSocket.send('[{"toUser":"' + toUser + '","contentText":"' + contentText + '","timestamp":"' + timestamp + '","flag":"message"}]'
            )
        }
    }

    function sendMessageTo(toUser) {
        console.log(toUser);
        $("#toUser").text(toUser);
    }

    function closeSocket() {
        webSocket.close();
    }
</script>
</body>
</html>