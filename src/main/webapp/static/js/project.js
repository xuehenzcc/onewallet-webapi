/**
 * Created by Haolin on 2018/2/2.
 */

var path = {
  server:'http://api.wtlyhqb.com/onewallet-webapi',
  api:{
    getToken:'/common/getToken',
    sendCode:'/common/h5SendSms',
    reg:'/userCenter/h5Regist',
    getUserData:'/userCenter/getUserInfo'
  }
};
//基于JQ的ajax封装
var ajax = function (apiURL,data,type,resolve,reject) {
  console.log("%c%s","color:#347dfd;","参数: ",data);
  $.ajax({
    url:path.server+apiURL,
    type:type.toUpperCase(),
    data:data,
    dataType:"json",
    success: function (data) {
      console.log("%c%s","color:#5cb85c;","响应成功：",data);
      resolve(data);
    },
    error: function(error){
      if(reject){
        reject(error);
      }else{
        console.log("%c%s","color:#d9534f;","响应失败",error);
        pro.popup.show("响应失败，请稍后再试。",{type:"warning"});
      }
    }
  });
};
//数据统一接口
var fetch = {
  getToken:function (rId,sign,resolve) {
    ajax(path.api.getToken,{
      recommendId:rId,
      sign:sign
    },"POST",resolve)
  },
  sendCode:function (tel,token,resolve) {
    ajax(path.api.sendCode,{
      tel:tel,
      type:"验证码",
      token:token
    },"POST",resolve);
  },
  reg:function (data,resolve) {
    ajax(path.api.reg,data,"POST",resolve);
  },
  getUserDate:function (uid,token,resolve) {
    ajax(path.api.getUserData,{
      userId:uid,
      token:token
    },"POST",resolve);
  }
};


//项目全局对象
var pro = {
  isLogin:function () {
    var userId = localStorage.getItem("userId");
    if(!userId||userId===""){
      var url = "http://cwcgzh.sysucc.org.cn/zshospital/login/?href="+encodeURI(location.href);
      var wx = 'https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx53dbcd7852096299&redirect_uri='+url+'&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect';
      window.open(wx,"_self");
    }
  },
  formatDate:function (dateTime,formatType) {
    if(dateTime&&dateTime!==""){
      var arg = typeof dateTime!=="number"?Number(dateTime):dateTime;
      var date = new Date(arg),
        y = date.getFullYear(),
        m = date.getMonth()+1,
        d = date.getDate();
      if(!formatType){
        return y+"-"+m+"-"+d;
      }else{
        var result = "";
        switch (formatType){
          case "ym":
            result = y+"-"+m;
            break;
          case "年月":
            result = y+"年"+m+"月";
            break;
          case "年月日":
            result = y+"年"+m+"月"+d+"日";
            break;
          case "年月日星期":
            var day = date.getDay(),
              xq = '';
            if(day===1){
              xq = ' 星期一';
            }else if(day===2){
              xq = ' 星期二';
            }else if(day===3){
              xq = ' 星期三';
            }else if(day===4){
              xq = ' 星期四';
            }else if(day===5){
              xq = ' 星期五';
            }else if(day===6){
              xq = ' 星期六';
            }else if(day===0){
              xq = ' 星期日';
            }
            result = y+"年"+m+"月"+d+"日"+xq;
        }
        return result;
      }
    }else{
      return "";
    }
  },
  popup:{
    initFinish:false,
    init:function () {
      if(!this.initFinish){
        var block = '<div class="pro-popup view-top"><div class=pro-popup-icon></div><div class=pro-popup-text></div></div>';
        document.body.appendChild(fragNode(block));
        this.initFinish = true;
      }
    },
    show:function (str,config) {
      var icon = $qs(".pro-popup-icon"),
        text = $qs(".pro-popup-text"),
        popup = $qs(".pro-popup"),
        classStr = "pro-popup-icon i-pp-"+config.type;
      icon.setAttribute("class",classStr);
      text.textContent = str;
      popup.style.display = "block";
      if(config.type!=="loading"){
        var timeOut = config.time?config.time:1500;
        setTimeout(function () {
          popup.style.display = "none";
          if(config.callback){
            config.callback();
          }
        },timeOut);
      }
    },
    close:function () {
      var popup = $qs(".pro-popup");
      popup.style.display = "none";
    }
  }
};

