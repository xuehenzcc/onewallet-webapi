package com.group.wallet.controller;

import com.group.core.annotation.ActionAnnotation;
import com.group.core.commons.SysCode;
import com.group.core.controller.BaseController;
import com.group.wallet.channel.quick.yibaoPay.impl.YibaoQuickPayImpl;
import com.group.wallet.model.WalletDeductRate;
import com.group.wallet.model.WalletUserInfo;
import com.group.wallet.service.ChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 通道
 */
@Controller
@RequestMapping("channel")
public class ChannelController extends BaseController {

    @Autowired
    private ChannelService channelService;

    @Autowired
    private YibaoQuickPayImpl yibaoQuickPay;

    /**
     * 获取通道汇率
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("getChannelRate")
    @ActionAnnotation(params={"channelId"})
    public void getChannelRate(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String,String> params = (Map<String,String>) request.getAttribute("params");
        String channelId = params.get("channelId");
        Assert.hasLength(channelId, "channelId不能为空");

        WalletDeductRate result = channelService.getChannelRate(Long.parseLong(channelId));
        Map<Class<?>, String[]> includes = new HashMap<Class<?>, String[]>() {
            private static final long serialVersionUID = -5349178483472578926L;
            {
                put(WalletDeductRate.class, new String[] {"id","deductRate","poundage","limitMin","limitMax","dayMax"});
            }
        };
        renderJson(request, response, SysCode.SUCCESS, result, includes);
    }

    /**
     * 测试接口
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("test")
    @ActionAnnotation(params={"channelId"}, encrypt = false)
    public void test(HttpServletRequest request, HttpServletResponse response) throws Exception{
        yibaoQuickPay.updateImage();
        renderJson(request, response, SysCode.SUCCESS);
    }

}
