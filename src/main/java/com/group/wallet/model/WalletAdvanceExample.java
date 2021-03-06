//package com.group.wallet.model;
//
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//public class WalletAdvanceExample {
//    protected String orderByClause;
//
//    protected boolean distinct;
//
//    protected List<Criteria> oredCriteria;
//
//    public WalletAdvanceExample() {
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
//        public Criteria andUserIdIsNull() {
//            addCriterion("user_id is null");
//            return (Criteria) this;
//        }
//
//        public Criteria andUserIdIsNotNull() {
//            addCriterion("user_id is not null");
//            return (Criteria) this;
//        }
//
//        public Criteria andUserIdEqualTo(Long value) {
//            addCriterion("user_id =", value, "userId");
//            return (Criteria) this;
//        }
//
//        public Criteria andUserIdNotEqualTo(Long value) {
//            addCriterion("user_id <>", value, "userId");
//            return (Criteria) this;
//        }
//
//        public Criteria andUserIdGreaterThan(Long value) {
//            addCriterion("user_id >", value, "userId");
//            return (Criteria) this;
//        }
//
//        public Criteria andUserIdGreaterThanOrEqualTo(Long value) {
//            addCriterion("user_id >=", value, "userId");
//            return (Criteria) this;
//        }
//
//        public Criteria andUserIdLessThan(Long value) {
//            addCriterion("user_id <", value, "userId");
//            return (Criteria) this;
//        }
//
//        public Criteria andUserIdLessThanOrEqualTo(Long value) {
//            addCriterion("user_id <=", value, "userId");
//            return (Criteria) this;
//        }
//
//        public Criteria andUserIdIn(List<Long> values) {
//            addCriterion("user_id in", values, "userId");
//            return (Criteria) this;
//        }
//
//        public Criteria andUserIdNotIn(List<Long> values) {
//            addCriterion("user_id not in", values, "userId");
//            return (Criteria) this;
//        }
//
//        public Criteria andUserIdBetween(Long value1, Long value2) {
//            addCriterion("user_id between", value1, value2, "userId");
//            return (Criteria) this;
//        }
//
//        public Criteria andUserIdNotBetween(Long value1, Long value2) {
//            addCriterion("user_id not between", value1, value2, "userId");
//            return (Criteria) this;
//        }
//
//        public Criteria andBaseAmountIsNull() {
//            addCriterion("base_amount is null");
//            return (Criteria) this;
//        }
//
//        public Criteria andBaseAmountIsNotNull() {
//            addCriterion("base_amount is not null");
//            return (Criteria) this;
//        }
//
//        public Criteria andBaseAmountEqualTo(BigDecimal value) {
//            addCriterion("base_amount =", value, "baseAmount");
//            return (Criteria) this;
//        }
//
//        public Criteria andBaseAmountNotEqualTo(BigDecimal value) {
//            addCriterion("base_amount <>", value, "baseAmount");
//            return (Criteria) this;
//        }
//
//        public Criteria andBaseAmountGreaterThan(BigDecimal value) {
//            addCriterion("base_amount >", value, "baseAmount");
//            return (Criteria) this;
//        }
//
//        public Criteria andBaseAmountGreaterThanOrEqualTo(BigDecimal value) {
//            addCriterion("base_amount >=", value, "baseAmount");
//            return (Criteria) this;
//        }
//
//        public Criteria andBaseAmountLessThan(BigDecimal value) {
//            addCriterion("base_amount <", value, "baseAmount");
//            return (Criteria) this;
//        }
//
//        public Criteria andBaseAmountLessThanOrEqualTo(BigDecimal value) {
//            addCriterion("base_amount <=", value, "baseAmount");
//            return (Criteria) this;
//        }
//
//        public Criteria andBaseAmountIn(List<BigDecimal> values) {
//            addCriterion("base_amount in", values, "baseAmount");
//            return (Criteria) this;
//        }
//
//        public Criteria andBaseAmountNotIn(List<BigDecimal> values) {
//            addCriterion("base_amount not in", values, "baseAmount");
//            return (Criteria) this;
//        }
//
//        public Criteria andBaseAmountBetween(BigDecimal value1, BigDecimal value2) {
//            addCriterion("base_amount between", value1, value2, "baseAmount");
//            return (Criteria) this;
//        }
//
//        public Criteria andBaseAmountNotBetween(BigDecimal value1, BigDecimal value2) {
//            addCriterion("base_amount not between", value1, value2, "baseAmount");
//            return (Criteria) this;
//        }
//
//        public Criteria andNoRefundAmountIsNull() {
//            addCriterion("no_refund_amount is null");
//            return (Criteria) this;
//        }
//
//        public Criteria andNoRefundAmountIsNotNull() {
//            addCriterion("no_refund_amount is not null");
//            return (Criteria) this;
//        }
//
//        public Criteria andNoRefundAmountEqualTo(BigDecimal value) {
//            addCriterion("no_refund_amount =", value, "noRefundAmount");
//            return (Criteria) this;
//        }
//
//        public Criteria andNoRefundAmountNotEqualTo(BigDecimal value) {
//            addCriterion("no_refund_amount <>", value, "noRefundAmount");
//            return (Criteria) this;
//        }
//
//        public Criteria andNoRefundAmountGreaterThan(BigDecimal value) {
//            addCriterion("no_refund_amount >", value, "noRefundAmount");
//            return (Criteria) this;
//        }
//
//        public Criteria andNoRefundAmountGreaterThanOrEqualTo(BigDecimal value) {
//            addCriterion("no_refund_amount >=", value, "noRefundAmount");
//            return (Criteria) this;
//        }
//
//        public Criteria andNoRefundAmountLessThan(BigDecimal value) {
//            addCriterion("no_refund_amount <", value, "noRefundAmount");
//            return (Criteria) this;
//        }
//
//        public Criteria andNoRefundAmountLessThanOrEqualTo(BigDecimal value) {
//            addCriterion("no_refund_amount <=", value, "noRefundAmount");
//            return (Criteria) this;
//        }
//
//        public Criteria andNoRefundAmountIn(List<BigDecimal> values) {
//            addCriterion("no_refund_amount in", values, "noRefundAmount");
//            return (Criteria) this;
//        }
//
//        public Criteria andNoRefundAmountNotIn(List<BigDecimal> values) {
//            addCriterion("no_refund_amount not in", values, "noRefundAmount");
//            return (Criteria) this;
//        }
//
//        public Criteria andNoRefundAmountBetween(BigDecimal value1, BigDecimal value2) {
//            addCriterion("no_refund_amount between", value1, value2, "noRefundAmount");
//            return (Criteria) this;
//        }
//
//        public Criteria andNoRefundAmountNotBetween(BigDecimal value1, BigDecimal value2) {
//            addCriterion("no_refund_amount not between", value1, value2, "noRefundAmount");
//            return (Criteria) this;
//        }
//
//        public Criteria andSignatureIsNull() {
//            addCriterion("signature is null");
//            return (Criteria) this;
//        }
//
//        public Criteria andSignatureIsNotNull() {
//            addCriterion("signature is not null");
//            return (Criteria) this;
//        }
//
//        public Criteria andSignatureEqualTo(String value) {
//            addCriterion("signature =", value, "signature");
//            return (Criteria) this;
//        }
//
//        public Criteria andSignatureNotEqualTo(String value) {
//            addCriterion("signature <>", value, "signature");
//            return (Criteria) this;
//        }
//
//        public Criteria andSignatureGreaterThan(String value) {
//            addCriterion("signature >", value, "signature");
//            return (Criteria) this;
//        }
//
//        public Criteria andSignatureGreaterThanOrEqualTo(String value) {
//            addCriterion("signature >=", value, "signature");
//            return (Criteria) this;
//        }
//
//        public Criteria andSignatureLessThan(String value) {
//            addCriterion("signature <", value, "signature");
//            return (Criteria) this;
//        }
//
//        public Criteria andSignatureLessThanOrEqualTo(String value) {
//            addCriterion("signature <=", value, "signature");
//            return (Criteria) this;
//        }
//
//        public Criteria andSignatureLike(String value) {
//            addCriterion("signature like", value, "signature");
//            return (Criteria) this;
//        }
//
//        public Criteria andSignatureNotLike(String value) {
//            addCriterion("signature not like", value, "signature");
//            return (Criteria) this;
//        }
//
//        public Criteria andSignatureIn(List<String> values) {
//            addCriterion("signature in", values, "signature");
//            return (Criteria) this;
//        }
//
//        public Criteria andSignatureNotIn(List<String> values) {
//            addCriterion("signature not in", values, "signature");
//            return (Criteria) this;
//        }
//
//        public Criteria andSignatureBetween(String value1, String value2) {
//            addCriterion("signature between", value1, value2, "signature");
//            return (Criteria) this;
//        }
//
//        public Criteria andSignatureNotBetween(String value1, String value2) {
//            addCriterion("signature not between", value1, value2, "signature");
//            return (Criteria) this;
//        }
//
//        public Criteria andCreateTimeIsNull() {
//            addCriterion("create_time is null");
//            return (Criteria) this;
//        }
//
//        public Criteria andCreateTimeIsNotNull() {
//            addCriterion("create_time is not null");
//            return (Criteria) this;
//        }
//
//        public Criteria andCreateTimeEqualTo(Date value) {
//            addCriterion("create_time =", value, "createTime");
//            return (Criteria) this;
//        }
//
//        public Criteria andCreateTimeNotEqualTo(Date value) {
//            addCriterion("create_time <>", value, "createTime");
//            return (Criteria) this;
//        }
//
//        public Criteria andCreateTimeGreaterThan(Date value) {
//            addCriterion("create_time >", value, "createTime");
//            return (Criteria) this;
//        }
//
//        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
//            addCriterion("create_time >=", value, "createTime");
//            return (Criteria) this;
//        }
//
//        public Criteria andCreateTimeLessThan(Date value) {
//            addCriterion("create_time <", value, "createTime");
//            return (Criteria) this;
//        }
//
//        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
//            addCriterion("create_time <=", value, "createTime");
//            return (Criteria) this;
//        }
//
//        public Criteria andCreateTimeIn(List<Date> values) {
//            addCriterion("create_time in", values, "createTime");
//            return (Criteria) this;
//        }
//
//        public Criteria andCreateTimeNotIn(List<Date> values) {
//            addCriterion("create_time not in", values, "createTime");
//            return (Criteria) this;
//        }
//
//        public Criteria andCreateTimeBetween(Date value1, Date value2) {
//            addCriterion("create_time between", value1, value2, "createTime");
//            return (Criteria) this;
//        }
//
//        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
//            addCriterion("create_time not between", value1, value2, "createTime");
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