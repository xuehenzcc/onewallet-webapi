package com.group.wallet.model;

import com.group.core.model.BaseEntity2;

public class WalletBranchBank extends BaseEntity2{

    private String branchBankName;

    private String barnchBankCode;

    private String swiftCode;


    public String getBranchBankName() {
        return branchBankName;
    }

    public void setBranchBankName(String branchBankName) {
        this.branchBankName = branchBankName == null ? null : branchBankName.trim();
    }

    public String getBarnchBankCode() {
        return barnchBankCode;
    }

    public void setBarnchBankCode(String barnchBankCode) {
        this.barnchBankCode = barnchBankCode == null ? null : barnchBankCode.trim();
    }

    public String getSwiftCode() {
        return swiftCode;
    }

    public void setSwiftCode(String swiftCode) {
        this.swiftCode = swiftCode;
    }
}