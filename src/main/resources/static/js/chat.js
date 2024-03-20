document.addEventListener("DOMContentLoaded", function () {
  const icon = document.querySelector(".chat-circle");
  const chat = document.querySelector(".chat-window");
  const cross = document.querySelector(".cross");

  icon.addEventListener("click", function () {
    icon.classList.add("icon-chat-close");
    icon.classList.remove("icon-chat-open");
    chat.classList.add("chat-open");
    chat.classList.remove("chat-close");
    chat.style.display = "block";
    openChat();
  });

  cross.addEventListener("click", function () {
    icon.classList.add("icon-chat-open");
    icon.classList.remove("icon-chat-close");
    chat.classList.remove("chat-open");
    chat.classList.add("chat-close");
    setTimeout(function () {
      chat.style.display = "none";
    }, 950);
    if (curSubscription) {
      curSubscription.unsubscribe();
    }
    curSubscription = null
      let chatMessages = document.getElementById('message-window');
      try {
          let messages = chatMessages.getElementsByClassName('message');
          let messagesArray = Array.from(messages);
          messagesArray.forEach(function (message) {
              message.remove();
          });
      } catch (Error){
      }
  });
});

let curSubscription = null;
const socket = new SockJS('/chat');
const stompClient = Stomp.over(socket);
stompClient.connect();

function sendMessage(senderEmail) {
  const message = {
    senderEmail: senderEmail,
    content: document.getElementById("chat-input").value,
  }
  stompClient.send("/app/chat/sendMessage", {}, JSON.stringify(message));
  document.getElementById("chat-input").value = "";
}

function displayMessage(message) {
  const chatMessages = document.getElementById('message-window');
  const messageElement = document.createElement('div');
  messageElement.classList.add('message');
  messageElement.innerHTML = '<strong style="color: #0b102b">' + message.senderEmail + ':</strong> ' + message.content;
  chatMessages.appendChild(messageElement);
}
function openChat(){
      if (curSubscription) {
        curSubscription.unsubscribe();
      }
      curSubscription =
          stompClient.subscribe('/topic/chat/sendMessage', function (message) {
            const chatMessage = JSON.parse(message.body);
            console.log('Полученное сообщение: ' + chatMessage.content);
            displayMessage(chatMessage);
          });
    let chatMessages = document.getElementById('message-window');
    try {
        let messages = chatMessages.getElementsByClassName('message');
        let messagesArray = Array.from(messages);
        messagesArray.forEach(function (message) {
            message.remove();
        });
    } catch (Error){
    }
}
