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

/**
 * 该例子证明多实例的Loop cardinality（循环次数）只能在在进入节点时计算一次，
 * 其他时候是无法动态改变的
 *    
 *
 */
@SpringBootApplication
public class MultiInstanceTest_onepeople2 {
    public static void main(String[] args) throws InterruptedException {
    	
    	SpringApplication.run(MultiInstanceTest_onepeople2.class, args);
        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = engine.getRepositoryService();
        repositoryService.createDeployment().addClasspathResource("bpmn/multiInstance2.bpmn").deploy();
         
        Map<String, Object> vars = new HashMap<>(); //参数
        vars.put("num", 2);
        vars.put("assignee", "tom");
        
        RuntimeService runtimeService = engine.getRuntimeService();
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("multiInstance2", vars);
        
        TaskService taskService = engine.getTaskService();
        Task tasktom1 = taskService.createTaskQuery().taskAssignee("tom").processInstanceId(processInstance.getId()).singleResult();
        
        vars.put("num", 3);
        taskService.complete(tasktom1.getId(), vars);
        
        Task tasktom2 = taskService.createTaskQuery().taskAssignee("tom").processInstanceId(processInstance.getId()).singleResult();
        vars.put("num", 6);
        taskService.complete(tasktom2.getId(), vars);
        
        Task tasktom3 = taskService.createTaskQuery().taskAssignee("tom").processInstanceId(processInstance.getId()).singleResult();
        vars.put("num", 4);
        taskService.complete(tasktom3.getId(), vars);
        
        System.out.println("end");
      
    }
}
