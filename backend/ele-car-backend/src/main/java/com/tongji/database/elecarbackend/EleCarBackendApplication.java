package com.tongji.database.elecarbackend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.tongji.database.elecarbackend.DAO")
public class EleCarBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(EleCarBackendApplication.class, args);
    }

}
