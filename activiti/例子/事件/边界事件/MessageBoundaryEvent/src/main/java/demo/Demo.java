package demo;

import java.util.Date;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ExecutionQuery;
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
        repositoryService.createDeployment().addClasspathResource("bpmn/demo.bpmn").deploy();
         
        RuntimeService runtimeService = engine.getRuntimeService();
        
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("demo");
        String processInstanceId = processInstance.getId();
         
        TaskService taskService = engine.getTaskService();

        // 结束子流程中的任务，并设置结束参数
        Task usertask1 = taskService.createTaskQuery().processInstanceId(processInstance.getId()).taskName("usertask1").singleResult();
        
        ExecutionQuery executionQuery = runtimeService.createExecutionQuery();
        Execution execution_testSignal = executionQuery.processInstanceId(processInstance.getId()).messageEventSubscriptionName("testMSG").singleResult();
        
        runtimeService.messageEventReceived("testMSG", execution_testSignal.getId());
        
        
        Task usertask2 = taskService.createTaskQuery().processInstanceId(processInstance.getId()).taskName("usertask2").singleResult();

        if ( usertask2 == null ) {
        	System.out.println("usertask2 == null 是正确的，因为通过消息，工作流已经流转到了usertask3");
        } else {
        	System.out.println("usertask2 != null 是不正确的，因为通过消息，工作流已经流应该转到了usertask3");
        }

        Task usertask3 = taskService.createTaskQuery().processInstanceId(processInstance.getId()).taskName("usertask3").singleResult();
        
        if ( usertask3 != null ) {
        	System.out.println("usertask3 != null 是正确的，因为通过消息，工作流已经流转到了usertask3");
        } else {
        	System.out.println("usertask3 == null 是不正确的，因为通过消息，工作流已经流应该转到了usertask3");
        }
        
        System.out.println("执行usertask3，之后，工作流应该流转到usertask2了");
        taskService.complete(usertask3.getId());
        
        usertask2 = taskService.createTaskQuery().processInstanceId(processInstance.getId()).taskName("usertask2").singleResult();
        
        if ( usertask2 != null ) {
        	System.out.println("现在usertask2 != null 是正确的，usertask2的id = " + usertask2.getId());
        }
        
        System.out.println("现在执行usertask2");
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
