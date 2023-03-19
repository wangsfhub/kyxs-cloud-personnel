package com.kyxs.cloud.personnel;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@EnableCaching
@MapperScan({"com.kyxs.**.mapper"})
@EnableFeignClients(basePackages = {"com.kyxs"})
@ComponentScan(basePackages = { "com.kyxs"})
@Slf4j
public class PersonnelApplication {

    public static void main(String[] args) {
        SpringApplication.run(PersonnelApplication.class, args);
        log.info(" SUCCESSFUL com.kyxs.cloud.personnel.PersonnelApplication - 人事服务 kyxs-cloud-personnel:8888 启动成功！");
    }

}
