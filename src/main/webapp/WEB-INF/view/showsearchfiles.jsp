<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>搜索结果</title>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">

    <link rel="stylesheet" href="static/css/bootstrap.css"/>
    <script type="text/javascript" src="static/js/jquery-3.3.1.min.js"></script>
    <script type="text/javascript" src="static/js/bootstrap.min.js"></script>
    <script type="text/javascript">
        $(function () {
            $("#pagesize")[0].selectedIndex =${pageInfo.pageSize/5-1};
        })
    </script>
</head>
<body>
<%@include file="/head.jsp" %>
<div class="col-lg-offset-1">
    <div class="page-header">
        <h3>搜索结果</h3>
    </div>
</div>
<div class="container">
    <div class="row">
        <div class="col-lg-4">
            关键词：<label id="searchcontent">${searchcontent }</label>
        </div>
        <div class="col-lg-2 col-lg-offset-6">共[${pageInfo.total}]条记录</div>
    </div>
    <div class="table-responsive">
        <table class="table table-hover">
            <thead>
            <tr>
                <th>文件名</th>
                <th>文件大小</th>
                <th>上传者</th>
                <th>修改日期</th>
                <th>下载文件</th>
            <tr>
            </thead>
            <tbody>
            <c:forEach var="efile" items="${pageInfo.list}"
                       varStatus="stat">
            <tr class="${stat.count%2==0?'success':'warning'}">
                <td><a class="btn"
                       onclick="openfile('${efile.filepath}','${efile.filename }')"> <c:set
                        var="filetype"
                        value="${efile.filename.substring(efile.filename.lastIndexOf('.')+1,efile.filename.length())}"/>
                    <c:choose>
                        <c:when test="${filetype=='mp4' }">
                            <span class="glyphicon glyphicon-hd-video"></span>
                        </c:when>

                        <c:when test="${filetype=='mp3' }">
                            <span class="glyphicon glyphicon-play-music"></span>
                        </c:when>

                        <c:when test="${filetype=='jpg'||filetype=='png' }">
                            <span class="glyphicon glyphicon-picture"></span>
                        </c:when>

                        <c:when
                                test="${filetype=='txt'||filetype=='pdf'||filetype=='doc' }">
                            <span class="glyphicon glyphicon-file"></span>
                        </c:when>
                    </c:choose> ${efile.filename }
                </a></td>
                <td><c:choose>
                    <c:when test="${efile.filesize>1000000}">
                        <fmt:formatNumber type="number" value="${efile.filesize/1000000 }"
                                          pattern="0.0" maxFractionDigits="1"/>G</c:when>
                    <c:when test="${efile.filesize>1000}">
                        <fmt:formatNumber type="number" value="${efile.filesize/1000 }"
                                          pattern="0.0" maxFractionDigits="1"/>M</c:when>
                    <c:otherwise>
                        <fmt:formatNumber type="number" value="${efile.filesize}"
                                          pattern="0.0" maxFractionDigits="1"/>k</c:otherwise>
                </c:choose></td>
                <td>${efile.filepath }</td>
                <td><fmt:formatDate value="${efile.createtime }"
                                    pattern="yyyy-MM-dd HH:mm"/></td>
                <td>
                    <button type="button" class="btn btn-primary btn-xs"
                            onclick="downloadfile('${efile.id}','${efile.filename }')">
                        <span class="glyphicon glyphicon-download-alt"></span>下载
                    </button>
                </td>
            <tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<ul class="pager">

    <li>每页 <select title="选择每页记录数"
                   onchange="gotoPage(${pageInfo.pageSize})" id="pageSize">
        <option value="5">5
        </option>
        <option value="10">10</option>
        <option value="15">15</option>
    </select>条
    </li>
    <li>共[${pageInfo.pages}]页</li>
    <li><a href="#" onclick="gotoPage(${pageInfo.navigateFirstPage})">首页</a></li>
    <li><c:choose>
        <c:when test="${pageInfo.isFirstPage}">
            <span>&laquo;</span>
        </c:when>
        <c:otherwise>
            <a href="#"
               onclick="gotoPage(${pageInfo.prePage})">&laquo;</a>
        </c:otherwise>
    </c:choose></li>
    <c:forEach var="pageNum" items='${pageInfo.navigatepageNums}'>
        <c:choose>
            <c:when test="${pageNum==pageInfo.pageNum }">
                <li><span>${pageNum }</span></li>
            </c:when>
            <c:otherwise>
                <li><a href="#"
                       onclick="gotoPage(${pageNum})">${pageNum}</a></li>
            </c:otherwise>
        </c:choose>
    </c:forEach>
    <li><c:choose>
        <c:when
                test="${pageInfo.isLastPage}">
            <a href=":void(0)">&raquo;</a>
        </c:when>
        <c:otherwise>
            <a href="#"
               onclick="gotoPage(${pageInfo.nextPage})">&raquo;</a>
        </c:otherwise>
    </c:choose></li>
    <li>跳转至第<input type="text" style="width: 2%;" maxlength="5"
                   id="pageNum" onblur="gotoPageOn()"/>页
    </li>
</ul>

<script type="text/javascript">
    function openfile(filepath, filename) {
        var str = filename.substring(filename.lastIndexOf('.') + 1, filename.length);
        filename = encodeURI(encodeURI(filename));
        if ('mp4' === str || 'ogg' === str) {
            window.location.href = '${pageContext.request.contextPath}/videoPlay?username=' + filepath + '&filename=' + filename;
        } else if ('mp3' === str || 'ogg' === str) {
            window.location.href = '/fileDir/' + filepath + '/' + filename;
        } else if ('txt' === str || 'doc' === str || 'pdf'=== str) {
            window.location.href = '/fileDir/' + filepath + '/' + filename;
        } else if ('jpg' === str || 'jpeg' === str || 'png' === str) {
            window.location.href = '/fileDir/' + filepath + '/' + filename;
        }
    }

    function downloadfile(id) {
        window.location.href = '${pageContext.request.contextPath}/download?id=' + id;
    }

    function gotoPage(pageNum) {
        var pageSize = document.getElementById("pageSize").value;
        var searchcontent = $("#searchcontent").text();
        window.location.href = '${pageContext.request.contextPath }/searchfile?pageNum=' + pageNum + '&pageSize=' + pageSize + '&searchcontent=' + searchcontent;
    }

    function gotoPageOn() {
        var pageNum = document.getElementById("pageNum").value.trim();
        if (pageNum === "") return;

        var pageSize = document.getElementById("pageSize").value;
        var searchcontent = $("#searchcontent").text();

        window.location.href = '${pageContext.request.contextPath }/searchfile?pageNum=' + pageNum + '&pageSize=' + pageSize + '&searchcontent=' + searchcontent;
    }

</script>
</body>
</html>