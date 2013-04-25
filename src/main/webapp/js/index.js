function checkPassword() {
	if ($('#password').val() != $('#passwordRepeat').val()) {
		$('#jsErrorField').text('Пароли должны совпадать!');
	} else {
		$('#jsErrorField').text("");
	}
}

function changeTooltip() {
	$('#jsDescField').text($('#div'+$('#sectionCheck').val()).html());
}
