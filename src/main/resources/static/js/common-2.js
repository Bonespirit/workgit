//省份城市选择器
setTimeout(function () {
  $("#pcSel")[0] ? BuildCitySelect("#pcSel", 110000) : null;
});

let BuildCitySelect = (function () {
  let AreaCityData = (function () {
    let AreaCityData = (function (r) {
      let x = function (a) {
          return '"}}' + Array(a.length).join("}}");
        },
        i = 0;
      return JSON.parse(
        r
          .replace(/\[/g, '"!"c":{"')
          .replace(/(\]+);/g, function (a, b) {
            return x(b) + '}!"';
          })
          .replace(/;/g, '"}!"')
          .replace(/\]+/g, x)
          .replace(/,/g, function () {
            return ++i % 2 == 1 ? '":{"n":"' : '","y":"';
          })
          .replace(/!/g, ",")
          .substr(6)
      );
    })(
      "[110000,北京市,b[110101,东城区,d;110102,西城区,x;110103,崇文区,c;110104,宣武区,w;110105,朝阳区,c;110106,丰台区,f;110107,石景山区,s;110108,海淀区,h;110109,门头沟区,m;110110,房山区,f;110112,通州区,t;110113,顺义区,x;110114,昌平区,c;110115,大兴区,d;110116,怀柔区,h;110117,平谷区,p;110118,密云区,m;110119,延庆区,y];120000,天津市,t[120101,和平区,h;120102,河东区,h;120103,河西区,h;120104,南开区,n;120105,河北区,h;120106,红桥区,h;120110,东丽区,d;120111,西青区,x;120112,津南区,j;120113,北辰区,b;120114,武清区,w;120115,宝坻区,b;120116,滨海新区,b;120117,宁河区,n;120118,静海区,j;120119,蓟州区,j];130000,河北省,h[130100,石家庄,s;130200,唐山,t;130300,秦皇岛,q;130400,邯郸,h;130500,邢台,x;130600,保定,b;130700,张家口,z;130800,承德,c;130900,沧州,c;131000,廊坊,l;131100,衡水,h];140000,山西省,s[140100,太原,t;140200,大同,d;140300,阳泉,y;140400,长治,c;140500,晋城,j;140600,朔州,s;140700,晋中,j;140800,运城,y;140900,忻州,x;141000,临汾,l;141100,吕梁,l];150000,内蒙古,n[150100,呼和浩特,h;150200,包头,b;150300,乌海,w;150400,赤峰,c;150500,通辽,t;150600,鄂尔多斯,e;150700,呼伦贝尔,h;150800,巴彦淖尔,b;150900,乌兰察布,w;152200,兴安,x;152500,锡林郭勒,x;152900,阿拉善,a];210000,辽宁省,l[210100,沈阳,s;210200,大连,d;210300,鞍山,a;210400,抚顺,f;210500,本溪,b;210600,丹东,d;210700,锦州,j;210800,营口,y;210900,阜新,f;211000,辽阳,l;211100,盘锦,p;211200,铁岭,t;211300,朝阳,c;211400,葫芦岛,h];220000,吉林省,j[220100,长春,c;220200,吉林市,j;220300,四平,s;220400,辽源,l;220500,通化,t;220600,白山,b;220700,松原,s;220800,白城,b;222400,延边,y];230000,黑龙江,h[230100,哈尔滨,h;230200,齐齐哈尔,q;230300,鸡西,j;230400,鹤岗,h;230500,双鸭山,s;230600,大庆,d;230700,伊春,y;230800,佳木斯,j;230900,七台河,q;231000,牡丹江,m;231100,黑河,h;231200,绥化,s;232700,大兴安岭,d];310000,上海市,s[310101,黄浦区,h;310104,徐汇区,x;310105,长宁区,c;310106,静安区,j;310107,普陀区,p;310109,虹口区,h;310110,杨浦区,y;310112,闵行区,m;310113,宝山区,b;310114,嘉定区,j;310115,浦东新区,p;310116,金山区,j;310117,松江区,s;310118,青浦区,q;310120,奉贤区,f;310151,崇明区,c];320000,江苏省,j[320100,南京,n;320200,无锡,w;320300,徐州,x;320400,常州,c;320500,苏州,s;320600,南通,n;320700,连云港,l;320800,淮安,h;320900,盐城,y;321000,扬州,y;321100,镇江,z;321200,泰州,t;321300,宿迁,s];330000,浙江省,z[330100,杭州,h;330200,宁波,n;330300,温州,w;330400,嘉兴,j;330500,湖州,h;330600,绍兴,s;330700,金华,j;330800,衢州,q;330900,舟山,z;331000,台州,t;331100,丽水,l];340000,安徽省,a[340100,合肥,h;340200,芜湖,w;340300,蚌埠,b;340400,淮南,h;340500,马鞍山,m;340600,淮北,h;340700,铜陵,t;340800,安庆,a;341000,黄山,h;341100,滁州,c;341200,阜阳,f;341300,宿州,s;341500,六安,l;341600,亳州,b;341700,池州,c;341800,宣城,x];350000,福建省,f[350100,福州,f;350200,厦门,x;350300,莆田,p;350400,三明,s;350500,泉州,q;350600,漳州,z;350700,南平,n;350800,龙岩,l;350900,宁德,n];360000,江西省,j[360100,南昌,n;360200,景德镇,j;360300,萍乡,p;360400,九江,j;360500,新余,x;360600,鹰潭,y;360700,赣州,g;360800,吉安,j;360900,宜春,y;361000,抚州,f;361100,上饶,s];370000,山东省,s[370100,济南,j;370200,青岛,q;370300,淄博,z;370400,枣庄,z;370500,东营,d;370600,烟台,y;370700,潍坊,w;370800,济宁,j;370900,泰安,t;371000,威海,w;371100,日照,r;371300,临沂,l;371400,德州,d;371500,聊城,l;371600,滨州,b;371700,菏泽,h];410000,河南省,h[410100,郑州,z;410200,开封,k;410300,洛阳,l;410400,平顶山,p;410500,安阳,a;410600,鹤壁,h;410700,新乡,x;410800,焦作,j;410900,濮阳,p;411000,许昌,x;411100,漯河,l;411200,三门峡,s;411300,南阳,n;411400,商丘,s;411500,信阳,x;411600,周口,z;411700,驻马店,z;419001,济源,j];420000,湖北省,h[420100,武汉,w;420200,黄石,h;420300,十堰,s;420500,宜昌,y;420600,襄阳,x;420700,鄂州,e;420800,荆门,j;420900,孝感,x;421000,荆州,j;421100,黄冈,h;421200,咸宁,x;421300,随州,s;422800,恩施,e;429004,仙桃,x;429005,潜江,q;429006,天门,t;429021,神农架,s];430000,湖南省,h[430100,长沙,c;430200,株洲,z;430300,湘潭,x;430400,衡阳,h;430500,邵阳,s;430600,岳阳,y;430700,常德,c;430800,张家界,z;430900,益阳,y;431000,郴州,c;431100,永州,y;431200,怀化,h;431300,娄底,l;433100,湘西,x];440000,广东省,g[440100,广州,g;440200,韶关,s;440300,深圳,s;440400,珠海,z;440500,汕头,s;440600,佛山,f;440700,江门,j;440800,湛江,z;440900,茂名,m;441200,肇庆,z;441300,惠州,h;441400,梅州,m;441500,汕尾,s;441600,河源,h;441700,阳江,y;441800,清远,q;441900,东莞,d;442000,中山,z;445100,潮州,c;445200,揭阳,j;445300,云浮,y];450000,广西,g[450001,南宁,n;450002,柳州,l;450003,桂林,g;450004,梧州,w;450005,北海,b;450006,防城港,f;450007,钦州,q;450008,贵港,g;450009,玉林,y;450010,百色,b;450011,贺州,h;450012,河池,h;450013,来宾,l;450014,崇左,c];460000,海南省,h[460100,海口,h;460200,三亚,s;460300,三沙,s;460400,儋州,d;469001,五指山,w;469002,琼海,q;469005,文昌,w;469006,万宁,w;469007,东方,d;469021,定安,d;469022,屯昌,t;469023,澄迈,c;469024,临高,l;469025,白沙,b;469026,昌江,c;469027,乐东,l;469028,陵水,l;469029,保亭,b;469030,琼中,q];500000,重庆市,c[500101,万州区,w;500102,涪陵区,f;500103,渝中区,y;500104,大渡口区,d;50015,江北区,j;500106,沙坪坝区,s;500107,九龙坡区,j;500108,南岸区,n;500109,北碚区,b;500110,綦江区,q;500111,大足区,d;500112,渝北区,y;500113,巴南区,b;500114,黔江区,q;500115,长寿区,c;500116,江津区,j;500117,合川区,h;500118,永川区,y;500119,南川区,n;500120,璧山区,b;500151,铜梁区,t;500152,潼南区,t;500153,荣昌区,r;500154,开州区,k;500155,梁平区,l;500156,武隆区,w;500229,城口县,c;500230,丰都县,f;500231,垫江县,d;500233,忠县,z;500235,云阳县,y;500236,奉节县,f;500237,巫山县,w;500238,巫溪县,w;500240,石柱土家族自治县,s;500241,秀山土家族苗族自治县,x;500242,酉阳土家族苗族自治县,y;500243,彭水苗族土家族自治县,p];510000,四川省,s[510100,成都,c;510300,自贡,z;510400,攀枝花,p;510500,泸州,l;510600,德阳,d;510700,绵阳,m;510800,广元,g;510900,遂宁,s;511000,内江,n;511100,乐山,l;511300,南充,n;511400,眉山,m;511500,宜宾,y;511600,广安,g;511700,达州,d;511800,雅安,y;511900,巴中,b;512000,资阳,z;513200,阿坝,a;513300,甘孜,g;513400,凉山,l];520000,贵州省,g[520100,贵阳,g;520200,六盘水,l;520300,遵义,z;520400,安顺,a;520500,毕节,b;520600,铜仁,t;522300,黔西南,q;522600,黔东南,q;522700,黔南,q];530000,云南省,y[530100,昆明,k;530300,曲靖,q;530400,玉溪,y;530500,保山,b;530600,昭通,z;530700,丽江,l;530800,普洱,p;530900,临沧,l;532300,楚雄,c;532500,红河,h;532600,文山,w;532800,西双版纳,x;532900,大理,d;533100,德宏,d;533300,怒江,n;533400,迪庆,d];540000,西藏,x[540100,拉萨,l;540200,日喀则,r;540300,昌都,c;540400,林芝,l;540500,山南,s;540600,那曲,n;542500,阿里,a];610000,陕西省,s[610100,西安,x;610200,铜川,t;610300,宝鸡,b;610400,咸阳,x;610500,渭南,w;610600,延安,y;610700,汉中,h;610800,榆林,y;610900,安康,a;611000,商洛,s];620000,甘肃省,g[620100,兰州,l;620200,嘉峪关,j;620300,金昌,j;620400,白银,b;620500,天水,t;620600,武威,w;620700,张掖,z;620800,平凉,p;620900,酒泉,j;621000,庆阳,q;621100,定西,d;621200,陇南,l;622900,临夏,l;623000,甘南,g];630000,青海省,q[630100,西宁,x;630200,海东,h;632200,海北,h;632300,黄南,h;632500,海南,h;632600,果洛,g;632700,玉树,y;632800,海西,h];640000,宁夏,n[640100,银川,y;640200,石嘴山,s;640300,吴忠,w;640400,固原,g;640500,中卫,z];650000,新疆,x[650100,乌鲁木齐,w;650200,克拉玛依,k;650400,吐鲁番,t;650500,哈密,h;652300,昌吉,c;652700,博尔塔拉,b;652800,巴音郭楞,b;652900,阿克苏,a;653000,克孜勒苏,k;653100,喀什,k;653200,和田,h;654000,伊犁,y;654200,塔城,t;654300,阿勒泰,a;659001,石河子,s;659002,阿拉尔,a;659003,图木舒克,t;659004,五家渠,w;659005,北屯,b;659006,铁门关,t;659007,双河,s;659008,可克达拉,k;659009,昆玉,k;659010,胡杨河,h];710000,台湾省,~3[710100,台北,t;710200,高雄,g;710300,台南,t;710400,台中,t;710600,南投,n;710700,基隆,j;710800,新竹市,x;710900,嘉义市,j;711100,新北,x;711200,宜兰,y;711300,新竹县,x;711400,桃园,t;711500,苗栗,m;711700,彰化,z;711900,嘉义县,j;712100,云林,y;712400,屏东,p;712500,台东,t;712600,花莲,h;712700,澎湖,p];810000,香港,~1[810000,香港,x];820000,澳门,~2[820000,澳门,a];910000,国外,~4[910000,国外,g]]"
    );
    let data = AreaCityData;
    let obj = {};
    let x = function (arr, p) {
      for (let k in arr) {
        let o = arr[k];
        obj[k] = {
          id: +k,
          pid: p,
          name: o.n,
          y: o.y,
        };
        x(o.c || {}, +k);
      }
    };
    x(data, 0);
    return obj;
  })();
  let BuildSelect = function (elem, set, changeFn) {
    let index = 0;
    let data = AreaCityData;
    if (typeof elem == "string") {
      elem = document.querySelector(elem);
    } else if (elem.length) {
      elem = elem[0];
    }
    let build = function (pid, id, ids) {
      index++;
      let has = false;
      let arr = [];
      for (let k in data) {
        let o = data[k];
        if (o.pid == pid) {
          has = true;
          arr.push(o);
        }
      }
      if (set.sort) {
        arr = set.sort(arr);
      }
      let selectID = index == 2 ? "citySel" : "provinceSel";
      let html = [
        '<select name="' +
          selectID +
          '" id="' +
          selectID +
          '" class="selectpicker" data-live-search="true" style="display:none;">',
      ];
      selectID == "citySel"
        ? html.push('<option value="">请选择</option>')
        : "";
      let idsFind = ids ? " " + ids.join(" ") + " " : "";
      for (let i = 0, o; i < arr.length; i++) {
        o = arr[i];
        let slc = false;
        if (ids) {
          slc = idsFind.indexOf(" " + o.id + " ") != -1;
        } else {
          slc = o.id == id;
        }
        html.push(
          '<option value="' +
            o.id +
            '" ' +
            (slc ? "selected" : "") +
            ">" +
            o.name +
            "</option>"
        );
      }
      html.push("</select>");
      return pid == 0 || has ? html.join("\n") : "";
    };

    let loopid = set.id,
      pid = -1,
      html = [],
      hasChild = false,
      childSelect;
    while (pid != 0) {
      pid = (data[loopid] && data[loopid].pid) || 0;
      html.push(build(pid, loopid, null));
      loopid = pid;
    }
    html.reverse();
    if (set.id) {
      childSelect = build(set.id, 0);
      if (childSelect) {
        hasChild = true;
        html.push(childSelect);
      }
    }

    let onChange = function (tg, id, pid) {
      let values = [];
      if (!id) {
        id = pid;
      }
      set.id = id;
      set.values = values;
      childSelect = BuildSelect(elem, set, changeFn);
      changeFn && changeFn(id, childSelect, data);
    };
    elem.innerHTML = html.join("\n");
    let arr = elem.querySelectorAll("select");
    arr[0].addEventListener("change", function (e) {
      let tg = e.target;
      onChange(tg, +tg.value, +tg.getAttribute("pid"));
      $(".selectpicker").selectpicker("refresh");
    });
    arr[1].addEventListener("change", function (e) {
      $(".selectpicker").selectpicker("refresh");
    });
    return hasChild;
  };
  function sort(arr, buildFn) {
    let rtv = [];
    arr.sort(function (a, b) {
      let y = a.y.charCodeAt(0) - b.y.charCodeAt(0);
      if (y) {
        return y;
      } else {
        return (a.y + a.name).localeCompare(b.y + b.name);
      }
    });
    for (let i = 0, o, name; i < arr.length; i++) {
      o = arr[i];
      name = o.y.substr(0, 1).toUpperCase() + " " + o.name;
      if (buildFn) {
        rtv.push(buildFn(o, name));
      } else {
        rtv.push({ id: o.id, name: name });
      }
    }
    return rtv;
  }

  return function (elem, id, changeFn) {
    return BuildSelect(elem, { id: id, sort: sort }, changeFn);
  };
})();

//获取职能类别
let skill = [
  { name: "职位类别", no: "" },
  { name: "销售/商务拓展", no: 1 },
  { name: "人事/行政/财务/法务", no: 2 },
  { name: "互联网/通信及硬件", no: 3 },
  { name: "运维/测试", no: 4 },
  { name: "视觉/交互/设计", no: 5 },
  { name: "运营/专业分析", no: 6 },
  { name: "产品/项目/高级管理", no: 7 },
  { name: "市场/品牌/公关", no: 8 },
  { name: "金融/保险", no: 9 },
  { name: "房地产/工程/建筑", no: 10 },
  { name: "物流/采购/供应链", no: 11 },
  { name: "生产制造/营运管理", no: 12 },
  { name: "农业/能源/环保", no: 13 },
  { name: "医疗/医美/医务", no: 14 },
  { name: "教育/培训/科研", no: 15 },
  { name: "编辑/记者/翻译", no: 16 },
  { name: "影视传媒", no: 17 },
  { name: "商务服务/生活服务", no: 18 },
  { name: "管培生/非企业从业者", no: 19 },
  { name: "不限", no: 20 },
  { name: "互联网/IT/电子/通信", no: 21 },
  { name: "房地产/建筑", no: 22 },
  { name: "金融业", no: 23 },
  { name: "教育培训/科研", no: 24 },
  { name: "广告/传媒/文化/体育", no: 25 },
  { name: "制药/医疗", no: 26 },
  { name: "批发/零售/贸易", no: 27 },
  { name: "制造业", no: 28 },
  { name: "汽车", no: 29 },
  { name: "交通运输/仓储/物流", no: 30 },
  { name: "专业服务", no: 31 },
  { name: "生活服务", no: 32 },
  { name: "能源/环保/矿产", no: 33 },
  { name: "政府/非盈利机构", no: 34 },
  { name: "农/林/牧/渔", no: 35 },
];
//获取专业
let majors = [
  {
    name: "机电工程学院",
    no: 1100,
    majorList: [
      { name: "微电子制造工程", no: 1101 },
      { name: "车辆工程专业", no: 1102 },
      { name: "机械电子工程", no: 1103 },
      { name: "机械设计制造及其自动化", no: 1104 },
      { name: "电气工程及其自动化", no: 1105 },
      { name: "通信工程", no: 1106 },
      { name: "信息安全", no: 1107 },
    ],
  },
  {
    name: "信息与通信学院",
    no: 1200,
    majorList: [
      { name: "电子科学技术", no: 1201 },
      { name: "通信工程", no: 1202 },
      { name: "导航工程", no: 1203 },
      { name: "电子信息工程", no: 1204 },
      { name: "微电子科学与工程", no: 1205 },
    ],
  },
  {
    name: "计算机与信息安全学院",
    no: 1300,
    majorList: [
      { name: "信息安全", no: 1301 },
      { name: "计算机科学与技术", no: 1302 },
      { name: "智能科学与技术", no: 1303 },
      { name: "信息对抗", no: 1304 },
      { name: "物联网工程", no: 1305 },
      { name: "软件工程", no: 1306 },
    ],
  },
  {
    name: "艺术与设计学院",
    no: 1400,
    majorList: [
      { name: "环境设计", no: 1401 },
      { name: "视觉传达设计", no: 1402 },
      { name: "服装与服饰设计", no: 1403 },
      { name: "书法学", no: 1404 },
      { name: "数字媒体技术", no: 1405 },
      { name: "产品设计", no: 1406 },
      { name: "工业设计", no: 1407 },
      { name: "动画", no: 1408 },
    ],
  },
  {
    name: "商学院",
    no: 1500,
    majorList: [
      { name: "国际经济与贸易", no: 1501 },
      { name: "会计学", no: 1502 },
      { name: "财务管理", no: 1503 },
      { name: "工商管理", no: 1504 },
      { name: "市场营销", no: 1505 },
      { name: "工业工程", no: 1506 },
      { name: "物流管理", no: 1507 },
      { name: "电子商务", no: 1508 },
    ],
  },
  {
    name: "外国语学院",
    no: 1600,
    majorList: [
      { name: "英语", no: 1601 },
      { name: "日语", no: 1602 },
    ],
  },
  {
    name: "数学与计算科学学院",
    no: 1700,
    majorList: [
      { name: "统计学", no: 1701 },
      { name: "信息与计算科学", no: 1702 },
      { name: "数学与应用数学", no: 1703 },
    ],
  },
  {
    name: "电子工程与自动化学院",
    no: 1800,
    majorList: [
      { name: "光信息科学与技术", no: 1801 },
      { name: "电子信息科学与技术", no: 1802 },
      { name: "智能科学与技术", no: 1803 },
      { name: "自动化", no: 1804 },
      { name: "测控技术与仪器", no: 1805 },
    ],
  },
  {
    name: "法学院",
    no: 1900,
    majorList: [
      { name: "法学", no: 1901 },
      { name: "行政管理", no: 1902 },
    ],
  },
  {
    name: "材料科学与工程学院",
    no: 2000,
    majorList: [
      { name: "材料成型及控制工程", no: 2001 },
      { name: "高分子材料与工程", no: 2002 },
      { name: "材料科学与工程", no: 2003 },
      { name: "应用物理学", no: 2004 },
    ],
  },
  {
    name: "公共事务学院",
    no: 2100,
    majorList: [{ name: "公共事业管理", no: 2101 }],
  },
  { name: "马克思主义学院", no: 2200, majorList: [] },
  { name: "国际学院", no: 2300, majorList: [{ name: "对外汉语", no: 2301 }] },
  {
    name: "建筑与交通工程学院",
    no: 2400,
    majorList: [
      { name: "建筑电气与智能化", no: 2401 },
      { name: "土木工程", no: 2402 },
      { name: "交通工程", no: 2403 },
      { name: "建筑环境与能源应用工程", no: 2404 },
    ],
  },
  {
    name: "生命与环境科学学院",
    no: 2500,
    majorList: [
      { name: "生物医学工程", no: 2501 },
      { name: "生物工程", no: 2502 },
      { name: "环境工程", no: 2503 },
    ],
  },
];

//判断doT.js是否加载
//使用doT模板渲染json数据
//生成doT模板
$(function () {
  $("script").each(function (i, e) {
    if (e.src.indexOf("doT.min.js") >= 0) {
      let tmpltxt1 = doT.template($("#skillData")[0].innerHTML);
      let tmpltxt2 = doT.template($("#majorData")[0].innerHTML);
      //渲染职能类别下拉选择框
      $("#skill").html(tmpltxt1(skill));
      //渲染专业类别下拉选择框
      $("#contents").html(tmpltxt2(majors));
    }
  });
});
$(function () {
  $("#contents input").on("click", function () {
    if ($("#myModal .modal-dialog #contents input:checked").length > 10) {
      if (!$("#myModal .alert")[0]) {
        let warning = $(
          '<div class="alert alert-warning">' +
            '<a href="#" class="close" data-dismiss="alert"> &times; </a>' +
            "<strong>警告！</strong>最多可以选择10个哦</div>"
        );
        warning.appendTo($("#myModal .modal-dialog .modal-header"));
      }
      return false;
    }
  });
  $("#myModal .modal-dialog .ensure").on("click", function () {
    let code = new Array(),
      name = new Array();
    let oChecked = document.querySelectorAll(
      "#myModal .modal-dialog #contents input:checked"
    );
    oChecked.forEach(function (e) {
      code.push(e.value);
      name.push($(e).parent()[0].childNodes[2].nodeValue.trim());
    });
    $("#center .right #mcode").attr("value", code);
    $("#center .right #majors").attr({
      value: name.toString(),
      title: name.toString(),
    });
  });
  $("#location .modal-dialog .ensure").on("click", function () {
    if ($("#citySel").val()) {
      $("#center .right #citycode").attr("value", $("#citySel").val());
      $("#center .right #city").attr(
        "value",
        $("#provinceSel").siblings("button")[0].title.slice(1) +
          $("#citySel").siblings("button")[0].title.slice(1)
      );
    } else {
      $("#center .right #citycode").attr("value", $("#provinceSel").val());
      $("#center .right #city").attr(
        "value",
        $("#provinceSel").siblings("button")[0].title.slice(1)
      );
    }
    $("#center .right #city").trigger("change");
  });
});
