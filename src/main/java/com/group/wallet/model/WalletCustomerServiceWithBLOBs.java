package com.group.wallet.model;

import javax.persistence.Table;

@Table(name = "wallet_customer_service")
public class WalletCustomerServiceWithBLOBs extends WalletCustomerService {
    private String image;

    private String content;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image == null ? null : image.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}