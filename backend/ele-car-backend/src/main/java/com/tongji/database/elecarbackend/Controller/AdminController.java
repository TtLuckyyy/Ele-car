package com.tongji.database.elecarbackend.Controller;

import com.tongji.database.elecarbackend.DAO.AdminMapper;
import com.tongji.database.elecarbackend.DAO.UserMapper;
import com.tongji.database.elecarbackend.Entity.Admin;
import com.tongji.database.elecarbackend.Entity.Trader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/ele-car/home/Admin")
public class AdminController {
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private UserMapper userMapper;
    //管理者信息登记
    @PostMapping("/AdminRegistration")
    public Map<String, Object> AdminRegistration(@RequestBody Admin admin) {
        Map<String, Object> response = new HashMap<>();
        System.out.println(admin);
        int result;
        try {
            if(adminMapper.getAdminById(admin.getId())!= null)
            {
                adminMapper.updateAdmin(admin);
                response.put("status", "success");
                response.put("message", "管理者信息更新成功！");
                return response;
            }
            result = adminMapper.insertAdmin(admin);
        }
        catch (Exception e) {
            response.put("status", "error");
            response.put("message", "管理者信息提交失败！请联系开发人员！");
            return response;
        }
        if(result == 1) {
            response.put("status", "success");
            response.put("message", "管理者信息信息登记成功！");
        }
        else {
            response.put("status", "error");
            response.put("message", "管理者信息信息登记失败！");
        }
        return response;
    }
}
