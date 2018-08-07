package com.example.demo.api;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.jy.activiti.client.runtime.processinstance.entity.ProcessInstance;

@Component
public class StartProcessInstanceByKeyTestCase {
	@Autowired
    private RestTemplate restTemplate;
	
	public void testStartProcessInstance() throws URISyntaxException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("processDefinitionKey", "myDemo");
		
//		ResponseEntity<ProcessInstance> resp 
//			= this.restTemplate.postForEntity("http://ACTIVITI-REST/runtime/process-instances/", params, ProcessInstance.class);

//		System.out.println(resp);

		String param = "{\"processDefinitionKey\":\"myDemo\"}";
		
		String str 
		= this.restTemplate.postForObject("http://ACTIVITI-REST/runtime/process-instances/", 
				params, String.class);
		System.out.println(str);
	}
}
