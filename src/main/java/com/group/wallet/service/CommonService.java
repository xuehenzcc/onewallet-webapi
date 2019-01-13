package com.group.wallet.service;


import com.group.wallet.model.CommonAdvertising;
import com.group.wallet.model.ModularDesign;
import com.group.wallet.model.SysConfig;

import java.util.List;

public interface CommonService {

    /**
     * 获取参数值
     * @param key
     * @return
     */
    SysConfig getConfigValue(String key);

    /**
     * 获取服务器路径
     * @return
     */
    String getProjectUrl();

    /**
     * 获取模块
     * @param position
     * @return
     */
    List<ModularDesign> getModules(String position);

    /**
     * 发送验证码短信
     * @param tel
     * @return
     */
    String sendIdentifyCode(String tel) throws Exception;
    
}
