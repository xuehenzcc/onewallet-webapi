package com.group.wallet.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.group.core.controller.BaseController;
import com.group.utils.HttpClientUtils;
import com.group.utils.ResponseUtils;
import com.group.wallet.channel.payment.yspay.utils.SignUtils;
import com.group.wallet.channel.payment.yspay.utils.StringUtils;
import com.group.wallet.channel.pos.lkl.bean.OrderResultBean;
import com.group.wallet.channel.quick.shenzhouPay.utils.MerchantApiUtil;
import com.group.wallet.channel.quick.youfuPay.impl.YoufuTmQuickPayImpl;
import com.group.wallet.channel.quick.youfuPay.utils.Base64;
import com.group.wallet.channel.quick.youfuPay.utils.LocalUtil;
import com.group.wallet.service.NoticeService;
import com.group.wallet.service.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 通知
 */
@Controller
@RequestMapping("notice")
public class NoticeController extends BaseController {

    @Autowired
    private NoticeService noticeService;
    @Autowired
    private PayService payService;

    /**
     * 神州支付快捷支付通知
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("szzfpay")
    public void szzfpay(HttpServletRequest request, HttpServletResponse response) throws Exception{
        logger.info("收到神州支付快捷支付通知");
        String payKey = request.getParameter("payKey");
        String paySecret = request.getParameter("paySecret");
        String orderPrice = request.getParameter("orderPrice");
        String outTradeNo = request.getParameter("outTradeNo");
        String productType = request.getParameter("productType");
        String orderTime = request.getParameter("orderTime");
        String productName = request.getParameter("productName");
        String tradeStatus = request.getParameter("tradeStatus");
        String successTime = request.getParameter("successTime");
        String trxNo = request.getParameter("trxNo");
        String field2 = request.getParameter("field2");
        String remark = request.getParameter("remark");
        remark = URLDecoder.decode(remark, "utf-8");
        String remitRequestNo = request.getParameter("remitRequestNo");
        String remitStatus = request.getParameter("remitStatus");
        String sign = request.getParameter("sign");

        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("orderPrice", orderPrice);
        paramMap.put("orderTime", orderTime);
        paramMap.put("outTradeNo", outTradeNo);
        paramMap.put("payKey", payKey);
        paramMap.put("productName", productName);
        paramMap.put("productType", productType);
        paramMap.put("remark", remark);
        paramMap.put("remitRequestNo", remitRequestNo);
        paramMap.put("remitStatus", remitStatus);
        paramMap.put("successTime", successTime);
        paramMap.put("tradeStatus", tradeStatus);
        paramMap.put("trxNo", trxNo);
        paramMap.put("paySecret",paySecret);

        String sign1 = MerchantApiUtil.getSign(paramMap, "eb067bc7f8b040178fce2a43cd5336f3");
        if(!sign.equals(sign1)){
            //throw new ServiceException("2000","签名错误");
        }

        noticeService.paySuccessNotice(outTradeNo);
        ResponseUtils.renderText(response, "SUCCESS");
    }

    /**
     * 哲扬支付通知
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("zheyangfpay")
    public void zheyangfpay(HttpServletRequest request, HttpServletResponse response) throws Exception{
        logger.info("===收到哲扬支付快捷支付通知===");
        String bizOrderNumber = request.getParameter("bizOrderNumber");
        String completedTime = request.getParameter("completedTime");
        String mid = request.getParameter("mid");
        String srcAmt = request.getParameter("srcAmt");
        String sign = request.getParameter("sign");

        logger.info("bizOrderNumber-"+bizOrderNumber);
        logger.info("completedTime-"+completedTime);
        logger.info("mid-"+mid);
        logger.info("srcAmt-"+srcAmt);
        logger.info("sign-"+sign);

        Map<String, Object> map = new HashMap<>();
        map.put("bizOrderNumber", bizOrderNumber);
        map.put("completedTime", completedTime);
        map.put("mid", mid);
        map.put("srcAmt", srcAmt);
        map.put("sign", sign);
        boolean check = noticeService.checkSign("ZYZF", map);
        if(check){
            noticeService.paySuccessNotice(bizOrderNumber);
            ResponseUtils.renderText(response, "success");
        }else {
            logger.error("===签名失败===");
        }
    }

    /**
     * 易宝支付通知
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("yibaopay")
    public void yibaopay(HttpServletRequest request, HttpServletResponse response) throws Exception{
        logger.info("===收到易宝支付快捷支付通知===");
        String code = request.getParameter("code");
        String message = request.getParameter("message");
        String requestId = request.getParameter("requestId");
        String customerNumber = request.getParameter("customerNumber");
        String externalld = request.getParameter("externalld");
        String createTime = request.getParameter("createTime");
        String payTime = request.getParameter("payTime");
        String amount = request.getParameter("amount");
        String fee = request.getParameter("fee");
        String status = request.getParameter("status");
        String busiType = request.getParameter("busiType");
        String bankCode = request.getParameter("bankCode");
        String payerName = request.getParameter("payerName");
        String payerPhone = request.getParameter("payerPhone");
        String lastNo = request.getParameter("lastNo");
        String src = request.getParameter("src");
        String hmac = request.getParameter("hmac");

        logger.info("code-"+code);
        logger.info("message-"+message);
        logger.info("requestId-"+requestId);
        logger.info("customerNumber-"+customerNumber);
        logger.info("externalld-"+externalld);
        logger.info("createTime-"+createTime);
        logger.info("payTime-"+payTime);
        logger.info("amount-"+amount);
        logger.info("fee-"+fee);
        logger.info("status-"+status);
        logger.info("busiType-"+busiType);
        logger.info("bankCode-"+bankCode);
        logger.info("payerName-"+payerName);
        logger.info("payerPhone-"+payerPhone);
        logger.info("lastNo-"+lastNo);
        logger.info("src-"+src);

        if("0000".equals(code)){
            StringBuffer signature = new StringBuffer();
            signature
                    .append(code == null ? "" : code)
                    .append(message == null ? "" : message)
                    .append(requestId == null ? "" : requestId)
                    .append(customerNumber == null ? "" : customerNumber)
                    .append(externalld == null ? "" : externalld)
                    .append(createTime == null ? "" : createTime)
                    .append(payTime == null ? "" : payTime)
                    .append(amount == null ? "" : amount)
                    .append(fee == null ? "" : fee)
                    .append(status == null ? "" : status)
                    .append(busiType == null ? "" : busiType)
                    .append(bankCode == null ? "" : bankCode)
                    .append(payerName == null ? "" : payerName)
                    .append(payerPhone == null ? "" : payerPhone)
                    .append(lastNo == null ? "" : lastNo)
                    .append(src == null ? "" : src);

            Map<String, Object> map = new HashMap<>();
            map.put("source", signature.toString());
            map.put("hmac", hmac);

            boolean check = noticeService.checkSign("YIBAO", map);
            if(check){
                //出款
                try {
                    Map<String, Object> params = new HashMap<>();
                    params.put("requestId", requestId);
                    params.put("customerNumber", customerNumber);
                    params.put("transferWay", "1");
                    payService.settlement("YIBAO", params);
                } catch (Exception e) {
                    logger.error("===易宝结算失败===", e);
                }

                //系统结算
                noticeService.paySuccessNotice(requestId);
                logger.info("===易宝通知成功===");
                ResponseUtils.renderText(response, "success");
            }else {
                logger.error("===签名验证失败===");
            }
        }
    }

    /**
     * 优付支付通知
     * @param request
     * @param response1
     * @throws Exception
     */
    @RequestMapping(value = "youfupay", produces = "text/html;charset=UTF-8")
    public void youfupay(HttpServletRequest request, HttpServletResponse response1) throws Exception{
        logger.info("===收到优付支付快捷支付通知===");

        String strcont = "";
        Enumeration<String> e=request.getParameterNames();
        while(e.hasMoreElements()){
            String s=(String) e.nextElement();
            strcont = s + request.getParameter(s);
        }

        JSONObject jsonObj = JSONObject.parseObject(strcont);
        String serviceCode = jsonObj.getString("serviceCode");
        JSONObject response = jsonObj.getJSONObject("response");

        Map<String, Object> signMap = new HashMap<String, Object>();
        signMap.put("amount", response.get("amount").toString());
        signMap.put("respCode", response.get("respCode").toString());
        signMap.put("channelNum", response.get("channelNum").toString());
        signMap.put("appOrderId", response.get("appOrderId").toString());
        signMap.put("orderId", response.get("orderId").toString());
        signMap.put("respInfo", response.get("respInfo").toString());
        signMap.put("sign", response.get("sign").toString().replace(" ","+"));

        boolean check = noticeService.checkSign("YOUFUTM", signMap);
        if(check){
            noticeService.paySuccessNotice(response.get("appOrderId").toString());
            ResponseUtils.renderText(response1, "success");
        }else {
            logger.error("====签名校验失败===");
        }
    }

    /**
     * 福汇通付款通知
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("fuhtpay")
    public void fuhtpay(HttpServletRequest request, HttpServletResponse response) throws Exception{
        logger.info("===收到福汇通支付快捷支付通知===");

        String strcont = "";
        Enumeration e = request.getParameterNames();
        while (e.hasMoreElements()) {
            strcont = (String) e.nextElement();
            System.out.println(strcont);
        }

        Map<String, Object> result = new HashMap<>();

        Map<String, Object> map = JSONObject.parseObject(strcont, HashMap.class);
        String return_code = (String) map.get("return_code");
        if(!"01".equals(return_code)){
            result.put("return_code", "02");
            result.put("return_msg", "付款失败");
        }else {
            boolean check = noticeService.checkSign("FUHT", map);
            if(check){
                String result_code = (String) map.get("result_code");
                if (!"01".equals(result_code)){
                    result.put("return_code", "02");
                    result.put("return_msg", "付款失败");
                }else {
                    noticeService.paySuccessNotice(map.get("attach").toString());
                    result.put("return_code", "01");
                    result.put("return_msg", "操作成功");
                }
            }else {
                logger.error("====签名校验失败===");
                result.put("return_code", "02");
                result.put("return_msg", "签名失败");
            }
        }

        ResponseUtils.renderText(response, JSON.toJSONString(result));
    }

    /**
     * 微刷支付通知
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("weishuapay")
    public void weishuapay(HttpServletRequest request, HttpServletResponse response) throws Exception{
        logger.info("===收到微刷支付快捷支付通知===");

        String transtype = request.getParameter("transtype");
        String merchno = request.getParameter("merchno");
        String signType = request.getParameter("signType");
        String status = request.getParameter("status");
        String orderid = request.getParameter("orderid");
        String dsorderid = request.getParameter("dsorderid");
        String amount = request.getParameter("amount");
        String paytime = request.getParameter("paytime");
        String sign = request.getParameter("sign");

        String result = "";
        if("00".equals(status)){
            Map<String, Object> map = new HashMap<>();
            map.put("transtype", transtype);
            map.put("merchno", merchno);
            map.put("signType", signType);
            map.put("status", status);
            map.put("orderid", orderid);
            map.put("dsorderid", dsorderid);
            map.put("amount", amount);
            map.put("paytime", paytime);
            map.put("sign", sign);

            boolean check = noticeService.checkSign("WS", map);
            if(check){
                noticeService.paySuccessNotice(dsorderid);
                result = "success";
            }else {
                logger.error("===签名失败===");
                result = "error";
            }
        }else {
            logger.error("===支付失败===");
            result = "error";
        }

        ResponseUtils.renderText(response, result);
    }

    /**
     * 北京凌创快捷支付通知
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("lingchuangpay")
    public void lingchuangpay(HttpServletRequest request, HttpServletResponse response) throws Exception{
        logger.info("===收到北京凌创快捷支付通知===");

        String strcont = HttpClientUtils.JsonReq(request);
        System.out.println(strcont);
        Map<String, Object> map = JSON.parseObject(strcont, HashMap.class);
        String paySt = (String) map.get("paySt");

        String result = "";
        if("2".equals(paySt)){
            boolean check = noticeService.checkSign("LINGCHUANG", map);
            if(check){
                String mchntOrderNo = (String) map.get("mchntOrderNo");
                noticeService.paySuccessNotice(mchntOrderNo);
                result = "success";
            }else {
                logger.error("===签名失败===");
                result = "error";
            }
        }else {
            logger.error("===支付失败===");
            result = "error";
        }

        ResponseUtils.renderText(response, result);
    }

    /**
     * 收到四方通道支付通知
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("sifangpay")
    public void sifangpay(HttpServletRequest request, HttpServletResponse response) throws Exception{
        logger.info("===收到四方通道快捷支付通知===");

        String merchId = request.getParameter("merchId");
        String tranDate = request.getParameter("tranDate");
        String tranId = request.getParameter("tranId");
        String tranTime = request.getParameter("tranTime");
        String tranAmt = request.getParameter("tranAmt");
        String settleDate = request.getParameter("settleDate");
        String respCode = request.getParameter("respCode");
        String respMsg = request.getParameter("respMsg");
        String signature = request.getParameter("signature");

        Map<String, Object> map = new HashMap<>();
        map.put("merchId", merchId);
        map.put("tranDate", tranDate);
        map.put("tranId", tranId);
        map.put("tranTime", tranTime);
        map.put("tranAmt", tranAmt);
        map.put("settleDate", settleDate);
        map.put("respCode", respCode);
        map.put("respMsg", respMsg);
        map.put("signature", signature);

        String result = "";
        if("00".equals(respCode)){
            //boolean check = noticeService.checkSign("SIFANG", map);
            if(true){
                noticeService.paySuccessNotice(tranId);
                result = "success";
            }else {
                logger.error("===签名失败===");
                result = "error";
            }
        }else {
            logger.error("===支付失败===");
            result = "error";
        }

        ResponseUtils.renderText(response, result);
    }

    /**
     * 拉卡拉刷卡支付通知
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("lklpay")
    public void lklpay(HttpServletRequest request, HttpServletResponse response) throws Exception{
        logger.info("===收到拉卡拉支付快捷支付通知===");

        String strcont = HttpClientUtils.JsonReq(request);
        System.out.println(strcont);

        Map<String, Object> result = new HashMap<>();

        OrderResultBean orderResultBean = JSONObject.parseObject(strcont, OrderResultBean.class);
        orderResultBean.setSign(orderResultBean.getSign().replace(" ", "+"));
        String payed_code = orderResultBean.getPayed_code();
        if("1".equals(payed_code)){
            if(orderResultBean.verify()){
                String app_order_no = orderResultBean.getApp_order_no();
                noticeService.paySuccessNotice(app_order_no);
                result.put("success", "ok");
            }else {
                logger.error("===签名失败===");
                result.put("success", "fail");
            }
        } else {
            logger.error("===支付失败===");
            result.put("success", "fail");
        }

        ResponseUtils.renderText(response, JSON.toJSONString(result));
    }

    /**
     * 银盛支付成功通知
     * @param request
     * @param resp
     * @throws Exception
     */
    @RequestMapping("yszfpayNotice")
    public void yszfpayNotice(HttpServletRequest request, HttpServletResponse resp) throws Exception{
        logger.info("===收到银盛代付结果通知===");

        PrintWriter out = null;
        request.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        Map<String, String> params = new HashMap();
        Map<String, String[]> requestParams = request.getParameterMap();
        logger.info("===回调参数："+ JSON.toJSONString(requestParams));

        for (Map.Entry<String, String[]> entry : requestParams.entrySet()) {
            params.put(entry.getKey(), entry.getValue()[0]);
        }

        try {
            boolean verifyResult = SignUtils.verifySign(request, params);
            out = resp.getWriter();
            String trade_status = params.get("trade_status") == null?"":(String)params.get("trade_status");
            String protocol_status = params.get("protocol_status") == null?"":(String)params.get("protocol_status");
            if(verifyResult) {
                logger.info("====异步通知验证签名成功===");
                if(!trade_status.equals("TRADE_FINISHED") && !"PROTOCOL_EFFECT".equals(protocol_status)) {
                    if(trade_status.equals("TRADE_SUCCESS")) {
                        out.println("success");

                        String out_trade_no = params.get("out_trade_no");
                        noticeService.paymentNotice(out_trade_no);
                    } else {
                        out.println("fail");
                    }
                } else {
                    out.println("success");
                }
            } else {
                out.println("fail");
                logger.info("===异步通知验证签名失败===");
            }
        } catch (Exception var14) {
            logger.error("签名异常", var14);
        } finally {
            if(out != null) {
                out.close();
            }
        }
    }

    /**
     * 芝麻认证通知
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("zhimaNotice")
    public void zhimaNotice(HttpServletRequest request, HttpServletResponse response) throws Exception{

    }

    /**
     * 升级用户级别通知
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("upgradeNotice")
    public void upgradeNotice(HttpServletRequest request, HttpServletResponse response) throws Exception{
        /*String userId = request.getParameter("userId");
        String orderPrice = request.getParameter("orderPrice");
        settleService.calculateUpgrdeProfit(Long.parseLong(userId), new BigDecimal(orderPrice));
        renderJson(request, response, SysCode.SUCCESS, null);*/
    }
}
