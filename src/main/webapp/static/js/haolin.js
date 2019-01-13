/**
 * Created by Haolin on 2017/8/25.
 */
// 已完成 ✓
// 未完成 ✗

function addLoadEvent(func) {
    var oldOnload = window.onload;
    if (typeof window.onload !== 'function') {
        window.onload = func;
    } else {
        window.onload = function() {
            oldOnload();
            func();
        }
    }
}

function $qs(CssSelectors,start){
    if(start){
        return start.querySelector(CssSelectors);
    }else{
        return document.querySelector(CssSelectors);
    }
}
function $qsa(CssSelectors,start){
    if(start){
        return start.querySelectorAll(CssSelectors);
    }else{
        return document.querySelectorAll(CssSelectors);
    }
}

function string_param(string,json){
    return string.replace(/\{(.+?)\}/g,function(cc){
        var o=cc.substring(1,cc.length-1);
        var a = new RegExp('\\((.+?)\\)'); //注意是非全局匹配
        if(a.test(o)){
            var b= o.split('(')[0].replace(/(^\s+)|(\s+$)/g,"");
            if(b!==""){
                var c=o.match(a)[1].split(','),d=c.length,e=[],f=b.split('.');
                while(d--) {e.unshift(json[c[d]]);}
                return (f.length===1?window[b].apply(null,e):window[f[0]][f[1]].apply(null,e));
            }
        }else{
            return json[o];
        }
    });
}
function fragNode(strHTML){
    var range =document.createRange();
    range.selectNodeContents(document.documentElement);
    return range.createContextualFragment(strHTML);
}

function getStyleValue(element,CSSProperty){
    return window.getComputedStyle(element).getPropertyValue(CSSProperty);
}
function addClass(node,classStr) {
    var temp = node.getAttribute("class");
    if(temp){
        node.setAttribute("class",temp+" "+classStr);
    }else{
        node.setAttribute("class",classStr);
    }
}
function removeClass(node,classStr) {
    var temp = node.getAttribute("class"),
        arr = temp.split(" "),
        len = arr.length,
        i,result=[];
    for(i=0;i<len;i++){
        if(arr[i]!==classStr){
            result.push(arr[i]);
        }
    }
    node.setAttribute("class",result.join(" "))
}

function getViewport(){
  if (document.compatMode === "BackCompat"){
    return {
      width: document.body.clientWidth,
      height: document.body.clientHeight
    }
  } else {
    return {
      width: document.documentElement.clientWidth,
      height: document.documentElement.clientHeight
    }
  }
}

HTMLElement.prototype.$setCSSText = function (CSSText) {
  var old = this.getAttribute('style');
  this.style.cssText = old&&old!==''?old+" "+CSSText:CSSText;
};
HTMLElement.prototype.$removeCSSText = function (CSSText) {
  var old = this.getAttribute('style');
  if(old.indexOf(CSSText)!==-1){
    var arr = old.split(CSSText);
    this.style.cssText = arr.join();
    return true;
  }else{
    return false;
  }
};

//获取url中的参数
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r !== null) return unescape(r[2]); return null; //返回参数值
}
//判断是否在可支付的浏览器中
function payBrowser(browserName) {//browserName: wechat/微信  alipay/支付宝
    var ua = window.navigator.userAgent.toLowerCase(),
        wechat = ua.lastIndexOf("micromessenger"),
        alipay = ua.lastIndexOf("alipayclient");
    if(browserName){
        if(browserName==="wechat"||browserName==="微信"){
            return wechat!== -1;
        }else if(browserName==="alipay"||browserName==="支付宝"){
            return alipay!== -1;
        }
    }else{
        return wechat!== -1||alipay!== -1;
    }
}
//设置存储
function setStorage(keyName,value,isSession) {
    if(isSession){
        sessionStorage.setItem(keyName,JSON.stringify(value));
    }else {
        localStorage.setItem(keyName,JSON.stringify(value));
    }
}
function setStorageDirect(keyName,value,isSession) {
    if(isSession){
        sessionStorage.setItem(keyName,value);
    }else {
        localStorage.setItem(keyName,value);
    }
}
//获取存储
function getStrong(keyName,isSession) {
    if(isSession){
        return JSON.parse(sessionStorage.getItem(keyName));
    }else {
        return JSON.parse(localStorage.getItem(keyName));
    }
}
function getStrongDirect(keyName,isSession) {
    if(isSession){
        return sessionStorage.getItem(keyName);
    }else {
        return localStorage.getItem(keyName);
    }
}
//dataset
function setData(node,dataName,value) {
    node.dataset[dataName] = JSON.stringify(value);
}
function getData(node,dateName) {
    var data = node.dataset[dateName];
    return data?JSON.parse(data):null;
}
//
function number(arg) {
    return typeof arg !== "number"?Number(arg):arg;
}
//切换display => block/none
function nodesToggle(nodes){
    var len = arguments.length,i;
    if(getStyleValue(arguments[0],"display")==="none"){
        for(i=0;i<len;i++){
            arguments[i].style.display = "block";
        }
    }else{
        for(i=0;i<len;i++){
            arguments[i].style.display = "none";
        }
    }
}
function phoneVerify(arg) {
    var reg = /^0?(13[0-9]|15[012356789]|17[013678]|18[0-9]|14[57])[0-9]{8}$/;
    var phoneNum = typeof arg==="number"?arg.toString():arg;
    console.log('正则验证手机号:',reg.test(phoneNum));
    return reg.test(phoneNum);
}

function enuEmpty(arg) {
    var obj = arg;
    var arr = Object.keys(obj),
        len = arr.length,
        i;
    for(i=0;i<len;i++){
        if(obj[arr[i]]===""||!obj[arr[i]]){
            obj[arr[i]] = "--";
        }
    }
    return obj;
}

function strInsert(str,insert,position) {
    var piece1 = str.slice(0,position),
        piece2 = str.slice(position);
    return piece1+insert+piece2;
}

//点击加载
//20170908
var bookObj = {
    nav:[],
    style:{
        nav:{

        },
        list:[],
        state:[]
    }
};
// var nodeObj = {
//     node:{},
//     style:{}
// };
function LoadData(bookObj) {//LoadData对象构造函数
    this.page = 1;
    this.type = 0;//用于标签切换点位当前标签
    this.btns = bookObj.nav?bookObj.nav:[];//标签按钮名称
    this.haveMoreData = true;//用于数据加载后判断有无更多数据
    console.log(this);
}
LoadData.prototype.style = function () {
    var set = '<style>.loadData_nav_set{background-color:#fff;font-size:.875rem;top:3rem}.loadData_nav_btn_set{line-height:3rem}.loadData_nav_set>.loadData_nav_active_set{color:#0f9d58;border-bottom:.25rem solid #0f9d58}.loadData_list_set{padding-top:3.25rem}</style>';
};
LoadData.prototype.initNode = function (nodeObj,nodeObj2) {
    //初始化节点,设置样式
    //初始化样式

    //初始化标签节点
    if(this.btns&&this.btns!==[]){
        var i,len = this.btns.length,navStr = '',activeClass = " loadData_nav_active";
        for(i=0;i<len;i++){
            if(i>0){activeClass = "";}
            navStr += '<div class="loadData_nav_btn'+activeClass+'" data-type="'+i+'">'+this.btns[i]+'</div>';
        }
        var result = '<div id="loadData_nav">'+navStr+'</div>';
        if(nodeObj.node){
            nodeObj.node.appendChild(fragNode(result));
        }else{
            document.body.appendChild(fragNode(result));
        }
    }
    //初始化列表容器及加载更多数据按钮组
    var nodeStr = '<div id="loadData_list" data-page="0"></div>'+'<div class=loadData_wrapper><div class=loadData_click><a href="javascript:void(0);">加载更多数据</a></div><div class=loadData_more><i class=i_loading></i><span>正在加载</span></div><div class=loadData_none>没有更多数据</div><div class="loadData_event"></div></div>';
    if(nodeObj.node2){
        nodeObj.node2.appendChild(fragNode(nodeStr));
    }else if(nodeObj.node){
        nodeObj.node.appendChild(fragNode(nodeStr));
    }else{
        document.body.appendChild(fragNode(nodeStr));
    }
};
LoadData.prototype.click = function (callback,argumentObj) {//点击“加载更多”按钮事件
    var that = this;
    $qs(".loadData_click>a").addEventListener("click",function (event) {
        event.preventDefault();
        that.state(2);
        setTimeout(function () {
            if(argumentObj){
                callback(argumentObj,that);
            }else{
                callback(that);
            }
        },750);
    },false);
};
LoadData.prototype.state = function (state) {//数据底部数据提示信息,state 0:初始话;1:点击加载更多;2:正在加载;3:没有更多;
    var old = $qs(".loadData_show");
    if(old){
        removeClass(old,"loadData_show");
    }
    var str = ".loadData_";
    switch (state){
        case 1:
            str+="click";
            break;
        case 2:
            str+="more";
            break;
        case 3:
            str+="none";
            break;
        default:
            return;
    }
    addClass($qs(str),"loadData_show");
};
LoadData.prototype.finish = function (state) {//数据记载完成后执行
    if(state){
        this.page++;
        this.state(1);
    }else{
        this.state(3);
    }
};
//标签切换
LoadData.prototype.navSwitch = function (callback,argumentObj) {
    var that = this;
    $qs("#loadData_nav").addEventListener("click",function (event) {
        event.preventDefault();
        var tag = event.target;
        //console.log("type:",tag.dataset.type,"this.type:",that.type);
        if(Number(tag.dataset.type)!==that.type){
            that.page = 1;
            //切换标签，清空列表数据
            var list = $qs("#loadData_list");
            removeClass($qs(".loadData_nav_active"),"loadData_nav_active");
            addClass(tag,"loadData_nav_active");
            if(list.childElementCount>0){
                list.innerHTML = "";
            }
            that.type = Number(tag.dataset.type);
            //请求数据并加载，根据相应情况显示适当提示
            that.state(2);
            setTimeout(function () {
                if(argumentObj){
                    callback(argumentObj,that);
                }else{
                    callback(that);
                }
            },750);
        }
    },false);
};



//请求数据时拼接api与params
function paramsJoin(apiURL,paramsObj) {
  var arr = Object.keys(paramsObj),
      len = arr.length,
      result= apiURL&&apiURL!==""?apiURL+'?':'',
      i;
  for(i=0;i<len;i++){
    if(i!==0){result+="&";}
    result = result+arr[i]+"="+paramsObj[arr[i]];
  }
  return result;
}


