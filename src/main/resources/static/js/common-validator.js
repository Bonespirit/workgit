$(function () {
	$("#post-jobinfo").bootstrapValidator({
		excluded: [":hidden"],
		submitButtons: "#submitBtn",
		message: "不能为空",
		fields: {
			title: {
				trigger: "blur",
				selector: "#title",
				validators: {
					notEmpty: {
						message: "标题不能为空",
					},
					stringLength: {
						min: 6,
						max: 50,
						message: "标题长度为6~50个字符",
					},
				},
			},
			date: {
				selector: "#date",
				validators: {
					notEmpty: {
						message: "日期不能为空",
					},
				},
			},
			time: {
				selector: "#time",
				trigger: "blur",
				validators: {
					notEmpty: {
						message: "具体时间不能为空",
					},
				},
			},
		},
	});
});
//表单提交
$("#submitBtn").on("click", function () {
	if ($("#contents")[0].value == "") {
		alert("请输入招聘简章");
		return;
	} else if (typeof $("#enclosure")[0] != "undefined" &&
			$("#enclosure")[0].value == "") {
		alert("请先上传文件");
		return;
	}
	//获取表单对象
	var bootstrapValidator = $("#post-jobinfo").data("bootstrapValidator");
	//手动触发验证
	bootstrapValidator.validate();
	if (bootstrapValidator.isValid()) {
		//表单提交的方法、比如ajax提交
		let imgsrc = new Array()
		let wimg = $("#wangEditor img");
		let mlength = wimg.length;
		for(let i=0;i<mlength;i++){
			imgsrc.push(wimg[i].src.split("/").pop())
		}
		$("#validurl").attr("value",imgsrc);
		$("#post-jobinfo")[0].submit();
	}
});
