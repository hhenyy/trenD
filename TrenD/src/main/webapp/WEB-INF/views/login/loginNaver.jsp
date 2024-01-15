<%--
  Created by IntelliJ IDEA.
  User: skinl
  Date: 2024-01-01
  Time: 오후 9:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>TrenD</title>
    <script type="text/javascript" src="https://static.nid.naver.com/js/naverLogin_implicit-1.0.3.js" charset="utf-8"></script>
    <script src="http://code.jquery.com/jquery-latest.js"></script>
</head>
<body>
<script type="text/javascript">

    // .gitignore
    var naver_id_login = new naver_id_login("Vr6jD6ZAK9pWVBE5bZhK", "http://localhost/naverCallback");
      //alert(naver_id_login.oauthParams.access_token);
    naver_id_login.get_naver_userprofile("naverSignInCallback()");
      //alert('콜백실행');

    // 네이버 사용자 프로필 조회 이후 프로필 정보를 처리할 callback function
    function naverSignInCallback() {
        id = naver_id_login.getProfileData('id');
        name = naver_id_login.getProfileData('name'); // 사용자 이름
        nickname = naver_id_login.getProfileData('nickname'); // skinlet
        email = naver_id_login.getProfileData('email'); // skinlet@naver.com

        $.ajax({
           type: 'POST',
           url: 'joinNaverUser',
           data : {'userId': id, 'userName': name, 'userEmail':email},
           success: function(result){
               console.log("네이버 로그인 성공");
               // 연동기록 없는 네이버 로그인 경우
                if(result === 'J'){
                    alert("안녕하세요. 트렌D 오신걸 환영합니다. \n 최초의 로그인은 나이, 지역, 성별을 설정해야 합니다.");
                    location.href = 'editUserForm';
                    // 탈퇴한 회원
                } else if(result === 'N'){
                    alert("탈퇴한 아이디 입니다. \n 소셜로그인으로 재가입을 원하실 경우 문의 메일을 남겨주세요.")
                    location.href = 'loginout_ok';
                } else if(result === 'Y'){
                    location.href="loginout_ok";
                } else {
                    alert("네이버 로그인이 실패하였습니다. \n 가입후 로그인 해주세요.");
                    location.href="loginout_ok";
                }
           },
            error: function(result){
               alert("네이버 로그인이 실패하였습니다. \n 가입후 로그인 해주세요.");
                location.href="loginout_ok";
            }
        });

        // $.ajax({
        //     type: 'POST',
        //     url: 'joinNaverMem.do',
        //     data: {'userId':id, 'userName':name, 'userEmail':email},
        //     success: function(result){
        //         if(result=="Y"){
        //             alert('환영합니다. '+name+'님!\n오브리에서 다양한 연주자들과 소통하세요.');
        //             location.href="home.do";
        //         }else if(result=="N"){
        //             alert('탈퇴한 계정입니다.\n소셜로그인으로 재가입을 원하실 경우 문의 메일을 남겨주세요.');
        //             location.href="home.do";
        //         }else if(result=="X"){
        //             alert('관리자에 의해 사용이 정지된 계정입니다.');
        //             location.href="home.do";
        //         }else{
        //             alert('네이버 로그인에 실패했습니다.\n가입 후 로그인을 시도해주세요.');
        //             location.href="login.do";
        //         }
        //     },
        //     error: function(result){
        //         alert('네이버 로그인에 실패했습니다.\n가입 후 로그인을 시도해주세요.');
        //         location.href="login.do";
        //     }
        // })
    }
</script>
</body>
</html>
