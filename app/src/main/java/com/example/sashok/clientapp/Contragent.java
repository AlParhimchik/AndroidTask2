
package com.example.sashok.clientapp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Contragent {

    @SerializedName("contragentType")
    @Expose
    private String contragentType;
    @SerializedName("legalName")
    @Expose
    private String legalName;
    @SerializedName("legalAddress")
    @Expose
    private String legalAddress;
    @SerializedName("INN")
    @Expose
    private String iNN;
    @SerializedName("OKPO")
    @Expose
    private String oKPO;
    @SerializedName("KPP")
    @Expose
    private String kPP;
    @SerializedName("OGRN")
    @Expose
    private String oGRN;
    @SerializedName("BIK")
    @Expose
    private String bIK;
    @SerializedName("bank")
    @Expose
    private String bank;
    @SerializedName("corrAccount")
    @Expose
    private String corrAccount;
    @SerializedName("bankAccount")
    @Expose
    private String bankAccount;

    public String getContragentType() {
        return contragentType;
    }

    public void setContragentType(String contragentType) {
        this.contragentType = contragentType;
    }

    public String getLegalName() {
        return legalName;
    }

    public void setLegalName(String legalName) {
        this.legalName = legalName;
    }

    public String getLegalAddress() {
        return legalAddress;
    }

    public void setLegalAddress(String legalAddress) {
        this.legalAddress = legalAddress;
    }

    public String getINN() {
        return iNN;
    }

    public void setINN(String iNN) {
        this.iNN = iNN;
    }

    public String getOKPO() {
        return oKPO;
    }

    public void setOKPO(String oKPO) {
        this.oKPO = oKPO;
    }

    public String getKPP() {
        return kPP;
    }

    public void setKPP(String kPP) {
        this.kPP = kPP;
    }

    public String getOGRN() {
        return oGRN;
    }

    public void setOGRN(String oGRN) {
        this.oGRN = oGRN;
    }

    public String getBIK() {
        return bIK;
    }

    public void setBIK(String bIK) {
        this.bIK = bIK;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getCorrAccount() {
        return corrAccount;
    }

    public void setCorrAccount(String corrAccount) {
        this.corrAccount = corrAccount;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
