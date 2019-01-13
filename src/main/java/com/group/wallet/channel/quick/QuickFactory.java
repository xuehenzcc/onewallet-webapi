package com.group.wallet.channel.quick;

import com.group.core.exception.ServiceException;
import com.group.utils.SpringUtil;
import com.group.wallet.channel.ChannelFactory;
import com.group.wallet.channel.quick.fuhtPay.impl.FuhtQuickPayImpl;
import com.group.wallet.channel.quick.lingchuangPay.impl.LingchuangQuickPayImpl;
import com.group.wallet.channel.quick.sifangPay.impl.SifangQuickPayImpl;
import com.group.wallet.channel.quick.weishuaPay.impl.WeishuaQuickPayImpl;
import com.group.wallet.channel.quick.yibaoPay.impl.YibaoQuickPayImpl;
import com.group.wallet.channel.quick.youfuPay.impl.YoufuQuickPayImpl;
import com.group.wallet.channel.quick.youfuPay.impl.YoufuTm2QuickPayImpl;
import com.group.wallet.channel.quick.youfuPay.impl.YoufuTm4QuickPayImpl;
import com.group.wallet.channel.quick.youfuPay.impl.YoufuTmQuickPayImpl;
import com.group.wallet.channel.quick.ysbPay.impl.YsbQuickPayImpl;
import com.group.wallet.channel.quick.zheyangPay.impl.*;
import com.group.wallet.model.WalletChannel;
import com.group.wallet.channel.quick.shenzhouPay.impl.ShenzhouQuickPayImpl;

/**
 * 快捷支付工厂类，负责创建快捷通道
 */
public class QuickFactory implements ChannelFactory {

    @Override
    public QuickPay getChannelPay(WalletChannel channel){
        String channelNo = channel.getNumber();

        if("SZZF".equals(channelNo)){
            return SpringUtil.getBean(ShenzhouQuickPayImpl.class);
        }
        else if("ZYZF".equals(channelNo)){
            return SpringUtil.getBean(ZheyangQuickPayImpl.class);
        }
        else if("ZYZF-LD".equals(channelNo)){
            return SpringUtil.getBean(ZheyangLDQuickPayImpl.class);
        }
        else if("ZYZF-HF".equals(channelNo)){
            return SpringUtil.getBean(ZheyangHFQuickPayImpl.class);
        }
        else if("ZYZF-XE".equals(channelNo)){
            return SpringUtil.getBean(ZheyangXEQuickPayImpl.class);
        }
        else if("ZYZF-HZXE1".equals(channelNo)){
            return SpringUtil.getBean(ZheyangHZXE1QuickPayImpl.class);
        }
        else if("ZYZF-KJT".equals(channelNo)){
            return SpringUtil.getBean(ZheyangKJTQuickPayImpl.class);
        }
        else if("ZYZF-HFHZ".equals(channelNo)){
            return SpringUtil.getBean(ZheyangHFHZQuickPayImpl.class);
        }
        else if("ZYZF-YQ".equals(channelNo)){
            return SpringUtil.getBean(ZheyangYqQuickPayImpl.class);
        }
        else if("ZYZF-XE7".equals(channelNo)){
            return SpringUtil.getBean(ZheyangXE7QuickPayImpl.class);
        }
        else if("ZYZF-SHYB".equals(channelNo)){
            return SpringUtil.getBean(ZheyangSHYBQuickPayImpl.class);
        }
        else if("ZYZF-KB5".equals(channelNo)){
            return SpringUtil.getBean(ZheyangKB5QuickPayImpl.class);
        }
        else if("WS".equals(channelNo)){
            return SpringUtil.getBean(WeishuaQuickPayImpl.class);
        }
        else if("YIBAO".equals(channelNo)){
            return SpringUtil.getBean(YibaoQuickPayImpl.class);
        }
        else if("YOUFU".equals(channelNo)){
            return SpringUtil.getBean(YoufuQuickPayImpl.class);
        }
        else if("YOUFUTM".equals(channelNo)){
            return SpringUtil.getBean(YoufuTmQuickPayImpl.class);
        }
        else if("YOUFUTM2".equals(channelNo)){
            return SpringUtil.getBean(YoufuTm2QuickPayImpl.class);
        }
        else if("YOUFUTM4".equals(channelNo)){
            return SpringUtil.getBean(YoufuTm4QuickPayImpl.class);
        }
        else if("YINSB".equals(channelNo)){
            return SpringUtil.getBean(YsbQuickPayImpl.class);
        }
        else if("FUHT".equals(channelNo)){
            return SpringUtil.getBean(FuhtQuickPayImpl.class);
        }
        else if("LINGCHUANG".equals(channelNo)){
            return SpringUtil.getBean(LingchuangQuickPayImpl.class);
        }
        else if("SIFANG".equals(channelNo)){
            return SpringUtil.getBean(SifangQuickPayImpl.class);
        }
        else {
            throw new ServiceException("2000", "渠道编码未配置路由");
        }
    }
}
