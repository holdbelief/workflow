package demo;

import java.util.Date;
import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ExecutionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = org.activiti.spring.boot.SecurityAutoConfiguration.class)
public class demo_CancelActivityIsFalse {
    public static void main(String[] args) {
    	
    	SpringApplication.run(demo_CancelActivityIsFalse.class, args);
        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = engine.getRepositoryService();
        Deployment deploy = repositoryService.createDeployment().addClasspathResource("bpmn/demo_CancelActivity=false.bpmn").deploy();
         
        RuntimeService runtimeService = engine.getRuntimeService();
        
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("demo");
        String processInstanceId = processInstance.getId();
         
        TaskService taskService = engine.getTaskService();

        // 结束子流程中的任务，并设置结束参数
        Task usertask1 = taskService.createTaskQuery().processInstanceId(processInstance.getId()).taskName("usertask1").singleResult();
        
        System.out.println("第一步：usertask1 id = " + usertask1.getId());
        
        ExecutionQuery executionQuery = runtimeService.createExecutionQuery();
        Execution execution_testMessage = executionQuery.processInstanceId(processInstance.getId()).messageEventSubscriptionName("testMSG").singleResult();
        
        runtimeService.messageEventReceived("testMSG", execution_testMessage.getId());
        runtimeService.messageEventReceived("testMSG", execution_testMessage.getId());
        runtimeService.messageEventReceived("testMSG", execution_testMessage.getId());
        
        System.out.println("触发message");
        
        System.out.println("usertask3的个数：" + taskService.createTaskQuery().processInstanceId(processInstance.getId()).taskName("usertask3").count());
        
        List<Task> usertask3s = taskService.createTaskQuery().processInstanceId(processInstance.getId()).taskName("usertask3").list();
        
        for ( Task t : usertask3s ) {
        	taskService.complete(t.getId());
        }
        
        
        taskService.complete(usertask1.getId());
        
        
        
        
//        taskService.complete(usertask3.getId());
//        
//        usertask1 = taskService.createTaskQuery().processInstanceId(processInstance.getId()).taskName("usertask1").singleResult();
//        
//        System.out.println("第三步：又回到了usertask1 id" + usertask1.getId());
//        
////        execution_testSignal = executionQuery.processInstanceId(processInstance.getId()).messageEventSubscriptionName("testMSG").singleResult();
////        runtimeService.messageEventReceived("testMSG", execution_testSignal.getId());
//        
//        taskService.complete(usertask1.getId());
//        
        Task usertask2 = taskService.createTaskQuery().processInstanceId(processInstance.getId()).taskName("usertask2").singleResult();
//        
//        System.out.println("第四步：usertask2 id = " + usertask2.getId());
//      
        
        taskService.complete(usertask2.getId());
        
        
        usertask1 = taskService.createTaskQuery().processInstanceId(processInstance.getId()).taskName("usertask1").singleResult();
        taskService.complete(usertask1.getId());
        
        usertask2 = taskService.createTaskQuery().processInstanceId(processInstance.getId()).taskName("usertask2").singleResult();
        taskService.complete(usertask2.getId());
        
        HistoricProcessInstance historicProcessInstance = engine.getHistoryService().createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        Date endTime = historicProcessInstance.getEndTime();
        
        
        processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        
        if ( processInstance == null ) {
        	System.out.println("流程结束, 结束时间： " + endTime);
        } else {
	        System.out.println(processInstance.isEnded());
	        System.out.println(processInstance.isSuspended());
	        System.out.println(processInstance.getActivityId());
        }
        

    }
}
