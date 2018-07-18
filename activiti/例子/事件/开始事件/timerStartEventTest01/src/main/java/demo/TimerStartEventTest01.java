package demo;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.TaskQuery;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TimerStartEventTest01 {
    public static void main(String[] args) throws InterruptedException {
    	
    	SpringApplication.run(TimerStartEventTest01.class, args);
        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = engine.getRepositoryService();
        repositoryService.createDeployment().addClasspathResource("bpmn/timerStartEventTest01.bpmn").deploy();
         
        RuntimeService runtimeService = engine.getRuntimeService();
        
        System.out.println("流程部署成功");
        
        // 暂停60秒
        Thread.sleep(1000 * 60);
        
        TaskService taskService = engine.getTaskService();
        TaskQuery taskQuery = taskService.createTaskQuery();
        System.out.println("task size = " + taskQuery.list().size());
    }
}
