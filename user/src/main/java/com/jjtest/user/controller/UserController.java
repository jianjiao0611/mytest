package com.jjtest.user.controller;

import com.jjtest.tool.response.ResultObject;
import com.jjtest.user.po.UserPO;
import com.jjtest.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController()
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private HttpServletRequest request;

    @GetMapping("/test")
    public ResultObject testClient() {

        String token = request.getHeader("testjj");
        System.out.println(token);
        ResultObject resultObject = new ResultObject();
        UserPO fengfeng = userService.selectUserByUserName("jj");
        resultObject.successResultObject(fengfeng);
        return resultObject;
    }

}
