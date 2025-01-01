package com.tongji.database.elecarbackend.Controller;

import com.tongji.database.elecarbackend.DAO.CarOwnerMapper;
import com.tongji.database.elecarbackend.DAO.ReviewMapper;
import com.tongji.database.elecarbackend.DAO.TraderMapper;
import com.tongji.database.elecarbackend.DAO.UserMapper;
import com.tongji.database.elecarbackend.Entity.CarOwner;
import com.tongji.database.elecarbackend.Entity.Trader;
import com.tongji.database.elecarbackend.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/ele-car/home/Trader")
public class TraderController {
    @Autowired
    private TraderMapper traderMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ReviewMapper reviewMapper;
    //运营商信息登记
    @PostMapping("/TraderRegistration")
    public Map<String, Object> TraderRegistration(@RequestBody Trader trader) {
        Map<String, Object> response = new HashMap<>();
        int result;
        try {
            if(traderMapper.getTraderById(trader.getId())!= null)
            {
                traderMapper.updateTrader(trader);
                response.put("status", "success");
                response.put("message", "运营商信息更新成功！");
                return response;
            }
            result = traderMapper.insertTrader(trader);
        }
        catch (Exception e) {
            response.put("status", "error");
            response.put("message", "运营商信息提交失败！请联系开发人员！");
            return response;
        }
        if(result == 1) {
            response.put("status", "success");
            response.put("message", "运营商信息信息登记成功！");
        }
        else {
            response.put("status", "error");
            response.put("message", "运营商信息信息登记失败！");
        }
        return response;
    }

    //获取运营商名称和id
    @GetMapping("/{id}")
    public Map<String, Object> getTradername(@PathVariable("id") long userId) {
        Map<String, Object> response = new HashMap<>();
        Trader trader = traderMapper.getTraderById(userId);
        if(trader == null)
        {
            response.put("status", "error");
            response.put("message", "该运营商尚未完善注册信息！");
            return response;
        }
        String tradername = trader.getTradername();
        long trader_id = trader.getTraderId();
        response.put("status", "success");
        response.put("message", "获取运营商名称成功！");
        response.put("tradername", tradername);
        response.put("trader_id", trader_id);
        return response;
    }

    // 获取所有的用户评论
    @GetMapping("/getAllreviews")
    public List getAllreviews() {
        return reviewMapper.getAllReviews();
    }
}
