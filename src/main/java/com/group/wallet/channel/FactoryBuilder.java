package com.group.wallet.channel;

import com.group.core.exception.ServiceException;
import com.group.wallet.channel.pos.PosFactory;
import com.group.wallet.channel.quick.QuickFactory;
import com.group.wallet.model.enums.ChannelType;

public class FactoryBuilder {

    public static ChannelFactory build(String channelType){
        if(ChannelType.快捷渠道.getValue().equals(channelType)){
            return new QuickFactory();
        }
        else if(ChannelType.H5快捷渠道.getValue().equals(channelType)){
            return new QuickFactory();
        }
        else if(ChannelType.二维码渠道.getValue().equals(channelType)){
            return new QuickFactory();
        }
        else if(ChannelType.代付渠道.getValue().equals(channelType)){
            return new QuickFactory();
        }
        else if(ChannelType.刷卡渠道.getValue().equals(channelType)){
            return new PosFactory();
        }
        else {
            throw new ServiceException("2000", "通道类型未配置路由");
        }
    }

}
