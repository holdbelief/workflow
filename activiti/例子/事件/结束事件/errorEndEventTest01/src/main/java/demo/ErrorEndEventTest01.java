package demo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ErrorEndEventTest01 {
    public static void main(String[] args) {
    	
    	SpringApplication.run(ErrorEndEventTest01.class, args);
        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = engine.getRepositoryService();
        repositoryService.createDeployment().addClasspathResource("bpmn/errorEndEventTest01.bpmn").deploy();
         
        RuntimeService runtimeService = engine.getRuntimeService();
        runtimeService.startProcessInstanceByKey("errorEndEventTest01");
         
        TaskService taskService = engine.getTaskService();
        // 结束子流程中的任务，并设置结束参数
        Task task = taskService.createTaskQuery().singleResult();
        Map<String, Object> taskArgs = new HashMap<String, Object>();
        taskArgs.put("flag", false);
        taskService.complete(task.getId(), taskArgs);
         
        // 查看到达的任务
        List<Task> tasks = taskService.createTaskQuery().list();
        for (Task t : tasks) {
            System.out.println(t.getId() + "  =  " + t.getName());
        }
    }
}
