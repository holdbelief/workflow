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
public class MultiInstanceTest_onepeople {
    public static void main(String[] args) throws InterruptedException {
    	
    	SpringApplication.run(MultiInstanceTest_onepeople.class, args);
        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = engine.getRepositoryService();
        repositoryService.createDeployment().addClasspathResource("bpmn/multiInstance.bpmn").deploy();
         
        List<String> assigneeList=new ArrayList<>(); //分配任务的人员
        assigneeList.add("tom");
        assigneeList.add("tom");
        assigneeList.add("tom");
        Map<String, Object> vars = new HashMap<>(); //参数
        vars.put("assigneeList", assigneeList);
        
        RuntimeService runtimeService = engine.getRuntimeService();
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("multiInstance", vars);
        
        TaskService taskService = engine.getTaskService();
        Task tasktom1 = taskService.createTaskQuery().taskAssignee("tom").processInstanceId(processInstance.getId()).singleResult();
        
        taskService.complete(tasktom1.getId());
        
        Task tasktom2 = taskService.createTaskQuery().taskAssignee("tom").processInstanceId(processInstance.getId()).singleResult();
        
        taskService.complete(tasktom2.getId());
        
        Task tasktom3 = taskService.createTaskQuery().taskAssignee("tom").processInstanceId(processInstance.getId()).singleResult();
        
        taskService.complete(tasktom3.getId());
        
        System.out.println("end");
      
    }
}
