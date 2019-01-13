package com.group.wallet.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WalletTradeRecordsExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public WalletTradeRecordsExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andOrderNoIsNull() {
            addCriterion("order_no is null");
            return (Criteria) this;
        }

        public Criteria andOrderNoIsNotNull() {
            addCriterion("order_no is not null");
            return (Criteria) this;
        }

        public Criteria andOrderNoEqualTo(String value) {
            addCriterion("order_no =", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoNotEqualTo(String value) {
            addCriterion("order_no <>", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoGreaterThan(String value) {
            addCriterion("order_no >", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoGreaterThanOrEqualTo(String value) {
            addCriterion("order_no >=", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoLessThan(String value) {
            addCriterion("order_no <", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoLessThanOrEqualTo(String value) {
            addCriterion("order_no <=", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoLike(String value) {
            addCriterion("order_no like", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoNotLike(String value) {
            addCriterion("order_no not like", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoIn(List<String> values) {
            addCriterion("order_no in", values, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoNotIn(List<String> values) {
            addCriterion("order_no not in", values, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoBetween(String value1, String value2) {
            addCriterion("order_no between", value1, value2, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoNotBetween(String value1, String value2) {
            addCriterion("order_no not between", value1, value2, "orderNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoIsNull() {
            addCriterion("serial_no is null");
            return (Criteria) this;
        }

        public Criteria andSerialNoIsNotNull() {
            addCriterion("serial_no is not null");
            return (Criteria) this;
        }

        public Criteria andSerialNoEqualTo(String value) {
            addCriterion("serial_no =", value, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoNotEqualTo(String value) {
            addCriterion("serial_no <>", value, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoGreaterThan(String value) {
            addCriterion("serial_no >", value, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoGreaterThanOrEqualTo(String value) {
            addCriterion("serial_no >=", value, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoLessThan(String value) {
            addCriterion("serial_no <", value, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoLessThanOrEqualTo(String value) {
            addCriterion("serial_no <=", value, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoLike(String value) {
            addCriterion("serial_no like", value, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoNotLike(String value) {
            addCriterion("serial_no not like", value, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoIn(List<String> values) {
            addCriterion("serial_no in", values, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoNotIn(List<String> values) {
            addCriterion("serial_no not in", values, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoBetween(String value1, String value2) {
            addCriterion("serial_no between", value1, value2, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoNotBetween(String value1, String value2) {
            addCriterion("serial_no not between", value1, value2, "serialNo");
            return (Criteria) this;
        }

        public Criteria andMerchantNoIsNull() {
            addCriterion("merchant_no is null");
            return (Criteria) this;
        }

        public Criteria andMerchantNoIsNotNull() {
            addCriterion("merchant_no is not null");
            return (Criteria) this;
        }

        public Criteria andMerchantNoEqualTo(String value) {
            addCriterion("merchant_no =", value, "merchantNo");
            return (Criteria) this;
        }

        public Criteria andMerchantNoNotEqualTo(String value) {
            addCriterion("merchant_no <>", value, "merchantNo");
            return (Criteria) this;
        }

        public Criteria andMerchantNoGreaterThan(String value) {
            addCriterion("merchant_no >", value, "merchantNo");
            return (Criteria) this;
        }

        public Criteria andMerchantNoGreaterThanOrEqualTo(String value) {
            addCriterion("merchant_no >=", value, "merchantNo");
            return (Criteria) this;
        }

        public Criteria andMerchantNoLessThan(String value) {
            addCriterion("merchant_no <", value, "merchantNo");
            return (Criteria) this;
        }

        public Criteria andMerchantNoLessThanOrEqualTo(String value) {
            addCriterion("merchant_no <=", value, "merchantNo");
            return (Criteria) this;
        }

        public Criteria andMerchantNoLike(String value) {
            addCriterion("merchant_no like", value, "merchantNo");
            return (Criteria) this;
        }

        public Criteria andMerchantNoNotLike(String value) {
            addCriterion("merchant_no not like", value, "merchantNo");
            return (Criteria) this;
        }

        public Criteria andMerchantNoIn(List<String> values) {
            addCriterion("merchant_no in", values, "merchantNo");
            return (Criteria) this;
        }

        public Criteria andMerchantNoNotIn(List<String> values) {
            addCriterion("merchant_no not in", values, "merchantNo");
            return (Criteria) this;
        }

        public Criteria andMerchantNoBetween(String value1, String value2) {
            addCriterion("merchant_no between", value1, value2, "merchantNo");
            return (Criteria) this;
        }

        public Criteria andMerchantNoNotBetween(String value1, String value2) {
            addCriterion("merchant_no not between", value1, value2, "merchantNo");
            return (Criteria) this;
        }

        public Criteria andMerchantNameIsNull() {
            addCriterion("merchant_name is null");
            return (Criteria) this;
        }

        public Criteria andMerchantNameIsNotNull() {
            addCriterion("merchant_name is not null");
            return (Criteria) this;
        }

        public Criteria andMerchantNameEqualTo(String value) {
            addCriterion("merchant_name =", value, "merchantName");
            return (Criteria) this;
        }

        public Criteria andMerchantNameNotEqualTo(String value) {
            addCriterion("merchant_name <>", value, "merchantName");
            return (Criteria) this;
        }

        public Criteria andMerchantNameGreaterThan(String value) {
            addCriterion("merchant_name >", value, "merchantName");
            return (Criteria) this;
        }

        public Criteria andMerchantNameGreaterThanOrEqualTo(String value) {
            addCriterion("merchant_name >=", value, "merchantName");
            return (Criteria) this;
        }

        public Criteria andMerchantNameLessThan(String value) {
            addCriterion("merchant_name <", value, "merchantName");
            return (Criteria) this;
        }

        public Criteria andMerchantNameLessThanOrEqualTo(String value) {
            addCriterion("merchant_name <=", value, "merchantName");
            return (Criteria) this;
        }

        public Criteria andMerchantNameLike(String value) {
            addCriterion("merchant_name like", value, "merchantName");
            return (Criteria) this;
        }

        public Criteria andMerchantNameNotLike(String value) {
            addCriterion("merchant_name not like", value, "merchantName");
            return (Criteria) this;
        }

        public Criteria andMerchantNameIn(List<String> values) {
            addCriterion("merchant_name in", values, "merchantName");
            return (Criteria) this;
        }

        public Criteria andMerchantNameNotIn(List<String> values) {
            addCriterion("merchant_name not in", values, "merchantName");
            return (Criteria) this;
        }

        public Criteria andMerchantNameBetween(String value1, String value2) {
            addCriterion("merchant_name between", value1, value2, "merchantName");
            return (Criteria) this;
        }

        public Criteria andMerchantNameNotBetween(String value1, String value2) {
            addCriterion("merchant_name not between", value1, value2, "merchantName");
            return (Criteria) this;
        }

        public Criteria andStoreIdIsNull() {
            addCriterion("store_id is null");
            return (Criteria) this;
        }

        public Criteria andStoreIdIsNotNull() {
            addCriterion("store_id is not null");
            return (Criteria) this;
        }

        public Criteria andStoreIdEqualTo(Long value) {
            addCriterion("store_id =", value, "storeId");
            return (Criteria) this;
        }

        public Criteria andStoreIdNotEqualTo(Long value) {
            addCriterion("store_id <>", value, "storeId");
            return (Criteria) this;
        }

        public Criteria andStoreIdGreaterThan(Long value) {
            addCriterion("store_id >", value, "storeId");
            return (Criteria) this;
        }

        public Criteria andStoreIdGreaterThanOrEqualTo(Long value) {
            addCriterion("store_id >=", value, "storeId");
            return (Criteria) this;
        }

        public Criteria andStoreIdLessThan(Long value) {
            addCriterion("store_id <", value, "storeId");
            return (Criteria) this;
        }

        public Criteria andStoreIdLessThanOrEqualTo(Long value) {
            addCriterion("store_id <=", value, "storeId");
            return (Criteria) this;
        }

        public Criteria andStoreIdIn(List<Long> values) {
            addCriterion("store_id in", values, "storeId");
            return (Criteria) this;
        }

        public Criteria andStoreIdNotIn(List<Long> values) {
            addCriterion("store_id not in", values, "storeId");
            return (Criteria) this;
        }

        public Criteria andStoreIdBetween(Long value1, Long value2) {
            addCriterion("store_id between", value1, value2, "storeId");
            return (Criteria) this;
        }

        public Criteria andStoreIdNotBetween(Long value1, Long value2) {
            addCriterion("store_id not between", value1, value2, "storeId");
            return (Criteria) this;
        }

        public Criteria andCashierIdIsNull() {
            addCriterion("cashier_id is null");
            return (Criteria) this;
        }

        public Criteria andCashierIdIsNotNull() {
            addCriterion("cashier_id is not null");
            return (Criteria) this;
        }

        public Criteria andCashierIdEqualTo(Long value) {
            addCriterion("cashier_id =", value, "cashierId");
            return (Criteria) this;
        }

        public Criteria andCashierIdNotEqualTo(Long value) {
            addCriterion("cashier_id <>", value, "cashierId");
            return (Criteria) this;
        }

        public Criteria andCashierIdGreaterThan(Long value) {
            addCriterion("cashier_id >", value, "cashierId");
            return (Criteria) this;
        }

        public Criteria andCashierIdGreaterThanOrEqualTo(Long value) {
            addCriterion("cashier_id >=", value, "cashierId");
            return (Criteria) this;
        }

        public Criteria andCashierIdLessThan(Long value) {
            addCriterion("cashier_id <", value, "cashierId");
            return (Criteria) this;
        }

        public Criteria andCashierIdLessThanOrEqualTo(Long value) {
            addCriterion("cashier_id <=", value, "cashierId");
            return (Criteria) this;
        }

        public Criteria andCashierIdIn(List<Long> values) {
            addCriterion("cashier_id in", values, "cashierId");
            return (Criteria) this;
        }

        public Criteria andCashierIdNotIn(List<Long> values) {
            addCriterion("cashier_id not in", values, "cashierId");
            return (Criteria) this;
        }

        public Criteria andCashierIdBetween(Long value1, Long value2) {
            addCriterion("cashier_id between", value1, value2, "cashierId");
            return (Criteria) this;
        }

        public Criteria andCashierIdNotBetween(Long value1, Long value2) {
            addCriterion("cashier_id not between", value1, value2, "cashierId");
            return (Criteria) this;
        }

        public Criteria andQrcodeIdIsNull() {
            addCriterion("qrcode_id is null");
            return (Criteria) this;
        }

        public Criteria andQrcodeIdIsNotNull() {
            addCriterion("qrcode_id is not null");
            return (Criteria) this;
        }

        public Criteria andQrcodeIdEqualTo(Long value) {
            addCriterion("qrcode_id =", value, "qrcodeId");
            return (Criteria) this;
        }

        public Criteria andQrcodeIdNotEqualTo(Long value) {
            addCriterion("qrcode_id <>", value, "qrcodeId");
            return (Criteria) this;
        }

        public Criteria andQrcodeIdGreaterThan(Long value) {
            addCriterion("qrcode_id >", value, "qrcodeId");
            return (Criteria) this;
        }

        public Criteria andQrcodeIdGreaterThanOrEqualTo(Long value) {
            addCriterion("qrcode_id >=", value, "qrcodeId");
            return (Criteria) this;
        }

        public Criteria andQrcodeIdLessThan(Long value) {
            addCriterion("qrcode_id <", value, "qrcodeId");
            return (Criteria) this;
        }

        public Criteria andQrcodeIdLessThanOrEqualTo(Long value) {
            addCriterion("qrcode_id <=", value, "qrcodeId");
            return (Criteria) this;
        }

        public Criteria andQrcodeIdIn(List<Long> values) {
            addCriterion("qrcode_id in", values, "qrcodeId");
            return (Criteria) this;
        }

        public Criteria andQrcodeIdNotIn(List<Long> values) {
            addCriterion("qrcode_id not in", values, "qrcodeId");
            return (Criteria) this;
        }

        public Criteria andQrcodeIdBetween(Long value1, Long value2) {
            addCriterion("qrcode_id between", value1, value2, "qrcodeId");
            return (Criteria) this;
        }

        public Criteria andQrcodeIdNotBetween(Long value1, Long value2) {
            addCriterion("qrcode_id not between", value1, value2, "qrcodeId");
            return (Criteria) this;
        }

        public Criteria andAssociatorIdIsNull() {
            addCriterion("associator_id is null");
            return (Criteria) this;
        }

        public Criteria andAssociatorIdIsNotNull() {
            addCriterion("associator_id is not null");
            return (Criteria) this;
        }

        public Criteria andAssociatorIdEqualTo(Long value) {
            addCriterion("associator_id =", value, "associatorId");
            return (Criteria) this;
        }

        public Criteria andAssociatorIdNotEqualTo(Long value) {
            addCriterion("associator_id <>", value, "associatorId");
            return (Criteria) this;
        }

        public Criteria andAssociatorIdGreaterThan(Long value) {
            addCriterion("associator_id >", value, "associatorId");
            return (Criteria) this;
        }

        public Criteria andAssociatorIdGreaterThanOrEqualTo(Long value) {
            addCriterion("associator_id >=", value, "associatorId");
            return (Criteria) this;
        }

        public Criteria andAssociatorIdLessThan(Long value) {
            addCriterion("associator_id <", value, "associatorId");
            return (Criteria) this;
        }

        public Criteria andAssociatorIdLessThanOrEqualTo(Long value) {
            addCriterion("associator_id <=", value, "associatorId");
            return (Criteria) this;
        }

        public Criteria andAssociatorIdIn(List<Long> values) {
            addCriterion("associator_id in", values, "associatorId");
            return (Criteria) this;
        }

        public Criteria andAssociatorIdNotIn(List<Long> values) {
            addCriterion("associator_id not in", values, "associatorId");
            return (Criteria) this;
        }

        public Criteria andAssociatorIdBetween(Long value1, Long value2) {
            addCriterion("associator_id between", value1, value2, "associatorId");
            return (Criteria) this;
        }

        public Criteria andAssociatorIdNotBetween(Long value1, Long value2) {
            addCriterion("associator_id not between", value1, value2, "associatorId");
            return (Criteria) this;
        }

        public Criteria andTradeTimeIsNull() {
            addCriterion("trade_time is null");
            return (Criteria) this;
        }

        public Criteria andTradeTimeIsNotNull() {
            addCriterion("trade_time is not null");
            return (Criteria) this;
        }

        public Criteria andTradeTimeEqualTo(Date value) {
            addCriterion("trade_time =", value, "tradeTime");
            return (Criteria) this;
        }

        public Criteria andTradeTimeNotEqualTo(Date value) {
            addCriterion("trade_time <>", value, "tradeTime");
            return (Criteria) this;
        }

        public Criteria andTradeTimeGreaterThan(Date value) {
            addCriterion("trade_time >", value, "tradeTime");
            return (Criteria) this;
        }

        public Criteria andTradeTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("trade_time >=", value, "tradeTime");
            return (Criteria) this;
        }

        public Criteria andTradeTimeLessThan(Date value) {
            addCriterion("trade_time <", value, "tradeTime");
            return (Criteria) this;
        }

        public Criteria andTradeTimeLessThanOrEqualTo(Date value) {
            addCriterion("trade_time <=", value, "tradeTime");
            return (Criteria) this;
        }

        public Criteria andTradeTimeIn(List<Date> values) {
            addCriterion("trade_time in", values, "tradeTime");
            return (Criteria) this;
        }

        public Criteria andTradeTimeNotIn(List<Date> values) {
            addCriterion("trade_time not in", values, "tradeTime");
            return (Criteria) this;
        }

        public Criteria andTradeTimeBetween(Date value1, Date value2) {
            addCriterion("trade_time between", value1, value2, "tradeTime");
            return (Criteria) this;
        }

        public Criteria andTradeTimeNotBetween(Date value1, Date value2) {
            addCriterion("trade_time not between", value1, value2, "tradeTime");
            return (Criteria) this;
        }

        public Criteria andTradeMoneyIsNull() {
            addCriterion("trade_money is null");
            return (Criteria) this;
        }

        public Criteria andTradeMoneyIsNotNull() {
            addCriterion("trade_money is not null");
            return (Criteria) this;
        }

        public Criteria andTradeMoneyEqualTo(BigDecimal value) {
            addCriterion("trade_money =", value, "tradeMoney");
            return (Criteria) this;
        }

        public Criteria andTradeMoneyNotEqualTo(BigDecimal value) {
            addCriterion("trade_money <>", value, "tradeMoney");
            return (Criteria) this;
        }

        public Criteria andTradeMoneyGreaterThan(BigDecimal value) {
            addCriterion("trade_money >", value, "tradeMoney");
            return (Criteria) this;
        }

        public Criteria andTradeMoneyGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("trade_money >=", value, "tradeMoney");
            return (Criteria) this;
        }

        public Criteria andTradeMoneyLessThan(BigDecimal value) {
            addCriterion("trade_money <", value, "tradeMoney");
            return (Criteria) this;
        }

        public Criteria andTradeMoneyLessThanOrEqualTo(BigDecimal value) {
            addCriterion("trade_money <=", value, "tradeMoney");
            return (Criteria) this;
        }

        public Criteria andTradeMoneyIn(List<BigDecimal> values) {
            addCriterion("trade_money in", values, "tradeMoney");
            return (Criteria) this;
        }

        public Criteria andTradeMoneyNotIn(List<BigDecimal> values) {
            addCriterion("trade_money not in", values, "tradeMoney");
            return (Criteria) this;
        }

        public Criteria andTradeMoneyBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("trade_money between", value1, value2, "tradeMoney");
            return (Criteria) this;
        }

        public Criteria andTradeMoneyNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("trade_money not between", value1, value2, "tradeMoney");
            return (Criteria) this;
        }

        public Criteria andRealTradeMoneyIsNull() {
            addCriterion("real_trade_money is null");
            return (Criteria) this;
        }

        public Criteria andRealTradeMoneyIsNotNull() {
            addCriterion("real_trade_money is not null");
            return (Criteria) this;
        }

        public Criteria andRealTradeMoneyEqualTo(BigDecimal value) {
            addCriterion("real_trade_money =", value, "realTradeMoney");
            return (Criteria) this;
        }

        public Criteria andRealTradeMoneyNotEqualTo(BigDecimal value) {
            addCriterion("real_trade_money <>", value, "realTradeMoney");
            return (Criteria) this;
        }

        public Criteria andRealTradeMoneyGreaterThan(BigDecimal value) {
            addCriterion("real_trade_money >", value, "realTradeMoney");
            return (Criteria) this;
        }

        public Criteria andRealTradeMoneyGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("real_trade_money >=", value, "realTradeMoney");
            return (Criteria) this;
        }

        public Criteria andRealTradeMoneyLessThan(BigDecimal value) {
            addCriterion("real_trade_money <", value, "realTradeMoney");
            return (Criteria) this;
        }

        public Criteria andRealTradeMoneyLessThanOrEqualTo(BigDecimal value) {
            addCriterion("real_trade_money <=", value, "realTradeMoney");
            return (Criteria) this;
        }

        public Criteria andRealTradeMoneyIn(List<BigDecimal> values) {
            addCriterion("real_trade_money in", values, "realTradeMoney");
            return (Criteria) this;
        }

        public Criteria andRealTradeMoneyNotIn(List<BigDecimal> values) {
            addCriterion("real_trade_money not in", values, "realTradeMoney");
            return (Criteria) this;
        }

        public Criteria andRealTradeMoneyBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("real_trade_money between", value1, value2, "realTradeMoney");
            return (Criteria) this;
        }

        public Criteria andRealTradeMoneyNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("real_trade_money not between", value1, value2, "realTradeMoney");
            return (Criteria) this;
        }

        public Criteria andDiscountMoneyIsNull() {
            addCriterion("discount_money is null");
            return (Criteria) this;
        }

        public Criteria andDiscountMoneyIsNotNull() {
            addCriterion("discount_money is not null");
            return (Criteria) this;
        }

        public Criteria andDiscountMoneyEqualTo(BigDecimal value) {
            addCriterion("discount_money =", value, "discountMoney");
            return (Criteria) this;
        }

        public Criteria andDiscountMoneyNotEqualTo(BigDecimal value) {
            addCriterion("discount_money <>", value, "discountMoney");
            return (Criteria) this;
        }

        public Criteria andDiscountMoneyGreaterThan(BigDecimal value) {
            addCriterion("discount_money >", value, "discountMoney");
            return (Criteria) this;
        }

        public Criteria andDiscountMoneyGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("discount_money >=", value, "discountMoney");
            return (Criteria) this;
        }

        public Criteria andDiscountMoneyLessThan(BigDecimal value) {
            addCriterion("discount_money <", value, "discountMoney");
            return (Criteria) this;
        }

        public Criteria andDiscountMoneyLessThanOrEqualTo(BigDecimal value) {
            addCriterion("discount_money <=", value, "discountMoney");
            return (Criteria) this;
        }

        public Criteria andDiscountMoneyIn(List<BigDecimal> values) {
            addCriterion("discount_money in", values, "discountMoney");
            return (Criteria) this;
        }

        public Criteria andDiscountMoneyNotIn(List<BigDecimal> values) {
            addCriterion("discount_money not in", values, "discountMoney");
            return (Criteria) this;
        }

        public Criteria andDiscountMoneyBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("discount_money between", value1, value2, "discountMoney");
            return (Criteria) this;
        }

        public Criteria andDiscountMoneyNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("discount_money not between", value1, value2, "discountMoney");
            return (Criteria) this;
        }

        public Criteria andMerchantPoundageIsNull() {
            addCriterion("merchant_poundage is null");
            return (Criteria) this;
        }

        public Criteria andMerchantPoundageIsNotNull() {
            addCriterion("merchant_poundage is not null");
            return (Criteria) this;
        }

        public Criteria andMerchantPoundageEqualTo(BigDecimal value) {
            addCriterion("merchant_poundage =", value, "merchantPoundage");
            return (Criteria) this;
        }

        public Criteria andMerchantPoundageNotEqualTo(BigDecimal value) {
            addCriterion("merchant_poundage <>", value, "merchantPoundage");
            return (Criteria) this;
        }

        public Criteria andMerchantPoundageGreaterThan(BigDecimal value) {
            addCriterion("merchant_poundage >", value, "merchantPoundage");
            return (Criteria) this;
        }

        public Criteria andMerchantPoundageGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("merchant_poundage >=", value, "merchantPoundage");
            return (Criteria) this;
        }

        public Criteria andMerchantPoundageLessThan(BigDecimal value) {
            addCriterion("merchant_poundage <", value, "merchantPoundage");
            return (Criteria) this;
        }

        public Criteria andMerchantPoundageLessThanOrEqualTo(BigDecimal value) {
            addCriterion("merchant_poundage <=", value, "merchantPoundage");
            return (Criteria) this;
        }

        public Criteria andMerchantPoundageIn(List<BigDecimal> values) {
            addCriterion("merchant_poundage in", values, "merchantPoundage");
            return (Criteria) this;
        }

        public Criteria andMerchantPoundageNotIn(List<BigDecimal> values) {
            addCriterion("merchant_poundage not in", values, "merchantPoundage");
            return (Criteria) this;
        }

        public Criteria andMerchantPoundageBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("merchant_poundage between", value1, value2, "merchantPoundage");
            return (Criteria) this;
        }

        public Criteria andMerchantPoundageNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("merchant_poundage not between", value1, value2, "merchantPoundage");
            return (Criteria) this;
        }

        public Criteria andPlatformPoundageIsNull() {
            addCriterion("platform_poundage is null");
            return (Criteria) this;
        }

        public Criteria andPlatformPoundageIsNotNull() {
            addCriterion("platform_poundage is not null");
            return (Criteria) this;
        }

        public Criteria andPlatformPoundageEqualTo(BigDecimal value) {
            addCriterion("platform_poundage =", value, "platformPoundage");
            return (Criteria) this;
        }

        public Criteria andPlatformPoundageNotEqualTo(BigDecimal value) {
            addCriterion("platform_poundage <>", value, "platformPoundage");
            return (Criteria) this;
        }

        public Criteria andPlatformPoundageGreaterThan(BigDecimal value) {
            addCriterion("platform_poundage >", value, "platformPoundage");
            return (Criteria) this;
        }

        public Criteria andPlatformPoundageGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("platform_poundage >=", value, "platformPoundage");
            return (Criteria) this;
        }

        public Criteria andPlatformPoundageLessThan(BigDecimal value) {
            addCriterion("platform_poundage <", value, "platformPoundage");
            return (Criteria) this;
        }

        public Criteria andPlatformPoundageLessThanOrEqualTo(BigDecimal value) {
            addCriterion("platform_poundage <=", value, "platformPoundage");
            return (Criteria) this;
        }

        public Criteria andPlatformPoundageIn(List<BigDecimal> values) {
            addCriterion("platform_poundage in", values, "platformPoundage");
            return (Criteria) this;
        }

        public Criteria andPlatformPoundageNotIn(List<BigDecimal> values) {
            addCriterion("platform_poundage not in", values, "platformPoundage");
            return (Criteria) this;
        }

        public Criteria andPlatformPoundageBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("platform_poundage between", value1, value2, "platformPoundage");
            return (Criteria) this;
        }

        public Criteria andPlatformPoundageNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("platform_poundage not between", value1, value2, "platformPoundage");
            return (Criteria) this;
        }

        public Criteria andRefundMoneyIsNull() {
            addCriterion("refund_money is null");
            return (Criteria) this;
        }

        public Criteria andRefundMoneyIsNotNull() {
            addCriterion("refund_money is not null");
            return (Criteria) this;
        }

        public Criteria andRefundMoneyEqualTo(BigDecimal value) {
            addCriterion("refund_money =", value, "refundMoney");
            return (Criteria) this;
        }

        public Criteria andRefundMoneyNotEqualTo(BigDecimal value) {
            addCriterion("refund_money <>", value, "refundMoney");
            return (Criteria) this;
        }

        public Criteria andRefundMoneyGreaterThan(BigDecimal value) {
            addCriterion("refund_money >", value, "refundMoney");
            return (Criteria) this;
        }

        public Criteria andRefundMoneyGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("refund_money >=", value, "refundMoney");
            return (Criteria) this;
        }

        public Criteria andRefundMoneyLessThan(BigDecimal value) {
            addCriterion("refund_money <", value, "refundMoney");
            return (Criteria) this;
        }

        public Criteria andRefundMoneyLessThanOrEqualTo(BigDecimal value) {
            addCriterion("refund_money <=", value, "refundMoney");
            return (Criteria) this;
        }

        public Criteria andRefundMoneyIn(List<BigDecimal> values) {
            addCriterion("refund_money in", values, "refundMoney");
            return (Criteria) this;
        }

        public Criteria andRefundMoneyNotIn(List<BigDecimal> values) {
            addCriterion("refund_money not in", values, "refundMoney");
            return (Criteria) this;
        }

        public Criteria andRefundMoneyBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("refund_money between", value1, value2, "refundMoney");
            return (Criteria) this;
        }

        public Criteria andRefundMoneyNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("refund_money not between", value1, value2, "refundMoney");
            return (Criteria) this;
        }

        public Criteria andGoodsNameIsNull() {
            addCriterion("goods_name is null");
            return (Criteria) this;
        }

        public Criteria andGoodsNameIsNotNull() {
            addCriterion("goods_name is not null");
            return (Criteria) this;
        }

        public Criteria andGoodsNameEqualTo(String value) {
            addCriterion("goods_name =", value, "goodsName");
            return (Criteria) this;
        }

        public Criteria andGoodsNameNotEqualTo(String value) {
            addCriterion("goods_name <>", value, "goodsName");
            return (Criteria) this;
        }

        public Criteria andGoodsNameGreaterThan(String value) {
            addCriterion("goods_name >", value, "goodsName");
            return (Criteria) this;
        }

        public Criteria andGoodsNameGreaterThanOrEqualTo(String value) {
            addCriterion("goods_name >=", value, "goodsName");
            return (Criteria) this;
        }

        public Criteria andGoodsNameLessThan(String value) {
            addCriterion("goods_name <", value, "goodsName");
            return (Criteria) this;
        }

        public Criteria andGoodsNameLessThanOrEqualTo(String value) {
            addCriterion("goods_name <=", value, "goodsName");
            return (Criteria) this;
        }

        public Criteria andGoodsNameLike(String value) {
            addCriterion("goods_name like", value, "goodsName");
            return (Criteria) this;
        }

        public Criteria andGoodsNameNotLike(String value) {
            addCriterion("goods_name not like", value, "goodsName");
            return (Criteria) this;
        }

        public Criteria andGoodsNameIn(List<String> values) {
            addCriterion("goods_name in", values, "goodsName");
            return (Criteria) this;
        }

        public Criteria andGoodsNameNotIn(List<String> values) {
            addCriterion("goods_name not in", values, "goodsName");
            return (Criteria) this;
        }

        public Criteria andGoodsNameBetween(String value1, String value2) {
            addCriterion("goods_name between", value1, value2, "goodsName");
            return (Criteria) this;
        }

        public Criteria andGoodsNameNotBetween(String value1, String value2) {
            addCriterion("goods_name not between", value1, value2, "goodsName");
            return (Criteria) this;
        }

        public Criteria andScanTypeIsNull() {
            addCriterion("scan_type is null");
            return (Criteria) this;
        }

        public Criteria andScanTypeIsNotNull() {
            addCriterion("scan_type is not null");
            return (Criteria) this;
        }

        public Criteria andScanTypeEqualTo(String value) {
            addCriterion("scan_type =", value, "scanType");
            return (Criteria) this;
        }

        public Criteria andScanTypeNotEqualTo(String value) {
            addCriterion("scan_type <>", value, "scanType");
            return (Criteria) this;
        }

        public Criteria andScanTypeGreaterThan(String value) {
            addCriterion("scan_type >", value, "scanType");
            return (Criteria) this;
        }

        public Criteria andScanTypeGreaterThanOrEqualTo(String value) {
            addCriterion("scan_type >=", value, "scanType");
            return (Criteria) this;
        }

        public Criteria andScanTypeLessThan(String value) {
            addCriterion("scan_type <", value, "scanType");
            return (Criteria) this;
        }

        public Criteria andScanTypeLessThanOrEqualTo(String value) {
            addCriterion("scan_type <=", value, "scanType");
            return (Criteria) this;
        }

        public Criteria andScanTypeLike(String value) {
            addCriterion("scan_type like", value, "scanType");
            return (Criteria) this;
        }

        public Criteria andScanTypeNotLike(String value) {
            addCriterion("scan_type not like", value, "scanType");
            return (Criteria) this;
        }

        public Criteria andScanTypeIn(List<String> values) {
            addCriterion("scan_type in", values, "scanType");
            return (Criteria) this;
        }

        public Criteria andScanTypeNotIn(List<String> values) {
            addCriterion("scan_type not in", values, "scanType");
            return (Criteria) this;
        }

        public Criteria andScanTypeBetween(String value1, String value2) {
            addCriterion("scan_type between", value1, value2, "scanType");
            return (Criteria) this;
        }

        public Criteria andScanTypeNotBetween(String value1, String value2) {
            addCriterion("scan_type not between", value1, value2, "scanType");
            return (Criteria) this;
        }

        public Criteria andPayWayIsNull() {
            addCriterion("pay_way is null");
            return (Criteria) this;
        }

        public Criteria andPayWayIsNotNull() {
            addCriterion("pay_way is not null");
            return (Criteria) this;
        }

        public Criteria andPayWayEqualTo(String value) {
            addCriterion("pay_way =", value, "payWay");
            return (Criteria) this;
        }

        public Criteria andPayWayNotEqualTo(String value) {
            addCriterion("pay_way <>", value, "payWay");
            return (Criteria) this;
        }

        public Criteria andPayWayGreaterThan(String value) {
            addCriterion("pay_way >", value, "payWay");
            return (Criteria) this;
        }

        public Criteria andPayWayGreaterThanOrEqualTo(String value) {
            addCriterion("pay_way >=", value, "payWay");
            return (Criteria) this;
        }

        public Criteria andPayWayLessThan(String value) {
            addCriterion("pay_way <", value, "payWay");
            return (Criteria) this;
        }

        public Criteria andPayWayLessThanOrEqualTo(String value) {
            addCriterion("pay_way <=", value, "payWay");
            return (Criteria) this;
        }

        public Criteria andPayWayLike(String value) {
            addCriterion("pay_way like", value, "payWay");
            return (Criteria) this;
        }

        public Criteria andPayWayNotLike(String value) {
            addCriterion("pay_way not like", value, "payWay");
            return (Criteria) this;
        }

        public Criteria andPayWayIn(List<String> values) {
            addCriterion("pay_way in", values, "payWay");
            return (Criteria) this;
        }

        public Criteria andPayWayNotIn(List<String> values) {
            addCriterion("pay_way not in", values, "payWay");
            return (Criteria) this;
        }

        public Criteria andPayWayBetween(String value1, String value2) {
            addCriterion("pay_way between", value1, value2, "payWay");
            return (Criteria) this;
        }

        public Criteria andPayWayNotBetween(String value1, String value2) {
            addCriterion("pay_way not between", value1, value2, "payWay");
            return (Criteria) this;
        }

        public Criteria andPayTimeIsNull() {
            addCriterion("pay_time is null");
            return (Criteria) this;
        }

        public Criteria andPayTimeIsNotNull() {
            addCriterion("pay_time is not null");
            return (Criteria) this;
        }

        public Criteria andPayTimeEqualTo(Date value) {
            addCriterion("pay_time =", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeNotEqualTo(Date value) {
            addCriterion("pay_time <>", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeGreaterThan(Date value) {
            addCriterion("pay_time >", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("pay_time >=", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeLessThan(Date value) {
            addCriterion("pay_time <", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeLessThanOrEqualTo(Date value) {
            addCriterion("pay_time <=", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeIn(List<Date> values) {
            addCriterion("pay_time in", values, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeNotIn(List<Date> values) {
            addCriterion("pay_time not in", values, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeBetween(Date value1, Date value2) {
            addCriterion("pay_time between", value1, value2, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeNotBetween(Date value1, Date value2) {
            addCriterion("pay_time not between", value1, value2, "payTime");
            return (Criteria) this;
        }

        public Criteria andTradeStateIsNull() {
            addCriterion("trade_state is null");
            return (Criteria) this;
        }

        public Criteria andTradeStateIsNotNull() {
            addCriterion("trade_state is not null");
            return (Criteria) this;
        }

        public Criteria andTradeStateEqualTo(String value) {
            addCriterion("trade_state =", value, "tradeState");
            return (Criteria) this;
        }

        public Criteria andTradeStateNotEqualTo(String value) {
            addCriterion("trade_state <>", value, "tradeState");
            return (Criteria) this;
        }

        public Criteria andTradeStateGreaterThan(String value) {
            addCriterion("trade_state >", value, "tradeState");
            return (Criteria) this;
        }

        public Criteria andTradeStateGreaterThanOrEqualTo(String value) {
            addCriterion("trade_state >=", value, "tradeState");
            return (Criteria) this;
        }

        public Criteria andTradeStateLessThan(String value) {
            addCriterion("trade_state <", value, "tradeState");
            return (Criteria) this;
        }

        public Criteria andTradeStateLessThanOrEqualTo(String value) {
            addCriterion("trade_state <=", value, "tradeState");
            return (Criteria) this;
        }

        public Criteria andTradeStateLike(String value) {
            addCriterion("trade_state like", value, "tradeState");
            return (Criteria) this;
        }

        public Criteria andTradeStateNotLike(String value) {
            addCriterion("trade_state not like", value, "tradeState");
            return (Criteria) this;
        }

        public Criteria andTradeStateIn(List<String> values) {
            addCriterion("trade_state in", values, "tradeState");
            return (Criteria) this;
        }

        public Criteria andTradeStateNotIn(List<String> values) {
            addCriterion("trade_state not in", values, "tradeState");
            return (Criteria) this;
        }

        public Criteria andTradeStateBetween(String value1, String value2) {
            addCriterion("trade_state between", value1, value2, "tradeState");
            return (Criteria) this;
        }

        public Criteria andTradeStateNotBetween(String value1, String value2) {
            addCriterion("trade_state not between", value1, value2, "tradeState");
            return (Criteria) this;
        }

        public Criteria andIsSettledIsNull() {
            addCriterion("is_settled is null");
            return (Criteria) this;
        }

        public Criteria andIsSettledIsNotNull() {
            addCriterion("is_settled is not null");
            return (Criteria) this;
        }

        public Criteria andIsSettledEqualTo(Integer value) {
            addCriterion("is_settled =", value, "isSettled");
            return (Criteria) this;
        }

        public Criteria andIsSettledNotEqualTo(Integer value) {
            addCriterion("is_settled <>", value, "isSettled");
            return (Criteria) this;
        }

        public Criteria andIsSettledGreaterThan(Integer value) {
            addCriterion("is_settled >", value, "isSettled");
            return (Criteria) this;
        }

        public Criteria andIsSettledGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_settled >=", value, "isSettled");
            return (Criteria) this;
        }

        public Criteria andIsSettledLessThan(Integer value) {
            addCriterion("is_settled <", value, "isSettled");
            return (Criteria) this;
        }

        public Criteria andIsSettledLessThanOrEqualTo(Integer value) {
            addCriterion("is_settled <=", value, "isSettled");
            return (Criteria) this;
        }

        public Criteria andIsSettledIn(List<Integer> values) {
            addCriterion("is_settled in", values, "isSettled");
            return (Criteria) this;
        }

        public Criteria andIsSettledNotIn(List<Integer> values) {
            addCriterion("is_settled not in", values, "isSettled");
            return (Criteria) this;
        }

        public Criteria andIsSettledBetween(Integer value1, Integer value2) {
            addCriterion("is_settled between", value1, value2, "isSettled");
            return (Criteria) this;
        }

        public Criteria andIsSettledNotBetween(Integer value1, Integer value2) {
            addCriterion("is_settled not between", value1, value2, "isSettled");
            return (Criteria) this;
        }

        public Criteria andSettleTypeIsNull() {
            addCriterion("settle_type is null");
            return (Criteria) this;
        }

        public Criteria andSettleTypeIsNotNull() {
            addCriterion("settle_type is not null");
            return (Criteria) this;
        }

        public Criteria andSettleTypeEqualTo(String value) {
            addCriterion("settle_type =", value, "settleType");
            return (Criteria) this;
        }

        public Criteria andSettleTypeNotEqualTo(String value) {
            addCriterion("settle_type <>", value, "settleType");
            return (Criteria) this;
        }

        public Criteria andSettleTypeGreaterThan(String value) {
            addCriterion("settle_type >", value, "settleType");
            return (Criteria) this;
        }

        public Criteria andSettleTypeGreaterThanOrEqualTo(String value) {
            addCriterion("settle_type >=", value, "settleType");
            return (Criteria) this;
        }

        public Criteria andSettleTypeLessThan(String value) {
            addCriterion("settle_type <", value, "settleType");
            return (Criteria) this;
        }

        public Criteria andSettleTypeLessThanOrEqualTo(String value) {
            addCriterion("settle_type <=", value, "settleType");
            return (Criteria) this;
        }

        public Criteria andSettleTypeLike(String value) {
            addCriterion("settle_type like", value, "settleType");
            return (Criteria) this;
        }

        public Criteria andSettleTypeNotLike(String value) {
            addCriterion("settle_type not like", value, "settleType");
            return (Criteria) this;
        }

        public Criteria andSettleTypeIn(List<String> values) {
            addCriterion("settle_type in", values, "settleType");
            return (Criteria) this;
        }

        public Criteria andSettleTypeNotIn(List<String> values) {
            addCriterion("settle_type not in", values, "settleType");
            return (Criteria) this;
        }

        public Criteria andSettleTypeBetween(String value1, String value2) {
            addCriterion("settle_type between", value1, value2, "settleType");
            return (Criteria) this;
        }

        public Criteria andSettleTypeNotBetween(String value1, String value2) {
            addCriterion("settle_type not between", value1, value2, "settleType");
            return (Criteria) this;
        }

        public Criteria andRouteNoIsNull() {
            addCriterion("route_no is null");
            return (Criteria) this;
        }

        public Criteria andRouteNoIsNotNull() {
            addCriterion("route_no is not null");
            return (Criteria) this;
        }

        public Criteria andRouteNoEqualTo(String value) {
            addCriterion("route_no =", value, "routeNo");
            return (Criteria) this;
        }

        public Criteria andRouteNoNotEqualTo(String value) {
            addCriterion("route_no <>", value, "routeNo");
            return (Criteria) this;
        }

        public Criteria andRouteNoGreaterThan(String value) {
            addCriterion("route_no >", value, "routeNo");
            return (Criteria) this;
        }

        public Criteria andRouteNoGreaterThanOrEqualTo(String value) {
            addCriterion("route_no >=", value, "routeNo");
            return (Criteria) this;
        }

        public Criteria andRouteNoLessThan(String value) {
            addCriterion("route_no <", value, "routeNo");
            return (Criteria) this;
        }

        public Criteria andRouteNoLessThanOrEqualTo(String value) {
            addCriterion("route_no <=", value, "routeNo");
            return (Criteria) this;
        }

        public Criteria andRouteNoLike(String value) {
            addCriterion("route_no like", value, "routeNo");
            return (Criteria) this;
        }

        public Criteria andRouteNoNotLike(String value) {
            addCriterion("route_no not like", value, "routeNo");
            return (Criteria) this;
        }

        public Criteria andRouteNoIn(List<String> values) {
            addCriterion("route_no in", values, "routeNo");
            return (Criteria) this;
        }

        public Criteria andRouteNoNotIn(List<String> values) {
            addCriterion("route_no not in", values, "routeNo");
            return (Criteria) this;
        }

        public Criteria andRouteNoBetween(String value1, String value2) {
            addCriterion("route_no between", value1, value2, "routeNo");
            return (Criteria) this;
        }

        public Criteria andRouteNoNotBetween(String value1, String value2) {
            addCriterion("route_no not between", value1, value2, "routeNo");
            return (Criteria) this;
        }

        public Criteria andRouteMerchantNoIsNull() {
            addCriterion("route_merchant_no is null");
            return (Criteria) this;
        }

        public Criteria andRouteMerchantNoIsNotNull() {
            addCriterion("route_merchant_no is not null");
            return (Criteria) this;
        }

        public Criteria andRouteMerchantNoEqualTo(String value) {
            addCriterion("route_merchant_no =", value, "routeMerchantNo");
            return (Criteria) this;
        }

        public Criteria andRouteMerchantNoNotEqualTo(String value) {
            addCriterion("route_merchant_no <>", value, "routeMerchantNo");
            return (Criteria) this;
        }

        public Criteria andRouteMerchantNoGreaterThan(String value) {
            addCriterion("route_merchant_no >", value, "routeMerchantNo");
            return (Criteria) this;
        }

        public Criteria andRouteMerchantNoGreaterThanOrEqualTo(String value) {
            addCriterion("route_merchant_no >=", value, "routeMerchantNo");
            return (Criteria) this;
        }

        public Criteria andRouteMerchantNoLessThan(String value) {
            addCriterion("route_merchant_no <", value, "routeMerchantNo");
            return (Criteria) this;
        }

        public Criteria andRouteMerchantNoLessThanOrEqualTo(String value) {
            addCriterion("route_merchant_no <=", value, "routeMerchantNo");
            return (Criteria) this;
        }

        public Criteria andRouteMerchantNoLike(String value) {
            addCriterion("route_merchant_no like", value, "routeMerchantNo");
            return (Criteria) this;
        }

        public Criteria andRouteMerchantNoNotLike(String value) {
            addCriterion("route_merchant_no not like", value, "routeMerchantNo");
            return (Criteria) this;
        }

        public Criteria andRouteMerchantNoIn(List<String> values) {
            addCriterion("route_merchant_no in", values, "routeMerchantNo");
            return (Criteria) this;
        }

        public Criteria andRouteMerchantNoNotIn(List<String> values) {
            addCriterion("route_merchant_no not in", values, "routeMerchantNo");
            return (Criteria) this;
        }

        public Criteria andRouteMerchantNoBetween(String value1, String value2) {
            addCriterion("route_merchant_no between", value1, value2, "routeMerchantNo");
            return (Criteria) this;
        }

        public Criteria andRouteMerchantNoNotBetween(String value1, String value2) {
            addCriterion("route_merchant_no not between", value1, value2, "routeMerchantNo");
            return (Criteria) this;
        }

        public Criteria andRouteOrderNoIsNull() {
            addCriterion("route_order_no is null");
            return (Criteria) this;
        }

        public Criteria andRouteOrderNoIsNotNull() {
            addCriterion("route_order_no is not null");
            return (Criteria) this;
        }

        public Criteria andRouteOrderNoEqualTo(String value) {
            addCriterion("route_order_no =", value, "routeOrderNo");
            return (Criteria) this;
        }

        public Criteria andRouteOrderNoNotEqualTo(String value) {
            addCriterion("route_order_no <>", value, "routeOrderNo");
            return (Criteria) this;
        }

        public Criteria andRouteOrderNoGreaterThan(String value) {
            addCriterion("route_order_no >", value, "routeOrderNo");
            return (Criteria) this;
        }

        public Criteria andRouteOrderNoGreaterThanOrEqualTo(String value) {
            addCriterion("route_order_no >=", value, "routeOrderNo");
            return (Criteria) this;
        }

        public Criteria andRouteOrderNoLessThan(String value) {
            addCriterion("route_order_no <", value, "routeOrderNo");
            return (Criteria) this;
        }

        public Criteria andRouteOrderNoLessThanOrEqualTo(String value) {
            addCriterion("route_order_no <=", value, "routeOrderNo");
            return (Criteria) this;
        }

        public Criteria andRouteOrderNoLike(String value) {
            addCriterion("route_order_no like", value, "routeOrderNo");
            return (Criteria) this;
        }

        public Criteria andRouteOrderNoNotLike(String value) {
            addCriterion("route_order_no not like", value, "routeOrderNo");
            return (Criteria) this;
        }

        public Criteria andRouteOrderNoIn(List<String> values) {
            addCriterion("route_order_no in", values, "routeOrderNo");
            return (Criteria) this;
        }

        public Criteria andRouteOrderNoNotIn(List<String> values) {
            addCriterion("route_order_no not in", values, "routeOrderNo");
            return (Criteria) this;
        }

        public Criteria andRouteOrderNoBetween(String value1, String value2) {
            addCriterion("route_order_no between", value1, value2, "routeOrderNo");
            return (Criteria) this;
        }

        public Criteria andRouteOrderNoNotBetween(String value1, String value2) {
            addCriterion("route_order_no not between", value1, value2, "routeOrderNo");
            return (Criteria) this;
        }

        public Criteria andRouteSerialNoIsNull() {
            addCriterion("route_serial_no is null");
            return (Criteria) this;
        }

        public Criteria andRouteSerialNoIsNotNull() {
            addCriterion("route_serial_no is not null");
            return (Criteria) this;
        }

        public Criteria andRouteSerialNoEqualTo(String value) {
            addCriterion("route_serial_no =", value, "routeSerialNo");
            return (Criteria) this;
        }

        public Criteria andRouteSerialNoNotEqualTo(String value) {
            addCriterion("route_serial_no <>", value, "routeSerialNo");
            return (Criteria) this;
        }

        public Criteria andRouteSerialNoGreaterThan(String value) {
            addCriterion("route_serial_no >", value, "routeSerialNo");
            return (Criteria) this;
        }

        public Criteria andRouteSerialNoGreaterThanOrEqualTo(String value) {
            addCriterion("route_serial_no >=", value, "routeSerialNo");
            return (Criteria) this;
        }

        public Criteria andRouteSerialNoLessThan(String value) {
            addCriterion("route_serial_no <", value, "routeSerialNo");
            return (Criteria) this;
        }

        public Criteria andRouteSerialNoLessThanOrEqualTo(String value) {
            addCriterion("route_serial_no <=", value, "routeSerialNo");
            return (Criteria) this;
        }

        public Criteria andRouteSerialNoLike(String value) {
            addCriterion("route_serial_no like", value, "routeSerialNo");
            return (Criteria) this;
        }

        public Criteria andRouteSerialNoNotLike(String value) {
            addCriterion("route_serial_no not like", value, "routeSerialNo");
            return (Criteria) this;
        }

        public Criteria andRouteSerialNoIn(List<String> values) {
            addCriterion("route_serial_no in", values, "routeSerialNo");
            return (Criteria) this;
        }

        public Criteria andRouteSerialNoNotIn(List<String> values) {
            addCriterion("route_serial_no not in", values, "routeSerialNo");
            return (Criteria) this;
        }

        public Criteria andRouteSerialNoBetween(String value1, String value2) {
            addCriterion("route_serial_no between", value1, value2, "routeSerialNo");
            return (Criteria) this;
        }

        public Criteria andRouteSerialNoNotBetween(String value1, String value2) {
            addCriterion("route_serial_no not between", value1, value2, "routeSerialNo");
            return (Criteria) this;
        }

        public Criteria andRoutePoundageIsNull() {
            addCriterion("route_poundage is null");
            return (Criteria) this;
        }

        public Criteria andRoutePoundageIsNotNull() {
            addCriterion("route_poundage is not null");
            return (Criteria) this;
        }

        public Criteria andRoutePoundageEqualTo(BigDecimal value) {
            addCriterion("route_poundage =", value, "routePoundage");
            return (Criteria) this;
        }

        public Criteria andRoutePoundageNotEqualTo(BigDecimal value) {
            addCriterion("route_poundage <>", value, "routePoundage");
            return (Criteria) this;
        }

        public Criteria andRoutePoundageGreaterThan(BigDecimal value) {
            addCriterion("route_poundage >", value, "routePoundage");
            return (Criteria) this;
        }

        public Criteria andRoutePoundageGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("route_poundage >=", value, "routePoundage");
            return (Criteria) this;
        }

        public Criteria andRoutePoundageLessThan(BigDecimal value) {
            addCriterion("route_poundage <", value, "routePoundage");
            return (Criteria) this;
        }

        public Criteria andRoutePoundageLessThanOrEqualTo(BigDecimal value) {
            addCriterion("route_poundage <=", value, "routePoundage");
            return (Criteria) this;
        }

        public Criteria andRoutePoundageIn(List<BigDecimal> values) {
            addCriterion("route_poundage in", values, "routePoundage");
            return (Criteria) this;
        }

        public Criteria andRoutePoundageNotIn(List<BigDecimal> values) {
            addCriterion("route_poundage not in", values, "routePoundage");
            return (Criteria) this;
        }

        public Criteria andRoutePoundageBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("route_poundage between", value1, value2, "routePoundage");
            return (Criteria) this;
        }

        public Criteria andRoutePoundageNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("route_poundage not between", value1, value2, "routePoundage");
            return (Criteria) this;
        }

        public Criteria andFailreasonIsNull() {
            addCriterion("failreason is null");
            return (Criteria) this;
        }

        public Criteria andFailreasonIsNotNull() {
            addCriterion("failreason is not null");
            return (Criteria) this;
        }

        public Criteria andFailreasonEqualTo(String value) {
            addCriterion("failreason =", value, "failreason");
            return (Criteria) this;
        }

        public Criteria andFailreasonNotEqualTo(String value) {
            addCriterion("failreason <>", value, "failreason");
            return (Criteria) this;
        }

        public Criteria andFailreasonGreaterThan(String value) {
            addCriterion("failreason >", value, "failreason");
            return (Criteria) this;
        }

        public Criteria andFailreasonGreaterThanOrEqualTo(String value) {
            addCriterion("failreason >=", value, "failreason");
            return (Criteria) this;
        }

        public Criteria andFailreasonLessThan(String value) {
            addCriterion("failreason <", value, "failreason");
            return (Criteria) this;
        }

        public Criteria andFailreasonLessThanOrEqualTo(String value) {
            addCriterion("failreason <=", value, "failreason");
            return (Criteria) this;
        }

        public Criteria andFailreasonLike(String value) {
            addCriterion("failreason like", value, "failreason");
            return (Criteria) this;
        }

        public Criteria andFailreasonNotLike(String value) {
            addCriterion("failreason not like", value, "failreason");
            return (Criteria) this;
        }

        public Criteria andFailreasonIn(List<String> values) {
            addCriterion("failreason in", values, "failreason");
            return (Criteria) this;
        }

        public Criteria andFailreasonNotIn(List<String> values) {
            addCriterion("failreason not in", values, "failreason");
            return (Criteria) this;
        }

        public Criteria andFailreasonBetween(String value1, String value2) {
            addCriterion("failreason between", value1, value2, "failreason");
            return (Criteria) this;
        }

        public Criteria andFailreasonNotBetween(String value1, String value2) {
            addCriterion("failreason not between", value1, value2, "failreason");
            return (Criteria) this;
        }

        public Criteria andRemarksIsNull() {
            addCriterion("remarks is null");
            return (Criteria) this;
        }

        public Criteria andRemarksIsNotNull() {
            addCriterion("remarks is not null");
            return (Criteria) this;
        }

        public Criteria andRemarksEqualTo(String value) {
            addCriterion("remarks =", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksNotEqualTo(String value) {
            addCriterion("remarks <>", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksGreaterThan(String value) {
            addCriterion("remarks >", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksGreaterThanOrEqualTo(String value) {
            addCriterion("remarks >=", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksLessThan(String value) {
            addCriterion("remarks <", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksLessThanOrEqualTo(String value) {
            addCriterion("remarks <=", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksLike(String value) {
            addCriterion("remarks like", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksNotLike(String value) {
            addCriterion("remarks not like", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksIn(List<String> values) {
            addCriterion("remarks in", values, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksNotIn(List<String> values) {
            addCriterion("remarks not in", values, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksBetween(String value1, String value2) {
            addCriterion("remarks between", value1, value2, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksNotBetween(String value1, String value2) {
            addCriterion("remarks not between", value1, value2, "remarks");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}