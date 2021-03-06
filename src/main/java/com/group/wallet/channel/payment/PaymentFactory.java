package com.group.wallet.channel.payment;

import com.group.core.exception.ServiceException;
import com.group.utils.SpringUtil;
import com.group.wallet.channel.payment.yspay.impl.YspayPaymentImpl;
import com.group.wallet.model.zzlm.ZzlmChannel;

public class PaymentFactory {

    public static PaymentPay newPaymentPay(ZzlmChannel channel){
        String channelNo = channel.getNumber();

        if("YINSHENG".equals(channelNo)){
            return SpringUtil.getBean(YspayPaymentImpl.class);
        }
        else if("".equals(channelNo)){

        }
        else {
            throw new ServiceException("2000", "渠道编码未配置路由");
        }

        return null;
    }
}
