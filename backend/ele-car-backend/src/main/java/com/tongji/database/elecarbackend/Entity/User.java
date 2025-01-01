package com.tongji.database.elecarbackend.Entity;

public class User {
    private Long id;
    private Long identity;
    private String gender ;
    private String phone ;
    private String username;
    private String password;
    private String email;
    private String status="active";

    // Getters and Setters
    public long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public Long getIdentity() {return identity;}
    public void setIdentity(Long identity) {this.identity = identity;}
    public String getGender() {return gender;}
    public void setGender(String gender) {this.gender = gender;}
    public String getPhone() {return phone;}
    public void setPhone(String phone) {this.phone =phone;}
    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", identity=" + identity +
                ", gender='" + gender + '\'' +
                ", phone='" + phone + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
