package demo;

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
public class TimerBoundaryEventTest {
    public static void main(String[] args) {
    	
    	SpringApplication.run(TimerBoundaryEventTest.class, args);
        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = engine.getRepositoryService();
        repositoryService.createDeployment().addClasspathResource("bpmn/TimerBoundaryEvent.bpmn").deploy();
         
        RuntimeService runtimeService = engine.getRuntimeService();
        Map<String, Object> var = new HashMap<>();
        var.put("timeDuration", "PT10S");
//        var.put("timeDuration", "");
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("TimerBoundaryEvent", var);
         
        TaskService taskService = engine.getTaskService();
        // 结束子流程中的任务，并设置结束参数
//        Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
        
//        Task task = taskService.createTaskQuery().singleResult();
         
    }
}
