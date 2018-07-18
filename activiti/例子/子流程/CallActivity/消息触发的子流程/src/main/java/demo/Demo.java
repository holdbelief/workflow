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
import org.activiti.engine.repository.Deployment;
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
        Deployment deploy = repositoryService.createDeployment().name("callActivityDemo").addClasspathResource("diagrams/MyProcess.bpmn").addClasspathResource("diagrams/MyProcess2.bpmn").deploy();
        
        Map<String, Object> var = new HashMap<>();	
        var.put("outterVar1", 1);
        
        RuntimeService runtimeService = engine.getRuntimeService();
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("myDemo", var);
        
        TaskService taskService = engine.getTaskService();
        Task usertask1 = taskService.createTaskQuery().processInstanceId(processInstance.getId()).taskName("usertask1").singleResult();
        System.out.println("外层的usertask1的id = " + usertask1.getId());
        System.out.println("outterVar1 = " + runtimeService.getVariable(processInstance.getId(), "outterVar1"));
        
        //发出消息，触发MessageBoundaryEvent
        ExecutionQuery executionQuery = runtimeService.createExecutionQuery();
        Execution execution_MSG 
        		= executionQuery.processInstanceId(processInstance.getId()).messageEventSubscriptionName("msg1").singleResult();
        
        runtimeService.messageEventReceived("msg1", execution_MSG.getId());
        
        // 由于是cancelActivity=true，所以下面的代码不能执行
//        taskService.complete(usertask1.getId());
        
        ProcessInstance piSub = runtimeService.createProcessInstanceQuery().superProcessInstanceId(processInstance.getId()).processDefinitionKey("calledProcess2").singleResult();
        Task calledProcess2_usertask1 = taskService.createTaskQuery().processInstanceId(piSub.getId()).taskName("calledProcess2_usertask1").singleResult();
        System.out.println(taskService.getVariable(calledProcess2_usertask1.getId(), "outterVal1"));;
        System.out.println(taskService.getVariable(calledProcess2_usertask1.getId(), "innerVar1"));
        System.out.println(runtimeService.getVariable(piSub.getId(), "innerVar1"));
        System.out.println("calledProcess2的calledProcess2_usertask1的id = " + calledProcess2_usertask1.getId());
        
        Map m_inner = new HashMap();
        int innerVar2_val = Integer.parseInt(runtimeService.getVariable(piSub.getId(), "innerVar1").toString()) + 1;
        m_inner.put("innerVar2", innerVar2_val);
        
        taskService.complete(calledProcess2_usertask1.getId(), m_inner);
        
        Task usertask2 = taskService.createTaskQuery().processInstanceId(processInstance.getId()).taskName("usertask2").singleResult();
        System.out.println("外层的usertask2的id = " + usertask2.getId());
        System.out.println("外层的outterVar2的值 = " + runtimeService.getVariable(processInstance.getId(), "outterVar2").toString());
        System.out.println("外层的outterVar1的值 = " + runtimeService.getVariable(processInstance.getId(), "outterVar1").toString());
        
        taskService.complete(usertask2.getId());
         
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
