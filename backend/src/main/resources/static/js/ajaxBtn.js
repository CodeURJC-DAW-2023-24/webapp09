$(window).on("load", function () {

    //loading Buttons from menu
    valueIndex(1);

    //loading Button from profile
    $("#btnMoreAdminSubjects").on("click", () =>
        functionMoreSubejctAdmin(
            "#moreAdminSubjects",
            "#spinner",
            "#btnMoreAdminSubjects"
        )
    );

});



var indexAdminSubjects;


function ajaxCall(url, spinner, where, button) {
  $.ajax({
    type: "GET",
    contentType: "aplication/json",
    url: url,
    beforeSend: function () {
      $(spinner).removeClass("hidden");
    },
    success: function (result) {
      $(where).append(result);
    },
    error: function (e) {
      $(button).addClass("hidden");
    },
    complete: function () {
      $(spinner).addClass("hidden");
    },
  });
}


function functionMoreSubejctAdmin(where, spinner, button) {
    var value = indexAdminSubjects;
    this.indexAdminSubjects += 1;
  
    var url = "/moreSubjectsAdmin?page="+value;
    ajaxCall(url, spinner, where, button);
}


function valueIndex(num) {
    this.indexAdminSubjects = num;
}
