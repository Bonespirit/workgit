$(function () {
	setTimeout(function(){
		postData(1);
    },500)
	// 搜索
	$("#search").on("click", function () {
		postData(1);
	});
	// 单位性质等改变触发查询
	$("#search-job .nbor select").on("change", function () {
		postData(1);
	});
	//省份城市变更触发
	$("#center .right #city").on("change", function () {
		postData(1);
	});
	//底部翻页的跳页导航
	addListenToLi();
	//底部首页导航
	$("#center .right .search-info .nav-bar .homepage").on("click", function () {
		parseInt($("#total").val()) == 1 ? null : postData(1);
	});
	//底部上一页导航
	$("#center .right .search-info .nav-bar .prev").on("click", function () {
		let curp = $("#center .right .search-info .nav-bar .cur")[0].childNodes[0]
		.nodeValue;
		curp > 1 ? postData(curp - 1) : null;
	});
	//底部下一页导航
	$("#center .right .search-info .nav-bar .next").on("click", function () {
		if (parseInt($("#total").val()) == 1) {
			return false;
		}
		let curp = $("#center .right .search-info .nav-bar .cur")[0].childNodes[0]
		.nodeValue;
		if (parseInt($("#total").val()) == curp) {
			return false;
		} else {
			postData(parseInt(curp) + 1);
		}
	});
	//底部尾页导航
	$("#center .right .search-info .nav-bar .lastpage").on("click", function () {
		parseInt($("#total").val()) == 1 ? null : postData(-1);
	});
});
//底部翻页的跳页导航
function addListenToLi() {
	$("#center .right .search-info .nav-bar li").on("click", function () {
		$("#center .right .search-info .nav-bar li").removeClass("cur");
		postData(parseInt(this.innerText));
	});
}
function postData(from) {
	console.log("发送ajax请求" + "第" + from + "页");
	function eachPage(cur, pages) {
		if (pages == 1) {
	       $("#center .right .search-info .nav-bar").addClass("one");
	       return false;
	    }
		let oul = $("#center .right .search-info .nav-bar .page");
		let html = [];
		let maxp = 0,
		curp = 0;
		if (pages <= 5) {
			curp = 1;
			maxp = pages;
		} else {
			if (cur < 3) {
				curp = 1;
			} else if (cur != pages) {
				curp = cur + 2 > pages ? cur - 3 : cur - 2;
			} else {
				curp = cur - 4;
			}
			maxp = 5;
		}
		for (let i = 0; i < maxp; i++) {
			curp == cur
			? html.push('<li class="cur">' + curp + "</li>")
					: html.push("<li>" + curp + "</li>");
			curp++;
		}
		oul.html(html);
	}
	let formdata = new FormData($("#search-job")[0]);
	$.ajax({
		url: "/search/jobs/"+from,
		type: "post",
		data: formdata,
		cache: false,
		contentType: false,
		processData: false,
		success: function (data) {
			console.log(data.total)
			if (data.total == 0) {
				$("#center .right .search-info .nav-bar").addClass("one");
				$("#itemlist").html("");
				$("#itemlist").siblings(".nodata").addClass("mactive");
				
			}else{
				let pages = (data.total%15 == 0) ? data.total/15 : (parseInt(data.total/15)+1)
				$("#total").val(pages);
				eachPage(data.current, pages);
				addListenToLi();
				let tmpltxt = doT.template($("#positionData")[0].innerHTML);
				$("#itemlist").html(tmpltxt(data));
			}
		},
		error:function(e){
			console.log(e.responseText)
		}
	});
}
