package com.group.wallet.controller;

import com.group.core.annotation.ActionAnnotation;
import com.group.core.commons.SysCode;
import com.group.core.controller.BaseController;
import com.group.wallet.service.FaceIdService;
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
 * 版权：小月科技
 * 作者：dailing
 * 生成日期：2018/11/6 下午2:56
 * 描述：face人脸识别
 */
@Controller
@RequestMapping("faceId")
public class FaceIdController extends BaseController {

    @Autowired
    private FaceIdService faceIdService;

    /**
     * 获取biz_token
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("getBizToken")
    @ActionAnnotation(params={"userId","idcardNumber","idcardName"})
    public void getBizToken(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String,String> params = (Map<String,String>) request.getAttribute("params");
        String userId = params.get("userId");
        String idcardNumber = params.get("idcardNumber");
        String idcardName = params.get("idcardName");

        Assert.hasLength(userId, "userId不能为空");
        Assert.hasLength(idcardNumber, "idcardNumber不能为空");
        Assert.hasLength(idcardName, "idcardName不能为空");

        String result = faceIdService.getBizToken(idcardName, idcardNumber);
        renderJson(request, response, SysCode.SUCCESS, result);
    }

    /**
     * 此接口提供基于人脸比对的身份核实功能
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("verify")
    @ActionAnnotation(params={"userId","bizToken"}, encrypt = true)
    public void verify(HttpServletRequest request, HttpServletResponse response,
                       @RequestParam("meglive_data") MultipartFile meglive_data) throws Exception{
        Map<String,String> params = (Map<String,String>) request.getAttribute("params");
        String userId = params.get("userId");
        String bizToken = params.get("bizToken");

        String originalFilename = meglive_data.getOriginalFilename();
        byte[] bytes = meglive_data.getBytes();




        Assert.hasLength(userId, "userId不能为空");
        Assert.hasLength(bizToken, "bizToken不能为空");

        Map<String, Object> result = faceIdService.verify(Long.valueOf(userId), meglive_data, bizToken);
        renderJson(request, response, SysCode.SUCCESS, result);
    }

}
