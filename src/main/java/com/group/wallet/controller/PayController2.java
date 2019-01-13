package com.group.wallet.controller;

import com.group.core.annotation.ActionAnnotation;
import com.group.core.commons.SysCode;
import com.group.core.controller.BaseController;
import com.group.core.service.AlipayService;
import com.group.utils.OrderUtils;
import com.group.utils.ResponseUtils;
import com.group.wallet.model.*;
import com.group.wallet.model.enums.OrderType;
import com.group.wallet.model.vo.ShopOrder;
import com.group.wallet.service.CommonService;
import com.group.wallet.service.NoticeService;
import com.group.wallet.service.OrderService;
import com.group.wallet.service.PayService;
import com.group.wallet.service.wal.HomeService;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 支付接口
 * @author Administrator
 *
 */

@Controller
@RequestMapping("pay")
public class PayController2 extends BaseController{
	
	Logger logger = LoggerFactory.getLogger(PayController.class);

	@Value("${spring.profiles.active}")
	private String profiles;

	@Autowired
	private AlipayService alipayService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private NoticeService noticeService;
	@Autowired
	private CommonService commonService;
	@Autowired
	private HomeService homeService;
	
	/**
	 * 支付宝支付
	 * @param req
	 * @param resp
	 * @throws Exception
	 */
	@RequestMapping("alipayRequest")
	@ActionAnnotation(params={"outTradeNo","payWay"}, encrypt = true)
	public void alipayRequest(HttpServletRequest req,HttpServletResponse resp) throws Exception{
		Map<String, String> params = (Map<String, String>) req.getAttribute("params");
        String outTradeNo = params.get("outTradeNo");
        String payWay = params.get("payWay");

        Assert.hasLength(outTradeNo, "订单号不能为空");
		Assert.hasLength(payWay, "付款方式不能为空");

		SysConfig sysConfig = commonService.getConfigValue("onewallet_api_url");
		String apiUrl = sysConfig.getConfigvalue();

		String productName = "";
		String orderPrice = "";
		String notifyUrl = apiUrl+"pay/apilypayNotify";
		logger.info("支付宝异步通知地址："+notifyUrl);

		if(StringUtils.startsWith(outTradeNo, OrderType.升级订单.getValue())){
			WalletUpgradeOrder upgradeOrder = orderService.getUpgradeOrder(outTradeNo);
			productName = upgradeOrder.getTitle();
			orderPrice = upgradeOrder.getOrderPrice()+"";
		}

		if(StringUtils.startsWith(outTradeNo, OrderType.提前还款订单.getValue())){
			WalletRefundOrder refundOrder = orderService.getRefundOrder(outTradeNo);
			productName = refundOrder.getTitle();
			orderPrice = refundOrder.getAmount()+"";
		}

		if(StringUtils.startsWith(outTradeNo, OrderType.购买刷卡机订单.getValue())){
			WalletReceiveOrder refundOrder = orderService.getReceiveOrder(outTradeNo);
			productName = "购买刷卡机订单";
			orderPrice = new DecimalFormat("#.##").format(refundOrder.getTotalPrice());
		}
		//至尊联盟订单
		if(StringUtils.startsWith(outTradeNo, "Z")){
			ShopOrder order=new ShopOrder();
			order.setOrderNum(outTradeNo);
			List<ShopOrder> orderList = homeService.getShopOrderList(order);
			productName = "购买POS订单";
			orderPrice = new DecimalFormat("#.##").format(orderList.get(0).getTotalPrice());
		}
		
		if("dev".equals(profiles)){
			orderPrice = "0.01";
		}

		Map<String, Object> result = new HashMap<String, Object>();
		String orderString = alipayService.createAlipayOrder("",productName,outTradeNo,orderPrice,notifyUrl);
		result.put("orderString", orderString);
				
		renderJson(req, resp, SysCode.SUCCESS, result);
	} 

	/**
	 * 支付宝支付异步通知
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("apilypayNotify")
	public void apilypayNotify(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Map requestParams = request.getParameterMap();
		boolean flag = alipayService.verifyNotify(requestParams);
		if(flag){
			//商户订单号
			String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
			logger.info("商户订单号："+out_trade_no);
			//交易状态
			String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
			logger.info("交易状态："+trade_status);
			
			//支付成功处理逻辑
			if(trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")){
				if(StringUtils.startsWith(out_trade_no, OrderType.升级订单.getValue())){
					noticeService.upgradePayNotice(out_trade_no);
				}

				if(StringUtils.startsWith(out_trade_no, OrderType.购买刷卡机订单.getValue())){
					noticeService.buyPOSMachineOrderNotice(out_trade_no);
				}

				if(StringUtils.startsWith(out_trade_no, OrderType.提前还款订单.getValue())){
					noticeService.refundOrderNotice(out_trade_no);
				}
				//至尊联盟
				if(StringUtils.startsWith(out_trade_no, "Z")){
					ShopOrder order =new ShopOrder();
					order.setOrderNum(out_trade_no);
					System.out.println("支付宝支付成功通知：out_trade_no="+out_trade_no);
					homeService.updateOrderByPay(order);
				}
				
				logger.info("支付宝通知成功,后台处理结果为：SUCCESS");
				ResponseUtils.renderText(response, "SUCCESS");
			}
		}else{
			//验证失败
			logger.info("参数验证失败");
		}
	}
	
	/**
	 * 付款成功回调(测试)
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("paySuccess")
	@ActionAnnotation(params={"outTradeNo"})
	public void sellers(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Map<String, String> params = (Map<String, String>) request.getAttribute("params");
		String outTradeNo = params.get("outTradeNo");
		Assert.hasLength(outTradeNo, "outTradeNo不能为空");

		String result = null;

		renderJson(request, response, SysCode.SUCCESS, result);
	}
}
