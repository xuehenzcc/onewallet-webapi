package com.group.wallet.service;

import com.group.wallet.model.WalletMerchant;

/**
 * 商家
 */
public interface MerchantService {

    /**
     * 商家认证
     * @param walletMerchant
     */
    void merchantIdentify(WalletMerchant walletMerchant);

}
