package com.group.wallet.service.wal;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.group.core.service.impl.PushService;
import com.group.utils.DateUtil;
import com.group.wallet.mapper.CommonMessagesMapper;
import com.group.wallet.mapper.HomeMapper;
import com.group.wallet.mapper.WalletIncomeRecordsMapper;
import com.group.wallet.mapper.WalletUserInfoMapper;
import com.group.wallet.model.CommonMessages;
import com.group.wallet.model.WalletTradeRecords;
import com.group.wallet.model.WalletUserInfo;
import com.group.wallet.model.enums.IncomeRecordsState;
import com.group.wallet.model.enums.IncomeType;
import com.group.wallet.model.vo.Account;
import com.group.wallet.model.vo.Adress;
import com.group.wallet.model.vo.BusinessData;
import com.group.wallet.model.vo.ChannelVO;
import com.group.wallet.model.vo.ParamVO;
import com.group.wallet.model.vo.Pos;
import com.group.wallet.model.vo.PosLog;
import com.group.wallet.model.vo.Shop;
import com.group.wallet.model.vo.ShopOrder;
import com.group.wallet.model.zzlm.ZzlmIncomeRecords;
import com.group.wallet.service.SettleService;

@Service
public class HomeService {

	@Autowired
	private HomeMapper homeMapper;
	@Autowired
	private ImageService iMageService;
	@Autowired
	private SettleService settleService;
	@Autowired
	private WalletUserInfoMapper walletUserInfoMapper;
	@Autowired
    private WalletIncomeRecordsMapper walletIncomeRecordsMapper;
	@Autowired
    private CommonMessagesMapper commonMessagesMapper;
	@Autowired
    private PushService pushService;
	
	public List<WalletUserInfo> getUserInfoList(WalletUserInfo user){
		return homeMapper.getUserInfoList(user);
	}
	
	public List<Pos> getPosListByT(Pos pos){
		return homeMapper.getPosListByT(pos);
	}
	//添加地址
	@Transactional
	public Integer addAddress(Adress address){
		if("0".equals(address.getType())){//设置默认地址
			homeMapper.updateAddressByUser(address);
		}
		return homeMapper.addAddress(address);
	}
	public Integer deleteAddress(Adress address){
		return homeMapper.deleteAddress(address);
	}
	@Transactional
	public Integer updateAddress(Adress address){
		if("0".equals(address.getType())){//设置默认地址
			homeMapper.updateAddressByMode(address);
		}
		return homeMapper.updateAddress(address);
	}
	public Integer updateAddressByMode(Adress address){
		return homeMapper.updateAddressByMode(address);
	}
	public List<Adress> getAddress(Adress address){
		return homeMapper.getAddress(address);
	}
	public List<Shop> getShopList(Shop pos){
		List<Shop> shopList = homeMapper.getShopList(pos);
		for (Shop shop:shopList) {
			String image=shop.getImage();
			String content=shop.getContent();
			if(null!=image){
				System.out.println(image);
				List<String> images=iMageService.convertImageUrlHtml2(image);
				System.out.println(images.size());
				shop.setImages(images);
			}
			if(null!=content){
				System.out.println(content);
				String newcontent = iMageService.convertImageUrlHtml(content);
				System.out.println(newcontent);
				shop.setContent(newcontent);
			}
		}
		return shopList;
	}
	public List<ShopOrder> getShopOrderList(ShopOrder order){
		return homeMapper.getShopOrderList(order);
	}
	public Integer addShopOrder(ShopOrder order){
		return homeMapper.addShopOrder(order);
	}
	public Integer updateShopOrder(ShopOrder order){
		return homeMapper.updateShopOrder(order);
	}
	//支付成功通知
	public void updateOrderByPay(ShopOrder order){
		List<ShopOrder> orderList = homeMapper.getShopOrderList(order);
		String status=orderList.get(0).getState();
		if("1".equals(status)){//待支付状态
			order.setState("2");//支付状态
			order.setPayTime(new Date());
			order.setPayWay("alipay");
		}
		int result = homeMapper.updateShopOrder(order);
		System.out.println("支付成功，更新订单状态：0失败，1成功------------"+result);
	}
	
	public List<Pos> getPosList(Pos pos){
		return homeMapper.getPosList(pos);
	}
	
	@Transactional
	public int updatePos(Pos pos){
		int result = homeMapper.updatePos(pos);
		if(result>0){
			PosLog data=new PosLog();
			data.setSn(pos.getSn());
			data.setStatus("0");
			if("1".equals(pos.getAction())){//下发
				data.setUserId(pos.getActiveUserId());
				homeMapper.addPosLog(data);
			}else if("3".equals(pos.getAction())){//同意召回
				Pos p=new Pos();
				p.setSn(pos.getSn());
				List<Pos> ps=homeMapper.getPosList(p);
				if(ps.size()>0){
					data.setUserId(ps.get(0).getUserId());
					homeMapper.addPosLog(data);
				}else{
					return 0;
				}
			}else if("2".equals(pos.getAction())){//召回--发送系统消息
				Pos posVO=new Pos();
				posVO.setSn(pos.getSn());
				List<Pos> posList = homeMapper.getPosList(posVO);
				Long userId = posList.get(0).getActiveUserId();
				//通知对方，要召回机具。
				String content="您的编号为："+pos.getSn()+" 的机具正在被召回,请速速到被召回机具列表处理！！！";
				CommonMessages messages = new CommonMessages();
	            messages.setApp("至尊联盟");
	            messages.setUserId(userId);
	            messages.setType("1");//系统消息
	            messages.setTitle("机具召回");
	            messages.setContent(content);
	            messages.setCreateTime(new Date());
	            commonMessagesMapper.insertSelective(messages);
	            pushService.push(userId+"", content, "", null);//推送通知
			}
		}
		return result;
	}
	public List<BusinessData> getBusinessDataList(BusinessData data){
		return homeMapper.getBusinessDataList(data);
	}
	public List<BusinessData> getBusinessDataListByT(BusinessData data){
		return homeMapper.getBusinessDataListByT(data);
	}
	public List<Account> getImcomeList(Account data){
		return homeMapper.getImcomeList(data);
	}
	public List<Account> getImcomeList2(Account data){
		return homeMapper.getImcomeList2(data);
	}
	public List<Account> getImcomeList3(Account data){
		return homeMapper.getImcomeList3(data);
	}
	public Integer addPosLog(PosLog data){
		return homeMapper.addPosLog(data);
	}
	
	
	
	
	//计算pos是否达到刷满条件 (定时执行)
	@Transactional
	public void getCompPos() throws Exception{
		Pos pos=new Pos();
		pos.setStatus("0");//未激活
		pos.setType("3");//小POS
		List<Pos> activePosList=new ArrayList<Pos>();
		List<Pos> posList=homeMapper.getPosList(pos);
		for (Pos p:posList) {
			String sn = p.getSn();
			BigDecimal compAmt=p.getCompAmt();
			BigDecimal totalAmt=BigDecimal.ZERO;
			BusinessData data=new BusinessData();
			data.setSn(sn);
			List<BusinessData> businessList=homeMapper.getBusinessDataList(data);
			if(businessList!=null && businessList.size()>0){
				for (int i = 0; i < businessList.size(); i++) {
					data=businessList.get(i);
					BigDecimal amt=data.getAmt();
					totalAmt = totalAmt.add(amt);
				}
				if(compAmt.compareTo(totalAmt)<=0){//满足条件
					p.setStatus("1");
					p.setReach(1);
					p.setActiveTime(new Date());
					activePosList.add(p);//要激活的POS
				}
			}
		}
		//获取未返现(大POS,智能POS)
		List<Pos> POSList=new ArrayList<Pos>();
		Pos bigPos=new Pos();
		bigPos.setReach(0);
		List<Pos> bigPosList=homeMapper.getPosList(bigPos);
		for (int i = 0; i < bigPosList.size(); i++) {
			Pos p=bigPosList.get(i);
			if("3".equals(bigPosList.get(i).getType())){
				continue;
			}
			POSList.add(bigPosList.get(i));
			Date activeTime=p.getActiveTime();
			int limit=p.getLimitnum();//天（180天刷满8万）
//			Date endDate= DateUtil.addMonth(activeTime, limit);
			Date endDate= DateUtil.addDay(activeTime, limit);
			String sn=p.getSn();
			BigDecimal compAmt=p.getCompAmt();//达标金额
			BigDecimal totalAmt=BigDecimal.ZERO;
			
			BusinessData data=new BusinessData();
			data.setSn(sn);
			data.setEndTime(DateUtil.formatDate(endDate, "yyyy-MM-dd HH:mm:ss"));
			List<BusinessData> businessList=homeMapper.getBusinessDataList(data);
			if(businessList!=null && businessList.size()>0){
				for (int j = 0; j < businessList.size(); j++) {
					data=businessList.get(j);
					BigDecimal amt=data.getAmt();
					totalAmt = totalAmt.add(amt);
				}
				if(compAmt.compareTo(totalAmt)<=0){//满足条件
					p.setReach(1);
					activePosList.add(p);//要返现的大POS
				}else{//不满足
					if(endDate.getTime()<new Date().getTime()){//已经超过期限了
						p.setReach(2);//未达标
						activePosList.add(p);//要返现的大POS
					}
				}
			}
		}
		//更新数据
		if(activePosList.size()>0){
			for (int i = 0; i < activePosList.size(); i++) {
				updateReachData(activePosList.get(i));
			}
		}
	}
	
	//创建分润记录（待开发：余额变动）
	@Transactional
	public void updateReachData(Pos pos) throws Exception{
		String type=pos.getType();
		if("3".equals(type)){//小POS（返激活保证金+返现）
			updateCalculateResult(pos.getUserId(),IncomeType.激活返现, "激活返现",pos.getUserId(),pos.getBackAmt(),pos.getSn());
		}
		homeMapper.updatePos(pos);
	}
	
	/**
	 * 测试分润接口
	 */
	public void test(BusinessData business){
		
		WalletTradeRecords record=new WalletTradeRecords();
		record.setUserId(Long.valueOf(business.getUserId()));
		record.setOrderNo(business.getSn());
		record.setTradeTime(business.getBusinessTime());
//		record.setTradeMoney(business.getAmt());
		record.setRealTradeMoney(business.getAmt());
//		record.setRate(BigDecimal.valueOf(0.6));
		record.setSettleType("T0");
		record.setOrderNo(business.getSn());//sn
		
		String payID=business.getRemark();
		if("".equals(payID) || null==payID){
			List<ChannelVO> channels=homeMapper.getChannelList(null);
			if(channels.size()>0){
				record.setChannelId(channels.get(0).getId());//ZZLM
			}
		}else{
			record.setChannelId(Long.valueOf(payID));//ZZLM
		}
		settleService.calculateTradeProfit(record);
	}
	
	/**
	 * 上传交易数据，批量分润
	 */
	public void bachFenRun(String orderNumber){
		BusinessData bu=new BusinessData();
		bu.setRemark3(orderNumber);
		bu.setStatus("0");
		List<BusinessData> businessList=homeMapper.getBusinessDataList(bu);
		if(null != businessList && businessList.size()>0){
			for (BusinessData business:businessList) {
				Date bdate=business.getBusinessTime();
				PosLog posLog=new PosLog();
				List<PosLog> posLogList = homeMapper.getPosLogList(posLog);
				for (PosLog plog:posLogList) {
					if(plog.getSn().equals(business.getSn())){
						if("0".equals(plog.getStatus())){//当前用户
							plog.setEndTime(new Date());
						}
						if(bdate.getTime()>plog.getStartTime().getTime() && bdate.getTime()<plog.getEndTime().getTime()){//该用户消费的。
							business.setUserId(plog.getUserId()+"");
							break;
						}
					}
				}
				if(null !=business.getUserId() && !"".equals(business.getUserId())){
					test(business);//计算分润
					business.setStatus("1");//已经分润
				}
			}
			homeMapper.updateBusinessDataList(businessList);
			homeMapper.updateIncomeByPosType();//更新POS类型
			//检查是否有达标数据，有的话返还金额。
			try {
				getCompPos();
			} catch (Exception e) {
				System.out.println("Faile to update reach Data  更新达标数据失败！");
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 获取用户层级关系--分别进行激活返现
	 * @param incomeType 激活奖励上级("A8"),退还保证金("A10"), 购买pos机("B2")
	 * @param amt pos数量，1个奖励5元，5元，20元。
	 * @param sn 订单编号
	 * @param userId 用户ID
	 * @throws Exception 
	 */
	@Transactional
	public void getUserRelation(Long userId,BigDecimal amt,String sn) throws Exception{
//		amt=amt.divide(BigDecimal.valueOf(3),2);//均分三份
//		A8,178659,1,Z19022319101488
		ParamVO param=new ParamVO();
		param.setNumber("101");
		List<ParamVO> paramList=homeMapper.getParam(param);
		String paramstr=paramList.get(0).getParam();
		String [] params=paramstr.split(",");
		BigDecimal zhitui=BigDecimal.valueOf(Long.valueOf(params[0])).multiply(amt);//直接 推荐 人 
		BigDecimal jiantui=BigDecimal.valueOf(Long.valueOf(params[1])).multiply(amt);//间接推荐人 
		BigDecimal hehuoren=BigDecimal.valueOf(Long.valueOf(params[2])).multiply(amt);//最近 超级合伙人
		WalletUserInfo userInfo = walletUserInfoMapper.selectByPrimaryKey(userId);
		int num=0;
		while(true) {
			num++;
			Long uid=userInfo.getRecommonId();//推荐人ID
			if(null==uid){
				break;
			}
			userInfo = walletUserInfoMapper.selectByPrimaryKey(uid);
			String userType=userInfo.getUserType();
			if(null==userType || "".equals(userType)){
				break;
			}else{
				if(num==1){
					if("F".equals(userType)){//超级合伙人
						updateCalculateResult(userId,IncomeType.激活返现, "盟友激活返现", uid, hehuoren, sn);//下级返现本人
						break;
					}else{//直推
						updateCalculateResult(userId,IncomeType.激活返现, "盟友激活返现", uid, zhitui, sn);//直推
					}
				}else{
					if("F".equals(userType)){//超级合伙人
						//TODO
						updateCalculateResult(userId,IncomeType.激活返现, "盟友激活返现", uid, hehuoren, sn);//超级合伙人
						break;
					}
					if(num==2){//间接推荐人 
						updateCalculateResult(userId,IncomeType.激活返现, "盟友激活返现", uid, jiantui, sn);//间接推荐人 
					}
				}
			}
		}
	}
	
	/**
	 * 更新用户-分润余额（激活返现）
	 * @param incomeType 激活返现("A8"),盟友激活返现("A9"),退还保证金("A10"), 购买pos机("B2")
	 * @param account
	 * @param incomeName
	 * @throws Exception 
	 */
	@Transactional
	public void updateCalculateResult(Long fromUserId,IncomeType incomeType,String incomeName,Long userId,BigDecimal amt,String sn) throws Exception{
		WalletUserInfo userInfo = walletUserInfoMapper.selectByPrimaryKey(userId);
		BigDecimal profitBalance = userInfo.getProfitBalance2()==null?BigDecimal.ZERO:userInfo.getProfitBalance2();//用户剩余分润金额
        BigDecimal profitBalance2 = null;
        profitBalance2 = profitBalance.add(amt);
        
		WalletUserInfo userInfo1=new WalletUserInfo();
		userInfo1.setId(userId);
		userInfo1.setProfitBalance2(profitBalance2);
		updateUser(userInfo1);//更新统一入口
        //添加分润记录
        ZzlmIncomeRecords incomeRecords = new ZzlmIncomeRecords();
        incomeRecords.setUserId(userInfo.getId());
        incomeRecords.setOrderNum(sn);
        incomeRecords.setType(incomeType.getValue());
        incomeRecords.setAmount(amt);//分润/提现金额
        incomeRecords.setFromUserId(fromUserId);//分润来源用户ID 
        incomeRecords.setPristineAmount(profitBalance);//原始金额
        incomeRecords.setSurplusAmount(profitBalance2);//剩余金额
        incomeRecords.setState(IncomeRecordsState.已到账.getValue());
        incomeRecords.setDescp(incomeName);
//        incomeRecords.setCreateTime(new Date());
//        walletIncomeRecordsMapper.insertSelective(incomeRecords);
        homeMapper.addIncome(incomeRecords);
	}
	//悲观锁——更新余额（分润）
	public void updateUser(WalletUserInfo userInfo) throws Exception{
		Long userId=userInfo.getId();
		synchronized (userId) {
			//更新分润余额
	        walletUserInfoMapper.updateByPrimaryKeySelective(userInfo);
		}
	}
}
