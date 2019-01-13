
// 转为json数据格式
function transformToJson(formData) {
    var obj = {}
    for (var i in formData) {
        /*[{"name":"user","value":"hpc"},{"name":"pwd","value":"123"},{"name":"sex","value":"M"},{"name":"age","value":"100"},{"name":"phone","value":"13011112222"},{"name":"email","value":"xxx@xxx.com"}]
        */
        //下标为的i的name做为json对象的key，下标为的i的value做为json对象的value
        obj[formData[i].name] = formData[i]['value'];
    }
    return obj;
}

// 信息提示
function showMsg2(mesg) {
    $('.js_dialog').css({"opacity": 0,"display": "none"});
    $('#iosDialog2 .weui-dialog__bd').html(mesg);
    $('#iosDialog2').css({"opacity": 1,"display": "block"});
}

function closeMsg() {
    $('.js_dialog').css({"display": "none"});
}


