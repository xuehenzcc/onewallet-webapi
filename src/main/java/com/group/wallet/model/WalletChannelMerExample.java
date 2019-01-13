package com.group.wallet.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WalletChannelMerExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public WalletChannelMerExample() {
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

        public Criteria andChannelIdIsNull() {
            addCriterion("channel_id is null");
            return (Criteria) this;
        }

        public Criteria andChannelIdIsNotNull() {
            addCriterion("channel_id is not null");
            return (Criteria) this;
        }

        public Criteria andChannelIdEqualTo(Long value) {
            addCriterion("channel_id =", value, "channelId");
            return (Criteria) this;
        }

        public Criteria andChannelIdNotEqualTo(Long value) {
            addCriterion("channel_id <>", value, "channelId");
            return (Criteria) this;
        }

        public Criteria andChannelIdGreaterThan(Long value) {
            addCriterion("channel_id >", value, "channelId");
            return (Criteria) this;
        }

        public Criteria andChannelIdGreaterThanOrEqualTo(Long value) {
            addCriterion("channel_id >=", value, "channelId");
            return (Criteria) this;
        }

        public Criteria andChannelIdLessThan(Long value) {
            addCriterion("channel_id <", value, "channelId");
            return (Criteria) this;
        }

        public Criteria andChannelIdLessThanOrEqualTo(Long value) {
            addCriterion("channel_id <=", value, "channelId");
            return (Criteria) this;
        }

        public Criteria andChannelIdIn(List<Long> values) {
            addCriterion("channel_id in", values, "channelId");
            return (Criteria) this;
        }

        public Criteria andChannelIdNotIn(List<Long> values) {
            addCriterion("channel_id not in", values, "channelId");
            return (Criteria) this;
        }

        public Criteria andChannelIdBetween(Long value1, Long value2) {
            addCriterion("channel_id between", value1, value2, "channelId");
            return (Criteria) this;
        }

        public Criteria andChannelIdNotBetween(Long value1, Long value2) {
            addCriterion("channel_id not between", value1, value2, "channelId");
            return (Criteria) this;
        }

        public Criteria andTypeIsNull() {
            addCriterion("type is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("type is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(String value) {
            addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(String value) {
            addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(String value) {
            addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(String value) {
            addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(String value) {
            addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(String value) {
            addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLike(String value) {
            addCriterion("type like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotLike(String value) {
            addCriterion("type not like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<String> values) {
            addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<String> values) {
            addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(String value1, String value2) {
            addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(String value1, String value2) {
            addCriterion("type not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andChannelMerNoIsNull() {
            addCriterion("channel_mer_no is null");
            return (Criteria) this;
        }

        public Criteria andChannelMerNoIsNotNull() {
            addCriterion("channel_mer_no is not null");
            return (Criteria) this;
        }

        public Criteria andChannelMerNoEqualTo(String value) {
            addCriterion("channel_mer_no =", value, "channelMerNo");
            return (Criteria) this;
        }

        public Criteria andChannelMerNoNotEqualTo(String value) {
            addCriterion("channel_mer_no <>", value, "channelMerNo");
            return (Criteria) this;
        }

        public Criteria andChannelMerNoGreaterThan(String value) {
            addCriterion("channel_mer_no >", value, "channelMerNo");
            return (Criteria) this;
        }

        public Criteria andChannelMerNoGreaterThanOrEqualTo(String value) {
            addCriterion("channel_mer_no >=", value, "channelMerNo");
            return (Criteria) this;
        }

        public Criteria andChannelMerNoLessThan(String value) {
            addCriterion("channel_mer_no <", value, "channelMerNo");
            return (Criteria) this;
        }

        public Criteria andChannelMerNoLessThanOrEqualTo(String value) {
            addCriterion("channel_mer_no <=", value, "channelMerNo");
            return (Criteria) this;
        }

        public Criteria andChannelMerNoLike(String value) {
            addCriterion("channel_mer_no like", value, "channelMerNo");
            return (Criteria) this;
        }

        public Criteria andChannelMerNoNotLike(String value) {
            addCriterion("channel_mer_no not like", value, "channelMerNo");
            return (Criteria) this;
        }

        public Criteria andChannelMerNoIn(List<String> values) {
            addCriterion("channel_mer_no in", values, "channelMerNo");
            return (Criteria) this;
        }

        public Criteria andChannelMerNoNotIn(List<String> values) {
            addCriterion("channel_mer_no not in", values, "channelMerNo");
            return (Criteria) this;
        }

        public Criteria andChannelMerNoBetween(String value1, String value2) {
            addCriterion("channel_mer_no between", value1, value2, "channelMerNo");
            return (Criteria) this;
        }

        public Criteria andChannelMerNoNotBetween(String value1, String value2) {
            addCriterion("channel_mer_no not between", value1, value2, "channelMerNo");
            return (Criteria) this;
        }

        public Criteria andChannelMerNameIsNull() {
            addCriterion("channel_mer_name is null");
            return (Criteria) this;
        }

        public Criteria andChannelMerNameIsNotNull() {
            addCriterion("channel_mer_name is not null");
            return (Criteria) this;
        }

        public Criteria andChannelMerNameEqualTo(String value) {
            addCriterion("channel_mer_name =", value, "channelMerName");
            return (Criteria) this;
        }

        public Criteria andChannelMerNameNotEqualTo(String value) {
            addCriterion("channel_mer_name <>", value, "channelMerName");
            return (Criteria) this;
        }

        public Criteria andChannelMerNameGreaterThan(String value) {
            addCriterion("channel_mer_name >", value, "channelMerName");
            return (Criteria) this;
        }

        public Criteria andChannelMerNameGreaterThanOrEqualTo(String value) {
            addCriterion("channel_mer_name >=", value, "channelMerName");
            return (Criteria) this;
        }

        public Criteria andChannelMerNameLessThan(String value) {
            addCriterion("channel_mer_name <", value, "channelMerName");
            return (Criteria) this;
        }

        public Criteria andChannelMerNameLessThanOrEqualTo(String value) {
            addCriterion("channel_mer_name <=", value, "channelMerName");
            return (Criteria) this;
        }

        public Criteria andChannelMerNameLike(String value) {
            addCriterion("channel_mer_name like", value, "channelMerName");
            return (Criteria) this;
        }

        public Criteria andChannelMerNameNotLike(String value) {
            addCriterion("channel_mer_name not like", value, "channelMerName");
            return (Criteria) this;
        }

        public Criteria andChannelMerNameIn(List<String> values) {
            addCriterion("channel_mer_name in", values, "channelMerName");
            return (Criteria) this;
        }

        public Criteria andChannelMerNameNotIn(List<String> values) {
            addCriterion("channel_mer_name not in", values, "channelMerName");
            return (Criteria) this;
        }

        public Criteria andChannelMerNameBetween(String value1, String value2) {
            addCriterion("channel_mer_name between", value1, value2, "channelMerName");
            return (Criteria) this;
        }

        public Criteria andChannelMerNameNotBetween(String value1, String value2) {
            addCriterion("channel_mer_name not between", value1, value2, "channelMerName");
            return (Criteria) this;
        }

        public Criteria andWalletMerNoIsNull() {
            addCriterion("wallet_mer_no is null");
            return (Criteria) this;
        }

        public Criteria andWalletMerNoIsNotNull() {
            addCriterion("wallet_mer_no is not null");
            return (Criteria) this;
        }

        public Criteria andWalletMerNoEqualTo(String value) {
            addCriterion("wallet_mer_no =", value, "walletMerNo");
            return (Criteria) this;
        }

        public Criteria andWalletMerNoNotEqualTo(String value) {
            addCriterion("wallet_mer_no <>", value, "walletMerNo");
            return (Criteria) this;
        }

        public Criteria andWalletMerNoGreaterThan(String value) {
            addCriterion("wallet_mer_no >", value, "walletMerNo");
            return (Criteria) this;
        }

        public Criteria andWalletMerNoGreaterThanOrEqualTo(String value) {
            addCriterion("wallet_mer_no >=", value, "walletMerNo");
            return (Criteria) this;
        }

        public Criteria andWalletMerNoLessThan(String value) {
            addCriterion("wallet_mer_no <", value, "walletMerNo");
            return (Criteria) this;
        }

        public Criteria andWalletMerNoLessThanOrEqualTo(String value) {
            addCriterion("wallet_mer_no <=", value, "walletMerNo");
            return (Criteria) this;
        }

        public Criteria andWalletMerNoLike(String value) {
            addCriterion("wallet_mer_no like", value, "walletMerNo");
            return (Criteria) this;
        }

        public Criteria andWalletMerNoNotLike(String value) {
            addCriterion("wallet_mer_no not like", value, "walletMerNo");
            return (Criteria) this;
        }

        public Criteria andWalletMerNoIn(List<String> values) {
            addCriterion("wallet_mer_no in", values, "walletMerNo");
            return (Criteria) this;
        }

        public Criteria andWalletMerNoNotIn(List<String> values) {
            addCriterion("wallet_mer_no not in", values, "walletMerNo");
            return (Criteria) this;
        }

        public Criteria andWalletMerNoBetween(String value1, String value2) {
            addCriterion("wallet_mer_no between", value1, value2, "walletMerNo");
            return (Criteria) this;
        }

        public Criteria andWalletMerNoNotBetween(String value1, String value2) {
            addCriterion("wallet_mer_no not between", value1, value2, "walletMerNo");
            return (Criteria) this;
        }

        public Criteria andMd5KeyIsNull() {
            addCriterion("md5_key is null");
            return (Criteria) this;
        }

        public Criteria andMd5KeyIsNotNull() {
            addCriterion("md5_key is not null");
            return (Criteria) this;
        }

        public Criteria andMd5KeyEqualTo(String value) {
            addCriterion("md5_key =", value, "md5Key");
            return (Criteria) this;
        }

        public Criteria andMd5KeyNotEqualTo(String value) {
            addCriterion("md5_key <>", value, "md5Key");
            return (Criteria) this;
        }

        public Criteria andMd5KeyGreaterThan(String value) {
            addCriterion("md5_key >", value, "md5Key");
            return (Criteria) this;
        }

        public Criteria andMd5KeyGreaterThanOrEqualTo(String value) {
            addCriterion("md5_key >=", value, "md5Key");
            return (Criteria) this;
        }

        public Criteria andMd5KeyLessThan(String value) {
            addCriterion("md5_key <", value, "md5Key");
            return (Criteria) this;
        }

        public Criteria andMd5KeyLessThanOrEqualTo(String value) {
            addCriterion("md5_key <=", value, "md5Key");
            return (Criteria) this;
        }

        public Criteria andMd5KeyLike(String value) {
            addCriterion("md5_key like", value, "md5Key");
            return (Criteria) this;
        }

        public Criteria andMd5KeyNotLike(String value) {
            addCriterion("md5_key not like", value, "md5Key");
            return (Criteria) this;
        }

        public Criteria andMd5KeyIn(List<String> values) {
            addCriterion("md5_key in", values, "md5Key");
            return (Criteria) this;
        }

        public Criteria andMd5KeyNotIn(List<String> values) {
            addCriterion("md5_key not in", values, "md5Key");
            return (Criteria) this;
        }

        public Criteria andMd5KeyBetween(String value1, String value2) {
            addCriterion("md5_key between", value1, value2, "md5Key");
            return (Criteria) this;
        }

        public Criteria andMd5KeyNotBetween(String value1, String value2) {
            addCriterion("md5_key not between", value1, value2, "md5Key");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
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