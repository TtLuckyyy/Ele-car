package com.tongji.database.elecarbackend.DAO;

import com.tongji.database.elecarbackend.Entity.Admin;
import com.tongji.database.elecarbackend.Entity.Trader;
import org.apache.ibatis.annotations.*;

public interface AdminMapper {
    @Insert("INSERT INTO Admin (AdminId,Id,username, admintitle,adminname, department, registrationDate) " +
            "VALUES (#{AdminId},#{id},#{username}, #{admintitle},#{adminname}, #{department}, #{registrationDate})")
    int insertAdmin(Admin admin);


    @Update("UPDATE Admin SET username = #{username}, admintitle = #{admintitle},adminname = #{adminname}, department = #{department}, registrationDate = #{registrationDate} WHERE Id = #{id}")
    int updateAdmin(Admin admin);

    @Delete("DELETE FROM Admin WHERE Id = #{id}")
    int deleteAdmin(@Param("id") int id);

    @Select("SELECT * FROM Admin WHERE Id = #{id}")
    Trader getAdminById(@Param("id") long id);
}
