package com.group.wallet.model;

import javax.persistence.Table;

@Table(name = "wallet_copywriting")
public class WalletCopywritingWithBLOBs extends WalletCopywriting {
    private String content;

    private String images;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images == null ? null : images.trim();
    }
}