package com.group.wallet.service.impl;

import com.group.core.service.AliyunSmsService;
import com.group.utils.CommonUtils;
import com.group.wallet.mapper.ModularDesignMapper;
import com.group.wallet.mapper.SysConfigMapper;
import com.group.wallet.model.CommonAdvertising;
import com.group.wallet.model.ModularDesign;
import com.group.wallet.model.SysConfig;
import com.group.wallet.service.CommonService;
import com.group.wallet.service.EmaySmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommonServiceImpl implements CommonService {

    @Value("${project.name}")
    private String projectName;

    @Autowired
    private AliyunSmsService aliyunSmsService;
    @Autowired
    private EmaySmsService smsService;
    @Autowired
    private SysConfigMapper sysConfigMapper;
    @Autowired
    private ModularDesignMapper modularDesignMapper;

    @Override
    public SysConfig getConfigValue(String key) {
        SysConfig sysConfig = new SysConfig();
        sysConfig.setConfigkey(key);
        sysConfig = sysConfigMapper.selectOne(sysConfig);
        return sysConfig;
    }

    @Override
    public String getProjectUrl() {
        String key = "";
        if("至尊钱包".equals(projectName)){
            key = "onewallet_api_url";
        }
        if("移动收款宝".equals(projectName)){
            key = "ydskb_api_url";
        }

        SysConfig config = getConfigValue(key);
        return config.getConfigvalue();
    }

    @Override
    public List<ModularDesign> getModules(String position) {
        Example example = new Example(ModularDesign.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andLike("app", "%"+projectName+"%");
        criteria.andEqualTo("position", position);
        example.setOrderByClause("sort_order");

        List<ModularDesign> list = modularDesignMapper.selectByExample(example);
        return list;
    }

    @Override
    public String sendIdentifyCode(String tel) throws Exception{
        String code = CommonUtils.createRandom(true,4);
        Map<String,Object> param = new HashMap<>();
        param.put("code",code);
        smsService.sendSms(tel,"【"+projectName+"】尊敬的用户，您的验证码是 "+code);
        return code;
    }

}
