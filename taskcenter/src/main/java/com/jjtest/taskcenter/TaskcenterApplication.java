package com.jjtest.taskcenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class TaskcenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskcenterApplication.class, args);
    }

}
