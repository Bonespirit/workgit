let oCont = document.getElementById("contents");
const E = window.wangEditor;
const editor = new E("#wangEditor");
editor.config.height = 600;
editor.config.zindex = 10;
editor.config.showFullScreen = false;
editor.config.pasteIgnoreImg = true;
//配置 server 接口地址
let token = $("meta[name='_csrf']").attr("content");
editor.config.uploadImgHeaders = {
		'X-CSRF-TOKEN': token,
}
editor.config.debug = true;
editor.config.uploadFileName = "file";
//editor.config.uploadImgMaxSize = 1024 * 1024;
editor.config.uploadImgAccept = ['jpg', 'jpeg', 'png', 'gif', 'bmp']
editor.config.uploadImgMaxLength = 5
editor.config.uploadImgServer ='/upload/wangEditor';
//editor.config.uploadImgHooks = {
//	customInsert: function(insertImgFn, result) {
//        insertImgFn("/"+result)
//    }
//}
editor.config.onchange = function (newHtml) {
	oCont.value = newHtml;
};
$("#rich-text button")
.eq(0)
.on("click", function () {
	$("#rich-text button").addClass("mybtn");
	$(this).removeClass("mybtn");
	editor.config.pasteFilterStyle = true;
});
$("#rich-text button")
.eq(1)
.on("click", function () {
	$("#rich-text button").addClass("mybtn");
	$(this).removeClass("mybtn");
	editor.config.pasteFilterStyle = false;
});
$("#preview").on("click", function () {
	$("#pre-show").show();
	$("#pre-show iframe")
	.contents()
	.find("#preview")[0].innerHTML = editor.txt.html();
});
$("#pre-show button").on("click", function () {
	$("#pre-show").hide();
	$("#pre-show iframe")[0].contentWindow.location.reload(true);
});
editor.create();
$(function () {
	let oA = $("#center #publish #column").siblings(".link").children();
	$("#center #publish #column").on("change", function () {
		switch (this.value) {
		case "5":
			oA[0].click();
			break;
		case "6":
			oA[1].click();
			break;
		case "7":
			oA[2].click();
			break;
		case "8":
			oA[3].click();
			break;
		default:
			break;
		}
	});
});
