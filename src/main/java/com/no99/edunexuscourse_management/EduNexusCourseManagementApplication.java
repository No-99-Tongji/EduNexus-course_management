package com.no99.edunexuscourse_management;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@MapperScan("com.no99.edunexuscourse_management.mapper")
@EnableDiscoveryClient
public class EduNexusCourseManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(EduNexusCourseManagementApplication.class, args);
    }

}
