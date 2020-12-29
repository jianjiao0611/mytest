package com.jjtest.user.controller;

import com.jjtest.tool.exception.ServiceException;
import com.jjtest.tool.response.ResultObject;
import com.jjtest.user.po.UserPO;
import com.jjtest.user.service.UserService;
import com.jjtest.user.vo.UserLoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录控制器
 */
@RestController
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/test")
    public String test() {
        return "user";
    }

    @PostMapping("/login")
    public ResultObject login(@RequestBody UserLoginVO vo) {
        ResultObject resultObject = new ResultObject();
        if (vo == null) {
            throw new ServiceException("参数有误");
        }
        UserPO userPO = userService.selectUserByUserName(vo.getUserName());
        if (userPO == null) {
            throw new ServiceException("用户不存在");
        }
        if (userPO.getPassword().equals(vo.getPassword())) {
            resultObject.successResultObject();
        } else {
            throw new ServiceException("密码错误");
        }
        return resultObject;
    }

}
