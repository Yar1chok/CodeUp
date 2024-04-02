document.addEventListener("DOMContentLoaded", function() {
    function checkPassword() {
        const password = document.getElementById('password').value;
        const lengthRule = document.getElementById('length-rule');
        const uppercaseRule = document.getElementById('uppercase-rule');
        const lowercaseRule = document.getElementById('lowercase-rule');
        const numberRule = document.getElementById('number-rule');
        const specialRule = document.getElementById('special-rule');
        const submitButton = document.getElementById('submit-button');
        let isValid = true;
        if (password.length >= 8) {
            lengthRule.classList.add('valid');
            lengthRule.classList.remove('not-valid');
        } else {
            lengthRule.classList.add('not-valid');
            lengthRule.classList.remove('valid');
            isValid = false;
        }

        if (/[A-Z]/.test(password)) {
            uppercaseRule.classList.add('valid');
            uppercaseRule.classList.remove('not-valid');
        } else {
            uppercaseRule.classList.add('not-valid');
            uppercaseRule.classList.remove('valid');
            isValid = false;
        }

        if (/[a-z]/.test(password)) {
            lowercaseRule.classList.add('valid');
            lowercaseRule.classList.remove('not-valid');
        } else {
            lowercaseRule.classList.add('not-valid');
            lowercaseRule.classList.remove('valid');
            isValid = false;
        }

        if (/\d/.test(password)) {
            numberRule.classList.add('valid');
            numberRule.classList.remove('not-valid');
        } else {
            numberRule.classList.add('not-valid');
            numberRule.classList.remove('valid');
            isValid = false;
        }

        if (/[!@#$%/&*^№]/.test(password)) {
            specialRule.classList.add('valid');
            specialRule.classList.remove('not-valid');
        } else {
            specialRule.classList.add('not-valid');
            specialRule.classList.remove('valid');
            isValid = false;
        }
        submitButton.disabled = !isValid;
        if (!isValid){
            submitButton.style.cursor = "default";
        } else {
            submitButton.style.cursor = "pointer";
        }

    }
    const passwordInput = document.getElementById('password');
    passwordInput.addEventListener('input', checkPassword);
});