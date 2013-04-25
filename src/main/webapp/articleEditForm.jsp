<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<form action="${pageContext.request.contextPath}/index" method="POST">
	<table class="articleEditPanelForm">
		<c:if test="${null != form.errors}">
			<tr class="errors">
				<td colspan="2">Обнаружены следующие ошибки</td>
			</tr>
			<c:forEach var="str" items="${form.errors}">
				<tr class="errors">
					<td colspan="2">${str}</td>
				</tr>
			</c:forEach>
		</c:if>
		<tr>
			<th>Раздел: </th>
			<td>
				<select name="articleSection">
					<c:forEach var="section" items="${form.sectionsList}">
						<c:choose>
							<c:when test="${section.id == form.article.section}">
								<option value="${section.id}" selected="selected">${section.name}</option>
							</c:when>
							<c:otherwise>
								<option value="${section.id}">${section.name}</option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</select>
			</td>
		</tr>
		<tr>
			<th>Заголовок: </th>
			<td><input type="text" name="articleTitle" value="${form.article.title}" class="titleInput"></td>
		</tr>
		<tr>
			<td colspan="2"><textarea name="articleContent" class="articleEditArea">${form.article.content}</textarea></td>
		</tr>
	</table>
	<table class="articleInputs">
		<tr>
			<td><input type="submit" name="uARTICLECONFIRM" value="Продолжить"></td>
			<td><input type="submit" name="uARTICLECANCEL" value="Отмена"></td>
		</tr>
	</table>
</form>