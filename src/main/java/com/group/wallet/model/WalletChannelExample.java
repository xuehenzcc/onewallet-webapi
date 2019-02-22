//package com.group.wallet.model;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class WalletChannelExample {
//    protected String orderByClause;
//
//    protected boolean distinct;
//
//    protected List<Criteria> oredCriteria;
//
//    public WalletChannelExample() {
//        oredCriteria = new ArrayList<Criteria>();
//    }
//
//    public void setOrderByClause(String orderByClause) {
//        this.orderByClause = orderByClause;
//    }
//
//    public String getOrderByClause() {
//        return orderByClause;
//    }
//
//    public void setDistinct(boolean distinct) {
//        this.distinct = distinct;
//    }
//
//    public boolean isDistinct() {
//        return distinct;
//    }
//
//    public List<Criteria> getOredCriteria() {
//        return oredCriteria;
//    }
//
//    public void or(Criteria criteria) {
//        oredCriteria.add(criteria);
//    }
//
//    public Criteria or() {
//        Criteria criteria = createCriteriaInternal();
//        oredCriteria.add(criteria);
//        return criteria;
//    }
//
//    public Criteria createCriteria() {
//        Criteria criteria = createCriteriaInternal();
//        if (oredCriteria.size() == 0) {
//            oredCriteria.add(criteria);
//        }
//        return criteria;
//    }
//
//    protected Criteria createCriteriaInternal() {
//        Criteria criteria = new Criteria();
//        return criteria;
//    }
//
//    public void clear() {
//        oredCriteria.clear();
//        orderByClause = null;
//        distinct = false;
//    }
//
//    protected abstract static class GeneratedCriteria {
//        protected List<Criterion> criteria;
//
//        protected GeneratedCriteria() {
//            super();
//            criteria = new ArrayList<Criterion>();
//        }
//
//        public boolean isValid() {
//            return criteria.size() > 0;
//        }
//
//        public List<Criterion> getAllCriteria() {
//            return criteria;
//        }
//
//        public List<Criterion> getCriteria() {
//            return criteria;
//        }
//
//        protected void addCriterion(String condition) {
//            if (condition == null) {
//                throw new RuntimeException("Value for condition cannot be null");
//            }
//            criteria.add(new Criterion(condition));
//        }
//
//        protected void addCriterion(String condition, Object value, String property) {
//            if (value == null) {
//                throw new RuntimeException("Value for " + property + " cannot be null");
//            }
//            criteria.add(new Criterion(condition, value));
//        }
//
//        protected void addCriterion(String condition, Object value1, Object value2, String property) {
//            if (value1 == null || value2 == null) {
//                throw new RuntimeException("Between values for " + property + " cannot be null");
//            }
//            criteria.add(new Criterion(condition, value1, value2));
//        }
//
//        public Criteria andIdIsNull() {
//            addCriterion("id is null");
//            return (Criteria) this;
//        }
//
//        public Criteria andIdIsNotNull() {
//            addCriterion("id is not null");
//            return (Criteria) this;
//        }
//
//        public Criteria andIdEqualTo(Long value) {
//            addCriterion("id =", value, "id");
//            return (Criteria) this;
//        }
//
//        public Criteria andIdNotEqualTo(Long value) {
//            addCriterion("id <>", value, "id");
//            return (Criteria) this;
//        }
//
//        public Criteria andIdGreaterThan(Long value) {
//            addCriterion("id >", value, "id");
//            return (Criteria) this;
//        }
//
//        public Criteria andIdGreaterThanOrEqualTo(Long value) {
//            addCriterion("id >=", value, "id");
//            return (Criteria) this;
//        }
//
//        public Criteria andIdLessThan(Long value) {
//            addCriterion("id <", value, "id");
//            return (Criteria) this;
//        }
//
//        public Criteria andIdLessThanOrEqualTo(Long value) {
//            addCriterion("id <=", value, "id");
//            return (Criteria) this;
//        }
//
//        public Criteria andIdIn(List<Long> values) {
//            addCriterion("id in", values, "id");
//            return (Criteria) this;
//        }
//
//        public Criteria andIdNotIn(List<Long> values) {
//            addCriterion("id not in", values, "id");
//            return (Criteria) this;
//        }
//
//        public Criteria andIdBetween(Long value1, Long value2) {
//            addCriterion("id between", value1, value2, "id");
//            return (Criteria) this;
//        }
//
//        public Criteria andIdNotBetween(Long value1, Long value2) {
//            addCriterion("id not between", value1, value2, "id");
//            return (Criteria) this;
//        }
//
//        public Criteria andNumberIsNull() {
//            addCriterion("number is null");
//            return (Criteria) this;
//        }
//
//        public Criteria andNumberIsNotNull() {
//            addCriterion("number is not null");
//            return (Criteria) this;
//        }
//
//        public Criteria andNumberEqualTo(String value) {
//            addCriterion("number =", value, "number");
//            return (Criteria) this;
//        }
//
//        public Criteria andNumberNotEqualTo(String value) {
//            addCriterion("number <>", value, "number");
//            return (Criteria) this;
//        }
//
//        public Criteria andNumberGreaterThan(String value) {
//            addCriterion("number >", value, "number");
//            return (Criteria) this;
//        }
//
//        public Criteria andNumberGreaterThanOrEqualTo(String value) {
//            addCriterion("number >=", value, "number");
//            return (Criteria) this;
//        }
//
//        public Criteria andNumberLessThan(String value) {
//            addCriterion("number <", value, "number");
//            return (Criteria) this;
//        }
//
//        public Criteria andNumberLessThanOrEqualTo(String value) {
//            addCriterion("number <=", value, "number");
//            return (Criteria) this;
//        }
//
//        public Criteria andNumberLike(String value) {
//            addCriterion("number like", value, "number");
//            return (Criteria) this;
//        }
//
//        public Criteria andNumberNotLike(String value) {
//            addCriterion("number not like", value, "number");
//            return (Criteria) this;
//        }
//
//        public Criteria andNumberIn(List<String> values) {
//            addCriterion("number in", values, "number");
//            return (Criteria) this;
//        }
//
//        public Criteria andNumberNotIn(List<String> values) {
//            addCriterion("number not in", values, "number");
//            return (Criteria) this;
//        }
//
//        public Criteria andNumberBetween(String value1, String value2) {
//            addCriterion("number between", value1, value2, "number");
//            return (Criteria) this;
//        }
//
//        public Criteria andNumberNotBetween(String value1, String value2) {
//            addCriterion("number not between", value1, value2, "number");
//            return (Criteria) this;
//        }
//
//        public Criteria andNameIsNull() {
//            addCriterion("name is null");
//            return (Criteria) this;
//        }
//
//        public Criteria andNameIsNotNull() {
//            addCriterion("name is not null");
//            return (Criteria) this;
//        }
//
//        public Criteria andNameEqualTo(String value) {
//            addCriterion("name =", value, "name");
//            return (Criteria) this;
//        }
//
//        public Criteria andNameNotEqualTo(String value) {
//            addCriterion("name <>", value, "name");
//            return (Criteria) this;
//        }
//
//        public Criteria andNameGreaterThan(String value) {
//            addCriterion("name >", value, "name");
//            return (Criteria) this;
//        }
//
//        public Criteria andNameGreaterThanOrEqualTo(String value) {
//            addCriterion("name >=", value, "name");
//            return (Criteria) this;
//        }
//
//        public Criteria andNameLessThan(String value) {
//            addCriterion("name <", value, "name");
//            return (Criteria) this;
//        }
//
//        public Criteria andNameLessThanOrEqualTo(String value) {
//            addCriterion("name <=", value, "name");
//            return (Criteria) this;
//        }
//
//        public Criteria andNameLike(String value) {
//            addCriterion("name like", value, "name");
//            return (Criteria) this;
//        }
//
//        public Criteria andNameNotLike(String value) {
//            addCriterion("name not like", value, "name");
//            return (Criteria) this;
//        }
//
//        public Criteria andNameIn(List<String> values) {
//            addCriterion("name in", values, "name");
//            return (Criteria) this;
//        }
//
//        public Criteria andNameNotIn(List<String> values) {
//            addCriterion("name not in", values, "name");
//            return (Criteria) this;
//        }
//
//        public Criteria andNameBetween(String value1, String value2) {
//            addCriterion("name between", value1, value2, "name");
//            return (Criteria) this;
//        }
//
//        public Criteria andNameNotBetween(String value1, String value2) {
//            addCriterion("name not between", value1, value2, "name");
//            return (Criteria) this;
//        }
//
//        public Criteria andSettleTypeIsNull() {
//            addCriterion("settle_type is null");
//            return (Criteria) this;
//        }
//
//        public Criteria andSettleTypeIsNotNull() {
//            addCriterion("settle_type is not null");
//            return (Criteria) this;
//        }
//
//        public Criteria andSettleTypeEqualTo(String value) {
//            addCriterion("settle_type =", value, "settleType");
//            return (Criteria) this;
//        }
//
//        public Criteria andSettleTypeNotEqualTo(String value) {
//            addCriterion("settle_type <>", value, "settleType");
//            return (Criteria) this;
//        }
//
//        public Criteria andSettleTypeGreaterThan(String value) {
//            addCriterion("settle_type >", value, "settleType");
//            return (Criteria) this;
//        }
//
//        public Criteria andSettleTypeGreaterThanOrEqualTo(String value) {
//            addCriterion("settle_type >=", value, "settleType");
//            return (Criteria) this;
//        }
//
//        public Criteria andSettleTypeLessThan(String value) {
//            addCriterion("settle_type <", value, "settleType");
//            return (Criteria) this;
//        }
//
//        public Criteria andSettleTypeLessThanOrEqualTo(String value) {
//            addCriterion("settle_type <=", value, "settleType");
//            return (Criteria) this;
//        }
//
//        public Criteria andSettleTypeLike(String value) {
//            addCriterion("settle_type like", value, "settleType");
//            return (Criteria) this;
//        }
//
//        public Criteria andSettleTypeNotLike(String value) {
//            addCriterion("settle_type not like", value, "settleType");
//            return (Criteria) this;
//        }
//
//        public Criteria andSettleTypeIn(List<String> values) {
//            addCriterion("settle_type in", values, "settleType");
//            return (Criteria) this;
//        }
//
//        public Criteria andSettleTypeNotIn(List<String> values) {
//            addCriterion("settle_type not in", values, "settleType");
//            return (Criteria) this;
//        }
//
//        public Criteria andSettleTypeBetween(String value1, String value2) {
//            addCriterion("settle_type between", value1, value2, "settleType");
//            return (Criteria) this;
//        }
//
//        public Criteria andSettleTypeNotBetween(String value1, String value2) {
//            addCriterion("settle_type not between", value1, value2, "settleType");
//            return (Criteria) this;
//        }
//
//        public Criteria andChannelUrlIsNull() {
//            addCriterion("channel_url is null");
//            return (Criteria) this;
//        }
//
//        public Criteria andChannelUrlIsNotNull() {
//            addCriterion("channel_url is not null");
//            return (Criteria) this;
//        }
//
//        public Criteria andChannelUrlEqualTo(String value) {
//            addCriterion("channel_url =", value, "channelUrl");
//            return (Criteria) this;
//        }
//
//        public Criteria andChannelUrlNotEqualTo(String value) {
//            addCriterion("channel_url <>", value, "channelUrl");
//            return (Criteria) this;
//        }
//
//        public Criteria andChannelUrlGreaterThan(String value) {
//            addCriterion("channel_url >", value, "channelUrl");
//            return (Criteria) this;
//        }
//
//        public Criteria andChannelUrlGreaterThanOrEqualTo(String value) {
//            addCriterion("channel_url >=", value, "channelUrl");
//            return (Criteria) this;
//        }
//
//        public Criteria andChannelUrlLessThan(String value) {
//            addCriterion("channel_url <", value, "channelUrl");
//            return (Criteria) this;
//        }
//
//        public Criteria andChannelUrlLessThanOrEqualTo(String value) {
//            addCriterion("channel_url <=", value, "channelUrl");
//            return (Criteria) this;
//        }
//
//        public Criteria andChannelUrlLike(String value) {
//            addCriterion("channel_url like", value, "channelUrl");
//            return (Criteria) this;
//        }
//
//        public Criteria andChannelUrlNotLike(String value) {
//            addCriterion("channel_url not like", value, "channelUrl");
//            return (Criteria) this;
//        }
//
//        public Criteria andChannelUrlIn(List<String> values) {
//            addCriterion("channel_url in", values, "channelUrl");
//            return (Criteria) this;
//        }
//
//        public Criteria andChannelUrlNotIn(List<String> values) {
//            addCriterion("channel_url not in", values, "channelUrl");
//            return (Criteria) this;
//        }
//
//        public Criteria andChannelUrlBetween(String value1, String value2) {
//            addCriterion("channel_url between", value1, value2, "channelUrl");
//            return (Criteria) this;
//        }
//
//        public Criteria andChannelUrlNotBetween(String value1, String value2) {
//            addCriterion("channel_url not between", value1, value2, "channelUrl");
//            return (Criteria) this;
//        }
//
//        public Criteria andReturnUrlIsNull() {
//            addCriterion("return_url is null");
//            return (Criteria) this;
//        }
//
//        public Criteria andReturnUrlIsNotNull() {
//            addCriterion("return_url is not null");
//            return (Criteria) this;
//        }
//
//        public Criteria andReturnUrlEqualTo(String value) {
//            addCriterion("return_url =", value, "returnUrl");
//            return (Criteria) this;
//        }
//
//        public Criteria andReturnUrlNotEqualTo(String value) {
//            addCriterion("return_url <>", value, "returnUrl");
//            return (Criteria) this;
//        }
//
//        public Criteria andReturnUrlGreaterThan(String value) {
//            addCriterion("return_url >", value, "returnUrl");
//            return (Criteria) this;
//        }
//
//        public Criteria andReturnUrlGreaterThanOrEqualTo(String value) {
//            addCriterion("return_url >=", value, "returnUrl");
//            return (Criteria) this;
//        }
//
//        public Criteria andReturnUrlLessThan(String value) {
//            addCriterion("return_url <", value, "returnUrl");
//            return (Criteria) this;
//        }
//
//        public Criteria andReturnUrlLessThanOrEqualTo(String value) {
//            addCriterion("return_url <=", value, "returnUrl");
//            return (Criteria) this;
//        }
//
//        public Criteria andReturnUrlLike(String value) {
//            addCriterion("return_url like", value, "returnUrl");
//            return (Criteria) this;
//        }
//
//        public Criteria andReturnUrlNotLike(String value) {
//            addCriterion("return_url not like", value, "returnUrl");
//            return (Criteria) this;
//        }
//
//        public Criteria andReturnUrlIn(List<String> values) {
//            addCriterion("return_url in", values, "returnUrl");
//            return (Criteria) this;
//        }
//
//        public Criteria andReturnUrlNotIn(List<String> values) {
//            addCriterion("return_url not in", values, "returnUrl");
//            return (Criteria) this;
//        }
//
//        public Criteria andReturnUrlBetween(String value1, String value2) {
//            addCriterion("return_url between", value1, value2, "returnUrl");
//            return (Criteria) this;
//        }
//
//        public Criteria andReturnUrlNotBetween(String value1, String value2) {
//            addCriterion("return_url not between", value1, value2, "returnUrl");
//            return (Criteria) this;
//        }
//
//        public Criteria andNoticeUrlIsNull() {
//            addCriterion("notice_url is null");
//            return (Criteria) this;
//        }
//
//        public Criteria andNoticeUrlIsNotNull() {
//            addCriterion("notice_url is not null");
//            return (Criteria) this;
//        }
//
//        public Criteria andNoticeUrlEqualTo(String value) {
//            addCriterion("notice_url =", value, "noticeUrl");
//            return (Criteria) this;
//        }
//
//        public Criteria andNoticeUrlNotEqualTo(String value) {
//            addCriterion("notice_url <>", value, "noticeUrl");
//            return (Criteria) this;
//        }
//
//        public Criteria andNoticeUrlGreaterThan(String value) {
//            addCriterion("notice_url >", value, "noticeUrl");
//            return (Criteria) this;
//        }
//
//        public Criteria andNoticeUrlGreaterThanOrEqualTo(String value) {
//            addCriterion("notice_url >=", value, "noticeUrl");
//            return (Criteria) this;
//        }
//
//        public Criteria andNoticeUrlLessThan(String value) {
//            addCriterion("notice_url <", value, "noticeUrl");
//            return (Criteria) this;
//        }
//
//        public Criteria andNoticeUrlLessThanOrEqualTo(String value) {
//            addCriterion("notice_url <=", value, "noticeUrl");
//            return (Criteria) this;
//        }
//
//        public Criteria andNoticeUrlLike(String value) {
//            addCriterion("notice_url like", value, "noticeUrl");
//            return (Criteria) this;
//        }
//
//        public Criteria andNoticeUrlNotLike(String value) {
//            addCriterion("notice_url not like", value, "noticeUrl");
//            return (Criteria) this;
//        }
//
//        public Criteria andNoticeUrlIn(List<String> values) {
//            addCriterion("notice_url in", values, "noticeUrl");
//            return (Criteria) this;
//        }
//
//        public Criteria andNoticeUrlNotIn(List<String> values) {
//            addCriterion("notice_url not in", values, "noticeUrl");
//            return (Criteria) this;
//        }
//
//        public Criteria andNoticeUrlBetween(String value1, String value2) {
//            addCriterion("notice_url between", value1, value2, "noticeUrl");
//            return (Criteria) this;
//        }
//
//        public Criteria andNoticeUrlNotBetween(String value1, String value2) {
//            addCriterion("notice_url not between", value1, value2, "noticeUrl");
//            return (Criteria) this;
//        }
//
//        public Criteria andPayUrlIsNull() {
//            addCriterion("pay_url is null");
//            return (Criteria) this;
//        }
//
//        public Criteria andPayUrlIsNotNull() {
//            addCriterion("pay_url is not null");
//            return (Criteria) this;
//        }
//
//        public Criteria andPayUrlEqualTo(String value) {
//            addCriterion("pay_url =", value, "payUrl");
//            return (Criteria) this;
//        }
//
//        public Criteria andPayUrlNotEqualTo(String value) {
//            addCriterion("pay_url <>", value, "payUrl");
//            return (Criteria) this;
//        }
//
//        public Criteria andPayUrlGreaterThan(String value) {
//            addCriterion("pay_url >", value, "payUrl");
//            return (Criteria) this;
//        }
//
//        public Criteria andPayUrlGreaterThanOrEqualTo(String value) {
//            addCriterion("pay_url >=", value, "payUrl");
//            return (Criteria) this;
//        }
//
//        public Criteria andPayUrlLessThan(String value) {
//            addCriterion("pay_url <", value, "payUrl");
//            return (Criteria) this;
//        }
//
//        public Criteria andPayUrlLessThanOrEqualTo(String value) {
//            addCriterion("pay_url <=", value, "payUrl");
//            return (Criteria) this;
//        }
//
//        public Criteria andPayUrlLike(String value) {
//            addCriterion("pay_url like", value, "payUrl");
//            return (Criteria) this;
//        }
//
//        public Criteria andPayUrlNotLike(String value) {
//            addCriterion("pay_url not like", value, "payUrl");
//            return (Criteria) this;
//        }
//
//        public Criteria andPayUrlIn(List<String> values) {
//            addCriterion("pay_url in", values, "payUrl");
//            return (Criteria) this;
//        }
//
//        public Criteria andPayUrlNotIn(List<String> values) {
//            addCriterion("pay_url not in", values, "payUrl");
//            return (Criteria) this;
//        }
//
//        public Criteria andPayUrlBetween(String value1, String value2) {
//            addCriterion("pay_url between", value1, value2, "payUrl");
//            return (Criteria) this;
//        }
//
//        public Criteria andPayUrlNotBetween(String value1, String value2) {
//            addCriterion("pay_url not between", value1, value2, "payUrl");
//            return (Criteria) this;
//        }
//
//        public Criteria andMd5KeyIsNull() {
//            addCriterion("md5_key is null");
//            return (Criteria) this;
//        }
//
//        public Criteria andMd5KeyIsNotNull() {
//            addCriterion("md5_key is not null");
//            return (Criteria) this;
//        }
//
//        public Criteria andMd5KeyEqualTo(String value) {
//            addCriterion("md5_key =", value, "md5Key");
//            return (Criteria) this;
//        }
//
//        public Criteria andMd5KeyNotEqualTo(String value) {
//            addCriterion("md5_key <>", value, "md5Key");
//            return (Criteria) this;
//        }
//
//        public Criteria andMd5KeyGreaterThan(String value) {
//            addCriterion("md5_key >", value, "md5Key");
//            return (Criteria) this;
//        }
//
//        public Criteria andMd5KeyGreaterThanOrEqualTo(String value) {
//            addCriterion("md5_key >=", value, "md5Key");
//            return (Criteria) this;
//        }
//
//        public Criteria andMd5KeyLessThan(String value) {
//            addCriterion("md5_key <", value, "md5Key");
//            return (Criteria) this;
//        }
//
//        public Criteria andMd5KeyLessThanOrEqualTo(String value) {
//            addCriterion("md5_key <=", value, "md5Key");
//            return (Criteria) this;
//        }
//
//        public Criteria andMd5KeyLike(String value) {
//            addCriterion("md5_key like", value, "md5Key");
//            return (Criteria) this;
//        }
//
//        public Criteria andMd5KeyNotLike(String value) {
//            addCriterion("md5_key not like", value, "md5Key");
//            return (Criteria) this;
//        }
//
//        public Criteria andMd5KeyIn(List<String> values) {
//            addCriterion("md5_key in", values, "md5Key");
//            return (Criteria) this;
//        }
//
//        public Criteria andMd5KeyNotIn(List<String> values) {
//            addCriterion("md5_key not in", values, "md5Key");
//            return (Criteria) this;
//        }
//
//        public Criteria andMd5KeyBetween(String value1, String value2) {
//            addCriterion("md5_key between", value1, value2, "md5Key");
//            return (Criteria) this;
//        }
//
//        public Criteria andMd5KeyNotBetween(String value1, String value2) {
//            addCriterion("md5_key not between", value1, value2, "md5Key");
//            return (Criteria) this;
//        }
//
//        public Criteria andRsaKeyIsNull() {
//            addCriterion("rsa_key is null");
//            return (Criteria) this;
//        }
//
//        public Criteria andRsaKeyIsNotNull() {
//            addCriterion("rsa_key is not null");
//            return (Criteria) this;
//        }
//
//        public Criteria andRsaKeyEqualTo(String value) {
//            addCriterion("rsa_key =", value, "rsaKey");
//            return (Criteria) this;
//        }
//
//        public Criteria andRsaKeyNotEqualTo(String value) {
//            addCriterion("rsa_key <>", value, "rsaKey");
//            return (Criteria) this;
//        }
//
//        public Criteria andRsaKeyGreaterThan(String value) {
//            addCriterion("rsa_key >", value, "rsaKey");
//            return (Criteria) this;
//        }
//
//        public Criteria andRsaKeyGreaterThanOrEqualTo(String value) {
//            addCriterion("rsa_key >=", value, "rsaKey");
//            return (Criteria) this;
//        }
//
//        public Criteria andRsaKeyLessThan(String value) {
//            addCriterion("rsa_key <", value, "rsaKey");
//            return (Criteria) this;
//        }
//
//        public Criteria andRsaKeyLessThanOrEqualTo(String value) {
//            addCriterion("rsa_key <=", value, "rsaKey");
//            return (Criteria) this;
//        }
//
//        public Criteria andRsaKeyLike(String value) {
//            addCriterion("rsa_key like", value, "rsaKey");
//            return (Criteria) this;
//        }
//
//        public Criteria andRsaKeyNotLike(String value) {
//            addCriterion("rsa_key not like", value, "rsaKey");
//            return (Criteria) this;
//        }
//
//        public Criteria andRsaKeyIn(List<String> values) {
//            addCriterion("rsa_key in", values, "rsaKey");
//            return (Criteria) this;
//        }
//
//        public Criteria andRsaKeyNotIn(List<String> values) {
//            addCriterion("rsa_key not in", values, "rsaKey");
//            return (Criteria) this;
//        }
//
//        public Criteria andRsaKeyBetween(String value1, String value2) {
//            addCriterion("rsa_key between", value1, value2, "rsaKey");
//            return (Criteria) this;
//        }
//
//        public Criteria andRsaKeyNotBetween(String value1, String value2) {
//            addCriterion("rsa_key not between", value1, value2, "rsaKey");
//            return (Criteria) this;
//        }
//
//        public Criteria andMerKeyIsNull() {
//            addCriterion("mer_key is null");
//            return (Criteria) this;
//        }
//
//        public Criteria andMerKeyIsNotNull() {
//            addCriterion("mer_key is not null");
//            return (Criteria) this;
//        }
//
//        public Criteria andMerKeyEqualTo(String value) {
//            addCriterion("mer_key =", value, "merKey");
//            return (Criteria) this;
//        }
//
//        public Criteria andMerKeyNotEqualTo(String value) {
//            addCriterion("mer_key <>", value, "merKey");
//            return (Criteria) this;
//        }
//
//        public Criteria andMerKeyGreaterThan(String value) {
//            addCriterion("mer_key >", value, "merKey");
//            return (Criteria) this;
//        }
//
//        public Criteria andMerKeyGreaterThanOrEqualTo(String value) {
//            addCriterion("mer_key >=", value, "merKey");
//            return (Criteria) this;
//        }
//
//        public Criteria andMerKeyLessThan(String value) {
//            addCriterion("mer_key <", value, "merKey");
//            return (Criteria) this;
//        }
//
//        public Criteria andMerKeyLessThanOrEqualTo(String value) {
//            addCriterion("mer_key <=", value, "merKey");
//            return (Criteria) this;
//        }
//
//        public Criteria andMerKeyLike(String value) {
//            addCriterion("mer_key like", value, "merKey");
//            return (Criteria) this;
//        }
//
//        public Criteria andMerKeyNotLike(String value) {
//            addCriterion("mer_key not like", value, "merKey");
//            return (Criteria) this;
//        }
//
//        public Criteria andMerKeyIn(List<String> values) {
//            addCriterion("mer_key in", values, "merKey");
//            return (Criteria) this;
//        }
//
//        public Criteria andMerKeyNotIn(List<String> values) {
//            addCriterion("mer_key not in", values, "merKey");
//            return (Criteria) this;
//        }
//
//        public Criteria andMerKeyBetween(String value1, String value2) {
//            addCriterion("mer_key between", value1, value2, "merKey");
//            return (Criteria) this;
//        }
//
//        public Criteria andMerKeyNotBetween(String value1, String value2) {
//            addCriterion("mer_key not between", value1, value2, "merKey");
//            return (Criteria) this;
//        }
//
//        public Criteria andRemarksIsNull() {
//            addCriterion("remarks is null");
//            return (Criteria) this;
//        }
//
//        public Criteria andRemarksIsNotNull() {
//            addCriterion("remarks is not null");
//            return (Criteria) this;
//        }
//
//        public Criteria andRemarksEqualTo(String value) {
//            addCriterion("remarks =", value, "remarks");
//            return (Criteria) this;
//        }
//
//        public Criteria andRemarksNotEqualTo(String value) {
//            addCriterion("remarks <>", value, "remarks");
//            return (Criteria) this;
//        }
//
//        public Criteria andRemarksGreaterThan(String value) {
//            addCriterion("remarks >", value, "remarks");
//            return (Criteria) this;
//        }
//
//        public Criteria andRemarksGreaterThanOrEqualTo(String value) {
//            addCriterion("remarks >=", value, "remarks");
//            return (Criteria) this;
//        }
//
//        public Criteria andRemarksLessThan(String value) {
//            addCriterion("remarks <", value, "remarks");
//            return (Criteria) this;
//        }
//
//        public Criteria andRemarksLessThanOrEqualTo(String value) {
//            addCriterion("remarks <=", value, "remarks");
//            return (Criteria) this;
//        }
//
//        public Criteria andRemarksLike(String value) {
//            addCriterion("remarks like", value, "remarks");
//            return (Criteria) this;
//        }
//
//        public Criteria andRemarksNotLike(String value) {
//            addCriterion("remarks not like", value, "remarks");
//            return (Criteria) this;
//        }
//
//        public Criteria andRemarksIn(List<String> values) {
//            addCriterion("remarks in", values, "remarks");
//            return (Criteria) this;
//        }
//
//        public Criteria andRemarksNotIn(List<String> values) {
//            addCriterion("remarks not in", values, "remarks");
//            return (Criteria) this;
//        }
//
//        public Criteria andRemarksBetween(String value1, String value2) {
//            addCriterion("remarks between", value1, value2, "remarks");
//            return (Criteria) this;
//        }
//
//        public Criteria andRemarksNotBetween(String value1, String value2) {
//            addCriterion("remarks not between", value1, value2, "remarks");
//            return (Criteria) this;
//        }
//    }
//
//    public static class Criteria extends GeneratedCriteria {
//
//        protected Criteria() {
//            super();
//        }
//    }
//
//    public static class Criterion {
//        private String condition;
//
//        private Object value;
//
//        private Object secondValue;
//
//        private boolean noValue;
//
//        private boolean singleValue;
//
//        private boolean betweenValue;
//
//        private boolean listValue;
//
//        private String typeHandler;
//
//        public String getCondition() {
//            return condition;
//        }
//
//        public Object getValue() {
//            return value;
//        }
//
//        public Object getSecondValue() {
//            return secondValue;
//        }
//
//        public boolean isNoValue() {
//            return noValue;
//        }
//
//        public boolean isSingleValue() {
//            return singleValue;
//        }
//
//        public boolean isBetweenValue() {
//            return betweenValue;
//        }
//
//        public boolean isListValue() {
//            return listValue;
//        }
//
//        public String getTypeHandler() {
//            return typeHandler;
//        }
//
//        protected Criterion(String condition) {
//            super();
//            this.condition = condition;
//            this.typeHandler = null;
//            this.noValue = true;
//        }
//
//        protected Criterion(String condition, Object value, String typeHandler) {
//            super();
//            this.condition = condition;
//            this.value = value;
//            this.typeHandler = typeHandler;
//            if (value instanceof List<?>) {
//                this.listValue = true;
//            } else {
//                this.singleValue = true;
//            }
//        }
//
//        protected Criterion(String condition, Object value) {
//            this(condition, value, null);
//        }
//
//        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
//            super();
//            this.condition = condition;
//            this.value = value;
//            this.secondValue = secondValue;
//            this.typeHandler = typeHandler;
//            this.betweenValue = true;
//        }
//
//        protected Criterion(String condition, Object value, Object secondValue) {
//            this(condition, value, secondValue, null);
//        }
//    }
//}