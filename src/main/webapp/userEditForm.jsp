<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<form action="${pageContext.request.contextPath}/index" method="POST">
	<table class="userEditForm">
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
		<c:choose>
			<c:when test="${0 == form.editedUser.id}">
				<tr>
					<th colspan="2">Заполните поля</th>
				</tr>
				<tr>
					<td>Имя учетной записи:</td>
					<td><input type="text" name="userLogin" class="backgroundColorInput"></td>
				</tr>
				<tr>
					<td>Пароль:</td>
					<td><input type="password" name="userPassword" class="backgroundColorInput" id="password" onkeyup="checkPassword()"></td>
				</tr>
				<tr>
					<td>Подтвердите пароль:</td>
					<td><input type="password" name="userPasswordRepeat" class="backgroundColorInput" id="passwordRepeat" onkeyup="checkPassword()"></td>
				</tr>
				<tr>
					<td>Отображаемый никнейм:</td>
					<td><input type="text" name="userNickname" class="backgroundColorInput"></td>
				</tr>
				<tr>
					<td colspan="2" id="jsErrorField"></td>
				</tr>
			</c:when>
			<c:otherwise>
				<c:if test="${1 == form.userRole && 1 != form.editedUser.role}">
					<tr>
						<td>Группа пользователя:</td>
						<td>
							<select name="newUserGroup">
								<c:forEach var="role" items="${form.roles}">
									<c:choose>
										<c:when test="${form.editedUser.role == role.id}">
											<option value="${role.id}" selected="selected">${role.name}</option>
										</c:when>
										<c:otherwise>
											<option value="${role.id}">${role.name}</option>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</select>
						</td>
					</tr>
				</c:if>
				<tr>
					<td>Новый никнейм:</td>
					<td><input type="text" name="newUserNickname" class="backgroundColorInput"></td>
				</tr>
				<c:if test="${form.userId == form.editedUser.id}">
					<tr class="errors">
						<td colspan="2">В случае смены пароля, Вам понадобится заново залогиниться!</td>
					</tr>
					<tr>
						<td>Новый пароль:</td>
						<td>
							<input type="password" name="newUserPassword" class="backgroundColorInput" id="password" onkeyup="checkPassword()">
						</td>
					</tr>
					<tr>
						<td>Подтвердите новый пароль:</td>
						<td>
							<input type="password" name="newUserPasswordRepeat" class="backgroundColorInput" id="passwordRepeat" onkeyup="checkPassword()">
						</td>
					</tr>
				</c:if>
				<tr>
					<td colspan="2" id="jsErrorField"></td>
				</tr>
			</c:otherwise>
		</c:choose>
		<tr class="inputs">
			<td><input type="submit" name="uUSERCONFIRM" value="Продолжить"></td>
			<td><input type="submit" name="uUSERCANCEL" value="Отмена"></td>
		</tr>
	</table>
</form>