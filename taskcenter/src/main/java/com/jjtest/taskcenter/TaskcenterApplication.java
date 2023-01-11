package com.jjtest.taskcenter;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableFeignClients
@ComponentScan(basePackages = {"com.jjtest.tool", "com.jjtest.taskcenter"})
@EnableEncryptableProperties
public class TaskcenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskcenterApplication.class, args);
    }

}
