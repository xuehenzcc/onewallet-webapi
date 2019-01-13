package com.group.wallet.channel.payment.yspay.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.group.core.exception.ServiceException;
import com.group.utils.HttpClientUtils;
import com.group.wallet.channel.payment.PaymentPay;
import com.group.wallet.channel.payment.yspay.bean.YsPayOrderBean;
import com.group.wallet.channel.payment.yspay.config.YspayConfig;
import com.group.wallet.channel.payment.yspay.utils.SignUtils;
import com.group.wallet.mapper.CommonMessagesMapper;
import com.group.wallet.mapper.SysConfigMapper;
import com.group.wallet.model.CommonMessages;
import com.group.wallet.model.SysConfig;
import com.group.wallet.model.WalletChannel;
import com.qiniu.util.Json;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class YspayPaymentImpl implements PaymentPay {

    protected static Logger logger = LoggerFactory.getLogger(YspayPaymentImpl.class);

    @Override
    public void t0pay(HttpServletRequest request, WalletChannel channel, Map<String, Object> bizContent) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        YsPayOrderBean bean = new YsPayOrderBean();
        bean.setMethod("ysepay.df.single.quick.accept");
        bean.setPartner_id("weitanli");
        bean.setTimestamp(sdf.format(new Date()));
        bean.setCharset("utf-8");
        bean.setSign_type("RSA");
        bean.setNotify_url(channel.getNoticeUrl());
        bean.setVersion("3.0");
        bean.setBiz_content(JSON.toJSONString(bizContent));

        Map<String, String> sPara = buildRequestPara(request, getProperty(bean));

        String result = HttpClientUtils.sendPost(YspayConfig.YSEPAY_GATEWAY_URL_DS, sPara, null, "utf-8");
        JSONObject json = JSON.parseObject(result);
        JSONObject response = json.getJSONObject("ysepay_df_single_quick_accept_response");
        String code = response.getString("code");
        if(!"10000".equals(code)){
            String msg = response.getString("msg");
            String sub_msg = response.getString("sub_msg");
            throw new ServiceException("2000", "代付失败-"+msg+"["+sub_msg+"]");
        }
    }

    /**
     * 获取证书路径并且签名
     * @param request
     * @param sParaTemp
     * @return
     */
    private static Map<String, String> buildRequestPara(HttpServletRequest request, Map<String, String> sParaTemp) throws IOException{
        //除去数组中的空值和签名参数
        Map<String, String> sPara = SignUtils.paraFilter(sParaTemp);

        //私钥证书路径
        String calssPath =Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String partnerCert = calssPath+"cert/weitanli.pfx";

        //读取证书
        InputStream pfxCertFileInputStream = new FileInputStream(new File(partnerCert));

        String mysign = "";
        try {
            //遍历以及根据重新排序
            String signContent = SignUtils.getSignContent(sPara);

            mysign = SignUtils.rsaSign(signContent, sParaTemp.get("charset"), pfxCertFileInputStream);

        } catch (Exception e) {
            throw new RuntimeException("签名失败，请检查证书文件是否存在，密码是否正确");
        }

        sPara.put("sign", mysign);

        return sPara;
    }

    /**
     * 取对应的key和value
     * @param obj
     * @return
     */
    public static Map<String, String> getProperty(Object obj) {

        Map<String, String> map = null;
        try {
            Field fields[] = obj.getClass().getDeclaredFields();
            String[] name = new String[fields.length];
            Object[] value = new Object[fields.length];
            Field.setAccessible(fields, true);
            map = new HashMap<String, String>();
            for (int i = 0; i < name.length; i++) {
                name[i] = fields[i].getName();
                value[i] = fields[i].get(obj);

                if (value[i] == null ||name[i] == null || "serialVersionUID".equals(name[i])) {
                    continue;
                }

                map.put(name[i], String.valueOf(value[i]));
            }
        } catch (Exception e) {
            throw new RuntimeException("获取bean属性值异常:" + e.getMessage());
        }
        return map;
    }
}
