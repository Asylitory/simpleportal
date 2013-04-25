<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<form action="${pageContext.request.contextPath}/index" method="POST">
	<table class="loginForm">
		<tr>
			<td colspan="2">Войдите или зарегистрируйтесь:</td>
		</tr>
		<tr>
			<td>Логин:</td>
			<td><input type="text" name="login" class="backgroundColorInput"></td>
		</tr>
		<tr>
			<td>Пароль:</td>
			<td><input type="password" name="password" class="backgroundColorInput"></td>
		</tr>
		<tr>
			<td><input type="submit" name="uLOGIN" value="Войти"></td>
			<td><input type="submit" name="uREGISTER" value="Зарегистрироваться"></td>
		</tr>
	</table>
</form>