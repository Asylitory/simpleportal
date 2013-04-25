<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<form action="${pageContext.request.contextPath}/index" method="POST">
	<table class="userProfileForm">
		<tr>
			<td>Никнейм пользователя: </td>
			<td>${form.profileUser.nickname}</td>
		</tr>
		<tr>
			<td>Дата регистрации: </td>
			<td>${form.profileUser.joinDate}</td>
		</tr>
		<tr>
			<td>Группа пользователя: </td>
			<td>${form.profileUserRoleName}</td>
		</tr>
		<c:if test="${(1 == form.userRole && 1 != form.profileUser.role) || form.userId == form.profileUser.id}">
			<tr>
				<td colspan="2"><input type="submit" name="uPROFILEEDIT" value="Редактировать профиль"></td>
			</tr>
		</c:if>
		<tr>
			<td colspan="2">
				<div id="tabs">
					<ul>
						<li><a href="#tabs-1">Статьи пользователя</a></li>
						<li><a href="#tabs-2">Комментарии пользователя</a></li>
					</ul>
					<div id="tabs-1">
						<c:choose>
							<c:when test="${form.articleWrapListEmpty}">
								<h5>Этот пользователь еще не написал ни одной статьи</h5>
							</c:when>
							<c:otherwise>
								<table>
									<tr>
										<th>Автор статьи</th>
										<th>Название статьи</th>
										<th>Раздел</th>
										<th>Дата создания</th>
									</tr>
									<c:forEach var="article" items="${form.articleWrapList}">
										<tr>
											<td><a href="${pageContext.request.contextPath}/index?USER=${article.authorId}">${article.authorNickname}</a></td>
											<td><a href="${pageContext.request.contextPath}/index?ARTICLE=${article.id}">${article.title}</a></td>
											<td>${article.sectionName}</td>
											<td>${article.createDate}</td>
										</tr>		
									</c:forEach>
								</table>
							</c:otherwise>
						</c:choose>						
					</div>
					<div id="tabs-2">
						<c:choose>
							<c:when test="${form.commentWrapListEmpty}">
								<h5>Этот пользователь еще не проккоментировал ни одной статьи</h5>
							</c:when>
							<c:otherwise>
								<c:forEach var="comment" items="${form.commentWrapList}">
									<div class="comment">
									 	<h5 class="commentTitle">
											${comment.authorNickname} @ ${comment.createDate}
									 	</h5>
									 	<pre>${comment.content}</pre>
									 	<c:if test="${comment.createDate != comment.editDate}">
									 		<h5 class="lastEdit">
									 			Последний раз редактировалось: ${comment.editDate}
									 		</h5>
									 		<c:if test="${comment.authorId != comment.editorId}">
									 			, ${comment.editorNickname}
									 		</c:if>
									 	</c:if>
									</div>								
								</c:forEach>
							</c:otherwise>
						</c:choose>				
					</div>
				</div>
			
			</td>
		</tr>
	</table>
</form>