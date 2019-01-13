/**
 * 版权：zcc
 * 作者：c0z00k8
 * @data 2018年8月17日
 */
package com.group.wallet.mapper;

import com.group.wallet.model.WalletChannel;
import com.group.wallet.model.WalletIncomeRecords;
import com.group.wallet.model.WalletUserInfo;
import com.group.wallet.model.vo.*;

import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author c0z00k8
 *
 */
@Component
public interface HomeMapper {
	
	//添加地址
	public int addAddress(Adress address);
	public int deleteAddress(Adress address);//删除地址
	public int updateAddress(Adress address);//修改地址
	public int updateAddressByMode(Adress address);//设置默认地址
	public int updateAddressByUser(Adress address);//设置默认地址
	public List<Adress> getAddress(Adress address);//获取地址
	
	//获取shop
	public List<Shop> getShopList(Shop pos);
	//获取订单列表
	public List<ShopOrder> getShopOrderList(ShopOrder order);
	public int addShopOrder(ShopOrder order);//创建订单
	public int updateShopOrder(ShopOrder order);//更新订单
	
	//获取POS
	public List<Pos> getPosList(Pos pos);
	public int updatePos(Pos pos);
	
	public int updateBusinessDataList(List<BusinessData> data);//更新分润情况
	public int updateIncomeByPosType();//更新POS类型（分润收益表）
	public List<BusinessData> getBusinessDataList(BusinessData data);
	public List<BusinessData> getBusinessDataListByT(BusinessData data);//团队交易额
	public List<Account> getImcomeList(Account data);
	public List<Account> getImcomeList2(Account data);
	public List<Account> getImcomeList3(Account data);
	public int addPosLog(PosLog order);//创建POSLog
	
	//添加分润
	public int addAccount(Account account);
	public int addAccountList(List<Account> account);
	public int addIncome(WalletIncomeRecords inCome);
	public int updatePosList(List<Pos> posList);
	
	//获取用户信息
	public List<WalletUserInfo> getUserInfoList(WalletUserInfo user);
	public List<Pos> getPosListByT(Pos pos);//团队POS
	public List<PosLog> getPosLogList(PosLog pos);//POS所属列表
	public List<ChannelVO> getChannelList(ChannelVO channel);//获取通道
	
}
