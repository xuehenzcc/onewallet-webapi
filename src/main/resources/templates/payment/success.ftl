<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
    <title>支付成功</title>
    <link rel="stylesheet" href="https://res.wx.qq.com/open/libs/weui/1.1.2/weui.min.css">
    <link rel="stylesheet" type="text/css" href="../css/base.css"/>
    <script type="text/javascript" src="https://res.wx.qq.com/open/libs/weuijs/1.1.3/weui.min.js"></script>
    <script src="http://lib.sinaapp.com/js/jquery/2.0.2/jquery-2.0.2.min.js"></script>
</head>
<style>
    .page-bd {
        margin-top: 40%;
        text-align: center;
    }

    .icon-box__ctn {
        margin-top: 10px;
    }

</style>
<body>
<div class="page-bd">
    <i class="weui-icon-success weui-icon_msg"></i>
    <div class="icon-box__ctn weui-msg__text-area">
        <h3 class="icon-box__title">支付成功</h3>
        <p class="weui-msg__desc">实际支付结果以银行扣款结果为准</p>
        <p class="weui-msg__desc">金额：${(realTradeMoney)!} 元</p>
        <p class="weui-msg__desc">通道名称：${(channelName)!}</p>
        <p class="weui-msg__desc">订单号：${(orderNo)!}</p>
    </div>
</div>
</body>
</html>