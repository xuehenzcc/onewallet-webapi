package com.group.wallet.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.group.core.annotation.ActionAnnotation;
import com.group.core.commons.SysCode;
import com.group.core.controller.BaseController;
import com.group.core.service.QiniuyunService;
import com.group.wallet.model.CommonAdvertising;
import com.group.wallet.model.WalletModule;
import com.group.wallet.model.WalletReceiveOrder;
import com.group.wallet.model.WalletTradeRecords;
import com.group.wallet.model.zzlm.ZzlmAdvance;
import com.group.wallet.model.zzlm.ZzlmAdvanceBill;
import com.group.wallet.model.zzlm.ZzlmIncomeRecords;
import com.group.wallet.service.WalletService;

/**
 * 钱包
 */
@Controller
@RequestMapping("wallet")
public class WalletController extends BaseController {

    @Autowired
    private WalletService walletService;
    @Autowired
    private QiniuyunService qiniuyunService;

    /**
     * 创建领取刷卡机订单
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("createPOSMachineOrder")
    @ActionAnnotation(params={"userId","unitPrice","count","totalPrice","expressCompany","receiveMan","linkTel","receiveAddress"}, encrypt = true)
    public void createPOSMachineOrder(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String,String> params = (Map<String,String>) request.getAttribute("params");
        String userId = params.get("userId");
        String unitPrice = params.get("unitPrice");
        String count = params.get("count");
        String totalPrice = params.get("totalPrice");
        String expressCompany = params.get("expressCompany");
        String receiveMan = params.get("receiveMan");
        String linkTel = params.get("linkTel");
        String receiveAddress = params.get("receiveAddress");

        Assert.hasLength(userId, "userId不能为空");
        Assert.hasLength(unitPrice, "unitPrice不能为空");
        Assert.hasLength(count, "count不能为空");
        Assert.hasLength(totalPrice, "totalPrice不能为空");
        Assert.hasLength(expressCompany, "expressCompany不能为空");
        Assert.hasLength(receiveMan, "receiveMan不能为空");
        Assert.hasLength(linkTel, "linkTel不能为空");
        Assert.hasLength(receiveAddress, "receiveAddress不能为空");

        WalletReceiveOrder receiveOrder = new WalletReceiveOrder();
        receiveOrder.setUserId(Long.parseLong(userId));
        receiveOrder.setUnitPrice(new BigDecimal(unitPrice));
        receiveOrder.setCount(Integer.parseInt(count));
        receiveOrder.setTotalPrice(new BigDecimal(totalPrice));
        receiveOrder.setExpressCompany(expressCompany);
        receiveOrder.setReceiveMan(receiveMan);
        receiveOrder.setLinkTel(linkTel);
        receiveOrder.setReceiveAddress(receiveAddress);

        Map<String,Object> result = walletService.createPOSMachineOrder(receiveOrder);
        renderJson(request, response, SysCode.SUCCESS, result);
    }

    /**
     * 绑定刷卡器
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("bindPOSMachine")
    @ActionAnnotation(params={"userId","sn"}, encrypt = true)
    public void bindPOSMachine(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String,String> params = (Map<String,String>) request.getAttribute("params");
        String userId = params.get("userId");
        String sn = params.get("sn");

        Assert.hasLength(userId, "userId不能为空");
        Assert.hasLength(sn, "sn不能为空");

        Map<String, Object> result = walletService.getMyIncome(Long.parseLong(userId));
        renderJson(request, response, SysCode.SUCCESS, result);
    }

    /**
     * 获取我的收益
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("getMyIncome")
    @ActionAnnotation(params={"userId"}, encrypt = true)
    public void getMyIncome(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String,String> params = (Map<String,String>) request.getAttribute("params");
        String userId = params.get("userId");
        Assert.hasLength(userId, "userId不能为空");

        Map<String, Object> result = walletService.getMyIncome(Long.parseLong(userId));
        renderJson(request, response, SysCode.SUCCESS, result);
    }

    /**
     * 获取收益明细
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("incomeRecords")
    @ActionAnnotation(params={"userId","startDate","endDate"}, encrypt = true)
    public void incomeRecords(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String,String> params = (Map<String,String>) request.getAttribute("params");
        String userId = params.get("userId");
        String startDate = params.get("startDate");
        String endDate = params.get("endDate");

        Assert.hasLength(userId, "userId不能为空");

        Map<String,Object> result = walletService.incomeRecords(Long.parseLong(userId), startDate, endDate);
        renderJson(request, response, SysCode.SUCCESS, result);
    }

    /**
     * 获取全部明细
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("allIncomeRecords")
    @ActionAnnotation(params={"userId","type","pageNo"}, encrypt = true)
    public void allIncomeRecords(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String,String> params = (Map<String,String>) request.getAttribute("params");
        String userId = params.get("userId");
        String type = params.get("type");
        String pageNo = params.get("pageNo");

        Assert.hasLength(userId, "userId不能为空");
        Assert.hasLength(type, "type不能为空");
        Assert.hasLength(pageNo, "pageNo不能为空");

        List<ZzlmIncomeRecords> list = walletService.allIncomeRecords(Long.parseLong(userId), type, Integer.parseInt(pageNo));
        Map<Class<?>, String[]> includes = new HashMap<Class<?>, String[]>() {
            private static final long serialVersionUID = -5349178483472578926L;
            {
                put(ZzlmIncomeRecords.class, new String[] {"id","userId","phone","name","userType","type","typeName","amount","stateName","descp","createTime"});
            }
        };
        renderJson(request, response, SysCode.SUCCESS, list, includes);
    }

    /**
     * 收款明细
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("proceedsRecords")
    @ActionAnnotation(params={"userId","pageNo"}, encrypt = true)
    public void proceedsRecords(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String,String> params = (Map<String,String>) request.getAttribute("params");
        String userId = params.get("userId");
        String pageNo = params.get("pageNo");

        Assert.hasLength(userId, "userId不能为空");
        Assert.hasLength(pageNo, "pageNo不能为空");

        List<WalletTradeRecords> list = walletService.proceedsRecords(Long.parseLong(userId), Integer.parseInt(pageNo));
        Map<Class<?>, String[]> includes = new HashMap<Class<?>, String[]>() {
            private static final long serialVersionUID = -5349178483472578926L;
            {
                put(WalletTradeRecords.class, new String[] {"id","orderNo","tradeTime","realTradeMoney","tradeState","settleType","channelName"});
            }
        };
        renderJson(request, response, SysCode.SUCCESS, list, includes);
    }

    /**
     * 判断是否可提现
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("checkTakeOut")
    @ActionAnnotation(params={"userId"}, encrypt = true)
    public void checkTakeOut(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String,String> params = (Map<String,String>) request.getAttribute("params");
        String userId = params.get("userId");
        Assert.hasLength(userId, "userId不能为空");

        walletService.checkTakeOut(Long.parseLong(userId));
        renderJson(request, response, SysCode.SUCCESS, null);
    }

    /**
     * 分润提现
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("takeOutIncom")
    @ActionAnnotation(params={"userId","amount"}, encrypt = true)
    public void takeOutIncom(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String,String> params = (Map<String,String>) request.getAttribute("params");
        String userId = params.get("userId");
        String amount = params.get("amount");

        Assert.hasLength(userId, "userId不能为空");
        Assert.hasLength(amount, "amount不能为空");

        Map<String, Object> result = walletService.takeOutIncom(request, Long.parseLong(userId), new BigDecimal(amount));
        renderJson(request, response, SysCode.SUCCESS, result);
    }

    /**
     * 获取我的预支收益
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("getMyAdvanceIncome")
    @ActionAnnotation(params={"userId"}, encrypt = true)
    public void getMyAdvanceIncome(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String,String> params = (Map<String,String>) request.getAttribute("params");
        String userId = params.get("userId");
        Assert.hasLength(userId, "userId不能为空");

        Map<String, Object> result = walletService.getMyAdvanceIncome(Long.parseLong(userId));
        renderJson(request, response, SysCode.SUCCESS, result);
    }

    /**
     * 确认预支
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("advanceIncome")
    @ActionAnnotation(params={"userId","amount"}, encrypt = true)
    public void advanceIncome(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String,String> params = (Map<String,String>) request.getAttribute("params");
        String userId = params.get("userId");
        String amount = params.get("amount");

        Assert.hasLength(userId, "userId不能为空");
        Assert.hasLength(amount, "amount不能为空");

        Long advanceId = walletService.advanceIncome(Long.parseLong(userId), new BigDecimal(amount));
        renderJson(request, response, SysCode.SUCCESS, advanceId);
    }

    /**
     * 更新预支签名
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("updateSign")
    @ActionAnnotation(params={"advanceId"}, encrypt = true)
    public void updateSign(HttpServletRequest request, HttpServletResponse response,
                           @RequestParam("signImage")MultipartFile signImage) throws Exception{
        Map<String,String> params = (Map<String,String>) request.getAttribute("params");
        String advanceId = params.get("advanceId");

        Assert.hasLength(advanceId, "advanceId不能为空");

        String imageUrl =  qiniuyunService.uploadByest(signImage.getBytes(),null);
        ZzlmAdvance advance = new ZzlmAdvance();
        advance.setId(Long.parseLong(advanceId));
        advance.setSignature(imageUrl);

        walletService.updateSign(advance);
        renderJson(request, response, SysCode.SUCCESS, null);
    }

    /**
     * 预支明细
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("advancedIncomeRecords")
    @ActionAnnotation(params={"userId","pageNo"}, encrypt = true)
    public void advancedIncomeRecords(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String,String> params = (Map<String,String>) request.getAttribute("params");
        String userId = params.get("userId");
        String pageNo = params.get("pageNo");

        Assert.hasLength(userId, "userId不能为空");
        Assert.hasLength(pageNo, "pageNo不能为空");

        List<ZzlmAdvance> list = walletService.advancedIncomeRecords(Long.parseLong(userId), Integer.parseInt(pageNo));
        Map<Class<?>, String[]> includes = new HashMap<Class<?>, String[]>() {
            private static final long serialVersionUID = -5349178483472578926L;
            {
                put(ZzlmAdvance.class, new String[] {"id","userId","baseAmount","noRefundAmount","state","stateName","createTime"});
            }
        };
        renderJson(request, response, SysCode.SUCCESS, list, includes);
    }

    /**
     * 账单明细
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("advancedIncomeBills")
    @ActionAnnotation(params={"userId","pageNo"}, encrypt = false)
    public void advancedIncomeBills(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String,String> params = (Map<String,String>) request.getAttribute("params");
        String userId = params.get("userId");
        String pageNo = params.get("pageNo");

        Assert.hasLength(userId, "userId不能为空");
        Assert.hasLength(pageNo, "pageNo不能为空");

        List<ZzlmAdvanceBill> list = walletService.advancedIncomeBills(Long.parseLong(userId), Integer.parseInt(pageNo));
        Map<Class<?>, String[]> includes = new HashMap<Class<?>, String[]>() {
            private static final long serialVersionUID = -5349178483472578926L;
            {
                put(ZzlmAdvanceBill.class, new String[] {"id","baseAmount","yesProfit","manageAmount","penalAmount","noRefundAmount","refundAmount","dayCount","createTime"});
            }
        };
        renderJson(request, response, SysCode.SUCCESS, list, includes);
    }

    /**
     * 获取账户管理费
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("getManageAmount")
    @ActionAnnotation(params={"userId"}, encrypt = true)
    public void getManageAmount(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String,String> params = (Map<String,String>) request.getAttribute("params");
        String userId = params.get("userId");
        Assert.hasLength(userId, "userId不能为空");

        Map<String, BigDecimal> result = walletService.getManageAmount(Long.parseLong(userId));
        renderJson(request, response, SysCode.SUCCESS, result);
    }

    /**
     * 提前还款
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("prepayment")
    @ActionAnnotation(params={"userId","amount"}, encrypt = true)
    public void prepayment(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String,String> params = (Map<String,String>) request.getAttribute("params");
        String userId = params.get("userId");
        String amount = params.get("amount");

        Assert.hasLength(userId, "userId不能为空");
        Assert.hasLength(amount, "amount不能为空");

        Map<String, String> result = walletService.prepayment(Long.parseLong(userId), new BigDecimal(amount));
        renderJson(request, response, SysCode.SUCCESS, result);
    }

    /**
     * 创建升级订单
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("createUpgradeOrder")
    @ActionAnnotation(params={"userId","userType","amount"}, encrypt = true)
    public void createUpgradeOrder(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String,String> params = (Map<String,String>) request.getAttribute("params");
        String userId = params.get("userId");
        String userType = params.get("userType");
        String amount = params.get("amount");

        Assert.hasLength(userId, "userId不能为空");
        Assert.hasLength(userType, "userType不能为空");
        Assert.hasLength(amount, "amount不能为空");

        Map<String, Object> result = walletService.createUpgradeOrder(Long.parseLong(userId), userType, new BigDecimal(amount));
        renderJson(request, response, SysCode.SUCCESS, result);
    }

    /**
     * 获取轮播图，通知
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("getAdverts")
    @ActionAnnotation(params={"advertType"}, encrypt = true)
    public void getAdverts(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String,String> params = (Map<String,String>) request.getAttribute("params");
        String advertType = params.get("advertType");

        Assert.hasLength(advertType, "advertType不能为空");

        List<CommonAdvertising> list = walletService.getAdverts(advertType);
        Map<Class<?>, String[]> includes = new HashMap<Class<?>, String[]>() {
            private static final long serialVersionUID = -5349178483472578926L;
            {
                put(CommonAdvertising.class, new String[] {"id","title","image","linkUrl","content"});
            }
        };
        renderJson(request, response, SysCode.SUCCESS, list, includes);
    }

    /**
     * 获取第三方链接
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("getThirdLinks")
    @ActionAnnotation(params={"type"}, encrypt = true)
    public void getThirdLinks(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String,String> params = (Map<String,String>) request.getAttribute("params");
        String type = params.get("type");

        Assert.hasLength(type, "type不能为空");

        List<WalletModule> list = walletService.getThirdLinks(type);
        Map<Class<?>, String[]> includes = new HashMap<Class<?>, String[]>() {
            private static final long serialVersionUID = -5349178483472578926L;
            {
                put(WalletModule.class, new String[] {"id","title","introduce","image","linkUrl","content"});
            }
        };
        renderJson(request, response, SysCode.SUCCESS, list, includes);
    }

    /**
     * 获取拉卡拉订单详情
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("getLklOrderDetail")
    @ActionAnnotation(params={"orderId"}, encrypt = true)
    public void getLklOrderDetail(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String,String> params = (Map<String,String>) request.getAttribute("params");
        String orderId = params.get("orderId");

        Assert.hasLength(orderId, "orderId不能为空");

        JSONObject result = walletService.getLklOrderDetail(Long.parseLong(orderId));
        renderJson(request, response, SysCode.SUCCESS, result);
    }

}
