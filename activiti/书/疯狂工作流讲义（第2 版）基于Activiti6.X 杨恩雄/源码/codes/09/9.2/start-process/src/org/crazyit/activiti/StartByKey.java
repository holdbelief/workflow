package org.crazyit.activiti;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.Deployment;

/**
 * 根据key启动流程
 * @author yangenxiong
 *
 */
public class StartByKey {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// 创建流程引擎
		ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
		// 得到流程存储服务实例
		RepositoryService repositoryService = engine.getRepositoryService();
		RuntimeService runtimeService = engine.getRuntimeService();
		// 部署流程描述文件
		repositoryService.createDeployment()
				.addClasspathResource("bpmn/startByKey.bpmn20.xml").deploy();	
		//初始化流程参数
		Map<String, Object> vars = new HashMap<String, Object>();
		vars.put("days", 4);
		//启动流程
		runtimeService.startProcessInstanceByKey("vacationRequest");
		runtimeService.startProcessInstanceByKey("vacationRequest", vars);
		runtimeService.startProcessInstanceByKey("vacationRequest", "testKey");
		runtimeService.startProcessInstanceByKey("vacationRequest", "testKey2", vars);
		// 查询流程实例，结果为4
		long count = runtimeService.createProcessInstanceQuery().count();
		System.out.println("流程实例数量：" + count);
	}

}
