package com.jiayidai.jiedai.SpringBootWithJunit;

import static org.junit.Assert.assertEquals;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.test.Deployment;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootWithJunitApplication.class)
public class SpringBootWithJunitApplicationTests {

	
//	private ProcessEngine processEngine;
	
	protected String deploymentId;
	
	@Autowired
	protected RepositoryService repositoryService;
	
	@Autowired
	protected RuntimeService runtimeService;
	
	@Autowired
	protected TaskService taskService;
	
	@Autowired
	protected FormService formService;
	
	@Autowired
	protected HistoryService historyService;
	
	@Autowired
	protected IdentityService identityService;
	
	@Autowired
	protected ManagementService managementService;
	
	@Before
	public void initialize() {
		deploymentId = repositoryService.createDeployment()
				.addClasspathResource("bpmn/ServiceTaskDemo.bpmn")
				.deploy().getId();
	}
	
//	@Deployment(resources="bpmn/ServiceTaskDemo.bpmn") 这种方法也可以自动化部署，但是获取deploymentId比较困难
	@Test
	public void testForkJoin() {
		//根据流程ID获取流程实例
		ProcessInstance pi = runtimeService.startProcessInstanceByKey("AutoExecuteTask");
		//根据运行是服务检查是否执行的结束
		assertEquals(true, pi.isEnded());
	}

}
