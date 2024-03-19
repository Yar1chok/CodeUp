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
  });

  cross.addEventListener("click", function () {
    icon.classList.add("icon-chat-open");
    icon.classList.remove("icon-chat-close");
    chat.classList.remove("chat-open");
    chat.classList.add("chat-close");
    setTimeout(function () {
      chat.style.display = "none";
    }, 950);
  });
});
