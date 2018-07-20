package tasklistener;

import java.util.HashMap;
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
public class TaskListenerDemo {
    public static void main(String[] args) {
    	
    	SpringApplication.run(TaskListenerDemo.class, args);
        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = engine.getRepositoryService();
        repositoryService.createDeployment().addClasspathResource("bpmn/TaskListenerDemo.bpmn").deploy();
         
        RuntimeService runtimeService = engine.getRuntimeService();
        
        Map<String, Object> vars = new HashMap<>();
        vars.put("var", "myvar");
        vars.put("myListener_TaskListener3", new MyListener_TaskListener3());
        
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("TaskListenerDemo", vars);
         
        TaskService taskService = engine.getTaskService();
        
        // 结束子流程中的任务，并设置结束参数
        Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
        
        taskService.complete(task.getId());
        if (processInstance.isEnded())
        {
        	System.out.println("end");
        }
    }
}
