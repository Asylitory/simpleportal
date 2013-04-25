<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<form action="${pageContext.request.contextPath}/index" method="POST">
	<div class="hiddenDiv" id="div0">Все новости</div>
	<c:forEach var="section" items="${form.sectionsList}">
		<div class="hiddenDiv" id="div${section.id}">${section.description}</div>
	</c:forEach>
	<table class="navigationPanelForm">
		<tr>
			<td colspan="2"  id="jsDescField">Все новости</td>
		</tr>
		<tr>
			<td>
				<select name="articlesGroup" id="sectionCheck" onchange="changeTooltip()">
					<option value="0">Все статьи</option>
					<c:forEach var="section" items="${form.sectionsList}">
						<option value="${section.id}">${section.name}</option>
					</c:forEach>
				</select>
			</td>
			<td><input type="submit" name="uARTICLELIST" value="Перейти" class="navButton"></td>
		</tr>
		<tr>
			<td><input type="submit" name="uARTICLENEW" value="Создать статью" class="navButton"></td>
			<td><input type="submit" name="uUSERLIST" value="Пользователи" class="navButton"></td>			
		</tr>
	</table>
</form>