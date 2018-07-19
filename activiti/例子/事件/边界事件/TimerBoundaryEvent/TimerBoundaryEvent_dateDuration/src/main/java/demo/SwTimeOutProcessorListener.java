package demo;

import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SwTimeOutProcessorListener implements JavaDelegate {

	@Autowired  
    private TaskService taskService;
	
	@Override
	public void execute(DelegateExecution execution) {
		//获得事务id  
        String businessKey = execution.getProcessInstanceBusinessKey();  
        Task task = taskService.createTaskQuery().processInstanceId(execution.getProcessInstanceId()).singleResult();
        String assignee = task.getAssignee();  
        
        System.out.println(assignee);  
	}

}
