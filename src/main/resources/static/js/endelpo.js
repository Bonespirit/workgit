$(function () {
	$("#center .right .poslist #itemlist li").on("mouseenter", function () {
		$(this).append(
				'<div id="delpos"><button type="button" class="">删除</button></div>'
		);
		removePos();
	});
	$("#center .right .poslist #itemlist li").on("mouseleave", function () {
		$(this).children("#delpos").remove();
	});

	function removePos() {
		$("#center .right .poslist #itemlist li #delpos button").on(
				"click",
				function () {
					let tid = $(this).parent().siblings("a")[0].href.split("/");
					$(this).parents("li").remove();

					$.ajax({
						url: "/enterprise/delpos/id/" + tid[tid.length - 1],
						type: "delete",
						success: function (data) {
							console.log(data);
						},
						error: function (xhr) {
							console.log(xhr.responseText);
						},
					});
				}
		);
	}
});
