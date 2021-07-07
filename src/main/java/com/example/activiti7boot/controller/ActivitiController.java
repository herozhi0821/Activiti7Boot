package com.example.activiti7boot.controller;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ActivitiController {

	@Autowired
    private RepositoryService repositoryService;//流程定义和部署相关的存储服务
    @Autowired
    private TaskService taskService;//流程运行过程中，每个任务节点的相关操作接口，如complete,delete,delegate等。
    @Autowired
    private RuntimeService runtimeService;//流程运行时相关的服务，如根据流程好启动流程实例startProcessInstanceByKey。
    @Autowired
    private HistoryService historyService;//历史记录相关服务接口。
    
    @RequestMapping("start")
    public Object startObject() {
        Map<String, Object> map = new HashMap<String, Object>();
        //在holiday.bpmn中,填写请假单的任务办理人为动态传入的userId,此处模拟一个id
        map.put("userId", 2);
    	ProcessInstance instance = runtimeService.startProcessInstanceByKey("kyrsapplication",map);
		return instance;
    }

    @RequestMapping("inits")
    public Object inits() {
        repositoryService.createDeployment()
                .addClasspathResource("processes/diagram.bpmn")
                .name("测试diagram").deploy();
        return null;
    }
    
}
