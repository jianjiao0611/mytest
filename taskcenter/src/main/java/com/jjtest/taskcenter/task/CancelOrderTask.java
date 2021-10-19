package com.jjtest.taskcenter.task;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class CancelOrderTask {

    @Scheduled(cron = "* 5 * * * ?")
    public void cancelOrder() {

    }

}
