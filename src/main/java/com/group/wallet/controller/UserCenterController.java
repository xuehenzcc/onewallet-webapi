package com.group.wallet.controller;

import com.alibaba.fastjson.JSONObject;
import com.group.core.annotation.ActionAnnotation;
import com.group.core.commons.SysCode;
import com.group.core.controller.BaseController;
import com.group.core.enums.FileType;
import com.group.core.exception.ServiceException;
import com.group.core.service.QiniuyunService;
import com.group.wallet.channel.pos.lkl.impl.LklPosPayImpl;
import com.group.wallet.model.*;
import com.group.wallet.model.enums.TradeState;
import com.group.wallet.model.enums.UserState;
import com.group.wallet.service.NoticeService;
import com.group.wallet.service.UserCenterService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static javax.swing.UIManager.put;

/**
 * 用户中心
 */
@Controller
@RequestMapping("userCenter")
public class UserCenterController extends BaseController {

    @Autowired
    private QiniuyunService qiniuyunService;
    @Autowired
    private UserCenterService userCenterService;


    /**
     * 注册
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("regist")
    @ActionAnnotation(params={"phone","password","inviter"}, encrypt = true)
    public void regist(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String,String> params = (Map<String,String>) request.getAttribute("params");
        String phone = params.get("phone");
        String password = params.get("password");
        String inviter = params.get("inviter");

        Assert.hasLength(phone, "key不能为空");
        Assert.hasLength(password, "password不能为空");
        //Assert.hasLength(inviter, "inviter不能为空");

        userCenterService.regist(phone,password,inviter);
        renderJson(request, response, SysCode.SUCCESS, null);
    }

    /**
     * 获取用户信息
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("getUserInfo")
    @ActionAnnotation(params={"userId","token"}, encrypt = false)
    public void getUserInfo(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String,String> params = (Map<String,String>) request.getAttribute("params");
        String userId = params.get("userId");
        String token = params.get("token");

        Assert.hasLength(userId, "userId不能为空");

        HttpSession session = request.getSession();
        String token1 = (String) session.getValue("token");
        if(!token.equals(token1)){
            throw new ServiceException("2000","token验证失败");
        }

        WalletUserInfo userInfo = userCenterService.getUserInfo(Long.parseLong(userId));
        Map<Class<?>, String[]> includes = new HashMap<Class<?>, String[]>() {
            private static final long serialVersionUID = -5349178483472578926L;
            {
                put(WalletUserInfo.class, new String[] {"id","phone","realName"});
            }
        };
        renderJson(request, response, SysCode.SUCCESS, userInfo, includes);
    }

    /**
     * 获取拉卡拉用户信息
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("getLklUserInfo")
    @ActionAnnotation(params={"userId"}, encrypt = true)
    public void getLklUserInfo(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String,String> params = (Map<String,String>) request.getAttribute("params");
        String userId = params.get("userId");

        Assert.hasLength(userId, "userId不能为空");

        JSONObject result = userCenterService.getLklUserInfo(Long.parseLong(userId));
        renderJson(request, response, SysCode.SUCCESS, result);
    }

    /**
     * H5注册
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("h5Regist")
    @ActionAnnotation(params={"phone","password","inviter","token"}, encrypt = false)
    public void h5Regist(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String,String> params = (Map<String,String>) request.getAttribute("params");
        String phone = params.get("phone");
        String password = params.get("password");
        String inviter = params.get("inviter");
        String token = params.get("token");

        Assert.hasLength(phone, "key不能为空");
        Assert.hasLength(password, "password不能为空");
        Assert.hasLength(inviter, "inviter不能为空");

        HttpSession session = request.getSession();
        String token1 = (String) session.getValue("token");
        if(!token.equals(token1)){
            throw new ServiceException("2000","token验证失败");
        }

        userCenterService.h5Regist(phone, password, Long.parseLong(inviter));
        renderJson(request, response, SysCode.SUCCESS, null);
    }

    /**
     * 忘记密码
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("restPassword")
    @ActionAnnotation(params={"phone","password"}, encrypt = true)
    public void restPassword(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String,String> params = (Map<String,String>) request.getAttribute("params");
        String phone = params.get("phone");
        String password = params.get("password");

        Assert.hasLength(phone, "phone不能为空");
        Assert.hasLength(password, "password不能为空");

        userCenterService.restPassword(phone,password);
        renderJson(request, response, SysCode.SUCCESS, null);
    }

    /**
     * 登录
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("login")
    @ActionAnnotation(params={"phone","password"},encrypt = true)
    public void login(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String,String> params = (Map<String,String>) request.getAttribute("params");
        String phone = params.get("phone");
        String password = params.get("password");

        Assert.hasLength(phone, "phone不能为空");
        Assert.hasLength(password, "password不能为空");

        Map<String, Object> result = userCenterService.login(phone,password);
        renderJson(request, response, SysCode.SUCCESS, result);
    }

    /**
     * 获取用户中心数据
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("getUserCenterData")
    @ActionAnnotation(params={"userId"},encrypt = true)
    public void getUserCenterData(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String,String> params = (Map<String,String>) request.getAttribute("params");
        String userId = params.get("userId");

        Assert.hasLength(userId, "userId不能为空");

        Map<String,Object> result = userCenterService.getUserCenterData(Long.parseLong(userId));
        renderJson(request, response, SysCode.SUCCESS, result);
    }

    /**
     * 修改我的图像
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("updateHeadImage")
    @ActionAnnotation(params={"userId"},encrypt = true)
    public void updateHeadImage(HttpServletRequest request, HttpServletResponse response,
                                @RequestParam("headImage")MultipartFile headImage) throws Exception{
        Map<String,String> params = (Map<String,String>) request.getAttribute("params");
        String userId = params.get("userId");
        Assert.hasLength(userId, "userId不能为空");

        String imageUrl = userCenterService.updateHeadImage(Long.parseLong(userId),headImage);
        renderJson(request, response, SysCode.SUCCESS, imageUrl);
    }

    /**
     * 修改昵称
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("updateNickName")
    @ActionAnnotation(params={"userId","nickName"},encrypt = true)
    public void updateNickName(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String,String> params = (Map<String,String>) request.getAttribute("params");
        String userId = params.get("userId");
        String nickName = params.get("nickName");

        Assert.hasLength(userId, "userId不能为空");
        Assert.hasLength(nickName, "nickName不能为空");

        userCenterService.updateNickName(Long.parseLong(userId),nickName);
        renderJson(request, response, SysCode.SUCCESS, null);
    }

    /**
     * 修改微信号
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("updateWxNo")
    @ActionAnnotation(params={"userId","wxNo"},encrypt = true)
    public void updateWxNo(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String,String> params = (Map<String,String>) request.getAttribute("params");
        String userId = params.get("userId");
        String wxNo = params.get("wxNo");

        Assert.hasLength(userId, "userId不能为空");
        Assert.hasLength(wxNo, "wxNo不能为空");

        userCenterService.updateWxNo(Long.parseLong(userId),wxNo);
        renderJson(request, response, SysCode.SUCCESS, null);
    }

    /**
     * 修改微信二维码
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("updateWxQrCode")
    @ActionAnnotation(params={"userId"}, encrypt = true)
    public void updateWxQrCode(HttpServletRequest request, HttpServletResponse response,
                               @RequestParam("wxQrCode")MultipartFile wxQrCode) throws Exception{
        Map<String,String> params = (Map<String,String>) request.getAttribute("params");
        String userId = params.get("userId");
        Assert.hasLength(userId, "userId不能为空");

        String imageUrl = userCenterService.updateWxQrCode(Long.parseLong(userId),wxQrCode);
        renderJson(request, response, SysCode.SUCCESS, imageUrl);
    }

    /**
     * 隐私开关
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("privacywitch")
    @ActionAnnotation(params={"userId","privacy"}, encrypt = true)
    public void privacywitch(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String,String> params = (Map<String,String>) request.getAttribute("params");
        String userId = params.get("userId");
        String privacy = params.get("privacy");

        Assert.hasLength(userId, "userId不能为空");
        Assert.hasLength(privacy, "privacy不能为空");

        userCenterService.privacywitch(Long.parseLong(userId),privacy);
        renderJson(request, response, SysCode.SUCCESS, null);
    }

    /**
     * 获取分行
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("getBranchBank")
    @ActionAnnotation(params={"bankName","search","pageNo"}, encrypt = true)
    public void getBranchBank(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String,String> params = (Map<String,String>) request.getAttribute("params");
        String bankName = params.get("bankName");
        String search = params.get("search");
        String pageNo = params.get("pageNo");

        Assert.hasLength(bankName, "bankName不能为空");
        Assert.hasLength(pageNo, "pageNo不能为空");

        List<WalletBranchBank> list = userCenterService.getBranchBank(bankName, search, Integer.parseInt(pageNo));
        renderJson(request, response, SysCode.SUCCESS, list);
    }

    /**
     * 认证身份证
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("authIdCard")
    @ActionAnnotation(params={"userId","realName","idCard","indate"}, encrypt = true)
    public void authIdCard(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String,String> params = (Map<String,String>) request.getAttribute("params");
        String userId = params.get("userId");
        String realName = params.get("realName");
        String idCard = params.get("idCard");
        String indate = params.get("indate");

        Assert.hasLength(userId, "userId不能为空");
        Assert.hasLength(realName, "realName不能为空");
        Assert.hasLength(idCard, "idCard不能为空");
        Assert.hasLength(indate, "indate不能为空");

        userCenterService.authIdCard(Long.parseLong(userId), realName, idCard, indate);
        renderJson(request, response, SysCode.SUCCESS, "认证成功");
    }

    /**
     * 活体认证
     * @param request
     * @param response
     * @throws Exception
     */
    /*@RequestMapping("authActivityImage")
    @ActionAnnotation(params={"userId"}, encrypt = true)
    public void authActivityImage(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String,String> params = (Map<String,String>) request.getAttribute("params");
        String userId = params.get("userId");

        Assert.hasLength(userId, "userId不能为空");

        userCenterService.authActivityImage(Long.valueOf(userId), null);
        renderJson(request, response, SysCode.SUCCESS, null);
    }*/

    /**
     * 认证储蓄卡
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("realNameAuthentication")
    @ActionAnnotation(params={"userId","realName","idCard","indate","settleCardNo","settleProvince","settleCity","settleBank","settleBranchBank","settlePhone"}, encrypt = true)
    public void realNameAuthentication(HttpServletRequest request, HttpServletResponse response,
                                       @RequestParam("idCardZImage")MultipartFile idCardZImage,
                                       @RequestParam("idCardFImage")MultipartFile idCardFImage,
                                       @RequestParam("handIdcardImage")MultipartFile handIdcardImage,
                                       @RequestParam("settleCardZScan")MultipartFile settleCardZScan,
                                       @RequestParam("settleCardFScan")MultipartFile settleCardFScan) throws Exception{
        Map<String,String> params = (Map<String,String>) request.getAttribute("params");

        String userId = params.get("userId");
        String realName = params.get("realName");
        String idCard = params.get("idCard");
        String indate = params.get("indate");
        String settleCardNo = params.get("settleCardNo");
        String settleProvince = params.get("settleProvince");
        String settleCity = params.get("settleCity");
        String settleBank = params.get("settleBank");
        String settleBranchBank = params.get("settleBranchBank");
        String settlePhone = params.get("settlePhone");

        Assert.hasLength(userId, "userId不能为空");
        Assert.hasLength(realName, "realName不能为空");
        Assert.hasLength(idCard, "idCard不能为空");
        Assert.hasLength(indate, "indate不能为空");
        Assert.hasLength(settleCardNo, "settleCardNo不能为空");
        //Assert.hasLength(settleProvince, "settleProvince不能为空");
        //Assert.hasLength(settleCity, "settleCity不能为空");
        Assert.hasLength(settleBank, "settleBank不能为空");
        //Assert.hasLength(settleBranchBank, "settleBranchBank不能为空");
        Assert.hasLength(settlePhone, "settlePhone不能为空");

        //settleBank = StringUtils.replace(settleBank, "中国", "");
        settleBank = StringUtils.substringBefore(settleBank, "(");

        WalletUserInfo userInfo = new WalletUserInfo();
        userInfo.setId(Long.parseLong(userId));
        userInfo.setRealName(realName);
        userInfo.setIdCard(idCard);
        userInfo.setIndate(indate);
        userInfo.setSettleCardNo(settleCardNo);
        userInfo.setSettleProvince(settleProvince);
        userInfo.setSettleCity(settleCity);
        userInfo.setSettleBank(settleBank);
        userInfo.setSettleBranchBank(settleBranchBank);
        userInfo.setSettlePhone(settlePhone);
        userInfo.setSettleName(realName);

        userInfo.setIdCardZImage(qiniuyunService.uploadMultipartFile(idCardZImage, FileType.图片.getValue()));
        userInfo.setIdCardFImage(qiniuyunService.uploadMultipartFile(idCardFImage,FileType.图片.getValue()));
        userInfo.setHandIdcardImage(qiniuyunService.uploadMultipartFile(handIdcardImage,FileType.图片.getValue()));
        userInfo.setSettleCardZScan(qiniuyunService.uploadMultipartFile(settleCardZScan,FileType.图片.getValue()));
        userInfo.setSettleCardFScan(qiniuyunService.uploadMultipartFile(settleCardFScan,FileType.图片.getValue()));

        userInfo.setState(UserState.已认证储蓄卡.getValue());
        userCenterService.realNameAuthentication(userInfo);
        renderJson(request, response, SysCode.SUCCESS, null);
    }

    /**
     * 认证信用卡
     * @throws Exception
     */
    @RequestMapping("authCreditCard")
    @ActionAnnotation(params={"userId"}, encrypt = true)
    public void authCreditCard(HttpServletRequest request, HttpServletResponse response,
                                       @RequestParam("creditCardZImage")MultipartFile creditCardZImage,
                                       @RequestParam("handCreditImage")MultipartFile handCreditImage) throws Exception{

        Map<String,String> params = (Map<String,String>) request.getAttribute("params");
        String userId = params.get("userId");
        Assert.hasLength(userId, "userId不能为空");

        WalletUserInfo userInfo = new WalletUserInfo();
        userInfo.setId(Long.parseLong(userId));
        userInfo.setCreditCardZImage(qiniuyunService.uploadMultipartFile(creditCardZImage,FileType.图片.getValue()));
        userInfo.setHandCreditImage(qiniuyunService.uploadMultipartFile(handCreditImage,FileType.图片.getValue()));

        userInfo.setState(UserState.待审核.getValue());
        userCenterService.creditCardAuthentication(userInfo);
        renderJson(request, response, SysCode.SUCCESS, "认证成功");
    }

    @RequestMapping("authCreditCard2")
    @ActionAnnotation(params={"userId","isAuth"}, encrypt = true)
    public void authCreditCard2(HttpServletRequest request, HttpServletResponse response,
                               @RequestParam("creditCardZImage")MultipartFile creditCardZImage,
                               @RequestParam("handCreditImage")MultipartFile handCreditImage,
                               @RequestParam("activityImage")MultipartFile activityImage) throws Exception{

        Map<String,String> params = (Map<String,String>) request.getAttribute("params");
        String userId = params.get("userId");
        String isAuth = params.get("isAuth");
        Assert.hasLength(userId, "userId不能为空");
        Assert.hasLength(isAuth, "isAuth不能为空");

        WalletUserInfo userInfo = new WalletUserInfo();
        userInfo.setId(Long.parseLong(userId));
        userInfo.setIsAuth(Integer.valueOf(isAuth));
        userInfo.setCreditCardZImage(qiniuyunService.uploadMultipartFile(creditCardZImage,FileType.图片.getValue()));
        userInfo.setHandCreditImage(qiniuyunService.uploadMultipartFile(handCreditImage,FileType.图片.getValue()));
        userInfo.setActivityImage(qiniuyunService.uploadMultipartFile(activityImage,FileType.图片.getValue()));

        userInfo.setState(UserState.待审核.getValue());
        userCenterService.creditCardAuthentication(userInfo);
        renderJson(request, response, SysCode.SUCCESS, "认证成功");
    }

    /**
     * 获取我的信用卡
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("getMyCreditCards")
    @ActionAnnotation(params={"userId"}, encrypt = true)
    public void getMyCreditCards(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String,String> params = (Map<String,String>) request.getAttribute("params");
        String userId = params.get("userId");
        Assert.hasLength(userId, "userId不能为空");

        List<WalletBankCard> list = userCenterService.getMyCreditCards(Long.parseLong(userId));
        Map<Class<?>, String[]> includes = new HashMap<Class<?>, String[]>() {
            private static final long serialVersionUID = -5349178483472578926L;
            {
                put(WalletBankCard.class, new String[] {"id","name","idCard","bankName","cardNo","validThru","cvv","dueDate","billDate","phone","isAuth"});
            }
        };
        renderJson(request, response, SysCode.SUCCESS, list, includes);
    }

    /**
     * 获取他人信用卡
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("getOtherCreditCards")
    @ActionAnnotation(params={"userId"}, encrypt = true)
    public void getOtherCreditCards(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String,String> params = (Map<String,String>) request.getAttribute("params");
        String userId = params.get("userId");
        Assert.hasLength(userId, "userId不能为空");

        List<WalletBankCard> list = userCenterService.getOtherCreditCards(Long.parseLong(userId));
        Map<Class<?>, String[]> includes = new HashMap<Class<?>, String[]>() {
            private static final long serialVersionUID = -5349178483472578926L;
            {
                put(WalletBankCard.class, new String[] {"id","name","idCard","bankName","cardNo","validThru","cvv","dueDate","billDate","phone","isAuth"});
            }
        };
        renderJson(request, response, SysCode.SUCCESS, list, includes);
    }

    /**
     * 添加信用卡
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("addCreditCards")
    @ActionAnnotation(params={"userId","name","idCard","bankName","cardNo","phone","validThru","cvv","dueDate","billDate"}, encrypt = false)
    public void addCreditCards(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String,String> params = (Map<String,String>) request.getAttribute("params");
        String userId = params.get("userId");
        String name = params.get("name");
        String idCard = params.get("idCard");
        String bankName = params.get("bankName");
        String cardNo = params.get("cardNo");
        String phone = params.get("phone");
        String validThru = params.get("validThru");
        String cvv = params.get("cvv");
        String dueDate = params.get("dueDate");
        String billDate = params.get("billDate");

        Assert.hasLength(userId, "userId不能为空");
        //Assert.hasLength(name, "name不能为空");
        Assert.hasLength(idCard, "idCard不能为空");
        Assert.hasLength(bankName, "bankName不能为空");
        Assert.hasLength(cardNo, "cardNo不能为空");
        Assert.hasLength(phone, "phone不能为空");
        /*Assert.hasLength(validThru, "validThru不能为空");
        Assert.hasLength(cvv, "cvv不能为空");
        Assert.hasLength(dueDate, "dueDate不能为空");
        Assert.hasLength(billDate, "billDate不能为空");*/

        //bankName = StringUtils.replace(bankName, "中国", "");
        bankName = StringUtils.substringBefore(bankName, "(");

        WalletBankCard bankCard = new WalletBankCard();
        bankCard.setUserId(Long.parseLong(userId));
        bankCard.setName(name);
        bankCard.setIdCard(idCard);
        bankCard.setBankName(bankName);
        bankCard.setCardNo(cardNo);
        bankCard.setPhone(phone);
        bankCard.setValidThru(validThru);
        bankCard.setCvv(cvv);
        bankCard.setDueDate(dueDate);
        bankCard.setBillDate(billDate);
        bankCard.setIsAuth("0");
        bankCard.setCreateTime(new Date());

        userCenterService.addCreditCards(bankCard);
        renderJson(request, response, SysCode.SUCCESS, null);
    }

    /**
     * 修改信用卡
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("updateCreditCards")
    @ActionAnnotation(params={"cardId","validThru","cvv","dueDate","billDate"}, encrypt = false)
    public void updateCreditCards(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String,String> params = (Map<String,String>) request.getAttribute("params");
        String cardId = params.get("cardId");
        String validThru = params.get("validThru");
        String cvv = params.get("cvv");
        String dueDate = params.get("dueDate");
        String billDate = params.get("billDate");

        WalletBankCard bankCard = new WalletBankCard();
        bankCard.setId(Long.parseLong(cardId));
        bankCard.setValidThru(validThru);
        bankCard.setCvv(cvv);
        bankCard.setDueDate(dueDate);
        bankCard.setBillDate(billDate);

        userCenterService.updateCreditCards(bankCard);
        renderJson(request, response, SysCode.SUCCESS, null);
    }

    /**
     * 删除信用卡
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("delCreditCards")
    @ActionAnnotation(params={"cardId"}, encrypt = true)
    public void delCreditCards(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String,String> params = (Map<String,String>) request.getAttribute("params");
        String cardId = params.get("cardId");
        Assert.hasLength(cardId, "cardId不能为空");

        userCenterService.delCreditCards(Long.parseLong(cardId));
        renderJson(request, response, SysCode.SUCCESS, null);
    }

    /**
     * 修改结算卡
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("updateSettleCard")
    @ActionAnnotation(params={"userId","settleCardNo","settleProvince","settleCity","settleBank","settleBranchBank","settlePhone"}, encrypt = true)
    public void updateSettleCard(HttpServletRequest request, HttpServletResponse response,
                                 @RequestParam("settleCardZScan")MultipartFile settleCardZScan,
                                 @RequestParam("settleCardFScan")MultipartFile settleCardFScan) throws Exception{
        Map<String,String> params = (Map<String,String>) request.getAttribute("params");
        String userId = params.get("userId");
        String settleCardNo = params.get("settleCardNo");
        String settleProvince = params.get("settleProvince");
        String settleCity = params.get("settleCity");
        String settleBank = params.get("settleBank");
        String settleBranchBank = params.get("settleBranchBank");
        String settlePhone = params.get("settlePhone");

        Assert.hasLength(userId, "userId不能为空");
        Assert.hasLength(settleCardNo, "settleCardNo不能为空");
        //Assert.hasLength(settleProvince, "settleProvince不能为空");
        //Assert.hasLength(settleCity, "settleCity不能为空");
        Assert.hasLength(settleBank, "settleBank不能为空");
        //Assert.hasLength(settleBranchBank, "settleBranchBank不能为空");
        Assert.hasLength(settlePhone, "settlePhone不能为空");

        WalletUserInfo userInfo = new WalletUserInfo();
        userInfo.setId(Long.parseLong(userId));
        userInfo.setSettleCardNo(settleCardNo);
        userInfo.setSettleProvince(settleProvince);
        userInfo.setSettleCity(settleCity);
        userInfo.setSettleBank(settleBank);
        userInfo.setSettleBranchBank(settleBranchBank);
        userInfo.setSettlePhone(settlePhone);
        userInfo.setSettleCardZScan(qiniuyunService.uploadMultipartFile(settleCardZScan, FileType.图片.getValue()));
        userInfo.setSettleCardFScan(qiniuyunService.uploadMultipartFile(settleCardFScan,FileType.图片.getValue()));

        userCenterService.updateSettleCard(userInfo);
        renderJson(request, response, SysCode.SUCCESS, null);
    }

    /**
     * 修改手机号
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("updatePhoneApply")
    @ActionAnnotation(params={"userId","phone"})
    public void updatePhoneApply(HttpServletRequest request, HttpServletResponse response,
                                 @RequestParam("invoice")MultipartFile invoice,
                                 @RequestParam("handInvoice")MultipartFile handInvoice) throws Exception{
        Map<String,String> params = (Map<String,String>) request.getAttribute("params");
        String userId = params.get("userId");
        String phone = params.get("phone");

        Assert.hasLength(userId, "userId不能为空");
        Assert.hasLength(phone, "phone不能为空");

        WalletUpdatephoneApply updatephoneApply = new WalletUpdatephoneApply();
        updatephoneApply.setUserId(Long.parseLong(userId));
        updatephoneApply.setPhone(phone);
        updatephoneApply.setInvoice(qiniuyunService.uploadByest(invoice.getBytes(),null));
        updatephoneApply.setHandInvoice(qiniuyunService.uploadByest(handInvoice.getBytes(),null));
        updatephoneApply.setState("0");
        updatephoneApply.setCreateTime(new Date());

        userCenterService.updatePhoneApply(updatephoneApply);
        renderJson(request, response, SysCode.SUCCESS, null);
    }

    /**
     * 修改密码
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("updatePassword")
    @ActionAnnotation(params={"userId","oldPassword","newPassword"}, encrypt = true)
    public void updatePassword(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String,String> params = (Map<String,String>) request.getAttribute("params");
        String userId = params.get("userId");
        String oldPassword = params.get("oldPassword");
        String newPassword = params.get("newPassword");

        Assert.hasLength(userId, "userId不能为空");
        Assert.hasLength(oldPassword, "oldPassword不能为空");
        Assert.hasLength(newPassword, "newPassword不能为空");

        userCenterService.updatePassword(Long.parseLong(userId),oldPassword,newPassword);
        renderJson(request, response, SysCode.SUCCESS, null);
    }

    /**
     * 修改权限
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("updatePrivacy")
    @ActionAnnotation(params={"userId","type","value"}, encrypt = true)
    public void updatePrivacy(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String,String> params = (Map<String,String>) request.getAttribute("params");
        String userId = params.get("userId");
        String type = params.get("type");
        String value = params.get("value");

        Assert.hasLength(userId, "userId不能为空");
        Assert.hasLength(type, "type不能为空");
        Assert.hasLength(value, "value不能为空");

        userCenterService.updatePrivacy(Long.parseLong(userId),type,value);
        renderJson(request, response, SysCode.SUCCESS, null);
    }

    /**
     * 获取我的商户
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("getMyRecommonMerchant")
    @ActionAnnotation(params={"userId"})
    public void getMyRecommonMerchant(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String,String> params = (Map<String,String>) request.getAttribute("params");
        String userId = params.get("userId");
        Assert.hasLength(userId, "userId不能为空");

        Map<String,Object> result = userCenterService.getMyRecommonMerchant(Long.parseLong(userId));
        renderJson(request, response, SysCode.SUCCESS,result);
    }

    /**
     * 获取推荐商户数量
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("getRecommonMerchantCount")
    @ActionAnnotation(params={"userId"}, encrypt = true)
    public void getMyRecommonMerchant1(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String,String> params = (Map<String,String>) request.getAttribute("params");
        String userId = params.get("userId");
        Assert.hasLength(userId, "userId不能为空");

        Map<String,Object> result = userCenterService.getMyRecommonMerchant2(Long.parseLong(userId));
        renderJson(request, response, SysCode.SUCCESS,result);
    }

    /**
     * 获取推荐的商户
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("getRecommonMerchant")
    @ActionAnnotation(params={"userId","userType","userState"}, encrypt = true)
    public void getRecommonMerchant(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String,String> params = (Map<String,String>) request.getAttribute("params");
        String userId = params.get("userId");
        String userType = params.get("userType");
        String userState = params.get("userState");

        Assert.hasLength(userId, "userId不能为空");
        Assert.hasLength(userType, "userType不能为空");

        List<WalletUserInfo> list = userCenterService.getRecommonMerchant(Long.parseLong(userId), userType, userState);
        Map<Class<?>, String[]> includes = new HashMap<Class<?>, String[]>() {
            private static final long serialVersionUID = -5349178483472578926L;
            {
                put(WalletUserInfo.class, new String[] {"id","phone","realName","userType","userTypeName;","headImage","createTime"});
            }
        };
        renderJson(request, response, SysCode.SUCCESS, list, includes);
    }

    /**
     * 获取推荐商户分页数据
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("getRecommonMerchantPageDate")
    @ActionAnnotation(params={"userId","userState","pageNo"}, encrypt = true)
    public void getRecommonMerchant1(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String,String> params = (Map<String,String>) request.getAttribute("params");
        String userId = params.get("userId");
        String userState = params.get("userState");
        String pageNo = params.get("pageNo");

        Assert.hasLength(userId, "userId不能为空");
        Assert.hasLength(userState, "userType不能为空");
        Assert.hasLength(pageNo, "pageNo不能为空");

        List<WalletUserInfo> list = userCenterService.getRecommonMerchant1(Long.parseLong(userId), userState, Integer.parseInt(pageNo));
        Map<Class<?>, String[]> includes = new HashMap<Class<?>, String[]>() {
            private static final long serialVersionUID = -5349178483472578926L;
            {
                put(WalletUserInfo.class, new String[] {"id","phone","realName","userType","userTypeName;","headImage","createTime","state"});
            }
        };
        renderJson(request, response, SysCode.SUCCESS, list, includes);
    }

    /**
     * 获取我的账户
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("getMyAccount")
    @ActionAnnotation(params={"userId"}, encrypt = true)
    public void getMyAccount(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String,String> params = (Map<String,String>) request.getAttribute("params");
        String userId = params.get("userId");
        Assert.hasLength(userId, "userId不能为空");

        BigDecimal result = userCenterService.getMyAccount(Long.parseLong(userId));
        renderJson(request, response, SysCode.SUCCESS, result);
    }

    /**
     * 获取账号明细
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("getMyAccountDetail")
    @ActionAnnotation(params={"userId"}, encrypt = true)
    public void getMyAccountDetail(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String,String> params = (Map<String,String>) request.getAttribute("params");
        String userId = params.get("userId");
        Assert.hasLength(userId, "userId不能为空");

        List<WalletAccount> list = userCenterService.getMyAccountDetail(Long.parseLong(userId));
        renderJson(request, response, SysCode.SUCCESS, list);
    }

    /**
     * 我要提现
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("takeOutMoney")
    @ActionAnnotation(params={"accountId","money"}, encrypt = true)
    public void takeOutMoney(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String,String> params = (Map<String,String>) request.getAttribute("params");
        String accountId = params.get("accountId");
        String money = params.get("money");

        Assert.hasLength(accountId, "accountId不能为空");
        Assert.hasLength(money, "money不能为空");

        userCenterService.takeOutMoney(Long.parseLong(accountId),new BigDecimal(money));
        renderJson(request, response, SysCode.SUCCESS, null);
    }

    /**
     * 我的费率
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("getMyRate")
    @ActionAnnotation(params={"userType","payWay"}, encrypt = true)
    public void getMyRate(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String,String> params = (Map<String,String>) request.getAttribute("params");
        String userType = params.get("userType");
        String payWay = params.get("payWay");

        Assert.hasLength(userType, "userType不能为空");
        Assert.hasLength(payWay, "payWay不能为空");

        List<Map<String, Object>> list = userCenterService.getMyRate(userType, payWay);
        /*Map<Class<?>, String[]> includes = new HashMap<Class<?>, String[]>() {
            private static final long serialVersionUID = -5349178483472578926L;
            {
                put(WalletDeductRate.class, new String[] {"id","channelId","channelName","settleType","deductRate","poundage","limitMin","limitMax","dayMax"});
            }
        };*/
        renderJson(request, response, SysCode.SUCCESS, list);
    }

    /**
     * 获取消息记录
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("getMessageList")
    @ActionAnnotation(params={"userId","pageNo","type"}, encrypt = true)
    public void getMessageList(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String,String> params = (Map<String,String>) request.getAttribute("params");
        String userId = params.get("userId");
        String pageNo = params.get("pageNo");
        String type = params.get("type");

        Assert.hasLength(userId, "userId不能为空");
        Assert.hasLength(pageNo, "pageNo不能为空");
        Assert.hasLength(type, "type不能为空");

        List<CommonMessages> list = userCenterService.getMessageList(Long.parseLong(userId), Integer.parseInt(pageNo),type);
        renderJson(request, response, SysCode.SUCCESS, list);
    }

    /**
     * 获取中央文案库
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("getCopywriting")
    @ActionAnnotation(params={"type","pageNo"}, encrypt = true)
    public void getCopywriting(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String,String> params = (Map<String,String>) request.getAttribute("params");
        String type = params.get("type");
        String pageNo = params.get("pageNo");

        Assert.hasLength(type, "type不能为空");
        Assert.hasLength(pageNo, "pageNo不能为空");

        List<WalletCopywritingWithBLOBs> list = userCenterService.getCopywriting(type,Integer.parseInt(pageNo));
        Map<Class<?>, String[]> includes = new HashMap<Class<?>, String[]>() {
            private static final long serialVersionUID = -5349178483472578926L;
            {
                put(WalletCopywritingWithBLOBs.class, new String[] {"id","title","downCount","createTime","content","images"});
            }
        };
        renderJson(request, response, SysCode.SUCCESS, list, includes);
    }

    /**
     * 获取客服
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("getCustomerService")
    @ActionAnnotation(params={"type"}, encrypt = true)
    public void getCustomerService(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String,String> params = (Map<String,String>) request.getAttribute("params");
        String type = params.get("type");

        Assert.hasLength(type, "type不能为空");

        List<WalletCustomerServiceWithBLOBs> list = userCenterService.getCustomerService(type);
        renderJson(request, response, SysCode.SUCCESS, list);
    }

}
