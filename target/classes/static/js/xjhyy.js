$("#personnelList .add").on("click", function () {
  if ($("#personnelList table tr").length <= 3) {
    let trTemp = $("<tr></tr>");
    trTemp.append('<td><input type="text" name="name"></td>');
    trTemp.append('<td><input type="text" name="gender"></td>');
    trTemp.append('<td><input type="text" name="duty"></td>');
    trTemp.append('<td><input type="text" name="idcard"></td>');
    trTemp.append('<td><input type="text" name="telephone"></td>');
    trTemp.append('<td><a href="javascript:;">删除</a></td>');
    trTemp.appendTo("#personnelList .table");
  }
});
$(document).on("click", "#personnelList .table a", function () {
  $(this).parents("tr").remove();
});
