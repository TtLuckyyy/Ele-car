package com.tongji.database.elecarbackend.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class Trader {
    private Long TraderId;
    private long id;//id要小写，不能大写，大写会触发关键词冲突
    private String username;  // 用户名
    private String tradername;  // 运营商名称
    private String companyAddress;  // 公司地址
    private Date registrationDate;  // 注册日期

    // Getters and Setters
    public Long getTraderId() {
        return TraderId;
    }
    public void setTraderId(Long TraderId) {
        this.TraderId = TraderId;
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTradername() {
        return tradername;
    }

    public void setTradername(String tradername) {
        this.tradername = tradername;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }
    public String toString() {
        return "Trader{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", tradername='" + tradername + '\'' +
                ", companyAddress='" + companyAddress + '\'' +
                ", registrationDate=" + registrationDate +
                '}';
    }
}

