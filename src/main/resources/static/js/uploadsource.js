$("#input-ke-2").fileinput({
	language : "zh",
	theme : "explorer-fa",
	uploadUrl : '/upload/sourceupload',
	uploadAsync : false,
	enctype : 'multipart/form-data',
	minFileCount : 0,
	maxFileCount : 20,
	hideThumbnailContent : false,
	showClose : false,
	showCaption : false,
	maxFilePreviewSize : 10240, //最大预览大小
	maxFileSize:1024*1024*10,	//最大10M文件
	overwriteInitial : false,
	dropZoneEnabled : true,
	previewClass : "bg-light",
	browseClass : "btn btn-success",
	browseLabel : "选择附件",
	browseIcon : '<i class="glyphicon glyphicon-file"></i> ',
	removeClass : "btn btn-danger",
	removeLabel : "删除",
	removeIcon : '<i class="glyphicon glyphicon-trash"></i> ',
	uploadClass : "btn btn-info",
	uploadLabel : "上传",
	uploadIcon : '<i class="glyphicon glyphicon-upload"></i> ',
	fileActionSettings : {
		//预览回调函数，控制可预览的文件类型
		showZoom : function(config) {
			if (config.type === "pdf" || config.type === "image") {
				return true;
			}
			return false;
		},
		removeIcon : '<i class="fa fa-trash"></i>', // 删除图标
		uploadIcon : '<i class="fa fa-upload"></i>', // 上传图标
		uploadRetryIcon : '<i class="fa fa-repeat"></i>', // 重试图标
	},
	preferIconicPreview : true,
	previewFileIconSettings : {
		doc : '<i class="fa fa-file-word-o text-primary"></i>',
		xls : '<i class="fa fa-file-excel-o text-success"></i>',
		ppt : '<i class="fa fa-file-powerpoint-o text-danger"></i>',
		pdf : '<i class="fa fa-file-pdf-o text-danger"></i>',
		zip : '<i class="fa fa-file-archive-o text-muted"></i>',
		htm : '<i class="fa fa-file-code-o text-info"></i>',
		txt : '<i class="fa fa-file-text-o text-info"></i>',
		mov : '<i class="fa fa-file-video-o text-warning"></i>',
		mp3 : '<i class="fa fa-file-audio-o text-warning"></i>',
		jpg : '<i class="fa fa-file-image-o text-danger"></i>',
		gif : '<i class="fa fa-file-image-o text-muted"></i>',
		png : '<i class="fa fa-file-image-o text-primary"></i>',
	},
	previewFileExtSettings : {
		doc : function(ext) {
			return ext.match(/(doc|docx)$/i);
		},
		xls : function(ext) {
			return ext.match(/(xls|xlsx)$/i);
		},
		ppt : function(ext) {
			return ext.match(/(ppt|pptx)$/i);
		},
		zip : function(ext) {
			return ext.match(/(zip|rar|tar|gzip|gz|7z)$/i);
		},
		htm : function(ext) {
			return ext.match(/(htm|html)$/i);
		},
		txt : function(ext) {
			return ext.match(/(txt|ini|csv|java|php|js|css|md)$/i);
		},
		mov : function(ext) {
			return ext.match(/(avi|mpg|mkv|mov|mp4|3gp|webm|wmv)$/i);
		},
		mp3 : function(ext) {
			return ext.match(/(mp3|wav)$/i);
		},
	},
});
//上传成功回调 uploadAsync=true
$('#input-ke-2').on('fileuploaded',
		function(event, data, previewId, index) {
	console.log(data.response)
	if (typeof (data.response) != 'undefined') {
		result.push(data.response)
	}
});
$('#input-ke-2').on('filebatchuploadsuccess', function(event, data, previewId, index) {
	console.log(data)
	console.log("上传附件成功111");
//	setTimeout(function(){
//		$(event.target).fileinput('clear')
//    },2000)
});
//全部上传成功回调
//上传成功回调 uploadAsync=true
//$("#input-ke-2").on("filebatchuploadcomplete", function() {
//	if($("#enclosure")[0].value.length == 0){
//		console.log("上传附件失败");
//	}else{
//		console.log("上传附件成功222");
//	}
//});
//上传失败回调
$("#input-ke-2").on("fileerror", function(event, data, msg) {
	alert("失败了")
	alert(data.msg);
	tokenTimeOut(data);
});