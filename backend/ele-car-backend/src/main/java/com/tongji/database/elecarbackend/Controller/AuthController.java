package com.tongji.database.elecarbackend.Controller;

import com.tongji.database.elecarbackend.DAO.UserMapper;
import com.tongji.database.elecarbackend.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/ele-car")
public class AuthController {
    @Autowired
    private UserMapper userMapper;

    // 登录接口
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody User user) {
        Map<String, Object> response = new HashMap<>();
        User user_find = userMapper.findByUsernameAndPassword(user.getUsername(), user.getPassword());
        if (user_find == null)
        {
            response.put("status", "error");
            response.put("message", "用户名或密码错误！");
        }
        else if ("inactive".equals(user_find.getStatus()))
        {
            response.put("status", "error");
            response.put("message", "该用户已被禁用！");
        }
        else
        {
            response.put("status", "success");
            response.put("message", "登录成功！");
            response.put("user", user_find);
        }
        return response;
    }

    // 注册接口
    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody User user) {
        Map<String, Object> response = new HashMap<>();

        // 检查用户名是否存在
        if (userMapper.existsByUsername(user.getUsername()) > 0) {
            response.put("status", "error");
            response.put("message", "用户名已存在！");
            return response;
        }

        // 创建新用户
        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(user.getPassword());
        newUser.setEmail(user.getEmail());
        System.out.println(newUser);
        newUser.setPhone(user.getPhone());
        newUser.setGender(user.getGender());
        newUser.setIdentity(user.getIdentity());
        newUser.setStatus(user.getStatus());
        userMapper.saveUser(newUser);

        response.put("status", "success");
        response.put("message", "注册成功！");
        return response;
    }

}
