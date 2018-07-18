package com.easyway.workflow.activiti;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 项目组长批准请假
 *    模拟当开发人员请假小于3天，组长比准
 *    
 *    
 *     <serviceTask id="servicetask3" name="项目组长同意" activiti:class="com.easyway.workflow.activiti.gateway.TeamLeaderServiceTask"></serviceTask>
 *  
 * @author longgangbai
 * 
 * 2011-12-17  上午09:07:37
 */
public class TeamLeaderServiceTask implements JavaDelegate {

	private final Logger log = LoggerFactory.getLogger(TeamLeaderServiceTask.class.getName());
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		Thread.sleep(10000);
		log.info("variavles=" + execution.getVariables());
		execution.setVariable("项目组长", "请假天数小于3天，同意请假。");
		log.info("项目组长,请假天数小于3天，同意请假。");
	}

}
