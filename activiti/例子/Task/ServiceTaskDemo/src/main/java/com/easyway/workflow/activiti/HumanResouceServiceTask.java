package com.easyway.workflow.activiti;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * execute方法的参数DelegateExecution execution可以在流程中各个结点之间传递流程变量。 
*自动执行 
<serviceTask>元素，可以实现自动活动，语法如下所示： 
<serviceTask id="serviceTaskId" name="serviceTaskName" 
activiti:class="org.shirdrn.workflow.activiti.gateway.ServiceTaskClass"/> 
其中，activiti:class属性为该结点对应的处理类，该类要求实现org.activiti.engine.delegate.JavaDelegate接口

*  <serviceTask id="servicetask5" name="人事部门同意" activiti:class="com.easyway.workflow.activiti.gateway.HumanResouceServiceTask"></serviceTask>
*
* 人事部门审批过程
* @author longgangbai
* 
* 2011-12-17  下午07:45:47
*/
public class HumanResouceServiceTask implements JavaDelegate {

	private final Logger log = LoggerFactory.getLogger(HumanResouceServiceTask.class.getName());
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		Thread.sleep(10000);
		log.info("variavles=" + execution.getVariables());
		execution.setVariable("人事部门", "请假天数大约3天，同意请假。");
		log.info("人事部门 ，请假天数大约3天，同意请假。");
	}

}
