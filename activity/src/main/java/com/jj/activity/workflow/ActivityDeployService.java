package com.jj.activity.workflow;

import com.jj.activity.po.DeploymentPO;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 流程部署service
 */
@Service
public class ActivityDeployService {


    @Autowired
    private RepositoryService repositoryService;

    public void deploy(DeploymentPO deploymentPO) {
        Deployment deployment = repositoryService.createDeployment().name(deploymentPO.getName())
                .addClasspathResource(deploymentPO.getBpmnPath())
                .addClasspathResource(deploymentPO.getPngPath())
                .deploy();

        System.out.println("流程id：" + deployment.getId());
    }

}
