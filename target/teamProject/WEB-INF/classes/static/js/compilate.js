function compile_code() {
    var code = document.getElementById("input").value;

    $.ajax({
        type: "POST",
        url: "/compile",
        data: code,
        contentType: "text/plain",
        success: function(response) {
            // console.log(response);
            // document.getElementById("output").value = response;
        },
        error: function(xhr, status, error) {
            // console.log("error");
            // console.error(xhr.responseText);
        }
    });
}

// function send_last_input() {
//     var outputTextarea = document.getElementById("output");
//     var lines = outputTextarea.value.split("\n");
//     var lastLine = lines[lines.length - 1];

//     $.ajax({
//         type: "POST",
//         url: "/input",
//         data: lastLine,
//         contentType: "text/plain",
//         success: function(response) {
//             console.log("Последняя строка успешно отправлена на сервер");
//         },
//         error: function(xhr, status, error) {
//             console.error("Ошибка при отправке последней строки на сервер:", xhr.responseText);
//         }
//     });
// }

// document.addEventListener("DOMContentLoaded", function() {
//     document.getElementById("output").addEventListener("keypress", function(event) {
//         if (event.key === "Enter") {
//             send_last_input();
//         }
//     });
// });

// var socket = new WebSocket("ws://localhost:8080/compilate");

// socket.onopen = function(event) {
//   console.log("WebSocket connection established.");
// };

// socket.onmessage = function(event) {
//   var message = event.data;
//   console.log("Received message from server:", message);
// };

// socket.onclose = function(event) {
//   console.log("WebSocket connection closed.");
// };

// socket.onerror = function(error) {
//   console.error("WebSocket error:", error);
// };

var socket = new SockJS('/compilate');
var stompClient = Stomp.over(socket);

stompClient.connect({}, function(frame) {
	console.log('Connected: ' + frame);
    stompClient.subscribe('/topic', function(message) {
        console.log('Received message from server:', message.body);
    });
});