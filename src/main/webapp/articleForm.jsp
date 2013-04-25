<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="article">
	<h1 class="title">${form.articleWrap.title}</h1>
	<h4 class="section">${form.articleWrap.sectionName}</h4>
	<h4 class="author">
		Автор: 
		<a href="${pageContext.request.contextPath}/index?USER=${form.articleWrap.authorId}">
			${form.articleWrap.authorNickname}
		</a>
	</h4>
	<h4 class="createDate">Создано: ${form.articleWrap.createDate}</h4>
	<pre class="content">
	${form.articleWrap.content}
	</pre>
	<c:if test="${form.articleWrap.createDate != form.articleWrap.editDate || form.articleWrap.authorId != form.articleWrap.editorId}">
		<h4 class="lastEdit">
			Последний раз редактировалось ${form.articleWrap.editDate}
			<c:if test="${form.articleWrap.authorId != form.articleWrap.editorId}">by ${form.articleWrap.editorNickname}
			</c:if>
		</h4>
	</c:if>
	<form action="${pageContext.request.contextPath}/index" method="POST">
		<table class="inputsTable">
			<tr>
				<c:if test="${1 == form.userRole || 2 == form.userRole || form.userId == comment.authorId}">
					<td><input type="submit" name="uARTICLEEDIT" value="Редактировать"></td>
				</c:if>
		  		<td><input type="submit" name="uCOMMENTNEW" value="Комментировать"></td>
		  	</tr>
		</table>
	</form>
	<h4 class="commentsHeader">Комментарии: </h4>
	<c:choose>
		<c:when test="${form.commentsEmpty}">
			<h5 class="commentsHeader">Эту статью еще не прокомментировали</h5>
		</c:when>
		<c:otherwise>
			<c:forEach var="comment" items="${form.commentWrapList}">
				<div class="comment">
				 	<h5 class="commentTitle">
				 		<a href="${pageContext.request.contextPath}/index?USER=${comment.authorId}">
				 			${comment.authorNickname}
				 		</a>
				 		@ ${comment.createDate}
				 	</h5>
				 	<pre>${comment.content}</pre>
				 	<c:if test="${comment.createDate != comment.editDate || comment.authorId != comment.editorId}">
				 		<h5 class="lastEdit">Последний раз редактировалось: ${comment.editDate}</h5>
				 		<c:if test="${comment.authorId != comment.editorId}"><h5 class="lastEdit">by ${comment.editorNickname}</h5>
				 		</c:if>
				 	</c:if>
				 	<c:if test="${1 == form.userRole || 2 == form.userRole || form.userId == comment.authorId}">
				 		<form action="${pageContext.request.contextPath}/index" method="POST">
				 			<input type="submit" name="uCOMMENTEDIT" value="Редактировать комментарий" class="commentEdit">
				 			<input type="hidden" name="COMMENT_ID" value="${comment.id}">
				 		</form>
				 	</c:if>
				</div>
			</c:forEach>
		</c:otherwise>
	</c:choose>

</div>