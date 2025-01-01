package com.tongji.database.elecarbackend.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Date;


public class ChargingPile {

    private long Pile_id;           // 充电桩ID
    private String tradername;        // 运营商名称
    private String introduction;      // 充电桩相关信息描述
    private String address;           // 充电桩地址描述
    private String status;            // 充电桩状态
    private long trader_id;         // 运营商ID
    private Float price;              // 充电价格
    private Date registrationDate;    // 充电桩注册时间

    // Getter 和 Setter 方法
    public long getPile_id() {
        return Pile_id;
    }

    public void setPile_id(long Pile_id) {
        this.Pile_id = Pile_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getTrader_id() {
        return trader_id;
    }

    public void setTrader_id(long trader_id) {
        this.trader_id = trader_id;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String gettradername() {
        return tradername;
    }

    public void settradername(String tradername) {
        this.tradername = tradername;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    @Override
    public String toString() {
        return "ChargingPile{" +
                "Pile_id=" + Pile_id +
                ", address='" + address + '\'' +
                ", status='" + status + '\'' +
                ", trader_id=" + trader_id +
                ", price=" + price +
                ", introduction='" + introduction + '\'' +
                ", tradername='" + tradername + '\'' +
                ", registrationDate=" + registrationDate +
                '}';
    }
}
