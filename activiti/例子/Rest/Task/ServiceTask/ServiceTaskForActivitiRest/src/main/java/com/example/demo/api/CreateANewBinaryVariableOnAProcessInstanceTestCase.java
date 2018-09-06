package com.example.demo.api;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.example.demo.ServiceTask2;

@Component
public class CreateANewBinaryVariableOnAProcessInstanceTestCase {
	
	@Autowired
    private RestTemplate restTemplate;
	
	public void execute(String processInstanceId) throws IOException, URISyntaxException {
		restTemplate.getMessageConverters().add(new ByteArrayHttpMessageConverter());
		restTemplate.getMessageConverters().add(new AllEncompassingFormHttpMessageConverter());
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Accept", MediaType.APPLICATION_JSON.toString());
		
        headers.setContentType( MediaType.parseMediaType("multipart/form-data; charset=UTF-8"));
        MultiValueMap<String, Object> form = new  LinkedMultiValueMap<String, Object> ();
        ServiceTask2 serviceTask2 = new ServiceTask2();
        
        ByteArrayOutputStream byt=new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(byt);
        oos.writeObject(serviceTask2);
        ByteArrayResource arrayResource = new ByteArrayResource(byt.toByteArray()) {
        	@Override
            public String getFilename() {
                return "serviceTask2";
            }
        };
        
        form.add("file", arrayResource);
        form.add("name", "serviceTask2");
        form.add("type", "serializable");
        
       
		
//        HttpEntity< MultiValueMap<String, Object> > formEntity = new HttpEntity< MultiValueMap<String, Object> >(form, headers);
//        RequestEntity< MultiValueMap<String, Object> > requestEntity = new RequestEntity<>(headers, method, url)
		
        URI uri = new URI("http://ACTIVITI-REST/runtime/process-instances/" + processInstanceId + "/variables/");
        
//        ResponseEntity<String> str_resp = this.restTemplate.exchange(uri, HttpMethod.POST, formEntity, String.class);
        
        String str_resp = this.restTemplate.postForObject(uri, form, String.class);

        System.out.println(str_resp);
        
//        String str_resp 
//		= this.restTemplate.exchange("http://ACTIVITI-REST/runtime/process-instances/" + processInstanceId + "/variables/", 
//				formEntity, String.class);
        
//		ResponseEntity<Variable> resp 
//			= this.restTemplate.postForEntity("http://ACTIVITI-REST/runtime/process-instances/" + processInstanceId + "/variables/", 
//					object, Variable.class);
//
//		System.out.println(resp);
	}
}
