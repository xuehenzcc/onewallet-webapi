package com.group.wallet.channel.pos.lkl.bean;

/**
 * 
*    
* 项目名称：rsrpaydemo   
* 类名称：BanckCardDomain   
* 类描述：   绑定商户银行卡
* 创建人：
* 创建时间：2017年1月8日 下午2:08:25   
* @version 1.0   
*
 */
public class BanckCardBean extends BaseBean
{
    /**
     * 开户行省份编号
     */
    private String open_province_id;
    
    /**
     * 开户行所在城市编号
     */
    private String open_city_id;
    
    /**
     * 持卡人姓名
     */
    private String cardholder;
    
    /**
     * 持卡人身份证号码
     */
    private String id_no;
    
    /**
     * 银行卡号
     */
    private String card_no;
    
    /**
     * 开户行支行号
     */
    private String bank_branch_nbk_code;
    
    /**
     * 用户预留手机号码
     */
    private String mobile_no;
    
    /**
     * 商户在本应用中的唯一编码
     * 当前银行卡对应商户编码
     */
    protected String app_merch_no;
    
    private int account_type;
    
    private String hold_bank_photo;
    
    private String bank_photo;
    
    private String bank_back_photo;
    
    public String getOpen_province_id()
    {
        return open_province_id;
    }
    
    public void setOpen_province_id(String open_province_id)
    {
        this.open_province_id = open_province_id;
    }
    
    public String getOpen_city_id()
    {
        return open_city_id;
    }
    
    public void setOpen_city_id(String open_city_id)
    {
        this.open_city_id = open_city_id;
    }
    
    public String getCardholder()
    {
        return cardholder;
    }
    
    public void setCardholder(String cardholder)
    {
        this.cardholder = cardholder;
    }
    
    public String getId_no()
    {
        return id_no;
    }
    
    public void setId_no(String id_no)
    {
        this.id_no = id_no;
    }
    
    public String getCard_no()
    {
        return card_no;
    }
    
    public void setCard_no(String card_no)
    {
        this.card_no = card_no;
    }
    
    public String getBank_branch_nbk_code()
    {
        return bank_branch_nbk_code;
    }
    
    public void setBank_branch_nbk_code(String bank_branch_nbk_code)
    {
        this.bank_branch_nbk_code = bank_branch_nbk_code;
    }
    
    public String getMobile_no()
    {
        return mobile_no;
    }
    
    public void setMobile_no(String mobile_no)
    {
        this.mobile_no = mobile_no;
    }
    
    public String getApp_merch_no()
    {
        return app_merch_no;
    }
    
    public void setApp_merch_no(String app_merch_no)
    {
        this.app_merch_no = app_merch_no;
    }
    
    public int getAccount_type()
    {
        return account_type;
    }
    
    public void setAccount_type(int account_type)
    {
        this.account_type = account_type;
    }

    public String getHold_bank_photo()
    {
        return hold_bank_photo;
    }

    public void setHold_bank_photo(String hold_bank_photo)
    {
        this.hold_bank_photo = hold_bank_photo;
    }

    public String getBank_photo()
    {
        return bank_photo;
    }

    public void setBank_photo(String bank_photo)
    {
        this.bank_photo = bank_photo;
    }

    public String getBank_back_photo()
    {
        return bank_back_photo;
    }

    public void setBank_back_photo(String bank_back_photo)
    {
        this.bank_back_photo = bank_back_photo;
    }
   
}
