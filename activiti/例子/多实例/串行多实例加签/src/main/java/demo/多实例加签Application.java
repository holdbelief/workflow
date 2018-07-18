package demo;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = {org.activiti.spring.boot.SecurityAutoConfiguration.class, org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class})
public class 多实例加签Application {

	public static void main(String[] args) {
		SpringApplication.run(多实例加签Application.class, args);
		
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
        String executionId = tasktom1.getExecutionId();
        System.out.println(executionId);
        taskService.complete(tasktom1.getId());
        
        Task tasktom2 = taskService.createTaskQuery().taskAssignee("tom").processInstanceId(processInstance.getId()).singleResult();

        /*
         * 通过下面的方法获得MultiRootExecution
         */
        Execution multiRootExecution = runtimeService.createNativeExecutionQuery().
        	sql("SELECT * FROM act_ru_execution WHERE PARENT_ID_ = #{PARENT_ID_} AND IS_MI_ROOT_ = '1'")
        	.parameter("PARENT_ID_", processInstance.getId()).singleResult();
        
//        Execution currentExe = runtimeService.createExecutionQuery().executionId(tasktom2.getExecutionId()).singleResult();
//        ExecutionEntity ee = (ExecutionEntity) currentExe;
//        ExecutionEntity pe = ee.getParent();
        
//        System.out.println(pe.getVariableLocal("nrOfInstances"));
//        List<Execution> list = runtimeService.createExecutionQuery().rootProcessInstanceId(processInstance.getId()).list();
//        System.out.println(list);
//        
//        for ( Execution e : list ) {
//        	e.getActivityId();
//        }
//        executionId = tasktom2.getExecutionId();
//        System.out.println(executionId);
        
        
        // 实现加签
        runtimeService.setVariable(multiRootExecution.getId(), "nrOfInstances", assigneeList.size() + 1);

        assigneeList.add("tom");
        vars.put("assigneeList", assigneeList);
        
        taskService.complete(tasktom2.getId(), vars);
        
        Task tasktom3 = taskService.createTaskQuery().taskAssignee("tom").processInstanceId(processInstance.getId()).singleResult();
        
        taskService.complete(tasktom3.getId());
        
        HistoricProcessInstance historicProcessInstance = engine.getHistoryService().createHistoricProcessInstanceQuery().processInstanceId(processInstance.getId()).singleResult();
        Date endTime = historicProcessInstance.getEndTime();
        
        Task tasktom4 = taskService.createTaskQuery().taskAssignee("tom").processInstanceId(processInstance.getId()).singleResult();
        taskService.complete(tasktom4.getId());
        
        endTime = historicProcessInstance.getEndTime();
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
