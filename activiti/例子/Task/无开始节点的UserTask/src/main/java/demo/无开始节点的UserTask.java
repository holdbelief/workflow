package demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class 无开始节点的UserTask {
    public static void main(String[] args) throws InterruptedException {
    	
    	SpringApplication.run(无开始节点的UserTask.class, args);
        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = engine.getRepositoryService();
        repositoryService.createDeployment().addClasspathResource("bpmn/无开始节点的UserTask.bpmn").deploy();
         
        Map<String, Object> vars = new HashMap<>();
        vars.put("assignee", "u1");
        
        RuntimeService runtimeService = engine.getRuntimeService();
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("无开始节点的UserTask", vars);
        
        TaskService taskService = engine.getTaskService();
        Task tasku1 = taskService.createTaskQuery().taskAssignee("u1").processInstanceId(processInstance.getId()).singleResult();
        
//        taskService.complete(tasku1.getId());
        
        Task tasktu2 = taskService.createTaskQuery().taskAssignee("u2").processInstanceId(processInstance.getId()).singleResult();
        
        taskService.complete(tasktu2.getId());
        
//        Task tasktom3 = taskService.createTaskQuery().taskAssignee("tom").processInstanceId(processInstance.getId()).singleResult();
//        
//        taskService.complete(tasktom3.getId());
        
        System.out.println("end");
      
    }
}
