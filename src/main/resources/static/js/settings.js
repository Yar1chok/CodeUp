document.addEventListener('DOMContentLoaded', function() {
    var errorModal = document.querySelector('.error');
    var errorForm = document.querySelector('.error_form');
    var repeatButton = document.querySelector('.repeat');
    var cancelButton = document.querySelector('.cancel');
    var daySelect = document.getElementById('day');
    var yearSelect = document.getElementById('year');
    var photoInput = document.getElementById('photoInput');
    var previewImage = document.getElementsByClassName('profile_img')[0];
    var githubLinkInput = document.getElementById('githubLink');
    var placeholderText = 'https://github.com/';

    githubLinkInput.addEventListener('focus', function() {
        if (this.value === '' || this.value === placeholderText) {
            this.value = placeholderText;
        }
    });

    githubLinkInput.addEventListener('blur', function() {
        var currentValue = this.value.trim();

        if (currentValue === placeholderText) {
            this.value = '';
        }
    });

    githubLinkInput.addEventListener('input', function() {
        var currentValue = this.value.trim();

        if (currentValue === '') {
            this.value = '';
        }
    });
    function openErrorModal() {
        errorModal.style.display = 'block';
    }

    function closeErrorModal() {
        errorModal.style.display = 'none';
    }

    function handleFileUpload(file) {
        var allowedTypes = ['image/jpeg', 'image/png', 'image/gif'];

        if (file && allowedTypes.includes(file.type)) {
            var reader = new FileReader();
            reader.onload = function() {
                previewImage.src = reader.result;
            }
            reader.readAsDataURL(file);
        } else {
            openErrorModal();
        }
    }

    repeatButton.addEventListener('click', function() {
        closeErrorModal();
        photoInput.click();
    });

    photoInput.addEventListener('change', function() {
        var file = this.files[0];
        handleFileUpload(file);
    });

    cancelButton.onclick = function() {
        closeErrorModal();
    };

    document.addEventListener('click', function(event) {
        if (event.target === errorModal) {
            closeErrorModal();
        }
    });

    errorForm.addEventListener('click', function(event) {
        event.stopPropagation();
    });
});
