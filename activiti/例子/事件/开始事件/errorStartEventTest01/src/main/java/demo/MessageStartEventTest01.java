package demo;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MessageStartEventTest01 {
    public static void main(String[] args) {
    	
    	SpringApplication.run(MessageStartEventTest01.class, args);
        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = engine.getRepositoryService();
        repositoryService.createDeployment().addClasspathResource("bpmn/messageStartEventTest01.bpmn").deploy();
         
        RuntimeService runtimeService = engine.getRuntimeService();
        
        runtimeService.startProcessInstanceByMessage("myMessageName");
        
        System.out.println("流程实例数量：" + runtimeService.createProcessInstanceQuery().list());
    }
}
