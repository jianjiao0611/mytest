package com.jjtest.user.po;

import com.jjtest.tool.util.excel.Excel;
import lombok.Data;

import java.util.List;

@Data
public class StudentPo  {
    private Integer id;
    @Excel(name = "用户名", isExport = true)
    private String userName;

    @Excel(name = "密码", isExport = true)
    private String password;

    private Integer sex;

    private Integer age;

    private String list;


    private String phone;


    private List<String> stud;
}
