package com.example.activiti7boot.controller;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

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

    //------------------------------------------------------部署流程实例-------------------------------------------------------------
    // bpmn文件部署方式
    @RequestMapping("initsProces")
    public Object initsProces() {
        Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource("processes/MyProcess.bpmn")
                .addClasspathResource("processes/MyProcess.png")
                .name("测试diagram").deploy();
        return deployment;
    }

    // 根据部署ID删除流程
    @RequestMapping("deleteProces")
    public Object deleteProces(String deploymentId) {
        repositoryService.deleteDeployment(deploymentId);
        return "ok";
    }

    //-------------------------------------------------------------------启动流程实例------------------------------------------------------------
    //启动流程实例
    @RequestMapping("startProcess")
    public Object startProcess() {
        //在bpmn中
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("reason", "测试请假");
        map.put("days", 2);
        map.put("time", new Date());

    	ProcessInstance instance = runtimeService.startProcessInstanceByKey("myProcess");
		return instance;
    }

    //根据流程定义ID查询流程实例
    @RequestMapping("searchByKey")
    public Object searchByKey(String processKey) {
        ProcessInstanceQuery processInstanceQuery = runtimeService.createProcessInstanceQuery();
        List<ProcessInstance> runningList = processInstanceQuery.processDefinitionKey(processKey).list();
        int size = runningList.size();
        if (size > 0) {
            List<Map<String, String>> resultList = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                ProcessInstance pi = runningList.get(i);
                Map<String, String> resultMap = new HashMap<>(2);
                resultMap.put("processID", pi.getId()); // 流程实例ID
                resultMap.put("processDefinitionKey", pi.getProcessDefinitionId());// 流程定义ID
                resultList.add(resultMap);
            }
            return resultList;
        }
        return "无对应实例";
    }

    //根据流程实例ID查询流程实例
    @RequestMapping("processID")
    public Object searchByID(String processID){
        try {
            ProcessInstance pi = runtimeService.createProcessInstanceQuery()
                    .processInstanceId(processID)
                    .singleResult();
            if (pi != null) {
                Map<String, String> resultMap = new HashMap<>(2);
                resultMap.put("processID", pi.getId());// 流程实例ID
                resultMap.put("processDefinitionKey", pi.getProcessDefinitionId());//流程定义ID
                return resultMap;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //根据流程实例key删除流程实例
    @RequestMapping(path = "deleteProcessInstanceByKey")
    public Object deleteProcessInstanceByKey(@RequestParam("processKey") String processDefinitionKey){
        try {
            ProcessInstanceQuery processInstanceQuery = runtimeService.createProcessInstanceQuery();
            List<ProcessInstance> runningList = processInstanceQuery.processDefinitionKey(processDefinitionKey).list();
            int size = runningList.size();
            if (size > 0) {
                List<Map<String, String>> resultList = new ArrayList<>();
                for (int i = 0; i < size; i++) {
                    ProcessInstance pi = runningList.get(i);
                    runtimeService.deleteProcessInstance(pi.getId(),"删除");
                }
                return resultList;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }

    //根据流程实例ID删除流程实例
    @RequestMapping(path = "deleteProcessInstanceByID")
    public Object deleteProcessInstanceByID(@RequestParam("processID") String processID){
        try {
            runtimeService.deleteProcessInstance(processID,"删除" + processID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "ok";
    }

    //-------------------------------------------------------------------任务相关------------------------------------------------------------
    //据流程assignee查询当前人的个人任务
    @RequestMapping(path = "findTaskByAssignee")
    public Object findTaskByAssignee(@RequestParam("assignee") String assignee) {//代理人（当前用户）
        //创建任务查询对象
        try {
            List<Task> taskList = taskService.createTaskQuery()
                    //指定个人任务查询
                    .taskAssignee(assignee)
                    .list();
            if (taskList != null && taskList.size() > 0) {
                List<Map<String, String>> resultList = new ArrayList<>();
                for (Task task : taskList) {
                    Map<String, String> resultMap = new HashMap<>(7);
                    /* 任务ID */
                    resultMap.put("taskID", task.getId());

                    /* 任务名称 */
                    resultMap.put("taskName", task.getName());

                    /* 任务的创建时间 */
                    resultMap.put("taskCreateTime", task.getCreateTime().toString());

                    /* 任务的办理人 */
                    resultMap.put("taskAssignee", task.getAssignee());

                    /* 流程实例ID */
                    resultMap.put("processInstanceId", task.getProcessInstanceId());

                    /* 执行对象ID */
                    resultMap.put("executionId", task.getExecutionId());

                    /* 流程定义ID */
                    resultMap.put("processDefinitionId", task.getProcessDefinitionId());
                    resultList.add(resultMap);
                }
                return resultList;
            } else {
                return "无任务";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //完成任务，任务进入下一个节点
    @RequestMapping(path = "completeTask")
    public Object completeTask(@RequestParam("taskId") String taskId, @RequestParam("days") int days) {
        try {
            HashMap<String, Object> variables = new HashMap<>(1);
            variables.put("days", days);
            taskService.complete(taskId, variables);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
