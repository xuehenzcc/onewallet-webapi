package com.group.wallet.controller;

import com.group.core.annotation.ActionAnnotation;
import com.group.core.commons.SysCode;
import com.group.core.controller.BaseController;
import com.group.core.exception.ServiceException;
import com.group.utils.CommonUtils;
import com.group.wallet.model.ModularDesign;
import com.group.wallet.model.SysConfig;
import com.group.wallet.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CommonController
 */
@Controller
@RequestMapping("common")
public class CommonController extends BaseController{

    @Autowired
    private CommonService commonService;


    /**
     * 获取系统参数
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("getConfigValue")
    @ActionAnnotation(params={"key"}, encrypt = true)
    public void getConfigValue(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String,String> params = (Map<String,String>) request.getAttribute("params");

        String key = params.get("key");
        Assert.hasLength(key, "key不能为空");

        SysConfig result = commonService.getConfigValue(key);
        Map<Class<?>, String[]> includes = new HashMap<Class<?>, String[]>() {
            private static final long serialVersionUID = -5349178483472578926L;
            {
                put(SysConfig.class, new String[] {"configid","configkey","configvalue","configdesc"});
            }
        };
        renderJson(request, response, SysCode.SUCCESS, result);
    }

    /**
     * 获取模块
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("getModules")
    @ActionAnnotation(params={"position"}, encrypt = true)
    public void getModules(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String,String> params = (Map<String,String>) request.getAttribute("params");

        String position = params.get("position");
        Assert.hasLength(position, "position不能为空");

        List<ModularDesign> list = commonService.getModules(position);
        renderJson(request, response, SysCode.SUCCESS, list);
    }

    /**
     * 发送短信
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("sendSms")
    @ActionAnnotation(params={"tel","type"}, encrypt = true)
    public void sendSms(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String, String> params = (Map<String, String>) request.getAttribute("params");
        String tel = params.get("tel");
        String type = params.get("type");

        Assert.hasLength(tel, "手机号不能为空");
        Assert.hasLength(type, "短信类型不能为空");

        String code = commonService.sendIdentifyCode(tel);
        renderJson(request, response, SysCode.SUCCESS, code);
    }

    /**
     * h5发送短信
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("h5SendSms")
    @ActionAnnotation(params={"tel","type","token"}, encrypt = false)
    public void h5SendSms(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String, String> params = (Map<String, String>) request.getAttribute("params");
        String tel = params.get("tel");
        String type = params.get("type");
        String token = params.get("token");

        Assert.hasLength(tel, "手机号不能为空");
        Assert.hasLength(type, "短信类型不能为空");
        Assert.hasLength(token, "token不能为空");

        HttpSession session = request.getSession();
        String token1 = (String) session.getValue("token");
        if(!token.equals(token1)){
            throw new ServiceException("2000","token验证失败");
        }

        String code = commonService.sendIdentifyCode(tel);
        renderJson(request, response, SysCode.SUCCESS, code);
    }

    /**
     * 获取token
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("getToken")
    @ActionAnnotation(params={"recommendId","sign"}, encrypt = false)
    public void getToken(HttpServletRequest request, HttpServletResponse response) throws Exception{
        /*Map<String, String> params = (Map<String, String>) request.getAttribute("params");
        String recommonId = params.get("recommendId");
        String sign = params.get("sign1");

        Assert.hasLength(recommonId, "recommonId不能为空");
        Assert.hasLength(sign, "sign不能为空");

        Map<String, String> params1 = new HashMap<>();
        params1.put("recommonId",recommonId);
        String appSecert = MyWebAppConfig.environment.getProperty("ape.apikey");
        String sign1 = SignUtils.singParam(params1, false, false, appSecert);
        if(!sign.equals(sign1)){
            //throw new ServiceException("2000","签名错误");
        }*/

        HttpSession session = request.getSession();
        String token = (String) session.getValue("token");
        if(token==null){
            token = CommonUtils.createRandom(true,32);
            session.putValue("token",token);
        }
        renderJson(request, response, SysCode.SUCCESS, token);
    }

}
