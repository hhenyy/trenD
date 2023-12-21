<%--
  Created by IntelliJ IDEA.
  User: skinl
  Date: 2023-12-21
  Time: 오후 4:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>회원가입</title>

  <script src="http://code.jquery.com/jquery-latest.js"></script>

  <script>
  function(checkId){
    console.log("체크ID 들어옴");
    $.post()
  }


  </script>
</head>
<body>
 <form name = "joinForm" method="post" action="joinForm">
   <div class="row mb-0">
     <label for="userId" class="form-label mb-0" align="left">아이디</label>
     <div class="row mb-0 g-1 align-items-center">
       <div class="col-9 mb-1">
         <input type="text" class="form-control" id="userId" name="userId"
                aria-describedby="idGuide" placeholder="4~12자 영소문자, 숫자 이용 특수문자 사용 안됨"
                maxlength="12" oninput="numberMaxLength(this);">
       </div>
       <div class="col mb-1">
         <button type="button" class="btn btn-outline-dark" onclick="checkId()"
                 style="font-size: 14px;" >중복확인</button>
       </div>
     </div>
   </div>

 </form>

</body>
</html>
