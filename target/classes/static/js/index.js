//通知公示等第二部分
let oNoticeLi = document.querySelectorAll(
  "#section .content2 .leftcontent .leftcontent-nav ul li"
);
let oNoticeMore = document.querySelectorAll(
  "#section .content2 .leftcontent .leftcontent-nav a"
);
let oNoticeC = document.querySelector(
  "#section .content2 .leftcontent .leftcontent-content .notice-content"
);
let oPublicC = document.querySelector(
  "#section .content2 .leftcontent .leftcontent-content .public-content"
);
oNoticeMore[0].onclick = () => {
  if (oNoticeLi[0].className === "mactive") {
    oNoticeMore[1].click();
  } else {
    oNoticeMore[2].click();
  }
};
oNoticeLi[0].onmouseover = () => {
  oNoticeLi[1].className = "";
  oNoticeLi[0].className = "mactive";
  oPublicC.style.display = "none";
  oNoticeC.style.display = "block";
};
oNoticeLi[1].onmouseover = () => {
  oNoticeLi[0].className = "";
  oNoticeLi[1].className = "mactive";
  oNoticeC.style.display = "none";
  oPublicC.style.display = "block";
};
//////////////招聘信息////
let oRcruidNav = document.querySelectorAll(
  "#section .content3 .recruidinfo .nav ul li"
);
let oCareerList = document.querySelectorAll(
  "#section .content3 .recruidinfo .tab-cont-list .tab-cont-item"
);
let oA = $("#section .content3 .recruidinfo .nav a");
let urls = ["/ajax/id/1","/ajax/id/2","/ajax/id/3"]
$("#section .content3 .recruidinfo .nav .more").on("click",function(){
	for(let i=0;i<oRcruidNav.length;i++){
		if(oRcruidNav[i].className == "mactive"){
			oA[i+1].click()
		}
	}
})
for (let i = 0; i < 4; i++) {
  oRcruidNav[i].onmouseover = () => {
    for (let index = 0; index < 4; index++) {
      oRcruidNav[index].className = "";
      oCareerList[index].style.display = "none";
    }
    oRcruidNav[i].className = "mactive";
    $(oCareerList[i]).show(300);
    if(i > 0 && oCareerList[i].innerHTML==""){
    	$.ajax({
    		url: urls[i-1],
    		type: "get",
    		success:function(data){
    			console.log(i)
    			if(i > 1){
    				let tmpltxt = doT.template($("#myData1")[0].innerHTML);
    				$(oCareerList[i]).html(tmpltxt(data));
    			}else{
    				let tmpltxt = doT.template($("#myData2")[0].innerHTML);
    				$(oCareerList[i]).html(tmpltxt(data));
    			}
    		},
        })
    }
  };
}

////////////////////////////////////////////////////
$("#search-click").click(() => {
  $("#header .top-item .item1 .top-search")[0].style.display = "block";
});
$("#search-close").click(() => {
  $("#header .top-item .item1 .top-search")[0].style.display = "none";
});
