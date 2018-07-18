package com.jiayidai.jiedai.SpringBootWithJunit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = org.activiti.spring.boot.SecurityAutoConfiguration.class)
public class SpringBootWithJunitApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootWithJunitApplication.class, args);
	}
}
