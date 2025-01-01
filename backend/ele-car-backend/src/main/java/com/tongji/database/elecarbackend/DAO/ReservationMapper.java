package com.tongji.database.elecarbackend.DAO;

import com.tongji.database.elecarbackend.Entity.Reservation;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ReservationMapper {
    @Insert("INSERT INTO reservation (user_id, pile_id, reservation_date, status, price, charge_time, cost) " +
            "VALUES (#{userId}, #{pileId}, #{reservationDate}, #{status}, #{price}, #{chargeTime}, #{cost})")
    @Options(useGeneratedKeys = true, keyProperty = "resId")
    int insertReservation(Reservation reservation);

    @Select("SELECT * FROM reservation")
    @Results({
            @Result(property = "resId", column = "res_id"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "pileId", column = "pile_id"),
            @Result(property = "reservationDate", column = "reservation_date"),
            @Result(property = "status", column = "status"),
            @Result(property = "price", column = "price"),
            @Result(property = "chargeTime", column = "charge_time"),
            @Result(property = "cost", column = "cost")
    })
    List<Reservation> getAllReservations();

    // 删除预约订单
    @Delete("DELETE FROM reservation WHERE res_id = #{resId}")
    int deleteReservation(@Param("resId")int resId);

    // 取消订单
    @Update("UPDATE reservation SET status = #{status} WHERE res_id = #{resId}")
    int cancelReservation(@Param("resId")int resId, @Param("status")String status);
}
