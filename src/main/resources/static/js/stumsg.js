$(function () {
  //全选
  $("#center .right .search-info .table .selectall").on("click", function () {
    let ocheck = $(
      "#center .right .search-info .table tr td:first-child input"
    );
    if (this.checked) {
      for (let i = 0; i < ocheck.length; i++) {
        ocheck[i].checked = true;
      }
      return;
    }
    for (let i = 0; i < ocheck.length; i++) {
      ocheck[i].checked = false;
    }
  });

  //删除消息记录
  $("#center .right .delmsg").on("click", function () {
    let ochecked = $(
      "#center .right .search-info .table tr td:first-child input:checked"
    );
    let olength = ochecked.length;
    if (olength == 0) {
      alert("请选择");
      return false;
    }
    let idlist = new Array();
    for (let i = 0; i < olength; i++) {
      idlist.push(ochecked[i].name.split("_")[1]);
    }

    $.ajax({
      url: "/student/deletemsg",
      type: "delete",
      data: {
        idlist: idlist,
      },
      success: function () {
        location.reload();
      },
      error: function (xhr) {
        console.log(xhr.responseText);
      },
    });
  });

  //根据标签查询简历
  $("#searchbystat").on("change", function () {
    if (this.value == "0") {
      return false;
    } else {
      searchByTag(this.value);
    }
  });

  //通过标签查询简历
  function searchByTag(tag) {
    console.log(tag);
    // $.ajax({
    //   url: "/student/searchbytag",
    //   type: "get",
    //   data: {
    //     tag: tag,
    //   },
    //   success: function (data) {
    //     let tmpltxt3 = doT.template($("#resumeData")[0].innerHTML);
    //     $("#center .right .table tbody").html(tmpltxt(data));
    //   },
    //   error: function (xhr) {
    //     console.log(xhr.responseText);
    //   },
    // });
  }
});
