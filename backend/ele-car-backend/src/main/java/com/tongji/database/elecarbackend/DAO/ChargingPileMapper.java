package com.tongji.database.elecarbackend.DAO;

import com.tongji.database.elecarbackend.Entity.ChargingPile;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ChargingPileMapper {

    //插入充电桩数据
    @Insert("INSERT INTO ChargingPile (Pile_id,tradername, introduction, address, status, trader_id, price, registrationDate) " +
            "VALUES (#{Pile_id},#{tradername}, #{introduction}, #{address}, #{status}, #{trader_id}, #{price}, #{registrationDate})")
    int insertChargingPile(ChargingPile chargingPile);

    // 根据运营商ID来删除对应的充电桩
    @Delete("DELETE FROM ChargingPile WHERE trader_id = #{id}")
    int deleteChargingPileByTraderId(@Param("id") long id);

    // 获取所有充电桩
    @Select("SELECT * FROM ChargingPile")
    @Results({
            @Result(property = "Pile_id", column = "Pile_id"),   // 显式映射数据库字段和 Java 属性
            @Result(property = "tradername", column = "tradername"),
            @Result(property = "address", column = "address"),
            @Result(property = "status", column = "status"),
            @Result(property = "trader_id", column = "trader_id"),
            @Result(property = "price", column = "price"),
            @Result(property = "introduction", column = "introduction"),
            @Result(property = "registrationDate", column = "registrationDate")
    })
    List<ChargingPile> getAllChargingPiles();


    // 更新充电桩信息
    @Update("UPDATE ChargingPile SET " +
            "tradername = #{tradername}, " +
            "introduction = #{introduction}, " +
            "address = #{address}, " +
            "status = #{status}, " +
            "price = #{price}, " +
            "registrationDate = #{registrationDate}, " +
            "trader_id = #{trader_id}, " +
            "Pile_id = #{Pile_id} " + // 假设 Pile_id 是不可修改的，如果可以修改，这里可以去掉
            "WHERE Pile_id = #{Pile_id}")
    int updateChargingPile(ChargingPile chargingPile);

    // 根据充电桩 ID 删除充电桩
    @Delete("DELETE FROM ChargingPile WHERE Pile_id = #{Pile_id}")
    int deleteChargingPileById(@Param("Pile_id") String Pile_id);

}