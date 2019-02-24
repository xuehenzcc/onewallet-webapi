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

import com.group.core.annotation.ActionAnnotation;
import com.group.core.commons.SysCode;
import com.group.core.controller.BaseController;
import com.group.wallet.model.WalletPlan;
import com.group.wallet.model.WalletPlanDetail;
import com.group.wallet.service.RepaymentCreditCardService;

/**
 * 还信用卡
 */
@Controller
@RequestMapping("repayment")
public class RepaymentCreditCardController extends BaseController{

    @Autowired
    private RepaymentCreditCardService repaymentCreditCardService;

    /**
     * 创建还款计划
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("addPlan")
    @ActionAnnotation(params={"userId","cardId","planType","amount","count"}, encrypt = true)
    public void addPlan(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String,String> params = (Map<String,String>) request.getAttribute("params");
        String userId = params.get("userId");
        String cardId = params.get("cardId");
        String planType = params.get("planType");
        String amount = params.get("amount");
        String count = params.get("count");

        Assert.hasLength(userId, "userId不能为空");
        Assert.hasLength(cardId, "cardId不能为空");
        Assert.hasLength(planType, "planType不能为空");
        Assert.hasLength(amount, "amount不能为空");
        Assert.hasLength(count, "count不能为空");

        Map<String, Object> result = repaymentCreditCardService.addPlan(Long.parseLong(userId), Long.parseLong(cardId), planType, new BigDecimal(amount), Integer.parseInt(count));
        Map<Class<?>, String[]> includes = new HashMap<Class<?>, String[]>() {
            private static final long serialVersionUID = -5349178483472578926L;
            {
                put(WalletPlan.class, new String[] {"id","deductRate","poundage"});
                put(WalletPlanDetail.class, new String[] {"id","executeTime","payAmount","arrivalAmount","deductRate","poundage","serviceCharge","count","state","stateName"});
            }
        };
        renderJson(request, response, SysCode.SUCCESS, result, includes);
    }

    /**
     * 确认还款计划
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("confirmPlan")
    @ActionAnnotation(params={"planId"},encrypt = true)
    public void confirmPlan(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String,String> params = (Map<String,String>) request.getAttribute("params");
        String planId = params.get("planId");

        Assert.hasLength(planId, "planId不能为空");

        repaymentCreditCardService.confirmPlan(Long.parseLong(planId));
        renderJson(request, response, SysCode.SUCCESS, null);
    }

    /**
     * 还款进度查询
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("planList")
    @ActionAnnotation(params={"userId","planType"},encrypt = true)
    public void planList(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String,String> params = (Map<String,String>) request.getAttribute("params");
        String userId = params.get("userId");
        String planType = params.get("planType");

        Assert.hasLength(userId, "userId不能为空");
        Assert.hasLength(planType, "planType不能为空");

        List<WalletPlan> list = repaymentCreditCardService.planList(Long.parseLong(userId), planType);
        Map<Class<?>, String[]> includes = new HashMap<Class<?>, String[]>() {
            private static final long serialVersionUID = -5349178483472578926L;
            {
                put(WalletPlan.class, new String[] {"id","cardNo","cardName","amount","state","stateName","startTime","endTime"});
            }
        };
        renderJson(request, response, SysCode.SUCCESS, list, includes);
    }

    /**
     * 还款计划详情
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("planDetail")
    @ActionAnnotation(params={"planId"}, encrypt = true)
    public void planDetail(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String,String> params = (Map<String,String>) request.getAttribute("params");
        String planId = params.get("planId");

        Assert.hasLength(planId, "planId不能为空");

        List<WalletPlanDetail> list = repaymentCreditCardService.planDetail(Long.parseLong(planId));
        Map<Class<?>, String[]> includes = new HashMap<Class<?>, String[]>() {
            private static final long serialVersionUID = -5349178483472578926L;
            {
                put(WalletPlanDetail.class, new String[] {"id","executeTime","payAmount","arrivalAmount","deductRate","poundage","serviceCharge","count","state","stateName"});
            }
        };
        renderJson(request, response, SysCode.SUCCESS, list, includes);
    }

    /**
     * 中止计划
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("abortPlan")
    @ActionAnnotation(params={"planId"}, encrypt = true)
    public void abortPlan(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String,String> params = (Map<String,String>) request.getAttribute("params");
        String planId = params.get("planId");

        Assert.hasLength(planId, "planId不能为空");

        repaymentCreditCardService.abortPlan(Long.parseLong(planId));
        renderJson(request, response, SysCode.SUCCESS, null);
    }

    /**
     * 获取还款等级，费率
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("getCreditRate")
    @ActionAnnotation(params={"userId"}, encrypt = true)
    public void getCreditRate(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String,String> params = (Map<String,String>) request.getAttribute("params");
        String userId = params.get("userId");

        Assert.hasLength(userId, "userId不能为空");

        Map<String, Object> result = repaymentCreditCardService.getCreditRate(Long.parseLong(userId));
        renderJson(request, response, SysCode.SUCCESS, result);
    }

    /**
     * 创建还卡升级订单
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("createUpgradeOrder")
    @ActionAnnotation(params={"userId","userCreditType","amount"},encrypt = true)
    public void createUpgradeOrder(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String,String> params = (Map<String,String>) request.getAttribute("params");
        String userId = params.get("userId");
        String userCreditType = params.get("userCreditType");
        String amount = params.get("amount");

        Assert.hasLength(userId, "userId不能为空");
        Assert.hasLength(userCreditType, "userCreditType不能为空");
        Assert.hasLength(amount, "amount不能为空");

        Map<String, Object> result = repaymentCreditCardService.createUpgradeOrder(Long.parseLong(userId), userCreditType, new BigDecimal(amount));
        renderJson(request, response, SysCode.SUCCESS, result);
    }



}
