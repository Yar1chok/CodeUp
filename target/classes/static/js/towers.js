window.addEventListener('resize', updateImageScale);
window.addEventListener('load', updateImageScale);

function updateImageScale() {
    let imageContainerLeft = document.querySelector('.LeftTower');

    let imageLeft = document.querySelector('.LeftTower img');
    let imageCenter = document.querySelector('.JavaTower img')
    let imageRight = document.querySelector('.RightTower img')

    // Определить текущий масштаб страницы
    let pageScale = window.innerWidth / document.documentElement.clientWidth;
    // Обновить ширину изображения с учетом масштаба страницы
    let newWidth = imageContainerLeft.offsetWidth * pageScale;
    imageLeft.style.width = (newWidth / 9) + 'px';
    imageCenter.style.width = (newWidth / 2.6) + 'px';
    imageRight.style.width = (newWidth / 8) + 'px';
}
