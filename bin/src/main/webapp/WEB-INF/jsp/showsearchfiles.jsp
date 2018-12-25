<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>搜索结果</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">

<link rel="stylesheet" href="css/bootstrap.css" />
<script type="text/javascript" src="js/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
<script type="text/javascript">

$(function() {
	$("#pagesize")[0].selectedIndex=${pagebean.pagesize/5-1};
})
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
			<a class="navbar-brand" href="#">410云盘</a>
		</div>
		<div class="collapse navbar-collapse" id="example-navbar-collapse">
			<ul class="nav navbar-nav navbar-right">
				<li><a href="${pageContext.request.contextPath}/index.jsp"><span
						class="glyphicon glyphicon-home"></span>首页</a></li>
				<li><a href="${pageContext.request.contextPath}/signInPage"><span
						class="glyphicon glyphicon-log-in"></span>登录</a></li>
				<li><a href="${pageContext.request.contextPath}/registPage"><span
						class="glyphicon glyphicon-log-in"></span>注册</a></li>
				<li><a
					href="${pageContext.request.contextPath}/autoSignIn?user_name=${user_name}"><span
						class="glyphicon glyphicon-user"></span>我的主页</a></li>
				<li><a href="${pageContext.request.contextPath}/help.jsp"><span
						class="glyphicon glyphicon-info-sign"></span>帮助</a></li>
			</ul>
		</div>
	</nav>
	<div class="col-lg-offset-1">
		<div class="page-header">
			<h3>搜索结果</h3>
		</div>
	</div>
	<div class="container">
		<div class="row">
			<div class="col-lg-4">
				关键词：<label id="searchcontent">${pagebean.searchcontent }</label>
			</div>
			<div class="col-lg-2 col-lg-offset-6">共[${requestScope.pagebean.totalrecord}]条记录</div>
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
					<c:forEach var="c" items="${requestScope.pagebean.list}"
						varStatus="stat">
						<tr class="${stat.count%2==0?'success':'warning'}">
							<td><a class="btn"
								onclick="openfile('${c.filepath}','${c.filename }')"> <c:set
										var="filetype"
										value="${c.filename.substring(c.filename.lastIndexOf('.')+1,c.filename.length())}"></c:set>
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
									</c:choose> ${c.filename }
							</a></td>
							<td><c:choose>
									<c:when test="${c.filesize>1000000}">
										<fmt:formatNumber type="number" value="${c.filesize/1000000 }"
											pattern="0.0" maxFractionDigits="1"></fmt:formatNumber>G</c:when>
									<c:when test="${c.filesize>1000}">
										<fmt:formatNumber type="number" value="${c.filesize/1000 }"
											pattern="0.0" maxFractionDigits="1"></fmt:formatNumber>M</c:when>
									<c:otherwise>
										<fmt:formatNumber type="number" value="${c.filesize}"
											pattern="0.0" maxFractionDigits="1"></fmt:formatNumber>k</c:otherwise>
								</c:choose></td>
							<td>${c.filepath }</td>
							<td><fmt:formatDate value="${c.createtime }"
									pattern="yyyy-MM-dd HH:mm" /></td>
							<td><button type="button" class="btn btn-primary btn-xs"
									onclick="downloadfile('${c.id}','${c.filename }')">
									<span class="glyphicon glyphicon-download-alt"></span>下载
								</button></td>
						<tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
	<ul class="pager">

		<li>每页 <select title="选择每页记录数"
			onchange="gotopage(${pagebean.currentpage})" id="pagesize"><option
					value="5">5</option>
				<option value="10">10</option>
				<option value="15">15</option>
		</select>条
		</li>
		<li>共[${requestScope.pagebean.totalpage}]页</li>
		<li><a href="javascript:void(0)" onclick="gotopage(1)">回到首页</a></li>
		<li><c:choose>
				<c:when test="${requestScope.pagebean.currentpage==1}">
					<a href="javascript:void(0)">&laquo;</a>
				</c:when>
				<c:otherwise>
					<a href="javascript:void(0)"
						onclick="gotopage(${requestScope.pagebean.currentpage-1})">&laquo;</a>
				</c:otherwise>
			</c:choose></li>
		<c:forEach var='pagenum' items='${requestScope.pagebean.pagebar}'>
			<c:choose>
				<c:when test="${pagenum==pagebean.currentpage }">
					<li><a href="#">${pagenum }</a></li>
				</c:when>
				<c:otherwise>
					<li><a href="javascript:void(0)"
						onclick="gotopage(${pagenum})">${pagenum}</a></li>
				</c:otherwise>
			</c:choose>
		</c:forEach>
		<li><c:choose>
				<c:when
					test="${requestScope.pagebean.currentpage==requestScope.pagebean.totalpage}">
					<a href="javascript:void(0)">&raquo;</a>
				</c:when>
				<c:otherwise>
					<a href="javascript:void(0)"
						onclick="gotopage(${requestScope.pagebean.currentpage+1})">&raquo;</a>
				</c:otherwise>
			</c:choose></li>
		<li>跳转至第<input type="text" style="width: 2%;" maxlength="5"
			id="pagenum" onblur="gotopageon()">页
		</li>
	</ul>

	<script type="text/javascript">
		function openfile(filepath,filename){
			var str=filename.substring(filename.lastIndexOf('.')+1,filename.length);
			filename=encodeURI(encodeURI(filename));
			if ('mp4'==str||'ogg'==str) {
			window.location.href ='${pageContext.request.contextPath}/videoPlay?userName='+filepath+'&filename='+filename;
			}
		};
		
		function downloadfile(id){
			window.location.href ='${pageContext.request.contextPath}/download?id='+id;
		};
		
    	function gotopage(pageNo){
    	  var pagesize = document.getElementById("pagesize").value;
    	  var searchcontent = $("#searchcontent").text();
    	  if('${pageBean.totalrecord }'<=(pageNo-1)*pagesize){
    		  pageNo=1;
    	  }
    	  window.location.href = '${pageContext.request.contextPath }/searchfile?currentpage='+pageNo+'&pagesize='+ pagesize+'&searchcontent='+searchcontent;
      };
  
      function gotopageon(){
    	  var pageNo=document.getElementById("pagenum").value
    	  if(pageNo!=""){
    	  var pagesize = document.getElementById("pagesize").value;
    	  var searchcontent = $("#searchcontent").text();
    	  
    	  if(pageNo >'${pagebean.totalpage}'){
    		  pageNo = '${pagebean.totalpage}';
    		  pagesize = '${pagebean.pagesize}';
    	  }else if(pageNo < 1){
    		  pageNo = 1 ;
    		  pagesize = '${pagebean.pagesize}';
    	  };
    	  
    	  window.location.href = '${pageContext.request.contextPath }/searchfile?currentpage='+pageNo+'&pagesize='+ pagesize+'&searchcontent='+searchcontent;
      }}

</script>
</body>
</html>