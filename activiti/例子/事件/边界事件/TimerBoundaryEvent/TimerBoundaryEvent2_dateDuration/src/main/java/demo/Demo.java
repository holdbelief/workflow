package demo;

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
import org.activiti.engine.task.TaskQuery;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = org.activiti.spring.boot.SecurityAutoConfiguration.class)
public class Demo {
    public static void main(String[] args) throws InterruptedException {
    	
    	SpringApplication.run(Demo.class, args);
        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = engine.getRepositoryService();
        repositoryService.createDeployment().name("callActivityDemo").addClasspathResource("diagrams/MyProcess.bpmn").deploy();
        
        RuntimeService runtimeService = engine.getRuntimeService();
        
        Map<String, Object> var = new HashMap<>();
        var.put("timeDuration", "PT10S");
        
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("myDemo", var);
        TaskService taskService = engine.getTaskService();
        TaskQuery taskQuery = taskService.createTaskQuery();
        
        Task usertask1 = taskQuery.processInstanceId(processInstance.getId()).taskName("usertask1").singleResult();
        taskService.complete(usertask1.getId());
        
        
        Task usertask5 = taskQuery.processInstanceId(processInstance.getId()).taskName("usertask5").singleResult();
        
        // 停止15秒
     	Thread.sleep(1000 * 20);

        Task usertask4 = taskQuery.processInstanceId(processInstance.getId()).taskName("usertask4").singleResult();
        System.out.println("20秒钟后，usertask4有值，不等于null，usertask4 的 ID = " + usertask4.getId());
        taskService.complete(usertask4.getId());
        
        // 验证流程是否全部完成, 由于usertask4后面接的是TerminateEndEvent，所以usertask4结束之后，整个流程实例就应该结束了
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
