package com.jjtest.user.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class TestVO {

    @JSONField(name = "t_user_vo")
    private UserLoginVO user;
}
