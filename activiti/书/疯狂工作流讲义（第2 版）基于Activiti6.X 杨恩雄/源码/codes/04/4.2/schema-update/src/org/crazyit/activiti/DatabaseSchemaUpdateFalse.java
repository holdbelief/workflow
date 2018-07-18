package org.crazyit.activiti;

import org.activiti.engine.ProcessEngineConfiguration;

public class DatabaseSchemaUpdateFalse {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//读取Activiti配置
		ProcessEngineConfiguration config = ProcessEngineConfiguration
				.createProcessEngineConfigurationFromResource("schemaUpdate-false.xml");
		//启动Activiti
		config.buildProcessEngine();
	}

}
