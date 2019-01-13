package com.group.wallet.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.group.core.commons.SysCode;
import com.group.core.controller.BaseController;
import com.group.utils.DateUtil;
import com.group.wallet.model.WalletUserInfo;
import com.group.wallet.model.vo.Account;
import com.group.wallet.model.vo.Adress;
import com.group.wallet.model.vo.BusinessData;
import com.group.wallet.model.vo.Pos;
import com.group.wallet.model.vo.Shop;
import com.group.wallet.model.vo.ShopOrder;
import com.group.wallet.model.vo.TotalPosVO;
import com.group.wallet.service.wal.HomeService;

@Controller
@RequestMapping("/home")
public class HomeController extends BaseController {

    @Autowired
    private HomeService homeService;

    /**
     * 准备参数
     * @param request
     * @param method
     * @param paramKey
     * @return
     */
    protected Map<String, String> parseParams(HttpServletRequest request, String method, String[] paramKey) {
        logger.info("请求接口：" + method );
        Map<String, String> params = new HashMap<String, String>();
        for (int i = 0; i < paramKey.length; i++) {
            String param = request.getParameter(paramKey[i]);
            if (param != null) {
                params.put(paramKey[i], param);
                logger.info("【text-- the key:" + paramKey[i] + " ,value :" + param + "】");
            } else {
                logger.info("【warn-- the key:" + paramKey[i] + " is empty or is null!】");
            }
        }
        return params;
    }

    @RequestMapping("/test")
    public void test(HttpServletRequest request,HttpServletResponse response){
//    	homeService.test();
    	try {
			homeService.getCompPos();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//    	homeService.bachFenRun("ZZLM19010418590255");
    }
    
    //添加地址
    @RequestMapping("/addAddress")
    public void addAddress(HttpServletRequest request,HttpServletResponse response){

        String[] paramKey = {"name","telephone","city","address","userId","type"};
        Map<String, String> params = parseParams(request, "addAddress", paramKey);
        String userId = params.get("userId");
        String name = params.get("name");
        String telephone = params.get("telephone");
        String city = params.get("city");
        String address = params.get("address");
        String type = params.get("type");
        if(StringUtils.isBlank(userId) || StringUtils.isBlank(name) || StringUtils.isBlank(telephone)
                || StringUtils.isBlank(city) || StringUtils.isBlank(address)){//userID不能为空
            renderJson(request, response, SysCode.PARAM_IS_ERROR, null);
            return;
        }
        Adress addressVO=new Adress();
        addressVO.setAddress(address);
        addressVO.setCity(city);
        addressVO.setName(name);
        addressVO.setTelephone(telephone);
        if("0".equals(type)){
        	addressVO.setType("0");//默认地址
        }else{
        	addressVO.setType("1");//非默认地址
        }
        addressVO.setUserId(new Long(userId));

        try {
            Integer result = homeService.addAddress(addressVO);
            renderJson(request, response, SysCode.SUCCESS, result);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("`````method``````addAddress()`````"+e.getMessage());
            renderJson(request, response, SysCode.SYS_ERR, e.getMessage());
        }
    }
    //修改地址
    @RequestMapping("/updateAddress")
    public void updateAddress(HttpServletRequest request,HttpServletResponse response){

        String[] paramKey = {"name","telephone","city","address","id","type"};
        Map<String, String> params = parseParams(request, "updateAddress", paramKey);
        String id = params.get("id");
        String name = params.get("name");
        String telephone = params.get("telephone");
        String city = params.get("city");
        String address = params.get("address");
        String type = params.get("type");
        if(StringUtils.isBlank(id)){//ID不能为空
            renderJson(request, response, SysCode.PARAM_IS_ERROR, null);
            return;
        }
        
        Adress addressVO=new Adress();
        addressVO.setId(Long.valueOf(id));
        List<Adress> addressList=homeService.getAddress(addressVO);
        if(null==addressList || addressList.size()<=0){
        	renderJson(request, response, SysCode.PARAM_IS_ERROR, "地址ID无效");
            return;
        }
        addressVO.setAddress(address);
        addressVO.setCity(city);
        addressVO.setName(name);
        addressVO.setTelephone(telephone);
        addressVO.setType(type);
        addressVO.setUserId(addressList.get(0).getUserId());
        try {
            Integer result = homeService.updateAddress(addressVO);
            renderJson(request, response, SysCode.SUCCESS, result);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("`````method``````updateAddress()`````"+e.getMessage());
            renderJson(request, response, SysCode.SYS_ERR, e.getMessage());
        }
    }
    //设置默认地址
    @RequestMapping("/updateAddressByMode")
    public void updateAddressByMode(HttpServletRequest request,HttpServletResponse response){

        String[] paramKey = {"id","userId"};
        Map<String, String> params = parseParams(request, "updateAddressByMode", paramKey);
        String id = params.get("id");
        String userId = params.get("userId");
        if(StringUtils.isBlank(id) || StringUtils.isBlank(userId)){//ID不能为空
            renderJson(request, response, SysCode.PARAM_IS_ERROR, null);
            return;
        }
        Adress addressVO=new Adress();
        addressVO.setId(Long.valueOf(id));
        addressVO.setUserId(Long.valueOf(userId));

        try {
            Integer result = homeService.updateAddressByMode(addressVO);
            renderJson(request, response, SysCode.SUCCESS, result);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("`````method``````updateAddressByMode()`````"+e.getMessage());
            renderJson(request, response, SysCode.SYS_ERR, e.getMessage());
        }
    }
    //删除地址
    @RequestMapping("/deleteAddress")
    public void deleteAddress(HttpServletRequest request,HttpServletResponse response){

        String[] paramKey = {"id"};
        Map<String, String> params = parseParams(request, "deleteAddress", paramKey);
        String id = params.get("id");
        if(StringUtils.isBlank(id)){//ID不能为空
            renderJson(request, response, SysCode.PARAM_IS_ERROR, null);
            return;
        }
        Adress addressVO=new Adress();
        addressVO.setId(Long.valueOf(id));

        try {
            Integer result = homeService.deleteAddress(addressVO);
            renderJson(request, response, SysCode.SUCCESS, result);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("`````method``````deleteAddress()`````"+e.getMessage());
            renderJson(request, response, SysCode.SYS_ERR, e.getMessage());
        }
    }
    //获取地址
    @RequestMapping("/getAddress")
    public void getAddress(HttpServletRequest request,HttpServletResponse response){

        String[] paramKey = {"userId"};
        Map<String, String> params = parseParams(request, "getAddress", paramKey);
        String userId = params.get("userId");
        if(StringUtils.isBlank(userId)){//userID不能为空
            renderJson(request, response, SysCode.PARAM_IS_ERROR, null);
            return;
        }
        Adress addressVO=new Adress();
        addressVO.setUserId(Long.valueOf(userId));

        try {
            List<Adress> result = homeService.getAddress(addressVO);
            renderJson(request, response, SysCode.SUCCESS, result);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("`````method``````getAddress()`````"+e.getMessage());
            renderJson(request, response, SysCode.SYS_ERR, e.getMessage());
        }
    }
    //获取POS机器列表
    //排序功能：待开发
    @RequestMapping("/getShopList")
    public void getShopList(HttpServletRequest request,HttpServletResponse response){

        String[] paramKey = {"type"};
        Map<String, String> params = parseParams(request, "getShopList", paramKey);
        String type = params.get("type");
        if(StringUtils.isBlank(type)){//type不能为空
            renderJson(request, response, SysCode.PARAM_IS_ERROR, null);
            return;
        }
        Shop posVO=new Shop();
        posVO.setType(type);//机器类型（0展业,1大pos，2智能pos,3小pos）

        try {
            List<Shop> result = homeService.getShopList(posVO);
            renderJson(request, response, SysCode.SUCCESS, result);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("`````method``````getShopList()`````"+e.getMessage());
            renderJson(request, response, SysCode.SYS_ERR, e.getMessage());
        }
    }
    //获取商品详情
    @RequestMapping("/getShop")
    public void getShop(HttpServletRequest request,HttpServletResponse response){

        String[] paramKey = {"id"};
        Map<String, String> params = parseParams(request, "getShop", paramKey);
        String id = params.get("id");
        if(StringUtils.isBlank(id)){//id不能为空
            renderJson(request, response, SysCode.PARAM_IS_ERROR, null);
            return;
        }
        Shop posVO=new Shop();
        posVO.setId(Long.valueOf(id));//id

        try {
            List<Shop> result = homeService.getShopList(posVO);
            if(result.size()>0){
                renderJson(request, response, SysCode.SUCCESS, result.get(0));//商品详情
            }else{
                renderJson(request, response, SysCode.SUCCESS, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("`````method``````getShop()`````"+e.getMessage());
            renderJson(request, response, SysCode.SYS_ERR, e.getMessage());
        }
    }
    //获取订单列表
    @RequestMapping("/getShopOrderList")
    public void getShopOrderList(HttpServletRequest request,HttpServletResponse response){

        String[] paramKey = {"status","userId"};
        Map<String, String> params = parseParams(request, "getShopOrderList", paramKey);
        String status = params.get("status");
        String userId = params.get("userId");
        if(StringUtils.isBlank(userId)){//userId不能为空
            renderJson(request, response, SysCode.PARAM_IS_ERROR, null);
            return;
        }
        ShopOrder orderVO=new ShopOrder();
        orderVO.setState(status);//订单状态(1待支付，2待发货，3待收货，4已完成)
        orderVO.setUserId(Long.valueOf(userId));

        try {
            List<ShopOrder> result = homeService.getShopOrderList(orderVO);
            renderJson(request, response, SysCode.SUCCESS, result);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("`````method``````getShopOrderList()`````"+e.getMessage());
            renderJson(request, response, SysCode.SYS_ERR, e.getMessage());
        }
    }
    //获取订单详情
    @RequestMapping("/getShopOrder")
    public void getShopOrder(HttpServletRequest request,HttpServletResponse response){

        String[] paramKey = {"orderNum","id"};
        Map<String, String> params = parseParams(request, "getShopOrder", paramKey);
        String orderNum = params.get("orderNum");
        String id = params.get("id");
        if(StringUtils.isBlank(id) && StringUtils.isBlank(orderNum)){//id不能为空
            renderJson(request, response, SysCode.PARAM_IS_ERROR, null);
            return;
        }
        ShopOrder orderVO=new ShopOrder();
        orderVO.setOrderNum(orderNum);//订单编号
        if(!StringUtils.isBlank(id)){
            orderVO.setId(Long.valueOf(id));
        }

        try {
            List<ShopOrder> result = homeService.getShopOrderList(orderVO);
            if(result.size()>0){
                renderJson(request, response, SysCode.SUCCESS, result.get(0));//订单详情
            }else{
                renderJson(request, response, SysCode.SUCCESS, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("`````method``````getShopOrder()`````"+e.getMessage());
            renderJson(request, response, SysCode.SYS_ERR, e.getMessage());
        }
    }
    //创建——订单
    @RequestMapping("/addShopOrder")
    public void addShopOrder(HttpServletRequest request,HttpServletResponse response){
//		#{orderNum},#{userId},#{unitPrice},#{count},#{totalPrice},#{state},#{receiveMan},#{linkTel},#{receiveAddress}
        String[] paramKey = {"userId","shopId","unitPrice","count","receiveMan","linkTel","receiveAddress"};
        Map<String, String> params = parseParams(request, "addShopOrder", paramKey);
        String receiveAddress = params.get("receiveAddress");
        String linkTel = params.get("linkTel");
        String receiveMan = params.get("receiveMan");
        String unitPrice = params.get("unitPrice");
        String count = params.get("count");
        String userId = params.get("userId");
        String shopId = params.get("shopId");
        if(StringUtils.isBlank(shopId) || StringUtils.isBlank(userId)|| StringUtils.isBlank(unitPrice)|| StringUtils.isBlank(count)
                || StringUtils.isBlank(receiveMan)|| StringUtils.isBlank(linkTel)|| StringUtils.isBlank(receiveAddress)){//id不能为空
            renderJson(request, response, SysCode.PARAM_IS_ERROR, null);
            return;
        }
        Shop shop=new Shop();
        shop.setId(Long.valueOf(shopId));
        List<Shop> shops=homeService.getShopList(shop);
        if(shops==null || shops.size()<=0){
            renderJson(request, response, SysCode.SYS_ERR, "获取商品失败");
            return;
        }
        shop=shops.get(0);
        Random r=new Random();
        int num=r.nextInt(90) +10 ;
        String orderNumber= DateUtil.getNow("yyMMddHHmmss")+num;//DateUtil.getNow("yyMMddHHmmss")+num;//编码生成规则，时间+随机码（两位）
        BigDecimal price=new BigDecimal(unitPrice);
        BigDecimal nums=new BigDecimal(count);
        BigDecimal totalPrice = price.multiply(nums);//总价格
        ShopOrder orderVO=new ShopOrder();
        orderVO.setOrderNum("Z"+orderNumber);//订单编号
        orderVO.setState("1");//待支付
        orderVO.setShopId(Long.valueOf(shopId));
        orderVO.setUserId(Long.valueOf(userId));
        orderVO.setTotalPrice(totalPrice);
        orderVO.setUnitPrice(price);
        orderVO.setCount(Integer.parseInt(count));
        orderVO.setReceiveMan(receiveMan);
        orderVO.setLinkTel(linkTel);
        orderVO.setReceiveAddress(receiveAddress);
        orderVO.setShopName(shop.getTitle());
        if(null != shop.getImages() && shop.getImages().size()>0){
        	orderVO.setShopImage(shop.getImages().get(0));//图片取第一张
        }
        try {
            int result = homeService.addShopOrder(orderVO);
            if(1==result){
            	renderJson(request, response, SysCode.SUCCESS, orderVO.getOrderNum());
            	return ;
            }
            renderJson(request, response, SysCode.SUCCESS, result);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("`````method``````addShopOrder()`````"+e.getMessage());
            renderJson(request, response, SysCode.SYS_ERR, e.getMessage());
        }
    }
    //更新——订单
    @RequestMapping("/updateShopOrder")
    public void updateShopOrder(HttpServletRequest request,HttpServletResponse response){
//		#{orderNum},#{userId},#{unitPrice},#{count},#{totalPrice},#{state},#{receiveMan},#{linkTel},#{receiveAddress}
        String[] paramKey = {"status","orderNum"};
        Map<String, String> params = parseParams(request, "updateShopOrder", paramKey);
        String orderNum = params.get("orderNum");
        String status = params.get("status");
        if(StringUtils.isBlank(status) || StringUtils.isBlank(orderNum)){//id不能为空
            renderJson(request, response, SysCode.PARAM_IS_ERROR, null);
            return;
        }
        ShopOrder orderVO=new ShopOrder();
        orderVO.setOrderNum(orderNum);//订单编号
        List<ShopOrder> orderList = homeService.getShopOrderList(orderVO);
        if("3".equals(status)){//商家发货
        	String statusNum=orderList.get(0).getState();
        	if(!"2".equals(statusNum)){
        		renderJson(request, response, SysCode.PARAM_IS_ERROR, "订单状态异常");
                return;
        	}
        }else if("4".equals(status)){//用户收货
        	String statusNum=orderList.get(0).getState();
        	if(!"3".equals(statusNum)){
        		renderJson(request, response, SysCode.PARAM_IS_ERROR, "订单状态异常");
                return;
        	}
        }else{
        	renderJson(request, response, SysCode.PARAM_IS_ERROR, "订单状态异常");
            return;
        }
        orderVO.setState(status);//待支付(1待支付2待发货3待收货4已完成)
        try {
            Integer result = homeService.updateShopOrder(orderVO);
            renderJson(request, response, SysCode.SUCCESS, result);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("`````method``````updateShopOrder()`````"+e.getMessage());
            renderJson(request, response, SysCode.SYS_ERR, e.getMessage());
        }
    }
    //获取POS列表(统计)
    @RequestMapping("/getPosListByTotal")
    public void getPosListByTotal(HttpServletRequest request,HttpServletResponse response){

        String[] paramKey = {"userId"};
        Map<String, String> params = parseParams(request, "getPosListByTotal", paramKey);
        String userId = params.get("userId");
        if(StringUtils.isBlank(userId)){//id不能为空
            renderJson(request, response, SysCode.PARAM_IS_ERROR, null);
            return;
        }
        Pos posVO=new Pos();
        posVO.setActiveUserId(Long.valueOf(userId));
        int z_da=0;//直营_大pos
        int z_a_da=0;//直营_激活_大pos
        int z_zhi=0;//直营_智能pos
        int z_a_zhi=0;//直营_激活_智能pos
        int z_xiao=0;//直营_小pos
        int z_a_xiao=0;//直营_激活_小pos
        int t_da=0;//团队_大pos
        int t_a_da=0;//团队_激活_大pos
        int t_zhi=0;//团队_智能pos
        int t_a_zhi=0;//团队_激活_智能pos
        int t_xiao=0;//团队_小pos
        int t_a_xiao=0;//团队_激活_小pos
        try {
            List<Pos> result = homeService.getPosList(posVO);
            for (int i = 0; i < result.size(); i++) {
                Pos pos=result.get(i);
                if("1".equals(pos.getType())){//大pos
                    z_da++;
                    if("1".equals(pos.getStatus())){//激活
                        z_a_da++;
                    }
                }else if("2".equals(pos.getType())){//智能
                    z_zhi++;
                    if("1".equals(pos.getStatus())){//激活
                        z_a_zhi++;
                    }
                }else if("3".equals(pos.getType())){//小pos
                    z_xiao++;
                    if("1".equals(pos.getStatus())){//激活
                        z_a_xiao++;
                    }
                }
            }
            //团队--（下线）
            List<Pos> tuandui = homeService.getPosListByT(posVO);//团队
            for (int i = 0; i < tuandui.size(); i++) {
                Pos pos=tuandui.get(i);
                if("1".equals(pos.getType())){//大pos
                    t_da++;
                    if("1".equals(pos.getStatus())){//激活
                        t_a_da++;
                    }
                }else if("2".equals(pos.getType())){//智能
                    t_zhi++;
                    if("1".equals(pos.getStatus())){//激活
                        t_a_zhi++;
                    }
                }else if("3".equals(pos.getType())){//小pos
                    t_xiao++;
                    if("1".equals(pos.getStatus())){//激活
                        t_a_xiao++;
                    }
                }
            }
            TotalPosVO totalPosVO=new TotalPosVO();
            totalPosVO.setT_a_da(t_a_da+z_a_da);
            totalPosVO.setT_a_xiao(t_a_xiao+z_a_xiao);
            totalPosVO.setT_a_zhi(t_a_zhi+z_a_zhi);
            totalPosVO.setT_da(t_da+z_da);
            totalPosVO.setT_xiao(t_xiao+z_xiao);
            totalPosVO.setT_zhi(t_zhi+z_zhi);
            totalPosVO.setZ_a_da(z_a_da);
            totalPosVO.setZ_a_xiao(z_a_xiao);
            totalPosVO.setZ_a_zhi(z_a_zhi);
            totalPosVO.setZ_da(z_da);
            totalPosVO.setZ_xiao(z_xiao);
            totalPosVO.setZ_zhi(z_zhi);
            renderJson(request, response, SysCode.SUCCESS, totalPosVO);//统计POS
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("`````method``````getPosListByTotal()`````"+e.getMessage());
            renderJson(request, response, SysCode.SYS_ERR, e.getMessage());
        }
    }
    //获取POS列表(根据品牌)
    @RequestMapping("/getPosListByBrand")
    public void getPosListByBrand(HttpServletRequest request,HttpServletResponse response){

        String[] paramKey = {"userId","type","who"};
        Map<String, String> params = parseParams(request, "getPosListByBrand", paramKey);
        String userId = params.get("userId");
        String type = params.get("type"); //1大pos,2智能pos,3小pos
        String who = params.get("who"); //z-直营，t-团队
        if(StringUtils.isBlank(userId) || StringUtils.isBlank(type) || StringUtils.isBlank(who)){//id不能为空
            renderJson(request, response, SysCode.PARAM_IS_ERROR, null);
            return;
        }
        Pos posVO=new Pos();
        posVO.setActiveUserId(Long.valueOf(userId));
        posVO.setType(type);
        List<Pos> posList=new ArrayList<Pos>();
        Map<String, List<Pos>> map=new HashMap<String, List<Pos>>();
        try {
            List<Pos> result = homeService.getPosList(posVO);
            for (int i = 0; i < result.size(); i++) {
                Pos pos=result.get(i);
                List<Pos> list = map.get(pos.getBrand());
                if(list==null){//添加品牌
                    list=new ArrayList<Pos>();
                    list.add(pos);
                    map.put(pos.getBrand(), list);
                }else{//加入列表
                    list.add(pos);
                }
            }
            if("t".equals(who)){//团队
            	List<Pos> PosList = homeService.getPosListByT(posVO);
                for (int i = 0; i < PosList.size(); i++) {
                    Pos pos=PosList.get(i);
                    List<Pos> list = map.get(pos.getBrand());
                    if(list==null){//添加品牌
                        list=new ArrayList<Pos>();
                        list.add(pos);
                        map.put(pos.getBrand(), list);
                    }else{//加入列表
                        list.add(pos);
                    }
                }
            }
            for (String brand:map.keySet()) {
                List<Pos> list = map.get(brand);
                int active=0;
                for (int i = 0; i < list.size(); i++) {
                    if("1".equals(list.get(i).getStatus())){
                        active++;
                    }
                }
                Pos p=new Pos();
                p.setBrand(brand);
                p.setLogo(list.get(0).getLogo());
                p.setCount(list.size());
                p.setActiveNum(active);
                posList.add(p);
            }
            renderJson(request, response, SysCode.SUCCESS, posList);//统计POS
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("`````method``````getPosListByBrand()`````"+e.getMessage());
            renderJson(request, response, SysCode.SYS_ERR, e.getMessage());
        }
    }
    //获取POS列表(根据品牌、召回)
    @RequestMapping("/getPosList")
    public void getPosList(HttpServletRequest request,HttpServletResponse response){

        String[] paramKey = {"userId","type","who","brand","sn","action"};
        Map<String, String> params = parseParams(request, "getPosList", paramKey);
        String userId = params.get("userId");
        String type = params.get("type"); //1大pos,2智能pos,3小pos
        String who = params.get("who"); //z-直营，t-团队
        String brand = params.get("brand"); //品牌
        String sn = params.get("sn"); //sn
        String action = params.get("action"); //1下发，2召回
//		if(StringUtils.isBlank(userId) || StringUtils.isBlank(type) || StringUtils.isBlank(who)|| StringUtils.isBlank(brand)){//id不能为空
//        	renderJson(request, response, SysCode.PARAM_IS_ERROR, null);
//        	return;
//        }
        Pos posVO=new Pos();
        posVO.setType(type);
        posVO.setBrand(brand);
        posVO.setSn(sn);
        posVO.setAction(action);
        if("1".equals(action)){
            posVO.setUserId(Long.valueOf(userId));
        }else{
            posVO.setActiveUserId(Long.valueOf(userId));
        }
        if(null==action){
        	posVO.setAction("0");
        }
        try {
            List<Pos> result = homeService.getPosList(posVO);
            if("t".equals(who)){//团队
            	List<Pos> result_t = homeService.getPosListByT(posVO);
            	if(result_t.size()>0){
            		result.addAll(result_t);
            	}
            }
            renderJson(request, response, SysCode.SUCCESS, result);//统计POS
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("`````method``````getPosList()`````"+e.getMessage());
            renderJson(request, response, SysCode.SYS_ERR, e.getMessage());
        }
    }
    //下拨POS
    @RequestMapping("/updatePos")
    public void updatePos(HttpServletRequest request,HttpServletResponse response){

        String[] paramKey = {"userId","giveUserId","sn","action"};
        Map<String, String> params = parseParams(request, "updatePos", paramKey);
//		String userId = params.get("userId");
        String giveUserId = params.get("giveUserId"); //下拨盟友ID
        String sn = params.get("sn"); //机器码
        String action = params.get("action"); //1下放，2召回,3同意,4不同意
        if(StringUtils.isBlank(sn)|| StringUtils.isBlank(action)){//id不能为空
            renderJson(request, response, SysCode.PARAM_IS_ERROR, null);
            return;
        }
        Pos posVO=new Pos();
        posVO.setSn(sn);
        List<Pos> posList=homeService.getPosList(posVO);
        if(null==posList||posList.size()<=0){
       	 renderJson(request, response, SysCode.PARAM_IS_ERROR, "机器码SN无效");
            return;
        }
        posVO.setAction(action);//下放、召回
        if("1".equals(action)){
            if(StringUtils.isBlank(giveUserId)){//id不能为空
                renderJson(request, response, SysCode.PARAM_IS_ERROR, null);
                return;
            }
            WalletUserInfo user=new WalletUserInfo();
            user.setId(Long.valueOf(giveUserId));
            List<WalletUserInfo> users=homeService.getUserInfoList(user);
            if(null==users||users.size()<=0){
            	 renderJson(request, response, SysCode.PARAM_IS_ERROR, "用户ID无效");
                 return;
            }
            posVO.setActiveUserId(Long.valueOf(giveUserId));
        }else if("2".equals(action)){//召回
			if("1".equals(posList.get(0).getStatus())){//已激活的POS不能召回
				renderJson(request, response, SysCode.PARAM_IS_ERROR, "已经激活的POS不能召回");
                return;
			}
        }else if("3".equals(action)){
            //同意召回
        }else if("4".equals(action)){
            //不同意
        }
        try {
            int result = homeService.updatePos(posVO);
            renderJson(request, response, SysCode.SUCCESS, result);//
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("`````method``````updatePos()`````"+e.getMessage());
            renderJson(request, response, SysCode.SYS_ERR, e.getMessage());
        }
    }
    //交易数据列表()
    @RequestMapping("/getBusinessDataList")
    public void getBusinessDataList(HttpServletRequest request,HttpServletResponse response){

        String[] paramKey = {"userId","startTime","endTime","who"};
        Map<String, String> params = parseParams(request, "getBusinessDataList", paramKey);
        String userId = params.get("userId");
        String who = params.get("who"); //z-直营，t-团队
        String startTime = params.get("startTime"); //开始时间
        String endTime = params.get("endTime"); //结束时间
        if(StringUtils.isBlank(userId) || StringUtils.isBlank(startTime) || StringUtils.isBlank(who)|| StringUtils.isBlank(endTime)){//id不能为空
            renderJson(request, response, SysCode.PARAM_IS_ERROR, null);
            return;
        }
        BusinessData data=new BusinessData();
        data.setStartTime(startTime);
        data.setEndTime(endTime+" 23:59:59");
        data.setUserId(userId);
        List<BusinessData> result = new ArrayList<BusinessData>();
        if("t".equals(who)){//团队
        	result = homeService.getBusinessDataListByT(data);
        }else{
            result = homeService.getBusinessDataList(data);
        }
        try {
            renderJson(request, response, SysCode.SUCCESS, result);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("`````method``````getBusinessDataList()`````"+e.getMessage());
            renderJson(request, response, SysCode.SYS_ERR, e.getMessage());
        }
    }

//    //交易- 收益明细 -列表()
//    @RequestMapping("/getImcomeList")
//    public void getImcomeList(HttpServletRequest request,HttpServletResponse response){
//
//        String[] paramKey = {"userId","type","app"};
//        Map<String, String> params = parseParams(request, "getImcomeList", paramKey);
//        String userId = params.get("userId");
//        String app = params.get("app");
//        String type = params.get("type"); //1大2智能3小4激活返现
//        if(StringUtils.isBlank(userId) || StringUtils.isBlank(app)){//id不能为空
//            renderJson(request, response, SysCode.PARAM_IS_ERROR, null);
//            return;
//        }
//        Account data=new Account();
//        data.setUserId(Long.valueOf(userId));
//        if("1".equals(app)){//pos
//            data.setApp("1");//pos
//            if("1".equals(type) || "2".equals(type) || "3".equals(type)){
//                data.setPosType(type);
//            }//else 全部pos
//        }else{
//            data.setApp("2");//激活返现
//        }
//        try {
//            List<Account> result = homeService.getImcomeList(data);
//            renderJson(request, response, SysCode.SUCCESS, result);
//        } catch (Exception e) {
//            e.printStackTrace();
//            logger.info("`````method``````getImcomeList()`````"+e.getMessage());
//            renderJson(request, response, SysCode.SYS_ERR, e.getMessage());
//        }
//    }
    
    //交易- 收益明细 -列表()
    @RequestMapping("/getImcomeList")
    public void getImcomeList(HttpServletRequest request,HttpServletResponse response){

        String[] paramKey = {"userId","type","app"};
        Map<String, String> params = parseParams(request, "getImcomeList", paramKey);
        String userId = params.get("userId");
        String app = params.get("app");
        String type = params.get("type"); //1大2智能3小4激活返现
        if(StringUtils.isBlank(userId) || StringUtils.isBlank(app)){//id不能为空
            renderJson(request, response, SysCode.PARAM_IS_ERROR, null);
            return;
        }
        List<Account> result =null;
        Account data=new Account();
        data.setUserId(Long.valueOf(userId));
        if("1".equals(app)){//pos
            data.setApp("A1");//分润
            if("1".equals(type) || "2".equals(type) || "3".equals(type)){
                data.setPosType(type);
            }//else 全部pos
            result = homeService.getImcomeList2(data);
        }else{
            data.setApp("A8");//激活返现
            result = homeService.getImcomeList3(data);
        }
        try {
            renderJson(request, response, SysCode.SUCCESS, result);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("`````method``````getImcomeList()`````"+e.getMessage());
            renderJson(request, response, SysCode.SYS_ERR, e.getMessage());
        }
    }

    //获取用户(根据名称和手机号)
    @RequestMapping("/getUser")
    public void getUser(HttpServletRequest request,HttpServletResponse response){

        String[] paramKey = {"name","userId"};
        Map<String, String> params = parseParams(request, "getUser", paramKey);
        String userId = params.get("userId");
        String name = params.get("name");
//        String telephone = params.get("telephone"); //1大2智能3小4激活返现
        if(StringUtils.isBlank(userId)){//id不能为空
            renderJson(request, response, SysCode.PARAM_IS_ERROR, null);
            return;
        }
//        if(StringUtils.isBlank(name) && StringUtils.isBlank(telephone)){//id不能为空
//            renderJson(request, response, SysCode.PARAM_IS_ERROR, null);
//            return;
//        }
        WalletUserInfo userInfo=new WalletUserInfo();
        userInfo.setRecommonId(Long.valueOf(userId));
//        userInfo.setRealName(name);
        userInfo.setPhone(name);
        
        List<WalletUserInfo> userList=homeService.getUserInfoList(userInfo);
        
        try {
            renderJson(request, response, SysCode.SUCCESS, userList);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("`````method``````getUser()`````"+e.getMessage());
            renderJson(request, response, SysCode.SYS_ERR, e.getMessage());
        }
    }
    
}
