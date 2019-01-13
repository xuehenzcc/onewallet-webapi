package com.group.wallet.channel;

import com.group.core.exception.ServiceException;
import com.group.utils.SpringUtil;
import com.group.wallet.channel.pos.lkl.impl.LklPosPayImpl;
import com.group.wallet.channel.quick.shenzhouPay.impl.ShenzhouQuickPayImpl;
import com.group.wallet.channel.quick.weishuaPay.impl.WeishuaQuickPayImpl;
import com.group.wallet.channel.quick.yibaoPay.impl.YibaoQuickPayImpl;
import com.group.wallet.channel.quick.zheyangPay.impl.ZheyangHFQuickPayImpl;
import com.group.wallet.channel.quick.zheyangPay.impl.ZheyangLDQuickPayImpl;
import com.group.wallet.channel.quick.zheyangPay.impl.ZheyangQuickPayImpl;
import com.group.wallet.channel.quick.zheyangPay.impl.ZheyangXEQuickPayImpl;
import com.group.wallet.model.WalletChannel;

/**
 * 通道工厂接口
 */
public interface ChannelFactory {

    /**
     * 获取通道
     * @param channel
     * @return
     */
    public ChannelPay getChannelPay(WalletChannel channel);

}
