package com.example.demo.api;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.jy.activiti.client.repository.processdefinition.entity.ProcessDefinitions;

@Component
public class ProcessDefinitionsTestCase {
	
	@Autowired
    private RestTemplate restTemplate;
	
	public void listOfProcessDefinitions() throws URISyntaxException {
		URI uri = new URI("http://ACTIVITI-REST/repository/process-definitions/");
//		String object = this.restTemplate.getForObject(uri, String.class);
		ResponseEntity<ProcessDefinitions> resp = this.restTemplate.getForEntity(uri, ProcessDefinitions.class);
		
//		System.out.println(object);
		System.out.println(resp);
	}
}
