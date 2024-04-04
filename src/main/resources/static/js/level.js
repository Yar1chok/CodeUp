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

  const answerMap = {};
  let countLevel = 0;
  setupLevel();

  buttonNextLvl.forEach(function (button, index) {
    button.onclick = () => {
      let answer = document.querySelector(".answer-active");
      countLevel++;
      quizBox[countLevel].classList.add("quiz-box-active");
      quizBox[countLevel - 1].style.display = "none";
      quizBox[countLevel].style.display = "";
      setupLevel();
      answerMap[countLevel.toString] = answer.value;
      console.log(answerMap[countLevel.toString]);
    };
    if (index === buttonNextLvl.length - 1) {
      button.addEventListener("submit", function (event) {
        event.preventDefault();
        const userAnswers = document.getElementById("userAnswers");
        userAnswers.value = answerMap.toString();
        event.submit();
      });
    }
  });

  function setupLevel() {
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
