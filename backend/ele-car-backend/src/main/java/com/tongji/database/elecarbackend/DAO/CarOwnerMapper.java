package com.tongji.database.elecarbackend.DAO;
import com.tongji.database.elecarbackend.Entity.CarOwner;
import org.apache.ibatis.annotations.*;

@Mapper
public interface CarOwnerMapper {
    @Insert("INSERT INTO CarOwner (Id,username, ownername, model, licensePlate, registrationDate) " +
            "VALUES (#{id},#{username}, #{ownername}, #{model}, #{licensePlate}, #{registrationDate})")
    int insertCarOwner(CarOwner carOwner);

    @Update("UPDATE CarOwner SET username = #{username}, ownername = #{ownername}, model = #{model}, " +
            "licensePlate = #{licensePlate}, registrationDate = #{registrationDate} WHERE Id = #{id}")
    int updateCarOwner(CarOwner carOwner);

    @Delete("DELETE FROM CarOwner WHERE Id = #{id}")
    int deleteCarOwner(@Param("id") int id);
}
