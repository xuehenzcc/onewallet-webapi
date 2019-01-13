package com.group.wallet.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.group.core.exception.ServiceException;
import com.group.core.service.QiniuyunService;
import com.group.utils.HttpClientUtils;
import com.group.wallet.mapper.WalletAuthentRecordsMapper;
import com.group.wallet.mapper.WalletUserInfoMapper;
import com.group.wallet.model.WalletAuthentRecords;
import com.group.wallet.model.WalletUserInfo;
import com.group.wallet.model.enums.AuthType;
import com.group.wallet.service.FaceIdService;
import com.group.wallet.util.HmacSha1Sign;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 版权：小月科技
 * 作者：dailing
 * 生成日期：2018/11/6 下午3:00
 * 描述：Face人脸识别
 */
@Service
public class FaceIdServiceImpl implements FaceIdService {

    @Autowired
    private WalletUserInfoMapper walletUserInfoMapper;
    @Autowired
    private WalletAuthentRecordsMapper walletAuthentRecordsMapper;
    @Autowired
    private QiniuyunService qiniuyunService;

    @Override
    public String getBizToken(String idcardName, String idcardNumber) throws Exception{
        Map<String, Object> params = new HashMap<>();
        params.put("sign", HmacSha1Sign.genSign(api_key, api_secret, System.currentTimeMillis()));
        params.put("sign_version", "hmac_sha1");
        params.put("liveness_type", "meglive");

        params.put("comparison_type", "1");
        params.put("idcard_name", idcardName);
        params.put("idcard_number", idcardNumber);

        String result = HttpClientUtils.sendPostCommon("https://api.megvii.com/faceid/v3/sdk/get_biz_token", params, null, "utf-8");
        JSONObject jsonObject = JSON.parseObject(result);
        String biz_token = jsonObject.getString("biz_token");
        return biz_token;
    }

    @Override
    public Map<String, Object> verify(Long userId, MultipartFile meglive_data, String bizToken) throws Exception {
        Map<String, Object> map = new HashMap<>();

        WalletUserInfo userInfo1 = walletUserInfoMapper.selectByPrimaryKey(userId);
        if(Integer.valueOf(1).equals(userInfo1.getIsAuth())){
            map.put("verify", true);
            map.put("count", 0);
            return map;
        }

        WalletAuthentRecords authentRecords = new WalletAuthentRecords();
        authentRecords.setType(AuthType.人脸认证.getValue());
        authentRecords.setUserId(userId);
        int count = walletAuthentRecordsMapper.selectCount(authentRecords);
        if(count>=3) {
            map.put("verify", false);
            map.put("count", count);
            return map;
        }

        Map<String, Object> params = new HashMap<>();
        params.put("sign", HmacSha1Sign.genSign(api_key, api_secret, System.currentTimeMillis()));
        params.put("sign_version", "hmac_sha1");

        params.put("biz_token", bizToken);
        params.put("meglive_data", meglive_data);

        String result = HttpClientUtils.sendPostCommon("https://api.megvii.com/faceid/v3/sdk/verify", params, null, "utf-8");
        if(StringUtils.isEmpty(result)){
            authentRecords.setIsPass("0");
            authentRecords.setCreateTime(new Date());
            walletAuthentRecordsMapper.insertSelective(authentRecords);

            map.put("verify", false);
            map.put("count", count+1);
            return map;
        }

        JSONObject jsonObject = JSON.parseObject(result);
        String result_code = jsonObject.getString("result_code");
        String result_message = jsonObject.getString("result_message");

        if("1000".equals(result_code)){
            JSONObject images = jsonObject.getJSONObject("images");
            String image_best = images.getString("image_best");
            String url = qiniuyunService.uploadByest(Base64.decodeBase64(image_best), null);

            WalletUserInfo userInfo = new WalletUserInfo();
            userInfo.setId(userId);
            userInfo.setIsAuth(1);//认证通过
            userInfo.setActivityImage(url);
            walletUserInfoMapper.updateByPrimaryKeySelective(userInfo);

            authentRecords.setIsPass("1");
            authentRecords.setActivityImage(result_message);
            authentRecords.setCreateTime(new Date());
            walletAuthentRecordsMapper.insertSelective(authentRecords);

            map.put("verify", true);
            map.put("count", count+1);
            return map;
        }
        else {
            authentRecords.setIsPass("0");
            authentRecords.setActivityImage(result_message);
            authentRecords.setCreateTime(new Date());
            walletAuthentRecordsMapper.insertSelective(authentRecords);

            map.put("verify", false);
            map.put("count", count+1);
            return map;
        }
    }
}
