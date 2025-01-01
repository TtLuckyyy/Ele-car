package com.tongji.database.elecarbackend.Controller;

import com.tongji.database.elecarbackend.DAO.ReviewMapper;
import com.tongji.database.elecarbackend.Entity.CarOwner;
import com.tongji.database.elecarbackend.Entity.Reservation;
import com.tongji.database.elecarbackend.Entity.Review;
import com.tongji.database.elecarbackend.Entity.User;
import com.tongji.database.elecarbackend.DAO.CarOwnerMapper;
import com.tongji.database.elecarbackend.DAO.UserMapper;
import com.tongji.database.elecarbackend.DAO.ReservationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/ele-car/home/CarOwner")
public class CarOwnerController {
    @Autowired
    private CarOwnerMapper carOwnerMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ReservationMapper reservationMapper;
    @Autowired
    private ReviewMapper reviewMapper;
    //新能源车辆信息登记
    @PostMapping("/vehicleRegistration")
    public Map<String, Object> vehicleRegistration(@RequestBody CarOwner carowner) {
        Map<String, Object> response = new HashMap<>();
        User curuser = userMapper.findByUsername(carowner.getUsername());
        carowner.setId(curuser.getId());
        int result;
        try {
            result = carOwnerMapper.insertCarOwner(carowner);
        }
        catch (Exception e) {
            carOwnerMapper.updateCarOwner(carowner);
            response.put("status", "success");
            response.put("message", "用户新能源车辆信息更新成功！");
            return response;
        }
        if(result == 1) {
            response.put("status", "success");
            response.put("message", "新能源车辆信息登记成功！");
        }
        else {
            response.put("status", "error");
            response.put("message", "新能源车辆信息登记失败！");
        }
        return response;
    }

    // 预约充电桩
    @PostMapping("/reserveChargingPile")
    public Map<String, Object> reserveChargingPile(@RequestBody Reservation reservation) {
        Map<String, Object> response = new HashMap<>();
        System.out.println(reservation);
        int result;
        try {
            // 插入预约记录
            result = reservationMapper.insertReservation(reservation);
        } catch (Exception e) {
            // 出现异常时更新记录
            response.put("status", "error");
            response.put("message", "预约充电桩失败！");
            return response;
        }

        if (result == 1) {
            response.put("status", "success");
            response.put("message", "预约充电桩成功！");
        } else {
            response.put("status", "error");
            response.put("message", "预约充电桩失败！");
        }
        return response;
    }

    // 获取预约信息
    @GetMapping("/getAllReservations")
    public List getReservationInfo() {
        return reservationMapper.getAllReservations();
    }

    // 删除订单
    @DeleteMapping("/deleteReservation/{resId}")
    public Map<String, Object> deleteReservation(@PathVariable int resId) {
        Map<String, Object> response = new HashMap<>();
        int result = reservationMapper.deleteReservation(resId);
        if (result == 1) {
            response.put("status", "success");
            response.put("message", "取消预约成功！");
        } else {
            response.put("status", "error");
            response.put("message", "取消预约失败！");
        }
        return response;
    }

    // 取消订单（更新订单状态）
    @PutMapping("/cancelReservation/{resId}")
    public Map<String, Object> cancelReservation(@PathVariable int resId) {
        Map<String, Object> response = new HashMap<>();
        System.out.println(resId);
        String status = "cancelled";
        int result = reservationMapper.cancelReservation(resId,status);
        if (result == 1) {
            response.put("status", "success");
            response.put("message", "取消预约成功！");
        } else {
            response.put("status", "error");
            response.put("message", "取消预约失败！");
        }
        return response;
    }

    // 提交评论信息
    @PostMapping("/submitReview")
    public Map<String, Object> submitReview(@RequestBody Review review) {
        Map<String, Object> response = new HashMap<>();
        int result = reviewMapper.insertReview(review);
        if (result == 1) {
            response.put("status", "success");
            response.put("message", "评论信息提交成功！");
        } else {
            response.put("status", "error");
            response.put("message", "评论信息提交失败！");
        }
        return response;
    }

}
