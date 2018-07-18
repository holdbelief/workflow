package demo;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = {org.activiti.spring.boot.SecurityAutoConfiguration.class, org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration.class})
public class App {
    public static void main(String[] args) {
    	
    	SpringApplication.run(App.class, args);
//        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
//        RepositoryService repositoryService = engine.getRepositoryService();
//        Deployment deploy = repositoryService.createDeployment().addClasspathResource("diagrams/MyProcess.bpmn").deploy();
//         
//        Map<String, Object> var = new HashMap<>();	
//        var.put("var1", 1);
//        
//     // 查找流程定义
//     	ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().deploymentId(deploy.getId()).singleResult();
//        
//     	// 用流程定义启动流程实例
//        RuntimeService runtimeService = engine.getRuntimeService();
//        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinition.getId(), var);
////        processInstance.getProcessVariables(); 这个不管用
//        
//        TaskService taskService = engine.getTaskService();
//        
//        Task usertask1 = taskService.createTaskQuery().processInstanceId(processInstance.getId()).taskName("usertask1").singleResult();
//        taskService.complete(usertask1.getId());
//        
//        Task usertask2 = taskService.createTaskQuery().processInstanceId(processInstance.getId()).taskName("usertask2").singleResult();
//        taskService.complete(usertask2.getId());
//        
//        Task usertask3 = taskService.createTaskQuery().processInstanceId(processInstance.getId()).taskName("usertask3").singleResult();
//        
//        Map<String, Object> map = runtimeService.getVariables(processInstance.getId(), Arrays.asList("var1"));
//        System.out.println(map.get("var1"));
//        
//        taskService.complete(usertask3.getId());
//         
//        HistoricProcessInstance historicProcessInstance = engine.getHistoryService().createHistoricProcessInstanceQuery().processInstanceId(processInstance.getId()).singleResult();
//        Date endTime = historicProcessInstance.getEndTime();
//        
//        processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstance.getId()).singleResult();
//        
//        if ( processInstance == null ) {
//        	System.out.println("流程结束, 结束时间： " + endTime);
//        } else {
//	        System.out.println(processInstance.isEnded());
//	        System.out.println(processInstance.isSuspended());
//	        System.out.println(processInstance.getActivityId());
//        }
        
        
    }
}
