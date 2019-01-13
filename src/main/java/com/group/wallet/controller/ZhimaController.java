package com.group.wallet.controller;

import com.group.core.annotation.ActionAnnotation;
import com.group.core.commons.SysCode;
import com.group.core.controller.BaseController;
import com.group.wallet.service.ZhimaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 芝麻认证
 */
@Controller
@RequestMapping("zhima")
public class ZhimaController extends BaseController {

    @Autowired
    private ZhimaService zhimaService;

    /**
     * 芝麻认证初始化
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("certificationInitialize")
    @ActionAnnotation(params={"certName","certNo"}, encrypt = true)
    public void certificationInitialize(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String,String> params = (Map<String,String>) request.getAttribute("params");
        String certName = params.get("certName");
        String certNo = params.get("certNo");

        Assert.hasLength(certName, "certName不能为空");
        Assert.hasLength(certNo, "certNo不能为空");

        Map<String, Object> result = zhimaService.certificationInitialize(certName, certNo);
        renderJson(request, response, SysCode.SUCCESS, result);
    }
}
