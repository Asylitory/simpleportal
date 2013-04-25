<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<table class="articlesListFrom"> 
	<c:choose>
		<c:when test="${form.listEmpty}">
			<tr>
				<td>В данном разделе статей нет</td>
			</tr>
		</c:when>
		<c:otherwise>
			<tr>
				<th>Автор статьи</th>
				<th>Название статьи</th>
				<th>Раздел</th>
				<th>Дата создания</th>
			</tr>
			<c:forEach var="article" items="${form.articlesList}">
				<tr>
					<td><a href="${pageContext.request.contextPath}/index?USER=${article.authorId}">${article.authorNickname}</a></td>
					<td><a href="${pageContext.request.contextPath}/index?ARTICLE=${article.id}">${article.title}</a></td>
					<td>${article.sectionName}</td>
					<td>${article.createDate}</td>
				</tr>		
			</c:forEach>
		</c:otherwise>
	</c:choose>
</table>