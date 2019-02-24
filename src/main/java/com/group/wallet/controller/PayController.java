package com.group.wallet.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.group.core.annotation.ActionAnnotation;
import com.group.core.commons.SysCode;
import com.group.core.config.MyWebAppConfig;
import com.group.core.controller.BaseController;
import com.group.utils.SignUtils;
import com.group.wallet.model.zzlm.ZzlmDeductRate;
import com.group.wallet.service.OrderService;
import com.group.wallet.service.PayService;

/**
 * 支付
 */
@Controller
@RequestMapping("pay")
public class PayController extends BaseController {

    @Autowired
    private PayService payService;
    @Autowired
    private OrderService orderService;

    /**
     * 获取通道
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("getChannels")
    @ActionAnnotation(params={"userId","type"}, encrypt = true)
    public void getChannels(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String,String> params = (Map<String,String>) request.getAttribute("params");
        String userId = params.get("userId");
        String type = params.get("type");

        Assert.hasLength(userId, "userId不能为空");
        Assert.hasLength(type, "type不能为空");

        List<Map<String, Object>> list = payService.getChannels(Long.parseLong(userId), type);
        Map<Class<?>, String[]> includes = new HashMap<Class<?>, String[]>() {
            private static final long serialVersionUID = -5349178483472578926L;
            {
                put(ZzlmDeductRate.class, new String[] {"id","channelId","channelName","channelType","remarks","settleType","deductRate","poundage","limitMin","limitMax","dayMax"});
            }
        };
        renderJson(request, response, SysCode.SUCCESS, list, includes);
    }

    /**
     * 注册子商户
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("registSubMerchant")
    @ActionAnnotation(params={"channelId","userId","settleType"}, encrypt = true)
    public void registSubMerchant(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String,String> params = (Map<String,String>) request.getAttribute("params");
        String channelId = params.get("channelId");
        String userId = params.get("userId");
        String settleType = params.get("settleType");

        Assert.hasLength(channelId, "channelId不能为空");
        Assert.hasLength(userId, "userId不能为空");
        Assert.hasLength(settleType, "settleType不能为空");

        Map<String, Object> result = payService.registSubMerchant(Long.parseLong(channelId), Long.parseLong(userId), Long.parseLong("29"), settleType);
        renderJson(request, response, SysCode.SUCCESS, result);
    }

    /**
     * 快捷支付: 下单&返回H5
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("quickPay")
    @ActionAnnotation(params={"userId","channelId","bankCardId","orderPrice","settleType","lon","lat","tradeAddress"}, encrypt = true)
    public void quickPay(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String,String> params = (Map<String,String>) request.getAttribute("params");
        String userId = params.get("userId");
        String channelId = params.get("channelId");
        String orderPrice = params.get("orderPrice");
        String settleType = params.get("settleType");
        String bankCardId = params.get("bankCardId");
        String lon = params.get("lon");
        String lat = params.get("lat");
        String tradeAddress = params.get("tradeAddress");

        Assert.hasLength(userId, "userId不能为空");
        Assert.hasLength(channelId, "channelId不能为空");
        Assert.hasLength(orderPrice, "orderPrice不能为空");
        Assert.hasLength(orderPrice, "orderPrice不能为空");
        Assert.hasLength(bankCardId, "bankCardId不能为空");
        Assert.hasLength(lon, "lon不能为空");
        Assert.hasLength(lat, "lat不能为空");
        Assert.hasLength(tradeAddress, "tradeAddress不能为空");

        Map<String, Object> result = payService.quickPay(Long.parseLong(userId), Long.parseLong(channelId), Long.parseLong(bankCardId), new BigDecimal(orderPrice), settleType, Double.parseDouble(lon), Double.parseDouble(lat), tradeAddress);
        renderJson(request, response, SysCode.SUCCESS, result);
    }

    /**
     * 刷卡支付
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("posPay")
    @ActionAnnotation(params={"userId","channelId","bankCardId","orderPrice","settleType","lon","lat","tradeAddress"}, encrypt = true)
    public void posPay(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String,String> params = (Map<String,String>) request.getAttribute("params");
        String userId = params.get("userId");
        String channelId = params.get("channelId");
        String orderPrice = params.get("orderPrice");
        String settleType = params.get("settleType");
        String bankCardId = params.get("bankCardId");
        String lon = params.get("lon");
        String lat = params.get("lat");
        String tradeAddress = params.get("tradeAddress");

        Assert.hasLength(userId, "userId不能为空");
        Assert.hasLength(channelId, "channelId不能为空");
        Assert.hasLength(orderPrice, "orderPrice不能为空");
        Assert.hasLength(orderPrice, "orderPrice不能为空");
        Assert.hasLength(bankCardId, "bankCardId不能为空");
        Assert.hasLength(lon, "lon不能为空");
        Assert.hasLength(lat, "lat不能为空");
        Assert.hasLength(tradeAddress, "tradeAddress不能为空");

        Map<String, Object> result = payService.posPay(Long.parseLong(userId), Long.parseLong(channelId), Long.parseLong(bankCardId), new BigDecimal(orderPrice), settleType, Double.parseDouble(lon), Double.parseDouble(lat), tradeAddress);
        renderJson(request, response, SysCode.SUCCESS, result);
    }

    /**
     * 发送短信验证码
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("quickPaySendSms")
    @ActionAnnotation(params={"outTradeNo","jsonParams"}, encrypt = false)
    public void quickPaySendSms(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String,String> params = (Map<String,String>) request.getAttribute("params");
        String outTradeNo = params.get("outTradeNo");
        String jsonParams = params.get("jsonParams");

        Assert.hasLength(outTradeNo, "outTradeNo不能为空");
        Assert.hasLength(jsonParams, "jsonParams不能为空");

        Map<String, Object> result = payService.quickPaySendSms(outTradeNo, jsonParams);
        renderJson(request, response, SysCode.SUCCESS, result);
    }

    /**
     * 快捷支付确认
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("quickPayConfirm")
    @ActionAnnotation(params={"outTradeNo","jsonParams"}, encrypt = false)
    public void quickPayConfirm(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String,String> params = (Map<String,String>) request.getAttribute("params");
        String outTradeNo = params.get("outTradeNo");
        String jsonParams = params.get("jsonParams");

        Assert.hasLength(outTradeNo, "outTradeNo不能为空");
        Assert.hasLength(jsonParams, "jsonParams不能为空");

        Map<String, Object> result = payService.quickPayConfirm(outTradeNo, jsonParams);
        renderJson(request, response, SysCode.SUCCESS, result);
    }

    /**
     * 代付
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("payment")
    @ActionAnnotation(params={"userId","channelNum","outTradeNo","amount"}, encrypt = true)
    public void payment(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String,String> params = (Map<String,String>) request.getAttribute("params");
        String userId = params.get("userId");
        String channelNum = params.get("channelNum");
        String outTradeNo = params.get("outTradeNo");
        String amount = params.get("amount");

        Assert.hasLength(userId, "userId不能为空");
        Assert.hasLength(channelNum, "channelNum不能为空");
        Assert.hasLength(outTradeNo, "outTradeNo不能为空");
        Assert.hasLength(amount, "amount不能为空");

        payService.payment(request, Long.parseLong(userId), channelNum, outTradeNo, new BigDecimal(amount));
        renderJson(request, response, SysCode.SUCCESS, null);
    }

    /**
     * 易宝结算，临时
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("yibaosettle")
    @ActionAnnotation(params={"orderId", "balance"}, encrypt = true)
    public void yibaosettle(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String,String> params = (Map<String,String>) request.getAttribute("params");
        String orderId = params.get("orderId");
        String balance = params.get("balance");

        payService.yibaosettle(Long.parseLong(orderId), balance);
        renderJson(request, response, SysCode.SUCCESS, null);
    }

    /**
     * 跳转到支付页面
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("index")
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String orderNo = request.getParameter("orderNo");
        String sign = request.getParameter("sign");

        Map<String, Object> map = new HashMap<>();
        if(StringUtils.isEmpty(orderNo)){
            map.put("message", "订单号为空");
            return new ModelAndView("payment/error", map);
        }

        Map<String, String> params = new HashMap<String, String>();
        params.put("orderNo", orderNo);
        String sign1 = SignUtils.getSign(params, MyWebAppConfig.environment.getProperty("ape.apikey"), logger);
        if(!sign.equals(sign1)){
            map.put("message", "签名失败");
            return new ModelAndView("payment/error", map);
        }

        map = payService.h5Pay(orderNo);
        String channelNo = (String) map.get("channelNo");
        if("YOUFUTM".equals(channelNo) || "YOUFUTM2".equals(channelNo)){
            return new ModelAndView("payment/youfuPay", map);
        }
        else if ("FUHT".equals(channelNo)){
            return new ModelAndView("payment/fuhtPay", map);
        }
        else if ("WS".equals(channelNo)){
            return new ModelAndView("payment/weishuaPay", map);
        }
        else if ("SIFANG".equals(channelNo)){
            return new ModelAndView("payment/sifangPay", map);
        }
        else {
            map.put("message", "找不到页面");
            return new ModelAndView("payment/error", map);
        }
    }

    /**
     * 调整到支付成功页面
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("success")
    public ModelAndView success(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String orderNo = request.getParameter("orderNo");
        Map<String, Object> map = new HashMap<>();
        if(StringUtils.isNotEmpty(orderNo)){
            try {
                map = payService.h5Pay(orderNo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new ModelAndView("payment/success", map);
    }

}
