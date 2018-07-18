package org.crazyit.activiti;

import java.util.UUID;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;

/**
 * 参数类型
 * @author yangenxiong
 *
 */
public class SerilizableVariableType {

	public static void main(String[] args) throws Exception {
		//获取流程引擎实例
		ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
		// 获取任务服务组件
		TaskService taskService = engine.getTaskService();
		//保存第一个Task
		Task task1 = taskService.newTask(UUID.randomUUID().toString());
		task1.setName("出差申请");
		taskService.saveTask(task1);
		//设置序列化参数
		taskService.setVariable(task1.getId(), "arg0", new TestVO("crazyit"));
		TestVO var = (TestVO)taskService.getVariable(task1.getId(), "arg0");
		System.out.println(var.getName());
	}

}
