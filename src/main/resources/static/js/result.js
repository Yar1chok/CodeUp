document.addEventListener("DOMContentLoaded", function () {
  const resultBox = document.querySelector(".result-box");
  const tryAgain = document.querySelector(".tryAgain-btn");
  const homeButton = document.querySelector(".home-btn");

  resultBox.classList.add("result-box-active");
  setTimeout(() => {
    showResultBox();
  }, 500);
  tryAgain.onclick = () => {
    window.location.href = "/CodeUp/result";
  };

  homeButton.onclick = () => {
    window.location.href = "/CodeUp/result";
  };
});

function showResultBox() {
  const progressText = document.querySelector(".progress-value");
  const circularProgress = document.querySelector(".circular-progress");
  let progressStart = -1;
  let progressEnd = 80;
  const speed = 20;

  let progress = setInterval(() => {
    progressStart++;
    progressText.textContent = progressStart + "%";
    circularProgress.style.background = `conic-gradient(#c40094 ${
      progressStart * 3.6
    }deg, rgba(255, 255, 255, 0.1) 0deg)`;
    if (progressStart == progressEnd) {
      clearInterval(progress);
    }
  }, speed);
}
