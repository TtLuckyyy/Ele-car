package com.tongji.database.elecarbackend.Entity;

import java.util.Date;

public class CarOwner {
    private Long id; // 用户ID，自增
    private String username; // 用户名
    private String ownername; // 车主姓名
    private String model; // 车辆型号
    private String licensePlate; // 车牌号
    private Date registrationDate; // 注册日期

    // Getter 和 Setter 方法

    public Long getId() {
        return id;
    }

    public void setId(Long  id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOwnername() {
        return ownername;
    }

    public void setOwnername(String ownername) {
        this.ownername = ownername;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }
    public String toString() {
        return "CarOwner{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", ownername='" + ownername + '\'' +
                ", model='" + model + '\'' +
                ", licensePlate='" + licensePlate + '\'' +
                ", registrationDate=" + registrationDate +
                '}';
    }
}
