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
        $(function () {
            //$("#pagesize").get(0).selectedIndex=${pagebean.pagesize/5-1 };
            $("#fileupload").fileinput({
                language: 'zh',
                uploadUrl: '${pageContext.request.contextPath }/uploadfile',
                //allowedFileExtensions: ['mp4','zip','rar'],//接收的文件后缀
                //uploadExtraData:{"id": 1, "fileName":'123.mp3'},
                uploadAsync: true, //默认异步上传
                showUpload: true, //是否显示上传按钮
                showRemove: true, //显示移除按钮
                showPreview: true, //是否显示预览
                //showCaption:false,//是否显示标题
                browseClass: "btn btn-primary", //按钮样式    
                dropZoneEnabled: false,//是否显示拖拽区域
                //zoomModalHeight:100,
                //minImageWidth: 50, //图片的最小宽度
                //minImageHeight: 50,//图片的最小高度
                //maxImageWidth: 1000,//图片的最大宽度
                //maxImageHeight: 1000,//图片的最大高度
                maxFileSize: 150000,//单位为kb，如果为0表示不限制文件大小
                //minFileCount: 0,
                maxFileCount: 10, //表示允许同时上传的最大文件个数
                enctype: 'multipart/form-data',
                validateInitialCount: true,
                previewFileIcon: "<i class='glyphicon glyphicon-king'></i>",
                msgFilesTooMany: "选择上传的文件数量{n} 超过允许的最大数值{m}！"
            });
            $("#fileupload").on("fileuploaded", function (event, data, previewId, index) {
                $("#myModal").modal('hide');
                window.location.reload();
            });

            //按钮点击
            $('.nav li a').click(function () {
                window.location.href = '${pageContext.request.contextPath }/searchUserFile?filetype=' + $(this).parent().attr("id");
            });

            $('#' + '${sessionScope.filetype}').addClass('active');
            $('.pre-scrollable').css('max-height', $(document).height() * 0.81);
        });
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
                <li><a href="javscript:void(0)"><span class="glyphicon glyphicon-user"></span>${user.nickName}</a></li>
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
                <li id="all"><a href="javascript:void(0)">全部</a></li>
                <li id="photos"><a href="javascript:void(0)">图片</a></li>
                <li id="docs"><a href="javascript:void(0)">文档</a></li>
                <li id="videos"><a href="javascript:void(0)">视频</a></li>
                <li id="musics"><a href="javascript:void(0)">音乐</a></li>
                <li id="others"><a href="javascript:void(0)">其他</a></li>
            </ul>
        </div>
        <div class="col-xs-10 col-lg-11">
            <noframes>
                <fr
            </noframes>

        </div>
    </div>
</div>
<script type="text/javascript">
    function openfile(filepath, filename) {
        var str = filename.substring(filename.lastIndexOf('.') + 1, filename.length);
        if ('mp4' == str || 'ogg' == str) {
            window.location.href = '${pageContext.request.contextPath}/videoPlay?username=' + filepath + '&filename=' + filename;
        }
        else if ('mp3' == str || 'ogg' == str) {
            window.location.href = '/BaiduYunDownload/' + filepath + '/' + filename;
        }
        else if ('txt' == str || 'doc' == str || 'pdf' == str) {
            window.location.href = '/BaiduYunDownload/' + filepath + '/' + filename;
        }
        else if ('jpg' == str || 'jpeg' == str || 'png' == str) {
            window.location.href = '/BaiduYunDownload/' + filepath + '/' + filename;
        }
    };

    function downloadfile(id, filename) {
        window.location.href = '${pageContext.request.contextPath}/download?id=' + id + '&filename=' + filename;
    };

    function godelete(fileid) {
        var r = confirm("确认删除文件？");
        if (r == true) {
            window.location.href = '${pageContext.request.contextPath}/deletefile?id=' + fileid;
        } else {
            return false;
        }
    };

    function gochange(currentpage, fileid) {

        var canshare = document.getElementById(fileid).value;
        var pagesize = ${pagebean.pagesize};
        var r = confirm("如果设置共享，您的文件将可以被其他人搜索到");
        if (r == true) {
            window.location.href = '${pageContext.request.contextPath}/changefilestatus?currentpage=' + currentpage + '&pagesize=' + pagesize + '&id=' + fileid + '&canshare=' + canshare;
        } else {
            location.reload();
        }
    }

</script>
    
</body>
</html>








