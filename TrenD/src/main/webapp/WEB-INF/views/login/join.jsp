<%--
  Created by IntelliJ IDEA.
  User: skinl
  Date: 2023-12-21
  Time: 오후 4:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

<head>
  <title>가입하기</title>

  <script src="http://code.jquery.com/jquery-latest.js"></script>

  <script>
  var checkid = 'n';
  var checknick = 'n';

    window.onload = function() {
      selectAge();
      selectGender();
      selectLoc();
    }

    function selectAge(){
      //나이 select문
      $.ajax({
        type:"POST",
        url:"selectAge",
        dataType : "json",
        success: function (age) {
          var ageOptions = "";
          $.each(age, function (index, ageList) {
            ageOptions += '<option value="' + ageList.ageCd + '">' + ageList.ageNm + '</option>';
          });

          // $("#selectAge").append() 대신에 html() 함수를 사용하여 삽입
          $("#userAge").html(ageOptions);

        }
        ,
        error:function(e){
          alert("data error"+e);
        }
      });//$.ajax
    }

    function selectGender(){
      //성별 select문
      $.ajax({
        type:"POST",
        url:"selectGender",
        dataType : "json",
        success: function (gender) {
          var genderOptions = "";
          $.each(gender, function (index, genderList) {
            genderOptions += '<option value="' + genderList.genCd + '">' + genderList.genNm + '</option>';
          });

          // $("#selectAge").append() 대신에 html() 함수를 사용하여 삽입
          $("#userGender").html(genderOptions);

        }
        ,
        error:function(e){
          alert("data error"+e);
        }
      });//$.ajax
    }

    function selectLoc(){
      //지역 select문
      $.ajax({
        type:"POST",
        url:"selectLoc",
        dataType : "json",
        success: function (loc) {
            var locOptions = "";
            $.each(loc, function (index, locList) {
                locOptions += '<option value="' + locList.locCd + '">' + locList.locNm + '</option>';
            });

            // $("#selectAge").append() 대신에 html() 함수를 사용하여 삽입
            $("#userLoc").html(locOptions);
        }
        ,
        error:function(e){
          alert("data error"+e);
        }
      });//$.ajax
    }

  // 아이디 중복 확인
  function checkId(){
      console.log("여기들어옴11");
      $("#idGuide").hide();
      var memid=$("#userId").val();
      console.log(memid);

      // 1. 길이 검사
      if($.trim($("#userId").val()).length < 3){
          var newtext='<font color="red">아이디는 3~12자의 값이어야 합니다.</font>';
          $("#idGuide").text('');
          $("#idGuide").show();
          $("#idGuide").append(newtext);
          $("#userId").focus();
          return false;
      }

      // 2. 유효성 검사
      if(!(validateUserId(memid))){
          var newtext='<font color="red">아이디는 영소문자, 숫자만 가능합니다.</font>';
          $("#idGuide").text('');
          $("#idGuide").show();
          $("#idGuide").append(newtext);
          $("#userId").focus();
          return false;
      }

      // 3. 중복 검사
      $.ajax({
          type: "POST",
          url: "checkUserId",
          data: {"memid":memid},
          success: function(data) {
              if(data == 1){	// 중복
                  var newtext='<font color="red">사용 중인 아이디입니다.</font>';
                  $("#idGuide").text('');
                  $("#idGuide").show();
                  $("#idGuide").append(newtext);
                  $("#userId").focus();
                  return false;
              }else{	// 사용가능
                  var newtext='<font color="blue">사용 가능한 아이디입니다.</font>';
                  $("#idGuide").text('');
                  $("#idGuide").show();
                  $("#idGuide").append(newtext);
                  $("#userPw").focus();	// 다음으로 이동

                  checkid = 'y';
              }
          }
          ,
          error:function(e){
              alert("data error"+e);
          }
      });
  }

  // 닉네임 중복 확인
  function chkNick(){
      $("#nickNameGuide").hide();
      var memnick=$("#userName").val();
      console.log(memnick);

      // 1. 길이 검사
      if($.trim($("#userName").val()).length < 2 || $.trim($("#userName").val()).length > 8){
          var newtext='<font color="red">닉네임는 2~8자의 값이어야 합니다.</font>';
          $("#nickNameGuide").text('');
          $("#nickNameGuide").show();
          $("#nickNameGuide").append(newtext);
          $("#userName").focus();
          return false;
      }

      // 2. 유효성 검사
      if(!(validateUserNick(memnick))){
          var newtext='<font color="red">닉네임은 특수문자는 허용하지 않습니다.</font>';
          $("#nickNameGuide").text('');
          $("#nickNameGuide").show();
          $("#nickNameGuide").append(newtext);
          $("#userName").focus();
          return false;
      }

      // 3. 중복 검사
      $.ajax({
          type: "POST",
          url: "checkUserNick",
          data: {"memnick":memnick},
          success: function(data) {
              if(data == 1){	// 중복
                  var newtext='<font color="red">사용 중인 닉네임입니다.</font>';
                  $("#nickNameGuide").text('');
                  $("#nickNameGuide").show();
                  $("#nickNameGuide").append(newtext);
                  $("#userName").focus();
                  return false;
              }else{	// 사용가능
                  var newtext='<font color="blue">사용 가능한 닉네임입니다.</font>';
                  $("#nickNameGuide").text('');
                  $("#nickNameGuide").show();
                  $("#nickNameGuide").append(newtext);

                  checknick = 'y';
              }
          }
          ,
          error:function(e){
              alert("data error"+e);
          }
      });
  }

    // 가입 필수 입력 항목 검사
    function check(){

        $("#idGuide").hide();
        $("#pwGuide").hide();
        $("#nickNameGuide").hide();
        $("#emailGuide").hide();

        var mempw=$("#userPw").val();

        if($.trim($("#userId").val())==""){
            var newtext='<font color="red">아이디를 입력해주세요.</font>';
            $("#idGuide").text('');
            $("#idGuide").show();
            $("#idGuide").append(newtext);
            $("#userId").val("").focus();
            return false;
        }

        if($.trim($("#userPw").val())==""){
            var newtext='<font color="red">비밀번호를 입력해주세요.</font>';
            $("#pwGuide").text('');
            $("#pwGuide").show();
            $("#pwGuide").append(newtext);
            $("#userPw").val("").focus();
            return false;
        }
        if($.trim($("#userName").val())==""){
            var newtext='<font color="red">이름을 입력해주세요.</font>';
            $("#nickNameGuide").text('');
            $("#nickNameGuide").show();
            $("#nickNameGuide").append(newtext);
            $("#userName").val("").focus();
            return false;
        }

        if($.trim($("#userEmail").val())==""){
            var newtext='<font color="red">이메일을 입력해주세요.</font>';
            $("#emailGuide").text('');
            $("#emailGuide").show();
            $("#emailGuide").append(newtext);
            $("#userEmail").val("").focus();
            return false;
        }
        if($.trim($("#userPw").val()).length < 8){
            var newtext='<font color="red">비밀번호는 8자의 이상의 값이어야 합니다.</font>';
            $("#pwGuide").text('');
            $("#pwGuide").show();
            $("#pwGuide").append(newtext);
            $("#userPw").focus();
            return false;
        }
        if(!(validateUserPw(mempw))){
            var newtext='<font color="red">비밀번호는 영대소문자, 숫자 !,@,#,$,%,^의 특수문자만 가능합니다.</font>';
            $("#pwGuide").text('');
            $("#pwGuide").show();
            $("#pwGuide").append(newtext);
            $("#userPw").focus();
            return false;
        }
        if(checkid == 'n'){
            var newtext='<font color="red">아이디 중복확인을 해주세요.</font>';
            $("#idGuide").text('');
            $("#idGuide").show();
            $("#idGuide").append(newtext);
            $("#userId").focus();
            return false;
        }

        if(checknick == 'n'){
            var newtext='<font color="red">닉네임 중복확인을 해주세요.</font>';
            $("#nickNameGuide").text('');
            $("#nickNameGuide").show();
            $("#nickNameGuide").append(newtext);
            $("#userName").focus();
            return false;
        }
    }

    // 입력 아이디 유효성 검사 : 영소문자, 숫자
    function validateUserId(memid){
        var pattern= new RegExp(/^[a-z0-9]+$/);
        return pattern.test(memid);
    }

    // 입력 비밀번호 유효성 검사 : 영대소문자, 숫자 , 몇 가지 특수 문자 허용
    function validateUserPw(mempw){
        var pattern = new RegExp(/^[A-Za-z0-9!@#$%^]+$/);
      return pattern.test(mempw);
    }

    // 입력 닉네임 유효성 검사 : 한글, 영어 가능
    function validateUserNick(memnick) {
      var pattern = new RegExp(/^[a-zA-Z0-9\uAC00-\uD7A3]+$/);
      return pattern.test(memnick);
    }

  </script>
</head>
<jsp:include page="../include/metalink.jsp"/>
<body class="d-flex align-items-center py-4 bg-body-tertiary">

<jsp:include page="../include/header.jsp"/>
<jsp:include page="../include/sidebar.jsp"/>

<main class="form-joinin w-1000 m-auto">
  <form name="insertUserform" method="post" action="insertUser"
        onsubmit="return check()">
    <br>
    <br>

    <h2 class="h6 fw-bold" align="center">필수 입력 항목</h2>
    <hr>

    <div class="row mb-0">
      <label for="userId" class="form-label mb-0" align="left">아이디</label>
      <div class="row mb-0 g-1 align-items-center">
        <div class="col-9 mb-1">
          <input type="text" class="form-control" id="userId" name="userId"
                 aria-describedby="idGuide" placeholder="6~12자 영대소문자, 숫자 이용"
                 minlength="3" maxlength="12" >
        </div>
        <div class="col mb-1">
          <button type="button" class="btn btn-outline-dark" onclick="checkId()"
                  style="font-size: 14px;" >중복확인</button>
        </div>
      </div>
    </div>

    <div class="form-text row mb-2" id="idGuide" align="left"></div>

    <div class="row mb-3">
      <label for="userPw" class="form-label" align="left">비밀번호</label> <input
            type="password" class="form-control" id="userPw" name="userPw"
            placeholder="8~12자 영대소문자, 숫자 이용 가능" maxlength="12"  >
    </div>
    <div class="form-text mb-2" id="pwGuide" align="left"></div>

    <div class="row mb-0">
          <label for="userName" class="form-label mb-0" align="left">닉네임</label>
          <div class="row mb-0 g-1 align-items-center">
              <div class="col-9 mb-1">
                  <input type="text" class="form-control" id="userName" name="userName"
                         aria-describedby="nickNameGuide" placeholder="2~8자 한글 영어, 숫자 이용"
                         maxlength="12">
              </div>
              <div class="col mb-1">
                  <button type="button" class="btn btn-outline-dark" onclick="chkNick()"
                          style="font-size: 14px;" >중복확인</button>
              </div>
          </div>
    </div>

    <div class="form-text mb-2" id="nickNameGuide" align="left"></div>

    <div class="row mb-3">
      <label for="userAge" class="form-label" align="left">나이</label>
      <select class="form-select" id="userAge" name="ageCd"></select>
    </div>
<%--    <div class="form-text mb-2" id="phoneGuide" align="left"></div>--%>

    <div class="row mb-3">
      <label for="userGender" class="form-label" align="left">성별</label>
      <select class="form-select" id="userGender" name="genCd"></select>
    </div>

    <div class="row mb-3">
      <label for="userLoc" class="form-label" align="left">지역</label>
      <select class="form-select" id="userLoc" name="locCd"></select>
    </div>

    <div class="row mb-3">
      <label for="userEmail" class="form-label" align="left">이메일</label> <input
            type="text" class="form-control" id="userEmail" name="userEmail"
            placeholder="email@example.com">
    </div>
    <div class="form-text mb-2" id="emailGuide" align="left"></div>
    <br>
    <br>
    <button class="btn btn-outline-dark w-100 pr-100 fw-bold" type="submit">회원가입</button>

    <p class="mt-4 mb-3 text-body-secondary" align="center"
       style="font-size: 90%">
      이미 회원이신가요?&nbsp;<a href="login.do" style="color: gray">로그인</a>
    </p>

    <p class="mt-5 mb-3 text-body-dark" align="center"
       style="font-size: 80%">&copy; 2023. 1조</p>
  </form>
<%--<%@ include file="../include/footer.jsp" %>--%>

</body>
</html>

