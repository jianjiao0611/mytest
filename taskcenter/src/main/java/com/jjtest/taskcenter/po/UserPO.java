package com.jjtest.taskcenter.po;

import lombok.Data;

/**
 * 用户
 */
@Data
public class UserPO {

    private Integer id;

    private String userName;

    private String password;

    private Integer sex;

    private Integer age;

    private String phone;
}
