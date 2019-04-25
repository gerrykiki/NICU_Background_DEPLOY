var stompClient = null;

function setConnected(connected) {
	$("#connect").prop("disabled", connected);// 將id = connect 按鈕屬性改為disabled
	$("#disconnect").prop("disabled", !connected);// 將id = disconnect 按鈕屬性改為disabled
	if (connected) {
		$("#conversation").show();// 當連接上Websocket id = conversation 顯示
	} else {
		$("#conversation").hide();// 將 conversation隱藏
	}
	$("#greetings").html("");// 將greeting內容清空
}

// 連結WebSocket方法
function connect() {
	var socket = new SockJS('/gs-guide-websocket');// 建立一個socket物件名稱為:/gs-guide-websocket
	stompClient = Stomp.over(socket);
	stompClient.connect({}, function(frame) {
		setConnected(true);
		console.log('Connected: ' + frame);
		stompClient.subscribe('/topic/greetings', function(greeting) {
			showGreeting(JSON.parse(greeting.body).content);
		});
	});
}

// 關閉WebSocket方法
function disconnect() {
	if (stompClient !== null) {
		stompClient.disconnect();
	}
	setConnected(false);
	console.log("Disconnected");
}

// 傳送訊息方法
function sendName() {
	stompClient.send("/app/hello", {}, JSON.stringify({
		'name' : $("#name").val()
	}));
}

// 顯示接收回來的訊息方法
function showGreeting(message) {
	$("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function() {
	$("form").on('submit', function(e) {
		e.preventDefault();
	});
	$("#connect").click(function() {
		connect();
	});
	$("#disconnect").click(function() {
		disconnect();
	});
	$("#send").click(function() {
		sendName();
	});
});