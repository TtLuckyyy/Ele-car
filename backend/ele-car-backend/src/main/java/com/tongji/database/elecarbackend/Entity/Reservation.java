package com.tongji.database.elecarbackend.Entity;

import java.math.BigDecimal;
import java.util.Date;

public class Reservation {
    private Integer resId;          // 预约ID
    private Integer userId;         // 用户ID
    private Integer pileId;         // 充电桩ID
    private Date reservationDate;   // 预约日期
    private String status;          // 预约状态
    private BigDecimal price;       // 充电价格
    private Integer chargeTime;     // 充电时间 (单位: 分钟)
    private BigDecimal cost;        // 总费用

    // Getters and Setters
    public Integer getResId() {
        return resId;
    }

    public void setResId(Integer resId) {
        this.resId = resId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPileId() {
        return pileId;
    }

    public void setPileId(Integer pileId) {
        this.pileId = pileId;
    }

    public Date getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(Date reservationDate) {
        this.reservationDate = reservationDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getChargeTime() {
        return chargeTime;
    }

    public void setChargeTime(Integer chargeTime) {
        this.chargeTime = chargeTime;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "resId=" + resId +
                ", userId=" + userId +
                ", pileId=" + pileId +
                ", reservationDate=" + reservationDate +
                ", status='" + status + '\'' +
                ", price=" + price +
                ", chargeTime=" + chargeTime +
                ", cost=" + cost +
                '}';
    }
}
