package demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
//@SpringBootApplication(exclude = org.activiti.spring.boot.SecurityAutoConfiguration.class)
@SpringBootApplication(exclude = {org.activiti.spring.boot.SecurityAutoConfiguration.class, org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class})

public class SpringbootActivitiRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootActivitiRestApplication.class, args);
	}
}
