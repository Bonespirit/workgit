$(function () {
	$("#alterpw").bootstrapValidator({
		excluded: [":hidden"],
		fields: {
			oldpw: {
				selector: "#oldpw",
				validators: {
					notEmpty: {
						message: "密码不能为空",
					},
					regexp: {
						regexp: /^[\@A-Za-z0-9\!\#\$\%\^\&\*\.\~]{6,15}$/,
						message: "密码是由数字、26个英文字母或者符号组成",
					},
					stringLength: {
						min: 6,
						max: 15,
						message: "密码长度为6~15",
					},
				},
			},
			newpw: {
				trigger: "blur",
				selector: "#newpw",
				validators: {
					notEmpty: {
						message: "密码不能为空",
					},
					regexp: {
						regexp: /^[\@A-Za-z0-9\!\#\$\%\^\&\*\.\~]{6,15}$/,
						message: "密码是由数字、26个英文字母或者符号组成",
					},
					stringLength: {
						min: 6,
						max: 15,
						message: "密码最小长度为6",
					},
					different: {
						field: "oldpw",
						message: "新密码和旧密码相同",
					},
				},
			},
			cpw: {
				trigger: "blur",
				selector: "#cpw",
				validators: {
					notEmpty: {
						message: "确认密码不能为空",
					},
					identical: {
						field: "newpw",
						message: "确认密码与新密码不一致，请确认",
					},
				},
			},
		},
	});
});
