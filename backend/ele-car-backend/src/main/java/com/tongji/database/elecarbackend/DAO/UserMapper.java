package com.tongji.database.elecarbackend.DAO;

import com.tongji.database.elecarbackend.Entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    // 用于登录验证
    @Select("SELECT * FROM users WHERE username = #{username} AND password = #{password}")
    User findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    // 根据用户名寻找用户信息
    @Select("SELECT * FROM users WHERE username = #{username}")
    User findByUsername(@Param("username") String username);

    // 检查用户名是否存在,注册时判断使用
    @Select("SELECT COUNT(*) FROM users WHERE username = #{username}")
    int existsByUsername(@Param("username") String username);

    //获取所有用户信息
    @Select("SELECT * FROM users")
    @Results({
            @Result(property = "identity", column = "Identity"),
            @Result(property = "gender", column = "Gender"),
            @Result(property = "phone", column = "phone"),
            @Result(property = "username", column = "username"),
            @Result(property = "password", column = "password"),
            @Result(property = "email", column = "Email"),
            @Result(property = "status", column = "status")
    })
    List<User> getAllUsers();

    // 保存新用户
    @Insert("INSERT INTO users(Identity, Gender,phone,username,password,Email) VALUES(#{identity}, #{gender},#{phone},#{username},#{password},#{email})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void saveUser(User user);

    //更新用户信息
    @Update("UPDATE users SET Identity = #{identity}, Gender = #{gender}, phone = #{phone}, " +
            "username = #{username}, password = #{password}, Email = #{email},status = #{status} WHERE id = #{id}")
    int updateUser(User user);
    //更新用户状态信息
    @Update("UPDATE users SET status = #{status} WHERE id = #{id}")
    int updateUserStatus(@Param("status") String status, @Param("id") Integer id);

    //删除用户信息
    @Delete("DELETE FROM users WHERE id = #{id}")
    int deleteUserById(@Param("id") int id);
}
