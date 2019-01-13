package com.group.wallet.controller;

import com.group.core.annotation.ActionAnnotation;
import com.group.core.commons.SysCode;
import com.group.core.controller.BaseController;
import com.group.core.service.QiniuyunService;
import com.group.wallet.model.WalletMerchant;
import com.group.wallet.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 商家中心
 */
@Controller
@RequestMapping("merchant")
public class MerchantController extends BaseController {

    @Autowired
    private MerchantService merchantService;
    @Autowired
    private QiniuyunService qiniuyunService;

    /**
     * 商家认证
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("merchantIdentify")
    @ActionAnnotation(params={"userId","merName","address"}, encrypt = true)
    public void merchantIdentify(HttpServletRequest request, HttpServletResponse response,
                                 @RequestParam("businessLicense")MultipartFile businessLicense,
                                 @RequestParam("doorIamge")MultipartFile doorIamge,
                                 @RequestParam("cashierImage")MultipartFile cashierImage,
                                 @RequestParam("storeImage")MultipartFile storeImage) throws Exception{
        Map<String,String> params = (Map<String,String>) request.getAttribute("params");
        String userId = params.get("userId");
        String merName = params.get("merName");
        String address = params.get("address");

        Assert.hasLength(userId, "userId不能为空");
        Assert.hasLength(merName, "merName不能为空");
        Assert.hasLength(address, "address不能为空");

        WalletMerchant merchant = new WalletMerchant();
        merchant.setUserId(Long.parseLong(userId));
        merchant.setMerName(merName);
        merchant.setAddress(address);
        merchant.setBusinessLicense(qiniuyunService.uploadByest(businessLicense.getBytes(),null));
        merchant.setDoorIamge(qiniuyunService.uploadByest(doorIamge.getBytes(),null));
        merchant.setCashierImage(qiniuyunService.uploadByest(cashierImage.getBytes(),null));
        merchant.setStoreImage(qiniuyunService.uploadByest(storeImage.getBytes(),null));

        merchantService.merchantIdentify(merchant);
        renderJson(request, response, SysCode.SUCCESS, null);
    }

    /**
     * 添加门店
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("addStore")
    @ActionAnnotation(params={"key"})
    public void addStore(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String,String> params = (Map<String,String>) request.getAttribute("params");
        String key = params.get("key");
        Assert.hasLength(key, "key不能为空");


        renderJson(request, response, SysCode.SUCCESS, null);
    }


}
