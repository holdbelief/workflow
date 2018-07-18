package demo;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.bug315.bean.MyBeanStartTimerEvent;

@SpringBootApplication
public class StartTimerEvent01 {
	public static void main(String[] args) throws InterruptedException {

		SpringApplication.run(StartTimerEvent01.class, args);
		ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();

		// 启动JobExecutor
		System.out.println("启动JobExecutor...");
		engine.getProcessEngineConfiguration().getJobExecutor().start();

		RepositoryService repositoryService = engine.getRepositoryService();
		RuntimeService runtimeService = engine.getRuntimeService();
		TaskService taskService = engine.getTaskService();

		// 部署流程
		System.out.println("部署流程...");
		repositoryService.createDeployment().addClasspathResource("bpmn/startTimerEvent01.bpmn").deploy();

		// 启动流程
		System.out.println("开始启动流程...");
		Map<String, Object> variables = new HashMap<>();
		variables.put("myDate1", "2015-02-25T20:34:00");
		variables.put("myDate2", "2015-02-25T20:35:00");
		variables.put("myBean", new MyBeanStartTimerEvent());
		runtimeService.startProcessInstanceByKey("startTimerEvent01", variables);

		// 查询第一个任务（需要等到第一个定时器触发后才能得到任务）
		Thread.sleep(1000 * 60);

		Task task = taskService.createTaskQuery().singleResult();
		System.out.println("task = " + task);
		System.out.println("任务ID：" + task.getId() + "  任务名称：" + task.getName());
		taskService.complete(task.getId());

		// 重置第二个定时器的时间
		Thread.sleep(1000 * 5);
		Map<String, Object> variables2 = new HashMap<>();
		variables2.put("myDate2", "2015-02-25T20:37:00");
		runtimeService.setVariables(task.getExecutionId(), variables2);

		// 流程结束
		System.out.println("流程结束...");
	}
}
