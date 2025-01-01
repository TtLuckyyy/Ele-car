package com.tongji.database.elecarbackend.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class Admin {
    private Long AdminId;
    private long id;//id要小写，不能大写，大写会触发关键词冲突
    private String username;  // 用户名
    private String admintitle;
    private String adminname;  // 运营商名称
    private String department;  // 公司地址
    private Date registrationDate;  // 注册日期

    // Getters and Setters
    public Long getAdminId() {
        return AdminId;
    }
    public void setAdminId(Long AdminId) {
        this.AdminId = AdminId;
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

    public String getAdminname() {
        return adminname;
    }

    public void setAdminname(String adminname) {
        this.adminname = adminname;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String deparment) {
        this.department = deparment;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getAdmintitle() {
        return admintitle;
    }

    public void setAdmintitle(String admintitle) {
        this.admintitle = admintitle;
    }
    public String toString() {
        return "Admin{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", admintitle='" + admintitle + '\'' +
                ", adminname='" + adminname + '\'' +
                ", department='" + department + '\'' +
                ", registrationDate=" + registrationDate +
                '}';
    }
}

