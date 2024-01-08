window.addEventListener('resize', updateImageScale);
window.addEventListener('load', updateImageScale);

function updateImageScale() {
    var imageContainerLeft = document.querySelector('.LeftTower');

    var imageLeft = document.querySelector('.LeftTower img');
    var imageCenter = document.querySelector('.JavaTower img')
    var imageRight = document.querySelector('.RightTower img')

    // Определить текущий масштаб страницы
    var pageScale = window.innerWidth / document.documentElement.clientWidth;
    // Обновить ширину изображения с учетом масштаба страницы
    var newWidth = imageContainerLeft.offsetWidth * pageScale;
    imageLeft.style.width = (newWidth / 9) + 'px';
    imageCenter.style.width = (newWidth / 2.6) + 'px';
    imageRight.style.width = (newWidth / 8) + 'px';
}
