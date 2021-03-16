//通知公示等第二部分
let oNoticeLi = document.querySelectorAll(
  "#section .content2 .leftcontent .leftcontent-nav ul li"
);
let oNoticeMore = document.querySelector(
  "#section .content2 .leftcontent .leftcontent-nav a"
);
let oNoticeC = document.querySelector(
  "#section .content2 .leftcontent .leftcontent-content .notice-content"
);
let oPublicC = document.querySelector(
  "#section .content2 .leftcontent .leftcontent-content .public-content"
);
oNoticeMore.onclick = () => {
  if (oNoticeLi[0].className === "mactive") {
    //ajax请求数据
    alert("通知公告");
  } else {
    alert("就业公示");
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
for (let i = 0; i < 4; i++) {
  oRcruidNav[i].onmouseover = () => {
    for (let index = 0; index < 4; index++) {
      oRcruidNav[index].className = "";
      oCareerList[index].style.display = "none";
    }
    oRcruidNav[i].className = "mactive";
    $(oCareerList[i]).show(300);
    // oCareerList[i].style.display = "block";
  };
}

////////////////////////////////////////////////////
$("#search-click").click(() => {
  $("#header .top-item .item1 .top-search")[0].style.display = "block";
});
$("#search-close").click(() => {
  $("#header .top-item .item1 .top-search")[0].style.display = "none";
});
