package com.example.demo;

import java.io.IOException;
import java.net.URISyntaxException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import com.example.demo.api.CreateANewBinaryVariableOnAProcessInstanceTestCase;
import com.example.demo.utils.SpringUtil;

//import com.example.demo.api.StartProcessInstanceByKey;


//@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@EnableDiscoveryClient
@SpringBootApplication(exclude = { /* org.activiti.spring.boot.SecurityAutoConfiguration.class, */
		org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class })
public class ServiceTaskForActivitiRestApplication {
	
	public static void main(String[] args) throws URISyntaxException, IOException {
		SpringApplication.run(ServiceTaskForActivitiRestApplication.class, args);
//		ProcessDefinitionsTestCase ProcessDefinitionsTestCase = SpringUtil.getBean(ProcessDefinitionsTestCase.class);
//		ProcessDefinitionsTestCase.listOfProcessDefinitions();

//		StartProcessInstanceByKeyTestCase StartProcessInstanceByKeyTestCase = SpringUtil.getBean(StartProcessInstanceByKeyTestCase.class);
//		StartProcessInstanceByKeyTestCase.testStartProcessInstance();
		
		CreateANewBinaryVariableOnAProcessInstanceTestCase CreateANewBinaryVariableOnAProcessInstanceTestCase = SpringUtil.getBean(CreateANewBinaryVariableOnAProcessInstanceTestCase.class);
		
		CreateANewBinaryVariableOnAProcessInstanceTestCase.execute("2521");
	}
}
