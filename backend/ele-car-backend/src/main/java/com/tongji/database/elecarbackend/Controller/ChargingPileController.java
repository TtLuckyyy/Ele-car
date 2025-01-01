package com.tongji.database.elecarbackend.Controller;

import com.tongji.database.elecarbackend.Entity.ChargingPile;
import com.tongji.database.elecarbackend.DAO.ChargingPileMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/ele-car/home/Trader")
public class ChargingPileController {

    @Autowired
    private ChargingPileMapper chargingPileMapper;

    // 充电桩信息登记
    @PostMapping("/ChargingPileRegistration")
    public Map<String, Object> registerChargingPile(@RequestBody ChargingPile chargingPile) {
        System.out.println(chargingPile);
        Map<String, Object> response = new HashMap<>();
        // 插入充电桩信息到数据库
        int result = chargingPileMapper.insertChargingPile(chargingPile);
        if(result > 0) {
            response.put("status", "success");
            response.put("message", "充电桩信息登记成功！");
        }
        else {
            response.put("status", "error");
            response.put("message", "充电桩信息登记失败！");
        }
        return response;
    }

    // 获取充电桩信息列表
    @GetMapping("/ChargingPilesList")
    public List<ChargingPile> getChargingPiles() {
        return chargingPileMapper.getAllChargingPiles();
    }

    // 更新充电桩信息
    @PostMapping("/updateChargingPile")
    public Map<String, Object> updateChargingPile(@RequestBody ChargingPile chargingPile) {
        Map<String, Object> response = new HashMap<>();

        // 更新充电桩信息
        int result = chargingPileMapper.updateChargingPile(chargingPile);

        if (result > 0) {
            response.put("status", "success");
            response.put("message", "充电桩信息更新成功！");
        } else {
            response.put("status", "error");
            response.put("message", "充电桩信息更新失败！");
        }

        return response;
    }

    @DeleteMapping("/deleteChargingPile/{Pile_id}")
    public Map<String, Object> deleteChargingPile(@PathVariable String Pile_id) {
        Map<String, Object> response = new HashMap<>();
        int result = chargingPileMapper.deleteChargingPileById(Pile_id);
        if (result > 0) {
            response.put("status", "success");
            response.put("message", "充电桩删除成功！");
        } else {
            response.put("status", "error");
            response.put("message", "充电桩删除失败！");
        }
        return response;
    }

}