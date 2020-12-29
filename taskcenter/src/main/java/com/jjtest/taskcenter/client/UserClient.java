package com.jjtest.taskcenter.client;

import com.jjtest.tool.response.ResultObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("user")
public interface UserClient {

    @GetMapping("/user/test")
    ResultObject testClient();
}
