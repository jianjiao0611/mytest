package com.jj.flowable.service;

import com.jjtest.tool.exception.ServiceException;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class TaskHandlerService {

    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private RepositoryService repositoryService;

    public ProcessInstance startTask(String processKey, Map map) {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionKey(processKey)
                .latestVersion().singleResult();
        if (processDefinition == null) {
            throw new ServiceException("流程" + processKey + "不存在");
        }
        return runtimeService.startProcessInstanceByKey(processKey, map);
    }

}
