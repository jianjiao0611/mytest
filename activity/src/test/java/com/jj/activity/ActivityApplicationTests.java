package com.jj.activity;

import com.jj.activity.po.DeploymentPO;
import com.jj.activity.workflow.ActivityDeployService;
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

    @Autowired
    private ActivityDeployService activityDeployService;
    @Test
    public void contextLoads() {
        String property = evn.getProperty("jianjiao.test");
        System.out.println(property);
    }

    @Test
    public void activity() {
        DeploymentPO deploymentPO = new DeploymentPO();
        deploymentPO.setName("出差申请流程");
        deploymentPO.setBpmnPath("bpmn/evction.bpmn");
        deploymentPO.setPngPath("bpmn/evction.png");

        activityDeployService.deploy(deploymentPO);
    }

}
