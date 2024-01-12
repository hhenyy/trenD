/*챗봇js*/

function chatOpen() {
	document.getElementById("chat-open").style.display = "none";
	document.getElementById("chat-close").style.display = "block";
	document.getElementById("chat-window1").style.display = "block";
}
function chatClose() {
	document.getElementById("chat-open").style.display = "block";
	document.getElementById("chat-close").style.display = "none";
	document.getElementById("chat-window1").style.display = "none";
}
function openConversation() {
	document.getElementById("chat-window1").style.display = "none";
}

//Gets the text from the input box(user)
function userResponse() {
	let userText = document.getElementById("textInput").value;

	if (userText == "") {
		alert("Please type something!");
	} else {
		document.getElementById("messageBox").innerHTML += `<div class="first-chat">
      <p>${userText}</p>
      <div class="arrow"></div>
    </div>`;
		document.getElementById("textInput").value = "";
		var objDiv = document.getElementById("messageBox");
		objDiv.scrollTop = objDiv.scrollHeight;

		setTimeout(() => {
			adminResponse(userText);
		}, 100);
	}
}

//admin Respononse to user's message
//js에는 event source로 event를 받음
function adminResponse(userText) {
	source = new EventSource(`/chat?prompt=${userText}`);
	console.log("create EventSource");
	let count = 0;

	source.onmessage = function(ev) {
		console.log("on message: ", ev.data, ":", ev.data.length);
		if(count == 0) {
			answerHTML = `
				<div class="second-chat">
				<div class="circle" id="circle-mar"></div>
				<p></p>
				<div class="arrow"></div>
			</div>
				`;
			document.getElementById(
				"messageBox"
			).innerHTML += answerHTML;
		}
		let secondChatElements = document.querySelectorAll("#messageBox .second-chat");
		let lastSecondChatElement = secondChatElements[secondChatElements.length - 1];
		let pElement = lastSecondChatElement.querySelector("p");

		if (pElement) {
			// Replace the text content with your new text
			// if (pElement.textContent != "")
			// 	pElement.textContent += "\u00A0";
			let ans = ev.data;
			ans = ans.replaceAll('+',' ');
			pElement.textContent += ans;
			count += 1;
		}
		var objDiv = document.getElementById("messageBox");
		objDiv.scrollTop = objDiv.scrollHeight;
	};
	source.onerror = function(err) {
		console.log("on err: ", err);
		source.close();
		source = null;
	};
	
	// $.ajax({
	// 	url: 'chat',
	// 	type: 'POST',
	// 	data: { 'prompt': userText },
	// 	success: function(data) {
	// 		console.log(data)
			
	// 		answerHTML = `
	// 			<div class="second-chat">
	//           <div class="circle" id="circle-mar"></div>
	//           <p>${data}</p>
	//           <div class="arrow"></div>
	//         </div>
	//           `;
	// 		document.getElementById(
	// 			"messageBox"
	// 		).innerHTML += answerHTML;

	// 		var objDiv = document.getElementById("messageBox");
	// 		objDiv.scrollTop = objDiv.scrollHeight;
	// 	}
	// });
}

//press enter on keyboard and send message
addEventListener("keypress", (e) => {
	if (e.keyCode === 13) {
		const e = document.getElementById("textInput");
		if (e === document.activeElement) {
			userResponse();
		}
	}
});