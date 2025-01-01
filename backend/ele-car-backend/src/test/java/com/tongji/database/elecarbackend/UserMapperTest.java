package com.tongji.database.elecarbackend;

import com.tongji.database.elecarbackend.Entity.User;
import com.tongji.database.elecarbackend.DAO.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest//启动整个 Spring 应用程序上下文。它会加载 Spring Boot 应用程序的配置，使得测试类可以使用 Spring 容器管理的 Bean。
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test//测试方法，运行测试时会自动执行这个方法。
    public void testFindByUsernameAndPassword() {
        User user = userMapper.findByUsernameAndPassword("CarOwner1", "829111822");
        System.out.println("User"+user);
    }
}
