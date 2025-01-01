package com.tongji.database.elecarbackend.DAO;

import com.tongji.database.elecarbackend.Entity.CarOwner;
import com.tongji.database.elecarbackend.Entity.Trader;
import org.apache.ibatis.annotations.*;

@Mapper
public interface TraderMapper {
    @Insert("INSERT INTO Trader (TraderId,Id,username, tradername, companyAddress, registrationDate) " +
            "VALUES (#{TraderId},#{id},#{username}, #{tradername}, #{companyAddress}, #{registrationDate})")
    int insertTrader(Trader trader);


    @Update("UPDATE Trader SET username = #{username}, tradername = #{tradername}, companyAddress = #{companyAddress}, registrationDate = #{registrationDate} WHERE Id = #{id}")
    int updateTrader(Trader trader);

    @Delete("DELETE FROM Trader WHERE Id = #{id}")
    int deleteTrader(@Param("id") int id);

    @Select("SELECT * FROM Trader WHERE Id = #{id}")
    Trader getTraderById(@Param("id") long id);
}
