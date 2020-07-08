package com.jj.activity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ActivityApplicationTests {

    /**
     * 配置上下文（也可以理解为配置文件的获取工具）
     */
    @Autowired
    private Environment evn;
    @Test
    public void contextLoads() {
        String property = evn.getProperty("jianjiao.test");
        System.out.println(property);
    }

}
