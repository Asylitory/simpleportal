<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div id="tabs">
	<ul>	
		<c:forEach items="${form.rolesList}" var="role">
			<li><a href="#tabs-${role.id}">${role.name}</a></li>
		</c:forEach>
	</ul>
	<c:forEach items="${form.rolesList}" var="role">
		<div id="tabs-${role.id}">
			<table class="userListTable">
				<th>
					<td>Никнейм пользователя</td>
					<td>Дата регистрации</td>
				</th>
				<c:forEach items="${form.usersList}" var="user">
					<c:if test="${user.role == role.id}">
						<tr>
							<td><a href="${pageContext.request.contextPath}/index?USER=${user.id}">${user.nickname}</a></td>
							<td>${user.joinDate}</td>
						</tr>
					</c:if>
				</c:forEach>
			</table>
		</div>
	</c:forEach>
</div>