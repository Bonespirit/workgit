$(function () {
	$("#enterprise").bootstrapValidator({
		excluded: [":hidden"],
		submitButtons: "#submitBtn",
		fields: {
			loginName: {
				selector: "#loginName",
				validators: {
					notEmpty: {
						message: "登录用户名不能为空",
					},
					stringLength: {
						min: 3,
						message: "用户名最小长度为3个字符",
					},
					regexp: {
						regexp: /^(?!_)(?!\.)(?!.*?_$)(?![0-9]+$)[a-zA-Z0-9_\@\.]+$/,
						message: "姓名不能只含有数字、下划线不能以下划线开头和结尾",
					},
					// remote: {
					//   url: "",
					//   data: {
					//     loginName: $("#loginName").val(),
					//   },
					//   message: "登录用户名已存在",
					// },
				},
			},
			password: {
				selector: "#password",
				validators: {
					notEmpty: {
						message: "密码不能为空",
					},
					regexp: {
						regexp: /^[\@A-Za-z0-9\!\#\$\%\^\&\*\.\~]{6,15}$/,
						message: "密码是由数字、26个英文字母或者符号组成",
					},
					different: {
						field: "loginName",
						message: "密码不能和用户名一致",
					},
					stringLength: {
						min: 6,
						max: 15,
						message: "密码最小长度为6",
					},
				},
			},
			passwordConfirm: {
				trigger: "blur",
				selector: "#passwordConfirm",
				validators: {
					notEmpty: {
						message: "确认密码不能为空",
					},
					identical: {
						field: "password",
						message: "确认密码与新密码不一致，请确认",
					},
				},
			},
			contactName: {
				selector: "#contactName",
				validators: {
					notEmpty: {
						message: "联系人姓名不能为空",
					},
					regexp: {
						regexp: /^(?!_)(?!.*?_$)[a-zA-Z0-9_\u4e00-\u9fa5]{2,20}$/,
						message: "姓名只含有汉字、数字、字母、下划线不能以下划线开头和结尾",
					},
				},
			},
			schoolmateCard: {
				selector: "#schoolmateCard",
				validators: {
					notEmpty: {
						message: "校友卡号不能为空",
					},
					regexp: {
						regexp: /^[XYK]+[0-9]{11}$/,
						message: "卡号格式不正确，请重新输入",
					},
				},
			},
			telephone: {
				selector: "#telephone",
				validators: {
					notEmpty: {
						message: "联系人电话不能为空",
					},
					regexp: {
						regexp: /^1[1|2|3|4|5|6|7|8|9|][0-9]{9}$/,
						message: "请输入有效的联系人电话",
					},
				},
			},
			officeCellphone: {
				selector: "#officeCellphone",
				validators: {
					notEmpty: {
						message: "办公电话不能为空",
					},
					regexp: {
						regexp: /^(\d{3,4}-?)?\d{7,8}$/,
						message: "请输入有效的办公电话",
					},
				},
			},
			postcode: {
				validators: {
					notEmpty: {
						message: "邮编不能为空",
					},
					regexp: {
						regexp: /^[0-9]{6}$/,
						message: "请输入有效的邮编",
					},
				},
			},
			fax: {
				selector: "#fax",
				validators: {
					notEmpty: {
						message: "传真不能为空",
					},
					regexp: {
						regexp: /^(\d{3,4}-)?\d{7,8}$/,
						message: "请输入有效的传真",
					},
				},
			},
			cemail: {
				selector: "#cemail",
				validators: {
					notEmpty: {
						message: "联系邮箱不能为空",
					},
					emailAddress: {
						message: "请正确输入邮箱地址",
					},
				},
			},
			zpemail: {
				selector: "#zpemail",
				validators: {
					notEmpty: {
						message: "招聘邮箱不能为空",
					},
					emailAddress: {
						message: "请正确输入邮箱地址",
					},
				},
			},
			companyName: {
				trigger: "blur",
				selector: "#companyName",
				validators: {
					notEmpty: {
						message: "单位名称不能为空",
					},
					stringLength: {
						min: 3,
						max: 30,
						message: "单位名称长度为3至30个字符",
					},
					// remote: {
					//   url: "",
					//   data: {
					//     name: $("#name").val(),
					//   },
					//   message:
					//     '单位名称已存在，请<a href="/information/enterprise-register!reback.action" target="_self" href="#">单击此处申请重置密码</a>',
					// },
				},
			},
			introduction: {
				selector: "#introduction",
				validators: {
					notEmpty: {
						message: "单位简介不能为空",
					},
					stringLength: {
						min: 0,
						max: 300,
						message: "单位简介长度最大为300字符",
					},
				},
			},
			address: {
				selector: "#address",
				validators: {
					notEmpty: {
						message: "单位地址不能为空",
					},
					stringLength: {
						min: 0,
						max: 100,
						message: "单位地址长度最大为100字符",
					},
				},
			},
			mark: {
				selector: "#mark",
				validators: {
					notEmpty: {
						message: "验证码不能为空",
					},
					regexp: {
						regexp: /\w{0,5}/,
						message: "请输入有效验证码",
					},
					// remote: {
					//   url: "",
					//   data: {
					//     mark: $("#mark").val(),
					//   },
					//   message: "验证失败",
					// },
				},
			},
		},
	});
});

//是否校友企业判定
//是校友
function Schoolmate(tbtn) {
	$(tbtn)[0].className += " mybtn";
	$(tbtn).siblings("button").removeClass("mybtn");
	$(tbtn).siblings("input").val("1");
	$("#register .register-info #schoolmateInfo").attr("class", "mactive");
}
//不是校友
function noSchoolmate(tbtn) {
	$(tbtn)[0].className += " mybtn";
	$(tbtn).siblings("button").removeClass("mybtn");
	$(tbtn).siblings("input").val("0");
	$("#register .register-info #schoolmateInfo").removeClass("mactive");
}
//表单提交
$("#submitBtn").on("click", function () {
	//获取表单对象
	var bootstrapValidator = $("#enterprise").data("bootstrapValidator");
	//手动触发验证
	bootstrapValidator.validate();
	if (bootstrapValidator.isValid()) {
		if ($("#license")[0].files.length == 0) {
			alert("请添加营业执照或组织机构代码证");
		}
		//表单提交的方法、比如ajax提交
		$("#enterprise")[0].submit();
	}
});
