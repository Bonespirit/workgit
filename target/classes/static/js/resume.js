$(function () {
  inputchange();
  //上传简历附件
  $("#center .right .resume .selectfile").on("click", function () {
    let upload = $("#center .right .resume #uploadresume");
    upload.click();
    upload.on("change", function () {
      if (this.files.length == 0) {
        $(this).siblings(".showfilename").val("");
        return false;
      }
      if (this.files[0].size > 1024 * 1024 * 10) {
        alert("文件不能超过10M");
        this.value = null;
        return false;
      }
      let filename = this.files[0].name.split(".");
      if (
        filename[filename.length - 1] == "doc" ||
        filename[filename.length - 1] == "docx" ||
        filename[filename.length - 1] == "pdf"
      ) {
        $(this).siblings(".showfilename").val(this.files[0].name);
      } else {
        alert("请上传doc、docx、pdf格式的简历");
        this.value = null;
        return false;
      }
    });
  });

  //修改头像
  $("#center .right .studentinfo .edithead").on("click", function () {
    $("#center .right .studentinfo #uploadhead").click();
    $("#center .right .studentinfo #uploadhead").on("change", function () {
      if (this.files.length == 0) {
        return false;
      }
      if (this.files[0].size / 1024 > 300) {
        alert("头像不能超过500k!");
        return false;
      }
      let formData = new FormData();
      formData.append("head",$("#uploadhead")[0].files[0])
       $.ajax({
         url: "/student/changehead",
         type:"put",
         data:formData,
         cache: false,
         contentType: false,
         processData: false,
         success: function (data) {
           $("#center #head").attr("src", data);
           $("#center #headurl").val(data)
         },
         error: function (xhr) {
           console.log(xhr.responseText);
         },
       });
    });
  });

  //添加项目经历
  $("#center .right .project-exp .additem").on("click", function () {
    let enumber = $("#center .right .project-exp .editinfo").length;
    if (enumber == 3) {
      return false;
    }
    let editinfo = addExperienceEdit(enumber + 1);
    editinfo.append(
      '<span class="glyphicon glyphicon-trash mdelete edelete"></span>'
    );
    editinfo.appendTo("#center .right .project-exp");
    deleteD();
    inputchange();
  });

  //添加实习经历
  $("#center .right .practice-exp .additem").on("click", function () {
    let trlen = $("#center .right .practice-exp .table tr").length;
    if (trlen > 5) {
      return false;
    }
    let trtemp = $("<tr></tr>");
    trtemp.append('<td><input type="text" name="cname" /></td>');
    trtemp.append('<td><input type="text" name="cduty" /></td>');
    trtemp.append(
      '<td><input type="month" name="cbtime" />至<input type="month" name="cetime"/></td>'
    );
    trtemp.append('<td><a class="pdel" href="javascript:;">删除</a></td>');
    trtemp.appendTo("#center .right .practice-exp .table");
    pdel();
    inputchange();
  });

  //删除项目经历
  function deleteD() {
    $("#center .right .editinfo .mdelete").on("click", function () {
      if ($("#center .right .project-exp .editinfo").length == 1) {
        return false;
      }
      $(this).parent().remove();
    });
  }

  //删除实习经历
  function pdel() {
    $("#center .right .practice-exp .pdel").on("click", function () {
      $(this).parents("tr").remove();
    });
  }

  //添加项目经历模块
  function addExperienceEdit(enumber) {
    let editinfo = $('<div class="editinfo"></div>');
    editinfo.append("<h4>项目" + enumber + "</h4>");
    let trTemp = $('<form class="form-horizontal" role="form"></form>');
    trTemp.append(
      '<div class="form-group"><label for="name" class="col-sm-2 control-label">名称</label>' +
        '<div class="col-sm-10"><input type="text" class="form-control" id="name" name="name" placeholder="请输入实习单位名称或项目名称"' +
        'maxlength="40"/></div></div>'
    );
    trTemp.append(
      '<div class="form-group"><label for="duty" class="col-sm-2 control-label">担任职务</label>' +
        '<div class="col-sm-10"><input type="text" class="form-control" id="duty" name="duty" placeholder="请输入从事职务"' +
        'maxlength="40"/></div></div>'
    );
    trTemp.append(
      '<div class="form-group"><label for="duty" class="col-sm-2 control-label">时间</label>' +
        '<div class="col-sm-10"><input type="month" name="btime" />至<input type="month" name="etime"/></div></div>'
    );
    trTemp.append(
      '<div class="form-group"><label for="describe" class="col-sm-2 control-label">具体描述</label>' +
        '<div class="col-sm-10"><textarea id="describe" name="describe" rows="10" placeholder="请不要输入手机、QQ、邮箱信息"' +
        'style="resize: none" maxlength="1000"></textarea><p style="color: #999">请输入最多500字的描述</p></div></div>'
    );
    trTemp.appendTo(editinfo);
    return editinfo;
  }

  //监听input change事件
  function inputchange() {
    $("#center .right .resume input").on("change", function () {
      if ($(this).parents(".resume").hasClass("change")) {
        return false;
      }
      $(this).parents(".resume").addClass("change");
    });
    $("#center .right .resume select").on("change", function () {
      if ($(this).parents(".resume").hasClass("change")) {
        return false;
      }
      $(this).parents(".resume").addClass("change");
    });
    $("#center .right .resume textarea").on("change", function () {
      if ($(this).parents(".resume").hasClass("change")) {
        return false;
      }
      $(this).parents(".resume").addClass("change");
    });
  }

  //保存并更新
  $("#center .right .studentsave").on("click", function () {
    let reg = new RegExp("(name|duty|btime|etime|describe)");
    let inputChange = $("#center .right #mresume .change .editinfo input");
    let practiceChange = $(
      "#center .right #mresume .practice-exp.change input"
    );
    let textareaChange = $("#center .right #mresume .change textarea");
    let selectChange = $("#center .right #mresume .change select");
    let formdata = new FormData();
    if (practiceChange.length > 0) {
      formdata.append("practice", getPractice(practiceChange));
    }
    for (let i = 0; i < inputChange.length; i++) {
      let tname = inputChange[i].name;
      formdata.append(tname, inputChange[i].value);
    }
    for (let i = 0; i < textareaChange.length; i++) {
      let tname = textareaChange[i].name;
      formdata.append(tname, textareaChange[i].value);
    }
    for (let i = 0; i < selectChange.length; i++) {
      let tname = selectChange[i].name;
      formdata.append(tname, selectChange[i].value);
    }
    //是否操作头像
    if ($("#center #headurl")[0].value != "") {
		formdata.append("headurl",$("#center #headurl")[0].value)
	}
    //是否需要从新生成简历，如果简历为学生上传的本地简历，那么传回后台判断
    if ($("#center #resumeurl")[0].value != "") {
		formdata.append("resumeurl",$("#center #resumeurl")[0].value)
	}
    //是否上传本地简历
    if ($("#center #uploadresume")[0].files.length != 0) {
    	formdata.append("enclosure",$("#center #uploadresume")[0].files[0])
	}
    if (formdata.values().next().done) {
      alert("请修改");
      return false;
    }
    
	 $.ajax({
	   url: "/student/editresume",
	   type: "put",
	   data: formdata,
	   cache: false,
	   contentType: false,
	   processData: false,
	   success: function (data) {
	     console.log(data);
	     window.location.href="/"+data;
	   },
	   error: function (xhr) {
	     console.log(xhr.responseText);
	   },
	 });
  });
  
  //取消修改
  $("#center .right .studentcancel").on("click", function () {
	  window.history.go(-1);//返回上一页
  })
  
  function getPractice(practiceChange) {
    let name = new Array();
    let duty = new Array();
    let btime = new Array();
    let etime = new Array();
    let practice = new Array();
    for (let i = 0; i < practiceChange.length; i++) {
      switch (practiceChange[i].name) {
        case "cname":
          name.push(practiceChange[i].value);
          break;
        case "cduty":
          duty.push(practiceChange[i].value);
          break;
        case "cbtime":
          btime.push(practiceChange[i].value);
          break;
        case "cetime":
          etime.push(practiceChange[i].value);
          break;
        default:
          break;
      }
    }
    for (let i = 0; i < name.length; i++) {
      practice.push(name[i] + ":" + duty[i] + ":" + btime[i] + ":" + etime[i]);
    }
    return practice.toString();
  }
});
