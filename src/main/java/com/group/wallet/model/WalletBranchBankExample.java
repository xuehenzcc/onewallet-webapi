package com.group.wallet.model;

import java.util.ArrayList;
import java.util.List;

public class WalletBranchBankExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public WalletBranchBankExample() {
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

        public Criteria andBranchBankNameIsNull() {
            addCriterion("branch_bank_name is null");
            return (Criteria) this;
        }

        public Criteria andBranchBankNameIsNotNull() {
            addCriterion("branch_bank_name is not null");
            return (Criteria) this;
        }

        public Criteria andBranchBankNameEqualTo(String value) {
            addCriterion("branch_bank_name =", value, "branchBankName");
            return (Criteria) this;
        }

        public Criteria andBranchBankNameNotEqualTo(String value) {
            addCriterion("branch_bank_name <>", value, "branchBankName");
            return (Criteria) this;
        }

        public Criteria andBranchBankNameGreaterThan(String value) {
            addCriterion("branch_bank_name >", value, "branchBankName");
            return (Criteria) this;
        }

        public Criteria andBranchBankNameGreaterThanOrEqualTo(String value) {
            addCriterion("branch_bank_name >=", value, "branchBankName");
            return (Criteria) this;
        }

        public Criteria andBranchBankNameLessThan(String value) {
            addCriterion("branch_bank_name <", value, "branchBankName");
            return (Criteria) this;
        }

        public Criteria andBranchBankNameLessThanOrEqualTo(String value) {
            addCriterion("branch_bank_name <=", value, "branchBankName");
            return (Criteria) this;
        }

        public Criteria andBranchBankNameLike(String value) {
            addCriterion("branch_bank_name like", value, "branchBankName");
            return (Criteria) this;
        }

        public Criteria andBranchBankNameNotLike(String value) {
            addCriterion("branch_bank_name not like", value, "branchBankName");
            return (Criteria) this;
        }

        public Criteria andBranchBankNameIn(List<String> values) {
            addCriterion("branch_bank_name in", values, "branchBankName");
            return (Criteria) this;
        }

        public Criteria andBranchBankNameNotIn(List<String> values) {
            addCriterion("branch_bank_name not in", values, "branchBankName");
            return (Criteria) this;
        }

        public Criteria andBranchBankNameBetween(String value1, String value2) {
            addCriterion("branch_bank_name between", value1, value2, "branchBankName");
            return (Criteria) this;
        }

        public Criteria andBranchBankNameNotBetween(String value1, String value2) {
            addCriterion("branch_bank_name not between", value1, value2, "branchBankName");
            return (Criteria) this;
        }

        public Criteria andBarnchBankCodeIsNull() {
            addCriterion("barnch_bank_code is null");
            return (Criteria) this;
        }

        public Criteria andBarnchBankCodeIsNotNull() {
            addCriterion("barnch_bank_code is not null");
            return (Criteria) this;
        }

        public Criteria andBarnchBankCodeEqualTo(String value) {
            addCriterion("barnch_bank_code =", value, "barnchBankCode");
            return (Criteria) this;
        }

        public Criteria andBarnchBankCodeNotEqualTo(String value) {
            addCriterion("barnch_bank_code <>", value, "barnchBankCode");
            return (Criteria) this;
        }

        public Criteria andBarnchBankCodeGreaterThan(String value) {
            addCriterion("barnch_bank_code >", value, "barnchBankCode");
            return (Criteria) this;
        }

        public Criteria andBarnchBankCodeGreaterThanOrEqualTo(String value) {
            addCriterion("barnch_bank_code >=", value, "barnchBankCode");
            return (Criteria) this;
        }

        public Criteria andBarnchBankCodeLessThan(String value) {
            addCriterion("barnch_bank_code <", value, "barnchBankCode");
            return (Criteria) this;
        }

        public Criteria andBarnchBankCodeLessThanOrEqualTo(String value) {
            addCriterion("barnch_bank_code <=", value, "barnchBankCode");
            return (Criteria) this;
        }

        public Criteria andBarnchBankCodeLike(String value) {
            addCriterion("barnch_bank_code like", value, "barnchBankCode");
            return (Criteria) this;
        }

        public Criteria andBarnchBankCodeNotLike(String value) {
            addCriterion("barnch_bank_code not like", value, "barnchBankCode");
            return (Criteria) this;
        }

        public Criteria andBarnchBankCodeIn(List<String> values) {
            addCriterion("barnch_bank_code in", values, "barnchBankCode");
            return (Criteria) this;
        }

        public Criteria andBarnchBankCodeNotIn(List<String> values) {
            addCriterion("barnch_bank_code not in", values, "barnchBankCode");
            return (Criteria) this;
        }

        public Criteria andBarnchBankCodeBetween(String value1, String value2) {
            addCriterion("barnch_bank_code between", value1, value2, "barnchBankCode");
            return (Criteria) this;
        }

        public Criteria andBarnchBankCodeNotBetween(String value1, String value2) {
            addCriterion("barnch_bank_code not between", value1, value2, "barnchBankCode");
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