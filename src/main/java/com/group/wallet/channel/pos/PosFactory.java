package com.group.wallet.channel.pos;

import com.group.core.exception.ServiceException;
import com.group.utils.SpringUtil;
import com.group.wallet.channel.ChannelFactory;
import com.group.wallet.channel.pos.lkl.impl.LklPosPayImpl;
import com.group.wallet.model.zzlm.ZzlmChannel;

/**
 * pos机刷卡通道工厂类，负责创建pos刷卡通道
 */
public class PosFactory implements ChannelFactory{

    @Override
    public PosPay getChannelPay(ZzlmChannel channel) {
        String channelNo = channel.getNumber();
        if("LKL".equals(channelNo)){
            return SpringUtil.getBean(LklPosPayImpl.class);
        }
        else {
            throw new ServiceException("2000", "渠道编码未配置路由");
        }
    }
}
