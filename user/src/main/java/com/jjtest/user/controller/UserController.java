package com.jjtest.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.jjtest.tool.context.DefaultContextDataThreadLocal;
import com.jjtest.tool.model.LoginUserModel;
import com.jjtest.tool.response.ResultObject;
import com.jjtest.tool.util.threadpool.AsyncInvoke;
import com.jjtest.user.po.UserPO;
import com.jjtest.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.List;

@RestController()
@RequestMapping("/user")
@Api("用户")
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger("sys-user");
    @Autowired
    private UserService userService;

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private AsyncInvoke asyncInvoke;
    @Autowired
    HttpServletRequest httpServletRequest;
    @Autowired
    HttpServletResponse httpServletResponse;

    @GetMapping("/test")
    public ResultObject testClient(@RequestParam(value = "fileName", required = false)String fileName) {
        UserPO po = new UserPO(3);
//        LOGGER.info(JSONObject.toJSONString(po));
        System.out.println(fileName);


//        String token = request.getHeader("testjj");
//        System.out.println(token);
        ResultObject resultObject = ResultObject.successResult();
        resultObject.setData(po);
//        UserPO fengfeng = userService.selectUserByUserName("jj");
//        Object o = new Object();
//
//        resultObject.successResultObject(fengfeng);
//        List<UserPO> list = new ArrayList<>();
//        list.add(fengfeng);
//        ExcelUtil excelUtil = new ExcelUtil(UserPO.class);
//        excelUtil.exportExcel(list, "测试", httpServletRequest, httpServletResponse);
        return resultObject;
    }

    @GetMapping("/addUser")
    @ApiOperation("添加用户")
    public void testUser(@ApiParam(value = "list") @RequestParam(value = "list") List<String> list) {
        System.out.println(list);
    }

    @GetMapping("/file")
    @ApiOperation("添加用户")
    public void file(@ApiParam(value = "fileName") @RequestParam(value = "fileName") String fileName) {
        int index = fileName.lastIndexOf(File.separator) + 1;
        String path = fileName.substring(0, index);
        String file = fileName.substring(index);
        System.out.println(path);
        System.out.println(file);
        System.out.println(fileName);
    }



    @PostMapping("/addUser")
    @ApiOperation("添加用户")
    public void addUser(@ApiParam(value = "list") @RequestBody List<UserPO> list) {
        System.out.println(list);
    }

    @GetMapping("/setLoginUser")
    @ApiOperation("添加用户")
    public String setLoginUser(@RequestParam("userName")String userName, @RequestParam("id") String id) {
        LoginUserModel loginUserModel = new LoginUserModel();
        loginUserModel.setId(id);
        loginUserModel.setName(userName);
        DefaultContextDataThreadLocal.setLoginUser(loginUserModel);
        HttpSession session = httpServletRequest.getSession();
        session.setAttribute("loginUser", loginUserModel);
        return JSONObject.toJSONString(DefaultContextDataThreadLocal.getLoginUserModel());
    }

    @GetMapping("/getLoginUser")
    @ApiOperation("添加用户")
    public String getLoginUser() {
        LoginUserModel loginUserModel = DefaultContextDataThreadLocal.getLoginUserModel();
        return JSONObject.toJSONString(loginUserModel);
    }

}
