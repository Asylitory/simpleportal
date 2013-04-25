<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<form action="${pageContext.request.contextPath}/index" method="POST">
	<table class="logedForm">
		<tr>
			<td>Вы зашли как: </td>
			<td>${form.nickname}</td>
		</tr>
		<tr>
			<td><input type="submit" name="uPROFILE" value="Профиль"></td>
			<td><input type="submit" name="uQUIT" value="Выход"></td>
		</tr>
	</table>
</form>