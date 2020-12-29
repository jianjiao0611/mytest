package com.jj.flowable;

import com.jj.flowable.service.TaskHandlerService;
import org.flowable.engine.runtime.ProcessInstance;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FlowableApplicationTests {

    @Autowired
    private TaskHandlerService taskHandlerService;

    @Test
    public void contextLoads() {
        Map<String,Object> map = new HashMap<>();
        map.put("employee", 1);
        ProcessInstance processInstance = taskHandlerService.startTask("holidayRequest", map);
        System.out.println(processInstance);
    }

}
