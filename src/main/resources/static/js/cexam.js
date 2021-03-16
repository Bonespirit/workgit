$(function () {
  $("#center .right .info .allow").on("click", function () {
    if ($("#cr-agree").length > 0) {
      $("#cr-agree").addClass("mactive");
    } else {
      if (confirm("请再次确认是否审核!")) {
      } else {
        return false;
      }
    }
  });
  $("#cr-agree button")
    .eq(0)
    .on("click", function () {
      $("#cr-agree").removeClass("mactive");
    });
  $("#center .right .info .refuse").on("click", function () {
    $("#cr-refuse").addClass("mactive");
  });
  $("#cr-refuse div button")
    .eq(0)
    .on("click", function () {
      $("#cr-refuse").removeClass("mactive");
    });

  $("#pre-show button").on("click", function () {
    $("#pre-show").hide();
  });
});
