<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<form action="${pageContext.request.contextPath}/index" method="POST">
	<table class="commentEditPanel">
		<c:if test="${form.error}">
			<tr class="error">
				<td colspan="2">Текст комментария не может содержать менее 20 или более 21844 символов</td>
			</tr>
		</c:if>
		<tr>
			<th colspan="2">Введите свой комментарий:</th>
		</tr>
		<tr>
			<td colspan="2">
				<textarea name="commentContent" class="commentEditArea">${form.comment.content}</textarea>
			</td>
		</tr>
		<tr>
			<td class="inputLeft"><input class="inputButton" type="submit" name="uCOMMENTCONFIRM" value="Продолжить"></td>
			<td class="inputRight"><input class="inputButton" type="submit" name="uCOMMENTCANCEL" value="Отмена"></td>
		</tr>
	</table>	
</form>