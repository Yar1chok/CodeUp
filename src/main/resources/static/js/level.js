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

function runCode() {
    var code = document.getElementById('code').value;


    $.ajax({
        type: "POST",
        url: "http://localhost:9090/compile",
        data: JSON.stringify({ code: code }),
        contentType: "application/json",
        success: function(response) {
            document.getElementById('output').innerText = "Результат компиляции: " + response;
        },
        error: function() {
            alert('Произошла ошибка при отправке кода на компиляцию.');
        }
    });
}
