package com.group.wallet.model;

import com.group.core.model.BaseEntity2;

import java.util.Date;

public class WalletMerchant extends BaseEntity2{

    private Long userId;

    private String merName;

    private String address;

    private Double longitude;

    private Double latitude;

    private String businessLicense;

    private String doorIamge;

    private String cashierImage;

    private String storeImage;

    private String state;

    private Date createTime;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getMerName() {
        return merName;
    }

    public void setMerName(String merName) {
        this.merName = merName == null ? null : merName.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getBusinessLicense() {
        return businessLicense;
    }

    public void setBusinessLicense(String businessLicense) {
        this.businessLicense = businessLicense == null ? null : businessLicense.trim();
    }

    public String getDoorIamge() {
        return doorIamge;
    }

    public void setDoorIamge(String doorIamge) {
        this.doorIamge = doorIamge == null ? null : doorIamge.trim();
    }

    public String getCashierImage() {
        return cashierImage;
    }

    public void setCashierImage(String cashierImage) {
        this.cashierImage = cashierImage == null ? null : cashierImage.trim();
    }

    public String getStoreImage() {
        return storeImage;
    }

    public void setStoreImage(String storeImage) {
        this.storeImage = storeImage == null ? null : storeImage.trim();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}