$(function () {
  //收藏职位
  $("#center .contents .header .rightinfo .collect").on("click", function () {
    $.ajax({
      url: $(this).siblings("a")[0].href,
      type: "post",
      success: function (data) {
        console.log(data);
        $(this)
          .parent()
          .html(
            '<button type="button" class="btn btn-default hascollect">已收藏</button>'
          );
      },
      error: function (xhr) {
        console.log(xhr.responseText);
      },
    });
  });
  //投递简历
  $("#center .contents .header .rightinfo .deliver").on("click", function () {
    $.ajax({
      url: $(this).siblings("a")[0].href,
      type: "post",
      success: function (data) {
        console.log(data);
        $(this)
          .parent()
          .html(
            '<button type="button" class="btn btn-default hasdeliver">已申请</button>'
          );
      },
      error: function (xhr) {
        console.log(xhr.responseText);
      },
    });
  });
});
