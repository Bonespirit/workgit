$(function () {
	//导出简历
	$("#center .right .jobapp-item .download").on("click", function () {
		let idlist = getCheck(2);
		if (!idlist) {
			return false;
		}
		console.log("download studentid " + idlist.toString());
		$.ajax({
			url: "/downloadresume",
			type: "get",
			data: {
				idlist: idlist,
			},
			success: function (data) {
				console.log(data)
//				window.location.href="/"+data[0];
			},
			error: function (xhr) {
				alert("下载失败")
				console.log(xhr.responseText);
			},
		});
	});

	//收藏
	$("#center .right .jobapp-item .collect").on("mouseenter", function () {
		$(this).siblings(".tips").addClass("ractive");
	});
	$("#center .right .jobapp-item .collect").on("mouseleave", function () {
		$(this).siblings(".tips").removeClass("ractive");
	});
	$("#center .right .jobapp-item .collect").on("click", function () {
		let idlist = getCheck(1);
		if (!idlist) {
			return false;
		}
		console.log("collect " + idlist.toString());
		$.ajax({
			url: "/enterprise/resumecollect",
			type: "post",
			data: {
				idlist: idlist,
			},
			success: function (data) {
				console.log(data)
				location.reload();
			},
			error: function (xhr) {
				alert("请重试")
				console.log(xhr.responseText);
			},
		});
	});

	//发布系统通知
	$("#sysnotice").on("click", function () {
		if ($("#myModal #msgcontent")[0].value == "") {
			alert("请输入正文!");
			return false;
		}
		$("#myModal form")[0].submit();
	});
	//自行通知
	$("#selfnotice").on("click", function () {
		$.ajax({
			url: "/enterprise/selfnotice",
			type: "put",
			data: {
				idlist: $("#myModal form #idlist").val(),
			},
			success: function (data) {
				console.log(data)
				location.reload();
			},
			error: function (xhr) {
				alert("请重试")
				console.log(xhr.responseText);
			},
		});
	});

	//通知按钮事件
	$("#center .right .jobapp-item .notice").on("click", function () {
		let idlist = getCheck(1);
		if (!idlist) {
			return false;
		}
		$("#myModal #idlist").attr("value", idlist);
	});
	//不合适
	$("#center .right .jobapp-item .rout").on("click", function () {
		let idlist = getCheck(1);
		let sheet = $(this).siblings("input").val()
		if (!idlist) {
			return false;
		}
		console.log("idlist: " + idlist.toString()+"sheet: "+sheet);
		$.ajax({
			url: "/enterprise/resumeout",
			type: "post",
			data: {
				idlist: idlist,
				sheet: sheet
			},
			success: function () {
				location.reload();
			},
			error: function (xhr) {
				console.log(xhr.responseText);
			},
		});
	});

	//全选操作
	$("#center .right .jobapp-item .table .selectall").on("click", function () {
		let ocheck = $(
				"#center .right .jobapp-item .table tr td:first-child input"
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

	//退出自定义标签设置
	$("#customtag .cancel").on("click", function () {
		$("#customtag").removeClass("mactive");
	});

	//确定自定义标签
	function deterSet(idlist) {
		$("#customtag .deter").on("click", function () {
			let mtag = $("#customtag input").val();
			if (mtag == "") {
				return false;
			}
			changeTag(mtag, idlist);
			$("#customtag").removeClass("mactive");
		});
	}

	//设置自定义查询标签
	function deterSearchTag() {
		$("#customtag .deter").on("click", function () {
			let mtag = $("#customtag input").val();
			if (mtag == "") {
				return false;
			}
			searchByTag(mtag);
			$("#customtag").removeClass("mactive");
		});
	}

	//设置简历标签
	$("#resumeop").on("change", function () {
		let idlist = getCheck(1);
		if (!idlist || this.value == "0") {
			this.value = "0";
			return false;
		}
		if (this.value == "-1") {
			$("#customtag").addClass("mactive");
			this.value = "0";
			deterSet(idlist);
			return false;
		}
		changeTag(this.value, idlist);
	});

	//根据标签查询简历
	$("#searchbystat").on("change", function () {
		if (this.value == "0") {
			return false;
		} else if (this.value == "-1") {
			$("#customtag").addClass("mactive");
			this.value = "0";
			deterSearchTag();
			return;
		} else {
			searchByTag(this.value);
		}
	});

	//通过标签查询简历
	function searchByTag(tag) {
		console.log(tag);
		$.ajax({
			url: "/enterprise/psearch/tag/"+tag+"?page=1",
			type: "get",
			success: function (data) {
				console.log(data)
			},
			error: function (xhr) {
				console.log(xhr.responseText);
			},
		});
	}

	//修改标志
	function changeTag(status, idlist) {
		$.ajax({
			url: "/enterprise/changestatus",
			type: "put",
			data: {
				idlist: idlist,
				status: status,
			},
			success: function (data) {
				console.log(data)
			},
			error: function (xhr) {
				console.log(xhr.responseText);
			},
		});
	}
	//获取id
	function getCheck(index) {
		let ochecked = $(
				"#center .right .jobapp-item .table tr td:first-child input:checked"
		);
		let olength = ochecked.length;
		if (olength == 0) {
			alert("请选择要操作的学生");
			return false;
		}
		let idlist = new Array();
		for (let i = 0; i < olength; i++) {
			idlist.push(ochecked[i].name.split("_")[index]);
		}
		return idlist.toString();
	}
});
