$(document).ready(function () {
  $(".avatar").on("click", function (event) {
    event.stopPropagation();
    $(".menu").toggle();
  });

  $(document).on("click", function (event) {
    if (!$(event.target).closest(".avatar_menu").length) {
      $(".menu").hide();
    }
  });
});
