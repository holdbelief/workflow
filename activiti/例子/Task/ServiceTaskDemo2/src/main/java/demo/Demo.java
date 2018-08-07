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
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = org.activiti.spring.boot.SecurityAutoConfiguration.class)
public class Demo {
    public static void main(String[] args) {
    	
    	SpringApplication.run(Demo.class, args);
        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = engine.getRepositoryService();
        repositoryService.createDeployment().addClasspathResource("diagrams/MyProcess.bpmn").deploy();
         
        Map<String, Object> var = new HashMap<>();	
        var.put("var1", 1);
        var.put("serviceTask2", new ServiceTask2());
        var.put("serviceTask3", new ServiceTask3());
        
        
        RuntimeService runtimeService = engine.getRuntimeService();
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("myDemo","s", var);
        TaskService taskService = engine.getTaskService();
        
        Task serviceTask1 = taskService.createTaskQuery().processInstanceId(processInstance.getId()).taskName("servicetask1").singleResult();
        Task serviceTask2 = taskService.createTaskQuery().processInstanceId(processInstance.getId()).taskName("serviceTask2").singleResult();
        Task serviceTask3 = taskService.createTaskQuery().processInstanceId(processInstance.getId()).taskName("serviceTask3").singleResult();
        
        HistoricProcessInstance historicProcessInstance = engine.getHistoryService().createHistoricProcessInstanceQuery().processInstanceId(processInstance.getId()).singleResult();
        Date endTime = historicProcessInstance.getEndTime();
        
        processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstance.getId()).singleResult();
        
        if ( processInstance == null ) {
        	System.out.println("流程结束, 结束时间： " + endTime);
        } else {
	        System.out.println(processInstance.isEnded());
	        System.out.println(processInstance.isSuspended());
	        System.out.println(processInstance.getActivityId());
        }
        
        
    }
}
