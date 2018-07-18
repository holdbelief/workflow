package demo;

import java.util.Date;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo")
public class DemoController {
	@Autowired
	private RepositoryService repositoryService;
	@Autowired  
	private RuntimeService runtimeService;  
	@Autowired  
	private TaskService taskService;  
	@Autowired
	private ProcessEngine engine;
	

	@RequestMapping(value="startProcessInstanceById", method=RequestMethod.GET, produces=MediaType.TEXT_PLAIN_VALUE)
	public String startProcessInstanceById() {
		String result = "";
		
		Deployment deploy = this.repositoryService.createDeployment().addClasspathResource("diagrams/MyProcess.bpmn").deploy();
		ProcessDefinition processDefinition = this.repositoryService.createProcessDefinitionQuery().deploymentId(deploy.getId()).singleResult();
		
		ProcessInstance processInstance = this.runtimeService.startProcessInstanceById(processDefinition.getId());
		
		Task usertask1 = this.taskService.createTaskQuery().processInstanceId(processInstance.getId()).taskName("usertask1").singleResult();
		result += usertask1.getId();
		this.taskService.complete(usertask1.getId());
		
		Task usertask2 = this.taskService.createTaskQuery().processInstanceId(processInstance.getId()).taskName("usertask2").singleResult();
		result += " " + usertask2.getId();
		this.taskService.complete(usertask2.getId());
		
		Task usertask3 = this.taskService.createTaskQuery().processInstanceId(processInstance.getId()).taskName("usertask3").singleResult();
		result += " " + usertask3.getId();
		this.taskService.complete(usertask3.getId());
		
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
		
		return result;
	}
	
}
