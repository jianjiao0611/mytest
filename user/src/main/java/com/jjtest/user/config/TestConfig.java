package com.jjtest.user.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class TestConfig {
    @Value("${testff}")
    private String testff;

    @Value("${usernamae}")
    private String usernamae;

    @Value("${password}")
    private String password;
}
