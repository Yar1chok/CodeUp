document.addEventListener("DOMContentLoaded", function() {
    const buttons = document.querySelectorAll('.chapter');
    const chapterName = document.querySelector('.chapter_name');
    const description = document.querySelector('.description');

    buttons.forEach(button => {
        button.addEventListener('click', function() {
            chapterName.textContent = this.textContent;
            fetch('/texts/description.json')
                .then(response => response.json())
                .then(data => {
                    const chapters = data.chapters;
                    const selectedChapter = chapters.find(chapter => chapter.name === this.textContent);

                    if (selectedChapter) {
                        description.textContent = selectedChapter.description;
                    } else {
                        description.textContent = 'Описание отсутствует';
                    }
                })
                .catch(error => console.error('Ошибка загрузки текстов:', error));
        });
    });
});
