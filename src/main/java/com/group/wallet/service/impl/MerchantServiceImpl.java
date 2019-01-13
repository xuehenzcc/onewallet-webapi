package com.group.wallet.service.impl;

import com.group.core.exception.ServiceException;
import com.group.wallet.mapper.WalletMerchantMapper;
import com.group.wallet.mapper.WalletUserInfoMapper;
import com.group.wallet.model.WalletMerchant;
import com.group.wallet.model.WalletUserInfo;
import com.group.wallet.model.enums.MerchantType;
import com.group.wallet.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.rowset.serial.SerialException;

@Service
public class MerchantServiceImpl implements MerchantService {

    @Autowired
    private WalletMerchantMapper walletMerchantMapper;
    @Autowired
    private WalletUserInfoMapper walletUserInfoMapper;

    @Override
    public void merchantIdentify(WalletMerchant walletMerchant) {
        Long userId = walletMerchant.getUserId();
        WalletUserInfo userInfo = walletUserInfoMapper.selectByPrimaryKey(userId);
        if(MerchantType.商家.getValue().equals(userInfo.getMerchantType())){
            throw new ServiceException("2000","该用户已经是商家");
        }

        WalletMerchant merchant = new WalletMerchant();
        merchant.setUserId(userId);
        merchant = walletMerchantMapper.selectOne(merchant);

        if(merchant==null){
            walletMerchantMapper.insertSelective(walletMerchant);
        }else {
            String state = merchant.getState();
            if("1".equals(state)){
                throw new ServiceException("2000","该商户已经审核通过");
            }else {
                walletMerchant.setId(merchant.getId());
                walletMerchant.setState("0");
                walletMerchantMapper.updateByPrimaryKeySelective(walletMerchant);
            }
        }
    }
}
