
document.addEventListener("DOMContentLoaded",function(){
    let $btnmesg=$('.btn');
    let $btnconfirm=$('.confirm_btn');
    var txt=document.querySelector('.mesgtxt')
       //获取登录按钮绑定点击事件
    $btnmesg.click(function(e){
       //获取用户名密码
       
        var username=$(".usernam").val(); 
        var idcar=$(".idcar").val(); 
        var period=$("#period").val();
        var phone =$("#phone").val();
        var CVV2  =$("#CVV2").val();
        console.log(phone)
        //返回数据并且判断
        $.ajax({
            type:"post",
            url:"",
            dataType:'json',
            async:true,
            data:{username:username,idcar:idcar,period:period,phone:phone,CVV2:CVV2},
            success:function(data){
                if(data.code>0){
                alert('success')

                }else{
                alert('error')
                }
            },
            error:function(){
                alert("网络连接错误，请检查");
            }
        });
    })

    $btnconfirm.click(function(e){
        $(e.target).attr("disabled","disabled");
        $(e.target).css({"background-color":"#959595"});
        $("#loading").html("<img src='../img/loading.gif'/>");
        var formData = transformToJson($("#form").serializeArray());
        $.ajax({
            type: "post",
            url: "quickPayConfirm",
            contentType : "application/x-www-form-urlencoded; charset=UTF-8",
            data: formData,
            success: function (data) {
                $(e.target).removeAttr("disabled");
                $(e.target).css({"background-color":"#23AC3A"});
                $("#loading").html("");//ajax返回成功，清除loading图标

                var code = data.code;
                if(code=="0000"){
                    window.location.href = "success";
                }else {
                    var message = data.message;
                    showerror(message);
                }
            },
            error:function(){
                $(e.target).removeAttr("disabled");
                $(e.target).css({"background-color":"#23AC3A"});
                $("#loading").empty();//ajax返回成功，清除loading图标
                showerror("网络错误");
            }
        })
    });

    function showerror(mesg) {
        txt.classList.add('sux');
        txt.innerHTML = mesg + '支付失败'
    }
});

var countdown=60; 
function settime(obj) { 
    if (countdown == 0) { 
        obj.removeAttribute("disabled");    
        obj.value="获取验证码"; 
        countdown = 60;

        showMsg2("已发送");
        return;
    } else { 
        obj.style.backgroundColor = "#ccc";
        obj.style.border.color="#eee"
        obj.setAttribute("disabled", true); 
        obj.value="重新发送(" + countdown + ")"; 
        countdown--; 
        if(countdown==0){
            obj.style.backgroundColor = "#fff";
        }
    } 
    setTimeout(function() {
        settime(obj) }
        ,1000)
}

// 转为json数据格式
function transformToJson(formData){
    var obj={}
    for (var i in formData) {
        /*[{"name":"user","value":"hpc"},{"name":"pwd","value":"123"},{"name":"sex","value":"M"},{"name":"age","value":"100"},{"name":"phone","value":"13011112222"},{"name":"email","value":"xxx@xxx.com"}]
        */
        //下标为的i的name做为json对象的key，下标为的i的value做为json对象的value
        obj[formData[i].name]=formData[i]['value'];
    }
    return obj;
}



