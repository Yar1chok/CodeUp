$(document).ready(function(){
    $(".avatar").on("click", function(event){
        event.stopPropagation();
        $(".menu").toggle();
    });

    $(document).on("click", function(event){
        if (!$(event.target).closest(".avatar_menu").length) {
            $(".menu").hide();
        }
    });
});



fetch('/texts/questions.json')
  .then(response => response.json())
  .then(data => {
    const questionElement = document.querySelector('.question');
    const answerLabels = document.querySelectorAll('label[for^="answer_"]');
    const submitButton = document.querySelector('.but');
    const resultMessage = document.querySelector('.result_message');
    const resultText = document.getElementById('result_text');

    let currentQuestionIndex = 0;
    let currentQuestion = data[currentQuestionIndex];

    function displayQuestion() {
      questionElement.textContent = currentQuestion.question;
      currentQuestion.answers.forEach((answer, index) => {
        answerLabels[index].textContent = answer;
      });
    }

    displayQuestion();

    submitButton.addEventListener('click', (event) => {
      event.preventDefault();

      const selectedAnswer = document.querySelector('input[name="answer"]:checked');
      if (!selectedAnswer) {
        resultMessage.style.display = 'block';
        resultText.textContent = 'Пожалуйста, выберите ответ.';
        return;
      }

      const selectedAnswerIndex = parseInt(selectedAnswer.value.split('_')[1]);
      if (selectedAnswerIndex === currentQuestion.correctAnswer) {
        // resultText.textContent = 'Ответ верный!';
        resultMessage.style.display = 'none';
        currentQuestionIndex++;
        if (currentQuestionIndex < data.length) {
          currentQuestion = data[currentQuestionIndex];
          displayQuestion();
        } else {
            resultMessage.style.display = 'block';
            resultText.textContent = 'Вопросы закончились';
        }

        document.querySelector('input[name="answer"]:checked').checked = false;
      } else {
        resultMessage.style.display = 'block';
        resultText.textContent = 'Ответ неверный!';
      }
    });
  })
  .catch(error => {
    console.error('Ошибка загрузки или обработки JSON файла:', error);
  });
