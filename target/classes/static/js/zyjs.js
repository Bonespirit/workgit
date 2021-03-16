function getMajors(code, item) {
  $("#center .right .item #major").remove();
  let mdiv = $('<div id="major"></div>');
  $(item).after(mdiv);
  let tmpltxt = doT.template($("#collegeData")[0].innerHTML); //生成doT模板
  $.ajax({
    url: [[@{/enterprise/college/}]] + code,
    type: "get",
    success: function (data) {
      $("#major").html(tmpltxt(data));
    },
  });
}
