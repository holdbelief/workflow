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
        
        taskService.complete(usertask1.getId());
        
        Task usertask2 = taskService.createTaskQuery().processInstanceId(processInstance.getId()).taskName("usertask2").singleResult();
        taskService.complete(usertask2.getId());
        
        
        /*
         * 永远无法用 processInstance.isEnded() 判断流程实例是否完成。因为processInstance是在启动流程的时候创建的java对象，那么在流程结束的时候，
         * 该java对象是无法改变的。可以通过如下api重新获取prcessInstance对象：
         * processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
         * 但是如果流程确实走完了，上面api只能得到null，因为是通过runtimeService获取的，所以流程走完了，act_ru表中关于该流程的所有实例都已经从表中删除掉了,所以会得到null
         * 所以可以通过是否为null来判断流程是否走完。
         * 
         * 也可以通过历史api判断流程是否已经在act_hi历史表中了，不管流程实例是否结束，历史表中都有historicProcessInstance记录，但是区别是
         * 流程已经结束：historicProcessInstance.getEndTime()返回流程结束日期
         * 流程没有结束：historicProcessInstance。getEndTime()返回null
         */
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
