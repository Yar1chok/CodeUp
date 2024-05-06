document.getElementById("password").addEventListener("input", function () {
  let password = this.value;
  let passwordLabel = document.getElementById("password");
  let passwordComplexity = document.getElementById("password-complexity");
  let strengthBar = document.getElementById("password-strength");
  // Проверяем сложность пароля и устанавливаем цвет шкалы
  let strength = checkPasswordStrength(password);
  let color = getColorForStrength(strength);
  strengthBar.style.width = (strength + 1) * 33.33 + "%";
  strengthBar.style.backgroundColor = color;
  strengthBar.style.boxShadow = "0 0 5px " + color;
  if (password.length < 8 || strength <= 1) {
    passwordLabel.style.border = "2px solid #FF0000";
  } else {
    passwordLabel.style.border = "2px solid #00C400";
  }
  passwordComplexity.innerText = getTextForStrength(strength);
});

document
  .getElementById("confirm-password")
  .addEventListener("input", function () {
    let confirmPassword = this.value;
    let passwordLabel = document.getElementById("password");
    let password = passwordLabel.value;
    if (confirmPassword === password) {
      this.style.border = "2px solid #00C400";
    } else {
      this.style.border = "2px solid #FF0000";
    }
  });

// Функция для проверки сложности пароля
function checkPasswordStrength(password) {
  if (!password || password.length < 4) {
    return 0;
  }
  let okRegex = new RegExp("(?=.{4,}).*");
  let mediumRegex = new RegExp(
    "^(?=.{6,}.*)(((?=.*[A-Z])(?=.*[a-z]))|((?=.*[A-Z])(?=.*[0-9]))|((?=.*[a-z])(?=.*[0-9]))).*$"
  );
  let strongRegex = new RegExp(
    "^(?=.{8,}).*(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*\\W).*$"
  );
  if (strongRegex.test(password)) return 2;
  if (mediumRegex.test(password)) return 1;
  if (okRegex.test(password)) return 0;
  return "";
}

// Функция для получения цвета в зависимости от сложности пароля
function getColorForStrength(strength) {
  switch (strength) {
    case 0:
      return "#FF0000";
    case 1:
      return "yellow";
    case 2:
      return "#00C400";
    default:
      return "";
  }
}

// Функция для получения текста в зависимости от сложности пароля
function getTextForStrength(strength) {
  switch (strength) {
    case 0:
      return "Простой";
    case 1:
      return "Средний";
    case 2:
      return "Сильный";
    default:
      return "";
  }
}

document.addEventListener("DOMContentLoaded", function () {
  function checkPassword() {
    const password = document.getElementById("password").value;
    const lengthRule = document.getElementById("length-rule");
    const uppercaseRule = document.getElementById("uppercase-rule");
    const lowercaseRule = document.getElementById("lowercase-rule");
    const numberRule = document.getElementById("number-rule");
    const specialRule = document.getElementById("special-rule");
    const submitButton = document.getElementById("submit-button");
    let isValid = true;
    if (password.length >= 8) {
      lengthRule.classList.add("valid");
      lengthRule.classList.remove("not-valid");
    } else {
      lengthRule.classList.add("not-valid");
      lengthRule.classList.remove("valid");
      isValid = false;
    }

    if (/[A-Z]/.test(password)) {
      uppercaseRule.classList.add("valid");
      uppercaseRule.classList.remove("not-valid");
    } else {
      uppercaseRule.classList.add("not-valid");
      uppercaseRule.classList.remove("valid");
      isValid = false;
    }

    if (/[a-z]/.test(password)) {
      lowercaseRule.classList.add("valid");
      lowercaseRule.classList.remove("not-valid");
    } else {
      lowercaseRule.classList.add("not-valid");
      lowercaseRule.classList.remove("valid");
      isValid = false;
    }

    if (/\d/.test(password)) {
      numberRule.classList.add("valid");
      numberRule.classList.remove("not-valid");
    } else {
      numberRule.classList.add("not-valid");
      numberRule.classList.remove("valid");
      isValid = false;
    }

    if (/[!@#$%/&*^№]/.test(password)) {
      specialRule.classList.add("valid");
      specialRule.classList.remove("not-valid");
    } else {
      specialRule.classList.add("not-valid");
      specialRule.classList.remove("valid");
      isValid = false;
    }
    const confirmPassword = document.getElementById("confirm-password");
    if (confirmPassword) {
      if (confirmPassword.value !== password) {
        isValid = false;
      }
    }
    submitButton.disabled = !isValid;
    if (!isValid) {
      submitButton.style.cursor = "default";
    } else {
      submitButton.style.cursor = "pointer";
    }
  }
  const passwordInput = document.getElementById("confirm-password");
  passwordInput.addEventListener("input", checkPassword);
});
