let unreg = new RegExp("^(?!_)[0-9a-zA-Z|_]{3,20}(?<!_)$");
let pdreg = new RegExp("^(?!_)[0-9a-zA-Z|_]{8,16}(?<!_)$");
let emailreg = new RegExp("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(.[a-zA-Z0-9_-]+)+$");
let creditCodereg = new RegExp("^[0-9a-zA-Z]{18}$");
let telereg = new RegExp("^1[0-9]{10}$");
let city = { 110000: [{ citycode: 110000 }] };

//上传文件
function uploadFile(upload) {
	let fileInput = $(upload).parents(".form-group").find(".uploadfile");
	fileInput.click();
	$(upload)
	.parents(".form-group")
	.find(".uploadfile")
	.unbind()
	.change(function (e) {
		$(this).siblings("label").removeClass("mactive");
		let mfiles = this.files;
		// let fileID = new Array();
		if (mfiles.length == 0) {
			$(this).siblings("input").val("");
			$(this).siblings("label")[1].className += " mactive";
			return;
		}
		if (mfiles[0].size > 1024 * 300) {
			$(this).siblings("input").val("");
			$(this).siblings("label")[0].className += " mactive";
			this.value = null;
			return;
		}
		$(this).siblings("input").val(mfiles[0].name);
		// for (let i = 0; i < mfiles.length; i++) {
		//   if (fileID.indexOf(mfiles[i].lastModified) != -1) {
		//     fileInput.siblings("label")[2].className += " mactive";
		//     return;
		//   }
		//   fileID.push(mfiles[i].lastModified);
		// }
	});
}
//////////////忘记密码模块/////////////
//表单提交校验
function forgetSubmit(btn) {
	let forgetInput = $(btn).parents(".forget-form").find("input");
	if (
			check(forgetInput[0], unreg) &&
			check(forgetInput[1], emailreg) &&
			check(forgetInput[2], new RegExp(""))
	) {
		$("form").submit();
	}
}
/////////////////////////用户登录模块表单提交//////////////////
$("#login-info .login-form .login").on("click", () => {
	let oInput = $("#login-info .login-form .input-group .form-control");
	if (check(oInput[0], unreg) && check(oInput[1], pdreg)) {
		$("form").submit();
	}
});
$(function () {
	$("#login-info .login-form .input-group .view span").on("click", function () {
		if (this.className === "glyphicon glyphicon-eye-close") {
			$("#login-info #password")[0].type = "text";
			this.className = "glyphicon glyphicon-eye-open";
		} else {
			$("#login-info #password")[0].type = "password";
			this.className = "glyphicon glyphicon-eye-close";
		}
	});
	// $(".register form p .getCheck").on("click",function(){
	// 	$.ajax({
	// 		url:"http://localhost:8080/SmallAppStorage/user/getCheckCode",
	// 		type:"get",
	// 		async:false,
	// 		success:function(data){
	// 			alert(data);
	// 		}
	// 	});
	// });
});
//校验方法体
function check(utest, mreg) {
	if (utest.value == "") {
		$(utest).siblings("label").removeClass("mactive");
		utest.className += " error";
		$(utest).nextAll("label")[0].className += " mactive";
		return false;
	} else if (!mreg.test(utest.value)) {
		$(utest).siblings("label").removeClass("mactive");
		utest.className += " error";
		$(utest).nextAll("label")[1].className += " mactive";
		return false;
	} else {
		return true;
	}
}
//反馈样式
function getBack(btest) {
	$(btest).siblings("label").removeClass("mactive");
	btest.className = "form-control";
}
//校验单位名是否已注册
function testEnpName(CompanyName) {
	check(CompanyName, new RegExp("")) ? getBack(CompanyName) : null;
	$(CompanyName).siblings("i")[0].className = "true";
	//查询数据库是否存在该单位
	$.ajax({
		url:"${pageContext.request.contextPath }/user/isExistCompany",
		type:"get",
		async:false,
		data:{"CompanyName":CompanyName.value},
		success:function(data){
			if(data == "ok"){
				getBack(CompanyName)
			}else{
				check(CompanyName,new RegExp("?![\w\W]*"))
			}
		}
	});
}
////校验统一社会信用代码C
//function testCreditCode(creditCode) {
//	check(creditCode, creditCodereg) ? getBack(creditCode) : null;
//}
//校验邮件
function testEmail(email) {
	check(email, emailreg) ? getBack(email) : null;
}
//校验验证码
function testCheckCode(checkcode) {
	check(checkcode, new RegExp("")) ? getBack(checkcode) : null;
}
//校验密码
function testPassword(password) {
	check(password, pdreg) ? getBack(password) : null;
}
//校验用户名
function testUsername(username) {
	check(username, unreg) ? getBack(username) : null;
	//查询用户名是否重复
	// $.ajax({
	// 	url:"${pageContext.request.contextPath }/user/repeatNameCheck",
	// 	type:"post",
	// 	async:false,
	// 	data:{"username":$(".username")[0].value},
	// 	success:function(data){
	// 		if(data == "ok"){
	// 			username.style.border="1px solid #999";
	// 			$(username).next("i").css("display","inline-block");
	// 		}else{
	// 			username.style.border="1px solid red";
	// 			$(".register .usernameIsExit").show(500);
	// 		}
	// 	}
	// });
}
//function telephoneTest(test){
//if((test.value == '') || (!regTele.test(test.value))){
//$(test).next("i").css("display","none");
//test.style.border="red solid 1px";
//$(test).siblings('a').show(1000);
//}else{
//$(test).siblings('a').css("display","none");
//$(test).next("i").css("display","inline-block");
//test.style.border="1px solid #999";
//}
//}

//function getCity(province) {
//$("#citySel").html('<option value="100">信息传输</option>');
//$(".selectpicker").selectpicker("refresh");
//}
