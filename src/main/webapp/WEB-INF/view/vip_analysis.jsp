<%@page import="java.util.HashMap" %>
<%@page import="java.util.Map" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>VIP播放器</title>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0,minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <link rel="stylesheet" href="static/css/bootstrap.css"/>
    <link type="text/css" rel="stylesheet" href="static/static/css/video-js.min.css"/>
    <script type="text/javascript" src="static/js/jquery-3.3.1.min.js"></script>
    <script type="text/javascript" src="static/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="static/js/video.min.js"></script>
    <style type="text/css">
        .video-search, .video-area {
            position: relative;
            padding: 0 0 70% 0;
            background-color: #000000;
            color: #ac2925;
        }
    </style>
    <script>
        videojs.options.flash.swf = "js/video-js.swf";
    </script>
    <%
        String[] apis = {"http://jiexi.92fz.cn/player/vip.php?url=", "http://www.dgua.xyz/webcloud/?url=",
                "http://vb.tshu.top/webcloud/?url=", "http://api.hlglwl.com/jx.php?url=",
                "http://vip.jlsprh.com/index.php?url=", "http://app.baiyug.cn:2019/vip/?url="};
        int n = 0;
        pageContext.setAttribute("apis", apis);
        Map<String, String> searchengines = new HashMap<String, String>() {
            {
                put("爱奇艺", "https://so.iqiyi.com/so/q_");
                put("优酷", "https://so.youku.com/search_video/q_");
                put("腾讯视频", "https://v.qq.com/x/search/?q=");
                put("搜狐视频", "https://so.tv.sohu.com/mts?wd=");
                put("乐视视频", "http://so.le.com/s?wd=");
            }
        };
        pageContext.setAttribute("searchengines", searchengines);
    %>
    <script type="text/javascript">
        $(function () {
            $("#apis")[0].selectedIndex = 0;
            $("#searchengine")[0].selectedIndex = 0;
            //window.history.back(-1);
            $("#searchbt").click(function () {
                var searchvalue = $("#searchValue").val();
                var searchengine = $("#searchengine").val();
                $(".video-search").html('<iframe id="search-box" style="position: absolute" height="100%" width="100%" src='
                    + searchengine
                    + searchvalue
                    + ' frameborder="0" border="0" marginwidth="0" marginheight="0" allowfullscreen="true" sandbox="allow-scripts allow-same-origin allow-popups"></iframe>');
                /*$("#search-box").attr("src", searchengine + searchvalue);*/
            });

            $("#vipbt").click(function () {
                var source = $("#vipSource").val();
                var api = $("#apis").val();
                $(".video-area").html('<iframe id="play-box" style="position: absolute" height="100%" width="100%" src='
                    + api
                    + source
                    + ' frameborder="0" border="0" marginwidth="0" marginheight="0" scrolling="no" allowfullscreen="true"></iframe>');
            });
            $("#vipbt").click(function () {
                var url = $("#vipSource").val();
                var htmlobj = $.ajax({
                    url: "${pageContext.request.contextPath}/getTitle?url=" + url,
                    async: false
                });
                $("title").text(htmlobj.responseText);
                $("#title").text(htmlobj.responseText);
            });
            document.onkeydown = function (e) {
                var ev = (typeof event != 'undefined') ? window.event : e;
                if (ev.keyCode == 13 && document.activeElement.id == "searchValue") {
                    $("#searchbt").click();
                } else if (ev.keyCode == 13 && document.activeElement.id == "vipSource") {
                    $("#vipbt").click();
                }
            }
            /*$("#searchValue").keydown(function (event) {
                if (event.keyCode == 13 && this.act) {
                    $("#searchbt").click();
                }
            });
            $("#vipSource").keydown(function (event) {
                if (event.keyCode == 13) {
                    alert("hh")
                    $("#vipbt").click();
                }
            });*/
        });
    </script>
</head>
<body>
<%@include file="/head.jsp" %>
<div class="container-fluid">
    <div class="row">
        <div class="col-lg-8 col-lg-offset-2">
            <div class="input-group">
                <span class="input-group-btn">
                    <select id="searchengine" class="btn btn-default dropdown-toggle"><c:forEach
                            items="${searchengines}" var="searchengine">
                        <option value="${searchengine.value }">${searchengine.key }
                    </c:forEach></select>
                </span>
                <input id="searchValue" type="text" class="form-control" onfocus="this.select()">
                <span id="searchbt" class="input-group-addon btn">视频搜索</span>
            </div>
            <div class="input-group">
                <div class="input-group-btn">
                    <select id="apis" class="btn btn-default dropdown-toggle"><c:forEach
                            items="${apis }" var="api">
                        <option value="${api }">解析${n=n+1 }</option>
                    </c:forEach>
                    </select>
                </div>
                <input id="vipSource" type="text" class="form-control" onfocus="this.select()"
                       value="${videoInfo.get("videoAddress")}"> <span
                    id="vipbt" class="input-group-addon btn">立即播放</span>
            </div>
        </div>
    </div>
    <br>
    <h4 id="title">${videoInfo.get("videoName")}</h4>
    <div class="row">
        <div class="col-lg-6">
            <div class="video-search"></div>
        </div>
        <div class="col-lg-6">
            <div class="video-area"></div>
        </div>
    </div>
</div>
</body>
</html>