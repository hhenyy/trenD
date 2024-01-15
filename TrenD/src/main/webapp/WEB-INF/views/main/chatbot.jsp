<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

 
 <!-- css 불러오기 -->
<link rel="stylesheet" href="css/chatbot.css">

<script src="./js/chatbot/chatbot.js"></script>
<script src="http://code.jquery.com/jquery-latest.js"></script>
</head>
<body>
<!-- Popup chat window -->
<!-- <div class="chat-body"> -->
<!-- chat open -->
<div class="chat-bar-open" id="chat-open" style="display: block;">
   <button id="chat-open-button" type="button" class="collapsible close"
      onclick="chatOpen()" style="overflow: visible;">
      <img src="./img/chatbot.png" alt="Chat Botimage" style="width: 65px; height: 65px; position: relative; left: -16px; top: -15px;"/>
   </button>
</div>


<!-- chat close -->
<div class="chat-bar-close" id="chat-close" style="display: none;">
   <button id="chat-close-button" type="button" class="collapsible close" onclick="chatClose()">close
     <%-- <img src="./img/bye.png" alt="Close Icon" style="width: 50px; height: 50px; position: relative; left: -15px; top: -10px;">--%>
   </button>
</div>



<!-- chat chat-window -->
<div class="chat-window" id="chat-window1" style="display: none;">
   <div class="message-box" id="messageBox">
      <div class="hi-there">
         <p class="p1"> TrenD-ing은 chatGPT 기반 챗봇입니다.<br></p>
         <p class="p2">무엇이든 물어보세요. </p>
      </div>
   </div>


<!--   enter or button click시 userRespone() 호출 -->
<div class="input-box">
   <div class="write-reply">
      <input class="inputText" type="text" id="textInput" placeholder="메시지를 입력하세요." />
   </div>
   <div class="send-button">
      <button type="submit" class="send-message" id="send" onclick="userResponse()">
         send
         <%--<img src="./img/send.png" alt="Send Icon" style="width: 40px; height: 40px;">--%>
      </button>
   </div>
</div>


</div>
<!-- </div> -->
</body>
</html>