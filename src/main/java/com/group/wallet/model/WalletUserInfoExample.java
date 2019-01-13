package com.group.wallet.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WalletUserInfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public WalletUserInfoExample() {
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

        public Criteria andPhoneIsNull() {
            addCriterion("phone is null");
            return (Criteria) this;
        }

        public Criteria andPhoneIsNotNull() {
            addCriterion("phone is not null");
            return (Criteria) this;
        }

        public Criteria andPhoneEqualTo(String value) {
            addCriterion("phone =", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotEqualTo(String value) {
            addCriterion("phone <>", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneGreaterThan(String value) {
            addCriterion("phone >", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneGreaterThanOrEqualTo(String value) {
            addCriterion("phone >=", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneLessThan(String value) {
            addCriterion("phone <", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneLessThanOrEqualTo(String value) {
            addCriterion("phone <=", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneLike(String value) {
            addCriterion("phone like", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotLike(String value) {
            addCriterion("phone not like", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneIn(List<String> values) {
            addCriterion("phone in", values, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotIn(List<String> values) {
            addCriterion("phone not in", values, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneBetween(String value1, String value2) {
            addCriterion("phone between", value1, value2, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotBetween(String value1, String value2) {
            addCriterion("phone not between", value1, value2, "phone");
            return (Criteria) this;
        }

        public Criteria andLoginPwdIsNull() {
            addCriterion("login_pwd is null");
            return (Criteria) this;
        }

        public Criteria andLoginPwdIsNotNull() {
            addCriterion("login_pwd is not null");
            return (Criteria) this;
        }

        public Criteria andLoginPwdEqualTo(String value) {
            addCriterion("login_pwd =", value, "loginPwd");
            return (Criteria) this;
        }

        public Criteria andLoginPwdNotEqualTo(String value) {
            addCriterion("login_pwd <>", value, "loginPwd");
            return (Criteria) this;
        }

        public Criteria andLoginPwdGreaterThan(String value) {
            addCriterion("login_pwd >", value, "loginPwd");
            return (Criteria) this;
        }

        public Criteria andLoginPwdGreaterThanOrEqualTo(String value) {
            addCriterion("login_pwd >=", value, "loginPwd");
            return (Criteria) this;
        }

        public Criteria andLoginPwdLessThan(String value) {
            addCriterion("login_pwd <", value, "loginPwd");
            return (Criteria) this;
        }

        public Criteria andLoginPwdLessThanOrEqualTo(String value) {
            addCriterion("login_pwd <=", value, "loginPwd");
            return (Criteria) this;
        }

        public Criteria andLoginPwdLike(String value) {
            addCriterion("login_pwd like", value, "loginPwd");
            return (Criteria) this;
        }

        public Criteria andLoginPwdNotLike(String value) {
            addCriterion("login_pwd not like", value, "loginPwd");
            return (Criteria) this;
        }

        public Criteria andLoginPwdIn(List<String> values) {
            addCriterion("login_pwd in", values, "loginPwd");
            return (Criteria) this;
        }

        public Criteria andLoginPwdNotIn(List<String> values) {
            addCriterion("login_pwd not in", values, "loginPwd");
            return (Criteria) this;
        }

        public Criteria andLoginPwdBetween(String value1, String value2) {
            addCriterion("login_pwd between", value1, value2, "loginPwd");
            return (Criteria) this;
        }

        public Criteria andLoginPwdNotBetween(String value1, String value2) {
            addCriterion("login_pwd not between", value1, value2, "loginPwd");
            return (Criteria) this;
        }

        public Criteria andRecommonIdIsNull() {
            addCriterion("recommon_id is null");
            return (Criteria) this;
        }

        public Criteria andRecommonIdIsNotNull() {
            addCriterion("recommon_id is not null");
            return (Criteria) this;
        }

        public Criteria andRecommonIdEqualTo(Long value) {
            addCriterion("recommon_id =", value, "recommonId");
            return (Criteria) this;
        }

        public Criteria andRecommonIdNotEqualTo(Long value) {
            addCriterion("recommon_id <>", value, "recommonId");
            return (Criteria) this;
        }

        public Criteria andRecommonIdGreaterThan(Long value) {
            addCriterion("recommon_id >", value, "recommonId");
            return (Criteria) this;
        }

        public Criteria andRecommonIdGreaterThanOrEqualTo(Long value) {
            addCriterion("recommon_id >=", value, "recommonId");
            return (Criteria) this;
        }

        public Criteria andRecommonIdLessThan(Long value) {
            addCriterion("recommon_id <", value, "recommonId");
            return (Criteria) this;
        }

        public Criteria andRecommonIdLessThanOrEqualTo(Long value) {
            addCriterion("recommon_id <=", value, "recommonId");
            return (Criteria) this;
        }

        public Criteria andRecommonIdIn(List<Long> values) {
            addCriterion("recommon_id in", values, "recommonId");
            return (Criteria) this;
        }

        public Criteria andRecommonIdNotIn(List<Long> values) {
            addCriterion("recommon_id not in", values, "recommonId");
            return (Criteria) this;
        }

        public Criteria andRecommonIdBetween(Long value1, Long value2) {
            addCriterion("recommon_id between", value1, value2, "recommonId");
            return (Criteria) this;
        }

        public Criteria andRecommonIdNotBetween(Long value1, Long value2) {
            addCriterion("recommon_id not between", value1, value2, "recommonId");
            return (Criteria) this;
        }

        public Criteria andUserTypeIsNull() {
            addCriterion("user_type is null");
            return (Criteria) this;
        }

        public Criteria andUserTypeIsNotNull() {
            addCriterion("user_type is not null");
            return (Criteria) this;
        }

        public Criteria andUserTypeEqualTo(String value) {
            addCriterion("user_type =", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeNotEqualTo(String value) {
            addCriterion("user_type <>", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeGreaterThan(String value) {
            addCriterion("user_type >", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeGreaterThanOrEqualTo(String value) {
            addCriterion("user_type >=", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeLessThan(String value) {
            addCriterion("user_type <", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeLessThanOrEqualTo(String value) {
            addCriterion("user_type <=", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeLike(String value) {
            addCriterion("user_type like", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeNotLike(String value) {
            addCriterion("user_type not like", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeIn(List<String> values) {
            addCriterion("user_type in", values, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeNotIn(List<String> values) {
            addCriterion("user_type not in", values, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeBetween(String value1, String value2) {
            addCriterion("user_type between", value1, value2, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeNotBetween(String value1, String value2) {
            addCriterion("user_type not between", value1, value2, "userType");
            return (Criteria) this;
        }

        public Criteria andNickNameIsNull() {
            addCriterion("nick_name is null");
            return (Criteria) this;
        }

        public Criteria andNickNameIsNotNull() {
            addCriterion("nick_name is not null");
            return (Criteria) this;
        }

        public Criteria andNickNameEqualTo(String value) {
            addCriterion("nick_name =", value, "nickName");
            return (Criteria) this;
        }

        public Criteria andNickNameNotEqualTo(String value) {
            addCriterion("nick_name <>", value, "nickName");
            return (Criteria) this;
        }

        public Criteria andNickNameGreaterThan(String value) {
            addCriterion("nick_name >", value, "nickName");
            return (Criteria) this;
        }

        public Criteria andNickNameGreaterThanOrEqualTo(String value) {
            addCriterion("nick_name >=", value, "nickName");
            return (Criteria) this;
        }

        public Criteria andNickNameLessThan(String value) {
            addCriterion("nick_name <", value, "nickName");
            return (Criteria) this;
        }

        public Criteria andNickNameLessThanOrEqualTo(String value) {
            addCriterion("nick_name <=", value, "nickName");
            return (Criteria) this;
        }

        public Criteria andNickNameLike(String value) {
            addCriterion("nick_name like", value, "nickName");
            return (Criteria) this;
        }

        public Criteria andNickNameNotLike(String value) {
            addCriterion("nick_name not like", value, "nickName");
            return (Criteria) this;
        }

        public Criteria andNickNameIn(List<String> values) {
            addCriterion("nick_name in", values, "nickName");
            return (Criteria) this;
        }

        public Criteria andNickNameNotIn(List<String> values) {
            addCriterion("nick_name not in", values, "nickName");
            return (Criteria) this;
        }

        public Criteria andNickNameBetween(String value1, String value2) {
            addCriterion("nick_name between", value1, value2, "nickName");
            return (Criteria) this;
        }

        public Criteria andNickNameNotBetween(String value1, String value2) {
            addCriterion("nick_name not between", value1, value2, "nickName");
            return (Criteria) this;
        }

        public Criteria andRealNameIsNull() {
            addCriterion("real_name is null");
            return (Criteria) this;
        }

        public Criteria andRealNameIsNotNull() {
            addCriterion("real_name is not null");
            return (Criteria) this;
        }

        public Criteria andRealNameEqualTo(String value) {
            addCriterion("real_name =", value, "realName");
            return (Criteria) this;
        }

        public Criteria andRealNameNotEqualTo(String value) {
            addCriterion("real_name <>", value, "realName");
            return (Criteria) this;
        }

        public Criteria andRealNameGreaterThan(String value) {
            addCriterion("real_name >", value, "realName");
            return (Criteria) this;
        }

        public Criteria andRealNameGreaterThanOrEqualTo(String value) {
            addCriterion("real_name >=", value, "realName");
            return (Criteria) this;
        }

        public Criteria andRealNameLessThan(String value) {
            addCriterion("real_name <", value, "realName");
            return (Criteria) this;
        }

        public Criteria andRealNameLessThanOrEqualTo(String value) {
            addCriterion("real_name <=", value, "realName");
            return (Criteria) this;
        }

        public Criteria andRealNameLike(String value) {
            addCriterion("real_name like", value, "realName");
            return (Criteria) this;
        }

        public Criteria andRealNameNotLike(String value) {
            addCriterion("real_name not like", value, "realName");
            return (Criteria) this;
        }

        public Criteria andRealNameIn(List<String> values) {
            addCriterion("real_name in", values, "realName");
            return (Criteria) this;
        }

        public Criteria andRealNameNotIn(List<String> values) {
            addCriterion("real_name not in", values, "realName");
            return (Criteria) this;
        }

        public Criteria andRealNameBetween(String value1, String value2) {
            addCriterion("real_name between", value1, value2, "realName");
            return (Criteria) this;
        }

        public Criteria andRealNameNotBetween(String value1, String value2) {
            addCriterion("real_name not between", value1, value2, "realName");
            return (Criteria) this;
        }

        public Criteria andIdCardIsNull() {
            addCriterion("id_card is null");
            return (Criteria) this;
        }

        public Criteria andIdCardIsNotNull() {
            addCriterion("id_card is not null");
            return (Criteria) this;
        }

        public Criteria andIdCardEqualTo(String value) {
            addCriterion("id_card =", value, "idCard");
            return (Criteria) this;
        }

        public Criteria andIdCardNotEqualTo(String value) {
            addCriterion("id_card <>", value, "idCard");
            return (Criteria) this;
        }

        public Criteria andIdCardGreaterThan(String value) {
            addCriterion("id_card >", value, "idCard");
            return (Criteria) this;
        }

        public Criteria andIdCardGreaterThanOrEqualTo(String value) {
            addCriterion("id_card >=", value, "idCard");
            return (Criteria) this;
        }

        public Criteria andIdCardLessThan(String value) {
            addCriterion("id_card <", value, "idCard");
            return (Criteria) this;
        }

        public Criteria andIdCardLessThanOrEqualTo(String value) {
            addCriterion("id_card <=", value, "idCard");
            return (Criteria) this;
        }

        public Criteria andIdCardLike(String value) {
            addCriterion("id_card like", value, "idCard");
            return (Criteria) this;
        }

        public Criteria andIdCardNotLike(String value) {
            addCriterion("id_card not like", value, "idCard");
            return (Criteria) this;
        }

        public Criteria andIdCardIn(List<String> values) {
            addCriterion("id_card in", values, "idCard");
            return (Criteria) this;
        }

        public Criteria andIdCardNotIn(List<String> values) {
            addCriterion("id_card not in", values, "idCard");
            return (Criteria) this;
        }

        public Criteria andIdCardBetween(String value1, String value2) {
            addCriterion("id_card between", value1, value2, "idCard");
            return (Criteria) this;
        }

        public Criteria andIdCardNotBetween(String value1, String value2) {
            addCriterion("id_card not between", value1, value2, "idCard");
            return (Criteria) this;
        }

        public Criteria andIndateIsNull() {
            addCriterion("indate is null");
            return (Criteria) this;
        }

        public Criteria andIndateIsNotNull() {
            addCriterion("indate is not null");
            return (Criteria) this;
        }

        public Criteria andIndateEqualTo(String value) {
            addCriterion("indate =", value, "indate");
            return (Criteria) this;
        }

        public Criteria andIndateNotEqualTo(String value) {
            addCriterion("indate <>", value, "indate");
            return (Criteria) this;
        }

        public Criteria andIndateGreaterThan(String value) {
            addCriterion("indate >", value, "indate");
            return (Criteria) this;
        }

        public Criteria andIndateGreaterThanOrEqualTo(String value) {
            addCriterion("indate >=", value, "indate");
            return (Criteria) this;
        }

        public Criteria andIndateLessThan(String value) {
            addCriterion("indate <", value, "indate");
            return (Criteria) this;
        }

        public Criteria andIndateLessThanOrEqualTo(String value) {
            addCriterion("indate <=", value, "indate");
            return (Criteria) this;
        }

        public Criteria andIndateLike(String value) {
            addCriterion("indate like", value, "indate");
            return (Criteria) this;
        }

        public Criteria andIndateNotLike(String value) {
            addCriterion("indate not like", value, "indate");
            return (Criteria) this;
        }

        public Criteria andIndateIn(List<String> values) {
            addCriterion("indate in", values, "indate");
            return (Criteria) this;
        }

        public Criteria andIndateNotIn(List<String> values) {
            addCriterion("indate not in", values, "indate");
            return (Criteria) this;
        }

        public Criteria andIndateBetween(String value1, String value2) {
            addCriterion("indate between", value1, value2, "indate");
            return (Criteria) this;
        }

        public Criteria andIndateNotBetween(String value1, String value2) {
            addCriterion("indate not between", value1, value2, "indate");
            return (Criteria) this;
        }

        public Criteria andAuthenticatedIsNull() {
            addCriterion("authenticated is null");
            return (Criteria) this;
        }

        public Criteria andAuthenticatedIsNotNull() {
            addCriterion("authenticated is not null");
            return (Criteria) this;
        }

        public Criteria andAuthenticatedEqualTo(String value) {
            addCriterion("authenticated =", value, "authenticated");
            return (Criteria) this;
        }

        public Criteria andAuthenticatedNotEqualTo(String value) {
            addCriterion("authenticated <>", value, "authenticated");
            return (Criteria) this;
        }

        public Criteria andAuthenticatedGreaterThan(String value) {
            addCriterion("authenticated >", value, "authenticated");
            return (Criteria) this;
        }

        public Criteria andAuthenticatedGreaterThanOrEqualTo(String value) {
            addCriterion("authenticated >=", value, "authenticated");
            return (Criteria) this;
        }

        public Criteria andAuthenticatedLessThan(String value) {
            addCriterion("authenticated <", value, "authenticated");
            return (Criteria) this;
        }

        public Criteria andAuthenticatedLessThanOrEqualTo(String value) {
            addCriterion("authenticated <=", value, "authenticated");
            return (Criteria) this;
        }

        public Criteria andAuthenticatedLike(String value) {
            addCriterion("authenticated like", value, "authenticated");
            return (Criteria) this;
        }

        public Criteria andAuthenticatedNotLike(String value) {
            addCriterion("authenticated not like", value, "authenticated");
            return (Criteria) this;
        }

        public Criteria andAuthenticatedIn(List<String> values) {
            addCriterion("authenticated in", values, "authenticated");
            return (Criteria) this;
        }

        public Criteria andAuthenticatedNotIn(List<String> values) {
            addCriterion("authenticated not in", values, "authenticated");
            return (Criteria) this;
        }

        public Criteria andAuthenticatedBetween(String value1, String value2) {
            addCriterion("authenticated between", value1, value2, "authenticated");
            return (Criteria) this;
        }

        public Criteria andAuthenticatedNotBetween(String value1, String value2) {
            addCriterion("authenticated not between", value1, value2, "authenticated");
            return (Criteria) this;
        }

        public Criteria andWxNoIsNull() {
            addCriterion("wx_no is null");
            return (Criteria) this;
        }

        public Criteria andWxNoIsNotNull() {
            addCriterion("wx_no is not null");
            return (Criteria) this;
        }

        public Criteria andWxNoEqualTo(String value) {
            addCriterion("wx_no =", value, "wxNo");
            return (Criteria) this;
        }

        public Criteria andWxNoNotEqualTo(String value) {
            addCriterion("wx_no <>", value, "wxNo");
            return (Criteria) this;
        }

        public Criteria andWxNoGreaterThan(String value) {
            addCriterion("wx_no >", value, "wxNo");
            return (Criteria) this;
        }

        public Criteria andWxNoGreaterThanOrEqualTo(String value) {
            addCriterion("wx_no >=", value, "wxNo");
            return (Criteria) this;
        }

        public Criteria andWxNoLessThan(String value) {
            addCriterion("wx_no <", value, "wxNo");
            return (Criteria) this;
        }

        public Criteria andWxNoLessThanOrEqualTo(String value) {
            addCriterion("wx_no <=", value, "wxNo");
            return (Criteria) this;
        }

        public Criteria andWxNoLike(String value) {
            addCriterion("wx_no like", value, "wxNo");
            return (Criteria) this;
        }

        public Criteria andWxNoNotLike(String value) {
            addCriterion("wx_no not like", value, "wxNo");
            return (Criteria) this;
        }

        public Criteria andWxNoIn(List<String> values) {
            addCriterion("wx_no in", values, "wxNo");
            return (Criteria) this;
        }

        public Criteria andWxNoNotIn(List<String> values) {
            addCriterion("wx_no not in", values, "wxNo");
            return (Criteria) this;
        }

        public Criteria andWxNoBetween(String value1, String value2) {
            addCriterion("wx_no between", value1, value2, "wxNo");
            return (Criteria) this;
        }

        public Criteria andWxNoNotBetween(String value1, String value2) {
            addCriterion("wx_no not between", value1, value2, "wxNo");
            return (Criteria) this;
        }

        public Criteria andWxQrcodeIsNull() {
            addCriterion("wx_qrcode is null");
            return (Criteria) this;
        }

        public Criteria andWxQrcodeIsNotNull() {
            addCriterion("wx_qrcode is not null");
            return (Criteria) this;
        }

        public Criteria andWxQrcodeEqualTo(String value) {
            addCriterion("wx_qrcode =", value, "wxQrcode");
            return (Criteria) this;
        }

        public Criteria andWxQrcodeNotEqualTo(String value) {
            addCriterion("wx_qrcode <>", value, "wxQrcode");
            return (Criteria) this;
        }

        public Criteria andWxQrcodeGreaterThan(String value) {
            addCriterion("wx_qrcode >", value, "wxQrcode");
            return (Criteria) this;
        }

        public Criteria andWxQrcodeGreaterThanOrEqualTo(String value) {
            addCriterion("wx_qrcode >=", value, "wxQrcode");
            return (Criteria) this;
        }

        public Criteria andWxQrcodeLessThan(String value) {
            addCriterion("wx_qrcode <", value, "wxQrcode");
            return (Criteria) this;
        }

        public Criteria andWxQrcodeLessThanOrEqualTo(String value) {
            addCriterion("wx_qrcode <=", value, "wxQrcode");
            return (Criteria) this;
        }

        public Criteria andWxQrcodeLike(String value) {
            addCriterion("wx_qrcode like", value, "wxQrcode");
            return (Criteria) this;
        }

        public Criteria andWxQrcodeNotLike(String value) {
            addCriterion("wx_qrcode not like", value, "wxQrcode");
            return (Criteria) this;
        }

        public Criteria andWxQrcodeIn(List<String> values) {
            addCriterion("wx_qrcode in", values, "wxQrcode");
            return (Criteria) this;
        }

        public Criteria andWxQrcodeNotIn(List<String> values) {
            addCriterion("wx_qrcode not in", values, "wxQrcode");
            return (Criteria) this;
        }

        public Criteria andWxQrcodeBetween(String value1, String value2) {
            addCriterion("wx_qrcode between", value1, value2, "wxQrcode");
            return (Criteria) this;
        }

        public Criteria andWxQrcodeNotBetween(String value1, String value2) {
            addCriterion("wx_qrcode not between", value1, value2, "wxQrcode");
            return (Criteria) this;
        }

        public Criteria andAccountBalanceIsNull() {
            addCriterion("account_balance is null");
            return (Criteria) this;
        }

        public Criteria andAccountBalanceIsNotNull() {
            addCriterion("account_balance is not null");
            return (Criteria) this;
        }

        public Criteria andAccountBalanceEqualTo(BigDecimal value) {
            addCriterion("account_balance =", value, "accountBalance");
            return (Criteria) this;
        }

        public Criteria andAccountBalanceNotEqualTo(BigDecimal value) {
            addCriterion("account_balance <>", value, "accountBalance");
            return (Criteria) this;
        }

        public Criteria andAccountBalanceGreaterThan(BigDecimal value) {
            addCriterion("account_balance >", value, "accountBalance");
            return (Criteria) this;
        }

        public Criteria andAccountBalanceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("account_balance >=", value, "accountBalance");
            return (Criteria) this;
        }

        public Criteria andAccountBalanceLessThan(BigDecimal value) {
            addCriterion("account_balance <", value, "accountBalance");
            return (Criteria) this;
        }

        public Criteria andAccountBalanceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("account_balance <=", value, "accountBalance");
            return (Criteria) this;
        }

        public Criteria andAccountBalanceIn(List<BigDecimal> values) {
            addCriterion("account_balance in", values, "accountBalance");
            return (Criteria) this;
        }

        public Criteria andAccountBalanceNotIn(List<BigDecimal> values) {
            addCriterion("account_balance not in", values, "accountBalance");
            return (Criteria) this;
        }

        public Criteria andAccountBalanceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("account_balance between", value1, value2, "accountBalance");
            return (Criteria) this;
        }

        public Criteria andAccountBalanceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("account_balance not between", value1, value2, "accountBalance");
            return (Criteria) this;
        }

        public Criteria andProfitBalanceIsNull() {
            addCriterion("profit_balance is null");
            return (Criteria) this;
        }

        public Criteria andProfitBalanceIsNotNull() {
            addCriterion("profit_balance is not null");
            return (Criteria) this;
        }

        public Criteria andProfitBalanceEqualTo(BigDecimal value) {
            addCriterion("profit_balance =", value, "profitBalance");
            return (Criteria) this;
        }

        public Criteria andProfitBalanceNotEqualTo(BigDecimal value) {
            addCriterion("profit_balance <>", value, "profitBalance");
            return (Criteria) this;
        }

        public Criteria andProfitBalanceGreaterThan(BigDecimal value) {
            addCriterion("profit_balance >", value, "profitBalance");
            return (Criteria) this;
        }

        public Criteria andProfitBalanceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("profit_balance >=", value, "profitBalance");
            return (Criteria) this;
        }

        public Criteria andProfitBalanceLessThan(BigDecimal value) {
            addCriterion("profit_balance <", value, "profitBalance");
            return (Criteria) this;
        }

        public Criteria andProfitBalanceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("profit_balance <=", value, "profitBalance");
            return (Criteria) this;
        }

        public Criteria andProfitBalanceIn(List<BigDecimal> values) {
            addCriterion("profit_balance in", values, "profitBalance");
            return (Criteria) this;
        }

        public Criteria andProfitBalanceNotIn(List<BigDecimal> values) {
            addCriterion("profit_balance not in", values, "profitBalance");
            return (Criteria) this;
        }

        public Criteria andProfitBalanceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("profit_balance between", value1, value2, "profitBalance");
            return (Criteria) this;
        }

        public Criteria andProfitBalanceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("profit_balance not between", value1, value2, "profitBalance");
            return (Criteria) this;
        }

        public Criteria andSettleCardNoIsNull() {
            addCriterion("settle_card_no is null");
            return (Criteria) this;
        }

        public Criteria andSettleCardNoIsNotNull() {
            addCriterion("settle_card_no is not null");
            return (Criteria) this;
        }

        public Criteria andSettleCardNoEqualTo(String value) {
            addCriterion("settle_card_no =", value, "settleCardNo");
            return (Criteria) this;
        }

        public Criteria andSettleCardNoNotEqualTo(String value) {
            addCriterion("settle_card_no <>", value, "settleCardNo");
            return (Criteria) this;
        }

        public Criteria andSettleCardNoGreaterThan(String value) {
            addCriterion("settle_card_no >", value, "settleCardNo");
            return (Criteria) this;
        }

        public Criteria andSettleCardNoGreaterThanOrEqualTo(String value) {
            addCriterion("settle_card_no >=", value, "settleCardNo");
            return (Criteria) this;
        }

        public Criteria andSettleCardNoLessThan(String value) {
            addCriterion("settle_card_no <", value, "settleCardNo");
            return (Criteria) this;
        }

        public Criteria andSettleCardNoLessThanOrEqualTo(String value) {
            addCriterion("settle_card_no <=", value, "settleCardNo");
            return (Criteria) this;
        }

        public Criteria andSettleCardNoLike(String value) {
            addCriterion("settle_card_no like", value, "settleCardNo");
            return (Criteria) this;
        }

        public Criteria andSettleCardNoNotLike(String value) {
            addCriterion("settle_card_no not like", value, "settleCardNo");
            return (Criteria) this;
        }

        public Criteria andSettleCardNoIn(List<String> values) {
            addCriterion("settle_card_no in", values, "settleCardNo");
            return (Criteria) this;
        }

        public Criteria andSettleCardNoNotIn(List<String> values) {
            addCriterion("settle_card_no not in", values, "settleCardNo");
            return (Criteria) this;
        }

        public Criteria andSettleCardNoBetween(String value1, String value2) {
            addCriterion("settle_card_no between", value1, value2, "settleCardNo");
            return (Criteria) this;
        }

        public Criteria andSettleCardNoNotBetween(String value1, String value2) {
            addCriterion("settle_card_no not between", value1, value2, "settleCardNo");
            return (Criteria) this;
        }

        public Criteria andSettleNameIsNull() {
            addCriterion("settle_name is null");
            return (Criteria) this;
        }

        public Criteria andSettleNameIsNotNull() {
            addCriterion("settle_name is not null");
            return (Criteria) this;
        }

        public Criteria andSettleNameEqualTo(String value) {
            addCriterion("settle_name =", value, "settleName");
            return (Criteria) this;
        }

        public Criteria andSettleNameNotEqualTo(String value) {
            addCriterion("settle_name <>", value, "settleName");
            return (Criteria) this;
        }

        public Criteria andSettleNameGreaterThan(String value) {
            addCriterion("settle_name >", value, "settleName");
            return (Criteria) this;
        }

        public Criteria andSettleNameGreaterThanOrEqualTo(String value) {
            addCriterion("settle_name >=", value, "settleName");
            return (Criteria) this;
        }

        public Criteria andSettleNameLessThan(String value) {
            addCriterion("settle_name <", value, "settleName");
            return (Criteria) this;
        }

        public Criteria andSettleNameLessThanOrEqualTo(String value) {
            addCriterion("settle_name <=", value, "settleName");
            return (Criteria) this;
        }

        public Criteria andSettleNameLike(String value) {
            addCriterion("settle_name like", value, "settleName");
            return (Criteria) this;
        }

        public Criteria andSettleNameNotLike(String value) {
            addCriterion("settle_name not like", value, "settleName");
            return (Criteria) this;
        }

        public Criteria andSettleNameIn(List<String> values) {
            addCriterion("settle_name in", values, "settleName");
            return (Criteria) this;
        }

        public Criteria andSettleNameNotIn(List<String> values) {
            addCriterion("settle_name not in", values, "settleName");
            return (Criteria) this;
        }

        public Criteria andSettleNameBetween(String value1, String value2) {
            addCriterion("settle_name between", value1, value2, "settleName");
            return (Criteria) this;
        }

        public Criteria andSettleNameNotBetween(String value1, String value2) {
            addCriterion("settle_name not between", value1, value2, "settleName");
            return (Criteria) this;
        }

        public Criteria andSettleBankIsNull() {
            addCriterion("settle_bank is null");
            return (Criteria) this;
        }

        public Criteria andSettleBankIsNotNull() {
            addCriterion("settle_bank is not null");
            return (Criteria) this;
        }

        public Criteria andSettleBankEqualTo(String value) {
            addCriterion("settle_bank =", value, "settleBank");
            return (Criteria) this;
        }

        public Criteria andSettleBankNotEqualTo(String value) {
            addCriterion("settle_bank <>", value, "settleBank");
            return (Criteria) this;
        }

        public Criteria andSettleBankGreaterThan(String value) {
            addCriterion("settle_bank >", value, "settleBank");
            return (Criteria) this;
        }

        public Criteria andSettleBankGreaterThanOrEqualTo(String value) {
            addCriterion("settle_bank >=", value, "settleBank");
            return (Criteria) this;
        }

        public Criteria andSettleBankLessThan(String value) {
            addCriterion("settle_bank <", value, "settleBank");
            return (Criteria) this;
        }

        public Criteria andSettleBankLessThanOrEqualTo(String value) {
            addCriterion("settle_bank <=", value, "settleBank");
            return (Criteria) this;
        }

        public Criteria andSettleBankLike(String value) {
            addCriterion("settle_bank like", value, "settleBank");
            return (Criteria) this;
        }

        public Criteria andSettleBankNotLike(String value) {
            addCriterion("settle_bank not like", value, "settleBank");
            return (Criteria) this;
        }

        public Criteria andSettleBankIn(List<String> values) {
            addCriterion("settle_bank in", values, "settleBank");
            return (Criteria) this;
        }

        public Criteria andSettleBankNotIn(List<String> values) {
            addCriterion("settle_bank not in", values, "settleBank");
            return (Criteria) this;
        }

        public Criteria andSettleBankBetween(String value1, String value2) {
            addCriterion("settle_bank between", value1, value2, "settleBank");
            return (Criteria) this;
        }

        public Criteria andSettleBankNotBetween(String value1, String value2) {
            addCriterion("settle_bank not between", value1, value2, "settleBank");
            return (Criteria) this;
        }

        public Criteria andSettleProvinceIsNull() {
            addCriterion("settle_province is null");
            return (Criteria) this;
        }

        public Criteria andSettleProvinceIsNotNull() {
            addCriterion("settle_province is not null");
            return (Criteria) this;
        }

        public Criteria andSettleProvinceEqualTo(String value) {
            addCriterion("settle_province =", value, "settleProvince");
            return (Criteria) this;
        }

        public Criteria andSettleProvinceNotEqualTo(String value) {
            addCriterion("settle_province <>", value, "settleProvince");
            return (Criteria) this;
        }

        public Criteria andSettleProvinceGreaterThan(String value) {
            addCriterion("settle_province >", value, "settleProvince");
            return (Criteria) this;
        }

        public Criteria andSettleProvinceGreaterThanOrEqualTo(String value) {
            addCriterion("settle_province >=", value, "settleProvince");
            return (Criteria) this;
        }

        public Criteria andSettleProvinceLessThan(String value) {
            addCriterion("settle_province <", value, "settleProvince");
            return (Criteria) this;
        }

        public Criteria andSettleProvinceLessThanOrEqualTo(String value) {
            addCriterion("settle_province <=", value, "settleProvince");
            return (Criteria) this;
        }

        public Criteria andSettleProvinceLike(String value) {
            addCriterion("settle_province like", value, "settleProvince");
            return (Criteria) this;
        }

        public Criteria andSettleProvinceNotLike(String value) {
            addCriterion("settle_province not like", value, "settleProvince");
            return (Criteria) this;
        }

        public Criteria andSettleProvinceIn(List<String> values) {
            addCriterion("settle_province in", values, "settleProvince");
            return (Criteria) this;
        }

        public Criteria andSettleProvinceNotIn(List<String> values) {
            addCriterion("settle_province not in", values, "settleProvince");
            return (Criteria) this;
        }

        public Criteria andSettleProvinceBetween(String value1, String value2) {
            addCriterion("settle_province between", value1, value2, "settleProvince");
            return (Criteria) this;
        }

        public Criteria andSettleProvinceNotBetween(String value1, String value2) {
            addCriterion("settle_province not between", value1, value2, "settleProvince");
            return (Criteria) this;
        }

        public Criteria andSettleCityIsNull() {
            addCriterion("settle_city is null");
            return (Criteria) this;
        }

        public Criteria andSettleCityIsNotNull() {
            addCriterion("settle_city is not null");
            return (Criteria) this;
        }

        public Criteria andSettleCityEqualTo(String value) {
            addCriterion("settle_city =", value, "settleCity");
            return (Criteria) this;
        }

        public Criteria andSettleCityNotEqualTo(String value) {
            addCriterion("settle_city <>", value, "settleCity");
            return (Criteria) this;
        }

        public Criteria andSettleCityGreaterThan(String value) {
            addCriterion("settle_city >", value, "settleCity");
            return (Criteria) this;
        }

        public Criteria andSettleCityGreaterThanOrEqualTo(String value) {
            addCriterion("settle_city >=", value, "settleCity");
            return (Criteria) this;
        }

        public Criteria andSettleCityLessThan(String value) {
            addCriterion("settle_city <", value, "settleCity");
            return (Criteria) this;
        }

        public Criteria andSettleCityLessThanOrEqualTo(String value) {
            addCriterion("settle_city <=", value, "settleCity");
            return (Criteria) this;
        }

        public Criteria andSettleCityLike(String value) {
            addCriterion("settle_city like", value, "settleCity");
            return (Criteria) this;
        }

        public Criteria andSettleCityNotLike(String value) {
            addCriterion("settle_city not like", value, "settleCity");
            return (Criteria) this;
        }

        public Criteria andSettleCityIn(List<String> values) {
            addCriterion("settle_city in", values, "settleCity");
            return (Criteria) this;
        }

        public Criteria andSettleCityNotIn(List<String> values) {
            addCriterion("settle_city not in", values, "settleCity");
            return (Criteria) this;
        }

        public Criteria andSettleCityBetween(String value1, String value2) {
            addCriterion("settle_city between", value1, value2, "settleCity");
            return (Criteria) this;
        }

        public Criteria andSettleCityNotBetween(String value1, String value2) {
            addCriterion("settle_city not between", value1, value2, "settleCity");
            return (Criteria) this;
        }

        public Criteria andSettleBranchBankIsNull() {
            addCriterion("settle_branch_bank is null");
            return (Criteria) this;
        }

        public Criteria andSettleBranchBankIsNotNull() {
            addCriterion("settle_branch_bank is not null");
            return (Criteria) this;
        }

        public Criteria andSettleBranchBankEqualTo(String value) {
            addCriterion("settle_branch_bank =", value, "settleBranchBank");
            return (Criteria) this;
        }

        public Criteria andSettleBranchBankNotEqualTo(String value) {
            addCriterion("settle_branch_bank <>", value, "settleBranchBank");
            return (Criteria) this;
        }

        public Criteria andSettleBranchBankGreaterThan(String value) {
            addCriterion("settle_branch_bank >", value, "settleBranchBank");
            return (Criteria) this;
        }

        public Criteria andSettleBranchBankGreaterThanOrEqualTo(String value) {
            addCriterion("settle_branch_bank >=", value, "settleBranchBank");
            return (Criteria) this;
        }

        public Criteria andSettleBranchBankLessThan(String value) {
            addCriterion("settle_branch_bank <", value, "settleBranchBank");
            return (Criteria) this;
        }

        public Criteria andSettleBranchBankLessThanOrEqualTo(String value) {
            addCriterion("settle_branch_bank <=", value, "settleBranchBank");
            return (Criteria) this;
        }

        public Criteria andSettleBranchBankLike(String value) {
            addCriterion("settle_branch_bank like", value, "settleBranchBank");
            return (Criteria) this;
        }

        public Criteria andSettleBranchBankNotLike(String value) {
            addCriterion("settle_branch_bank not like", value, "settleBranchBank");
            return (Criteria) this;
        }

        public Criteria andSettleBranchBankIn(List<String> values) {
            addCriterion("settle_branch_bank in", values, "settleBranchBank");
            return (Criteria) this;
        }

        public Criteria andSettleBranchBankNotIn(List<String> values) {
            addCriterion("settle_branch_bank not in", values, "settleBranchBank");
            return (Criteria) this;
        }

        public Criteria andSettleBranchBankBetween(String value1, String value2) {
            addCriterion("settle_branch_bank between", value1, value2, "settleBranchBank");
            return (Criteria) this;
        }

        public Criteria andSettleBranchBankNotBetween(String value1, String value2) {
            addCriterion("settle_branch_bank not between", value1, value2, "settleBranchBank");
            return (Criteria) this;
        }

        public Criteria andSettlePhoneIsNull() {
            addCriterion("settle_phone is null");
            return (Criteria) this;
        }

        public Criteria andSettlePhoneIsNotNull() {
            addCriterion("settle_phone is not null");
            return (Criteria) this;
        }

        public Criteria andSettlePhoneEqualTo(String value) {
            addCriterion("settle_phone =", value, "settlePhone");
            return (Criteria) this;
        }

        public Criteria andSettlePhoneNotEqualTo(String value) {
            addCriterion("settle_phone <>", value, "settlePhone");
            return (Criteria) this;
        }

        public Criteria andSettlePhoneGreaterThan(String value) {
            addCriterion("settle_phone >", value, "settlePhone");
            return (Criteria) this;
        }

        public Criteria andSettlePhoneGreaterThanOrEqualTo(String value) {
            addCriterion("settle_phone >=", value, "settlePhone");
            return (Criteria) this;
        }

        public Criteria andSettlePhoneLessThan(String value) {
            addCriterion("settle_phone <", value, "settlePhone");
            return (Criteria) this;
        }

        public Criteria andSettlePhoneLessThanOrEqualTo(String value) {
            addCriterion("settle_phone <=", value, "settlePhone");
            return (Criteria) this;
        }

        public Criteria andSettlePhoneLike(String value) {
            addCriterion("settle_phone like", value, "settlePhone");
            return (Criteria) this;
        }

        public Criteria andSettlePhoneNotLike(String value) {
            addCriterion("settle_phone not like", value, "settlePhone");
            return (Criteria) this;
        }

        public Criteria andSettlePhoneIn(List<String> values) {
            addCriterion("settle_phone in", values, "settlePhone");
            return (Criteria) this;
        }

        public Criteria andSettlePhoneNotIn(List<String> values) {
            addCriterion("settle_phone not in", values, "settlePhone");
            return (Criteria) this;
        }

        public Criteria andSettlePhoneBetween(String value1, String value2) {
            addCriterion("settle_phone between", value1, value2, "settlePhone");
            return (Criteria) this;
        }

        public Criteria andSettlePhoneNotBetween(String value1, String value2) {
            addCriterion("settle_phone not between", value1, value2, "settlePhone");
            return (Criteria) this;
        }

        public Criteria andHeadImageIsNull() {
            addCriterion("head_image is null");
            return (Criteria) this;
        }

        public Criteria andHeadImageIsNotNull() {
            addCriterion("head_image is not null");
            return (Criteria) this;
        }

        public Criteria andHeadImageEqualTo(String value) {
            addCriterion("head_image =", value, "headImage");
            return (Criteria) this;
        }

        public Criteria andHeadImageNotEqualTo(String value) {
            addCriterion("head_image <>", value, "headImage");
            return (Criteria) this;
        }

        public Criteria andHeadImageGreaterThan(String value) {
            addCriterion("head_image >", value, "headImage");
            return (Criteria) this;
        }

        public Criteria andHeadImageGreaterThanOrEqualTo(String value) {
            addCriterion("head_image >=", value, "headImage");
            return (Criteria) this;
        }

        public Criteria andHeadImageLessThan(String value) {
            addCriterion("head_image <", value, "headImage");
            return (Criteria) this;
        }

        public Criteria andHeadImageLessThanOrEqualTo(String value) {
            addCriterion("head_image <=", value, "headImage");
            return (Criteria) this;
        }

        public Criteria andHeadImageLike(String value) {
            addCriterion("head_image like", value, "headImage");
            return (Criteria) this;
        }

        public Criteria andHeadImageNotLike(String value) {
            addCriterion("head_image not like", value, "headImage");
            return (Criteria) this;
        }

        public Criteria andHeadImageIn(List<String> values) {
            addCriterion("head_image in", values, "headImage");
            return (Criteria) this;
        }

        public Criteria andHeadImageNotIn(List<String> values) {
            addCriterion("head_image not in", values, "headImage");
            return (Criteria) this;
        }

        public Criteria andHeadImageBetween(String value1, String value2) {
            addCriterion("head_image between", value1, value2, "headImage");
            return (Criteria) this;
        }

        public Criteria andHeadImageNotBetween(String value1, String value2) {
            addCriterion("head_image not between", value1, value2, "headImage");
            return (Criteria) this;
        }

        public Criteria andPrivacyIsNull() {
            addCriterion("privacy is null");
            return (Criteria) this;
        }

        public Criteria andPrivacyIsNotNull() {
            addCriterion("privacy is not null");
            return (Criteria) this;
        }

        public Criteria andPrivacyEqualTo(String value) {
            addCriterion("privacy =", value, "privacy");
            return (Criteria) this;
        }

        public Criteria andPrivacyNotEqualTo(String value) {
            addCriterion("privacy <>", value, "privacy");
            return (Criteria) this;
        }

        public Criteria andPrivacyGreaterThan(String value) {
            addCriterion("privacy >", value, "privacy");
            return (Criteria) this;
        }

        public Criteria andPrivacyGreaterThanOrEqualTo(String value) {
            addCriterion("privacy >=", value, "privacy");
            return (Criteria) this;
        }

        public Criteria andPrivacyLessThan(String value) {
            addCriterion("privacy <", value, "privacy");
            return (Criteria) this;
        }

        public Criteria andPrivacyLessThanOrEqualTo(String value) {
            addCriterion("privacy <=", value, "privacy");
            return (Criteria) this;
        }

        public Criteria andPrivacyLike(String value) {
            addCriterion("privacy like", value, "privacy");
            return (Criteria) this;
        }

        public Criteria andPrivacyNotLike(String value) {
            addCriterion("privacy not like", value, "privacy");
            return (Criteria) this;
        }

        public Criteria andPrivacyIn(List<String> values) {
            addCriterion("privacy in", values, "privacy");
            return (Criteria) this;
        }

        public Criteria andPrivacyNotIn(List<String> values) {
            addCriterion("privacy not in", values, "privacy");
            return (Criteria) this;
        }

        public Criteria andPrivacyBetween(String value1, String value2) {
            addCriterion("privacy between", value1, value2, "privacy");
            return (Criteria) this;
        }

        public Criteria andPrivacyNotBetween(String value1, String value2) {
            addCriterion("privacy not between", value1, value2, "privacy");
            return (Criteria) this;
        }

        public Criteria andSettleCardZScanIsNull() {
            addCriterion("settle_card_z_scan is null");
            return (Criteria) this;
        }

        public Criteria andSettleCardZScanIsNotNull() {
            addCriterion("settle_card_z_scan is not null");
            return (Criteria) this;
        }

        public Criteria andSettleCardZScanEqualTo(String value) {
            addCriterion("settle_card_z_scan =", value, "settleCardZScan");
            return (Criteria) this;
        }

        public Criteria andSettleCardZScanNotEqualTo(String value) {
            addCriterion("settle_card_z_scan <>", value, "settleCardZScan");
            return (Criteria) this;
        }

        public Criteria andSettleCardZScanGreaterThan(String value) {
            addCriterion("settle_card_z_scan >", value, "settleCardZScan");
            return (Criteria) this;
        }

        public Criteria andSettleCardZScanGreaterThanOrEqualTo(String value) {
            addCriterion("settle_card_z_scan >=", value, "settleCardZScan");
            return (Criteria) this;
        }

        public Criteria andSettleCardZScanLessThan(String value) {
            addCriterion("settle_card_z_scan <", value, "settleCardZScan");
            return (Criteria) this;
        }

        public Criteria andSettleCardZScanLessThanOrEqualTo(String value) {
            addCriterion("settle_card_z_scan <=", value, "settleCardZScan");
            return (Criteria) this;
        }

        public Criteria andSettleCardZScanLike(String value) {
            addCriterion("settle_card_z_scan like", value, "settleCardZScan");
            return (Criteria) this;
        }

        public Criteria andSettleCardZScanNotLike(String value) {
            addCriterion("settle_card_z_scan not like", value, "settleCardZScan");
            return (Criteria) this;
        }

        public Criteria andSettleCardZScanIn(List<String> values) {
            addCriterion("settle_card_z_scan in", values, "settleCardZScan");
            return (Criteria) this;
        }

        public Criteria andSettleCardZScanNotIn(List<String> values) {
            addCriterion("settle_card_z_scan not in", values, "settleCardZScan");
            return (Criteria) this;
        }

        public Criteria andSettleCardZScanBetween(String value1, String value2) {
            addCriterion("settle_card_z_scan between", value1, value2, "settleCardZScan");
            return (Criteria) this;
        }

        public Criteria andSettleCardZScanNotBetween(String value1, String value2) {
            addCriterion("settle_card_z_scan not between", value1, value2, "settleCardZScan");
            return (Criteria) this;
        }

        public Criteria andSettleCardFScanIsNull() {
            addCriterion("settle_card_f_scan is null");
            return (Criteria) this;
        }

        public Criteria andSettleCardFScanIsNotNull() {
            addCriterion("settle_card_f_scan is not null");
            return (Criteria) this;
        }

        public Criteria andSettleCardFScanEqualTo(String value) {
            addCriterion("settle_card_f_scan =", value, "settleCardFScan");
            return (Criteria) this;
        }

        public Criteria andSettleCardFScanNotEqualTo(String value) {
            addCriterion("settle_card_f_scan <>", value, "settleCardFScan");
            return (Criteria) this;
        }

        public Criteria andSettleCardFScanGreaterThan(String value) {
            addCriterion("settle_card_f_scan >", value, "settleCardFScan");
            return (Criteria) this;
        }

        public Criteria andSettleCardFScanGreaterThanOrEqualTo(String value) {
            addCriterion("settle_card_f_scan >=", value, "settleCardFScan");
            return (Criteria) this;
        }

        public Criteria andSettleCardFScanLessThan(String value) {
            addCriterion("settle_card_f_scan <", value, "settleCardFScan");
            return (Criteria) this;
        }

        public Criteria andSettleCardFScanLessThanOrEqualTo(String value) {
            addCriterion("settle_card_f_scan <=", value, "settleCardFScan");
            return (Criteria) this;
        }

        public Criteria andSettleCardFScanLike(String value) {
            addCriterion("settle_card_f_scan like", value, "settleCardFScan");
            return (Criteria) this;
        }

        public Criteria andSettleCardFScanNotLike(String value) {
            addCriterion("settle_card_f_scan not like", value, "settleCardFScan");
            return (Criteria) this;
        }

        public Criteria andSettleCardFScanIn(List<String> values) {
            addCriterion("settle_card_f_scan in", values, "settleCardFScan");
            return (Criteria) this;
        }

        public Criteria andSettleCardFScanNotIn(List<String> values) {
            addCriterion("settle_card_f_scan not in", values, "settleCardFScan");
            return (Criteria) this;
        }

        public Criteria andSettleCardFScanBetween(String value1, String value2) {
            addCriterion("settle_card_f_scan between", value1, value2, "settleCardFScan");
            return (Criteria) this;
        }

        public Criteria andSettleCardFScanNotBetween(String value1, String value2) {
            addCriterion("settle_card_f_scan not between", value1, value2, "settleCardFScan");
            return (Criteria) this;
        }

        public Criteria andIdCardZImageIsNull() {
            addCriterion("id_card_z_image is null");
            return (Criteria) this;
        }

        public Criteria andIdCardZImageIsNotNull() {
            addCriterion("id_card_z_image is not null");
            return (Criteria) this;
        }

        public Criteria andIdCardZImageEqualTo(String value) {
            addCriterion("id_card_z_image =", value, "idCardZImage");
            return (Criteria) this;
        }

        public Criteria andIdCardZImageNotEqualTo(String value) {
            addCriterion("id_card_z_image <>", value, "idCardZImage");
            return (Criteria) this;
        }

        public Criteria andIdCardZImageGreaterThan(String value) {
            addCriterion("id_card_z_image >", value, "idCardZImage");
            return (Criteria) this;
        }

        public Criteria andIdCardZImageGreaterThanOrEqualTo(String value) {
            addCriterion("id_card_z_image >=", value, "idCardZImage");
            return (Criteria) this;
        }

        public Criteria andIdCardZImageLessThan(String value) {
            addCriterion("id_card_z_image <", value, "idCardZImage");
            return (Criteria) this;
        }

        public Criteria andIdCardZImageLessThanOrEqualTo(String value) {
            addCriterion("id_card_z_image <=", value, "idCardZImage");
            return (Criteria) this;
        }

        public Criteria andIdCardZImageLike(String value) {
            addCriterion("id_card_z_image like", value, "idCardZImage");
            return (Criteria) this;
        }

        public Criteria andIdCardZImageNotLike(String value) {
            addCriterion("id_card_z_image not like", value, "idCardZImage");
            return (Criteria) this;
        }

        public Criteria andIdCardZImageIn(List<String> values) {
            addCriterion("id_card_z_image in", values, "idCardZImage");
            return (Criteria) this;
        }

        public Criteria andIdCardZImageNotIn(List<String> values) {
            addCriterion("id_card_z_image not in", values, "idCardZImage");
            return (Criteria) this;
        }

        public Criteria andIdCardZImageBetween(String value1, String value2) {
            addCriterion("id_card_z_image between", value1, value2, "idCardZImage");
            return (Criteria) this;
        }

        public Criteria andIdCardZImageNotBetween(String value1, String value2) {
            addCriterion("id_card_z_image not between", value1, value2, "idCardZImage");
            return (Criteria) this;
        }

        public Criteria andIdCardFImageIsNull() {
            addCriterion("id_card_f_image is null");
            return (Criteria) this;
        }

        public Criteria andIdCardFImageIsNotNull() {
            addCriterion("id_card_f_image is not null");
            return (Criteria) this;
        }

        public Criteria andIdCardFImageEqualTo(String value) {
            addCriterion("id_card_f_image =", value, "idCardFImage");
            return (Criteria) this;
        }

        public Criteria andIdCardFImageNotEqualTo(String value) {
            addCriterion("id_card_f_image <>", value, "idCardFImage");
            return (Criteria) this;
        }

        public Criteria andIdCardFImageGreaterThan(String value) {
            addCriterion("id_card_f_image >", value, "idCardFImage");
            return (Criteria) this;
        }

        public Criteria andIdCardFImageGreaterThanOrEqualTo(String value) {
            addCriterion("id_card_f_image >=", value, "idCardFImage");
            return (Criteria) this;
        }

        public Criteria andIdCardFImageLessThan(String value) {
            addCriterion("id_card_f_image <", value, "idCardFImage");
            return (Criteria) this;
        }

        public Criteria andIdCardFImageLessThanOrEqualTo(String value) {
            addCriterion("id_card_f_image <=", value, "idCardFImage");
            return (Criteria) this;
        }

        public Criteria andIdCardFImageLike(String value) {
            addCriterion("id_card_f_image like", value, "idCardFImage");
            return (Criteria) this;
        }

        public Criteria andIdCardFImageNotLike(String value) {
            addCriterion("id_card_f_image not like", value, "idCardFImage");
            return (Criteria) this;
        }

        public Criteria andIdCardFImageIn(List<String> values) {
            addCriterion("id_card_f_image in", values, "idCardFImage");
            return (Criteria) this;
        }

        public Criteria andIdCardFImageNotIn(List<String> values) {
            addCriterion("id_card_f_image not in", values, "idCardFImage");
            return (Criteria) this;
        }

        public Criteria andIdCardFImageBetween(String value1, String value2) {
            addCriterion("id_card_f_image between", value1, value2, "idCardFImage");
            return (Criteria) this;
        }

        public Criteria andIdCardFImageNotBetween(String value1, String value2) {
            addCriterion("id_card_f_image not between", value1, value2, "idCardFImage");
            return (Criteria) this;
        }

        public Criteria andHandIdcardImageIsNull() {
            addCriterion("hand_idcard_image is null");
            return (Criteria) this;
        }

        public Criteria andHandIdcardImageIsNotNull() {
            addCriterion("hand_idcard_image is not null");
            return (Criteria) this;
        }

        public Criteria andHandIdcardImageEqualTo(String value) {
            addCriterion("hand_idcard_image =", value, "handIdcardImage");
            return (Criteria) this;
        }

        public Criteria andHandIdcardImageNotEqualTo(String value) {
            addCriterion("hand_idcard_image <>", value, "handIdcardImage");
            return (Criteria) this;
        }

        public Criteria andHandIdcardImageGreaterThan(String value) {
            addCriterion("hand_idcard_image >", value, "handIdcardImage");
            return (Criteria) this;
        }

        public Criteria andHandIdcardImageGreaterThanOrEqualTo(String value) {
            addCriterion("hand_idcard_image >=", value, "handIdcardImage");
            return (Criteria) this;
        }

        public Criteria andHandIdcardImageLessThan(String value) {
            addCriterion("hand_idcard_image <", value, "handIdcardImage");
            return (Criteria) this;
        }

        public Criteria andHandIdcardImageLessThanOrEqualTo(String value) {
            addCriterion("hand_idcard_image <=", value, "handIdcardImage");
            return (Criteria) this;
        }

        public Criteria andHandIdcardImageLike(String value) {
            addCriterion("hand_idcard_image like", value, "handIdcardImage");
            return (Criteria) this;
        }

        public Criteria andHandIdcardImageNotLike(String value) {
            addCriterion("hand_idcard_image not like", value, "handIdcardImage");
            return (Criteria) this;
        }

        public Criteria andHandIdcardImageIn(List<String> values) {
            addCriterion("hand_idcard_image in", values, "handIdcardImage");
            return (Criteria) this;
        }

        public Criteria andHandIdcardImageNotIn(List<String> values) {
            addCriterion("hand_idcard_image not in", values, "handIdcardImage");
            return (Criteria) this;
        }

        public Criteria andHandIdcardImageBetween(String value1, String value2) {
            addCriterion("hand_idcard_image between", value1, value2, "handIdcardImage");
            return (Criteria) this;
        }

        public Criteria andHandIdcardImageNotBetween(String value1, String value2) {
            addCriterion("hand_idcard_image not between", value1, value2, "handIdcardImage");
            return (Criteria) this;
        }

        public Criteria andCreditCardZImageIsNull() {
            addCriterion("credit_card_z_image is null");
            return (Criteria) this;
        }

        public Criteria andCreditCardZImageIsNotNull() {
            addCriterion("credit_card_z_image is not null");
            return (Criteria) this;
        }

        public Criteria andCreditCardZImageEqualTo(String value) {
            addCriterion("credit_card_z_image =", value, "creditCardZImage");
            return (Criteria) this;
        }

        public Criteria andCreditCardZImageNotEqualTo(String value) {
            addCriterion("credit_card_z_image <>", value, "creditCardZImage");
            return (Criteria) this;
        }

        public Criteria andCreditCardZImageGreaterThan(String value) {
            addCriterion("credit_card_z_image >", value, "creditCardZImage");
            return (Criteria) this;
        }

        public Criteria andCreditCardZImageGreaterThanOrEqualTo(String value) {
            addCriterion("credit_card_z_image >=", value, "creditCardZImage");
            return (Criteria) this;
        }

        public Criteria andCreditCardZImageLessThan(String value) {
            addCriterion("credit_card_z_image <", value, "creditCardZImage");
            return (Criteria) this;
        }

        public Criteria andCreditCardZImageLessThanOrEqualTo(String value) {
            addCriterion("credit_card_z_image <=", value, "creditCardZImage");
            return (Criteria) this;
        }

        public Criteria andCreditCardZImageLike(String value) {
            addCriterion("credit_card_z_image like", value, "creditCardZImage");
            return (Criteria) this;
        }

        public Criteria andCreditCardZImageNotLike(String value) {
            addCriterion("credit_card_z_image not like", value, "creditCardZImage");
            return (Criteria) this;
        }

        public Criteria andCreditCardZImageIn(List<String> values) {
            addCriterion("credit_card_z_image in", values, "creditCardZImage");
            return (Criteria) this;
        }

        public Criteria andCreditCardZImageNotIn(List<String> values) {
            addCriterion("credit_card_z_image not in", values, "creditCardZImage");
            return (Criteria) this;
        }

        public Criteria andCreditCardZImageBetween(String value1, String value2) {
            addCriterion("credit_card_z_image between", value1, value2, "creditCardZImage");
            return (Criteria) this;
        }

        public Criteria andCreditCardZImageNotBetween(String value1, String value2) {
            addCriterion("credit_card_z_image not between", value1, value2, "creditCardZImage");
            return (Criteria) this;
        }

        public Criteria andHandCreditImageIsNull() {
            addCriterion("hand_credit_image is null");
            return (Criteria) this;
        }

        public Criteria andHandCreditImageIsNotNull() {
            addCriterion("hand_credit_image is not null");
            return (Criteria) this;
        }

        public Criteria andHandCreditImageEqualTo(String value) {
            addCriterion("hand_credit_image =", value, "handCreditImage");
            return (Criteria) this;
        }

        public Criteria andHandCreditImageNotEqualTo(String value) {
            addCriterion("hand_credit_image <>", value, "handCreditImage");
            return (Criteria) this;
        }

        public Criteria andHandCreditImageGreaterThan(String value) {
            addCriterion("hand_credit_image >", value, "handCreditImage");
            return (Criteria) this;
        }

        public Criteria andHandCreditImageGreaterThanOrEqualTo(String value) {
            addCriterion("hand_credit_image >=", value, "handCreditImage");
            return (Criteria) this;
        }

        public Criteria andHandCreditImageLessThan(String value) {
            addCriterion("hand_credit_image <", value, "handCreditImage");
            return (Criteria) this;
        }

        public Criteria andHandCreditImageLessThanOrEqualTo(String value) {
            addCriterion("hand_credit_image <=", value, "handCreditImage");
            return (Criteria) this;
        }

        public Criteria andHandCreditImageLike(String value) {
            addCriterion("hand_credit_image like", value, "handCreditImage");
            return (Criteria) this;
        }

        public Criteria andHandCreditImageNotLike(String value) {
            addCriterion("hand_credit_image not like", value, "handCreditImage");
            return (Criteria) this;
        }

        public Criteria andHandCreditImageIn(List<String> values) {
            addCriterion("hand_credit_image in", values, "handCreditImage");
            return (Criteria) this;
        }

        public Criteria andHandCreditImageNotIn(List<String> values) {
            addCriterion("hand_credit_image not in", values, "handCreditImage");
            return (Criteria) this;
        }

        public Criteria andHandCreditImageBetween(String value1, String value2) {
            addCriterion("hand_credit_image between", value1, value2, "handCreditImage");
            return (Criteria) this;
        }

        public Criteria andHandCreditImageNotBetween(String value1, String value2) {
            addCriterion("hand_credit_image not between", value1, value2, "handCreditImage");
            return (Criteria) this;
        }

        public Criteria andRechargeReceiptIsNull() {
            addCriterion("\"recharge_ receipt\" is null");
            return (Criteria) this;
        }

        public Criteria andRechargeReceiptIsNotNull() {
            addCriterion("\"recharge_ receipt\" is not null");
            return (Criteria) this;
        }

        public Criteria andRechargeReceiptEqualTo(String value) {
            addCriterion("\"recharge_ receipt\" =", value, "rechargeReceipt");
            return (Criteria) this;
        }

        public Criteria andRechargeReceiptNotEqualTo(String value) {
            addCriterion("\"recharge_ receipt\" <>", value, "rechargeReceipt");
            return (Criteria) this;
        }

        public Criteria andRechargeReceiptGreaterThan(String value) {
            addCriterion("\"recharge_ receipt\" >", value, "rechargeReceipt");
            return (Criteria) this;
        }

        public Criteria andRechargeReceiptGreaterThanOrEqualTo(String value) {
            addCriterion("\"recharge_ receipt\" >=", value, "rechargeReceipt");
            return (Criteria) this;
        }

        public Criteria andRechargeReceiptLessThan(String value) {
            addCriterion("\"recharge_ receipt\" <", value, "rechargeReceipt");
            return (Criteria) this;
        }

        public Criteria andRechargeReceiptLessThanOrEqualTo(String value) {
            addCriterion("\"recharge_ receipt\" <=", value, "rechargeReceipt");
            return (Criteria) this;
        }

        public Criteria andRechargeReceiptLike(String value) {
            addCriterion("\"recharge_ receipt\" like", value, "rechargeReceipt");
            return (Criteria) this;
        }

        public Criteria andRechargeReceiptNotLike(String value) {
            addCriterion("\"recharge_ receipt\" not like", value, "rechargeReceipt");
            return (Criteria) this;
        }

        public Criteria andRechargeReceiptIn(List<String> values) {
            addCriterion("\"recharge_ receipt\" in", values, "rechargeReceipt");
            return (Criteria) this;
        }

        public Criteria andRechargeReceiptNotIn(List<String> values) {
            addCriterion("\"recharge_ receipt\" not in", values, "rechargeReceipt");
            return (Criteria) this;
        }

        public Criteria andRechargeReceiptBetween(String value1, String value2) {
            addCriterion("\"recharge_ receipt\" between", value1, value2, "rechargeReceipt");
            return (Criteria) this;
        }

        public Criteria andRechargeReceiptNotBetween(String value1, String value2) {
            addCriterion("\"recharge_ receipt\" not between", value1, value2, "rechargeReceipt");
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