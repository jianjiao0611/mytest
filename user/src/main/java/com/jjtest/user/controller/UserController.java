package com.jjtest.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jjtest.tool.context.DefaultContextDataThreadLocal;
import com.jjtest.tool.model.LoginUserModel;
import com.jjtest.tool.response.ResultObject;
import com.jjtest.tool.util.excel.ExcelUtil;
import com.jjtest.tool.util.sftp.FtpSitePo;
import com.jjtest.tool.util.sftp.SFtpUtil;
import com.jjtest.tool.util.sftp.SftpChannelInfo;
import com.jjtest.tool.util.threadpool.AsyncInvoke;
import com.jjtest.user.po.UserPO;
import com.jjtest.user.service.UserService;
import io.swagger.annotations.*;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import static com.alibaba.druid.sql.parser.Token.BY;

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

    @Autowired
    private MultipartHttpServletRequest servletRequest;
    @GetMapping("/test")
    public void testClient(@RequestParam(value = "fileName", required = false)String fileName) throws IOException {
//        UserPO po = new UserPO(3);
//        LOGGER.info(JSONObject.toJSONString(po));
        System.out.println(fileName);


//        String token = request.getHeader("testjj");
//        System.out.println(token);
//        ResultObject resultObject = ResultObject.successResult();
//        resultObject.setData(po);
//        UserPO fengfeng = userService.selectUserByUserName("jj");
//        Object o = new Object();
//
//        resultObject.successResultObject(fengfeng);
//        List<UserPO> list = new ArrayList<>();
//        list.add(fengfeng);
//        ExcelUtil excelUtil = new ExcelUtil(UserPO.class);
//        excelUtil.exportExcel(list, "测试", httpServletRequest, httpServletResponse);
//        return resultObject;

        Workbook workbook = new XSSFWorkbook();


        Sheet sheet = workbook.createSheet();

        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0);
        CellStyle style = workbook.createCellStyle();
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFillForegroundColor(IndexedColors.BLUE.index);
        cell.setCellStyle(style);
        cell.setCellValue("测试");
        httpServletResponse.setHeader("Content-Type", "application/vnd.ms-excel;charset=UTF-8");
        workbook.write(httpServletResponse.getOutputStream());
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

    @PostMapping(value = "/testF")
    @ApiOperation("文件")
    public String testF(@RequestPart("file") MultipartFile multipartFile) throws Exception {
//        MultipartFile file1 = servletRequest.getFile("file");
//        System.out.println(file1.getOriginalFilename());


//        Collection<Part> parts = request.getParts();
//        for (Iterator<Part> iterator = parts.iterator(); iterator.hasNext();) {
//            Part part = iterator.next();
//            System.out.println("-----类型名称------->" + part.getName());
//            System.out.println("-----类型------->" + part.getContentType());
//            System.out.println("-----提交的类型名称------->" + part.getSubmittedFileName());
//            System.out.println("----流-------->" + part.getInputStream());
//        }
//        CommonsMultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
//        if (resolver.isMultipart(request)) {
//            //将request转换成多分解请求
//            MultipartHttpServletRequest mhs = resolver.resolveMultipart(request);
//            //根据input中存在的name来获取是否存在上传文件
//            MultipartFile mf = mhs.getFile("file"); //这里可以用getFiles("file")的方式来处理多个文件。返回的是一个list.然后一个一个处理就可以了
//            //创建文件保存名
//            File file = new File(mf.getOriginalFilename());
//            System.out.println(file);
//        }
//        resolver.setDefaultEncoding("utf-8");
//        MultipartHttpServletRequest multipartHttpServletRequest = resolver.resolveMultipart(request);
//        MultiValueMap<String, MultipartFile> multiFileMap = multipartHttpServletRequest.getMultiFileMap();
//        System.out.println(multiFileMap);
        String originalFilename = multipartFile.getOriginalFilename();
        System.out.println(originalFilename);

        try {
            long size = multipartFile.getSize();
            System.out.println(size);
            FtpSitePo po = new FtpSitePo();
            po.setIp("127.0.0.1");
            po.setPort(22);
            po.setAccount("jiaojiao");
            po.setPassword("115714");
            SftpChannelInfo channelSftp = SFtpUtil.getChannelSftp(po);
            String floder = "/tmp/test/";
            SFtpUtil.uploadFile(channelSftp, "/data/test", "test.mp4", multipartFile.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "ok";
    }

    @PostMapping("/testXss")
    public ResultObject testXss(@RequestBody @Valid UserPO po) throws IOException {
        ResultObject resultObject = new ResultObject();

        resultObject.setData(po.getUserName());

        return resultObject;
    }

}
