<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 5.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf8">
		<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
		<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/jquery-ui-1.10.2.custom.css">
		<script src="${pageContext.request.contextPath}/js/jquery-1.9.1.js"></script>
		<script src="${pageContext.request.contextPath}/js/jquery-ui.js"></script>
		<script src="${pageContext.request.contextPath}/js/index.js"></script>
		<script>
			$(function() {
		    $( "#tabs" ).tabs();
		  	});
		</script>
		<title>${form.title}</title>
	</head>
	<body>
		<div id="wrapper">
			<div id="head">
				<div id="login">
					<c:choose>
						<c:when test="${form.logged}">
							<jsp:include page="logedForm.jsp">
								<jsp:param value="${form}" name="form"/>
							</jsp:include>
						</c:when>
						<c:otherwise>
							<jsp:include page="loginForm.jsp"></jsp:include>
						</c:otherwise>
					</c:choose>
				</div>
				<div id="logo">
					<h1>Simple portal</h1>
				</div>
				<div id="navigation">
					<c:if test="${form.logged}">
						<jsp:include page="navigationPanelForm.jsp">
							<jsp:param value="${form}" name="form"/>
						</jsp:include>
					</c:if>
				</div>
			</div>
			<div id="body">
				<c:choose>
					<c:when test="${1 == form.action}">
						<jsp:include page="userEditForm.jsp">
							<jsp:param value="${form}" name="form"/>
						</jsp:include>
					</c:when>
					<c:when test="${2 == form.action}">
						<jsp:include page="userProfileForm.jsp">
							<jsp:param value="${form}" name="form"/>
						</jsp:include>
					</c:when>
					<c:when test="${3 == form.action}">
						<jsp:include page="userListForm.jsp">
							<jsp:param value="${form}" name="form"/>
						</jsp:include>
					</c:when>
					<c:when test="${4 == form.action}">
						<jsp:include page="articleEditForm.jsp">
							<jsp:param value="${form}" name="form"/>
						</jsp:include>
					</c:when>
					<c:when test="${5 == form.action}">
						<jsp:include page="articleForm.jsp">
							<jsp:param value="${form}" name="form"/>
						</jsp:include>
					</c:when>
					<c:when test="${6 == form.action}">
						<jsp:include page="articlesListForm.jsp">
							<jsp:param value="${form}" name="form"/>
						</jsp:include>
					</c:when>
					<c:when test="${7 == form.action}">
						<jsp:include page="commentEdit.jsp">
							<jsp:param value="${form}" name="form"/>
						</jsp:include>
					</c:when>
					<c:otherwise>
						<h3 class="Message">${form.actionMessage}</h3>
					</c:otherwise>
				</c:choose>					
			</div>
			<div id="footer">
				<h3>Powered by Tass @ 2013</h3>
			</div>
		</div>
	</body>
</html>