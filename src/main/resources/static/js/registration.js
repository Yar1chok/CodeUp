$(document).ready(function () {
// Функция для валидации email
    function validateEmail(email) {
        const regex = /^[^\s()<>@,;:\/]+@\w[\w\.-]+\.[a-z]{2,}$/i;
        return regex.test(email);
    }

    $('#email').on('input', function () {
        const emailInput = $(this).val();
        const isValid = validateEmail(emailInput);

        if (isValid) {
            $(this).css({
                'border': '2px solid green',
                'box-shadow': '0 0 5px #00C400',
            });
        } else {
            $(this).css({
                'border': '2px solid red',
                'box-shadow': '0 0 5px #FF0000'
            });
        }
    });
});


document.getElementById('nickname').addEventListener('input', function () {
    let nickname = this.value;
    let nicknameLabel = document.getElementById('nickname');
    if (nickname.length < 3) {
        nicknameLabel.style.border = '2px solid #FF0000';
    } else {
        nicknameLabel.style.border = '2px solid #00C400';
    }
});


document.getElementById('password').addEventListener('input', function () {
    let password = this.value;
    let passwordLabel = document.getElementById('password');
    let passwordComplexity = document.getElementById('password-complexity');
    let strengthBar = document.getElementById('password-strength');
    // Проверяем сложность пароля и устанавливаем цвет шкалы
    let strength = checkPasswordStrength(password);
    let color = getColorForStrength(strength);
    strengthBar.style.width = (strength + 1) * 33.33 + '%';
    strengthBar.style.backgroundColor = color;
    strengthBar.style.boxShadow = '0 0 5px ' + color;
    if (password.length < 4) {
        passwordLabel.style.border = '2px solid #FF0000';
    } else {
        passwordLabel.style.border = '2px solid #00C400';
    }
    passwordComplexity.innerText = getTextForStrength(strength);
});

// Функция для проверки сложности пароля
function checkPasswordStrength(password) {
    if (!password || password.length < 4) {
        return 0;
    }
    let okRegex = new RegExp("(?=.{4,}).*");
    let mediumRegex = new RegExp("^(?=.{6,}.*)(((?=.*[A-Z])(?=.*[a-z]))|((?=.*[A-Z])(?=.*[0-9]))|((?=.*[a-z])(?=.*[0-9]))).*$");
    let strongRegex = new RegExp("^(?=.{8,}).*(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*\\W).*$");
    if (strongRegex.test(password)) return 2;
    if (mediumRegex.test(password)) return 1;
    if (okRegex.test(password)) return 0;
    return '';
}

// Функция для получения цвета в зависимости от сложности пароля
function getColorForStrength(strength) {
    switch (strength) {
        case 0:
            return '#FF0000';
        case 1:
            return 'yellow';
        case 2:
            return '#00C400';
        default:
            return '';
    }
}

// Функция для получения текста в зависимости от сложности пароля
function getTextForStrength(strength) {
    switch (strength) {
        case 0:
            return 'Простой';
        case 1:
            return 'Средний';
        case 2:
            return 'Сильный';
        default:
            return '';
    }
}
