document.addEventListener("DOMContentLoaded", function () {
  const quizBox = document.querySelectorAll(".quiz-box");
  quizBox[0].classList.add("quiz-box-active");
  const buttonNextLvl = document.querySelectorAll(".button-next-lvl");
  const url = window.location.href;
  const parts = url.split("/");
  const block = parts[parts.length - 2];
  const level = parts[parts.length - 1];
  const heads = document.getElementsByTagName("h1"); // Получаем коллекцию элементов h1
  const headsArray = Array.from(heads);

  headsArray.forEach((head) => {
    head.textContent = "Уровень " + block + "." + level;
  });

  let countLevel = 0;
  buttonNextLvl.forEach(function (button) {
    button.onclick = () => {
      countLevel++;
      setupLevel(countLevel);
    };
  });

  let answers = quizBox[countLevel].querySelectorAll(".list-answers .answer");
  answers.forEach(function (answer) {
    answer.addEventListener("click", function () {
      answers.forEach(function (item) {
        item.classList.remove("answer-active");
      });
      this.classList.add("answer-active");
      buttonNextLvl[countLevel].style.pointerEvents = "visible";
      buttonNextLvl[countLevel].classList.add("button-next-lvl-active");
    });
  });

  function setupLevel(level) {
    buttonNextLvl[level].onclick = () => {
      if (level < buttonNextLvl.length - 1) {
        countLevel++;
        setupLevel(level + 1); // Рекурсивный вызов для следующего уровня
      }
    };
  }

  function setupLevel() {
    quizBox[countLevel].classList.add("quiz-box-active");
    quizBox[countLevel - 1].style.display = "none";
    quizBox[countLevel].style.display = "";
    let answers = quizBox[countLevel].querySelectorAll(".list-answers .answer");
    answers.forEach(function (answer) {
      answer.addEventListener("click", function () {
        answers.forEach(function (item) {
          item.classList.remove("answer-active");
        });
        this.classList.add("answer-active");
        buttonNextLvl[countLevel].style.pointerEvents = "visible";
        buttonNextLvl[countLevel].classList.add("button-next-lvl-active");
      });
    });
  }
});
