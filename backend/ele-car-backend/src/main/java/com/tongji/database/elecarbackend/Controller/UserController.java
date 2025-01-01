package com.tongji.database.elecarbackend.Controller;

import com.tongji.database.elecarbackend.DAO.*;
import com.tongji.database.elecarbackend.Entity.Trader;
import com.tongji.database.elecarbackend.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/ele-car/home/user")
public class UserController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CarOwnerMapper carOwnerMapper;
    @Autowired
    private TraderMapper traderMapper;
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private ChargingPileMapper chargingPileMapper;
    // 用户信息更新接口
    @PostMapping("/updateUser")
    public Map<String, Object> updateUser(@RequestBody User user) {
        Map<String, Object> response = new HashMap<>();

        // 检查用户输入字段是否为空
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            response.put("status", "error");
            response.put("message", "用户名不能为空！");
            return response;
        }

        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            response.put("status", "error");
            response.put("message", "密码不能为空！");
            return response;
        }

        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            response.put("status", "error");
            response.put("message", "邮箱不能为空！");
            return response;
        }

        if (user.getPhone() == null || user.getPhone().trim().isEmpty()) {
            response.put("status", "error");
            response.put("message", "联系电话不能为空！");
            return response;
        }

        if (user.getGender() == null || user.getGender().trim().isEmpty()) {
            response.put("status", "error");
            response.put("message", "性别不能为空！");
            return response;
        }

        if (user.getIdentity() == null || user.getIdentity() <= 0||user.getIdentity() >= 3) {
            response.put("status", "error");
            response.put("message", "请输入正确的身份！");
            return response;
        }

        if (user.getStatus() == null || user.getStatus().trim().isEmpty()) {
            response.put("status", "error");
            response.put("message", "状态不能为空！");
            return response;
        }

        // 如果没有空值，则进行更新
        userMapper.updateUser(user);

        response.put("status", "success");
        response.put("message", "更新成功！");
        return response;
    }

    //获取所有用户信息
    @GetMapping("/getAllUsers")
    public List<User> getAllUsers() {
        return userMapper.getAllUsers(); // 直接返回用户列表
    }

    //删除用户信息
    @DeleteMapping("/deleteUser/{id}")
    public Map<String, Object> deleteUser(@PathVariable("id") int id) {
        Map<String, Object> response = new HashMap<>();
        try {
            //先删去其他表单中的数据，去除外键关联
            carOwnerMapper.deleteCarOwner(id);
            Trader trader_tem=traderMapper.getTraderById(id);
            if(trader_tem!=null) {
                System.out.println("删除充电桩"+trader_tem.getTradername());
                int result=chargingPileMapper.deleteChargingPileByTraderId(trader_tem.getTraderId());
                System.out.println("删除充电桩数量："+result);
            }
            traderMapper.deleteTrader(id);
            adminMapper.deleteAdmin(id);
            //再删除用户信息
            userMapper.deleteUserById(id);
            response.put("status", "success");
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", e.getMessage());
        }
        return response;
    }

    //更新用户状态
    @PostMapping("/updateStatus")
    public Map<String, Object> updateStatus(@RequestBody Map<String, Object> payload) {
        Map<String, Object> response = new HashMap<>();

        try {
            // 从请求体中获取用户 ID 和新状态
            Integer userId = (Integer) payload.get("id"); //前端传输整数时为Integer类型，需要转为int类型，如果是Long类型，则会报错
            String newStatus = (String) payload.get("status");

            // 验证输入
            if (userId == null || userId <= 0) {
                response.put("status", "error");
                response.put("message", "用户 ID 无效！");

                return response;
            }

            if (newStatus == null || (!newStatus.equals("active") && !newStatus.equals("inactive"))) {
                response.put("status", "error");
                response.put("message", "用户状态无效！");
                return response;
            }

            // 调用 Mapper 方法更新用户状态
            userMapper.updateUserStatus(newStatus,userId);

            // 返回成功消息
            response.put("status", "success");
            response.put("message", "用户状态更新成功！");
        } catch (Exception e) {
            // 捕获异常并返回错误消息
            response.put("status", "error");
            response.put("message", "更新用户状态时出错: " + e.getMessage());
        }

        return response;
    }

    //添加新用户
    @PostMapping("/addUser")
    public Map<String, Object> addUser(@RequestBody User user) {
        Map<String, Object> response = new HashMap<>();

        // 检查用户输入字段是否为空
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            response.put("status", "error");
            response.put("message", "用户名不能为空！");
            return response;
        }

        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            response.put("status", "error");
            response.put("message", "密码不能为空！");
            return response;
        }

        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            response.put("status", "error");
            response.put("message", "邮箱不能为空！");
            return response;
        }

        if (user.getPhone() == null || user.getPhone().trim().isEmpty()) {
            response.put("status", "error");
            response.put("message", "联系电话不能为空！");
            return response;
        }

        if (user.getGender() == null || user.getGender().trim().isEmpty()) {
            response.put("status", "error");
            response.put("message", "性别不能为空！");
            return response;
        }

        if (user.getIdentity() == null || user.getIdentity() <= 0||user.getIdentity() >= 3) {
            response.put("status", "error");
            response.put("message", "身份不能为空！");
            return response;
        }

        if (user.getStatus() == null || user.getStatus().trim().isEmpty()) {
            response.put("status", "error");
            response.put("message", "状态不能为空！");
            return response;
        }

        // 如果没有空值，则进行更新
        try {
            userMapper.saveUser(user);
        }
        catch (Exception e) {
            response.put("status", "error");
            response.put("message", "该用户已存在，添加失败");
            return response;
        }

        response.put("status", "success");
        response.put("message", "更新成功！");
        return response;
    }

}