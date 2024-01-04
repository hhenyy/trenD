function editPwCheck() {
	$("#pwGuide").hide();
	$("#pwCheckGuide").hide();

	var mempw=$("#userPw").val();

	if ($.trim($("#userPw").val()) == "") {
		var newtext = '<font color="red">변경할 비밀번호를 입력해주세요.</font>';
		$("#pwGuide").text('');
		$("#pwGuide").show();
		$("#pwGuide").append(newtext);
		$("#userPw").val("").focus();
		return false;
	}
	if ($.trim($("#userPwCheck").val()) == "") {
		var newtext = '<font color="red">한번 더 입력해주세요.</font>';
		$("#pwCheckGuide").text('');
		$("#pwCheckGuide").show();
		$("#pwCheckGuide").append(newtext);
		$("#userPwCheck").val("").focus();
		return false;
	}
	if($.trim($("#userPw").val()).length < 8){
		var newtext='<font color="red">비밀번호는 6~12자의 값이어야 합니다.</font>';
		$("#pwGuide").text('');
		$("#pwGuide").show();
		$("#pwGuide").append(newtext);
		$("#userPw").focus();
		return false;
	}
	if(!(validateUserPw(mempw))){
		var newtext='<font color="red">비밀번호는 영대소문자, 숫자, 특수문자(!,@,#,$,%,^)만 가능합니다.</font>';
		$("#pwGuide").text('');
		$("#pwGuide").show();
		$("#pwGuide").append(newtext);
		$("#userPw").focus();
		return false;
	}
	if ($.trim($("#userPw").val()) != $.trim($("#userPwCheck").val())) {
		var newtext = '<font color="red">비밀번호가 일치하지 않습니다.</font>';
		$("#pwDCGuide").text('');
		$("#pwDCGuide").show();
		$("#pwDCGuide").append(newtext);
		return false;
	}
};

// 입력 비밀번호 유효성 검사 : 영대소문자, 숫자 , 몇 가지 특수 문자 허용
function validateUserPw(mempw){
	var pattern = new RegExp(/^[A-Za-z0-9!@#$%^]+$/);
	return pattern.test(mempw);
}