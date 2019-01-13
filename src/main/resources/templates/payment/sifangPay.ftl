<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <title>快捷支付</title>
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0"
          id="vp"/>
    <link rel="stylesheet" href="https://res.wx.qq.com/open/libs/weui/1.1.2/weui.min.css">
    <link rel="stylesheet" type="text/css" href="../css/base.css"/>
    <script type="text/javascript" src="https://res.wx.qq.com/open/libs/weuijs/1.1.3/weui.min.js"></script>
    <script src="http://lib.sinaapp.com/js/jquery/2.0.2/jquery-2.0.2.min.js"></script>
    <script src="../js/zhifu.js" type="text/javascript"></script>
</head>
<body>

<div class="paylia">
    <div class="weui-cells__title duolor">￥${realTradeMoney}</div>
    <div class="weui-cell weli2">
        <#--<div class="weui-cell__hd navmig">
            <img src="img/jtong.jpg" height="34px" width="33px" alt=""/>
        </div>-->
        <div class="weui-cell__bd">
            <div class="nav_rig">
                <div class="nav_cen">
                    <P class="nav_rig_txt">${bankCardName}</P>
                    <div class="nav_last">
                        <span>尾号${bankCardNo}</span><span style="margin-left:0.666667rem;">信用卡</span>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- 交通银行 -->
    <div class="paylo">
        <form id="form" name="form" method="post" action="">
            <input style="display: none" type="text" id="outTradeNo" name="outTradeNo" value="${orderNo}" />
            <input style="display: none" type="text" id="serial" name="serial" value="" />

            <div class="weui-cell">
                <div class="weui-cell__hd"><label class="weui-label">有效期</label></div>
                <div class="weui-cell__bd">
                    <input name="validThru" id="validThru" class="weui-input period" type="number" autocomplete="off"
                           placeholder="如：09/15输入1509"/>
                </div>
            </div>
            <div class="weui-cell">
                <div class="weui-cell__hd"><label class="weui-label">CCV2</label></div>
                <div class="weui-cell__bd">
                    <input name="cvv2" id="cvv2" class="weui-input CVV2" type="number" autocomplete="off" placeholder="卡背面后三位"/>
                </div>
            </div>

            <input style="display: none" type="text" id="orderNoSure" name="orderNoSure" value="" />
            <div class="weui-cell mesgbtn">
                <div class="weui-cell__hd"><label class="weui-label">验证码</label></div>
                <div class="weui-cell__bd">
                    <input name="smsCode" id="mesg" class="weui-input mesg" type="tel" value="" placeholder="短信验证码"/>
                    <input type="button" id="btn" class="btn weui-btn weui-btn_plain-primary" value="获取验证码"
                           onclick="settime(this)"/>
                </div>
            </div>

        </form>
    </div>
    <!-- paylo -->
    <div class="weui-btn-area">
        <button onclick="confirmPay(this)" class="confirm_btn weui-btn weui-btn_primary weui-btn_loading">确认支付</button>
    </div>

    <div id="dialogs">
        <!--BEGIN dialog1-->
        <div class="js_dialog" id="iosDialog1" style="display: none;">
            <div class="weui-mask"></div>
            <div class="weui-dialog">
                <div class="weui-dialog__hd"><strong class="weui-dialog__title">弹窗标题</strong></div>
                <div class="weui-dialog__bd">弹窗内容，告知当前状态、信息和解决方法，描述文字尽量控制在三行内</div>
                <div class="weui-dialog__ft">
                    <a href="javascript:;" class="weui-dialog__btn weui-dialog__btn_default">辅助操作</a>
                    <a href="javascript:;" class="weui-dialog__btn weui-dialog__btn_primary">主操作</a>
                </div>
            </div>
        </div>
        <!--END dialog1-->
        <!--BEGIN dialog2-->
        <div class="js_dialog" id="iosDialog2" style="display: none;">
            <div class="weui-mask"></div>
            <div class="weui-dialog">
                <div class="weui-dialog__bd">弹窗内容，告知当前状态、信息和解决方法，描述文字尽量控制在三行内</div>
                <div class="weui-dialog__ft">
                    <a href="javascript:closeMsg();" class="weui-dialog__btn weui-dialog__btn_primary">知道了</a>
                </div>
            </div>
        </div>
        <!--END dialog2-->
        <!--BEGIN dialog3-->
        <div class="js_dialog" id="androidDialog1" style="display: none;">
            <div class="weui-mask"></div>
            <div class="weui-dialog weui-skin_android">
                <div class="weui-dialog__hd"><strong class="weui-dialog__title">弹窗标题</strong></div>
                <div class="weui-dialog__bd">
                    弹窗内容，告知当前状态、信息和解决方法，描述文字尽量控制在三行内
                </div>
                <div class="weui-dialog__ft">
                    <a href="javascript:;" class="weui-dialog__btn weui-dialog__btn_default">辅助操作</a>
                    <a href="javascript:;" class="weui-dialog__btn weui-dialog__btn_primary">主操作</a>
                </div>
            </div>
        </div>
        <!--END dialog3-->
        <!--BEGIN dialog4-->
        <div class="js_dialog" id="androidDialog2" style="display: none;">
            <div class="weui-mask"></div>
            <div class="weui-dialog weui-skin_android">
                <div class="weui-dialog__bd">
                    弹窗内容，告知当前状态、信息和解决方法，描述文字尽量控制在三行内
                </div>
                <div class="weui-dialog__ft">
                    <a href="javascript:;" class="weui-dialog__btn weui-dialog__btn_default">辅助操作</a>
                    <a href="javascript:;" class="weui-dialog__btn weui-dialog__btn_primary">主操作</a>
                </div>
            </div>
        </div>
        <!--END dialog4-->
    </div>
</div>

<script>

    //确认支付
    function confirmPay(_this) {
        $(_this).attr("disabled","true");
        $(_this).css({"background-color": "#959595"});
        $(_this).html('<i class="weui-loading"></i>支付中...');

        var formData = transformToJson($("#form").serializeArray());
        var smsCode = formData.smsCode;
        if(!smsCode){
            $(_this).removeAttr("disabled");
            $(_this).css({"background-color": "#23AC3A"});
            $(_this).html('确认支付');
            showMsg2("验证码不能为空！");
            return;
        }

        $.ajax({
            type: "post",
            url: "quickPayConfirm",
            contentType: "application/x-www-form-urlencoded; charset=UTF-8",
            data: {"outTradeNo":formData.outTradeNo,"jsonParams":JSON.stringify(formData)},
            success: function (data) {
                $(_this).removeAttr("disabled");
                $(_this).css({"background-color": "#23AC3A"});
                $(_this).html('确认支付');

                var code = data.code;
                if (code == "0000") {
                    window.location.href = "success?orderNo=${orderNo}";
                } else {
                    var message = data.message;
                    showMsg2(message);
                }
            },
            error: function () {
                $(_this).removeAttr("disabled");
                $(_this).css({"background-color": "#23AC3A"});
                $(_this).html('确认支付');
                showMsg2("网络错误");
            }
        })
    }

    var countdown = 60;
    function settime(obj) {
        if(countdown==60){
            sendSms(obj);
        }
        if (countdown == 0) {
            obj.removeAttribute("disabled");
            obj.value = "获取验证码";
            countdown = 60;
            return;
        } else {
            obj.style.backgroundColor = "#ccc";
            obj.style.border.color = "#eee"
            obj.setAttribute("disabled", true);
            obj.value = "重新发送(" + countdown + ")";
            countdown--;
            if (countdown == 0) {
                obj.style.backgroundColor = "#fff";
            }
        }
        setTimeout(function () {
            settime(obj)
        }, 1000);
    }

    //发送短信
    function sendSms(obj) {
        var formData = transformToJson($("#form").serializeArray());
        $.ajax({
            type: "post",
            url: "quickPaySendSms",
            contentType: "application/x-www-form-urlencoded; charset=UTF-8",
            data: {"outTradeNo":formData.outTradeNo,"jsonParams":JSON.stringify(formData)},
            success: function (data) {
                var code = data.code;
                if (code == "0000") {
                    var serial = data.result.serial;
                    $("#serial").val(serial);
                    showMsg2("短信验证码发送成功");
                } else {
                    var message = data.message;
                    showMsg2(message);
                }
            },
            error: function () {
                showMsg2("网络错误");
            }
        })
    }

</script>

</body>
</html>