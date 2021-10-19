package com.jjtest.user.po;

import com.jjtest.tool.util.excel.Excel;
import lombok.Data;

import java.util.List;

/**
 * 用户
 */
@Data
public class UserPO extends BasePo implements java.io.Serializable {

    private Integer id;
    @Excel(name = "用户名", isExport = true)
    private String userName;

    @Excel(name = "密码", isExport = true)
    private String password;

    private Integer sex;

    private Integer age;

    private String list;


    private String phone;

    private StudentPo studentPo;

    public UserPO() {
        this.studentPo = new StudentPo();
    }

    public UserPO(Integer id) {
        this.id = id;
    }
}
