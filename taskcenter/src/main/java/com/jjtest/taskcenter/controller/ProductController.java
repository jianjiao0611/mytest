package com.jjtest.taskcenter.controller;

import com.jjtest.taskcenter.client.UserClient;
import com.jjtest.tool.response.ResultObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    @Autowired
    private UserClient userClient;

    @GetMapping("/test")
    public ResultObject test() {
        ResultObject resultObject = userClient.testClient();

        System.out.println(resultObject);

        return resultObject;
    }
}
